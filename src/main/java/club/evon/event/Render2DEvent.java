package club.evon.event;
import net.minecraft.client.gui.DrawContext;




public final class Render2DEvent implements Event {
   private final DrawContext X;
   private final float A;

   public DrawContext getDrawContext() {
      return this.X;
   }

   public float getTickDelta() {
      return this.A;
   }

   public Render2DEvent(DrawContext drawContext, float tickDelta) {
      this.X = drawContext;
      this.A = tickDelta;
   }
}
