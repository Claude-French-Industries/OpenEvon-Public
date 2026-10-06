package club.evon.mixins;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.network.DisconnectionInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.EvonClient;




@Mixin(DisconnectedScreen.class)
public class DisconnectedScreenMixin {
   @Shadow
   @Final
   private DisconnectionInfo info;

   @Inject(method = "init()V", at = @At("TAIL"))
   private void checkBan(CallbackInfo callbackInfo) {
      EvonClient.getInstance().getBanManager().handleDisconnect(this.info);
   }

   @Inject(method = "init()V", at = @At("HEAD"))
   private void injectDisconnectEvent(CallbackInfo callbackInfo) {
   }
}
