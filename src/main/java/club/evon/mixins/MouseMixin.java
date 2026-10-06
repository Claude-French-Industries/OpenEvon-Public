package club.evon.mixins;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Mouse;
import net.minecraft.client.input.MouseInput;
import net.minecraft.client.input.Scroller;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerInventory;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.data.SlotSwitchMode;
import club.evon.event.EventBus;
import club.evon.event.MouseButtonEvent;
import club.evon.event.MouseUpdateEvent;
import club.evon.manager.RotationManager;
import club.evon.manager.SilentSlotManager;




@Mixin(Mouse.class)
public final class MouseMixin {
   @Shadow
   private double cursorDeltaX;
   @Shadow
   private double cursorDeltaY;
   @Unique
   private MouseUpdateEvent event;
   @Unique
   private boolean unlockCursorRun;

   private MouseMixin() {
   }

   @Inject(method = "onMouseButton(JLnet/minecraft/client/input/MouseInput;I)V", at = @At("HEAD"))
   private void onMouseButton(long longValue, MouseInput mouseInput, int value, CallbackInfo callbackInfo) {
      if (value == 1) {
         if (mouseInput.button() == -1) {
            return;
         }

         EventBus.post(new MouseButtonEvent(mouseInput.button()));
      }
   }

   @Redirect(method = "onMouseScroll(JDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V"))
   private void setSelectedSlot(PlayerInventory player, int value, @Local int value2) {
      SilentSlotManager instance = SilentSlotManager.getInstance();
      if (instance.isActive()) {
         if (instance.getMode() != SlotSwitchMode.NONE) {
            instance.setRealSlot(Scroller.scrollCycling(value2, instance.getRealSlot(), PlayerInventory.getHotbarSize()));
         }
      } else {
         player.setSelectedSlot(value);
      }
   }

   @Redirect(method = "updateMouse(D)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;smoothCameraEnabled:Z", opcode = 180))
   private boolean redirectCheck_r2(GameOptions gameOptions, @Local(ordinal = 3) double doubleValue) {
      this.event = new MouseUpdateEvent(this.cursorDeltaX, this.cursorDeltaY, doubleValue, this.unlockCursorRun);
      EventBus.post(this.event);
      return gameOptions.smoothCameraEnabled && !this.event.isHandled();
   }

   @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Mouse;isCursorLocked()Z"))
   private boolean redirectTickCursorLock(Mouse mouse) {
      if (mouse.isCursorLocked()) {
         return true;
      } else if (RotationManager.getRotationController().shouldForceCursorLock()) {
         this.unlockCursorRun = true;
         return true;
      } else {
         return false;
      }
   }

   @Inject(method = "updateMouse(D)V", at = @At("TAIL"))
   private void updateMouseTail(double doubleValue, CallbackInfo callbackInfo) {
      RotationManager.getMouseRotationManager().endUpdate();
      this.unlockCursorRun = false;
      this.event = null;
   }

   @Redirect(method = "updateMouse(D)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"))
   private boolean redirectCheck(ClientPlayerEntity player) {
      return player.isUsingSpyglass() && (this.event == null || !this.event.isHandled());
   }

   @Redirect(method = "updateMouse(D)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;cursorDeltaX:D", opcode = 180))
   private double redirectCursorX(Mouse mouse) {
      return this.event == null ? 0.0 : this.event.getDeltaX();
   }

   @Redirect(method = "updateMouse(D)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;cursorDeltaY:D", opcode = 180))
   private double redirectCursorY(Mouse mouse) {
      return this.event == null ? 0.0 : this.event.getDeltaY();
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
