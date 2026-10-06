package club.evon.event;
import org.json.JSONException;




public class BaseEvent implements Event {
   private boolean g;
   private static boolean initialized;

   public void cancel() {
      this.g = true;
   }

   public void setCanceled(boolean canceled) {
      this.g = canceled;
   }

   public boolean isCanceled() {
      return this.g;
   }

   public static void setInitialized(boolean value) {
      initialized = value;
   }

   public static boolean isInitialized() {
      return initialized;
   }

   public static boolean isNotInitialized() {
      boolean initialized2 = isInitialized();
      return !initialized2;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }

   static {
      if (!isNotInitialized()) {
         setInitialized(true);
      }
   }
}
