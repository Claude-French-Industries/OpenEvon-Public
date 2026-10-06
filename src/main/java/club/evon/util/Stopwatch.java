package club.evon.util;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.util.math.RandomUtil;




public final class Stopwatch {
   private long startTime = System.currentTimeMillis();
   private static final long KEY = KeyedRandom.create(6974182748901140546L, -7136418505392342784L, MethodHandles.lookup().lookupClass()).next(195992737726736L);

   public boolean hasElapsed(long time, boolean reset) {
      long longValue = KEY ^ 113963763652913L;
      int[] values = RandomUtil.getStateArray();
      boolean elapsed = this.hasElapsed(time);
      int[] values2 = values;
      boolean flag = elapsed;
      if (values2 == null) {
         if (elapsed) {
            if (values2 != null) {
               return reset;
            }

            if (reset) {
               this.reset();
            }
         }

         flag = elapsed;
      }

      return flag;
   }

   public boolean hasElapsed(long time) {
      long longValue = KEY ^ 115813015379658L;
      int[] values = RandomUtil.getStateArray();
      long longValue2;
      int value = (longValue2 = this.elapsed() - time) == 0L ? 0 : (longValue2 < 0L ? -1 : 1);
      if (values == null) {
         value = value >= 0 ? 1 : 0;
      }

      return value != 0;
   }

   public long elapsed() {
      return System.currentTimeMillis() - this.startTime;
   }

   public void reset() {
      this.startTime = System.currentTimeMillis();
   }

   public void addElapsed(long time) {
      this.startTime -= time;
   }

   public long getStartTime() {
      return this.startTime;
   }

   public void setStartTime(long prevTime) {
      this.startTime = prevTime;
   }

   private static JSONException a_r2(JSONException jSONException) {
      return jSONException;
   }
}
