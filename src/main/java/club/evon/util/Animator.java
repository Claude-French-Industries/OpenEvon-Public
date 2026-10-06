package club.evon.util;
import java.lang.invoke.MethodHandles;
import net.minecraft.util.math.MathHelper;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.math.Easing;




public final class Animator {
   private final Stopwatch stopwatch = new Stopwatch();
   private double Q;
   private double s;
   private double o;
   private int Z;
   private Easing d;
   private static int state;
   private static final long KEY = KeyedRandom.create(-1386605988870332117L, -5136400215793006666L, MethodHandles.lookup().lookupClass()).next(95496031201189L);

   public Animator(int duration, double targetValue, Easing easing) {
      this.Z = duration;
      this.o = targetValue;
      this.d = easing;
   }

   public Double getValue() {
      long longValue = KEY ^ 13726260453286L;
      int[] values = Easing.getStateArray();
      Object tmp = this;
      if (values != null) {
         if (this.isFinished()) {
            this.Q = this.o;
            if (values != null) {
               return this.Q;
            }

            BaseModule.setStateArray(new BaseModule[3]);
         }

         tmp = this.d.getFunction().apply((double)this.stopwatch.elapsed() / this.Z);
      }

      double doubleValue = (Double)tmp;
      this.Q = MathHelper.lerp(doubleValue, this.s, this.o);
      return this.Q;
   }

   public void finish() {
      long longValue = KEY ^ 105043003009887L;
      int[] values = Easing.getStateArray();
      Stopwatch tmp = this.stopwatch;
      long longValue2 = this.Z;
      if (values != null) {
         if (this.stopwatch.hasElapsed(longValue2)) {
            return;
         }

         tmp = this.stopwatch;
         longValue2 = this.Z - this.stopwatch.elapsed();
      }

      tmp.addElapsed(longValue2);
   }

   public void setValue(double value) {
      this.setTargetValue(value);
      this.finish();
   }

   public boolean isFinished() {
      return this.stopwatch.hasElapsed(this.Z);
   }

   public void setTargetValue(double targetValue) {
      long longValue = KEY ^ 98491080308233L;
      int[] values = Easing.getStateArray();
      Animator tmp = this;
      if (values != null) {
         if (this.o == targetValue) {
            return;
         }

         this.reset();
         tmp = this;
      }

      tmp.o = targetValue;
   }

   public void reset() {
      long longValue = KEY ^ 56938172480950L;
      Easing.getStateArray();
      this.s = this.Q;
      this.stopwatch.reset();
      if (BaseModule.getStateArray() == null) {
         Easing.setStateArray(new int[5]);
      }
   }

   public Stopwatch getStopwatch() {
      return this.stopwatch;
   }

   public double getStartValue() {
      return this.s;
   }

   public double getTargetValue() {
      return this.o;
   }

   public int getDuration() {
      return this.Z;
   }

   public Easing getEasing() {
      return this.d;
   }

   public void setStartValue(double startValue) {
      this.s = startValue;
   }

   public void setDuration(int duration) {
      this.Z = duration;
   }

   public void setEasing(Easing easing) {
      this.d = easing;
   }

   public static void setState(int value) {
      state = value;
   }

   public static int getState() {
      return state;
   }

   public static int getDefaultState() {
      int value = getState();
      return value == 0 ? 1 : 0;
   }

   static {
      if (getDefaultState() != 0) {
         setState(91);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
