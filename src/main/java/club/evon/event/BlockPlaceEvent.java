package club.evon.event;
import net.minecraft.util.hit.BlockHitResult;




public final class BlockPlaceEvent {
   private final BlockHitResult K;

   public BlockHitResult getBlockHitResult() {
      return this.K;
   }

   public BlockPlaceEvent(BlockHitResult blockHitResult) {
      this.K = blockHitResult;
   }
}
