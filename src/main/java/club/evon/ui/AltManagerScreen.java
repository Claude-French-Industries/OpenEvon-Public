package club.evon.ui;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import club.evon.EvonClient;
import club.evon.api.AccountType;
import club.evon.api.LoginType;
import club.evon.manager.FontManager;
import club.evon.module.BaseModule;
import club.evon.setting.DoubleSetting;
import club.evon.util.HoverUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.render.NvgFont;
import club.evon.util.render.RenderUtil;




public final class AltManagerScreen extends AbstractScreen {
   private static AltManagerScreen instance;
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final NvgFont titleFont;
   private static final NvgFont bodyFont;
   private static final Color PANEL_COLOR;
   private static final float PANEL_WIDTH = 585.0F;
   private static final float PANEL_HEIGHT = 450.0F;
   private static final int w;
   private static final int T;
   private AccountEntry selectedEntry;
   private final LoginMethodButton cookieButton;
   private final LoginMethodButton microsoftButton;
   private final LoginMethodButton tokenButton;
   private final AccountActionButton loginButton;
   private final AccountActionButton removeButton;
   private final Map<String, AccountEntry> accounts;
   private static final long KEY = KeyedRandom.create(5061095152453542543L, -8488319479886691138L, MethodHandles.lookup().lookupClass()).next(109374495925841L);
   private static final String[] STRINGS;

   public static synchronized AltManagerScreen getInstance() {
      long longValue = KEY ^ 84587595716073L;
      BaseModule[] values = DoubleSetting.X();
      AltManagerScreen tmp = instance;
      if (values == null) {
         if (instance == null) {
            instance = new AltManagerScreen();
         }

         tmp = instance;
      }

      return tmp;
   }

   private AltManagerScreen() {
      super();

      long longValue = KEY ^ 71207248993103L;
      BaseModule[] values = DoubleSetting.X();
      BaseModule[] values2 = values;
      this.accounts = new LinkedHashMap<>();
      float floatValue = 140.0F;
      float floatValue2 = 25.0F;
      float floatValue3 = 10.0F;
      this.cookieButton = new LoginMethodButton(AccountType.COOKIE, 140.0F, 25.0F, 10.0F);
      this.microsoftButton = new LoginMethodButton(AccountType.MICROSOFT, 140.0F, 25.0F, 10.0F);
      this.tokenButton = new LoginMethodButton(AccountType.TOKEN, 140.0F, 25.0F, 10.0F);
      this.loginButton = new AccountActionButton(AccountAction.LOGIN, 88.0F, 25.0F, 10.0F);
      this.removeButton = new AccountActionButton(AccountAction.REMOVE, 88.0F, 25.0F, 10.0F);
      Iterator iterator = EvonClient.getInstance().getAccountConfig().getAccounts().asMap().entrySet().stream().map(jsonElement -> ((JsonElement)jsonElement.getValue()).getAsJsonArray()).toList().iterator();

      while (true) {
         if (iterator.hasNext()) {
            JsonArray jsonArray = (JsonArray)iterator.next();
            this.addAccount(jsonArray);
            if (values2 != null) {
               break;
            }

            if (values2 == null) {
               continue;
            }
         }

         AltManagerScreen altManagerScreen = this;
         if (values2 == null) {
            if (this.accounts.isEmpty()) {
               break;
            }

            altManagerScreen = this;
         }

         altManagerScreen.selectedEntry = this.accounts.values().stream().toList().getFirst().copy();
         break;
      }
      }

   public void addAccount(JsonArray accountInfo) {
      long longValue = KEY ^ 139734364715112L;
      DoubleSetting.X();
      // Skip malformed entries instead of letting one bad row abort the whole alt list (the
      // list is rebuilt from accounts.json in the constructor).
      if (accountInfo == null || accountInfo.size() < 4) {
         return;
      }

      try {
         String asString = accountInfo.get(0).getAsString();
         this.accounts
            .put(
               asString,
               new AccountEntry(
                  asString,
                  accountInfo.get(1).getAsString(),
                  UUID.fromString(accountInfo.get(2).getAsString()),
                  LoginType.fromLevel(accountInfo.get(3).getAsInt()),
                  181.66667F,
                  55.0F,
                  10.0F
               )
            );
      } catch (Exception exception) {
         LOGGER.error("[Evon] Skipping malformed account entry", exception);
         return;
      }

      if (BaseModule.getStateArray() == null) {
         DoubleSetting.setStateArray(new BaseModule[1]);
      }
   }

