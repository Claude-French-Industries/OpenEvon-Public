package club.evon.event;


public final class EntityRemoveEvent implements Event {
   private final int V;

   public int getEntityId() {
      return this.V;
   }

   public EntityRemoveEvent(int entityId) {
      this.V = entityId;
   }
}
