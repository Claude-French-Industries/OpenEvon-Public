package club.evon.mixins;
import java.util.Map;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;




@Mixin(ClientCommonNetworkHandler.class)
public interface ClientCommonNetworkHandlerAccessor {
   @Accessor("serverCookies")
   public Map<Identifier, byte[]> getServerCookies();
}
