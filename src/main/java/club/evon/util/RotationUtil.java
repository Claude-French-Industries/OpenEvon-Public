package club.evon.util;
import com.google.common.base.Predicate;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.RotationHitResult;
import club.evon.manager.RotationManager;
import club.evon.module.BaseModule;
import club.evon.util.math.RandomUtil;




public final class RotationUtil {
   private static int[] state;
   private static final long KEY = KeyedRandom.create(2653888023721052553L, 1927434815995557523L, MethodHandles.lookup().lookupClass()).next(135791588455464L);

   public static Vec2f approachRotation(Vec2f from, Vec2f to, float timeDelta, float speed) {
      long longValue = KEY ^ 76586159186276L;
      String[] strings = PlayerUtil.getGuard();
      float floatValue = MathHelper.wrapDegrees(to.x - from.x) * timeDelta;
      float floatValue2 = (to.y - from.y) * timeDelta;
      double doubleValue = Math.sqrt(floatValue * floatValue + floatValue2 * floatValue2);
      String[] strings2 = strings;
      double doubleValue2 = doubleValue;
      double doubleValue3 = 0.0;
      if (strings2 != null) {
         if (doubleValue == 0.0) {
            return new Vec2f(from.x + floatValue, from.y + floatValue2);
         }

         doubleValue2 = floatValue;
         doubleValue3 = doubleValue;
      }

      double doubleValue4 = Math.abs(doubleValue2 / doubleValue3);
      double doubleValue5 = Math.abs(floatValue2 / doubleValue);
      double doubleValue6 = speed * doubleValue4;
      double doubleValue7 = speed * doubleValue5;
      float floatValue3 = (float)Math.max(Math.min(floatValue, doubleValue6), -doubleValue6);
      float floatValue4 = (float)Math.max(Math.min(floatValue2, doubleValue7), -doubleValue7);
      return new Vec2f(from.x + floatValue3, from.y + floatValue4);
   }

   public static Vec2f getPlayerRotation() {
      return new Vec2f(ClientContext.MINECRAFT_CLIENT.player.getYaw(), ClientContext.MINECRAFT_CLIENT.player.getPitch());
   }

   public static double toMouseDelta(double rotationDelta, double sensitivityMultiplier) {
      return (float)(rotationDelta / sensitivityMultiplier) / 0.15F;
   }

   public static Vec2f snapToSensitivity(Vec2f rotation, Vec2f prevRotation) {
      double doubleValue = (Double)ClientContext.MINECRAFT_CLIENT.options.getMouseSensitivity().getValue() * 0.6F + 0.2F;
      double doubleValue2 = doubleValue * doubleValue * doubleValue * 8.0;
      double doubleValue3 = doubleValue2 * 0.15F;
      float floatValue = rotation.x - prevRotation.x;
      float floatValue2 = rotation.y - prevRotation.y;
      float floatValue3 = prevRotation.x + (float)(Math.round(floatValue / doubleValue3) * doubleValue3);
      float floatValue4 = prevRotation.y + (float)(Math.round(floatValue2 / doubleValue3) * doubleValue3);
      return new Vec2f(floatValue3, floatValue4);
   }

   public static float toSensitivityDelta(double original) {
      double doubleValue = (Double)ClientContext.MINECRAFT_CLIENT.options.getMouseSensitivity().getValue() * 0.6F + 0.2F;
      double doubleValue2 = doubleValue * doubleValue * doubleValue * 8.0;
      return (float)(toMouseDelta(original, doubleValue2) * doubleValue2) * 0.15F;
   }

   public static Vec2f normalizeRotation(Vec2f original) {
      return toSensitivityRotation(snapToSensitivity(original, getPlayerRotation()));
   }

   public static Vec2f toSensitivityRotation(Vec2f original) {
      return new Vec2f(toSensitivityDelta(original.x), toSensitivityDelta(original.y));
   }

   public static Vec2f wrapYawNearPlayer(Vec2f original) {
      Vec2f normalizeRotation = normalizeRotation(original);
      float floatValue = wrapNear(normalizeRotation.x, ClientContext.MINECRAFT_CLIENT.player.getYaw());
      return new Vec2f(floatValue, normalizeRotation.y);
   }

