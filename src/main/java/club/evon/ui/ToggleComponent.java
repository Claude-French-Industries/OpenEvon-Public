package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.module.impl.render.Overlay;
import club.evon.setting.BooleanSetting;
import club.evon.util.Animator;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.RenderUtil;




public final class ToggleComponent extends SettingComponent<BooleanSetting> {
   private final Animator toggleAnimator;
   private static final int l;
   private static final int R;
   private static final float TRACK_RADIUS = 4.5F;
   private static final long KEY = KeyedRandom.create(-107387567328905744L, 9117640952729115700L, MethodHandles.lookup().lookupClass()).next(52277748006280L);

   public ToggleComponent(BooleanSetting property) {
      super(property);

      long longValue = KEY ^ 124634672364600L;
      this.toggleAnimator = new Animator(200, property.I() ? 1.0 : 0.0, Easing.LINEAR);
      }

   public void render(int mouseX, int mouseY) {
      long longValue = KEY ^ 35370547986223L;
      super.render(mouseX, mouseY);
      int value = ThemeDropdownComponent.getState();
      float floatValue = this.getX();
      int value2 = value;
      float floatValue2 = this.getY();
      float floatValue3 = this.getWidth();
      float floatValue4 = this.getHeight();
      BooleanSetting setting = this.getSetting();
      boolean flag = setting.I();
      this.toggleAnimator.setTargetValue(flag ? 1.0 : 0.0);
      float floatValue5 = this.toggleAnimator.getValue().floatValue();
      float floatValue6 = (floatValue4 - 9.0F) / 2.0F;
      float floatValue7 = floatValue + floatValue3 - 22.5F;
      float floatValue8 = floatValue2 + floatValue6;
      RenderUtil.fillRoundedRect(floatValue7, floatValue8, 18.0F, 9.0F, 4.5F, ColorUtil.withAlpha(Color.WHITE, !this.isHovered(mouseX, mouseY) ? 0.1F : 0.15F));
      if (value2 == 0) {
         if (floatValue5 != 0.0F) {
            Color color = floatValue5 == 1.0F ? Overlay.getPrimaryColor() : ColorUtil.withAlpha(Overlay.getPrimaryColor(), floatValue5);
            Color color2 = floatValue5 == 1.0F ? Overlay.getSecondaryColor() : ColorUtil.withAlpha(Overlay.getSecondaryColor(), floatValue5);
            RenderUtil.fillShadowedGradientRect(floatValue7, floatValue8, 18.0F, 9.0F, 4.5F, color, color2);
         }

         float floatValue9 = 4.5F;
      }

      float floatValue10 = 2.25F;
      RenderUtil.fillRoundedRect(floatValue7 + 2.25F + 9.0F * floatValue5, floatValue8 + 2.25F, 4.5F, 4.5F, 2.25F, Color.WHITE);
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
      long longValue = KEY ^ 50109261662572L;
      int value = ThemeDropdownComponent.getDefaultState();
      if (button == 0) {
         BooleanSetting setting = this.getSetting();
         boolean tmp = setting.I();
         if (value != 0) {
            tmp = !tmp;
         }

         setting.setValue(tmp);
      }
   }

   @Override
   public Color getLabelColor() {
      long longValue = KEY ^ 71193746613601L;
      int value = ThemeDropdownComponent.getState();
      float floatValue = this.toggleAnimator.getValue().floatValue();
      int value2 = value;
      Color labelColor = super.getLabelColor();
      int value3 = labelColor.getRed();
      int value4 = labelColor.getGreen();
      int value5 = labelColor.getBlue();
      float floatValue2;
      value = (floatValue2 = floatValue - 0.0F) == 0.0F ? 0 : (floatValue2 < 0.0F ? -1 : 1);
      if (value2 == 0) {
         if (value == 0) {
            return labelColor;
         }

         float floatValue3;
         value = (floatValue3 = floatValue - 1.0F) == 0.0F ? 0 : (floatValue3 < 0.0F ? -1 : 1);
      }

      return value == 0 ? Color.WHITE : new Color((int)(value3 + (255 - value3) * floatValue), (int)(value4 + (255 - value4) * floatValue), (int)(value5 + (255 - value5) * floatValue));
   }

   static {
      try {
      long var1 = KEY ^ 123918956262061L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/NoPadding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long[] var0 = new long[2];
      int var6 = 0;
      String var7 = "[\u00b7\u001dv\u008b\u00afSlS\u00b2m\u00ae\u0097Y\u008bU";
      int var8 = "[\u00b7\u001dv\u008b\u00afSlS\u00b2m\u00ae\u0097Y\u008bU".length();
      int var5 = 0;

      do {
         int var10001 = var5;
         var5 += 8;
         byte[] var9 = var7.substring(var10001, var5).getBytes("ISO-8859-1");
         var10001 = var6++;
         long var10 = (var9[0] & 255L) << 56
            | (var9[1] & 255L) << 48
            | (var9[2] & 255L) << 40
            | (var9[3] & 255L) << 32
            | (var9[4] & 255L) << 24
            | (var9[5] & 255L) << 16
            | (var9[6] & 255L) << 8
            | var9[7] & 255L;
         byte[] var12 = var3.doFinal(
            new byte[]{
               (byte)(var10 >>> 56),
               (byte)(var10 >>> 48),
               (byte)(var10 >>> 40),
               (byte)(var10 >>> 32),
               (byte)(var10 >>> 24),
               (byte)(var10 >>> 16),
               (byte)(var10 >>> 8),
               (byte)var10
            }
         );
         long var10004 = (var12[0] & 255L) << 56
            | (var12[1] & 255L) << 48
            | (var12[2] & 255L) << 40
            | (var12[3] & 255L) << 32
            | (var12[4] & 255L) << 24
            | (var12[5] & 255L) << 16
            | (var12[6] & 255L) << 8
            | var12[7] & 255L;
         byte var14 = -1;
         var0[var10001] = var10004;
      } while (var5 < var8);

      R = (int)var0[1];
      l = (int)var0[0];
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
