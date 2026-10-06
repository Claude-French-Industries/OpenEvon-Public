package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.EvonClient;
import club.evon.manager.FontManager;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgFont;
import club.evon.util.render.RenderUtil;




public class AltManagerPlaceholderScreen extends AbstractScreen {
   private static final Color BACKGROUND_COLOR;
   private static final Color TEXT_COLOR;
   private static final Color SUBTEXT_COLOR;
   private static AltManagerPlaceholderScreen instance;
   private static final long KEY = KeyedRandom.create(-1558762892976260235L, -2683906452557966699L, MethodHandles.lookup().lookupClass()).next(64631875313989L);
   private static final String[] STRINGS;

   public static synchronized AltManagerPlaceholderScreen getInstance() {
      long longValue = KEY ^ 9803122923626L;
      int value = ColorUtil.getBranchFlag();
      AltManagerPlaceholderScreen tmp = instance;
      if (value != 0) {
         if (instance == null) {
            instance = new AltManagerPlaceholderScreen();
         }

         tmp = instance;
      }

      return tmp;
   }

   private AltManagerPlaceholderScreen() {
      MainMenuEntry[] values = new MainMenuEntry[]{MainMenuEntry.SINGLEPLAYER, MainMenuEntry.MULTIPLAYER, MainMenuEntry.OPTIONS, MainMenuEntry.ACCOUNTS, MainMenuEntry.EXIT};
   }

   public void render(float mouseX, float mouseY, int windowWidth, int windowHeight) {
      long longValue = KEY ^ 133272165510066L;
      int value = ColorUtil.getFlowState();
      RenderUtil.fillRect(0.0F, 0.0F, windowWidth, windowHeight, BACKGROUND_COLOR);
      FontManager fontManager = EvonClient.getInstance().getFontManager();
      String[] strings = STRINGS;
      NvgFont font = fontManager.getFont(strings[5]);
      byte byteValue = 15;
      int value2 = value;
      String text = strings[10];
      font.drawText(strings[7], (windowWidth - font.getTextWidth(strings[7], 15.0F)) / 2.0F, 17.0F + (windowHeight - font.getTextHeight(strings[7], 15.0F)) / 2.0F, 15.0F, TEXT_COLOR.getRGB());
      String text2 = strings[1];
      font.drawText(strings[2], (windowWidth - font.getTextWidth(strings[2], 15.0F)) / 2.0F, (windowHeight - font.getTextHeight(strings[2], 15.0F)) / 2.0F, 15.0F, TEXT_COLOR.getRGB());
      String text3 = strings[8];
      font.drawText(strings[4], (windowWidth - font.getTextWidth(strings[4], 15.0F)) / 2.0F, -17.0F + (windowHeight - font.getTextHeight(strings[4], 15.0F)) / 2.0F, 15.0F, TEXT_COLOR.getRGB());
      byte byteValue2 = 13;
      String text4 = strings[0];
      font.drawText(strings[6], (windowWidth - font.getTextWidth(strings[6], 13.0F)) / 2.0F, 100.0F + (windowHeight - font.getTextHeight(strings[6], 13.0F)) / 2.0F, 13.0F, SUBTEXT_COLOR.getRGB());
      String text5 = strings[9];
      font.drawText(strings[3], (windowWidth - font.getTextWidth(strings[3], 13.0F)) / 2.0F, 115.0F + (windowHeight - font.getTextHeight(strings[3], 13.0F)) / 2.0F, 13.0F, SUBTEXT_COLOR.getRGB());
      if (value2 != 0) {
         BaseModule.setStateArray(new BaseModule[5]);
      }
   }

   @Override
   public void keyPressed(int keyCode) {
      long longValue = KEY ^ 137548849334060L;
      if (keyCode == 256) {
         ScreenManager.setCurrentScreen(MainMenuScreen.getInstance());
      }
   }

