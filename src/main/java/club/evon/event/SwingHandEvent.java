package club.evon.event;
import net.minecraft.util.Hand;




public final class SwingHandEvent extends BaseEvent {
   private final Hand S;

   public Hand getHand() {
      return this.S;
   }

   public SwingHandEvent(Hand hand) {
      this.S = hand;
   }
}
