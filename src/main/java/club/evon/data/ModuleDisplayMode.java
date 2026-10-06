package club.evon.data;


public enum ModuleDisplayMode {
   ALL,
   NON_RENDER,
   BOUND;

   private static final ModuleDisplayMode[] s;

   private static ModuleDisplayMode[] V() {
      return new ModuleDisplayMode[]{ALL, NON_RENDER, BOUND};
   }

   static {
      s = V();
   }
}
