package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.setting.EnumSetting;
import club.evon.util.Animator;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.TextUtil;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgImage;
import club.evon.util.render.RenderUtil;




public final class EnumDropdownComponent extends SettingComponent<EnumSetting<?>> {
   private boolean expanded;
   private final Animator expandAnimator = new Animator(200, 0.0, Easing.LINEAR);
   private static final float ROW_HEIGHT = 15.0F;
   private static final float ARROW_OFFSET = 1.5F;
   private static final float ROW_RADIUS = 4.0F;
   private static final float ARROW_WIDTH = 5.5F;
   private static final float ARROW_HEIGHT = 3.5F;
   private static final NvgImage ARROW_IMAGE;
   private static final long KEY = KeyedRandom.create(7914719303441783405L, -1087950943654020256L, MethodHandles.lookup().lookupClass()).next(515981450113L);

   public EnumDropdownComponent(EnumSetting<?> property) {
      super(property);
   }

   public void render(int mouseX, int mouseY) {
      long longValue = KEY ^ 57495111445178L;
      int value = ThemeDropdownComponent.getDefaultState();
      super.render(mouseX, mouseY);
      float floatValue = this.getX();
      int value2 = value;
      float floatValue2 = this.getY();
      float floatValue3 = this.getWidth();
      EnumSetting setting = this.getSetting();
      float floatValue4 = super.getHeight();
      float floatValue5 = floatValue2 + floatValue4;
      this.expandAnimator.setTargetValue(this.expanded ? 1.0 : 0.0);
      RenderUtil.pushClip(floatValue, floatValue2 + floatValue4, floatValue3, this.getHeight() - (4.5F + floatValue4));
      ArrayList list = new ArrayList(setting.getValues());
      list.remove(setting.getValue());
      list.addFirst((Enum)setting.getValue());
      Iterator iterator = list.iterator();

      float floatValue6;
      float floatValue7;
      while (true) {
         if (iterator.hasNext()) {
            Enum enumValue = (Enum)iterator.next();
            boolean hovered = HoverUtil.isHovered(mouseX, mouseY, floatValue + 4.5F, floatValue5, floatValue3 - 9.0F, 15.0);
            RenderUtil.pushClip(floatValue, floatValue5, floatValue3, 15.0F);
            float floatValue8 = 0.85F;
            float floatValue9 = 0.425F;
            RenderUtil.strokeRoundedRect(
               floatValue + 4.5F + 0.425F, floatValue2 + floatValue4 + 0.425F, floatValue3 - 9.0F - 0.85F, this.getHeight() - (floatValue4 + 4.5F) - 0.85F, 4.0F, 0.85F, new Color(184, 202, 209, 19)
            );
            floatValue7 = floatValue + 4.5F;
            floatValue6 = floatValue2 + floatValue4;
            if (value2 == 0) {
               break;
            }

            RenderUtil.fillRoundedRect(floatValue7, floatValue6, floatValue3 - 9.0F, this.getHeight() - (floatValue4 + 4.5F), 4.0F, ColorUtil.withAlpha(Color.WHITE, !hovered ? 0.1F : 0.15F));
            Theme.FONT
               .drawText(
                  TextUtil.toDisplayName(enumValue.toString()),
                  floatValue + 4.5F + 4.0F,
                  floatValue5 + 4.0F + 0.5F,
                  7.0F,
                  setting.getValue() == enumValue ? Color.WHITE.getRGB() : new Color(220, 220, 220).getRGB()
               );
            RenderUtil.popClip();
            floatValue5 += 16.5F;
            if (value2 != 0) {
               continue;
            }
         }

         floatValue7 = floatValue + floatValue3;
         floatValue6 = 14.5F;
         break;
      }

      float floatValue10 = floatValue7 - floatValue6;
      float floatValue11 = floatValue2 + floatValue4 + 4.5F + 1.5F;
      RenderUtil.saveState();
      RenderUtil.translate(floatValue10 + 2.75F, floatValue11 + 1.75F);
      RenderUtil.rotate(3.125F * this.expandAnimator.getValue().floatValue());
      RenderUtil.translate(-2.75F, -1.75F);
      RenderUtil.drawImage(0.0F, 0.0F, 5.5F, 3.5F, null, ARROW_IMAGE);
      RenderUtil.restoreState();
      RenderUtil.popClip();
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
      int value;
      EnumSetting setting;
      int value2;
      boolean var18b = false;
      long longValue = KEY ^ 63716913503481L;
      value2 = ThemeDropdownComponent.getState();
      setting = this.getSetting();
      value = value2;
      value2 = this.expanded ? 1 : 0;
      label96:
      if (value == 0) {
         if (this.expanded) {
            var18b = HoverUtil.isHovered(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), super.getHeight() + 16.5F);
            if (value != 0) {
               break label96;
            }

            if (!var18b) {
               if (button != 0) {
                  return;
               }

               float floatValue = this.getX();
               float floatValue2 = this.getY();
               float floatValue3 = this.getWidth();
               float floatValue4 = super.getHeight();
               float floatValue5 = floatValue2 + floatValue4;
               ArrayList list = new ArrayList(setting.getValues());
               list.remove(setting.getValue());
               list.addFirst((Enum)setting.getValue());

               for (Object object : list) {
                  if (value != 0) {
                     return;
                  }

                  if (value == 0) {
                     if (HoverUtil.isHovered(mouseX, mouseY, floatValue + 4.5F, floatValue5, floatValue3 + 9.0F, 15.0)) {
                        setting.setByName(object.toString());
                        this.expanded = false;
                        if (value == 0) {
                           break;
                        }
                     }

                     floatValue5 += 16.5F;
                  }

                  if (value != 0) {
                     break;
                  }
               }

               if (value == 0) {
                  return;
               }
            }
         }

         value2 = button;
      }

      if (value == 0) {
         if (value2 > 1) {
            return;
         }

         var18b = HoverUtil.isHovered(mouseX, mouseY, this.getX() + 4.5F, this.getY() + super.getHeight(), this.getWidth() - 9.0F, 15.0);
      }

      if (value == 0) {
         if (!var18b) {
            return;
         }

         value2 = setting.getValues().size();
      }

      if (value2 > 1) {
         boolean tmp = this.expanded;
         if (value == 0) {
            tmp = !this.expanded;
         }

         this.expanded = tmp;
      }
   }

   @Override
   public float getHeight() {
      long longValue = KEY ^ 83036039138795L;
      int value = ThemeDropdownComponent.getState();
      float floatValue = super.getHeight();
      float floatValue2 = this.expandAnimator.getValue().floatValue();
      float floatValue3 = 0.0F;
      if (value == 0) {
         if (floatValue2 == 0.0F) {
            return floatValue + (16.5F + 0.0F * this.expandAnimator.getValue().floatValue()) - 1.5F + 4.5F;
         }

         floatValue2 = 16.5F;
         floatValue3 = this.getSetting().getValues().size() - 1;
      }

      return floatValue + (16.5F + floatValue2 * floatValue3 * this.expandAnimator.getValue().floatValue()) - 1.5F + 4.5F;
   }

   static {
      try {
      long var1 = KEY ^ 111847714450361L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var5 = var3.doFinal("\u0093;vS0\u00f0=\u00b1\u00a6\u00b3\u00eb\u001buZ\u00a5L".getBytes("ISO-8859-1"));
      String var6 = decodeUtf8(var5).intern();
      byte var10001 = -1;
      String var0 = var6;
      ARROW_IMAGE = new NvgImage(var0);
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
