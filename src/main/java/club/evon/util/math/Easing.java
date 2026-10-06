package club.evon.util.math;
import java.lang.invoke.MethodHandles;
import java.util.function.DoubleFunction;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.util.KeyedRandom;




public enum Easing {
   LINEAR(var0x -> -2.0 * Math.pow(var0x, 3.0) + 3.0 * Math.pow(var0x, 2.0)),
   DECELERATE(var0x -> -Math.pow(var0x, 2.0) + var0x * 2.0),
   IN_BACK(var0x -> Math.E * Math.pow(var0x, 3.0) - Math.E * Math.pow(var0x, 2.0) + Math.pow(var0x, 2.0)),
   IN_QUADRATIC(var0x -> Math.pow(var0x, 2.0)),
   OUT_QUADRATIC(var0x -> var0x * 2.0 - Math.pow(var0x, 2.0)),
   IN_OUT_QUADRATIC(var0x -> {
      int[] var4x = getStateArray();
      double var10000x = var0x;
      double var10001x = 0.5;
      if (var4x != null) {
         if (var0x < 0.5) {
            return 2.0 * Math.pow(var0x, 2.0);
         }

         var10000x = -1.0 + 4.0 * var0x;
         var10001x = 2.0 * Math.pow(var0x, 2.0);
      }

      return var10000x - var10001x;
   }),
   IN_EXPONENT(var0x -> {
      int[] var4x = getStateArray();
      double var10000x = var0x;
      double var10001x = 0.0;
      if (var4x != null) {
         if (var0x == 0.0) {
            return 0.0;
         }

         var10000x = 2.0;
         var10001x = 10.0 * var0x - 10.0;
      }

      return Math.pow(var10000x, var10001x);
   }),
   OUT_EXPONENT(var0x -> {
      int[] var4x = getStateArray();
      double var10000x = var0x;
      double var10001x = 1.0;
      if (var4x != null) {
         if (var0x == 1.0) {
            return 1.0;
         }

         var10000x = 1.0;
         var10001x = Math.pow(2.0, -10.0 * var0x);
      }

      return var10000x - var10001x;
   }),
   IN_OUT_EXPONENT(var0x -> {
      int[] var4x = getStateArray();
      double var7x;
      int var10000x = (var7x = var0x - 0.0) == 0.0 ? 0 : (var7x < 0.0 ? -1 : 1);
      if (var4x != null) {
         if (var10000x == 0) {
            return 0.0;
         }

         double var8x;
         var10000x = (var8x = var0x - 1.0) == 0.0 ? 0 : (var8x < 0.0 ? -1 : 1);
      }

      if (var4x != null) {
         if (var10000x == 0) {
            return 1.0;
         }

         if (var4x == null) {
            return var0x / 0.5;
         }

         double var9x;
         var10000x = (var9x = var0x - 0.5) == 0.0 ? 0 : (var9x < 0.0 ? -1 : 1);
      }

      return var10000x < 0 ? Math.pow(2.0, 20.0 * var0x - 10.0) / 2.0 : (2.0 - Math.pow(2.0, -20.0 * var0x + 10.0)) / 2.0;
   }),
   OUT_ELASTIC(var0x -> {
      int[] var4x = getStateArray();
      double var7x;
      int var10000x = (var7x = var0x - 0.0) == 0.0 ? 0 : (var7x < 0.0 ? -1 : 1);
      if (var4x != null) {
         if (var10000x == 0) {
            return 0.0;
         }

         if (var4x == null) {
            return var0x + 1.0;
         }

         double var8x;
         var10000x = (var8x = var0x - 1.0) == 0.0 ? 0 : (var8x < 0.0 ? -1 : 1);
      }

      return var10000x == 0 ? 1.0 : Math.pow(2.0, -10.0 * var0x) * Math.sin((var0x * 10.0 - 0.75) * (Math.PI * 2.0 / 3.0)) * 0.5 + 1.0;
   }),
   IN_ELASTIC(var0x -> {
      int[] var4x = getStateArray();
      double var7x;
      int var10000x = (var7x = var0x - 0.0) == 0.0 ? 0 : (var7x < 0.0 ? -1 : 1);
      if (var4x != null) {
         if (var10000x == 0) {
            return 0.0;
         }

         if (var4x == null) {
            return var0x * 1.0;
         }

         double var8x;
         var10000x = (var8x = var0x - 1.0) == 0.0 ? 0 : (var8x < 0.0 ? -1 : 1);
      }

      return var10000x == 0 ? 1.0 : -Math.pow(2.0, 10.0 * var0x - 10.0) * Math.sin((var0x * 10.0 - 10.75) * (Math.PI * 2) / 3.0);
   });

