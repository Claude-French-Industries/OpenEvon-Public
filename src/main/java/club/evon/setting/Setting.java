package club.evon.setting;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import org.json.JSONException;
import club.evon.config.ConfigManager;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public abstract class Setting<T> {
   private final String Q;
   private BooleanSupplier U;
   private T d;
   private static String[] stateArray;
   private static final long KEY = KeyedRandom.create(-7637434297370900796L, 1589824554498848735L, MethodHandles.lookup().lookupClass()).next(171254262767149L);

   public Setting(String name, T value) {
      this.Q = name;
      this.d = (T)value;
   }

   public final void setVisible(BooleanSupplier visible) {
      this.U = visible;
   }

   public final boolean isVisible() {
      String[] strings;
      boolean flag;
      label32: {
         label35: {
            long longValue = KEY ^ 139279609542840L;
            strings = getStateArray();
            BooleanSupplier tmp = this.U;
            if (strings == null) {
               if (this.U == null) {
                  break label35;
               }

               tmp = this.U;
            }

            flag = tmp.getAsBoolean();
            if (strings != null) {
               break label32;
            }

            if (!flag) {
               flag = false;
               break label32;
            }
         }

         flag = true;
      }

      if (strings != null) {
         BaseModule.setStateArray(new BaseModule[3]);
      }

      return flag;
   }

   public List<Setting<?>> getSettings() {
      return null;
   }

   public String getName() {
      return this.Q;
   }

   public BooleanSupplier getVisible() {
      return this.U;
   }

   public T getValue() {
      return this.d;
   }

   public void setValue(T value) {
      // Every setting type funnels through here (Double/Enum/Color/String/Position/Mode/Bind),
      // so this single hook covers the whole GUI and every ".setting" style command. The dirty
      // flag is debounced by ConfigManager, so dragging a slider does not hammer the disk.
      boolean changed = !Objects.equals(this.d, value);
      this.d = (T)value;
      if (changed) {
         ConfigManager.markDirty();
      }
   }

   public static void setStateArray(String[] strings) {
      stateArray = strings;
   }

   public static String[] getStateArray() {
      return stateArray;
   }

   static {
      if (getStateArray() != null) {
         setStateArray(new String[1]);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
