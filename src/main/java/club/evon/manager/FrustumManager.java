package club.evon.manager;
import net.minecraft.client.render.Frustum;
import club.evon.ClientService;
import club.evon.event.EventBus;
import club.evon.event.JoinWorldEvent;
import club.evon.event.Subscribe;




public final class FrustumManager implements ClientService {
   private static final FrustumManager instance = new FrustumManager();
   private static Frustum X;

   public static void setFrustum(Frustum frustum) {
      X = frustum;
   }

   @Subscribe
   public void onJoinWorld(JoinWorldEvent event) {
      setFrustum(null);
   }

   public static Frustum getFrustum() {
      return X;
   }

   static {
      EventBus.register(instance);
   }
}
