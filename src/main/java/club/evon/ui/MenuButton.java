package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.Animator;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.RenderUtil;




public abstract class MenuButton {
   private float I;
   private float a;
   private final float A;
   private final float X;
   private final float N;
   private final Animator hoverAnimator = new Animator(200, 0.0, Easing.LINEAR);
   private static final Color SHADOW_COLOR = ColorUtil.withAlpha(Color.BLACK, 0.2F);
   private static final Color BORDER_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.15F);
   private static BaseModule[] stateArray;
   private static final long KEY = KeyedRandom.create(4512180134561082051L, -1243089528880264811L, MethodHandles.lookup().lookupClass()).next(267166298772959L);

   public MenuButton(float width, float height, float radius) {
      this.A = width;
      this.X = height;
      this.N = radius;
   }

   public void render(float x, float y, double mouseX, double mouseY, Color color) {
      long longValue = KEY ^ 44339432136780L;
      BaseModule[] values = getStateArray();
      this.I = x;
      this.a = y;
      BaseModule[] values2 = values;
      double doubleValue = mouseX;
      if (values2 != null) {
         doubleValue = HoverUtil.isHovered(mouseX, mouseY, x, y, this.A, this.X) ? 1.0 : 0.0;
      }

      label31: {
         this.hoverAnimator.setTargetValue(doubleValue);
         double doubleValue2 = this.hoverAnimator.getValue();
         RenderUtil.fillRoundedRect(x, y, this.A, this.X, this.N, ColorUtil.withAlpha(color, 0.1F + 0.1F * doubleValue2));
         float floatValue = 1.0F;
         float floatValue2 = 0.5F;
         float floatValue3 = x + 0.5F;
         float floatValue4 = y + 0.5F;
         float floatValue5 = this.A - 1.0F;
         float floatValue6 = this.X - 1.0F;
         float floatValue7 = this.N;
         float floatValue8 = 1.0F;
         Color tmp = BORDER_COLOR;
         if (values2 != null) {
            RenderUtil.strokeRoundedRect(floatValue3, floatValue4, floatValue5, floatValue6, this.N, 1.0F, BORDER_COLOR);
            if (!(doubleValue2 > 0.0)) {
               break label31;
            }

            floatValue3 = x;
            floatValue4 = y;
            floatValue5 = this.A;
            floatValue6 = this.X;
            floatValue7 = this.N;
            floatValue8 = 1.0F;
            tmp = ColorUtil.withAlpha(Color.WHITE, 0.4F * doubleValue2);
         }

         RenderUtil.strokeHoverOutline(floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, tmp, (float)mouseX);
      }

      if (BaseModule.getStateArray() == null) {
         setStateArray(new BaseModule[4]);
      }
   }

   public void renderDefault(float x, float y, double mouseX, double mouseY) {
      this.render(x, y, mouseX, mouseY, new Color(239, 224, 227));
   }

   public abstract void onClick();

   public float getX() {
      return this.I;
   }

   public float getY() {
      return this.a;
   }

   public void setX(float x) {
      this.I = x;
   }

   public void setY(float y) {
      this.a = y;
   }

   public float getWidth() {
      return this.A;
   }

   public float getHeight() {
      return this.X;
   }

   public float getRadius() {
      return this.N;
   }

   static {
      BaseModule[] var10000 = new BaseModule[5];
      setStateArray(var10000);
   }

   public static void setStateArray(BaseModule[] values) {
      stateArray = values;
   }

   public static BaseModule[] getStateArray() {
      return stateArray;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
