package club.evon.module.impl.misc;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.text.Text;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.HypixelTargetFilter;
import club.evon.api.HypixelLocationManager;
import club.evon.data.HypixelLocation;
import club.evon.event.ChatMessageEvent;
import club.evon.event.JoinWorldEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.StringSetting;
import club.evon.util.KeyedRandom;




public final class Streamer extends BaseModule {
   private final BooleanSetting hideUsername;
   private final BooleanSetting hideHypixelId;
   private final StringSetting username;
   private String serverName;
   private static int state;
   private static final long KEY = KeyedRandom.create(9195903288609568342L, -2562169259792948185L, MethodHandles.lookup().lookupClass()).next(60583251367732L);
   private static final String[] STRINGS;

   public Streamer() {
      super((STRINGS)[9], (STRINGS)[5], ModuleCategory.MISC);

      String[] strings = STRINGS;
      this.hideUsername = new BooleanSetting(strings[11], true);
      this.hideHypixelId = new BooleanSetting(strings[21], true);
      this.username = new StringSetting(strings[2], strings[13]);
      this.username.setVisible(this.hideUsername::I);
      this.addSettings(this.hideUsername, this.username, this.hideHypixelId);
      }

   private List<String> getLocationAliases() {
      long longValue = KEY ^ 53475407433948L;
      int value = getStateFlag();
      if (!(ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter)) {
         return Collections.emptyList();
      }

      HypixelLocation currentLocation = HypixelLocationManager.getInstance().getCurrentLocation();
      HypixelLocation tmp = currentLocation;
      if (value != 0) {
         if (currentLocation == null) {
            return Collections.emptyList();
         }

         tmp = currentLocation;
      }

      String serverName2 = tmp.getServerName();
      return List.of(serverName2, serverName2.replace(STRINGS[3], "m"));
   }

   @Subscribe
   private void onChatMessage(ChatMessageEvent event) {
      long longValue = KEY ^ 108908388389088L;
      int value = getStateFlag();
      boolean tmp = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter;
      if (value != 0) {
         if (!tmp) {
            return;
         }

         tmp = this.hideHypixelId.I();
      }

      if (tmp) {
         String string = event.getText().getString();
         tmp = string.startsWith(STRINGS[12]);
         if (value != 0) {
            if (tmp) {
               this.serverName = string.replace(STRINGS[7], "").replace("!", "");
               event.setText(Text.of(STRINGS[22] + this.serverName + STRINGS[18]));
               if (value != 0) {
                  return;
               }
            }

            tmp = string.startsWith(STRINGS[4]);
         }

         if (tmp) {
            this.serverName = string.replace(STRINGS[1], "").replace(STRINGS[10], "");
            event.setText(Text.of(STRINGS[19] + this.serverName + STRINGS[0]));
         }
      }
   }

   @Subscribe
   private void onJoinWorld(JoinWorldEvent event) {
      this.serverName = null;
   }

   public static String censor(String original) {
      long longValue = KEY ^ 50813951938391L;
      int value = getStateFlag();
      Streamer moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Streamer.class);
      int value2 = value;
      if (!moduleByClass.isEnabled()) {
         return original;
      }

      String username2 = ClientContext.MINECRAFT_CLIENT.getSession().getUsername();
      boolean flag = moduleByClass.hideUsername.I();
      label109:
      if (value2 != 0) {
         if (flag) {
            flag = original.contains(username2);
            if (value2 != 0) {
               if (flag) {
                  original = original.replaceAll(username2, moduleByClass.username.getValue());
               }

               flag = original.toUpperCase().contains(username2.toUpperCase());
            }

            if (value2 == 0) {
               break label109;
            }

            if (flag) {
               String toUpperCase = original.toUpperCase();
               toUpperCase = toUpperCase.replaceAll(username2.toUpperCase(), moduleByClass.username.getValue());
               original = toUpperCase;
            }
         }

         flag = moduleByClass.hideHypixelId.I();
      }

      if (value2 != 0) {
         if (!flag) {
            return original;
         }

         flag = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter;
      }

      if (value2 != 0) {
         if (!flag) {
            return original;
         }

         flag = original.startsWith(STRINGS[20]);
      }

      label88:
      if (value2 != 0) {
         if (flag) {
            flag = original.contains("/");
            if (value2 == 0) {
               break label88;
            }

            if (flag) {
               flag = original.contains(STRINGS[6]);
               if (value2 == 0) {
                  break label88;
               }

               if (flag) {
                  String[] strings = STRINGS;
                  original = original.replace(strings[8], strings[14]);
               }
            }
         }

         if (value2 == 0) {
            return original;
         }

         flag = original.contains(STRINGS[16]);
      }

      if (flag) {
         if (value2 == 0) {
            return original;
         }

         if (original.contains(STRINGS[15])) {
            String[] strings2 = STRINGS;
            original = original.replace(strings2[15], strings2[17]);
         }
      }

