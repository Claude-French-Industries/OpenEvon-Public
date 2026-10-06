package club.evon.mixins;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.EvonClient;
import club.evon.event.EventBus;
import club.evon.event.ItemSlowdownEvent;
import club.evon.event.MotionEvent;
import club.evon.event.MovementEvent;
import club.evon.event.PostMotionEvent;
import club.evon.event.SwingHandEvent;
import club.evon.manager.RotationManager;
import club.evon.module.impl.motion.NoSlow;
import club.evon.module.impl.motion.Sprint;
import club.evon.util.SwingHandAccessor;




@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity implements SwingHandAccessor {
   @Shadow
   public Input input;
   @Unique
   private MotionEvent motionEvent;
   @Shadow
   private float mountJumpStrength;
   @Shadow
   public double lastXClient;
   @Shadow
   public double lastYClient;
   @Shadow
   public double lastZClient;
   @Shadow
   public float lastYawClient;
   @Shadow
   public float lastPitchClient;
   @Shadow
   public int ticksSinceLastPositionPacketSent;
   @Final
   @Shadow
   public ClientPlayNetworkHandler networkHandler;
   @Shadow
   public boolean lastOnGround;
   @Shadow
   public boolean lastHorizontalCollision;
   @Shadow
   public boolean autoJumpEnabled;
   @Final
   @Shadow
   private MinecraftClient client;
   @Unique
   private boolean prevHandSwinging;
   @Unique
   private int prevHandSwingTicks;

   public ClientPlayerEntityMixin(World world, GameProfile profile) {
      super(world, profile);
   }

   @Shadow
   public abstract void autoJump(float floatValue, float floatValue2);

   @Shadow
   public abstract void move(MovementType movementType, Vec3d pos);

   @Shadow
   public abstract boolean isUsingItem();

   @Shadow
   public abstract boolean isSneaking();

   @Shadow
   public abstract boolean shouldSlowDown();

   @Shadow
   public abstract void sendSprintingPacket();

   @Shadow
   public abstract boolean isCamera();

   @Shadow
   private static Vec2f applyDirectionalMovementSpeedFactors(Vec2f pos) {
      return null;
   }

   @Inject(method = "tickMovementInput()V", at = @At("TAIL"))
   private void tickNewAi(CallbackInfo callbackInfo) {
      if (this.isCamera()) {
         RotationManager.getMouseRotationManager().updateRenderRotation();
      }
   }

   @Inject(method = "sendMovementPackets()V", at = @At("HEAD"))
   private void sendMovementPacketsHead(CallbackInfo callbackInfo) {
      if (this.isCamera()) {
         this.motionEvent = new MotionEvent(
            this.getX(), this.getBoundingBox().minY, this.getZ(), this.getYaw(), this.getPitch(), this.lastYaw, this.lastPitch, this.isOnGround()
         );
         EventBus.post(this.motionEvent);
      }
   }

   @Inject(method = "sendMovementPackets()V", at = @At("TAIL"))
   private void sendMovementPacketsTail(CallbackInfo callbackInfo) {
      if (this.isCamera()) {
         EventBus.post(new PostMotionEvent());
      }
   }

   @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
   private float injectTick$getYaw(ClientPlayerEntity player) {
      return this.motionEvent == null ? player.getYaw() : this.motionEvent.getYaw();
   }

   @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
   private float injectTick$getPitch(ClientPlayerEntity player) {
      return this.motionEvent == null ? player.getPitch() : this.motionEvent.getPitch();
   }

   @Redirect(method = "sendMovementPackets()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
   private float injectSendMovement$getYaw(ClientPlayerEntity player) {
      return this.motionEvent == null ? player.getYaw() : this.motionEvent.getYaw();
   }

   @Redirect(method = "sendMovementPackets()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
   private float injectSendMovement$getPitch(ClientPlayerEntity player) {
      return this.motionEvent == null ? player.getPitch() : this.motionEvent.getPitch();
   }

   @Redirect(method = "sendMovementPackets()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getX()D"))
   private double injectSendMovement$getX(ClientPlayerEntity player) {
      return this.motionEvent == null ? player.getX() : this.motionEvent.getX();
   }

   @Redirect(method = "sendMovementPackets()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getY()D"))
   private double injectSendMovement$getY(ClientPlayerEntity player) {
      return this.motionEvent == null ? player.getY() : this.motionEvent.getY();
   }

   @Redirect(method = "sendMovementPackets()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getZ()D"))
   private double injectSendMovement$getZ(ClientPlayerEntity player) {
      return this.motionEvent == null ? player.getZ() : this.motionEvent.getZ();
   }

   @Redirect(method = "sendMovementPackets()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnGround()Z"))
   private boolean injectSendMovement$isOnGround(ClientPlayerEntity player) {
      return this.motionEvent == null ? player.isOnGround() : this.motionEvent.isOnGround();
   }

   @Redirect(
      method = "sendMovementPackets()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getEntityPos()Lnet/minecraft/util/math/Vec3d;")
   )
   private Vec3d hookSendMovementPacketsGetPos(ClientPlayerEntity player) {
      return this.motionEvent == null ? player.getEntityPos() : new Vec3d(this.motionEvent.getX(), this.motionEvent.getY(), this.motionEvent.getZ());
   }

   @ModifyExpressionValue(method = "canStartSprinting()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;hasForwardMovement()Z"))
   private boolean modifyIsWalking(boolean flag) {
      if (!EvonClient.getInstance().getModuleManager().getModuleByClass(Sprint.class).isOmniActive()) {
         return flag;
      }

      float floatValue = Math.abs(this.forwardSpeed);
      float floatValue2 = Math.abs(this.sidewaysSpeed);
      return this.isSubmergedInWater() ? floatValue > 1.0E-5F || floatValue2 > 1.0E-5F : floatValue > 0.8 || floatValue2 > 0.8;
   }

   @ModifyExpressionValue(method = "tickMovement()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;hasForwardMovement()Z"))
   private boolean modifyMovement(boolean flag) {
      return !EvonClient.getInstance().getModuleManager().getModuleByClass(Sprint.class).isOmniActive() ? flag : Math.abs(this.sidewaysSpeed) > 1.0E-5F || Math.abs(this.forwardSpeed) > 1.0E-5F;
   }

   @WrapWithCondition(
      method = "tickMovement()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;setSprinting(Z)V", ordinal = 3)
   )
   private boolean wrapSetSprinting(ClientPlayerEntity player, boolean flag) {
      return !EvonClient.getInstance().getModuleManager().getModuleByClass(Sprint.class).isOmniActive();
   }

   @Inject(method = "swingHand(Lnet/minecraft/util/Hand;)V", at = @At("HEAD"))
   private void hookSwingHandHead(Hand hand, CallbackInfo callbackInfo) {
      this.prevHandSwinging = this.handSwinging;
      this.prevHandSwingTicks = this.handSwingTicks;
   }

   @Inject(method = "swingHand(Lnet/minecraft/util/Hand;)V", at = @At("TAIL"))
   private void hookSwingHandTail(Hand hand, CallbackInfo callbackInfo) {
      SwingHandEvent swingHandEvent = new SwingHandEvent(hand);
      EventBus.post(swingHandEvent);
      if (swingHandEvent.isCanceled()) {
         this.handSwinging = this.prevHandSwinging;
         this.handSwingTicks = this.prevHandSwingTicks;
      }
   }

   @Redirect(method = "canStartSprinting()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
   private boolean hookAllowSprint(ClientPlayerEntity player) {
      NoSlow moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(NoSlow.class);
      return moduleByClass.isEnabled() && moduleByClass.canSprint() ? false : player.isUsingItem();
   }

   @Redirect(
      method = "applyMovementSpeedFactors(Lnet/minecraft/util/math/Vec2f;)Lnet/minecraft/util/math/Vec2f;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec2f;multiply(F)Lnet/minecraft/util/math/Vec2f;", ordinal = 1)
   )
   private Vec2f redirectUseSlowdown(Vec2f pos, float floatValue) {
      ItemSlowdownEvent itemSlowdownEvent = new ItemSlowdownEvent();
      EventBus.post(itemSlowdownEvent);
      return pos.multiply(itemSlowdownEvent.isCanceled() ? 1.0F : floatValue);
   }

   @ModifyVariable(
      method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",
      at = @At("HEAD"),
      name = "arg2",
      ordinal = 0,
      index = 2,
      argsOnly = true
   )
   private Vec3d move_r2(Vec3d pos, MovementType movementType) {
      MovementEvent movementEvent = new MovementEvent(pos);
      EventBus.post(movementEvent);
      return movementEvent.getMovement();
   }

   @Unique
   @Override
   public void evon$swingHandClientside(Hand hand) {
      super.swingHand(hand);
   }

   @Unique
   @Override
   public void evon$swingHandServerside(Hand hand) {
      this.networkHandler.sendPacket(new HandSwingC2SPacket(hand));
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
