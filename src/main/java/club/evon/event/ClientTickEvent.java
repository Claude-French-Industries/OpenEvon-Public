package club.evon.event;


public final class ClientTickEvent implements Event {
   private static int[] state;

   public static void setState(int[] value) {
      state = value;
   }

   public static int[] getState() {
      return state;
   }

   static {
      if (getState() == null) {
         setState(new int[2]);
      }
   }
}
