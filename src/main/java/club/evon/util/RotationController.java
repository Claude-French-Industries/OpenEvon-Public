package club.evon.util;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec2f;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.ClientTickEvent;
import club.evon.event.MouseUpdateEvent;
import club.evon.event.Subscribe;
import club.evon.manager.MouseRotationManager;
import club.evon.manager.RotationManager;
import club.evon.module.BaseModule;




public final class RotationController {
   private Vec2f d;
   private boolean rotating;
   private boolean pending;
   private Float j;
   private Vec2f currentRotation;
   private boolean manualTick;
   private boolean forceLock;
   private static final long KEY = KeyedRandom.create(1610306107844463212L, -5040084140541418240L, MethodHandles.lookup().lookupClass()).next(273123445007295L);

   public RotationController() {
      club.evon.event.EventBus.register(this);
   }

   @Subscribe
   public void onMouseUpdate(MouseUpdateEvent event) {
      RotationController tmp;
      long longValue = KEY ^ 31959467201146L;
      String flowGuard = MouseRotationManager.getFlowGuard();
      tmp = this;
      label86:
      if (flowGuard != null) {
         if (this.currentRotation != null) {
            tmp = this;
            if (flowGuard == null) {
               break label86;
            }

            label95:
            if (this.d != null && ClientContext.MINECRAFT_CLIENT.player != null) {
               boolean flag = this.rotating;
               if (flowGuard != null) {
                  if (!this.rotating) {
                     break label95;
                  }

                  flag = this.pending;
               }

               if (flowGuard != null) {
                  label80: {
                     if (!flag) {
                        this.refreshTargetRotation();
                        tmp = this;
                        if (flowGuard == null) {
                           break label80;
                        }

                        if (this.d == null) {
                           this.manualTick = false;
                           return;
                        }
                     }

                     tmp = this;
                  }

                  flag = tmp.manualTick;
               }

               float floatValue;
               label74: {
                  if (flag) {
                     floatValue = 1.0F;
                     this.manualTick = false;
                     if (flowGuard != null) {
                        break label74;
                     }
                  }

                  floatValue = MinecraftClient.getInstance().getRenderTickCounter().getTickProgress(false);
               }

               double doubleValue = event.getSensitivityMultiplier();
               Vec2f approachRotation = RotationUtil.approachRotation(this.currentRotation, this.d, floatValue, this.j + (float)((Math.random() - 0.5) * 5.0));
               double doubleValue2 = approachRotation.x - ClientContext.MINECRAFT_CLIENT.player.getYaw();
               double doubleValue3 = RotationUtil.toMouseDelta(doubleValue2, doubleValue);
               double doubleValue4 = approachRotation.y - ClientContext.MINECRAFT_CLIENT.player.getPitch();
               double doubleValue5 = RotationUtil.toMouseDelta(doubleValue4, doubleValue);
               boolean flag2 = (Boolean)ClientContext.MINECRAFT_CLIENT.options.getInvertMouseY().getValue();
               if (flowGuard != null) {
                  if (flag2) {
                     doubleValue5 *= -1.0;
                  }

                  event.setDeltaX(doubleValue3);
                  event.setDeltaY(doubleValue5);
                  event.setHandled(true);
                  flag2 = this.pending;
               }

               if (flowGuard != null) {
                  if (flag2) {
                     return;
                  }

                  flag2 = RotationUtil.getRotationDifference(approachRotation, this.d) != 0.0;
               }

               if (!flag2) {
                  this.d = null;
                  this.j = null;
                  this.rotating = false;
               }

               return;
            }
         }

         tmp = this;
      }

      tmp.manualTick = false;
   }

   @Subscribe(h = 8)
   public void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 12829443440927L;
      String flowGuard = MouseRotationManager.getFlowGuard();
      if (flowGuard != null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         this.tickMouse();
         this.consumePendingTarget();
         this.setCurrentRotation(RotationUtil.getPlayerRotation());
         this.forceLock = false;
      }
   }

   public boolean shouldForceCursorLock() {
      long longValue = KEY ^ 33310433463410L;
      String flowGuard = MouseRotationManager.getFlowGuard();
      boolean tmp = this.forceLock;
      if (flowGuard != null) {
         if (!this.forceLock) {
            return false;
         }

         tmp = this.manualTick;
      }

      return flowGuard == null ? tmp : tmp;
   }

   public void setCurrentRotation(Vec2f tickRotation) {
      this.currentRotation = tickRotation;
   }

   public void consumePendingTarget() {
      long longValue = KEY ^ 80095997696290L;
      String flowGuard = MouseRotationManager.getFlowGuard();
      RotationController tmp = this;
      if (flowGuard != null) {
         if (!this.pending) {
            return;
         }

         this.refreshTargetRotation();
         tmp = this;
      }

      tmp.pending = false;
   }

   private void tickMouse() {
      long longValue = KEY ^ 18131462425173L;
      String flowGuard = MouseRotationManager.getFlowGuard();
      RotationController tmp = this;
      if (flowGuard != null) {
         if (!this.rotating) {
            return;
         }

         this.manualTick = true;
         ClientContext.MINECRAFT_CLIENT.mouse.tick();
         tmp = this;
      }

      tmp.manualTick = false;
   }

   private void refreshTargetRotation() {
      MouseRotationManager mouseRotationManager = RotationManager.getMouseRotationManager();
      this.d = mouseRotationManager.getRotation();
   }

   public void rotateTo(Vec2f targetRotation, float speed) {
      long longValue = KEY ^ 94807084029947L;
      this.d = targetRotation;
      this.j = speed;
      String flowGuard = MouseRotationManager.getFlowGuard();
      this.pending = true;
      this.rotating = true;
      String text = flowGuard;
      this.tickMouse();
      if (text == null) {
         BaseModule.setStateArray(new BaseModule[4]);
      }
   }

   public void forceCursorLock() {
      this.forceLock = true;
   }

   public Vec2f getTargetRotation() {
      return this.d;
   }

   public boolean isRotating() {
      return this.rotating;
   }

   public boolean isPending() {
      return this.pending;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
