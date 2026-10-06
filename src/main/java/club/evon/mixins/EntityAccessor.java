package club.evon.mixins;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;




@Mixin(Entity.class)
public interface EntityAccessor {
   @Accessor("pos")
   public void setPos(Vec3d pos);

   @Invoker("calculateDefaultBoundingBox")
   public Box callCalculateDefaultBoundingBox(Vec3d pos);
}
