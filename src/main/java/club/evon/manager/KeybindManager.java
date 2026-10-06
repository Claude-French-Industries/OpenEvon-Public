package club.evon.manager;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.option.KeyBinding;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.util.KeyBindingState;




public final class KeybindManager {
   private final Map<KeyBinding, KeyBindingState> states = new HashMap<>();
   private final KeyBindingState attackState = this.register(new KeyBindingState(ClientContext.MINECRAFT_CLIENT.options.attackKey));
   private final KeyBindingState useState = this.register(new KeyBindingState(ClientContext.MINECRAFT_CLIENT.options.useKey));
   private static KeybindManager instance;
   private static boolean flowGuard;

   private KeybindManager() {
   }

   private <T extends KeyBindingState> T register(T state) {
      this.states.put(state.getKeyBinding(), state);
      return (T)state;
   }

   public void tick() {
      this.attackState.tick();
      this.useState.tick();
   }

   public static KeyBindingState getState(KeyBinding binding) {
      return instance.states.get(binding);
   }

   public static KeyBindingState getAttackState() {
      return instance.attackState;
   }

   public static KeyBindingState getUseState() {
      return instance.useState;
   }

   public static void init() {
      instance = new KeybindManager();
   }

   public static KeybindManager getInstance() {
      return instance;
   }

   public static void setFlowGuard(boolean value) {
      flowGuard = value;
   }

   public static boolean getFlowGuard() {
      return flowGuard;
   }

   public static boolean isFlowGuardUnset() {
      boolean flowGuard2 = getFlowGuard();
      return !flowGuard2;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }

   static {
      if (isFlowGuardUnset()) {
         setFlowGuard(true);
      }
   }
}
