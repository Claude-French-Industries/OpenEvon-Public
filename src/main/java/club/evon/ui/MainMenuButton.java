package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgFont;
import club.evon.util.render.NvgImage;
import club.evon.util.render.RenderUtil;




public final class MainMenuButton extends MenuButton {
   private final MainMenuEntry entry;
   private final Object display;
   private static final Color LABEL_COLOR;
   private static final int T;
   private static final long KEY = KeyedRandom.create(4768731035077664933L, -7448957784393494081L, MethodHandles.lookup().lookupClass()).next(222183740185078L);
   private static final String FONT_NAME;

   public MainMenuButton(MainMenuEntry type) {
      super(type == MainMenuEntry.ACCOUNTS ? 141.0F : (type == MainMenuEntry.EXIT ? 42.0F : 187.5F), 28.0F, 10.0F);

      long longValue = KEY ^ 7478868227078L;
      boolean state = MainMenuEntry.getState();
      this.entry = type;
      this.display = type.display;
      if (BaseModule.getStateArray() == null) {
         MainMenuEntry.setState(!state);
      }
      }

   public void render(float x, float y, double mouseX, double mouseY) {
      boolean flag;
      label46: {
         long longValue = KEY ^ 120439446711042L;
         boolean state = MainMenuEntry.getState();
         ScreenManager.drawPanel(x, y, this.getWidth(), this.getHeight(), this.getRadius());
         flag = state;
         MainMenuButton mainMenuButton = this;
         if (!flag) {
            if (this.entry != MainMenuEntry.EXIT) {
               super.renderDefault(x, y, mouseX, mouseY);
               if (!flag) {
                  break label46;
               }

               BaseModule.setStateArray(new BaseModule[1]);
            }

            mainMenuButton = this;
         }

         mainMenuButton.render(x, y, mouseX, mouseY, new Color(255, 44, 44, 10));
      }

      Object object;
      label49: {
         Object object2 = this.display;
         boolean flag2 = object2 instanceof String;
         if (!flag) {
            if (flag2) {
               String text = (String)object2;
               NvgFont font = EvonClient.getInstance().getFontManager().getFont(FONT_NAME);
               font.drawText(text, x + (this.getWidth() - font.getTextWidth(text, 12.5F)) / 2.0F, y + (this.getHeight() - font.getTextHeight(text, 12.5F)) / 2.0F + 1.0F, 12.5F, LABEL_COLOR.getRGB());
               if (!flag) {
                  return;
               }
            }

            object2 = this.display;
            object = object2;
            if (flag) {
               break label49;
            }

            flag2 = object2 instanceof NvgImage;
         }

         if (!flag2) {
            return;
         }

         object = object2;
      }

      NvgImage nvgImage = (NvgImage)object;
      RenderUtil.drawImage((int)(x + (this.getWidth() - 14.0F) / 2.0F) + 2, (int)(y + (this.getHeight() - 14.0F) / 2.0F) + 0.5F, 14.0F, 14.0F, null, nvgImage);
   }

   @Override
   public void onClick() {
      label41: {
         boolean flag;
         label40: {
            label39: {
               label38: {
                  long longValue = KEY ^ 98013860734601L;
                  flag = MainMenuEntry.getState();
                  if (!flag) {
                     switch (this.entry) {
                        case SINGLEPLAYER:
                           ClientContext.MINECRAFT_CLIENT.setScreen(new SelectWorldScreen(ClientContext.MINECRAFT_CLIENT.currentScreen));
                           break;
                        case MULTIPLAYER:
                           break label38;
                        case OPTIONS:
                           break label39;
                        case ACCOUNTS:
                           break label40;
                        case EXIT:
                           break label41;
                        default:
                           return;
                     }
                  }

                  if (!flag) {
                     return;
                  }
               }

               ClientContext.MINECRAFT_CLIENT.setScreen(new MultiplayerScreen(ClientContext.MINECRAFT_CLIENT.currentScreen));
               if (!flag) {
                  return;
               }
            }

            ClientContext.MINECRAFT_CLIENT.setScreen(new OptionsScreen(ClientContext.MINECRAFT_CLIENT.currentScreen, ClientContext.MINECRAFT_CLIENT.options));
            if (!flag) {
               return;
            }
         }

         ScreenManager.setCurrentScreen(AltManagerScreen.getInstance());
         if (!flag) {
            return;
         }
      }

      ClientContext.MINECRAFT_CLIENT.scheduleStop();
   }

   static {
      try {
      long var7 = KEY ^ 121382745387998L;
      Cipher var9;
      Cipher var10000 = var9 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var7 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var10 = 1; var10 < 8; var10++) {
         var10003[var10] = (byte)(var7 << var10 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var11 = var9.doFinal("\u00db\u00ae0\u00a9\u00a4\u00f7\u009dC\u00c0\u00eda\u0006\u00d8\u001c\u00be\u0087".getBytes("ISO-8859-1"));
      String var14 = decodeUtf8(var11).intern();
      byte var10001 = -1;
      FONT_NAME = var14;
      Cipher var2;
      var10000 = var2 = Cipher.getInstance("DES/CBC/NoPadding");
      var10002 = SecretKeyFactory.getInstance("DES");
      var10003 = new byte[]{(byte)(var7 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var7 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long var4 = -2601572365135034715L;
      byte[] var6 = var2.doFinal(
         new byte[]{
            (byte)(var4 >>> 56),
            (byte)(var4 >>> 48),
            (byte)(var4 >>> 40),
            (byte)(var4 >>> 32),
            (byte)(var4 >>> 24),
            (byte)(var4 >>> 16),
            (byte)(var4 >>> 8),
            (byte)var4
         }
      );
      long var16 = (var6[0] & 255L) << 56
         | (var6[1] & 255L) << 48
         | (var6[2] & 255L) << 40
         | (var6[3] & 255L) << 32
         | (var6[4] & 255L) << 24
         | (var6[5] & 255L) << 16
         | (var6[6] & 255L) << 8
         | var6[7] & 255L;
      var10001 = -1;
      long var0 = var16;
      T = (int)var0;
      LABEL_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.8F);
      } catch (Throwable var1000) {
         throw rethrow(var1000);
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

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException rethrow(Throwable exception) throws T {
      throw (T)exception;
   }
}
