package club.evon.data;
import club.evon.api.WebSocketMessage;



public final class IrcRecordThree extends WebSocketMessage {
   private static int[] data;

   public IrcRecordThree() {
      super(3);
   }

   public static void setData(int[] values) {
      data = values;
   }

   public static int[] getData() {
      return data;
   }

   static {
      if (getData() == null) {
         setData(new int[2]);
      }
   }
}
