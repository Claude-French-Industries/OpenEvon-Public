package club.evon.module.impl.combat;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.DoubleSetting;



public final class Reach extends BaseModule {
   private final DoubleSetting entityInteractReach;
   private final DoubleSetting blockPlaceReach;
   private static final String[] STRINGS;

   public Reach() {
      super((STRINGS)[3], (STRINGS)[0], ModuleCategory.COMBAT);

      String[] strings = STRINGS;
      this.entityInteractReach = new DoubleSetting(strings[2], 3.0, 3.0, 6.0, 0.05);
      this.blockPlaceReach = new DoubleSetting(strings[1], 4.5, 4.5, 6.0, 0.05);
      this.addSettings(this.entityInteractReach, this.blockPlaceReach);
      }

   public double getEntityInteractReach() {
      return this.entityInteractReach.J();
   }

   public double getBlockPlaceReach() {
      return this.blockPlaceReach.J();
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[4];
      int var3 = 0;
      String var2 = "`\u000b\u000bS\u0002\u0007rB\u0017\u001e\\\u0006\t}J\r\u001eX\u0017\u00031W\n\u0005SE\u0006;P\u0017\u000bS\u0006\u0007\u0011a\u000f\u0005^\u000eB\u0002O\u0002\tXE07B\u0000\u0002";
      int var4 = var2.length();
      char var1 = '"';
      int var8 = -1;

      while (true) {
         String var10001 = var2.substring(++var8, var8 + var1);
         char[] var24 = var10001.toCharArray();

         for (int var6 = 0; var6 < var24.length; var6++) {
            var24[var6] = (char)(var24[var6] ^ 16 ^ switch (var6 % 7) {
               case 0 -> 51;
               case 1 -> 115;
               case 2 -> 122;
               case 3 -> 45;
               case 4 -> 117;
               case 5 -> 114;
               default -> 66;
            });
         }

         String var29 = new String(var24).intern();
         var5[var3++] = var29;
         if ((var8 += var1) >= var4) {
            break;
         }

         var1 = var2.charAt(var8);
      }

      var2 = "\u000eev<ys\u001a\u0002ev0\u007fkY?+P0liR\u0005\u0019nc6e";
      var4 = var2.length();
      var1 = 21;
      var8 = -1;

      while (true) {
         String var10001 = var2.substring(++var8, var8 + var1);
         char[] var24 = var10001.toCharArray();

         for (int var6 = 0; var6 < var24.length; var6++) {
            var24[var6] = (char)(var24[var6] ^ 120 ^ switch (var6 % 7) {
               case 0 -> 51;
               case 1 -> 115;
               case 2 -> 122;
               case 3 -> 45;
               case 4 -> 117;
               case 5 -> 114;
               default -> 66;
            });
         }

         String var29 = new String(var24).intern();
         var5[var3++] = var29;
         if ((var8 += var1) >= var4) {
            break;
         }

         var1 = var2.charAt(var8);
      }

      STRINGS = var5;
   }
}
