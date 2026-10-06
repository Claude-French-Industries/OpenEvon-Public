package club.evon.command;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.EvonClient;
import club.evon.module.BaseModule;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public final class UnbindCommand extends Command {
   private static final long STRING_SEED = KeyedRandom.create(4014946694970223715L, 7416656690003381369L, MethodHandles.lookup().lookupClass()).next(72888976638111L);
   private static final String[] STRINGS_TABLE;

   public UnbindCommand() {
      super((STRINGS_TABLE)[5], (STRINGS_TABLE)[2], (STRINGS_TABLE)[1]);

      String[] strings = STRINGS_TABLE;
      }

   @Override
   public boolean execute(String[] arguments) {
      int value;
      label26: {
         label25: {
            long longValue = STRING_SEED ^ 19586479340416L;
            int value2 = PanicCommand.getDefaultState();
            BaseModule moduleByName = EvonClient.getInstance().getModuleManager().getModuleByName(arguments[0]);
            value = value2;
            BaseModule module = moduleByName;
            if (value != 0) {
               if (moduleByName == null) {
                  break label25;
               }

               moduleByName.getBindSetting().setValue(-1);
               module = moduleByName;
            }

            ChatUtil.sendMessage(STRINGS_TABLE[0] + module.getName());
            if (value != 0) {
               break label26;
            }
         }

         ChatUtil.sendError(STRINGS_TABLE[3] + arguments[0] + STRINGS_TABLE[4]);
      }

      if (BaseModule.getStateArray() == null) {
         PanicCommand.setState(++value);
      }

      return true;
   }

   @Override
   public ArrayList<String> getSuggestions() {
      return new UnbindCommandSuggestions(this);
   }

   static {
      try {
      long var0 = STRING_SEED ^ 115030422276796L;
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
      String var6 = "\u00d6\u00cb\u00ce\u0006I\u0086\u0082f\u00b98\u00a8\u00ef\u00d2\u00d7\u0013p\u008d\u0002\u00b1\u00a4?y0\u00f2\u0010\u007f\u009d\u00a1rK\u00c2\u00fb\u00e8A\u0017B\u00d8\u00cd\u00bb\u00d4\u00cc \u0089\u000eiHK\u0016\u0098\u00b8\u00a6\u001az\u0012\u00dbS\u00cc\u00dc\u00d2\u000b\u00a7N\u00c0\u00db\u00c9\u0017?\u00b7\u00c3\u00f85Y\u00d2\u001d\b\u00f7-\u00c8\u00af\u00caT\u00e0Z";
      int var8 = "\u00d6\u00cb\u00ce\u0006I\u0086\u0082f\u00b98\u00a8\u00ef\u00d2\u00d7\u0013p\u008d\u0002\u00b1\u00a4?y0\u00f2\u0010\u007f\u009d\u00a1rK\u00c2\u00fb\u00e8A\u0017B\u00d8\u00cd\u00bb\u00d4\u00cc \u0089\u000eiHK\u0016\u0098\u00b8\u00a6\u001az\u0012\u00dbS\u00cc\u00dc\u00d2\u000b\u00a7N\u00c0\u00db\u00c9\u0017?\u00b7\u00c3\u00f85Y\u00d2\u001d\b\u00f7-\u00c8\u00af\u00caT\u00e0Z"
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
                     STRINGS_TABLE = var9;
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

                  var6 = "\u00a1[\u00918\u00fd)l3'\u00f2\u00cdwh\u00a2\u0095\u00ed\b\u00b9d\u00bd\u00d9V\u00fa\u00b9\u0007";
                  var8 = "\u00a1[\u00918\u00fd)l3'\u00f2\u00cdwh\u00a2\u0095\u00ed\b\u00b9d\u00bd\u00d9V\u00fa\u00b9\u0007".length();
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
