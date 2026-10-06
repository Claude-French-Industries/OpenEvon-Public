package club.evon.mixins;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import club.evon.ClientContext;
import club.evon.manager.SilentSlotManager;
import club.evon.util.render.SwordBlockingHelper;




@Mixin(ArmedEntityRenderState.class)
public final class ArmedEntityRenderStateMixin {
   @Redirect(
      method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/ArmedEntityRenderState;Lnet/minecraft/client/item/ItemModelManager;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getStackInArm(Lnet/minecraft/util/Arm;)Lnet/minecraft/item/ItemStack;")
   )
   private static ItemStack hookGetStackInArm(LivingEntity entity, Arm arm) {
      return entity == ClientContext.MINECRAFT_CLIENT.player && arm == ClientContext.MINECRAFT_CLIENT.player.getMainArm() && SwordBlockingHelper.isSilentSwordBlocking() ? SilentSlotManager.getInstance().getMainHandStack(ClientContext.MINECRAFT_CLIENT.player) : entity.getStackInArm(arm);
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
