package club.evon.mixins;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Invoker;




@Mixin(ChestBlock.class)
public interface ChestBlockAccessor {
   @Mutable
   @Invoker("createScreenHandlerFactory")
   public NamedScreenHandlerFactory callCreateScreenHandlerFactory(BlockState blockState, World world, BlockPos pos);
}
