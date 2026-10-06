package club.evon.data;
import net.minecraft.text.Style;




public class TextStyleData {
   private CustomClickEvent i;
   private RunnableHoverEvent F;
   private Style v;

   public CustomClickEvent getClickEvent() {
      return this.i;
   }

   public RunnableHoverEvent getHoverEvent() {
      return this.F;
   }

   public Style getStyle() {
      return this.v;
   }

   public TextStyleData(CustomClickEvent clickEvent, RunnableHoverEvent hoverEvent, Style style) {
      this.i = clickEvent;
      this.F = hoverEvent;
      this.v = style;
   }
}
