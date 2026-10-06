package club.evon.event;
import club.evon.module.BaseModule;



public final class ModuleToggleEvent extends BaseEvent {
   private final BaseModule n;
   private final boolean enabled;

   public BaseModule getModule() {
      return this.n;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public ModuleToggleEvent(BaseModule module, boolean newState) {
      this.n = module;
      this.enabled = newState;
   }
}
