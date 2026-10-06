package club.evon.module.impl.render;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.ColorPair;
import club.evon.data.EvonUser;
import club.evon.event.HudRenderEvent;
import club.evon.event.HudRenderPassEvent;
import club.evon.event.Subscribe;
import club.evon.launch.LaunchProperties;
import club.evon.manager.FontManager;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.util.KeyedRandom;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.FontRenderer;
import club.evon.util.render.FontWeight;
import club.evon.util.render.NvgFont;
import club.evon.util.render.RenderUtil;




public final class EvonWatermark extends SubMode<Overlay> {
   private static final FontManager FONT_MANAGER;
   private static final NvgFont FONT;
   // Not final: follows the client name set with ".clientname".
   private static String TITLE;
   private static final int X;
   private static final int I;
   private static final int e;
   private static final float SPACING = 3.5F;
   private static final float PADDING = 6.5F;
   private static final float BORDER_INSET = 0.6F;
   private static final float BORDER_WIDTH = 1.2F;
   private static final float PANEL_RADIUS = 12.5F;
   private static final float SEPARATOR_WIDTH = 1.1F;
   private static final float OUTER_RADIUS = 12.95F;
   private static final Color VALUE_TEXT_COLOR;
   private static final Color LABEL_TEXT_COLOR;
   private static final Color BORDER_COLOR;
   private static final Color PANEL_BACKGROUND;
   private static final Color SEPARATOR_COLOR;
   // Not final: the display name can change at runtime via the ".name" command, so these are
   // refreshed from EvonClient.getUser() instead of being captured once at class-init.
   private static String username;
   private static String uid;
   private static final String versionText;
   private static String roleName;
   private final FontRenderer fontRenderer = EvonClient.getInstance().getFontRenderer();
   private float panelWidth;
   private float titleWidth;
   private float rowHeight;
   private static final long KEY = KeyedRandom.create(-5316721832258699907L, -3413521646814513407L, MethodHandles.lookup().lookupClass()).next(182212660173617L);
   private static final String[] STRINGS;

   public EvonWatermark(String name, Overlay parent) {
      super(name, parent);
   }

   /**
    * Re-reads the identity this watermark draws.
    *
    * <p>These used to be captured once at class-init from the authentication response. Now that
    * the client runs locally they are refreshed every frame, so ".name" (the user name) and
    * ".clientname" (the client name) take effect immediately.
    */
   private static void refreshIdentity() {
      TITLE = EvonClient.getClientName().toLowerCase();

      EvonUser user = EvonClient.getUser();
      if (user == null) {
         return;
      }

      username = user.getUsername();
      uid = user.getUid();
      roleName = user.getRole().toString().toLowerCase();
   }

   @Subscribe(h = -20)
   private void onHudRenderPass(HudRenderPassEvent event) {
      long longValue = KEY ^ 26291469716702L;
      refreshIdentity();
      BaseModule[] values = ColorPair.j();
      if (values != null) {
         if (ClientContext.MINECRAFT_CLIENT.options.hudHidden) {
            return;
         }

         RenderUtil.saveState();
         RenderUtil.translate(10.0F, 10.0F);
      }

      float floatValue = Overlay.getScale();
      RenderUtil.scale(floatValue, floatValue);
      // Measure the title exactly the way onHudRender paints it: the same font, the same weight,
      // and the per-glyph advance that drawGradientString actually uses. Measuring it with FONT
      // (a different face) as a whole string under-sized the panel and pulled every following
      // element left by an amount that grew with the length of the name.
      this.titleWidth = this.fontRenderer.getGradientStringWidth(STRINGS[3], FontWeight.BOLD, TITLE, 15.0F, 9);
      this.rowHeight = Math.max(FONT.getTextWidth(username, 8.0F), FONT.getTextWidth(uid, 7.0F));
      float floatValue2 = Math.max(FONT.getTextWidth(versionText, 8.0F), FONT.getTextWidth(roleName, 7.0F));
      this.panelWidth = 6.5F + this.titleWidth + 3.5F + 9.2F + this.rowHeight + 3.5F - 0.5F + floatValue2 + 3.5F + 6.5F;
      RenderUtil.fillRoundedRectWithBlur(-0.90000004F, -0.90000004F, this.panelWidth + 1.8000001F, 26.8F, 12.95F, Color.BLACK);
      RenderUtil.restoreState();
   }

