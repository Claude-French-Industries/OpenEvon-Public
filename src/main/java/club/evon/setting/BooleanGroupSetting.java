package club.evon.setting;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.util.KeyedRandom;




public class BooleanGroupSetting extends Setting<BooleanSetting[]> {
   private static boolean flag;
   private static final long KEY = KeyedRandom.create(1547606234334889385L, -2161277111399607946L, MethodHandles.lookup().lookupClass()).next(189139624876006L);

   public BooleanGroupSetting(String name, BooleanSetting... value) {
      super(name, value);
   }

   public BooleanSetting getByName(String name) {
      long longValue = KEY ^ 3126941603427L;

      for (BooleanSetting setting : this.getValue()) {
         if (setting.getName().equalsIgnoreCase(name)) {
            return setting;
         }
      }

      return null;
   }

   public static void setFlag(boolean flag2) {
      flag = flag2;
   }

   public static boolean isFlagSet() {
      return flag;
   }

   public static boolean isFlagUnset() {
      boolean flagSet = isFlagSet();
      return !flagSet;
   }

   static {
      if (isFlagSet()) {
         setFlag(true);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
