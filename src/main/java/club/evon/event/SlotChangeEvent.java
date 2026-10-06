package club.evon.event;


public final class SlotChangeEvent extends BaseEvent {
   private int G;

   public int getSlot() {
      return this.G;
   }

   public SlotChangeEvent(int slot) {
      this.G = slot;
   }
}
