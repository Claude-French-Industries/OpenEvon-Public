package club.evon.ui;
import club.evon.util.TextUtil;



public enum AccountAction {
   LOGIN,
   REMOVE;

   public final String displayName = TextUtil.toDisplayName(this.toString());
   private static final AccountAction[] VALUES_CACHE;

   private static AccountAction[] createValues() {
      return new AccountAction[]{LOGIN, REMOVE};
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      String[] var0 = new String[2];
      int var4 = 0;
      String var3 = "\u0007X>e\u0006\u0006\u0019R4c\u001eY";
      int var5 = "\u0007X>e\u0006\u0006\u0019R4c\u001eY".length();
      char var2 = 5;
      int var1 = -1;

      while (true) {
         char[] var10002;
         label39: {
            char[] var10003 = var3.substring(++var1, var1 + var2).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 22;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 22;
               var10006 = var6;
            } else {
               var13 = 22;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label39;
               }

               var10005 = var10003;
               var16 = 22;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 93;
                  case 1 -> 1;
                  case 2 -> 111;
                  case 3 -> 58;
                  case 4 -> 94;
                  case 5 -> 10;
                  default -> 119;
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
            VALUES_CACHE = createValues();
            break;
         }

         var2 = var3.charAt(var1);
      }
   }
}
