package club.evon.event;
import net.minecraft.entity.Entity;




public final class AttackEntityEvent extends BaseEvent {
   private final Entity f;

   public Entity getEntity() {
      return this.f;
   }

   public AttackEntityEvent(Entity entity) {
      this.f = entity;
   }
}
