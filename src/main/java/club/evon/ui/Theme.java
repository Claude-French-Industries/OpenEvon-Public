package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import club.evon.manager.FontManager;
import club.evon.util.KeyedRandom;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgFont;




public final class Theme {
   public static final int N;
   public static final Color BACKGROUND;
   public static final Color TEXT_COLOR;
   public static final Color OVERLAY;
   public static final int I;
   public static final int C;
   public static final float o = 4.5F;
   public static final float i = 4.5F;
   public static final float A = 3.5F;
   public static final FontManager FONT_MANAGER;
   public static final NvgFont FONT;

   static {
      try {
      long var12 = KeyedRandom.create(1635261272000714949L, -9167018688101132574L, MethodHandles.lookup().lookupClass()).next(13232626504881L) ^ 40892137039685L;
      Cipher var14;
      Cipher var10000 = var14 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var12 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var15 = 1; var15 < 8; var15++) {
         var10003[var15] = (byte)(var12 << var15 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var16 = var14.doFinal("\u00f2\u00ec\u00ed%\u001c\u00cc\u00c3\u0083\u00cd\u00c5U\u001eq\u00aatC".getBytes("ISO-8859-1"));
      String var20 = decodeUtf8(var16).intern();
      int var10001 = -1;
      String var11 = var20;
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
      String var5 = "\u00b8N\u00ba\u00df\u0011\u00ba\u0003\u00ac\u0004\"\u00b1\u0094x\u00bd\u00ce\u00e6";
      int var6 = "\u00b8N\u00ba\u00df\u0011\u00ba\u0003\u00ac\u0004\"\u00b1\u0094x\u00bd\u00ce\u00e6".length();
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
         byte var23 = -1;
         var0[var10001] = var10004;
      } while (var3 < var6);

      C = (int)var0[0];
      N = (int)var0[1];
      I = (int)var0[0];
      BACKGROUND = new Color(15, 15, 15);
      TEXT_COLOR = new Color(215, 215, 215);
      OVERLAY = ColorUtil.withAlpha(Color.WHITE, 0.05F);
      FONT_MANAGER = new FontManager();
      FONT = FONT_MANAGER.getFont(var11);
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
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
