package club.evon.mixins;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.util.BipedEntityRenderStateAccessor;




@Mixin(BipedEntityRenderer.class)
public final class BipedEntityRendererMixin {
   @Inject(
      method = "updateBipedRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;FLnet/minecraft/client/item/ItemModelManager;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/state/ArmedEntityRenderState;updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/item/ItemModelManager;)V",
         shift = Shift.AFTER
      )
   )
   private static void updateEntityField(LivingEntity entity, BipedEntityRenderState entity2, float floatValue, ItemModelManager itemModelManager, CallbackInfo callbackInfo) {
      ((BipedEntityRenderStateAccessor)entity2).evon$setEntity(entity);
   }
}
