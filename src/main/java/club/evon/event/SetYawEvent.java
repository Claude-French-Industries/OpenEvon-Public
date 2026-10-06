package club.evon.event;


public final class SetYawEvent extends BaseEvent {
   private float J;

   public float getYaw() {
      return this.J;
   }

   public void setYaw(float yaw) {
      this.J = yaw;
   }

   public SetYawEvent(float yaw) {
      this.J = yaw;
   }
}
