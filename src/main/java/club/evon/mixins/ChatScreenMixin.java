package club.evon.mixins;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.ChatScreen;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import club.evon.manager.HudDragManager;




@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin implements ParentElement {
   @Inject(method = "mouseClicked(Lnet/minecraft/client/gui/Click;Z)Z", at = @At("HEAD"))
   private void hookMouseClicked(Click click, boolean flag, CallbackInfoReturnable<Boolean> callbackInfoReturnableBoolean) {
      HudDragManager.getInstance().startDrag(click);
   }

   public boolean mouseReleased(Click click) {
      if (click.button() == 0) {
         HudDragManager.getInstance().stopDragging();
      }

      return ParentElement.super.mouseReleased(click);
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
