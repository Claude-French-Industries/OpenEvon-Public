package club.evon.mixins;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.ClientContext;
import club.evon.manager.RotationManager;




@Mixin(InventoryScreen.class)
public final class InventoryScreenMixin {
   private InventoryScreenMixin() {
   }

   @Inject(method = "drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIIIFFFLnet/minecraft/entity/LivingEntity;)V", at = @At("HEAD"))
   private static void hookDrawEntityHead(
      DrawContext drawContext, int value, int value2, int value3, int value4, int value5, float floatValue, float floatValue2, float floatValue3, LivingEntity entity, CallbackInfo callbackInfo
   ) {
      if (ClientContext.MINECRAFT_CLIENT.player != null && entity == ClientContext.MINECRAFT_CLIENT.player) {
         RotationManager.getMouseRotationManager().setUpdating(true);
      }
   }

   @Inject(method = "drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIIIFFFLnet/minecraft/entity/LivingEntity;)V", at = @At("TAIL"))
   private static void hookDrawEntityTail(
      DrawContext drawContext, int value, int value2, int value3, int value4, int value5, float floatValue, float floatValue2, float floatValue3, LivingEntity entity, CallbackInfo callbackInfo
   ) {
      if (ClientContext.MINECRAFT_CLIENT.player != null && entity == ClientContext.MINECRAFT_CLIENT.player) {
         RotationManager.getMouseRotationManager().setUpdating(false);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
