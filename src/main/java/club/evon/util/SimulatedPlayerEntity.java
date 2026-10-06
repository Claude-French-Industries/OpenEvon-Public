package club.evon.util;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;




public class SimulatedPlayerEntity extends OtherClientPlayerEntity {
   public SimulatedPlayerEntity(SimulatedPlayer owner, ClientWorld world, GameProfile profile) {
      super(world, profile);
   }

   public void pushAwayFrom(Entity entity) {
   }

   public void pushAway(Entity entity) {
   }
}
