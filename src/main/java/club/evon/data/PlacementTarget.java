package club.evon.data;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;




public record PlacementTarget(BlockPos pos, Direction direction) {
   public BlockPos getPos() {
      return this.pos;
   }

   public Direction getDirection() {
      return this.direction;
   }
}
