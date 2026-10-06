package club.evon.module.impl.render;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.TargetIndicatorType;
import club.evon.event.HudRenderEvent;
import club.evon.event.HudRenderPassEvent;
import club.evon.event.Subscribe;
import club.evon.manager.FontManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.impl.combat.KillAura;
import club.evon.setting.PositionSetting;
import club.evon.ui.TargetStatusIcon;
import club.evon.util.Animator;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.math.RandomUtil;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgFont;
import club.evon.util.render.NvgImage;
import club.evon.util.render.RenderUtil;




public final class TargetDisplay extends BaseModule {
   public LivingEntity target;
   private final Animator healthAnim;
   private final Animator hurtAnim;
   private final Animator delayedHealthAnim;
   private final Animator lowHealthAnim;
   private static final float CORNER_RADIUS = 5.0F;
   private static final float OUTLINE_RADIUS = 6.0F;
   private static final float PADDING = 3.5F;
   private static final float SHADOW_RADIUS = 5.5F;
   private static final float MIN_WIDTH = 118.0F;
   private static final float PANEL_HEIGHT = 37.5F;
   private static final float BAR_HEIGHT = 5.5F;
   private static final float ICON_SIZE = 24.5F;
   private static final float SHADOW_OFFSET = 1.0F;
   private static final float PANEL_PADDING = 2.0F;
   private static final float HEALTH_ICON_HEIGHT = 5.5F;
   private static final float BAR_RADIUS = 2.6666667F;
   private static final Color BAR_BACKGROUND;
   private float width;
   private final TargetStatusIcon[] statusIcons;
   private static final NvgImage HEALTH_ICON;
   private static final FontManager FONT_MANAGER;
   private static final NvgFont NAME_FONT;
   private static final NvgFont INFO_FONT;
   private static final float NAME_GAP = 2.5F;
   private static final Color BORDER_COLOR;
   private static final Color PANEL_BACKGROUND;
   private final PositionSetting position;
   private static final long KEY = KeyedRandom.create(9215283521949744867L, 5101181048801492530L, MethodHandles.lookup().lookupClass()).next(94656787526455L);
   private static final String[] STRINGS;

   public TargetDisplay() {
      super((STRINGS)[5], (STRINGS)[1], ModuleCategory.RENDER);

      String[] strings = STRINGS;
      this.healthAnim = new Animator(50, 0.0, Easing.LINEAR);
      this.hurtAnim = new Animator(150, 0.0, Easing.LINEAR);
      this.delayedHealthAnim = new Animator(350, 0.0, Easing.LINEAR);
      this.lowHealthAnim = new Animator(400, 0.0, Easing.LINEAR);
      this.width = 118.0F;
      this.statusIcons = new TargetStatusIcon[]{new TargetStatusIcon(this, TargetIndicatorType.BLOCKING), new TargetStatusIcon(this, TargetIndicatorType.STRENGTH)};
      this.position = new PositionSetting(0.55F, 0.5F);
      this.addSettings(this.position);
      }

   @Override
   public void onDisable() {
      this.target = null;
      super.onDisable();
   }

