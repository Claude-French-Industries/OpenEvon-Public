package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import org.lwjgl.glfw.GLFW;
import club.evon.ClientContext;
import club.evon.setting.StringSetting;
import club.evon.util.Animator;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgImage;
import club.evon.util.render.RenderUtil;




public final class StringSettingElement extends SettingComponent<StringSetting> {
   private static final float SPACING = 1.5F;
   private static final float FONT_SIZE = 5.5F;
   private static final float LABEL_Y_OFFSET = 3.5F;
   private static final NvgImage ARROW_IMAGE;
   private final Animator expandAnimator = new Animator(200, 0.0, Easing.LINEAR);
   private boolean expanded;
   private final StringBuilder inputBuffer = new StringBuilder();
   private boolean typing;
   private static final long KEY = KeyedRandom.create(3548588441545066944L, -7053041461291586449L, MethodHandles.lookup().lookupClass()).next(97314606365005L);
   private static final String[] STRINGS;

   public StringSettingElement(StringSetting property) {
      super(property);
   }

   @Override
   public void init() {
      this.inputBuffer.setLength(0);
      this.inputBuffer.append(this.getSetting().getValue());
      this.typing = false;
   }

   public void render(int mouseX, int mouseY) {
      long longValue = KEY ^ 107683537994393L;
      super.render(mouseX, mouseY);
      int value = ThemeDropdownComponent.getDefaultState();
      float floatValue = this.getX();
      float floatValue2 = this.getY();
      int value2 = value;
      float floatValue3 = this.getWidth();
      StringSetting setting = this.getSetting();
      this.expandAnimator.setTargetValue(this.expanded ? 1.0 : 0.0);
      String value3 = setting.getValue();
      byte byteValue = 10;
      value = value3.length();
      if (value2 != 0) {
         value = value >= 10 ? 1 : 0;
      }

      int value4 = value;
      String text = value4 == 0 ? value3 : value3.substring(0, 8) + STRINGS[1];
      float floatValue4 = 5.5F;
      Theme.FONT.drawText(text, floatValue + floatValue3 - (Theme.FONT.getTextWidth(text, 5.5F) + 3.0F + 9.0F), floatValue2 + 3.5F + 1.0F, 5.5F, Theme.TEXT_COLOR.getRGB());
      float floatValue5 = super.getHeight();
      float floatValue6 = floatValue2 + floatValue5;
      float floatValue7 = this.expandAnimator.getValue().floatValue();
      float floatValue8 = floatValue + floatValue3 - 11.0F;
      float floatValue9 = floatValue2 + 4.5F + 0.5F;
      RenderUtil.saveState();
      RenderUtil.translate(floatValue8 + 2.75F, floatValue9 + 1.75F);
      RenderUtil.rotate(3.125F * floatValue7);
      RenderUtil.translate(-2.75F, -1.75F);
      RenderUtil.drawImage(0.0F, 0.0F, 5.5F, 3.5F, null, ARROW_IMAGE);
      RenderUtil.restoreState();
      float floatValue10 = floatValue7;
      float floatValue11 = 0.0F;
      if (value2 != 0) {
         if (!(floatValue7 > 0.0F)) {
            return;
         }

         RenderUtil.pushClip(floatValue, floatValue2, floatValue3, this.getHeight());
         floatValue10 = floatValue + 4.5F;
         floatValue11 = floatValue6;
      }

      RenderUtil.fillRoundedRect(floatValue10, floatValue11, floatValue3 - 9.0F, 13.0F, 3.0F, ColorUtil.withAlpha(Color.WHITE, !this.typing ? 0.1F : 0.15F));
      float floatValue12 = floatValue + 4.5F + 3.0F;
      float floatValue13 = floatValue3 - 9.0F - 6.0F;
      RenderUtil.pushClip(floatValue12, floatValue6, floatValue13, this.getHeight() - floatValue5 - 4.5F);
      float floatValue14 = Theme.FONT.getTextWidth(value3, 7.0F);
      if (value2 != 0) {
         if (floatValue14 > floatValue13) {
            floatValue12 -= floatValue14 - floatValue13;
         }

         Theme.FONT.drawText(value3, floatValue12, floatValue6 + 3.0F, 7.0F, -1);
         RenderUtil.popClip();
      }

      RenderUtil.popClip();
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int button) {
      int value;
      int value2;
      long longValue = KEY ^ 119063594972378L;
      value = ThemeDropdownComponent.getDefaultState();
      value2 = button;
      label64:
      if (value != 0) {
         if (button <= 1) {
            value2 = HoverUtil.isHovered(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), super.getHeight()) ? 1 : 0;
            if (value == 0) {
               break label64;
            }

            if (value2 != 0) {
               boolean tmp = this.expanded;
               if (value != 0) {
                  tmp = !this.expanded;
               }

               this.expanded = tmp;
               this.typing = false;
               if (value != 0) {
                  return;
               }
            }
         }

         value2 = button;
      }

