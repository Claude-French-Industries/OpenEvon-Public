package club.evon.event;


public final class WindowResizeEvent implements Event {
   private final int Y;
   private final int O;

   public int getWidth() {
      return this.Y;
   }

   public int getHeight() {
      return this.O;
   }

   public WindowResizeEvent(int width, int height) {
      this.Y = width;
      this.O = height;
   }
}
