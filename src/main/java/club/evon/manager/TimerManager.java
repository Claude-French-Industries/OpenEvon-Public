package club.evon.manager;
import club.evon.ClientService;



public class TimerManager implements ClientService {
   private float L = 1.0F;
   public static TimerManager instance;

   public static void init() {
      instance = new TimerManager();
   }

   public float getTimer() {
      return this.L;
   }

   public void setTimer(float timer) {
      this.L = timer;
   }

   public static TimerManager getInstance() {
      return instance;
   }
}
