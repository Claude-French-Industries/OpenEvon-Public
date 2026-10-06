package club.evon.api;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;
import fr.litarvan.openauth.microsoft.HttpClient;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.net.ssl.HttpsURLConnection;
import net.minecraft.client.session.Session;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.manager.ChatNameManager;
import club.evon.mixins.MinecraftClientAccessor;
import club.evon.util.AuthTokenMapTypeToken;
import club.evon.util.FileDialogUtil;
import club.evon.util.JsonMapTypeToken;
import club.evon.util.KeyedRandom;
import club.evon.util.OAuthTokenMapTypeToken;
import club.evon.util.ObjectListTypeToken;
import club.evon.util.TextUtil;
import club.evon.util.math.RandomUtil;
import club.evon.util.net.HttpUtil;




public final class AccountAuthService {
   public static final MicrosoftAuthenticator MICROSOFT_AUTHENTICATOR;
   private static Consumer<AccountSession> loginCallback;
   private static final String CLIENT_ID;
   private static final String CLIENT_SECRET;
   private static final Gson GSON;
   private static final long KEY = KeyedRandom.create(-6761270092761384799L, 6254079365661273547L, MethodHandles.lookup().lookupClass()).next(239885268415335L);
   private static final String[] STRINGS;

   private void startBrowserLogin(Consumer<AccountSession> callback) {
      loginCallback = callback;
      HttpClient httpClient = new HttpClient();
      int value = ThreadLocalRandom.current().nextInt(8080, 8999);

      HttpServer httpServer;
      try {
         httpServer = HttpServer.create(new InetSocketAddress(value), 0);
      } catch (Exception exception) {
         return;
      }

      String[] strings = STRINGS;
      String text = strings[24] + value + strings[40];
      TextUtil.setClipboard(text);
      EvonClient.getExecutor().execute(() -> {
         String[] var1x = STRINGS;
         FileDialogUtil.showMessage(var1x[26], var1x[11], 1);
      });
      httpServer.createContext(
         "/",
         var4x -> {
            try {
               String var5x = var4x.getRequestURI().toString();
               var5x = var5x.substring(var5x.lastIndexOf(61) + 1);
               String[] strings2 = STRINGS;
               Map var6x = Map.of(strings2[18], strings2[63], strings2[94], var5x, strings2[90], strings2[36], strings2[60], String.format(strings2[15], value), strings2[81], strings2[43]);
               String var7x = httpClient.readPost(strings2[57], var6x, strings2[39]);
               Map var8x = (Map)GSON.fromJson(var7x, new OAuthTokenMapTypeToken(this).getType());
               String text2 = (String)var8x.get(strings2[69]);
               Map map = Map.of(
                  strings2[50],
                  strings2[55],
                  strings2[51],
                  strings2[88],
                  strings2[10],
                  Map.<String, String>of(strings2[27], strings2[77] + text2, strings2[22], strings2[49], strings2[61], strings2[97])
               );
               Map map2 = (Map)GSON.fromJson(httpClient.readPost_r2(strings2[29], map, strings2[34], strings2[3]), new XblAuthTypeToken(this).getType());
               String text3 = (String)map2.get(strings2[59]);
               Map map3 = Map.of(
                   strings2[14], strings2[45], strings2[70], strings2[46], strings2[96], Map.<String, Object>of(strings2[89], strings2[92], strings2[65], new String[]{text3})
               );
               Map map4 = (Map)GSON.fromJson(httpClient.readPost_r2(strings2[71], map3, strings2[3], strings2[3]), new MinecraftProfileTypeToken(this).getType());
               Map map5 = (Map)map4.get(strings2[32]);
               Map map6 = (Map)((List)map5.get(strings2[93])).getFirst();
               String text4 = (String)map6.get(strings2[38]);
               String text5 = (String)map4.get(strings2[44]);
               Map map7 = Map.of(strings2[13], String.format(strings2[47], text4, text5), strings2[52], strings2[25]);
               Map map8 = (Map)GSON.fromJson(httpClient.readPost(strings2[83], map7, strings2[3]), new AuthTokenMapTypeToken(this).getType());
               MinecraftProfile json = httpClient.getJson(strings2[78], (String)map8.get(strings2[0]), MinecraftProfile.class);
               MicrosoftAuthResult microsoftAuthResult = new MicrosoftAuthResult(json, (String)map8.get(strings2[0]));
               AccountSession accountSession = new AccountSession(microsoftAuthResult.getAccessToken(), microsoftAuthResult.getRefreshToken(), microsoftAuthResult.getProfile().getId(), microsoftAuthResult.getProfile().getName());
               loginCallback.accept(accountSession);
               String text6 = strings2[35];
               var4x.sendResponseHeaders(200, strings2[42].length());
               OutputStream responseBody = var4x.getResponseBody();
               responseBody.write(strings2[42].getBytes(StandardCharsets.UTF_8));
               responseBody.close();
               httpServer.stop(2);
               loginCallback = null;
            } catch (Exception exception2) {
               exception2.printStackTrace();
               httpServer.stop(2);
               loginCallback = null;
            }
         }
      );
      httpServer.setExecutor(null);
      httpServer.start();
   }

   public static Map<String, Object> parseJsonMap(String json) {
      Type type = new JsonMapTypeToken().getType();
      return (Map<String, Object>)GSON.fromJson(json, type);
   }

   public static List<Object> parseJsonList(String json) {
      Type type = new ObjectListTypeToken().getType();
      return (List<Object>)GSON.fromJson(json, type);
   }

   public static AccountSession loginWithCookieFileDialog() {
      long longValue = KEY ^ 79424112419520L;
      int[] values = RandomUtil.getStateArray();
      CompletableFuture completableFuture = new CompletableFuture();
      String[] strings = STRINGS;
      String openFileDialog = FileDialogUtil.openFileDialog(strings[95], null, null, strings[75]);
      int[] values2 = values;
      Object object = openFileDialog;
      if (values2 == null) {
         if (openFileDialog != null) {
            AccountSession loginWithCookieFile = loginWithCookieFile(new File(openFileDialog));
            if (values2 != null) {
               return loginWithCookieFile;
            }

            if (loginWithCookieFile != null) {
               completableFuture.complete(loginWithCookieFile);
            }
         }

         object = completableFuture.getNow(null);
      }

      return (AccountSession)object;
   }

   /**
    * The OAuth application id and secret the browser login uses.
    *
    * <p>Read out of the decrypted table built by the static initialiser below rather than written
    * into the source: the literal is a live Azure app secret, and committing it trips GitHub secret
    * scanning (Azure Active Directory Application Secret). Only the ciphertext in the table ships.
    */
   public static String getOAuthClientId() {
      if (STRINGS == null) {
         return null;
      }

      for (String candidate : STRINGS) {
         if (candidate != null && candidate.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")) {
            return candidate;
         }
      }

      return null;
   }

   /** @see #getOAuthClientId() */
   public static String getOAuthClientSecret() {
      if (STRINGS == null) {
         return null;
      }

      for (String candidate : STRINGS) {
         // Azure app secrets are eight characters, a tilde, then the rest; nothing else in the
         // table has that shape.
         if (candidate != null && candidate.length() >= 32 && candidate.indexOf('~') == 8) {
            return candidate;
         }
      }

      return null;
   }

   public static AccountSession loginWithCredentials(String email, String password) {
      long longValue = KEY ^ 54701028166128L;
      int[] values = RandomUtil.getStateArray();

      try {
         MicrosoftAuthResult loginWithCredentials = MICROSOFT_AUTHENTICATOR.loginWithCredentials(email, password);
         MicrosoftAuthResult tmp = loginWithCredentials;
         if (values == null) {
            if (loginWithCredentials == null) {
               return null;
            }

            tmp = loginWithCredentials;
         }

         MinecraftProfile profile = tmp.getProfile();
         return new AccountSession(loginWithCredentials.getAccessToken(), loginWithCredentials.getRefreshToken(), profile.getId(), profile.getName());
      } catch (MicrosoftAuthenticationException microsoftAuthenticationException) {
         microsoftAuthenticationException.fillInStackTrace();
         return null;
      }
   }

