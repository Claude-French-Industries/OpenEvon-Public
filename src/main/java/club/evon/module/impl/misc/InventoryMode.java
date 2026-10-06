package club.evon.module.impl.misc;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.HypixelTargetFilter;
import club.evon.api.HypixelLocationManager;
import club.evon.data.HypixelLocation;
import club.evon.event.ClientTickEvent;
import club.evon.event.JoinWorldEvent;
import club.evon.event.OutgoingPacketEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.manager.OutgoingPacketManager;
import club.evon.module.SubMode;
import club.evon.setting.BooleanSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.net.NetworkUtil;
import club.evon.util.net.PacketBlocker;




public final class InventoryMode extends SubMode<Disabler> {
   private final BooleanSetting inventory;
   private final PacketBlocker packetBlocker;
   private boolean desyncActive;
   private static final long KEY = KeyedRandom.create(4772063715573467591L, -4152607093251842525L, MethodHandles.lookup().lookupClass()).next(104206119278387L);
   private static final String SETTING_NAME;

   public InventoryMode(String name, Disabler parent) {
      super(name, parent);
      this.inventory = new BooleanSetting(SETTING_NAME, true);
      this.packetBlocker = new PacketBlocker(OutgoingPacketManager.getInstance());
      this.addSettings(this.inventory);
   }

   @Override
   public void onDisable() {
      this.packetBlocker.unblock();
      this.desyncActive = false;
      super.onDisable();
   }

   @Subscribe
   private void onOutgoingPacket(OutgoingPacketEvent event) {
      boolean flag;
      Packet packet;
      byte byteValue;
      InventoryMode inventoryMode;
      label134: {
         long longValue = KEY ^ 115028763229982L;
         boolean disabled = Cubecraft2Disabler.isDisabled();
         Packet packet2 = event.getPacket();
         flag = disabled;
         boolean flag2 = packet2 instanceof ClickSlotC2SPacket;
         if (flag) {
            if (flag2) {
               ClickSlotC2SPacket packet3 = (ClickSlotC2SPacket)packet2;
               boolean active = this.isActive();
               if (flag) {
                  if (!active) {
                     return;
                  }

                  active = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter;
               }

               label118:
               if (active) {
                  HypixelLocation currentLocation = HypixelLocationManager.getInstance().getCurrentLocation();
                  HypixelLocation hypixelLocation = currentLocation;
                  if (flag) {
                     if (currentLocation == null) {
                        break label118;
                     }

                     hypixelLocation = currentLocation;
                  }

                  if (hypixelLocation.hasLobby()) {
                     this.desyncActive = false;
                     return;
                  }
               }

               label111: {
                  label138: {
                     SlotActionType actionType = packet3.actionType();
                     SlotActionType tmp = SlotActionType.QUICK_MOVE;
                     if (flag) {
                        if (actionType == SlotActionType.QUICK_MOVE) {
                           break label138;
                        }

                        actionType = packet3.actionType();
                        tmp = SlotActionType.SWAP;
                     }

                     if (flag) {
                        if (actionType == tmp) {
                           break label138;
                        }

                        actionType = packet3.actionType();
                        tmp = SlotActionType.THROW;
                     }

                     if (actionType != tmp) {
                        byteValue = 0;
                        break label111;
                     }
                  }

                  byteValue = 1;
               }

               label97: {
                  byte byteValue2 = byteValue;
                  int value = packet3.syncId();
                  if (flag) {
                     if (value != ClientContext.MINECRAFT_CLIENT.player.playerScreenHandler.syncId) {
                        break label97;
                     }

                     value = byteValue2;
                  }

                  if (value != 0) {
                     NetworkUtil.sendPacket(new CloseHandledScreenC2SPacket(packet3.syncId()));
                     if (flag) {
                        return;
                     }
                  }
               }

               this.desyncActive = true;
               return;
            }

            packet2 = event.getPacket();
            packet = packet2;
            if (!flag) {
               break label134;
            }

            flag2 = packet2 instanceof CloseHandledScreenC2SPacket;
         }

         if (!flag2) {
            return;
         }

         packet = packet2;
      }

      CloseHandledScreenC2SPacket packet4 = (CloseHandledScreenC2SPacket)packet;
      if (ClientContext.MINECRAFT_CLIENT.player != null) {
         label139: {
            int value2 = packet4.getSyncId();
            if (flag) {
               if (value2 != ClientContext.MINECRAFT_CLIENT.player.playerScreenHandler.syncId) {
                  return;
               }

               inventoryMode = this;
               if (!flag) {
                  break label139;
               }

               value2 = this.isActive() ? 1 : 0;
            }

            if (value2 == 0) {
               return;
            }

            inventoryMode = this;
         }

         inventoryMode.desyncActive = false;
      }
   }

   @Subscribe(h = 2)
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 66035799632019L;
      boolean enabled = Cubecraft2Disabler.isEnabled();
      if (this.isActive()) {
         if (ClientContext.MINECRAFT_CLIENT.currentScreen == null) {
            this.desyncActive = false;
         }

         InventoryMode tmp = this;
         if (!enabled) {
            if (this.desyncActive) {
               this.packetBlocker.block(packet -> packet, packet2 -> {
                  long var1x = KEY ^ 58843117312051L;
                  boolean enabled2 = Cubecraft2Disabler.isEnabled();
                  boolean var10000x = packet2 instanceof ClickSlotC2SPacket;
                  if (!enabled2) {
                     if (var10000x) {
                        return false;
                     }

                     var10000x = packet2 instanceof CloseHandledScreenC2SPacket;
                  }

                  if (!enabled2) {
                     if (var10000x) {
                        return false;
                     }

                     var10000x = packet2 instanceof CommonPongC2SPacket;
                  }

                  if (!enabled2) {
                     if (var10000x) {
                        return false;
                     }

                     var10000x = packet2 instanceof KeepAliveC2SPacket;
                  }

                  return enabled2 ? var10000x : !var10000x;
               });
               if (!enabled) {
                  return;
               }
            }

            tmp = this;
         }

         tmp.packetBlocker.unblock();
      }
   }

   private boolean isActive() {
      long longValue = KEY ^ 8332237363935L;
      boolean disabled = Cubecraft2Disabler.isDisabled();
      boolean tmp = this.inventory.I();
      if (disabled) {
         if (!tmp) {
            return false;
         }

         tmp = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter;
      }

      return !disabled ? tmp : tmp;
   }

   @Subscribe
   private void onJoinWorld(JoinWorldEvent event) {
      this.desyncActive = false;
   }

   static {
      try {
      long var0 = KEY ^ 127276308583993L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var4 = var2.doFinal("\u00bb\u00ea\u00d6t\u00d6H\u0088\u00d1\u00bdn\u0084\u0005\u00b2rl\u009c".getBytes("ISO-8859-1"));
      String var5 = decodeUtf8(var4).intern();
      byte var10001 = -1;
      SETTING_NAME = var5;
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
