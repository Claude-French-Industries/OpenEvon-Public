package club.evon.mixins;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.event.EventBus;
import club.evon.event.MovementCollisionEvent;
import club.evon.event.UpdateVelocityEvent;
import club.evon.manager.RotationManager;
import club.evon.module.impl.motion.MoveFix;
import club.evon.module.impl.motion.TargetStrafe;




@Mixin(Entity.class)
public abstract class EntityMixin {
   @Shadow
   public float lastYaw;
   @Shadow
   public float lastPitch;
   @Shadow
   public boolean horizontalCollision;
   @Shadow
   private World world;

   @Shadow
   public abstract double getX();

   @Shadow
   public abstract double getY();

   @Shadow
   public abstract double getZ();

   @Shadow
   public abstract float getYaw();

   @Shadow
   public abstract float getPitch();

   @Shadow
   public abstract boolean isSubmergedInWater();

   @Final
   @Shadow
   public abstract Box getBoundingBox();

   @Shadow
   public abstract Vec3d getVelocity();

   @Shadow
   public abstract boolean isOnGround();

   @Shadow
   public abstract boolean hasVehicle();

   @Shadow
   public abstract Vec3d getEntityPos();

   @Inject(method = "setYaw(F)V", at = @At("HEAD"))
   private void setYaw(float floatValue, CallbackInfo callbackInfo) {
      this.checkRotation();
   }

   @Inject(method = "setPitch(F)V", at = @At("HEAD"))
   private void setPitch(float floatValue, CallbackInfo callbackInfo) {
      this.checkRotation();
   }

   @Redirect(method = "updateVelocity(FLnet/minecraft/util/math/Vec3d;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getYaw()F"))
   private float redirectYaw(Entity entity) {
      if (ClientContext.MINECRAFT_CLIENT.player != null && entity == ClientContext.MINECRAFT_CLIENT.player) {
         TargetStrafe moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(TargetStrafe.class);
         if (moduleByClass.isEnabled() && moduleByClass.isStrafeKeyHeld()) {
            return moduleByClass.getStrafeYaw();
         }

         if (!EvonClient.getInstance().getModuleManager().getModuleByClass(MoveFix.class).isEnabled()) {
            return RotationManager.getMouseRotationManager().getYaw(entity.getYaw());
         }
      }

      return entity.getYaw();
   }

   @Unique
   private void checkRotation() {
      if (ClientContext.MINECRAFT_CLIENT.player != null && (Object)this == ClientContext.MINECRAFT_CLIENT.player && this.world.isClient()) {
         RotationManager.getMouseRotationManager().clearRotationIfNotUpdating();
      }
   }

   @ModifyExpressionValue(
      method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isLogicalSideForUpdatingMovement()Z")
   )
   private boolean fixFallDistanceCalculation(boolean flag) {
      return (Object)this == ClientContext.MINECRAFT_CLIENT.player ? true : flag;
   }

   @Inject(
      method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
      at = @At(value = "RETURN", ordinal = 0),
      cancellable = true
   )
   private void onStep(Vec3d pos, CallbackInfoReturnable<Vec3d> pos2) {
      if ((Object)this == ClientContext.MINECRAFT_CLIENT.player) {
         MovementCollisionEvent movementCollisionEvent = new MovementCollisionEvent((Vec3d)pos2.getReturnValue());
         EventBus.post(movementCollisionEvent);
         if (movementCollisionEvent.isCanceled()) {
            pos2.setReturnValue(Vec3d.ZERO);
         } else {
            pos2.setReturnValue(movementCollisionEvent.getMovement());
         }
      }
   }

   @Inject(method = "updateVelocity(FLnet/minecraft/util/math/Vec3d;)V", at = @At("TAIL"))
   private void hookUpdateVelocityTail(float floatValue, Vec3d pos, CallbackInfo callbackInfo) {
      if ((Object)this == ClientContext.MINECRAFT_CLIENT.player) {
         EventBus.post(new UpdateVelocityEvent(floatValue, pos));
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