   public static float wrapNear(float value, float target) {
      return target + MathHelper.wrapDegrees(value - target);
   }

   public static boolean isInFov(Entity entity, float fov) {
      long longValue = KEY ^ 122578512938047L;
      String[] strings = PlayerUtil.getGuard();
      Vec2f rotationTo = getRotationTo(entity.getEyePos());
      double doubleValue = MathHelper.angleBetween(RotationManager.getMouseRotationManager().getYaw(ClientContext.MINECRAFT_CLIENT.player.getYaw()), rotationTo.x);
      String[] strings2 = strings;
      double doubleValue2 = MathHelper.angleBetween(RotationManager.getMouseRotationManager().getPitch(ClientContext.MINECRAFT_CLIENT.player.getPitch()), rotationTo.y);
      double doubleValue3;
      int value = (doubleValue3 = doubleValue + doubleValue2 - fov) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
      if (strings2 != null) {
         value = value <= 0 ? 1 : 0;
      }

      return value != 0;
   }

   /**
    * Finds an aim rotation that actually hits {@code entity}.
    *
    * <p>Rewritten from the decompiled form. The sampling loop used to be a {@code while (true)}
    * whose only exits were guarded by this class's flow-guard field, and the one exit that did not
    * depend on it sat after an unconditional {@code if (guard != null) continue;}. Since the guard
    * is always non-null in this build, the loop had no reachable exit and spun forever - which is
    * what froze the game whenever Scaffold had to solve a rotation for a placement that was not
    * directly reachable. The sampling grid, the direct-raycast shortcut and the "closest rotation
    * wins" tail are unchanged; only the control flow is now bounded.
    */
   @Nullable
   public static RotationHitResult findRotationToLivingEntity(LivingEntity entity, Vec3d closestVector, double entityInteractionRange) {
      Predicate<Entity> predicate = candidate -> candidate == entity;
      Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
      Vec2f directRotation = jitteredRotationTo(closestVector);

      EntityHitResult directHit = RaycastUtil.raycastEntityFromPlayer(entityInteractionRange, 1.0F, directRotation.x, directRotation.y, predicate);
      if (directHit != null) {
         return new RotationHitResult(directRotation, directHit);
      }

      float steps = 8.0F - (float)Math.random() * 0.25F;
      List<RotationHitResult> candidates = sampleBox(box, steps, directRotation, rotation ->
         RaycastUtil.raycastEntityFromPlayer(entityInteractionRange, 1.0F, rotation.x, rotation.y, predicate)
      );

      return bestCandidate(candidates, directRotation);
   }


   @Nullable
   public static RotationHitResult findRotationToEntity(Entity entity, Vec3d closestVector, double entityInteractionRange) {
      Predicate<Entity> predicate = candidate -> candidate == entity;
      Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
      Vec2f directRotation = jitteredRotationTo(closestVector);

      EntityHitResult directHit = RaycastUtil.raycastEntityFromPlayer(entityInteractionRange, 1.0F, directRotation.x, directRotation.y, predicate);
      if (directHit != null) {
         return new RotationHitResult(directRotation, directHit);
      }

      float steps = 8.0F - (float)Math.random() * 0.25F;
      List<RotationHitResult> candidates = sampleBox(box, steps, directRotation, rotation ->
         RaycastUtil.raycastEntityFromPlayer(entityInteractionRange, 1.0F, rotation.x, rotation.y, predicate)
      );

      return bestCandidate(candidates, directRotation);
   }


   public static Vec2f getRotationTo(Vec3d pos) {
      return getRotationBetween(ClientContext.MINECRAFT_CLIENT.player.getEyePos(), pos);
   }

