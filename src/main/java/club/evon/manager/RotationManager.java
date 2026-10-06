package club.evon.manager;
import org.json.JSONException;
import club.evon.util.RotationController;




public final class RotationManager {
   private static final MouseRotationManager mouseRotationManager = new MouseRotationManager();
   private static final RotationController rotationController = new RotationController();
   private static int state;

   public static MouseRotationManager getMouseRotationManager() {
      return mouseRotationManager;
   }

   public static RotationController getRotationController() {
      return rotationController;
   }

   static {
      setState(31);
   }

   public static void setState(int value) {
      state = value;
   }

   public static int getState() {
      return state;
   }

   public static int getStateValue() {
      int value = getState();
      return value == 0 ? 74 : 0;
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }
}
