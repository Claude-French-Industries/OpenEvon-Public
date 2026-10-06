package club.evon.data;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;
import club.evon.util.Stopwatch;




public final class DamageTracker {
   private final Stopwatch stopwatch;
   private double w;
   private static boolean L;
   private static final long a = KeyedRandom.create(-3007801067186827397L, 8394160230694227568L, MethodHandles.lookup().lookupClass()).next(181967395460984L);

   public DamageTracker(double damage) {
      super();

      long longValue = a ^ 93209161454754L;
      boolean tmp = k();
      boolean flag = tmp;
      this.stopwatch = new Stopwatch();
      this.recordDamage(false, damage);
      if (!flag) {
         BaseModule.setStateArray(new BaseModule[1]);
      }
      }

   public void recordDamage(boolean reset, double damage) {
      long longValue = a ^ 54086757563752L;
      boolean flag = C();
      if (!flag) {
         if (reset) {
            this.stopwatch.reset();
         }

         this.w = damage;
      }

      if (BaseModule.getStateArray() == null) {
         i(!flag);
      }
   }

   public long getElapsedTime() {
      return this.stopwatch.elapsed();
   }

   public double getDamage() {
      return this.w;
   }

   public static void i(boolean flag) {
      L = flag;
   }

   public static boolean k() {
      return L;
   }

   public static boolean C() {
      boolean flag = k();
      return !flag;
   }

   static {
      if (!k()) {
         i(true);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
