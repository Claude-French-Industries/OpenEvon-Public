package club.evon.event;


public class MouseInteractionEvent implements Event {
   private final int N;

   public int getInteractionCode() {
      return this.N;
   }

   public MouseInteractionEvent(int interactionCode) {
      this.N = interactionCode;
   }
}
