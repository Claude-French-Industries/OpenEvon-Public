package club.evon.mixins;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;




@Mixin(PlayerMoveC2SPacket.class)
public interface PlayerMoveC2SPacketAccessor {
   @Mutable
   @Accessor("x")
   public void setX(double doubleValue);

   @Mutable
   @Accessor("y")
   public void setY(double doubleValue);

   @Mutable
   @Accessor("z")
   public void setZ(double doubleValue);

   @Mutable
   @Accessor("onGround")
   public void setOnGround(boolean flag);

   @Mutable
   @Accessor("yaw")
   public void setYaw(float floatValue);

   @Mutable
   @Accessor("pitch")
   public void setPitch(float floatValue);

   @Mutable
   @Accessor("changePosition")
   public void setChangePosition(boolean flag);

   @Mutable
   @Accessor("changeLook")
   public void setChangeLook(boolean flag);
}