   public void removeAccount(String username) {
      long longValue = KEY ^ 103569114682021L;
      BaseModule[] values = DoubleSetting.X();
      if (values == null) {
         // The decompiled version dereferenced selectedEntry unconditionally, which throws when
         // the removed account was the selected (or the only) one.
         if (this.selectedEntry != null
            && this.accounts.containsKey(username)
            && this.selectedEntry.getUsername().equals(this.accounts.get(username).getUsername())) {
            this.selectedEntry = null;
         }

         this.accounts.remove(username);
      }
   }

   public void render(float mouseX, float mouseY, int windowWidth, int windowHeight) {
      long longValue = KEY ^ 58862902360097L;
      float floatValue = 20.0F;
      float floatValue2 = (windowHeight - 450.0F) / 2.0F;
      float floatValue3 = 290.0F;
      float floatValue4 = 160.0F;
      BaseModule[] values = DoubleSetting.X();
      float floatValue5 = (windowWidth - (585.0F + floatValue3 + 20.0F)) / 2.0F;
      float floatValue6 = floatValue2;
      float floatValue7 = 15.0F;
      BaseModule[] values2 = values;
      float floatValue8 = 20.0F;
      String[] strings = STRINGS;
      String text = strings[7];
      String[] strings2 = new String[]{strings[3], strings[4], strings[5], strings[9]};
      int value = new Color(190, 190, 190).getRGB();
      float floatValue9 = floatValue5 + 10.0F;
      float floatValue10 = floatValue5 + 25.5F;
      float floatValue11 = floatValue6 + 33.0F;
      float floatValue12 = bodyFont.getTextHeight(strings[6], 13.5F);
      ScreenManager.drawPanel(floatValue5, floatValue6, floatValue3, floatValue4, 15.0F);
      RenderUtil.fillRoundedRect(floatValue5, floatValue6, floatValue3, floatValue4, 15.0F, PANEL_COLOR);
      int value2 = 1;
      String[] strings3 = strings2;
      int length = strings3.length;
      int index = 0;

      while (true) {
         if (index < length) {
            String text2 = strings3[index];
            bodyFont.drawText(value2 + ".", floatValue9, floatValue11, 15.0F, Color.WHITE.getRGB());
            String[] strings4 = text2.split("\n");
            String[] strings5 = strings4;
            int length2 = strings5.length;
            if (values2 != null) {
               break;
            }

            int index2 = 0;

            label65: {
               while (index2 < length2) {
                  String text3 = strings5[index2];
                  bodyFont.drawText(text3, floatValue10, floatValue11, 13.5F, value);
                  floatValue11 += floatValue12 - 2.0F;
                  index2++;
                  if (values2 != null) {
                     break label65;
                  }

                  if (values2 != null) {
                     BaseModule.setStateArray(new BaseModule[5]);
                     break;
                  }
               }

               value2++;
               floatValue11 += 8.0F;
               index++;
            }

            if (values2 == null) {
               continue;
            }
         }

         drawPanel(floatValue5, floatValue6, floatValue3, floatValue4, 15.0F);
         titleFont.drawText(text, floatValue5 + centerTextX(floatValue3, text, titleFont, 20.0F), floatValue6 + 10.0F, 20.0F, Color.WHITE.getRGB());
         floatValue6 += floatValue4 + 10.0F;
         floatValue4 = 133.0F;
         strings = STRINGS;
         text = strings[0];
         ScreenManager.drawPanel(floatValue5, floatValue6, floatValue3, floatValue4, 15.0F);
         RenderUtil.fillRoundedRect(floatValue5, floatValue6, floatValue3, floatValue4, 15.0F, PANEL_COLOR);
         drawPanel(floatValue5, floatValue6, floatValue3, floatValue4, 15.0F);
         titleFont.drawText(text, floatValue5 + centerTextX(floatValue3, text, titleFont, 20.0F), floatValue6 + 10.0F, 20.0F, Color.WHITE.getRGB());
         break;
      }

      float floatValue13 = floatValue5 + (floatValue3 - this.cookieButton.getWidth()) / 2.0F;
      float floatValue14 = floatValue6 + 36.0F;
      floatValue9 = 20.0F;
      this.cookieButton.render(floatValue13, floatValue14, mouseX, mouseY);
      float floatValue15 = floatValue14 + (this.cookieButton.getHeight() + 4.0F);
      this.microsoftButton.render(floatValue13, floatValue15, mouseX, mouseY);
      float floatValue16 = floatValue15 + (this.microsoftButton.getHeight() + 4.0F);
      this.tokenButton.render(floatValue13, floatValue16, mouseX, mouseY);
      floatValue6 += floatValue4 + 10.0F;
      floatValue4 = 450.0F - (floatValue6 - floatValue2);
      text = STRINGS[2];
      ScreenManager.drawPanel(floatValue5, floatValue6, floatValue3, floatValue4, 15.0F);
      RenderUtil.fillRoundedRect(floatValue5, floatValue6, floatValue3, floatValue4, 15.0F, PANEL_COLOR);
      drawPanel(floatValue5, floatValue6, floatValue3, floatValue4, 15.0F);
      titleFont.drawText(text, floatValue5 + centerTextX(floatValue3, text, titleFont, 20.0F), floatValue6 + 10.0F, 20.0F, Color.WHITE.getRGB());
      if (values2 == null) {
         if (this.selectedEntry != null) {
            float floatValue17 = floatValue5 + (floatValue3 - this.selectedEntry.getWidth()) / 2.0F;
            this.selectedEntry.render(floatValue17, floatValue6 + 35.0F, mouseX, mouseY);
            float floatValue18 = 99.0F;
            this.loginButton.render(floatValue17, floatValue6 + 99.0F, mouseX, mouseY);
            this.removeButton.render(floatValue17 + this.loginButton.getWidth() + (this.selectedEntry.getWidth() - this.loginButton.getWidth() * 2.0F), floatValue6 + 99.0F, mouseX, mouseY);
         }

         float floatValue19 = floatValue5 + (floatValue3 + 20.0F);
         floatValue6 = floatValue2;
         ScreenManager.drawPanel(floatValue19, floatValue6, 585.0F, 450.0F, 15.0F);
         RenderUtil.fillRoundedRect(floatValue19, floatValue6, 585.0F, 450.0F, 15.0F, PANEL_COLOR);
         drawPanel(floatValue19, floatValue6, 585.0F, 450.0F, 15.0F);
         floatValue5 = floatValue19 + 10.0F;
         floatValue13 = floatValue5;
         floatValue6 += 10.0F;
      }

      value = 0;

      for (AccountEntry accountEntry : this.accounts.values()) {
         label46: {
            accountEntry.render(floatValue5, floatValue6, mouseX, mouseY);
            value++;
            if (values2 == null) {
               if (value % 3 != 0) {
                  floatValue5 += accountEntry.getWidth() + 10.0F;
                  if (values2 == null) {
                     break label46;
                  }
               }

               floatValue5 = floatValue13;
            }

            floatValue6 += accountEntry.getHeight() + 10.0F;
         }

         if (values2 != null) {
            break;
         }
      }
   }

