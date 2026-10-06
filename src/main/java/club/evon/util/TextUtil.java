package club.evon.util;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import club.evon.ClientContext;
import club.evon.util.math.RandomUtil;




public final class TextUtil {
   private static final long KEY = KeyedRandom.create(-1703171329067473756L, 1507569642532310724L, MethodHandles.lookup().lookupClass()).next(138685410739800L);
   private static final String[] STRINGS;

   public static boolean isNumeric(CharSequence cs) {
      long longValue = KEY ^ 17556369194230L;
      int[] values = RandomUtil.getStateArray();
      CharSequence tmp = cs;
      if (values == null) {
         if (cs == null) {
            return true;
         }

         tmp = cs;
      }

      int value;
      int value2 = value = tmp.length();
      if (values != null) {
         return value2 != 0;
      }

      if (value2 != 0) {
         int index = 0;

         while (index < value) {
            boolean digit = Character.isDigit(cs.charAt(index));
            if (values != null) {
               return digit;
            }

            if (values != null) {
               return digit;
            }

            if (!digit) {
               return false;
            }

            index++;
            if (values != null) {
               break;
            }
         }
      }

      return true;
   }

   public static String toDisplayName(String input) {
      long longValue = KEY ^ 29087731387179L;
      String[] strings = input.split(STRINGS[1]);
      if (strings.length == 1) {
         return capitalize(input);
      }

      StringBuilder builder = new StringBuilder();

      for (String text : strings) {
         builder.append(capitalize(text)).append(' ');
      }

      return builder.toString().trim();
   }

   public static String randomString(int length) {
      long longValue = KEY ^ 97188975899108L;
      int[] values = RandomUtil.getStateArray();
      String[] strings = STRINGS;
      String text = strings[2];
      StringBuilder builder = new StringBuilder();
      StringBuilder stringBuilder = null;
      int index = 0;
      int[] values2 = values;

      while (true) {
         if (index < length) {
            stringBuilder = builder.append(RandomUtil.randomInt(0, STRINGS[0].length()));
            if (values2 != null) {
               break;
            }

            index++;
            if (values2 == null) {
               continue;
            }
         }

         stringBuilder = builder;
         break;
      }

      return stringBuilder.toString();
   }

   public static String getClipboard() {
      // Minecraft sets java.awt.headless=true (net.minecraft.client.main.Main), so the AWT
      // clipboard always throws HeadlessException. Use the GLFW clipboard the game itself uses.
      MinecraftClient client = ClientContext.MINECRAFT_CLIENT;
      return client == null || client.keyboard == null ? null : client.keyboard.getClipboard();
   }

   public static void setClipboard(String text) {
      // See getClipboard(): GLFW, not AWT.
      MinecraftClient client = ClientContext.MINECRAFT_CLIENT;
      if (client != null && client.keyboard != null) {
         client.keyboard.setClipboard(text);
      }
   }

   private static String capitalize(String word) {
      return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
   }

   public static String toLegacyString(Text text) {
      StringBuilder builder = new StringBuilder();
      text.visit((var1x, string) -> {
         long longValue = KEY ^ 42858589549385L;
         int[] values = RandomUtil.getStateArray();
         if (values == null) {
            if (var1x.getColor() != null) {
               builder.append(Formatting.byName(var1x.getColor().getName()));
            }

            builder.append(string);
         }

         return Optional.empty();
      }, Style.EMPTY);
      return builder.toString();
   }

   static {
      try {
      long var0 = KEY ^ 137902378534532L;
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
      String var6 = "y\u008bTn\u0093\u00ba\u001fs:\u0084\u00f7}}\u00ed\u0097\u0094\u008b\n}(\u0003\u0005\u007f\u000f\u00d6\u00e7<A\u00aa\u001bD[\u0096\u00cd7\u00cc\u00b6\u00d1\u0091z\u0011\u00050\u00fet\u0080\u00a0{\u00ea \u00da@\u00e1\u0083\u009d\u00db\u00b4\u0085\u00d47\u00b1\u001f\u000b|\b.i\u0006W\u00c6\u00016\u00cb@y\u008bTn\u0093\u00ba\u001fs:\u0084\u00f7}}\u00ed\u0097\u0094\u008b\n}(\u0003\u0005\u007f\u000f\u00d6\u00e7<A\u00aa\u001bD[\u0096\u00cd7\u00cc\u00b6\u00d1\u0091z\u0011\u00050\u00fet\u0080\u00a0{\u00ea \u00da@\u00e1\u0083\u009d\u00db\u00b4\u0085\u00d47\u00b1\u001f\u000b|";
      int var8 = "y\u008bTn\u0093\u00ba\u001fs:\u0084\u00f7}}\u00ed\u0097\u0094\u008b\n}(\u0003\u0005\u007f\u000f\u00d6\u00e7<A\u00aa\u001bD[\u0096\u00cd7\u00cc\u00b6\u00d1\u0091z\u0011\u00050\u00fet\u0080\u00a0{\u00ea \u00da@\u00e1\u0083\u009d\u00db\u00b4\u0085\u00d47\u00b1\u001f\u000b|\b.i\u0006W\u00c6\u00016\u00cb@y\u008bTn\u0093\u00ba\u001fs:\u0084\u00f7}}\u00ed\u0097\u0094\u008b\n}(\u0003\u0005\u007f\u000f\u00d6\u00e7<A\u00aa\u001bD[\u0096\u00cd7\u00cc\u00b6\u00d1\u0091z\u0011\u00050\u00fet\u0080\u00a0{\u00ea \u00da@\u00e1\u0083\u009d\u00db\u00b4\u0085\u00d47\u00b1\u001f\u000b|"
         .length();
      char var5 = '@';
      int var4 = -1;

      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var13 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var13;
         if ((var4 += var5) >= var8) {
            STRINGS = var9;
            break;
         }

         var5 = var6.charAt(var4);
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static Exception ignoreException(Exception exception) {
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
