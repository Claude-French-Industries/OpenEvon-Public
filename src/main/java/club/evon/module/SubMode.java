package club.evon.module;
import java.util.ArrayList;
import java.util.Arrays;
import club.evon.event.EventBus;
import club.evon.setting.Setting;




public abstract class SubMode<T extends BaseModule> {
   public final String j;
   public final T a;
   public final ArrayList<Setting<?>> settings = new ArrayList<>();
   private static int[] stateArray;

   public final void addSettings(Setting<?>... properties) {
      this.settings.addAll(Arrays.asList(properties));
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public boolean isParentEnabled() {
      return this.getParent().isEnabled();
   }

   public final void enable() {
      EventBus.register(this);
      this.onEnable();
   }

   public void disable() {
      EventBus.unregister(this);
      this.onDisable();
   }

   public String getName() {
      return this.j;
   }

   public T getParent() {
      return this.a;
   }

   public ArrayList<Setting<?>> getSettings() {
      return this.settings;
   }

   public SubMode(String name, T parent) {
      this.j = name;
      this.a = (T)parent;
   }

   public static void setStateArray(int[] values) {
      stateArray = values;
   }

   public static int[] getStateArray() {
      return stateArray;
   }

   static {
      if (getStateArray() != null) {
         setStateArray(new int[2]);
      }
   }
}
