package club.evon.mixins;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import club.evon.EvonClient;
import club.evon.module.BaseModule;
import club.evon.module.impl.render.Animations;




@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
   private PlayerEntityRendererMixin(Context context, PlayerEntityModel player, float floatValue) {
      super(context, player, floatValue);
   }

   @Redirect(
      method = "getArmPose(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z")
   )
   private static boolean cancelShieldPose(ItemStack stack, @Local(argsOnly = true) Hand hand) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      return ((BaseModule)moduleByClass).isEnabled() && moduleByClass.isHideShield() && hand == Hand.OFF_HAND && stack.getItem() instanceof ShieldItem ? true : stack.isEmpty();
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
