package club.evon.data;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;




public enum OreType {

   DIAMOND(new Color(47, 223, 223), Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE),

   GOLD(new Color(223, 214, 47), Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE),

   EMERALD(new Color(30, 181, 45), Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE),

   IRON(new Color(189, 139, 69), Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE),

   REDSTONE(new Color(223, 47, 47), Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE),

   LAPIS(new Color(47, 103, 223), Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE),

   COAL(new Color(23, 19, 19), Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE);

   private final Color color;
   private final List<Block> blocks;
   private static final OreType[] Q;

   OreType(Color color2, Block... values) {
      this.color = color2;
      this.blocks = Arrays.asList(values);
   }

   public Color getColor() {
      return this.color;
   }

   public List<Block> getBlocks() {
      return this.blocks;
   }

   private static OreType[] C() {
      return new OreType[]{DIAMOND, GOLD, EMERALD, IRON, REDSTONE, LAPIS, COAL};
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      String[] var0 = new String[7];
      char[] var20;
      int var4 = 0;
      String var3 = "\u001e:\"\u001a\u0005\u00154>\u0017\u0013\b\u000b0*\r\u0014\u000bw\u001c\u0007\u001c8+\f\u0001\b}\u0007\u001d</\u0013\u000f\n}";
      int var5 = "\u001e:\"\u001a\u0005\u00154>\u0017\u0013\b\u000b0*\r\u0014\u000bw\u001c\u0007\u001c8+\f\u0001\b}\u0007\u001d</\u0013\u000f\n}".length();
      char var2 = 4;
      int var8 = -1;

      label53:
      while (true) {
         byte var10000 = 21;
         String var10001 = var3.substring(++var8, var8 + var2);
         byte var10002 = -1;

         while (true) {
            label48: {
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
                     break label48;
                  }

                  var36 = var24;
                  var32 = var10000;
                  var10006 = var6;
               }

               while (true) {
                  var36[var10006] = (char)(var36[var10006] ^ var32 ^ switch (var6 % 7) {
                     case 0 -> 76;
                     case 1 -> 96;
                     case 2 -> 123;
                     case 3 -> 75;
                     case 4 -> 85;
                     case 5 -> 81;
                     default -> 44;
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
                  var0[var4++] = var29;
                  if ((var8 += var2) >= var5) {
                     
                     
                     
                     
                     
                     
                     
                     Q = C();
                     break label53;
                  }

                  var2 = var3.charAt(var8);
                  break;
               default:
                  var0[var4++] = var29;
                  if ((var8 += var2) < var5) {
                     var2 = var3.charAt(var8);
                     continue label53;
                  }

                  var3 = "#\u0014\u0012#\u0004)\t\u001c!";
                  var5 = "#\u0014\u0012#\u0004)\t\u001c!".length();
                  var2 = 4;
                  var8 = -1;
            }

            var10000 = 38;
            var10001 = var3.substring(++var8, var8 + var2);
            var10002 = 0;
         }
      }
   }
}
