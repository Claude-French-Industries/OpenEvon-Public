package club.evon.manager;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.ClientService;
import club.evon.data.SlotSwitchMode;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class SilentSlotManager implements ClientService {
   private int realSlot;
   private int silentSlot;
   private boolean active;
   private int switchTick;
   private int tickCounter;
   private SlotSwitchMode mode = SlotSwitchMode.DEFAULT;
   private static SilentSlotManager instance;
   private static int[] stateArray;
   private static final long KEY = KeyedRandom.create(8682007984308715884L, 1786917264607341101L, MethodHandles.lookup().lookupClass()).next(38966575154361L);

   public SilentSlotManager switchTo(int slot) {
      long longValue = KEY ^ 57937155059930L;
      String flowGuard = ClientState.getFlowGuard();
      slot = MathHelper.clamp(slot, 0, 8);
      String text = flowGuard;
      SilentSlotManager silentSlotManager = this;
      if (text == null) {
         if (!this.active) {
            this.realSlot = ClientContext.MINECRAFT_CLIENT.player.getInventory().getSelectedSlot();
         }

         this.silentSlot = slot;
         this.switchTick = this.tickCounter;
         this.active = true;
         this.update(true, true);
         silentSlotManager = this;
      }

      if (BaseModule.getStateArray() == null) {
         ClientState.setFlowGuard("n0F4Mb");
      }

      return silentSlotManager;
   }

   public SilentSlotManager setMode(SlotSwitchMode silence) {
      this.mode = silence;
      return this;
   }

   public void setRealSlot(int slot) {
      this.realSlot = slot;
   }

   public void reset() {
      this.switchTick = -1;
      this.update(true, true);
   }

   public void update(boolean reset, boolean check) {
      long longValue = KEY ^ 100323461221831L;
      String flowGuard = ClientState.getFlowGuard();
      boolean flag = this.active;
      if (flowGuard == null) {
         if (!this.active) {
            return;
         }

         flag = check;
      }

      if (flowGuard == null) {
         if (flag) {
            MinecraftClient minecraftClient = ClientContext.MINECRAFT_CLIENT;
            if (flowGuard == null) {
               if (ClientContext.MINECRAFT_CLIENT.getOverlay() != null) {
                  return;
               }

               minecraftClient = ClientContext.MINECRAFT_CLIENT;
            }

            if (minecraftClient.currentScreen != null) {
               return;
            }
         }

         flag = reset;
      }

      label52: {
         if (flowGuard == null) {
            if (!flag) {
               break label52;
            }

            if (this.switchTick == this.tickCounter) {
               break label52;
            }
         } else if ((this.active ? 1 : 0) == this.tickCounter) {
            break label52;
         }

         ClientContext.MINECRAFT_CLIENT.player.getInventory().setSelectedSlot(this.realSlot);
         this.mode = SlotSwitchMode.DEFAULT;
         this.active = false;
         if (flowGuard == null) {
            return;
         }
      }

      ClientContext.MINECRAFT_CLIENT.player.getInventory().setSelectedSlot(this.silentSlot);
   }

   public void tick() {
      this.update(true, true);
      this.tickCounter++;
   }

   public ItemStack getMainHandStack(ClientPlayerEntity player) {
      long longValue = KEY ^ 133103676376035L;
      String flowGuard = ClientState.getFlowGuard();
      SilentSlotManager tmp = this;
      if (flowGuard == null) {
         if (!this.active) {
            return player.getMainHandStack();
         }

         tmp = this;
      }

      if (flowGuard != null) {
         return (ItemStack)(Object)tmp.mode;
      } else {
         return tmp.mode != SlotSwitchMode.NONE ? (ItemStack)player.getInventory().getMainStacks().get(this.realSlot) : player.getMainHandStack();
      }
   }

   public int getVisualSelectedSlot(PlayerInventory inventory) {
      long longValue = KEY ^ 124025139306439L;
      String flowGuard = ClientState.getFlowGuard();
      SilentSlotManager tmp = this;
      if (flowGuard == null) {
         if (!this.active) {
            return inventory.getSelectedSlot();
         }

         tmp = this;
      }

      if (flowGuard != null) {
         return tmp.realSlot;
      } else {
         return tmp.mode == SlotSwitchMode.FULL ? this.realSlot : inventory.getSelectedSlot();
      }
   }

   public int getRealSlot() {
      return this.realSlot;
   }

   public static void init() {
      instance = new SilentSlotManager();
   }

   public int getCurrentSlot() {
      return this.realSlot;
   }

   public int getSilentSlot() {
      return this.silentSlot;
   }

   public boolean isActive() {
      return this.active;
   }

   public int getSwitchTick() {
      return this.switchTick;
   }

   public int getTickCounter() {
      return this.tickCounter;
   }

   public SlotSwitchMode getMode() {
      return this.mode;
   }

   public static SilentSlotManager getInstance() {
      return instance;
   }

   public static void setStateArray(int[] values) {
      stateArray = values;
   }

   public static int[] getStateArray() {
      return stateArray;
   }

   static {
      if (getStateArray() == null) {
         setStateArray(new int[3]);
      }
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }
}
