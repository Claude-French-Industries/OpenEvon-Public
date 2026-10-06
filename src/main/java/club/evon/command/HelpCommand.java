package club.evon.command;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.Formatting;
import org.json.JSONException;
import club.evon.EvonClient;
import club.evon.module.BaseModule;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public final class HelpCommand extends Command {
   private static final long STRING_SEED = KeyedRandom.create(-4330044864182294791L, 4920194909635544381L, MethodHandles.lookup().lookupClass()).next(81004988102220L);
   private static final String[] STRINGS_TABLE;

   public HelpCommand() {
      super((STRINGS_TABLE)[5], (STRINGS_TABLE)[4], (STRINGS_TABLE)[0]);

      String[] strings = STRINGS_TABLE;
      }

   @Override
   public boolean execute(String[] arguments) {
      long longValue = STRING_SEED ^ 23319984759356L;
      BaseModule[] values = FriendCommand.getStateArray();
      String valueOf = String.valueOf(Formatting.GRAY);
      String valueOf2 = String.valueOf(Formatting.AQUA);
      String valueOf3 = String.valueOf(Formatting.BOLD);
      String valueOf4 = String.valueOf(Formatting.RESET);
      String valueOf5 = String.valueOf(Formatting.GRAY);
      String text = valueOf4;
      String text2 = valueOf3;
      String text3 = valueOf2;
      String[] strings = STRINGS_TABLE;
      ChatUtil.sendRawMessage(valueOf + strings[2] + text3 + text2 + strings[1] + text + valueOf5 + strings[6]);
      BaseModule[] values2 = values;
      EvonClient.getInstance().getCommandManager().getCommands().forEach(command -> ChatUtil.sendRawMessage("." + command.getName() + STRINGS_TABLE[3] + command.getDescription()));
      if (values2 == null) {
         BaseModule.setStateArray(new BaseModule[5]);
      }

      return true;
   }

   static {
      try {
      long var0 = STRING_SEED ^ 82208156195876L;
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
      String var6 = "\u00abj\u0005\u0006\u00e8\u0094\u00f4\u0005\u0010\u0013\u0003\u0002/m\u0094Q\u009a_\u0000\u0005\u00a3A\u0017\u0013\u00a4\u0018\u00c9\u00be\u001f(~\u0089\u00bf\u00b0\u00d6\u00f5n\u00d1\u009eM]6\u00f3\u0082'\u00afQt\u001a\u00b5\b\u00ff\u00b3\u00a1sc\u0013[\u00df8\u0093\u00be\u0083\u0083\u00fa\u0098\u0006\u00c6?\u00f9F\u00a4[\u00eb}\u00c7\u00bc\u00c0!\u00daLe\u00f4\u00a1\u0099\u00c7\u0003\u007f@\u00d9\u00a4D\u000eB\u00b0\u001a3\u00ed\u0083\u008cg\u0085\u0018\u0094\u0096\u008b\u00fe\u00cc2\f\u00e5\u008fy!\u00e3J";
      int var8 = "\u00abj\u0005\u0006\u00e8\u0094\u00f4\u0005\u0010\u0013\u0003\u0002/m\u0094Q\u009a_\u0000\u0005\u00a3A\u0017\u0013\u00a4\u0018\u00c9\u00be\u001f(~\u0089\u00bf\u00b0\u00d6\u00f5n\u00d1\u009eM]6\u00f3\u0082'\u00afQt\u001a\u00b5\b\u00ff\u00b3\u00a1sc\u0013[\u00df8\u0093\u00be\u0083\u0083\u00fa\u0098\u0006\u00c6?\u00f9F\u00a4[\u00eb}\u00c7\u00bc\u00c0!\u00daLe\u00f4\u00a1\u0099\u00c7\u0003\u007f@\u00d9\u00a4D\u000eB\u00b0\u001a3\u00ed\u0083\u008cg\u0085\u0018\u0094\u0096\u008b\u00fe\u00cc2\f\u00e5\u008fy!\u00e3J"
         .length();
      char var5 = '\b';
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

                  var6 = "\u00abj\u0005\u0006\u00e8\u0094\u00f4\u0005\u0018\u00ba\u00b7\u009d\u001cY\"\u008e\u00b0\u0004\u00c8\n\u00b7\u00e7\b\u00b7%q\u0090\u00ce\u00d2Qq\u001f\u0089";
                  var8 = "\u00abj\u0005\u0006\u00e8\u0094\u00f4\u0005\u0018\u00ba\u00b7\u009d\u001cY\"\u008e\u00b0\u0004\u00c8\n\u00b7\u00e7\b\u00b7%q\u0090\u00ce\u00d2Qq\u001f\u0089"
                     .length();
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
