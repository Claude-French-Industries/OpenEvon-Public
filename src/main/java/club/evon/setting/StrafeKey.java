package club.evon.setting;


public enum StrafeKey {
   SPACE,
   CTRL;

   private static final StrafeKey[] VALUES;

   private static StrafeKey[] buildValues() {
      return new StrafeKey[]{SPACE, CTRL};
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      String[] var0 = new String[2];
      int var4 = 0;
      String var3 = "<\u001e\u0016_^\u0004,\u001a\u0005P";
      int var5 = "<\u001e\u0016_^\u0004,\u001a\u0005P".length();
      char var2 = 5;
      int var1 = -1;

      while (true) {
         char[] var10002;
         label39: {
            char[] var10003 = var3.substring(++var1, var1 + var2).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 73;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 73;
               var10006 = var6;
            } else {
               var13 = 73;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label39;
               }

               var10005 = var10003;
               var16 = 73;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 38;
                  case 1 -> 7;
                  case 2 -> 30;
                  case 3 -> 85;
                  case 4 -> 82;
                  case 5 -> 119;
                  default -> 69;
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
         var0[var4++] = var15;
          if ((var1 += var2) >= var5) {
             VALUES = buildValues();
             break;
          }

         var2 = var3.charAt(var1);
      }
   }
}
