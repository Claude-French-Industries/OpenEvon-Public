package club.evon.command;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public final class IrcChatCommand extends Command {
   public static boolean enabled;
   private static String state;
   private static final long STRING_SEED = KeyedRandom.create(2336031094235391668L, -4717139131064411366L, MethodHandles.lookup().lookupClass()).next(99622615881018L);
   private static final String[] STRINGS_TABLE;

   public IrcChatCommand() {
      super((STRINGS_TABLE)[4], (STRINGS_TABLE)[5], "");

      String[] strings = STRINGS_TABLE;
      }

   @Override
   public boolean execute(String[] arguments) {
      String text;
      int value;
      long longValue = STRING_SEED ^ 62002507317945L;
      String state2 = getState();
      String toLowerCase = arguments[0].toLowerCase();
      text = state2;
      byte byteValue = -1;
      value = toLowerCase.hashCode();
      label51:
      if (text == null) {
         switch (value) {
            case 3551:
               value = toLowerCase.equals(STRINGS_TABLE[2]) ? 1 : 0;
               if (text != null) {
                  break label51;
               }

               if (value == 0) {
                  break;
               }

               byteValue = 0;
               if (text == null) {
                  break;
               }
            case 109935:
               value = toLowerCase.equals(STRINGS_TABLE[1]) ? 1 : 0;
               if (text != null) {
                  break label51;
               }

               if (value != 0) {
                  byteValue = 1;
               }
         }

         value = byteValue;
      }

      if (text == null) {
         switch (value) {
            case 0:
               enabled = true;
               ChatUtil.sendMessage(STRINGS_TABLE[0]);
               return true;
            case 1:
               enabled = false;
               ChatUtil.sendMessage(STRINGS_TABLE[3]);
               return true;
            default:
               value = 0;
         }
      }

      if (BaseModule.getStateArray() == null) {
         setState("YsQ2X");
      }

      return value != 0;
   }

   @Override
   public ArrayList<String> getSuggestions() {
      return new IrcChatCommandSuggestions(this);
   }

   static {
      try {
      long var0 = STRING_SEED ^ 1067756640664L;
      setState(null);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[6];
      int var7 = 0;
      String var6 = "\u00f2{\u00abo\u00d5\u0098\u0080\u0090\u008b]wn\u00e3\bp\u00ee\u0093\u00d6\u0090N\u00d2\u00b3\u008cF\bT\u00d2\u001a_@\u00c6c4\b\u00c3\u00denFSRG\u009b(R\u00aaA\u00f5\u0093\u00e0\nQ\u00d5\u0015%\u009a\u00e1\u00fec.\u00dd\u0004-X\u00df\u00f3bzCg\u00fd\u00eax\u00abb\u00ee\u00a5u\u00adR\u00f2:\u001c\u00d8";
      int var8 = "\u00f2{\u00abo\u00d5\u0098\u0080\u0090\u008b]wn\u00e3\bp\u00ee\u0093\u00d6\u0090N\u00d2\u00b3\u008cF\bT\u00d2\u001a_@\u00c6c4\b\u00c3\u00denFSRG\u009b(R\u00aaA\u00f5\u0093\u00e0\nQ\u00d5\u0015%\u009a\u00e1\u00fec.\u00dd\u0004-X\u00df\u00f3bzCg\u00fd\u00eax\u00abb\u00ee\u00a5u\u00adR\u00f2:\u001c\u00d8"
         .length();
      char var5 = 24;
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decryptString(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var19;
                  if ((var12 += var5) >= var8) {
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

                  var6 = "zb\u00be\u00c1\u00ed\u00ccG\u0094(\u00d9\u0080\u00e0\u00bcm\u0094Cn\u0089,\u0016\u001c\u00d6\u009bn\u0006\u00cb\u00e2<\u00f3.\u001e^ \u00e3\u00e5\u00c8\u00cc\u0096N,\u00fe\u00d4\u0019\u009f\u0098\u00c3\u00e0\u00ad\u0085";
                  var8 = "zb\u00be\u00c1\u00ed\u00ccG\u0094(\u00d9\u0080\u00e0\u00bcm\u0094Cn\u0089,\u0016\u001c\u00d6\u009bn\u0006\u00cb\u00e2<\u00f3.\u001e^ \u00e3\u00e5\u00c8\u00cc\u0096N,\u00fe\u00d4\u0019\u009f\u0098\u00c3\u00e0\u00ad\u0085"
                     .length();
                  var5 = '\b';
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      STRINGS_TABLE = var9;
      enabled = true;
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   public static void setState(String text) {
      state = text;
   }

   public static String getState() {
      return state;
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
