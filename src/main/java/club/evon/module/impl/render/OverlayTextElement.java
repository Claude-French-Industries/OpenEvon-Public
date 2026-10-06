package club.evon.module.impl.render;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.event.HudRenderEvent;
import club.evon.event.HudRenderPassEvent;
import club.evon.event.Subscribe;
import club.evon.manager.FontManager;
import club.evon.module.SubMode;
import club.evon.util.KeyedRandom;
import club.evon.util.render.NvgFont;




public class OverlayTextElement extends SubMode<Overlay> {
   private static final FontManager FONT_MANAGER;
   private static final NvgFont FONT;
   private static final String elementName;
   private static final long KEY = KeyedRandom.create(-7146211465580810713L, -3436010982153657857L, MethodHandles.lookup().lookupClass()).next(164065853308105L);

   public OverlayTextElement(String name, Overlay parent) {
      super(name, parent);
   }

   @Subscribe
   private void onHudRenderPass(HudRenderPassEvent event) {
      long longValue = KEY ^ 87520828544848L;
      if (!this.isHudHidden()) {
         ;
      }
   }

   @Subscribe
   private void onHudRender(HudRenderEvent event) {
      long longValue = KEY ^ 84835145543167L;
      if (!this.isHudHidden()) {
         ;
      }
   }

   private boolean isHudHidden() {
      return ClientContext.MINECRAFT_CLIENT.options.hudHidden;
   }

   static {
      long var1 = KEY ^ 117795016102988L;

      try {
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
         String var7 = "\u00d3l9R\u0082C\u00fc\u0090\u0010\u009d\u00cao\u00d6\u00d4\u00a4\u00bb\u009f\u008f\u00b1}\u0083\u0084\u00fb|`";
         int var9 = "\u00d3l9R\u0082C\u00fc\u0090\u0010\u009d\u00cao\u00d6\u00d4\u00a4\u00bb\u009f\u008f\u00b1}\u0083\u0084\u00fb|`".length();
         char var6 = '\b';
         int var5 = -1;

         label59:
         while (true) {
            byte[] var10 = var3.doFinal(var7.substring(++var5, var5 + var6).getBytes("ISO-8859-1"));
            String var13 = decodeUtf8(var10).intern();
            byte var10001 = -1;
            var0[var8++] = var13;
            if ((var5 += var6) >= var9) {
               FONT_MANAGER = EvonClient.getInstance().getFontManager();
               FONT = FONT_MANAGER.getFont(var0[1]);
               elementName = var0[0].toLowerCase();
               break label59;
            }

            var6 = var7.charAt(var5);
         }
      } catch (Exception var14) {
         throw new ExceptionInInitializerError(var14);
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
