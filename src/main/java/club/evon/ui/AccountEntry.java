package club.evon.ui;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.UUID;
import java.util.function.Consumer;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.EvonClient;
import club.evon.api.AccountType;
import club.evon.api.LoginType;
import club.evon.manager.FontManager;
import club.evon.module.BaseModule;
import club.evon.util.Animator;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.math.Easing;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.NvgFont;
import club.evon.util.render.RenderUtil;




public class AccountEntry {
   private float U;
   private float o;
   private final float N;
   private final float J;
   private final float F;
   private final Animator hoverAnimator = new Animator(200, 0.0, Easing.LINEAR);
   private final Animator fadeAnimator = new Animator(100, 0.0, Easing.LINEAR);
   private static final Color BORDER_COLOR;
   private final String z;
   private final String p;
   private final UUID M;
   private final LoginType loginType;
   private static final long KEY = KeyedRandom.create(2029814519829821927L, -3245157692221882753L, MethodHandles.lookup().lookupClass()).next(60255473608234L);
   private static final String[] STRINGS;

   public AccountEntry(String username, String token, UUID uuid, LoginType type, float width, float height, float radius) {
      this.z = username;
      this.p = token;
      this.M = uuid;
      this.loginType = type;
      this.N = width;
      this.J = height;
      this.F = radius;
      this.fadeAnimator.setTargetValue(0.0);
   }

   public void render(float x, float y, double mouseX, double mouseY) {
      long longValue = KEY ^ 2384792856530L;
      String tmp = AccountType.v();
      this.U = x;
      this.o = y;
      this.fadeAnimator.setDuration(800);
      this.fadeAnimator.setTargetValue(1.0);
      String text = tmp;
      float floatValue = this.fadeAnimator.getValue().floatValue();
      double doubleValue = mouseX;
      if (text == null) {
         doubleValue = HoverUtil.isHovered(mouseX, mouseY, x, y, this.N, this.J) ? 1.0 : 0.0;
      }

      double doubleValue2;
      String text2;
      Color color;
      Color color2;
      label43: {
         this.hoverAnimator.setTargetValue(doubleValue);
         doubleValue2 = this.hoverAnimator.getValue();
         text2 = EvonClient.getInstance().getBanManager().getRemainingBanTime(this.z);
         color = new Color(255, 80, 80);
         color2 = new Color(255, 168, 168, (int)(255.0F * floatValue));
         tmp = text2;
         if (text == null) {
            if (text2 == null) {
               String[] strings = STRINGS;
               text2 = strings[1];
               color = new Color(168, 255, 168, (int)(255.0F * floatValue));
               color2 = color;
               if (text == null) {
                  break label43;
               }

               BaseModule.setStateArray(new BaseModule[3]);
            }

            String[] strings2 = STRINGS;
            tmp = text2 + strings2[0];
         }

         text2 = tmp;
      }

      label35: {
         RenderUtil.fillRoundedRect(x, y, this.N, this.J, this.F, ColorUtil.withAlpha(color, (0.2F + 0.1F * doubleValue2) * floatValue));
         float floatValue2 = 1.0F;
         float floatValue3 = 0.5F;
         float floatValue4 = x + 0.5F;
         float floatValue5 = y + 0.5F;
         float floatValue6 = this.N - 1.0F;
         float floatValue7 = this.J - 1.0F;
         float floatValue8 = this.F;
         float floatValue9 = 1.0F;
         Color withAlpha = ColorUtil.withAlpha(BORDER_COLOR, BORDER_COLOR.getAlpha() / 255.0F * floatValue);
         if (text == null) {
            RenderUtil.strokeRoundedRect(floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, 1.0F, withAlpha);
            if (!(doubleValue2 > 0.0)) {
               break label35;
            }

            floatValue4 = x;
            floatValue5 = y;
            floatValue6 = this.N;
            floatValue7 = this.J;
            floatValue8 = this.F;
            floatValue9 = 1.0F;
            withAlpha = ColorUtil.withAlpha(Color.WHITE, 0.4F * doubleValue2);
         }

         RenderUtil.strokeHoverOutline(floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, floatValue9, withAlpha, (float)mouseX);
      }

      Color withAlpha2 = ColorUtil.withAlpha(Color.WHITE, floatValue);
      RenderUtil.drawPlayerHead(x + 7.0F, y + 7.0F, this.J - 14.0F, (this.J - 14.0F) / 2.0F, withAlpha2, this.M.toString());
      FontManager fontManager = EvonClient.getInstance().getFontManager();
      String[] strings3 = STRINGS;
      NvgFont font = fontManager.getFont(strings3[2]);
      font.drawText(this.z, x + this.J, y + 15.0F, 15.0F, withAlpha2.getRGB());
      NvgFont font2 = EvonClient.getInstance().getFontManager().getFont(strings3[3]);
      font2.drawText(text2, x + this.J, y + 30.0F, 10.0F, color2.getRGB());
   }

   public void withCopy(Consumer<AccountEntry> consumer) {
      consumer.accept(this.copy());
   }

   public AccountEntry copy() {
      return new AccountEntry(this.z, this.p, this.M, this.loginType, this.N, this.J, this.F);
   }

   public float getX() {
      return this.U;
   }

   public float getY() {
      return this.o;
   }

   public float getWidth() {
      return this.N;
   }

   public float getHeight() {
      return this.J;
   }

   public float getRadius() {
      return this.F;
   }

   public String getUsername() {
      return this.z;
   }

   public String getToken() {
      return this.p;
   }

   static {
      try {
      long var0 = KEY ^ 31796176610115L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[4];
      int var7 = 0;
      String var6 = "N.\u001f\u00dbtv\u00cc\u0091\"^d\u001e\u00eb\u0003\u0088\u00cc\bJ\u00e6o\u00184\u0015\u00b8\u008b";
      int var8 = "N.\u001f\u00dbtv\u00cc\u0091\"^d\u001e\u00eb\u0003\u0088\u00cc\bJ\u00e6o\u00184\u0015\u00b8\u008b".length();
      char var5 = 16;
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
                     BORDER_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.15F);
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

                  var6 = "s_\u0010\u00ed9u\u001ei\u00c0\na\u00e0\u00f0\u00d0\u00f8\u00c3\u0010Gz\u00f1\u001d?x\u00cb\u008cTJ\t\u00db\u00bf\u00f3\u00faY";
                  var8 = "s_\u0010\u00ed9u\u001ei\u00c0\na\u00e0\u00f0\u00d0\u00f8\u00c3\u0010Gz\u00f1\u001d?x\u00cb\u008cTJ\t\u00db\u00bf\u00f3\u00faY"
                     .length();
                  var5 = 16;
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
