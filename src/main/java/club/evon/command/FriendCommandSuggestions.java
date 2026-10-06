package club.evon.command;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public class FriendCommandSuggestions extends ArrayList<String> {
   public final FriendCommand command;
   private static final long STRING_SEED = KeyedRandom.create(4959121473005064381L, 780733301517914261L, MethodHandles.lookup().lookupClass()).next(9157573377966L);
   private static final String[] STRINGS_TABLE;

   public FriendCommandSuggestions(FriendCommand friendCommand) {
      super();
      long longValue = STRING_SEED ^ 135264437116886L;
      FriendCommand.getStateArray();
      this.command = friendCommand;
      String[] strings = STRINGS_TABLE;
      this.add(strings[0]);
      this.add(strings[1]);
      if (BaseModule.getStateArray() == null) {
         FriendCommand.setStateArray(new BaseModule[5]);
      }
   }

   static {
      try {
      long var0 = STRING_SEED ^ 88552624934334L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[2];
      int var7 = 0;
      String var6 = "\u00ef\u008c/3\u0006\u009f\u001a\u0015+\u00adpt\u00b1\u009fFcN\u0095\u00cbr\u00bd\u009a\u0087\u00c0\u00d6\u00d9S\u00df^\u00f7P\u0091\u0018\u00ef\u008c/3\u0006\u009f\u001a\u0015f\u009b\u008a\u00bb\u001a\u00e3\u0016\u0096d\u00b2\u0014\u00fd\ti\u00b0\u00b8";
      int var8 = "\u00ef\u008c/3\u0006\u009f\u001a\u0015+\u00adpt\u00b1\u009fFcN\u0095\u00cbr\u00bd\u009a\u0087\u00c0\u00d6\u00d9S\u00df^\u00f7P\u0091\u0018\u00ef\u008c/3\u0006\u009f\u001a\u0015f\u009b\u008a\u00bb\u001a\u00e3\u0016\u0096d\u00b2\u0014\u00fd\ti\u00b0\u00b8"
         .length();
      char var5 = ' ';
      int var4 = -1;

      label27:
      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var13 = decryptString(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var13;
         if ((var4 += var5) >= var8) {
            STRINGS_TABLE = var9;
            break label27;
         }

         var5 = var6.charAt(var4);
      }
      } catch (Exception var10004) {
         throw sneakyThrow(var10004);
      }
   }

   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
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
