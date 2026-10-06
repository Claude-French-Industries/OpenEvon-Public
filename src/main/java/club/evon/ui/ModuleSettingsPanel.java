package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import club.evon.manager.ModuleManager;
import club.evon.module.BaseModule;
import club.evon.module.impl.render.Overlay;
import club.evon.setting.BindSetting;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.ColorSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.setting.ModeSetting;
import club.evon.setting.Setting;
import club.evon.setting.StringSetting;
import club.evon.setting.ThemeSetting;
import club.evon.util.Animator;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgFont;
import club.evon.util.render.NvgImage;
import club.evon.util.render.RenderUtil;




public final class ModuleSettingsPanel extends AbstractGuiComponent {
   private final BaseModule a;
   private final CategoryPanel categoryPanel;
   private static final NvgImage CHEVRON_IMAGE;
   private static final float EXPAND_BUTTON_SIZE = 12.5F;
   private static final int d;
   private boolean expanded;
   private final Animator expandAnimator;
   private final Animator hoverAnimator;
   private final List<SettingComponent<?>> settingComponents;
   public static final Color EXPAND_BUTTON_COLOR;
   private static final Color EXPAND_BUTTON_OUTLINE_COLOR;
   private static final NvgFont NAME_FONT;
   private static final NvgFont i;
   private static final float ARROW_WIDTH = 5.5F;
   private static final float ARROW_HEIGHT = 3.5F;
   private static final int Z;
   private final Animator enabledAnimator;
   private static final long KEY = KeyedRandom.create(-685896264473134805L, -4594006742969599518L, MethodHandles.lookup().lookupClass()).next(170382106812198L);

   public ModuleSettingsPanel(BaseModule module, CategoryPanel category) {
      super();

      long longValue = KEY ^ 134363533002799L;
      String[] strings = ModuleManager.getFlowGuard();
      this.expandAnimator = new Animator(200, 0.0, Easing.LINEAR);
      this.hoverAnimator = new Animator(200, 0.0, Easing.LINEAR);
      this.settingComponents = new ArrayList<>();
      this.enabledAnimator = new Animator(200, 0.0, Easing.LINEAR);
      String[] strings2 = strings;
      this.a = module;
      this.categoryPanel = category;

      for (Setting setting : module.getSettings()) {
         Object object = setting;
         Objects.requireNonNull(object);
         if (strings2 == null) {
            if (object instanceof BooleanSetting setting2) {
               this.settingComponents.add(new ToggleComponent(setting2));
            } else if (object instanceof BindSetting setting3) {
               this.settingComponents.add(new KeybindComponent(setting3));
            } else if (object instanceof DoubleSetting setting4) {
               this.settingComponents.add(new SliderComponent(setting4));
            } else if (object instanceof ThemeSetting setting5) {
               this.settingComponents.add(new ThemeDropdownComponent(setting5));
            } else if (object instanceof EnumSetting setting6) {
               this.settingComponents.add(new EnumDropdownComponent(setting6));
            } else if (object instanceof ModeSetting setting7) {
               this.settingComponents.add(new ModeDropdownComponent(setting7));
            } else if (object instanceof BooleanGroupSetting setting8) {
               this.settingComponents.add(new BooleanGroupComponent(setting8));
            } else if (object instanceof StringSetting setting9) {
               this.settingComponents.add(new StringSettingElement(setting9));
            } else if (object instanceof ColorSetting setting10) {
               this.settingComponents.add(new ColorPickerComponent(setting10));
            }
         }

         if (strings2 != null) {
            break;
         }
      }

      if (BaseModule.getStateArray() == null) {
         ModuleManager.setFlowGuard(new String[2]);
      }
      }

   @Override
   public void init() {
      long longValue = KEY ^ 68382102433191L;
      String[] strings = ModuleManager.getFlowGuard();
      Iterator iterator = this.settingComponents.iterator();
      String[] strings2 = strings;

      while (true) {
         if (iterator.hasNext()) {
            GuiComponent guiComponent = (GuiComponent)iterator.next();
            guiComponent.init();
            if (strings2 != null) {
               break;
            }

            if (strings2 == null) {
               continue;
            }
         }

         this.enabledAnimator.setValue(this.a.isEnabled() ? 1.0 : 0.0);
         break;
      }
   }

