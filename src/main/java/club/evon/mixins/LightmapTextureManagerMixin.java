package club.evon.mixins;
import net.minecraft.client.render.LightmapTextureManager;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import club.evon.EvonClient;
import club.evon.module.impl.render.WorldEditor;




@Mixin(LightmapTextureManager.class)
public final class LightmapTextureManagerMixin {
   @Redirect(method = "update(F)V", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
   public float redirectGammaValue(Double d) {
      WorldEditor moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(WorldEditor.class);
      float floatValue = moduleByClass.isEnabled() && moduleByClass.isGamma() ? 15.0F : 1.0F;
      return d.floatValue() * floatValue;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
