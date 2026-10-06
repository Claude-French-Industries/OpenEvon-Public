package club.evon.mixins;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.PlayerInput;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import club.evon.ClientContext;
import club.evon.event.EventBus;
import club.evon.event.MovementInputEvent;




@Mixin(KeyboardInput.class)
public final class KeyboardInputMixin extends Input {
   @Unique
   private MovementInputEvent moveInputEvent;

   private KeyboardInputMixin() {
   }

   @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z", ordinal = 4))
   private boolean hookMoveInputEventJump(KeyBinding keyBinding) {
      return this.moveInputEvent != null && this.moveInputEvent.isJump();
   }

   @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyboardInput;getMovementMultiplier(ZZ)F", ordinal = 0))
   private float hookMoveInputEventForward(boolean flag, boolean flag2) {
      return this.moveInputEvent == null ? 0.0F : this.moveInputEvent.getForward();
   }

   @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyboardInput;getMovementMultiplier(ZZ)F", ordinal = 1))
   private float hookMoveInputEventStrafe(boolean flag, boolean flag2) {
      return this.moveInputEvent == null ? 0.0F : this.moveInputEvent.getSideways();
   }

   @ModifyExpressionValue(method = "tick()V", at = @At(value = "NEW", target = "(ZZZZZZZ)Lnet/minecraft/util/PlayerInput;"))
   private PlayerInput modifyInput(PlayerInput player) {
      this.moveInputEvent = new MovementInputEvent(
         getMovementMultiplier(ClientContext.MINECRAFT_CLIENT.options.forwardKey.isPressed(), ClientContext.MINECRAFT_CLIENT.options.backKey.isPressed()),
         getMovementMultiplier(ClientContext.MINECRAFT_CLIENT.options.leftKey.isPressed(), ClientContext.MINECRAFT_CLIENT.options.rightKey.isPressed()),
         ClientContext.MINECRAFT_CLIENT.options.jumpKey.isPressed(),
         player.sneak()
      );
      EventBus.post(this.moveInputEvent);
      return new PlayerInput(
         this.moveInputEvent.getForward() > 0.0F,
         this.moveInputEvent.getForward() < 0.0F,
         this.moveInputEvent.getSideways() > 0.0F,
         this.moveInputEvent.getSideways() < 0.0F,
         this.moveInputEvent.isJump(),
         this.moveInputEvent.isSneak(),
         player.sprint()
      );
   }

   @Unique
   private static float getMovementMultiplier(boolean flag, boolean flag2) {
      if (flag == flag2) {
         return 0.0F;
      } else {
         return flag ? 1.0F : -1.0F;
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
