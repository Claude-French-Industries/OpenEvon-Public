package club.evon.module.impl.render;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.joml.Matrix3x2fStack;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.ColorPair;
import club.evon.data.SlotSwitchMode;
import club.evon.event.HudRenderEvent;
import club.evon.event.HudRenderPassEvent;
import club.evon.event.Render2DEvent;
import club.evon.event.Subscribe;
import club.evon.manager.FontManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.module.impl.motion.Scaffold;
import club.evon.util.Animator;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgFont;
import club.evon.util.render.RenderUtil;




public final class ScaffoldHud extends SubMode<Overlay> {
   private static final int B;
   private static final int V;
   private static final int x;
   private static final int k;
   private static final int A;
   private static final float ITEM_OFFSET_X = 7.5F;
   private static final float BORDER_INSET = 0.6F;
   private static final float BORDER_WIDTH = 1.2F;
   private static final float PANEL_RADIUS = 11.875F;
   private static final float OUTER_RADIUS = 12.325F;
   private static final float ITEM_OFFSET_Y = 5.5F;
   private static final float BAR_HEIGHT = 3.5F;
   private static final float BAR_RADIUS = 1.75F;
   private static final Color BORDER_COLOR;
   private static final Color PANEL_BACKGROUND;
   private static final Color BAR_BACKGROUND;
   private static final Color TEXT_COLOR;
   private static final FontManager FONT_MANAGER;
   private static final NvgFont TITLE_FONT;
   private static final NvgFont COUNT_FONT;
   private static final String TITLE;
   private ItemStack blockStack;
   private boolean scaffoldEnabled;
   private final Animator fadeAnim = new Animator(200, 0.0, Easing.LINEAR);
   private Animator progressAnim = new Animator(200, 0.0, Easing.LINEAR);
   private static final long KEY = KeyedRandom.create(8688014846328325965L, 6976953675668626840L, MethodHandles.lookup().lookupClass()).next(135672095553839L);
   private static final String[] STRINGS;

   public ScaffoldHud(String name, Overlay parent) {
      super(name, parent);
   }

   @Override
   public void onDisable() {
      long longValue = KEY ^ 103589225040602L;
      BaseModule[] values = ColorPair.j();
      super.onDisable();
      BaseModule[] values2 = values;
      this.scaffoldEnabled = false;
      if (values2 == null) {
         BaseModule.setStateArray(new BaseModule[1]);
      }
   }

   private ItemStack getBlockStack() {
      long longValue = KEY ^ 55779608395691L;
      BaseModule[] values = ColorPair.j();
      SilentSlotManager instance = SilentSlotManager.getInstance();
      BaseModule[] values2 = values;
      SilentSlotManager silentSlotManager = instance;
      if (values2 != null) {
         if (!instance.isActive()) {
            return ClientContext.MINECRAFT_CLIENT.player.getMainHandStack();
         }

         silentSlotManager = instance;
      }

      if (values2 == null) {
         return silentSlotManager.getMainHandStack(ClientContext.MINECRAFT_CLIENT.player);
      } else {
         return silentSlotManager.getMode() == SlotSwitchMode.FULL ? instance.getMainHandStack(ClientContext.MINECRAFT_CLIENT.player) : ClientContext.MINECRAFT_CLIENT.player.getMainHandStack();
      }
   }