   public static Vec2f getRotationBetween(Vec3d from, Vec3d to) {
      double doubleValue = to.getX() - from.getX();
      double doubleValue2 = to.getY() - from.getY();
      double doubleValue3 = to.getZ() - from.getZ();
      double doubleValue4 = Math.sqrt(doubleValue * doubleValue + doubleValue3 * doubleValue3);
      float floatValue = (float)Math.toDegrees(-Math.atan2(doubleValue, doubleValue3));
      float floatValue2 = (float)(-Math.toDegrees(Math.atan2(doubleValue2, doubleValue4)));
      return new Vec2f(floatValue, floatValue2);
   }

   public static Vec3d getLookVector(float pitch, float yaw) {
      float floatValue = pitch * (float) (Math.PI / 180.0);
      float floatValue2 = -yaw * 0.01745329F;
      float floatValue3 = MathHelper.cos(floatValue2);
      float floatValue4 = MathHelper.sin(floatValue2);
      float floatValue5 = MathHelper.cos(floatValue);
      float floatValue6 = MathHelper.sin(floatValue);
      return new Vec3d(floatValue4 * floatValue5, -floatValue6, floatValue3 * floatValue5);
   }

   public static double[] getHorizontalDirection(float yaw, double distance) {
      return new double[]{-MathHelper.sin(yaw) * distance, MathHelper.cos(yaw) * distance};
   }

   public static Vec3d getInterpolatedEyePos(float tickDelta, Entity entity) {
      double doubleValue = MathHelper.lerp(tickDelta, entity.lastX, entity.getX());
      double doubleValue2 = MathHelper.lerp(tickDelta, entity.lastY, entity.getY()) + entity.getStandingEyeHeight();
      double doubleValue3 = MathHelper.lerp(tickDelta, entity.lastZ, entity.getZ());
      return new Vec3d(doubleValue, doubleValue2, doubleValue3);
   }

   public static Vec2f getRotationToFace(BlockPos blockPos, Direction direction) {
      long longValue = KEY ^ 1407338021997L;
      String[] strings = PlayerUtil.getGuard();
      float floatValue = (float)(blockPos.getX() + 0.5 - ClientContext.MINECRAFT_CLIENT.player.getX() + direction.getOffsetX() * 0.5);
      String[] strings2 = strings;
      float floatValue2 = (float)(ClientContext.MINECRAFT_CLIENT.player.getY() + ClientContext.MINECRAFT_CLIENT.player.getEyeHeight(ClientContext.MINECRAFT_CLIENT.player.getPose()) - blockPos.getY() - direction.getOffsetY() * 0.5);
      float floatValue3 = (float)(blockPos.getZ() + 0.5 - ClientContext.MINECRAFT_CLIENT.player.getZ() + direction.getOffsetZ() * 0.5);
      double doubleValue = MathHelper.sqrt(floatValue * floatValue + floatValue3 * floatValue3);
      float floatValue4 = (float)Math.toDegrees(-Math.atan2(floatValue, floatValue3));
      float floatValue5 = (float)Math.toDegrees(Math.atan(floatValue2 / doubleValue));
      Vec2f vec2f = new Vec2f(floatValue4, floatValue5);
      if (strings2 == null) {
         BaseModule.setStateArray(new BaseModule[4]);
      }

      return vec2f;
   }

   @Nullable
   public static RotationHitResult findRotationToBlock(BlockPos blockPos, Direction side, Vec2f priorityRotations, Vec3d playerPos) {
      Box box = new Box(blockPos);

      List<RotationHitResult> candidates = sampleBox(box, 8.0F, priorityRotations, rotation -> {
         HitResult hit = RaycastUtil.raycastFromStart(
            ClientContext.MINECRAFT_CLIENT.player.getBlockInteractionRange(), false, rotation.x, rotation.y, playerPos
         );
         if (hit == null || hit.getType() != Type.BLOCK) {
            return null;
         }

         BlockHitResult blockHit = (BlockHitResult)hit;
         return blockHit.getBlockPos().equals(blockPos) && blockHit.getSide() == side ? hit : null;
      });

      return bestCandidate(candidates, priorityRotations);
   }

   /** A raycast evaluated at one sampled point inside the target box. */
   @FunctionalInterface
   private interface SampleRaycast {
      HitResult cast(Vec2f rotation);
   }

