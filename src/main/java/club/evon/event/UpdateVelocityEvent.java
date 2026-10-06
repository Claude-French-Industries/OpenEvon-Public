package club.evon.event;
import net.minecraft.util.math.Vec3d;




public final class UpdateVelocityEvent {
   private final float u;
   private final Vec3d K;

   public float getSpeed() {
      return this.u;
   }

   public Vec3d getMovementInput() {
      return this.K;
   }

   public UpdateVelocityEvent(float speed, Vec3d movementInput) {
      this.u = speed;
      this.K = movementInput;
   }
}
