package club.evon.api;
import io.netty.channel.ChannelFuture;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.Address;
import net.minecraft.client.network.AllowedAddressResolver;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.ServerInfo.ServerType;
import net.minecraft.client.world.ClientChunkLoadProgress;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.network.packet.s2c.login.LoginDisconnectS2CPacket;
import net.minecraft.network.state.LoginStates;
import net.minecraft.text.Text;
import club.evon.ClientContext;
import club.evon.ClientService;
import club.evon.data.AccountData;
import club.evon.data.BanStatus;
import club.evon.event.EventBus;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.runtime.IndyRuntime;
import club.evon.util.KeyedRandom;




public final class HypixelBanChecker implements ClientService {
   private final String host;
   private CompletableFuture<BanStatus> banStatusFuture;
   private String disconnectMessage;
   private final ExecutorService executor;
   private final Pattern durationPattern;
   private static HypixelBanChecker INSTANCE;
   private static final long KEY = KeyedRandom.create(-8924784802156313973L, 4613851900784897891L, MethodHandles.lookup().lookupClass()).next(180521940672872L);
   private static String[] STRINGS;
   private static long[] encryptedConstants;
   private static Long[] decryptedConstants;
   private static Map threadCipherCache;

   public HypixelBanChecker() {
      String[] strings = STRINGS;
      this.host = strings[1];
      this.executor = Executors.newCachedThreadPool(runnable -> {
         Thread var1x = new Thread(runnable, STRINGS[2]);
         var1x.setDaemon(true);
         return var1x;
      });
      this.durationPattern = Pattern.compile(strings[5]);
   }

   @Subscribe
   private void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 19423074533392L;
      String flowGuard = ClientState.getFlowGuard();
      Packet packet = event.getPacket();
      String text = flowGuard;
      Packet packet2 = packet;
      if (text == null) {
         if (!(packet instanceof LoginDisconnectS2CPacket)) {
            return;
         }

         packet2 = packet;
      }

      LoginDisconnectS2CPacket packet3 = (LoginDisconnectS2CPacket)packet2;
      LoginDisconnectS2CPacket packet4 = packet3;
      Text text2;

      try {
         text2 = packet4.reason();
      } catch (Throwable throwable) {
         throw new MatchException(throwable.toString(), throwable);
      }

      Text text3 = text2;
      Text text4 = text3;
      CompletableFuture completableFuture = this.banStatusFuture;
      if (text == null) {
         if (this.banStatusFuture == null) {
            return;
         }

         completableFuture = this.banStatusFuture;
      }

