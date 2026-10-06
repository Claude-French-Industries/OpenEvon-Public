package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.session.Session;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.api.AccountType;
import club.evon.api.MicrosoftLoginManager;
import club.evon.util.KeyedRandom;
import club.evon.util.render.NvgFont;




public class AccountActionButton extends MenuButton {
   private final AccountAction action;
   private static final NvgFont font;
   private String c;
   private String g;
   private static final long KEY = KeyedRandom.create(8314649173574492878L, -658755670319312848L, MethodHandles.lookup().lookupClass()).next(152565190105463L);

   public AccountActionButton(AccountAction type, float width, float height, float radius) {
      super(width, height, radius);
      this.action = type;
   }

   public void render(float x, float y, double mouseX, double mouseY) {
      Color color;
      label11: {
         long longValue = KEY ^ 76548039515138L;
         String text = AccountType.v();
         if (this.action == AccountAction.LOGIN) {
            color = new Color(173, 249, 138);
            if (text == null) {
               break label11;
            }
         }

         color = new Color(255, 44, 44);
      }

      super.render(x, y, mouseX, mouseY, color);
      float floatValue = this.getWidth();
      float floatValue2 = this.getHeight();
      font.drawText(this.action.displayName, x + (floatValue - font.getTextWidth(this.action.displayName, 11.0F)) / 2.0F, y + (floatValue2 - font.getTextHeight(this.action.displayName, 11.0F)) / 2.0F, 11.0F, Color.WHITE.getRGB());
   }

   @Override
   public void onClick() {
      long longValue = KEY ^ 124467709612425L;
      String text = AccountType.v();
      switch (this.action) {
         case LOGIN:
            Session session = ClientContext.MINECRAFT_CLIENT.getSession();
            if (text == null) {
               String tmp;
               label32: {
                  if (session != null) {
                     tmp = session.getUsername();
                     if (text != null) {
                        break label32;
                     }

                     if (tmp.equals(this.c)) {
                        return;
                     }
                  }

                  tmp = this.g;
               }

               MicrosoftLoginManager.loginWithToken(tmp);
            }

            if (text == null) {
               break;
            }
         case REMOVE:
            EvonClient.getInstance().getAccountConfig().removeAccount(this.c);
      }
   }

   public void setUsername(String username) {
      this.c = username;
   }

   public void setToken(String token) {
      this.g = token;
   }

   static {
      try {
      long var1 = KEY ^ 48301573381557L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var5 = var3.doFinal("\u00d6N\u0014\u00c5\u009c1\u0080\u0086t/\u00c4\u001e\u00f1\u00cf!\u0097".getBytes("ISO-8859-1"));
      String var6 = decodeUtf8(var5).intern();
      byte var10001 = -1;
      String var0 = var6;
      font = EvonClient.getInstance().getFontManager().getFont(var0);
   
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}

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


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
