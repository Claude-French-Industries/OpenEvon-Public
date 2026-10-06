package club.evon.event;
import java.lang.invoke.MethodHandles;
import java.util.UUID;
import net.minecraft.text.Text;
import org.json.JSONException;
import club.evon.data.ChatMessageType;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class ChatMessageEvent extends BaseEvent {
   private Text i;
   private ChatMessageType w;
   private UUID C;
   private static boolean initialized;
   private static final long KEY = KeyedRandom.create(4918192767441285790L, -1683407685036759468L, MethodHandles.lookup().lookupClass()).next(220360309038175L);

   public ChatMessageEvent(Text text, ChatMessageType messageType) {
      this.i = text;
      this.w = messageType;
   }

   public Text getText() {
      return this.i;
   }

   public ChatMessageType getMessageType() {
      return this.w;
   }

   public UUID getSender() {
      return this.C;
   }

   public void setText(Text text) {
      this.i = text;
   }

   public void setMessageType(ChatMessageType type) {
      this.w = type;
   }

   public void setSender(UUID sender) {
      this.C = sender;
   }

   public ChatMessageEvent(Text text, ChatMessageType type, UUID sender) {
      super();

      long longValue = KEY ^ 105054169900303L;
      boolean initialized2 = isInitialized();
      this.i = text;
      this.w = type;
      boolean flag = initialized2;
      this.C = sender;
      if (BaseModule.getStateArray() == null) {
         setInitialized(!flag);
      }
      }

   public static void setInitialized(boolean value) {
      initialized = value;
   }

   public static boolean isInitialized() {
      return initialized;
   }

   public static boolean isNotInitialized() {
      boolean initialized2 = isInitialized();
      return !initialized2;
   }

   static {
      if (!isInitialized()) {
         setInitialized(true);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
