package club.evon.event;
import net.minecraft.network.packet.Packet;




public class OutgoingPacketEvent extends BaseEvent {
   private Packet<?> r;

   public Packet<?> getPacket() {
      return this.r;
   }

   public void setPacket(Packet<?> packet) {
      this.r = packet;
   }

   public OutgoingPacketEvent(Packet<?> packet) {
      this.r = packet;
   }
}
