package club.evon.mixins;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;




@Mixin(GameOptions.class)
public class GameOptionsMixin {
   @Inject(method = "getMenuBackgroundBlurrinessValue()I", at = @At("HEAD"), cancellable = true)
   public void setMenuBackgroundBlurrinessValue(CallbackInfoReturnable<Integer> callbackInfo) {
      callbackInfo.setReturnValue(5);
   }
}
