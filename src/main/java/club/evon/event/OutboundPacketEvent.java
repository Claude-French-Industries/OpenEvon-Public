package club.evon.event;
import net.minecraft.network.packet.Packet;




public final class OutboundPacketEvent extends BaseEvent {
   private final Packet<?> W;

   public Packet<?> getPacket() {
      return this.W;
   }

   public OutboundPacketEvent(Packet<?> packet) {
      this.W = packet;
   }
}
