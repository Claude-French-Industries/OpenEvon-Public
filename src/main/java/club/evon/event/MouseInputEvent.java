package club.evon.event;


public final class MouseInputEvent extends BaseEvent {
   private final int r;
   private final int M;
   private double n;
   private double B;

   public int getButton() {
      return this.r;
   }

   public int getAction() {
      return this.M;
   }

   public double getHorizontal() {
      return this.n;
   }

   public double getVertical() {
      return this.B;
   }

   public MouseInputEvent(int button, int action, double horizontal, double vertical) {
      this.r = button;
      this.M = action;
      this.n = horizontal;
      this.B = vertical;
   }

   public MouseInputEvent(int button, int action) {
      this.r = button;
      this.M = action;
   }
}
