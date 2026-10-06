package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.session.Session;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.api.AccountType;
import club.evon.api.LoginType;
import club.evon.api.MicrosoftLoginManager;
import club.evon.data.AccountData;
import club.evon.mixins.MinecraftClientAccessor;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;
import club.evon.util.render.NvgFont;
import club.evon.util.render.RenderUtil;




public final class LoginMethodButton extends MenuButton {
   private final AccountType accountType;
   private final String label;
   private static final NvgFont FONT;
   private static final long KEY = KeyedRandom.create(-4774314177763851829L, -4456769795295851523L, MethodHandles.lookup().lookupClass()).next(169018434702714L);
   private static final String[] STRINGS;

   public LoginMethodButton(AccountType type, float width, float height, float radius) {
      super(width, height, radius);
      this.accountType = type;
      this.label = type.displayName + STRINGS[1];
   }

   public void render(float x, float y, double mouseX, double mouseY) {
      Color color;
      label32: {
         long longValue = KEY ^ 122498384106248L;
         String text = AccountType.v();
         AccountType tmp = this.accountType;
         AccountType tmp2 = AccountType.COOKIE;
         if (text == null) {
            if (this.accountType == AccountType.COOKIE) {
               color = new Color(250, 119, 5);
               if (text == null) {
                  break label32;
               }
            }

            tmp = this.accountType;
            tmp2 = AccountType.MICROSOFT;
         }

         if (tmp == tmp2) {
            color = new Color(173, 249, 138);
            if (text == null) {
               break label32;
            }
         }

         color = new Color(71, 116, 231);
      }

      super.render(x, y, mouseX, mouseY, color);
      float floatValue = this.getWidth();
      float floatValue2 = this.getHeight();
      FONT.drawText(this.label, x + (floatValue - FONT.getTextWidth(this.label, 11.0F)) / 2.0F, y + (floatValue2 - FONT.getTextHeight(this.label, 11.0F)) / 2.0F, 11.0F, Color.WHITE.getRGB());
      float floatValue3 = 17.5F;
      float floatValue4 = (floatValue2 - 17.5F) / 2.0F;
      RenderUtil.drawImage(x + floatValue - (17.5F + floatValue4), y + floatValue4, 17.5F, 17.5F, null, this.accountType.icon);
      if (BaseModule.getStateArray() == null) {
         AccountType.l("eL2elc");
      }
   }

   @Override
   public void onClick() {
      long longValue = KEY ^ 100898437176963L;
      String text = AccountType.v();
      if (text == null) {
         switch (this.accountType) {
            case MICROSOFT:
               try {
                  MicrosoftLoginManager.startMicrosoftLogin(microsoftAuthResult -> {});
                  break;
               } catch (Exception exception) {
                  throw new RuntimeException(exception);
               }
            case COOKIE:
               try {
                  MinecraftClientAccessor minecraftClientAccessor = (MinecraftClientAccessor)ClientContext.MINECRAFT_CLIENT;
                  AccountData importAccountFromFile = MicrosoftLoginManager.importAccountFromFile();
                  if (importAccountFromFile != null) {
                     Session session = new Session(importAccountFromFile.getUsername(), MicrosoftLoginManager.parseUuid(importAccountFromFile.getUuid()), importAccountFromFile.getToken(), Optional.empty(), Optional.empty());
                     minecraftClientAccessor.setSession(session);
                     EvonClient.getInstance().getAccountConfig().addAccount(session, LoginType.TOKEN);
                  }
                  break;
               } catch (Exception exception2) {
                  throw new RuntimeException(exception2);
               }
            case TOKEN:
               MicrosoftLoginManager.loginWithClipboardToken();
         }
      }
   }

   static {
      try {
      long var0 = KEY ^ 77408763752590L;
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
      String var6 = "\u00c1\u00b8\u0095\u00ce-\t\u00fb\u0098\u00b0\u0096\u00ccHK\u0014O\u007f\b\u00be\u0082;\u0018\u00c5\u00c4\u00fb\u00b4";
      int var8 = "\u00c1\u00b8\u0095\u00ce-\t\u00fb\u0098\u00b0\u0096\u00ccHK\u0014O\u007f\b\u00be\u0082;\u0018\u00c5\u00c4\u00fb\u00b4".length();
      char var5 = 16;
      int var4 = -1;

      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var13 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var13;
         if ((var4 += var5) >= var8) {
            STRINGS = var9;
            FONT = EvonClient.getInstance().getFontManager().getFont(STRINGS[0]);
            break;
         }

         var5 = var6.charAt(var4);
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static Exception identity(Exception exception) {
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
