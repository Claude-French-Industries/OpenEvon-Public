package club.evon.util.net;
import java.lang.invoke.MethodHandles;
import org.java_websocket.WebSocket;
import org.json.JSONException;
import club.evon.api.WebSocketMessage;
import club.evon.util.KeyedRandom;




public final class WebSocketUtil {
   private static final long KEY = KeyedRandom.create(-8756809087606976306L, 3066424248605920817L, MethodHandles.lookup().lookupClass()).next(167396481444631L);

   private WebSocketUtil() {
   }

   public static void sendMessage(String message, WebSocket socket) {
      long longValue = KEY ^ 38929674377739L;
      boolean enabled = WebSocketMessage.isEnabled();
      WebSocket tmp = socket;
      if (!enabled) {
         if (socket.isClosed()) {
            return;
         }

         tmp = socket;
      }

      tmp.send(message);
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
