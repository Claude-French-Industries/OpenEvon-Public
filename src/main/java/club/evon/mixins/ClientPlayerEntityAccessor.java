package club.evon.mixins;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;




@Mixin(ClientPlayerEntity.class)
public interface ClientPlayerEntityAccessor {
   @Accessor("inSneakingPose")
   public void setInSneakingPose(boolean flag);

   @Accessor("lastXClient")
   public double getLastXClient();

   @Accessor("lastYClient")
   public double getLastYClient();

   @Accessor("lastZClient")
   public double getLastZClient();

   @Mutable
   @Accessor("lastXClient")
   public void setLastXClient(double doubleValue);

   @Mutable
   @Accessor("lastYClient")
   public void setLastYClient(double doubleValue);

   @Mutable
   @Accessor("lastZClient")
   public void setLastZClient(double doubleValue);

   @Mutable
   @Accessor("lastYawClient")
   public void setLastYawClient(float floatValue);

   @Mutable
   @Accessor("lastPitchClient")
   public void setLastPitchClient(float floatValue);

   @Mutable
   @Accessor("lastOnGround")
   public void setLastOnGround(boolean flag);

   @Accessor("lastYawClient")
   public float getLastYawClient();

   @Accessor("lastPitchClient")
   public float getLastPitchClient();

   @Accessor("lastOnGround")
   public boolean isLastOnGround();

   @Accessor("lastSprinting")
   public boolean getLastSprinting();

   @Accessor("ticksSinceLastPositionPacketSent")
   public int getTicksSinceLastPositionPacketSent();

   @Mutable
   @Accessor("lastSprinting")
   public void setLastSprinting(boolean flag);

   @Mutable
   @Accessor("ticksSinceLastPositionPacketSent")
   public void setTicksSinceLastPositionPacketSent(int value);

   @Invoker("sendMovementPackets")
   public void callSendMovementPackets();

   @Invoker("canStartSprinting")
   public boolean callCanStartSprinting();
}
