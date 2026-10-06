package club.evon.data;
import net.minecraft.text.Style;




public record StyledGlyph(Style style, boolean hasColor, int codePoint) {
   public boolean isColored() {
      return this.hasColor;
   }
}
