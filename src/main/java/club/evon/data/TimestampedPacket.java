package club.evon.data;
import net.minecraft.network.packet.Packet;




public record TimestampedPacket(Packet packet, long timestamp) {
   public Packet<?> getPacket() {
      return this.packet;
   }
}
