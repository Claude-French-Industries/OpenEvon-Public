package club.evon.data;


public enum TargetSortMode {
   DISTANCE,
   HEALTH,
   HURTTIME;

   private static final TargetSortMode[] VALUES;

   private static TargetSortMode[] buildValues() {
      return new TargetSortMode[]{DISTANCE, HEALTH, HURTTIME};
   }

   static {
      VALUES = buildValues();
   }
}
