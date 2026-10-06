package club.evon.event;
import club.evon.data.InteractionAction;



public final class SyncSelectedSlotEvent extends BaseEvent {
   private final InteractionAction v;

   public InteractionAction getCaller() {
      return this.v;
   }

   public SyncSelectedSlotEvent(InteractionAction caller) {
      this.v = caller;
   }
}
