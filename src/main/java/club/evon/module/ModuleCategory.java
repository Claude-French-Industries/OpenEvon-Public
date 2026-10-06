package club.evon.module;
import club.evon.util.TextUtil;



public enum ModuleCategory {

   COMBAT(),

   MOTION(),

   PLAYER(),

   RENDER(),

   MISC();

   private final String displayName = TextUtil.toDisplayName(this.name());
   private static String[] stateArray;

   @Override
   public final String toString() {
      return this.displayName;
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      String[] var0 = new String[5];
      char[] var20;
      int var4 = 0;
      String var3 = "xsqN*z\u0006vshE$`\u0004xuvD";
      int var5 = "xsqN*z\u0006vshE$`\u0004xuvD".length();
      setStateArray(null);
      char var2 = 6;
      int var8 = -1;

      label53:
      while (true) {
         byte var10000 = 66;
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
                     case 0 -> 119;
                     case 1 -> 126;
                     case 2 -> 103;
                     case 3 -> 69;
                     case 4 -> 39;
                     case 5 -> 118;
                     default -> 1;
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

                  var3 = "MSAi\nL\u0006OZNt\nL";
                  var5 = "MSAi\nL\u0006OZNt\nL".length();
                  var2 = 6;
                  var8 = -1;
            }

            var10000 = 104;
            var10001 = var3.substring(++var8, var8 + var2);
            var10002 = 0;
         }
      }
   }

   public static void setStateArray(String[] strings) {
      stateArray = strings;
   }

   public static String[] getStateArray() {
      return stateArray;
   }
}
