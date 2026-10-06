package club.evon.util.net;
import java.lang.invoke.MethodHandles;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class PacketBlockerEntry {
   @Nullable
   private PacketTransformer transformer;
   @Nullable
   private final PacketValidator validator;
   private final boolean l;
   private final long v;
   private final long createdAt;
   private static final long KEY = KeyedRandom.create(6636622209707461218L, 3215885943356811788L, MethodHandles.lookup().lookupClass()).next(1141235725462L);

   public PacketBlockerEntry(@Nullable PacketTransformer packetTransformer, @Nullable PacketValidator packetValidator, boolean priority, long id) {
      super();
      long longValue = KEY ^ 79264808759442L;
      String stateString = PacketQueue.getStateString();
      this.createdAt = System.currentTimeMillis();
      this.transformer = packetTransformer;
      this.validator = packetValidator;
      this.l = priority;
      this.v = id;
      if (BaseModule.getStateArray() == null) {
         PacketQueue.setStateString("USK4Lb");
      }
   }

   @Nullable
   public PacketTransformer getTransformer() {
      return this.transformer;
   }

   public void setTransformer(@Nullable PacketTransformer packetTransformer) {
      this.transformer = packetTransformer;
   }

   @Nullable
   public PacketValidator getValidator() {
      return this.validator;
   }

   public long getId() {
      return this.v;
   }

   public boolean isPriority() {
      return this.l;
   }

   @Override
   public boolean equals(Object o) {
      long longValue = KEY ^ 133720664089042L;
      String stateString = PacketQueue.getStateString();
      if (o == null) {
         return false;
      }

      Object classValue = this.getClass();
      if (stateString != null) {
         if (classValue != o.getClass()) {
            return false;
         }

         classValue = o;
      }

      PacketBlockerEntry packet = (PacketBlockerEntry)classValue;
      long longValue2;
      int value = (longValue2 = this.v - packet.v) == 0L ? 0 : (longValue2 < 0L ? -1 : 1);
      if (stateString == null) {
         return value != 0;
      }

      return value == 0;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(this.v);
   }

   public long getCreatedAt() {
      return this.createdAt;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