   public void render(int mouseX, int mouseY) {
      long longValue = KEY ^ 129512971229078L;
      String[] strings = ModuleManager.getFlowGuard();
      float floatValue = this.getX();
      String[] strings2 = strings;
      float floatValue2 = this.getY();
      float floatValue3 = this.getWidth();
      float floatValue4 = this.getHeight();
      this.expandAnimator.setTargetValue(this.expanded ? 1.0 : 0.0);
      float floatValue5 = this.expandAnimator.getValue().floatValue();
      boolean enabled = this.a.isEnabled();
      this.enabledAnimator.setTargetValue(enabled ? 1.0 : 0.0);
      float floatValue6 = super.getHeight();
      RenderUtil.fillRoundedRect(floatValue, floatValue2, floatValue3, floatValue4, 4.0F, ColorUtil.withAlpha(Color.WHITE, 0.075F));
      double doubleValue = mouseX;
      if (strings2 == null) {
         doubleValue = HoverUtil.isHovered(doubleValue, mouseY, floatValue, floatValue2, floatValue3, floatValue6) ? 1.0 : 0.0;
      }

      label223: {
         this.hoverAnimator.setTargetValue(doubleValue);
         if (strings2 == null) {
            if (!HoverUtil.isHovered(mouseX, mouseY, floatValue, floatValue2, floatValue3, floatValue6)) {
               break label223;
            }

            RenderUtil.pushClip(floatValue, floatValue2, floatValue3, floatValue6);
         }

         RenderUtil.fillRoundedRect(floatValue, floatValue2, floatValue3, floatValue4, 4.0F, ColorUtil.withAlpha(Color.WHITE, !enabled ? 0.07F : 0.1F));
         RenderUtil.popClip();
      }

      float floatValue7;
      floatValue7 = this.enabledAnimator.getValue().floatValue();
      float floatValue8 = 0.85F;
      float floatValue9 = 0.425F;
      int value;
      float floatValue10 = 0.0F;
      float floatValue11 = 0.0F;
      byte byteValue = 0;
      RenderUtil.strokeRoundedRect(floatValue + 0.425F, floatValue2 + 0.425F, floatValue3 - 0.85F, floatValue4 - 0.85F, 4.0F, 0.85F, new Color(184, 202, 209, (int)(19.0F + 31.0F * floatValue7)));
      float floatValue12;
      value = (floatValue12 = floatValue7 - 0.0F) == 0.0F ? 0 : (floatValue12 < 0.0F ? -1 : 1);
      label213:
      if (strings2 == null) {
         if (value > 0) {
            Color primaryColor = Overlay.getPrimaryColor();
            Color secondaryColor = Overlay.getSecondaryColor();
            if (strings2 == null) {
               if (floatValue5 > 0.0F) {
                  RenderUtil.pushClip(floatValue, floatValue2, floatValue3, floatValue6);
               }

               float floatValue13 = 0.5F;
            }

            float floatValue14 = 4.0F;
            NVGPaint calloc = NVGPaint.calloc();
            NVGColor toNvgColor = RenderUtil.toNvgColor(ColorUtil.withAlpha(primaryColor, 0.5F * floatValue7).getRGB());
            NVGColor toNvgColor2 = RenderUtil.toNvgColor(ColorUtil.withAlpha(secondaryColor, 0.5F * floatValue7).getRGB());
            NanoVG.nvgLinearGradient(RenderUtil.NVG_CONTEXT, floatValue, floatValue2, floatValue + floatValue3, floatValue2 + floatValue6, toNvgColor, toNvgColor2, calloc);
            NanoVG.nvgBeginPath(RenderUtil.NVG_CONTEXT);
            NanoVG.nvgFillPaint(RenderUtil.NVG_CONTEXT, calloc);
            NanoVG.nvgRoundedRect(RenderUtil.NVG_CONTEXT, floatValue, floatValue2, floatValue3, floatValue4, 4.0F);
            NanoVG.nvgFill(RenderUtil.NVG_CONTEXT);
            NanoVG.nvgClosePath(RenderUtil.NVG_CONTEXT);
            toNvgColor.free();
            toNvgColor2.free();
            calloc.free();
            float floatValue15;
            value = (floatValue15 = floatValue5 - 0.0F) == 0.0F ? 0 : (floatValue15 < 0.0F ? -1 : 1);
            if (strings2 != null) {
               break label213;
            }

            if (value > 0) {
               RenderUtil.popClip();
               RenderUtil.pushClip(floatValue, floatValue2 + floatValue6, floatValue3, floatValue4 - floatValue6);
               float floatValue16 = 0.3F;
               float floatValue17 = super.getHeight();

               for (SettingComponent setting : this.settingComponents) {
                  if (strings2 == null) {
                     boolean var34b = setting.isVisible();
                     if (strings2 != null) {
                        break label213;
                     }

                     if (!var34b) {
                        if (strings2 == null) {
                           continue;
                        }

                        BaseModule.setStateArray(new BaseModule[5]);
                     }

                     floatValue17 += setting.getHeight();
                  }

                  if (strings2 != null) {
                     break;
                  }
               }

               RenderUtil.fillRoundedGradientRect(floatValue, floatValue2, floatValue3, floatValue17, 4.0F, ColorUtil.withAlpha(primaryColor, 0.3F * floatValue7), ColorUtil.withAlpha(secondaryColor, 0.3F * floatValue7), true);
               RenderUtil.popClip();
            }
         }

         float floatValue18;
         value = (floatValue18 = floatValue5 - 0.0F) == 0.0F ? 0 : (floatValue18 < 0.0F ? -1 : 1);
      }

      label188:
      if (strings2 == null) {
         if (value > 0) {
            float floatValue19;
            value = (floatValue19 = floatValue7 - 1.0F) == 0.0F ? 0 : (floatValue19 < 0.0F ? -1 : 1);
            if (strings2 != null) {
               break label188;
            }

            if (value < 0) {
               RenderUtil.pushClip(floatValue, floatValue2 + floatValue6, floatValue3, floatValue4 - floatValue6);
               RenderUtil.fillRoundedRect(floatValue, floatValue2, floatValue3, floatValue4, 4.0F, ColorUtil.withAlpha(Color.WHITE, 0.05F * (1.0F - floatValue7)));
               RenderUtil.popClip();
            }
         }

         float floatValue20;
         value = (floatValue20 = floatValue7 - 1.0F) == 0.0F ? 0 : (floatValue20 < 0.0F ? -1 : 1);
      }

      Color color;
      label228: {
         if (strings2 == null) {
            if (value == 0) {
               color = Color.WHITE;
               if (strings2 == null) {
                  break label228;
               }
            }

            float floatValue21;
            value = (floatValue21 = floatValue7 - 0.0F) == 0.0F ? 0 : (floatValue21 < 0.0F ? -1 : 1);
         }

         if (strings2 == null) {
            if (value == 0) {
               color = Theme.TEXT_COLOR;
               if (strings2 == null) {
                  break label228;
               }
            }

            value = (int)(Theme.TEXT_COLOR.getRed() + (255 - Theme.TEXT_COLOR.getRed()) * floatValue7);
         }

         int value2 = value;
         color = new Color(value2, value2, value2);
      }

      NAME_FONT.drawText(this.a.getName(), floatValue + 4.5F, floatValue2 + 7.5F, 7.5F, color.getRGB());
      if (!this.settingComponents.isEmpty()) {
         float floatValue22 = (floatValue6 - 12.5F) / 2.0F;
         float floatValue23 = floatValue2 + floatValue22;
         float floatValue24 = floatValue + floatValue3 - (12.5F + floatValue22);
         drawExpandButton(floatValue24, floatValue23);
         RenderUtil.saveState();
         RenderUtil.translate(floatValue24 + 6.25F, floatValue23 + 6.25F);
         RenderUtil.rotate(3.125F * floatValue5);
         RenderUtil.translate(-2.75F, -1.75F);
         RenderUtil.drawImage(0.0F, 0.0F, 5.5F, 3.5F, null, CHEVRON_IMAGE);
         RenderUtil.restoreState();
         float floatValue25 = floatValue5;
         float floatValue26 = 0.0F;
         if (strings2 == null) {
            if (!(floatValue5 > 0.0F)) {
               return;
            }

            RenderUtil.pushClip(floatValue, floatValue2, floatValue3, floatValue4);
            floatValue25 = floatValue2;
            floatValue26 = floatValue6;
         }

         float floatValue27 = floatValue25 + floatValue26;

         for (SettingComponent setting2 : this.settingComponents) {
            if (strings2 != null) {
               return;
            }

            label231: {
               boolean var37b = setting2.isVisible();
               if (strings2 == null) {
                  if (!var37b && strings2 == null) {
                     continue;
                  }

                  setting2.setX(floatValue);
                  setting2.setY(floatValue27);
                  setting2.setWidth(floatValue3);
                  setting2.setHeight(14.0F);
                  floatValue10 = setting2.getY() - this.categoryPanel.getY();
                  floatValue11 = this.categoryPanel.getHeight();
                  if (strings2 != null) {
                     break label231;
                  }

                  float floatValue28;
                  byteValue = (byte)((floatValue28 = floatValue10 - floatValue11) == 0.0F ? 0 : (floatValue28 < 0.0F ? -1 : 1));
               }

               if (byteValue < 0) {
                  floatValue10 = setting2.getY() + setting2.getHeight();
                  floatValue11 = this.categoryPanel.getY() + this.categoryPanel.getBaseHeight();
                  if (strings2 != null) {
                     break label231;
                  }

                  if (floatValue10 >= floatValue11) {
                     setting2.render(mouseX, mouseY);
                  }
               }

               floatValue10 = floatValue27;
               floatValue11 = setting2.getHeight();
            }

            floatValue27 = floatValue10 + floatValue11;
            if (strings2 != null) {
               break;
            }
         }

         RenderUtil.popClip();
      }
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
      String[] strings;
      boolean flag;
      boolean flag2;
      int value;
      SettingComponent setting;
      long longValue = KEY ^ 132436237984213L;
      String[] strings2 = ModuleManager.getFlowGuard();
      flag = HoverUtil.isHovered(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), super.getHeight());
      strings = strings2;
      flag2 = false;
      value = this.settingComponents.isEmpty() ? 1 : 0;
      label142:
      if (strings == null) {
         label140:
         if (value == 0) {
            ModuleSettingsPanel setting2 = this;
            if (strings == null) {
               if (this.expandAnimator.getValue() == 1.0) {
                   for (SettingComponent setting3 : this.settingComponents) {
                      value = setting3.isVisible() ? 1 : 0;
                      if (strings != null) {
                         break label142;
                      }

                     double doubleValue;
                     double doubleValue2;
                     label148: {
                        if (strings == null) {
                           if (value == 0) {
                              continue;
                           }

                           setting = setting3;
                           doubleValue = mouseX;
                           doubleValue2 = mouseY;
                           if (strings != null) {
                              break label148;
                           }

                           value = setting3.isHovered(mouseX, mouseY) ? 1 : 0;
                        }

                        if (value == 0 && strings == null) {
                           continue;
                        }

                        setting = setting3;
                        doubleValue = mouseX;
                        doubleValue2 = mouseY;
                     }

                     setting.mouseClicked(doubleValue, doubleValue2, button);
                     if (strings != null) {
                        break;
                     }
                  }
               }

               setting2 = this;
            }

            label150: {
               float floatValue = (setting2.getHeight() - 12.5F) / 2.0F;
               float floatValue2 = this.getX() + this.getWidth() - (12.5F + floatValue);
               float floatValue3 = this.getY() + floatValue;
               flag2 = HoverUtil.isHovered(mouseX, mouseY, floatValue2, floatValue3, 12.5, 12.5);
               value = button;
               label112:
               if (strings == null) {
                  if (button == 1) {
                     value = flag ? 1 : 0;
                     if (strings != null) {
                        break label112;
                     }

                     if (flag) {
                        break label150;
                     }
                  }

                  value = flag2 ? 1 : 0;
               }

               if (strings != null) {
                  break label142;
               }

               if (value == 0) {
                  break label140;
               }
            }

            boolean flag3 = this.expanded;
            if (strings == null) {
               flag3 = !this.expanded;
            }

            this.expanded = flag3;
         }

         value = button;
      }

