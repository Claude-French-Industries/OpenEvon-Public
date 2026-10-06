package club.evon.data;


public record PlacementHitData(BlockFace blockFace, RotationHitResult hitResult) {
   public RotationHitResult getHitResult() {
      return this.hitResult;
   }
}
