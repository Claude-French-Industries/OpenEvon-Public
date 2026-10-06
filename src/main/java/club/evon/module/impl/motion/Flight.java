package club.evon.module.impl.motion;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.ModeSetting;



public final class Flight extends BaseModule {
   private final ModeSetting mode;
   private static final String[] STRINGS;

   public Flight() {
      super((STRINGS)[1], (STRINGS)[3], ModuleCategory.MOTION);

      String[] strings = STRINGS;
      this.mode = new ModeSetting(strings[2], new FlightVanillaMode(strings[0], this));
      this.addSettings(this.mode);
      }

   @Override
   public void onDisable() {
      super.onDisable();
   }

   @Override
   public String getSuffix() {
      return this.mode.getValue().getName();
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      char[] var20 = null;
      String[] var5 = new String[4];
      int var3 = 0;
      String var2 = ":Q\u0018OA\n\u0006\u0006*\\\u001fAE\u0012";
      int var4 = ":Q\u0018OA\n\u0006\u0006*\\\u001fAE\u0012".length();
      char var1 = 7;
      int var8 = -1;

      label52:
      while (true) {
         byte var10000 = 114;
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
                     case 0 -> 30;
                     case 1 -> 66;
                     case 2 -> 4;
                     case 3 -> 84;
                     case 4 -> 95;
                     case 5 -> 20;
                     default -> 21;
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
                     STRINGS = var5;
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

                  var2 = " ^\u0013B\u0017+]\u000e\u0007M\u0015\t\u0018_\u0013\u0007@\u000e\r\b\u0011\u0016\u0007N\u000e\u0014\t\u001f";
                  var4 = " ^\u0013B\u0017+]\u000e\u0007M\u0015\t\u0018_\u0013\u0007@\u000e\r\b\u0011\u0016\u0007N\u000e\u0014\t\u001f".length();
                  var1 = 4;
                  var8 = -1;
            }

            var10000 = 115;
            var10001 = var2.substring(++var8, var8 + var1);
            var10002 = 0;
         }
      }
   }
}
