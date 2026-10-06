package club.evon.module.impl.misc;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.api.CubecraftServer;
import club.evon.data.TimestampedPacket;
import club.evon.event.JoinWorldEvent;
import club.evon.event.ModuleToggleEvent;
import club.evon.event.MotionEvent;
import club.evon.event.OutboundPacketEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.manager.ModuleManager;
import club.evon.mixins.PlayerMoveC2SPacketAccessor;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.module.impl.motion.Flight;
import club.evon.module.impl.motion.Scaffold;
import club.evon.module.impl.motion.Speed;
import club.evon.runtime.IndyRuntime;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.Stopwatch;
import club.evon.util.math.RandomUtil;
import club.evon.util.net.NetworkUtil;




public final class Cubecraft2Disabler extends SubMode<Disabler> {
   private final Queue<TimestampedPacket> pendingKeepAlives = new ConcurrentLinkedQueue<>();
   private final Queue<TimestampedPacket> B = new ConcurrentLinkedQueue<>();
   private int keepAliveDelay;
   private int k;
   private int D;
   private int p;
   private int motionCounter;
   private int K;
   private int o;
   private int packetLimit;
   private boolean A;
   private boolean highLatency;
   private boolean E;
   private boolean f;
   private Vec3d lastPosition = new Vec3d(0.0, 0.0, 0.0);
   private int packetCounter;
   private final Stopwatch X = new Stopwatch();
   private static boolean enabled;
   private static final long KEY = KeyedRandom.create(-824772354445988898L, 4089600218925095007L, MethodHandles.lookup().lookupClass()).next(47902513022056L);
   private static final String[] STRINGS_TABLE;
   private static final long[] encryptedValues;
   private static final Integer[] decryptedValues;
   private static final Map cipherCache;

   public Cubecraft2Disabler(String name, Disabler parent) {
      super(name, parent);
   }

   @Override
   public void onEnable() {
      long longValue = KEY ^ 87408728586927L;
      long longValue2 = 5559080389542701610L ^ longValue;
      short shortValue = 22465;
      this.motionCounter = ((Number)invokeIndySafe(
            "m",
            "(IJ)I",
            new Object[]{Integer.valueOf(shortValue), longValue2}
         ))
         .intValue();
      boolean enabled2 = isEnabled();
      long longValue3 = 6869722621859529923L ^ longValue;
      short shortValue2 = 14633;
      this.packetLimit = ((Number)invokeIndySafe(
            "m",
            "(IJ)I",
            new Object[]{Integer.valueOf(shortValue2), longValue3}
         ))
         .intValue();
      this.keepAliveDelay = RandomUtil.randomInt(4800, 5000);
      this.highLatency = this.E = false;
      boolean flag = enabled2;
      this.packetCounter = 0;
      super.onEnable();
      if (flag) {
         BaseModule.setStateArray(new BaseModule[3]);
      }
   }

   @Subscribe
   private void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 21452319149237L;
      boolean enabled2 = isEnabled();
      Packet packet = event.getPacket();
      boolean flag = enabled2;
      if (!(ClientState.getInstance().getServerDetector().getServerProfile() instanceof CubecraftServer)) {
      }

      enabled2 = packet instanceof PlayerPositionLookS2CPacket;
      label56:
      if (!flag) {
         label47:
         if (!enabled2) {
            label61: {
               Packet packet2 = packet;
               if (!flag) {
                  if (!(packet instanceof HealthUpdateS2CPacket)) {
                     break label61;
                  }

                  packet2 = packet;
               }

               HealthUpdateS2CPacket packet3 = (HealthUpdateS2CPacket)packet2;
               float floatValue;
               byte var10000s = (byte)((floatValue = packet3.getHealth() - 0.0F) == 0.0F ? 0 : (floatValue < 0.0F ? -1 : 1));
               if (flag) {
                  break label56;
               }

               if (var10000s <= 0) {
                  break label47;
               }
            }

            enabled2 = false;
            break label56;
         }

         enabled2 = true;
      }

      boolean flag2 = enabled2;
      if (flag2) {
      }

