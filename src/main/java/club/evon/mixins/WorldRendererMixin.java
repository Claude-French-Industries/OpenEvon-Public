package club.evon.mixins;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.manager.FrustumManager;




@Mixin(WorldRenderer.class)
public final class WorldRendererMixin {
   @Redirect(
      method = "getTransparencyPostEffectProcessor()Lnet/minecraft/client/gl/PostEffectProcessor;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isFabulousGraphicsOrBetter()Z")
   )
   private boolean redirectFabulousGraphics() {
      return true;
   }

   @Inject(
      method = "render(Lnet/minecraft/client/util/ObjectAllocator;Lnet/minecraft/client/render/RenderTickCounter;ZLnet/minecraft/client/render/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V",
      at = @At(
         value = "INVOKE_ASSIGN",
         target = "Lnet/minecraft/client/render/WorldRenderer;setupFrustum(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/client/render/Frustum;",
         shift = Shift.AFTER
      )
   )
   private void evon$hookFrustum$render(CallbackInfo callbackInfo, @Local Frustum frustum) {
      FrustumManager.setFrustum(frustum);
   }
}
