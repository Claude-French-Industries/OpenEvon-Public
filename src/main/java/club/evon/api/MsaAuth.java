package club.evon.api;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import club.evon.ClientContext;


/**
 * Microsoft / Xbox Live / Minecraft authentication.
 *
 * <p>Implements the three credential types an alt manager actually receives:
 *
 * <ul>
 *   <li><b>Refresh token</b> - the common case for saved alts. Redeemed at
 *       {@code login.live.com/oauth20_token.srf}. The legacy Xbox client
 *       ({@code 00000000402b5328} + {@code oauth20_desktop.srf} + the {@code MBI_SSL} scope) is
 *       tried first because that is what alt shops and checkers issue against; the client's own
 *       OAuth registration is the fallback.</li>
 *   <li><b>Minecraft access token</b> - a short-lived token that can only be used directly against
 *       {@code api.minecraftservices.com}.</li>
 *   <li><b>MSA cookies</b> - a Netscape {@code cookies.txt} (or a bare {@code __Host-MSAAUTHP=...}
 *       line). The cookie is exchanged for an MSA access token through the
 *       {@code login.live.com -> sisu.xboxlive.com} redirect chain, then XBL -> XSTS -> Minecraft.</li>
 * </ul>
 *
 * <p>Every path terminates in the same XBL chain, and the RPS ticket prefix differs by source
 * ({@code d=} for MSA access tokens, {@code t=} for the legacy refresh flow) - getting that wrong
 * is the usual cause of a "token does not work" report.
 */
public final class MsaAuth {
   private static final String USER_AGENT =
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36";

   /** Legacy Xbox Live client used by alt shops / checkers. */
   private static final String LEGACY_CLIENT_ID = "00000000402b5328";
   private static final String LEGACY_REDIRECT = "https://login.live.com/oauth20_desktop.srf";
   private static final String LEGACY_SCOPE = "service::user.auth.xboxlive.com::MBI_SSL";

   // The client's own MSA registration (the one the browser login uses) is NOT written here: its
   // secret is a live credential and committing it trips GitHub secret scanning. Both values are
   // read from AccountAuthService's decrypted table via getOAuthClientId()/getOAuthClientSecret().

   private static final String TOKEN_ENDPOINT = "https://login.live.com/oauth20_token.srf";
   private static final String XBL_ENDPOINT = "https://user.auth.xboxlive.com/user/authenticate";
   private static final String XSTS_ENDPOINT = "https://xsts.auth.xboxlive.com/xsts/authorize";
   private static final String MC_LOGIN_ENDPOINT = "https://api.minecraftservices.com/authentication/login_with_xbox";
   private static final String MC_ENTITLEMENTS_ENDPOINT = "https://api.minecraftservices.com/entitlements/mcstore";
   private static final String MC_PROFILE_ENDPOINT = "https://api.minecraftservices.com/minecraft/profile";
   private static final String SISU_URL =
      "https://login.live.com/oauth20_authorize.srf?redirect_uri=https://sisu.xboxlive.com/connect/oauth/XboxLive"
         + "&response_type=token&client_id=000000004420578E&scope=XboxLive.Signin%20XboxLive.offline_access";

   private static final Pattern JWT = Pattern.compile("^[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+\\.[A-Za-z0-9_-]+$");
   private static final Pattern COOKIE_PAIR = Pattern.compile("([^=;\\s]+)=([^;]*)");

   private MsaAuth() {
   }

   /** Outcome of a login attempt. */
   public static final class Result {
      public final boolean success;
      public final String accessToken;
      public final String refreshToken;
      public final String uuid;
      public final String name;
      public final String message;

      private Result(boolean success, String accessToken, String refreshToken, String uuid, String name, String message) {
         this.success = success;
         this.accessToken = accessToken;
         this.refreshToken = refreshToken;
         this.uuid = uuid;
         this.name = name;
         this.message = message;
      }

      public static Result ok(String accessToken, String refreshToken, String uuid, String name) {
         return new Result(true, accessToken, refreshToken, uuid, name, null);
      }

      static Result fail(String message) {
         return new Result(false, null, null, null, null, message);
      }
   }

