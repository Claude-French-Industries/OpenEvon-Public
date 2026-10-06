package club.evon.module.impl.misc;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.ModeSetting;



public final class Disabler extends BaseModule {
   private final ModeSetting mode;
   private static final String[] STRINGS_TABLE;

   public Disabler() {
      super((STRINGS_TABLE)[0], (STRINGS_TABLE)[1], ModuleCategory.MISC);

      String[] strings = STRINGS_TABLE;
      this.mode = new ModeSetting(strings[4], new InventoryMode(strings[2], this), new CubecraftDisabler(strings[3], this), new Cubecraft2Disabler(strings[5], this));
      this.addSettings(this.mode);
      }

   @Override
   public String getSuffix() {
      return this.mode.getValue().getName();
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[6];
      int var3 = 0;
      String var2 = "QS\t-!V\\g\u001aQS\t-!V\\f\u001a\u001b\"7SZ}_\u001b8cYQpY\u0011?m\u0007]C\n%;_U\tVO\u0018) HXsN";
      int var4 = "QS\t-!V\\g\u001aQS\t-!V\\f\u001a\u001b\"7SZ}_\u001b8cYQpY\u0011?m\u0007]C\n%;_U\tVO\u0018) HXsN".length();
      char var1 = '\b';
      int var8 = -1;

      label52:
      while (true) {
         byte var10000 = 87;
         String var10001 = var2.substring(++var8, var8 + var1);
         byte var10002 = -1;
         char[] var20 = null;

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
                     case 0 -> 66;
                     case 1 -> 109;
                     case 2 -> 45;
                     case 3 -> 27;
                     case 4 -> 20;
                     case 5 -> 109;
                     default -> 110;
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

                  var2 = "p}6\u0001\u000b~g0\u0001\b`p[frV";
                  var4 = "p}6\u0001\u000b~g0\u0001\b`p[frV".length();
                  var1 = 4;
                  var8 = -1;
            }

            var10000 = 127;
            var10001 = var2.substring(++var8, var8 + var1);
            var10002 = 0;
         }
      }
   }
}
