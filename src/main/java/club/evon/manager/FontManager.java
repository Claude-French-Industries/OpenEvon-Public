package club.evon.manager;
import java.util.HashMap;
import club.evon.util.render.NvgFont;




public final class FontManager {
   private final HashMap<String, NvgFont> fontCache = new HashMap<>();
   private static String flowGuard;
   private static final String[] STRINGS;

   public NvgFont getFont(String name) {
      return this.fontCache.computeIfAbsent(name, string -> {
         String[] strings = STRINGS;
         System.out.println(strings[0] + name);
         return new NvgFont(name, strings[1] + name + strings[2]);
      });
   }

   public static void setFlowGuard(String value) {
      flowGuard = value;
   }

   public static String getFlowGuard() {
      return flowGuard;
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[3];
      int var3 = 0;
      String var2 = "\u001dH\nZ&q\u000f(H\u0005U:\"\u0010!\\\r\\'\"\u001a!S\u001d\u0003c\u0012a\\\u001aJ&v\u000faX\u001fV--\u001a!S\u001d\u0016\u0004`I\u001d_";
      int var4 = "\u001dH\nZ&q\u000f(H\u0005U:\"\u0010!\\\r\\'\"\u001a!S\u001d\u0003c\u0012a\\\u001aJ&v\u000faX\u001fV--\u001a!S\u001d\u0016\u0004`I\u001d_"
         .length();
      setFlowGuard("C3UVI");
      char var1 = 26;
      int var0 = -1;

      label27: while (true) {
         char[] var10002;
         label38: {
            char[] var10003 = var2.substring(++var0, var0 + var1).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 77;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 77;
               var10006 = var6;
            } else {
               var13 = 77;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label38;
               }

               var10005 = var10003;
               var16 = 77;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 3;
                  case 1 -> 112;
                  case 2 -> 36;
                  case 3 -> 116;
                  case 4 -> 14;
                  case 5 -> 79;
                  default -> 49;
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
         var5[var3++] = var15;
          if ((var0 += var1) >= var4) {
             STRINGS = var5;
             break label27;
          }

         var1 = var2.charAt(var0);
      }
   }
}
