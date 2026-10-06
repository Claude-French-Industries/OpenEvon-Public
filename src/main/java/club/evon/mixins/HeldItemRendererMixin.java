package club.evon.mixins;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.manager.RotationManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.impl.render.Animations;
import club.evon.util.VisualAttackCooldownAccessor;
import club.evon.util.render.SwordBlockingHelper;




@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
   @Shadow
   private ItemStack mainHand;
   @Shadow
   private float equipProgressMainHand;

   @Shadow
   public abstract void applySwingOffset(MatrixStack matrixStack, Arm arm, float floatValue);

   @Inject(
      method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = Shift.AFTER)
   )
   private void hookRenderFirstPersonItem(
      AbstractClientPlayerEntity player,
      float floatValue,
      float floatValue2,
      Hand hand,
      float floatValue3,
      ItemStack stack,
      float floatValue4,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int value,
      CallbackInfo callbackInfo
   ) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      if (((BaseModule)moduleByClass).isEnabled() && Hand.MAIN_HAND == hand) {
         matrixStack.translate(moduleByClass.getOffsetX(), moduleByClass.getOffsetY(), moduleByClass.getOffsetZ());
      }
   }

   @Inject(
      method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private void hideShield(
      AbstractClientPlayerEntity player,
      float floatValue,
      float floatValue2,
      Hand hand,
      float floatValue3,
      ItemStack stack,
      float floatValue4,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int value,
      CallbackInfo callbackInfo
   ) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      if (hand == Hand.OFF_HAND && stack.getItem() instanceof ShieldItem && ((BaseModule)moduleByClass).isEnabled() && moduleByClass.isHideShield()) {
         callbackInfo.cancel();
      }
   }

   @ModifyArg(method = "updateHeldItems()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F", ordinal = 2), index = 0)
   private float modifyMainHandEquipProgress(float floatValue, @Local(ordinal = 0) ItemStack stack) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      boolean flag = ((BaseModule)moduleByClass).isEnabled() && moduleByClass.isOldCooldown();
      return flag && this.mainHand == stack ? 1.0F - this.equipProgressMainHand : floatValue;
   }

   @Redirect(
      method = "updateHeldItems()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F")
   )
   private float redirectGetAttackCooldown(ClientPlayerEntity player, float floatValue) {
      return ((VisualAttackCooldownAccessor)player).evon$getVisualAttackCooldownProgress(floatValue);
   }

   @ModifyArg(
      method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V",
         ordinal = 3
      ),
      index = 2
   )
   private float applyEquipOffset(float floatValue) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      return ((BaseModule)moduleByClass).isEnabled() && !moduleByClass.isEquipOffset() ? 0.0F : floatValue;
   }

   @Inject(
      method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
         ordinal = 1
      )
   )
   private void applySwordBlockingTransformation(
      AbstractClientPlayerEntity player,
      float floatValue,
      float floatValue2,
      Hand hand,
      float floatValue3,
      ItemStack stack,
      float floatValue4,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int value,
      CallbackInfo callbackInfo
   ) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      if (((BaseModule)moduleByClass).isEnabled() && moduleByClass.isSwordBlocking() && (SwordBlockingHelper.isShieldBlocking(player) || SwordBlockingHelper.isSwordBlocking(player) || SwordBlockingHelper.isSilentSwordBlocking())) {
         moduleByClass.applyBlockAnimation(matrixStack, floatValue3);
      }
   }

   @Inject(method = "swingArm(FFLnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/util/Arm;)V", at = @At("HEAD"), cancellable = true)
   private void cancelSwingArm(float floatValue, float floatValue2, MatrixStack matrixStack, int value, Arm arm, CallbackInfo callbackInfo) {
      if (SwordBlockingHelper.isShieldBlocking(ClientContext.MINECRAFT_CLIENT.player) || SwordBlockingHelper.isSilentSwordBlocking()) {
         matrixStack.translate(0.56F, -0.52F, -0.72F);
         callbackInfo.cancel();
      }
   }

   @Redirect(
      method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;")
   )
   private Item cancelBlockTransformation(ItemStack stack, @Local(argsOnly = true) MatrixStack matrixStack) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      return ((BaseModule)moduleByClass).isEnabled() && moduleByClass.isSwordBlocking() && stack.isIn(ItemTags.SWORDS) ? Items.SHIELD : stack.getItem();
   }

   @Inject(
      method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V",
         ordinal = 2,
         shift = Shift.AFTER
      )
   )
   private void applyEatingAndDrinkingOffset(
      AbstractClientPlayerEntity player,
      float floatValue,
      float floatValue2,
      Hand hand,
      float floatValue3,
      ItemStack stack,
      float floatValue4,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int value,
      CallbackInfo callbackInfo
   ) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      if (((BaseModule)moduleByClass).isEnabled() && player.handSwinging) {
         this.applySwingOffset(matrixStack, player.getActiveHand() == Hand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite(), floatValue3);
      }
   }

   @Inject(
      method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V",
         ordinal = 4,
         shift = Shift.AFTER
      )
   )
   private void applyBowOffset(
      AbstractClientPlayerEntity player,
      float floatValue,
      float floatValue2,
      Hand hand,
      float floatValue3,
      ItemStack stack,
      float floatValue4,
      MatrixStack matrixStack,
      OrderedRenderCommandQueue orderedRenderCommandQueue,
      int value,
      CallbackInfo callbackInfo
   ) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      if (((BaseModule)moduleByClass).isEnabled()) {
         this.applySwingOffset(matrixStack, player.getActiveHand() == Hand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite(), floatValue3);
      }
   }

   @Redirect(
      method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw(F)F")
   )
   private float redirectItemYaw(ClientPlayerEntity player, float floatValue) {
      return RotationManager.getMouseRotationManager().getYaw(player.getYaw(floatValue));
   }

   @Redirect(
      method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch(F)F")
   )
   private float redirectItemPitch(ClientPlayerEntity player, float floatValue) {
      return RotationManager.getMouseRotationManager().getPitch(player.getPitch(floatValue));
   }

   @Redirect(
      method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;lastRenderYaw:F", opcode = 180)
   )
   private float redirectItemLastRenderYaw(ClientPlayerEntity player) {
      return RotationManager.getMouseRotationManager().getLastRenderYaw(player.lastRenderYaw);
   }

   @Redirect(
      method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;lastRenderPitch:F", opcode = 180)
   )
   private float redirectItemLastRenderPitch(ClientPlayerEntity player) {
      return RotationManager.getMouseRotationManager().getLastRenderPitch(player.lastRenderPitch);
   }

   @Redirect(
      method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;renderYaw:F", opcode = 180)
   )
   private float redirectItemRenderYaw(ClientPlayerEntity player) {
      return RotationManager.getMouseRotationManager().getRenderYaw(player.renderYaw);
   }

   @Redirect(
      method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;renderPitch:F", opcode = 180)
   )
   private float redirectItemRenderPitch(ClientPlayerEntity player) {
      return RotationManager.getMouseRotationManager().getRenderPitch(player.renderPitch);
   }

   @Redirect(
      method = "updateHeldItems()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getMainHandStack()Lnet/minecraft/item/ItemStack;")
   )
   private ItemStack getMainHandStack(ClientPlayerEntity player) {
      return SilentSlotManager.getInstance().getMainHandStack(player);
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
