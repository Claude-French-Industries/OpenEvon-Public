package club.evon.api;
import net.minecraft.entity.LivingEntity;
import club.evon.ClientService;
import club.evon.event.EventBus;




public abstract class ServerProfile implements ClientService {
   private BrandDetector brandDetector;
   private final String J;
   private static int[] stateArray;

   public boolean isValidTarget(LivingEntity livingEntity) {
      return true;
   }

   public final void publishEvent() {
      EventBus.register(this);
   }

   public final void dispose() {
      EventBus.unregister(this.brandDetector);
      EventBus.unregister(this);
   }

   public ServerProfile(String name) {
      this.J = name;
   }

   public void setBrandDetector(BrandDetector proxyServer) {
      this.brandDetector = proxyServer;
   }

   public String getName() {
      return this.J;
   }

   public static void setStateArray(int[] values) {
      stateArray = values;
   }

   public static int[] getStateArray() {
      return stateArray;
   }

   static {
      if (getStateArray() == null) {
         setStateArray(new int[1]);
      }
   }
}
