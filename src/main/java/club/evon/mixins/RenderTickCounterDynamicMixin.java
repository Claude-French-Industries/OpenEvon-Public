package club.evon.mixins;
import net.minecraft.client.render.RenderTickCounter.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import club.evon.manager.TimerManager;




@Mixin(Dynamic.class)
public final class RenderTickCounterDynamicMixin {
   @Shadow
   private float dynamicDeltaTicks;

   @Inject(
      method = "beginRenderTick(J)I",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter$Dynamic;lastTimeMillis:J", opcode = 181)
   )
   private void onBeingRenderTick(long longValue, CallbackInfoReturnable<Integer> callbackInfoReturnableInteger) {
      this.dynamicDeltaTicks = this.dynamicDeltaTicks * TimerManager.getInstance().getTimer();
   }
}
