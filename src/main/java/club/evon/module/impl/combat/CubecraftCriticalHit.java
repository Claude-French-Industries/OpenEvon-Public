package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.ClientTickEvent;
import club.evon.event.OutboundPacketEvent;
import club.evon.event.Subscribe;
import club.evon.module.SubMode;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.net.NetworkUtil;




public class CubecraftCriticalHit extends SubMode<CriticalHit> {
   private LivingEntity target;
   private boolean pendingAttack;
   private boolean p;
   private static String[] stateArray;
   private static final long KEY = KeyedRandom.create(1324017374775324125L, -8484519306231416192L, MethodHandles.lookup().lookupClass()).next(59969362442214L);

   public CubecraftCriticalHit(String name, CriticalHit parent) {
      super(name, parent);
   }

   @Subscribe
   private void onOutboundPacket(OutboundPacketEvent event) {
      long longValue = KEY ^ 134157089678648L;
      String[] strings = M();
      Packet packet = event.getPacket();
      String[] strings2 = strings;
      Packet packet2 = packet;
      if (strings2 != null) {
         if (!(packet instanceof PlayerInteractEntityC2SPacket)) {
            return;
         }

         packet2 = packet;
      }

      PlayerInteractEntityC2SPacket player = (PlayerInteractEntityC2SPacket)packet2;
      if (player.type == PlayerInteractEntityC2SPacket.ATTACK) {
         Entity entityById = ClientContext.MINECRAFT_CLIENT.world.getEntityById(player.entityId);
         Entity entity = entityById;
         if (strings2 != null) {
            if (!(entityById instanceof LivingEntity)) {
               return;
            }

            entity = entityById;
         }

         LivingEntity entity2;
         entity2 = (LivingEntity)entity;
         boolean canMove = PlayerUtil.canMove();
         label47:
         if (strings2 != null) {
            if (canMove) {
               canMove = ClientContext.MINECRAFT_CLIENT.player.isOnGround();
               if (strings2 == null) {
                  break label47;
               }

               if (!canMove) {
                  double doubleValue = ClientContext.MINECRAFT_CLIENT.player.fallDistance - 0.0;
                  canMove = doubleValue != 0.0;
                  if (strings2 == null) {
                     break label47;
                  }

                  if (canMove) {
                     return;
                  }
               }
            }

            event.cancel();
            canMove = ClientContext.MINECRAFT_CLIENT.player.isSprinting();
         }

         boolean flag = canMove;
         ClientContext.MINECRAFT_CLIENT.player.setSprinting(false);
         NetworkUtil.sendPacket(new ClientCommandC2SPacket(ClientContext.MINECRAFT_CLIENT.player, Mode.STOP_SPRINTING));
         double doubleValue2 = 0.11 + Math.random() / 100.0;
         NetworkUtil.sendPacket(new PositionAndOnGround(ClientContext.MINECRAFT_CLIENT.player.getX(), ClientContext.MINECRAFT_CLIENT.player.getY() + doubleValue2, ClientContext.MINECRAFT_CLIENT.player.getZ(), false, ClientContext.MINECRAFT_CLIENT.player.horizontalCollision));
         NetworkUtil.sendPacket(
            new PositionAndOnGround(
               ClientContext.MINECRAFT_CLIENT.player.getX(), ClientContext.MINECRAFT_CLIENT.player.getY() + doubleValue2 - 0.01, ClientContext.MINECRAFT_CLIENT.player.getZ(), false, ClientContext.MINECRAFT_CLIENT.player.horizontalCollision
            )
         );
         NetworkUtil.sendManagedPacket(PlayerInteractEntityC2SPacket.attack(entity2, ClientContext.MINECRAFT_CLIENT.player.isSneaking()));
         ClientContext.MINECRAFT_CLIENT.player.setSprinting(flag);
      }
   }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 140294768205099L;
      String[] strings = M();
      if (ClientContext.MINECRAFT_CLIENT.player != null) {
         CubecraftCriticalHit tmp = this;
         if (strings != null) {
            if (!this.pendingAttack) {
               return;
            }

            tmp = this;
         }

         if (strings != null) {
            if (tmp.target != null) {
               NetworkUtil.sendManagedPacket(PlayerInteractEntityC2SPacket.attack(this.target, ClientContext.MINECRAFT_CLIENT.player.isSneaking()));
               this.target = null;
            }

            tmp = this;
         }

         tmp.pendingAttack = false;
      }
   }

   public static void setStateArray(String[] strings) {
      stateArray = strings;
   }

   public static String[] M() {
      return stateArray;
   }

   static {
      if (M() == null) {
         setStateArray(new String[2]);
      }
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }
}