   // ------------------------------------------------------------------ entry points

   /**
    * Logs in with whatever the user pasted: a refresh token, a Minecraft access token, or a cookie.
    * The token type is detected, and if the first interpretation fails the other is tried, so a
    * mislabelled token still works.
    */
   public static Result loginWithToken(String input) {
      final String token = cleanToken(input);
      if (token.isEmpty()) {
         return Result.fail("No token found.");
      }

      final boolean looksLikeJwt = JWT.matcher(token).matches();

      if (looksLikeJwt) {
         final Result direct = loginWithMinecraftAccessToken(token);
         if (direct.success) {
            return direct;
         }
         return prefer(loginWithRefreshToken(token), direct);
      }

      final Result refreshed = loginWithRefreshToken(token);
      if (refreshed.success) {
         return refreshed;
      }
      return prefer(loginWithMinecraftAccessToken(token), refreshed);
   }

   /** Redeems an MSA refresh token for a Minecraft session. */
   public static Result loginWithRefreshToken(String refreshToken) {
      final String token = cleanToken(refreshToken);
      if (token.isEmpty()) {
         return Result.fail("No refresh token found.");
      }

      // 1) Legacy Xbox client - what alt shops and checkers issue against.
      Result legacy = redeemRefreshToken(token, LEGACY_CLIENT_ID, null, LEGACY_REDIRECT, LEGACY_SCOPE, "t");
      if (legacy.success) {
         return legacy;
      }

      // 2) The client's own OAuth registration, when its credentials are available.
      final String ownClientId = AccountAuthService.getOAuthClientId();
      final String ownClientSecret = AccountAuthService.getOAuthClientSecret();
      if (ownClientId != null && ownClientSecret != null) {
         Result own = redeemRefreshToken(token, ownClientId, ownClientSecret, null, null, "d");
         if (own.success) {
            return own;
         }
      }

      return Result.fail("Refresh token rejected (" + legacy.message + ")");
   }

   /** Validates a Minecraft access token against the profile endpoint. */
   public static Result loginWithMinecraftAccessToken(String accessToken) {
      final String token = cleanToken(accessToken);
      if (token.isEmpty()) {
         return Result.fail("No access token found.");
      }

      final JsonObject profile = fetchProfile(token);
      if (profile == null) {
         return Result.fail("Access token rejected or the account does not own Minecraft.");
      }

      return Result.ok(token, null, id(profile), name(profile));
   }

   /** Logs in from the contents of a Netscape {@code cookies.txt} or a bare cookie line. */
   public static Result loginWithCookieText(String cookieText) {
      if (cookieText == null || cookieText.isBlank()) {
         return Result.fail("The cookie file is empty.");
      }

      final Map<String, String> cookies = parseCookies(cookieText);
      if (cookies.isEmpty()) {
         return Result.fail("No cookies found in the file.");
      }

      final String msaToken = exchangeCookiesForMsaToken(cookies);
      if (msaToken == null) {
         return Result.fail("Cookie rejected (expired, 2FA required, or rate limited).");
      }

      return xblChain(msaToken, "d", null);
   }

   // ------------------------------------------------------------------ refresh flow

   private static Result redeemRefreshToken(
      String refreshToken, String clientId, String clientSecret, String redirectUri, String scope, String rpsPrefix
   ) {
      final StringBuilder form = new StringBuilder();
      form.append("client_id=").append(urlEncode(clientId));
      if (clientSecret != null) {
         form.append("&client_secret=").append(urlEncode(clientSecret));
      }
      form.append("&grant_type=refresh_token");
      form.append("&refresh_token=").append(urlEncode(refreshToken));
      if (redirectUri != null) {
         form.append("&redirect_uri=").append(urlEncode(redirectUri));
      }
      if (scope != null) {
         form.append("&scope=").append(urlEncode(scope));
      }

      final JsonObject response = postJson(TOKEN_ENDPOINT, form.toString(), false);
      if (response == null || !response.has("access_token")) {
         return Result.fail("live.com refused the refresh token");
      }

      return xblChain(response.get("access_token").getAsString(), rpsPrefix, refreshToken);
   }

