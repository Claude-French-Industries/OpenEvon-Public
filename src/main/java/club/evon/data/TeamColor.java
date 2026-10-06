package club.evon.data;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import club.evon.HypixelTargetFilter;
import club.evon.util.KeyedRandom;




public enum TeamColor {
   RED(28, 16733525),
   GREEN(19, 5635925),
   BLUE(25, 5592575),
   YELLOW(18, 16777045),
   AQUA(23, 5636095),
   WHITE(8, 16777215),
   PINK(20, 16733695),
   GRAY(21, 5592405);

   public final int mapColorId;
   public final int teamColor;
   private static final TeamColor[] VALUES;
   private static final long KEY = KeyedRandom.create(8092263940224330396L, 495071701885811159L, MethodHandles.lookup().lookupClass()).next(85785444087219L);

   @Nullable
   public static TeamColor fromTeamColor(int teamColor) {
      long longValue = KEY ^ 71137737300771L;
      TeamColor[] values = values();
      int value = HypixelTargetFilter.e();
      int length = values.length;
      int index = 0;
      int value2 = value;

      while (index < length) {
         TeamColor teamColor2 = values[index];
         if (value2 != 0) {
            if (teamColor2.teamColor == teamColor) {
               return teamColor2;
            }

            index++;
         }

         if (value2 == 0) {
            break;
         }
      }

      return null;
   }

   public int getMapColorId() {
      return this.mapColorId;
   }

   public int getTeamColor() {
      return this.teamColor;
   }

   TeamColor(int value, int value2) {
      this.mapColorId = value;
      this.teamColor = value2;
   }