   private final DoubleFunction<Double> function;
   private static int[] stateArray;
   private static final long KEY = KeyedRandom.create(3768708869583401960L, -1717032343723295308L, MethodHandles.lookup().lookupClass()).next(201458224029828L);

   public DoubleFunction<Double> getFunction() {
      return this.function;
   }

   Easing(DoubleFunction<Double> doubleFunctionDouble) {
      this.function = doubleFunctionDouble;
   }

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      try {
      int[] var10000 = new int[4];
      long var1 = KEY ^ 69505734791985L;
      setStateArray(var10000);
      Cipher var3;
      Cipher var13 = var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var13.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var0 = new String[11];
      int var8 = 0;
      String var7 = "\u0004?\u000e\u00a0\u00ce\u00dd\u00fa\u00c4\u00d7\u00f9!h\u00e6\u000e\u00bd\u00d1\b\u00ea,\u008e\u001f`\u00d7\u00b8w\u00104\u009b\u00fbt\u00d3\u00a4\u0099'\u009d9h,\u00db\u0080\u0010P\u0010y\u00e4x\nV\u00a6\u00d1dgZ\u00ac\u00fee\u0011\u00f8\u00cf\u0010\f\u0093\u00eb\u00dc(\u0080\u0090\u00d7\u00d7\u0091\nDQ\u0003\u0086\u0019\u0010CO\u009d\u00a0L\r \u0015\u00d9\u00db\u0099\u0017\u008b\u00cb\u00e4&\b\u00cf*\u0018R\u0014\u0081\u0018\u00e1\u0010\u00e7\u00b4\u0003:\u00d1\u0095\u0010~\u0093\u00d0 \u00d5\u00ffIS\u0017\u0010\u0000]x\u0018\u00d6\u0010\u0005Q\u00b0\u00ef\u00a5*\u00df\u0007\u00019";
      int var9 = "\u0004?\u000e\u00a0\u00ce\u00dd\u00fa\u00c4\u00d7\u00f9!h\u00e6\u000e\u00bd\u00d1\b\u00ea,\u008e\u001f`\u00d7\u00b8w\u00104\u009b\u00fbt\u00d3\u00a4\u0099'\u009d9h,\u00db\u0080\u0010P\u0010y\u00e4x\nV\u00a6\u00d1dgZ\u00ac\u00fee\u0011\u00f8\u00cf\u0010\f\u0093\u00eb\u00dc(\u0080\u0090\u00d7\u00d7\u0091\nDQ\u0003\u0086\u0019\u0010CO\u009d\u00a0L\r \u0015\u00d9\u00db\u0099\u0017\u008b\u00cb\u00e4&\b\u00cf*\u0018R\u0014\u0081\u0018\u00e1\u0010\u00e7\u00b4\u0003:\u00d1\u0095\u0010~\u0093\u00d0 \u00d5\u00ffIS\u0017\u0010\u0000]x\u0018\u00d6\u0010\u0005Q\u00b0\u00ef\u00a5*\u00df\u0007\u00019"
         .length();
      char var6 = 16;
      int var12 = -1;

      label28:
      while (true) {
         String var14 = var7.substring(++var12, var12 + var6);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var3.doFinal(var14.getBytes("ISO-8859-1"));
            String var20 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var0[var8++] = var20;
                  if ((var12 += var6) >= var9) {
                     break label28;
                  }

                  var6 = var7.charAt(var12);
                  break;
               default:
                  var0[var8++] = var20;
                  if ((var12 += var6) < var9) {
                     var6 = var7.charAt(var12);
                     continue label28;
                  }

                  var7 = "\u0098\t$&\u0006c\u0000\u0094_\u00e6\u007f\u00c7qV\u00ff\u0002\u0018`\u00d0\u00c2\u00b4$\u00cfn\u008aZ\u0095\u00f6\u0019\u0099\u00f5\u008c\u00a8\u00ca\u0019\u00a2L\u00a9#\u0099\u00af";
                  var9 = "\u0098\t$&\u0006c\u0000\u0094_\u00e6\u007f\u00c7qV\u00ff\u0002\u0018`\u00d0\u00c2\u00b4$\u00cfn\u008aZ\u0095\u00f6\u0019\u0099\u00f5\u008c\u00a8\u00ca\u0019\u00a2L\u00a9#\u0099\u00af"
                     .length();
                  var6 = 16;
                  var12 = -1;
            }

            var14 = var7.substring(++var12, var12 + var6);
            var10001 = 0;
         }
      }
   
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}

   public static void setStateArray(int[] values) {
      stateArray = values;
   }

   public static int[] getStateArray() {
      return stateArray;
   }

   private static JSONException ignoreException(JSONException exception) {
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


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