   @Subscribe
   private void onHudRenderPass(HudRenderPassEvent event) {
      long longValue = KEY ^ 24586994332120L;
      BaseModule[] values = ColorPair.j();
      if (!ClientContext.MINECRAFT_CLIENT.options.hudHidden) {
         Scaffold moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Scaffold.class);
         this.scaffoldEnabled = moduleByClass.isEnabled();
         this.fadeAnim.setTargetValue(this.scaffoldEnabled ? 1.0 : 0.0);
         ScaffoldHud tmp = this;
         if (values != null) {
            if (this.fadeAnim.getValue() == 0.0) {
               this.blockStack = null;
               return;
            }

            this.blockStack = this.getBlockStack();
            tmp = this;
         }

         ItemStack stack = tmp.blockStack;
         if (values != null) {
            if (tmp.blockStack == null) {
               return;
            }

            stack = this.blockStack;
         }

         int value = stack.isEmpty() ? 1 : 0;
         if (values != null) {
            if (value != 0) {
               return;
            }

            value = this.blockStack.getItem() instanceof BlockItem ? 1 : 0;
         }

         if (values != null) {
            if (value == 0) {
               return;
            }

            value = ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth() - 155;
         }

         float floatValue = value / 2.0F;
         float floatValue2 = 10.0F - 36.2F * (1.0F - this.fadeAnim.getValue().floatValue());
         float floatValue3 = Overlay.getScale();
         RenderUtil.fillRoundedRect(floatValue - 0.90000004F, floatValue2 - 0.90000004F, 156.8F * floatValue3, 26.8F * floatValue3, 12.325F * floatValue3, Color.BLACK);
      }
   }

   @Subscribe
   private void onRender2D(Render2DEvent event) {
      BaseModule[] values;
      ScaffoldHud scaffoldHud;
      label60: {
         long longValue = KEY ^ 19323836269678L;
         values = ColorPair.j();
         int value;
         if (values != null) {
            if (ClientContext.MINECRAFT_CLIENT.options.hudHidden) {
               return;
            }

            scaffoldHud = this;
            if (values == null) {
               break label60;
            }

            double doubleValue;
            value = (doubleValue = this.fadeAnim.getValue() - 0.0) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
         } else {
            value = ClientContext.MINECRAFT_CLIENT.options.hudHidden ? 1 : 0;
         }

         if (value == 0) {
            return;
         }

         scaffoldHud = this;
      }

      ItemStack stack = scaffoldHud.blockStack;
      if (values != null) {
         if (scaffoldHud.blockStack == null) {
            return;
         }

         stack = this.blockStack;
      }

      int value2 = stack.isEmpty() ? 1 : 0;
      if (values != null) {
         if (value2 != 0) {
            return;
         }

         value2 = this.blockStack.getItem() instanceof BlockItem ? 1 : 0;
      }

      if (values != null) {
         if (value2 == 0) {
            return;
         }

         value2 = ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth() - 155;
      }

      float floatValue = value2 / 2.0F;
      float floatValue2 = 10.0F - 36.2F * (1.0F - this.fadeAnim.getValue().floatValue());
      DrawContext drawContext = event.getDrawContext();
      Matrix3x2fStack matrices = drawContext.getMatrices();
      matrices.pushMatrix();
      matrices.translate(floatValue + 7.5F - 1.0F, floatValue2 + 5.5F - 1.0F);
      float floatValue3 = Overlay.getScale();
      matrices.scale(floatValue3, floatValue3);
      drawContext.drawItem(this.blockStack, 0, 0);
      matrices.popMatrix();
   }

   @Subscribe
   private void onHudRender(HudRenderEvent event) {
      BaseModule[] values;
      ScaffoldHud scaffoldHud;
      label92: {
         long longValue = KEY ^ 67145794050073L;
         values = ColorPair.j();
         int value;
         if (values != null) {
            if (ClientContext.MINECRAFT_CLIENT.options.hudHidden) {
               return;
            }

            scaffoldHud = this;
            if (values == null) {
               break label92;
            }

            double doubleValue;
            value = (doubleValue = this.fadeAnim.getValue() - 0.0) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
         } else {
            value = ClientContext.MINECRAFT_CLIENT.options.hudHidden ? 1 : 0;
         }

         if (value == 0) {
            return;
         }

         scaffoldHud = this;
      }

      label69:
      if (values != null) {
         if (scaffoldHud.blockStack != null) {
            scaffoldHud = this;
            if (values == null) {
               break label69;
            }

            label76:
            if (!this.blockStack.isEmpty()) {
               boolean flag = this.blockStack.getItem() instanceof BlockItem;
               if (values != null) {
                  if (!flag) {
                     break label76;
                  }

                  flag = ClientContext.MINECRAFT_CLIENT.player.isInCreativeMode();
               }

               boolean flag2;
               int value2;
               double doubleValue2;
               label64: {
                  flag2 = flag;
                  value2 = this.blockStack.getCount();
                  this.progressAnim.setDuration(200);
                  int value3 = flag2 ? 1 : 0;
                  if (values != null) {
                     if (flag2) {
                        doubleValue2 = 1.0;
                        break label64;
                     }

                     value3 = value2;
                  }

                  doubleValue2 = (float)value3 / this.blockStack.getMaxCount();
               }

               float floatValue;
               float floatValue2;
               float floatValue3;
               Object object;
               label58: {
                  this.progressAnim.setTargetValue(doubleValue2);
                  this.progressAnim.getValue();
                  floatValue = (ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth() - 155) / 2.0F;
                  float floatValue4 = 10.0F - 36.2F * (1.0F - this.fadeAnim.getValue().floatValue());
                  float floatValue5 = Overlay.getScale();
                  RenderUtil.fillRoundedRectPaint(floatValue - 0.90000004F, floatValue4 - 0.90000004F, 156.8F * floatValue5, 26.8F * floatValue5, 12.325F * floatValue5, RenderUtil.gameBlurPaint);
                  RenderUtil.saveState();
                  RenderUtil.translate(floatValue, floatValue4);
                  RenderUtil.scale(floatValue5, floatValue5);
                  RenderUtil.fillRoundedRect(0.0F, 0.0F, 155.0F, 25.0F, 11.875F, PANEL_BACKGROUND);
                  RenderUtil.strokeRoundedRect(-0.6F, -0.6F, 156.2F, 26.2F, 12.325F, 1.2F, BORDER_COLOR);
                  float floatValue6 = 7.5F;
                  floatValue3 = 5.5F;
                  floatValue2 = floatValue6 + 16.0F;
                  floatValue3 += 0.5F;
                  String[] strings = STRINGS;
                  TITLE_FONT.drawText(strings[2], floatValue2, floatValue3, 7.0F, Color.WHITE.getRGB());
                  floatValue3 += TITLE_FONT.getTextHeight(strings[1], 7.0F) + 0.5F;
                  int value4 = flag2 ? 1 : 0;
                  if (values != null) {
                     if (flag2) {
                        object = STRINGS[5];
                        break label58;
                     }

                     value4 = value2;
                  }

                  object = value4;
               }

               String valueOf = String.valueOf(object);
               String[] strings2 = STRINGS;
               String text = valueOf + strings2[0];
               COUNT_FONT.drawText(text, floatValue2, floatValue3, 6.0F, TEXT_COLOR.getRGB());
               floatValue2 += TITLE_FONT.getTextWidth(strings2[1], 6.0F) + 15.0F;
               float floatValue7 = 11.25F;
               float floatValue8 = 147.0F - (floatValue + floatValue2 - floatValue);
               RenderUtil.fillRoundedRect(floatValue2, 11.5F, floatValue8, 3.5F, 1.75F, BAR_BACKGROUND);
               floatValue8 = (float)(floatValue8 * this.progressAnim.getValue());
               RenderUtil.fillShadowedGradientRect(floatValue2, 11.5F, floatValue8, 3.5F, 1.5F, Overlay.getPrimaryColor(), Overlay.getSecondaryColor());
               RenderUtil.restoreState();
               return;
            }
         }

         scaffoldHud = this;
      }

      scaffoldHud.progressAnim = new Animator(this.progressAnim.getDuration(), 1.0, this.progressAnim.getEasing());
   }

   static {
      try {
         long var11 = KEY ^ 121766985493889L;
         Cipher var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
         SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
         byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

         for (int var14 = 1; var14 < 8; var14++) {
            var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
         }

         var13.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
         String[] var20 = new String[6];
         int var18 = 0;
         String var17 = "\u00af\u00a9\u001b\u00d1\u00dbs\u0095\"\u0010\u009b\u00bfR\u00c9A\u0018>\u00ee\u00c3\u00a6\u001f\u0010\u0014w\u00003\u0010\u009b\u00bfR\u00c9A\u0018>\u00ee\u00c3\u00a6\u001f\u0010\u0014w\u00003\u0010k\u009c\b\u00caLD\\\u00c9\u0089V>\u00cc\u00c9\u0006!\u00e7";
         int var19 = var17.length();
         int var16 = 8;
         int var25 = -1;
         boolean varA_b = false;

         while (true) {
            String var26 = var17.substring(++var25, var25 + var16);
            byte[] var21 = var13.doFinal(var26.getBytes("ISO-8859-1"));
            var20[var18++] = decodeUtf8(var21).intern();
            var25 += var16;
            if (!varA_b) {
               if (var25 < var19) {
                  var16 = var17.charAt(var25);
               } else {
                  var17 = "\u00e3S\u00e6\u00b3\u0001\u00ca\u00ad\u00d7w\u00fb,C\u00ecvV\u00a8\b\u00aaH\u0010C\u00dc\u0090\u0087\u00b3";
                  var19 = var17.length();
                  var16 = 16;
                  var25 = -1;
                  varA_b = true;
               }
            } else {
               if (var25 >= var19) {
                  break;
               }

               var16 = var17.charAt(var25);
            }
         }

         STRINGS = var20;
         TITLE = STRINGS[1];
         Cipher var1 = Cipher.getInstance("DES/CBC/NoPadding");
         SecretKeyFactory var10006 = SecretKeyFactory.getInstance("DES");
         byte[] var10007 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

         for (int var2 = 1; var2 < 8; var2++) {
            var10007[var2] = (byte)(var11 << var2 * 8 >>> 56);
         }

         var1.init(2, var10006.generateSecret(new DESKeySpec(var10007)), new IvParameterSpec(new byte[8]));
         long[] var0 = new long[4];
         int var4 = 0;
         String var5 = "\u00966\u009a*Y\u00f0\u00b0\u009b\u00152(\u00c7\u007f\t\u001a\u0093";
         int var6 = var5.length();
         int var3 = 0;
         boolean varB_b = false;

         while (true) {
            String var7s = var5.substring(var3, var3 + 8);
            var3 += 8;
            byte[] var7 = var7s.getBytes("ISO-8859-1");
            long var41 = (var7[0] & 255L) << 56
               | (var7[1] & 255L) << 48
               | (var7[2] & 255L) << 40
               | (var7[3] & 255L) << 32
               | (var7[4] & 255L) << 24
               | (var7[5] & 255L) << 16
               | (var7[6] & 255L) << 8
               | var7[7] & 255L;
            byte[] var10 = var1.doFinal(
               new byte[]{
                  (byte)(var41 >>> 56),
                  (byte)(var41 >>> 48),
                  (byte)(var41 >>> 40),
                  (byte)(var41 >>> 32),
                  (byte)(var41 >>> 24),
                  (byte)(var41 >>> 16),
                  (byte)(var41 >>> 8),
                  (byte)var41
               }
            );
            long var46 = (var10[0] & 255L) << 56
               | (var10[1] & 255L) << 48
               | (var10[2] & 255L) << 40
               | (var10[3] & 255L) << 32
               | (var10[4] & 255L) << 24
               | (var10[5] & 255L) << 16
               | (var10[6] & 255L) << 8
               | var10[7] & 255L;
            var0[var4++] = var46;
            if (var3 >= var6) {
               if (varB_b) {
                  break;
               }

               var5 = "\u00ae\u008cd\u009dp\u00e7\u00b0\u001f\u00853\u00f3\u00d2\u00e4\u00cdr\u00d2";
               var6 = var5.length();
               var3 = 0;
               varB_b = true;
            }
         }

         x = (int)var0[3];
         B = (int)var0[0];
         k = (int)var0[1];
         A = (int)var0[1];
         V = (int)var0[2];
         BORDER_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.05);
         PANEL_BACKGROUND = ColorUtil.withAlpha(new Color(35, 35, 35), 0.3F);
         BAR_BACKGROUND = ColorUtil.withAlpha(Color.WHITE, 0.25);
         TEXT_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.35F);
         FONT_MANAGER = EvonClient.getInstance().getFontManager();
         String[] var23 = STRINGS;
         TITLE_FONT = FONT_MANAGER.getFont(var23[3]);
         COUNT_FONT = FONT_MANAGER.getFont(var23[4]);
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