      Packet packet4 = packet;
      if (!flag) {
         if (!(packet instanceof KeepAliveS2CPacket)) {
            return;
         }

         packet4 = packet;
      }

      KeepAliveS2CPacket packet5 = (KeepAliveS2CPacket)packet4;
      KeepAliveC2SPacket keepAliveC2SPacket = new KeepAliveC2SPacket(packet5.getId());
      long longValue2 = System.currentTimeMillis() + this.keepAliveDelay;
      this.pendingKeepAlives.add(new TimestampedPacket(keepAliveC2SPacket, longValue2));
      event.cancel();
   }

   @Subscribe
   private void onOutboundPacket(OutboundPacketEvent event) {
      long longValue = KEY ^ 61488613772461L;
      boolean disabled = isDisabled();
      Packet packet = event.getPacket();
      int index = 0;
      boolean var10000b = false;
      Cubecraft2Disabler cubecraft2Disabler = null;
      Packet packet2 = null;
      if (!(ClientState.getInstance().getServerDetector().getServerProfile() instanceof CubecraftServer)) {
      }

      if (packet instanceof KeepAliveC2SPacket) {
         event.cancel();
      }

      label339: {
         ClientPlayerEntity player = ClientContext.MINECRAFT_CLIENT.player;
         if (disabled) {
            if (ClientContext.MINECRAFT_CLIENT.player == null) {
               break label339;
            }

            player = ClientContext.MINECRAFT_CLIENT.player;
         }

         index = player.age;
         if (disabled) {
            if (player.age <= 20) {
               break label339;
            }

            var10000b = packet instanceof PlayerMoveC2SPacket;
         }

         if (disabled) {
            if (var10000b) {
               PlayerMoveC2SPacket player2;
               PlayerMoveC2SPacketAccessor player3;
               label317: {
                  label291: {
                     player2 = (PlayerMoveC2SPacket)packet;
                     player3 = (PlayerMoveC2SPacketAccessor)player2;
                     index = this.packetCounter;
                     byte byteValue = 3;
                     if (disabled) {
                        if (this.packetCounter < 3) {
                           break label317;
                        }

                        index = this.packetCounter;
                        if (!disabled) {
                           break label291;
                        }

                        byteValue = 8;
                     }

                     index = index <= byteValue ? 1 : 0;
                  }

                  int value = index;
                  index = this.packetCounter;
                  if (disabled) {
                     index = this.packetCounter >= this.packetLimit - 2 ? 1 : 0;
                  }

                  label281: {
                     label280: {
                        label319: {
                           int value2 = index;
                           index = value;
                           if (disabled) {
                              if (value != 0) {
                                 break label319;
                              }

                              index = value2;
                           }

                           if (!disabled) {
                              break label281;
                           }

                           if (index == 0) {
                              break label280;
                           }
                        }

                        event.cancel();
                     }

                     index = this.packetCounter % 55;
                  }

                  if (index == 0) {
                     NetworkUtil.sendManagedPacket(new OnGroundOnly(true, ClientContext.MINECRAFT_CLIENT.player.horizontalCollision));
                  }
               }

               label320: {
                  Scaffold moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Scaffold.class);
                  MinecraftClient minecraftClient = ClientContext.MINECRAFT_CLIENT;
                  if (disabled) {
                     if (ClientContext.MINECRAFT_CLIENT.currentScreen != null) {
                        break label320;
                     }

                     minecraftClient = ClientContext.MINECRAFT_CLIENT;
                  }

                  boolean usingItem = minecraftClient.player.isUsingItem();
                  if (disabled) {
                     if (usingItem) {
                        break label320;
                     }

                     usingItem = moduleByClass.isEnabled();
                  }

                  if (!usingItem) {
                     Hand activeHand = ClientContext.MINECRAFT_CLIENT.player.getActiveHand();
                     if (disabled) {
                        activeHand = activeHand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
                     }

                     Hand hand = activeHand;
                     BlockHitResult blockHitResult = new BlockHitResult(ClientContext.MINECRAFT_CLIENT.player.getBlockPos().toCenterPos(), Direction.UP, ClientContext.MINECRAFT_CLIENT.player.getBlockPos(), false);
                     NetworkUtil.sendSequencedPacket(var2x -> new PlayerInteractBlockC2SPacket(hand, blockHitResult, var2x));
                  }
               }

               label257: {
                  double doubleValue = player2.getY(ClientContext.MINECRAFT_CLIENT.player.getY());
                  doubleValue -= doubleValue % 0.015625;
                  player3.setY(doubleValue);
                  boolean changesPosition = player2.changesPosition();
                  if (disabled) {
                     if (!changesPosition) {
                        break label257;
                     }

                     changesPosition = event.isCanceled();
                  }

                  if (!changesPosition) {
                     this.lastPosition = new Vec3d(player2.x, player2.y, player2.z);
                  }
               }

               if (disabled) {
                  return;
               }
            }

            var10000b = packet instanceof CommonPongC2SPacket;
         }

         label321: {
            if (disabled) {
               if (var10000b) {
                  CommonPongC2SPacket packet3 = (CommonPongC2SPacket)packet;
                  int value3 = packet3.getParameter();
                  index = this.packetCounter;
                  if (disabled) {
                     if (this.packetCounter == 1) {
                        this.packetCounter++;
                     }

                     index = value3;
                  }

                  label324: {
                     label241:
                     if (disabled) {
                        label239:
                        if (index < 0) {
                           Cubecraft2Disabler cubecraft2Disabler2 = this;
                           if (disabled) {
                              if (this.packetCounter < 3) {
                                 index = this.packetCounter;
                                 if (!disabled) {
                                    break label241;
                                 }

                                 if (this.packetCounter != 0) {
                                    break label239;
                                 }
                              }

                              cubecraft2Disabler2 = this;
                           }

                           cubecraft2Disabler2.packetCounter++;
                           NetworkUtil.sendManagedPacket(new CommonPongC2SPacket(value3 + 1));
                           if (disabled) {
                              break label324;
                           }
                        }

                        index = value3;
                     }

                     label226: {
                        label325: {
                           if (disabled) {
                              if (index == 0) {
                                 break label324;
                              }

                              cubecraft2Disabler = this;
                              if (!disabled) {
                                 break label325;
                              }

                              index = this.packetCounter;
                           }

                           if (index < this.packetLimit) {
                              cubecraft2Disabler = this;
                              if (!disabled) {
                                 break label325;
                              }

                              if (this.packetCounter != 2) {
                                 break label226;
                              }
                           }

                           NetworkUtil.sendManagedPacket(new CommonPongC2SPacket(value3 + 3));
                           cubecraft2Disabler = this;
                        }

                        long longValue2 = 1868381400612063662L ^ longValue;
                        short shortValue = 581;
                        cubecraft2Disabler.packetCounter = ((Number)invokeIndySafe(
                              "m",
                              "(IJ)I",
                              new Object[]{Integer.valueOf(shortValue), longValue2}
                           ))
                           .intValue();
                     }

                     event.cancel();
                  }

                  if (disabled) {
                     return;
                  }
               }

               packet2 = packet;
               if (!disabled) {
                  break label321;
               }

               var10000b = packet instanceof PlayerInteractEntityC2SPacket;
            }

            if (!var10000b) {
               return;
            }

            packet2 = packet;
         }

         PlayerInteractEntityC2SPacket player4 = (PlayerInteractEntityC2SPacket)packet2;
         if (player4.type == PlayerInteractEntityC2SPacket.ATTACK) {
            ClientWorld world = ClientContext.MINECRAFT_CLIENT.world;
            if (disabled) {
               if (ClientContext.MINECRAFT_CLIENT.world == null) {
                  return;
               }

               world = ClientContext.MINECRAFT_CLIENT.world;
            }

            Entity entityById = world.getEntityById(player4.entityId);
            if (entityById == null) {
               return;
            }

            double doubleValue2 = this.lastPosition.distanceTo(PlayerUtil.closestPointOnBox(this.lastPosition, entityById.getBoundingBox().expand(entityById.getTargetingMargin())));
            double doubleValue3 = doubleValue2;
            if (disabled) {
               if (!(doubleValue2 >= 4.3)) {
                  return;
               }

               event.cancel();
               doubleValue3 = doubleValue2;
            }

            ChatUtil.sendMessage(STRINGS_TABLE[0] + doubleValue3);
         }

         return;
      }

      this.f = false;
   }

   @Subscribe
   private void onModuleToggle(ModuleToggleEvent event) {
      long longValue = KEY ^ 79163097186471L;
      boolean disabled = isDisabled();
      ModuleToggleEvent event2 = null;
      boolean tmp = this.highLatency;
      if (disabled) {
         if (this.highLatency) {
            return;
         }

         tmp = event.isEnabled();
      }

      label52: {
         if (disabled) {
            if (!tmp) {
               return;
            }

            event2 = event;
            if (!disabled) {
               break label52;
            }

            tmp = event.getModule() instanceof Speed;
         }

         if (!tmp) {
            event2 = event;
            if (!disabled) {
               break label52;
            }

            if (!(event.getModule() instanceof Flight)) {
               return;
            }
         }

         event2 = event;
      }

      event2.cancel();
      ChatUtil.sendMessage(STRINGS_TABLE[2]);
   }

   @Subscribe
   private void onMotion(MotionEvent event) {
      long longValue = KEY ^ 35694372404620L;
      boolean enabled2 = isEnabled();
      this.motionCounter++;
      boolean flag = enabled2;
      Cubecraft2Disabler cubecraft2Disabler = this;
      Flight flight = null;
      if (!flag) {
         label61:
         if (!this.highLatency) {
            PlayerListEntry player;
            label71: {
               label70: {
                  player = ClientContext.MINECRAFT_CLIENT.getNetworkHandler().getPlayerListEntry(ClientContext.MINECRAFT_CLIENT.player.getUuid());
                  ModuleManager moduleManager = EvonClient.getInstance().getModuleManager();
                  Speed moduleByClass = moduleManager.getModuleByClass(Speed.class);
                  Flight moduleByClass2 = moduleManager.getModuleByClass(Flight.class);
                  Speed speed = moduleByClass;
                  if (!flag) {
                     if (!moduleByClass.isEnabled()) {
                        flight = moduleByClass2;
                        if (flag) {
                           break label70;
                        }

                        if (!moduleByClass2.isEnabled()) {
                           break label71;
                        }
                     }

                     ChatUtil.sendMessage(STRINGS_TABLE[3]);
                     speed = moduleByClass;
                  }

                  speed.setEnabled(false);
                  flight = moduleByClass2;
               }

               flight.setEnabled(false);
            }

            PlayerListEntry player2 = player;
            if (!flag) {
               if (player == null) {
                  break label61;
               }

               player2 = player;
            }

            if (player2.getLatency() > 500) {
               ChatUtil.sendMessage(STRINGS_TABLE[1]);
               this.highLatency = true;
            }
         }

         cubecraft2Disabler = this;
      }

      Iterator iterator = cubecraft2Disabler.pendingKeepAlives.iterator();

      while (iterator.hasNext()) {
         label50: {
            TimestampedPacket packet = (TimestampedPacket)iterator.next();
            if (!flag) {
               if (System.currentTimeMillis() < packet.timestamp()) {
                  break label50;
               }

               NetworkUtil.sendManagedPacket(packet.getPacket());
            }

            iterator.remove();
         }

         if (flag) {
            break;
         }
      }
   }

   @Subscribe
   private void onJoinWorld(JoinWorldEvent event) {
      this.packetCounter = 0;
      this.K = 0;
      this.o = 0;
      this.highLatency = false;
   }

   @Override
   public void onDisable() {
      this.B.clear();
      this.pendingKeepAlives.clear();
      super.onDisable();
   }

   public static void setEnabled(boolean flag) {
      enabled = flag;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
   }

   static {
      try {
      long var11 = KEY ^ 60324058339225L;
      setEnabled(false);
      Cipher var13;
      Cipher var10000 = var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var14 = 1; var14 < 8; var14++) {
         var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var20 = new String[4];
      int var18 = 0;
      String var17 = "\u00c1f\u0010!\u00f4\u00d2\u00ef\u00fa\u00b8\t`\u00f8\u00edo\u0099\u0005A\u00ea'\u00f8zv\u00910\u0018\u00b6\u0099\nJ\u009b\u0085\u00fbl\u0010\u00e7J\u00b9\t\u00a8w\u00843\u0092\u008b\u0002\u0004\u0095Z9";
      int var19 = "\u00c1f\u0010!\u00f4\u00d2\u00ef\u00fa\u00b8\t`\u00f8\u00edo\u0099\u0005A\u00ea'\u00f8zv\u00910\u0018\u00b6\u0099\nJ\u009b\u0085\u00fbl\u0010\u00e7J\u00b9\t\u00a8w\u00843\u0092\u008b\u0002\u0004\u0095Z9"
         .length();
      char var16 = 24;
      int var23 = -1;

      label45:
      while (true) {
         String var24 = var17.substring(++var23, var23 + var16);
         int var10001 = -1;

         while (true) {
            byte[] var21 = var13.doFinal(var24.getBytes("ISO-8859-1"));
            String var33 = decodeUtf8(var21).intern();
            switch (var10001) {
               case 0:
                  var20[var18++] = var33;
                  if ((var23 += var16) >= var19) {
                     STRINGS_TABLE = var20;
                     cipherCache = new HashMap(13);
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long[] var6 = new long[3];
                     int var3 = 0;
                     String var4 = "\b\u0092\u0088\u0091\u00e8\u00cb&\u00e4\u00fe\u00ec\nc\u0007\u00c8\u0014\u0015\u00c1|I\u00b8\u009e\u001aE\u00d0";
                     int var5 = "\b\u0092\u0088\u0091\u00e8\u00cb&\u00e4\u00fe\u00ec\nc\u0007\u00c8\u0014\u0015\u00c1|I\u00b8\u009e\u001aE\u00d0".length();
                     int var2 = 0;

                     do {
                        var10001 = var2;
                        var2 += 8;
                        byte[] var7 = var4.substring(var10001, var2).getBytes("ISO-8859-1");
                        var10001 = var3++;
                        long var8 = (var7[0] & 255L) << 56
                           | (var7[1] & 255L) << 48
                           | (var7[2] & 255L) << 40
                           | (var7[3] & 255L) << 32
                           | (var7[4] & 255L) << 24
                           | (var7[5] & 255L) << 16
                           | (var7[6] & 255L) << 8
                           | var7[7] & 255L;
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
                        long var10004 = (var10[0] & 255L) << 56
                           | (var10[1] & 255L) << 48
                           | (var10[2] & 255L) << 40
                           | (var10[3] & 255L) << 32
                           | (var10[4] & 255L) << 24
                           | (var10[5] & 255L) << 16
                           | (var10[6] & 255L) << 8
                           | var10[7] & 255L;
                        byte var38 = -1;
                        var6[var10001] = var10004;
                     } while (var2 < var5);

                     encryptedValues = var6;
                     decryptedValues = new Integer[3];
                     break label45;
                  }

                  var16 = var17.charAt(var23);
                  break;
               default:
                  var20[var18++] = var33;
                  if ((var23 += var16) < var19) {
                     var16 = var17.charAt(var23);
                     continue label45;
                  }

                  var17 = "\u00b4\u00f7\u001d\u008a\u00b4\u009ar{\u00b5_-\u00df5\t-W@\u00be\u001f\u001a\u0082\u000fO;\u001c\"\u00c4U\u001fG\u00f2\u00e2a\u0093\u00d8w\u0000\u0082j\u00aao\u00c15X\u00cd\u0093}\u000b'v\u00ff\u00eb\u00a5\u000f:'8\u00b4\u00f7\u001d\u008a\u00b4\u009ar{\u00b5_-\u00df5\t-W@\u00be\u001f\u001a\u0082\u000fO;\u001c\"\u00c4U\u001fG\u00f2\u00e2a\u0093\u00d8w\u0000\u0082j\u00aao\u00c15X\u00cd\u0093}\u000b'v\u00ff\u00eb\u00a5\u000f:'";
                  var19 = "\u00b4\u00f7\u001d\u008a\u00b4\u009ar{\u00b5_-\u00df5\t-W@\u00be\u001f\u001a\u0082\u000fO;\u001c\"\u00c4U\u001fG\u00f2\u00e2a\u0093\u00d8w\u0000\u0082j\u00aao\u00c15X\u00cd\u0093}\u000b'v\u00ff\u00eb\u00a5\u000f:'8\u00b4\u00f7\u001d\u008a\u00b4\u009ar{\u00b5_-\u00df5\t-W@\u00be\u001f\u001a\u0082\u000fO;\u001c\"\u00c4U\u001fG\u00f2\u00e2a\u0093\u00d8w\u0000\u0082j\u00aao\u00c15X\u00cd\u0093}\u000b'v\u00ff\u00eb\u00a5\u000f:'"
                     .length();
                  var16 = '8';
                  var23 = -1;
            }

            var24 = var17.substring(++var23, var23 + var16);
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

   private static int decryptInt(int key, long seed) {
      int value = key ^ (int)(seed & 32767L) ^ 32607;
      if (decryptedValues[value] == null) {
         byte[] bytes = new byte[]{
            (byte)(seed >>> 56),
            (byte)(seed >>> 48),
            (byte)(seed >>> 40),
            (byte)(seed >>> 32),
            (byte)(seed >>> 24),
            (byte)(seed >>> 16),
            (byte)(seed >>> 8),
            (byte)seed
         };
         long longValue = encryptedValues[value];
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
         Object[] values = (Object[])cipherCache.get(threadId);

         byte[] bytes3;
         try {
            if (values == null) {
               values = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
               cipherCache.put(threadId, values);
            }

            DESKeySpec dESKeySpec = new DESKeySpec(bytes);
            SecretKey generateSecret = ((SecretKeyFactory)values[1]).generateSecret(dESKeySpec);
            Cipher cipher = (Cipher)values[0];
            cipher.init(2, generateSecret, (IvParameterSpec)values[2]);
            bytes3 = cipher.doFinal(bytes2);
         } catch (Exception exception) {
            throw new RuntimeException("club/evon/yl", exception);
         }

         int value2 = (bytes3[4] & 255) << 24 | (bytes3[5] & 255) << 16 | (bytes3[6] & 255) << 8 | bytes3[7] & 255;
         decryptedValues[value] = value2;
      }

      return decryptedValues[value];
   }

   private static int bootstrapIntConstant(Lookup lookup, MutableCallSite callSite, String text, Object[] values) {
      int value = (Integer)values[0];
      long longValue = (Long)values[1];
      int value2 = decryptInt(value, longValue);
      MethodHandle constant = MethodHandles.constant(int.class, value2);
      callSite.setTarget(MethodHandles.dropArguments(constant, 0, int.class, long.class));
      return value2;
   }

   private static CallSite bootstrap(Lookup lookup, String text, MethodType type) {
      MutableCallSite mutableCallSite = new MutableCallSite(type);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(
                  MethodHandles.lookup()
                     .findStatic(
                        Cubecraft2Disabler.class,
                        "bootstrapIntConstant",
                        MethodType.methodType(int.class, Lookup.class, MutableCallSite.class, String.class, Object[].class)
                     )
                     .asCollector(Object[].class, type.parameterCount()),
                  0,
                  lookup,
                  mutableCallSite,
                  text
               ),
               type
            )
         );
         return mutableCallSite;
      } catch (Exception exception) {
         throw new RuntimeException("club/evon/yl" + " : " + text + " : " + type.toString(), exception);
      }
   }

   private static Object invokeIndySafe(String name, String desc, Object[] args) {
      try {
         return IndyRuntime.invoke(
               "club.evon.module.impl.misc.Cubecraft2Disabler",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               name,
               desc,
               args);
      } catch (Throwable throwable) {
         throw new RuntimeException(throwable);
      }
   }
}
