package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.math.MathHelper;
import org.json.JSONException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.setting.ColorSetting;
import club.evon.util.Animator;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.math.RandomUtil;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgImage;
import club.evon.util.render.RenderUtil;




public class ColorPickerComponent extends SettingComponent<ColorSetting> {
   private boolean expanded;
   private final Animator expandAnimator = new Animator(200, 0.0, Easing.LINEAR);
   private static final float ROW_HEIGHT = 15.0F;
   private static final float ARROW_OFFSET = 1.5F;
   private static final float ROW_RADIUS = 4.0F;
   private static final float ARROW_WIDTH = 5.5F;
   private static final float ARROW_HEIGHT = 3.5F;
   private static final float HUE_GAP = 1.5F;
   private static final float PADDING = 4.5F;
   private float pickerHeight;
   private float pickerWidth;
   private float gradientHeight;
   private final float[] hsb = new float[3];
   private float targetCursorX;
   private float targetCursorY;
   private float cursorX;
   private float cursorY;
   private float targetHueX;
   private float hueX;
   private boolean dragging;
   private boolean draggingHue;
   private static final NvgImage ARROW_IMAGE;
   private static final NvgImage PICKER_IMAGE;
   private final Animator handleAnimator = new Animator(200, 0.0, Easing.LINEAR);
   private final Animator hueHandleAnimator = new Animator(200, 0.0, Easing.LINEAR);
   private static final long KEY = KeyedRandom.create(-8021135135880900202L, 1059405015486360869L, MethodHandles.lookup().lookupClass()).next(80470329099150L);
   private static final String[] STRINGS;

   public ColorPickerComponent(ColorSetting property) {
      super(property);
      Color value = property.getValue();
      Color.RGBtoHSB(value.getRed(), value.getGreen(), value.getBlue(), this.hsb);
   }

