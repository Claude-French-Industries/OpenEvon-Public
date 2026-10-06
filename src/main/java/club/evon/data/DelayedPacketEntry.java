package club.evon.data;
import net.minecraft.network.packet.Packet;




public final class DelayedPacketEntry {
   private final Packet<?> r;
   private final long timestamp;

   public DelayedPacketEntry(Packet<?> packet) {
      this.r = packet;
      this.timestamp = System.currentTimeMillis();
   }

   public Packet<?> getPacket() {
      return this.r;
   }

   public long getTimestamp() {
      return this.timestamp;
   }
}
