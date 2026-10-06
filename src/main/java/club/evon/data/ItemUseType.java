package club.evon.data;


public enum ItemUseType {

   BLOCKABLE(),

   USEABLE(),

   BOW(),

   NONE();

   private static final ItemUseType[] Z;

   private static ItemUseType[] n() {
      return new ItemUseType[]{BLOCKABLE, USEABLE, BOW, NONE};
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      String[] var0 = new String[4];
      int var4 = 0;
      String var3 = "As{*\u0003Msb";
      int var5 = "As{*\u0003Msb".length();
      char var2 = 4;
      int var8 = -1;

      label53:
      while (true) {
         byte var10000 = 36;
         String var10001 = var3.substring(++var8, var8 + var2);
         byte var10002 = -1;
         char[] var20 = null;

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
                     case 0 -> 43;
                     case 1 -> 24;
                     case 2 -> 17;
                     case 3 -> 75;
                     case 4 -> 115;
                     case 5 -> 19;
                     default -> 26;
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
                     
                     
                     
                     
                     Z = n();
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

                  var3 = "'\u0012\rSh\u0006\u0006\t0\r\u0007Qa\u000b\u0001>\u0004";
                  var5 = "'\u0012\rSh\u0006\u0006\t0\r\u0007Qa\u000b\u0001>\u0004".length();
                  var2 = 7;
                  var8 = -1;
            }

            var10000 = 89;
            var10001 = var3.substring(++var8, var8 + var2);
            var10002 = 0;
         }
      }
   }
}
