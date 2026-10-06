package club.evon.mixins;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.util.math.BlockPos;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.event.ChunkOcclusionEvent;
import club.evon.event.EventBus;




@Mixin(ChunkOcclusionDataBuilder.class)
public abstract class ChunkOcclusionDataBuilderMixin {
   @Inject(method = "markClosed(Lnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"), cancellable = true)
   private void onMarkClosed(BlockPos pos, CallbackInfo callbackInfo) {
      ChunkOcclusionEvent chunkOcclusionEvent = new ChunkOcclusionEvent();
      EventBus.post(chunkOcclusionEvent);
      if (chunkOcclusionEvent.isCanceled()) {
         callbackInfo.cancel();
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
