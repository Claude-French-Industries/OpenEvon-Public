package club.evon.api;
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
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.AccountData;
import club.evon.manager.ChatNameManager;
import club.evon.mixins.MinecraftClientAccessor;
import club.evon.module.BaseModule;
import club.evon.util.FileDialogUtil;
import club.evon.util.TextUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.math.RandomUtil;
import club.evon.util.net.HttpUtil;




public final class MicrosoftLoginManager {
   private static final MicrosoftAuthenticator AUTHENTICATOR;
   private static MicrosoftAuthResult authResult;
   private static Consumer<MicrosoftAuthResult> callback;
   private static final String CLIENT_ID;
   private static final String CLIENT_SECRET;
   private static final long KEY = KeyedRandom.create(-1245219453619565271L, -9188805092001871266L, MethodHandles.lookup().lookupClass()).next(176709710566232L);
   private static final String[] STRINGS;

   public static MicrosoftAuthResult startMicrosoftLogin(Consumer<MicrosoftAuthResult> callBackResponse) throws Exception {
      long longValue = KEY ^ 22756143223569L;
      int[] values = RandomUtil.getStateArray();
      callback = callBackResponse;
      HttpClient httpClient = new HttpClient();
      int[] values2 = values;
      int value = ThreadLocalRandom.current().nextInt(8080, 8999);
      HttpServer create = HttpServer.create(new InetSocketAddress(value), 0);
      String[] strings = STRINGS;
      String text = strings[78] + value + strings[93];
      club.evon.util.TextUtil.setClipboard(text);
      EvonClient.getExecutor().execute(() -> {
         String[] var1x = STRINGS;
         FileDialogUtil.showMessage(var1x[19], var1x[72], 1);
      });
      create.createContext("/", var3x -> {
         String var4x = var3x.getRequestURI().toString();
         var4x = var4x.substring(var4x.lastIndexOf(61) + 1);

         try {
            JSONObject var5x = new JSONObject();
            String[] strings2 = STRINGS;
            var5x.put(strings2[62], strings2[53]);
            var5x.put(strings2[11], var4x);
            var5x.put(strings2[79], strings2[1]);
            var5x.put(strings2[24], String.format(strings2[25], value));
            var5x.put(strings2[17], strings2[57]);
            String var6x = httpClient.readPost(strings2[0], jsonToMap(var5x), strings2[68]);
            String var7x = new JSONObject(var6x).getString(strings2[86]);
            JSONObject var8x = new JSONObject();
            var8x.put(strings2[76], strings2[23]);
            var8x.put(strings2[83], strings2[7]);
            JSONObject var9x = new JSONObject();
            var9x.put(strings2[10], strings2[29] + var7x);
            var9x.put(strings2[33], strings2[81]);
            var9x.put(strings2[44], strings2[35]);
            var8x.put(strings2[89], var9x);
            JSONObject jSONObject = new JSONObject(httpClient.readPost_r4(strings2[32], var8x, strings2[40], strings2[69]));
            String string = jSONObject.getString(strings2[47]);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(strings2[6], strings2[70]);
            jSONObject2.put(strings2[74], strings2[31]);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(strings2[65], strings2[38]);
            jSONObject3.put(strings2[54], new String[]{string});
            jSONObject2.put(strings2[71], jSONObject3);
            JSONObject jSONObject4 = new JSONObject(httpClient.readPost_r4(strings2[98], jSONObject2, strings2[69], strings2[69]));
            String string2 = jSONObject4.getString(strings2[80]);
            String string3 = jSONObject4.getJSONObject(strings2[4]).getJSONArray(strings2[61]).getJSONObject(0).getString(strings2[58]);
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put(strings2[52], String.format(strings2[48], string3, string2));
            jSONObject5.put(strings2[59], strings2[26]);
            JSONObject jSONObject6 = new JSONObject(httpClient.readPost_r3(strings2[28], jSONObject5, strings2[69]));
            MinecraftProfile json = httpClient.getJson(strings2[84], jSONObject6.getString(strings2[86]), MinecraftProfile.class);
            authResult = new MicrosoftAuthResult(json, jSONObject6.getString(strings2[86]));
            System.out.println(authResult.getAccessToken());
            club.evon.util.TextUtil.setClipboard(authResult.getAccessToken());
            callback.accept(authResult);
            // This runs on the HttpServer thread: setSession and the account store touch GUI
            // state, so hand the install back to the render thread.
            MsaAuth.Result browserResult = MsaAuth.Result.ok(
               authResult.getAccessToken(), null, authResult.getProfile().getId(), authResult.getProfile().getName()
            );
            ClientContext.MINECRAFT_CLIENT.execute(() -> installSession(browserResult, LoginType.MICROSOFT));
            String text2 = strings2[8];
            var3x.sendResponseHeaders(200, strings2[45].length());
            OutputStream responseBody = var3x.getResponseBody();
            responseBody.write(strings2[45].getBytes(StandardCharsets.UTF_8));
            responseBody.close();
            EvonClient.getExecutor().execute(() -> {
               String[] var2x = STRINGS;
               FileDialogUtil.showMessage(var2x[99], String.format(var2x[36], browserResult.name), 1);
            });
            create.stop(2);
            callback = null;
         } catch (Exception exception) {
            exception.fillInStackTrace();
            create.stop(2);
            callback = null;
         }
      });
      create.setExecutor(null);
      create.start();
      if (values2 != null) {
         BaseModule.setStateArray(new BaseModule[1]);
      }

      return authResult;
   }

   private static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
      long longValue = KEY ^ 87463171382628L;
      HashMap map = new HashMap();
      int[] values = RandomUtil.getStateArray();
      Iterator keys = json.keys();
      int[] values2 = values;

      while (keys.hasNext()) {
         label34: {
            String text;
            Object object;
            label33: {
               text = (String)keys.next();
               object = json.get(text);
               boolean flag = object instanceof JSONArray;
               if (values2 == null) {
                  if (flag) {
                     object = jsonToList((JSONArray)object);
                     if (values2 == null) {
                        break label33;
                     }
                  }

                  if (values2 != null) {
                     break label34;
                  }

                  flag = object instanceof JSONObject;
               }

               if (flag) {
                  object = jsonToMap((JSONObject)object);
               }
            }

            map.put(text, object);
         }

         if (values2 != null) {
            break;
         }
      }

