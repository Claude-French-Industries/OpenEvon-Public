package club.evon.event;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import club.evon.data.EventSubscriber;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class EventDispatcher {
   private final Map<Type, CopyOnWriteArrayList<EventSubscriber>> subscribersByType = new HashMap<>();
   private static BaseModule[] modules;
   private static final long KEY = KeyedRandom.create(-7422857683936330028L, 3074906052788739506L, MethodHandles.lookup().lookupClass()).next(156229016609415L);

   public void register(Object registrar) {
      long longValue = KEY ^ 65944007266492L;

      for (Method method : registrar.getClass().getDeclaredMethods()) {
         if (method.isAnnotationPresent(Subscribe.class)) {
            Subscribe declaredAnnotation = method.getDeclaredAnnotation(Subscribe.class);
            if (!method.canAccess(registrar)) {
               method.setAccessible(true);
            }

            Class classValue = method.getParameterTypes()[0];
            EventSubscriber eventSubscriber = new EventSubscriber(registrar, method, declaredAnnotation.h());
            this.subscribersByType.computeIfAbsent(classValue, type -> new CopyOnWriteArrayList<>()).add(eventSubscriber);
         }
      }

      this.sortSubscribers();
   }

   private void sortSubscribers() {
      long longValue = KEY ^ 85662799425762L;

      for (CopyOnWriteArrayList list : this.subscribersByType.values()) {
         list.sort(Comparator.comparingInt(EventSubscriber::priority));
      }
   }

   public void unregister(Object registrar) {
      long longValue = KEY ^ 73004777450324L;

      for (CopyOnWriteArrayList<EventSubscriber> list : this.subscribersByType.values()) {
         list.removeIf(eventSubscriber -> eventSubscriber.listener().equals(registrar));
      }

      this.sortSubscribers();
   }

   public <T> void dispatch(T event) {
      long longValue = KEY ^ 109522585646289L;
      CopyOnWriteArrayList<EventSubscriber> orDefault = this.subscribersByType.getOrDefault(event.getClass(), null);
      if (orDefault != null && !orDefault.isEmpty()) {
         try {
            for (EventSubscriber eventSubscriber : orDefault) {
               eventSubscriber.method().invoke(eventSubscriber.listener(), event);
               if (event instanceof BaseEvent event2 && event2.isCanceled()) {
                  break;
               }
            }
         } catch (IllegalAccessException | InvocationTargetException illegalAccessExceptionInvocationTargetException) {
            throw new RuntimeException(illegalAccessExceptionInvocationTargetException);
         }
      }
   }

   public static void setModules(BaseModule[] values) {
      modules = values;
   }

   public static BaseModule[] getModules() {
      return modules;
   }

   static {
      if (getModules() != null) {
         setModules(new BaseModule[5]);
      }
   }

   private static Exception identity(Exception exception) {
      return exception;
   }
}
