package club.evon.event;


public final class KeyPressEvent extends BaseEvent implements Event {
   private final int X;
   private int y;

   public int getAction() {
      return this.X;
   }

   public int getKey() {
      return this.y;
   }

   public void setKey(int key) {
      this.y = key;
   }

   public KeyPressEvent(int action, int key) {
      this.X = action;
      this.y = key;
   }
}
