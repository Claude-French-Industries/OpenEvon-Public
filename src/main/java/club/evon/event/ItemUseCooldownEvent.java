package club.evon.event;


public final class ItemUseCooldownEvent extends BaseEvent {
   private int cooldown;

   public int getCooldown() {
      return this.cooldown;
   }

   public void setCooldown(int coolDown) {
      this.cooldown = coolDown;
   }

   public ItemUseCooldownEvent(int coolDown) {
      this.cooldown = coolDown;
   }
}
