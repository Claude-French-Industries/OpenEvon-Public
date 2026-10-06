package club.evon.event;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.util.KeyedRandom;




public final class MovementInputEvent extends BaseEvent {
   private float H;
   private float I;
   private boolean U;
   private boolean c;
   private static final long xorKey = KeyedRandom.create(-4976458137127410524L, -2821013885746833051L, MethodHandles.lookup().lookupClass()).next(82255045382799L);

   public void reset() {
      this.H = 0.0F;
      this.I = 0.0F;
      this.U = false;
      this.c = false;
   }

   public boolean hasMovementInput() {
      long longValue = xorKey ^ 132040251348048L;
      int[] values = ClientTickEvent.getState();
      float floatValue;
      int value = (floatValue = this.H - 0.0F) == 0.0F ? 0 : (floatValue < 0.0F ? -1 : 1);
      if (values != null) {
         if (value != 0) {
            return true;
         }

         float floatValue2;
         value = (floatValue2 = this.I - 0.0F) == 0.0F ? 0 : (floatValue2 < 0.0F ? -1 : 1);
         if (values == null) {
            return value != 0;
         }

         if (value == 0) {
            return false;
         }

         value = 1;
      }

      return value != 0;
   }

   public float getForward() {
      return this.H;
   }

   public float getSideways() {
      return this.I;
   }

   public boolean isJump() {
      return this.U;
   }

   public boolean isSneak() {
      return this.c;
   }

   public void setForward(float forward) {
      this.H = forward;
   }

   public void setSideways(float sideways) {
      this.I = sideways;
   }

   public void setJump(boolean jump) {
      this.U = jump;
   }

   public void setSneak(boolean sneak) {
      this.c = sneak;
   }

   public MovementInputEvent(float forward, float sideways, boolean jump, boolean sneak) {
      this.H = forward;
      this.I = sideways;
      this.U = jump;
      this.c = sneak;
   }

   private static JSONException identity(JSONException jSONException) {
      return jSONException;
   }
}
