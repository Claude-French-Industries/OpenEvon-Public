package club.evon.setting;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.util.KeyedRandom;




public final class BooleanSetting extends Setting<Boolean> {
   private static String state;
   private static final long KEY = KeyedRandom.create(6040199669455245941L, 4798167381918504979L, MethodHandles.lookup().lookupClass()).next(193512925296424L);

   public BooleanSetting(String name, Boolean defaultValue) {
      super(name, defaultValue);
   }

   public Boolean I() {
      long longValue = KEY ^ 101577741786106L;
      String state2 = getState();
      boolean visible = this.isVisible();
      if (state2 != null) {
         if (!visible) {
            return false;
         }

         visible = (Boolean)super.getValue();
      }

      if (state2 == null) {
         return visible;
      } else {
         return visible ? true : false;
      }
   }

   public static void setState(String value) {
      state = value;
   }

   public static String getState() {
      return state;
   }

   static {
      if (getState() == null) {
         setState("sPc1nc");
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
