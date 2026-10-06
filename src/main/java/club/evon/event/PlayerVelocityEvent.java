package club.evon.event;
import net.minecraft.util.math.Vec3d;




public final class PlayerVelocityEvent extends BaseEvent {
   private final Vec3d y;
   private final boolean u;

   public Vec3d getVelocity() {
      return this.y;
   }

   public boolean isExplosion() {
      return this.u;
   }

   public PlayerVelocityEvent(Vec3d velocity, boolean explosion) {
      this.y = velocity;
      this.u = explosion;
   }
}
