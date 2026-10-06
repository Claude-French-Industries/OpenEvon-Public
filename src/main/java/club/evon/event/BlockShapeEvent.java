package club.evon.event;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;




public final class BlockShapeEvent extends BaseEvent {
   private BlockState s;
   private BlockPos W;
   private VoxelShape t;
   private final List<VoxelShape> q;

   public BlockState getBlockState() {
      return this.s;
   }

   public BlockPos getBlockPos() {
      return this.W;
   }

   public VoxelShape getShape() {
      return this.t;
   }

   public List<VoxelShape> getExtraVoxelShapes() {
      return this.q;
   }

   public void setBlockState(BlockState blockState) {
      this.s = blockState;
   }

   public void setBlockPos(BlockPos blockPos) {
      this.W = blockPos;
   }

   public void setShape(VoxelShape shape) {
      this.t = shape;
   }

   public BlockShapeEvent(BlockState blockState, BlockPos blockPos, VoxelShape shape, List<VoxelShape> extraVoxelShapes) {
      this.s = blockState;
      this.W = blockPos;
      this.t = shape;
      this.q = extraVoxelShapes;
   }
}
