package club.evon.data;


public enum ServerMode {
   VANILLA,
   HYPIXEL;

   private static final ServerMode[] m;

   private static ServerMode[] y() {
      return new ServerMode[]{VANILLA, HYPIXEL};
   }

   static {
      m = y();
   }
}