   @Override
   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
      long longValue = KEY ^ 104066595846323L;
      BaseModule[] values = DoubleSetting.X();
      int value = mouseButton;
      if (values == null) {
         if (mouseButton != 0) {
            return;
         }

         value = HoverUtil.isHovered(mouseX, mouseY, this.cookieButton.getX(), this.cookieButton.getY(), this.cookieButton.getWidth(), this.cookieButton.getHeight()) ? 1 : 0;
      }

      if (values == null) {
         if (value != 0) {
            this.cookieButton.onClick();
         }

         value = HoverUtil.isHovered(mouseX, mouseY, this.microsoftButton.getX(), this.microsoftButton.getY(), this.microsoftButton.getWidth(), this.microsoftButton.getHeight()) ? 1 : 0;
      }

      if (values == null) {
         if (value != 0) {
            this.microsoftButton.onClick();
         }

         value = HoverUtil.isHovered(mouseX, mouseY, this.tokenButton.getX(), this.tokenButton.getY(), this.tokenButton.getWidth(), this.tokenButton.getHeight()) ? 1 : 0;
      }

      if (values == null) {
         if (value != 0) {
            this.tokenButton.onClick();
         }

         value = HoverUtil.isHovered(mouseX, mouseY, this.loginButton.getX(), this.loginButton.getY(), this.loginButton.getWidth(), this.loginButton.getHeight()) ? 1 : 0;
      }

      if (values == null) {
         if (value != 0) {
            this.loginButton.setUsername(this.selectedEntry.getUsername());
            this.loginButton.setToken(this.selectedEntry.getToken());
            this.loginButton.onClick();
         }

         value = HoverUtil.isHovered(mouseX, mouseY, this.removeButton.getX(), this.removeButton.getY(), this.removeButton.getWidth(), this.removeButton.getHeight()) ? 1 : 0;
      }

