package club.evon.mixins;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.event.EventBus;
import club.evon.event.JumpEvent;
import club.evon.manager.RotationManager;
import club.evon.module.impl.motion.MoveFix;
import club.evon.module.impl.motion.Sprint;
import club.evon.module.impl.motion.TargetStrafe;
import club.evon.module.impl.player.NoJumpDelay;
import club.evon.module.impl.render.Animations;
import club.evon.util.KeyedRandom;
import club.evon.util.SwingHandAccessor;




@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
   @Shadow
   public int jumpingCooldown;
   @Shadow
   public float forwardSpeed;
   @Shadow
   public float sidewaysSpeed;
   private static final long a = KeyedRandom.create(-574741364660145740L, -417458646480008180L, MethodHandles.lookup().lookupClass()).next(201437681801141L);
   private static final long b;

   public LivingEntityMixin(EntityType<?> type, World world) {
      super(type, world);
   }

   @Shadow
   public abstract void jump();

   @Shadow
   public abstract boolean isUsingItem();

   @Shadow
   public abstract double getAttributeValue(RegistryEntry<EntityAttribute> entity);

   @Shadow
   public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> registryEntryStatusEffect);

   @Shadow
   public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> registryEntryStatusEffect);

   @Redirect(method = "tickMovement()V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;jumpingCooldown:I", opcode = 181, ordinal = 1))
   private void modifyJumpingCooldown(LivingEntity entity, int value) {
      long longValue = a ^ 86165010887785L;
      if (entity == ClientContext.MINECRAFT_CLIENT.player && EvonClient.getInstance().getModuleManager().getModuleByClass(NoJumpDelay.class).isEnabled()) {
         this.jumpingCooldown = 0;
      } else {
         this.jumpingCooldown = (int)b;
      }
   }

   @Inject(method = "jump()V", at = @At("HEAD"), cancellable = true)
   private void jumpHead(CallbackInfo callbackInfo) {
      if ((Object)this == ClientContext.MINECRAFT_CLIENT.player) {
         JumpEvent jumpEvent = new JumpEvent(this.isSprinting());
         EventBus.post(jumpEvent);
         if (jumpEvent.isCanceled()) {
            callbackInfo.cancel();
         }
      }
   }

   @ModifyConstant(method = "getHandSwingDuration()I", constant = @Constant(intValue = 6))
   private int modifyHandSwingDuration(int value) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      return moduleByClass.isEnabled() ? (int)(value * moduleByClass.getSwingSpeedMultiplier()) : value;
   }

   @Redirect(
      method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;swingHand(Lnet/minecraft/util/Hand;)V")
   )
   private void redirectDropSwing(LivingEntity entity, Hand hand) {
      if (entity instanceof ClientPlayerEntity player) {
         Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
         if (moduleByClass.isEnabled() && moduleByClass.isFixSwordDrop()) {
            ((SwingHandAccessor)player).evon$swingHandServerside(hand);
            return;
         }
      }

      entity.swingHand(hand);
   }

   @Redirect(method = "jump()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
   private float redirectYaw(LivingEntity entity) {
      if (entity instanceof ClientPlayerEntity player) {
         if (EvonClient.getInstance().getModuleManager().getModuleByClass(MoveFix.class).isEnabled()) {
            return entity.getYaw();
         } else {
            TargetStrafe moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(TargetStrafe.class);
            if (moduleByClass.isEnabled() && moduleByClass.isStrafeKeyHeld()) {
               return moduleByClass.getStrafeYaw();
            } else {
               Sprint moduleByClass2 = EvonClient.getInstance().getModuleManager().getModuleByClass(Sprint.class);
               float floatValue = RotationManager.getMouseRotationManager().getYaw(entity.getYaw());
               if (!moduleByClass2.isEnabled() || !moduleByClass2.isOmniActive() || player.input.hasForwardMovement()) {
                  return floatValue;
               } else if (player.input.getMovementInput().y < -1.0E-5F) {
                  return floatValue + 180.0F;
               } else {
                  return player.input.getMovementInput().x > 0.0F ? floatValue - 90.0F : floatValue + 90.0F;
               }
            }
         }
      } else {
         return entity.getYaw();
      }
   }

   static {
      try {
      long var0 = a ^ 36533481501564L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/NoPadding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long var4 = -9218813711732707894L;
      byte[] var6 = var2.doFinal(
         new byte[]{
            (byte)(var4 >>> 56),
            (byte)(var4 >>> 48),
            (byte)(var4 >>> 40),
            (byte)(var4 >>> 32),
            (byte)(var4 >>> 24),
            (byte)(var4 >>> 16),
            (byte)(var4 >>> 8),
            (byte)var4
         }
      );
      long var7 = (var6[0] & 255L) << 56
         | (var6[1] & 255L) << 48
         | (var6[2] & 255L) << 40
         | (var6[3] & 255L) << 32
         | (var6[4] & 255L) << 24
         | (var6[5] & 255L) << 16
         | (var6[6] & 255L) << 8
         | var6[7] & 255L;
      byte var10001 = -1;
      b = var7;
      } catch (Exception var8) {
         throw new RuntimeException(var8);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
