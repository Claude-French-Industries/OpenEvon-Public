package club.evon;
import net.minecraft.client.network.ServerAddress;
import club.evon.api.ServerProfile;




public interface TargetFilterFactory {
   public ServerProfile createProfile(ServerAddress address);
}
