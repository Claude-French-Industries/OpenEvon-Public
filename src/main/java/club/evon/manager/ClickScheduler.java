package club.evon.manager;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.network.ClientPlayerEntity;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.ClientService;
import club.evon.util.KeyedRandom;
import club.evon.util.Stopwatch;




public final class ClickScheduler implements ClientService {
   private final Stopwatch clickTimer = new Stopwatch();
   private static ClickScheduler instance;
   private static final long KEY = KeyedRandom.create(1335722151623709251L, -1380850701887777915L, MethodHandles.lookup().lookupClass()).next(135375555962373L);

   public boolean canClick(double cps, boolean modern) {
      return this.tryClick(cps, modern, false);
   }

   public boolean tryClick(double cps, boolean modern, boolean reset) {
      String text;
      ClientPlayerEntity player;
      label62: {
         long longValue = KEY ^ 24912071594557L;
         text = ClientState.getFlowGuard();
         boolean tmp = modern;
         if (text == null) {
            if (modern) {
               player = ClientContext.MINECRAFT_CLIENT.player;
               if (text != null) {
                  break label62;
               }

               if (ClientContext.MINECRAFT_CLIENT.player != null) {
                  player = ClientContext.MINECRAFT_CLIENT.player;
                  break label62;
               }
            }

            tmp = this.clickTimer.hasElapsed((long)(1000.0 / cps));
         }

         if (text == null) {
            if (tmp) {
               boolean flag = reset;
               if (text == null) {
                  if (reset) {
                     this.reset();
                  }

                  flag = true;
               }

               return flag;
            }

            tmp = false;
         }

         return tmp;
      }

      float floatValue;
      int value = (floatValue = player.getAttackCooldownProgress(0.0F) - 1.0F) == 0.0F ? 0 : (floatValue < 0.0F ? -1 : 1);
      if (text == null) {
         value = value >= 0 ? 1 : 0;
      }

      return value != 0;
   }

   public void reset() {
      this.clickTimer.reset();
   }

   public static void init() {
      instance = new ClickScheduler();
   }

   public static ClickScheduler getInstance() {
      return instance;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
