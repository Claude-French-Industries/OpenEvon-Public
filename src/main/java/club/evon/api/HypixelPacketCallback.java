package club.evon.api;
import java.lang.invoke.MethodHandles;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.hypixel.modapi.packet.ClientboundHypixelPacket;
import club.evon.manager.SilentSlotManager;
import club.evon.util.KeyedRandom;




public interface HypixelPacketCallback {
   public Event<HypixelPacketCallback> EVENT = EventFactory.createArrayBacked(HypixelPacketCallback.class, var0 -> var1 -> {
      long var2 = HypixelPacketCallback.KEY ^ 140191175262255L;
      int[] var10000 = SilentSlotManager.getStateArray();
      HypixelPacketCallback[] var5 = var0;
      int var6 = var5.length;
      int[] var4 = var10000;
      int var7 = 0;

      while (var7 < var6) {
         HypixelPacketCallback var8 = var5[var7];
         var8.onPacket(var1);
         var7++;
         if (var4 == null) {
            break;
         }
      }
   });
   public long KEY = KeyedRandom.create(3783073214294293191L, 4488657649544500483L, MethodHandles.lookup().lookupClass()).next(68284986910069L);

   public void onPacket(ClientboundHypixelPacket packet);
}
