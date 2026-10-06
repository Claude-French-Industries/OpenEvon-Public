package club.evon.mixins;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;




@Mixin(ClientPlayerInteractionManager.class)
public interface ClientPlayerInteractionManagerAccessor {
   @Invoker("sendSequencedPacket")
   public void callSendSequencedPacket(ClientWorld world, SequencedPacketCreator packet);

   @Invoker("syncSelectedSlot")
   public void callSyncSelectedSlot();

   @Mutable
   @Accessor("currentBreakingProgress")
   public float getCurBlockDamageMP();

   @Mutable
   @Accessor("currentBreakingProgress")
   public void setCurBlockDamageMP(float floatValue);
}