      if (!completableFuture.isDone()) {
         String string = text4.getString();
         System.out.println(string);
         boolean contains = string.contains(STRINGS[0]);
         if (text == null) {
            if (contains) {
               this.banStatusFuture.complete(BanStatus.SUSPICIOUS_BAN);
               if (text == null) {
                  return;
               }
            }

            contains = string.contains(STRINGS[7]);
         }

         if (text == null) {
            if (contains) {
               this.banStatusFuture.complete(BanStatus.PERMANENT_BAN);
               if (text == null) {
                  return;
               }
            }

            contains = string.contains(STRINGS[6]);
         }

         if (text == null) {
            if (contains) {
               this.banStatusFuture.complete(BanStatus.TEMPORARY_BAN);
               if (text == null) {
                  return;
               }
            }

            contains = string.contains(STRINGS[3]);
         }

         if (text == null) {
            if (contains) {
               this.banStatusFuture.complete(BanStatus.UNBANNED);
               if (text == null) {
                  return;
               }
            }

            this.banStatusFuture.complete(BanStatus.UNKNOWN);
         }
      }
   }

   public BanStatus checkBan(AccountData account) {
      long longValue = KEY ^ 85764296955436L;
      this.banStatusFuture = new CompletableFuture<>();
      this.executor.execute(() -> this.performBanCheck(account));

      try {
         long longValue2 = 9041053682633843313L ^ longValue;
         short shortValue = 26756;
         return this.banStatusFuture
            .get(
               ((Number)IndyRuntime.invoke(
                     "club.evon.api.HypixelBanChecker",
                     "bootstrap",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "y",
                     "(IJ)J",
                     new Object[]{Integer.valueOf(shortValue), longValue2}
                  ))
                  .longValue(),
               TimeUnit.SECONDS
            );
      } catch (Throwable throwable) {
         if (throwable instanceof Error) {
            throw (Error)throwable;
         }

         return BanStatus.UNKNOWN;
      } finally {
         this.banStatusFuture = null;
      }
   }

   private void performBanCheck(AccountData account) {
      long longValue = KEY ^ 45967800642958L;
      String flowGuard = ClientState.getFlowGuard();

      try {
         String[] strings = STRINGS;
         Optional map = AllowedAddressResolver.DEFAULT.resolve(ServerAddress.parse(strings[4])).map(Address::getInetSocketAddress);
         Object tmp = map;
         if (flowGuard == null) {
            if (map.isEmpty()) {
               this.banStatusFuture.complete(BanStatus.UNBANNED);
               return;
            }

            tmp = map.get();
         }

         InetSocketAddress inetSocketAddress = (InetSocketAddress)tmp;
         ClientConnection clientConnection = new ClientConnection(NetworkSide.CLIENTBOUND);
         clientConnection.resetPacketSizeLog(ClientContext.MINECRAFT_CLIENT.getDebugHud().getPacketSizeLog());
         ChannelFuture connect = ClientConnection.connect(inetSocketAddress, ClientContext.MINECRAFT_CLIENT.options.shouldUseNativeTransport(), clientConnection);
         connect.syncUninterruptibly();
         String hostName = inetSocketAddress.getHostName();
         int value = inetSocketAddress.getPort();
         strings = STRINGS;
         clientConnection.connect(
            hostName,
            value,
            LoginStates.C2S,
            LoginStates.S2C,
            new ClientLoginNetworkHandler(
               clientConnection, ClientContext.MINECRAFT_CLIENT, new ServerInfo(strings[1], strings[1], ServerType.OTHER), null, false, null, this::handleLoginDisconnect, new ClientChunkLoadProgress(), null
            ),
            false
         );
         clientConnection.send(new LoginHelloC2SPacket(account.getUsername(), MicrosoftLoginManager.parseUuid(account.getUuid())));
      } catch (Exception exception) {
         this.banStatusFuture.completeExceptionally(exception);
      }
   }

   private void handleLoginDisconnect(Text reason) {
      long longValue = KEY ^ 25516301511235L;
      String flowGuard = ClientState.getFlowGuard();
      HypixelBanChecker tmp = this;
      if (flowGuard == null) {
         if (this.banStatusFuture == null) {
            return;
         }

         tmp = this;
      }

      tmp.disconnectMessage = reason.getString();
   }

   public long parseBanDuration(String input) {
      try {
      long longValue = KEY ^ 14082970627044L;
      Matcher matcher = this.durationPattern.matcher(input.toLowerCase().trim());
      if (!matcher.matches()) {
         return 0L;
      }

      long longValue2 = this.parseDurationPart(matcher, 1);
      long longValue3 = this.parseDurationPart(matcher, 2);
      long longValue4 = this.parseDurationPart(matcher, 3);
      long longValue5 = this.parseDurationPart(matcher, 4);
      long longValue6 = 2992664134239497391L ^ longValue;
      short shortValue = 7057;
      long longValue7 = longValue2
            * ((Number)IndyRuntime.invoke(
                  "club.evon.api.HypixelBanChecker",
                  "bootstrap",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue), longValue6}
               ))
               .longValue()
         + longValue3;
      long longValue8 = 5230867305777400450L ^ longValue;
      short shortValue2 = 15806;
      longValue7 = longValue7
            * ((Number)IndyRuntime.invoke(
                  "club.evon.api.HypixelBanChecker",
                  "bootstrap",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                  new Object[]{Integer.valueOf(shortValue2), longValue8}
               ))
               .longValue()
         + longValue4;
      long longValue9 = 6243281496272187326L ^ longValue;
      short shortValue3 = 6273;
      return longValue7
            * ((Number)IndyRuntime.invoke(
                  "club.evon.api.HypixelBanChecker",
                  "bootstrap",
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                  "y",
                  "(IJ)J",
                   new Object[]{Integer.valueOf(shortValue3), longValue9}
                ))
                .longValue()
         + longValue5;
      } catch (Throwable throwable) {
         throw sneakyThrow(throwable);
      }
   }

   private long parseDurationPart(Matcher matcher, int group) {
      long longValue = KEY ^ 75966519317312L;
      String flowGuard = ClientState.getFlowGuard();
      String group2 = matcher.group(group);
      String text = flowGuard;
      flowGuard = group2;
      if (text == null) {
         if (group2 == null) {
            return 0L;
         }

         flowGuard = group2;
      }

      return Long.parseLong(flowGuard);
   }

   public static void init() {
      INSTANCE = new HypixelBanChecker();
      EventBus.register(INSTANCE);
   }

   public static HypixelBanChecker getInstance() {
      return INSTANCE;
   }

   static {
      try {
      long var11 = KEY ^ 134486671204452L;
      Cipher var13;
      Cipher var10000 = var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var14 = 1; var14 < 8; var14++) {
         var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var20 = new String[8];
      int var18 = 0;
      String var17 = "9\u00e6e\u00a4HaZ\u0091o\u001cT\u00cftx\r\u0091$Uu\u00e85M\u00f0X\u0018\u0086-\u00c6\u00fct@\u00e7I&\u007fXF\u00e1!\u0014\u0099J\u00e8\u00ea\u00a7\u00e8\u001e55\u0010k\u00c3\u00c0\u00c8U\u00e6\u0001{\u001ccH\u00b2\u00b9\u00d9\u00ec(\u0018\u00c5\u0084\u0017\u00bf\u00f3\u00ffQ\u008a*\u0084\u0014%\u00ed\u0003\u00d2\u008b\u00cd3\u000b/a\u0091\u0097\u00fc\u0018\u0086-\u00c6\u00fct@\u00e7I&\u007fXF\u00e1!\u0014\u0099J\u00e8\u00ea\u00a7\u00e8\u001e558b\u0094\u009c\u0096\u00a3I\u0087\u00fb\u00ad\u00b9\u00f7\u00f8\u00c5(!X\u00c1\u00f4fR\u0014\u00fdj\u0002\u00d2^\u00c1\r\u001c\u009a\u00cd>*P W9\u00d1\u0091\u00fc.>&K\u00c7\u00c8\u0018\u009dKF\u0011\u00b1\u00cf\u0015\u00b4]";
      int var19 = "9\u00e6e\u00a4HaZ\u0091o\u001cT\u00cftx\r\u0091$Uu\u00e85M\u00f0X\u0018\u0086-\u00c6\u00fct@\u00e7I&\u007fXF\u00e1!\u0014\u0099J\u00e8\u00ea\u00a7\u00e8\u001e55\u0010k\u00c3\u00c0\u00c8U\u00e6\u0001{\u001ccH\u00b2\u00b9\u00d9\u00ec(\u0018\u00c5\u0084\u0017\u00bf\u00f3\u00ffQ\u008a*\u0084\u0014%\u00ed\u0003\u00d2\u008b\u00cd3\u000b/a\u0091\u0097\u00fc\u0018\u0086-\u00c6\u00fct@\u00e7I&\u007fXF\u00e1!\u0014\u0099J\u00e8\u00ea\u00a7\u00e8\u001e558b\u0094\u009c\u0096\u00a3I\u0087\u00fb\u00ad\u00b9\u00f7\u00f8\u00c5(!X\u00c1\u00f4fR\u0014\u00fdj\u0002\u00d2^\u00c1\r\u001c\u009a\u00cd>*P W9\u00d1\u0091\u00fc.>&K\u00c7\u00c8\u0018\u009dKF\u0011\u00b1\u00cf\u0015\u00b4]"
         .length();
      char var16 = 24;
      int var24 = -1;

      label54:
      while (true) {
         String var25 = var17.substring(++var24, var24 + var16);
         int var10001 = -1;

         while (true) {
            byte[] var21 = var13.doFinal(var25.getBytes("ISO-8859-1"));
            String var36 = decodeUtf8(var21).intern();
            switch (var10001) {
               case 0:
                  var20[var18++] = var36;
                  if ((var24 += var16) >= var19) {
                     STRINGS = var20;
                     threadCipherCache = new HashMap(13);
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long[] var6 = new long[4];
                     int var3 = 0;
                     String var4 = "\u00fc\t\u00eec\u00e2lh\u00bb\u00a5\u0017\u00f0\u00947_$\u00ac";
                     int var5 = "\u00fc\t\u00eec\u00e2lh\u00bb\u00a5\u0017\u00f0\u00947_$\u00ac".length();
                     int var2 = 0;

                     label36:
                     while (true) {
                        var10001 = var2;
                        var2 += 8;
                        byte[] var7 = var4.substring(var10001, var2).getBytes("ISO-8859-1");
                        long[] var28 = var6;
                        var10001 = var3++;
                        long var40 = (var7[0] & 255L) << 56
                           | (var7[1] & 255L) << 48
                           | (var7[2] & 255L) << 40
                           | (var7[3] & 255L) << 32
                           | (var7[4] & 255L) << 24
                           | (var7[5] & 255L) << 16
                           | (var7[6] & 255L) << 8
                           | var7[7] & 255L;
                        byte var43 = -1;

                        while (true) {
                           long var8 = var40;
                           byte[] var10 = var0.doFinal(
                              new byte[]{
                                 (byte)(var8 >>> 56),
                                 (byte)(var8 >>> 48),
                                 (byte)(var8 >>> 40),
                                 (byte)(var8 >>> 32),
                                 (byte)(var8 >>> 24),
                                 (byte)(var8 >>> 16),
                                 (byte)(var8 >>> 8),
                                 (byte)var8
                              }
                           );
                           long var45 = (var10[0] & 255L) << 56
                              | (var10[1] & 255L) << 48
                              | (var10[2] & 255L) << 40
                              | (var10[3] & 255L) << 32
                              | (var10[4] & 255L) << 24
                              | (var10[5] & 255L) << 16
                              | (var10[6] & 255L) << 8
                              | var10[7] & 255L;
                           switch (var43) {
                              case 0:
                                 var28[var10001] = var45;
                                  if (var2 >= var5) {
                                     encryptedConstants = var6;
                                     decryptedConstants = new Long[4];
                                     break label54;
                                 }
                                 break;
                              default:
                                 var28[var10001] = var45;
                                 if (var2 < var5) {
                                    continue label36;
                                 }

                                 var4 = "\u00a9\u0011\u000b\u00a14\u00b81\u00b1\r\u00da\u00b0\u00c0\u000f\u00d1\u00d3\u00f6";
                                 var5 = "\u00a9\u0011\u000b\u00a14\u00b81\u00b1\r\u00da\u00b0\u00c0\u000f\u00d1\u00d3\u00f6".length();
                                 var2 = 0;
                           }

                           int var34 = var2;
                           var2 += 8;
                           var7 = var4.substring(var34, var2).getBytes("ISO-8859-1");
                           var28 = var6;
                           var10001 = var3++;
                           var40 = (var7[0] & 255L) << 56
                              | (var7[1] & 255L) << 48
                              | (var7[2] & 255L) << 40
                              | (var7[3] & 255L) << 32
                              | (var7[4] & 255L) << 24
                              | (var7[5] & 255L) << 16
                              | (var7[6] & 255L) << 8
                              | var7[7] & 255L;
                           var43 = 0;
                        }
                     }
                  }

                  var16 = var17.charAt(var24);
                  break;
               default:
                  var20[var18++] = var36;
                  if ((var24 += var16) < var19) {
                     var16 = var17.charAt(var24);
                     continue label54;
                  }

                  var17 = "\u00be\u0012\u0092\u0094t\u00ae\r\u00dc\u00c5q\u0097512\u00fd\u000e\u0087\u00d9\u0093\u00eft\b\u00d3J0\u00aeMq\u0097\u00a3\u00ed\u00e0=!\u00ab\u00d5\u00898\u00bf\u00ee?\u00ecu\u00ac=|\u00c7\u0087\u00ca\u00a6\u00a9\u0004\fa\u00ad<\u00d2\u0001\u00e0t4\f\u00f3\r\u00f4Y/\u00e3\u00a4-\u008b\n\u0017";
                  var19 = "\u00be\u0012\u0092\u0094t\u00ae\r\u00dc\u00c5q\u0097512\u00fd\u000e\u0087\u00d9\u0093\u00eft\b\u00d3J0\u00aeMq\u0097\u00a3\u00ed\u00e0=!\u00ab\u00d5\u00898\u00bf\u00ee?\u00ecu\u00ac=|\u00c7\u0087\u00ca\u00a6\u00a9\u0004\fa\u00ad<\u00d2\u0001\u00e0t4\f\u00f3\r\u00f4Y/\u00e3\u00a4-\u008b\n\u0017"
                     .length();
                  var16 = 24;
                  var24 = -1;
            }

            var25 = var17.substring(++var24, var24 + var16);
            var10001 = 0;
         }
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static Throwable identity(Throwable throwable) {
      return throwable;
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
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

   private static long decryptConstant(int index, long encrypted) {
      int value = index ^ (int)(encrypted & 32767L) ^ 16701;
      if (decryptedConstants[value] == null) {
         byte[] bytes = new byte[]{
            (byte)(encrypted >>> 56),
            (byte)(encrypted >>> 48),
            (byte)(encrypted >>> 40),
            (byte)(encrypted >>> 32),
            (byte)(encrypted >>> 24),
            (byte)(encrypted >>> 16),
            (byte)(encrypted >>> 8),
            (byte)encrypted
         };
         long longValue = encryptedConstants[value];
         byte[] bytes2 = new byte[]{
            (byte)(longValue >>> 56),
            (byte)(longValue >>> 48),
            (byte)(longValue >>> 40),
            (byte)(longValue >>> 32),
            (byte)(longValue >>> 24),
            (byte)(longValue >>> 16),
            (byte)(longValue >>> 8),
            (byte)longValue
         };
         Long threadId = Thread.currentThread().threadId();
         Object[] values = (Object[])threadCipherCache.get(threadId);

         byte[] bytes3;
         try {
            if (values == null) {
               values = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
               threadCipherCache.put(threadId, values);
            }

            DESKeySpec dESKeySpec = new DESKeySpec(bytes);
            SecretKey generateSecret = ((SecretKeyFactory)values[1]).generateSecret(dESKeySpec);
            Cipher cipher = (Cipher)values[0];
            cipher.init(2, generateSecret, (IvParameterSpec)values[2]);
            bytes3 = cipher.doFinal(bytes2);
         } catch (Exception exception) {
            throw new RuntimeException("club/evon/api/HypixelBanChecker", exception);
         }

         long longValue2 = (bytes3[0] & 255L) << 56
            | (bytes3[1] & 255L) << 48
            | (bytes3[2] & 255L) << 40
            | (bytes3[3] & 255L) << 32
            | (bytes3[4] & 255L) << 24
            | (bytes3[5] & 255L) << 16
            | (bytes3[6] & 255L) << 8
            | bytes3[7] & 255L;
         decryptedConstants[value] = longValue2;
      }

      return decryptedConstants[value];
   }

   private static long bootstrapConstant(Lookup lookup, MutableCallSite callSite, String name, Object[] args) {
      int value = (Integer)args[0];
      long longValue = (Long)args[1];
      long longValue2 = decryptConstant(value, longValue);
      MethodHandle constant = MethodHandles.constant(long.class, longValue2);
      callSite.setTarget(MethodHandles.dropArguments(constant, 0, int.class, long.class));
      return longValue2;
   }

   private static CallSite bootstrap(Lookup lookup, String name, MethodType type) {
      MutableCallSite mutableCallSite = new MutableCallSite(type);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(
                  MethodHandles.lookup()
                     .findStatic(HypixelBanChecker.class, "bootstrapConstant", MethodType.methodType(long.class, Lookup.class, MutableCallSite.class, String.class, Object[].class))
                     .asCollector(Object[].class, type.parameterCount()),
                  0,
                  lookup,
                  mutableCallSite,
                  name
               ),
               type
            )
         );
         return mutableCallSite;
      } catch (Exception exception) {
         throw new RuntimeException("club/evon/api/HypixelBanChecker : " + name + " : " + type.toString(), exception);
      }
   }
}
