package club.evon.util.net;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.net.ssl.HttpsURLConnection;
import club.evon.util.IoUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.math.RandomUtil;




public final class HttpUtil {
   public static final String USER_AGENT;
   private static final long KEY = KeyedRandom.create(-5278129924419099280L, -8498773127296574475L, MethodHandles.lookup().lookupClass()).next(178849171802112L);
   private static final String[] STRINGS;

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static String post(String url, String body, boolean json) {
      long longValue = KEY ^ 33456945876552L;
      int[] values = RandomUtil.getStateArray();

      try {
         HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URI(url).toURL().openConnection();
         String[] strings = STRINGS;
         httpsURLConnection.addRequestProperty(strings[3], strings[5]);
         httpsURLConnection.setRequestMethod(strings[9]);
         httpsURLConnection.setDoOutput(true);
         byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
         httpsURLConnection.setFixedLengthStreamingMode(bytes.length);
         String tmp = strings[2];
         String tmp2;
         if (json) {
            tmp2 = strings[0];
         } else {
            strings = STRINGS;
            tmp2 = strings[10];
         }

         httpsURLConnection.addRequestProperty(tmp, tmp2);
         strings = STRINGS;
         httpsURLConnection.addRequestProperty(strings[4], strings[6]);
         httpsURLConnection.connect();
         OutputStream outputStream = httpsURLConnection.getOutputStream();

         try {
            outputStream.write(bytes);
         } catch (Throwable throwable) {
            OutputStream tmp3 = outputStream;
            if (values == null) {
               if (outputStream == null) {
                  throw throwable;
               }

               try {
                  tmp3 = outputStream;
               } catch (Throwable throwable2) {
                  throwable.addSuppressed(throwable2);
                  throw throwable;
               }
            }

            try {
               tmp3.close();
            } catch (Throwable throwable3) {
               throwable.addSuppressed(throwable3);
            }

            throw throwable;
         }

         label80: {
            OutputStream outputStream2 = outputStream;
            if (values == null) {
               if (outputStream == null) {
                  break label80;
               }

               outputStream2 = outputStream;
            }

            outputStream2.close();
         }

         InputStream inputStream;
         label74: {
            label73: {
               int value = httpsURLConnection.getResponseCode();
               int value2 = value;
               short shortValue = 200;
               if (values == null) {
                  if (value == 200) {
                     break label73;
                  }

                  value2 = value;
                  shortValue = 300;
               }

               if (value2 != shortValue) {
                  inputStream = httpsURLConnection.getErrorStream();
                  break label74;
               }
            }

            inputStream = httpsURLConnection.getInputStream();
         }

         InputStream inputStream2 = inputStream;
         inputStream = inputStream2;
         if (values == null) {
            if (inputStream2 == null) {
               return null;
            }

            inputStream = inputStream2;
         }

         return IoUtil.readString(inputStream);
      } catch (Exception exception) {
         exception.fillInStackTrace();
         return null;
      }
   }

   public static String get(String url) {
      long longValue = KEY ^ 84050397309112L;
      int[] values = RandomUtil.getStateArray();

      try {
         HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URI(url).toURL().openConnection();
         httpsURLConnection.addRequestProperty(STRINGS[1], STRINGS[7]);
         httpsURLConnection.setRequestMethod(STRINGS[8]);
         HttpsURLConnection tmp = httpsURLConnection;
         if (values == null) {
            if (httpsURLConnection.getResponseCode() != 200) {
               return null;
            }

            tmp = httpsURLConnection;
         }

         return IoUtil.readString(tmp.getInputStream());
      } catch (Exception exception) {
         exception.fillInStackTrace();
         return null;
      }
   }

