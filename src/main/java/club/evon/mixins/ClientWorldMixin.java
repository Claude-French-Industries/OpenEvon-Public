package club.evon.mixins;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.event.EntityAddEvent;
import club.evon.event.EntityRemoveEvent;
import club.evon.event.EventBus;




@Mixin(ClientWorld.class)
public final class ClientWorldMixin {
   @Inject(method = "addEntity(Lnet/minecraft/entity/Entity;)V", at = @At("RETURN"))
   public void injectAddEntity(Entity entity, CallbackInfo ci) {
      EntityAddEvent entityAddEvent = new EntityAddEvent(entity);
      EventBus.post(entityAddEvent);
   }

   @Inject(method = "removeEntity(ILnet/minecraft/entity/Entity$RemovalReason;)V", at = @At("RETURN"))
   public void injectRemoveEntity(int entityId, RemovalReason removalReason, CallbackInfo callbackInfo) {
      EntityRemoveEvent entityRemoveEvent = new EntityRemoveEvent(entityId);
      EventBus.post(entityRemoveEvent);
   }
}
