package club.evon.util;
import java.util.Comparator;
import club.evon.util.math.PathNode;




public final class PathNodeComparator implements Comparator<PathNode> {
   public int compare(PathNode o1, PathNode o2) {
      return (int)(o1.getGoalDistanceSq() + o1.getTotalCost() - (o2.getGoalDistanceSq() + o2.getTotalCost()));
   }
}
