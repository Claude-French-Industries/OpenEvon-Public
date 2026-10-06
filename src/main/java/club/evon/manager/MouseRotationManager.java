package club.evon.manager;
import java.lang.invoke.MethodHandles;
import net.minecraft.util.math.Vec2f;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.EventBus;
import club.evon.event.MouseUpdateEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;
import club.evon.util.RotationUtil;




public final class MouseRotationManager {
   private Vec2f Y;
   private boolean updating;
   private float lastRenderYaw;
   private float renderYaw;
   private float lastRenderPitch;
   private float renderPitch;
   private static String flowGuard;
   private static final long KEY = KeyedRandom.create(7791061390677093680L, 1494291138135756402L, MethodHandles.lookup().lookupClass()).next(57421458649807L);

   public MouseRotationManager() {
      EventBus.register(this);
   }

   @Subscribe(h = 1)
   public void onMouseUpdate(MouseUpdateEvent event) {
      long longValue = KEY ^ 140726013675580L;
      String flowGuard2 = getFlowGuard();
      if (flowGuard2 != null) {
         if (ClientContext.MINECRAFT_CLIENT.player != null && !event.isUnlockCursorRun()) {
            label48: {
               MouseRotationManager tmp = this;
               if (flowGuard2 != null) {
                  if (this.Y != null) {
                     break label48;
                  }

                  tmp = this;
               }

               tmp.initRotation();
            }

            double doubleValue;
            double doubleValue2;
            int value;
            label42: {
               double doubleValue3 = event.getSensitivityMultiplier();
               doubleValue = event.getDeltaX() * doubleValue3;
               doubleValue2 = event.getDeltaY() * doubleValue3;
               value = 1;
               int value2 = (Boolean)ClientContext.MINECRAFT_CLIENT.options.getInvertMouseY().getValue() ? 1 : 0;
               if (flowGuard2 != null) {
                  if (value2 == 0) {
                     break label42;
                  }

                  value2 = -1;
               }

               value = value2;
            }

            float floatValue = (float)doubleValue * 0.15F;
            float floatValue2 = (float)(doubleValue2 * value) * 0.15F;
            float floatValue3 = this.Y.x + floatValue;
            float floatValue4 = this.Y.y + floatValue2;
            this.Y = new Vec2f(floatValue3, Math.clamp(floatValue4 % 360.0F, -90.0F, 90.0F));
         }

         this.updating = true;
      }

      if (BaseModule.getStateArray() == null) {
         setFlowGuard("jI9YZ");
      }
   }

   private void initRotation() {
      this.Y = RotationUtil.getPlayerRotation();
      this.lastRenderYaw = ClientContext.MINECRAFT_CLIENT.player.lastRenderYaw;
      this.renderYaw = ClientContext.MINECRAFT_CLIENT.player.renderYaw;
      this.lastRenderPitch = ClientContext.MINECRAFT_CLIENT.player.lastRenderPitch;
      this.renderPitch = ClientContext.MINECRAFT_CLIENT.player.renderPitch;
   }

   public void updateRenderRotation() {
      long longValue = KEY ^ 99848593271564L;
      String flowGuard2 = getFlowGuard();
      MouseRotationManager tmp = this;
      if (flowGuard2 != null) {
         if (this.Y == null) {
            return;
         }

         this.lastRenderYaw = this.renderYaw;
         this.lastRenderPitch = this.renderPitch;
         this.renderPitch = this.renderPitch + (this.Y.y - this.renderPitch) * 0.5F;
         tmp = this;
      }

      tmp.renderYaw = this.renderYaw + (this.Y.x - this.renderYaw) * 0.5F;
   }

   public void endUpdate() {
      this.updating = false;
   }

   public void clearRotationIfNotUpdating() {
      long longValue = KEY ^ 66470859340649L;
      String flowGuard2 = getFlowGuard();
      MouseRotationManager tmp = this;
      if (flowGuard2 != null) {
         if (this.updating) {
            return;
         }

         tmp = this;
      }

      tmp.Y = null;
   }

   public float getYaw(float fallback) {
      long longValue = KEY ^ 86877719526632L;
      String flowGuard2 = getFlowGuard();
      Vec2f tmp = this.Y;
      if (flowGuard2 != null) {
         if (this.Y == null) {
            return fallback;
         }

         tmp = this.Y;
      }

      return tmp.x;
   }

   public float getPitch(float fallback) {
      long longValue = KEY ^ 85962817428057L;
      String flowGuard2 = getFlowGuard();
      Vec2f tmp = this.Y;
      if (flowGuard2 != null) {
         if (this.Y == null) {
            return fallback;
         }

         tmp = this.Y;
      }

      return tmp.y;
   }

   public float getLastRenderYaw(float fallback) {
      long longValue = KEY ^ 19456615708704L;
      String flowGuard2 = getFlowGuard();
      MouseRotationManager tmp = this;
      if (flowGuard2 != null) {
         if (this.Y == null) {
            return fallback;
         }

         tmp = this;
      }

      return tmp.lastRenderYaw;
   }

   public float getLastRenderPitch(float fallback) {
      long longValue = KEY ^ 50168471022352L;
      String flowGuard2 = getFlowGuard();
      MouseRotationManager tmp = this;
      if (flowGuard2 != null) {
         if (this.Y == null) {
            return fallback;
         }

         tmp = this;
      }

      return tmp.lastRenderPitch;
   }

   public float getRenderYaw(float fallback) {
      long longValue = KEY ^ 64829084849570L;
      String flowGuard2 = getFlowGuard();
      MouseRotationManager tmp = this;
      if (flowGuard2 != null) {
         if (this.Y == null) {
            return fallback;
         }

         tmp = this;
      }

      return tmp.renderYaw;
   }

   public float getRenderPitch(float fallback) {
      long longValue = KEY ^ 10352398674617L;
      String flowGuard2 = getFlowGuard();
      MouseRotationManager tmp = this;
      if (flowGuard2 != null) {
         if (this.Y == null) {
            return fallback;
         }

         tmp = this;
      }

      return tmp.renderPitch;
   }

   public Vec2f getRotation() {
      long longValue = KEY ^ 13165283545813L;
      String flowGuard2 = getFlowGuard();
      MouseRotationManager tmp = this;
      if (flowGuard2 != null) {
         if (this.Y == null) {
            return RotationUtil.getPlayerRotation();
         }

         tmp = this;
      }

      return tmp.Y;
   }

   public void setRotation(Vec2f rotation) {
      this.Y = rotation;
   }

   public void setUpdating(boolean ticking) {
      this.updating = ticking;
   }

   public static void setFlowGuard(String value) {
      flowGuard = value;
   }

   public static String getFlowGuard() {
      return flowGuard;
   }

   static {
      if (getFlowGuard() == null) {
         setFlowGuard("xAAvWc");
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
