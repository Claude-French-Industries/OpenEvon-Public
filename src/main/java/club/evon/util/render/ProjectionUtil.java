package club.evon.util.render;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4d;
import org.joml.Vector4f;
import org.json.JSONException;
import org.lwjgl.opengl.GL11C;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.module.impl.motion.Scaffold;
import club.evon.util.KeyedRandom;




public final class ProjectionUtil {
   public static final Matrix4f projectionMatrix = new Matrix4f();
   public static final Matrix4f modelViewMatrix = new Matrix4f();
   public static final Matrix4f positionMatrix = new Matrix4f();
   private static final long KEY = KeyedRandom.create(4193218418251250369L, 475105229866009643L, MethodHandles.lookup().lookupClass()).next(91593564010288L);

   public static Vector4d projectBounds(List<Vec3d> bounds) {
      long longValue = KEY ^ 11003262331239L;
      Vector4d vector4d = null;
      String[] strings = RenderUtil.getGuardState();
      Iterator iterator = bounds.iterator();
      String[] strings2 = strings;

      while (iterator.hasNext()) {
         label36: {
            Vec3d pos = (Vec3d)iterator.next();
            pos = worldToScreen(pos);
            double doubleValue;
            int value = (doubleValue = pos.z - 0.0) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
            if (strings2 == null) {
               if (value <= 0) {
                  break label36;
               }

               double doubleValue2;
               value = (doubleValue2 = pos.z - 1.0) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);
            }

            if (value < 0) {
               Vector4d vector4d2 = vector4d;
               if (strings2 == null) {
                  if (vector4d == null) {
                     vector4d = new Vector4d(pos.x, pos.y, pos.z, 0.0);
                  }

                  vector4d.x = Math.min(vector4d.x, pos.x);
                  vector4d.y = Math.min(vector4d.y, pos.y);
                  vector4d.z = Math.max(vector4d.z, pos.x);
                  vector4d2 = vector4d;
               }

               vector4d2.w = Math.max(vector4d.w, pos.y);
            }
         }

         if (strings2 != null) {
            break;
         }
      }

      return vector4d;
   }

   public static Vector4d getEntityBounds(Entity entity) {
      long longValue = KEY ^ 30157749133160L;
      String[] strings = RenderUtil.getGuardState();
      Vector4d vector4d = null;
      String[] strings2 = strings;
      Vec3d[] values = getEntityCorners(entity);
      int length = values.length;
      int index = 0;

      while (index < length) {
         Vec3d pos = values[index];
         pos = worldToScreen(pos);
         if (strings2 == null) {
            if (pos.z > 0.0 && pos.z < 1.0) {
               Vector4d vector4d2 = vector4d;
               if (strings2 == null) {
                  if (vector4d == null) {
                     vector4d = new Vector4d(pos.x, pos.y, pos.z, 0.0);
                  }

                  vector4d.x = Math.min(vector4d.x, pos.x);
                  vector4d.y = Math.min(vector4d.y, pos.y);
                  vector4d.z = Math.max(vector4d.z, pos.x);
                  vector4d2 = vector4d;
               }

               vector4d2.w = Math.max(vector4d.w, pos.y);
            }

            index++;
         }

         if (strings2 != null) {
            break;
         }
      }

      return vector4d;
   }

   public static Vec3d getRelativePosition(Entity entity) {
      float floatValue = ClientContext.MINECRAFT_CLIENT.getRenderTickCounter().getTickProgress(false);
      Vec3d pos = ClientContext.MINECRAFT_CLIENT.gameRenderer.getCamera().getPos();
      double doubleValue = MathHelper.lerp(floatValue, entity.lastX, entity.getX()) - pos.x;
      double doubleValue2 = MathHelper.lerp(floatValue, entity.lastY, entity.getY()) - pos.y;
      double doubleValue3 = MathHelper.lerp(floatValue, entity.lastZ, entity.getZ()) - pos.z;
      return new Vec3d(doubleValue, doubleValue2, doubleValue3);
   }

   public static Vec3d[] getEntityCorners(Entity entity) {
      Vec3d relativePosition = getRelativePosition(entity);
      double doubleValue = relativePosition.x;
      double doubleValue2 = relativePosition.y;
      double doubleValue3 = relativePosition.z;
      double doubleValue4 = getEyeHeight(entity);
      double doubleValue5 = entity.getWidth() / 1.7;
      Box box = new Box(doubleValue - doubleValue5, doubleValue2, doubleValue3 - doubleValue5, doubleValue + doubleValue5, doubleValue2 + doubleValue4, doubleValue3 + doubleValue5);
      return new Vec3d[]{
         new Vec3d(box.minX, box.minY, box.minZ),
         new Vec3d(box.minX, box.maxY, box.minZ),
         new Vec3d(box.maxX, box.minY, box.minZ),
         new Vec3d(box.maxX, box.maxY, box.minZ),
         new Vec3d(box.minX, box.minY, box.maxZ),
         new Vec3d(box.minX, box.maxY, box.maxZ),
         new Vec3d(box.maxX, box.minY, box.maxZ),
         new Vec3d(box.maxX, box.maxY, box.maxZ)
      };
   }

   private static double getEyeHeight(Entity entity) {
      long longValue = KEY ^ 64639957969922L;
      String[] strings = RenderUtil.getGuardState();
      Entity tmp = entity;
      Entity tmp2 = ClientContext.MINECRAFT_CLIENT.player;
      if (strings == null) {
         if (entity == ClientContext.MINECRAFT_CLIENT.player) {
            Scaffold moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Scaffold.class);
            boolean enabled = moduleByClass.isEnabled();
            if (strings == null) {
               if (!enabled) {
                  return entity.getEyeHeight(entity.getPose()) + 0.3;
               }

               enabled = moduleByClass.isHypixel();
            }

            if (enabled) {
               return entity.getStandingEyeHeight() + 0.3;
            }

            return entity.getEyeHeight(entity.getPose()) + 0.3;
         }

         tmp = entity;
         tmp2 = entity;
      }

      return tmp.getEyeHeight(tmp2.getPose()) + 0.3;
   }

   public static Vec3d worldToScreen(Vec3d position) {
      int[] values = new int[4];
      GL11C.glGetIntegerv(2978, values);
      Vector4f mul = new Vector4f((float)position.x, (float)position.y, (float)position.z, 0.0F).mul(positionMatrix);
      Matrix4f matrix4f = new Matrix4f(projectionMatrix);
      Vector3f vector3f = new Vector3f();
      matrix4f.project(mul.x, mul.y, mul.z, values, vector3f);
      double doubleValue = ClientContext.MINECRAFT_CLIENT.getWindow().getScaleFactor();
      return new Vec3d(vector3f.x / doubleValue, (ClientContext.MINECRAFT_CLIENT.getWindow().getHeight() - vector3f.y) / doubleValue, vector3f.z);
   }

   private static JSONException rethrow(JSONException exception) {
      return exception;
   }
}
