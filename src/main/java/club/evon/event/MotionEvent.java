package club.evon.event;


public final class MotionEvent extends BaseEvent {
   private double R;
   private double e;
   private double k;
   private float S;
   private float w;
   private float G;
   private float c;
   private boolean h;

   public double getX() {
      return this.R;
   }

   public double getY() {
      return this.e;
   }

   public double getZ() {
      return this.k;
   }

   public float getYaw() {
      return this.S;
   }

   public float getPitch() {
      return this.w;
   }

   public float getPrevYaw() {
      return this.G;
   }

   public float getPrevPitch() {
      return this.c;
   }

   public boolean isOnGround() {
      return this.h;
   }

   public void setX(double x) {
      this.R = x;
   }

   public void setY(double y) {
      this.e = y;
   }

   public void setZ(double z) {
      this.k = z;
   }

   public void setYaw(float yaw) {
      this.S = yaw;
   }

   public void setPitch(float pitch) {
      this.w = pitch;
   }

   public void setPrevYaw(float prevYaw) {
      this.G = prevYaw;
   }

   public void setPrevPitch(float prevPitch) {
      this.c = prevPitch;
   }

   public void setOnGround(boolean onGround) {
      this.h = onGround;
   }

   public MotionEvent(double x, double y, double z, float yaw, float pitch, float prevYaw, float prevPitch, boolean onGround) {
      this.R = x;
      this.e = y;
      this.k = z;
      this.S = yaw;
      this.w = pitch;
      this.G = prevYaw;
      this.c = prevPitch;
      this.h = onGround;
   }
}
