package club.evon.util;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent.ShowText;
import net.minecraft.util.Formatting;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.CustomClickEvent;




public final class ChatUtil {
   // Not final: rebuilt by refreshPrefix() so the tag follows ".clientname".
   private static String PREFIX;
   private static final String ERROR_PREFIX;
   private static boolean enabled;
   private static final long KEY = KeyedRandom.create(-7331911096442181225L, 478237071930209408L, MethodHandles.lookup().lookupClass()).next(154066742979624L);

   /**
    * Rebuilds the chat tag from the current client name.
    *
    * <p>The tag was a fixed decrypted "[Evon]" string; it is now derived from
    * {@link EvonClient#getClientName()} so ".clientname" is reflected immediately. The colour
    * codes reproduce the original table entry exactly: gray '[', white bold name, gray '] ', reset.
    */
   public static void refreshPrefix() {
      PREFIX = Formatting.GRAY + "[" + Formatting.WHITE + Formatting.BOLD
            + EvonClient.getClientName() + Formatting.GRAY + "] " + Formatting.RESET;
   }

   public static void sendMessage(Object message) {
      sendText(Text.literal(PREFIX + message.toString()));
   }

   public static void sendText(Text message) {
      long longValue = KEY ^ 43856315044244L;
      String[] strings = PlayerUtil.getGuard();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (strings != null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (tmp.world != null && message != null) {
         ClientContext.MINECRAFT_CLIENT.inGameHud.getChatHud().addMessage(message);
      }
   }

   public static void sendRawMessage(Object message) {
      sendText(Text.literal(message.toString()));
   }

   public static void sendRawText(Text message) {
      long longValue = KEY ^ 46761152080714L;
      String[] strings = PlayerUtil.getGuard();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (strings != null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (tmp.world != null && message != null) {
         ClientContext.MINECRAFT_CLIENT.inGameHud.getChatHud().addMessage(message);
      }
   }

   public static void sendChatMessage(String message) {
      long longValue = KEY ^ 131210056239932L;
      String[] strings = PlayerUtil.getGuard();
      ClientPlayerEntity tmp = ClientContext.MINECRAFT_CLIENT.player;
      if (strings != null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.player;
      }

      tmp.networkHandler.sendChatMessage(message);
   }

   public static void sendCommand(String message) {
      long longValue = KEY ^ 4874773408076L;
      String[] strings = PlayerUtil.getGuard();
      ClientPlayerEntity tmp = ClientContext.MINECRAFT_CLIENT.player;
      if (strings != null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.player;
      }

      tmp.networkHandler.sendChatCommand(message);
   }

   public static void sendClickableMessage(String message, Runnable action) {
      MutableText setStyle = Text.literal(PREFIX + message).setStyle(Style.EMPTY.withClickEvent(new CustomClickEvent(action)));
      ClientContext.MINECRAFT_CLIENT.inGameHud.getChatHud().addMessage(setStyle);
   }

   public static void sendRawClickableMessage(String message, Runnable action) {
      MutableText setStyle = Text.literal(message).setStyle(Style.EMPTY.withClickEvent(new CustomClickEvent(action)));
      ClientContext.MINECRAFT_CLIENT.inGameHud.getChatHud().addMessage(setStyle);
   }

   public static void sendHoverMessage(String message, Text hoveredText) {
      MutableText setStyle = Text.literal(PREFIX + message).setStyle(Style.EMPTY.withHoverEvent(new ShowText(hoveredText)));
      ClientContext.MINECRAFT_CLIENT.inGameHud.getChatHud().addMessage(setStyle);
   }

   public static void sendError(String message) {
      ClientContext.MINECRAFT_CLIENT.inGameHud.getChatHud().addMessage(Text.of(ERROR_PREFIX + message));
   }

   static {
      try {
      long var1 = KEY ^ 51684202199583L;
      setEnabled(true);
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var0 = new String[4];
      int var8 = 0;
      String var7 = "\\\u008d\u0080\u00f9:^{.\ba\u00caT;=\\\u00a7\u00db";
      int var9 = "\\\u008d\u0080\u00f9:^{.\ba\u00caT;=\\\u00a7\u00db".length();
      char var6 = '\b';
      int var20 = -1;

      label28:
      while (true) {
         String var21 = var7.substring(++var20, var20 + var6);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var3.doFinal(var21.getBytes("ISO-8859-1"));
            String var31 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var0[var8++] = var31;
                  if ((var20 += var6) >= var9) {
                     String var23 = String.valueOf(Formatting.GRAY);
                     String var28 = String.valueOf(Formatting.WHITE);
                     String var34 = String.valueOf(Formatting.BOLD);
                     String var36 = String.valueOf(Formatting.GRAY);
                     String var11 = String.valueOf(Formatting.RESET);
                     String var12 = var36;
                     String var13 = var34;
                     String var14 = var28;
                     PREFIX = var23 + "[" + var14 + var13 + var0[1] + var12 + var0[2] + var11;
                     String var24 = String.valueOf(Formatting.GRAY);
                     String var29 = String.valueOf(Formatting.RED);
                     String var35 = String.valueOf(Formatting.BOLD);
                     String var37 = String.valueOf(Formatting.GRAY);
                     String var15 = String.valueOf(Formatting.RESET);
                     String var16 = var37;
                     String var17 = var35;
                     String var18 = var29;
                     ERROR_PREFIX = var24 + "[" + var18 + var17 + var0[0] + var16 + var0[3] + var15;
                     refreshPrefix();
                     break label28;
                  }

                  var6 = var7.charAt(var20);
                  break;
               default:
                  var0[var8++] = var31;
                  if ((var20 += var6) < var9) {
                     var6 = var7.charAt(var20);
                     continue label28;
                  }

                  var7 = "\u00c5f>T\u00f9\u008a\u00cc;\b\u00c5f>T\u00f9\u008a\u00cc;";
                  var9 = "\u00c5f>T\u00f9\u008a\u00cc;\b\u00c5f>T\u00f9\u008a\u00cc;".length();
                  var6 = '\b';
                  var20 = -1;
            }

            var21 = var7.substring(++var20, var20 + var6);
            var10001 = 0;
         }
      }
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   public static void setEnabled(boolean flag) {
      enabled = flag;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
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
