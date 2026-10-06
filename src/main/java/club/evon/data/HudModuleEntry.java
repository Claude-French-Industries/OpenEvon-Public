package club.evon.data;
import club.evon.module.BaseModule;
import club.evon.util.Animator;
import club.evon.util.math.Easing;



public final class HudModuleEntry {
   private final BaseModule h;
   private float v;
   private final Animator animator = new Animator(200, 0.0, Easing.DECELERATE);

   public BaseModule getModule() {
      return this.h;
   }

   public float getLastY() {
      return this.v;
   }

   public Animator getAnimator() {
      return this.animator;
   }

   public void setLastY(float lastY) {
      this.v = lastY;
   }

   public HudModuleEntry(BaseModule module) {
      this.h = module;
   }
}
