package club.evon.setting;


public enum InfiniteAuraTargetMode {
   SINGLE,
   SWITCH;

   private static final InfiniteAuraTargetMode[] VALUES;

   private static InfiniteAuraTargetMode[] buildValues() {
      return new InfiniteAuraTargetMode[]{SINGLE, SWITCH};
   }

   static {
      VALUES = buildValues();
   }
}
