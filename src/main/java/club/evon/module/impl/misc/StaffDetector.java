package club.evon.module.impl.misc;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;



public final class StaffDetector extends BaseModule {
   private final BooleanSetting panic;
   private static final String[] STRINGS;

   public StaffDetector() {
      super((STRINGS)[2], (STRINGS)[0], ModuleCategory.MISC);

      String[] strings = STRINGS;
      this.panic = new BooleanSetting(strings[1], false);
      this.addSettings(this.panic);
      }

   public boolean isPanic() {
      return this.panic.I();
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[3];
      int var3 = 0;
      String var2 = "\r\u001a>Dkx8i\f>@njk \u0011jXgy9i\u0018+Lm,$'_\u0002Xxe3,\u0013d\u0005\u0019\u001e$Hk\u000e\u001a\u000b+Gn,\u000f,\u000b/B|c9";
      int var4 = "\r\u001a>Dkx8i\f>@njk \u0011jXgy9i\u0018+Lm,$'_\u0002Xxe3,\u0013d\u0005\u0019\u001e$Hk\u000e\u001a\u000b+Gn,\u000f,\u000b/B|c9".length();
      char var1 = '&';
      int var0 = -1;

      labelX:
      while (true) {
         char[] var10002;
         label38: {
            char[] var10003 = var2.substring(++var0, var0 + var1).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 79;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 79;
               var10006 = var6;
            } else {
               var13 = 79;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label38;
               }

               var10005 = var10003;
               var16 = 79;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 6;
                  case 1 -> 48;
                  case 2 -> 5;
                  case 3 -> 110;
                  case 4 -> 71;
                  case 5 -> 67;
                  default -> 4;
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
            break labelX;
         }

         var1 = var2.charAt(var0);
      }
   }
}
