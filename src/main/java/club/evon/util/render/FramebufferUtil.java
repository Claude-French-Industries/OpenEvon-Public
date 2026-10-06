package club.evon.util.render;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Framebuffer;




public final class FramebufferUtil {
   public static void copy(Framebuffer sourceBuffer, Framebuffer destinationBuffer) {
      RenderSystem.getDevice()
         .createCommandEncoder()
         .copyTextureToTexture(
            sourceBuffer.getColorAttachment(),
            destinationBuffer.getColorAttachment(),
            0,
            0,
            0,
            0,
            0,
            destinationBuffer.textureWidth,
            destinationBuffer.textureHeight
         );
   }
}
