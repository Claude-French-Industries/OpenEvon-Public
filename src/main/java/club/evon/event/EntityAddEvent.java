package club.evon.event;
import net.minecraft.entity.Entity;




public final class EntityAddEvent implements Event {
   private final Entity B;

   public Entity getEntity() {
      return this.B;
   }

   public EntityAddEvent(Entity entity) {
      this.B = entity;
   }
}