      if (value != 0) {
         if (value2 != 0) {
            return;
         }

         value2 = HoverUtil.isHovered(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), super.getHeight()) ? 1 : 0;
      }

      if (value2 == 0) {
         boolean flag = this.typing;
         if (value != 0) {
            flag = !this.typing;
         }

         this.typing = flag;
      }
   }

   @Override
   public void keyPressed(int keyCode) {
      long longValue = KEY ^ 90060608565905L;
      int value = ThemeDropdownComponent.getDefaultState();
      int value2 = keyCode;
      String text;
      if (value != 0) {
         if (keyCode == 257) {
            this.typing = false;
            return;
         }

         value2 = this.typing ? 1 : 0;
      }

      if (value != 0) {
         if (value2 == 0) {
            return;
         }

         value2 = keyCode;
      }

      short shortValue = 259;
      if (value != 0) {
         if (value2 == 259) {
            if (value != 0) {
               if (this.inputBuffer.isEmpty()) {
                  return;
               }

               this.inputBuffer.deleteCharAt(this.inputBuffer.length() - 1);
            }

            this.getSetting().setValue(this.inputBuffer.toString());
            return;
         }

         value2 = keyCode;
         shortValue = 0;
      }

      String glfwGetKeyName = GLFW.glfwGetKeyName(value2, shortValue);
      if (keyCode == 32) {
         glfwGetKeyName = " ";
      }

      if (glfwGetKeyName != null) {
         label93: {
            value2 = GLFW.glfwGetKey(ClientContext.MINECRAFT_CLIENT.getWindow().getHandle(), 340);
            if (value != 0) {
               if (value2 != 1) {
                  break label93;
               }

               value2 = Character.isLetter(glfwGetKeyName.toCharArray()[0]) ? 1 : 0;
            }

            label78: {
               if (value != 0) {
                  if (value2 != 0) {
                     text = glfwGetKeyName.toUpperCase();
                     break label78;
                  }

                  value2 = keyCode;
               }

               switch (value2) {
                  case 45:
                     text = "_";
                     break;
                  case 61:
                     text = "+";
                     break;
                  default:
                     text = glfwGetKeyName;
               }
            }

            glfwGetKeyName = text;
         }

         this.inputBuffer.append(glfwGetKeyName);
         this.getSetting().setValue(this.inputBuffer.toString());
      }
   }

   @Override
   public float getHeight() {
      float floatValue = 17.5F;
      return super.getHeight() + floatValue * this.expandAnimator.getValue().floatValue();
   }

   static {
      try {
      long var0 = KEY ^ 11108912433791L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[2];
      int var7 = 0;
      String var6 = "\u00aa\u00cc\u008c#\u0095 \u0084\u009c\u00a7\u00ba\\}\r_\t#\b\u00ca\u00a4\u008cp\u00f8B\u0095{";
      int var8 = "\u00aa\u00cc\u008c#\u0095 \u0084\u009c\u00a7\u00ba\\}\r_\t#\b\u00ca\u00a4\u008cp\u00f8B\u0095{".length();
      char var5 = 16;
      int var4 = -1;

      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var13 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var13;
         if ((var4 += var5) >= var8) {
            STRINGS = var9;
            ARROW_IMAGE = new NvgImage(STRINGS[0]);
            break;
         }

         var5 = var6.charAt(var4);
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
