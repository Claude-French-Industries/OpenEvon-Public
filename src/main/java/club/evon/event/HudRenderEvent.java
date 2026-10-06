package club.evon.event;
import net.minecraft.client.gui.DrawContext;




public final class HudRenderEvent implements Event {
   private final DrawContext T;
   private final float Z;
   private final double N;
   private final double q;
   private static String dummyString;

   public DrawContext getDrawContext() {
      return this.T;
   }

   public float getTickDelta() {
      return this.Z;
   }

   public double getMouseX() {
      return this.N;
   }

   public double getMouseY() {
      return this.q;
   }

   public HudRenderEvent(DrawContext drawContext, float tickDelta, double mouseX, double mouseY) {
      this.T = drawContext;
      this.Z = tickDelta;
      this.N = mouseX;
      this.q = mouseY;
   }

   public static void setDummyString(String value) {
      dummyString = value;
   }

   public static String getDummyString() {
      return dummyString;
   }

   static {
      if (getDummyString() != null) {
         setDummyString("rEAKqb");
      }
   }
}