   @Subscribe
   private void onHudRenderPass(HudRenderPassEvent event) {
      long longValue = KEY ^ 37310787261948L;
      boolean flagSet = Animations.isFlagSet();
      if (!ClientContext.MINECRAFT_CLIENT.options.hudHidden) {
         float floatValue;
         float floatValue2;
         Object object;
         label164: {
            floatValue = this.position.getX();
            floatValue2 = this.position.getY();
            KillAura moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(KillAura.class);
            LivingEntity target2 = moduleByClass.getTarget();
            object = null;
            LivingEntity tmp = target2;
            if (!flagSet) {
               if (target2 == null) {
                  MinecraftClient minecraftClient = ClientContext.MINECRAFT_CLIENT;
                  if (!flagSet) {
                     if (!(ClientContext.MINECRAFT_CLIENT.currentScreen instanceof ChatScreen)) {
                        break label164;
                     }

                     minecraftClient = ClientContext.MINECRAFT_CLIENT;
                  }

                  object = minecraftClient.player;
                  break label164;
               }

               tmp = target2;
            }

            object = tmp;
         }

         LivingEntity entity;
         entity = this.target;
         label153:
         if (!flagSet) {
            if (this.target != object) {
               this.target = (LivingEntity)object;
               entity = this.target;
               if (flagSet) {
                  break label153;
               }

               if (this.target != null) {
                  float floatValue3 = this.target.getHealth();
                  this.healthAnim.setValue(floatValue3);
                  this.delayedHealthAnim.setValue(floatValue3);
               }
            }

            entity = this.target;
         }

         if (entity != null) {
            float floatValue4 = 31.5F;
            String string = this.target.getName().getString();
            float floatValue5 = 31.5F + NAME_FONT.getTextWidth(string, 9.0F) + 2.5F;
            float floatValue6 = 4.0F;
            int index = 0;
            TargetStatusIcon[] values = this.statusIcons;
            int length = values.length;
            int index2 = 0;

            while (true) {
               if (index2 < length) {
                  TargetStatusIcon targetStatusIcon = values[index2];
                  if (!flagSet) {
                     boolean test = targetStatusIcon.indicatorType.predicate.test(this.target);
                     if (flagSet) {
                        break;
                     }

                     if (test) {
                        floatValue5 += 11.0F;
                        index++;
                     }

                     index2++;
                  }

                  if (!flagSet) {
                     continue;
                  }
               }

               break;
            }

            if (index != 0) {
               floatValue5 -= 3.0F;
               floatValue5 += 4.0F;
            }

            label134: {
               TargetDisplay targetDisplay = this;
               boolean flag = false;
               label169: {
                  label170: {
                     label131: {
                        floatValue5 += 1.5F;
                        this.width = Math.max(floatValue5, 118.0F);
                        RenderUtil.saveState();
                        RenderUtil.translate(floatValue, floatValue2);
                        float floatValue7 = Overlay.getScale();
                        RenderUtil.scale(floatValue7, floatValue7);
                        this.position.setWidth((this.width + 2.0F) * floatValue7);
                        this.position.setHeight(39.5F * floatValue7);
                        this.position.setRound(5.0F);
                        float floatValue8 = this.target.getHealth();
                        this.healthAnim.setTargetValue(floatValue8);
                        this.delayedHealthAnim.setTargetValue(floatValue8);
                        TargetDisplay targetDisplay2 = this;
                        if (!flagSet) {
                           if (this.target.hurtTime >= 9) {
                              boolean finished = this.hurtAnim.isFinished();
                              if (flagSet) {
                                 break label170;
                              }

                              if (!finished) {
                                 break label131;
                              }

                              double doubleValue = this.hurtAnim.getValue();
                              int value = doubleValue == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
                              if (flagSet) {
                                 break label170;
                              }

                              if (value != 0) {
                                 break label131;
                              }

                              this.hurtAnim.setValue(1.0);
                              if (!flagSet) {
                                 break label131;
                              }
                           }

                           targetDisplay2 = this;
                        }

                        targetDisplay2.hurtAnim.setTargetValue(0.0);
                     }

                     targetDisplay = this;
                     if (flagSet) {
                        break label169;
                     }

                     float floatValue9 = this.target.getHealth() - this.target.getMaxHealth() / 4.0F;
                     flag = floatValue9 <= 0.0F;
                  }

                  if (flag) {
                     Animator animator = this.lowHealthAnim;
                     if (!flagSet) {
                        if (!this.lowHealthAnim.isFinished()) {
                           break label134;
                        }

                        animator = this.lowHealthAnim;
                     }

                     double doubleValue2 = this.lowHealthAnim.getValue();
                     if (!flagSet) {
                        doubleValue2 = doubleValue2 == 0.0 ? 1.0 : 0.0;
                     }

                     animator.setTargetValue(doubleValue2);
                     if (!flagSet) {
                        break label134;
                     }
                  }

                  targetDisplay = this;
               }

               targetDisplay.lowHealthAnim.setTargetValue(0.0);
            }

            RenderUtil.fillRoundedRectWithBlur(-1.0F, -1.0F, this.width + 2.0F, 39.5F, 5.5F, Color.BLACK);
            RenderUtil.restoreState();
         }
      }
   }

