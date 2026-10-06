package club.evon.manager;
import java.util.ArrayList;
import java.util.List;
import club.evon.data.Notification;
import club.evon.event.EventBus;
import club.evon.module.BaseModule;
import club.evon.ui.NotificationBuilder;




public final class NotificationManager {
   public final List<Notification> notifications = new ArrayList<>();
   private static BaseModule[] stateArray;

   public NotificationManager() {
      EventBus.register(this);
   }

   public NotificationBuilder createNotificationBuilder() {
      return new NotificationBuilder(this);
   }

   public static void setStateArray(BaseModule[] values) {
      stateArray = values;
   }

   public static BaseModule[] getStateArray() {
      return stateArray;
   }

   static {
      if (getStateArray() == null) {
         setStateArray(new BaseModule[2]);
      }
   }
}
