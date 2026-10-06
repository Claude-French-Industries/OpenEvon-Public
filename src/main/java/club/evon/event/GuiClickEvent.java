package club.evon.event;
import net.minecraft.client.gui.Click;




public final class GuiClickEvent implements Event {
   private final Click S;
   private boolean O;

   public Click getClick() {
      return this.S;
   }

   public boolean isReleased() {
      return this.O;
   }

   public GuiClickEvent(Click click, boolean released) {
      this.S = click;
      this.O = released;
   }
}
