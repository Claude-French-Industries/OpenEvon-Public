package club.evon.event;
import net.minecraft.client.gui.DrawContext;




public final class HudRenderPassEvent implements Event {
   private final DrawContext A;
   private final float y;

   public DrawContext getDrawContext() {
      return this.A;
   }

   public float getTickDelta() {
      return this.y;
   }

   public HudRenderPassEvent(DrawContext drawContext, float tickDelta) {
      this.A = drawContext;
      this.y = tickDelta;
   }
}
