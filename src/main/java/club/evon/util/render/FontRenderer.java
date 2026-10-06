package club.evon.util.render;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.Formatting;
import org.json.JSONException;
import club.evon.EvonClient;
import club.evon.manager.FontManager;
import club.evon.util.KeyedRandom;




public final class FontRenderer {
   private static final String COLOR_CODES;
   private static final int[] FORMATTING_COLORS;
   private final FontManager fontManager = EvonClient.getInstance().getFontManager();
   private static final long INDY_KEY = KeyedRandom.create(4502101003311413076L, 1589706966423719501L, MethodHandles.lookup().lookupClass()).next(63242139261496L);
   private static final String[] STRING_TABLE;

   public void drawString(String fontName, FontWeight weight, String text, float x, float y, float size, int color, boolean shadow, float bloomAmount, int alignment) {
      long longValue = INDY_KEY ^ 119631127459867L;
      String stateString = NvgFont.getStateString();
      int value = alignment & 4;
      if (stateString != null) {
         value = value == 4 ? 1 : 0;
      }

      int value2 = value;
      int value3 = color;
      StringBuilder builder = new StringBuilder();
      boolean flag = false;
      text = this.sanitizeText(text);
      int index = 0;

      while (true) {
         if (index < text.length()) {
            char character = text.charAt(index);
            value = character;
            if (stateString == null) {
               break;
            }

            label136: {
               if (stateString != null) {
                  if (character != 167) {
                     builder.append(character);
                     if (stateString != null) {
                        break label136;
                     }
                  }

                  value = builder.isEmpty() ? 1 : 0;
               }

               if (stateString != null) {
                  if (value == 0) {
                     label120: {
                        float floatValue;
                        label119: {
                           NvgFont font = this.getFont(fontName, weight);
                           String toString = builder.toString();
                           font.drawText(toString, x, y, size, color, shadow, bloomAmount, alignment);
                           floatValue = font.getTextWidth(toString, size, alignment);
                           if (stateString != null) {
                              if (value2 == 0) {
                                 break label119;
                              }

                              x -= floatValue;
                           }

                           if (stateString != null) {
                              break label120;
                           }
                        }

                        x += floatValue;
                     }

                     builder.setLength(0);
                  }

                  value = index;
               }

               if (stateString != null) {
                  value = value == text.length() - 1 ? 21 : STRING_TABLE[3].indexOf(text.charAt(index + 1));
               }

               label138: {
                  int value4 = value;
                  value = value4;
                  if (stateString != null) {
                     if (value4 < 16) {
                        flag = false;
                        value = value4;
                        if (stateString != null) {
                           if (value4 < 0) {
                              value4 = 15;
                           }

                           value = color >> 24 & 0xFF;
                        }

                        int value5 = value;
                        color = ColorUtil.applyOpacity(FORMATTING_COLORS[value4], value5);
                        if (stateString != null) {
                           break label138;
                        }
                     }

                     value = value4;
                  }

                  if (stateString != null) {
                     switch (value) {
                        case 17:
                           if (stateString != null) {
                              break;
                           }
                        case 21:
                           flag = false;
                           color = value3;
                           if (stateString != null) {
                              break;
                           }
                        case 22:
                           flag = true;
                           if (stateString != null) {
                              break;
                           }
                        default:
                           boolean flag2 = true;
                     }
                  }
               }

               index++;
            }

            index++;
            if (stateString != null) {
               continue;
            }
         }

          value = builder.toString().trim().isEmpty() ? 1 : 0;
         break;
      }

      if (value == 0) {
         NvgFont font2 = this.getFont(fontName, weight);
         font2.drawText(builder.toString(), x, y, size, color, shadow, bloomAmount, alignment);
      }
   }

   public void drawString(String fontName, FontWeight weight, String text, float x, float y, float size, int color) {
      this.drawString(fontName, weight, text, x, y, size, color, false, 0.0F, 9);
   }

