package club.evon.api;
import java.lang.invoke.MethodHandles;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.hypixel.modapi.error.ErrorReason;
import club.evon.manager.SilentSlotManager;
import club.evon.util.KeyedRandom;




public interface HypixelApiErrorEvent {
   public Event<HypixelApiErrorEvent> EVENT = EventFactory.createArrayBacked(HypixelApiErrorEvent.class, var0 -> (var1, var2) -> {
      long var3 = HypixelApiErrorEvent.KEY ^ 3288760371333L;
      HypixelApiErrorEvent[] var6 = var0;
      int[] var10000 = SilentSlotManager.getStateArray();
      int var7 = var6.length;
      int var8 = 0;
      int[] var5 = var10000;

      while (var8 < var7) {
         HypixelApiErrorEvent var9 = var6[var8];
         var9.onError(var1, var2);
         var8++;
         if (var5 == null) {
            break;
         }
      }
   });
   public long KEY = KeyedRandom.create(302543381095523222L, 5829185379141425254L, MethodHandles.lookup().lookupClass()).next(277347648242665L);

   public void onError(String identifier, ErrorReason reason);
}
