package club.evon.data;
import club.evon.util.math.ScoredBlockPos;



public record BlockTarget(ScoredBlockPos target, double score) {
   public ScoredBlockPos target() {
      return this.target;
   }

   public double score() {
      return this.score;
   }
}
