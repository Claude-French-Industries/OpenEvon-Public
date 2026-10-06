package club.evon.data;


public enum BanStatus {

   UNBANNED(),

   SUSPICIOUS_BAN(),

   TEMPORARY_BAN(),

   PERMANENT_BAN(),

   UNKNOWN();

   private static final BanStatus[] n;

   private static BanStatus[] G() {
      return new BanStatus[]{UNBANNED, SUSPICIOUS_BAN, TEMPORARY_BAN, PERMANENT_BAN, UNKNOWN};
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      String[] var0 = new String[5];
      char[] var20;
      int var4 = 0;
      String var3 = "~\u0011g\u001bm2\u001ab\u0011g\u0014f0\u001d\r}\u0001f\u0006e?\u0016c\u0010k\te?\ry\u0001y\u001bk#\u0012\u007f\u001dk\te?";
      int var5 = "~\u0011g\u001bm2\u001ab\u0011g\u0014f0\u001d\r}\u0001f\u0006e?\u0016c\u0010k\te?\ry\u0001y\u001bk#\u0012\u007f\u001dk\te?".length();
      char var2 = 14;
      int var8 = -1;

      label53:
      while (true) {
         byte var10000 = 76;
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
                     case 0 -> 97;
                     case 1 -> 8;
                     case 2 -> 120;
                     case 3 -> 7;
                     case 4 -> 104;
                     case 5 -> 61;
                     default -> 31;
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
                     
                     
                     
                     
                     
                     n = G();
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

                  var3 = "8J6J*\u007fV)\u00078J?E+f]";
                  var5 = "8J6J*\u007fV)\u00078J?E+f]".length();
                  var2 = '\b';
                  var8 = -1;
            }

            var10000 = 12;
            var10001 = var3.substring(++var8, var8 + var2);
            var10002 = 0;
         }
      }
   }
}
