package club.evon.data;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.ClickEvent.Action;




public record CustomClickEvent(Runnable callback) implements ClickEvent {
   public Action getAction() {
      return Action.CUSTOM;
   }

   public Runnable callback() {
      return this.callback;
   }
}
