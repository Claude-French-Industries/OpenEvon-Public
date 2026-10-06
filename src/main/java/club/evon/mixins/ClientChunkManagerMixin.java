package club.evon.mixins;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.ChunkData.BlockEntityVisitor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;




@Mixin(ClientChunkManager.class)
public abstract class ClientChunkManagerMixin {
   @Inject(
      method = "loadChunkFromPacket(IILnet/minecraft/network/PacketByteBuf;Ljava/util/Map;Ljava/util/function/Consumer;)Lnet/minecraft/world/chunk/WorldChunk;",
      at = @At("HEAD")
   )
   private void onChunkLoad(
      int value, int value2, PacketByteBuf packet, Map<Type, long[]> map, Consumer<BlockEntityVisitor> entity, CallbackInfoReturnable<WorldChunk> world
   ) {
   }

   @Inject(method = "unload(Lnet/minecraft/util/math/ChunkPos;)V", at = @At("HEAD"))
   private void onChunkUnload(ChunkPos chunkPos, CallbackInfo callbackInfo) {
   }
}
