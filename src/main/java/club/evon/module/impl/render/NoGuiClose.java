package club.evon.module.impl.render;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;



public final class NoGuiClose extends BaseModule {
   private static final String[] STRINGS;

   public NoGuiClose() {
      super((STRINGS)[1], (STRINGS)[0], ModuleCategory.RENDER);

      String[] strings = STRINGS;
      }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[2];
      int var3 = 0;
      String var2 = "?E\u0005a2#\u0016OD\u0005e!(\u0010\u001c\u0017\u0006e8 B\f[\u000fd>#\u0005Op5^$c\f!X@P\"$B,[\u000fd2";
      int var4 = "?E\u0005a2#\u0016OD\u0005e!(\u0010\u001c\u0017\u0006e8 B\f[\u000fd>#\u0005Op5^$c\f!X@P\"$B,[\u000fd2".length();
      char var1 = '"';
      int var0 = -1;

      while (true) {
         char[] var10002;
         label38: {
            char[] var10003 = var2.substring(++var0, var0 + var1).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 7;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 7;
               var10006 = var6;
            } else {
               var13 = 7;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label38;
               }

               var10005 = var10003;
               var16 = 7;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 104;
                  case 1 -> 48;
                  case 2 -> 103;
                  case 3 -> 16;
                  case 4 -> 80;
                  case 5 -> 74;
                  default -> 101;
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
             STRINGS = var5;
             break;
          }

         var1 = var2.charAt(var0);
      }
   }
}
