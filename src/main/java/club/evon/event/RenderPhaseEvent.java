package club.evon.event;


public final class RenderPhaseEvent {
   private final EventPhase phase;

   public RenderPhaseEvent(EventPhase direction) {
      this.phase = direction;
   }

   public EventPhase getPhase() {
      return this.phase;
   }
}
