package club.evon.util;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;




public final class AesEncryption {
   private static final String[] STRINGS;

   public static String encrypt(String key, String data) throws Exception {
      byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
      String[] strings = STRINGS;
      SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, strings[3]);
      Cipher instance = Cipher.getInstance(strings[1]);
      instance.init(1, secretKeySpec);
      byte[] bytes2 = instance.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(bytes2);
   }

   public static String decrypt(String key, String encryptedData) throws Exception {
      byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
      String[] strings = STRINGS;
      SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, strings[0]);
      Cipher instance = Cipher.getInstance(strings[2]);
      instance.init(2, secretKeySpec);
      byte[] bytes2 = Base64.getDecoder().decode(encryptedData);
      byte[] bytes3 = instance.doFinal(bytes2);
      return new String(bytes3, StandardCharsets.UTF_8);
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[4];
      int var3 = 0;
      String var2 = "|Xy\u0014|Xy{-@f\u0012Ma\u0017;6t\\yN=\u0006d";
      int var4 = "|Xy\u0014|Xy{-@f\u0012Ma\u0017;6t\\yN=\u0006d".length();
      char var1 = 3;
      int var8 = -1;

      label52:
      while (true) {
         byte var10000 = 81;
         String var10001 = var2.substring(++var8, var8 + var1);
         byte var10002 = -1;
         char[] var20 = null;

         while (true) {
            label47: {
               char[] var24 = var10001.toCharArray();
               int var10004 = var24.length;
               int var6 = 0;
               byte var27 = var10000;
               var20 = var24;
               int var14 = var10004;
               byte var32;
               char[] var36;
               int var10006;
               if (var10004 <= 1) {
                  var36 = var24;
                  var32 = var10000;
                  var10006 = var6;
               } else {
                  var27 = var10000;
                  var14 = var10004;
                  if (var10004 <= var6) {
                     break label47;
                  }

                  var36 = var24;
                  var32 = var10000;
                  var10006 = var6;
               }

               while (true) {
                  var36[var10006] = (char)(var36[var10006] ^ var32 ^ switch (var6 % 7) {
                     case 0 -> 108;
                     case 1 -> 76;
                     case 2 -> 123;
                     case 3 -> 5;
                     case 4 -> 57;
                     case 5 -> 82;
                     default -> 117;
                  });
                  var6++;
                  if (var27 == 0) {
                     var10006 = var27;
                     var36 = var20;
                     var32 = var27;
                  } else {
                     if (var14 <= var6) {
                        break;
                     }

                     var36 = var20;
                     var32 = var27;
                     var10006 = var6;
                  }
               }
            }

            String var29 = new String(var20).intern();
            switch (var10002) {
               case 0:
                  var5[var3++] = var29;
                  if ((var8 += var1) >= var4) {
                     STRINGS = var5;
                     break label52;
                  }

                  var1 = var2.charAt(var8);
                  break;
               default:
                  var5[var3++] = var29;
                  if ((var8 += var1) < var4) {
                     var1 = var2.charAt(var8);
                     continue label52;
                  }

                  var2 = "UqPR\u0004iO;dH>\u0012\u001f]uPg\u0014/M\u0003UqP";
                  var4 = "UqPR\u0004iO;dH>\u0012\u001f]uPg\u0014/M\u0003UqP".length();
                  var1 = 20;
                  var8 = -1;
            }

            var10000 = 120;
            var10001 = var2.substring(++var8, var8 + var1);
            var10002 = 0;
         }
      }
   }
}
