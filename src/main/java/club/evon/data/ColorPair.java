package club.evon.data;
import java.awt.Color;
import club.evon.module.BaseModule;




public class ColorPair {
   private final Color Q;
   private final Color O;
   private static BaseModule[] m;

   public ColorPair(Color colorOne, Color colorTwo) {
      this.Q = colorOne;
      this.O = colorTwo;
   }

   public Color getColorOne() {
      return this.Q;
   }

   public Color getColorTwo() {
      return this.O;
   }

   public static void z(BaseModule[] values) {
      m = values;
   }

   public static BaseModule[] j() {
      return m;
   }

   static {
      if (j() == null) {
         z(new BaseModule[1]);
      }
   }
}
