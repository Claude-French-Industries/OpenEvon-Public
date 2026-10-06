package club.evon.api;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.HypixelTargetFilter;
import club.evon.event.ClientTickEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.util.KeyedRandom;




public class BrandDetector extends ServerProfile {
   private static final long KEY = KeyedRandom.create(9124823564794282674L, -267430632723924131L, MethodHandles.lookup().lookupClass()).next(42575701363386L);

   public BrandDetector(String name) {
      super(name);
   }

   @Subscribe
   public void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 112040117785180L;
      int value = HypixelTargetFilter.M();
      ClientPlayNetworkHandler networkHandler = ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
      if (value == 0) {
         if (networkHandler == null) {
            return;
         }

         networkHandler = ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
      }

      String brand = networkHandler.getBrand();
      if (brand != null && HypixelTargetFilter.HYPIXEL_BRAND_PATTERN.matcher(brand).matches()) {
         HypixelTargetFilter hypixelTargetFilter = new HypixelTargetFilter();
         hypixelTargetFilter.setBrandDetector(this);
         ClientState.getInstance().getServerDetector().setServerProfile(hypixelTargetFilter);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
