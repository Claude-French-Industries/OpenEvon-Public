package club.evon.mixins;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import club.evon.data.CustomClickEvent;
import club.evon.ui.ScreenManager;




@Mixin(Screen.class)
public abstract class ScreenMixin {
   @Shadow
   public MinecraftClient client;

   @Inject(method = "keyPressed(Lnet/minecraft/client/input/KeyInput;)Z", at = @At("HEAD"))
   private void hookKeyPressed(KeyInput keyInput, CallbackInfoReturnable<Boolean> callbackInfoReturnableBoolean) {
      ScreenManager.onKeyPress(keyInput.getKeycode());
   }

   @Inject(method = "handleTextClick(Lnet/minecraft/text/Style;)Z", at = @At("HEAD"), cancellable = true)
   public void handleTextClick(Style style, CallbackInfoReturnable<Boolean> cir) {
      if (style != null && style.getClickEvent() instanceof CustomClickEvent event) {
         CustomClickEvent tmp = event;
         Runnable runnable;

         try {
            runnable = tmp.callback();
         } catch (Throwable throwable) {
            throw new MatchException(throwable.toString(), throwable);
         }

         Runnable runnable2 = runnable;
         Runnable runnable3 = runnable2;
         runnable3.run();
         cir.setReturnValue(true);
      }
   }
}