   public void drawStringWithShadow(String fontName, FontWeight weight, String text, float x, float y, float size, int color, boolean shadow) {
      this.drawString(fontName, weight, text, x, y, size, color, shadow, 0.0F, 9);
   }

   public void drawStringWithShadow(String fontName, FontWeight weight, String text, float x, float y, float size, int color, boolean shadow, int alignment) {
      this.drawString(fontName, weight, text, x, y, size, color, shadow, 0.0F, alignment);
   }

   public void drawStringRegular(String fontName, String text, float x, float y, float size, int color, boolean shadow, int alignment) {
      this.drawString(fontName, FontWeight.REGULAR, text, x, y, size, color, shadow, 0.0F, alignment);
   }

   public void drawStringRegular(String fontName, String text, float x, float y, float size, int color, boolean shadow) {
      this.drawString(fontName, FontWeight.REGULAR, text, x, y, size, color, shadow, 0.0F, 9);
   }

   public void drawStringRegular(String fontName, String text, float x, float y, float size, int color) {
      this.drawString(fontName, FontWeight.REGULAR, text, x, y, size, color, false, 0.0F, 9);
   }

   public void drawGradientString(
      String fontName, FontWeight weight, String text, float x, float y, float size, int color1, int color2, boolean shadow, float bloomAmount, int alignment
   ) {
      long longValue = INDY_KEY ^ 90358161433408L;
      float floatValue = 0.0F;
      String stateString = NvgFont.getStateString();
      int index = 0;
      String text2 = stateString;

      while (index < text.length()) {
         Character charAt = text.charAt(index);
         String valueOf = String.valueOf(charAt);
         float floatValue2 = this.getStringWidth(fontName, weight, valueOf, size, alignment);
         int value = ColorUtil.sinusoidalBlend(new Color(color1), new Color(color2), 500.0, index * 0.4F).brighter().getRGB();
         this.drawString(fontName, weight, valueOf, x + floatValue, y, size, value, shadow, bloomAmount, alignment);
         floatValue += floatValue2;
         index++;
         if (text2 == null) {
            break;
         }
      }
   }

   public void drawGradientString(
      String fontName, FontWeight weight, String text, float x, float y, float size, int color1, int color2, float alpha, boolean shadow, int bloomAmount, int alignment
   ) {
      long longValue = INDY_KEY ^ 130336790484615L;
      String stateString = NvgFont.getStateString();
      float floatValue = 0.0F;
      int index = 0;
      String text2 = stateString;

      while (index < text.length()) {
         Character charAt = text.charAt(index);
         String valueOf = String.valueOf(charAt);
         float floatValue2 = this.getStringWidth(fontName, weight, valueOf, size, alignment);
         Color brighter = ColorUtil.sinusoidalBlend(new Color(color1), new Color(color2), 500.0, index * 0.75F).brighter();
         Color withAlpha = ColorUtil.withAlpha(brighter, alpha / 255.0F);
         this.drawString(fontName, weight, valueOf, x + floatValue, y, size, withAlpha.getRGB(), shadow, bloomAmount, alignment);
         floatValue += floatValue2;
         index++;
         if (text2 == null) {
            break;
         }
      }
   }

