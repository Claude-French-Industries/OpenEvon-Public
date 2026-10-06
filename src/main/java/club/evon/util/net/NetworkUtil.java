package club.evon.util.net;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.network.packet.Packet;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.manager.OutgoingPacketManager;
import club.evon.mixins.ClientPlayerInteractionManagerAccessor;
import club.evon.module.BaseModule;
import club.evon.util.ClientConnectionAccessor;
import club.evon.util.KeyedRandom;
import club.evon.util.NetworkSideSwitchMap;




public final class NetworkUtil {
   private static String guard;
   private static final long KEY = KeyedRandom.create(-4416783675688375286L, -7711881775388358344L, MethodHandles.lookup().lookupClass()).next(207843652533673L);

   public static void sendManagedPacket(Packet<?> packet) {
      OutgoingPacketManager.sendPacket(packet);
   }

   public static void sendPacket(Packet<?> packet) {
      ClientContext.MINECRAFT_CLIENT.getNetworkHandler().sendPacket(packet);
   }

   public static void sendSequencedPacket(SequencedPacketCreator sequencedPacketCreator) {
      ((ClientPlayerInteractionManagerAccessor)ClientContext.MINECRAFT_CLIENT.interactionManager).callSendSequencedPacket(ClientContext.MINECRAFT_CLIENT.world, sequencedPacketCreator);
   }

   public static void dispatchPacket(Packet<?> packet) {
      label20: {
         long longValue = KEY ^ 12243782319302L;
         String guard2 = getGuard();
         if (guard2 == null) {
            switch (NetworkSideSwitchMap.SWITCH_MAP[packet.getPacketType().side().ordinal()]) {
               case 1:
                  applyPacket(packet);
                  break;
               case 2:
                  break label20;
               default:
                  return;
            }
         }

         if (guard2 == null) {
            return;
         }
      }

      sendPacket(packet);
   }

   public static void dispatchPacketSilently(Packet<?> packet) {
      label26: {
         label25: {
            long longValue = KEY ^ 108089016037054L;
            String guard2 = getGuard();
            if (guard2 == null) {
               switch (NetworkSideSwitchMap.SWITCH_MAP[packet.getPacketType().side().ordinal()]) {
                  case 1:
                     readPacketSilently(packet);
                     break;
                  case 2:
                     break label25;
                  default:
                     break label26;
               }
            }

            if (guard2 == null) {
               break label26;
            }
         }

         sendManagedPacket(packet);
      }

      if (BaseModule.getStateArray() == null) {
         setGuard("jd6yEc");
      }
   }

   public static void applyPacket(Packet<?> packet) {
      ((Packet)packet).apply(ClientContext.MINECRAFT_CLIENT.getNetworkHandler());
   }

   public static void readPacketSilently(Packet<?> packet) {
      long longValue = KEY ^ 56768064059714L;
      String guard2 = getGuard();
      ClientConnectionAccessor clientConnectionAccessor = (ClientConnectionAccessor)ClientContext.MINECRAFT_CLIENT.getNetworkHandler().getConnection();
      String text = guard2;
      clientConnectionAccessor.evon$channelReadSilent(packet);
      if (text != null) {
         BaseModule.setStateArray(new BaseModule[5]);
      }
   }

   public static void setGuard(String text) {
      guard = text;
   }

   public static String getGuard() {
      return guard;
   }

   static {
      if (getGuard() != null) {
         setGuard("vxmAR");
      }
   }

   private static JSONException ignoreException(JSONException exception) {
      return exception;
   }
}
