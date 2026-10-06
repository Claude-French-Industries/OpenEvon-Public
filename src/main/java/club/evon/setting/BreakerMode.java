package club.evon.setting;


public enum BreakerMode {
   BED,
   EGG;

   private static final BreakerMode[] VALUES;

   private static BreakerMode[] buildValues() {
      return new BreakerMode[]{BED, EGG};
   }

   static {
      VALUES = buildValues();
   }
}
