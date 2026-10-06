package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.ItemUseType;
import club.evon.event.ClientTickEvent;
import club.evon.event.InputEvent;
import club.evon.event.ItemSlowdownEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.PostInputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.KeybindManager;
import club.evon.manager.OutgoingPacketManager;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.util.ItemUtils;
import club.evon.util.KeyBindingState;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.math.RandomUtil;
import club.evon.util.net.PacketBlocker;




public final class NoSlowSwords extends SubMode<NoSlow> {
   private final PacketBlocker packetBlocker;
   private boolean blocking;
   private boolean X;
   private boolean y;
   private boolean k;
   private int K;
   private int m;
   private static final long b = KeyedRandom.create(-955909401725039209L, 4224076206311915267L, MethodHandles.lookup().lookupClass()).next(52756485912127L);

   public NoSlowSwords(String name, NoSlow parent) {
      super(name, parent);

      long longValue = b ^ 25883427059390L;
      this.packetBlocker = new PacketBlocker(OutgoingPacketManager.getInstance());
      boolean disabled = NoSlowMode.isDisabled();
      this.K = -1;
      boolean flag = disabled;
      if (flag) {
         BaseModule.setStateArray(new BaseModule[5]);
      }
      }

   @Subscribe
   public void onItemSlowdown(ItemSlowdownEvent event) {
      label31: {
         NoSlowSwords noSlowSwords;
         label34: {
            long longValue = b ^ 60882788211262L;
            boolean enabled = NoSlowMode.isEnabled();
            ItemUseType itemUseType = this.getParent().getItemUseType();
            ItemUseType tmp = ItemUseType.BOW;
            if (enabled) {
               if (itemUseType == ItemUseType.BOW) {
                  return;
               }

               noSlowSwords = this;
               if (!enabled) {
                  break label34;
               }

               itemUseType = this.getParent().getItemUseType();
               tmp = ItemUseType.USEABLE;
            }

            if (itemUseType != tmp) {
               break label31;
            }

            noSlowSwords = this;
         }

         if (!noSlowSwords.packetBlocker.isBlocking()) {
            return;
         }
      }

      event.cancel();
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent event) {
      long longValue = b ^ 59126305882631L;
      boolean disabled = NoSlowMode.isDisabled();
      Packet packet = event.getPacket();
      Packet packet2 = packet;
      if (!disabled) {
         if (!(packet instanceof EntityStatusS2CPacket)) {
            return;
         }

         packet2 = packet;
      }

      EntityStatusS2CPacket packet3 = (EntityStatusS2CPacket)packet2;
      Entity tmp = ClientContext.MINECRAFT_CLIENT.player;
      if (!disabled) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = packet3.getEntity(ClientContext.MINECRAFT_CLIENT.world);
      }

