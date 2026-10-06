package club.evon.util;
import java.lang.invoke.MethodHandles;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.json.JSONException;
import club.evon.ClientContext;




public final class RaycastUtil {
   private static final long KEY = KeyedRandom.create(8666196602261477191L, -648997360529390021L, MethodHandles.lookup().lookupClass()).next(37190023248001L);

   public static HitResult raycastFromEntity(Entity entity, double distance, RenderTickCounter renderTickCounter, boolean includeFluids, float[] rotations) {
      long longValue = KEY ^ 8975887551907L;
      Vec3d interpolatedEyePos = RotationUtil.getInterpolatedEyePos(renderTickCounter.getTickProgress(false), entity);
      Vec3d lookVector = RotationUtil.getLookVector(rotations[1], rotations[0]);
      Vec3d add = interpolatedEyePos.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);
      return ClientContext.MINECRAFT_CLIENT.world.raycast(new RaycastContext(interpolatedEyePos, add, ShapeType.OUTLINE, includeFluids ? FluidHandling.ANY : FluidHandling.NONE, entity));
   }

   public static boolean isLookingAtBlock(Direction facing, BlockPos position, boolean strict, float reach, float yaw, float pitch) {
      long longValue = KEY ^ 40204237836823L;
      String[] strings = PlayerUtil.getGuard();
      HitResult raycastFromLocalPlayer = raycastFromLocalPlayer(reach, new float[]{yaw, pitch}, ClientContext.MINECRAFT_CLIENT.getRenderTickCounter(), false);
      String[] strings2 = strings;
      boolean flag = raycastFromLocalPlayer instanceof BlockHitResult;
      if (strings2 != null) {
         if (flag) {
            BlockHitResult blockHitResult = (BlockHitResult)raycastFromLocalPlayer;
            if (strings2 != null) {
               int value = blockHitResult.getBlockPos().getX();
               if (strings2 != null) {
                  if (value == position.getX()) {
                     int value2 = blockHitResult.getBlockPos().getY();
                     if (strings2 == null) {
                        return value2 != 0;
                     }

                     if (value2 == position.getY()) {
                        int value3 = blockHitResult.getBlockPos().getZ();
                        if (strings2 == null) {
                           return value3 != 0;
                        }

                        if (value3 == position.getZ()) {
                           boolean flag2 = strict;
                           if (strings2 != null) {
                              if (strict) {
                                 return blockHitResult.getSide() == facing;
                              }

                              flag2 = true;
                           }

                           return flag2;
                        }
                     }
                  }

                  value = 0;
               }

               return value != 0;
            }
         }

         flag = false;
      }

      return flag;
   }

   public static BlockHitResult raycastBlockFromEntity(Entity entity, double distance, float[] rotations, BlockPos blockPos, BlockState state) {
      Vec3d eyePos = entity.getEyePos();
      Vec3d lookVector = RotationUtil.getLookVector(rotations[1], rotations[0]);
      Vec3d add = eyePos.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);
      return ClientContext.MINECRAFT_CLIENT.world.raycastBlock(eyePos, add, blockPos, state.getOutlineShape(ClientContext.MINECRAFT_CLIENT.world, blockPos, ShapeContext.of(entity)), state);
   }

   public static HitResult raycastFromPlayer(double maxDistance, float tickDelta, boolean includeFluids, float yaw, float pitch) {
      Vec3d interpolatedEyePos = RotationUtil.getInterpolatedEyePos(tickDelta, ClientContext.MINECRAFT_CLIENT.player);
      return raycastFromStart(maxDistance, includeFluids, yaw, pitch, interpolatedEyePos);
   }

   public static HitResult raycastFromStart(double maxDistance, boolean includeFluids, float yaw, float pitch, Vec3d start) {
      long longValue = KEY ^ 86166017073147L;
      Vec3d lookVector = RotationUtil.getLookVector(pitch, yaw);
      Vec3d add = start.add(lookVector.x * maxDistance, lookVector.y * maxDistance, lookVector.z * maxDistance);
      return ClientContext.MINECRAFT_CLIENT.world.raycast(new RaycastContext(start, add, ShapeType.OUTLINE, includeFluids ? FluidHandling.ANY : FluidHandling.NONE, ClientContext.MINECRAFT_CLIENT.player));
   }

   public static EntityHitResult raycastEntityFromPlayer(double maxDistance, float tickDelta, float yaw, float pitch, Predicate<Entity> predicate) {
      return raycastEntityFrom(maxDistance, RotationUtil.getInterpolatedEyePos(tickDelta, ClientContext.MINECRAFT_CLIENT.player), yaw, pitch, predicate);
   }

   public static EntityHitResult raycastEntityFrom(double maxDistance, Vec3d start, float yaw, float pitch, Predicate<Entity> predicate) {
      Vec3d lookVector = RotationUtil.getLookVector(pitch, yaw);
      Vec3d add = start.add(lookVector.x * maxDistance, lookVector.y * maxDistance, lookVector.z * maxDistance);
      Box expand = ClientContext.MINECRAFT_CLIENT.player.getBoundingBox().stretch(lookVector.multiply(maxDistance)).expand(1.0, 1.0, 1.0);
      return ProjectileUtil.raycast(ClientContext.MINECRAFT_CLIENT.player, start, add, expand, predicate, MathHelper.square(maxDistance));
   }

   public static HitResult raycastFromLocalPlayer(double distance, float[] rotations, RenderTickCounter renderTickCounter, boolean includeFluids) {
      return raycastFromEntity(ClientContext.MINECRAFT_CLIENT.player, distance, renderTickCounter, includeFluids, rotations);
   }

   public static BlockHitResult raycastBlockFromPlayer(double distance, float[] rotations, BlockPos blockPos, BlockState state) {
      return raycastBlockFromEntity(ClientContext.MINECRAFT_CLIENT.player, distance, rotations, blockPos, state);
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