   public float getStringWidth(String fontName, FontWeight weight, String text, float size, int alignment) {
      long longValue = INDY_KEY ^ 113973633446854L;
      StringBuilder builder = new StringBuilder();
      float floatValue = 0.0F;
      String stateString = NvgFont.getStateString();
      boolean flag = false;
      text = this.sanitizeText(text);
      String text2 = stateString;
      int index = 0;
      int index2 = 0;

      while (true) {
         if (index < text.length()) {
            char character = text.charAt(index);
            index2 = character;
            if (text2 == null) {
               break;
            }

            label80: {
               if (text2 != null) {
                  if (character != 167) {
                     builder.append(character);
                     if (text2 != null) {
                        break label80;
                     }
                  }

                  index2 = builder.isEmpty() ? 1 : 0;
               }

               if (text2 != null) {
                  if (index2 == 0) {
                     NvgFont font = this.getFont(fontName, weight);
                     floatValue += font.getTextWidth(builder.toString(), size, alignment);
                     builder.setLength(0);
                  }

                  index2 = index;
               }

               if (text2 != null) {
                  if (index2 == text.length() - 1) {
                     index2 = 21;
                  } else {
                     String[] strings = STRING_TABLE;
                     index2 = strings[1].indexOf(text.charAt(index + 1));
                  }
               }

               int value = index2;
               switch (value) {
                  case 17:
                     if (text2 != null) {
                        break;
                     }
                  case 21:
                     if (text2 == null) {
                     }
                  case 22:
               }

               index++;
            }

            index++;
            if (text2 != null) {
               continue;
            }
         }

         index2 = builder.isEmpty() ? 1 : 0;
         break;
      }

      if (index2 == 0) {
         NvgFont font2 = this.getFont(STRING_TABLE[0], weight);
         floatValue += font2.getTextWidth(builder.toString(), size, alignment);
      }

      return floatValue;
   }

   public float getStringWidth(String fontName, FontWeight weight, String text, float size) {
      return this.getStringWidth(fontName, weight, text, size, 9);
   }

   /**
    * Total horizontal advance that {@link #drawGradientString} will consume for {@code text}.
    *
    * <p>{@code drawGradientString} renders one glyph at a time, advancing by the width of that
    * single character, so the space it takes is the <em>sum of the individual glyph widths</em> -
    * not the width of the whole string (which also applies kerning). Layout code that sizes a
    * container around a gradient string must use this, otherwise the measured width disagrees
    * with what is painted and everything positioned after the string lands in the wrong place.
    * The error grows with the length of the text.
    */
   public float getGradientStringWidth(String fontName, FontWeight weight, String text, float size, int alignment) {
      float total = 0.0F;

      for (int index = 0; index < text.length(); index++) {
         total += this.getStringWidth(fontName, weight, String.valueOf(text.charAt(index)), size, alignment);
      }

      return total;
   }

   public float getGradientStringWidth(String fontName, FontWeight weight, String text, float size) {
      return this.getGradientStringWidth(fontName, weight, text, size, 9);
   }

   public float getStringWidth(String fontName, String text, float size) {
      return this.getStringWidth(fontName, FontWeight.REGULAR, text, size, 9);
   }

   public float getStringHeight(String fontName, FontWeight weight, String text, float size, int alignment) {
      return this.getFont(fontName, weight).getTextHeight(text, size, alignment);
   }

   public float getStringHeightRegular(String fontName, String text, float size, int alignment) {
      return this.getStringHeight(fontName, FontWeight.REGULAR, text, size, alignment);
   }

   public float getStringHeightRegular(String fontName, String text, float size) {
      return this.getStringHeight(fontName, FontWeight.REGULAR, text, size, 9);
   }

   public float getStringHeight(String fontName, FontWeight fontWeight, String text, float size) {
      return this.getStringHeight(fontName, fontWeight, text, size, 9);
   }

   private NvgFont getFont(String text, FontWeight fontWeight) {
      return this.fontManager.getFont(text + "-" + fontWeight.getDisplayName());
   }

