package club.evon.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Arm;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import club.evon.EvonClient;
import club.evon.module.BaseModule;
import club.evon.module.impl.render.Animations;
import club.evon.util.BipedEntityRenderStateAccessor;




@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends BipedEntityRenderState> extends EntityModel<T> implements ModelWithArms, ModelWithHead {
   @Final
   @Shadow
   public ModelPart head;

   private BipedEntityModelMixin(ModelPart modelPart) {
      super(modelPart);
   }

   @WrapOperation(
      method = "positionBlockingArm(Lnet/minecraft/client/model/ModelPart;Z)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F")
   )
   private float fixThirdPersonBlockRotation(float floatValue, float floatValue2, float floatValue3, Operation<Float> operationFloat) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      return ((BaseModule)moduleByClass).isEnabled() && moduleByClass.isSwordBlocking() ? 0.0F : (Float)operationFloat.call(new Object[]{floatValue, floatValue2, floatValue3});
   }

   @WrapOperation(
      method = {
            "positionLeftArm(Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;)V",
            "positionRightArm(Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;)V"
      },
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;positionBlockingArm(Lnet/minecraft/client/model/ModelPart;Z)V"
      )
   )
   private void fixThirdPersonBlockPosition(BipedEntityModel<?> entity, ModelPart modelPart, boolean flag, Operation<Void> operationVoid, @Local(argsOnly = true) T t) {
      operationVoid.call(new Object[]{entity, modelPart, flag});
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      if (((BaseModule)moduleByClass).isEnabled() && moduleByClass.isSwordBlocking() && ((BipedEntityRenderStateAccessor)t).evon$getEntity() instanceof LivingEntity entity2 && t instanceof BipedEntityRenderState) {
         ItemStack stack = flag ? entity2.getStackInArm(Arm.RIGHT) : entity2.getStackInArm(Arm.LEFT);
         if (!(stack.getItem() instanceof ShieldItem)) {
            modelPart.pitch = modelPart.pitch * 0.5F - (float) (Math.PI / 5);
            modelPart.yaw = 0.0F;
         }
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