      if (strings == null) {
         if (value != 0) {
            return;
         }

         value = flag2 ? 1 : 0;
      }

      if (strings == null) {
         if (value != 0) {
            return;
         }

         value = flag ? 1 : 0;
      }

      if (value != 0) {
         BaseModule module = this.a;
         boolean enabled = this.a.isEnabled();
         if (strings == null) {
            enabled = !enabled;
         }

         module.setEnabled(enabled);
      }
   }

   @Override
   public void mouseReleased(double mouseX, double mouseY, int button) {
      long longValue = KEY ^ 114477042927830L;
      String[] strings = ModuleManager.getFlowGuard();
      Iterator iterator = this.settingComponents.iterator();
      String[] strings2 = strings;

      while (iterator.hasNext()) {
         SettingComponent setting = (SettingComponent)iterator.next();
         setting.mouseReleased(mouseX, mouseY, button);
         if (strings2 != null) {
            break;
         }
      }
   }

   @Override
   public void keyPressed(int keyCode) {
      long longValue = KEY ^ 76769574512542L;
      String[] strings = ModuleManager.getFlowGuard();
      Iterator iterator = this.settingComponents.iterator();
      String[] strings2 = strings;

      while (iterator.hasNext()) {
         SettingComponent setting = (SettingComponent)iterator.next();
         SettingComponent setting2 = setting;
         if (strings2 == null) {
            if (!setting.isVisible() && strings2 == null) {
               continue;
            }

            setting2 = setting;
         }

         setting2.keyPressed(keyCode);
         if (strings2 != null) {
            break;
         }
      }
   }

   @Override
   public float getHeight() {
      long longValue = KEY ^ 11018143576263L;
      String[] strings = ModuleManager.getFlowGuard();
      float floatValue = super.getHeight();
      String[] strings2 = strings;
      float floatValue2 = this.expandAnimator.getValue().floatValue();
      float floatValue3 = floatValue2;
      if (strings2 == null) {
         if (floatValue2 > 0.0F) {
            for (SettingComponent setting : this.settingComponents) {
               if (strings2 == null) {
                  if (!setting.isVisible() && strings2 == null) {
                     continue;
                  }

                  floatValue += setting.getHeight() * floatValue2;
               }

               if (strings2 != null) {
                  break;
               }
            }
         }

         floatValue3 = floatValue;
      }

      return floatValue3;
   }

   private static void drawExpandButton(float x, float y) {
      RenderUtil.fillRoundedRect(x, y, 12.5F, 12.5F, 3.0F, EXPAND_BUTTON_COLOR);
      RenderUtil.strokeRoundedRect(x, y, 12.5F, 12.5F, 2.75F, 0.75F, EXPAND_BUTTON_OUTLINE_COLOR);
   }

   static {
      try {
      long var12 = KEY ^ 50919310717043L;
      Cipher var14;
      Cipher var10000 = var14 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var12 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var15 = 1; var15 < 8; var15++) {
         var10003[var15] = (byte)(var12 << var15 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var11 = new String[3];
      int var19 = 0;
      String var18 = "\u00c9\u0006\u0007\u0098\rn6\u009c\u000f\u000eS\u00ff\u0098r\u00ce=\u0010q\u00fb\u00cf;\u0083\u00a9\u008ftT\u001f\u00cf\u00f7R\u009c#\u00e0\u0010\u0016\u0096\u0004\u00e4\u0018\u0091\u00be\u00cfC\u00ee7\u00ce\f\u00a2(\u00c5";
      int var20 = "\u00c9\u0006\u0007\u0098\rn6\u009c\u000f\u000eS\u00ff\u0098r\u00ce=\u0010q\u00fb\u00cf;\u0083\u00a9\u008ftT\u001f\u00cf\u00f7R\u009c#\u00e0\u0010\u0016\u0096\u0004\u00e4\u0018\u0091\u00be\u00cfC\u00ee7\u00ce\f\u00a2(\u00c5"
         .length();
      char var17 = 16;
      int var16 = -1;

      while (true) {
         byte[] var21 = var14.doFinal(var18.substring(++var16, var16 + var17).getBytes("ISO-8859-1"));
         String var27 = decodeUtf8(var21).intern();
         int var10001 = -1;
         var11[var19++] = var27;
         if ((var16 += var17) >= var20) {
            Cipher var1;
            var10000 = var1 = Cipher.getInstance("DES/CBC/NoPadding");
            var10002 = SecretKeyFactory.getInstance("DES");
            var10003 = new byte[]{(byte)(var12 >>> 56), 0, 0, 0, 0, 0, 0, 0};

            for (int var2 = 1; var2 < 8; var2++) {
               var10003[var2] = (byte)(var12 << var2 * 8 >>> 56);
            }

            var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long[] var0 = new long[2];
            int var4 = 0;
            String var5 = "\u00d1\u00f1\u001c\u00aesZ\u00f4\u00b4\u00eb\u00ab\u00c7*f\u0089\u00c4\u00f3";
            int var6 = "\u00d1\u00f1\u001c\u00aesZ\u00f4\u00b4\u00eb\u00ab\u00c7*f\u0089\u00c4\u00f3".length();
            int var3 = 0;

            do {
               var10001 = var3;
               var3 += 8;
               byte[] var7 = var5.substring(var10001, var3).getBytes("ISO-8859-1");
               var10001 = var4++;
               long var8 = (var7[0] & 255L) << 56
                  | (var7[1] & 255L) << 48
                  | (var7[2] & 255L) << 40
                  | (var7[3] & 255L) << 32
                  | (var7[4] & 255L) << 24
                  | (var7[5] & 255L) << 16
                  | (var7[6] & 255L) << 8
                  | var7[7] & 255L;
               byte[] var10 = var1.doFinal(
                  new byte[]{
                     (byte)(var8 >>> 56),
                     (byte)(var8 >>> 48),
                     (byte)(var8 >>> 40),
                     (byte)(var8 >>> 32),
                     (byte)(var8 >>> 24),
                     (byte)(var8 >>> 16),
                     (byte)(var8 >>> 8),
                     (byte)var8
                  }
               );
               long var10004 = (var10[0] & 255L) << 56
                  | (var10[1] & 255L) << 48
                  | (var10[2] & 255L) << 40
                  | (var10[3] & 255L) << 32
                  | (var10[4] & 255L) << 24
                  | (var10[5] & 255L) << 16
                  | (var10[6] & 255L) << 8
                  | var10[7] & 255L;
               byte var31 = -1;
               var0[var10001] = var10004;
            } while (var3 < var6);

            d = (int)var0[1];
            Z = (int)var0[0];
            CHEVRON_IMAGE = new NvgImage(var11[1]);
            EXPAND_BUTTON_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.15F);
            EXPAND_BUTTON_OUTLINE_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.1F);
            NAME_FONT = Theme.FONT_MANAGER.getFont(var11[2]);
            i = Theme.FONT_MANAGER.getFont(var11[0]);
            break;
         }

         var17 = var18.charAt(var16);
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }

   private static String decodeUtf8(byte[] bytes) {
      int index = 0;
      int value;
      char[] chars = new char[value = bytes.length];

      for (int i2 = 0; i2 < value; i2++) {
         int value2;
         if ((value2 = 255 & bytes[i2]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = bytes[++i2];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i2 < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = bytes[++i2];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = bytes[++i2];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
