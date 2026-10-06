package club.evon.mixins;
import net.minecraft.entity.player.PlayerInventory;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.event.EventBus;
import club.evon.event.SlotChangeEvent;




@Mixin(PlayerInventory.class)
public final class PlayerInventoryMixin {
   @Inject(
      method = "setSelectedSlot(I)V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I"),
      cancellable = true
   )
   private void hookSlotChangeMethod(int value, CallbackInfo callbackInfo) {
      SlotChangeEvent slotChangeEvent = new SlotChangeEvent(value);
      EventBus.post(slotChangeEvent);
      if (slotChangeEvent.isCanceled()) {
         callbackInfo.cancel();
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