      return original;
   }

   public static void setState(int value) {
      state = value;
   }

   public static int getState() {
      return state;
   }

   public static int getStateFlag() {
      int value = getState();
      return value == 0 ? 44 : 0;
   }

   static {
      try {
      long var0 = KEY ^ 108692482933393L;
      setState(0);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[23];
      int var7 = 0;
      String var6 = "\u009ct&-\u00c6\u00b7n\u00edk3\u0013\u00c7dk\u00037\u0018(D\u00a3\u000b\u00d7\u0086\u00d6\u00d8\u0014`\u00ec\u00d5\rC\u00c2m\u00cf\u00bc{;G\u00af}\u00e1\u0010\u00e0\u001e\u0003\u00ed\u0012\u00fb\u00e2\u0006$Zl\u00a7\u00c1\u0095`.\b\u0083\u00ca\u00b8s\u00d1\u00fb\u00b1\u00c9\u0018(D\u00a3\u000b\u00d7\u0086\u00d6\u00d8\u0014`\u00ec\u00d5\rC\u00c2m\u00cf\u00bc{;G\u00af}\u00e10J~\u0083a\u0013dX\u00bf\u00ca\u00faS2\u00bd#7\u00eb\u00a9BM%l\u0083\u00bd\u00e0\u008fG\u0016\u00a1f\u00e5\u00fe=\u001c*\u00ac\u0095\u00a2_<\u001eT<b\u00ff\u00db\u00d2\u001e\u00a3\b\u0080hL\u00aa\u0012yM\u0089\u0010(D\u00a3\u000b\u00d7\u0086\u00d6\u00d8{\u00c3V\u0014\u00d7\u0097\u00c2\u00a9\b\u00a1x\u00e0\u0099\u0006\u00f2T\u00ca\u0010\u00024NK\u0080\u000b\u0012hi\u00d2*Gkd\u00ad\u00f3\b*\u00f9\u00a9\u009d\u00b4\u008b8\u00ed\u0010\u0006\u001eF\u00d6\u001b\u00a6\u00bc\u0093,a\u00c1\u0080h%z\u00b4\u0010(D\u00a3\u000b\u00d7\u0086\u00d6\u00d89\u00f3j\"L\u00b0\u00d3|\b\f\u00e8\u00fb\u00ea\u00b6j\u00ea\u0082\b\f<\u0011d_\u0085\u00df<\b\u00a1x\u00e0\u0099\u0006\u00f2T\u00ca\b\u007f[\\\u00d3z\u0012W\u008b\b\f<\u0011d_\u0085\u00df<\b_\b$#\u00ba\u00e7!z\u0018\u00fa)\u00c4z\u0096\u00c5\u00128\u00b4\u001e\u00ed\u0091\u00d5N\u00a0\u00fc\u00e2/`&\u00d5=\u00b2\u0007\bJ\u0096?\u00e4\u0088\u00f2\u00e5+";
      int var8 = "\u009ct&-\u00c6\u00b7n\u00edk3\u0013\u00c7dk\u00037\u0018(D\u00a3\u000b\u00d7\u0086\u00d6\u00d8\u0014`\u00ec\u00d5\rC\u00c2m\u00cf\u00bc{;G\u00af}\u00e1\u0010\u00e0\u001e\u0003\u00ed\u0012\u00fb\u00e2\u0006$Zl\u00a7\u00c1\u0095`.\b\u0083\u00ca\u00b8s\u00d1\u00fb\u00b1\u00c9\u0018(D\u00a3\u000b\u00d7\u0086\u00d6\u00d8\u0014`\u00ec\u00d5\rC\u00c2m\u00cf\u00bc{;G\u00af}\u00e10J~\u0083a\u0013dX\u00bf\u00ca\u00faS2\u00bd#7\u00eb\u00a9BM%l\u0083\u00bd\u00e0\u008fG\u0016\u00a1f\u00e5\u00fe=\u001c*\u00ac\u0095\u00a2_<\u001eT<b\u00ff\u00db\u00d2\u001e\u00a3\b\u0080hL\u00aa\u0012yM\u0089\u0010(D\u00a3\u000b\u00d7\u0086\u00d6\u00d8{\u00c3V\u0014\u00d7\u0097\u00c2\u00a9\b\u00a1x\u00e0\u0099\u0006\u00f2T\u00ca\u0010\u00024NK\u0080\u000b\u0012hi\u00d2*Gkd\u00ad\u00f3\b*\u00f9\u00a9\u009d\u00b4\u008b8\u00ed\u0010\u0006\u001eF\u00d6\u001b\u00a6\u00bc\u0093,a\u00c1\u0080h%z\u00b4\u0010(D\u00a3\u000b\u00d7\u0086\u00d6\u00d89\u00f3j\"L\u00b0\u00d3|\b\f\u00e8\u00fb\u00ea\u00b6j\u00ea\u0082\b\f<\u0011d_\u0085\u00df<\b\u00a1x\u00e0\u0099\u0006\u00f2T\u00ca\b\u007f[\\\u00d3z\u0012W\u008b\b\f<\u0011d_\u0085\u00df<\b_\b$#\u00ba\u00e7!z\u0018\u00fa)\u00c4z\u0096\u00c5\u00128\u00b4\u001e\u00ed\u0091\u00d5N\u00a0\u00fc\u00e2/`&\u00d5=\u00b2\u0007\bJ\u0096?\u00e4\u0088\u00f2\u00e5+"
         .length();
      char var5 = 16;
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

                  var6 = "\b\u00f3\u00a8\u00f4m\u00b4\u00ce+\u0005N\u00b4\u00f5\u00de2\u00ccu\u00187\u009c\u0093\u00d8\u00c4=M\u00d7\u00ef\u00dd\u0005t\u00e7i-\u00fe<\u00a3\u008e\u00ea\u0098\u00a5r\u00a3";
                  var8 = "\b\u00f3\u00a8\u00f4m\u00b4\u00ce+\u0005N\u00b4\u00f5\u00de2\u00ccu\u00187\u009c\u0093\u00d8\u00c4=M\u00d7\u00ef\u00dd\u0005t\u00e7i-\u00fe<\u00a3\u008e\u00ea\u0098\u00a5r\u00a3"
                     .length();
                  var5 = 16;
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

   private static JSONException rethrow(JSONException exception) {
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
