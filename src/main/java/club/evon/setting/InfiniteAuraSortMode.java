package club.evon.setting;


public enum InfiniteAuraSortMode {
   DISTANCE,
   HEALTH,
   HURTTIME;

   private static final InfiniteAuraSortMode[] VALUES;

   private static InfiniteAuraSortMode[] buildValues() {
      return new InfiniteAuraSortMode[]{DISTANCE, HEALTH, HURTTIME};
   }

   static {
      VALUES = buildValues();
   }
}