   private static TeamColor[] buildValues() {
      return new TeamColor[]{RED, GREEN, BLUE, YELLOW, AQUA, WHITE, PINK, GRAY};
   }

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      try {
      long var20 = KEY ^ 11987414478684L;
      Cipher var12;
      Cipher var10000 = var12 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var20 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var13 = 1; var13 < 8; var13++) {
         var10003[var13] = (byte)(var20 << var13 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var11 = new String[8];
      int var17 = 0;
      String var16 = "`\u00a0\u00d1\u00ad|rZ$\b\u00f5\u00c5W'\u0091u\u0092\u00c7\bN\u0086\u00b3h\u001c\u008a\u00e3\u000e\b\u00e0B\u0093\u00b5\u00166Y\u0085\bw\u00ee\u00c5\u0010U\u00c9\u00d6\u0014\b0\u0018u% \u00c1\u00a8\u001a";
      int var18 = "`\u00a0\u00d1\u00ad|rZ$\b\u00f5\u00c5W'\u0091u\u0092\u00c7\bN\u0086\u00b3h\u001c\u008a\u00e3\u000e\b\u00e0B\u0093\u00b5\u00166Y\u0085\bw\u00ee\u00c5\u0010U\u00c9\u00d6\u0014\b0\u0018u% \u00c1\u00a8\u001a"
         .length();
      char var15 = '\b';
      int var24 = -1;

      label55:
      while (true) {
         String var25 = var16.substring(++var24, var24 + var15);
         int var10001 = -1;

         while (true) {
            byte[] var19 = var12.doFinal(var25.getBytes("ISO-8859-1"));
            String var36 = decodeUtf8(var19).intern();
            switch (var10001) {
               case 0:
                  var11[var17++] = var36;
                  if ((var24 += var15) >= var18) {
                     Cipher var1;
                     var10000 = var1 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var20 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var2 = 1; var2 < 8; var2++) {
                        var10003[var2] = (byte)(var20 << var2 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long[] var0 = new long[8];
                     int var4 = 0;
                     String var5 = "z\u0088i\u008c\u00ec2}\u00a3\u001d1W\u0086\u00c9\u00bfr\u0015\u00a4\u00cdR\u00904\u001fX\u009c\u00bc\u0011\u00c7XE\u0018\u00e9\u00adc\"|\u00e4\u00a9#\u00e9h;\u00d7m$\u0016\u00f7}V";
                     int var6 = "z\u0088i\u008c\u00ec2}\u00a3\u001d1W\u0086\u00c9\u00bfr\u0015\u00a4\u00cdR\u00904\u001fX\u009c\u00bc\u0011\u00c7XE\u0018\u00e9\u00adc\"|\u00e4\u00a9#\u00e9h;\u00d7m$\u0016\u00f7}V"
                        .length();
                     int var3 = 0;

                     label37:
                     while (true) {
                        var10001 = var3;
                        var3 += 8;
                        byte[] var7 = var5.substring(var10001, var3).getBytes("ISO-8859-1");
                        long[] var28 = var0;
                        var10001 = var4++;
                        long var40 = (var7[0] & 255L) << 56
                           | (var7[1] & 255L) << 48
                           | (var7[2] & 255L) << 40
                           | (var7[3] & 255L) << 32
                           | (var7[4] & 255L) << 24
                           | (var7[5] & 255L) << 16
                           | (var7[6] & 255L) << 8
                           | var7[7] & 255L;
                        byte var43 = -1;

                        while (true) {
                           long var8 = var40;
                           byte[] var10 = var1.doFinal(
                              new byte[]{
                                 (byte)(var8 >>> 56),
                                 (byte)(var8 >>> 48),
                                 (byte)(var8 >>> 40),
                                 (byte)(var8 >>> 32),
                                 (byte)(var8 >>> 24),
                                 (byte)(var8 >>> 16),
                                 (byte)(var8 >>> 8),
                                 (byte)var8
                              }
                           );
                           long var45 = (var10[0] & 255L) << 56
                              | (var10[1] & 255L) << 48
                              | (var10[2] & 255L) << 40
                              | (var10[3] & 255L) << 32
                              | (var10[4] & 255L) << 24
                              | (var10[5] & 255L) << 16
                              | (var10[6] & 255L) << 8
                              | var10[7] & 255L;
                           switch (var43) {
                              case 0:
                                 var28[var10001] = var45;
                                 if (var3 >= var6) {
                                    break label55;
                                 }
                                 break;
                              default:
                                 var28[var10001] = var45;
                                 if (var3 < var6) {
                                    continue label37;
                                 }

                                 var5 = "\u00e6oW\u009f\u0097\u00ee\u00e9\u00b5\"\u00ca.l\u00e0\u001b\u00e9\u0090";
                                 var6 = "\u00e6oW\u009f\u0097\u00ee\u00e9\u00b5\"\u00ca.l\u00e0\u001b\u00e9\u0090".length();
                                 var3 = 0;
                           }

                           int var34 = var3;
                           var3 += 8;
                           var7 = var5.substring(var34, var3).getBytes("ISO-8859-1");
                           var28 = var0;
                           var10001 = var4++;
                           var40 = (var7[0] & 255L) << 56
                              | (var7[1] & 255L) << 48
                              | (var7[2] & 255L) << 40
                              | (var7[3] & 255L) << 32
                              | (var7[4] & 255L) << 24
                              | (var7[5] & 255L) << 16
                              | (var7[6] & 255L) << 8
                              | var7[7] & 255L;
                           var43 = 0;
                        }
                     }
                  }

                  var15 = var16.charAt(var24);
                  break;
               default:
                  var11[var17++] = var36;
                  if ((var24 += var15) < var18) {
                     var15 = var16.charAt(var24);
                     continue label55;
                  }

                  var16 = "\r\u00dc\u0088v\u00c4\u00fd\u00ff{\b-\u00ef\u00ed0n\u00040\u00b9";
                  var18 = "\r\u00dc\u0088v\u00c4\u00fd\u00ff{\b-\u00ef\u00ed0n\u00040\u00b9".length();
                  var15 = '\b';
                  var24 = -1;
            }

            var25 = var16.substring(++var24, var24 + var15);
            var10001 = 0;
         }
      }
      VALUES = buildValues();
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException identity(JSONException exception) {
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
