package club.evon.util;
import net.minecraft.network.packet.Packet;




public interface ClientConnectionAccessor {
   public void evon$channelReadSilent(Packet<?> packet);
}
