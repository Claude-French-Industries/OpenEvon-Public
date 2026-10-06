package club.evon.event;


public final class YawEvent implements Event {
   private float P;

   public float getYaw() {
      return this.P;
   }

   public void setYaw(float yaw) {
      this.P = yaw;
   }

   public YawEvent(float yaw) {
      this.P = yaw;
   }
}
