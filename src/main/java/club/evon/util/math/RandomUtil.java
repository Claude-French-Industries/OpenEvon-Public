package club.evon.util.math;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.random.RandomGenerator;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.util.KeyedRandom;




public final class RandomUtil {
   public static final Random RANDOM;
   private static int[] stateArray;
   private static final long KEY = KeyedRandom.create(-8163310480882230063L, -6165268878804743527L, MethodHandles.lookup().lookupClass()).next(163558218700234L);
   private static final String DECIMAL_POINT_REGEX;

   public static float lerp(float start, float end, float delta) {
      return start + delta * (end - start);
   }

   public static double round(double value, int places) {
      return new BigDecimal(value).setScale(Math.max(places, 0), RoundingMode.HALF_UP).stripTrailingZeros().doubleValue();
   }

   public static int getDecimalPlaces(double value) {
      return String.valueOf(value).split(DECIMAL_POINT_REGEX)[1].length();
   }

   public static int randomInt(int min, int max) {
      long longValue = KEY ^ 43346566825383L;
      int[] values = getStateArray();
      int value = min;
      int value2 = max;
      if (values == null) {
         if (min == max) {
            return min;
         }

         value = min;
         value2 = RANDOM.nextInt(max - min);
      }

      return value + value2;
   }

   public static boolean chance(int percentChance) {
      long longValue = KEY ^ 109462847451597L;
      int[] values = getStateArray();
      int value = RANDOM.nextInt(100);
      if (values == null) {
         value = value < percentChance ? 1 : 0;
      }

      return value != 0;
   }

   public static int randomIntBelow(int bound) {
      return RANDOM.nextInt(bound);
   }

   public static float randomFloat(float min, float max, float rand) {
      long longValue = KEY ^ 111711724113388L;
      int[] values = getStateArray();
      float floatValue = min;
      float floatValue2 = max;
      if (values == null) {
         if (min == max) {
            return min;
         }

         floatValue = min;
         floatValue2 = (max - min) * rand;
      }

      return floatValue + floatValue2;
   }

   public static double randomDouble(double min, double max, double rand) {
      long longValue = KEY ^ 128972025664457L;
      int[] values = getStateArray();
      double doubleValue = min;
      double doubleValue2 = max;
      if (values == null) {
         if (min == max) {
            return min;
         }

         doubleValue = min;
         doubleValue2 = (max - min) * rand;
      }

      return doubleValue + doubleValue2;
   }

   public static double randomDouble(double min, double max) {
      return randomDouble(min, max, RANDOM.nextDouble());
   }

   public static float randomFloat(float min, float max) {
      return randomFloat(min, max, RANDOM.nextFloat());
   }

   public static int randomIntInRange(int min, int max) {
      long longValue = KEY ^ 27921173282102L;
      int[] values = getStateArray();
      int value = min;
      int value2 = max;
      if (values == null) {
         if (min > max) {
            int value3 = max;
            max = min;
            min = value3;
         }

         value = min;
         value2 = (max - min) * RandomGenerator.getDefault().nextInt();
      }

      return value + value2;
   }

   static {
      try {
      long var0 = KEY ^ 131474424509156L;
      setStateArray(null);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var4 = var2.doFinal("\u000fB&2\u0015\u0004\u00b9;".getBytes("ISO-8859-1"));
      String var5 = decodeUtf8(var4).intern();
      byte var10001 = -1;
      DECIMAL_POINT_REGEX = var5;
      RANDOM = new Random();
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

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
}
