package club.evon.mixins;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;




@Mixin(Mouse.class)
public interface MouseAccessor {
   @Accessor("leftButtonClicked")
   public void setLeftButtonClicked(boolean flag);

   @Accessor("middleButtonClicked")
   public void setMiddleButtonClicked(boolean flag);

   @Accessor("rightButtonClicked")
   public void setRightButtonClicked(boolean flag);
}
