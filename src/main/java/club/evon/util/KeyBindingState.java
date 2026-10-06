package club.evon.util;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.option.KeyBinding;
import org.json.JSONException;
import club.evon.mixins.KeyBindingAccessor;
import club.evon.module.BaseModule;




public final class KeyBindingState {
   private final KeyBinding x;
   private boolean queuedPress;
   private boolean suppressed;
   private int T;
   private boolean J = true;
   private static int state;
   private static final long KEY = KeyedRandom.create(-852647286984373716L, -5868646223759761421L, MethodHandles.lookup().lookupClass()).next(107938235820726L);

   public KeyBindingState(KeyBinding keyBinding) {
      this.x = keyBinding;
   }

   public void reset() {
      long longValue = KEY ^ 46240538115728L;
      this.queuedPress = false;
      int value = getBranchCode();
      this.T = 0;
      int value2 = value;
      this.suppressed = true;
      if (BaseModule.getStateArray() == null) {
         setState(++value2);
      }
   }

   public void queuePress() {
      this.setPressState(true, 0);
   }

   public void setPressState(boolean pressed, int holdTicks) {
      this.queuedPress = pressed;
      this.T = holdTicks;
   }

   public boolean wasPressed() {
      long longValue = KEY ^ 58444479722160L;
      int value = getState();
      boolean tmp = this.suppressed;
      if (value != 0) {
         if (this.suppressed) {
            return false;
         }

         tmp = false;
      }

      boolean flag = tmp;
      tmp = this.queuedPress;
      if (value != 0) {
         if (this.queuedPress) {
            flag = true;
            this.queuedPress = false;
         }

         tmp = this.x.wasPressed();
      }

      if (value != 0) {
         if (!tmp) {
            if (value == 0) {
               return flag;
            }

            if (!flag) {
               return false;
            }
         }

         tmp = true;
      }

      return tmp;
   }

   public boolean isPressed() {
      long longValue = KEY ^ 44459369370041L;
      int value = getState();
      boolean tmp = this.suppressed;
      if (value != 0) {
         if (this.suppressed) {
            return false;
         }

         tmp = this.x.isPressed();
      }

      if (value != 0) {
         if (!tmp) {
            if (value == 0) {
               return this.queuedPress;
            }

            if (!this.queuedPress) {
               if (value == 0) {
                  return this.T > 0;
               }

               if (this.T <= 0) {
                  return false;
               }
            }
         }

         tmp = true;
      }

      return tmp;
   }

   public boolean hasQueuedPress() {
      return this.queuedPress;
   }

   public void tick() {
      int value;
      KeyBindingState keyBindingState;
      label45: {
         label44: {
            label48: {
               long longValue = KEY ^ 90621025085869L;
               value = getState();
               int value2 = this.T;
               boolean flag = value2 != 0;
               if (value != 0) {
                  if (this.T > 0) {
                     this.T--;
                  }

                  keyBindingState = this;
                  if (value == 0) {
                     break label48;
                  }

                  flag = this.x.isPressed();
               }

               if (!flag) {
                  keyBindingState = this;
                  if (value == 0) {
                     break label45;
                  }

                  if (this.T != 0) {
                     break label44;
                  }
               }

               keyBindingState = this;
            }

            keyBindingState.J = true;
         }

         this.queuedPress = false;
         keyBindingState = this;
      }

      keyBindingState.suppressed = false;
      if (value == 0) {
         BaseModule.setStateArray(new BaseModule[4]);
      }
   }

   public int getHoldTicks() {
      return this.T;
   }

   public boolean isSuppressed() {
      return this.suppressed;
   }

   public boolean shouldSwing() {
      long longValue = KEY ^ 78677253560561L;
      int value = getBranchCode();
      boolean tmp = this.J;
      if (value == 0) {
         if (!this.J) {
            int value2 = ((KeyBindingAccessor)this.x).getTimesPressed();
            if (value != 0) {
               return value2 > 0;
            }

            if (value2 <= 0) {
               return false;
            }
         }

         tmp = true;
      }

      return tmp;
   }

   public void setShowSwings(boolean showSwings) {
      this.J = showSwings;
   }

   public KeyBinding getKeyBinding() {
      return this.x;
   }

   public static void setState(int value) {
      state = value;
   }

   public static int getState() {
      return state;
   }

   public static int getBranchCode() {
      int value = getState();
      return value == 0 ? 123 : 0;
   }

   static {
      if (getState() == 0) {
         setState(34);
      }
   }

   private static JSONException rethrow(JSONException exception) {
      return exception;
   }
}
