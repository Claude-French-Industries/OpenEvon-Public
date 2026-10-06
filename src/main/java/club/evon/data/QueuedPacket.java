package club.evon.data;
import net.minecraft.network.packet.Packet;




public final class QueuedPacket {
   private final Packet<?> M;
   private final long W;

   public Packet<?> getPacket() {
      return this.M;
   }

   public long getId() {
      return this.W;
   }

   public QueuedPacket(Packet<?> packet, long id) {
      this.M = packet;
      this.W = id;
   }
}
