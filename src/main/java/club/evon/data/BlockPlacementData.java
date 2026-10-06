package club.evon.data;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;




public class BlockPlacementData {
   private final BlockPos g;
   private final Direction d;

   public BlockPos getBlockPos() {
      return this.g;
   }

   public Direction getDirection() {
      return this.d;
   }

   public BlockPlacementData(BlockPos blockPos, Direction direction) {
      this.g = blockPos;
      this.d = direction;
   }
}
