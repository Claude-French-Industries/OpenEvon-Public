package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.ClutchItemPriorities;
import club.evon.data.SlotSwitchMode;
import club.evon.event.BlockPlaceEvent;
import club.evon.event.ClientTickEvent;
import club.evon.event.InputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.KeybindManager;
import club.evon.manager.RotationManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.SubMode;
import club.evon.util.ItemUtils;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.RotationUtil;
import club.evon.util.SimulatedPlayer;
import club.evon.util.math.MovementUtil;




public final class LegitNoFall extends SubMode<NoFall> {
   private int simulationTick;
   private int clutchSlot;
   private boolean pendingPlacement;
   private boolean placedWater;
   private boolean actionScheduled;
   private final HashMap<Item, Integer> itemPriorities = new ClutchItemPriorities(this);
   private static int state;
   private static final long KEY = KeyedRandom.create(5109690774020636072L, 149500965223101160L, MethodHandles.lookup().lookupClass()).next(229108175590763L);

   public LegitNoFall(String name, NoFall parent) {
      super(name, parent);
   }

   @Subscribe
   private void onInput(InputEvent event) {
      long longValue = KEY ^ 86058768415015L;
      int value = getState();
      Block targetBlock = PlayerUtil.getTargetBlock();
      int value2 = value;
      boolean interactiveBlock = ItemUtils.isInteractiveBlock(targetBlock);
      if (value2 == 0) {
         if (interactiveBlock) {
            return;
         }

         interactiveBlock = this.consumeAction();
      }

      if (interactiveBlock) {
         KeybindManager.getUseState().queuePress();
      }
   }

   private boolean consumeAction() {
      long longValue = KEY ^ 119170737436004L;
      int value = getStateValue();
      boolean tmp = this.actionScheduled;
      if (value != 0) {
         if (this.actionScheduled) {
            this.actionScheduled = false;
            return true;
         }

         tmp = false;
      }

      return tmp;
   }

   @Subscribe
   private void onBlockPlace(BlockPlaceEvent event) {
      long longValue = KEY ^ 61850430424243L;
      int value = getState();
      if (this.pendingPlacement) {
         BlockPos blockPos = event.getBlockHitResult().getBlockPos();
         Block block = ClientContext.MINECRAFT_CLIENT.world.getBlockState(blockPos).getBlock();
         if (value == 0) {
            if (block != Blocks.WATER) {
               return;
            }

            this.placedWater = true;
         }

         this.pendingPlacement = false;
      }
   }

   @Subscribe
   private void onTick(ClientTickEvent event) {
      long longValue = KEY ^ 22259450539589L;
      int value = getState();
      ClientPlayerEntity tmp = ClientContext.MINECRAFT_CLIENT.player;
      if (value == 0) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.player;
      }

      boolean onGround = tmp.isOnGround();
      label148:
      if (value == 0) {
         if (!onGround) {
            onGround = ClientContext.MINECRAFT_CLIENT.player.isSubmergedInWater();
            if (value != 0) {
               break label148;
            }

            if (!onGround) {
               onGround = ClientContext.MINECRAFT_CLIENT.player.isTouchingWater();
               if (value != 0) {
                  break label148;
               }

               if (!onGround) {
                  onGround = PlayerUtil.isPlayerStandingInCobweb();
                  if (value != 0) {
                     break label148;
                  }

                  if (!onGround && PlayerUtil.isPlayerTouchingCobweb()) {
                  }
               }
            }
         }

         onGround = this.placedWater;
      }

      int value2 = onGround ? 1 : 0;
      if (value == 0) {
         if (onGround) {
            Block targetBlock = PlayerUtil.getTargetBlock();
            if (targetBlock != Blocks.WATER) {
               return;
            }

            BlockPos targetBlockPos = PlayerUtil.getTargetBlockPos();
            if (targetBlockPos == null) {
               return;
            }

            int value3 = ItemUtils.findItemSlot(Items.BUCKET, true);
            if (value == 0) {
               if (value3 == -1) {
                  return;
               }

               SilentSlotManager.getInstance().switchTo(value3).setMode(SlotSwitchMode.FULL);
               RotationManager.getRotationController().rotateTo(RotationUtil.getRotationToFace(targetBlockPos, Direction.UP), 360.0F);
               this.actionScheduled = true;
            }

            this.placedWater = false;
         }

         double doubleValue;
         value2 = (doubleValue = ClientContext.MINECRAFT_CLIENT.player.fallDistance - MovementUtil.getJumpHeight()) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
      }

      if (value == 0) {
         if (value2 <= 0) {
            return;
         }

         value2 = this.placedWater ? 1 : 0;
      }

      if (value == 0) {
         if (value2 != 0) {
            return;
         }

         value2 = this.findClutchSlot();
      }

      int value4 = value2;
      if (value == 0) {
         if (value4 == -1) {
            return;
         }

         this.clutchSlot = value4;
      }

      SimulatedPlayer simulatedPlayer = new SimulatedPlayer(ClientContext.MINECRAFT_CLIENT.player);
      OtherClientPlayerEntity fakePlayer = simulatedPlayer.getFakePlayer();
      int index = 0;

      while (index < 7) {
         label120: {
            simulatedPlayer.tick();
            if (value == 0) {
               if (fakePlayer.isOnGround() && value == 0) {
                  break label120;
               }

               SilentSlotManager.getInstance().switchTo(value4).setMode(SlotSwitchMode.FULL);
               RotationManager.getRotationController().rotateTo(new Vec2f(ClientContext.MINECRAFT_CLIENT.player.getYaw(), 90.0F), 360.0F);
               this.simulationTick = ClientContext.MINECRAFT_CLIENT.player.age + index;
               this.pendingPlacement = true;
               this.actionScheduled = true;
            }

            if (value == 0) {
               break;
            }
         }

         index++;
         if (value != 0) {
            break;
         }
      }
   }

   private int findClutchSlot() {
      long longValue = KEY ^ 40493561710260L;
      int value = getStateValue();
      int value2 = -1;
      int value3 = value;
      int value4 = -1;
      int index = 0;

      while (true) {
         if (index < 9) {
            ItemStack stack = ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(index);
            int value5 = this.itemPriorities.getOrDefault(stack.getItem(), -1);
            if (value3 != 0) {
               value = value5;
               if (value3 == 0) {
                  break;
               }

               if (value5 > value4) {
                  value2 = index;
                  value4 = value5;
               }

               index++;
            }

            if (value3 != 0) {
               continue;
            }
         }

         value = value2;
         break;
      }

      return value;
   }

   public static void setState(int value) {
      state = value;
   }

   public static int getState() {
      return state;
   }

   public static int getStateValue() {
      int value = getState();
      return value == 0 ? 52 : 0;
   }

   static {
      if (getState() != 0) {
         setState(6);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
