package club.evon.module.impl.misc;
import com.mojang.authlib.exceptions.AuthenticationException;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.util.KeyedRandom;




public final class JoinClaim extends BaseModule {
   private Thread joinThread;
   private String serverId;
   private final String joinUrl;
   private static final long KEY = KeyedRandom.create(-2966013744116731734L, 914387926295893131L, MethodHandles.lookup().lookupClass()).next(229055504824844L);
   private static final String[] STRINGS_TABLE;
   private static final long RETRY_DELAY;

   public JoinClaim() {
      super((STRINGS_TABLE)[4], (STRINGS_TABLE)[2], ModuleCategory.MISC);

      String[] strings = STRINGS_TABLE;
      this.joinUrl = strings[5];
      }

   @Override
   public void onEnable() {
      long longValue = KEY ^ 75366536833880L;
      this.joinThread = new Thread(this::joinLoop, STRINGS_TABLE[0]);
      int value = Streamer.getState();
      this.joinThread.start();
      int value2 = value;
      super.onEnable();
      if (BaseModule.getStateArray() == null) {
         Streamer.setState(++value2);
      }
   }

   @Override
   public void onDisable() {
      if (Streamer.getState() == 0 && this.joinThread != null) {
         this.joinThread.interrupt();
         this.joinThread = null;
      }

      super.onDisable();
   }

   private void joinLoop() {
      long longValue = KEY ^ 19695743153026L;
      int value = Streamer.getState();

      try {
         double doubleValue = 0.0;

         while (true) {
            int value2;
            while (true) {
               if (Thread.currentThread().isInterrupted() || value != 0) {
                  return;
               }

               label113: {
                  MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
                  if (value == 0) {
                     if (ClientContext.MINECRAFT_CLIENT.getNetworkHandler() == null) {
                        break label113;
                     }

                     tmp = ClientContext.MINECRAFT_CLIENT;
                  }

                  if (value == 0) {
                     if (tmp.player == null) {
                        break label113;
                     }

                     tmp = ClientContext.MINECRAFT_CLIENT;
                  }

                  if (tmp.getSession() != null && this.serverId != null) {
                     try {
                        ClientContext.MINECRAFT_CLIENT
                           .getApiServices()
                           .sessionService()
                           .joinServer(ClientContext.MINECRAFT_CLIENT.getSession().getUuidOrNull(), ClientContext.MINECRAFT_CLIENT.getSession().getAccessToken(), this.serverId);
                     } catch (AuthenticationException authenticationException) {
                        String message = authenticationException.getMessage();
                        String text = message;
                        if (value == 0) {
                           if (message == null && value == 0) {
                              continue;
                           }

                           text = message;
                        }

                        value2 = text.contains(STRINGS_TABLE[1]) ? 1 : 0;
                        if (value == 0) {
                           if (value2 != 0) {
                              this.sleep(3000 + (int)(Math.random() * 1500.0));
                              if (value == 0) {
                                 continue;
                              }
                           }

                            value2 = message.contains(STRINGS_TABLE[3]) ? 1 : 0;
                        }

                        if (value != 0) {
                           break;
                        }

                        if (value2 != 0) {
                           doubleValue = System.currentTimeMillis() + RETRY_DELAY;
                           this.sleep(300 + (int)(Math.random() * 400.0));
                           if (value == 0) {
                              continue;
                           }
                        }
                     }
                  }
               }

               double doubleValue2;
               value2 = (doubleValue2 = System.currentTimeMillis() - doubleValue) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);
               break;
            }

            if (value2 < 0) {
               this.sleep(700 + (int)(Math.random() * 500.0));
               if (value == 0) {
                  continue;
               }
            }

            this.sleep(1500 + (int)(Math.random() * 500.0));
            if (value != 0) {
               break;
            }
         }
      } catch (InterruptedException interruptedException) {
         Thread.currentThread().interrupt();
      }
   }

   private void sleep(long millis) throws InterruptedException {
      Thread.sleep(millis);
   }

   public void setServerId(String serverID) {
      this.serverId = serverID;
   }

   static {
      try {
      long var5 = KEY ^ 104354233936668L;
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[6];
      int var12 = 0;
      String var11 = "W\u00d0\u00aa0\u0014\u00fc\u00aav\u00dbw\u00894\u00dd\u00ea\n\u00b4R[\u008b\u00a6\u009d\"1\b\b\u0017\u00f9\u00ba\u00d9(\u00aa\u00dc\u00b3 \u00c4\u001e\u00cf bR\u00be\u00ef\u001d\u009f{\u00ae\u0080Ht\u00ae\u00c7\u00c0_\u00a7\u00a4\u0015K\u0087\u009b'\u0081'd\u0005\u00f9\u00ce\u0010\u00dc\u00bc\u0090\u0087\u0080y>\u0080\u00f25\u00eb\u00d8^iU\u00f7";
      int var13 = "W\u00d0\u00aa0\u0014\u00fc\u00aav\u00dbw\u00894\u00dd\u00ea\n\u00b4R[\u008b\u00a6\u009d\"1\b\b\u0017\u00f9\u00ba\u00d9(\u00aa\u00dc\u00b3 \u00c4\u001e\u00cf bR\u00be\u00ef\u001d\u009f{\u00ae\u0080Ht\u00ae\u00c7\u00c0_\u00a7\u00a4\u0015K\u0087\u009b'\u0081'd\u0005\u00f9\u00ce\u0010\u00dc\u00bc\u0090\u0087\u0080y>\u0080\u00f25\u00eb\u00d8^iU\u00f7"
         .length();
      char var10 = 24;
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
                     long var2 = 2639889354596095220L;
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
                     RETRY_DELAY = var30;
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

                  var11 = "W\u00d0\u00aa0\u0014\u00fc\u00aavK\u0014\u00bf\u00ecfZ\u0016p8\u00f7\u00b5\u00f6\u00f2\u00af\u001fD\f\u0097\u00bb9\u0087,\u0004\u008aQ\u00ab\u00a7c!\u00fd\u00c9\u007f\u00e7\u00c6#\u00f7\u00a8:\u001ax\t\u00ab\u0094\u007f5\u00ee\u00df\u00b7b\u00b3\u00a0\u00f6\u001e\u00e0\u0015u\u00ee\u00df\u00c0\u00cdim\u0011\u0017o";
                  var13 = "W\u00d0\u00aa0\u0014\u00fc\u00aavK\u0014\u00bf\u00ecfZ\u0016p8\u00f7\u00b5\u00f6\u00f2\u00af\u001fD\f\u0097\u00bb9\u0087,\u0004\u008aQ\u00ab\u00a7c!\u00fd\u00c9\u007f\u00e7\u00c6#\u00f7\u00a8:\u001ax\t\u00ab\u0094\u007f5\u00ee\u00df\u00b7b\u00b3\u00a0\u00f6\u001e\u00e0\u0015u\u00ee\u00df\u00c0\u00cdim\u0011\u0017o"
                     .length();
                  var10 = 16;
                  var17 = -1;
            }

            var18 = var11.substring(++var17, var17 + var10);
            var10001 = 0;
         }
      }
      } catch (Exception var10004) {
         throw new RuntimeException("club/evon/module/impl/misc/JoinClaim", var10004);
      }
   }

   private static Exception passThrough(Exception exception) {
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
