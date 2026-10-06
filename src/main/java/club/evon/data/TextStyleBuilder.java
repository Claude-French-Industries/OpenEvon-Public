package club.evon.data;
import net.minecraft.text.Style;




public class TextStyleBuilder {
   private CustomClickEvent L;
   private RunnableHoverEvent J;
   private Style B;

   public TextStyleBuilder setClickEvent(CustomClickEvent clickEvent) {
      this.L = clickEvent;
      return this;
   }

   public TextStyleBuilder setHoverEvent(RunnableHoverEvent hoverEvent) {
      this.J = hoverEvent;
      return this;
   }

   public TextStyleBuilder setStyle(Style style) {
      this.B = style;
      return this;
   }

   public TextStyleData build() {
      return new TextStyleData(this.L, this.J, this.B);
   }
}