   @Subscribe(h = -20)
   private void onHudRender(HudRenderEvent event) {
      long longValue = KEY ^ 111763899245524L;
      refreshIdentity();
      BaseModule[] values = ColorPair.j();
      if (values != null) {
         if (ClientContext.MINECRAFT_CLIENT.options.hudHidden) {
            return;
         }

         RenderUtil.saveState();
         RenderUtil.translate(10.0F, 10.0F);
      }

      float floatValue = Overlay.getScale();
      RenderUtil.scale(floatValue, floatValue);
      float floatValue2 = 6.5F;
      RenderUtil.fillRoundedRectPaint(-0.90000004F, -0.90000004F, this.panelWidth + 1.8000001F, 26.8F, 12.95F, RenderUtil.gameBlurPaint);
      RenderUtil.fillRoundedRect(0.0F, 0.0F, this.panelWidth, 25.0F, 12.5F, PANEL_BACKGROUND);
      RenderUtil.strokeRoundedRect(-0.6F, -0.6F, this.panelWidth + 1.2F, 26.2F, 12.95F, 1.2F, BORDER_COLOR);
      Color primaryColor = Overlay.getPrimaryColor();
      Color secondaryColor = Overlay.getSecondaryColor();
      String[] strings = STRINGS;
      this.fontRenderer.drawGradientString(strings[3], FontWeight.BOLD, TITLE, floatValue2, (25.0F - FONT.getTextHeight(strings[2], 15.0F)) / 2.0F, 15.0F, primaryColor.getRGB(), secondaryColor.getRGB(), false, 0.0F, 9);
      floatValue2 += this.titleWidth + 3.5F;
      float floatValue3 = 6.5F;
      float floatValue4 = 7.0F;
      RenderUtil.fillRoundedRect(floatValue2, 7.0F, 1.1F, 12.0F, 0.55F, SEPARATOR_COLOR);
      floatValue2 += 4.6F;
      FONT.drawText(username, floatValue2, 6.0F, 8.0F, LABEL_TEXT_COLOR.getRGB());
      float floatValue5 = 6.0F + FONT.getTextHeight(strings[4], 8.0F);
      FONT.drawText(uid, floatValue2, floatValue5, 7.0F, VALUE_TEXT_COLOR.getRGB());
      floatValue2 += this.rowHeight + 3.5F - 0.5F;
      RenderUtil.fillRoundedRect(floatValue2, 7.0F, 1.1F, 12.0F, 0.55F, SEPARATOR_COLOR);
      floatValue2 += 4.6F;
      FONT.drawText(versionText, floatValue2, 6.0F, 8.0F, LABEL_TEXT_COLOR.getRGB());
      FONT.drawText(roleName, floatValue2, floatValue5 - 0.5F, 7.5F, VALUE_TEXT_COLOR.getRGB());
      RenderUtil.restoreState();
   }

