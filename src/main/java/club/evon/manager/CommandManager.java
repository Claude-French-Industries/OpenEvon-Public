package club.evon.manager;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap.Builder;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Collection;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.Formatting;
import club.evon.command.Command;
import club.evon.event.EventBus;
import club.evon.event.SendChatMessageEvent;
import club.evon.event.Subscribe;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public final class CommandManager {
   private final ImmutableClassToInstanceMap<Command> commandMap;
   private final String prefix;
   private static final long KEY = KeyedRandom.create(-3889654761058124601L, 3318414317477056336L, MethodHandles.lookup().lookupClass()).next(55075932119549L);
   private static final String[] STRINGS;

   public CommandManager(Command... commands) {
      super();

      long longValue = KEY ^ 48535996065510L;
      this.prefix = ".";
      Builder builder = new Builder();

      for (Command command : commands) {
         builder.put(command.getClass(), command);
      }

      this.commandMap = builder.build();
      EventBus.register(this);
      }

   public Collection<Command> getCommands() {
      return this.commandMap.values();
   }

   public <T extends Command> T getCommand(Class<T> clazz) {
      return (T)this.commandMap.getInstance(clazz);
   }

   @Subscribe
   private void onSendChatMessage(SendChatMessageEvent event) {
      long longValue = KEY ^ 10444347318530L;
      boolean disabled = Command.isDisabled();
      String message = event.getMessage();
      boolean flag = disabled;
      String text = message;
      String tmp = ".";
      if (!flag) {
         if (!message.startsWith(".")) {
            return;
         }

         text = message.substring(1);
         tmp = " ";
      }

      String[] strings = text.split(tmp);

      for (Command command : this.getCommands()) {
         if (flag) {
            return;
         }

         label56: {
            label55: {
               disabled = command.getName().equalsIgnoreCase(strings[0]);
               if (!flag) {
                  if (disabled) {
                     break label55;
                  }

                  disabled = command.getAliases().stream().anyMatch(var1x -> var1x.equalsIgnoreCase(strings[0]));
               }

               if (!disabled) {
                  break label56;
               }
            }

            try {
               label47: {
                  String[] strings2 = Arrays.copyOfRange(strings, 1, strings.length);
                  Command command2 = command;
                  if (!flag) {
                     if (command.execute(strings2)) {
                        break label47;
                     }

                     String valueOf = String.valueOf(Formatting.GRAY);
                     tmp = String.valueOf(Formatting.RED);
                     String valueOf2 = String.valueOf(Formatting.BOLD);
                     String valueOf3 = String.valueOf(Formatting.RESET);
                     String valueOf4 = String.valueOf(Formatting.GRAY);
                     String text2 = valueOf3;
                     String text3 = valueOf2;
                     String text4 = tmp;
                     String[] strings3 = STRINGS;
                     ChatUtil.sendRawMessage(valueOf + strings3[0] + text4 + text3 + strings3[1] + text2 + valueOf4 + strings3[6]);
                     command2 = command;
                  }

                  command2.getSuggestions().forEach(ChatUtil::sendRawMessage);
               }
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
               String valueOf5 = String.valueOf(Formatting.GRAY);
               tmp = String.valueOf(Formatting.RED);
               String valueOf6 = String.valueOf(Formatting.BOLD);
               String valueOf7 = String.valueOf(Formatting.RESET);
               String valueOf8 = String.valueOf(Formatting.GRAY);
               String text5 = valueOf7;
               String text6 = valueOf6;
               String text7 = tmp;
               String[] strings4 = STRINGS;
               ChatUtil.sendRawMessage(valueOf5 + strings4[3] + text7 + text6 + strings4[4] + text5 + valueOf8 + strings4[2]);
               command.getSuggestions().forEach(ChatUtil::sendRawMessage);
            } catch (Exception exception) {
               ChatUtil.sendError(STRINGS[5] + exception);
            }
         }

         if (flag) {
            break;
         }
      }

      event.cancel();
   }

   public String getPrefix() {
      return ".";
   }

   static {
      try {
      long var0 = KEY ^ 95983809582260L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[7];
      int var7 = 0;
      String var6 = "u7\u0081v:\u00bd\u009e{B\u00f6\u00f8\u00a5;\u00be\u008d\u00ea\u0097\u00f1\u00cd\u00a6I\u009117\u0010\u0017\u00b7\u009c{\u00b7\u0098\u009a\u00d6\"\u008b\u0080\u00f5\u00a0\u00c5\u0007#\u0018\u0001KR\u0019\u0086\n\u00eaX\u001a\u00a9\u0012\u0085\u00e2\u0089V\u0014R)\u00f0=\u00ce-\u0006\\\u0018u7\u0081v:\u00bd\u009e{B\u00f6\u00f8\u00a5;\u00be\u008d\u00ea\u0097\u00f1\u00cd\u00a6I\u009117\u0010\u0017\u00b7\u009c{\u00b7\u0098\u009a\u00d6\"\u008b\u0080\u00f5\u00a0\u00c5\u0007#";
      int var8 = "u7\u0081v:\u00bd\u009e{B\u00f6\u00f8\u00a5;\u00be\u008d\u00ea\u0097\u00f1\u00cd\u00a6I\u009117\u0010\u0017\u00b7\u009c{\u00b7\u0098\u009a\u00d6\"\u008b\u0080\u00f5\u00a0\u00c5\u0007#\u0018\u0001KR\u0019\u0086\n\u00eaX\u001a\u00a9\u0012\u0085\u00e2\u0089V\u0014R)\u00f0=\u00ce-\u0006\\\u0018u7\u0081v:\u00bd\u009e{B\u00f6\u00f8\u00a5;\u00be\u008d\u00ea\u0097\u00f1\u00cd\u00a6I\u009117\u0010\u0017\u00b7\u009c{\u00b7\u0098\u009a\u00d6\"\u008b\u0080\u00f5\u00a0\u00c5\u0007#"
         .length();
      char var5 = 24;
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

                  var6 = "\u00d5\u00b1\u00f6\u00ebn$\u0005\u00a4aI|vV\u00f0\u00b2|{\u00c4\u009b\u0095\u001b\u00e54\u00cc\u001f\u00e8\u0000\u00e3\nU?S\u0018\u0001KR\u0019\u0086\n\u00eaX\u001a\u00a9\u0012\u0085\u00e2\u0089V\u0014R)\u00f0=\u00ce-\u0006\\";
                  var8 = "\u00d5\u00b1\u00f6\u00ebn$\u0005\u00a4aI|vV\u00f0\u00b2|{\u00c4\u009b\u0095\u001b\u00e54\u00cc\u001f\u00e8\u0000\u00e3\nU?S\u0018\u0001KR\u0019\u0086\n\u00eaX\u001a\u00a9\u0012\u0085\u00e2\u0089V\u0014R)\u00f0=\u00ce-\u0006\\"
                     .length();
                  var5 = ' ';
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

   private static ArrayIndexOutOfBoundsException identity(ArrayIndexOutOfBoundsException exception) {
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
