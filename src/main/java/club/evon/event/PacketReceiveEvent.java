package club.evon.event;
import net.minecraft.network.packet.Packet;
import club.evon.module.BaseModule;




public final class PacketReceiveEvent extends BaseEvent {
   private final Packet<?> B;
   private static BaseModule[] moduleState;

   public Packet<?> getPacket() {
      return this.B;
   }

   public PacketReceiveEvent(Packet<?> packet) {
      this.B = packet;
   }

   public static void setModuleState(BaseModule[] state) {
      moduleState = state;
   }

   public static BaseModule[] getModuleState() {
      return moduleState;
   }

   static {
      if (getModuleState() == null) {
         setModuleState(new BaseModule[3]);
      }
   }
}
