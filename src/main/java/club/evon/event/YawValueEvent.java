package club.evon.event;


public final class YawValueEvent implements Event {
   private float J;

   public float getYaw() {
      return this.J;
   }

   public void setYaw(float yaw) {
      this.J = yaw;
   }

   public YawValueEvent(float yaw) {
      this.J = yaw;
   }
}
