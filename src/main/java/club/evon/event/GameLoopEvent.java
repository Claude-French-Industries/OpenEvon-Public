package club.evon.event;


public final class GameLoopEvent {
   private final boolean ticking;

   public GameLoopEvent(boolean tick) {
      this.ticking = tick;
   }

   public boolean isTicking() {
      return this.ticking;
   }
}
