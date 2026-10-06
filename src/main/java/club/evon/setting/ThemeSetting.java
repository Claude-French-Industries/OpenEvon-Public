package club.evon.setting;


public class ThemeSetting<T extends Enum<T>> extends EnumSetting<T> {
   public ThemeSetting(String name, T defaultValue) {
      super(name, (T)defaultValue);
   }
}
