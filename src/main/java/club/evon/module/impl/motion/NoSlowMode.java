package club.evon.module.impl.motion;
import org.json.JSONException;
import club.evon.event.ItemSlowdownEvent;
import club.evon.event.Subscribe;
import club.evon.module.SubMode;




public final class NoSlowMode extends SubMode<NoSlow> {
   private static boolean enabled;

   public NoSlowMode(String name, NoSlow parent) {
      super(name, parent);
   }

   @Subscribe
   private void onItemSlowdown(ItemSlowdownEvent event) {
      event.cancel();
   }

   public static void setEnabled(boolean value) {
      enabled = value;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
   }

   private static JSONException rethrow(JSONException exception) {
      return exception;
   }

   static {
      if (isDisabled()) {
         setEnabled(true);
      }
   }
}