   // ------------------------------------------------------------------ cookie flow

   /**
    * Walks {@code login.live.com -> sisu.xboxlive.com} with the supplied cookie jar until the
    * {@code #access_token=} fragment shows up, and returns that MSA access token.
    */
   private static String exchangeCookiesForMsaToken(Map<String, String> cookies) {
      String currentUrl = SISU_URL;

      for (int redirect = 0; redirect < 12; redirect++) {
         final HttpURLConnection connection = open(currentUrl, "GET", cookies);
         if (connection == null) {
            return null;
         }

         try {
            final int status = connection.getResponseCode();
            String location = connection.getHeaderField("Location");
            final String finalUrl = connection.getURL().toString();

            storeCookies(cookies, connection);
            final String body = readBody(connection);

            String token = tokenFrom(location);
            if (token == null) {
               token = tokenFrom(finalUrl);
            }
            if (token == null) {
               token = tokenFromBody(body);
            }
            if (token != null) {
               return token;
            }

            if (body != null) {
               final String lower = body.toLowerCase(Locale.ROOT);
               if (lower.contains("recover?mkt") || lower.contains("identity/confirm?mkt")
                  || lower.contains("email/confirm?mkt") || lower.contains("/abuse?mkt=")
                  || lower.contains("help us protect your account")) {
                  return null; // 2FA gate - cannot be completed from a cookie
               }
               if (lower.contains("too many requests") || lower.contains("please retry with a different device")) {
                  return null; // rate limited
               }
            }

            if (location == null || location.isEmpty() || status < 300 || status >= 400) {
               return null;
            }

            if (!location.startsWith("http")) {
               location = new URL(new URL(currentUrl), location).toString();
            }
            location = location.replace(" ", "%20");
            currentUrl = location;
         } catch (Exception exception) {
            return null;
         } finally {
            connection.disconnect();
         }
      }

      return null;
   }

   private static String tokenFrom(String url) {
      if (url == null) {
         return null;
      }

      int index = url.indexOf("#access_token=");
      if (index < 0) {
         return null;
      }

      String value = url.substring(index + "#access_token=".length());
      final int end = firstOf(value, '&', '"', '\'', ' ');
      if (end >= 0) {
         value = value.substring(0, end);
      }

      try {
         return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
      } catch (Exception exception) {
         return value;
      }
   }

   private static String tokenFromBody(String body) {
      if (body == null) {
         return null;
      }

      final int index = body.indexOf("#access_token=");
      if (index < 0) {
         return null;
      }

      return tokenFrom("https://placeholder/" + body.substring(index));
   }

   private static int firstOf(String text, char... chars) {
      int best = -1;
      for (char c : chars) {
         final int at = text.indexOf(c);
         if (at >= 0 && (best < 0 || at < best)) {
            best = at;
         }
      }
      return best;
   }

   /**
    * Parses Netscape cookie files, bare {@code name=value} lines and {@code Set-Cookie} headers.
    * Skips blanks, {@code #} comments and the {@code #HttpOnly_} prefix.
    */
   static Map<String, String> parseCookies(String text) {
      final Map<String, String> cookies = new LinkedHashMap<>();

      for (String rawLine : text.split("\r?\n")) {
         String line = rawLine.trim();
         if (line.isEmpty()) {
            continue;
         }
         if (line.startsWith("#HttpOnly_")) {
            line = line.substring("#HttpOnly_".length());
         } else if (line.startsWith("#")) {
            continue;
         }

         if (line.contains("\t")) {
            // Netscape: domain, flag, path, secure, expiry, name, value
            final String[] parts = line.split("\t");
            if (parts.length < 7) {
               continue;
            }
            putCookie(cookies, parts[parts.length - 2], parts[parts.length - 1]);
            continue;
         }

         final Matcher matcher = COOKIE_PAIR.matcher(line);
         while (matcher.find()) {
            putCookie(cookies, matcher.group(1), matcher.group(2));
         }
      }

      return cookies;
   }

