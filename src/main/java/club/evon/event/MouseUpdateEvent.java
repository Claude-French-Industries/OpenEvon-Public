package club.evon.event;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class MouseUpdateEvent {
   private double W;
   private double r;
   private final double D;
   private final boolean O;
   private boolean M;
   private static final long KEY = KeyedRandom.create(4654467361225359953L, -8020401843385128164L, MethodHandles.lookup().lookupClass()).next(77885243135431L);

   public MouseUpdateEvent(double deltaX, double deltaY, double sensitivityMultiplier, boolean unlockCursorRun) {
      super();

      long longValue = KEY ^ 121747458811304L;
      boolean notInitialized = ChatMessageEvent.isNotInitialized();
      this.D = sensitivityMultiplier;
      this.r = deltaY;
      boolean flag = notInitialized;
      this.W = deltaX;
      this.O = unlockCursorRun;
      if (flag) {
         BaseModule.setStateArray(new BaseModule[1]);
      }
      }

   public double getDeltaX() {
      return this.W;
   }

   public double getDeltaY() {
      return this.r;
   }

   public double getSensitivityMultiplier() {
      return this.D;
   }

   public boolean isUnlockCursorRun() {
      return this.O;
   }

   public boolean isHandled() {
      return this.M;
   }

   public void setDeltaX(double deltaX) {
      this.W = deltaX;
   }

   public void setDeltaY(double deltaY) {
      this.r = deltaY;
   }

   public void setHandled(boolean handled) {
      this.M = handled;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
