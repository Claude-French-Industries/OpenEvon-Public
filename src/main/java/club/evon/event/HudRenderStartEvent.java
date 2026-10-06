package club.evon.event;
import net.minecraft.client.gui.DrawContext;




public final class HudRenderStartEvent implements Event {
   private final DrawContext H;
   private final float A;

   public DrawContext getDrawContext() {
      return this.H;
   }

   public float getTickDelta() {
      return this.A;
   }

   public HudRenderStartEvent(DrawContext drawContext, float tickDelta) {
      this.H = drawContext;
      this.A = tickDelta;
   }
}
