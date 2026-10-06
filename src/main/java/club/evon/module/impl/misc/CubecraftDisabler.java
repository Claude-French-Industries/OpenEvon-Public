package club.evon.module.impl.misc;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.crypto.Cipher;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.api.CubecraftServer;
import club.evon.data.TimestampedPacket;
import club.evon.event.ClientTickEvent;
import club.evon.event.JoinWorldEvent;
import club.evon.event.ModuleToggleEvent;
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
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.math.RandomUtil;
import club.evon.util.net.NetworkUtil;




public final class CubecraftDisabler extends SubMode<Disabler> {
   private final Queue<TimestampedPacket> pendingKeepAlives = new ConcurrentLinkedQueue<>();
   private int packetLimit;
   private int packetCounter;
   private int keepAliveDelay;
   private boolean highLatency;
   private boolean y;
   private Vec3d lastPosition = new Vec3d(0.0, 0.0, 0.0);
   private static final long KEY = KeyedRandom.create(-6847391687283652219L, 1049077968015166293L, MethodHandles.lookup().lookupClass()).next(167852602842192L);
   private static final String[] STRINGS_TABLE;
   private static final long COUNTER_RESET;

   public CubecraftDisabler(String name, Disabler parent) {
      super(name, parent);
   }

   @Subscribe
   private void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 60012025528610L;
      boolean disabled = Cubecraft2Disabler.isDisabled();
      Packet packet = event.getPacket();
      boolean flag = disabled;
      Packet packet2;
      disabled = ClientState.getInstance().getServerDetector().getServerProfile() instanceof CubecraftServer;
      if (flag) {
         if (!disabled) {
            return;
         }

         disabled = packet instanceof KeepAliveS2CPacket;
      }

      if (flag) {
         if (disabled) {
            KeepAliveS2CPacket packet3 = (KeepAliveS2CPacket)packet;
            if (ClientContext.MINECRAFT_CLIENT.player.isDead()) {
               return;
            }

            KeepAliveC2SPacket keepAliveC2SPacket = new KeepAliveC2SPacket(packet3.getId());
            long longValue2 = System.currentTimeMillis() + this.keepAliveDelay;
            this.pendingKeepAlives.add(new TimestampedPacket(keepAliveC2SPacket, longValue2));
            event.cancel();
            if (flag) {
               return;
            }
         }

         disabled = packet instanceof PlayerPositionLookS2CPacket;
      }

      label51: {
         label64: {
            if (flag) {
               if (disabled) {
                  break label51;
               }

               packet2 = packet;
               if (!flag) {
                  break label64;
               }

               disabled = packet instanceof HealthUpdateS2CPacket;
            }

            if (!disabled) {
               return;
            }

            packet2 = packet;
         }

         HealthUpdateS2CPacket packet4 = (HealthUpdateS2CPacket)packet2;
         if (!(packet4.getHealth() <= 0.0F)) {
            return;
         }
      }