      if (tmp == ClientContext.MINECRAFT_CLIENT.player) {
         this.stopBlocking();
      }
   }

   @Subscribe
   public void onTick(ClientTickEvent event) {
      boolean flag;
      label44: {
         long longValue = b ^ 119130036677915L;
         flag = NoSlowMode.isDisabled();
         if (!flag) {
            label40:
            if (ClientContext.MINECRAFT_CLIENT.player != null) {
               MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
               if (!flag) {
                  if (ClientContext.MINECRAFT_CLIENT.currentScreen != null) {
                     break label40;
                  }

                  tmp = ClientContext.MINECRAFT_CLIENT;
               }

               if (tmp.getOverlay() == null) {
                  break label44;
               }
            }

            this.reset();
         }

         this.stopBlocking();
      }

      if (BaseModule.getStateArray() == null) {
         NoSlowMode.setEnabled(!flag);
      }
   }

   private void startBlocking() {
      this.packetBlocker.blockAll();
   }

   private void stopBlocking() {
      this.packetBlocker.unblock();
   }

   private void reset() {
      this.blocking = false;
      this.X = false;
      this.K = -1;
   }

   @Subscribe
   public void onPostInput(PostInputEvent event) {
      long longValue = b ^ 1600693386930L;
      boolean enabled = NoSlowMode.isEnabled();
      MinecraftClient minecraftClient;
      if (ClientContext.MINECRAFT_CLIENT.player != null) {
         NoSlowSwords tmp = this;
         if (enabled) {
            if (this.getParent().getItemUseType() != ItemUseType.BLOCKABLE) {
               return;
            }

            tmp = this;
         }

         label49: {
            boolean flag = tmp.blocking;
            if (enabled) {
               if (!tmp.blocking) {
                  return;
               }

               minecraftClient = ClientContext.MINECRAFT_CLIENT;
               if (!enabled) {
                  break label49;
               }

               flag = ClientContext.MINECRAFT_CLIENT.player.isUsingItem();
            }

            if (!flag) {
               return;
            }

            this.startBlocking();
            minecraftClient = ClientContext.MINECRAFT_CLIENT;
         }

         minecraftClient.interactionManager.stopUsingItem(ClientContext.MINECRAFT_CLIENT.player);
         KeybindManager.getAttackState().reset();
         this.blocking = false;
      }
   }

   @Subscribe(h = 1)
   public void onInput(InputEvent event) {
      long longValue = b ^ 87786769207723L;
      boolean disabled = NoSlowMode.isDisabled();
      if (!disabled) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         this.X = false;
      }

      KeyBindingState useState = KeybindManager.getUseState();
      NoSlowSwords tmp = this;
      if (!disabled) {
         if (this.getParent().getItemUseType() == ItemUseType.BLOCKABLE) {
            boolean pressed = useState.isPressed();
            if (!disabled) {
               if (pressed) {
                  label74: {
                     boolean usingItem = ClientContext.MINECRAFT_CLIENT.player.isUsingItem();
                     if (!disabled) {
                        if (!usingItem) {
                           break label74;
                        }

                        usingItem = this.packetBlocker.isBlocking();
                     }

                     if (usingItem) {
                        return;
                     }
                  }

                  Block targetBlock = PlayerUtil.getTargetBlock();
                  boolean interactiveBlock = ItemUtils.isInteractiveBlock(targetBlock);
                  if (!disabled) {
                     if (interactiveBlock) {
                        return;
                     }

                     interactiveBlock = ClientContext.MINECRAFT_CLIENT.interactionManager.isBreakingBlock();
                  }

                  if (interactiveBlock) {
                     return;
                  }

                  this.blocking = true;
                  useState.queuePress();
                  if (!disabled) {
                     return;
                  }
               }

               pressed = ClientContext.MINECRAFT_CLIENT.player.isUsingItem();
            }

            if (pressed) {
               return;
            }

            this.stopBlocking();
            if (!disabled) {
               return;
            }
         }

         tmp = this;
      }

      tmp.stopBlocking();
   }

   @Override
   public void onDisable() {
      this.stopBlocking();
      this.reset();
      super.onDisable();
   }

   private int findEmptySlot() {
      long longValue = b ^ 22286022202914L;
      int value = NoSlowMode.isEnabled() ? 1 : 0;
      int value2 = RandomUtil.randomIntBelow(8);
      boolean flag = value != 0;
      int value3 = value2;

      while (true) {
          if (value3 < 9) {
             value = this.isEmptySlot(value3) ? 1 : 0;
             if (!flag) {
                break;
             }

             if (!flag) {
                return value;
             }

             if (value != 0) {
                return value3;
             }

             value3++;
             if (flag) {
                continue;
             }
          }

          value = value2 - 1;
          break;
      }

      value3 = value;

      while (true) {
          if (value3 >= 0) {
             value = this.isEmptySlot(value3) ? 1 : 0;
             if (!flag) {
                break;
             }

            if (!flag) {
               return value;
            }

            if (value != 0) {
               return value3;
            }

            value3--;
            if (flag) {
               continue;
            }
         }

         value = value2;
         break;
      }

      return value;
   }

   private boolean isEmptySlot(int slot) {
      long longValue = b ^ 24532078136024L;
      boolean enabled = NoSlowMode.isEnabled();
      PlayerInventory inventory = ClientContext.MINECRAFT_CLIENT.player.getInventory();
      boolean flag = enabled;
      enabled = slot != 0;
      if (flag) {
         if (slot != inventory.getSelectedSlot()) {
            ItemStack stack = (ItemStack)inventory.getMainStacks().get(slot);
            boolean empty = stack.isEmpty();
            if (flag) {
               if (!empty) {
                  empty = stack.getName().getStyle().isEmpty();
                  if (!flag) {
                     return empty;
                  }

                  if (!empty) {
                     return false;
                  }
               }

               empty = true;
            }

            return empty;
         }

         enabled = false;
      }

      return enabled;
   }

   private static JSONException rethrow(JSONException exception) {
      return exception;
   }
}
