package club.evon.event;
import net.minecraft.client.gui.screen.Screen;




public final class ScreenChangeEvent extends BaseEvent implements Event {
   private Screen T;

   public Screen getScreen() {
      return this.T;
   }

   public void setScreen(Screen screen) {
      this.T = screen;
   }

   public ScreenChangeEvent(Screen screen) {
      this.T = screen;
   }
}
