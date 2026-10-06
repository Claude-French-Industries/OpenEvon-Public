package club.evon.data;


public record PlacementResult(PlacementTarget target, RotationHitResult hitResult) {
   public RotationHitResult getHitResult() {
      return this.hitResult;
   }
}
