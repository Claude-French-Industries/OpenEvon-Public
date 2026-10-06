package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.util.Window;
import net.minecraft.util.math.MathHelper;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;
import club.evon.util.math.RandomUtil;
import club.evon.util.render.NvgImage;
import club.evon.util.render.RenderUtil;




public final class ScreenManager {
   private static AbstractScreen currentScreen;
   public static final NvgImage BACKGROUND_IMAGE;
   public static final NvgImage PANEL_IMAGE;
   private static final Color OVERLAY_COLOR;
   private static float smoothedMouseX;
   private static float smoothedMouseY;
   private static final long KEY = KeyedRandom.create(8516826488656699261L, -2530783455928732433L, MethodHandles.lookup().lookupClass()).next(205115474671419L);

   public static void render(float mouseX, float mouseY) {
      renderBackground(mouseX, mouseY);
      Window window = ClientContext.MINECRAFT_CLIENT.getWindow();
      currentScreen.render(mouseX, mouseY, window.getScaledWidth(), window.getScaledHeight());
   }

   public static void renderBackground(double mouseX, double mouseY) {
      long longValue = KEY ^ 102101659381298L;
      Window window = ClientContext.MINECRAFT_CLIENT.getWindow();
      float floatValue = window.getScaledWidth();
      BaseModule[] values = MenuButton.getStateArray();
      float floatValue2 = window.getScaledHeight();
      byte byteValue = 100;
      byte byteValue2 = 56;
      mouseX = MathHelper.clamp(mouseX, 0.0, floatValue);
      mouseY = MathHelper.clamp(mouseY, 0.0, floatValue2);
      BaseModule[] values2 = values;
      float floatValue3 = 0.3F;
      smoothedMouseX = RandomUtil.lerp(smoothedMouseX, (float)mouseX, 0.3F);
      smoothedMouseY = RandomUtil.lerp(smoothedMouseY, (float)mouseY, 0.3F);
      int value = (int)((smoothedMouseX - floatValue / 2.0F) / floatValue * 100.0F) * 2;
      int value2 = (int)((smoothedMouseY - floatValue2 / 2.0F) / floatValue2 * 56.0F) * 2;
      float floatValue4 = floatValue + 200.0F;
      float floatValue5 = floatValue2 + 112.0F;
      RenderUtil.saveState();
      RenderUtil.drawImage(-100 - value, -56 - value2, floatValue4, floatValue5, null, BACKGROUND_IMAGE);
      RenderUtil.restoreState();
      RenderUtil.fillRect(0.0F, 0.0F, floatValue, floatValue2, OVERLAY_COLOR);
      if (values2 == null) {
         BaseModule.setStateArray(new BaseModule[2]);
      }
   }

   public static void drawPanel(float x, float y, float width, float height, float radius) {
      Window window = ClientContext.MINECRAFT_CLIENT.getWindow();
      float floatValue = window.getScaledWidth();
      float floatValue2 = window.getScaledHeight();
      byte byteValue = 100;
      byte byteValue2 = 56;
      int value = (int)((smoothedMouseX - floatValue / 2.0F) / floatValue * 100.0F) * 2;
      int value2 = (int)((smoothedMouseY - floatValue2 / 2.0F) / floatValue2 * 56.0F) * 2;
      RenderUtil.drawRoundedImageRegion(x, y, width, height, radius, -100 - value, -56 - value2, floatValue + 200.0F, floatValue2 + 112.0F, null, PANEL_IMAGE);
   }

   public static void onMouseClick(double mouseX, double mouseY, int mouseButton) {
      currentScreen.mouseClicked(mouseX, mouseY, mouseButton);
   }

   public static void onKeyPress(int keyCode) {
      long longValue = KEY ^ 86531155842086L;
      BaseModule[] values = MenuButton.getStateArray();
      AbstractScreen tmp = currentScreen;
      if (values != null) {
         if (currentScreen == null) {
            return;
         }

         tmp = currentScreen;
      }

      tmp.keyPressed(keyCode);
   }

   public static AbstractScreen getCurrentScreen() {
      return currentScreen;
   }

   public static void setCurrentScreen(AbstractScreen screen) {
      currentScreen = screen;
   }

   static {
      try {
      long var1 = KEY ^ 98318034697171L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var0 = new String[2];
      int var8 = 0;
      String var7 = "\u0000\u007f\u0083\u0015c&\\2\u00a3\u001d\u00ca\u00e1w\u00f6\u00a9\u0088\u0018\u00fdf\"\u0092\u0092\u008e.!\u00a1\u0004\u00e8D)\u00ae\u0096n\u0015\u0081\u00df\u000b\u00b0\u0085@\u008e";
      int var9 = "\u0000\u007f\u0083\u0015c&\\2\u00a3\u001d\u00ca\u00e1w\u00f6\u00a9\u0088\u0018\u00fdf\"\u0092\u0092\u008e.!\u00a1\u0004\u00e8D)\u00ae\u0096n\u0015\u0081\u00df\u000b\u00b0\u0085@\u008e"
         .length();
      char var6 = 16;
      int var5 = -1;

      label27: while (true) {
         byte[] var10 = var3.doFinal(var7.substring(++var5, var5 + var6).getBytes("ISO-8859-1"));
         String var13 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var0[var8++] = var13;
         if ((var5 += var6) >= var9) {
            BACKGROUND_IMAGE = new NvgImage(var0[0]);
            PANEL_IMAGE = new NvgImage(var0[1]);
            OVERLAY_COLOR = new Color(11, 5, 18, 35);
            break label27;
         }

         var6 = var7.charAt(var5);
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
