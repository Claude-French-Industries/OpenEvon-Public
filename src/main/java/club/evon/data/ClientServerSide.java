package club.evon.data;


public enum ClientServerSide {
   CLIENT,
   SERVER;

   private static final ClientServerSide[] X;

   private static ClientServerSide[] a() {
      return new ClientServerSide[]{CLIENT, SERVER};
   }

   static {
      X = a();
   }
}
