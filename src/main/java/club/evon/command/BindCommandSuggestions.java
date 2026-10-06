package club.evon.command;
import java.util.ArrayList;




public class BindCommandSuggestions extends ArrayList<String> {
   public final BindCommand command;
   private static final String[] USAGE_LINES;

   public BindCommandSuggestions(BindCommand bindCommand) {
      this.command = bindCommand;
      String[] strings = USAGE_LINES;
      this.add(strings[1]);
      this.add(strings[0]);
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[2];
      int var3 = 0;
      String var2 = "\u0017a`JL\u001b~ZolEZ\u0005\u0019\u0017a`JL\u001b~TlmQD^|\u0019?bAQ\u0014,Vml\u001a";
      int var4 = "\u0017a`JL\u001b~ZolEZ\u0005\u0019\u0017a`JL\u001b~TlmQD^|\u0019?bAQ\u0014,Vml\u001a".length();
      char var1 = '\r';
      int var0 = -1;

      while (true) {
         char[] var10002;
         label38: {
            char[] var10003 = var2.substring(++var0, var0 + var1).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 38;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 38;
               var10006 = var6;
            } else {
               var13 = 38;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label38;
               }

               var10005 = var10003;
               var16 = 38;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 31;
                  case 1 -> 37;
                  case 2 -> 47;
                  case 3 -> 2;
                  case 4 -> 14;
                  case 5 -> 29;
                  default -> 100;
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
