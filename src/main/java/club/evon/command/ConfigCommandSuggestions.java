package club.evon.command;
import java.util.ArrayList;




public class ConfigCommandSuggestions extends ArrayList<String> {
   public final ConfigCommand command;
   private static final String[] USAGE_LINES;

   public ConfigCommandSuggestions(ConfigCommand configCommand) {
      this.command = configCommand;
      String[] strings = USAGE_LINES;
      this.add(strings[1]);
      this.add(strings[0]);
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[2];
      int var3 = 0;
      String var2 = "h\u00066b;!-fY5e.<e4\u00004c+-+*\tvj2$.#\u0017g#h\u00066b;!-fY5c<,e5\u0004/ir:/+\n/ichv%\n7j4/t";
      int var4 = "h\u00066b;!-fY5e.<e4\u00004c+-+*\tvj2$.#\u0017g#h\u00066b;!-fY5c<,e5\u0004/ir:/+\n/ichv%\n7j4/t".length();
      char var1 = 31;
      int var0 = -1;

      while (true) {
         char[] var10002;
         label38: {
            char[] var10003 = var2.substring(++var0, var0 + var1).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 102;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 102;
               var10006 = var6;
            } else {
               var13 = 102;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label38;
               }

               var10005 = var10003;
               var16 = 102;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 32;
                  case 1 -> 3;
                  case 2 -> 63;
                  case 3 -> 106;
                  case 4 -> 59;
                  case 5 -> 46;
                  default -> 44;
               });
               var6++;
               if (var13 == 0) {
                  var10006 = var13;
                  var10005 = var10002;
                  var16 = var13;
               } else {
                  if (var8 <= var6) {
                     break;
                  }

                  var10005 = var10002;
                  var16 = var13;
                  var10006 = var6;
               }
            }
         }

         String var15 = new String(var10002).intern();
         byte var10 = -1;
         var5[var3++] = var15;
         if ((var0 += var1) >= var4) {
            USAGE_LINES = var5;
            break;
         }

         var1 = var2.charAt(var0);
      }
   }
}
