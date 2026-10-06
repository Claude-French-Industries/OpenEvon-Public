package club.evon.util.net;
import net.minecraft.network.packet.Packet;




public interface PacketTransformer {
   public Packet<?> transform(Packet<?> packet);
}
