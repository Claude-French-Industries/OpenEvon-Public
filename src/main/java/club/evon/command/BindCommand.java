package club.evon.command;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import org.lwjgl.glfw.GLFW;
import club.evon.EvonClient;
import club.evon.module.BaseModule;
import club.evon.setting.BindMode;
import club.evon.setting.BindSetting;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public final class BindCommand extends Command {
   private static final long STRING_SEED = KeyedRandom.create(-6964764662006183561L, -6732643053938539485L, MethodHandles.lookup().lookupClass()).next(86727719527076L);
   private static final String[] STRINGS_TABLE;

   public BindCommand() {
      super((STRINGS_TABLE)[2], (STRINGS_TABLE)[1], (STRINGS_TABLE)[6]);

      String[] strings = STRINGS_TABLE;
      }

   @Override
   public boolean execute(String[] arguments) {
      int value;
      int value2;
      String text;
      long longValue = STRING_SEED ^ 44146164557483L;
      value2 = PanicCommand.getState();
      String toLowerCase = arguments[0].toLowerCase();
      value = value2;
      int value3 = -1;
      value2 = toLowerCase.hashCode();
      label225:
      if (value == 0) {
         switch (value2) {
            case -840745386:
               value2 = toLowerCase.equals(STRINGS_TABLE[15]) ? 1 : 0;
               if (value != 0) {
                  break label225;
               }

               if (value2 == 0) {
                  break;
               }

               value3 = 0;
               if (value == 0) {
                  break;
               }

               BaseModule.setStateArray(new BaseModule[4]);
            case -1335458389:
               value2 = toLowerCase.equals(STRINGS_TABLE[19]) ? 1 : 0;
               if (value != 0) {
                  break label225;
               }

               if (value2 == 0) {
                  break;
               }

               value3 = 1;
               if (value == 0) {
                  break;
               }
            case -934610812:
               value2 = toLowerCase.equals(STRINGS_TABLE[7]) ? 1 : 0;
               if (value != 0) {
                  break label225;
               }

               if (value2 == 0) {
                  break;
               }

               value3 = 2;
               if (value == 0) {
                  break;
               }
            case 3357091:
               value2 = toLowerCase.equals(STRINGS_TABLE[14]) ? 1 : 0;
               if (value != 0) {
                  break label225;
               }

               if (value2 == 0) {
                  break;
               }

               value3 = 3;
               if (value == 0) {
                  break;
               }
            case 94746189:
               value2 = toLowerCase.equals(STRINGS_TABLE[3]) ? 1 : 0;
               if (value != 0) {
                  break label225;
               }

               if (value2 == 0) {
                  break;
               }

               value3 = 4;
               if (value == 0) {
                  break;
               }
            case 1603572619:
               value2 = toLowerCase.equals(STRINGS_TABLE[18]) ? 1 : 0;
               if (value != 0) {
                  break label225;
               }

               if (value2 == 0) {
                  break;
               }

               value3 = 5;
               if (value == 0) {
                  break;
               }
            case -358707178:
               value2 = toLowerCase.equals(STRINGS_TABLE[4]) ? 1 : 0;
               if (value != 0) {
                  break label225;
               }

               if (value2 == 0) {
                  break;
               }

               value3 = 6;
               if (value == 0) {
                  break;
               }
            case 1282376349:
               value2 = toLowerCase.equals(STRINGS_TABLE[5]) ? 1 : 0;
               if (value != 0) {
                  break label225;
               }

               if (value2 != 0) {
                  value3 = 7;
               }
         }

         value2 = value3;
      }

      switch (value2) {
         case 0:
         case 1:
         case 2:
            label142: {
               BaseModule moduleByName = EvonClient.getInstance().getModuleManager().getModuleByName(arguments[1]);
               BaseModule module = moduleByName;
               if (value == 0) {
                  if (moduleByName == null) {
                     break label142;
                  }

                  moduleByName.getBindSetting().setValue(-1);
                  module = moduleByName;
               }

               ChatUtil.sendMessage(STRINGS_TABLE[0] + module.getName());
               if (value == 0) {
                  return true;
               }
            }

            ChatUtil.sendError(STRINGS_TABLE[8] + arguments[0] + STRINGS_TABLE[12]);
            return true;
         case 3:
            BaseModule moduleByName2 = EvonClient.getInstance().getModuleManager().getModuleByName(arguments[1]);
            BaseModule module2 = moduleByName2;
            if (value == 0) {
               if (moduleByName2 == null) {
                  return true;
               }

               module2 = moduleByName2;
            }

            BindSetting bindSetting = module2.getBindSetting();
            BindMode bindMode = moduleByName2.getBindSetting().getBindMode();
            if (value == 0) {
               bindMode = bindMode == BindMode.HOLD ? BindMode.TOGGLE : BindMode.HOLD;
            }

            bindSetting.setBindMode(bindMode);
            String text2 = arguments[0];
            String toString = moduleByName2.getBindSetting().getBindMode().toString();
            String[] strings = STRINGS_TABLE;
            ChatUtil.sendMessage(strings[9] + text2 + strings[20] + toString);
            return true;
         case 4:
         case 5:
         case 6:
         case 7:
            EvonClient.getInstance().getModuleManager().getModules().forEach(module3 -> module3.getBindSetting().setValue(-1));
            String[] strings2 = STRINGS_TABLE;
            ChatUtil.sendMessage(strings2[13]);
            return true;
         default:
            BaseModule moduleByName3 = EvonClient.getInstance().getModuleManager().getModuleByName(arguments[0]);
            if (moduleByName3 != null) {
               value3 = 32;

               while (value3 <= 348) {
                  value2 = arguments[0].equalsIgnoreCase(STRINGS_TABLE[16]) ? 1 : 0;
                  if (value != 0) {
                     return value2 != 0;
                  }

                  label171: {
                     if (value == 0) {
                        if (value2 != 0) {
                           text = GLFW.glfwGetKeyName(-1, 0);
                           break label171;
                        }

                        value2 = value3;
                     }

                     text = GLFW.glfwGetKeyName(value2, 0);
                  }

                  String text3 = text;
                  if (value == 0) {
                     label162:
                     if (text3 != null) {
                        String text4 = text3;
                        String tmp = arguments[1];
                        if (value == 0) {
                           if (!text3.equalsIgnoreCase(tmp)) {
                              break label162;
                           }

                           moduleByName3.getBindSetting().setValue(value3);
                           text4 = moduleByName3.getName();
                           tmp = arguments[1].toUpperCase();
                        }

                        ChatUtil.sendMessage(STRINGS_TABLE[10] + text4 + STRINGS_TABLE[21] + tmp);
                        if (value == 0) {
                           break;
                        }
                     }

                     value3++;
                  }

                  if (value != 0) {
                     break;
                  }
               }

               if (value == 0) {
                  return true;
               }
            }

            ChatUtil.sendError(STRINGS_TABLE[17] + arguments[0] + STRINGS_TABLE[11]);
            return true;
      }
   }

   @Override
   public ArrayList<String> getSuggestions() {
      return new BindCommandSuggestions(this);
   }

   static {
      try {
      long var0 = STRING_SEED ^ 74070395278866L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[22];
      int var7 = 0;
      String var6 = "h\u00c6*\u009a\u008b\u0018\u00d706\u0017\u00c4\u00db$\u00e2\u00a2\u008c\u00b5\u0017\u00c9\u00b1\u00be\u0006\u00b6U8XrP\u00c6\u008eE\u00ca\u00f4Tm\u00b56\u00ff\u0004N\u008d\u00ac\u00f8Y\u00ddB6\u0010I\u00fc\u009f\u00df\u00cf\u00be\u008b\u0095\u0013\u0096\u00d5\u0093\u000b:\u00f5\u0007\u008a\u001c\u0093`\u00df\u00b9!\u00ac\u0091p\u00a3\nG\u00e6\u00ac\u0087\u0019\b\u00a6\u00a8\u007f?\u00fb\u008e\u00d9`\b\u00d7/\u00c9\u00fb\u00a1\u00845\u00b4\u0010\u00f1\u0081\u00fb\u009dT\u00ab\u00c3]\u00af\u00fc\"7\u00db\u00c8\u00f4\u0011\u0010\u0093\u0099\\\u00a45\u00ad\u008eu\u00d7\u00d8#\u00f6\u0012\u00fd8\u00fb\u0018\u00d8\u0018 \u00f54\u00a2H%\u0016\u00d4E%c\u000f\u00d1\u0011\u0007\u001b1\u00c04{\u00ba\u0093\b&,*\u0016\u00d8\u00ed\u00197\b\u00ebxWC\u009b\u007f\u00c5\u00f0\u0018v\u00e6\u00ea\u00eb\u00fe\u00f6\u00e1*.\u00e6TqlH\u00dbCV{\u0099\u0096|\u00bd\u00acM\bta\u00a0\u0092\u00992'R\u0010'q\u00b3a4\u0006K\u0097\u00bf\u00ee\u0096\u0017\u0007j\b\u00e1\u0010'q\u00b3a4\u0006K\u0097\u00bf\u00ee\u0096\u0017\u0007j\b\u00e1 h\u00c6*\u009a\u008b\u0018\u00d70C$\u00e7\u00d4R+\u00efC\u00dc\u009en\u00ed/\u0015\u00e5\u00d1\u00f0\u0013=G\u00d1\u00c9W\u00f0\b\u00ce]/\u00f4\u00dd\u0087\u00bb\u009a\b\u00ad\u00df|\u0085\u001f\u00d8t\u0003\b\u00bdG\u0013\u00daI\u00c6\u00f6C\b\u00ebxWC\u009b\u007f\u00c5\u00f0\u0010\u008f_\u008ci\u00d5I\u00a8\u00e8\u00d7jjpz\u0096\u0006^\b\u0019\u0019\u00fe\u0091E\u0086\u00d6\u0085";
      int var8 = "h\u00c6*\u009a\u008b\u0018\u00d706\u0017\u00c4\u00db$\u00e2\u00a2\u008c\u00b5\u0017\u00c9\u00b1\u00be\u0006\u00b6U8XrP\u00c6\u008eE\u00ca\u00f4Tm\u00b56\u00ff\u0004N\u008d\u00ac\u00f8Y\u00ddB6\u0010I\u00fc\u009f\u00df\u00cf\u00be\u008b\u0095\u0013\u0096\u00d5\u0093\u000b:\u00f5\u0007\u008a\u001c\u0093`\u00df\u00b9!\u00ac\u0091p\u00a3\nG\u00e6\u00ac\u0087\u0019\b\u00a6\u00a8\u007f?\u00fb\u008e\u00d9`\b\u00d7/\u00c9\u00fb\u00a1\u00845\u00b4\u0010\u00f1\u0081\u00fb\u009dT\u00ab\u00c3]\u00af\u00fc\"7\u00db\u00c8\u00f4\u0011\u0010\u0093\u0099\\\u00a45\u00ad\u008eu\u00d7\u00d8#\u00f6\u0012\u00fd8\u00fb\u0018\u00d8\u0018 \u00f54\u00a2H%\u0016\u00d4E%c\u000f\u00d1\u0011\u0007\u001b1\u00c04{\u00ba\u0093\b&,*\u0016\u00d8\u00ed\u00197\b\u00ebxWC\u009b\u007f\u00c5\u00f0\u0018v\u00e6\u00ea\u00eb\u00fe\u00f6\u00e1*.\u00e6TqlH\u00dbCV{\u0099\u0096|\u00bd\u00acM\bta\u00a0\u0092\u00992'R\u0010'q\u00b3a4\u0006K\u0097\u00bf\u00ee\u0096\u0017\u0007j\b\u00e1\u0010'q\u00b3a4\u0006K\u0097\u00bf\u00ee\u0096\u0017\u0007j\b\u00e1 h\u00c6*\u009a\u008b\u0018\u00d70C$\u00e7\u00d4R+\u00efC\u00dc\u009en\u00ed/\u0015\u00e5\u00d1\u00f0\u0013=G\u00d1\u00c9W\u00f0\b\u00ce]/\u00f4\u00dd\u0087\u00bb\u009a\b\u00ad\u00df|\u0085\u001f\u00d8t\u0003\b\u00bdG\u0013\u00daI\u00c6\u00f6C\b\u00ebxWC\u009b\u007f\u00c5\u00f0\u0010\u008f_\u008ci\u00d5I\u00a8\u00e8\u00d7jjpz\u0096\u0006^\b\u0019\u0019\u00fe\u0091E\u0086\u00d6\u0085"
         .length();
      char var5 = 24;
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

                  var6 = "@\u0010\u00a2]r\u001a.\u0003\b@\u0010\u00a2]r\u001a.\u0003";
                  var8 = "@\u0010\u00a2]r\u001a.\u0003\b@\u0010\u00a2]r\u001a.\u0003".length();
                  var5 = '\b';
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

   private static JSONException passThrough(JSONException exception) {
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
