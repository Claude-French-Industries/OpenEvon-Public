package fr.litarvan.openauth.microsoft;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.json.JSONObject;

public class HttpClient {
   public static final String MIME_TYPE_JSON = "application/json";
   public static final String MIME_TYPE_URLENCODED_FORM = "application/x-www-form-urlencoded";
   private final Gson gson = new Gson();

   public String getText(String url, Map<String, String> params) throws MicrosoftAuthenticationException {
      return this.readResponse(this.createConnection(url + "?" + this.buildParams(params)));
   }

   public String readPost(String url, Map<String, Object> params, String contentType) throws MicrosoftAuthenticationException {
      return this.readResponse(this.post(url, contentType, "*/*", this.buildParamsStr(params)));
   }

   public String readPost_r2(String url, Map<String, Object> params, String contentType, String accept) throws MicrosoftAuthenticationException {
      return this.readJson(this.post(url, contentType, accept, this.buildParamsStr(params)));
   }

   public String readPost_r3(String url, JSONObject params, String contentType) throws MicrosoftAuthenticationException {
      HttpURLConnection var4 = this.post(url, contentType, "*/*", params.toString());
      return this.readJson(var4);
   }

   public String readPost_r4(String url, JSONObject params, String contentType, String accept) throws MicrosoftAuthenticationException {
      HttpURLConnection var5 = this.post(url, contentType, accept, params.toString());
      return this.readJson(var5);
   }

   public <T> T getJson(String url, String token, Class<T> responseClass) throws MicrosoftAuthenticationException {
      HttpURLConnection var4 = this.createConnection(url);
      var4.addRequestProperty("Authorization", "Bearer " + token);
      var4.addRequestProperty("Accept", "application/json");
      return this.readJson_r2(var4, responseClass);
   }

   public HttpURLConnection postForm(String url, Map<String, String> params) throws MicrosoftAuthenticationException {
      return this.post(url, "application/x-www-form-urlencoded", "*/*", this.buildParams(params));
   }

   public <T> T postJson(String url, Object request, Class<T> responseClass) throws MicrosoftAuthenticationException {
      HttpURLConnection var4 = this.post(url, "application/json", "application/json", this.gson.toJson(request));
      return this.readJson_r2(var4, responseClass);
   }

   public <T> T postFormGetJson(String url, Map<String, String> params, Class<T> responseClass) throws MicrosoftAuthenticationException {
      return this.readJson_r2(this.postForm(url, params), responseClass);
   }

   protected HttpURLConnection post(String url, String contentType, String accept, String data) throws MicrosoftAuthenticationException {
      HttpURLConnection var5 = this.createConnection(url);
      var5.setDoOutput(true);
      var5.addRequestProperty("Content-Type", contentType);
      var5.addRequestProperty("Accept", accept);

      try {
         var5.setRequestMethod("POST");
         var5.getOutputStream().write(data.getBytes());
         return var5;
      } catch (IOException var7) {
         throw new MicrosoftAuthenticationException(var7);
      }
   }

   protected HttpURLConnection put(String url) throws MicrosoftAuthenticationException {
      HttpURLConnection var2 = this.createConnection(url);
      var2.setDoOutput(true);

      try {
         var2.setRequestMethod("PUT");
         return var2;
      } catch (IOException var4) {
         throw new MicrosoftAuthenticationException(var4);
      }
   }

   protected <T> T readJson_r2(HttpURLConnection connection, Class<T> responseType) throws MicrosoftAuthenticationException {
      return (T)this.gson.fromJson(this.readResponse(connection), responseType);
   }

   protected String readJson(HttpURLConnection connection) throws MicrosoftAuthenticationException {
      return this.readResponse(connection);
   }

