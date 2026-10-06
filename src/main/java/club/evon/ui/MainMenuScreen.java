package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.EvonClient;
import club.evon.launch.LaunchProperties;
import club.evon.manager.FontManager;
import club.evon.module.BaseModule;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgFont;




public final class MainMenuScreen extends AbstractScreen {
   private final List<MainMenuButton> buttons;
   private static final float BUTTON_SPACING = 4.5F;
   private static final float SMALL_FONT_SIZE = 7.5F;
   private static final float FOOTER_MARGIN = 6.5F;
   public static final float DEFAULT_BUTTON_WIDTH = 187.5F;
   public static final float BUTTON_HEIGHT = 28.0F;
   public static final float BUTTON_RADIUS = 10.0F;
   public static final float EXIT_BUTTON_WIDTH = 42.0F;
   public static final float ACCOUNTS_BUTTON_WIDTH = 141.0F;
   private static final Color TITLE_COLOR;
   private static final Color FOOTER_COLOR;
   private static MainMenuScreen instance;
   private static final long KEY = KeyedRandom.create(5665002457024314006L, -7631312971788281646L, MethodHandles.lookup().lookupClass()).next(130082878439421L);
   private static final String[] STRINGS;

   public static synchronized MainMenuScreen getInstance() {
      long longValue = KEY ^ 75331069826694L;
      int value = ColorUtil.getFlowState();
      MainMenuScreen tmp = instance;
      if (value == 0) {
         if (instance == null) {
            instance = new MainMenuScreen();
         }

         tmp = instance;
      }

      return tmp;
   }

   private MainMenuScreen() {
      super();

      long longValue = KEY ^ 41431140510257L;
      int value = ColorUtil.getFlowState();
      this.buttons = new ArrayList<>();
      MainMenuEntry[] values = new MainMenuEntry[]{MainMenuEntry.SINGLEPLAYER, MainMenuEntry.MULTIPLAYER, MainMenuEntry.OPTIONS, MainMenuEntry.ACCOUNTS, MainMenuEntry.EXIT};
      int value2 = value;

      for (MainMenuEntry mainMenuEntry : values) {
         this.buttons.add(new MainMenuButton(mainMenuEntry));
         if (value2 != 0) {
            break;
         }
      }
      }

   public void render(float mouseX, float mouseY, int windowWidth, int windowHeight) {
      long longValue = KEY ^ 79317400210804L;
      int value = ColorUtil.getFlowState();
      FontManager fontManager = EvonClient.getInstance().getFontManager();
      int value2 = value;
      String[] strings = STRINGS;
      String clientName = EvonClient.getClientName();
      String version = LaunchProperties.VERSION;
      NvgFont font = fontManager.getFont(strings[1]);
      String text = strings[2];
      String text2 = strings[0];
      float floatValue = font.getTextWidth(clientName, 44.0F);
      float floatValue2 = (windowWidth - (floatValue + font.getTextWidth(version, 15.0F))) / 2.0F;
      float floatValue3 = (windowHeight - ((this.buttons.size() - 1) * 32.5F - 4.5F + 50.0F)) / 2.0F;
      font.drawText(clientName, floatValue2, floatValue3 - 7.5F, 44.0F, TITLE_COLOR.getRGB());
      font.drawText(version, floatValue2 + floatValue + 2.0F, floatValue3 + 15.5F, 15.0F, TITLE_COLOR.getRGB());
      float floatValue4 = (windowWidth - 187.5F) / 2.0F;
      float floatValue5 = floatValue3 + 50.0F;

      for (MainMenuButton mainMenuButton : this.buttons) {
         mainMenuButton.render(floatValue4, floatValue5, mouseX, mouseY);
         float floatValue6 = mainMenuButton.getWidth();
         float floatValue7 = 187.5F;
         if (value2 == 0) {
            if (floatValue6 == 187.5F) {
               floatValue5 += mainMenuButton.getHeight() + 4.5F;
               if (value2 == 0) {
                  continue;
               }
            }

            floatValue6 = floatValue4;
            floatValue7 = mainMenuButton.getWidth() + 4.5F;
         }

         floatValue4 = floatValue6 + floatValue7;
         if (value2 != 0) {
            break;
         }
      }

      strings = STRINGS;
      NvgFont font2 = fontManager.getFont(strings[6]);
      float floatValue8 = font2.getTextHeight(strings[7], 7.5F);
      float floatValue9 = windowHeight - (5.5F + floatValue8);
      font2.drawText(strings[4], 6.5F, floatValue9, 7.5F, FOOTER_COLOR.getRGB());
      floatValue9 -= floatValue8 + 1.0F;
      font2.drawText(strings[8], 6.5F, floatValue9, 7.5F, FOOTER_COLOR.getRGB());
      if (BaseModule.getStateArray() == null) {
         ColorUtil.setFlowState(++value2);
      }
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
      long longValue = KEY ^ 38549335182822L;
      int value = ColorUtil.getBranchFlag();
      Iterator iterator = this.buttons.iterator();
      int value2 = value;

      while (iterator.hasNext()) {
         MainMenuButton mainMenuButton = (MainMenuButton)iterator.next();
         value = mouseButton;
         if (value2 != 0) {
            if (mouseButton != 0) {
               continue;
            }

            value = HoverUtil.isHovered(mouseX, mouseY, mainMenuButton.getX(), mainMenuButton.getY(), mainMenuButton.getWidth(), mainMenuButton.getHeight()) ? 1 : 0;
         }

         if (value != 0 || value2 == 0) {
            mainMenuButton.onClick();
            if (value2 == 0) {
               break;
            }
         }
      }
   }

