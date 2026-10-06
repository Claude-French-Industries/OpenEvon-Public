package club.evon.api;
import net.hypixel.data.type.ServerType;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;
import org.jetbrains.annotations.Nullable;
import club.evon.data.HypixelLocation;




public final class HypixelLocationManager {
   private static final HypixelLocationManager INSTANCE = new HypixelLocationManager();
   private boolean initialized;
   @Nullable
   private HypixelLocation L;
   @Nullable
   private HypixelLocation previousLocation;

   public HypixelLocationManager() {
      HypixelModAPI instance = HypixelModAPI.getInstance();
      instance.subscribeToEventPacket(ClientboundLocationPacket.class);
      instance.createHandler(ClientboundLocationPacket.class, this::onLocationPacket);
   }

   public static HypixelLocationManager getInstance() {
      return INSTANCE;
   }

   @Nullable
   public HypixelLocation getCurrentLocation() {
      return this.L;
   }

   public void setCurrentLocation(@Nullable HypixelLocation currentLocation) {
      this.L = currentLocation;
   }

   @Nullable
   public HypixelLocation getPreviousLocation() {
      return this.previousLocation;
   }

   private void onLocationPacket(ClientboundLocationPacket packet) {
      this.previousLocation = this.L;
      this.L = new HypixelLocation(
         packet.getServerName(),
         (ServerType)packet.getServerType().orElse(null),
         (String)packet.getLobbyName().orElse(null),
         (String)packet.getMode().orElse(null),
         (String)packet.getMap().orElse(null)
      );
   }
}
