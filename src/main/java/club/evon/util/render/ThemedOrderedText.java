package club.evon.util.render;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import org.json.JSONException;
import club.evon.data.Codepoint;
import club.evon.data.StyledGlyph;
import club.evon.data.TextSpan;
import club.evon.module.BaseModule;
import club.evon.module.impl.misc.Streamer;
import club.evon.util.KeyedRandom;
import club.evon.util.TextSpanParser;




public class ThemedOrderedText implements OrderedText {
   private final List<StyledGlyph> glyphs;
   private static final long KEY = KeyedRandom.create(-6719074980873505722L, 2937267724529178991L, MethodHandles.lookup().lookupClass()).next(210696054763605L);

   public ThemedOrderedText(OrderedText original) {
      super();

      long longValue = KEY ^ 98490802029442L;
      int value = Streamer.getStateFlag();
      this.glyphs = new ArrayList<>(64);
      ArrayList list = new ArrayList(64);
      int value2 = value;
      original.accept((charIndex, style, codePoint) -> collectGlyph(list, charIndex, style, codePoint));
      StringBuilder builder = new StringBuilder();
      Iterator iterator = list.iterator();
      StringBuilder stringBuilder;

      while (true) {
         if (iterator.hasNext()) {
            StyledGlyph styledGlyph = (StyledGlyph)iterator.next();
            stringBuilder = builder.appendCodePoint(styledGlyph.codePoint());
            if (value2 == 0) {
               break;
            }

            if (value2 != 0) {
               continue;
            }

            BaseModule.setStateArray(new BaseModule[3]);
         }

         stringBuilder = builder;
         break;
      }

      String toString = stringBuilder.toString();
      List parse = TextSpanParser.parse(toString);
      int index = 0;
      int index2 = 0;

      while (index2 < list.size()) {
         TextSpan textSpan;
         label96: {
            textSpan = index < parse.size() ? (TextSpan)parse.get(index) : null;
            TextSpan textSpan2 = textSpan;
            if (value2 != 0) {
               if (textSpan == null) {
                  value = -1;
                  break label96;
               }

               textSpan2 = textSpan;
            }

            value = textSpan2.range.start;
         }

         label90: {
            label89: {
               int value3 = value;
               value = value3;
               int value4 = index2;
               if (value2 != 0) {
                  if (value3 == index2) {
                     value = ((StyledGlyph)list.get(value3)).isColored() ? 1 : 0;
                     if (value2 != 0) {
                        if (value != 0) {
                           index++;
                           if (value2 != 0) {
                              continue;
                           }
                        }

                        value = textSpan.colorProvider.getColor();
                     }

                     int value5 = value;
                     Iterator iterator2 = textSpan.codepoints.iterator();

                     while (true) {
                        if (iterator2.hasNext()) {
                           Codepoint codepoint = (Codepoint)iterator2.next();
                           this.glyphs.add(new StyledGlyph(((StyledGlyph)list.get(index2)).style().withColor(value5), false, codepoint.value));
                           if (value2 == 0) {
                              break;
                           }

                           if (value2 != 0) {
                              continue;
                           }
                        }

                        index2 = textSpan.range.end + 1;
                        index++;
                        break;
                     }

                     if (value2 != 0) {
                        break label90;
                     }
                  }

                  value = value3;
                  if (value2 == 0) {
                     break label89;
                  }

                  value4 = -1;
               }

               value = value != value4 ? value3 : list.size();
            }

            int value6 = value;
            this.glyphs.addAll(list.subList(index2, value6));
            index2 = value6;
         }

         if (value2 == 0) {
            break;
         }
      }
      }

   public boolean accept(CharacterVisitor visitor) {
      long longValue = KEY ^ 48687099466591L;
      int index = 0;
      int value = Streamer.getState();
      Iterator iterator = this.glyphs.iterator();
      int value2 = value;
      boolean flag;

      while (true) {
         if (iterator.hasNext()) {
            StyledGlyph styledGlyph = (StyledGlyph)iterator.next();
            if (value2 == 0) {
               flag = visitor.accept(index, styledGlyph.style(), styledGlyph.codePoint());
               if (value2 != 0) {
                  break;
               }

               if (!flag) {
                  return false;
               }

               index++;
            }

            if (value2 == 0) {
               continue;
            }
         }

         flag = true;
         break;
      }

      return flag;
   }

   private static boolean collectGlyph(List glyphList, int value, Style style, int value2) {
      long longValue = KEY ^ 51790860051519L;
      glyphList.add(new StyledGlyph(style, style.getColor() != null, value2));
      return true;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
