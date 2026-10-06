package club.evon.util;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.util.render.RenderUtil;




public final class HoverUtil {
   private static final long KEY = KeyedRandom.create(-569371252316894162L, -5157143036116447286L, MethodHandles.lookup().lookupClass()).next(125148346995211L);

   public static boolean isHovered(double mouseX, double mouseY, double x, double y, double width, double height) {
      long longValue = KEY ^ 15307817198380L;
      String[] strings = RenderUtil.getGuardState();
      double doubleValue;
      int value = (doubleValue = mouseX - x) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
      if (strings == null) {
         if (value < 0) {
            return false;
         }

         double doubleValue2;
         value = (doubleValue2 = mouseY - y) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);
      }

      if (strings == null) {
         if (value < 0) {
            return false;
         }

         double doubleValue3;
         value = (doubleValue3 = mouseX - (x + width)) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
      }

      if (strings == null) {
         if (value > 0) {
            return false;
         }

         double doubleValue4;
         value = (doubleValue4 = mouseY - (y + height)) == 0.0 ? 0 : (doubleValue4 < 0.0 ? -1 : 1);
      }

      return (boolean)(strings != null ? value : value <= 0);
   }

   private static JSONException rethrow(JSONException exception) {
      return exception;
   }
}
