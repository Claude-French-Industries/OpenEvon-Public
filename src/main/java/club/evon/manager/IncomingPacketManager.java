package club.evon.manager;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.ChatSuggestionsS2CPacket;
import net.minecraft.network.packet.s2c.play.ClearTitleS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.ClientConnectionAccessor;
import club.evon.util.KeyedRandom;
import club.evon.util.net.PacketQueue;
import club.evon.util.net.PacketValidator;




public final class IncomingPacketManager extends PacketQueue<ClientPlayNetworkHandler> {
   private static final IncomingPacketManager instance = new IncomingPacketManager();
   public static final PacketValidator PACKET_VALIDATOR = var0 -> {
      long var1 = IncomingPacketManager.KEY ^ 63771632648923L;
      boolean var3 = OutgoingPacketManager.isDisabled();
      if (var3) {
         return var0 instanceof EntityStatusS2CPacket;
      }

      if (var0 instanceof EntityStatusS2CPacket) {
         EntityStatusS2CPacket var4 = (EntityStatusS2CPacket)var0;
         if (var4.getStatus() == 2) {
            return false;
         }

         if (var4.getStatus() == 3) {
            return false;
         }

         return true;
      }

      if (var0 instanceof EntityTrackerUpdateS2CPacket) {
         EntityTrackerUpdateS2CPacket var5 = (EntityTrackerUpdateS2CPacket)var0;
         ClientPlayerEntity var6 = MinecraftClient.getInstance().player;
         if (var6 != null && var5.id() != var6.getId()) {
            return false;
         }

         return true;
      }

      if (var0 instanceof EntityAnimationS2CPacket) {
         return false;
      }

      if (var0 instanceof TitleS2CPacket) {
         return false;
      }

      if (var0 instanceof TitleFadeS2CPacket) {
         return false;
      }

      if (var0 instanceof ClearTitleS2CPacket) {
         return false;
      }

      if (var0 instanceof PlaySoundS2CPacket) {
         return false;
      }

      if (var0 instanceof StopSoundS2CPacket) {
         return false;
      }

      if (var0 instanceof ChatMessageS2CPacket) {
         return false;
      }

      if (var0 instanceof ChatSuggestionsS2CPacket) {
         return false;
      }

      if (var0 instanceof EntityEquipmentUpdateS2CPacket) {
         return false;
      }

      if (var0 instanceof SubtitleS2CPacket) {
         return false;
      }

      return true;
   };
   private static final long KEY = KeyedRandom.create(-4145866483660704387L, 7958692627266828279L, MethodHandles.lookup().lookupClass()).next(110126959993066L);

   @Override
   public void dispatchPacket(ClientConnection connection, Packet<?> packet) {
      long longValue = KEY ^ 15229108853271L;
      boolean disabled = OutgoingPacketManager.isDisabled();
      ClientConnectionAccessor clientConnectionAccessor = (ClientConnectionAccessor)connection;
      boolean flag = disabled;
      clientConnectionAccessor.evon$channelReadSilent(packet);
      if (BaseModule.getStateArray() == null) {
         OutgoingPacketManager.setEnabled(!flag);
      }
   }

   public static IncomingPacketManager getInstance() {
      return instance;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
