package club.evon.util;
import java.lang.invoke.MethodHandles;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.json.JSONException;
import club.evon.util.render.RenderUtil;




public final class LegacyTextCollector implements CharacterVisitor {
   private final StringBuilder builder = new StringBuilder();
   private Formatting lastFormatting = null;
   private static final long KEY = KeyedRandom.create(5958780896404922605L, 8119651165914492289L, MethodHandles.lookup().lookupClass()).next(85229312387753L);

   public boolean accept(int index, Style style, int codePoint) {
      long longValue = KEY ^ 140095906378325L;
      String[] strings = RenderUtil.getGuardState();
      Style tmp = style;
      if (strings == null) {
         if (style.isBold()) {
            this.builder.append(Formatting.BOLD);
            this.lastFormatting = Formatting.BOLD;
         }

         tmp = style;
      }

      if (tmp.getColor() != null) {
         Formatting[] values = Formatting.values();
         int length = values.length;
         int index2 = 0;

         while (index2 < length) {
            Formatting formatting = values[index2];
            if (strings == null) {
               boolean color = formatting.isColor();
               if (strings != null) {
                  return color;
               }

               label54:
               if (color) {
                  Formatting formatting2 = formatting;
                  if (strings == null) {
                     if (formatting.getColorValue() != style.getColor().getRgb()) {
                        break label54;
                     }

                     formatting2 = formatting;
                  }

                  if (formatting2 != this.lastFormatting) {
                     this.builder.append(formatting);
                     this.lastFormatting = formatting;
                     if (strings == null) {
                        break;
                     }
                  }
               }

               index2++;
            }

            if (strings != null) {
               break;
            }
         }
      }

      this.builder.append(new String(Character.toChars(codePoint)));
      return true;
   }

   public String getText() {
      return this.builder.toString();
   }

   private static JSONException ignoreException(JSONException exception) {
      return exception;
   }
}
