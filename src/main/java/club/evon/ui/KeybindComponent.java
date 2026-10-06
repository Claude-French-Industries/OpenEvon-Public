package club.evon.ui;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import org.lwjgl.glfw.GLFW;
import club.evon.setting.BindSetting;
import club.evon.util.KeyedRandom;




public final class KeybindComponent extends SettingComponent<BindSetting> {
   private boolean listening;
   private static final long KEY = KeyedRandom.create(6954181863998084365L, -3287740187992325566L, MethodHandles.lookup().lookupClass()).next(43826426609902L);
   private static final String[] STRINGS;

   public KeybindComponent(BindSetting property) {
      super(property);
   }

   public void render(int mouseX, int mouseY) {
      float floatValue;
      float floatValue2;
      float floatValue3;
      String text;
      label30: {
         long longValue = KEY ^ 1840632025517L;
         int value = ThemeDropdownComponent.getDefaultState();
         super.render(mouseX, mouseY);
         int value2 = value;
         floatValue = this.getX();
         floatValue2 = this.getY();
         floatValue3 = this.getWidth();
         BindSetting setting = this.getSetting();
         int value3 = setting.getValue();
         String[] strings = STRINGS;
         String text2 = strings[2];
          int value4 = this.listening ? 1 : 0;
         if (value2 != 0) {
            if (this.listening) {
               text = STRINGS[0];
               break label30;
            }

            value4 = value3;
         }

         byte byteValue = -1;
         if (value2 != 0) {
            if (value4 == -1) {
               text = STRINGS[1];
               break label30;
            }

            value4 = value3;
            byteValue = 0;
         }

         Optional<String> map = Optional.ofNullable(GLFW.glfwGetKeyName(value4, byteValue)).map(String::toUpperCase);
         strings = STRINGS;
         text = map.orElse(strings[1]);
      }

      String text3 = text;
      Theme.FONT.drawText(text3, floatValue + floatValue3 - (Theme.FONT.getTextWidth(text3, 7.5F) + 4.5F), floatValue2 + 3.5F, 7.5F, Theme.TEXT_COLOR.getRGB());
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
      KeybindComponent keybindComponent;
      label44: {
         long longValue = KEY ^ 13285113505774L;
         int value = ThemeDropdownComponent.getState();
         int value2 = button;
         if (value == 0) {
            if (button == 0) {
               boolean tmp = this.listening;
               if (value == 0) {
                  tmp = !this.listening;
               }

               this.listening = tmp;
               if (value == 0) {
                  return;
               }
            }

            keybindComponent = this;
            if (value != 0) {
               break label44;
            }

            value2 = this.listening ? 1 : 0;
         }

         if (value2 == 0) {
            return;
         }

         this.getSetting().setValue(-1);
         keybindComponent = this;
      }

      keybindComponent.listening = false;
   }

   @Override
   public void keyPressed(int keyCode) {
      long longValue = KEY ^ 54650634297765L;
      int value = ThemeDropdownComponent.getState();
      KeybindComponent tmp = this;
      if (value == 0) {
         if (!this.listening) {
            return;
         }

         this.getSetting().setValue(keyCode);
         tmp = this;
      }

      tmp.listening = false;
   }

   static {
      try {
      long var0 = KEY ^ 18373933894455L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[3];
      int var7 = 0;
      String var6 = "\u00e5E\u0090\u001d\u001ab\u0094\u00dd\b\u00a6`n\u00d3AD\u000f\u00b7\b\u00a6`n\u00d3AD\u000f\u00b7";
      int var8 = "\u00e5E\u0090\u001d\u001ab\u0094\u00dd\b\u00a6`n\u00d3AD\u000f\u00b7\b\u00a6`n\u00d3AD\u000f\u00b7".length();
      char var5 = '\b';
      int var4 = -1;

      label27:
      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var13 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var13;
         if ((var4 += var5) >= var8) {
            STRINGS = var9;
            break label27;
         }

         var5 = var6.charAt(var4);
      }
      } catch (Exception var10004) {
         throw rethrow(var10004);
      }
   }

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
