package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Direction.Axis;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.api.CubecraftServer;
import club.evon.event.OutboundPacketEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.mixins.PlayerMoveC2SPacketAccessor;
import club.evon.module.SubMode;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;




public final class CubecraftNoFall extends SubMode<NoFall> {
   private static final long KEY = KeyedRandom.create(-6611090934616998022L, 8819548222816653015L, MethodHandles.lookup().lookupClass()).next(217612384712094L);

   public CubecraftNoFall(String name, NoFall parent) {
      super(name, parent);
   }

   @Subscribe
   private void onOutboundPacket(OutboundPacketEvent event) {
      long longValue = KEY ^ 65775088952655L;
      int value = LegitNoFall.getState();
      Packet packet = event.getPacket();
      if (ClientContext.MINECRAFT_CLIENT.player != null) {
         boolean flag = ClientState.getInstance().getServerDetector().getServerProfile() instanceof CubecraftServer;
         if (value == 0) {
            if (!flag) {
               return;
            }

            flag = ClientContext.MINECRAFT_CLIENT.player.getAbilities().flying;
         }

         if (value == 0) {
            if (flag) {
               return;
            }

            flag = ClientContext.MINECRAFT_CLIENT.player.getAbilities().allowFlying;
         }

         Packet packet2;
         label80: {
            if (value == 0) {
               if (flag) {
                  return;
               }

               packet2 = packet;
               if (value != 0) {
                  break label80;
               }

               flag = packet instanceof PlayerMoveC2SPacket;
            }

            if (!flag) {
               return;
            }

            packet2 = packet;
         }

         PlayerMoveC2SPacketAccessor player;
         double doubleValue;
         label82: {
            PlayerMoveC2SPacket player2 = (PlayerMoveC2SPacket)packet2;
            player = (PlayerMoveC2SPacketAccessor)player2;
            double doubleValue2;
            int value2 = (doubleValue2 = this.getParent().getFallDistance() - MovementUtil.getJumpHeight()) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);
            if (value == 0) {
               if (value2 <= 0) {
                  return;
               }

               doubleValue = 8.0;
               if (value != 0) {
                  break label82;
               }

               if (!MovementUtil.hasGroundBelow(8.0)) {
                  return;
               }
            } else if (value2 == 0) {
               return;
            }

            doubleValue = player2.getY(ClientContext.MINECRAFT_CLIENT.player.getY());
         }

         double doubleValue3 = doubleValue;
         doubleValue3 -= doubleValue3 % 0.015625;
         player.setY(doubleValue3);
         player.setOnGround(true);
         ClientContext.MINECRAFT_CLIENT.player.setPosition(ClientContext.MINECRAFT_CLIENT.player.lastX, ClientContext.MINECRAFT_CLIENT.player.lastY, ClientContext.MINECRAFT_CLIENT.player.lastZ);
         ClientContext.MINECRAFT_CLIENT.player.setVelocity(ClientContext.MINECRAFT_CLIENT.player.getVelocity().withAxis(Axis.Y, ClientContext.MINECRAFT_CLIENT.player.getVelocity().getY() + 0.1));
         this.getParent().updateFallDistance();
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
