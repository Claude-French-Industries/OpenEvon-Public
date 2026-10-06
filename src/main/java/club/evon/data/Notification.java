package club.evon.data;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.manager.NotificationManager;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;
import club.evon.util.Stopwatch;
import club.evon.util.render.ColorUtil;




public final class Notification {
   private final String title;
   private final String subtitle;
   private final NotificationType type;
   private final long duration;
   private static final int Y;
   private static final int x;
   private static final int w;
   private static final int F;
   private static final float q = 1.5F;
   private static final float a = 3.0F;
   private static final float y = 10.5F;
   private static final Color W;
   private static final Color O;
   private static final Color u;
   private final Stopwatch animationTimer;
   private static final long b = KeyedRandom.create(-1577199037375129448L, -9078439179547574758L, MethodHandles.lookup().lookupClass()).next(180543178568271L);

   public Notification(NotificationType notificationType, long longValue, String text, String text2) {
      super();
      long longValue2 = b ^ 139442579730692L;
      this.animationTimer = new Stopwatch();
      NotificationManager.getStateArray();
      this.type = notificationType;
      this.duration = longValue;
      this.title = text;
      this.subtitle = text2;
      if (BaseModule.getStateArray() == null) {
         NotificationManager.setStateArray(new BaseModule[5]);
      }
   }

   static {
      try {
      long var1 = b ^ 18599511615437L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/NoPadding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long[] var0 = new long[4];
      int var6 = 0;
      String var7 = "h\u009c\u000fT\u0081B\u00b3\u0001\u00df\u00b7\u00abHS\u00b9_]";
      int var8 = "h\u009c\u000fT\u0081B\u00b3\u0001\u00df\u00b7\u00abHS\u00b9_]".length();
      int var5 = 0;

      label23:
      while (true) {
         int var10001 = var5;
         var5 += 8;
         byte[] var9 = var7.substring(var10001, var5).getBytes("ISO-8859-1");
         long[] var14 = var0;
         var10001 = var6++;
         long var17 = (var9[0] & 255L) << 56
            | (var9[1] & 255L) << 48
            | (var9[2] & 255L) << 40
            | (var9[3] & 255L) << 32
            | (var9[4] & 255L) << 24
            | (var9[5] & 255L) << 16
            | (var9[6] & 255L) << 8
            | var9[7] & 255L;
         byte var19 = -1;

         while (true) {
            long var10 = var17;
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
            long var21 = (var12[0] & 255L) << 56
               | (var12[1] & 255L) << 48
               | (var12[2] & 255L) << 40
               | (var12[3] & 255L) << 32
               | (var12[4] & 255L) << 24
               | (var12[5] & 255L) << 16
               | (var12[6] & 255L) << 8
               | var12[7] & 255L;
            switch (var19) {
               case 0:
                  var14[var10001] = var21;
                  if (var5 >= var8) {
                     x = (int)var0[1];
                     F = (int)var0[0];
                     Y = (int)var0[2];
                     w = (int)var0[3];
                     W = new Color(68, 68, 68, 45);
                     O = ColorUtil.withAlpha(Color.WHITE, 0.15F);
                     u = ColorUtil.withAlpha(Color.BLACK, 0.45F);
                     break label23;
                  }
                  break;
               default:
                  var14[var10001] = var21;
                  if (var5 < var8) {
                     continue label23;
                  }

                  var7 = "I\u0094\b0\u00f0\u00e5}\u00f5!\u00f9\u008e\u00fa\u0080k\u000er";
                  var8 = "I\u0094\b0\u00f0\u00e5}\u00f5!\u00f9\u008e\u00fa\u0080k\u000er".length();
                  var5 = 0;
            }

            int var16 = var5;
            var5 += 8;
            var9 = var7.substring(var16, var5).getBytes("ISO-8859-1");
            var14 = var0;
            var10001 = var6++;
            var17 = (var9[0] & 255L) << 56
               | (var9[1] & 255L) << 48
               | (var9[2] & 255L) << 40
               | (var9[3] & 255L) << 32
               | (var9[4] & 255L) << 24
               | (var9[5] & 255L) << 16
               | (var9[6] & 255L) << 8
               | var9[7] & 255L;
            var19 = 0;
         }
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
