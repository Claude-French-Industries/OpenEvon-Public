package club.evon.mixins;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import club.evon.ui.MainMenuScreen;
import club.evon.ui.ScreenManager;
import club.evon.util.render.RenderUtil;




@Mixin(TitleScreen.class)
public class TitleScreenMixin {
   @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;IIF)V", at = @At("HEAD"), cancellable = true)
   private void render(DrawContext drawContext, int value, int value2, float floatValue, CallbackInfo callbackInfo) {
      callbackInfo.cancel();
      if (ScreenManager.getCurrentScreen() == null) {
         ScreenManager.setCurrentScreen(MainMenuScreen.getInstance());
      }

      RenderUtil.beginFrame();
      ScreenManager.render(value, value2);
      RenderUtil.endFrame(true);
   }

   @Inject(method = "mouseClicked(Lnet/minecraft/client/gui/Click;Z)Z", at = @At("HEAD"), cancellable = true)
   private void mouseClicked(Click click, boolean flag, CallbackInfoReturnable<Boolean> callbackInfoReturnableBoolean) {
      callbackInfoReturnableBoolean.setReturnValue(false);
      callbackInfoReturnableBoolean.cancel();
      if (ScreenManager.getCurrentScreen() != null) {
         ScreenManager.onMouseClick(click.x(), click.y(), click.button());
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