   static {
      try {
      long var0 = KEY ^ 126049890022559L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[11];
      int var7 = 0;
      String var6 = "r-\u00ec\u0013\u00afQc\u00e9\u008dE\u00d1\u00a7\u00ef \u00ef\u001e~\u00d8\u00d2H7',\u0095\u00f2gpH\u00e22\u00d3\u00be\u00a8\u00f3\u00bc\u0011\u00de\u0011S\u00b4\u0018!\u0097\u0091\u00bfC\u00f6\u00d5n*\u00173s\u00b46\u00fb\u00d8\u0087\u00d8\u00c99\u0083\u00be\u007f0\u0018!\u0097\u0091\u00bfC\u00f6\u00d5n*\u00173s\u00b46\u00fb\u00d8\u0087\u00d8\u00c99\u0083\u00be\u007f0(<\u00bf\u00bbv~\u0000(?\u00e0\u00e4\u0095\u00c1\u00a1\u0005\u00b0D\u001d\u00faC\u00ee\u00b7[\u00e1\u001eGt\u000e1\u00d3\u00d4\u00f3\u0004\u00cc\u00adM\u0010\u0005{\u00fb\u00dc *\u00feiW\u00de)G6\u009d\u000eK9S\u00ac\u00b4\u00df\u00cf\b\u00a0\u00df\u00ab&n\u00f4S9\u00d9\u00e2,\u001b\u00deH\u0010{\u00b0|&\u00fe\u009d9yHg\u0080\"\u0082$\u00f1k(r-\u00ec\u0013\u00afQc\u00e9\u008dE\u00d1\u00a7\u00ef \u00ef\u001e~\u00d8\u00d2H7',\u0095\u00f2gpH\u00e22\u00d3\u00be\u00a8\u00f3\u00bc\u0011\u00de\u0011S\u00b4 \u00fc\u00f9?\u00d6s\u007f\u00038\u00fbh{\u00af>\u00f1\u000f\u00b0\u00cb]\u008e~\u0087\u0002d\u0004\u0081\u00a0W\u001a\u00bf\u0098\u00ec\u00df *\u00feiW\u00de)G6\u009d\u000eK9S\u00ac\u00b4\u00df\u00cf\b\u00a0\u00df\u00ab&n\u00f4S9\u00d9\u00e2,\u001b\u00deH";
      int var8 = "r-\u00ec\u0013\u00afQc\u00e9\u008dE\u00d1\u00a7\u00ef \u00ef\u001e~\u00d8\u00d2H7',\u0095\u00f2gpH\u00e22\u00d3\u00be\u00a8\u00f3\u00bc\u0011\u00de\u0011S\u00b4\u0018!\u0097\u0091\u00bfC\u00f6\u00d5n*\u00173s\u00b46\u00fb\u00d8\u0087\u00d8\u00c99\u0083\u00be\u007f0\u0018!\u0097\u0091\u00bfC\u00f6\u00d5n*\u00173s\u00b46\u00fb\u00d8\u0087\u00d8\u00c99\u0083\u00be\u007f0(<\u00bf\u00bbv~\u0000(?\u00e0\u00e4\u0095\u00c1\u00a1\u0005\u00b0D\u001d\u00faC\u00ee\u00b7[\u00e1\u001eGt\u000e1\u00d3\u00d4\u00f3\u0004\u00cc\u00adM\u0010\u0005{\u00fb\u00dc *\u00feiW\u00de)G6\u009d\u000eK9S\u00ac\u00b4\u00df\u00cf\b\u00a0\u00df\u00ab&n\u00f4S9\u00d9\u00e2,\u001b\u00deH\u0010{\u00b0|&\u00fe\u009d9yHg\u0080\"\u0082$\u00f1k(r-\u00ec\u0013\u00afQc\u00e9\u008dE\u00d1\u00a7\u00ef \u00ef\u001e~\u00d8\u00d2H7',\u0095\u00f2gpH\u00e22\u00d3\u00be\u00a8\u00f3\u00bc\u0011\u00de\u0011S\u00b4 \u00fc\u00f9?\u00d6s\u007f\u00038\u00fbh{\u00af>\u00f1\u000f\u00b0\u00cb]\u008e~\u0087\u0002d\u0004\u0081\u00a0W\u001a\u00bf\u0098\u00ec\u00df *\u00feiW\u00de)G6\u009d\u000eK9S\u00ac\u00b4\u00df\u00cf\b\u00a0\u00df\u00ab&n\u00f4S9\u00d9\u00e2,\u001b\u00deH"
         .length();
      char var5 = '(';
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var19;
                  if ((var12 += var5) >= var8) {
                     STRINGS = var9;
                     BACKGROUND_COLOR = new Color(11, 5, 18, 35);
                     TEXT_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.5);
                     SUBTEXT_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.4F);
                     break label27;
                  }

                  var5 = var6.charAt(var12);
                  break;
               default:
                  var9[var7++] = var19;
                  if ((var12 += var5) < var8) {
                     var5 = var6.charAt(var12);
                     continue label27;
                  }

                  var6 = "<\u00bf\u00bbv~\u0000(?\u00e0\u00e4\u0095\u00c1\u00a1\u0005\u00b0D\u001d\u00faC\u00ee\u00b7[\u00e1\u001eGt\u000e1\u00d3\u00d4\u00f3\u0004\u00cc\u00adM\u0010\u0005{\u00fb\u00dc \u00fc\u00f9?\u00d6s\u007f\u00038\u00fbh{\u00af>\u00f1\u000f\u00b0\u00cb]\u008e~\u0087\u0002d\u0004\u0081\u00a0W\u001a\u00bf\u0098\u00ec\u00df";
                  var8 = "<\u00bf\u00bbv~\u0000(?\u00e0\u00e4\u0095\u00c1\u00a1\u0005\u00b0D\u001d\u00faC\u00ee\u00b7[\u00e1\u001eGt\u000e1\u00d3\u00d4\u00f3\u0004\u00cc\u00adM\u0010\u0005{\u00fb\u00dc \u00fc\u00f9?\u00d6s\u007f\u00038\u00fbh{\u00af>\u00f1\u000f\u00b0\u00cb]\u008e~\u0087\u0002d\u0004\u0081\u00a0W\u001a\u00bf\u0098\u00ec\u00df"
                     .length();
                  var5 = '(';
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }

   private static String decodeUtf8(byte[] data) {
      int index = 0;
      int value;
      char[] chars = new char[value = data.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & data[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = data[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
