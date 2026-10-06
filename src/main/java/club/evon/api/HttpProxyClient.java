package club.evon.api;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;
import club.evon.util.net.HttpProxySelector;
import club.evon.util.net.ProxyAuthenticator;




public class HttpProxyClient {
   private final HttpClient httpClient;
   private static boolean enabled;
   private static final long KEY = KeyedRandom.create(3752942853629995391L, -3864147593648568278L, MethodHandles.lookup().lookupClass()).next(142355324843636L);
   private static final String[] STRINGS;
   private static final long CONNECT_TIMEOUT_SECONDS;

   public HttpProxyClient(String proxyHost, int proxyPort, String proxyUsername, String proxyPassword) {
      super();

      long longValue = KEY ^ 118645982320614L;
      boolean disabled = isDisabled();
      boolean flag = disabled;
      HttpProxySelector httpProxySelector = new HttpProxySelector(this, proxyHost, proxyPort);
      ProxyAuthenticator proxyAuthenticator = new ProxyAuthenticator(this, proxyUsername, proxyPassword);
      this.httpClient = HttpClient.newBuilder().proxy(httpProxySelector).authenticator(proxyAuthenticator).connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS)).build();
      if (flag) {
         BaseModule.setStateArray(new BaseModule[5]);
      }
      }

   public String get(String url) throws IOException, InterruptedException {
      HttpRequest build = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
      HttpResponse httpResponse = this.httpClient.send(build, BodyHandlers.ofString());
      return (String)httpResponse.body();
   }

   public String postJson(String url, String jsonBody, Map<String, String> headers) throws IOException, InterruptedException {
      boolean flag;
      Builder builder;
      label27: {
         long longValue = KEY ^ 40329564537344L;
         boolean enabled2 = isEnabled();
         Builder pOST = HttpRequest.newBuilder().uri(URI.create(url)).POST(BodyPublishers.ofString(jsonBody));
         String[] strings = STRINGS;
         builder = pOST.header(strings[1], strings[0]);
         flag = enabled2;
         Map<String, String> map = headers;
         if (flag) {
            if (headers == null) {
               break label27;
            }

            map = headers;
         }

         map.forEach(builder::header);
      }

      HttpRequest build = builder.build();
      HttpResponse httpResponse = this.httpClient.send(build, BodyHandlers.ofString());
      String text = (String)httpResponse.body();
      if (BaseModule.getStateArray() == null) {
         setEnabled(!flag);
      }

      return text;
   }

   public static void setEnabled(boolean flag) {
      enabled = flag;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
   }

   static {
      long var5 = KEY ^ 51692287383945L;
      setEnabled(true);

      try {
         Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[2];
      int var12 = 0;
      String var11 = "9+\u00f6>\u0083\u000fr\u008c[\u00114\u009e\u0098\u0098\u00d2\u00a6`\u001b\u0000\u009e\u0018\u0019\u0001\u00b7\u0010\u0095\u00ff?\u0012\u0004\u0082-/\u009c\u001a\u00a49\u00daI\u0018\u00db";
      int var13 = "9+\u00f6>\u0083\u000fr\u008c[\u00114\u009e\u0098\u0098\u00d2\u00a6`\u001b\u0000\u009e\u0018\u0019\u0001\u00b7\u0010\u0095\u00ff?\u0012\u0004\u0082-/\u009c\u001a\u00a49\u00daI\u0018\u00db"
         .length();
      char var10 = 24;
      int var9 = -1;

      label112:
      while (true) {
         byte[] var15 = var7.doFinal(var11.substring(++var9, var9 + var10).getBytes("ISO-8859-1"));
         String var20 = decodeUtf8(var15).intern();
         byte var10001 = -1;
         var14[var12++] = var20;
         if ((var9 += var10) >= var13) {
            STRINGS = var14;
            Cipher var0;
            var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
            var10002 = SecretKeyFactory.getInstance("DES");
            var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

            for (int var1 = 1; var1 < 8; var1++) {
               var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
            }

            var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long var2 = -5912371693972776889L;
            byte[] var4 = var0.doFinal(
               new byte[]{
                  (byte)(var2 >>> 56),
                  (byte)(var2 >>> 48),
                  (byte)(var2 >>> 40),
                  (byte)(var2 >>> 32),
                  (byte)(var2 >>> 24),
                  (byte)(var2 >>> 16),
                  (byte)(var2 >>> 8),
                  (byte)var2
               }
            );
            long var23 = (var4[0] & 255L) << 56
               | (var4[1] & 255L) << 48
               | (var4[2] & 255L) << 40
               | (var4[3] & 255L) << 32
               | (var4[4] & 255L) << 24
               | (var4[5] & 255L) << 16
               | (var4[6] & 255L) << 8
               | var4[7] & 255L;
            var10001 = -1;
            CONNECT_TIMEOUT_SECONDS = var23;
            break label112;
         }

         var10 = var11.charAt(var9);
      }
      } catch (Exception var24) {
         throw new ExceptionInInitializerError(var24);
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
