package club.evon.event;


public final class JumpEvent extends BaseEvent {
   private final boolean n;

   public boolean isSprinting() {
      return this.n;
   }

   public JumpEvent(boolean sprinting) {
      this.n = sprinting;
   }
}
