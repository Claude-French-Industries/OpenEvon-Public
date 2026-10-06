package club.evon.mixins;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;




@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
   @Mutable
   @Accessor("itemUseTimeLeft")
   public void setItemUseTimeLeft(int value);

   @Accessor("jumping")
   public boolean isJumping();

   @Invoker("travelMidAir")
   public void callTravelMidAir(Vec3d pos);

   @Invoker("getJumpVelocity")
   public float callGetJumpVelocity();

   @Accessor("jumpingCooldown")
   public void setJumpingCooldown(int value);
}
