package club.evon.module.impl.combat;
import com.google.common.base.Predicates;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.ClientTickEvent;
import club.evon.event.MovementInputEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.mixins.LivingEntityAccessor;
import club.evon.module.SubMode;
import club.evon.util.KeyedRandom;
import club.evon.util.RaycastUtil;




public final class VelocityMinemenMode extends SubMode<Velocity> {
   private boolean pendingJump;
   private static final long KEY = KeyedRandom.create(-4317228708378280409L, -7151826356588862856L, MethodHandles.lookup().lookupClass()).next(69285054045007L);

   public VelocityMinemenMode(String name, Velocity parent) {
      super(name, parent);
   }

   @Subscribe
   private void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 134463213334410L;
      String stateString = VelocityHypixelMode.getStateString();
      Packet packet = event.getPacket();
      String text = stateString;
      Packet packet2 = packet;
      if (text == null) {
         if (!(packet instanceof EntityVelocityUpdateS2CPacket)) {
            return;
         }

         packet2 = packet;
      }

      EntityVelocityUpdateS2CPacket packet3 = (EntityVelocityUpdateS2CPacket)packet2;
      boolean shouldCancel = this.getParent().shouldCancel();
      if (text == null) {
         if (shouldCancel) {
            return;
         }

         int value = packet3.getEntityId();
         if (value != ClientContext.MINECRAFT_CLIENT.player.getId()) {
            return;
         }

         shouldCancel = ClientContext.MINECRAFT_CLIENT.player.isSprinting();
      }

      if (shouldCancel) {
         ClientContext.MINECRAFT_CLIENT.player.setSprinting(false);
      }

      this.pendingJump = true;
   }

   @Subscribe
   private void onMovementInput(MovementInputEvent event) {
      long longValue = KEY ^ 138628048815638L;
      String stateString = VelocityHypixelMode.getStateString();
      VelocityMinemenMode tmp = this;
      if (stateString == null) {
         if (!this.pendingJump) {
            return;
         }

         ((LivingEntityAccessor)ClientContext.MINECRAFT_CLIENT.player).setJumpingCooldown(0);
         event.setJump(true);
         tmp = this;
      }

      tmp.pendingJump = false;
   }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 73129731332087L;
      String stateString = VelocityHypixelMode.getStateString();
      ClientPlayerEntity tmp = ClientContext.MINECRAFT_CLIENT.player;
      if (stateString == null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.player;
      }

      if (stateString == null) {
         if (tmp.hurtTime <= 0) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.player;
      }

      EntityHitResult raycastEntityFromPlayer = RaycastUtil.raycastEntityFromPlayer(
         tmp.getEntityInteractionRange(),
         ClientContext.MINECRAFT_CLIENT.getRenderTickCounter().getTickProgress(false),
         ClientContext.MINECRAFT_CLIENT.player.getYaw(),
         ClientContext.MINECRAFT_CLIENT.player.getPitch(),
         Predicates.alwaysTrue()
      );
      if (stateString == null) {
         if (!(raycastEntityFromPlayer instanceof EntityHitResult)) {
            return;
         }

         ClientContext.MINECRAFT_CLIENT.player.setVelocity(ClientContext.MINECRAFT_CLIENT.player.getVelocity().multiply(0.6, 1.0, 0.6));
      }

      ClientContext.MINECRAFT_CLIENT.player.setSprinting(false);
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }
}
