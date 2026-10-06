package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.data.ColorPair;
import club.evon.module.impl.render.Animations;
import club.evon.module.impl.render.Overlay;
import club.evon.util.KeyedRandom;




public enum ColorTheme {

   EVON(Overlay.EVON),

   RED(Overlay.RED),

   RED_2(Overlay.RED_2),

   PURPLE(Overlay.PURPLE),

   PEARL(Overlay.PEARL),

   GREEN(Overlay.GREEN),

   BUBBLEGUM(Overlay.BUBBLEGUM),

   GRAY(Overlay.GRAY),

   GRAY_2(Overlay.GRAY_2),

   RAINBOW(null),

   OCEAN_PISS(Overlay.OCEAN_PISS);

   private final ColorPair colorPair;
   private static final ColorTheme[] VALUES;
   private static final long KEY = KeyedRandom.create(-1658826109049756853L, -2666627383377149396L, MethodHandles.lookup().lookupClass()).next(125643335192588L);

   public ColorPair getColorPair() {
      long longValue = KEY ^ 57555357898139L;
      boolean flagUnset = Animations.isFlagUnset();
      ColorTheme tmp = this;
      if (flagUnset) {
         if (this == RAINBOW) {
            double doubleValue = 0.1;
            float floatValue = 0.8F;
            float floatValue2 = 1.0F;
            Color rainbowColor = Overlay.getRainbowColor(System.currentTimeMillis() / 1000.0, 0.0, 0.1, 0.8F, 1.0F);
            float[] values = Color.RGBtoHSB(rainbowColor.getRed(), rainbowColor.getGreen(), rainbowColor.getBlue(), null);
            float floatValue3 = 0.083333336F;
            float floatValue4 = (values[0] + 0.083333336F) % 1.0F;
            Color hSBColor = Color.getHSBColor(floatValue4, values[1], values[2] * 0.7F);
            return new ColorPair(rainbowColor, hSBColor);
         }

         tmp = this;
      }

      return tmp.colorPair;
   }

   ColorTheme(ColorPair colorPair2) {
      this.colorPair = colorPair2;
   }

   private static ColorTheme[] buildValues() {
      return new ColorTheme[]{EVON, RED, RED_2, PURPLE, PEARL, GREEN, BUBBLEGUM, GRAY, GRAY_2, RAINBOW, OCEAN_PISS};
   }

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      try {
      long var1 = KEY ^ 87223586944531L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var0 = new String[11];
      int var8 = 0;
      String var7 = "\u00ae\u00bd\u0016y<\u00c8\u00adQ\b\u0081\u00f4\u0080=\u0012\u0080_`\b\u0000Y{c9\u0094|\u00c7\b\u00f1\u00f1\u0086,\u00adtY7\bK\u00df\u00f6#\u00a0M0L\b\u00b6\u001f\u0087\u008d \u000f\u00ebK\u0010\u0007\u00c2\u00bfnF\u008e\u00d8\u001c\u001c|e\u009a\u0004\u00ef \u00da\u0010\f\u00f3\u00fa\u009b\bF\u00a2`x\u00ec\u0097O\u00a8\u00c4\u00b8N\b^cA\u0018`\u0085\u0002\u00e2";
      int var9 = "\u00ae\u00bd\u0016y<\u00c8\u00adQ\b\u0081\u00f4\u0080=\u0012\u0080_`\b\u0000Y{c9\u0094|\u00c7\b\u00f1\u00f1\u0086,\u00adtY7\bK\u00df\u00f6#\u00a0M0L\b\u00b6\u001f\u0087\u008d \u000f\u00ebK\u0010\u0007\u00c2\u00bfnF\u008e\u00d8\u001c\u001c|e\u009a\u0004\u00ef \u00da\u0010\f\u00f3\u00fa\u009b\bF\u00a2`x\u00ec\u0097O\u00a8\u00c4\u00b8N\b^cA\u0018`\u0085\u0002\u00e2"
         .length();
      char var6 = '\b';
      int var12 = -1;

      label28:
      while (true) {
         String var13 = var7.substring(++var12, var12 + var6);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var3.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var0[var8++] = var19;
                  if ((var12 += var6) >= var9) {
                     
                     
                     
                     
                     
                     
                     
                     
                     
                     
                     
                     VALUES = buildValues();
                     break label28;
                  }

                  var6 = var7.charAt(var12);
                  break;
               default:
                  var0[var8++] = var19;
                  if ((var12 += var6) < var9) {
                     var6 = var7.charAt(var12);
                     continue label28;
                  }

                  var7 = "\u00d7\u00b5m\u00f5\u00d0@\u00afF\b\u0092\u001aq\u00bf\u00a2\u00b7\u00f5^";
                  var9 = "\u00d7\u00b5m\u00f5\u00d0@\u00afF\b\u0092\u001aq\u00bf\u00a2\u00b7\u00f5^".length();
                  var6 = '\b';
                  var12 = -1;
            }

            var13 = var7.substring(++var12, var12 + var6);
            var10001 = 0;
         }
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
