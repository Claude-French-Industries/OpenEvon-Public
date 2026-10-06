package club.evon.event;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;




public final class ServerConnectEvent {
   private final ServerAddress R;
   private final ServerInfo E;

   public ServerAddress getAddress() {
      return this.R;
   }

   public ServerInfo getInfo() {
      return this.E;
   }

   public ServerConnectEvent(ServerAddress address, ServerInfo info) {
      this.R = address;
      this.E = info;
   }
}
