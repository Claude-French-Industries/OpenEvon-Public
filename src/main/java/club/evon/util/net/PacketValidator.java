package club.evon.util.net;
import net.minecraft.network.packet.Packet;




public interface PacketValidator {
   public boolean shouldBlock(Packet<?> packet);
}