   protected String readResponse(HttpURLConnection connection) throws MicrosoftAuthenticationException {
      String var2 = connection.getHeaderField("Location");
      if (var2 != null) {
         return this.readResponse(this.createConnection(var2));
      }

      StringBuilder var3 = new StringBuilder();

      try {
         InputStream var4 = connection.getInputStream();
         if (this.checkUrl(connection.getURL())) {
            ByteArrayOutputStream var5 = new ByteArrayOutputStream();
            byte[] var7 = new byte[8192];

            int var6;
            while ((var6 = var4.read(var7, 0, var7.length)) != -1) {
               var5.write(var7, 0, var6);
            }

            byte[] var8 = var5.toString("UTF-8")
               .replaceAll("integrity ?=", "integrity.disabled=")
               .replaceAll("setAttribute\\(\"integrity\"", "setAttribute(\"integrity.disabled\"")
               .getBytes(StandardCharsets.UTF_8);
            var4 = new ByteArrayInputStream(var8);
         }

         String var14;
         try (BufferedReader var13 = new BufferedReader(new InputStreamReader(var4))) {
            while ((var14 = var13.readLine()) != null) {
               var3.append(var14).append('\n');
            }
         } catch (IOException var11) {
            throw new MicrosoftAuthenticationException(var11);
         }
      } catch (IOException var12) {
         throw new RuntimeException(var12);
      }

      return var3.toString();
   }

   private boolean checkUrl(URL var1) {
      return "login.microsoftonline.com".equals(var1.getHost()) && var1.getPath().endsWith("/oauth2/authorize")
         || "login.live.com".equals(var1.getHost()) && "/oauth20_authorize.srf".equals(var1.getPath())
         || "login.live.com".equals(var1.getHost()) && "/ppsecure/post.srf".equals(var1.getPath())
         || "login.microsoftonline.com".equals(var1.getHost()) && "/login.srf".equals(var1.getPath())
         || "login.microsoftonline.com".equals(var1.getHost()) && var1.getPath().endsWith("/login")
         || "login.microsoftonline.com".equals(var1.getHost()) && var1.getPath().endsWith("/SAS/ProcessAuth")
         || "login.microsoftonline.com".equals(var1.getHost()) && var1.getPath().endsWith("/federation/oauth2")
         || "login.microsoftonline.com".equals(var1.getHost()) && var1.getPath().endsWith("/oauth2/v2.0/authorize");
   }

   protected HttpURLConnection followRedirects(HttpURLConnection connection) throws MicrosoftAuthenticationException {
      String var2 = connection.getHeaderField("Location");
      if (var2 != null) {
         connection = this.followRedirects(this.createConnection(var2));
      }

      return connection;
   }

   protected String buildParamsStr(Map<String, Object> params) {
      StringBuilder var2 = new StringBuilder();
      params.forEach((var1, var2x) -> {
         if (var2.length() > 0) {
            var2.append('&');
         }

         try {
            var2.append(var1).append('=').append(URLEncoder.encode(var2x.toString(), "UTF-8"));
         } catch (UnsupportedEncodingException var4) {
         }
      });
      return var2.toString();
   }

   protected String buildParams(Map<String, String> params) {
      StringBuilder var2 = new StringBuilder();
      params.forEach((var1, var2x) -> {
         if (var2.length() > 0) {
            var2.append('&');
         }

         try {
            var2.append(var1).append('=').append(URLEncoder.encode(var2x, "UTF-8"));
         } catch (UnsupportedEncodingException var4) {
         }
      });
      return var2.toString();
   }

   protected HttpURLConnection createConnection(String url) throws MicrosoftAuthenticationException {
      HttpURLConnection var2;
      try {
         var2 = (HttpURLConnection)new URL(url).openConnection();
      } catch (IOException var4) {
         throw new MicrosoftAuthenticationException(var4);
      }

      String var3 = "Mozilla/5.0 (XboxReplay; XboxLiveAuth/3.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";
      var2.setRequestProperty("Accept-Language", "en-US");
      var2.setRequestProperty("Accept-Charset", "UTF-8");
      var2.setRequestProperty("User-Agent", var3);
      return var2;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
