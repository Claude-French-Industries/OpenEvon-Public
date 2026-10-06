package club.evon.mixins;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.OrderedText;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import club.evon.EvonClient;
import club.evon.module.impl.misc.Streamer;
import club.evon.util.render.ThemedOrderedText;




@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {
   @ModifyArg(
      method = "getWidth(Ljava/lang/String;)I",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextHandler;getWidth(Ljava/lang/String;)F"),
      index = 0
   )
   @Nullable
   private String injectNameProtectWidthA(@Nullable String text) {
      return EvonClient.getInstance().getModuleManager().getModuleByClass(Streamer.class).isEnabled() && text != null ? Streamer.censor(text) : text;
   }

   @ModifyArg(
      method = "getWidth(Lnet/minecraft/text/OrderedText;)I",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextHandler;getWidth(Lnet/minecraft/text/OrderedText;)F"),
      index = 0
   )
   private OrderedText injectNameProtectWidthB(OrderedText text) {
      return EvonClient.getInstance().getModuleManager().getModuleByClass(Streamer.class).isEnabled() ? new ThemedOrderedText(text) : text;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
