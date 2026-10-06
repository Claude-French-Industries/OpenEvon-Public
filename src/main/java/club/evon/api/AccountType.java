package club.evon.api;
import club.evon.util.TextUtil;
import club.evon.util.render.NvgImage;



public enum AccountType {

   MICROSOFT(),

   COOKIE(),

   TOKEN();

   public final String displayName;
   public final NvgImage icon;
   private static final AccountType[] VALUES;
   private static String m;
   private static final String[] STRINGS;

   AccountType() {
      String toString = this.toString();
      this.displayName = TextUtil.toDisplayName(toString);
      this.icon = new NvgImage(toString.toLowerCase() + ".png");
   }

   private static AccountType[] buildValues() {
      return new AccountType[]{MICROSOFT, COOKIE, TOKEN};
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      String[] var5 = new String[4];
      char[] var21 = null;
      int var3 = 0;
      String var2 = ".+LW\u0001*\u0005%6\u0006 -@N\u0007<";
      int var4 = ".+LW\u0001*\u0005%6\u0006 -@N\u0007<".length();
      char var1 = '\t';
      l(null);
      int var8 = -1;

      label52:
      while (true) {
         byte var10000 = 28;
         String var10001 = var2.substring(++var8, var8 + var1);
         byte var10002 = -1;

         while (true) {
            label47: {
               char[] var25 = var10001.toCharArray();
               int var10004 = var25.length;
               int var6 = 0;
               byte var28 = var10000;
               var21 = var25;
               int var15 = var10004;
               byte var33;
               char[] var37;
               int var10006;
               if (var10004 <= 1) {
                  var37 = var25;
                  var33 = var10000;
                  var10006 = var6;
               } else {
                  var28 = var10000;
                  var15 = var10004;
                  if (var10004 <= var6) {
                     break label47;
                  }

                  var37 = var25;
                  var33 = var10000;
                  var10006 = var6;
               }

               while (true) {
                  var37[var10006] = (char)(var37[var10006] ^ var33 ^ switch (var6 % 7) {
                     case 0 -> 127;
                     case 1 -> 126;
                     case 2 -> 19;
                     case 3 -> 25;
                     case 4 -> 82;
                     case 5 -> 101;
                     default -> 86;
                  });
                  var6++;
                  if (var28 == 0) {
                     var10006 = var28;
                     var37 = var21;
                     var33 = var28;
                  } else {
                     if (var15 <= var6) {
                        break;
                     }

                     var37 = var21;
                     var33 = var28;
                     var10006 = var6;
                  }
               }
            }

            String var30 = new String(var21).intern();
            switch (var10002) {
               case 0:
                  var5[var3++] = var30;
                  if ((var8 += var1) >= var4) {
                     STRINGS = var5;
                     String[] var9 = STRINGS;
                     
                     
                     
                     VALUES = buildValues();
                     break label52;
                  }

                  var1 = var2.charAt(var8);
                  break;
               default:
                  var5[var3++] = var30;
                  if ((var8 += var1) < var4) {
                     var1 = var2.charAt(var8);
                     continue label52;
                  }

                  var2 = "*u\u0006\u0005\u0005PJ#'g";
                  var4 = "*u\u0006\u0005\u0005PJ#'g".length();
                  var1 = 4;
                  var8 = -1;
            }

            var10000 = 123;
            var10001 = var2.substring(++var8, var8 + var1);
            var10002 = 0;
         }
      }
   }

   public static void l(String text) {
      m = text;
   }

   public static String v() {
      return m;
   }
}