   /**
    * Walks an 8x8x8 (by default) grid of points inside {@code box} and collects the first hit the
    * supplied raycast reports at each one.
    */
   private static List<RotationHitResult> sampleBox(Box box, float steps, Vec2f reference, SampleRaycast raycast) {
      List<RotationHitResult> candidates = new ArrayList<>();
      Vec3d center = box.getCenter();
      double lengthX = box.getLengthX();
      double lengthY = box.getLengthY();
      double lengthZ = box.getLengthZ();
      double stepX = lengthX / steps;
      double stepY = lengthY / steps;
      double stepZ = lengthZ / steps;

      for (double offsetX = -lengthX; offsetX < lengthX; offsetX += stepX) {
         for (double offsetY = -lengthY; offsetY < lengthY; offsetY += stepY) {
            for (double offsetZ = -lengthZ; offsetZ < lengthZ; offsetZ += stepZ) {
               Vec2f rotation = wrapYawNearPlayer(getRotationTo(center.add(new Vec3d(offsetX, offsetY, offsetZ))));
               HitResult hit = raycast.cast(rotation);
               if (hit != null) {
                  RotationHitResult candidate = new RotationHitResult(rotation, hit);
                  candidates.add(candidate);

                  // An aim this close to the requested rotation cannot be meaningfully beaten, so
                  // stop walking the grid. Without this the full 8x8x8 sweep ran for every one of
                  // the (up to 10) placement-search attempts Scaffold makes per tick.
                  if (reference != null && getRotationDifference(rotation, reference) <= 1.0F) {
                     return candidates;
                  }
               }
            }
         }
      }

      return candidates;
   }

   /** The candidate whose rotation differs least from {@code reference}, or null if there are none. */
   @Nullable
   private static RotationHitResult bestCandidate(List<RotationHitResult> candidates, Vec2f reference) {
      if (candidates.isEmpty()) {
         return null;
      }

      candidates.sort(Comparator.comparingDouble(candidate -> getRotationDifference(candidate.rotation(), reference)));
      return candidates.getFirst();
   }

   /** The rotation towards {@code pos}, nudged by a small random offset and wrapped near the player. */
   private static Vec2f jitteredRotationTo(Vec3d pos) {
      Vec2f rotationTo = getRotationTo(pos);
      float jitter = (float)RandomUtil.randomDouble(0.01, 0.05);
      Vec2f offset = new Vec2f(RandomUtil.randomFloat(-jitter, jitter), RandomUtil.randomFloat(-jitter, jitter));
      return wrapYawNearPlayer(rotationTo.add(offset));
   }


   public static float getRotationDifference(Vec2f rotationA, Vec2f rotationB) {
      return MathHelper.angleBetween(rotationA.x, rotationB.x) + Math.abs(rotationA.y - rotationB.y);
   }

   public static Vec3d getPlayerEyePos() {
      float floatValue = ClientContext.MINECRAFT_CLIENT.player.getEyeHeight(ClientContext.MINECRAFT_CLIENT.player.getPose());
      return new Vec3d(ClientContext.MINECRAFT_CLIENT.player.getX(), ClientContext.MINECRAFT_CLIENT.player.getY() + floatValue, ClientContext.MINECRAFT_CLIENT.player.getZ());
   }

   public static Vec3d getClosestPointOnEntity(Entity entity) {
      Vec3d playerEyePos = getPlayerEyePos();
      float floatValue = 0.0F;
      Box expand = entity.getBoundingBox().expand(floatValue);
      double doubleValue = MathHelper.clamp(playerEyePos.getX(), expand.minX, expand.maxX);
      double doubleValue2 = MathHelper.clamp(playerEyePos.getY(), expand.minY, expand.maxY);
      double doubleValue3 = MathHelper.clamp(playerEyePos.getZ(), expand.minZ, expand.maxZ);
      return new Vec3d(doubleValue, doubleValue2, doubleValue3);
   }

   public static void setState(int[] values) {
      state = values;
   }

   public static int[] getState() {
      return state;
   }

   static {
      if (getState() != null) {
         setState(new int[2]);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