   public void render(int mouseX, int mouseY) {
      long longValue = KEY ^ 130786046640031L;
      super.render(mouseX, mouseY);
      float floatValue = this.getX();
      float floatValue2 = this.getY();
      int value = ThemeDropdownComponent.getState();
      float floatValue3 = this.getWidth();
      float floatValue4 = super.getHeight();
      float floatValue5 = floatValue2 + floatValue4;
      this.pickerWidth = floatValue3 - 18.0F + 1.0F;
      this.pickerHeight = this.pickerWidth * ((float)ClientContext.MINECRAFT_CLIENT.getWindow().getScaledHeight() / ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth());
      this.gradientHeight = this.pickerHeight - 9.0F;
      int value2 = value;
      this.expandAnimator.setTargetValue(this.expanded ? 1.0 : 0.0);
      this.handleAnimator.setTargetValue(this.dragging ? 1.0 : 0.0);
      this.hueHandleAnimator.setTargetValue(this.draggingHue ? 1.0 : 0.0);
      RenderUtil.pushClip(floatValue, floatValue2 + floatValue4, floatValue3, this.getHeight() - (4.5F + floatValue4));
      RenderUtil.pushClip(floatValue, floatValue5, floatValue3, 15.0F);
      this.renderRowBackground(mouseX, mouseY, floatValue5);
      RenderUtil.popClip();
      Color value3 = this.getSetting().getValue();
      Theme.FONT.drawText(String.format(STRINGS[2], value3.getRed(), value3.getGreen(), value3.getBlue()), floatValue + 4.5F + 4.0F, floatValue5 + 4.0F + 0.5F, 7.0F, Color.WHITE.getRGB());
      float floatValue6 = 20.5F;
      float floatValue7 = 6.0F;
      RenderUtil.fillRoundedRect(floatValue + floatValue3 - 38.5F, floatValue5 + 4.5F + 0.5F, 20.5F, 6.0F, 2.0F, value3);
      float floatValue8 = floatValue5 + 16.5F;
      if (value2 == 0) {
         floatValue5 = floatValue8;
         if (this.expandAnimator.getValue() > 0.0) {
            float floatValue9 = floatValue;
            float floatValue10 = floatValue5;
            if (value2 == 0) {
               RenderUtil.pushClip(floatValue, floatValue5, floatValue3, this.getHeight() - (floatValue5 - floatValue2));
               this.renderRowBackground(mouseX, mouseY, floatValue5);
               if (GLFW.glfwGetMouseButton(ClientContext.MINECRAFT_CLIENT.getWindow().getHandle(), 0) != 1) {
                  this.dragging = this.draggingHue = false;
               }

               floatValue9 = floatValue + 9.0F;
               floatValue10 = 0.5F;
            }

            float floatValue11;
            float floatValue12;
            floatValue11 = floatValue9 - floatValue10;
            floatValue12 = floatValue5 + 4.5F - 1.0F;
            float floatValue13;
            value = (floatValue13 = this.targetCursorX - 0.0F) == 0.0F ? 0 : (floatValue13 < 0.0F ? -1 : 1);
            label98:
            if (value2 == 0) {
               if (value == 0) {
                  float floatValue14;
                  value = (floatValue14 = this.targetCursorY - 0.0F) == 0.0F ? 0 : (floatValue14 < 0.0F ? -1 : 1);
                  if (value2 != 0) {
                     break label98;
                  }

                  if (value == 0) {
                     this.targetCursorX = this.hsb[1] * this.pickerWidth + floatValue11 - this.getX();
                     this.cursorX = this.targetCursorX;
                     this.targetCursorY = (1.0F - this.hsb[2]) * this.gradientHeight + floatValue12 - floatValue5;
                     this.cursorY = this.targetCursorY;
                  }
               }

               float floatValue15;
               value = (floatValue15 = this.targetHueX - 0.0F) == 0.0F ? 0 : (floatValue15 < 0.0F ? -1 : 1);
            }

            if (value2 == 0) {
               if (value == 0) {
                  this.targetHueX = this.hsb[0] * this.pickerWidth + floatValue11;
                  this.hueX = this.targetHueX;
               }

               RenderUtil.fillColorPicker(floatValue11, floatValue12, this.pickerWidth, this.gradientHeight, 2.0F, this.hsb[0]);
               value = mouseX;
            }

            float floatValue16 = MathHelper.clamp(value, floatValue11, floatValue11 + this.pickerWidth);
            float floatValue17 = MathHelper.clamp(mouseY, floatValue12, floatValue12 + this.gradientHeight);
            ColorPickerComponent colorPickerComponent = this;
            if (value2 == 0) {
               if (this.dragging) {
                  this.targetCursorX = floatValue16 - this.getX();
                  this.targetCursorY = floatValue17 - floatValue5;
                  this.hsb[1] = (floatValue16 - floatValue11) / this.pickerWidth;
                  this.hsb[2] = 1.0F - (floatValue17 - (floatValue5 + 4.5F - 1.0F)) / this.gradientHeight;
                  this.getSetting().setValue(Color.getHSBColor(this.hsb[0], this.hsb[1], this.hsb[2]));
               }

               this.cursorX = RandomUtil.lerp(this.cursorX, this.targetCursorX, 0.1F);
               this.cursorY = RandomUtil.lerp(this.cursorY, this.targetCursorY, 0.1F);
               drawHueBar(floatValue11, floatValue12 + this.gradientHeight + 1.5F, this.pickerWidth, 4.5F, 2.0F);
               colorPickerComponent = this;
            }

            float floatValue18 = colorPickerComponent.handleAnimator.getValue().floatValue();
            float floatValue19 = 3.5F + 1.5F * floatValue18;
            RenderUtil.strokeRoundedRect(floatValue + this.cursorX - floatValue19 / 2.0F, floatValue5 + this.cursorY - floatValue19 / 2.0F, floatValue19, floatValue19, 2.0F + 1.0F * floatValue18, 1.0F + 0.5F * floatValue18, Color.WHITE);
            float floatValue20 = this.hueHandleAnimator.getValue().floatValue();
            float floatValue21 = 2.5F + 1.5F * floatValue20;
            RenderUtil.strokeRoundedRect(this.targetHueX - floatValue21 / 2.0F, floatValue12 + this.gradientHeight + 1.5F + 1.0F - 0.75F * floatValue20, floatValue21, floatValue21, 1.25F + 1.0F * floatValue20, 0.75F + 0.25F * floatValue20, Color.WHITE);
            ColorPickerComponent colorPickerComponent2 = this;
            if (value2 == 0) {
               if (this.draggingHue) {
                  this.targetHueX = MathHelper.clamp(floatValue16, floatValue11 + floatValue21 / 2.0F + 1.0F, floatValue11 + this.pickerWidth - floatValue21 / 2.0F - 1.0F);
                  this.getSetting().setValue(Color.getHSBColor(this.hsb[0] = (floatValue16 - floatValue11) / this.pickerWidth, 1.0F, this.hsb[2]));
               }

               colorPickerComponent2 = this;
            }

            colorPickerComponent2.hueX = RandomUtil.lerp(this.hueX, this.targetHueX, 0.1F);
            RenderUtil.popClip();
         }

         floatValue8 = floatValue + floatValue3 - 14.5F;
      }

      float floatValue22 = floatValue8;
      float floatValue23 = floatValue2 + floatValue4 + 4.5F + 1.5F;
      RenderUtil.saveState();
      RenderUtil.translate(floatValue22 + 2.75F, floatValue23 + 1.75F);
      RenderUtil.rotate(3.125F * this.expandAnimator.getValue().floatValue());
      RenderUtil.translate(-2.75F, -1.75F);
      RenderUtil.drawImage(0.0F, 0.0F, 5.5F, 3.5F, null, ARROW_IMAGE);
      RenderUtil.restoreState();
      RenderUtil.popClip();
      if (value2 != 0) {
         BaseModule.setStateArray(new BaseModule[3]);
      }
   }

