package club.evon.data;


public enum ColorMode {
   THEMED,
   SINGLE,
   DOUBLE;

   private static final ColorMode[] q;

   private static ColorMode[] x() {
      return new ColorMode[]{THEMED, SINGLE, DOUBLE};
   }

   static {
      q = x();
   }
}