   @Subscribe
   private void onHudRender(HudRenderEvent event) {
      long longValue = KEY ^ 49122074472377L;
      boolean flagUnset = Animations.isFlagUnset();
      if (!ClientContext.MINECRAFT_CLIENT.options.hudHidden) {
         label61: {
            float floatValue;
            float floatValue2;
            float floatValue3;
            float floatValue4;
            float floatValue5;
            label65: {
               float floatValue6 = Overlay.getScale();
               float floatValue7 = this.position.getX();
               float floatValue8 = this.position.getY();
               this.position.setWidth((this.width + 2.0F) * floatValue6);
               this.position.setHeight(39.5F * floatValue6);
               this.position.setRound(5.0F);
               LivingEntity tmp = this.target;
               if (flagUnset) {
                  if (this.target == null) {
                     return;
                  }

                  RenderUtil.saveState();
                  RenderUtil.translate(-10.0F, -10.0F);
                  RenderUtil.fillRoundedRectPaint(floatValue7 - 1.0F + 10.0F, floatValue8 - 1.0F + 10.0F, (this.width + 2.0F) * floatValue6, 39.5F * floatValue6, 6.0F * floatValue6, RenderUtil.gameBlurPaint);
                  RenderUtil.restoreState();
                  RenderUtil.saveState();
                  RenderUtil.translate(floatValue7, floatValue8);
                  RenderUtil.scale(floatValue6, floatValue6);
                  RenderUtil.fillRoundedRect(0.0F, 0.0F, this.width, 37.5F, 5.0F, PANEL_BACKGROUND);
                  floatValue = -0.5F;
                  floatValue2 = -0.5F;
                  floatValue3 = this.width + 1.0F;
                  floatValue4 = 38.5F;
                  floatValue5 = 5.5F;
                  if (!flagUnset) {
                     break label65;
                  }

                  RenderUtil.strokeRoundedRect(-0.5F, -0.5F, floatValue3, 38.5F, 5.5F, 1.0F, BORDER_COLOR);
                  tmp = this.target;
               }

               if (tmp instanceof PlayerEntity) {
                  RenderUtil.drawEntityHead(3.5F, 3.5F, 24.5F, 3.5F, Color.WHITE, this.target);
                  if (flagUnset) {
                     break label61;
                  }
               }

               floatValue = 3.5F;
               floatValue2 = 3.5F;
               floatValue3 = 24.5F;
               floatValue4 = 24.5F;
               floatValue5 = 3.5F;
            }

            RenderUtil.fillRoundedRect(floatValue, floatValue2, floatValue3, floatValue4, floatValue5, Color.WHITE);
         }

         RenderUtil.fillRoundedRect(3.5F, 3.5F, 24.5F, 24.5F, 3.5F, new Color(255, 0, 0, (int)(85.0F * this.hurtAnim.getValue().floatValue())));
         float floatValue9 = 30.8F;
         float floatValue10 = 4.5F;
         String string = this.target.getName().getString();
         float floatValue11 = this.lowHealthAnim.getValue().floatValue();
         int value = 255 - (int)(150.0F * floatValue11);
         NAME_FONT.drawText(string, 30.8F, floatValue10, 9.0F, new Color(255, value, value).getRGB());
         floatValue10 += 9.0F;
         float floatValue12 = this.healthAnim.getValue().floatValue();
         double doubleValue = RandomUtil.round(floatValue12, 1);
         Color withAlpha = ColorUtil.withAlpha(Color.WHITE, 0.75);
         NvgFont nvgFont = INFO_FONT;
         double doubleValue2 = RandomUtil.round(ClientContext.MINECRAFT_CLIENT.player.distanceTo(this.target), 1);
         String[] strings = STRINGS;
         nvgFont.drawText(strings[4] + doubleValue2, 30.8F, floatValue10, 7.0F, withAlpha.getRGB());
         floatValue10 += 7.0F;
         String text = strings[3] + doubleValue;
         INFO_FONT.drawText(text, 30.8F, floatValue10, 7.0F, withAlpha.getRGB());
         floatValue10 += 0.5F;
         RenderUtil.drawImage(30.8F + INFO_FONT.getTextWidth(text, 7.0F) + 2.0F, floatValue10, 6.0F, 5.5F, Color.WHITE, HEALTH_ICON);
         float floatValue13 = 30.8F + NAME_FONT.getTextWidth(string, 9.0F) + 2.5F;
         float floatValue14 = 3.5F;
         TargetStatusIcon[] values = this.statusIcons;
         int length = values.length;
         int index = 0;

         while (index < length) {
            TargetStatusIcon targetStatusIcon = values[index];
            if (flagUnset) {
               if (targetStatusIcon.render(floatValue13, 3.5F)) {
                  floatValue13 += 11.0F;
               }

               index++;
            }

            if (!flagUnset) {
               break;
            }
         }

         float floatValue15 = 3.5F;
         float floatValue16 = 30.0F;
         double doubleValue3 = RandomUtil.round(ClientContext.MINECRAFT_CLIENT.player.getHealth() - this.target.getHealth(), 1);
         String text2 = (doubleValue3 >= 0.0 ? "+" : "") + doubleValue3;
         float floatValue17 = this.width - 7.0F;
         INFO_FONT.drawText(text2, 3.5F + floatValue17 - INFO_FONT.getTextWidth(text2, 4.5F), 30.0F - (INFO_FONT.getTextHeight(STRINGS[2], 4.5F) + 2.0F) + 1.0F, 4.5F, withAlpha.getRGB());
         RenderUtil.fillRoundedRect(3.5F, 30.0F, floatValue17 - 0.15F, 5.5F, 2.6666667F, BAR_BACKGROUND);
         Color primaryColor = Overlay.getPrimaryColor();
         Color secondaryColor = Overlay.getSecondaryColor();
         float floatValue18 = this.target.getMaxHealth();
         RenderUtil.fillShadowedGradientRect(3.5F, 30.0F, floatValue17 * (this.delayedHealthAnim.getValue().floatValue() / floatValue18), 5.5F, 2.6666667F, ColorUtil.scaleBrightness(primaryColor, 0.75F), ColorUtil.scaleBrightness(secondaryColor, 0.75F));
         RenderUtil.fillShadowedGradientRect(3.5F, 30.0F, floatValue17 * (floatValue12 / floatValue18), 5.5F, 2.6666667F, primaryColor, secondaryColor);
         RenderUtil.restoreState();
      }
   }

