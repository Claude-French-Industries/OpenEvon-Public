package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.setting.BooleanSetting;
import club.evon.util.Animator;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.RenderUtil;




public class CheckboxComponent extends AbstractGuiComponent {
   private final BooleanSetting setting;
   private final Animator checkAnimator;
   private static String[] strings;
   private static final long KEY = KeyedRandom.create(-999579470410755135L, -7183248333826353285L, MethodHandles.lookup().lookupClass()).next(40656597054842L);

   public CheckboxComponent(BooleanSetting property) {
      super();
      long longValue = KEY ^ 2986692982970L;
      this.checkAnimator = new Animator(200, 0.0, Easing.LINEAR);
      this.setting = property;
      if (BaseModule.getStateArray() == null) {
         setStrings(new String[5]);
      }
   }

   @Override
   public void render(int mouseX, int mouseY) {
      String[] strings2;
      label57: {
         long longValue = KEY ^ 102763847335121L;
         strings2 = getStrings();
         this.checkAnimator.setTargetValue(this.setting.I() ? 1.0 : 0.0);
         RenderUtil.fillRoundedRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 2.0F, ColorUtil.withAlpha(Color.WHITE, this.isHovered(mouseX, mouseY) ? 0.15F : 0.1F));
         float floatValue = this.checkAnimator.getValue().floatValue();
         float floatValue2 = floatValue;
         float floatValue3 = 0.0F;
         if (strings2 == null) {
            if (!(floatValue > 0.0F)) {
               break label57;
            }

            floatValue2 = this.getX();
            floatValue3 = this.getY();
         }

         RenderUtil.fillRoundedRect(floatValue2, floatValue3, this.getWidth(), this.getHeight(), 2.0F, ColorUtil.withAlpha(Color.WHITE, 0.1F * floatValue));
      }

      String text;
      Color color;
      label61: {
         text = this.setting.getName();
         float floatValue4 = 6.5F;
         float floatValue5 = this.checkAnimator.getValue().floatValue();
         float floatValue6;
         int value = (floatValue6 = floatValue5 - 1.0F) == 0.0F ? 0 : (floatValue6 < 0.0F ? -1 : 1);
         if (strings2 == null) {
            if (value == 0) {
               color = Color.WHITE;
               if (strings2 == null) {
                  break label61;
               }

               BaseModule.setStateArray(new BaseModule[3]);
            }

            float floatValue7;
            value = (floatValue7 = floatValue5 - 0.0F) == 0.0F ? 0 : (floatValue7 < 0.0F ? -1 : 1);
         }

         if (strings2 == null) {
            if (value == 0) {
               color = Theme.TEXT_COLOR;
               if (strings2 == null) {
                  break label61;
               }
            }

            value = (int)(Theme.TEXT_COLOR.getRed() + (255 - Theme.TEXT_COLOR.getRed()) * floatValue5);
         }

         int value2 = value;
         color = new Color(value2, value2, value2);
      }

      Theme.FONT.drawText(text, this.getX() + (this.getWidth() - Theme.FONT.getTextWidth(text, 6.5F)) / 2.0F, 1.0F + this.getY() + (this.getHeight() - Theme.FONT.getTextHeight(text, 6.5F)) / 2.0F, 6.5F, color.getRGB());
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
      String[] strings2;
      CheckboxComponent checkboxComponent;
      label40: {
         long longValue = KEY ^ 88850841127570L;
         strings2 = getStrings();
         boolean tmp = button != 0;
         if (strings2 == null) {
            if (button != 0) {
               return;
            }

            checkboxComponent = this;
            if (strings2 != null) {
               break label40;
            }

            tmp = this.isHovered(mouseX, mouseY);
         }

         if (!tmp) {
            return;
         }

         checkboxComponent = this;
      }

      BooleanSetting setting2 = checkboxComponent.setting;
      boolean tmp2 = this.setting.I();
      if (strings2 == null) {
         tmp2 = !tmp2;
      }

      setting2.setValue(tmp2);
   }

   @Override
   public boolean isVisible() {
      return this.setting.isVisible();
   }

   public static void setStrings(String[] strings2) {
      strings = strings2;
   }

   public static String[] getStrings() {
      return strings;
   }

   static {
      if (getStrings() != null) {
         setStrings(new String[4]);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
