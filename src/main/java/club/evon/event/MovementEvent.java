package club.evon.event;
import java.lang.invoke.MethodHandles;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.module.BaseModule;
import club.evon.module.impl.motion.TargetStrafe;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;




public final class MovementEvent implements Event {
   private Vec3d g;
   private static final long KEY = KeyedRandom.create(9140996949755137159L, 9114223504151018911L, MethodHandles.lookup().lookupClass()).next(66798765978680L);

   public float getSpeed() {
      return (float)Math.sqrt(this.g.getX() * this.g.getX() + this.g.getZ() * this.g.getZ());
   }

   public void setSpeed(double speed) {
      int[] values;
      double doubleValue;
      TargetStrafe targetStrafe;
      label34: {
         label33: {
            label32: {
               long longValue = KEY ^ 90467954962521L;
               int[] values2 = ClientTickEvent.getState();
               TargetStrafe moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(TargetStrafe.class);
               values = values2;
               boolean enabled = moduleByClass.isEnabled();
               if (values != null) {
                  if (!enabled) {
                     break label32;
                  }

                  targetStrafe = moduleByClass;
                  if (values == null) {
                     break label33;
                  }

                  enabled = moduleByClass.isStrafeKeyHeld();
               }

               if (enabled) {
                  targetStrafe = moduleByClass;
                  break label33;
               }
            }

            doubleValue = ClientContext.MINECRAFT_CLIENT.player.getYaw();
            break label34;
         }

         doubleValue = targetStrafe.getStrafeYaw().floatValue();
      }

      double doubleValue2 = MovementUtil.getMovementYawRadians(doubleValue);
      this.g = new Vec3d(-MathHelper.sin((float)doubleValue2) * speed, this.g.getY(), MathHelper.cos((float)doubleValue2) * speed);
      ClientContext.MINECRAFT_CLIENT.player.setVelocity(this.g.withAxis(Axis.Y, ClientContext.MINECRAFT_CLIENT.player.getVelocity().getY()));
      if (values == null) {
         BaseModule.setStateArray(new BaseModule[5]);
      }
   }

   public void setSpeedWithYaw(double speed, float yaw) {
      long longValue = KEY ^ 91579877329962L;
      ClientTickEvent.getState();
      this.g = new Vec3d(-MathHelper.sin(yaw) * speed, this.g.getY(), MathHelper.cos(yaw) * speed);
      ClientContext.MINECRAFT_CLIENT.player.setVelocity(this.g.withAxis(Axis.Y, ClientContext.MINECRAFT_CLIENT.player.getVelocity().getY()));
      if (BaseModule.getStateArray() == null) {
         ClientTickEvent.setState(new int[3]);
      }
   }

   public Vec3d getMovement() {
      return this.g;
   }

   public void setMovement(Vec3d movement) {
      this.g = movement;
   }

   public MovementEvent(Vec3d movement) {
      this.g = movement;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