   static {
      try {
      long var0 = KEY ^ 132425703217704L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[9];
      int var7 = 0;
      String var6 = "n\u0007\u008c\u000e7\u00f5\u00c1\u00de\u0010\u00e5/>0\u00f1\u009a\u00fcL\u00b7$?\u00ecpq!\r\bt\u00fa\u00d8Ltw!;\bn\u0007\u008c\u000e7\u00f5\u00c1\u00de \u00aa\u00e8\u00afI\u00e3h\u009dq\u001e,\u001aVB}\u00c9\u00a18\u00d8\u00f8\u00a1\u00ec\u00a0\u00d58\u0081\u0086\u001f\u00a7\u00e6\u00df\u00a8\u00b4\bt\u00fa\u00d8Ltw!;\u0010n\u00b3!\u00d1\u000f90\u00c7\u00b2)3\u00d7\u00cb\u00fa\u0017\u00f7";
      int var8 = "n\u0007\u008c\u000e7\u00f5\u00c1\u00de\u0010\u00e5/>0\u00f1\u009a\u00fcL\u00b7$?\u00ecpq!\r\bt\u00fa\u00d8Ltw!;\bn\u0007\u008c\u000e7\u00f5\u00c1\u00de \u00aa\u00e8\u00afI\u00e3h\u009dq\u001e,\u001aVB}\u00c9\u00a18\u00d8\u00f8\u00a1\u00ec\u00a0\u00d58\u0081\u0086\u001f\u00a7\u00e6\u00df\u00a8\u00b4\bt\u00fa\u00d8Ltw!;\u0010n\u00b3!\u00d1\u000f90\u00c7\u00b2)3\u00d7\u00cb\u00fa\u0017\u00f7"
         .length();
      char var5 = '\b';
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
                     TITLE_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.5);
                     FOOTER_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.45F);
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

                  var6 = "\u0001\t\u00a2h\u00a5\u00bf@\u0088\u0018\u00d3\u00fe\u00df\u00e9e\u00e0\u00bf\u00d60\u000eM$Ka\u0090\u0095\u00ec\u0006\u00a8\u00aa\u00c1h\u00e1\u0017";
                  var8 = "\u0001\t\u00a2h\u00a5\u00bf@\u0088\u0018\u00d3\u00fe\u00df\u00e9e\u00e0\u00bf\u00d60\u000eM$Ka\u0090\u0095\u00ec\u0006\u00a8\u00aa\u00c1h\u00e1\u0017"
                     .length();
                  var5 = '\b';
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var20) {
         throw new RuntimeException(var20);
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
