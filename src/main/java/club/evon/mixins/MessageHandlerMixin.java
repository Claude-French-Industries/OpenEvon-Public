package club.evon.mixins;
import java.util.UUID;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import club.evon.data.ChatMessageType;
import club.evon.event.ChatMessageEvent;
import club.evon.event.EventBus;




@Mixin(MessageHandler.class)
public abstract class MessageHandlerMixin {
   @Shadow
   public abstract UUID extractSender(Text text);

   @ModifyArg(
      method = "method_45745(Lnet/minecraft/network/message/MessageType$Parameters;Lnet/minecraft/text/Text;Ljava/time/Instant;)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;)V")
   )
   private Text modifyDisguisedChatLambda(Text text) {
      ChatMessageEvent chatMessageEvent = new ChatMessageEvent(text, ChatMessageType.CHAT_MESSAGE);
      EventBus.post(chatMessageEvent);
      return chatMessageEvent.getText();
   }

   @ModifyArg(
      method = "processChatMessageInternal(Lnet/minecraft/network/message/MessageType$Parameters;Lnet/minecraft/network/message/SignedMessage;Lnet/minecraft/text/Text;Lcom/mojang/authlib/GameProfile;ZLjava/time/Instant;)Z",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"
      )
   )
   private Text modifyChatMessage(Text text) {
      ChatMessageEvent chatMessageEvent = new ChatMessageEvent(text, ChatMessageType.CHAT_MESSAGE);
      EventBus.post(chatMessageEvent);
      return chatMessageEvent.getText();
   }

   @ModifyArg(
      method = "onGameMessage(Lnet/minecraft/text/Text;Z)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;)V")
   )
   private Text modifyGameMessage(Text text) {
      ChatMessageEvent chatMessageEvent = new ChatMessageEvent(text, ChatMessageType.GAME_MESSAGE, this.extractSender(text));
      EventBus.post(chatMessageEvent);
      return chatMessageEvent.getText();
   }
}
