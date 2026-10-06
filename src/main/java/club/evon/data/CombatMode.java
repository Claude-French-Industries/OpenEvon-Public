package club.evon.data;


public enum CombatMode {
   MODERN,
   LEGACY;

   private static final CombatMode[] i;

   private static CombatMode[] g() {
      return new CombatMode[]{MODERN, LEGACY};
   }

   static {
      i = g();
   }
}