   private static void putCookie(Map<String, String> cookies, String name, String value) {
      if (name == null || value == null) {
         return;
      }

      final String key = name.trim();
      final String val = value.trim();
      if (key.isEmpty() || val.isEmpty()) {
         return;
      }

      cookies.put(key, val);
   }

   private static void storeCookies(Map<String, String> cookies, HttpURLConnection connection) {
      final Map<String, List<String>> headers = connection.getHeaderFields();
      if (headers == null) {
         return;
      }

      for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
         if (entry.getKey() == null || !entry.getKey().equalsIgnoreCase("Set-Cookie")) {
            continue;
         }

         for (String header : entry.getValue()) {
            final int semi = header.indexOf(';');
            final String pair = semi > 0 ? header.substring(0, semi) : header;
            final int eq = pair.indexOf('=');
            if (eq > 0) {
               putCookie(cookies, pair.substring(0, eq), pair.substring(eq + 1));
            }
         }
      }
   }

   // ------------------------------------------------------------------ XBL -> XSTS -> Minecraft

   private static Result xblChain(String msaAccessToken, String rpsPrefix, String refreshToken) {
      final JsonObject xbl = postJson(
         XBL_ENDPOINT,
         "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\""
            + rpsPrefix + "=" + msaAccessToken + "\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}",
         true
      );
      if (xbl == null || !xbl.has("Token")) {
         return Result.fail("Xbox Live rejected the Microsoft token.");
      }

      final String uhs = uhs(xbl);
      if (uhs == null) {
         return Result.fail("Xbox Live response had no user hash.");
      }

      final JsonObject xsts = postJson(
         XSTS_ENDPOINT,
         "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"" + xbl.get("Token").getAsString()
            + "\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}",
         true
      );
      if (xsts == null || !xsts.has("Token")) {
         return Result.fail(xstsError(xsts));
      }

      final String xstsUhs = uhs(xsts) != null ? uhs(xsts) : uhs;
      final JsonObject mc = postJson(
         MC_LOGIN_ENDPOINT,
         "{\"identityToken\":\"XBL3.0 x=" + xstsUhs + ";" + xsts.get("Token").getAsString() + "\"}",
         true
      );
      if (mc == null || !mc.has("access_token")) {
         return Result.fail("Minecraft services refused the Xbox token.");
      }

      final String accessToken = mc.get("access_token").getAsString();
      final JsonObject profile = fetchProfile(accessToken);
      if (profile == null) {
         return Result.fail("Signed in, but the account does not own Minecraft.");
      }

      return Result.ok(accessToken, refreshToken, id(profile), name(profile));
   }

   private static String xstsError(JsonObject xsts) {
      if (xsts == null || !xsts.has("XErr")) {
         return "XSTS authorisation failed.";
      }

      final long code = xsts.get("XErr").getAsLong();
      if (code == 2148916233L) {
         return "That account has no Xbox profile.";
      }
      if (code == 2148916238L) {
         return "That is a child account and needs an adult to add it to a family.";
      }
      if (code == 2148916235L) {
         return "Xbox Live is not available in that account's country.";
      }
      if (code == 2148916227L) {
         return "That Xbox account is banned.";
      }
      return "XSTS authorisation failed (XErr " + code + ").";
   }

   private static String uhs(JsonObject response) {
      if (response == null || !response.has("DisplayClaims")) {
         return null;
      }

      final JsonObject claims = response.getAsJsonObject("DisplayClaims");
      if (claims == null || !claims.has("xui")) {
         return null;
      }

      final JsonArray xui = claims.getAsJsonArray("xui");
      if (xui == null || xui.isEmpty()) {
         return null;
      }

      final JsonObject first = xui.get(0).getAsJsonObject();
      return first.has("uhs") ? first.get("uhs").getAsString() : null;
   }

   private static JsonObject fetchProfile(String accessToken) {
      if (getJson(MC_ENTITLEMENTS_ENDPOINT, accessToken) == null) {
         return null;
      }

      return getJson(MC_PROFILE_ENDPOINT, accessToken);
   }

   private static String id(JsonObject profile) {
      return profile.has("id") ? profile.get("id").getAsString() : null;
   }

   private static String name(JsonObject profile) {
      return profile.has("name") ? profile.get("name").getAsString() : null;
   }

   // ------------------------------------------------------------------ HTTP

   private static JsonObject getJson(String url, String bearer) {
      final HttpURLConnection connection = open(url, "GET", null);
      if (connection == null) {
         return null;
      }

      try {
         connection.setRequestProperty("Authorization", "Bearer " + bearer);
         connection.setRequestProperty("Accept", "application/json");
         if (connection.getResponseCode() != 200) {
            return null;
         }

         return parse(readBody(connection));
      } catch (Exception exception) {
         return null;
      } finally {
         connection.disconnect();
      }
   }

   private static JsonObject postJson(String url, String body, boolean json) {
      final HttpURLConnection connection = open(url, "POST", null);
      if (connection == null) {
         return null;
      }

      try {
         final byte[] payload = body.getBytes(StandardCharsets.UTF_8);
         connection.setDoOutput(true);
         connection.setFixedLengthStreamingMode(payload.length);
         connection.setRequestProperty(
            "Content-Type", json ? "application/json" : "application/x-www-form-urlencoded; charset=UTF-8"
         );
         connection.setRequestProperty("Accept", "application/json");
         connection.connect();

         try (OutputStream stream = connection.getOutputStream()) {
            stream.write(payload);
         }

         if (connection.getResponseCode() != 200) {
            return null;
         }

         return parse(readBody(connection));
      } catch (Exception exception) {
         return null;
      } finally {
         connection.disconnect();
      }
   }

   private static HttpURLConnection open(String url, String method, Map<String, String> cookies) {
      try {
         final HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
         connection.setRequestMethod(method);
         connection.setInstanceFollowRedirects(false);
         connection.setConnectTimeout(15000);
         connection.setReadTimeout(20000);
         connection.setRequestProperty("User-Agent", USER_AGENT);
         connection.setRequestProperty(
            "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8"
         );
         connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9");

         if (cookies != null && !cookies.isEmpty()) {
            final StringBuilder header = new StringBuilder();
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
               if (header.length() > 0) {
                  header.append("; ");
               }
               header.append(entry.getKey()).append('=').append(entry.getValue());
            }
            connection.setRequestProperty("Cookie", header.toString());
         }

         return connection;
      } catch (Exception exception) {
         return null;
      }
   }

   private static String readBody(HttpURLConnection connection) {
      try {
         final InputStream stream = connection.getResponseCode() >= 400
            ? connection.getErrorStream()
            : connection.getInputStream();
         return stream == null ? null : IOUtils.toString(stream, StandardCharsets.UTF_8);
      } catch (Exception exception) {
         return null;
      }
   }

   private static JsonObject parse(String json) {
      if (json == null || json.isBlank()) {
         return null;
      }

      try {
         return (JsonObject)ClientContext.GSON.fromJson(json, JsonObject.class);
      } catch (Exception exception) {
         return null;
      }
   }

   private static String urlEncode(String value) {
      try {
         return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
      } catch (Exception exception) {
         return value;
      }
   }

   /** Strips {@code name=} prefixes, quotes, whitespace and a trailing {@code ;}. */
   private static String cleanToken(String raw) {
      if (raw == null) {
         return "";
      }

      String token = raw.trim();
      if (token.isEmpty()) {
         return "";
      }

      for (String prefix : new ArrayList<>(List.of(
         "refresh_token=", "access_token=", "token=", "Bearer ", "bearer ", "\"", "'"
      ))) {
         if (token.regionMatches(true, 0, prefix, 0, prefix.length())) {
            token = token.substring(prefix.length()).trim();
         }
      }

      final int semi = token.indexOf(';');
      if (semi >= 0) {
         token = token.substring(0, semi).trim();
      }
      if (token.length() > 1 && token.startsWith("\"") && token.endsWith("\"")) {
         token = token.substring(1, token.length() - 1);
      }

      return token;
   }

   /** Prefers whichever result actually succeeded, else the one with a message. */
   private static Result prefer(Result first, Result second) {
      if (first.success) {
         return first;
      }
      if (second.success) {
         return second;
      }
      return first.message != null ? first : second;
   }
}