      return map;
   }

   private static List<Object> jsonToList(JSONArray jsonArray) throws JSONException {
      long longValue = KEY ^ 137630518091460L;
      int[] values = RandomUtil.getStateArray();
      ArrayList list = new ArrayList();
      int[] values2 = values;
      int index = 0;

      while (index < jsonArray.length()) {
         label34: {
            Object object;
            label42: {
               object = jsonArray.get(index);
               boolean flag = object instanceof JSONArray;
               if (values2 == null) {
                  if (flag) {
                     object = jsonToList((JSONArray)object);
                     if (values2 == null) {
                        break label42;
                     }
                  }

                  flag = object instanceof JSONObject;
               }

               if (values2 != null) {
                  break label34;
               }

               if (flag) {
                  object = jsonToMap((JSONObject)object);
               }
            }

            list.add(object);
         }

         index++;
         if (values2 != null) {
            break;
         }
      }

      return list;
   }

   public static AccountData importAccountFromFile() {
      String[] strings = STRINGS;
      String selected = FileDialogUtil.openFileDialog(strings[92], null, null, strings[75]);
      if (selected == null) {
         return null;
      }

      // The cookie exchange is several network round trips; keep it off the render thread.
      File file = new File(selected);
      EvonClient.getExecutor().execute(() -> loginWithCookie(file));
      return null;
   }

   public static List<AccountData> importAccountsFromFiles() {
      long longValue = KEY ^ 24682670323294L;
      int[] values = RandomUtil.getStateArray();
      ArrayList list = new ArrayList();
      int[] values2 = values;
      String[] strings = STRINGS;
      String[] strings2 = FileDialogUtil.openMultipleFileDialog(strings[46], null, null, strings[18]);
      String[] strings3 = strings2;
      if (values2 == null) {
         if (strings2 == null) {
            return list;
         }

         strings3 = strings2;
      }

      String[] strings4 = strings3;
      int length = strings4.length;
      int index = 0;

      while (index < length) {
         String text = strings4[index];
         AccountData loginWithCookie = loginWithCookie(new File(text));
         if (values2 == null) {
            if (loginWithCookie != null) {
               list.add(loginWithCookie);
            }

            index++;
         }

         if (values2 != null) {
            break;
         }
      }

      return list;
   }

   public static AccountData loginWithCredentials(String email, String password) {
      long longValue = KEY ^ 41060164944079L;
      int[] values = RandomUtil.getStateArray();

      try {
         MicrosoftAuthResult loginWithCredentials = AUTHENTICATOR.loginWithCredentials(email, password);
         MicrosoftAuthResult tmp = loginWithCredentials;
         if (values == null) {
            if (loginWithCredentials == null) {
               return null;
            }

            tmp = loginWithCredentials;
         }

         MinecraftProfile profile = tmp.getProfile();
         return new AccountData(loginWithCredentials.getAccessToken(), loginWithCredentials.getRefreshToken(), profile.getId(), profile.getName());
      } catch (MicrosoftAuthenticationException microsoftAuthenticationException) {
         microsoftAuthenticationException.fillInStackTrace();
         return null;
      }
   }

   /**
    * Logs in from a Netscape {@code cookies.txt} (or a file holding a bare {@code __Host-MSAAUTHP=...}
    * line).
    *
    * <p>The previous implementation split every line on tab and read fields 5 and 6 unconditionally,
    * so anything that was not a strict 7-column Netscape line threw
    * {@link ArrayIndexOutOfBoundsException} and the login silently returned null. Cookie parsing now
    * lives in {@link MsaAuth}, which also handles {@code #HttpOnly_} lines, comments, plain
    * {@code name=value} pairs and {@code Set-Cookie} headers.
    */
   public static AccountData loginWithCookie(File cookieFile) {
      MsaAuth.Result result;

      try {
         String contents = FileUtils.readFileToString(cookieFile, StandardCharsets.UTF_8);
         result = MsaAuth.loginWithCookieText(contents);
      } catch (Exception exception) {
         notify("Cookie login failed", "Could not read " + cookieFile.getName() + ".");
         return null;
      }

      if (!result.success) {
         notify("Cookie login failed", result.message);
         return null;
      }

      return applySession(result);
   }

   private static AccountData fetchAccountFromToken(String text) {
      try {
         String[] strings = STRINGS;
         HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URI(strings[94]).toURL().openConnection();
         httpsURLConnection.setRequestMethod(strings[49]);
         httpsURLConnection.setRequestProperty(strings[63], strings[43] + text);
         JsonObject jsonObject = (JsonObject)ClientContext.GSON.fromJson(IOUtils.toString(httpsURLConnection.getInputStream()), JsonObject.class);
         return new AccountData(text, null, jsonObject.get(strings[14]).getAsString(), jsonObject.get(strings[50]).getAsString());
      } catch (Exception exception) {
         return null;
      }
   }

   /**
    * Token login from the clipboard.
    *
    * <p>Accepts either a Minecraft <em>access</em> token or a Microsoft <em>refresh</em> token -
    * see {@link MsaAuth#loginWithToken(String)}.
    */
   public static void loginWithClipboardToken() {
      String text = TextUtil.getClipboard();
      if (text == null || text.isBlank()) {
         notify("Token login", "There is no token on the clipboard.");
         return;
      }

      // The refresh/validate chain is several network round trips; keep it off the render thread.
      EvonClient.getExecutor().execute(() -> loginWithToken(text));
   }

   /**
    * Logs in with a pasted or imported token.
    *
    * <p>The old implementation sent the value straight to
    * {@code api.minecraftservices.com/minecraft/profile} as a Bearer token, so it only ever worked
    * for a live Minecraft access token - and those expire within a day. Saved alts hold
    * <em>refresh</em> tokens, which is why "token login does not work". {@link MsaAuth} now detects
    * the token type, redeems refresh tokens through
    * {@code login.live.com -> XBL -> XSTS -> login_with_xbox}, and falls back to the other
    * interpretation if the first one fails.
    */
   public static AccountData loginWithToken(String token) {
      MsaAuth.Result result = MsaAuth.loginWithToken(token);
      if (!result.success) {
         notify("Token login failed", result.message);
         return null;
      }

      return applySession(result);
   }

   /** Installs the Minecraft session for a successful login and stores the account. */
   private static AccountData applySession(MsaAuth.Result result) {
      AccountData data = new AccountData(result.accessToken, result.refreshToken, result.uuid, result.name);
      MinecraftClient client = ClientContext.MINECRAFT_CLIENT;

      // setSession and the account store touch GUI state, so they have to run on the render thread.
      if (client != null && !client.isOnThread()) {
         client.execute(() -> installSession(result, LoginType.TOKEN));
      } else {
         installSession(result, LoginType.TOKEN);
      }

      return data;
   }

   static void installSession(MsaAuth.Result result, LoginType loginType) {
      try {
         MinecraftClientAccessor accessor = (MinecraftClientAccessor)ClientContext.MINECRAFT_CLIENT;
         Session session = new Session(
            result.name,
            parseUuid(result.uuid),
            result.accessToken,
            Optional.empty(),
            Optional.empty()
         );
         accessor.setSession(session);
         EvonClient.getInstance().getAccountConfig().addAccount(session, loginType);
         notify("Logged in", "Logged into " + result.name + ".");
      } catch (Exception exception) {
         notify("Login failed", "Signed in, but the session could not be applied.");
      }
   }

   private static void notify(String title, String message) {
      FileDialogUtil.showMessage(title, message == null ? "Unknown error." : message, 1);
   }

   private static String getRedirectUrl(URL url, String cookie) throws IOException {
      HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
      String[] strings = STRINGS;
      httpsURLConnection.setRequestMethod(strings[51]);
      httpsURLConnection.setRequestProperty(strings[95], strings[66]);
      httpsURLConnection.setRequestProperty(strings[5], "*");
      httpsURLConnection.setRequestProperty(strings[37], strings[90]);
      httpsURLConnection.setRequestProperty(strings[60], strings[13]);
      httpsURLConnection.setRequestProperty(strings[73], cookie);
      httpsURLConnection.setInstanceFollowRedirects(false);
      httpsURLConnection.connect();
      return httpsURLConnection.getHeaderField(strings[91]);
   }

   public static UUID parseUuid(String uuidString) throws JSONException {
      try {
         String[] strings = STRINGS;
         String replaceFirst = uuidString.replaceFirst(strings[87], strings[96]);
         return UUID.fromString(replaceFirst);
      } catch (IllegalArgumentException illegalArgumentException) {
         throw new JSONException(STRINGS[39]);
      }
   }

   static {
      try {
      long var0 = KEY ^ 44837599728789L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[101];
      int var7 = 0;
      String var6 = "\u00c1;\u00d2bvzu\u00d0G\u0010\u00ac\u0098\u00c5\u00ce\u0091G\u0093?\u00bf32\u00e4\u00990Z\u00d7u\u00e7\u009b!\u00eda@\"'\u00cc\u0089\u001a\u0013\u00a2Y\u0094%\b\u00aej\u0097:\u0018\u00ac\u0085\u0013\u00ad\u0096\u00d8=\u009c?\u009afCU\u00ad\u0016\u00b8\u008bt5\u00e7\t\u00a5\u00f9}0\u008c\u00ec\u00da\u00cc\u00de>\u00f1\u00f9\u0001  \u00acB\r\u0007\u00b60]\u00ac\u00a8\u00b7\u00cbE\u00e4\u00ed\u00d2m\u008c0\u00b6\u0085/\u00964\u00e6\u0086:\u00f93P\u008d(\u00d3\u00ff\u0092\u0019\u00e4\u00ce0B\u00a6Z\u00f5\u00a1\u00c4BC@\u00ffI\u00a3\u00b3\u009eP\u00c1\u00cc\u00db\u00ca\u0090\u00a7\u00e8\u0081h=\u00ad\u00b0\u008c\u00df\u00d4fP\u00b1A\u00b5\u00d3\u0007\u00f6\u0082\u00b4p\u00a9c*G\u00ffae\u0010&\u0013\u008b\u007f\u001e\u00e1TL_H\u00a3\u0011\u00a2\u00c7|\u00d5\u0010\u00a9nM\u00c3\u0011crY\u007f\u00d9@s\u008f'\u0019\u00b7\u0010=\u0015\u00d3\u00d1)4<;\u0081JM\u00d0:\u00bao\n\b\r\u00f1)M\u00bb&\u00c9a0\u00f4\u00a5\u0012zL?\u00bc\u0081\u00f8\u00d1\u0082\u0006\u00ccO\u0006\u00c9\u0085k\u00d5\u0085\u00ab%\u00c2\u00ecb?\u0089\u00ca\u00a1\u0015\u00af\u00efP\"d\u00c7\u009am~[\u00da\u00dbHN-1<\u0097 9TV\u008b\u00c0\u00fd#\u00811\u00e6l\u0093\u00e2\u0083=]\t\u00fe\u0090h\u00bbu\u000b`\u008a^\u00a8\u009d\u00f7\u00acZ\u00f3\u0010.\u0016HXK8E\u0006\u0099\u00fd\u0083L\u0017\u00a8I-\b5GR\u00afyz\u00d9\u0016\u0010\u00c4u\u00dcSa\u00c3$\u008b\u00ee\u001b\u0018?\u00ab\u009bQVp\u009a\u00a7*x4\u0007\u00ab\u00e6(\u00cfYY\u00a5\u00b72\u00de\u00fc*O\u0099;\u0017\u00e8JX\u000eL\u00b8\u00c0\u0095\u00ed\u009b\u00f1\u0091\u00f3\u00f9\u0014\u0018\u00d0\u00eb\"\u000bD\u00faS\u000b\u008f\u00b6\u000e\u00b4\u009e\u0094\u001f\u00dc\u000f5T\u0013w\u001b\u0010\bcD\u00d2\u00c0\u00ad\u00ca@\u00f9B`\u00b7\u0093\u00b3\u0010:\n\u0016\u00bf&\u0090\u0017\u00b8\u000e,\u008fab\u008f\u0016Q5\u00e63\u00ec\u001b\u0086,\u00dc%\u008a\f\u00c3\u00af6\u0088)\u00ac7\u001d\u007f\b\u0013\u009f\u008d\u00d6\u00c2\u00074,\b\u00a0\u009e\u00e5\u0012\u0004\u00f5\u00d1:\b\u00ec\"P\u00eeB\u00ab\u00c9\u008e\u0010\u00e6S)\u00bd\u0016'Hx/p\u000f\u00a3\u00a6\u00d2Ul\b\u00e7\u00c5\"\u00ff\u00bb[\u00af\u00b2\u0018\u00e4\u00a1@\u00a5!\u00cb\b_X\\\u00c6I\u00dc\u0085F,\u009d\u00ac\\\u00f6EL\u00c2\f(\u00bd\u00b8,0\u00b8\u00f2S\u00ecH\u0019`+\u00e0\u0092\u0087\u00e5y\u00ae\u00c1\u00bb\u00c1\u00aa\u00d3\f/\u00fd\u00ede\u0096\u009d{\u000fb\u00be\u0006\u00b3\u00cbJ)+\u0010Q(6\u00a0\u00d9\u00de)W\u008d\u0092\u00f3\n\u00b03\u001d\u00aa\u0018H\u00dag\u00ba\u00a4ae\u0010R\u00ce{\u0000,\u00fc\u008a\u001d\u00a4B\u0091\u00bd\u0004\u00fd\u001e\u009e \u00c9\u0089\u0005\u00850\u00a3\u0015\u00c6y\u00af\u00bf\u00af\u00a1\u00e7\\\u00bbc\u00ef}\u0001\u009d]|q\u0089\u009e\u00c4\u00ca\u0090\u00de}|\u00105\u00a7\u0095\u00aa\u00db\u00b9\u0013\u00d9b\u00c0\u00b4\u008a\u000e\u001b\u000f$\u0018Y}\u00ab`\u00f94g\u000f\u008a\u00d0\u00ecD\u00155\u00b8\u0086\u00e5\u00a9\u00e9\u0097\u00d2\u00ef\u00d4\u0090\bn>\u0082J\u00d1\u00b53\u00ee\u00b0\u00c1;\u00d2bvzu\u00d0|\u00d4+\u008e\u00bcq\u0092\u00ea\u00d4_)\u0001f\u00d71UE \u00dbv\u00f6 \u009b\u001e\u00d5=\u00da\u009f\u0097c\u00e4/'D\u00d4\u00cd\u009cl\u0001}\u008d\u00bbjF\u0001$\u00c2\u00c7\u00ccJ\r[\u0007~o\u00d3\u0082m\u00d1\u00eb:\u0006\u00ae\u00b4\u008d\u00f9=q[\u0094\u009cTD\u0099i\u00e1\u00a4\u0089\u00ba\u00e3H.\u00c5\u00b2\u00f6\u00a4nna\u0097Sy\u008fc\u000f\u00fa@\u00c0/\u00d0^T\u00fe\u001c\u0002\u00ac\u00a8g\u00f4|+\u0080L>\u0016|\u00f1\u00ba\u00b2',\u00b4\u00be\u00dfJ\u0081\u00f9\u0006l\u0089\u0087\u00d6&\u00e0.\u00f4\u00cd8\u00d3\u00e9\u0015i\u000eY\u00cc4\n\u000b\u00b8\u00a3~\u0018>\u0080\u001f\u00c8h\u008b\u0089\u0014|K\u008aWz\u0086\u00f0`H\u00c1;\u00d2bvzu\u00d06-\u00ac\u00da\u0006\u00bc2\u00d4u5\u00bc\u00cb\u00e3\u001f\u00ac\u0088\u0016\u00f5V\u00b1~a\u00de\u00ad\u00ff:>\u00c8\u0002\u00f4\u00ab\u0015{\u000e\u001b\u0019\u00ef\u00ce\u00ef\u00d4\u0019\u00d4M\u00ee;-\u00a4\u00ae\u00d6}J\u0016\u00e7A\u00e2,\u008d\u000emFd\u00c8\u00c4\u00ca\b\b\u00e8}\u00fc2\u00bd\u0099\u00fe0B\u00a6Z\u00f5\u00a1\u00c4BC@\u00ffI\u00a3\u00b3\u009eP\u00c1\u00cc\u00db\u00ca\u0090\u00a7\u00e8\u0081h=\u00ad\u00b0\u008c\u00df\u00d4fP\u00b1A\u00b5\u00d3\u0007\u00f6\u0082\u00b4p\u00a9c*G\u00ffae\b\r\u00f1)M\u00bb&\u00c9a8\u00c1;\u00d2bvzu\u00d0\u0091u?E\u00ed|\u00cf\u00b8\u00dc\u008fl\u00a86\u00d0\u00bd\u00135\u0081\u00e2\u00e5\u0089S\u009c\u000f\u00b6\u00f0\u001d\u00f2E\u00d1\u00b5*\u00ebU\u00f7PP;\u00de9z\u0093p\u00f4\u00ef\u00e4\u0010d\u0010\u0097X\u00947\u00ac\u00ed\u00fe\u00b5u\u00eb\u0092\u00cc\u00da\u00ce\u0007\u00af(\u0086\u00f7H\u0014\u00c0\u0083g\u0084D\u00dd^\u00c5\u00c4V\nBGP%\u001fnyH\u0006\u0098\u0092i\u00f0\u00a3\u0019\u00dc\t\u0081C~\u00ac\u00c3\u00bf]\u00b8\b/n\u0082\u0096\u00c5\u0012\u00c8w\u0010\u00c4u\u00dcSa\u00c3$\u008b\u00ee\u001b\u0018?\u00ab\u009bQV\u0010\u001dE\u009b\u0089\u001b\u00b9\u00db\u0096g\u00f7}I\u0000\u00dc\u00bc\u009a\b\u00a7\u00ec\u00af\u009b\u00ed\u00a9\u00b4\u001f\u0010B\u00a6Z\u00f5\u00a1\u00c4BC\u0093\u001a\b9\u00f9\u0003gf\u0018\u00c0\u00aa\u00e4\r^\u00fa\u0015|\u00f7\u00ab\tP\"p\u0087wz\u00c7\u0097\u00d3T>o\u00a0\u0010%o\r^\u00a4\u0099\u0002\u00f6R\u008b\u00c8\u00e6\u0001-\u00f8\u00fd(\u00bd\u00fb\u0011l$\u00a4\\_T\u00a2\u0092x\u0086\u001f,Y\u00e0\u0015\u0003\b}\u0082\u00d4\u00d8\u00b6WoU\u008b\u009dn:s.\u00cd\u00bbx\u0004\u00be\t\b\u007f\u00cd\u00a2>tD\u00d2\u00a7\u0010\u008e\u00ca\u00ce+W_\u0082\u00c2\u00b2\u00c0\u0002\u00ef\u00ca\u0086v\u00e00\u00f4\u00a5\u0012zL?\u00bc\u0081\u00f8\u00d1\u0082\u0006\u00ccO\u0006\u00c9\u0085k\u00d5\u0085\u00ab%\u00c2\u00ecb?\u0089\u00ca\u00a1\u0015\u00af\u00efP\"d\u00c7\u009am~[\u00da\u00dbHN-1<\u0097(\u0094\u0018\u00c5s\u00d7#\u00a6\u00f6\u008b\n\u000e\u00cc\u00a6.U\u0084\u00a9\u00b2\u00d2\u009a\u0007\u0007\"\u00c4\u0005C\u00d5X>\u009f\u00b5|\u00d8\r\u00c7\u00b2\u009e\u00c1\u00e5\u0088\b\u00f46\u00a4\u00aeN8W\r\u0010\u0090<P\u00dc\u00ea2\u00e4nm1\u0098\u0097\u000e\u00c0\u00cd\u0083\b\u008b\u00cb\r\u00bd\u00d8\u0093\u00ea\u009b\bw\u00fe=\u0003|7S\u00bb\b\u008b\u00cb\r\u00bd\u00d8\u0093\u00ea\u009b\u0010\u0005\u00b2\u001f\u0006\u0083\u00e5\u0000;>:\u0095\u00f4\u00aa{7!(\u00bd\u00b8,0\u00b8\u00f2S\u00ecH\u0019`+\u00e0\u0092\u0087\u00e5y\u00ae\u00c1\u00bb\u00c1\u00aa\u00d3\f/\u00fd\u00ede\u0096\u009d{\u000fb\u00be\u0006\u00b3\u00cbJ)+\u0010\u00ef\u00f1\u000e\u00e2\\(\u00fa\u0092\u0093\u00c1hdG\u00f6\u0095l\u0018\u0086J~\u00c7\u0082n\u00915\u00a2n\u00cfL\u00db\u0081\u00ca'\u00d6\u00cb\u0096\u0084\u00a1\u00e9\bGH\u00c1;\u00d2bvzu\u00d06-\u00ac\u00da\u0006\u00bc2\u00d4u5\u00bc\u00cb\u00e3\u001f\u00ac\u0088\u0016\u00f5V\u00b1~a\u00de\u00ad\u00ff:>\u00c8\u0002\u00f4\u00ab\u0015{\u000e\u001b\u0019\u00ef\u00ce\u00ef\u00d4\u0019\u00d4M\u00ee;-\u00a4\u00ae\u00d6}J\u0016\u00e7A\u00e2,\u008d\u000emFd\u00c8\u00c4\u00ca0\u008c\u00ec\u00da\u00cc\u00de>\u00f1\u00f9\u0001  \u00acB\r\u0007\u00b60]\u00ac\u00a8\u00b7\u00cbE\u00e4\u00ed\u00d2m\u008c0\u00b6\u0085/\u00964\u00e6\u0086:\u00f93P\u008d(\u00d3\u00ff\u0092\u0019\u00e4\u00ce\b\u00a7\u00e1\u0004\u00eb\u00a2\u0094\u00f5a\u0018\u00b3\u0098\u00bd\u0096\u00e6!\u00a7\u00a8\u0013b!>\u00cc\u00a6\u00be'\u009b\u00d8i\u009bx4\u0089 \u0010?\u00db\u00e0\u0085w\fm=\u008d\u00d5L\u0095\t\u00c6i!\b\u00ce\u00c5 \u0005C(\u00d9}\u0010)T^!\u00ca\u0087\u00ea\u0081'%0iLr\u0002\u00d0\u0010\u00d21\rZsd\u00d4\u00b5\u00de\u00069\u00f4a\t_\u00ca\u0010Kc\u00d6\u001c\u0094U\u00f6\ny6\u0005\"~\u00f5\u00f6v\u0010\u00c7\u0094\r\u0086\u000e)=Y@\u00f2\u00c1\u00ebG\u0012Y\u00f3\u0088\u001bE\u00a4\u00ca\u008c\u0017q\u00d4D\u009e\u00ee}\u00d9\u009e\u0083\u00f0\u0015\u0090S\u00d4\u00eb\u00c3q\u0095\u00f3\u0089O\u000e\u00fc\n\r\u00b63\u00f1u\u00a6\u00c7s\u0013l\u00d3\u00d9-\u0016\n\u008a\u00cf\u0083\u00dbWog\u0096\u0006\u0014\u00bb\u00f0k\u00eb\u00caq\u00ce&\u00bb\u00ecoZ\u0094A%\u0007|\u0080\u00d2g,\u0091/p\u00eek\u00f9|\u00aa\u001e\u00b8\u00c3\u00dcK\u00cb\u00e2c\u00ecu\u00d3\u0080)\u0096B\u00aa\u0098c*\u0091q\u0090\u0019\u00d0\u00fd\u00e8k\u008dg\u00fb\u00c1I\u0083 \u00e3\u00a3\u001e\u00df\u00d1G\u00a5\u00d2\u00d0\u008dM\u00fd\u0091\n/\u00c9\u001a\u001e(0\u0096\u00c8q\u00e9\u00a6i\u00ca\u00d2\u00f4\u008d\u00c0\u00e6,\u00cdu\t\u0086\u00efPF\u00f3\u00faS\u0081\u001b\u00e4U\u00ea\u0084\u0017&m\u00d3_\u00d103\u0094\u00af(\u00c0\u00aa\u00e4\r^\u00fa\u0015|k\u00a4\u000b\t\u001e\u0094g\u00abc\u00c8*\u00cf\u00c1'sI\u00ceR\u0090\u00c6X\u00a9\u00ab\u0091\u00c7F\u00ffz\u00eeO\u00d2\u0099\u0018\u00c0\u00aa\u00e4\r^\u00fa\u0015|\u00f7\u00ab\tP\"p\u0087wz\u00c7\u0097\u00d3T>o\u00a0 \u00c8\u00bc\b\u009b\u00d0\u00965\u00fd%Ft\u00d5\u00ab\f\u009ae`\u00dd\u0016\u00c5V\u0015m\u00cf\u00c7_({3Q\u00e1+\u0010\u0015\u0011)p\u00c3\u00a4|\u00b7\u00c2\u00cd\u00adO63\nYP\u0080[\u00f8\u0097pU)R\u00cf\u00885\u00e9\u00bawO%\u008f\u0013U\u0016\u00aeax\u0011\u00f3\u0084\u00fb\u00d5x\u00f5\u00b7\u0083\u00a7\u00a4\u00f7,C\u00af\u008b\u00ed\u0006+c\u00f1\u00a9\u00ec\u0016\u0093\u00f6\u00f4<K\u00f4\t*\u0081\t]A\u0005\u00fa\u00f8j]\u00fc}\u00cb?\u00ac\u00dd\u00fa^\u0085'\u0092\u00a8!U\u00cb\u001b\b\u001e\u00d4Y)\u0095\u00e3\u00926\u0010\u00c89`\u00c7\u00d9\u00cd\u001cX\u00f2\u0080\u00bd\u00f3\u008f\u0000\u00c5a\b\u00e7\u00c5\"\u00ff\u00bb[\u00af\u00b2\u0010=\u0015\u00d3\u00d1)4<;\u0081JM\u00d0:\u00bao\n\u0010&rr\u0014\u00dd\u00a2]\u008c\u0094\u008d\u00cax\u00c2\u0007\u00d0\u0003\u0090\u00c1;\u00d2bvzu\u00d0G\u0010\u00ac\u0098\u00c5\u00ce\u0091G\u0093?\u00bf32\u00e4\u00990\u001d\u00f0u2\u0007\u00abP=\u00c3ap\u00d3\u00ad\u0099\u0012:CVs\u00a2=\u00a9\u0082\u00d2_p|\u00f95\u00ca\u009d\u007f<\u000f><\u00e2\u00c0\u00aa\n1U|\u00c1R\u00b5\u00f0\u00a6C\u00b604\u00d9\u001e\u00f3?]o\u0091\u0092\u00cepmDU\u00d4\u00b9\u0098<\u00a7\u00d3\u0088 \u001e\u0006e\u00abF\u00f0\u0010\u0012F\u00d8\u009eq\u00c0\u00bb\u00f7\u0007\u00c7<\u00db\u00ae\u0086\u00d0hzJk\u00eb\u00fe1\u00c7Al\u00d8\u0083/\u0099SL\u00c68]\u00d9\u00b9\u009c\u008eaV\u0010\u0093\u00a2\u0094\u00fd\u00e2\u009f\u009f\u0016\n\u0007v\u00bc\"\u00a2\u00b1\u000b\b\u00f46\u00a4\u00aeN8W\r\u0018\u00dd\u00e1\u00a0\u00eb\u00db\u0013\u00f6\u0007W'B\u00c9n\u00bc\u00b7\u00fc&\u0094E\u00d8\u00ac.\u008dR\u0018H\u00dag\u00ba\u00a4ae\u0010R\u00ce{\u0000,\u00fc\u008a\u001d\u00a4B\u0091\u00bd\u0004\u00fd\u001e\u009e\u0010\u00c89`\u00c7\u00d9\u00cd\u001cX\u00f2\u0080\u00bd\u00f3\u008f\u0000\u00c5a8\u00c1;\u00d2bvzu\u00d06-\u00ac\u00da\u0006\u00bc2\u00d4u5\u00bc\u00cb\u00e3\u001f\u00ac\u0088\u0016\u00f5V\u00b1~a\u00de\u00ad\"\u008e7\u00f9xw\u00e3\u0019\u00faqX\u008cV9\u00c0e\u008b\u0016OyvY\u00d0R(0\u0096\u00c8q\u00e9\u00a6i\u00ca\u00d2\u00f4\u008d\u00c0\u00e6,\u00cdu\t\u0086\u00efPF\u00f3\u00faS\u0081\u001b\u00e4U\u00ea\u0084\u0017&m\u00d3_\u00d103\u0094\u00af\u0010&rr\u0014\u00dd\u00a2]\u008c\u0094\u008d\u00cax\u00c2\u0007\u00d0\u0003P!\u00cao\u00c7\u00c1^aG\u00ee\u0019\u00f7\u0003\u00ab~\u0011\u0019\u0083\u00b1K\u00cb\u001f/\u00cdV\u0096=\u0006Y\u00f0\u00ed\u00fbQ\u0088*\u001e\u00fbQ\u00a0\u00da\u001b\u0019\u001c\u0095)\u0002\u0014^\u00d6\u0004&\u00cd\u00dfL\u00d0\u00a9\u00ccGu cs\u00a0S\u00c4w\u00f1\u00e6\u0094\u008e\u00b2\u00c9\u00bc\u00b9\u0099\u0093\u00a7\u00f4\u0003E\u0093\u0010r\u00d1\u0012\u0089I\u0000\u0092\u00b5[m\u009a4\"\u00e8\u00baN\u0010\u0015\u0011)p\u00c3\u00a4|\u00b7\u00c2\u00cd\u00adO63\nY(\u00b3\u0014z#\u00d3\u008eg\u00a4?f\u00cb\u00f6J~\u0006g\u00ddj\u001f\u00ab\u0085\u00e6\u0014\u0012\u00f3\u008a\u00f9\u0085\u0088\u00b9p\u0010\u00eaAk\u000e\u0016\u00b9_L\u0010\u00e8\u00bdi\u001e\u00ed\u00d1i\u00821F.\u00d4)z`\u00e6\u0018\u00f6\u00b5\u00b62\u008bC\"\u00e6\u00a4U\u008b\u00f0XT\u00db\u00ffW|C(M\u0097\u00d1A0!j\u00a7\u00c5\u00b7(\u00fc\"\u0099\u00b2\u009f\u00f0\u00d6\u001f\u00b0\u0080\u00c1Oj\u00f0p\u00f7\u00b0\u00ae\u001c\u00ca\u008a\u0013\u00101Z:v\u00d7\u00ees\u00c5\u00e5w\u00eeK\u00b5\u00f6\u0005\u00b5\u0081\u00c0y8\u00c1;\u00d2bvzu\u00d06-\u00ac\u00da\u0006\u00bc2\u00d4u5\u00bc\u00cb\u00e3\u001f\u00ac\u0088\u0016\u00f5V\u00b1~a\u00de\u00ad\"\u008e7\u00f9xw\u00e3\u0019\u00faqX\u008cV9\u00c0e\u008b\u0016OyvY\u00d0R\b\u00c9\u0090\u001cMe\u00e2$\u0001\u0010r\u00d1\u0012\u0089I\u0000\u0092\u00b5[m\u009a4\"\u00e8\u00baN\u0010\u0090<P\u00dc\u00ea2\u00e4n\u0006\u00fev\u00eb\u00f8\u00c7LE0\u00c1;\u00d2bvzu\u00d0aQb\u00cd6B2\u0095\u00b8\u00a0\u0015f6\u009f\u00ba\u00ab\u00bb\u0003\u00eb\u00d6Av\u0012\u0019\u00e5\u0099\u00c7\u00f0{\u0093\u00ce\u00de\u009b\u00fc\u00d7\u0088I\u009b,\u00f9";
      int var8 = "\u00c1;\u00d2bvzu\u00d0G\u0010\u00ac\u0098\u00c5\u00ce\u0091G\u0093?\u00bf32\u00e4\u00990Z\u00d7u\u00e7\u009b!\u00eda@\"'\u00cc\u0089\u001a\u0013\u00a2Y\u0094%\b\u00aej\u0097:\u0018\u00ac\u0085\u0013\u00ad\u0096\u00d8=\u009c?\u009afCU\u00ad\u0016\u00b8\u008bt5\u00e7\t\u00a5\u00f9}0\u008c\u00ec\u00da\u00cc\u00de>\u00f1\u00f9\u0001  \u00acB\r\u0007\u00b60]\u00ac\u00a8\u00b7\u00cbE\u00e4\u00ed\u00d2m\u008c0\u00b6\u0085/\u00964\u00e6\u0086:\u00f93P\u008d(\u00d3\u00ff\u0092\u0019\u00e4\u00ce0B\u00a6Z\u00f5\u00a1\u00c4BC@\u00ffI\u00a3\u00b3\u009eP\u00c1\u00cc\u00db\u00ca\u0090\u00a7\u00e8\u0081h=\u00ad\u00b0\u008c\u00df\u00d4fP\u00b1A\u00b5\u00d3\u0007\u00f6\u0082\u00b4p\u00a9c*G\u00ffae\u0010&\u0013\u008b\u007f\u001e\u00e1TL_H\u00a3\u0011\u00a2\u00c7|\u00d5\u0010\u00a9nM\u00c3\u0011crY\u007f\u00d9@s\u008f'\u0019\u00b7\u0010=\u0015\u00d3\u00d1)4<;\u0081JM\u00d0:\u00bao\n\b\r\u00f1)M\u00bb&\u00c9a0\u00f4\u00a5\u0012zL?\u00bc\u0081\u00f8\u00d1\u0082\u0006\u00ccO\u0006\u00c9\u0085k\u00d5\u0085\u00ab%\u00c2\u00ecb?\u0089\u00ca\u00a1\u0015\u00af\u00efP\"d\u00c7\u009am~[\u00da\u00dbHN-1<\u0097 9TV\u008b\u00c0\u00fd#\u00811\u00e6l\u0093\u00e2\u0083=]\t\u00fe\u0090h\u00bbu\u000b`\u008a^\u00a8\u009d\u00f7\u00acZ\u00f3\u0010.\u0016HXK8E\u0006\u0099\u00fd\u0083L\u0017\u00a8I-\b5GR\u00afyz\u00d9\u0016\u0010\u00c4u\u00dcSa\u00c3$\u008b\u00ee\u001b\u0018?\u00ab\u009bQVp\u009a\u00a7*x4\u0007\u00ab\u00e6(\u00cfYY\u00a5\u00b72\u00de\u00fc*O\u0099;\u0017\u00e8JX\u000eL\u00b8\u00c0\u0095\u00ed\u009b\u00f1\u0091\u00f3\u00f9\u0014\u0018\u00d0\u00eb\"\u000bD\u00faS\u000b\u008f\u00b6\u000e\u00b4\u009e\u0094\u001f\u00dc\u000f5T\u0013w\u001b\u0010\bcD\u00d2\u00c0\u00ad\u00ca@\u00f9B`\u00b7\u0093\u00b3\u0010:\n\u0016\u00bf&\u0090\u0017\u00b8\u000e,\u008fab\u008f\u0016Q5\u00e63\u00ec\u001b\u0086,\u00dc%\u008a\f\u00c3\u00af6\u0088)\u00ac7\u001d\u007f\b\u0013\u009f\u008d\u00d6\u00c2\u00074,\b\u00a0\u009e\u00e5\u0012\u0004\u00f5\u00d1:\b\u00ec\"P\u00eeB\u00ab\u00c9\u008e\u0010\u00e6S)\u00bd\u0016'Hx/p\u000f\u00a3\u00a6\u00d2Ul\b\u00e7\u00c5\"\u00ff\u00bb[\u00af\u00b2\u0018\u00e4\u00a1@\u00a5!\u00cb\b_X\\\u00c6I\u00dc\u0085F,\u009d\u00ac\\\u00f6EL\u00c2\f(\u00bd\u00b8,0\u00b8\u00f2S\u00ecH\u0019`+\u00e0\u0092\u0087\u00e5y\u00ae\u00c1\u00bb\u00c1\u00aa\u00d3\f/\u00fd\u00ede\u0096\u009d{\u000fb\u00be\u0006\u00b3\u00cbJ)+\u0010Q(6\u00a0\u00d9\u00de)W\u008d\u0092\u00f3\n\u00b03\u001d\u00aa\u0018H\u00dag\u00ba\u00a4ae\u0010R\u00ce{\u0000,\u00fc\u008a\u001d\u00a4B\u0091\u00bd\u0004\u00fd\u001e\u009e \u00c9\u0089\u0005\u00850\u00a3\u0015\u00c6y\u00af\u00bf\u00af\u00a1\u00e7\\\u00bbc\u00ef}\u0001\u009d]|q\u0089\u009e\u00c4\u00ca\u0090\u00de}|\u00105\u00a7\u0095\u00aa\u00db\u00b9\u0013\u00d9b\u00c0\u00b4\u008a\u000e\u001b\u000f$\u0018Y}\u00ab`\u00f94g\u000f\u008a\u00d0\u00ecD\u00155\u00b8\u0086\u00e5\u00a9\u00e9\u0097\u00d2\u00ef\u00d4\u0090\bn>\u0082J\u00d1\u00b53\u00ee\u00b0\u00c1;\u00d2bvzu\u00d0|\u00d4+\u008e\u00bcq\u0092\u00ea\u00d4_)\u0001f\u00d71UE \u00dbv\u00f6 \u009b\u001e\u00d5=\u00da\u009f\u0097c\u00e4/'D\u00d4\u00cd\u009cl\u0001}\u008d\u00bbjF\u0001$\u00c2\u00c7\u00ccJ\r[\u0007~o\u00d3\u0082m\u00d1\u00eb:\u0006\u00ae\u00b4\u008d\u00f9=q[\u0094\u009cTD\u0099i\u00e1\u00a4\u0089\u00ba\u00e3H.\u00c5\u00b2\u00f6\u00a4nna\u0097Sy\u008fc\u000f\u00fa@\u00c0/\u00d0^T\u00fe\u001c\u0002\u00ac\u00a8g\u00f4|+\u0080L>\u0016|\u00f1\u00ba\u00b2',\u00b4\u00be\u00dfJ\u0081\u00f9\u0006l\u0089\u0087\u00d6&\u00e0.\u00f4\u00cd8\u00d3\u00e9\u0015i\u000eY\u00cc4\n\u000b\u00b8\u00a3~\u0018>\u0080\u001f\u00c8h\u008b\u0089\u0014|K\u008aWz\u0086\u00f0`H\u00c1;\u00d2bvzu\u00d06-\u00ac\u00da\u0006\u00bc2\u00d4u5\u00bc\u00cb\u00e3\u001f\u00ac\u0088\u0016\u00f5V\u00b1~a\u00de\u00ad\u00ff:>\u00c8\u0002\u00f4\u00ab\u0015{\u000e\u001b\u0019\u00ef\u00ce\u00ef\u00d4\u0019\u00d4M\u00ee;-\u00a4\u00ae\u00d6}J\u0016\u00e7A\u00e2,\u008d\u000emFd\u00c8\u00c4\u00ca\b\b\u00e8}\u00fc2\u00bd\u0099\u00fe0B\u00a6Z\u00f5\u00a1\u00c4BC@\u00ffI\u00a3\u00b3\u009eP\u00c1\u00cc\u00db\u00ca\u0090\u00a7\u00e8\u0081h=\u00ad\u00b0\u008c\u00df\u00d4fP\u00b1A\u00b5\u00d3\u0007\u00f6\u0082\u00b4p\u00a9c*G\u00ffae\b\r\u00f1)M\u00bb&\u00c9a8\u00c1;\u00d2bvzu\u00d0\u0091u?E\u00ed|\u00cf\u00b8\u00dc\u008fl\u00a86\u00d0\u00bd\u00135\u0081\u00e2\u00e5\u0089S\u009c\u000f\u00b6\u00f0\u001d\u00f2E\u00d1\u00b5*\u00ebU\u00f7PP;\u00de9z\u0093p\u00f4\u00ef\u00e4\u0010d\u0010\u0097X\u00947\u00ac\u00ed\u00fe\u00b5u\u00eb\u0092\u00cc\u00da\u00ce\u0007\u00af(\u0086\u00f7H\u0014\u00c0\u0083g\u0084D\u00dd^\u00c5\u00c4V\nBGP%\u001fnyH\u0006\u0098\u0092i\u00f0\u00a3\u0019\u00dc\t\u0081C~\u00ac\u00c3\u00bf]\u00b8\b/n\u0082\u0096\u00c5\u0012\u00c8w\u0010\u00c4u\u00dcSa\u00c3$\u008b\u00ee\u001b\u0018?\u00ab\u009bQV\u0010\u001dE\u009b\u0089\u001b\u00b9\u00db\u0096g\u00f7}I\u0000\u00dc\u00bc\u009a\b\u00a7\u00ec\u00af\u009b\u00ed\u00a9\u00b4\u001f\u0010B\u00a6Z\u00f5\u00a1\u00c4BC\u0093\u001a\b9\u00f9\u0003gf\u0018\u00c0\u00aa\u00e4\r^\u00fa\u0015|\u00f7\u00ab\tP\"p\u0087wz\u00c7\u0097\u00d3T>o\u00a0\u0010%o\r^\u00a4\u0099\u0002\u00f6R\u008b\u00c8\u00e6\u0001-\u00f8\u00fd(\u00bd\u00fb\u0011l$\u00a4\\_T\u00a2\u0092x\u0086\u001f,Y\u00e0\u0015\u0003\b}\u0082\u00d4\u00d8\u00b6WoU\u008b\u009dn:s.\u00cd\u00bbx\u0004\u00be\t\b\u007f\u00cd\u00a2>tD\u00d2\u00a7\u0010\u008e\u00ca\u00ce+W_\u0082\u00c2\u00b2\u00c0\u0002\u00ef\u00ca\u0086v\u00e00\u00f4\u00a5\u0012zL?\u00bc\u0081\u00f8\u00d1\u0082\u0006\u00ccO\u0006\u00c9\u0085k\u00d5\u0085\u00ab%\u00c2\u00ecb?\u0089\u00ca\u00a1\u0015\u00af\u00efP\"d\u00c7\u009am~[\u00da\u00dbHN-1<\u0097(\u0094\u0018\u00c5s\u00d7#\u00a6\u00f6\u008b\n\u000e\u00cc\u00a6.U\u0084\u00a9\u00b2\u00d2\u009a\u0007\u0007\"\u00c4\u0005C\u00d5X>\u009f\u00b5|\u00d8\r\u00c7\u00b2\u009e\u00c1\u00e5\u0088\b\u00f46\u00a4\u00aeN8W\r\u0010\u0090<P\u00dc\u00ea2\u00e4nm1\u0098\u0097\u000e\u00c0\u00cd\u0083\b\u008b\u00cb\r\u00bd\u00d8\u0093\u00ea\u009b\bw\u00fe=\u0003|7S\u00bb\b\u008b\u00cb\r\u00bd\u00d8\u0093\u00ea\u009b\u0010\u0005\u00b2\u001f\u0006\u0083\u00e5\u0000;>:\u0095\u00f4\u00aa{7!(\u00bd\u00b8,0\u00b8\u00f2S\u00ecH\u0019`+\u00e0\u0092\u0087\u00e5y\u00ae\u00c1\u00bb\u00c1\u00aa\u00d3\f/\u00fd\u00ede\u0096\u009d{\u000fb\u00be\u0006\u00b3\u00cbJ)+\u0010\u00ef\u00f1\u000e\u00e2\\(\u00fa\u0092\u0093\u00c1hdG\u00f6\u0095l\u0018\u0086J~\u00c7\u0082n\u00915\u00a2n\u00cfL\u00db\u0081\u00ca'\u00d6\u00cb\u0096\u0084\u00a1\u00e9\bGH\u00c1;\u00d2bvzu\u00d06-\u00ac\u00da\u0006\u00bc2\u00d4u5\u00bc\u00cb\u00e3\u001f\u00ac\u0088\u0016\u00f5V\u00b1~a\u00de\u00ad\u00ff:>\u00c8\u0002\u00f4\u00ab\u0015{\u000e\u001b\u0019\u00ef\u00ce\u00ef\u00d4\u0019\u00d4M\u00ee;-\u00a4\u00ae\u00d6}J\u0016\u00e7A\u00e2,\u008d\u000emFd\u00c8\u00c4\u00ca0\u008c\u00ec\u00da\u00cc\u00de>\u00f1\u00f9\u0001  \u00acB\r\u0007\u00b60]\u00ac\u00a8\u00b7\u00cbE\u00e4\u00ed\u00d2m\u008c0\u00b6\u0085/\u00964\u00e6\u0086:\u00f93P\u008d(\u00d3\u00ff\u0092\u0019\u00e4\u00ce\b\u00a7\u00e1\u0004\u00eb\u00a2\u0094\u00f5a\u0018\u00b3\u0098\u00bd\u0096\u00e6!\u00a7\u00a8\u0013b!>\u00cc\u00a6\u00be'\u009b\u00d8i\u009bx4\u0089 \u0010?\u00db\u00e0\u0085w\fm=\u008d\u00d5L\u0095\t\u00c6i!\b\u00ce\u00c5 \u0005C(\u00d9}\u0010)T^!\u00ca\u0087\u00ea\u0081'%0iLr\u0002\u00d0\u0010\u00d21\rZsd\u00d4\u00b5\u00de\u00069\u00f4a\t_\u00ca\u0010Kc\u00d6\u001c\u0094U\u00f6\ny6\u0005\"~\u00f5\u00f6v\u0010\u00c7\u0094\r\u0086\u000e)=Y@\u00f2\u00c1\u00ebG\u0012Y\u00f3\u0088\u001bE\u00a4\u00ca\u008c\u0017q\u00d4D\u009e\u00ee}\u00d9\u009e\u0083\u00f0\u0015\u0090S\u00d4\u00eb\u00c3q\u0095\u00f3\u0089O\u000e\u00fc\n\r\u00b63\u00f1u\u00a6\u00c7s\u0013l\u00d3\u00d9-\u0016\n\u008a\u00cf\u0083\u00dbWog\u0096\u0006\u0014\u00bb\u00f0k\u00eb\u00caq\u00ce&\u00bb\u00ecoZ\u0094A%\u0007|\u0080\u00d2g,\u0091/p\u00eek\u00f9|\u00aa\u001e\u00b8\u00c3\u00dcK\u00cb\u00e2c\u00ecu\u00d3\u0080)\u0096B\u00aa\u0098c*\u0091q\u0090\u0019\u00d0\u00fd\u00e8k\u008dg\u00fb\u00c1I\u0083 \u00e3\u00a3\u001e\u00df\u00d1G\u00a5\u00d2\u00d0\u008dM\u00fd\u0091\n/\u00c9\u001a\u001e(0\u0096\u00c8q\u00e9\u00a6i\u00ca\u00d2\u00f4\u008d\u00c0\u00e6,\u00cdu\t\u0086\u00efPF\u00f3\u00faS\u0081\u001b\u00e4U\u00ea\u0084\u0017&m\u00d3_\u00d103\u0094\u00af(\u00c0\u00aa\u00e4\r^\u00fa\u0015|k\u00a4\u000b\t\u001e\u0094g\u00abc\u00c8*\u00cf\u00c1'sI\u00ceR\u0090\u00c6X\u00a9\u00ab\u0091\u00c7F\u00ffz\u00eeO\u00d2\u0099\u0018\u00c0\u00aa\u00e4\r^\u00fa\u0015|\u00f7\u00ab\tP\"p\u0087wz\u00c7\u0097\u00d3T>o\u00a0 \u00c8\u00bc\b\u009b\u00d0\u00965\u00fd%Ft\u00d5\u00ab\f\u009ae`\u00dd\u0016\u00c5V\u0015m\u00cf\u00c7_({3Q\u00e1+\u0010\u0015\u0011)p\u00c3\u00a4|\u00b7\u00c2\u00cd\u00adO63\nYP\u0080[\u00f8\u0097pU)R\u00cf\u00885\u00e9\u00bawO%\u008f\u0013U\u0016\u00aeax\u0011\u00f3\u0084\u00fb\u00d5x\u00f5\u00b7\u0083\u00a7\u00a4\u00f7,C\u00af\u008b\u00ed\u0006+c\u00f1\u00a9\u00ec\u0016\u0093\u00f6\u00f4<K\u00f4\t*\u0081\t]A\u0005\u00fa\u00f8j]\u00fc}\u00cb?\u00ac\u00dd\u00fa^\u0085'\u0092\u00a8!U\u00cb\u001b\b\u001e\u00d4Y)\u0095\u00e3\u00926\u0010\u00c89`\u00c7\u00d9\u00cd\u001cX\u00f2\u0080\u00bd\u00f3\u008f\u0000\u00c5a\b\u00e7\u00c5\"\u00ff\u00bb[\u00af\u00b2\u0010=\u0015\u00d3\u00d1)4<;\u0081JM\u00d0:\u00bao\n\u0010&rr\u0014\u00dd\u00a2]\u008c\u0094\u008d\u00cax\u00c2\u0007\u00d0\u0003\u0090\u00c1;\u00d2bvzu\u00d0G\u0010\u00ac\u0098\u00c5\u00ce\u0091G\u0093?\u00bf32\u00e4\u00990\u001d\u00f0u2\u0007\u00abP=\u00c3ap\u00d3\u00ad\u0099\u0012:CVs\u00a2=\u00a9\u0082\u00d2_p|\u00f95\u00ca\u009d\u007f<\u000f><\u00e2\u00c0\u00aa\n1U|\u00c1R\u00b5\u00f0\u00a6C\u00b604\u00d9\u001e\u00f3?]o\u0091\u0092\u00cepmDU\u00d4\u00b9\u0098<\u00a7\u00d3\u0088 \u001e\u0006e\u00abF\u00f0\u0010\u0012F\u00d8\u009eq\u00c0\u00bb\u00f7\u0007\u00c7<\u00db\u00ae\u0086\u00d0hzJk\u00eb\u00fe1\u00c7Al\u00d8\u0083/\u0099SL\u00c68]\u00d9\u00b9\u009c\u008eaV\u0010\u0093\u00a2\u0094\u00fd\u00e2\u009f\u009f\u0016\n\u0007v\u00bc\"\u00a2\u00b1\u000b\b\u00f46\u00a4\u00aeN8W\r\u0018\u00dd\u00e1\u00a0\u00eb\u00db\u0013\u00f6\u0007W'B\u00c9n\u00bc\u00b7\u00fc&\u0094E\u00d8\u00ac.\u008dR\u0018H\u00dag\u00ba\u00a4ae\u0010R\u00ce{\u0000,\u00fc\u008a\u001d\u00a4B\u0091\u00bd\u0004\u00fd\u001e\u009e\u0010\u00c89`\u00c7\u00d9\u00cd\u001cX\u00f2\u0080\u00bd\u00f3\u008f\u0000\u00c5a8\u00c1;\u00d2bvzu\u00d06-\u00ac\u00da\u0006\u00bc2\u00d4u5\u00bc\u00cb\u00e3\u001f\u00ac\u0088\u0016\u00f5V\u00b1~a\u00de\u00ad\"\u008e7\u00f9xw\u00e3\u0019\u00faqX\u008cV9\u00c0e\u008b\u0016OyvY\u00d0R(0\u0096\u00c8q\u00e9\u00a6i\u00ca\u00d2\u00f4\u008d\u00c0\u00e6,\u00cdu\t\u0086\u00efPF\u00f3\u00faS\u0081\u001b\u00e4U\u00ea\u0084\u0017&m\u00d3_\u00d103\u0094\u00af\u0010&rr\u0014\u00dd\u00a2]\u008c\u0094\u008d\u00cax\u00c2\u0007\u00d0\u0003P!\u00cao\u00c7\u00c1^aG\u00ee\u0019\u00f7\u0003\u00ab~\u0011\u0019\u0083\u00b1K\u00cb\u001f/\u00cdV\u0096=\u0006Y\u00f0\u00ed\u00fbQ\u0088*\u001e\u00fbQ\u00a0\u00da\u001b\u0019\u001c\u0095)\u0002\u0014^\u00d6\u0004&\u00cd\u00dfL\u00d0\u00a9\u00ccGu cs\u00a0S\u00c4w\u00f1\u00e6\u0094\u008e\u00b2\u00c9\u00bc\u00b9\u0099\u0093\u00a7\u00f4\u0003E\u0093\u0010r\u00d1\u0012\u0089I\u0000\u0092\u00b5[m\u009a4\"\u00e8\u00baN\u0010\u0015\u0011)p\u00c3\u00a4|\u00b7\u00c2\u00cd\u00adO63\nY(\u00b3\u0014z#\u00d3\u008eg\u00a4?f\u00cb\u00f6J~\u0006g\u00ddj\u001f\u00ab\u0085\u00e6\u0014\u0012\u00f3\u008a\u00f9\u0085\u0088\u00b9p\u0010\u00eaAk\u000e\u0016\u00b9_L\u0010\u00e8\u00bdi\u001e\u00ed\u00d1i\u00821F.\u00d4)z`\u00e6\u0018\u00f6\u00b5\u00b62\u008bC\"\u00e6\u00a4U\u008b\u00f0XT\u00db\u00ffW|C(M\u0097\u00d1A0!j\u00a7\u00c5\u00b7(\u00fc\"\u0099\u00b2\u009f\u00f0\u00d6\u001f\u00b0\u0080\u00c1Oj\u00f0p\u00f7\u00b0\u00ae\u001c\u00ca\u008a\u0013\u00101Z:v\u00d7\u00ees\u00c5\u00e5w\u00eeK\u00b5\u00f6\u0005\u00b5\u0081\u00c0y8\u00c1;\u00d2bvzu\u00d06-\u00ac\u00da\u0006\u00bc2\u00d4u5\u00bc\u00cb\u00e3\u001f\u00ac\u0088\u0016\u00f5V\u00b1~a\u00de\u00ad\"\u008e7\u00f9xw\u00e3\u0019\u00faqX\u008cV9\u00c0e\u008b\u0016OyvY\u00d0R\b\u00c9\u0090\u001cMe\u00e2$\u0001\u0010r\u00d1\u0012\u0089I\u0000\u0092\u00b5[m\u009a4\"\u00e8\u00baN\u0010\u0090<P\u00dc\u00ea2\u00e4n\u0006\u00fev\u00eb\u00f8\u00c7LE0\u00c1;\u00d2bvzu\u00d0aQb\u00cd6B2\u0095\u00b8\u00a0\u0015f6\u009f\u00ba\u00ab\u00bb\u0003\u00eb\u00d6Av\u0012\u0019\u00e5\u0099\u00c7\u00f0{\u0093\u00ce\u00de\u009b\u00fc\u00d7\u0088I\u009b,\u00f9"
         .length();
      char var5 = '0';
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
                     CLIENT_ID = STRINGS[20];
                     CLIENT_SECRET = var15[2];
                     AUTHENTICATOR = new MicrosoftAuthenticator();
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

                  var6 = "\u00e4\u00a1@\u00a5!\u00cb\b_X\\\u00c6I\u00dc\u0085F,\u009d\u00ac\\\u00f6EL\u00c2\f K\u00b7\u001f\u00d1\u00af6Hf\u00ee<OO\u009d\u0004\u00ca\u0003\u0014\u00a4\u0000r\u0011\u0006rH\u00d1[J\"9r\u00fc\u00f8";
                  var8 = "\u00e4\u00a1@\u00a5!\u00cb\b_X\\\u00c6I\u00dc\u0085F,\u009d\u00ac\\\u00f6EL\u00c2\f K\u00b7\u001f\u00d1\u00af6Hf\u00ee<OO\u009d\u0004\u00ca\u0003\u0014\u00a4\u0000r\u0011\u0006rH\u00d1[J\"9r\u00fc\u00f8"
                     .length();
                  var5 = 24;
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

   private static Exception identity(Exception exception) {
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