      this.flushAllKeepAlives();
   }

   @Subscribe
   private void onOutboundPacket(OutboundPacketEvent event) {
      long longValue = KEY ^ 42920641416806L;
      int value = Cubecraft2Disabler.isEnabled() ? 1 : 0;
      Packet packet = event.getPacket();
      boolean flag = value != 0;
      Packet packet2;
      CubecraftDisabler cubecraftDisabler = this;
      value = ClientState.getInstance().getServerDetector().getServerProfile() instanceof CubecraftServer ? 1 : 0;
      if (!flag) {
         if (value == 0) {
            return;
         }

         value = packet instanceof KeepAliveC2SPacket ? 1 : 0;
      }

      if (value != 0) {
         event.cancel();
      }

      label333: {
         ClientPlayerEntity player = ClientContext.MINECRAFT_CLIENT.player;
         if (!flag) {
            if (ClientContext.MINECRAFT_CLIENT.player == null) {
               break label333;
            }

            player = ClientContext.MINECRAFT_CLIENT.player;
         }

         value = player.age;
         if (!flag) {
            if (player.age <= 20) {
               break label333;
            }

            value = packet instanceof PlayerMoveC2SPacket ? 1 : 0;
         }

         if (!flag) {
            if (value != 0) {
               PlayerMoveC2SPacket player2;
               PlayerMoveC2SPacketAccessor player3;
               label312: {
                  label286: {
                     player2 = (PlayerMoveC2SPacket)packet;
                     player3 = (PlayerMoveC2SPacketAccessor)player2;
                     value = this.packetCounter;
                     byte byteValue = 3;
                     if (!flag) {
                        if (this.packetCounter < 3) {
                           break label312;
                        }

                        value = this.packetCounter;
                        if (flag) {
                           break label286;
                        }

                        byteValue = 8;
                     }

                     value = value <= byteValue ? 1 : 0;
                  }

                  int value2 = value;
                  value = this.packetCounter;
                  if (!flag) {
                     value = this.packetCounter >= this.packetLimit - 2 ? 1 : 0;
                  }

                  label276: {
                     label275: {
                        label314: {
                           int value3 = value;
                           value = value2;
                           if (!flag) {
                              if (value2 != 0) {
                                 break label314;
                              }

                              value = value3;
                           }

                           if (flag) {
                              break label276;
                           }

                           if (value == 0) {
                              break label275;
                           }
                        }

                        event.cancel();
                     }

                     value = this.packetCounter % 55;
                  }

                  if (value == 0) {
                     NetworkUtil.sendManagedPacket(new OnGroundOnly(true, ClientContext.MINECRAFT_CLIENT.player.horizontalCollision));
                  }
               }

               label315: {
                  Scaffold moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Scaffold.class);
                  MinecraftClient minecraftClient = ClientContext.MINECRAFT_CLIENT;
                  if (!flag) {
                     if (ClientContext.MINECRAFT_CLIENT.currentScreen != null) {
                        break label315;
                     }

                     minecraftClient = ClientContext.MINECRAFT_CLIENT;
                  }

                  boolean usingItem = minecraftClient.player.isUsingItem();
                  if (!flag) {
                     if (usingItem) {
                        break label315;
                     }

                     usingItem = moduleByClass.isEnabled();
                  }

                  if (!usingItem) {
                     BlockPos down = ClientContext.MINECRAFT_CLIENT.player.getBlockPos().down();
                     Hand activeHand = ClientContext.MINECRAFT_CLIENT.player.getActiveHand();
                     if (!flag) {
                        activeHand = activeHand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
                     }

                     Hand hand = activeHand;
                     BlockHitResult blockHitResult = new BlockHitResult(down.toCenterPos(), Direction.UP, down, false);
                     NetworkUtil.sendSequencedPacket(var2x -> new PlayerInteractBlockC2SPacket(hand, blockHitResult, var2x));
                  }
               }

               label252: {
                  double doubleValue = player2.getY(ClientContext.MINECRAFT_CLIENT.player.getY());
                  doubleValue -= doubleValue % 0.015625;
                  player3.setY(doubleValue);
                  boolean changesPosition = player2.changesPosition();
                  if (!flag) {
                     if (!changesPosition) {
                        break label252;
                     }

                     changesPosition = event.isCanceled();
                  }

                  if (!changesPosition) {
                     this.lastPosition = new Vec3d(player2.x, player2.y, player2.z);
                  }
               }

               if (!flag) {
                  return;
               }
            }

            value = packet instanceof CommonPongC2SPacket ? 1 : 0;
         }

         label316: {
            if (!flag) {
               if (value != 0) {
                  CommonPongC2SPacket packet3 = (CommonPongC2SPacket)packet;
                  int value4 = packet3.getParameter();
                  value = this.packetCounter;
                  if (!flag) {
                     if (this.packetCounter == 1) {
                        this.packetCounter++;
                     }

                     value = value4;
                  }

                  label319: {
                     label236:
                     if (!flag) {
                        label234:
                        if (value < 0) {
                           CubecraftDisabler cubecraftDisabler2 = this;
                           if (!flag) {
                              if (this.packetCounter < 3) {
                                 value = this.packetCounter;
                                 if (flag) {
                                    break label236;
                                 }

                                 if (this.packetCounter != 0) {
                                    break label234;
                                 }
                              }

                              cubecraftDisabler2 = this;
                           }

                           cubecraftDisabler2.packetCounter++;
                           NetworkUtil.sendManagedPacket(new CommonPongC2SPacket(value4 + 1));
                           if (!flag) {
                              break label319;
                           }
                        }

                        value = value4;
                     }

                     label221: {
                        label320: {
                           if (!flag) {
                              if (value == 0) {
                                 break label319;
                              }

                              cubecraftDisabler = this;
                              if (flag) {
                                 break label320;
                              }

                              value = this.packetCounter;
                           }

                           if (value < this.packetLimit) {
                              cubecraftDisabler = this;
                              if (flag) {
                                 break label320;
                              }

                              if (this.packetCounter != 2) {
                                 break label221;
                              }
                           }

                           NetworkUtil.sendManagedPacket(new CommonPongC2SPacket(value4 + 3));
                           cubecraftDisabler = this;
                        }

                        cubecraftDisabler.packetCounter = (int)COUNTER_RESET;
                     }

                     event.cancel();
                  }

                  if (!flag) {
                     return;
                  }
               }

               packet2 = packet;
               if (flag) {
                  break label316;
               }

            value = packet instanceof PlayerInteractEntityC2SPacket ? 1 : 0;
         }

            if (value == 0) {
               return;
            }

            packet2 = packet;
         }

         PlayerInteractEntityC2SPacket player4 = (PlayerInteractEntityC2SPacket)packet2;
         if (player4.type == PlayerInteractEntityC2SPacket.ATTACK) {
            ClientWorld world = ClientContext.MINECRAFT_CLIENT.world;
            if (!flag) {
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
            if (doubleValue2 >= 4.3) {
            }
         }

         return;
      }

      this.y = false;
   }

   @Subscribe
   private void onModuleToggle(ModuleToggleEvent event) {
      long longValue = KEY ^ 55822197950000L;
      boolean enabled = Cubecraft2Disabler.isEnabled();
      ModuleToggleEvent event2 = event;
      boolean tmp = ClientState.getInstance().getServerDetector().getServerProfile() instanceof CubecraftServer;
      if (!enabled) {
         if (!tmp) {
            return;
         }

         tmp = this.highLatency;
      }

      if (!enabled) {
         if (tmp) {
            return;
         }

         tmp = event.isEnabled();
      }

      label64: {
         if (!enabled) {
            if (!tmp) {
               return;
            }

            event2 = event;
            if (enabled) {
               break label64;
            }

            tmp = event.getModule() instanceof Speed;
         }

         if (!tmp) {
            event2 = event;
            if (enabled) {
               break label64;
            }

            if (!(event.getModule() instanceof Flight)) {
               return;
            }
         }

         event2 = event;
      }

      event2.cancel();
      ChatUtil.sendMessage(STRINGS_TABLE[0]);
   }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 24146219462149L;
      boolean enabled = Cubecraft2Disabler.isEnabled();
      if (ClientState.getInstance().getServerDetector().getServerProfile() instanceof CubecraftServer) {
         Flight flight;
         MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
         if (!enabled) {
            if (ClientContext.MINECRAFT_CLIENT.player == null) {
               return;
            }

            tmp = ClientContext.MINECRAFT_CLIENT;
         }

         if (tmp.getNetworkHandler() != null) {
            CubecraftDisabler cubecraftDisabler = this;
            if (!enabled) {
               label51:
               if (!this.highLatency) {
                  PlayerListEntry player;
                  label61: {
                     label60: {
                        player = ClientContext.MINECRAFT_CLIENT.getNetworkHandler().getPlayerListEntry(ClientContext.MINECRAFT_CLIENT.player.getUuid());
                        ModuleManager moduleManager = EvonClient.getInstance().getModuleManager();
                        Speed moduleByClass = moduleManager.getModuleByClass(Speed.class);
                        Flight moduleByClass2 = moduleManager.getModuleByClass(Flight.class);
                        Speed speed = moduleByClass;
                        if (!enabled) {
                           if (!moduleByClass.isEnabled()) {
                              flight = moduleByClass2;
                              if (enabled) {
                                 break label60;
                              }

                              if (!moduleByClass2.isEnabled()) {
                                 break label61;
                              }
                           }

                           ChatUtil.sendMessage(STRINGS_TABLE[2]);
                           speed = moduleByClass;
                        }

                        speed.setEnabled(false);
                        flight = moduleByClass2;
                     }

                     flight.setEnabled(false);
                  }

                  PlayerListEntry player2 = player;
                  if (!enabled) {
                     if (player == null) {
                        break label51;
                     }

                     player2 = player;
                  }

                  if (player2.getLatency() > 500) {
                     this.highLatency = true;
                     ChatUtil.sendMessage(STRINGS_TABLE[1]);
                  }
               }

               cubecraftDisabler = this;
            }

            cubecraftDisabler.flushDueKeepAlives();
         }
      }
   }

   private void flushAllKeepAlives() {
      long longValue = KEY ^ 110713285724151L;
      boolean enabled = Cubecraft2Disabler.isEnabled();

      TimestampedPacket packet;
      while ((packet = this.pendingKeepAlives.poll()) != null) {
         NetworkUtil.sendManagedPacket(packet.getPacket());
         if (enabled) {
            break;
         }
      }
   }

   private void flushDueKeepAlives() {
      long longValue = KEY ^ 118115485414054L;
      boolean disabled = Cubecraft2Disabler.isDisabled();
      long longValue2 = System.currentTimeMillis();
      boolean flag = disabled;
      Iterator iterator = this.pendingKeepAlives.iterator();

      while (iterator.hasNext()) {
         label22: {
            TimestampedPacket packet = (TimestampedPacket)iterator.next();
            if (flag) {
               if (longValue2 < packet.timestamp()) {
                  break label22;
               }

               NetworkUtil.sendManagedPacket(packet.getPacket());
            }

            iterator.remove();
         }

         if (!flag) {
            break;
         }
      }
   }

   @Subscribe
   private void onJoinWorld(JoinWorldEvent event) {
      this.highLatency = false;
   }

   @Override
   public void onEnable() {
      long longValue = KEY ^ 7826720605879L;
      this.packetLimit = RandomUtil.randomInt(4900, 5100);
      this.keepAliveDelay = RandomUtil.randomInt(4900, 5100);
      this.highLatency = this.y = false;
      boolean enabled = Cubecraft2Disabler.isEnabled();
      this.packetCounter = 0;
      boolean flag = enabled;
      super.onEnable();
      if (BaseModule.getStateArray() == null) {
         Cubecraft2Disabler.setEnabled(!flag);
      }
   }

   @Override
   public void onDisable() {
      this.pendingKeepAlives.clear();
      this.highLatency = false;
      super.onDisable();
   }

   static {
      try {
      long var5 = KEY ^ 3423834599877L;
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[3];
      int var12 = 0;
      String var11 = "\u00e0\t!\u009f\u0089\u00d1\u00d3T\u0084\u00bdH\u00148i\u00a2\u001df\\A\u00fa[\u00b6\u00d39ug\b;\u009a\u0005rZn`\u008c\u009eF\u001f\u00da\u0094\u001e\u00b6\u008a\u0085\u00f1\u0010t\u00e2\u00a7\u00ed\u009d\u00c3\u00dd\u0010\u00ad\u00f2\u0018\u00a6\u00e1\u00f5\u00a7\u00d8D\u00ebxOI\u0082Y\u0080\u00d4\u0012\u0082\u00e0d\u0081\u00fa\u00ebl\u0089\u001e8\u00e0\t!\u009f\u0089\u00d1\u00d3T\u0084\u00bdH\u00148i\u00a2\u001df\\A\u00fa[\u00b6\u00d39ug\b;\u009a\u0005rZn`\u008c\u009eF\u001f\u00da\u0094\u001e\u00b6\u008a\u0085\u00f1\u0010t\u00e2\u00a7\u00ed\u009d\u00c3\u00dd\u0010\u00ad\u00f2";
      int var13 = "\u00e0\t!\u009f\u0089\u00d1\u00d3T\u0084\u00bdH\u00148i\u00a2\u001df\\A\u00fa[\u00b6\u00d39ug\b;\u009a\u0005rZn`\u008c\u009eF\u001f\u00da\u0094\u001e\u00b6\u008a\u0085\u00f1\u0010t\u00e2\u00a7\u00ed\u009d\u00c3\u00dd\u0010\u00ad\u00f2\u0018\u00a6\u00e1\u00f5\u00a7\u00d8D\u00ebxOI\u0082Y\u0080\u00d4\u0012\u0082\u00e0d\u0081\u00fa\u00ebl\u0089\u001e8\u00e0\t!\u009f\u0089\u00d1\u00d3T\u0084\u00bdH\u00148i\u00a2\u001df\\A\u00fa[\u00b6\u00d39ug\b;\u009a\u0005rZn`\u008c\u009eF\u001f\u00da\u0094\u001e\u00b6\u008a\u0085\u00f1\u0010t\u00e2\u00a7\u00ed\u009d\u00c3\u00dd\u0010\u00ad\u00f2"
         .length();
      char var10 = '8';
      int var9 = -1;

      label28:
      while (true) {
         byte[] var15 = var7.doFinal(var11.substring(++var9, var9 + var10).getBytes("ISO-8859-1"));
         String var20 = decodeUtf8(var15).intern();
         byte var10001 = -1;
         var14[var12++] = var20;
         if ((var9 += var10) >= var13) {
            STRINGS_TABLE = var14;
            Cipher var0;
            var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
            var10002 = SecretKeyFactory.getInstance("DES");
            var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

            for (int var1 = 1; var1 < 8; var1++) {
               var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
            }

            var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long var2 = 2970114578937548952L;
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
            long var23 = (var4[0] & 255L) << 56
               | (var4[1] & 255L) << 48
               | (var4[2] & 255L) << 40
               | (var4[3] & 255L) << 32
               | (var4[4] & 255L) << 24
               | (var4[5] & 255L) << 16
               | (var4[6] & 255L) << 8
               | var4[7] & 255L;
            var10001 = -1;
            COUNTER_RESET = var23;
            break label28;
         }

         var10 = var11.charAt(var9);
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
}
