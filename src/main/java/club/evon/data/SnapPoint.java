package club.evon.data;
import club.evon.ui.SnapAxis;



public record SnapPoint(SnapAxis axis, float coordinate) {
   public float getCoordinate() {
      return this.coordinate;
   }
}
