package club.evon.mixins;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.ClientContext;
import club.evon.command.IrcChatCommand;
import club.evon.event.EventBus;
import club.evon.event.PlayerVelocityEvent;
import club.evon.event.SendChatMessageEvent;
import club.evon.manager.ChatNameManager;
import club.evon.ui.ClickGuiScreen;




@Mixin(ClientPlayNetworkHandler.class)
public final class ClientPlayNetworkHandlerMixin {
   @Inject(method = "sendChatMessage(Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
   private void onSendChatMessage(String text, CallbackInfo callbackInfo) {
      String trim = text.trim();
      SendChatMessageEvent sendChatMessageEvent = new SendChatMessageEvent(trim);
      EventBus.post(sendChatMessageEvent);
      if (sendChatMessageEvent.isCanceled()) {
         ClientContext.MINECRAFT_CLIENT.inGameHud.getChatHud().addToMessageHistory(trim);
         callbackInfo.cancel();
      }

      if (IrcChatCommand.enabled && trim.startsWith("#") && trim.length() > 1) {
         String substring = trim.substring(1);
         ChatNameManager.getInstance().i(substring);
         ClientContext.MINECRAFT_CLIENT.inGameHud.getChatHud().addToMessageHistory(trim);
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = "onEntityVelocityUpdate(Lnet/minecraft/network/packet/s2c/play/EntityVelocityUpdateS2CPacket;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocityClient(Lnet/minecraft/util/math/Vec3d;)V"),
      cancellable = true
   )
   private void hookVelocityUpdate(EntityVelocityUpdateS2CPacket packet, CallbackInfo callbackInfo) {
      if (packet.getEntityId() == ClientContext.MINECRAFT_CLIENT.player.getId()) {
         PlayerVelocityEvent playerVelocityEvent = new PlayerVelocityEvent(packet.getVelocity(), false);
         EventBus.post(playerVelocityEvent);
         if (!playerVelocityEvent.isCanceled()) {
            ClientContext.MINECRAFT_CLIENT.player.setVelocityClient(playerVelocityEvent.getVelocity());
         }

         callbackInfo.cancel();
      }
   }

   @Inject(
      method = "onExplosion(Lnet/minecraft/network/packet/s2c/play/ExplosionS2CPacket;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/s2c/play/ExplosionS2CPacket;playerKnockback()Ljava/util/Optional;"),
      cancellable = true
   )
   private void hookVelocityUpdateExplosion(ExplosionS2CPacket packet, CallbackInfo callbackInfo) {
      packet.playerKnockback().ifPresent(var1x -> {
         Vec3d var2x = ClientContext.MINECRAFT_CLIENT.player.getVelocity().add(var1x);
         PlayerVelocityEvent playerVelocityEvent = new PlayerVelocityEvent(var2x, true);
         EventBus.post(playerVelocityEvent);
         if (!playerVelocityEvent.isCanceled()) {
            ClientContext.MINECRAFT_CLIENT.player.addVelocityInternal(playerVelocityEvent.getVelocity());
         }

         callbackInfo.cancel();
      });
   }

   @WrapWithCondition(
      method = "onCloseScreen(Lnet/minecraft/network/packet/s2c/play/CloseScreenS2CPacket;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;closeScreen()V")
   )
   private boolean preventScreenClose(ClientPlayerEntity player) {
      return !(ClientContext.MINECRAFT_CLIENT.currentScreen instanceof ChatScreen) && !(ClientContext.MINECRAFT_CLIENT.currentScreen instanceof ClickGuiScreen);
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
