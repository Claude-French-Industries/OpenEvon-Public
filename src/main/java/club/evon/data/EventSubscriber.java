package club.evon.data;
import java.lang.reflect.Method;




public record EventSubscriber(Object listener, Method method, int priority) {
   public EventSubscriber(Object listener, Method method, int priority) {
      this.listener = listener;
      this.method = method;
      this.priority = -priority;
   }

   public Method method() {
      return this.method;
   }
}
