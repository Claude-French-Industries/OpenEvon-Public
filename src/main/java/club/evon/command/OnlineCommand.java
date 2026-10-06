package club.evon.command;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import club.evon.data.UserProfile;
import club.evon.manager.ChatNameManager;
import club.evon.module.BaseModule;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public class OnlineCommand extends Command {
   private static final long STRING_SEED = KeyedRandom.create(6088672933651899385L, -8594369125145436059L, MethodHandles.lookup().lookupClass()).next(50313270581088L);
   private static final String[] STRINGS_TABLE;

   public OnlineCommand() {
      super((STRINGS_TABLE)[1], (STRINGS_TABLE)[0], "");

      String[] strings = STRINGS_TABLE;
      }

   @Override
   public boolean execute(String... args) {
      long longValue = STRING_SEED ^ 16742701217547L;
      String state = IrcChatCommand.getState();
      List profiles = ChatNameManager.getInstance().getProfiles();
      String valueOf = String.valueOf(Formatting.GRAY);
      String valueOf2 = String.valueOf(Formatting.GOLD);
      String valueOf3 = String.valueOf(Formatting.BOLD);
      int value = profiles.size();
      String valueOf4 = String.valueOf(Formatting.RESET);
      String valueOf5 = String.valueOf(Formatting.GRAY);
      String text = valueOf4;
      int value2 = value;
      String text2 = valueOf3;
      String text3 = valueOf2;
      String[] strings = STRINGS_TABLE;
      ChatUtil.sendRawMessage(valueOf + strings[3] + text3 + text2 + strings[6] + value2 + strings[4] + text + valueOf5 + strings[2]);
      Iterator iterator = profiles.iterator();
      String text4 = state;

      while (iterator.hasNext()) {
         UserProfile userProfile = (UserProfile)iterator.next();
         ChatUtil.sendRawText(Text.literal(userProfile.getUsername()).withColor(userProfile.getRank().getTextColor().getRgb()).append(Text.literal(STRINGS_TABLE[5] + userProfile.getMinecraftUsername() + ")").formatted(Formatting.GRAY)));
         if (text4 != null) {
            BaseModule.setStateArray(new BaseModule[1]);
            break;
         }
      }

      return true;
   }

   static {
      try {
      long var0 = STRING_SEED ^ 38725423580910L;
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
      String var6 = "\u001bR@^\u00f1\u009aLlu\u00f8*\u008c\u00bdN\u00f5\u009b_g\u00ff\u0016\u0018A\u00dd8\b\u00b1\u008c?\u0000\u00a8\u00b0\u00cd\u00d6\u0018\u00c9\u0004\u00c3\u00ff\u00e0\u000fz6\\Od\u009a\u00d9\u0098\u001f\u0081_\u00fd\u0084z\u001e\u0099\u009bO\u0018\u0087\u00b6Sl\u0019\u0087g\u00d1\u0006\u00da\u00acM\u00a2R\u0019 \u00fa\u00df\u000fl\u00ee\u0004\u009f\u00f8\u0010\u00e2\u0007m)\u00a8O\u00cc\u00de\u00d1\u0016\u00f4T\u00fe\u00a5^6";
      int var8 = "\u001bR@^\u00f1\u009aLlu\u00f8*\u008c\u00bdN\u00f5\u009b_g\u00ff\u0016\u0018A\u00dd8\b\u00b1\u008c?\u0000\u00a8\u00b0\u00cd\u00d6\u0018\u00c9\u0004\u00c3\u00ff\u00e0\u000fz6\\Od\u009a\u00d9\u0098\u001f\u0081_\u00fd\u0084z\u001e\u0099\u009bO\u0018\u0087\u00b6Sl\u0019\u0087g\u00d1\u0006\u00da\u00acM\u00a2R\u0019 \u00fa\u00df\u000fl\u00ee\u0004\u009f\u00f8\u0010\u00e2\u0007m)\u00a8O\u00cc\u00de\u00d1\u0016\u00f4T\u00fe\u00a5^6"
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

                  var6 = "\u0089\u00d9\u008f8m\u00f7\u00a6%\bLA(\u001e\u00a2%\u0006;";
                  var8 = "\u0089\u00d9\u008f8m\u00f7\u00a6%\bLA(\u001e\u00a2%\u0006;".length();
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