   private static void drawHueBar(float x, float y, float width, float height, float radius) {
      RenderUtil.saveState();
      RenderUtil.translate(x, y);
      NVGPaint calloc = NVGPaint.calloc();
      int value = PICKER_IMAGE.getHandle();
      NanoVG.nvgImagePattern(RenderUtil.NVG_CONTEXT, 0.0F, 0.0F, width, height, 0.0F, value, 1.0F, calloc);
      NanoVG.nvgBeginPath(RenderUtil.NVG_CONTEXT);
      NanoVG.nvgRoundedRectVarying(RenderUtil.NVG_CONTEXT, 0.0F, 0.0F, width, height, radius, radius, radius, radius);
      NanoVG.nvgFillPaint(RenderUtil.NVG_CONTEXT, calloc);
      NanoVG.nvgFill(RenderUtil.NVG_CONTEXT);
      calloc.free();
      RenderUtil.restoreState();
   }

   private void renderRowBackground(float floatValue, float floatValue2, float offsetY) {
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      int value;
      label33: {
         label36: {
            long longValue = KEY ^ 107076576985837L;
            value = ThemeDropdownComponent.getDefaultState();
            float floatValue7 = 0.85F;
            float floatValue8 = 0.425F;
            int value2 = value;
            floatValue3 = this.getWidth();
            floatValue4 = this.getX();
            floatValue5 = this.getY();
            floatValue6 = super.getHeight();
            float floatValue9;
            value = (floatValue9 = offsetY - (floatValue5 + floatValue6)) == 0.0F ? 0 : (floatValue9 < 0.0F ? -1 : 1);
            if (value2 != 0) {
               if (value != 0) {
                  break label36;
               }

               value = HoverUtil.isHovered(floatValue, floatValue2, floatValue4 + 4.5F, offsetY, floatValue3 - 9.0F, 15.0) ? 1 : 0;
            }

            if (value2 == 0) {
               break label33;
            }

            if (value != 0) {
               value = 1;
               break label33;
            }
         }

         value = 0;
      }

      int value3 = value;
      RenderUtil.strokeRoundedRect(floatValue4 + 4.5F + 0.425F, floatValue5 + floatValue6 + 0.425F, floatValue3 - 9.0F - 0.85F, this.getHeight() - (floatValue6 + 4.5F) - 0.85F, 4.0F, 0.85F, new Color(184, 202, 209, 19));
      RenderUtil.fillRoundedRect(floatValue4 + 4.5F, floatValue5 + floatValue6, floatValue3 - 9.0F, this.getHeight() - (floatValue6 + 4.5F), 4.0F, ColorUtil.withAlpha(Color.WHITE, value3 == 0 ? 0.1F : 0.15F));
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
      int value;
      int value2;
      long longValue = KEY ^ 135563656464860L;
      value = ThemeDropdownComponent.getDefaultState();
      value2 = button;
      label59:
      if (value != 0) {
         if (button <= 1) {
             value2 = HoverUtil.isHovered(mouseX, mouseY, this.getX() + 4.5F, this.getY() + super.getHeight(), this.getWidth() - 9.0F, 15.0) ? 1 : 0;
             if (value == 0) {
                break label59;
             }

             if (value2 != 0) {
               boolean tmp = this.expanded;
               if (value != 0) {
                  tmp = !this.expanded;
               }

               this.expanded = tmp;
               if (value != 0) {
                  return;
               }
            }
         }

         value2 = button;
      }

      if (value2 == 0) {
         float floatValue = this.getX() + 9.0F - 0.5F;
         float floatValue2 = this.getY() + super.getHeight() + 15.0F + 1.5F + 4.5F - 1.0F;
         boolean hovered = HoverUtil.isHovered(mouseX, mouseY, floatValue, floatValue2, this.pickerWidth, this.gradientHeight);
         if (value != 0) {
            if (hovered) {
               this.dragging = true;
               if (value != 0) {
                  return;
               }
            }

            hovered = HoverUtil.isHovered(mouseX, mouseY, floatValue, floatValue2 + this.gradientHeight + 1.5F, this.pickerWidth, 4.5);
         }

         if (hovered) {
            this.draggingHue = true;
         }
      }
   }