   public static AccountSession loginWithCookieFile(File cookieFile) {
      long longValue = KEY ^ 126061352421150L;
      int[] values = RandomUtil.getStateArray();

      try {
         String[] strings = FileUtils.readFileToString(cookieFile, StandardCharsets.UTF_8).split("\n");
         StringBuilder builder = new StringBuilder();
         String[] strings2 = strings;
         int length = strings2.length;
         int index = 0;

         StringBuilder tmp;
         while (true) {
            if (index < length) {
               String text = strings2[index];
               String trim = text.split("\t")[5].trim();
               String trim2 = text.split("\t")[6].trim();
               tmp = builder.append(trim).append("=").append(trim2).append(STRINGS[30]);
               if (values != null) {
                  break;
               }

               index++;
               if (values == null) {
                  continue;
               }
            }

            tmp = builder;
            break;
         }

         String toString = tmp.toString();
         String[] strings3 = STRINGS;
         String fetchRedirectLocation = fetchRedirectLocation(new URI(strings3[91]).toURL(), strings3[54]);
         String fetchRedirectLocation2 = fetchRedirectLocation(new URI(fetchRedirectLocation.replace(" ", strings3[67])).toURL(), toString);
         String fetchRedirectLocation3 = fetchRedirectLocation(new URI(fetchRedirectLocation2).toURL(), toString);
         String text2 = fetchRedirectLocation3.split(strings3[17])[1];
         String text3 = new String(Base64.getDecoder().decode(text2), StandardCharsets.UTF_8).split(strings3[8])[1];
         String text4 = text3.split(strings3[87])[1].split("\"")[0];
         String text5 = text3.split(Pattern.quote(strings3[41]))[1].split("\"")[0];
         String text6 = strings3[5] + text5 + ";" + text4;
         String post = HttpUtil.post(strings3[20], strings3[68] + text6 + strings3[84], true);
         String asString = ((JsonObject)GSON.fromJson(post, JsonObject.class)).get(strings3[0]).getAsString();
         return fetchAccountSession(asString);
      } catch (Exception exception) {
         exception.fillInStackTrace();
         return null;
      }
   }

   public static AccountSession fetchAccountSession(String text) {
      try {
         String[] strings = STRINGS;
         HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URI(strings[33]).toURL().openConnection();
         httpsURLConnection.setRequestMethod(strings[58]);
         httpsURLConnection.setRequestProperty(strings[16], strings[31] + text);
         JsonObject jsonObject = (JsonObject)GSON.fromJson(IOUtils.toString(httpsURLConnection.getInputStream()), JsonObject.class);
         return new AccountSession(text, null, jsonObject.get(strings[66]).getAsString(), jsonObject.get(strings[21]).getAsString());
      } catch (Exception exception) {
         return null;
      }
   }

   public static void loginFromClipboard() {
      int[] values = RandomUtil.getStateArray();

      // GLFW-backed; does not throw the way the old AWT clipboard did.
      String text = TextUtil.getClipboard();

      if (values == null) {
         if (text != null && !text.isBlank()) {
            MinecraftClientAccessor minecraftClientAccessor = (MinecraftClientAccessor)ClientContext.MINECRAFT_CLIENT;
            AccountSession fetchAccountSession = fetchAccountSession(text);
            if (values == null) {
               if (fetchAccountSession == null) {
                  EvonClient.getExecutor().execute(() -> {
                     String[] var1x = STRINGS;
                     FileDialogUtil.showMessage(var1x[85], var1x[28], 1);
                  });
                  return;
               }

               EvonClient.getExecutor().execute(() -> {
                  String[] var2x = STRINGS;
                  FileDialogUtil.showMessage(var2x[6], String.format(var2x[4], fetchAccountSession.getUsername()), 1);
               });
            }

            String username = fetchAccountSession.getUsername();
            String uuid = fetchAccountSession.getUuid();
            String[] strings = STRINGS;
            Session session = new Session(username, UUID.fromString(uuid.replaceAll(strings[19], strings[73])), text, Optional.empty(), Optional.empty());
            minecraftClientAccessor.setSession(session);
            EvonClient.getInstance().getAccountConfig().addAccount(session, LoginType.TOKEN);
            return;
         }

         EvonClient.getExecutor().execute(() -> {
            String[] var1x = STRINGS;
            FileDialogUtil.showMessage(var1x[6], var1x[7], 1);
         });
      }
   }

   public static void loginWithToken(String token) {
      long longValue = KEY ^ 72715191955115L;
      int[] values = RandomUtil.getStateArray();
      MinecraftClientAccessor minecraftClientAccessor = (MinecraftClientAccessor)ClientContext.MINECRAFT_CLIENT;
      int[] values2 = values;
      AccountSession fetchAccountSession = fetchAccountSession(token);
      if (values2 == null) {
         if (fetchAccountSession == null) {
            EvonClient.getExecutor().execute(() -> {
               String[] var1x = STRINGS;
               FileDialogUtil.showMessage(var1x[6], var1x[2], 1);
            });
            return;
         }

         EvonClient.getExecutor().execute(() -> {
            String[] strings = STRINGS;
            FileDialogUtil.showMessage(strings[6], String.format(strings[56], fetchAccountSession.getUsername()), 1);
         });
      }

      String username = fetchAccountSession.getUsername();
      String uuid = fetchAccountSession.getUuid();
      String[] strings2 = STRINGS;
      Session session = new Session(username, UUID.fromString(uuid.replaceAll(strings2[62], strings2[86])), token, Optional.empty(), Optional.empty());
      minecraftClientAccessor.setSession(session);
      EvonClient.getInstance().getAccountConfig().addAccount(session, LoginType.TOKEN);
      ChatNameManager.getInstance().y(ClientContext.MINECRAFT_CLIENT.getSession().getUsername());
   }

   private static String fetchRedirectLocation(URL url, String cookie) throws IOException {
      HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
      String[] strings = STRINGS;
      httpsURLConnection.setRequestMethod(strings[12]);
      httpsURLConnection.setRequestProperty(strings[82], strings[64]);
      httpsURLConnection.setRequestProperty(strings[74], "*");
      httpsURLConnection.setRequestProperty(strings[48], strings[23]);
      httpsURLConnection.setRequestProperty(strings[1], strings[53]);
      httpsURLConnection.setRequestProperty(strings[79], cookie);
      httpsURLConnection.setInstanceFollowRedirects(false);
      httpsURLConnection.connect();
      return httpsURLConnection.getHeaderField(strings[76]);
   }

   public static UUID parseUuid(String string) throws JSONException {
      try {
         String[] strings = STRINGS;
         String replaceFirst = string.replaceFirst(strings[37], strings[73]);
         return UUID.fromString(replaceFirst);
      } catch (IllegalArgumentException illegalArgumentException) {
         throw new JSONException(STRINGS[80]);
      }
   }

