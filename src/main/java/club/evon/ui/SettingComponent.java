package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.setting.Setting;
import club.evon.util.KeyedRandom;




public class SettingComponent<T extends Setting<?>> extends AbstractGuiComponent {
   private final T setting;
   private static int state;
   private static final long KEY = KeyedRandom.create(-3734372755062442346L, -6016942586133173802L, MethodHandles.lookup().lookupClass()).next(27808592885690L);

   public SettingComponent(T property) {
      super();

      long longValue = KEY ^ 50113782123357L;
      int value = getState();
      this.setting = (T)property;
      int value2 = value;
      if (BaseModule.getStateArray() == null) {
         setState(++value2);
      }
      }

   @Override
   public void render(int mouseX, int mouseY) {
      long longValue = KEY ^ 32385928775654L;
      int value = getStateFlag();
      float floatValue = this.getX();
      float floatValue2 = this.getY();
      Theme.FONT.drawText(this.setting.getName(), floatValue + 4.5F, floatValue2 + 3.5F, 7.5F, this.getLabelColor().getRGB());
      int value2 = value;
      if (value2 == 0) {
         BaseModule.setStateArray(new BaseModule[3]);
      }
   }

   @Override
   public boolean isVisible() {
      return this.setting.isVisible();
   }

   public Color getLabelColor() {
      return Theme.TEXT_COLOR;
   }

   public T getSetting() {
      return this.setting;
   }

   public static void setState(int value) {
      state = value;
   }

   public static int getState() {
      return state;
   }

   public static int getStateFlag() {
      int value = getState();
      return value == 0 ? 87 : 0;
   }

   static {
      if (getStateFlag() == 0) {
         setState(118);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
