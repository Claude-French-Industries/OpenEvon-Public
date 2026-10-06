package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.module.impl.render.Overlay;
import club.evon.setting.DoubleSetting;
import club.evon.util.Animator;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.RenderUtil;




public final class SliderComponent extends SettingComponent<DoubleSetting> {
   private boolean dragging;
   private final Animator hoverAnimator = new Animator(200, 0.0, Easing.LINEAR);
   private static final float TRACK_RADIUS = 2.25F;
   private static final long KEY = KeyedRandom.create(5588981489060152710L, -2287566062762387591L, MethodHandles.lookup().lookupClass()).next(185690514654769L);

   public SliderComponent(DoubleSetting property) {
      super(property);
   }

   public void render(int mouseX, int mouseY) {
      long longValue = KEY ^ 37204532017124L;
      int value = ThemeDropdownComponent.getState();
      super.render(mouseX, mouseY);
      float floatValue = this.getX();
      int value2 = value;
      float floatValue2 = this.getY();
      float floatValue3 = this.getWidth();
      DoubleSetting setting = this.getSetting();
      float floatValue4 = setting.J().floatValue();
      String valueOf = String.valueOf(floatValue4);
      Theme.FONT.drawText(valueOf, floatValue + floatValue3 - (Theme.FONT.getTextWidth(valueOf, 7.5F) + 4.5F), floatValue2 + 3.5F, 7.5F, Theme.TEXT_COLOR.getRGB());
      float floatValue5 = 4.5F;
      float floatValue6 = this.getWidth() - 9.0F;
      float floatValue7 = 4.5F;
      float floatValue8 = 2.25F;
      float floatValue9 = this.getX() + 4.5F;
      float floatValue10 = this.getY() + super.getHeight();
      RenderUtil.fillRoundedRect(floatValue9, floatValue10, floatValue6, 4.5F, 2.25F, ColorUtil.withAlpha(Color.WHITE, !this.isHovered(mouseX, mouseY) ? 0.1F : 0.15F));
      float floatValue11 = setting.getMinimum().floatValue();   // lower bound (was the collapsed J())
      float floatValue12 = setting.getMaximum().floatValue();
      float floatValue13 = 0.5F;
      float floatValue14 = 5.5F;
      float floatValue15 = 2.75F;
      float floatValue16 = (floatValue6 - 5.5F) * ((floatValue4 - floatValue11) / (floatValue12 - floatValue11));
      float floatValue17;
      value = (floatValue17 = floatValue4 - 0.0F) == 0.0F ? 0 : (floatValue17 < 0.0F ? -1 : 1);
      if (value2 == 0) {
         if (value > 0) {
            RenderUtil.fillShadowedGradientRect(floatValue9, floatValue10, floatValue16 + 5.5F, 4.5F, 2.25F, Overlay.getPrimaryColor(), Overlay.getSecondaryColor());
         }

         RenderUtil.fillRoundedRect(floatValue9 + floatValue16, floatValue10 - 0.5F, 5.5F, 5.5F, 2.75F, Color.WHITE);
         value = this.dragging ? 1 : 0;
      }

      if (value2 == 0) {
         if (value == 0) {
            return;
         }

         value = mouseX;
      }

      double doubleValue = (value - floatValue9) / floatValue6;
      double doubleValue2 = 1.0 / setting.getIncrement();
      setting.setValue(Math.max(floatValue11, Math.min(floatValue12, Math.round((doubleValue * (floatValue12 - floatValue11) + floatValue11) * doubleValue2) / doubleValue2)));
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
      long longValue = KEY ^ 48859458126247L;
      int value = ThemeDropdownComponent.getState();
      if (value == 0) {
         if (button != 0) {
            return;
         }

         this.dragging = true;
      }
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      this.dragging = false;
   }

   @Override
   public float getHeight() {
      return super.getHeight() + 9.0F;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
