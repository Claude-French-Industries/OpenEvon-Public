package club.evon.mixins;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;




@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
   @Mutable
   @Accessor("session")
   public void setSession(Session session);

   @Mutable
   @Accessor("itemUseCooldown")
   public int getItemUseCooldown();

   @Mutable
   @Accessor("itemUseCooldown")
   public void setItemUseCooldown(int value);
}
