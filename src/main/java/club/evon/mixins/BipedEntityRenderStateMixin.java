package club.evon.mixins;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import club.evon.util.BipedEntityRenderStateAccessor;




@Mixin(BipedEntityRenderState.class)
public final class BipedEntityRenderStateMixin implements BipedEntityRenderStateAccessor {
   @Unique
   public LivingEntity entity;

   @Override
   public LivingEntity evon$getEntity() {
      return this.entity;
   }

   @Override
   public void evon$setEntity(LivingEntity entity) {
      this.entity = entity;
   }
}