      AltManagerScreen altManagerScreen;
      label104: {
         if (value != 0) {
            altManagerScreen = this;
            if (values != null) {
               break label104;
            }

            if (this.selectedEntry != null) {
               this.removeButton.setUsername(this.selectedEntry.getUsername());
               this.removeButton.setToken(this.selectedEntry.getToken());
               this.removeButton.onClick();
            }
         }

         altManagerScreen = this;
      }

      for (AccountEntry accountEntry : altManagerScreen.accounts.values()) {
         AccountEntry accountEntry2;
         label94:
         if (HoverUtil.isHovered(mouseX, mouseY, accountEntry.getX(), accountEntry.getY(), accountEntry.getWidth(), accountEntry.getHeight())) {
            accountEntry2 = this.selectedEntry;
            label92:
            if (values == null) {
               if (this.selectedEntry != null) {
                  accountEntry2 = this.selectedEntry;
                  if (values != null) {
                     break label92;
                  }

                  if (this.selectedEntry.getUsername().equals(accountEntry.getUsername())) {
                     break label94;
                  }
               }

               accountEntry2 = accountEntry;
            }

            accountEntry2.withCopy(accountEntry3 -> this.selectedEntry = accountEntry3);
         }

         if (values != null) {
            break;
         }
      }
   }

   @Override
   public void keyPressed(int keyCode) {
      long longValue = KEY ^ 54586219566271L;
      BaseModule[] values = DoubleSetting.X();
      if (values == null) {
         if (keyCode != 256) {
            return;
         }

         ScreenManager.setCurrentScreen(MainMenuScreen.getInstance());
      }
   }

   private static float centerTextX(float containerWidth, String text, NvgFont font, float fontSize) {
      return (containerWidth - font.getTextWidth(text, fontSize)) / 2.0F;
   }

   static {
      try {
      long var11 = KEY ^ 64791931289999L;
      Cipher var13;
      Cipher var10000 = var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var14 = 1; var14 < 8; var14++) {
         var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var20 = new String[10];
      int var18 = 0;
      String var17 = "s}r\u00c2\u0081\u00947\u0084\u0010\u00e2\u0094=q.%\u00e4\u00d0\u00ff\u00a3\u001d\u0019\u000e\u0087\u00b8W\u0018\u00bdNnwB}\u00faT4\u001d\u0093\u000e\n\u00acEKG\u0083!I\u00e2;\u00d5R Np=x\u000e\u00b3=\u00bdM\u0011v(\u0013\u00e0\u0000\u00ac\u00cdPU\u0097<\u00c5K\u00d0\u0000\u00f2\u00c1J\u008d\u00da\u00df\u00a60\u00d1\u00edKP/\u00ccI\u00da\u00e4\u00c7\u00a9\u0014N\u00acj~\u0015R0~jEZ)A\u00037\u00a5g`\u0097d\u00d8\u00e4!\u001a\u0015\u00eb\r\u0017\u00c6\u009d\u00ee\u0017Zo\u00c2Vh\u0005w\u00b2\u0007\u00ed\u00eafLX\u00f5\u00a0h\u001a\u00ed_\u00165\u0087P7\u00e1\u00e0\u00f2\u00f9\u00bd\u00f4\u00117\u008c\u00a0P\u0096R\u00f7b\u009f\u0004\t{\u00f1\u00f1\u0015,\u0096\u0089\u00d6\u00ae\u0096\t\u00e0r\u00ed\u00f1\\\u00d1U+PJ\u00bb\u00cdC\u009b'v\u00a0\u0000\u008b\u0016\u00efuZx\u00ac\u008d\u00f6\u00f1\u00d3\u00ef\u00db'\u008c:w\u00fe>6\u0003\u00961U\u00a9\u00c3\u008cW\u00f9\u00ae\u0094\u0092\f(\u0001\u0092O\b\u0096 \u00ee.\u008bw\u0092\u0005\u0010\u0018\u00ca\u00c0xO\u00cbm\u00aa8\u00fa`;\u00ce\u0016\u00d9O";
      int var19 = "s}r\u00c2\u0081\u00947\u0084\u0010\u00e2\u0094=q.%\u00e4\u00d0\u00ff\u00a3\u001d\u0019\u000e\u0087\u00b8W\u0018\u00bdNnwB}\u00faT4\u001d\u0093\u000e\n\u00acEKG\u0083!I\u00e2;\u00d5R Np=x\u000e\u00b3=\u00bdM\u0011v(\u0013\u00e0\u0000\u00ac\u00cdPU\u0097<\u00c5K\u00d0\u0000\u00f2\u00c1J\u008d\u00da\u00df\u00a60\u00d1\u00edKP/\u00ccI\u00da\u00e4\u00c7\u00a9\u0014N\u00acj~\u0015R0~jEZ)A\u00037\u00a5g`\u0097d\u00d8\u00e4!\u001a\u0015\u00eb\r\u0017\u00c6\u009d\u00ee\u0017Zo\u00c2Vh\u0005w\u00b2\u0007\u00ed\u00eafLX\u00f5\u00a0h\u001a\u00ed_\u00165\u0087P7\u00e1\u00e0\u00f2\u00f9\u00bd\u00f4\u00117\u008c\u00a0P\u0096R\u00f7b\u009f\u0004\t{\u00f1\u00f1\u0015,\u0096\u0089\u00d6\u00ae\u0096\t\u00e0r\u00ed\u00f1\\\u00d1U+PJ\u00bb\u00cdC\u009b'v\u00a0\u0000\u008b\u0016\u00efuZx\u00ac\u008d\u00f6\u00f1\u00d3\u00ef\u00db'\u008c:w\u00fe>6\u0003\u00961U\u00a9\u00c3\u008cW\u00f9\u00ae\u0094\u0092\f(\u0001\u0092O\b\u0096 \u00ee.\u008bw\u0092\u0005\u0010\u0018\u00ca\u00c0xO\u00cbm\u00aa8\u00fa`;\u00ce\u0016\u00d9O"
         .length();
      char var16 = '\b';
      int var24 = -1;

      label45:
      while (true) {
         String var25 = var17.substring(++var24, var24 + var16);
         int var10001 = -1;

         while (true) {
            byte[] var21 = var13.doFinal(var25.getBytes("ISO-8859-1"));
            String var35 = decodeUtf8(var21).intern();
            switch (var10001) {
               case 0:
                  var20[var18++] = var35;
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
                     String var5 = "7\u00a0\u0015Y\u0087M\u00e8y\u00f4\u00e4!a\u00b6\u00df\u00e1\u009b";
                     int var6 = "7\u00a0\u0015Y\u0087M\u00e8y\u00f4\u00e4!a\u00b6\u00df\u00e1\u009b".length();
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
                        byte var40 = -1;
                        var0[var10001] = var10004;
                     } while (var3 < var6);

                     T = (int)var0[0];
                     w = (int)var0[1];
                     FontManager var28 = EvonClient.getInstance().getFontManager();
                     String[] var22 = STRINGS;
                     titleFont = var28.getFont(var22[1]);
                     bodyFont = EvonClient.getInstance().getFontManager().getFont(var22[8]);
                     PANEL_COLOR = new Color(11, 5, 18, 50);
                     break label45;
                  }

                  var16 = var17.charAt(var24);
                  break;
               default:
                  var20[var18++] = var35;
                  if ((var24 += var16) < var19) {
                     var16 = var17.charAt(var24);
                     continue label45;
                  }

                  var17 = "+\u00b4xe\u00d1\u00af\u00ab|M\u0083\u00d1\u009fv\u00d8\u0085\u00d9P\u0005w\u00b2\u0007\u00ed\u00eafLd\u0001[\u00a2\u00fe\u001bf\u0003\u00b23\u00ad\u0086.o@\u00f4\u009c\u00a2\u00db\u009b\u00d6\u00ad\u00dc\u001c5\u0080\u0012\u007f>\u0004J\u00ff\u00ec\u0085\u00c5\u001f\u00c4L\u00ce\u009a?$\u0011\u00cb\u008d\u00eb\u0082\u008b\u00d3\u0092M\u00aa\u0089\u0099\u00ef?+\u00e0\u0007iX\u0015\u001e\u00cdg\u00e7\u0001\u0018\u00a26\u00b8\u000f";
                  var19 = "+\u00b4xe\u00d1\u00af\u00ab|M\u0083\u00d1\u009fv\u00d8\u0085\u00d9P\u0005w\u00b2\u0007\u00ed\u00eafLd\u0001[\u00a2\u00fe\u001bf\u0003\u00b23\u00ad\u0086.o@\u00f4\u009c\u00a2\u00db\u009b\u00d6\u00ad\u00dc\u001c5\u0080\u0012\u007f>\u0004J\u00ff\u00ec\u0085\u00c5\u001f\u00c4L\u00ce\u009a?$\u0011\u00cb\u008d\u00eb\u0082\u008b\u00d3\u0092M\u00aa\u0089\u0099\u00ef?+\u00e0\u0007iX\u0015\u001e\u00cdg\u00e7\u0001\u0018\u00a26\u00b8\u000f"
                     .length();
                  var16 = 16;
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
