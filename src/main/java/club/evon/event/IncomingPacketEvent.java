package club.evon.event;
import net.minecraft.network.packet.Packet;




public class IncomingPacketEvent extends BaseEvent {
   private Packet<?> u;

   public Packet<?> getPacket() {
      return this.u;
   }

   public void setPacket(Packet<?> packet) {
      this.u = packet;
   }

   public IncomingPacketEvent(Packet<?> packet) {
      this.u = packet;
   }
}
