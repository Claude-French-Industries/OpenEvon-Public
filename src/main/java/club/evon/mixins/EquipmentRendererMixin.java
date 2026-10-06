package club.evon.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.Identifier;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import club.evon.EvonClient;
import club.evon.module.BaseModule;
import club.evon.module.impl.render.Animations;
import club.evon.util.render.BipedRenderStateHolder;




@Mixin(EquipmentRenderer.class)
public abstract class EquipmentRendererMixin {
   @WrapOperation(
      method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"
      )
   )
   private RenderLayer evon$useOldArmorDamageLayer(Identifier identifier, Operation<RenderLayer> operationRenderLayer) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      return ((BaseModule)moduleByClass).isEnabled() && moduleByClass.isOldArmorDamageTint() ? RenderLayer.getEntityCutoutNoCullZOffset(identifier) : (RenderLayer)operationRenderLayer.call(new Object[]{identifier});
   }

   @ModifyArg(
      method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/command/RenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"
      ),
      index = 5
   )
   private int evon$modifyArmorUv(int value) {
      return this.evon$applyOldDamageUv(value);
   }

   @Redirect(
      method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/util/Identifier;II)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getArmorTrims(Z)Lnet/minecraft/client/render/RenderLayer;")
   )
   private RenderLayer evon$oldTrimLayer(boolean flag) {
      return RenderLayer.getEntityCutoutNoCullZOffset(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
   }

   @Unique
   private int evon$applyOldDamageUv(int value) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      BipedEntityRenderState state = BipedRenderStateHolder.getState();
      return ((BaseModule)moduleByClass).isEnabled() && moduleByClass.isOldArmorDamageTint() && state != null ? OverlayTexture.packUv(OverlayTexture.getU(0.0F), OverlayTexture.getV(state.hurt)) : value;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
