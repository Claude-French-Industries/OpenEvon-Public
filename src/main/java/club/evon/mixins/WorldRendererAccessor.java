package club.evon.mixins;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;




@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {
   @Mutable
   @Accessor("capturedFrustum")
   public Frustum getFrustum();
}
