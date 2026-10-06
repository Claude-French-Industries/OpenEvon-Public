package club.evon.util.math;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.manager.RotationManager;
import club.evon.manager.TimerManager;
import club.evon.module.impl.motion.TargetStrafe;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.RotationUtil;




public final class MovementUtil {
   public static final double GRAVITY = -0.0784000015258789;
   public static final double GRAVITY_STRONG = -0.09800000190734864;
   private static String[] guard;
   private static final long KEY = KeyedRandom.create(-5407000040528493229L, 7560797617165900201L, MethodHandles.lookup().lookupClass()).next(14377130244443L);

   public static boolean isMoving() {
      long longValue = KEY ^ 120015574296686L;
      String[] strings = PlayerUtil.getGuard();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (strings != null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return false;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (strings != null) {
         if (tmp.world == null) {
            return false;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      float floatValue;
      boolean flag = (floatValue = tmp.player.forwardSpeed - 0.0F) != 0.0F;
      if (strings != null) {
         if (!flag) {
            float floatValue2;
            boolean flag2 = (floatValue2 = ClientContext.MINECRAFT_CLIENT.player.sidewaysSpeed - 0.0F) != 0.0F;
            if (strings == null) {
               return flag2;
            }

            if (!flag2) {
               return false;
            }
         }

         flag = true;
      }

      return flag;
   }

   public static void setSpeed(double speed) {
      long longValue = KEY ^ 10059190949164L;
      if (isMoving()) {
         double[] values = RotationUtil.getHorizontalDirection((float)getInputYawRadians(RotationManager.getMouseRotationManager().getYaw(ClientContext.MINECRAFT_CLIENT.player.getYaw())), speed);
         ClientContext.MINECRAFT_CLIENT.player.setVelocity(values[0], ClientContext.MINECRAFT_CLIENT.player.getVelocity().getY(), values[1]);
      }
   }

   public static void setSpeedSmoothly(double speed, double percentage) {
      long longValue = KEY ^ 23712223327285L;
      percentage /= 100.0;
      percentage = MathHelper.clamp(percentage, 0.0, 1.0);
      Vec3d velocity = ClientContext.MINECRAFT_CLIENT.player.getVelocity();
      setSpeed(speed);
      if (isMoving()) {
         ClientContext.MINECRAFT_CLIENT
            .player
            .setVelocity(
               MathHelper.lerp(percentage, velocity.getX(), ClientContext.MINECRAFT_CLIENT.player.getVelocity().getX()),
               ClientContext.MINECRAFT_CLIENT.player.getVelocity().getY(),
               MathHelper.lerp(percentage, velocity.getZ(), ClientContext.MINECRAFT_CLIENT.player.getVelocity().getZ())
            );
      }
   }

   public static float getMovementYaw() {
      Vector2f vector2f = new Vector2f((float)ClientContext.MINECRAFT_CLIENT.player.lastX, (float)ClientContext.MINECRAFT_CLIENT.player.lastZ);
      Vector2f vector2f2 = new Vector2f((float)ClientContext.MINECRAFT_CLIENT.player.getX(), (float)ClientContext.MINECRAFT_CLIENT.player.getZ());
      Vector2f vector2f3 = new Vector2f(vector2f2.getX() - vector2f.getX(), vector2f2.getY() - vector2f.getY());
      return (float)Math.toDegrees((Math.atan2(-vector2f3.getX(), vector2f3.getY()) + (float) (Math.PI * 2)) % (float) (Math.PI * 2));
   }

   public static double getHorizontalDistance() {
      return Math.abs(Math.sqrt(Math.pow(ClientContext.MINECRAFT_CLIENT.player.getX() - ClientContext.MINECRAFT_CLIENT.player.lastX, 2.0) + Math.pow(ClientContext.MINECRAFT_CLIENT.player.getZ() - ClientContext.MINECRAFT_CLIENT.player.lastZ, 2.0)));
   }

   public static double getBlocksPerSecond() {
      return getHorizontalDistance() * TimerManager.getInstance().getTimer() * 20.0;
   }

   public static double getBaseSpeed() {
      long longValue = KEY ^ 73467551562083L;
      return applySpeedEffect(ClientContext.MINECRAFT_CLIENT.player.isOnGround() ? 0.48 : 0.2873);
   }

   public static double applySpeedEffect(double speed) {
      long longValue = KEY ^ 140518960444074L;
      String[] strings = PlayerUtil.getGuard();
      int value = ClientContext.MINECRAFT_CLIENT.player.hasStatusEffect(StatusEffects.SPEED) ? 1 : 0;
      if (strings != null) {
         if (value == 0) {
            return speed;
         }

         value = ClientContext.MINECRAFT_CLIENT.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1;
      }

      int value2 = value;
      speed *= 1.0 + value2 * 0.035;
      return speed;
   }

   public static double getSpeedWithEffect() {
      long longValue = KEY ^ 72325001297099L;
      String[] strings = PlayerUtil.getGuard();
      double doubleValue = 0.59;
      String[] strings2 = strings;
      int value = ClientContext.MINECRAFT_CLIENT.player.hasStatusEffect(StatusEffects.SPEED) ? 1 : 0;
      if (strings2 != null) {
         if (value == 0) {
            return doubleValue;
         }

         value = ClientContext.MINECRAFT_CLIENT.player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
      }

      double doubleValue2 = value >= 1 ? 0.089 : 0.065;
      doubleValue += (ClientContext.MINECRAFT_CLIENT.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1) * doubleValue2;
      return doubleValue;
   }

   public static double getHorizontalVelocity() {
      return Math.sqrt(Math.pow(ClientContext.MINECRAFT_CLIENT.player.getVelocity().getX(), 2.0) + Math.pow(ClientContext.MINECRAFT_CLIENT.player.getVelocity().getZ(), 2.0));
   }

   public static float getInputYaw() {
      return getInputYaw(RotationManager.getMouseRotationManager().getYaw(ClientContext.MINECRAFT_CLIENT.player.getYaw()));
   }

   public static double getInputYawRadians() {
      return getInputYawRadians(RotationManager.getMouseRotationManager().getYaw(ClientContext.MINECRAFT_CLIENT.player.getYaw()));
   }

   public static float getInputYaw(float yaw) {
      String[] strings;
      boolean flag;
      long longValue = KEY ^ 48327877734347L;
      String[] strings2 = PlayerUtil.getGuard();
      TargetStrafe moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(TargetStrafe.class);
      strings = strings2;
      flag = moduleByClass.isEnabled();
      label69:
      if (strings != null) {
         if (flag) {
            flag = moduleByClass.isStrafeKeyHeld();
            if (strings == null) {
               break label69;
            }

            if (flag) {
               yaw = moduleByClass.getStrafeYaw();
            }
         }

         flag = ClientContext.MINECRAFT_CLIENT.options.backKey.isPressed();
      }

      if (flag) {
         yaw += 180.0F;
      }

      float floatValue;
      label61: {
         label72: {
            floatValue = 1.0F;
            flag = ClientContext.MINECRAFT_CLIENT.options.backKey.isPressed();
            if (strings != null) {
               if (flag) {
                  floatValue = -0.5F;
                  if (strings != null) {
                     break label72;
                  }
               }

               flag = ClientContext.MINECRAFT_CLIENT.options.forwardKey.isPressed();
            }

            if (strings == null) {
               break label61;
            }

            if (flag) {
               floatValue = 0.5F;
            }
         }

         flag = ClientContext.MINECRAFT_CLIENT.options.leftKey.isPressed();
      }

      if (strings != null) {
         if (flag) {
            yaw -= 90.0F * floatValue;
            if (strings != null) {
               return yaw;
            }
         }

         flag = ClientContext.MINECRAFT_CLIENT.options.rightKey.isPressed();
      }

      if (flag) {
         yaw += 90.0F * floatValue;
      }

      return yaw;
   }

   public static double getInputYawRadians(float yaw) {
      return Math.toRadians(getInputYaw(yaw));
   }

   public static double getMovementYawRadians(float rotationYaw, double moveForward, double moveStrafing) {
      long longValue = KEY ^ 71461793956293L;
      String[] strings = PlayerUtil.getGuard();
      if (moveForward < 0.0) {
         rotationYaw += 180.0F;
      }

      float floatValue;
      int value;
      label52: {
         label56: {
            floatValue = 1.0F;
            double doubleValue;
            value = (doubleValue = moveForward - 0.0) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
            if (strings != null) {
               if (value < 0) {
                  floatValue = -0.5F;
                  if (strings != null) {
                     break label56;
                  }
               }

               double doubleValue2;
               value = (doubleValue2 = moveForward - 0.0) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);
            }

            if (strings == null) {
               break label52;
            }

            if (value > 0) {
               floatValue = 0.5F;
            }
         }

         double doubleValue3;
         value = (doubleValue3 = moveStrafing - 0.0) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
      }

      if (strings != null) {
         if (value > 0) {
            rotationYaw -= 90.0F * floatValue;
         }

         if (strings == null) {
            return moveStrafing;
         }

         double doubleValue4;
         value = (doubleValue4 = moveStrafing - 0.0) == 0.0 ? 0 : (doubleValue4 < 0.0 ? -1 : 1);
      }

      if (value < 0) {
         rotationYaw += 90.0F * floatValue;
      }

      return Math.toRadians(rotationYaw);
   }

   public static double getMovementYawRadians(double yaw) {
      String[] strings;
      double doubleValue;
      int value;
      label45: {
         label48: {
            long longValue = KEY ^ 55690248049145L;
            String[] strings2 = PlayerUtil.getGuard();
            doubleValue = 1.0;
            strings = strings2;
            float floatValue;
            value = (floatValue = ClientContext.MINECRAFT_CLIENT.player.forwardSpeed - 0.0F) == 0.0F ? 0 : (floatValue < 0.0F ? -1 : 1);
            if (strings != null) {
               if (value < 0) {
                  yaw += 180.0;
                  doubleValue = -0.5;
                  if (strings != null) {
                     break label48;
                  }
               }

               float floatValue2;
               value = (floatValue2 = ClientContext.MINECRAFT_CLIENT.player.forwardSpeed - 0.0F) == 0.0F ? 0 : (floatValue2 < 0.0F ? -1 : 1);
            }

            if (strings == null) {
               break label45;
            }

            if (value > 0) {
               doubleValue = 0.5;
            }
         }

         float floatValue3;
         value = (floatValue3 = ClientContext.MINECRAFT_CLIENT.player.sidewaysSpeed - 0.0F) == 0.0F ? 0 : (floatValue3 < 0.0F ? -1 : 1);
      }

      if (strings != null) {
         if (value > 0) {
            yaw -= 90.0 * doubleValue;
            if (strings != null) {
               return Math.toRadians(yaw);
            }
         }

         float floatValue4;
         value = (floatValue4 = ClientContext.MINECRAFT_CLIENT.player.sidewaysSpeed - 0.0F) == 0.0F ? 0 : (floatValue4 < 0.0F ? -1 : 1);
      }

      if (value < 0) {
         yaw += 90.0 * doubleValue;
      }

      return Math.toRadians(yaw);
   }

   public static double applyGravity(double motion) {
      return (motion - 0.08) * 0.98;
   }

   public static double applyGravityForTicks(double motion, int ticks) {
      long longValue = KEY ^ 115400177733822L;
      String[] strings = PlayerUtil.getGuard();
      if (ticks == 0) {
         return motion;
      }

      double doubleValue = motion;
      int index = 0;

      double doubleValue2;
      while (true) {
         if (index < ticks) {
            doubleValue2 = applyGravity(doubleValue);
            if (strings == null) {
               break;
            }

            doubleValue = doubleValue2;
            index++;
            if (strings != null) {
               continue;
            }
         }

         doubleValue2 = doubleValue;
         break;
      }

      return doubleValue2;
   }

   public static boolean hasGroundBelow(double height) {
      long longValue = KEY ^ 13385849226443L;
      String[] strings = PlayerUtil.getGuard();
      int index = 0;
      String[] strings2 = strings;
      boolean flag;

      while (true) {
         if (index < height) {
            flag = ClientContext.MINECRAFT_CLIENT.world.getBlockState(ClientContext.MINECRAFT_CLIENT.player.getBlockPos().down(index)).isAir();
            if (strings2 == null) {
               break;
            }

            if (strings2 == null) {
               return flag;
            }

            if (!flag) {
               return true;
            }

            index++;
            if (strings2 != null) {
               continue;
            }
         }

         flag = false;
         break;
      }

      return flag;
   }

   public static boolean hasGroundBelow() {
      return hasGroundBelow(Math.round(ClientContext.MINECRAFT_CLIENT.player.getY() + ClientContext.MINECRAFT_CLIENT.player.getEyeHeight(ClientContext.MINECRAFT_CLIENT.player.getPose())));
   }

   public static float getJumpHeight() {
      long longValue = KEY ^ 72137043674974L;
      float floatValue = 3.0F;
      if (ClientContext.MINECRAFT_CLIENT.player.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
         floatValue += ClientContext.MINECRAFT_CLIENT.player.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1;
      }

      return floatValue;
   }

   public static void setGuard(String[] strings) {
      guard = strings;
   }

   public static String[] getGuard() {
      return guard;
   }

   static {
      if (getGuard() == null) {
         setGuard(new String[3]);
      }
   }

   private static JSONException ignoreException(JSONException exception) {
      return exception;
   }
}
