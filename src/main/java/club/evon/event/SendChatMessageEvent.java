package club.evon.event;


public final class SendChatMessageEvent extends BaseEvent {
   private final String N;

   public String getMessage() {
      return this.N;
   }

   public SendChatMessageEvent(String message) {
      this.N = message;
   }
}
