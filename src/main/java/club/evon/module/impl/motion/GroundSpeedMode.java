package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.MovementInputEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.event.UpdateVelocityEvent;
import club.evon.manager.ClientState;
import club.evon.manager.OutgoingPacketManager;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;
import club.evon.util.net.PacketBlocker;




public class GroundSpeedMode extends SubMode<Speed> {
   private double speed;
   private final PacketBlocker packetBlocker;
   private static final long KEY = KeyedRandom.create(8956310332322617761L, 1588166954598224226L, MethodHandles.lookup().lookupClass()).next(214054282198717L);

   public GroundSpeedMode(String name, Speed parent) {
      super(name, parent);

      long longValue = KEY ^ 52797356652837L;
      String tmp = SpeedVanillaMode.q();
      this.packetBlocker = new PacketBlocker(OutgoingPacketManager.getInstance());
      String text = tmp;
      if (text != null) {
         BaseModule.setStateArray(new BaseModule[2]);
      }
      }

   @Override
   public void onEnable() {
      long longValue = KEY ^ 58136238068017L;
      String text = SpeedVanillaMode.q();
      if (text == null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         this.speed = MovementUtil.getHorizontalVelocity();
         super.onEnable();
      }
   }

   @Override
   public void onDisable() {
      long longValue = KEY ^ 122846888629548L;
      String tmp = SpeedVanillaMode.q();
      this.packetBlocker.unblock();
      String text = tmp;
      ClientPlayerEntity player = ClientContext.MINECRAFT_CLIENT.player;
      if (text == null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         player = ClientContext.MINECRAFT_CLIENT.player;
      }

      player.setVelocity(ClientContext.MINECRAFT_CLIENT.player.getVelocity().multiply(0.0, 1.0, 0.0));
      super.onDisable();
   }

   @Subscribe
   private void onMovementInput(MovementInputEvent event) {
      long longValue = KEY ^ 121566024035540L;
      String text = SpeedVanillaMode.q();
      boolean moving = MovementUtil.isMoving();
      if (text == null) {
         if (!moving) {
            return;
         }

         moving = ClientContext.MINECRAFT_CLIENT.player.isOnGround();
      }

      if (moving) {
         event.setJump(true);
      }
   }

   @Subscribe
   private void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 90975152104421L;
      String tmp = SpeedVanillaMode.q();
      Packet packet = event.getPacket();
      String text = tmp;
      Packet packet2 = packet;
      if (text == null) {
         if (!(packet instanceof EntityVelocityUpdateS2CPacket)) {
            return;
         }

         packet2 = packet;
      }

      EntityVelocityUpdateS2CPacket packet3 = (EntityVelocityUpdateS2CPacket)packet2;
      if (ClientContext.MINECRAFT_CLIENT.player != null) {
         EntityVelocityUpdateS2CPacket packet4 = packet3;
         if (text == null) {
            if (packet3.getEntityId() != ClientContext.MINECRAFT_CLIENT.player.getId()) {
               return;
            }

            packet4 = packet3;
         }

         double doubleValue = Math.sqrt(Math.pow(packet4.getVelocity().getX(), 2.0) + Math.pow(packet3.getVelocity().getZ(), 2.0));
         if (doubleValue > this.speed) {
            this.speed = doubleValue;
         }
      }
   }

   @Subscribe
   private void onUpdateVelocity(UpdateVelocityEvent event) {
      long longValue = KEY ^ 77010552765331L;
      String tmp = SpeedVanillaMode.q();
      int value = ClientState.getInstance().airTicks;
      String text = tmp;
      boolean onGround = ClientContext.MINECRAFT_CLIENT.player.isOnGround();
      if (text == null) {
         if (onGround) {
            int value2 = ClientContext.MINECRAFT_CLIENT.player.hasStatusEffect(StatusEffects.SPEED)
               ? ClientContext.MINECRAFT_CLIENT.player.getStatusEffect(StatusEffects.SPEED).getAmplifier()
               : 0;
            this.speed = 0.48 + value2 * 0.05;
         }

         if (value == 1) {
            int value3 = ClientContext.MINECRAFT_CLIENT.player.hasStatusEffect(StatusEffects.SPEED)
               ? ClientContext.MINECRAFT_CLIENT.player.getStatusEffect(StatusEffects.SPEED).getAmplifier()
               : 0;
            this.speed = 0.345 + value3 * 0.05;
         }

         onGround = MovementUtil.isMoving();
      }

      label57: {
         if (onGround) {
            MovementUtil.setSpeed(this.speed);
            if (text == null) {
               break label57;
            }
         }

         ClientContext.MINECRAFT_CLIENT.player.setVelocity(ClientContext.MINECRAFT_CLIENT.player.getVelocity().multiply(0.0, 1.0, 0.0));
      }

      this.speed = this.speed - this.speed / 190.0;
   }

   private static JSONException rethrow(JSONException exception) {
      return exception;
   }
}
