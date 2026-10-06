package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.MotionEvent;
import club.evon.event.Subscribe;
import club.evon.module.SubMode;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;




public final class NoFallVanillaMode extends SubMode<NoFall> {
   private static final long KEY = KeyedRandom.create(-7018060746614765838L, -5696841203471108133L, MethodHandles.lookup().lookupClass()).next(188431179328151L);

   public NoFallVanillaMode(String name, NoFall parent) {
      super(name, parent);
   }

   @Subscribe
   private void onMotion(MotionEvent event) {
      long longValue = KEY ^ 22769713267630L;
      if (ClientContext.MINECRAFT_CLIENT.player.fallDistance > MovementUtil.getJumpHeight()) {
         event.setOnGround(true);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
