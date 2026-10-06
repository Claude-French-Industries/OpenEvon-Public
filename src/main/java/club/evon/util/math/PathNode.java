package club.evon.util.math;
import java.util.ArrayList;
import net.minecraft.util.math.Vec3d;




public final class PathNode {
   private Vec3d p;
   private PathNode B;
   private ArrayList<Vec3d> H;
   private double goalDistanceSq;
   private double M;
   private double w;

   public Vec3d getLoc() {
      return this.p;
   }

   public PathNode getParent() {
      return this.B;
   }

   public ArrayList<Vec3d> getPath() {
      return this.H;
   }

   public double getGoalDistanceSq() {
      return this.goalDistanceSq;
   }

   public double getCost() {
      return this.M;
   }

   public double getTotalCost() {
      return this.w;
   }

   public void setLoc(Vec3d loc) {
      this.p = loc;
   }

   public void setParent(PathNode parent) {
      this.B = parent;
   }

   public void setPath(ArrayList<Vec3d> path) {
      this.H = path;
   }

   public void setGoalDistanceSq(double squareDistanceToFromTarget) {
      this.goalDistanceSq = squareDistanceToFromTarget;
   }

   public void setCost(double cost) {
      this.M = cost;
   }

   public void setTotalCost(double totalCost) {
      this.w = totalCost;
   }

   public PathNode(Vec3d loc, PathNode parent, ArrayList<Vec3d> path, double squareDistanceToFromTarget, double cost, double totalCost) {
      this.p = loc;
      this.B = parent;
      this.H = path;
      this.goalDistanceSq = squareDistanceToFromTarget;
      this.M = cost;
      this.w = totalCost;
   }
}
