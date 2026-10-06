package club.evon.mixins;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.SlotSwitchMode;
import club.evon.event.EventBus;
import club.evon.event.KeepSprintEvent;
import club.evon.manager.ClientState;
import club.evon.manager.SilentSlotManager;
import club.evon.module.impl.combat.Reach;
import club.evon.module.impl.player.Breaker;
import club.evon.util.PlayerUtil;
import club.evon.util.VisualAttackCooldownAccessor;




@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements VisualAttackCooldownAccessor {
   @Shadow
   @Nullable
   public FishingBobberEntity fishHook;
   @Unique
   private int visualLastAttackedTicks;
   @Unique
   private ItemStack visualSelectedItem;
   private KeepSprintEvent keepSprintEvent;

   public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
      super(entityType, world);
   }

   @Inject(method = "tick()V", at = @At("TAIL"))
   private void incrementTicks(CallbackInfo callbackInfo) {
      if ((Object)this == ClientContext.MINECRAFT_CLIENT.player) {
         ClientState instance = ClientState.getInstance();
         if (this.isOnGround()) {
            instance.airTicks = 0;
            instance.groundTicks++;
         } else {
            instance.airTicks++;
            instance.groundTicks = 0;
         }
      }
   }

   @Inject(method = "resetLastAttackedTicks()V", at = @At("TAIL"))
   private void resetVisualLastAttackedTicks(CallbackInfo callbackInfo) {
      this.visualLastAttackedTicks = 0;
   }

   @Inject(
      method = "tick()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMainHandStack()Lnet/minecraft/item/ItemStack;")
   )
   private void checkVisualItemStackEquality(CallbackInfo callbackInfo) {
      if ((Object)this == ClientContext.MINECRAFT_CLIENT.player) {
         ItemStack mainHandStack = SilentSlotManager.getInstance().getMainHandStack(ClientContext.MINECRAFT_CLIENT.player);
         if (this.visualSelectedItem == null) {
            this.visualSelectedItem = ItemStack.EMPTY;
         }

         if (!ItemStack.areEqual(this.visualSelectedItem, mainHandStack)) {
            if (!ItemStack.areItemsEqual(this.visualSelectedItem, mainHandStack)) {
               this.visualLastAttackedTicks = 0;
            }

            this.visualSelectedItem = mainHandStack.copy();
         }
      }
   }

   @Redirect(
      method = "canHarvest(Lnet/minecraft/block/BlockState;)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getSelectedStack()Lnet/minecraft/item/ItemStack;")
   )
   private ItemStack redirectHandStack(PlayerInventory player) {
      Breaker moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Breaker.class);
      return moduleByClass.isEnabled() && moduleByClass.isBreaking() && moduleByClass.getTargetSlot() != -1 ? player.getStack(moduleByClass.getTargetSlot()) : player.getSelectedStack();
   }

   @Redirect(
      method = "getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getSelectedStack()Lnet/minecraft/item/ItemStack;")
   )
   private ItemStack redirectHandStack2(PlayerInventory player) {
      Breaker moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Breaker.class);
      return moduleByClass.isEnabled() && moduleByClass.isBreaking() && moduleByClass.getTargetSlot() != -1 ? player.getStack(moduleByClass.getTargetSlot()) : player.getSelectedStack();
   }

   @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V"))
   private void onSwapLastAttackedTicksReset(PlayerEntity player) {
      this.lastAttackedTicks = 0;
   }

   @Inject(method = "tick()V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;lastAttackedTicks:I", ordinal = 0))
   private void incrementLastAttackedTicks(CallbackInfo callbackInfo) {
      this.visualLastAttackedTicks++;
   }

   @Unique
   private float getVisualAttackCooldownProgressPerTick() {
      SilentSlotManager instance = SilentSlotManager.getInstance();
      double doubleValue;
      if (instance.isActive() && instance.getMode() != SlotSwitchMode.NONE) {
         doubleValue = PlayerUtil.getAttackSpeed(instance.getMainHandStack(ClientContext.MINECRAFT_CLIENT.player));
      } else {
         doubleValue = this.getAttributeValue(EntityAttributes.ATTACK_SPEED);
      }

      return (float)(1.0 / doubleValue * 20.0);
   }

   @Unique
   @Override
   public float evon$getVisualAttackCooldownProgress(float baseTime) {
      return MathHelper.clamp((this.visualLastAttackedTicks + baseTime) / this.getVisualAttackCooldownProgressPerTick(), 0.0F, 1.0F);
   }

   @Inject(
      method = "attack(Lnet/minecraft/entity/Entity;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V")
   )
   private void hookKeepSprint(Entity entity, CallbackInfo callbackInfo) {
      this.keepSprintEvent = new KeepSprintEvent();
      EventBus.post(this.keepSprintEvent);
   }

   @ModifyArg(
      method = "attack(Lnet/minecraft/entity/Entity;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setSprinting(Z)V")
   )
   private boolean hookKeepSprintState(boolean flag) {
      return (this.keepSprintEvent == null || this.keepSprintEvent.isCanceled()) && ClientContext.MINECRAFT_CLIENT.player.isSprinting();
   }

   @ModifyArg(
      method = "attack(Lnet/minecraft/entity/Entity;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V")
   )
   private Vec3d hookKeepSprintVelocity(Vec3d pos) {
      return (this.keepSprintEvent == null || this.keepSprintEvent.isCanceled()) && ClientContext.MINECRAFT_CLIENT.player.isSprinting() ? ClientContext.MINECRAFT_CLIENT.player.getVelocity() : pos;
   }

   @Inject(method = "getEntityInteractionRange()D", at = @At("HEAD"), cancellable = true)
   private void hookEntityReach(CallbackInfoReturnable<Double> callbackInfoReturnableDouble) {
      Reach moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Reach.class);
      if (moduleByClass.isEnabled()) {
         callbackInfoReturnableDouble.setReturnValue(moduleByClass.getEntityInteractReach());
      }
   }

   @Inject(method = "getBlockInteractionRange()D", at = @At("HEAD"), cancellable = true)
   private void hookBlockReach(CallbackInfoReturnable<Double> callbackInfoReturnableDouble) {
      Reach moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Reach.class);
      if (moduleByClass.isEnabled()) {
         callbackInfoReturnableDouble.setReturnValue(moduleByClass.getBlockPlaceReach());
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
