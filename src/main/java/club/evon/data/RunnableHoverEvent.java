package club.evon.data;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.HoverEvent.Action;




public record RunnableHoverEvent(Runnable runnable) implements HoverEvent {
   public Action getAction() {
      return Action.SHOW_TEXT;
   }
}
