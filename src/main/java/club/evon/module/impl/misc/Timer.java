package club.evon.module.impl.misc;
import club.evon.event.ClientTickEvent;
import club.evon.event.Subscribe;
import club.evon.manager.TimerManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.DoubleSetting;



public final class Timer extends BaseModule {
   private final DoubleSetting speed;
   private static final String[] STRINGS;

   public Timer() {
      super((STRINGS)[2], (STRINGS)[1], ModuleCategory.MISC);

      String[] strings = STRINGS;
      this.speed = new DoubleSetting(strings[0], 1.0, 0.1, 10.0, 0.01);
      this.addSettings(this.speed);
      }

   @Override
   public void onDisable() {
      TimerManager.getInstance().setTimer(1.0F);
      super.onDisable();
   }

   @Subscribe
   private void onTick(ClientTickEvent event) {
      TimerManager.getInstance().setTimer(this.speed.J().floatValue());
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   static {
      String[] var5 = new String[3];
      int var3 = 0;
      String var2 = "\":\u0016\r\u001d\u001f\":\u0016\r\u001d\u007fS\u0001j\u001c\u001aY,J\u001e=S\f\u0016(HQ>\u001b\rY8G\u001c/]\u0005%#\u001e\r\u000b";
      int var4 = "\":\u0016\r\u001d\u001f\":\u0016\r\u001d\u007fS\u0001j\u001c\u001aY,J\u001e=S\f\u0016(HQ>\u001b\rY8G\u001c/]\u0005%#\u001e\r\u000b".length();
      char var1 = 5;
      int var0 = -1;

      label35:
      while (true) {
         char[] var10002;
         label38: {
            char[] var10003 = var2.substring(++var0, var0 + var1).toCharArray();
            int var10004 = var10003.length;
            int var6 = 0;
            byte var13 = 37;
            var10002 = var10003;
            int var8 = var10004;
            byte var16;
            char[] var10005;
            int var10006;
            if (var10004 <= 1) {
               var10005 = var10003;
               var16 = 37;
               var10006 = var6;
            } else {
               var13 = 37;
               var8 = var10004;
               if (var10004 <= var6) {
                  break label38;
               }

               var10005 = var10003;
               var16 = 37;
               var10006 = var6;
            }

            while (true) {
               var10005[var10006] = (char)(var10005[var10006] ^ var16 ^ switch (var6 % 7) {
                  case 0 -> 84;
                  case 1 -> 111;
                  case 2 -> 86;
                  case 3 -> 77;
                  case 4 -> 92;
                  case 5 -> 122;
                  default -> 3;
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
            break label35;
         }

         var1 = var2.charAt(var0);
      }
   }
}
