package club.evon.event;
import net.minecraft.client.util.math.MatrixStack;




public final class RenderWorldEvent implements Event {
   private final float P;
   private final MatrixStack o;

   public float getTickDelta() {
      return this.P;
   }

   public MatrixStack getMatrixStack() {
      return this.o;
   }

   public RenderWorldEvent(float tickDelta, MatrixStack matrixStack) {
      this.P = tickDelta;
      this.o = matrixStack;
   }
}
