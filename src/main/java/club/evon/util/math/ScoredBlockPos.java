package club.evon.util.math;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import club.evon.util.PlayerUtil;




public class ScoredBlockPos {
   private final BlockPos pos;
   public double distance;

   public ScoredBlockPos(BlockPos pos2) {
      this.pos = pos2;
      this.updateDistance();
   }

   public ScoredBlockPos offset(Direction direction) {
      return new ScoredBlockPos(this.pos.offset(direction));
   }

   public void updateDistance() {
      this.distance = PlayerUtil.getDistanceToBlock(this.pos);
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
