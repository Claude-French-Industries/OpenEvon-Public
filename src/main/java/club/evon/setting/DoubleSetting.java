package club.evon.setting;
import net.minecraft.util.math.MathHelper;
import club.evon.module.BaseModule;




public final class DoubleSetting extends Setting<Double> {
   private final Double minimum;
   private final Double maximum;
   private final Double increment;
   private static BaseModule[] stateArray;

   public DoubleSetting(String name, double defaultValue, double minimumValue, double maximumValue, double incrementalValue) {
      super(name, defaultValue);
      this.minimum = minimumValue;
      this.maximum = maximumValue;
      this.increment = incrementalValue;
   }

   public void setValue(Double value) {
      double doubleValue = 1.0 / this.increment;
      super.setValue(Math.round(MathHelper.clamp(value, this.minimum, this.maximum) * doubleValue) / doubleValue);
   }

   /**
    * Current value of this setting (the inherited {@link Setting#getValue()}).
    *
    * <p>Bytecode artefact: the original class declared <em>two</em> methods named {@code J}
    * that differed only by return type - the inherited value accessor
    * ({@code B.J:()Ljava/lang/Object;}, returning the mutable value field) and a
    * {@code Double}-returning accessor for the {@code final} {@code minimum} field. Java
    * cannot declare both, so the decompiler kept only the minimum-returning one and all 76
    * call sites (config serialisation, the GUI sliders, and every module range/CPS/delay/
    * speed read) silently got the minimum instead of the current value. That is what made
    * e.g. the Animations hand offsets read -2.0 and push the first-person hand out of view.
    * {@code J()} is restored to the value accessor; use {@link #getMinimum()} for the bound.
    */
   public Double J() {
      return super.getValue();
   }

   public Double getMinimum() {
      return this.minimum;
   }

   public Double getMaximum() {
      return this.maximum;
   }

   public Double getIncrement() {
      return this.increment;
   }

   public static void setStateArray(BaseModule[] values) {
      stateArray = values;
   }

   public static BaseModule[] X() {
      return stateArray;
   }

   static {
      if (X() != null) {
         setStateArray(new BaseModule[2]);
      }
   }
}
