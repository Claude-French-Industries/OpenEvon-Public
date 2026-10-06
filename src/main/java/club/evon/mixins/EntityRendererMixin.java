package club.evon.mixins;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import club.evon.EvonClient;
import club.evon.module.impl.render.ESP;




@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
   @Inject(method = "getDisplayName(Lnet/minecraft/entity/Entity;)Lnet/minecraft/text/Text;", at = @At("HEAD"), cancellable = true)
   private void onRenderLabel(T t, CallbackInfoReturnable<Text> callbackInfoReturnableText) {
      ESP moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(ESP.class);
      if (moduleByClass.shouldRenderNameTag(t)) {
         callbackInfoReturnableText.cancel();
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
