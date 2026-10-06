package club.evon.mixins;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import club.evon.ClientContext;
import club.evon.util.BipedEntityRenderStateAccessor;
import club.evon.util.render.SwordBlockingHelper;




@Mixin(PlayerEntityModel.class)
public final class PlayerEntityModelMixin {
   @ModifyVariable(method = "setAngles(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)V", at = @At("HEAD"), argsOnly = true)
   private PlayerEntityRenderState modifyThirdPersonRenderState(PlayerEntityRenderState player) {
      LivingEntity evon$getEntity = ((BipedEntityRenderStateAccessor)player).evon$getEntity();
      if (evon$getEntity == ClientContext.MINECRAFT_CLIENT.player && SwordBlockingHelper.shouldRenderSwordBlocking(player)) {
         player.isUsingItem = true;
         player.activeHand = Hand.MAIN_HAND;
         if (player.mainArm == Arm.RIGHT) {
            player.leftArmPose = ArmPose.EMPTY;
            player.rightArmPose = ArmPose.BLOCK;
         } else {
            player.leftArmPose = ArmPose.BLOCK;
            player.rightArmPose = ArmPose.EMPTY;
         }
      }

      return player;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
