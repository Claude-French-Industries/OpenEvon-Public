package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.util.Animator;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgImage;
import club.evon.util.render.RenderUtil;




public final class BooleanGroupComponent extends SettingComponent<BooleanGroupSetting> {
   private static final float SPACING = 1.5F;
   private static final float FONT_SIZE = 5.5F;
   private static final float LABEL_Y_OFFSET = 3.5F;
   private static final NvgImage ARROW_IMAGE;
   private final Animator expandAnimator;
   private boolean expanded;
   private final List<GuiComponent> checkboxes;
   private static final long KEY = KeyedRandom.create(1608722433268995802L, 9089471009773710593L, MethodHandles.lookup().lookupClass()).next(278900251169498L);

   public BooleanGroupComponent(BooleanGroupSetting property) {
      super(property);

      long longValue = KEY ^ 88377054211440L;
      this.expandAnimator = new Animator(200, 0.0, Easing.LINEAR);
      int value = ThemeDropdownComponent.getDefaultState();
      this.checkboxes = new ArrayList<>();
      int value2 = value;

      for (BooleanSetting setting : property.getValue()) {
         this.checkboxes.add(new CheckboxComponent(setting));
         if (value2 == 0) {
            break;
         }
      }
      }

   public void render(int mouseX, int mouseY) {
      long longValue = KEY ^ 100400681947720L;
      int value = ThemeDropdownComponent.getDefaultState();
      super.render(mouseX, mouseY);
      float floatValue = this.getX();
      float floatValue2 = this.getY();
      int value2 = value;
      float floatValue3 = this.getWidth();
      BooleanGroupSetting setting = this.getSetting();
      this.expandAnimator.setTargetValue(this.expanded ? 1.0 : 0.0);
      int index = 0;
      BooleanSetting[] values = setting.getValue();
      int length = values.length;
      int index2 = 0;

      while (index2 < length) {
         BooleanSetting setting2 = values[index2];
         if (value2 != 0) {
            if (setting2.I()) {
               index++;
            }

            index2++;
         }

         if (value2 == 0) {
            break;
         }
      }

      label108: {
         List<GuiComponent> toList = this.checkboxes.stream().filter(GuiComponent::isVisible).toList();
         String text = index + "/" + toList.size();
         float floatValue4 = 5.5F;
         Theme.FONT.drawText(text, floatValue + floatValue3 - (Theme.FONT.getTextWidth(text, 5.5F) + 3.0F + 9.0F), floatValue2 + 3.5F + 1.0F, 5.5F, Theme.TEXT_COLOR.getRGB());
         float floatValue5 = super.getHeight();
         float floatValue6 = floatValue2 + floatValue5;
         float floatValue7 = floatValue + 4.5F + 3.5F;
         float floatValue8 = floatValue7;
         float floatValue9 = floatValue6;
         int index3 = 0;
         float floatValue10 = this.expandAnimator.getValue().floatValue();
         float floatValue11 = floatValue + floatValue3 - 11.0F;
         float floatValue12 = floatValue2 + 4.5F + 0.5F;
         RenderUtil.saveState();
         RenderUtil.translate(floatValue11 + 2.75F, floatValue12 + 1.75F);
         RenderUtil.rotate(3.125F * floatValue10);
         RenderUtil.translate(-2.75F, -1.75F);
         RenderUtil.drawImage(0.0F, 0.0F, 5.5F, 3.5F, null, ARROW_IMAGE);
         RenderUtil.restoreState();
         float floatValue13 = floatValue10;
         float floatValue14 = 0.0F;
         if (value2 != 0) {
            if (!(floatValue10 > 0.0F)) {
               break label108;
            }

            floatValue13 = floatValue;
            floatValue14 = floatValue2;
         }

         RenderUtil.pushClip(floatValue13, floatValue14, floatValue3, this.getHeight());
         float floatValue15 = 0.0F;
         int index4 = 0;

         for (GuiComponent guiComponent : toList) {
            guiComponent.setX(floatValue8);
            guiComponent.setY(floatValue9);
            guiComponent.setWidth((floatValue3 - ((floatValue7 - floatValue) * 2.0F + 1.5F)) / 2.0F);
            guiComponent.setHeight(12.0F);
            guiComponent.render(mouseX, mouseY);
            index3++;
            if (value2 == 0) {
               break label108;
            }

            label86: {
               label110: {
                  label111: {
                     label83: {
                        if (value2 != 0) {
                           if (index3 % 2 != 0) {
                              value = toList.size();
                              index4 = 1;
                              if (value2 == 0) {
                                 break label111;
                              }

                              if (value != 1) {
                                 break label83;
                              }
                           }

                           floatValue8 = floatValue7;
                           floatValue9 += guiComponent.getHeight() + 1.5F;
                        }

                        if (value2 != 0) {
                           break label86;
                        }
                     }

                     floatValue15 = floatValue8 + (guiComponent.getWidth() + 1.5F);
                     if (value2 == 0) {
                        break label110;
                     }

                     floatValue8 = floatValue15;
                     value = index3;
                     index4 = toList.size();
                  }

                  if (value != index4) {
                     break label86;
                  }

                  floatValue15 = floatValue9 + (guiComponent.getHeight() + 1.5F);
               }

               floatValue9 = floatValue15;
            }

            if (value2 == 0) {
               break;
            }
         }

         float floatValue16 = 2.0F;
         RenderUtil.fillRect(floatValue + 4.5F, floatValue6 - 2.0F, 1.0F, floatValue9 - (floatValue6 + 1.5F) + 4.0F, ColorUtil.withAlpha(Color.WHITE, 0.75));
         RenderUtil.popClip();
      }

      if (BaseModule.getStateArray() == null) {
         ThemeDropdownComponent.setState(++value2);
      }
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
      int value;
      int value2;
      BooleanGroupComponent booleanGroupComponent;
      long longValue = KEY ^ 95562933527563L;
      value = ThemeDropdownComponent.getState();
      value2 = button;
      label83:
      if (value == 0) {
         if (button <= 1) {
            value2 = HoverUtil.isHovered(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), super.getHeight()) ? 1 : 0;
            if (value != 0) {
               break label83;
            }

            if (value2 != 0) {
               boolean tmp = this.expanded;
               if (value == 0) {
                  tmp = !this.expanded;
               }

               this.expanded = tmp;
               if (value == 0) {
                  return;
               }
            }
         }

         value2 = button;
      }