   static {
      try {
      long var0 = KEY ^ 137921015280048L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[11];
      int var7 = 0;
      String var6 = "v\u001b%\u00bd.7\u001b\u00b8\u00adN\u00c600\u0082N\u0013$\u001b\u00e5\u00c6\u0005\u00ba\u00beV\u0010\u00f0\u00a1\u00fa\u0019&\u0011i\u00cd\u009elg<\b\u00195\u00bd\u0010\u00ac\u00de\u0087\u00a1\u00a1\u00b6Q\u0086\u00ac\u0006P\u00d6\u00f5\u00c1\u00efZ\u0010\u00f0\u00a1\u00fa\u0019&\u0011i\u00cd\u009elg<\b\u00195\u00bd\b\u00b3\u0014\u00a4\u001f\u00ef\u00e7\"3p\u00e8YuQ\u008e$:NJ\u00bbh\u00b3\u00d8i\r\u00f79\u00f4yk\u000f\u0097\u0088&\u00b5\u00d8B\u00f3\u008a\u00cd\u00117\u00f9\\\u00f0\u00f4\u00aen\u00142\u00a9\n|7\u00a7\u0086\u0019a\u0091\u00b7\u007f\t(\u00be\u001bM\u00eb\u00df\bl\u00934\u00fa9\u00bc'\u00ea=\u00d2|Y\u0015\u00b6\u008b\u00a2\u00a8\n\u00d6M\u00f8f\u00df\u00a8\u00c6\u000e`\u00b5X\u00b4\f\u008a\u00e2\u00ad\u00a6k?\b\u00daA\u00a6^(}\u0090\u00da\u00c4\u007f\u00c8!]o1\u0018v\u001b%\u00bd.7\u001b\u00b8\u00adN\u00c600\u0082N\u0013$\u001b\u00e5\u00c6\u0005\u00ba\u00beVp\u00e8YuQ\u008e$:NJ\u00bbh\u00b3\u00d8i\r\u00f79\u00f4yk\u000f\u0097\u0088&\u00b5\u00d8B\u00f3\u008a\u00cd\u00117\u00f9\\\u00f0\u00f4\u00aen\u00142\u00a9\n|7\u00a7\u0086\u0019a\u0091\u00b7\u007f\t(\u00be\u001bM\u00eb\u00df\bl\u00934\u00fa9\u00bc'\u00ea=\u00d2|Y\u0015\u00b6\u008b\u00a2\u00a8\n\u00d6M\u00f8f\u00df\u00a8\u00c6\u000e`\u00b5X\u00b4\f\u008a\u00e2\u00ad\u00a6k?\b\u00daA\u00a6^(}\u0090\u00da\u00c4\u007f\u00c8!]o1\b\u001f\u009f\u0097CIf\u00dc+";
      int var8 = "v\u001b%\u00bd.7\u001b\u00b8\u00adN\u00c600\u0082N\u0013$\u001b\u00e5\u00c6\u0005\u00ba\u00beV\u0010\u00f0\u00a1\u00fa\u0019&\u0011i\u00cd\u009elg<\b\u00195\u00bd\u0010\u00ac\u00de\u0087\u00a1\u00a1\u00b6Q\u0086\u00ac\u0006P\u00d6\u00f5\u00c1\u00efZ\u0010\u00f0\u00a1\u00fa\u0019&\u0011i\u00cd\u009elg<\b\u00195\u00bd\b\u00b3\u0014\u00a4\u001f\u00ef\u00e7\"3p\u00e8YuQ\u008e$:NJ\u00bbh\u00b3\u00d8i\r\u00f79\u00f4yk\u000f\u0097\u0088&\u00b5\u00d8B\u00f3\u008a\u00cd\u00117\u00f9\\\u00f0\u00f4\u00aen\u00142\u00a9\n|7\u00a7\u0086\u0019a\u0091\u00b7\u007f\t(\u00be\u001bM\u00eb\u00df\bl\u00934\u00fa9\u00bc'\u00ea=\u00d2|Y\u0015\u00b6\u008b\u00a2\u00a8\n\u00d6M\u00f8f\u00df\u00a8\u00c6\u000e`\u00b5X\u00b4\f\u008a\u00e2\u00ad\u00a6k?\b\u00daA\u00a6^(}\u0090\u00da\u00c4\u007f\u00c8!]o1\u0018v\u001b%\u00bd.7\u001b\u00b8\u00adN\u00c600\u0082N\u0013$\u001b\u00e5\u00c6\u0005\u00ba\u00beVp\u00e8YuQ\u008e$:NJ\u00bbh\u00b3\u00d8i\r\u00f79\u00f4yk\u000f\u0097\u0088&\u00b5\u00d8B\u00f3\u008a\u00cd\u00117\u00f9\\\u00f0\u00f4\u00aen\u00142\u00a9\n|7\u00a7\u0086\u0019a\u0091\u00b7\u007f\t(\u00be\u001bM\u00eb\u00df\bl\u00934\u00fa9\u00bc'\u00ea=\u00d2|Y\u0015\u00b6\u008b\u00a2\u00a8\n\u00d6M\u00f8f\u00df\u00a8\u00c6\u000e`\u00b5X\u00b4\f\u008a\u00e2\u00ad\u00a6k?\b\u00daA\u00a6^(}\u0090\u00da\u00c4\u007f\u00c8!]o1\b\u001f\u009f\u0097CIf\u00dc+"
         .length();
      char var5 = 24;
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var19;
                  if ((var12 += var5) >= var8) {
                     STRINGS = var9;
                     USER_AGENT = STRINGS[5];
                     break label27;
                  }

                  var5 = var6.charAt(var12);
                  break;
               default:
                  var9[var7++] = var19;
                  if ((var12 += var5) < var8) {
                     var5 = var6.charAt(var12);
                     continue label27;
                  }

                  var6 = "\u00c6\u0084\u008d@\u001bIA\u00b28v\u001b%\u00bd.7\u001b\u00b8\u00de\u00f8\u00c9],\u00c1\u00c1\u00ea\u0095i\u00f3\u001a\u00e7-Pp\u00fd\u0090\u00e9?\u00e8\u001a6N\u00c2q]0\u0018dpz>\u0091\u00cd\u0083\u00aa03I\u0096\u00df\u00b6p\u00d2\u00e9\u00a0:";
                  var8 = "\u00c6\u0084\u008d@\u001bIA\u00b28v\u001b%\u00bd.7\u001b\u00b8\u00de\u00f8\u00c9],\u00c1\u00c1\u00ea\u0095i\u00f3\u001a\u00e7-Pp\u00fd\u0090\u00e9?\u00e8\u001a6N\u00c2q]0\u0018dpz>\u0091\u00cd\u0083\u00aa03I\u0096\u00df\u00b6p\u00d2\u00e9\u00a0:"
                     .length();
                  var5 = '\b';
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

   private static Throwable ignoreException(Throwable exception) {
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
