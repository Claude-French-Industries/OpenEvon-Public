package club.evon.manager;
import java.lang.invoke.MethodHandles;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;
import club.evon.util.net.PacketQueue;




public final class OutgoingPacketManager extends PacketQueue<ServerPlayNetworkHandler> {
   private static final OutgoingPacketManager instance = new OutgoingPacketManager();
   private static boolean enabled;
   private static final long KEY = KeyedRandom.create(-287543885811604891L, -220668950659970651L, MethodHandles.lookup().lookupClass()).next(235292813353224L);

   public static void sendPacket(Packet<?> packet) {
      long longValue = KEY ^ 56664338894747L;
      boolean enabled2 = isEnabled();
      ClientContext.MINECRAFT_CLIENT.getNetworkHandler().getConnection().send(packet, null, true);
      boolean flag = enabled2;
      if (!flag) {
         BaseModule.setStateArray(new BaseModule[2]);
      }
   }

   @Override
   public void dispatchPacket(ClientConnection connection, Packet<?> packet) {
      connection.send(packet, null);
   }

   public static OutgoingPacketManager getInstance() {
      return instance;
   }

   static {
      setEnabled(true);
   }

   public static void setEnabled(boolean flag) {
      enabled = flag;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }
}
