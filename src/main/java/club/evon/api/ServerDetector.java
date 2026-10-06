package club.evon.api;
import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.ServerAddress;
import org.json.JSONException;
import club.evon.HypixelTargetFilter;
import club.evon.TargetFilterFactory;
import club.evon.antibot.MinemenAntibot;
import club.evon.event.EventBus;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class ServerDetector {
   private ServerProfile serverProfile;
   private static final TargetFilterFactory[] TARGET_FILTER_FACTORIES;
   private static BaseModule[] stateArray;
   private static final long STRING_SEED = KeyedRandom.create(-9192219716914497938L, 615683873101991365L, MethodHandles.lookup().lookupClass()).next(57243850253250L);
   private static final String[] STRINGS_TABLE;

   public void detectServer(ServerAddress address) {
      long longValue = STRING_SEED ^ 58086619547152L;
      int[] values = ServerProfile.getStateArray();
      TargetFilterFactory[] values2 = TARGET_FILTER_FACTORIES;
      int length = values2.length;
      int[] values3 = values;
      int index = 0;

      while (true) {
         if (index < length) {
            TargetFilterFactory targetFilterFactory = values2[index];
            ServerProfile createProfile = targetFilterFactory.createProfile(address);
            if (values3 == null) {
               break;
            }

            if (values3 != null) {
               if (createProfile != null) {
                  this.serverProfile = createProfile;
                  EventBus.register(this.serverProfile);
                  return;
               }

               index++;
            }

            if (values3 != null) {
               continue;
            }

            BaseModule.setStateArray(new BaseModule[5]);
         }

         this.serverProfile = null;
         break;
      }
   }

   public void clearServerProfile() {
      long longValue = STRING_SEED ^ 66727421368338L;
      int[] values = ServerProfile.getStateArray();
      ServerProfile tmp = this.serverProfile;
      if (values != null) {
         if (this.serverProfile == null) {
            return;
         }

         tmp = this.serverProfile;
      }

      tmp.dispose();
      this.serverProfile = null;
   }

   public void setServerProfile(ServerProfile currentServer) {
      label21: {
         long longValue = STRING_SEED ^ 139990910683456L;
         int[] values = ServerProfile.getStateArray();
         this.clearServerProfile();
         int[] values2 = values;
         ServerProfile serverProfile2 = this.serverProfile;
         if (values2 != null) {
            if (this.serverProfile == currentServer) {
               break label21;
            }

            this.serverProfile = currentServer;
            serverProfile2 = this.serverProfile;
         }

         EventBus.register(serverProfile2);
      }

      if (BaseModule.getStateArray() == null) {
         ServerProfile.setStateArray(new int[3]);
      }
   }

   private static boolean matchesDomain(ServerAddress serverAddress, String domain, boolean allowSubdomains) {
      long longValue = STRING_SEED ^ 120808553828751L;
      String toLowerCase = serverAddress.getAddress().toLowerCase();
      String quote = Pattern.quote(domain);
      String[] strings = STRINGS_TABLE;
      String text = quote + strings[3];
      if (allowSubdomains) {
         text = strings[7] + text;
      }

      Pattern compile = Pattern.compile(text);
      Matcher matcher = compile.matcher(toLowerCase);
      return matcher.matches();
   }

   public ServerProfile getServerProfile() {
      return this.serverProfile;
   }

   static {
      try {
      long var0 = STRING_SEED ^ 71882066136365L;
      setStateArray(null);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[9];
      int var7 = 0;
      String var6 = "\u00bc\u00fc#\u00d3\u00f7g?\u00b1\u001c\u00f7\u00bf\u00be\u00f5\u00e2\u0007i\u001e\u00bf\"\u008a\u00a5\u0081)Y\u0010/\u00b2\u009f\u0082v\u00d5\u0010c\u00cab\u00fe\u00a0\u0099&\t\u00dd\u00106D\u00dcb\u00cc\u00d7\u00eb<\u000eZ\u00fd\u0012\u00d5j\u009b\u00b6\b\u00e7\u00baO\u00c7r\u00df\u00ca-\u0010\u00faS\u008f\u00f8\u0088\u0091\u00bc\u00ae\u009f\u00105)\u00a3X\u00ea\u00a8\u0018g\u0018\u008bs\u00aa\u00f9\u0017\u0002\u00e3\u0001\u00e6\u00d2ef\u00cc/\u0000\u00e2=\u0088g\u00f8\u008c\u00fb\u0010/\u00b2\u009f\u0082v\u00d5\u0010ch]/N?\u00a7\u001b\u00b8";
      int var8 = "\u00bc\u00fc#\u00d3\u00f7g?\u00b1\u001c\u00f7\u00bf\u00be\u00f5\u00e2\u0007i\u001e\u00bf\"\u008a\u00a5\u0081)Y\u0010/\u00b2\u009f\u0082v\u00d5\u0010c\u00cab\u00fe\u00a0\u0099&\t\u00dd\u00106D\u00dcb\u00cc\u00d7\u00eb<\u000eZ\u00fd\u0012\u00d5j\u009b\u00b6\b\u00e7\u00baO\u00c7r\u00df\u00ca-\u0010\u00faS\u008f\u00f8\u0088\u0091\u00bc\u00ae\u009f\u00105)\u00a3X\u00ea\u00a8\u0018g\u0018\u008bs\u00aa\u00f9\u0017\u0002\u00e3\u0001\u00e6\u00d2ef\u00cc/\u0000\u00e2=\u0088g\u00f8\u008c\u00fb\u0010/\u00b2\u009f\u0082v\u00d5\u0010ch]/N?\u00a7\u001b\u00b8"
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
                     TARGET_FILTER_FACTORIES = new TargetFilterFactory[]{var0x -> {
                        long var1 = STRING_SEED ^ 89214014897173L;
                        int[] var3x = ServerProfile.getStateArray();
                        int var10000x = var0x.getPort();
                        if (var3x != null) {
                           if (var10000x != 25565) {
                              return null;
                           }

                           var10000x = matchesDomain(var0x, STRINGS_TABLE[6], true) ? 1 : 0;
                        }

                        if (var3x != null) {
                           if (var10000x != 0) {
                              return new HypixelTargetFilter();
                           }

                           var10000x = matchesDomain(var0x, STRINGS_TABLE[1], true) ? 1 : 0;
                        }

                        if (var3x != null) {
                           if (var10000x != 0) {
                              return new HypixelTargetFilter();
                           }

                           var10000x = matchesDomain(var0x, STRINGS_TABLE[0], true) ? 1 : 0;
                        }

                        return var10000x != 0 ? new HypixelTargetFilter() : null;
                     }, var0x -> {
                        long var1 = STRING_SEED ^ 100943521407038L;
                        int[] var3x = ServerProfile.getStateArray();
                        int var10000x = var0x.getPort();
                        if (var3x != null) {
                           if (var10000x != 25565) {
                              return null;
                           }

                           var10000x = matchesDomain(var0x, STRINGS_TABLE[8], false) ? 1 : 0;
                        }

                        if (var3x != null) {
                           if (var10000x != 0) {
                              return new CubecraftServer();
                           }

                           var10000x = matchesDomain(var0x, STRINGS_TABLE[5], false) ? 1 : 0;
                        }

                        return var10000x != 0 ? new CubecraftServer() : null;
                     }, var0x -> {
                        long var1 = STRING_SEED ^ 118709086972528L;
                        int[] var3x = ServerProfile.getStateArray();
                        int var10000x = var0x.getPort();
                        if (var3x != null) {
                           if (var10000x != 25565) {
                              return null;
                           }

                           var10000x = matchesDomain(var0x, STRINGS_TABLE[4], true) ? 1 : 0;
                        }

                        return var10000x != 0 ? new MinemenAntibot() : null;
                     }, var0x -> {
                        long var1 = STRING_SEED ^ 122227457050312L;
                        int[] var3x = ServerProfile.getStateArray();
                        int var10000x = var0x.getPort();
                        if (var3x != null) {
                           if (var10000x != 25565) {
                              return null;
                           }

                           var10000x = matchesDomain(var0x, STRINGS_TABLE[2], true) ? 1 : 0;
                        }

                        return var10000x != 0 ? new LiquidProxyDetector() : null;
                     }};
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

                  var6 = "\u00ebbc\u0098\u0094\u0091p\u00ea\u00fb\u008b\u00dd\u0014CGF\u00ae\u009f\u00b5{\u0017\u00cb\u00c1\u00f1\u00e4\u0010`\u000b\u0013\u000b\f\fo2*\u001e\u00cdH\u009d\u00e7\u00a2T";
                  var8 = "\u00ebbc\u0098\u0094\u0091p\u00ea\u00fb\u008b\u00dd\u0014CGF\u00ae\u009f\u00b5{\u0017\u00cb\u00c1\u00f1\u00e4\u0010`\u000b\u0013\u000b\f\fo2*\u001e\u00cdH\u009d\u00e7\u00a2T"
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

   public static void setStateArray(BaseModule[] values) {
      stateArray = values;
   }

   public static BaseModule[] getStateArray() {
      return stateArray;
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
