package club.evon.module.impl.combat;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.ModeSetting;



public final class CriticalHit extends BaseModule {
   private final ModeSetting mode;
   private static final String[] STRINGS;

   public CriticalHit() {
      super((STRINGS)[0], (STRINGS)[2], ModuleCategory.COMBAT);

      String[] strings = STRINGS;
      this.mode = new ModeSetting(strings[1], new CubecraftCriticalHit(strings[3], this));
      this.addSettings(this.mode);
      }

   @Override
   public String getSuffix() {
      return this.mode.getValue().getName();
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[4];
      char[] var20;
      int var3 = 0;
      String var2 = "S_KzV\u0006 |\rjgK\u0004]BFk";
      int var4 = "S_KzV\u0006 |\rjgK\u0004]BFk".length();
      char var1 = '\f';
      int var8 = -1;

      label52:
      while (true) {
         byte var10000 = 73;
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
                     case 0 -> 89;
                     case 1 -> 100;
                     case 2 -> 107;
                     case 3 -> 71;
                     case 4 -> 118;
                     case 5 -> 44;
                     default -> 8;
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

                  var2 = "\u0005\u0019\u00123NWb(\b\u001a<\u000fX0)\u0015\u0007,@\t\u0002\t\u0011:\rFq'\b";
                  var4 = "\u0005\u0019\u00123NWb(\b\u001a<\u000fX0)\u0015\u0007,@\t\u0002\t\u0011:\rFq'\b".length();
                  var1 = 19;
                  var8 = -1;
            }

            var10000 = 24;
            var10001 = var2.substring(++var8, var8 + var1);
            var10002 = 0;
         }
      }
   }
}
