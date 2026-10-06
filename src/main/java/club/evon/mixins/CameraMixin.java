package club.evon.mixins;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.EvonClient;
import club.evon.manager.ModuleManager;
import club.evon.manager.RotationManager;
import club.evon.module.impl.motion.Scaffold;
import club.evon.module.impl.render.Animations;




@Mixin(Camera.class)
public abstract class CameraMixin {
   @Shadow
   private boolean thirdPerson;
   @Shadow
   private float yaw;
   @Shadow
   private float pitch;
   @Shadow
   private float cameraY;
   @Shadow
   private float lastCameraY;
   @Unique
   private EntityPose prevPose;

   @Shadow
   public abstract void setRotation(float floatValue, float floatValue2);

   @Redirect(
      method = "update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getYaw(F)F")
   )
   private float redirectYaw(Entity entity, float floatValue) {
      return RotationManager.getMouseRotationManager().getYaw(entity.getYaw(floatValue));
   }

   @Redirect(
      method = "update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getPitch(F)F")
   )
   private float redirectPitch(Entity entity, float floatValue) {
      return RotationManager.getMouseRotationManager().getPitch(entity.getPitch(floatValue));
   }

   @Shadow
   public abstract Entity getFocusedEntity();

   @Inject(
      method = "updateEyeHeight()V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/Camera;lastCameraY:F", opcode = 181),
      cancellable = true
   )
   private void modifyCameraY(CallbackInfo callbackInfo) {
      ModuleManager moduleManager = EvonClient.getInstance().getModuleManager();
      Animations moduleByClass = moduleManager.getModuleByClass(Animations.class);
      Entity focusedEntity = this.getFocusedEntity();
      EntityPose pose = focusedEntity.getPose();
      if (moduleByClass.isOldSneaking() && this.prevPose != null && (pose == EntityPose.CROUCHING || pose == EntityPose.STANDING && this.prevPose == EntityPose.CROUCHING)) {
         this.cameraY = focusedEntity.getStandingEyeHeight();
         if (pose == EntityPose.CROUCHING) {
            this.cameraY += 0.27F;
         }

         this.lastCameraY = this.cameraY;
         callbackInfo.cancel();
      }

      Scaffold moduleByClass2 = moduleManager.getModuleByClass(Scaffold.class);
      this.prevPose = pose;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
