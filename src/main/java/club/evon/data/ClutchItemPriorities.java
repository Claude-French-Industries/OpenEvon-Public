package club.evon.data;
import java.util.HashMap;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import club.evon.module.impl.player.LegitNoFall;




public class ClutchItemPriorities extends HashMap<Item, Integer> {
   public final LegitNoFall module;

   public ClutchItemPriorities(LegitNoFall legitNoFall) {
      this.module = legitNoFall;
      this.put(Items.WATER_BUCKET, 3);
      this.put(Items.COBWEB, 2);
      this.put(Items.HAY_BLOCK, 1);
   }
}
