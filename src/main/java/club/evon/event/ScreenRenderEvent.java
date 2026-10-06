package club.evon.event;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class ScreenRenderEvent implements Event {
   private final Screen K;
   private final DrawContext e;
   private final float z;
   private final float J;
   private final float H;
   private static final long xorKey = KeyedRandom.create(-984923575376952809L, 1916388770959868942L, MethodHandles.lookup().lookupClass()).next(196666296816045L);

   public Screen getScreen() {
      return this.K;
   }

   public DrawContext getDrawContext() {
      return this.e;
   }

   public float getTickDelta() {
      return this.z;
   }

   public float getMouseX() {
      return this.J;
   }

   public float getMouseY() {
      return this.H;
   }

   public ScreenRenderEvent(Screen screen, DrawContext drawContext, float tickDelta, float mouseX, float mouseY) {
      super();

      long longValue = xorKey ^ 47705817930911L;
      HudRenderEvent.getDummyString();
      this.K = screen;
      this.e = drawContext;
      this.z = tickDelta;
      this.J = mouseX;
      this.H = mouseY;
      if (BaseModule.getStateArray() == null) {
         HudRenderEvent.setDummyString("osFMtb");
      }
      }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
