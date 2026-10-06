package club.evon.manager;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeybindAction;
import club.evon.util.KeyedRandom;




public final class CallbackRegistry {
   private final HashMap<String, KeybindAction> callbacks;
   private static String[] state;
   private static final long xorKey = KeyedRandom.create(-2161248144975047482L, -1293900846984056735L, MethodHandles.lookup().lookupClass()).next(198515108822797L);

   public CallbackRegistry() {
      super();

      long longValue = xorKey ^ 40498346555118L;
      String[] strings = getState();
      String[] strings2 = strings;
      this.callbacks = new HashMap<>();
      if (strings2 == null) {
         BaseModule.setStateArray(new BaseModule[1]);
      }
      }

   private void registerCallback(String name, KeybindAction action) {
   }

   public HashMap<String, KeybindAction> getCallbacks() {
      return this.callbacks;
   }

   public static void setState(String[] strings) {
      state = strings;
   }

   public static String[] getState() {
      return state;
   }

   static {
      if (getState() == null) {
         setState(new String[4]);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
