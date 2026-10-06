package club.evon.event;
import net.minecraft.util.math.Vec3d;




public final class MovementCollisionEvent extends BaseEvent {
   private final Vec3d V;

   public Vec3d getMovement() {
      return this.V;
   }

   public MovementCollisionEvent(Vec3d movement) {
      this.V = movement;
   }
}
