package club.evon.setting;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import club.evon.util.KeyedRandom;




public class EnumSetting<T extends Enum<T>> extends Setting<T> {
   private final List<T> values;
   private static int[] stateArray;
   private static final long KEY = KeyedRandom.create(-208425208276526121L, -3246217414709224277L, MethodHandles.lookup().lookupClass()).next(60584139684187L);

   public EnumSetting(String name, T defaultValue) {
      super(name, (T)defaultValue);
      this.values = List.of((T[])defaultValue.getDeclaringClass().getEnumConstants());
   }

   public void setByName(String name) {
      long longValue = KEY ^ 89500133871896L;
      String state = BooleanSetting.getState();
      Iterator iterator = this.values.iterator();
      String text = state;

      while (true) {
         if (!iterator.hasNext()) {
            return;
         }

         Enum enumValue = (Enum)iterator.next();
         if (text == null) {
            break;
         }

         if (enumValue.toString().equalsIgnoreCase(name) || text == null) {
            this.setValue((T)enumValue);
            break;
         }
      }
   }

   public String getDisplayValue() {
      return club.evon.util.TextUtil.toDisplayName(this.getValue().toString());
   }

   public List<T> getValues() {
      return this.values;
   }

   public static void setStateArray(int[] values2) {
      stateArray = values2;
   }

   public static int[] d() {
      return stateArray;
   }

   static {
      if (d() != null) {
         setStateArray(new int[3]);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
