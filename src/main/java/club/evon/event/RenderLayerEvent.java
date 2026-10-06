package club.evon.event;
import net.minecraft.client.render.RenderLayer;




public final class RenderLayerEvent {
   private RenderLayer O;

   public RenderLayerEvent(RenderLayer renderLayer) {
      this.O = renderLayer;
   }

   public RenderLayer getRenderLayer() {
      return this.O;
   }

   public void setRenderLayer(RenderLayer renderLayer) {
      this.O = renderLayer;
   }
}
