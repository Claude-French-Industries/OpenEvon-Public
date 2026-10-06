package club.evon.command;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.Formatting;
import org.json.JSONException;
import club.evon.manager.FriendManager;
import club.evon.module.BaseModule;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public final class FriendCommand extends Command {
   private static BaseModule[] stateArray;
   private static final long STRING_SEED = KeyedRandom.create(-5133075379898756605L, -4147213842456635851L, MethodHandles.lookup().lookupClass()).next(212363016032067L);
   private static final String[] STRINGS_TABLE;

   public FriendCommand() {
      super((STRINGS_TABLE)[7], (STRINGS_TABLE)[11], "");

      String[] strings = STRINGS_TABLE;
      }

   @Override
   public boolean execute(String[] arguments) {
      BaseModule[] values;
      int value;
      long longValue = STRING_SEED ^ 37540737917034L;
      BaseModule[] values2 = getStateArray();
      String toLowerCase = arguments[0].toLowerCase();
      byte byteValue = -1;
      values = values2;
      value = toLowerCase.hashCode();
      label107:
      if (values != null) {
         switch (value) {
            case 96417:
               value = toLowerCase.equals(STRINGS_TABLE[9]) ? 1 : 0;
               if (values == null) {
                  break label107;
               }

               if (value == 0) {
                  break;
               }

               byteValue = 0;
               if (values != null) {
                  break;
               }
            case -934610812:
               value = toLowerCase.equals(STRINGS_TABLE[2]) ? 1 : 0;
               if (values == null) {
                  break label107;
               }

               if (value == 0) {
                  break;
               }

               byteValue = 1;
               if (values != null) {
                  break;
               }
            case 94746189:
               value = toLowerCase.equals(STRINGS_TABLE[5]) ? 1 : 0;
               if (values == null) {
                  break label107;
               }

               if (value == 0) {
                  break;
               }

               byteValue = 2;
               if (values != null) {
                  break;
               }
            case 3322014:
               value = toLowerCase.equals(STRINGS_TABLE[3]) ? 1 : 0;
               if (values == null) {
                  break label107;
               }

               if (value != 0) {
                  byteValue = 3;
               }
         }

         value = byteValue;
      }

      if (values != null) {
         switch (value) {
            case 0:
               String text;
               label79: {
                  text = arguments[1];
                  FriendManager instance = FriendManager.getInstance();
                  String text2 = text;
                  if (values != null) {
                     if (instance.isFriend(text)) {
                        break label79;
                     }

                     instance = FriendManager.getInstance();
                     text2 = text;
                  }

                  instance.addFriend(text2);
                  ChatUtil.sendMessage(Formatting.GREEN + STRINGS_TABLE[13] + text + STRINGS_TABLE[1]);
                  if (values != null) {
                     return true;
                  }
               }

               ChatUtil.sendMessage(Formatting.RED + "'" + text + STRINGS_TABLE[6]);
               return true;
            case 1:
               String text3;
               label70: {
                  text3 = arguments[1];
                  FriendManager instance2 = FriendManager.getInstance();
                  String text4 = text3;
                  if (values != null) {
                     if (!instance2.isFriend(text3)) {
                        break label70;
                     }

                     instance2 = FriendManager.getInstance();
                     text4 = text3;
                  }

                  instance2.removeFriend(text4);
                  ChatUtil.sendMessage(Formatting.GREEN + STRINGS_TABLE[10] + text3 + STRINGS_TABLE[14]);
                  if (values != null) {
                     return true;
                  }
               }

               ChatUtil.sendMessage(Formatting.RED + "'" + text3 + STRINGS_TABLE[15]);
               return true;
            case 2:
               FriendManager.getInstance().clearFriends();
               String valueOf = String.valueOf(Formatting.RED);
               String[] strings = STRINGS_TABLE;
               ChatUtil.sendMessage(valueOf + strings[12]);
               return true;
            case 3:
               String valueOf2 = String.valueOf(Formatting.GRAY);
               String valueOf3 = String.valueOf(Formatting.AQUA);
               String valueOf4 = String.valueOf(Formatting.BOLD);
               String valueOf5 = String.valueOf(Formatting.RESET);
               String valueOf6 = String.valueOf(Formatting.GRAY);
               String text5 = valueOf5;
               String text6 = valueOf4;
               String text7 = valueOf3;
               String[] strings2 = STRINGS_TABLE;
               ChatUtil.sendRawMessage(valueOf2 + strings2[4] + text7 + text6 + strings2[8] + text5 + valueOf6 + strings2[0]);
               FriendManager.getInstance().getFriends().forEach(ChatUtil::sendRawMessage);
               return true;
            default:
               value = 0;
         }
      }

      return value != 0;
   }

   @Override
   public ArrayList<String> getSuggestions() {
      return new FriendCommandSuggestions(this);
   }

   public static void setStateArray(BaseModule[] values) {
      stateArray = values;
   }

   public static BaseModule[] getStateArray() {
      return stateArray;
   }

   static {
      try {
      BaseModule[] var10000 = new BaseModule[4];
      long var0 = STRING_SEED ^ 138937875393472L;
      setStateArray(var10000);
      Cipher var2;
      Cipher var13 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var13.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[16];
      int var7 = 0;
      String var6 = "t\u00cd\f\u00c4\u001a\u00e0J\u0011\b#?\u00817\u0085\u00bdTS\u0088\u00fec\u00a4\u00b3\u001b\u0098\u0018\u008e\u00d3H\u00f0\u008e]7=;\u00928\u008a\u00b4\u00ee#\u00aex\ne8\u00ee{\u00e8\u00c3\b\u00a3\u00d0\u0003\u00d0F7D{\b\u00dfYB\u00a3\u00bf\u00beQV\u0018y\u00eb\u00ea\u00fa\u00b0\u00cd|\u00a7a\u0087j\u00fe\u009c\u00cb\u0015$\u00af\u0015c\u0014\u00a5\u0019\u00c6h\b\u00db#\u0013:\u00f2\u0007!\u001b }~a\"z\r.V\u00d8\u00d5\u00e3W\u009e\u0015\u00a1@\u00f4\u00b3\u00007e\u00c5y\u00e6\u0007\u00c74\u009ff\u00e5s\u00b1\bQ\u0001z\u0091<\u00d7u\u00c0\b\u00b1\u00be'\u0013\u00b02\u0080L\b\u007f\u00ef\u00f5\u00acd\u0084\u00c8j\u0010\u00d2F\u008dzcr\u00ac\u00ee\u00ff\u00ba\u0098\u00ff\u00bf\u009b\u00da\u00a6\u00103\u0001\u008a\u0085/:S\u00dc5\u00f0$G\u0088\u00da\u0084\u0005\u0018\u00d2F\u008dzcr\u00ac\u00ee\u00e97\u00fc\u0091\u0014\u0013\u00b5n\u00db\u00dant.L\u00deM\b\u00f0\u0096\u00cbU5yfq";
      int var8 = "t\u00cd\f\u00c4\u001a\u00e0J\u0011\b#?\u00817\u0085\u00bdTS\u0088\u00fec\u00a4\u00b3\u001b\u0098\u0018\u008e\u00d3H\u00f0\u008e]7=;\u00928\u008a\u00b4\u00ee#\u00aex\ne8\u00ee{\u00e8\u00c3\b\u00a3\u00d0\u0003\u00d0F7D{\b\u00dfYB\u00a3\u00bf\u00beQV\u0018y\u00eb\u00ea\u00fa\u00b0\u00cd|\u00a7a\u0087j\u00fe\u009c\u00cb\u0015$\u00af\u0015c\u0014\u00a5\u0019\u00c6h\b\u00db#\u0013:\u00f2\u0007!\u001b }~a\"z\r.V\u00d8\u00d5\u00e3W\u009e\u0015\u00a1@\u00f4\u00b3\u00007e\u00c5y\u00e6\u0007\u00c74\u009ff\u00e5s\u00b1\bQ\u0001z\u0091<\u00d7u\u00c0\b\u00b1\u00be'\u0013\u00b02\u0080L\b\u007f\u00ef\u00f5\u00acd\u0084\u00c8j\u0010\u00d2F\u008dzcr\u00ac\u00ee\u00ff\u00ba\u0098\u00ff\u00bf\u009b\u00da\u00a6\u00103\u0001\u008a\u0085/:S\u00dc5\u00f0$G\u0088\u00da\u0084\u0005\u0018\u00d2F\u008dzcr\u00ac\u00ee\u00e97\u00fc\u0091\u0014\u0013\u00b5n\u00db\u00dant.L\u00deM\b\u00f0\u0096\u00cbU5yfq"
         .length();
      char var5 = 24;
      int var12 = -1;

      label27:
      while (true) {
         String var14 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var14.getBytes("ISO-8859-1"));
            String var20 = decryptString(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var20;
                  if ((var12 += var5) >= var8) {
                     STRINGS_TABLE = var9;
                     break label27;
                  }

                  var5 = var6.charAt(var12);
                  break;
               default:
                  var9[var7++] = var20;
                  if ((var12 += var5) < var8) {
                     var5 = var6.charAt(var12);
                     continue label27;
                  }

                  var6 = "h[\u00db\u00a2\u009f\u001c\u00a84\u0098\u009a\u00a5\u00d7&\u00eb\u0081\r\u00ef=\u00dc\u00c6\u00cc0\u00040 }~a\"z\r.V\u00d8\u00d5\u00e3W\u009e\u0015\u00a1@\u00f4\u00b3\u00007e\u00c5y\u00e6\u0007\u00c74\u009ff\u00e5s\u00b1";
                  var8 = "h[\u00db\u00a2\u009f\u001c\u00a84\u0098\u009a\u00a5\u00d7&\u00eb\u0081\r\u00ef=\u00dc\u00c6\u00cc0\u00040 }~a\"z\r.V\u00d8\u00d5\u00e3W\u009e\u0015\u00a1@\u00f4\u00b3\u00007e\u00c5y\u00e6\u0007\u00c74\u009ff\u00e5s\u00b1"
                     .length();
                  var5 = 24;
                  var12 = -1;
            }

            var14 = var6.substring(++var12, var12 + var5);
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
