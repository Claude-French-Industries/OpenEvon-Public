package club.evon.module.impl.misc;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.ClientTickEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.DoubleSetting;
import club.evon.setting.StringSetting;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.Stopwatch;




public final class PartySpammer extends BaseModule {
   private final StringSetting username;
   private final DoubleSetting delay;
   private final Stopwatch stopwatch;
   private boolean leaveNext;
   private static final long KEY = KeyedRandom.create(-4279829268810019461L, 2644575792255286326L, MethodHandles.lookup().lookupClass()).next(22106569852574L);
   private static final String[] STRINGS;
   private static final long TIME_SCALE;

   public PartySpammer() {
      super((STRINGS)[1], (STRINGS)[3], ModuleCategory.MISC);

      String[] strings = STRINGS;
      this.username = new StringSetting(strings[5], "");
      this.delay = new DoubleSetting(strings[0], 500.0, 500.0, 1000.0, 50.0);
      this.stopwatch = new Stopwatch();
      this.addSettings(this.username, this.delay);
      }

   @Subscribe
   private void onTick(ClientTickEvent event) {
      long longValue = KEY ^ 75369017091431L;
      int value = Streamer.getStateFlag();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (value != 0) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      Object object = tmp.world;
      if (value != 0) {
         if (tmp.world == null) {
            return;
         }

         object = this.username.getValue();
      }

      boolean empty = ((String)object).isEmpty();
      if (value != 0) {
         if (empty) {
            return;
         }

         empty = this.stopwatch.hasElapsed(this.delay.J().longValue() / TIME_SCALE, true);
      }

      Object object2 = null;
      String text = null;

      label59: {
         label79: {
            if (value != 0) {
               if (!empty) {
                  return;
               }

               object2 = this;
               if (value == 0) {
                  break label79;
               }

               empty = this.leaveNext;
            }

            if (empty) {
               text = STRINGS[4];
               break label59;
            }

            object2 = this.username.getValue();
         }

         text = STRINGS[2] + (String)object2;
      }

      String text2 = text;
      ChatUtil.sendChatMessage(text2);
      boolean tmp2 = this.leaveNext;
      if (value != 0) {
         tmp2 = !this.leaveNext;
      }

      this.leaveNext = tmp2;
   }

   static {
      try {
      long var5 = KEY ^ 67748435269811L;
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[6];
      int var12 = 0;
      String var11 = "y\u00fe\u001c\u00f0\u001a\u009a\u00c9\u008a\u0010\u0094\u0095P\u00e9\u0097\u00f43[H\u00caqAsI\u00edV\b\u009f\u008e\u00dd\u00b3\u00db\u001a\u0000\u00f4 \u0007\u00a2\u0086/hR<9\u00e5x\u00fb-a\u0012\u00db\u00bf2\u00cdq\u0013\u00d2\u00b4T\u00e9\u00a4\u00aa\u000fW_\u00b9*-";
      int var13 = "y\u00fe\u001c\u00f0\u001a\u009a\u00c9\u008a\u0010\u0094\u0095P\u00e9\u0097\u00f43[H\u00caqAsI\u00edV\b\u009f\u008e\u00dd\u00b3\u00db\u001a\u0000\u00f4 \u0007\u00a2\u0086/hR<9\u00e5x\u00fb-a\u0012\u00db\u00bf2\u00cdq\u0013\u00d2\u00b4T\u00e9\u00a4\u00aa\u000fW_\u00b9*-"
         .length();
      char var10 = '\b';
      int var17 = -1;

      label37:
      while (true) {
         String var18 = var11.substring(++var17, var17 + var10);
         byte var10001 = -1;

         while (true) {
            byte[] var15 = var7.doFinal(var18.getBytes("ISO-8859-1"));
            String var26 = decodeUtf8(var15).intern();
            switch (var10001) {
               case 0:
                  var14[var12++] = var26;
                  if ((var17 += var10) >= var13) {
                     STRINGS = var14;
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long var2 = -8681018460799195877L;
                     byte[] var4 = var0.doFinal(
                        new byte[]{
                           (byte)(var2 >>> 56),
                           (byte)(var2 >>> 48),
                           (byte)(var2 >>> 40),
                           (byte)(var2 >>> 32),
                           (byte)(var2 >>> 24),
                           (byte)(var2 >>> 16),
                           (byte)(var2 >>> 8),
                           (byte)var2
                        }
                     );
                     long var30 = (var4[0] & 255L) << 56
                        | (var4[1] & 255L) << 48
                        | (var4[2] & 255L) << 40
                        | (var4[3] & 255L) << 32
                        | (var4[4] & 255L) << 24
                        | (var4[5] & 255L) << 16
                        | (var4[6] & 255L) << 8
                        | var4[7] & 255L;
                     var10001 = -1;
                     TIME_SCALE = var30;
                     break label37;
                  }

                  var10 = var11.charAt(var17);
                  break;
               default:
                  var14[var12++] = var26;
                  if ((var17 += var10) < var13) {
                     var10 = var11.charAt(var17);
                     continue label37;
                  }

                  var11 = "\u008a\u00fbT\u00b4[\u00d1k\u001e\u0093\u009c\u0017\u00d8\u00b4~R\u00dd\u0010Q\u00bc\u00f3+X/\u001f\u00a3<^E\u00ca\u00aeu\u00a8\u00f6";
                  var13 = "\u008a\u00fbT\u00b4[\u00d1k\u001e\u0093\u009c\u0017\u00d8\u00b4~R\u00dd\u0010Q\u00bc\u00f3+X/\u001f\u00a3<^E\u00ca\u00aeu\u00a8\u00f6"
                     .length();
                  var10 = 16;
                  var17 = -1;
            }

            var18 = var11.substring(++var17, var17 + var10);
            var10001 = 0;
         }
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException rethrow(JSONException exception) {
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
