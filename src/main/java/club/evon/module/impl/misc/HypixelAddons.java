package club.evon.module.impl.misc;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.text.ClickEvent.RunCommand;
import club.evon.HypixelTargetFilter;
import club.evon.api.HypixelLocationManager;
import club.evon.data.HypixelLocation;
import club.evon.event.ChatMessageEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.StringSetting;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public final class HypixelAddons extends BaseModule {
   private final BooleanSetting autoQueue;
   private final DoubleSetting delay;
   private final BooleanSetting autoGg;
   private final BooleanSetting lobbyOnBan;
   private final StringSetting autoGgMessage;
   private final ScheduledExecutorService scheduler;
   private long lastAutoGg;
   private static final long KEY = KeyedRandom.create(7694988867711329176L, -381349253153906303L, MethodHandles.lookup().lookupClass()).next(11865748956275L);
   private static final String[] STRINGS_TABLE;
   private static final long AUTO_GG_COOLDOWN;

   public HypixelAddons() {
      super((STRINGS_TABLE)[8], (STRINGS_TABLE)[10], ModuleCategory.MISC);

      String[] strings = STRINGS_TABLE;
      this.autoQueue = new BooleanSetting(strings[4], true);
      this.delay = new DoubleSetting(strings[13], 2.0, 0.0, 20.0, 1.0);
      this.autoGg = new BooleanSetting(strings[9], true);
      this.lobbyOnBan = new BooleanSetting(strings[16], true);
      this.autoGgMessage = new StringSetting(strings[1], strings[12]);
      this.scheduler = Executors.newSingleThreadScheduledExecutor();
      this.delay.setVisible(this.autoQueue::I);
      this.autoGgMessage.setVisible(this.autoGg::I);
      this.addSettings(this.autoQueue, this.delay, this.autoGg, this.autoGgMessage, this.lobbyOnBan);
      }

   @Subscribe
   public void onChatMessage(ChatMessageEvent event) {
      long longValue = KEY ^ 72434605769813L;
      int value = Streamer.getStateFlag();
      if (ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter) {
         HypixelLocation currentLocation = HypixelLocationManager.getInstance().getCurrentLocation();
         if (currentLocation != null) {
            String string = event.getText().getString();
            boolean tmp = this.lobbyOnBan.I();
            label139:
            if (value != 0) {
               if (tmp) {
                  tmp = string.equals(STRINGS_TABLE[2]);
                  if (value == 0) {
                     break label139;
                  }

                  if (tmp) {
                     ChatUtil.sendChatMessage(STRINGS_TABLE[14]);
                     ChatUtil.sendMessage(STRINGS_TABLE[11]);
                     return;
                  }
               }

               tmp = this.autoGg.I();
            }

            label132:
            if (value != 0) {
               if (tmp) {
                  long longValue2 = System.currentTimeMillis() - this.lastAutoGg - AUTO_GG_COOLDOWN;
                  tmp = longValue2 > 0L;
                  if (value == 0) {
                     break label132;
                  }

                  if (tmp) {
                     tmp = HypixelTargetFilter.U.stream().anyMatch(pattern -> pattern.matcher(string).matches());
                     if (value == 0) {
                        break label132;
                     }

                     if (tmp) {
                        ChatUtil.sendChatMessage(STRINGS_TABLE[15] + this.autoGgMessage.getValue());
                        this.lastAutoGg = System.currentTimeMillis();
                     }
                  }
               }

               tmp = this.autoQueue.I();
            }

            if (value != 0) {
               if (!tmp) {
                  return;
               }

               tmp = string.equals(STRINGS_TABLE[3]);
            }

            if (value != 0) {
               if (tmp) {
                  this.queueGame();
                  if (value != 0) {
                     return;
                  }
               }

               tmp = currentLocation.hasLobby();
            }

            if (!tmp) {
               Iterator iterator = event.getText().getSiblings().iterator();
               Object object;

               while (true) {
                  while (true) {
                     if (!iterator.hasNext()) {
                        return;
                     }

                     Text text = (Text)iterator.next();
                     ClickEvent clickEvent = text.getStyle().getClickEvent();
                     if (value == 0) {
                        return;
                     }

                     object = clickEvent;
                     if (value == 0) {
                        break;
                     }

                     if (clickEvent instanceof RunCommand) {
                        object = clickEvent;
                        break;
                     }
                  }

                  RunCommand runCommand = (RunCommand)object;
                  RunCommand runCommand2 = runCommand;
                  String text2;
                  try {
                     text2 = runCommand2.command();
                  } catch (Throwable throwable) {
                     throw new MatchException(throwable.toString(), throwable);
                  }

                  String text3 = text2;
                  String text4 = text3;
                  if (value == 0) {
                     break;
                  }

                  if (text4.startsWith(STRINGS_TABLE[7]) || value == 0) {
                     this.queueGame();
                     break;
                  }
               }
            }
         }
      }
   }

   private void queueGame() {
      long longValue = KEY ^ 96992043078917L;
      int value = Streamer.getState();
      HypixelLocation currentLocation = HypixelLocationManager.getInstance().getCurrentLocation();
      Object object = currentLocation;
      if (value == 0) {
         if (currentLocation == null) {
            return;
         }

         object = this.delay.J();
      }

      double doubleValue = (Double)object;
      this.scheduler.schedule(() -> ChatUtil.sendChatMessage(STRINGS_TABLE[5] + currentLocation.getMode()), this.delay.J().longValue(), TimeUnit.SECONDS);
      String text;
      if (value == 0 && !(doubleValue > 0.0)) {
         text = "";
      } else {
         text = STRINGS_TABLE[0] + doubleValue + "s";
      }

      ChatUtil.sendMessage(STRINGS_TABLE[6] + text + "!");
   }

   static {
      try {
      long var5 = KEY ^ 127886217894797L;
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[17];
      int var12 = 0;
      String var11 = "6G^\u00f2Y\u008f\u00aa\u00a7\u00105\u00e51z\u00a3\u00c2(\u00feHT\u00c4\u008a4@.\u001b0\u00d1\u00b9\u00ffbr\u00f6\u00d5\u0081 \u00b0\u00f4Q\u0016\u00bc\u00a5\u00c1Pq6\u00955j7\u009f\u00e8w\u00fb6q\u00b4e\u00e0\u0082\u00e9\u00c2\u007fw\u00d1\u00a4\u00d3]\u00ae\u00c8*\u00d6\u00e7\u00d7\u0084 \u00a3\u00d7\u0098\u00c7\u0083i\u00ef\u0095\u00ae\u009eo\u001e\u00b3\u00bb*\u00052\u0006\u009d\u00f2\u00f38B\u00e8\u0098(\u00f1@\u00daF/\u00a8\u0010'\u00bf\u00ab\u00ddv\u00be\u00a3&i\u00f0\u0087]*<\u00e8n\by\u00f4\u00ac\u00acp\u00e3y\u0007 [\u00cb\u00b3`G\u00f1\u00fdT\u00c1\u001e\u0010\u008b\u00c4G\"\u00ea\u0091\u00de\u00cdo\u0087\u00d4fFm\u00e7\u001b*\u00e1)8\u00e6\by\u00f4\u00ac\u00acp\u00e3y\u0007\u0010\u00e1g\u0094f\u00b7\u001d\u008a\u00a3\u00ed>\u0085'\u00dbB\"\u00b5\b\u00c7\u00d0E+\u00ad\u00c9\\\u009d(*\u001fVA\u0087\u00aa\u00eczPX\u00ee\u00eb_\u00a4\u00b9\u00e2(\u00a8\u0090\u001a\u00f0e\u009cf\u00b3\\a\u001b\u00cd\u00fd1\u001e\u00cf\u00bc_\u00e0e\u0015\u00ef\u009c(\u00d1\u00b9\u00ffbr\u00f6\u00d5\u0081\u0007\u00eb\u00ee\u00d0Y\u008cV\u0004\u001b<\u0092\u00c6\u00e8\u00b9\u0003uaP\b\u00b1\u0085\u00b8\u008b\u00d2\u0002Y\u00be\u00fa$\u0001C\u00b1\b\u00b6\u00f0\b\u00f0\u009f{\u00ea\"\b\u00c8\u00c3;W^Xpr\b\u0011u\u0010\u0090a'\b\u00d0";
      int var13 = "6G^\u00f2Y\u008f\u00aa\u00a7\u00105\u00e51z\u00a3\u00c2(\u00feHT\u00c4\u008a4@.\u001b0\u00d1\u00b9\u00ffbr\u00f6\u00d5\u0081 \u00b0\u00f4Q\u0016\u00bc\u00a5\u00c1Pq6\u00955j7\u009f\u00e8w\u00fb6q\u00b4e\u00e0\u0082\u00e9\u00c2\u007fw\u00d1\u00a4\u00d3]\u00ae\u00c8*\u00d6\u00e7\u00d7\u0084 \u00a3\u00d7\u0098\u00c7\u0083i\u00ef\u0095\u00ae\u009eo\u001e\u00b3\u00bb*\u00052\u0006\u009d\u00f2\u00f38B\u00e8\u0098(\u00f1@\u00daF/\u00a8\u0010'\u00bf\u00ab\u00ddv\u00be\u00a3&i\u00f0\u0087]*<\u00e8n\by\u00f4\u00ac\u00acp\u00e3y\u0007 [\u00cb\u00b3`G\u00f1\u00fdT\u00c1\u001e\u0010\u008b\u00c4G\"\u00ea\u0091\u00de\u00cdo\u0087\u00d4fFm\u00e7\u001b*\u00e1)8\u00e6\by\u00f4\u00ac\u00acp\u00e3y\u0007\u0010\u00e1g\u0094f\u00b7\u001d\u008a\u00a3\u00ed>\u0085'\u00dbB\"\u00b5\b\u00c7\u00d0E+\u00ad\u00c9\\\u009d(*\u001fVA\u0087\u00aa\u00eczPX\u00ee\u00eb_\u00a4\u00b9\u00e2(\u00a8\u0090\u001a\u00f0e\u009cf\u00b3\\a\u001b\u00cd\u00fd1\u001e\u00cf\u00bc_\u00e0e\u0015\u00ef\u009c(\u00d1\u00b9\u00ffbr\u00f6\u00d5\u0081\u0007\u00eb\u00ee\u00d0Y\u008cV\u0004\u001b<\u0092\u00c6\u00e8\u00b9\u0003uaP\b\u00b1\u0085\u00b8\u008b\u00d2\u0002Y\u00be\u00fa$\u0001C\u00b1\b\u00b6\u00f0\b\u00f0\u009f{\u00ea\"\b\u00c8\u00c3;W^Xpr\b\u0011u\u0010\u0090a'\b\u00d0"
         .length();
      char var10 = '\b';
      int var17 = -1;

      label37:
      while (true) {
         String var18 = var11.substring(++var17, var17 + var10);
         byte var10001 = -1;

         while (true) {
            byte[] var15 = var7.doFinal(var18.getBytes("ISO-8859-1"));
            String var26 = decodeUtf8(var15).intern();
            switch (var10001) {
               case 0:
                  var14[var12++] = var26;
                  if ((var17 += var10) >= var13) {
                     STRINGS_TABLE = var14;
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long var2 = -3719278808292915679L;
                     byte[] var4 = var0.doFinal(
                        new byte[]{
                           (byte)(var2 >>> 56),
                           (byte)(var2 >>> 48),
                           (byte)(var2 >>> 40),
                           (byte)(var2 >>> 32),
                           (byte)(var2 >>> 24),
                           (byte)(var2 >>> 16),
                           (byte)(var2 >>> 8),
                           (byte)var2
                        }
                     );
                     long var30 = (var4[0] & 255L) << 56
                        | (var4[1] & 255L) << 48
                        | (var4[2] & 255L) << 40
                        | (var4[3] & 255L) << 32
                        | (var4[4] & 255L) << 24
                        | (var4[5] & 255L) << 16
                        | (var4[6] & 255L) << 8
                        | var4[7] & 255L;
                     var10001 = -1;
                     AUTO_GG_COOLDOWN = var30;
                     break label37;
                  }

                  var10 = var11.charAt(var17);
                  break;
               default:
                  var14[var12++] = var26;
                  if ((var17 += var10) < var13) {
                     var10 = var11.charAt(var17);
                     continue label37;
                  }

                  var11 = "Mzt\u00e2\u00fb\u00a8\b\u00d2\u0010\u0014\u00073\u00bb\u00f0r\u00cb\u00c0\u00d1|\u008c\u0016\u00ea\u00f0\u00efz";
                  var13 = "Mzt\u00e2\u00fb\u00a8\b\u00d2\u0010\u0014\u00073\u00bb\u00f0r\u00cb\u00c0\u00d1|\u008c\u0016\u00ea\u00f0\u00efz".length();
                  var10 = '\b';
                  var17 = -1;
            }

            var18 = var11.substring(++var17, var17 + var10);
            var10001 = 0;
         }
      }
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }
   }

   private static Throwable passThrough(Throwable throwable) {
      return throwable;
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

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
