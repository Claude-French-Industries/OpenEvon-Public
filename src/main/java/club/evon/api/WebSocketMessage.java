package club.evon.api;
import com.google.gson.JsonObject;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public class WebSocketMessage {
   private final int E;
   private static boolean enabled;
   private static final long STRING_SEED = KeyedRandom.create(-1003818311562048336L, 1644579813301606455L, MethodHandles.lookup().lookupClass()).next(212988692095059L);
   private static final String[] STRINGS_TABLE;

   public WebSocketMessage(int id) {
      super();

      long longValue = STRING_SEED ^ 84001529466781L;
      boolean enabled2 = isEnabled();
      boolean flag = enabled2;
      this.E = id;
      if (BaseModule.getStateArray() == null) {
         setEnabled(!flag);
      }
      }

   public JsonObject toJson() {
      long longValue = STRING_SEED ^ 62787196909742L;
      JsonObject jsonObject = new JsonObject();
      boolean disabled = isDisabled();
      String[] strings = STRINGS_TABLE;
      jsonObject.addProperty(strings[1], this.E);
      jsonObject.addProperty(strings[0], System.currentTimeMillis());
      boolean flag = disabled;
      if (!flag) {
         BaseModule.setStateArray(new BaseModule[4]);
      }

      return jsonObject;
   }

   public static void setEnabled(boolean value) {
      enabled = value;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
   }

   static {
      try {
      long var0 = STRING_SEED ^ 95271832304406L;
      setEnabled(false);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[2];
      int var7 = 0;
      String var6 = "\u00c0f\u008c%h\u00d9'\u00f2\b\u00c91pX\u00bc\u0083F\u008f";
      int var8 = "\u00c0f\u008c%h\u00d9'\u00f2\b\u00c91pX\u00bc\u0083F\u008f".length();
      char var5 = '\b';
      int var4 = -1;

      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var13 = decryptString(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var13;
         if ((var4 += var5) >= var8) {
            STRINGS_TABLE = var9;
            break;
         }

         var5 = var6.charAt(var4);
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }

   private static String decryptString(byte[] data) {
      int index = 0;
      int value;
      char[] chars = new char[value = data.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & data[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = data[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