   static {
      try {
      long var0 = KEY ^ 4465423770684L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[8];
      int var7 = 0;
      String var6 = "\u00da\u000b\u008a\u00b3\u00ac\u00aa\u00b0o\u00a6\u00bev}v\u00e9\u00f8\u0087\u0018\\\u0094.\u0081\u00f2\u00b1\b\u00c1\u00aa\u008a\"\u00ea\u000eYnF\u00a9\u0090\u0092A\u00d6\u001f\u00d8\u00ee\b\u001c\u00e1\u00e7\u00fb\u00ee\u00f7\u00a3\u00bd\u0010\u00b5\u009eqX+x\u0094\u00ede\u0012\u00d1\u0082\u00e554U\u0010~\u0016\u001d4c\u00a7O`\u0015]-\u00ff\f\u0016\u00a7\u0000\u0010\u00d2\u0081\u000f\u0003\u00f0\u0088O\u0099|\u00da\u0096\u00f4j\u00ed\u0085!";
      int var8 = "\u00da\u000b\u008a\u00b3\u00ac\u00aa\u00b0o\u00a6\u00bev}v\u00e9\u00f8\u0087\u0018\\\u0094.\u0081\u00f2\u00b1\b\u00c1\u00aa\u008a\"\u00ea\u000eYnF\u00a9\u0090\u0092A\u00d6\u001f\u00d8\u00ee\b\u001c\u00e1\u00e7\u00fb\u00ee\u00f7\u00a3\u00bd\u0010\u00b5\u009eqX+x\u0094\u00ede\u0012\u00d1\u0082\u00e554U\u0010~\u0016\u001d4c\u00a7O`\u0015]-\u00ff\f\u0016\u00a7\u0000\u0010\u00d2\u0081\u000f\u0003\u00f0\u0088O\u0099|\u00da\u0096\u00f4j\u00ed\u0085!"
         .length();
      char var5 = 16;
      int var13 = -1;

      label27:
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
                     STRINGS = var9;
                     BAR_BACKGROUND = ColorUtil.withAlpha(new Color(0, 0, 0), 0.25);
                     String[] var11 = STRINGS;
                     HEALTH_ICON = new NvgImage(var11[7]);
                     FONT_MANAGER = EvonClient.getInstance().getFontManager();
                     NAME_FONT = FONT_MANAGER.getFont(var11[6]);
                     INFO_FONT = FONT_MANAGER.getFont(var11[0]);
                     BORDER_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.05F);
                     PANEL_BACKGROUND = ColorUtil.withAlpha(new Color(35, 35, 35), 0.3F);
                     break label27;
                  }

                  var5 = var6.charAt(var13);
                  break;
               default:
                  var9[var7++] = var20;
                  if ((var13 += var5) < var8) {
                     var5 = var6.charAt(var13);
                     continue label27;
                  }

                  var6 = "\u0013\u0000\u0015m\u00f8+\u00a6\u0082\u00ecw\u008c\u0090\u00b8\u00d9w\f\u0010Jg\u00b5w\u00a9>\u00d3v#3\u00c2\u00bb.2{{";
                  var8 = "\u0013\u0000\u0015m\u00f8+\u00a6\u0082\u00ecw\u008c\u0090\u00b8\u00d9w\f\u0010Jg\u00b5w\u00a9>\u00d3v#3\u00c2\u00bb.2{{".length();
                  var5 = 16;
                  var13 = -1;
            }

            var14 = var6.substring(++var13, var13 + var5);
            var10001 = 0;
         }
      }
   
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}

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


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
