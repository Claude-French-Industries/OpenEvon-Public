package club.evon.command;
import java.util.ArrayList;
import org.json.JSONException;
import club.evon.EvonClient;
import club.evon.config.ConfigManager;
import club.evon.data.PanicCommandSuggestions;
import club.evon.util.ChatUtil;




public final class PanicCommand extends Command {
   private static int state;
   private static final String[] STRINGS_TABLE;

   public PanicCommand() {
      super((STRINGS_TABLE)[2], (STRINGS_TABLE)[0], (STRINGS_TABLE)[4]);

      String[] strings = STRINGS_TABLE;
      }

   @Override
   public boolean execute(String[] arguments) {
      ConfigManager configManager = EvonClient.getInstance().getConfigManager();
      String[] strings = STRINGS_TABLE;
      configManager.saveConfig(strings[3]);
      EvonClient.getInstance().getModuleManager().getModules().forEach(module -> module.setEnabled(false));
      ChatUtil.sendMessage(strings[1]);
      return true;
   }

   @Override
   public ArrayList<String> getSuggestions() {
      return new PanicCommandSuggestions(this);
   }

   public static void setState(int value) {
      state = value;
   }

   public static int getState() {
      return state;
   }

   public static int getDefaultState() {
      int value = getState();
      return value == 0 ? 84 : 0;
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      char[] var20 = null;
      String[] var5 = new String[5];
      int var3 = 0;
      String var2 = "W4\u0015<\u001aJT`}\u00071\u0014\u0006\\|9\u00131\u001dU\u001fEW4\u0015<\u001aJTw}\u00071\u0014\u0006\\|9\u00131\u001dU\u001f3\u0004\t(\n\u0006Bv)\u00124\u0016AB3*\u0003/\u001d\u0006Br+\u00039XR^3)\u000e8XBTu<\u00131\f\u0006R|3\u00004\u001f\b\u0005c<\b4\u001b";
      int var4 = "W4\u0015<\u001aJT`}\u00071\u0014\u0006\\|9\u00131\u001dU\u001fEW4\u0015<\u001aJTw}\u00071\u0014\u0006\\|9\u00131\u001dU\u001f3\u0004\t(\n\u0006Bv)\u00124\u0016AB3*\u0003/\u001d\u0006Br+\u00039XR^3)\u000e8XBTu<\u00131\f\u0006R|3\u00004\u001f\b\u0005c<\b4\u001b"
         .length();
      char var1 = 21;
      int var8 = -1;
      setState(0);

      label52:
      while (true) {
         byte var10000 = 42;
         String var10001 = var2.substring(++var8, var8 + var1);
         byte var10002 = -1;

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
                     case 0 -> 57;
                     case 1 -> 119;
                     case 2 -> 76;
                     case 3 -> 119;
                     case 4 -> 82;
                     case 5 -> 12;
                     default -> 27;
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
                     STRINGS_TABLE = var5;
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

                  var2 = "0_g[j-\"\u0005\u0004[oS|";
                  var4 = "0_g[j-\"\u0005\u0004[oS|".length();
                  var1 = 7;
                  var8 = -1;
            }

            var10000 = 77;
            var10001 = var2.substring(++var8, var8 + var1);
            var10002 = 0;
         }
      }
   }
}
