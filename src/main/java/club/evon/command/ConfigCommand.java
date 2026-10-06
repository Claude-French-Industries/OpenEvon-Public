package club.evon.command;
import com.google.common.io.Files;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent.ShowText;
import net.minecraft.util.Formatting;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.config.Config;
import club.evon.data.CustomClickEvent;
import club.evon.data.UserRank;
import club.evon.util.ChatUtil;
import club.evon.util.FileDialogUtil;
import club.evon.util.KeyedRandom;




public final class ConfigCommand extends Command {
   private static final long STRING_SEED = KeyedRandom.create(6564769963830731725L, -3099219660782424080L, MethodHandles.lookup().lookupClass()).next(178156994320370L);
   private static final String[] STRINGS_TABLE;

   public ConfigCommand() {
      super((STRINGS_TABLE)[13], (STRINGS_TABLE)[31], (STRINGS_TABLE)[24]);

      String[] strings = STRINGS_TABLE;
      }

   @Override
   public boolean execute(String[] arguments) {
      int value;
      int value2;
      long longValue = STRING_SEED ^ 93374036999829L;
      String toLowerCase = arguments[0].toLowerCase();
      value2 = PanicCommand.getDefaultState();
      byte byteValue = -1;
      value = value2;
      value2 = toLowerCase.hashCode();
      label249:
      if (value != 0) {
         switch (value2) {
            case -1335458389:
                value2 = toLowerCase.equals(STRINGS_TABLE[8]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 0;
               if (value != 0) {
                  break;
               }
            case -934610812:
                value2 = toLowerCase.equals(STRINGS_TABLE[34]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 1;
               if (value != 0) {
                  break;
               }
            case 3143036:
                value2 = toLowerCase.equals(STRINGS_TABLE[6]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 2;
               if (value != 0) {
                  break;
               }
            case -1268966290:
                value2 = toLowerCase.equals(STRINGS_TABLE[20]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 3;
               if (value != 0) {
                  break;
               }
            case -962584979:
                value2 = toLowerCase.equals(STRINGS_TABLE[2]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 4;
               if (value != 0) {
                  break;
               }
            case 3433509:
                value2 = toLowerCase.equals(STRINGS_TABLE[27]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 5;
               if (value != 0) {
                  break;
               }
            case 3322014:
                value2 = toLowerCase.equals(STRINGS_TABLE[28]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 6;
               if (value != 0) {
                  break;
               }
            case 3522941:
                value2 = toLowerCase.equals(STRINGS_TABLE[17]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 7;
               if (value != 0) {
                  break;
               }
            case 3327206:
                value2 = toLowerCase.equals(STRINGS_TABLE[18]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 8;
               if (value != 0) {
                  break;
               }
            case -358707178:
                value2 = toLowerCase.equals(STRINGS_TABLE[21]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 == 0) {
                  break;
               }

               byteValue = 9;
               if (value != 0) {
                  break;
               }
            case 1282376349:
                value2 = toLowerCase.equals(STRINGS_TABLE[0]) ? 1 : 0;
               if (value == 0) {
                  break label249;
               }

               if (value2 != 0) {
                  byteValue = 10;
               }
         }

         value2 = byteValue;
      }

      if (value != 0) {
         switch (value2) {
            case 0:
            case 1:
               label186: {
                  Config config = EvonClient.getInstance().getConfigManager().getConfig(arguments[1]);
                  if (value != 0) {
                     if (config == null) {
                        break label186;
                     }

                     EvonClient.getInstance().getConfigManager().deleteConfig(arguments[1]);
                     ChatUtil.sendMessage(STRINGS_TABLE[12]);
                  }

                  if (value != 0) {
                     return true;
                  }
               }

               ChatUtil.sendError(STRINGS_TABLE[29] + arguments[1] + STRINGS_TABLE[7]);
               return true;
            case 2:
            case 3:
            case 4:
            case 5:
               try {
                  Desktop.getDesktop().open(EvonClient.getInstance().getConfigManager().getConfigDirectory());
               } catch (Exception exception) {
                  String[] strings = STRINGS_TABLE;
                  ChatUtil.sendError(strings[4]);
               }

               return true;
            case 6:
               Map<String, Config> configs = EvonClient.getInstance().getConfigManager().getConfigs();
               boolean empty = configs.isEmpty();
               if (value != 0) {
                  if (empty) {
                     ChatUtil.sendError(STRINGS_TABLE[10]);
                     return true;
                  }

                  String valueOf = String.valueOf(Formatting.GRAY);
                  String valueOf2 = String.valueOf(Formatting.AQUA);
                  String valueOf3 = String.valueOf(Formatting.BOLD);
                  String valueOf4 = String.valueOf(Formatting.RESET);
                  String valueOf5 = String.valueOf(Formatting.GRAY);
                  String text = valueOf4;
                  String text2 = valueOf3;
                  String text3 = valueOf2;
                  String[] strings2 = STRINGS_TABLE;
                  ChatUtil.sendRawMessage(valueOf + strings2[30] + text3 + text2 + strings2[3] + text + valueOf5 + strings2[5]);
                  configs.forEach(
                     (string, config2) -> {
                        config2.loadMetadata();
                        String var10000x = config2.getName();
                        String[] var4x = STRINGS_TABLE;
                        MutableText var2x = Text.literal(var10000x + var4x[32])
                           .append(Text.literal(config2.getAuthor()).withColor(UserRank.getTextColorByLevel(config2.getLevel()).getRgb()))
                           .append(var4x[9] + config2.getDate());
                        MutableText setStyle = var2x.copy().setStyle(Style.EMPTY.withClickEvent(new CustomClickEvent(() -> {
                           // Binds live in the config and are restored with it: loading a config
                           // without its binds and then autosaving would overwrite them.
                           EvonClient.getInstance().getConfigManager().loadConfig(string, true);
                           ChatUtil.sendMessage(STRINGS_TABLE[25]);
                        })).withHoverEvent(new ShowText(Text.literal(var4x[35]))));
                        ClientContext.MINECRAFT_CLIENT.inGameHud.getChatHud().addMessage(setStyle);
                     }
                  );
                  empty = true;
               }

               return empty;
            case 7:
               EvonClient.getInstance().getConfigManager().saveConfig(arguments[1]);
               String[] strings3 = STRINGS_TABLE;
               ChatUtil.sendMessage(strings3[33]);
               return true;
            case 8:
               if (arguments.length == 1) {
                  label175: {
                     String[] strings4 = STRINGS_TABLE;
                     String openFileDialog = FileDialogUtil.openFileDialog(strings4[15], null, null, strings4[36]);
                     String text4 = openFileDialog;
                     if (value != 0) {
                        if (openFileDialog != null) {
                           String toString = Paths.get(openFileDialog).getFileName().toString();
                           strings4 = STRINGS_TABLE;
                           String replaceFirst = toString.replaceFirst(strings4[1], "");
                           File configDirectory = EvonClient.getInstance().getConfigManager().getConfigDirectory();
                           Path get = Paths.get(openFileDialog);
                           Path resolve = configDirectory.toPath().resolve(toString);

                           try {
                              if (value != 0) {
                                 if (!resolve.toFile().exists()) {
                                    Files.copy(get.toFile(), resolve.toFile());
                                 }

                                 EvonClient.getInstance().getConfigManager().loadConfig(replaceFirst, true);
                                 strings4 = STRINGS_TABLE;
                                 ChatUtil.sendMessage(strings4[19]);
                              }
                           } catch (IOException iOException) {
                              strings4 = STRINGS_TABLE;
                              ChatUtil.sendError(strings4[26]);
                              iOException.printStackTrace();
                           }

                           if (value != 0) {
                              break label175;
                           }
                        }

                        text4 = STRINGS_TABLE[14];
                     }

                     ChatUtil.sendMessage(text4);
                  }

                  if (value != 0) {
                     return true;
                  }
               }

               label165: {
                  Config config3 = EvonClient.getInstance().getConfigManager().getConfig(arguments[1]);
                  if (value != 0) {
                     if (config3 == null) {
                        break label165;
                     }

                     EvonClient.getInstance().getConfigManager().loadConfig(arguments[1], true);
                     ChatUtil.sendMessage(STRINGS_TABLE[16]);
                  }

                  if (value != 0) {
                     return true;
                  }
               }

               ChatUtil.sendError(STRINGS_TABLE[23] + arguments[1] + STRINGS_TABLE[11]);
               return true;
            case 9:
            case 10:
               EvonClient.getInstance().getConfigManager().getConfigs().values().forEach(config4 -> EvonClient.getInstance().getConfigManager().deleteConfig(config4.getName()));
               ChatUtil.sendMessage(STRINGS_TABLE[22]);
               return true;
            default:
               value2 = 0;
         }
      }

      return value2 != 0;
   }

   @Override
   public ArrayList<String> getSuggestions() {
      return new ConfigCommandSuggestions(this);
   }

   static {
      try {
      long var0 = STRING_SEED ^ 713914000677L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[37];
      int var7 = 0;
      String var6 = "\u0012\u00abk\u00c3\u0006\u00c5\u00b0\u0089\u00ce\u00ff\u0013\u00fe\u00cd\u00f0\u0018\u0012\b\u00bf\u0005\u0019T\u00a8\u00b2\u00f8H\u0010-\t\u00ad6iA3\u00ff\u00c4\u00e4\u008d\u00e5y\u009bI\u00b6\b\u00f9\u00f7J\u00d9e\u00aa\u00cd[ \u00a62@K\u0010[\u0003\u00a3\u009a\u00c1\ft\u00f4cx\u0006\u00ff\u00bd\u00ed\u001e\u00ae\u0093\u00c6\u0088\u0082`d\u00a9\u000b\u0018~{\u0018\u00d3U*\u00c7{r@\u00f7\u008a4>%,E_\u00a9\u00da\u00bb\u00a5\u0001\b\u0086v\u00b6\bT\u00cc\u0081[%;g\u00ea\u0010P\u00e6\u0082\u00dfr3\u00f3(\u00f6d\u009bN\\\u0091\u0019\u00f3\bc\u00ee\u00bc\u00b1\u00ed\u00a0'\u00b7\b\u0092\nl\u00d6\u00cd&R\u00f6\u0018\u00e8?;\u0014<\u000eF\u0000+N\u0002k\u0007\u00ea\u00adl\u00a6\u00f6&\u00a1\u008b\u000eK&\u0010P\u00e6\u0082\u00dfr3\u00f3(\u00f6d\u009bN\\\u0091\u0019\u00f3\u0010cks=\u0083\u00cc\u00fbc\u00d9\u0097\u00b1=\u008a\u00d2\u00a9\u00e4\b0\u00a4\u00ac\u00a9(\u0017\u0085(\u0018\u00e8?;\u0014<\u000eF\u0000yx\u0015\u0085s\u00ab%!\u008ba\u00ec\u00e6\u00a4\u00a0L\u009d(D\u000e\u00d6\u00012\u00f0#[\u00ff\u0016\u00b2\u00cf\u0099Y\u00aevO\u0084\u00b3XZ\u009dD~\u00d8TV\u0016\u00b7K\u0001\u00a1G\u008d\u00ef\u009f\u00c3\u0019\u001f\u00d1\u0010U\u001a\u00f4t\u009d+a\u00ab\u0094\u00e4Ew\u0080=Tc\bJ\u0095]\u0015\u00b3\u00a8\u001bW\b\u0017|\u00b7\u0097\u00fa\u008c\u0005\u00d0\u0010U\u001a\u00f4t\u009d+a\u00ab\u0094\u00e4Ew\u0080=Tc\b\u0003\u0005\u0015^1$\u00d8\f\u00107\u0094\u0098g\f&2a\u009a\u0098\u00a0\u001e\u008d\u00c8n\u00e2\u0018cks=\u0083\u00cc\u00fbc\u0099wl\u00f4\u00e7X0#\u0012\u00f8\u00c4\u00c6J\u00e1{\u00ec\b\u00a3\u009a\u00ecu\u00a6\u00a9\u00cd\u0098(\u00a6p\u00ba0X:\u00ffR\u00a2\u00cbW\u0014]\u008f\u009cA5#S\n\u00b7\u0098\u00ef\u00e3F2\u00f1\u0010f\u00a4\u008c\u001d\u0000%\u00ad\u00b7\u00edc\u00ce\u00a5\u0010U\u001a\u00f4t\u009d+a\u00ab\u00a5\u00b6\u009b\u00dd\u0084c\u00d7u \u00a62@K\u0010[\u0003\u00a3V\u007f\"\u0083\u00de\u0002\u00e8\u0013Ddi\u00c0\u0007x\u001a\u001a\u00ac\u001dk\u0000M\u00a0\u00dd\u00ac\b\u00b4\u00a3\u00d9\u00c4\u0005\u00fb7\u00ac\b\u00ed\\\u0094o\u009b\u00be\u0016\u00d3\b\u00a3\u009a\u00ecu\u00a6\u00a9\u00cd\u0098\u0018p\u0018\u00bba\u00f3\u0010\u0006\u00d4Z&\u00e8[\u00c2,A\u00da\u008d\b\u0000\u0093$\u00cfm\u00c3\u0010\u00c8\u00cf\u00fe\tL\u001d\u009e\u00d3)rQ\u00fe:@\u00a5;\u0010WE\u008c\u00d2\u00e3\u0005z\u0097\u00b0\u0005U\u008d\u00e9\u00dc\u001cX\u00103O\u00a2\u0015\u00d9\u00cc\u0003\u00d5\u00e46:Ge\u00d9<&\b\b\u000e\u00bag Z|\u00f2";
      int var8 = "\u0012\u00abk\u00c3\u0006\u00c5\u00b0\u0089\u00ce\u00ff\u0013\u00fe\u00cd\u00f0\u0018\u0012\b\u00bf\u0005\u0019T\u00a8\u00b2\u00f8H\u0010-\t\u00ad6iA3\u00ff\u00c4\u00e4\u008d\u00e5y\u009bI\u00b6\b\u00f9\u00f7J\u00d9e\u00aa\u00cd[ \u00a62@K\u0010[\u0003\u00a3\u009a\u00c1\ft\u00f4cx\u0006\u00ff\u00bd\u00ed\u001e\u00ae\u0093\u00c6\u0088\u0082`d\u00a9\u000b\u0018~{\u0018\u00d3U*\u00c7{r@\u00f7\u008a4>%,E_\u00a9\u00da\u00bb\u00a5\u0001\b\u0086v\u00b6\bT\u00cc\u0081[%;g\u00ea\u0010P\u00e6\u0082\u00dfr3\u00f3(\u00f6d\u009bN\\\u0091\u0019\u00f3\bc\u00ee\u00bc\u00b1\u00ed\u00a0'\u00b7\b\u0092\nl\u00d6\u00cd&R\u00f6\u0018\u00e8?;\u0014<\u000eF\u0000+N\u0002k\u0007\u00ea\u00adl\u00a6\u00f6&\u00a1\u008b\u000eK&\u0010P\u00e6\u0082\u00dfr3\u00f3(\u00f6d\u009bN\\\u0091\u0019\u00f3\u0010cks=\u0083\u00cc\u00fbc\u00d9\u0097\u00b1=\u008a\u00d2\u00a9\u00e4\b0\u00a4\u00ac\u00a9(\u0017\u0085(\u0018\u00e8?;\u0014<\u000eF\u0000yx\u0015\u0085s\u00ab%!\u008ba\u00ec\u00e6\u00a4\u00a0L\u009d(D\u000e\u00d6\u00012\u00f0#[\u00ff\u0016\u00b2\u00cf\u0099Y\u00aevO\u0084\u00b3XZ\u009dD~\u00d8TV\u0016\u00b7K\u0001\u00a1G\u008d\u00ef\u009f\u00c3\u0019\u001f\u00d1\u0010U\u001a\u00f4t\u009d+a\u00ab\u0094\u00e4Ew\u0080=Tc\bJ\u0095]\u0015\u00b3\u00a8\u001bW\b\u0017|\u00b7\u0097\u00fa\u008c\u0005\u00d0\u0010U\u001a\u00f4t\u009d+a\u00ab\u0094\u00e4Ew\u0080=Tc\b\u0003\u0005\u0015^1$\u00d8\f\u00107\u0094\u0098g\f&2a\u009a\u0098\u00a0\u001e\u008d\u00c8n\u00e2\u0018cks=\u0083\u00cc\u00fbc\u0099wl\u00f4\u00e7X0#\u0012\u00f8\u00c4\u00c6J\u00e1{\u00ec\b\u00a3\u009a\u00ecu\u00a6\u00a9\u00cd\u0098(\u00a6p\u00ba0X:\u00ffR\u00a2\u00cbW\u0014]\u008f\u009cA5#S\n\u00b7\u0098\u00ef\u00e3F2\u00f1\u0010f\u00a4\u008c\u001d\u0000%\u00ad\u00b7\u00edc\u00ce\u00a5\u0010U\u001a\u00f4t\u009d+a\u00ab\u00a5\u00b6\u009b\u00dd\u0084c\u00d7u \u00a62@K\u0010[\u0003\u00a3V\u007f\"\u0083\u00de\u0002\u00e8\u0013Ddi\u00c0\u0007x\u001a\u001a\u00ac\u001dk\u0000M\u00a0\u00dd\u00ac\b\u00b4\u00a3\u00d9\u00c4\u0005\u00fb7\u00ac\b\u00ed\\\u0094o\u009b\u00be\u0016\u00d3\b\u00a3\u009a\u00ecu\u00a6\u00a9\u00cd\u0098\u0018p\u0018\u00bba\u00f3\u0010\u0006\u00d4Z&\u00e8[\u00c2,A\u00da\u008d\b\u0000\u0093$\u00cfm\u00c3\u0010\u00c8\u00cf\u00fe\tL\u001d\u009e\u00d3)rQ\u00fe:@\u00a5;\u0010WE\u008c\u00d2\u00e3\u0005z\u0097\u00b0\u0005U\u008d\u00e9\u00dc\u001cX\u00103O\u00a2\u0015\u00d9\u00cc\u0003\u00d5\u00e46:Ge\u00d9<&\b\b\u000e\u00bag Z|\u00f2"
         .length();
      char var5 = 16;
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decryptString(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var19;
                  if ((var12 += var5) >= var8) {
                     STRINGS_TABLE = var9;
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

                  var6 = "\u0094\u008a\u00edQ\u00bf\u00f5\u00c6\u00d8$\u0005\u007f\u0003\u000eq\u008eY\u008f:\u00f0\u0091\u0088^\u000f\u00c1\b\u001f\u0086\u00de\u00f5J\u00d9\u00a5)";
                  var8 = "\u0094\u008a\u00edQ\u00bf\u00f5\u00c6\u00d8$\u0005\u007f\u0003\u000eq\u008eY\u008f:\u00f0\u0091\u0088^\u000f\u00c1\b\u001f\u0086\u00de\u00f5J\u00d9\u00a5)"
                     .length();
                  var5 = 24;
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

   private static Exception passThrough(Exception exception) {
      return exception;
   }

   private static String decryptString(byte[] data) {
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
