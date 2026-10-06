package club.evon.event;
import org.json.JSONException;




public final class EventBus {
   private static final EventDispatcher dispatcher = new EventDispatcher();
   private static boolean enabled;

   public static void post(Object event) {
      dispatcher.dispatch(event);
   }

   public static void register(Object registrar) {
      dispatcher.register(registrar);
   }

   public static void unregister(Object registrar) {
      dispatcher.unregister(registrar);
   }

   static {
      setEnabled(true);
   }

   public static void setEnabled(boolean flag) {
      enabled = flag;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