   static {
      try {
      long var11 = KEY ^ 80733733596515L;
      Cipher var13;
      Cipher var10000 = var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var14 = 1; var14 < 8; var14++) {
         var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var20 = new String[7];
      int var18 = 0;
      String var17 = "\u00af\u00c3\u00874\u00fb\u0084\u00a2'\u0010\u0083\u008d\u00b4\u009f\u001e\u00c4Gx:\u00b3\u00f2\u0081\u001d\u00eb\u00ee\u00fe\b\u0082\u00a7\u00de\u00ae\u00b7U\u00f0e\b\u009c\u00d7\f\\-\u00e1\u008c\u007f\b\u0082\u00a7\u00de\u00ae\u00b7U\u00f0e";
      int var19 = "\u00af\u00c3\u00874\u00fb\u0084\u00a2'\u0010\u0083\u008d\u00b4\u009f\u001e\u00c4Gx:\u00b3\u00f2\u0081\u001d\u00eb\u00ee\u00fe\b\u0082\u00a7\u00de\u00ae\u00b7U\u00f0e\b\u009c\u00d7\f\\-\u00e1\u008c\u007f\b\u0082\u00a7\u00de\u00ae\u00b7U\u00f0e"
         .length();
      char var16 = '\b';
      int var24 = -1;

      label45:
      while (true) {
         String var25 = var17.substring(++var24, var24 + var16);
         int var10001 = -1;

         while (true) {
            byte[] var21 = var13.doFinal(var25.getBytes("ISO-8859-1"));
            String var34 = decodeUtf8(var21).intern();
            switch (var10001) {
               case 0:
                  var20[var18++] = var34;
                  if ((var24 += var16) >= var19) {
                     STRINGS = var20;
                     Cipher var1;
                     var10000 = var1 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var2 = 1; var2 < 8; var2++) {
                        var10003[var2] = (byte)(var11 << var2 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long[] var0 = new long[2];
                     int var4 = 0;
                     String var5 = "G\u00ea\u00a2\u0013?Bv\u00e3]\u000bG;\u0001\u00aa`\u009e";
                     int var6 = "G\u00ea\u00a2\u0013?Bv\u00e3]\u000bG;\u0001\u00aa`\u009e".length();
                     int var3 = 0;

                     do {
                        var10001 = var3;
                        var3 += 8;
                        byte[] var7 = var5.substring(var10001, var3).getBytes("ISO-8859-1");
                        var10001 = var4++;
                        long var8 = (var7[0] & 255L) << 56
                           | (var7[1] & 255L) << 48
                           | (var7[2] & 255L) << 40
                           | (var7[3] & 255L) << 32
                           | (var7[4] & 255L) << 24
                           | (var7[5] & 255L) << 16
                           | (var7[6] & 255L) << 8
                           | var7[7] & 255L;
                        byte[] var10 = var1.doFinal(
                           new byte[]{
                              (byte)(var8 >>> 56),
                              (byte)(var8 >>> 48),
                              (byte)(var8 >>> 40),
                              (byte)(var8 >>> 32),
                              (byte)(var8 >>> 24),
                              (byte)(var8 >>> 16),
                              (byte)(var8 >>> 8),
                              (byte)var8
                           }
                        );
                        long var10004 = (var10[0] & 255L) << 56
                           | (var10[1] & 255L) << 48
                           | (var10[2] & 255L) << 40
                           | (var10[3] & 255L) << 32
                           | (var10[4] & 255L) << 24
                           | (var10[5] & 255L) << 16
                           | (var10[6] & 255L) << 8
                           | var10[7] & 255L;
                        byte var39 = -1;
                        var0[var10001] = var10004;
                     } while (var3 < var6);

                     e = (int)var0[0];
                     I = (int)var0[0];
                     X = (int)var0[1];
                     FONT_MANAGER = EvonClient.getInstance().getFontManager();
                     String[] var22 = STRINGS;
                     FONT = FONT_MANAGER.getFont(var22[1]);
                     VALUE_TEXT_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.45F);
                     LABEL_TEXT_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.9F);
                     BORDER_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.05F);
                     PANEL_BACKGROUND = ColorUtil.withAlpha(new Color(35, 35, 35), 0.3F);
                     SEPARATOR_COLOR = ColorUtil.withAlpha(Color.WHITE, 0.45F);
                     refreshIdentity();
                     versionText = LaunchProperties.BUILD_TYPE.toLowerCase() + LaunchProperties.RELEASE;
                     break label45;
                  }

                  var16 = var17.charAt(var24);
                  break;
               default:
                  var20[var18++] = var34;
                  if ((var24 += var16) < var19) {
                     var16 = var17.charAt(var24);
                     continue label45;
                  }

                  var17 = "\u008e@i\u00ea.\u0092\u0006\u0018\u0010oH\u00bcq\u00f8~?+\u00eaK\u00f9A\u008dy.*";
                  var19 = "\u008e@i\u00ea.\u0092\u0006\u0018\u0010oH\u00bcq\u00f8~?+\u00eaK\u00f9A\u008dy.*".length();
                  var16 = '\b';
                  var24 = -1;
            }

            var25 = var17.substring(++var24, var24 + var16);
            var10001 = 0;
         }
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
