package club.evon.data;


public enum ChatMessageType {
   CHAT_MESSAGE,
   DISGUISED_CHAT_MESSAGE,
   GAME_MESSAGE;

   private static final ChatMessageType[] k;

   private static ChatMessageType[] G() {
      return new ChatMessageType[]{CHAT_MESSAGE, DISGUISED_CHAT_MESSAGE, GAME_MESSAGE};
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      String[] var0 = new String[3];
      int var4 = 0;
      String var3 = "\u0017/Q8\\2\u000b\u0003=]:F\u0016\u0014'O:V6\u001d\u0015*C>K>\u001a\u000f#Y.P>\t\u0015\f\u0013&])\\2\u000b\u0003=]:F";
      int var5 = "\u0017/Q8\\2\u000b\u0003=]:F\u0016\u0014'O:V6\u001d\u0015*C>K>\u001a\u000f#Y.P>\t\u0015\f\u0013&])\\2\u000b\u0003=]:F".length();
      char var2 = '\f';
      int var1 = -1;

      label25:
      while (true) {
         char[] var10002;
         label39: {
            char[] var10003 = var3.substring(++var1, var1 + var2).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 11;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 11;
               var10006 = var6;
            } else {
               var13 = 11;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label39;
               }

               var10005 = var10003;
               var16 = 11;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 91;
                  case 1 -> 101;
                  case 2 -> 23;
                  case 3 -> 118;
                  case 4 -> 8;
                  case 5 -> 116;
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
            k = G();
            break label25;
         }

         var2 = var3.charAt(var1);
      }
   }
}
