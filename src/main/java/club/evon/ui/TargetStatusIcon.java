package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.data.TargetIndicatorType;
import club.evon.module.impl.render.Animations;
import club.evon.module.impl.render.TargetDisplay;
import club.evon.util.KeyedRandom;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.RenderUtil;




public final class TargetStatusIcon {
   private static final float SHADOW_ALPHA = 0.25F;
   private static final int I;
   private static final int j;
   private static final int Z;
   public final TargetIndicatorType indicatorType;
   private static final Color SHADOW_COLOR;
   public final TargetDisplay targetDisplay;
   private static final long KEY = KeyedRandom.create(2758395506554900871L, -6605111819448346015L, MethodHandles.lookup().lookupClass()).next(244706317924223L);

   public TargetStatusIcon(TargetDisplay targetDisplay2, TargetIndicatorType targetIndicatorType) {
      this.targetDisplay = targetDisplay2;
      this.indicatorType = targetIndicatorType;
   }

   public boolean render(float x, float y) {
      long longValue = KEY ^ 109013573147188L;
      boolean flagSet = Animations.isFlagSet();
      if (!flagSet) {
         if (!this.indicatorType.predicate.test(this.targetDisplay.target)) {
            return false;
         }

         RenderUtil.fillRoundedRect(x, y, 9.0F, 9.0F, 2.0F, this.indicatorType.color);
         RenderUtil.strokeRoundedRect(x - 0.125F, y - 0.125F, 9.25F, 9.25F, 2.0F, 0.5F, SHADOW_COLOR);
      }

      float floatValue = 2.0F;
      RenderUtil.drawImage(x + 2.0F, y + 2.0F, 5.0F, 5.0F, Color.WHITE, this.indicatorType.image);
      return true;
   }

   static {
      try {
      long var1 = KEY ^ 80077223269624L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/NoPadding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long[] var0 = new long[3];
      int var6 = 0;
      String var7 = "\u00ab\u0098[\u00f3/\u00adgh\u00a3 a+\u00ec9\u00d0\u009a\u00c2\u00d4\u001a\u00acKw\u00ca\u00ac";
      int var8 = "\u00ab\u0098[\u00f3/\u00adgh\u00a3 a+\u00ec9\u00d0\u009a\u00c2\u00d4\u001a\u00acKw\u00ca\u00ac".length();
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

      I = (int)var0[2];
      j = (int)var0[0];
      Z = (int)var0[1];
      SHADOW_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.25);
      } catch (Exception var16) {
         throw new RuntimeException(var16);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