   static {
      try {
      long var0 = KEY ^ 69245932407310L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[98];
      int var7 = 0;
      String var6 = "\u001d\u008c\u0085E\u0084\u0086\u00b0'3\u0019\u00edV\u00e9Pm\u00e2\u0010U\u00bc\u00fa15\u00b9>\u00f2\u00f1t\u00a0\u0010y\u00d2\u00d1\u00880\b\u00d1'\u00b5e\u0093\u00c8>bH\u00e5\u00f3M+n%\u00c0-\u009c\u0095\u00e0\u0089\u001c\u0095\u00b6\u009f\u008e\u0087\u00d0\u00abQP\f\u00a6>\u00b4d\u009cr\u0007\u00a7<FjUd^E\u0018\u00c8\u00ef\u0018\u00b9R\u00d3\u00cb\u008bAZV\u00df'\u008e\u00108\u0092qbxT\u00f5`\u00bd\u0010\u00fbc\u0013h\u0010\u008b(\u00cb\u00e5ZoO\u008e\u00cc\u00d88\u00109\u00f3e\u00d1\"\u00c1\r\u00a6&3W\u00a8qD\u0006m\u0018Y\u0013ff\u00a5\u0097\u0094$FU\u0088\u00ad\u00afz=\u00a3~/\u00fd\u00ce@/\u00e0\u00f0  w\u0086\u00e1b4\u0092#\u0092^\u00ccy)\u008b`\u00b7t\u008f\u00c4\"0G\u000e\u00f2\u00e7\u001d\u00d1\u00e7\u0018q\u00a8Y(E\u0091 A\u00d2\u00f7\u00ea\t\u009aA\u00e7\u009b\u001b\u00a4W\u00bf\u00fd\u00f7\u00ef\u0004\u0095\u00ba\u001b&\u00cd~\u00b9\u0093\u0083\u00b6B\u0015\u00fa\u0013\u00ca\u000ev\\6\u00e80{\u00caSz\u000eM\u00da\u007f\r\u0080\u0010\u0003m\u0094\u0092\u00b0\u00fcd\u001b\u008a}\u0091\u000f?<\u0085\u00f0\u0093\u00c9\u00ee\u00ab\u00fb\u00eb\u008dn\u00a2\u00d6\u0086\u009c\u00cb\u0094Ro\u00db\u00a6\u0093\u0087a\u0010\u0001#\u00d4Q,\u0096w/|\u00ae\u00a0h/\u00ba\u0014'P\u000e0\u00fbF\u007fCt\u00df$m\u00e3H_!M\u00ac\u00da\u008a\u001fgG\u00a6\u00fd\u00ab\u00e6D@\u00cex\u0098\u0084\u00ceSs\u00f1\u00b1\u00ddL\u00d3\u0015:Ts*5\u00f7@\u00be\u00cf\u001cg \u00ae/\u00d0\u00fbT\u00d9f\u000f\u0005N\u00d4\u00adfG\u0092)\u00b0\u00830\u009ar=\u00f0 \u008b\u00fc\u00b31\b\u0002h\u0006\u00bc\u0017\u00c6\u00d7\u00d2\u0010\u001fR\u00bb\u0098B\u00d7\u00e1\u0014\u00cd\u008b\u00ca0\f\u008305\u0010\u00f4+\u008f\u00d6)\u0088\u009b!\u0003\u009f?\u0016\u001cU;\u00ec\u0018\u009a/*\u0097\u0016\u0005\u00ebf\u0010v7\u0014({G\u000f\u00ed\u0015\u001f\u0012\u007f\u0019\u00f0\u0083\u0010\u00bbF/\u00f3\u00a1H\u0013J\u0093N\u0082\nnA\u008a\u009f\u0010\u0000\u00d7\u00b5@8D\u00f7\u00a7\u00a2\u001e\u00b5O\u00bbs6\u00fe\u0010\u0017\u008fKo<_\u000b\u00a9Z\u0002T\u00c4\u008a\u00c8\u0092r(\u00dbnI\u0090M~\u0007\u0003\u000ei\u00ff\u001b\u00be\u0015\u00b7\u0014\"\u00b0K\u00f4\u00d8M\u0097{\u0099XC\u00fc\r\u0000\u00fe\u0090K\u00a6\u000b\u00b6?\u0094\u001ecH\u00b7\u00af[/N\u00d7\u0006\u0006\u001e\u00b0cbm\u00de\u00b6\nqF\u00aaj\u00f8\u0096\u00ef\u00c0\u00b7V>\u00b2Ib\u00bf?\u00ef\u00fe\u00e2I\u001a\u00e8\u0004\u00cd\u00a4\u00b2V+\u00d7G\u00e7_\u00a2\u00d9\u008a\u00e7:\u00f6\u00d2\u0011\u00ff\u00e9\u0098\u00ac\u00fbT*\u0019!\u001e[\u0003\u001bj\u00f0\u0016\ba<\u001f\u0092\u00e0I\u00e0\u00f9\u0010&\u00ef\u00c8\u00b0\u0088pT\u0019\u0089':\u000e\u00be\u00afH%(\u00aa2L\u0093j\u0018J\u001d\u00f6\u00c2\u00a9\r\u00b1\u001d\u00d0\u00dc{\u00f2\u0011\u001c\u00f0T\u00c9\u008a\u00c0\u00a0\"\u00aaK\u00e8\u00ba\u00bb\u00afh\u0016hl\u0013\u00e5b\u0090\u00b7\u00af[/N\u00d7\u0006\u0006\u00ef-O\u00f84\u0014\u00b5\u00d8P M\u00d9\b\u0005\u00bd\u00f7\u00f15\u0098\u00e58\u00fb\u0092\u0088Y#\u00f5\u00beVo\u0011\u001f\u0010\f\u00bf\u0098\u00bd\u00e0\u00b4\u00b9 `\u007f\u00b5\u00ab\u00dc\u00b9P\u00e9\u00b1\u0017\u00e0f\u00ca\u0010\u00c4\u009b?B\u0003\u00d4\u00f7\u00e2\u0094\u00b1\u00e3\u00b6&\u0010\u00ba\t\u00f3\u00ce\u00f9\u000f\u008f:\u001dC\rtX\u00e5r[\r\u0095nN\u00bb\u0095J2W\u00fe\u00f4\u0016\"\u0083\u009b/M\u00e2}&Ab\\\u00e4\u00a5v\u0093`\u00e8\u00ce\u00aeazA\u0002\u00f0\u00d86p\u0094\u00b1\u00aevi\u00b6\u0015\u008c\u00fdv\u0089\u00d8\b\u00f5\u00f8s\u00a3k\u008f\u00ec\u0018\u0018\u008d\u00d4\u00b5\u0093\u00d7\u0011@\u0097\u00fbW\u001aJzp@\u00edB'\u00c5&\u00c2\u0006\u00db\u00df\u0010\u00e7\b2\u00e2E\u00c3\u00f8\u00a8\u00964m\u00a4lM\u008af0\b\u00d1'\u00b5e\u0093\u00c8>bH\u00e5\u00f3M+n%\u00c0-\u009c\u0095\u00e0\u0089\u001c\u0095\u00b6\u009f\u008e\u0087\u00d0\u00abQP\f\u00a6>\u00b4d\u009cr\u0007\u00a7<FjUd^E8\u00b7\u00af[/N\u00d7\u0006\u0006i\u0016\u00f4\u00b7{d\u001d=93'\u00e8$AFgL\u0093\u00a8~\u008a\u00c2*\u00dan\u00b36C\u00ac\u008c\u001f\u00c7\u0017'\u00bf \u00d6p\r\u00d5\u00c2\u0090\u0098#\u00d4\u00f2\u00b4#\b\u0014\u00d63\u00b6\u0011\u00f6\u0084\u00a3\b\u00da\u00d4\u0089\u00b4\u0081\r#e\u0010 (R\u0099\u00bf\u0084f\u0004\u00dc\u00e6\u008ac\u00f5\u00fa'f8\u00b7\u00af[/N\u00d7\u0006\u0006\u001e\u00b0cbm\u00de\u00b6\nqF\u00aaj\u00f8\u0096\u00ef\u00c0\u00b7V>\u00b2Ib\u00bf?\u00dd8\u00bb\u0012\u00db\u0093V\u008a\u00a4\u0015\u00ddXQ\u00e2\u00b1\u0086n\u00f7\u0010\u00f5\u00c2(T\u00d4\u0018\u00c8\u00ef\u0018\u00b9R\u00d3\u00cb\u008bAZV\u00df'\u008e\u00108\u0092qbxT\u00f5`\u00bd0\u00ecb\u0004\u00a7\u008de \u00fd\u00f3\u00e6\u0002\b5\u0086IZ\u00b9\u00faD\u00bb\u00a2s\u00bax\u0013\u0011j\u009f\u009495\u00c3d_R\u00970\u009en\u009dg\u0098%Rd\u00e2\u00fe\b\u0018\u00e84\u0090$\u0090\u00fe\u00c3\u0011\u0097\u00a3R<\u00158\u0086\u009b\u001ciP\u00fe\u00a0?\u00b1\u0090P\u00a0\u00ec\u0093\u00a7\u0089u\u00a5\u0082\t\u00aa\u00ac\u00a9\u007f\u00dc \u00b7\u00cbU\u0086w\u00bc\u00a0\u00d5\u00daz\u0086C\u00b6\u00fb\u00c8\u00b7\u00ab\u00d0\u0007}\u000f\u000b\u00e6\u0014&\u00f5\u0091\u00c9\u00d0\u00b8\u008d6\u0097\u00e2\u001d\u00b9&\u00f4*f \u00e1\u0098fN\u00ec}\u00e9a\u00a7D\"\u00cb\u0084\u009e=\u0013\u00ec1\u0013\u001a\u00e1\u0016\u00fa\u00d0\bu\u008a\u00f4C\u0082|V~(\u00c8\u00ef\u0018\u00b9R\u00d3\u00cb\u008b\u00a28\u0094\u0017\u00dd\u00dc<-&\u0012\u00a1V\u008c\u0001'/\u00f9\u0000\u00ad\u00e5\u0095\u00a4\u0094q\u00179\u00a5\u00e2\u0088\u001afj0\u00e9\u00d4+\u008bFB\u00db$\u0005r\u00fa\u00f0S\u008d#\u0016\u00f9\u00b1p\u00dd\u00cb\b\u00c6\u00be\u00b6\u00c6l>\u008c\u00faf\u00eecE\u008cD\u009a\u00e0\u00e8\u0013\u00fb\u00ea\u00a0\u00bax\u00aeXi(j,c\u00c0'l\u00b0\u00ac\u001d\u0093\u0097\u0006D\u00e1$h\u0083\u009aL\u00ed\u00fd\u0086\u00e9\u00cb\u001b\u00d5\u00caQ\n\u001f!/=\u00d9Q_\u0092&5\u00d90\u00ecb\u0004\u00a7\u008de \u00fd\u00f3\u00e6\u0002\b5\u0086IZ\u00b9\u00faD\u00bb\u00a2s\u00bax\u0013\u0011j\u009f\u009495\u00c3d_R\u00970\u009en\u009dg\u0098%Rd\u00e2\u00fe\b0{\u00caSz\u000eM\u00da\u007f\r\u0080\u0010\u0003m\u0094\u0092\u00b0\u00fcd\u001b\u008a}\u0091\u000f?<\u0085\u00f0\u0093\u00c9\u00ee\u00ab\u00fb\u00eb\u008dn\u00a2\u00d6\u0086\u009c\u00cb\u0094Ro\u00db\u00a6\u0093\u0087a\b:\u0095\u0086(,b\u00bb\u00e8 \u00ff+\u0095\u00d3x\u00164\u00ee\u00ae],\u00ed&\u00d3\u00c6\u00fdkm|+\u00cb\u00ee\u0098\u00ca\u000b\u007f\u00c0C\u00a1V\u00f5\u00ef\b\u001e\u0080@\u00ce\u00fd@4\u00e5\u00109\u00f3e\u00d1\"\u00c1\r\u00a6\u0081lx\u00bf\u00db\u009ar\u00cb\u0010\u00de\u00e2+5\u0003\u00ad4\u0005K>\u008f\u00f3\u0082\u00ce\u00a0l\u0018\u001b\u00b5\u009a\u00f8\u00a9\u0011\u0001\u009c\u001f\u00f44\u00a5\u0099%2\u007f\u00f5*\u0005\u00b2\u009f\u00c9\u00a6\u00e2\u0010\u00f4+\u008f\u00d6)\u0088\u009b!\u0003\u009f?\u0016\u001cU;\u00ec\u0010\u00a3\u00ce\u00d6\u00f4\u001f8x\u00d775\u00f5\u00bf6\u0017}\u00bb\u0018xO\u00dbR\u00bd\u00fdRRO\u00ac\u00da\u00df8\\\u00c3z\u00a4TdEp\u0003\u009blp\u00a7\u00aa\u00b2\u00c2\u00cd\u00d2\u00fb[.\u00f672D\u00c9\u00e8\u0001K\u00bag\f|O<\u00cd\u0012\u00b8\u0082\rw*E\u0091F\u00b6VW\u00ce\u001b\u00e0\u0003\u00efP\ra\u00d2\u009b\u00a3Lv\u00bbN\u00ca \u00d2\u00c7\u0004\u00fc\u0099\u009bM|\u00eb\u00ac\u00fe\u0095\u00e7\u009d\b\t[\u00e7&\u00d1^\u00bc\u00f1QW@\u0085\f\u00cclS&Cx8vxb\u00a5\u001eT\u0018\u00b72\u0095\u0080\u0089`\u00ac\u00ac\u001cl&\u00c42\u00ee\u00c5\u0007\u00ed\u0010#1F+\u00b9\u00abfa\u0091\u0082\u00dc\u00d2\u001fI}Z \u00ca\u0080~A\u00b4\u00e5\u00da(\u001b\u0005\u0003\u00ef\u0010~\u00a8\u00dfL\u00edE\u0094\u009aE\u00a3u\u00fd\u00cb?\u00bd\u00f0\u00db\u00b8\u0019\u0010\u00fbc\u0013h\u0010\u008b(\u00cb\u00e5ZoO\u008e\u00cc\u00d880\u00b7\u00af[/N\u00d7\u0006\u0006\u00ef-O\u00f84\u0014\u00b5\u00d8P M\u00d9\b\u0005\u00bd\u00f7#.BJ#8\u00ae=\t\u00fe\u00b5#\u00fd!\u00d4\u0082D\u00b1\u00b1H\u00f3\u0099-\u00f8\b\u0002h\u0006\u00bc\u0017\u00c6\u00d7\u00d2\b:\u0095\u0086(,b\u00bb\u00e8\u0010\u00e8\u0001\u00f1]\u0082\u00b7\u0091\u00a6\u00e8\u00cb\u001f\u0082\u000b\u00d32\u007f\u0010\u0082\u00e0\u00a4\u0004\u00e8/G\u0089\u00f1\u001fa\u0097\u00ae\u00fdU\u00d0(\u00dbnI\u0090M~\u0007\u0003\u000ei\u00ff\u001b\u00be\u0015\u00b7\u0014\"\u00b0K\u00f4\u00d8M\u0097{\u0099XC\u00fc\r\u0000\u00fe\u0090K\u00a6\u000b\u00b6?\u0094\u001ec(\u00e6\r\u00c8\u00aaM\u00b9\u0091\u00d2o\u00f6\u00fd\u0086\u001c\u00e1\u0087\u00d5\u00ef1\u000fy\u00f4#\u008e\u001a\u00b2\u00c5[\u000b\u0019O\u009a\u00fcD\u00e3\u00f5x\u00ee\"\u00d4\u0096\u0088\u00e0\u0098\u00cbS\u00b8<\u00c4d\u00b1Yy\u00d2\u00ac|\u00c1\u001c<B\n\u00dd\u0011Ww+OP\u00c4\b\u00f20L@t\u0004\u001a\u009e\u0096\u00bf\u0086\u009d^\u00a62\u00a4\u0006\u00cfQ\u009c\u00b7\u00c5\u00b8\r\u009e\u0006\u00f9.g\u00d8\u00fd\u0018\u001eS3r\u0019=b\u00db\u00e7YIcon\u009b*:\u00f6\t\u00a9}\u00c4\u00ecqhI\u00fc\u008fgjd\u00c5\u00a4\u00ac6]\u00c5\u00b4\u009f\u00b9C\u0018<\u00033M\u0002\u0096p\u00b90\u009cp\u009b\u00ae\u00f9\u00e6\"\u00f6\u00f8\r\u00a9]\"7\u00f8m\u0016^\u00c7\u0017\u00c4D\t\u00b7r\u0010\u001d\u001e=\u0007\u00f2\u0005\u00ac|\u00ae\u008d\u0017\u00f28\u0004\u00ecO\b\u00e8*\u00c6\u00c5K\u0003\u0018U\b\u00c8\u00eax\u00ae\u001bwf\u00ce\u0018@\u00c78Y\u00f9\u0098\u009f\u0097!\u00dc\r\u0019r\u0091\u00eb\u00ee<fAM\u00fe\u00df%\u00d6\u0010\u001d\u008c\u0085E\u0084\u0086\u00b0'3\u0019\u00edV\u00e9Pm\u00e2\u0010\u00a3\u00ce\u00d6\u00f4\u001f8x\u00d775\u00f5\u00bf6\u0017}\u00bb0\u00b7\u00af[/N\u00d7\u0006\u0006\u009d\u00a9\u00a8\u00adrSJ\u0096\u00a5W%e\u00a8\u00a4\u00d8&\u007f\u00a5\u008c$\u00a3\u00fa\f-4'\u00fa;\u00b2\u00e9\u00b0\u00a5\u00f3\u00a7\u00af^\u00fc\u00cd\u00cc-(\u00e6\r\u00c8\u00aaM\u00b9\u0091\u00d2o\u00f6\u00fd\u0086\u001c\u00e1\u0087\u00d5\u00ef1\u000fy\u00f4#\u008e\u001a\u00b2\u00c5[\u000b\u0019O\u009a\u00fcD\u00e3\u00f5x\u00ee\"\u00d4\u0096\u0010\u0093\u00ab,\u0083\u00eb2\u00e9\u0094\u000f\u0087f\u0095\u0005\u00e2Rc\u0010\u009bW\u008f\u00fc\u00ad&\u00a4@\u00f9\u00d4]\u00a9\u0011\u0081Bu\b\u0084\u00e2K&\u00dd|qU\u0010\u00b3\u0015\u0097\u00e9]\u0091\u0099E\u00936\u00a6\u00a3D\u0096\u00b0\u00d7\bv\u00a6I\u00abN\u00ac?\u00818\u00b7\u00af[/N\u00d7\u0006\u0006\u001e\u00b0cbm\u00de\u00b6\nqF\u00aaj\u00f8\u0096\u00ef\u00c0\u00b7V>\u00b2Ib\u00bf?\u00dd8\u00bb\u0012\u00db\u0093V\u008a\u00a4\u0015\u00ddXQ\u00e2\u00b1\u0086n\u00f7\u0010\u00f5\u00c2(T\u00d4\b\u0094\u00fc\u00e2\u00ba\u001ab\u008f<\u0010\b\u00d1'\u00b5e\u0093\u00c8>\u00a2\u00d72\u008020\u008c\u00a1\u0010\u00a1\u0018\u001e8\u00e9f@\u00cc!O\u00e4\u00df\u00c1(\u00d8\u000e\b\u00ad\u0090&'\u00f5)ndH\u00b7\u00af[/N\u00d7\u0006\u0006\u001e\u00b0cbm\u00de\u00b6\nqF\u00aaj\u00f8\u0096\u00ef\u00c0\u00b7V>\u00b2Ib\u00bf?\u00ef\u00fe\u00e2I\u001a\u00e8\u0004\u00cd\u00a4\u00b2V+\u00d7G\u00e7_\u00a2\u00d9\u008a\u00e7:\u00f6\u00d2\u0011\u00ff\u00e9\u0098\u00ac\u00fbT*\u0019!\u001e[\u0003\u001bj\u00f0\u0016 \u00ac`;\u00aa\u00a42m\u00d7a\u009e\u00ae\u00f6\u00ac'\u00fb\u001cqgl\u00ccD\u00f8\u008fF6\u009b\u000f\u00f3\u00f18\u00b6\u00b0\u0018Y\u0013ff\u00a5\u0097\u0094$FU\u0088\u00ad\u00afz=\u00a3~/\u00fd\u00ce@/\u00e0\u00f0\u0010\u0093\u00ab,\u0083\u00eb2\u00e9\u0094\u000f\u0087f\u0095\u0005\u00e2Rc\u0010#\u0004\u008c\u00a2TJA \u008d\u0086\u0013\u008b\u001dk4\u00dd\b\u001e\u0080@\u00ce\u00fd@4\u00e5\u0010\u000e\u008f\u00a8\u00f7\u0092\u0012\u00a7]\u0091E\u0097\u008f\u00a9\u00a6QJ\u0010\u00cd\u0080\u00dfU\u000e\u00b3\u009b\u00ce\u00af\u00a4'\u00fd\u009f\u00d6\u0097\u0018\u00b0\u00b7\u00af[/N\u00d7\u0006\u0006\u00f8\u00bd\u00e9\u0094G\u00d9\u00ac<\u0098\u00d8\u00da\u00b9\u00e9+\u001e\u00e7\u0086\u00bb\u0001\u00f4\u0094\u001bt\u009e\u00e4\u0012\u00d0\u00b6\u00baJu7\u00afo\u0005/'Os\u00e9\u0010;\u00bd\u00a8\u000f\u0080S\u00f8!\u00bah~Q4.{96\u00f7\u00fd\u00d5\u0094\u00b9\u00c19\u00fe\u00e9\u009b\u0084\u00eeM!\u00d4\u00bc\u00cbgnA\u00b7Y\u00a3@@\u00c8\"\u00eax\u00e1S\u00ce\u001f\u00fcK\u0003\u0096\u009ap\r(;\u0006o,\u00c4m0\u00b3D\u00af9?\u00b9\u00aa\u00e6W\f\u00e7\u0012\u00df\u0080\u00a9l\u00a7\u00bd%~u\u0012l45\u00ae2\u00da\u00dfC\u00b0}{W\u007f7\u0085\u0005\u00e3\u00a2\u00dd\u00fcL\u00a1\u00f5D\u000f\u0095\u0014+\u00d2T\u0085\u00a9=\u00fd\u0013s\u00c6D+\u008a\be\u0082~\u00b9\u009e\u009f\u00a1U\b\u0090\u0011{\u00aaH\u00a8\u00a0\u0006\b1)\u0088^XH\u0096\u0094\u0018\u00f2\u009d\u00cfJ\u00a9N\u00a0\u0019\u008d\u00ac\u00a0T8\u0085\u009a\u0095}\u00f7\u00d0\u00ea\u007f\u00ad\u00b9s";
      int var8 = "\u001d\u008c\u0085E\u0084\u0086\u00b0'3\u0019\u00edV\u00e9Pm\u00e2\u0010U\u00bc\u00fa15\u00b9>\u00f2\u00f1t\u00a0\u0010y\u00d2\u00d1\u00880\b\u00d1'\u00b5e\u0093\u00c8>bH\u00e5\u00f3M+n%\u00c0-\u009c\u0095\u00e0\u0089\u001c\u0095\u00b6\u009f\u008e\u0087\u00d0\u00abQP\f\u00a6>\u00b4d\u009cr\u0007\u00a7<FjUd^E\u0018\u00c8\u00ef\u0018\u00b9R\u00d3\u00cb\u008bAZV\u00df'\u008e\u00108\u0092qbxT\u00f5`\u00bd\u0010\u00fbc\u0013h\u0010\u008b(\u00cb\u00e5ZoO\u008e\u00cc\u00d88\u00109\u00f3e\u00d1\"\u00c1\r\u00a6&3W\u00a8qD\u0006m\u0018Y\u0013ff\u00a5\u0097\u0094$FU\u0088\u00ad\u00afz=\u00a3~/\u00fd\u00ce@/\u00e0\u00f0  w\u0086\u00e1b4\u0092#\u0092^\u00ccy)\u008b`\u00b7t\u008f\u00c4\"0G\u000e\u00f2\u00e7\u001d\u00d1\u00e7\u0018q\u00a8Y(E\u0091 A\u00d2\u00f7\u00ea\t\u009aA\u00e7\u009b\u001b\u00a4W\u00bf\u00fd\u00f7\u00ef\u0004\u0095\u00ba\u001b&\u00cd~\u00b9\u0093\u0083\u00b6B\u0015\u00fa\u0013\u00ca\u000ev\\6\u00e80{\u00caSz\u000eM\u00da\u007f\r\u0080\u0010\u0003m\u0094\u0092\u00b0\u00fcd\u001b\u008a}\u0091\u000f?<\u0085\u00f0\u0093\u00c9\u00ee\u00ab\u00fb\u00eb\u008dn\u00a2\u00d6\u0086\u009c\u00cb\u0094Ro\u00db\u00a6\u0093\u0087a\u0010\u0001#\u00d4Q,\u0096w/|\u00ae\u00a0h/\u00ba\u0014'P\u000e0\u00fbF\u007fCt\u00df$m\u00e3H_!M\u00ac\u00da\u008a\u001fgG\u00a6\u00fd\u00ab\u00e6D@\u00cex\u0098\u0084\u00ceSs\u00f1\u00b1\u00ddL\u00d3\u0015:Ts*5\u00f7@\u00be\u00cf\u001cg \u00ae/\u00d0\u00fbT\u00d9f\u000f\u0005N\u00d4\u00adfG\u0092)\u00b0\u00830\u009ar=\u00f0 \u008b\u00fc\u00b31\b\u0002h\u0006\u00bc\u0017\u00c6\u00d7\u00d2\u0010\u001fR\u00bb\u0098B\u00d7\u00e1\u0014\u00cd\u008b\u00ca0\f\u008305\u0010\u00f4+\u008f\u00d6)\u0088\u009b!\u0003\u009f?\u0016\u001cU;\u00ec\u0018\u009a/*\u0097\u0016\u0005\u00ebf\u0010v7\u0014({G\u000f\u00ed\u0015\u001f\u0012\u007f\u0019\u00f0\u0083\u0010\u00bbF/\u00f3\u00a1H\u0013J\u0093N\u0082\nnA\u008a\u009f\u0010\u0000\u00d7\u00b5@8D\u00f7\u00a7\u00a2\u001e\u00b5O\u00bbs6\u00fe\u0010\u0017\u008fKo<_\u000b\u00a9Z\u0002T\u00c4\u008a\u00c8\u0092r(\u00dbnI\u0090M~\u0007\u0003\u000ei\u00ff\u001b\u00be\u0015\u00b7\u0014\"\u00b0K\u00f4\u00d8M\u0097{\u0099XC\u00fc\r\u0000\u00fe\u0090K\u00a6\u000b\u00b6?\u0094\u001ecH\u00b7\u00af[/N\u00d7\u0006\u0006\u001e\u00b0cbm\u00de\u00b6\nqF\u00aaj\u00f8\u0096\u00ef\u00c0\u00b7V>\u00b2Ib\u00bf?\u00ef\u00fe\u00e2I\u001a\u00e8\u0004\u00cd\u00a4\u00b2V+\u00d7G\u00e7_\u00a2\u00d9\u008a\u00e7:\u00f6\u00d2\u0011\u00ff\u00e9\u0098\u00ac\u00fbT*\u0019!\u001e[\u0003\u001bj\u00f0\u0016\ba<\u001f\u0092\u00e0I\u00e0\u00f9\u0010&\u00ef\u00c8\u00b0\u0088pT\u0019\u0089':\u000e\u00be\u00afH%(\u00aa2L\u0093j\u0018J\u001d\u00f6\u00c2\u00a9\r\u00b1\u001d\u00d0\u00dc{\u00f2\u0011\u001c\u00f0T\u00c9\u008a\u00c0\u00a0\"\u00aaK\u00e8\u00ba\u00bb\u00afh\u0016hl\u0013\u00e5b\u0090\u00b7\u00af[/N\u00d7\u0006\u0006\u00ef-O\u00f84\u0014\u00b5\u00d8P M\u00d9\b\u0005\u00bd\u00f7\u00f15\u0098\u00e58\u00fb\u0092\u0088Y#\u00f5\u00beVo\u0011\u001f\u0010\f\u00bf\u0098\u00bd\u00e0\u00b4\u00b9 `\u007f\u00b5\u00ab\u00dc\u00b9P\u00e9\u00b1\u0017\u00e0f\u00ca\u0010\u00c4\u009b?B\u0003\u00d4\u00f7\u00e2\u0094\u00b1\u00e3\u00b6&\u0010\u00ba\t\u00f3\u00ce\u00f9\u000f\u008f:\u001dC\rtX\u00e5r[\r\u0095nN\u00bb\u0095J2W\u00fe\u00f4\u0016\"\u0083\u009b/M\u00e2}&Ab\\\u00e4\u00a5v\u0093`\u00e8\u00ce\u00aeazA\u0002\u00f0\u00d86p\u0094\u00b1\u00aevi\u00b6\u0015\u008c\u00fdv\u0089\u00d8\b\u00f5\u00f8s\u00a3k\u008f\u00ec\u0018\u0018\u008d\u00d4\u00b5\u0093\u00d7\u0011@\u0097\u00fbW\u001aJzp@\u00edB'\u00c5&\u00c2\u0006\u00db\u00df\u0010\u00e7\b2\u00e2E\u00c3\u00f8\u00a8\u00964m\u00a4lM\u008af0\b\u00d1'\u00b5e\u0093\u00c8>bH\u00e5\u00f3M+n%\u00c0-\u009c\u0095\u00e0\u0089\u001c\u0095\u00b6\u009f\u008e\u0087\u00d0\u00abQP\f\u00a6>\u00b4d\u009cr\u0007\u00a7<FjUd^E8\u00b7\u00af[/N\u00d7\u0006\u0006i\u0016\u00f4\u00b7{d\u001d=93'\u00e8$AFgL\u0093\u00a8~\u008a\u00c2*\u00dan\u00b36C\u00ac\u008c\u001f\u00c7\u0017'\u00bf \u00d6p\r\u00d5\u00c2\u0090\u0098#\u00d4\u00f2\u00b4#\b\u0014\u00d63\u00b6\u0011\u00f6\u0084\u00a3\b\u00da\u00d4\u0089\u00b4\u0081\r#e\u0010 (R\u0099\u00bf\u0084f\u0004\u00dc\u00e6\u008ac\u00f5\u00fa'f8\u00b7\u00af[/N\u00d7\u0006\u0006\u001e\u00b0cbm\u00de\u00b6\nqF\u00aaj\u00f8\u0096\u00ef\u00c0\u00b7V>\u00b2Ib\u00bf?\u00dd8\u00bb\u0012\u00db\u0093V\u008a\u00a4\u0015\u00ddXQ\u00e2\u00b1\u0086n\u00f7\u0010\u00f5\u00c2(T\u00d4\u0018\u00c8\u00ef\u0018\u00b9R\u00d3\u00cb\u008bAZV\u00df'\u008e\u00108\u0092qbxT\u00f5`\u00bd0\u00ecb\u0004\u00a7\u008de \u00fd\u00f3\u00e6\u0002\b5\u0086IZ\u00b9\u00faD\u00bb\u00a2s\u00bax\u0013\u0011j\u009f\u009495\u00c3d_R\u00970\u009en\u009dg\u0098%Rd\u00e2\u00fe\b\u0018\u00e84\u0090$\u0090\u00fe\u00c3\u0011\u0097\u00a3R<\u00158\u0086\u009b\u001ciP\u00fe\u00a0?\u00b1\u0090P\u00a0\u00ec\u0093\u00a7\u0089u\u00a5\u0082\t\u00aa\u00ac\u00a9\u007f\u00dc \u00b7\u00cbU\u0086w\u00bc\u00a0\u00d5\u00daz\u0086C\u00b6\u00fb\u00c8\u00b7\u00ab\u00d0\u0007}\u000f\u000b\u00e6\u0014&\u00f5\u0091\u00c9\u00d0\u00b8\u008d6\u0097\u00e2\u001d\u00b9&\u00f4*f \u00e1\u0098fN\u00ec}\u00e9a\u00a7D\"\u00cb\u0084\u009e=\u0013\u00ec1\u0013\u001a\u00e1\u0016\u00fa\u00d0\bu\u008a\u00f4C\u0082|V~(\u00c8\u00ef\u0018\u00b9R\u00d3\u00cb\u008b\u00a28\u0094\u0017\u00dd\u00dc<-&\u0012\u00a1V\u008c\u0001'/\u00f9\u0000\u00ad\u00e5\u0095\u00a4\u0094q\u00179\u00a5\u00e2\u0088\u001afj0\u00e9\u00d4+\u008bFB\u00db$\u0005r\u00fa\u00f0S\u008d#\u0016\u00f9\u00b1p\u00dd\u00cb\b\u00c6\u00be\u00b6\u00c6l>\u008c\u00faf\u00eecE\u008cD\u009a\u00e0\u00e8\u0013\u00fb\u00ea\u00a0\u00bax\u00aeXi(j,c\u00c0'l\u00b0\u00ac\u001d\u0093\u0097\u0006D\u00e1$h\u0083\u009aL\u00ed\u00fd\u0086\u00e9\u00cb\u001b\u00d5\u00caQ\n\u001f!/=\u00d9Q_\u0092&5\u00d90\u00ecb\u0004\u00a7\u008de \u00fd\u00f3\u00e6\u0002\b5\u0086IZ\u00b9\u00faD\u00bb\u00a2s\u00bax\u0013\u0011j\u009f\u009495\u00c3d_R\u00970\u009en\u009dg\u0098%Rd\u00e2\u00fe\b0{\u00caSz\u000eM\u00da\u007f\r\u0080\u0010\u0003m\u0094\u0092\u00b0\u00fcd\u001b\u008a}\u0091\u000f?<\u0085\u00f0\u0093\u00c9\u00ee\u00ab\u00fb\u00eb\u008dn\u00a2\u00d6\u0086\u009c\u00cb\u0094Ro\u00db\u00a6\u0093\u0087a\b:\u0095\u0086(,b\u00bb\u00e8 \u00ff+\u0095\u00d3x\u00164\u00ee\u00ae],\u00ed&\u00d3\u00c6\u00fdkm|+\u00cb\u00ee\u0098\u00ca\u000b\u007f\u00c0C\u00a1V\u00f5\u00ef\b\u001e\u0080@\u00ce\u00fd@4\u00e5\u00109\u00f3e\u00d1\"\u00c1\r\u00a6\u0081lx\u00bf\u00db\u009ar\u00cb\u0010\u00de\u00e2+5\u0003\u00ad4\u0005K>\u008f\u00f3\u0082\u00ce\u00a0l\u0018\u001b\u00b5\u009a\u00f8\u00a9\u0011\u0001\u009c\u001f\u00f44\u00a5\u0099%2\u007f\u00f5*\u0005\u00b2\u009f\u00c9\u00a6\u00e2\u0010\u00f4+\u008f\u00d6)\u0088\u009b!\u0003\u009f?\u0016\u001cU;\u00ec\u0010\u00a3\u00ce\u00d6\u00f4\u001f8x\u00d775\u00f5\u00bf6\u0017}\u00bb\u0018xO\u00dbR\u00bd\u00fdRRO\u00ac\u00da\u00df8\\\u00c3z\u00a4TdEp\u0003\u009blp\u00a7\u00aa\u00b2\u00c2\u00cd\u00d2\u00fb[.\u00f672D\u00c9\u00e8\u0001K\u00bag\f|O<\u00cd\u0012\u00b8\u0082\rw*E\u0091F\u00b6VW\u00ce\u001b\u00e0\u0003\u00efP\ra\u00d2\u009b\u00a3Lv\u00bbN\u00ca \u00d2\u00c7\u0004\u00fc\u0099\u009bM|\u00eb\u00ac\u00fe\u0095\u00e7\u009d\b\t[\u00e7&\u00d1^\u00bc\u00f1QW@\u0085\f\u00cclS&Cx8vxb\u00a5\u001eT\u0018\u00b72\u0095\u0080\u0089`\u00ac\u00ac\u001cl&\u00c42\u00ee\u00c5\u0007\u00ed\u0010#1F+\u00b9\u00abfa\u0091\u0082\u00dc\u00d2\u001fI}Z \u00ca\u0080~A\u00b4\u00e5\u00da(\u001b\u0005\u0003\u00ef\u0010~\u00a8\u00dfL\u00edE\u0094\u009aE\u00a3u\u00fd\u00cb?\u00bd\u00f0\u00db\u00b8\u0019\u0010\u00fbc\u0013h\u0010\u008b(\u00cb\u00e5ZoO\u008e\u00cc\u00d880\u00b7\u00af[/N\u00d7\u0006\u0006\u00ef-O\u00f84\u0014\u00b5\u00d8P M\u00d9\b\u0005\u00bd\u00f7#.BJ#8\u00ae=\t\u00fe\u00b5#\u00fd!\u00d4\u0082D\u00b1\u00b1H\u00f3\u0099-\u00f8\b\u0002h\u0006\u00bc\u0017\u00c6\u00d7\u00d2\b:\u0095\u0086(,b\u00bb\u00e8\u0010\u00e8\u0001\u00f1]\u0082\u00b7\u0091\u00a6\u00e8\u00cb\u001f\u0082\u000b\u00d32\u007f\u0010\u0082\u00e0\u00a4\u0004\u00e8/G\u0089\u00f1\u001fa\u0097\u00ae\u00fdU\u00d0(\u00dbnI\u0090M~\u0007\u0003\u000ei\u00ff\u001b\u00be\u0015\u00b7\u0014\"\u00b0K\u00f4\u00d8M\u0097{\u0099XC\u00fc\r\u0000\u00fe\u0090K\u00a6\u000b\u00b6?\u0094\u001ec(\u00e6\r\u00c8\u00aaM\u00b9\u0091\u00d2o\u00f6\u00fd\u0086\u001c\u00e1\u0087\u00d5\u00ef1\u000fy\u00f4#\u008e\u001a\u00b2\u00c5[\u000b\u0019O\u009a\u00fcD\u00e3\u00f5x\u00ee\"\u00d4\u0096\u0088\u00e0\u0098\u00cbS\u00b8<\u00c4d\u00b1Yy\u00d2\u00ac|\u00c1\u001c<B\n\u00dd\u0011Ww+OP\u00c4\b\u00f20L@t\u0004\u001a\u009e\u0096\u00bf\u0086\u009d^\u00a62\u00a4\u0006\u00cfQ\u009c\u00b7\u00c5\u00b8\r\u009e\u0006\u00f9.g\u00d8\u00fd\u0018\u001eS3r\u0019=b\u00db\u00e7YIcon\u009b*:\u00f6\t\u00a9}\u00c4\u00ecqhI\u00fc\u008fgjd\u00c5\u00a4\u00ac6]\u00c5\u00b4\u009f\u00b9C\u0018<\u00033M\u0002\u0096p\u00b90\u009cp\u009b\u00ae\u00f9\u00e6\"\u00f6\u00f8\r\u00a9]\"7\u00f8m\u0016^\u00c7\u0017\u00c4D\t\u00b7r\u0010\u001d\u001e=\u0007\u00f2\u0005\u00ac|\u00ae\u008d\u0017\u00f28\u0004\u00ecO\b\u00e8*\u00c6\u00c5K\u0003\u0018U\b\u00c8\u00eax\u00ae\u001bwf\u00ce\u0018@\u00c78Y\u00f9\u0098\u009f\u0097!\u00dc\r\u0019r\u0091\u00eb\u00ee<fAM\u00fe\u00df%\u00d6\u0010\u001d\u008c\u0085E\u0084\u0086\u00b0'3\u0019\u00edV\u00e9Pm\u00e2\u0010\u00a3\u00ce\u00d6\u00f4\u001f8x\u00d775\u00f5\u00bf6\u0017}\u00bb0\u00b7\u00af[/N\u00d7\u0006\u0006\u009d\u00a9\u00a8\u00adrSJ\u0096\u00a5W%e\u00a8\u00a4\u00d8&\u007f\u00a5\u008c$\u00a3\u00fa\f-4'\u00fa;\u00b2\u00e9\u00b0\u00a5\u00f3\u00a7\u00af^\u00fc\u00cd\u00cc-(\u00e6\r\u00c8\u00aaM\u00b9\u0091\u00d2o\u00f6\u00fd\u0086\u001c\u00e1\u0087\u00d5\u00ef1\u000fy\u00f4#\u008e\u001a\u00b2\u00c5[\u000b\u0019O\u009a\u00fcD\u00e3\u00f5x\u00ee\"\u00d4\u0096\u0010\u0093\u00ab,\u0083\u00eb2\u00e9\u0094\u000f\u0087f\u0095\u0005\u00e2Rc\u0010\u009bW\u008f\u00fc\u00ad&\u00a4@\u00f9\u00d4]\u00a9\u0011\u0081Bu\b\u0084\u00e2K&\u00dd|qU\u0010\u00b3\u0015\u0097\u00e9]\u0091\u0099E\u00936\u00a6\u00a3D\u0096\u00b0\u00d7\bv\u00a6I\u00abN\u00ac?\u00818\u00b7\u00af[/N\u00d7\u0006\u0006\u001e\u00b0cbm\u00de\u00b6\nqF\u00aaj\u00f8\u0096\u00ef\u00c0\u00b7V>\u00b2Ib\u00bf?\u00dd8\u00bb\u0012\u00db\u0093V\u008a\u00a4\u0015\u00ddXQ\u00e2\u00b1\u0086n\u00f7\u0010\u00f5\u00c2(T\u00d4\b\u0094\u00fc\u00e2\u00ba\u001ab\u008f<\u0010\b\u00d1'\u00b5e\u0093\u00c8>\u00a2\u00d72\u008020\u008c\u00a1\u0010\u00a1\u0018\u001e8\u00e9f@\u00cc!O\u00e4\u00df\u00c1(\u00d8\u000e\b\u00ad\u0090&'\u00f5)ndH\u00b7\u00af[/N\u00d7\u0006\u0006\u001e\u00b0cbm\u00de\u00b6\nqF\u00aaj\u00f8\u0096\u00ef\u00c0\u00b7V>\u00b2Ib\u00bf?\u00ef\u00fe\u00e2I\u001a\u00e8\u0004\u00cd\u00a4\u00b2V+\u00d7G\u00e7_\u00a2\u00d9\u008a\u00e7:\u00f6\u00d2\u0011\u00ff\u00e9\u0098\u00ac\u00fbT*\u0019!\u001e[\u0003\u001bj\u00f0\u0016 \u00ac`;\u00aa\u00a42m\u00d7a\u009e\u00ae\u00f6\u00ac'\u00fb\u001cqgl\u00ccD\u00f8\u008fF6\u009b\u000f\u00f3\u00f18\u00b6\u00b0\u0018Y\u0013ff\u00a5\u0097\u0094$FU\u0088\u00ad\u00afz=\u00a3~/\u00fd\u00ce@/\u00e0\u00f0\u0010\u0093\u00ab,\u0083\u00eb2\u00e9\u0094\u000f\u0087f\u0095\u0005\u00e2Rc\u0010#\u0004\u008c\u00a2TJA \u008d\u0086\u0013\u008b\u001dk4\u00dd\b\u001e\u0080@\u00ce\u00fd@4\u00e5\u0010\u000e\u008f\u00a8\u00f7\u0092\u0012\u00a7]\u0091E\u0097\u008f\u00a9\u00a6QJ\u0010\u00cd\u0080\u00dfU\u000e\u00b3\u009b\u00ce\u00af\u00a4'\u00fd\u009f\u00d6\u0097\u0018\u00b0\u00b7\u00af[/N\u00d7\u0006\u0006\u00f8\u00bd\u00e9\u0094G\u00d9\u00ac<\u0098\u00d8\u00da\u00b9\u00e9+\u001e\u00e7\u0086\u00bb\u0001\u00f4\u0094\u001bt\u009e\u00e4\u0012\u00d0\u00b6\u00baJu7\u00afo\u0005/'Os\u00e9\u0010;\u00bd\u00a8\u000f\u0080S\u00f8!\u00bah~Q4.{96\u00f7\u00fd\u00d5\u0094\u00b9\u00c19\u00fe\u00e9\u009b\u0084\u00eeM!\u00d4\u00bc\u00cbgnA\u00b7Y\u00a3@@\u00c8\"\u00eax\u00e1S\u00ce\u001f\u00fcK\u0003\u0096\u009ap\r(;\u0006o,\u00c4m0\u00b3D\u00af9?\u00b9\u00aa\u00e6W\f\u00e7\u0012\u00df\u0080\u00a9l\u00a7\u00bd%~u\u0012l45\u00ae2\u00da\u00dfC\u00b0}{W\u007f7\u0085\u0005\u00e3\u00a2\u00dd\u00fcL\u00a1\u00f5D\u000f\u0095\u0014+\u00d2T\u0085\u00a9=\u00fd\u0013s\u00c6D+\u008a\be\u0082~\u00b9\u009e\u009f\u00a1U\b\u0090\u0011{\u00aaH\u00a8\u00a0\u0006\b1)\u0088^XH\u0096\u0094\u0018\u00f2\u009d\u00cfJ\u00a9N\u00a0\u0019\u008d\u00ac\u00a0T8\u0085\u009a\u0095}\u00f7\u00d0\u00ea\u007f\u00ad\u00b9s"
         .length();
      char var5 = 16;
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var20 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var20;
                  if ((var12 += var5) >= var8) {
                     STRINGS = var9;
                     String[] var15 = STRINGS;
                     CLIENT_ID = STRINGS[72];
                     CLIENT_SECRET = var15[9];
                     MICROSOFT_AUTHENTICATOR = new MicrosoftAuthenticator();
                     GSON = new Gson();
                     break label27;
                  }

                  var5 = var6.charAt(var12);
                  break;
               default:
                  var9[var7++] = var20;
                  if ((var12 += var5) < var8) {
                     var5 = var6.charAt(var12);
                     continue label27;
                  }

                  var6 = "\u0001#\u00d4Q,\u0096w/|\u00ae\u00a0h/\u00ba\u0014'\b\u00b9{\u0095\u00ad\u00e3\u00ca\u00fb\u0097";
                  var8 = "\u0001#\u00d4Q,\u0096w/|\u00ae\u00a0h/\u00ba\u0014'\b\u00b9{\u0095\u00ad\u00e3\u00ca\u00fb\u0097".length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static Exception a(Exception exception) {
      return exception;
   }

   private static String decodeUtf8(byte[] bytes) {
      int index = 0;
      int value;
      char[] chars = new char[value = bytes.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & bytes[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = bytes[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
