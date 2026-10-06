package club.evon.util.render;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.ItemUseType;
import club.evon.manager.KeybindManager;
import club.evon.manager.ModuleManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.impl.combat.AutoBlock;
import club.evon.module.impl.combat.KillAura;
import club.evon.module.impl.motion.NoSlow;
import club.evon.module.impl.render.Animations;
import club.evon.util.BipedEntityRenderStateAccessor;
import club.evon.util.ItemUtils;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;




public final class SwordBlockingHelper {
   private static final long KEY = KeyedRandom.create(-3576388120083654100L, 1298080149135274039L, MethodHandles.lookup().lookupClass()).next(89355155407598L);

   public static void applyBlockTransform(MatrixStack matrices) {
      matrices.translate(-0.15F, 0.16F, 0.15F);
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-18.0F));
      matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(82.0F));
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(112.0F));
   }

   public static void applySwingTransform(MatrixStack matrices, float swingProgress, float convertedProgress) {
      float floatValue = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F + floatValue * -20.0F));
      matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(convertedProgress * -20.0F));
      matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(convertedProgress * -80.0F));
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-45.0F));
   }

   public static boolean isSwordBlocking(PlayerEntity player) {
      long longValue = KEY ^ 22940259566263L;
      String[] strings = PlayerUtil.getGuard();
      boolean in = player.getMainHandStack().isIn(ItemTags.SWORDS);
      if (strings != null) {
         if (!in) {
            return false;
         }

         in = player.getMainHandStack().getUseAction().equals(UseAction.BLOCK);
      }

      if (strings != null) {
         if (!in) {
            return false;
         }

         return player.getItemUseTime() > 0;
      }

      return in;
   }

   public static boolean isShieldBlocking(PlayerEntity player) {
      long longValue = KEY ^ 80588357359491L;
      String[] strings = PlayerUtil.getGuard();
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      String[] strings2 = strings;
      boolean in = player.getMainHandStack().isIn(ItemTags.SWORDS);
      if (strings2 != null) {
         if (!in) {
            return false;
         }

         in = player.getActiveItem().getItem() instanceof ShieldItem;
      }

      if (strings2 != null) {
         if (!in) {
            return false;
         }

         in = player.getItemUseTime() > 0;
      }

      if (strings2 != null) {
         if (!in) {
            return false;
         }

         in = ((BaseModule)moduleByClass).isEnabled();
      }

      if (strings2 != null) {
         if (!in) {
            return false;
         }

         in = moduleByClass.isSwordBlocking();
      }

      if (strings2 != null) {
         if (!in) {
            return false;
         }

         in = isSwordBlocking(player);
      }

      if (strings2 != null) {
         return !in;
      }

      return in;
   }

   public static boolean shouldRenderSwordBlocking(ArmedEntityRenderState entityState) {
      long longValue = KEY ^ 104729629501161L;
      String[] strings = PlayerUtil.getGuard();
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      boolean enabled = ((BaseModule)moduleByClass).isEnabled();
      if (strings == null) {
         return enabled;
      }

      if (!enabled) {
         return false;
      }

      if (!moduleByClass.isSwordBlocking()) {
         return false;
      }

      if (!(entityState instanceof BipedEntityRenderState)) {
         return false;
      }

      BipedEntityRenderState entity = (BipedEntityRenderState)entityState;
      LivingEntity evon$getEntity = ((BipedEntityRenderStateAccessor)entity).evon$getEntity();
      if (entity.isUsingItem
         && entityState.mainArm == Arm.LEFT
         && (entityState.rightArmPose == ArmPose.EMPTY || entityState.rightArmPose == ArmPose.BLOCK)
         && evon$getEntity.getStackInArm(Arm.RIGHT).getItem() instanceof ShieldItem
         && evon$getEntity.getStackInArm(Arm.LEFT).isIn(ItemTags.SWORDS)) {
         return true;
      }

      if (entity.isUsingItem
         && entityState.mainArm == Arm.RIGHT
         && (entityState.leftArmPose == ArmPose.EMPTY || entityState.leftArmPose == ArmPose.BLOCK)
         && evon$getEntity.getStackInArm(Arm.LEFT).getItem() instanceof ShieldItem
         && evon$getEntity.getStackInArm(Arm.RIGHT).isIn(ItemTags.SWORDS)) {
         return true;
      }

      if (evon$getEntity == ClientContext.MINECRAFT_CLIENT.player && isSilentSwordBlocking()) {
         return true;
      }

      if (!(evon$getEntity instanceof PlayerEntity)) {
         return false;
      }

      PlayerEntity player = (PlayerEntity)evon$getEntity;
      if (isSwordBlocking(player)) {
         return true;
      }

      if (isShieldBlocking(player)) {
         return true;
      }

      return false;
   }

   public static boolean isSilentSwordBlocking() {
      long longValue = KEY ^ 77347448011417L;
      ModuleManager moduleManager = EvonClient.getInstance().getModuleManager();
      String[] strings = PlayerUtil.getGuard();
      Animations moduleByClass = moduleManager.getModuleByClass(Animations.class);
      NoSlow moduleByClass2 = moduleManager.getModuleByClass(NoSlow.class);
      AutoBlock moduleByClass3 = moduleManager.getModuleByClass(AutoBlock.class);
      KillAura moduleByClass4 = moduleManager.getModuleByClass(KillAura.class);
      SilentSlotManager instance = SilentSlotManager.getInstance();
      boolean flag;
      if (strings == null) {
         flag = moduleByClass2.getItemUseType() == ItemUseType.BLOCKABLE && KeybindManager.getUseState().isPressed();
      } else {
         flag = moduleByClass2.isEnabled() && moduleByClass2.getItemUseType() == ItemUseType.BLOCKABLE && KeybindManager.getUseState().isPressed();
      }

      boolean flag2 = strings == null ? moduleByClass4.isEnabled() : moduleByClass4.isEnabled() && moduleByClass4.isLagging();
      if (strings == null) {
         return ((BaseModule)moduleByClass).isEnabled();
      }

      if (!((BaseModule)moduleByClass).isEnabled()) {
         return false;
      }

      if (!moduleByClass.isSwordBlocking()) {
         return false;
      }

      if (!flag2 && !flag) {
         return false;
      }

      if (!instance.getMainHandStack(ClientContext.MINECRAFT_CLIENT.player).isIn(ItemTags.SWORDS)) {
         return false;
      }

      if (ItemUtils.isInteractiveBlock(PlayerUtil.getTargetBlock())) {
         return false;
      }

      if (ClientContext.MINECRAFT_CLIENT.player.getOffHandStack().getItem() instanceof ShieldItem) {
         return true;
      }

      return instance.getMainHandStack(ClientContext.MINECRAFT_CLIENT.player).getUseAction() == UseAction.BLOCK;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
