package club.evon.ui;
import club.evon.module.BaseModule;



public final class NoOpWidget implements RenderableElement {
   private static BaseModule[] stateArray;

   @Override
   public void draw(float x, float y) {
   }

   @Override
   public float getWidth() {
      return 0.0F;
   }

   @Override
   public float getHeight() {
      return 0.0F;
   }

   @Override
   public int getPriority() {
      return 0;
   }

   public static void setStateArray(BaseModule[] values) {
      stateArray = values;
   }

   public static BaseModule[] getStateArray() {
      return stateArray;
   }

   static {
      if (getStateArray() != null) {
         setStateArray(new BaseModule[2]);
      }
   }
}