   private String sanitizeText(String text) {
      long longValue = INDY_KEY ^ 138333037137660L;
      String stateString = NvgFont.getStateString();
      StringBuilder builder = new StringBuilder();
      String text2 = stateString;
      byte byteValue = 0;
      int index = 0;
      int index2 = 0;

      while (true) {
         if (index < text.length()) {
            stateString = text;
            if (text2 == null) {
               break;
            }

            label99: {
               char character;
               character = text.charAt(index);
               index2 = byteValue;
               label88:
               if (text2 != null) {
                  if (byteValue == 0) {
                     index2 = character;
                     if (text2 == null) {
                        break label88;
                     }

                     if (character == 167) {
                        index2 = index + 1;
                        if (text2 == null) {
                           break label88;
                        }

                        if (index2 < text.length()) {
                           index2 = text.charAt(index + 1);
                           if (text2 == null) {
                              break label88;
                           }

                           if (index2 == 105) {
                              builder.append(STRING_TABLE[2]);
                              byteValue = 1;
                              index++;
                              if (text2 != null) {
                                 break label99;
                              }
                           }
                        }
                     }
                  }

                  index2 = byteValue;
               }

               label100: {
                  if (text2 != null) {
                     if (index2 != 0) {
                        break label100;
                     }

                     index2 = character;
                  }

                  short shortValue = 167;
                  if (text2 != null) {
                     if (index2 == 167) {
                        break label100;
                     }

                     index2 = character;
                     shortValue = 32;
                  }

                  if (text2 != null) {
                     if (index2 < shortValue) {
                        break label99;
                     }

                     index2 = character;
                     shortValue = 126;
                  }

                  if (index2 > shortValue) {
                     break label99;
                  }
               }

               builder.append(character);
            }

            index++;
            if (text2 != null) {
               continue;
            }
         }

         stateString = builder.toString();
         break;
      }

      return stateString;
   }

   static {
      try {
      long var0 = INDY_KEY ^ 124893560926244L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[4];
      int var7 = 0;
      String var6 = "]\u00ac>\u00f0\u00f6\u00eb\u008c\u0003\u0018Jc\u00f1\u00bb9\n\u0018\u00e37\u00fc\u00cd\u00c1\u0081\u00a5\u00d3\u00d7e\u00a7\u00811xPK\u00d0";
      int var8 = "]\u00ac>\u00f0\u00f6\u00eb\u008c\u0003\u0018Jc\u00f1\u00bb9\n\u0018\u00e37\u00fc\u00cd\u00c1\u0081\u00a5\u00d3\u00d7e\u00a7\u00811xPK\u00d0"
         .length();
      char var5 = '\b';
      int var13 = -1;

      label38:
      while (true) {
         String var14 = var6.substring(++var13, var13 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var14.getBytes("ISO-8859-1"));
            String var20 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var20;
                  if ((var13 += var5) >= var8) {
                     STRING_TABLE = var9;
                     COLOR_CODES = STRING_TABLE[1];
                     FORMATTING_COLORS = new int[16];

                     for (int var11 = 0; var11 < 16; var11++) {
                        FORMATTING_COLORS[var11] = Formatting.values()[var11].getColorValue();
                     }

                     break label38;
                  }

                  var5 = var6.charAt(var13);
                  break;
               default:
                  var9[var7++] = var20;
                  if ((var13 += var5) < var8) {
                     var5 = var6.charAt(var13);
                     continue label38;
                  }

                  var6 = "\u0087\u009e\u0096\u0006j><\u00be\u0018Jc\u00f1\u00bb9\n\u0018\u00e37\u00fc\u00cd\u00c1\u0081\u00a5\u00d3\u00d7e\u00a7\u00811xPK\u00d0";
                  var8 = "\u0087\u009e\u0096\u0006j><\u00be\u0018Jc\u00f1\u00bb9\n\u0018\u00e37\u00fc\u00cd\u00c1\u0081\u00a5\u00d3\u00d7e\u00a7\u00811xPK\u00d0"
                     .length();
                  var5 = '\b';
                  var13 = -1;
            }

            var14 = var6.substring(++var13, var13 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var21) {
         throw new RuntimeException(var21);
      }
   }

   private static JSONException passThroughException(JSONException exception) {
      return exception;
   }

   private static String decodeUtf8(byte[] bytes) {
      int index = 0;
      int value;
      char[] chars = new char[value = bytes.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & bytes[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = bytes[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