      label87: {
         if (value == 0) {
            if (value2 != 0) {
               return;
            }

            booleanGroupComponent = this;
            if (value != 0) {
               break label87;
            }

            value2 = this.isHovered(mouseX, mouseY) ? 1 : 0;
         }

         if (value2 == 0) {
            return;
         }

         booleanGroupComponent = this;
      }

      for (GuiComponent guiComponent : booleanGroupComponent.checkboxes) {
         GuiComponent guiComponent2 = guiComponent;
         if (value == 0) {
            if (!guiComponent.isVisible() && value == 0) {
               continue;
            }

            guiComponent2 = guiComponent;
         }

         guiComponent2.mouseClicked(mouseX, mouseY, button);
         if (value != 0) {
            break;
         }
      }
   }

   @Override
   public float getHeight() {
      long longValue = KEY ^ 40267748472089L;
      int value = ThemeDropdownComponent.getState();
      float floatValue = 0.0F;
      int index = 0;
      int value2 = value;
      Iterator iterator = this.checkboxes.iterator();

      while (true) {
         if (iterator.hasNext()) {
            GuiComponent guiComponent = (GuiComponent)iterator.next();
            boolean visible = guiComponent.isVisible();
            if (value2 != 0) {
               value = visible ? 1 : 0;
               break;
            }

            if (value2 == 0) {
               if (!visible && value2 == 0) {
                  continue;
               }

               value = index % 2;
            }

            if (value == 0) {
               floatValue += guiComponent.getHeight() + 1.5F;
            }

            index++;
            if (value2 == 0) {
               continue;
            }
         }

         value = index;
         break;
      }

      if (value > 0) {
         floatValue -= 1.5F;
         floatValue += 4.5F;
      }

      return super.getHeight() + floatValue * this.expandAnimator.getValue().floatValue();
   }

   static {
      try {
      long var1 = KEY ^ 139860082272855L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var5 = var3.doFinal(")\u00ff\r\u00a6R\u0002\u00a7i\u00d0\u00c0n\u00efl\u000b\u00cf\u00fc".getBytes("ISO-8859-1"));
      String var6 = decodeUtf8(var5).intern();
      byte var10001 = -1;
      String var0 = var6;
      ARROW_IMAGE = new NvgImage(var0);
      } catch (Exception var10004) {
         throw sneakyThrow(var10004);
      }
   }

   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }

   private static String decodeUtf8(byte[] data) {
      int index = 0;
      int value;
      char[] chars = new char[value = data.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & data[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = data[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
