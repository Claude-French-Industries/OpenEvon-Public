package club.evon.data;


public enum SlotSwitchMode {
   NONE,
   DEFAULT,
   FULL;

   private static final SlotSwitchMode[] L;

   private static SlotSwitchMode[] O() {
      return new SlotSwitchMode[]{NONE, DEFAULT, FULL};
   }

   static {
      L = O();
   }
}
