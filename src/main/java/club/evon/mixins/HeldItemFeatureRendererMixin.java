package club.evon.mixins;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionfc;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.module.impl.render.Animations;
import club.evon.util.BipedEntityRenderStateAccessor;
import club.evon.util.render.SwordBlockingHelper;




@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin<S extends ArmedEntityRenderState, M extends EntityModel<S> & ModelWithArms> extends FeatureRenderer<S, M> {
   public HeldItemFeatureRendererMixin(FeatureRendererContext<S, M> context) {
      super(context);
   }

   @Inject(
      method = "renderItem(Lnet/minecraft/client/render/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At("HEAD")
   )
   private void setThirdPersonStackRef(
      S s,
      ItemRenderState itemRenderState,
      Arm arm,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int value,
      CallbackInfo callbackInfo,
      @Share("stack") LocalRef<ItemStack> stack
   ) {
      if (SwordBlockingHelper.shouldRenderSwordBlocking(s)) {
         stack.set(((BipedEntityRenderStateAccessor)s).evon$getEntity().getStackInArm(arm));
      }
   }

   @ModifyArgs(
      method = "renderItem(Lnet/minecraft/client/render/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V")
   )
   private void translateThirdPersonBlock(Args args, @Local(argsOnly = true) S s, @Share("stack") LocalRef<ItemStack> stack) {
      if (SwordBlockingHelper.shouldRenderSwordBlocking(s)) {
         args.setAll(new Object[]{(Float)args.get(0) * -1.0F, 0.4375F, (Float)args.get(2) / 10.0F * -1.0F});
      }
   }

   @WrapWithCondition(
      method = "renderItem(Lnet/minecraft/client/render/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionfc;)V")
   )
   private boolean removeThirdPersonMultiplyTransformations(
      MatrixStack matrixStack, Quaternionfc quaternionfc, @Local(argsOnly = true) S s, @Share("stack") LocalRef<ItemStack> stack
   ) {
      return !SwordBlockingHelper.shouldRenderSwordBlocking(s);
   }

   @Inject(
      method = "renderItem(Lnet/minecraft/client/render/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/ItemRenderState;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;III)V"
      )
   )
   private void applyThirdPersonBlockRotation(
      S s, ItemRenderState itemRenderState, Arm arm, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int value, CallbackInfo callbackInfo
   ) {
      if (SwordBlockingHelper.shouldRenderSwordBlocking(s)) {
         int value2 = arm == Arm.RIGHT ? 1 : -1;
         float floatValue = 0.625F;
         matrixStack.translate(value2 * 0.05F, 0.0F, -0.1F);
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(value2 * -50.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-10.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(value2 * -60.0F));
         matrixStack.translate(value2 * -0.0625F, 0.1875F, 0.0F);
         matrixStack.scale(0.625F, 0.625F, 0.625F);
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(100.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(value2 * -145));
         matrixStack.translate(-0.011765625F, 0.0F, 0.002125F);
         matrixStack.translate(0.0F, -0.3F, 0.0F);
         matrixStack.scale(1.5F, 1.5F, 1.5F);
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(value2 * 50.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(value2 * 335.0F));
         matrixStack.translate(value2 * -0.9375F, -0.0625F, 0.0F);
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(value2 * 180.0F));
         matrixStack.translate(value2 * -0.5F, 0.5F, 0.03125F);
         matrixStack.scale(1.1764705F, 1.1764705F, 1.1764705F);
         matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(value2 * -55.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(value2 * 90.0F));
         matrixStack.translate(0.0F, -0.25F, -0.03125F);
      }
   }

   @WrapOperation(
      method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/ArmedEntityRenderState;FF)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/feature/HeldItemFeatureRenderer;renderItem(Lnet/minecraft/client/render/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"
      )
   )
   private void cancelShieldRender(
      HeldItemFeatureRenderer heldItemFeatureRenderer, S s, ItemRenderState itemRenderState, Arm arm, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int value, Operation<Void> operationVoid
   ) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      if (!moduleByClass.isEnabled()
         || !moduleByClass.isHideShield()
         || arm != ClientContext.MINECRAFT_CLIENT.player.getMainArm().getOpposite()
         || !(ClientContext.MINECRAFT_CLIENT.player.getStackInHand(Hand.OFF_HAND).getItem() instanceof ShieldItem)) {
         operationVoid.call(new Object[]{heldItemFeatureRenderer, s, itemRenderState, arm, matrixStack, orderedRenderCommandQueue, value});
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