   @Override
   public float getHeight() {
      long longValue = KEY ^ 10158502942926L;
      int value = ThemeDropdownComponent.getState();
      float floatValue = super.getHeight();
      float floatValue2 = this.expandAnimator.getValue().floatValue();
      float floatValue3 = 0.0F;
      if (value == 0) {
         if (floatValue2 == 0.0F) {
            return floatValue + (16.5F + 0.0F * this.expandAnimator.getValue().floatValue()) - 1.5F + 4.5F;
         }

         floatValue2 = this.pickerHeight + 1.5F;
         floatValue3 = 4.5F;
      }

      return floatValue + (16.5F + (floatValue2 + floatValue3) * this.expandAnimator.getValue().floatValue()) - 1.5F + 4.5F;
   }

   static {
      try {
      long var0 = KEY ^ 125960193609147L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[3];
      int var7 = 0;
      String var6 = "\u0095\u000eW\u001f\u00e6Q\u00cf\u00b0VO\u001c\u00fe\u0088#\u00caV\u0010\u00d5v}\u0081B\u00ad\u008b\u00bcpJ\u00b4\u0006\u00a8\u00a2\u00ca\u009f\u0018G\ro\u0098\u0019oNj\u001e\u00bb\u00ba\u00f1ln*\u00d9\u00b8\u00baD ;yv\u00d6";
      int var8 = "\u0095\u000eW\u001f\u00e6Q\u00cf\u00b0VO\u001c\u00fe\u0088#\u00caV\u0010\u00d5v}\u0081B\u00ad\u008b\u00bcpJ\u00b4\u0006\u00a8\u00a2\u00ca\u009f\u0018G\ro\u0098\u0019oNj\u001e\u00bb\u00ba\u00f1ln*\u00d9\u00b8\u00baD ;yv\u00d6"
         .length();
      char var5 = 16;
      int var4 = -1;

      label28:
      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var14 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var14;
         if ((var4 += var5) >= var8) {
            STRINGS = var9;
            String[] var11 = STRINGS;
            ARROW_IMAGE = new NvgImage(var11[0]);
            PICKER_IMAGE = new NvgImage(var11[1]);
            break label28;
         }

         var5 = var6.charAt(var4);
      }
      } catch (Exception var10004) {
         throw rethrow(var10004);
      }
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException rethrow(Throwable exception) throws T {
      throw (T)exception;
   }

   private static JSONException identity(JSONException exception) {
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
