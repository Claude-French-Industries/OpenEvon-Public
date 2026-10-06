package club.evon.mixins;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import club.evon.util.EntityRenderStateAccessor;




@Mixin(EntityRenderState.class)
public final class EntityRenderStateMixin implements EntityRenderStateAccessor {
   @Unique
   private Entity entity;

   private EntityRenderStateMixin() {
   }

   @Override
   public Entity evon$getEntity() {
      return this.entity;
   }

   @Override
   public void evon$setEntity(Entity entity) {
      this.entity = entity;
   }
}
