package club.evon.util.render;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import org.jetbrains.annotations.Nullable;




public final class BipedRenderStateHolder {
   private static final ThreadLocal<BipedEntityRenderState> STATE = ThreadLocal.withInitial(() -> null);

   private BipedRenderStateHolder() {
   }

   @Nullable
   public static BipedEntityRenderState getState() {
      return STATE.get();
   }

   public static void setState(BipedEntityRenderState state) {
      STATE.set(state);
   }

   public static void clear() {
      STATE.remove();
   }
}
