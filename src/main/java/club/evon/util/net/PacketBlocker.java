package club.evon.util.net;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class PacketBlocker {
   private final PacketQueue<?> packetQueue;
   private final boolean A;
   private PacketBlockerEntry entry;
   public static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);
   private static BaseModule[] flowGuard;
   private static final long KEY = KeyedRandom.create(2176851107777632685L, 5961512543637695058L, MethodHandles.lookup().lookupClass()).next(140480273517832L);

   public PacketBlocker(PacketQueue<?> networkBlockage, boolean priority) {
      this.packetQueue = networkBlockage;
      this.A = priority;
   }

   public PacketBlocker(PacketQueue<?> networkBlockage) {
      this(networkBlockage, false);
   }

   public void block(PacketTransformer transformer, PacketValidator validator) {
      long longValue = KEY ^ 124876317629290L;
      BaseModule[] values = getFlowGuard();
      PacketBlocker tmp = this;
      if (values == null) {
         if (this.entry != null) {
            return;
         }

         tmp = this;
      }

      tmp.entry = this.packetQueue.createEntry(transformer, validator, this.A);
   }

   public void block(PacketTransformer transformer) {
      this.block(transformer, null);
   }

   public void blockAll() {
      this.block(null);
   }

   public void setTransformer(PacketTransformer transformer) {
      long longValue = KEY ^ 57261914097789L;
      BaseModule[] values = getFlowGuard();
      PacketBlockerEntry tmp = this.entry;
      if (values == null) {
         if (this.entry == null) {
            return;
         }

         tmp = this.entry;
      }

      tmp.setTransformer(transformer);
   }

   public void unblock() {
      long longValue = KEY ^ 113613318487970L;
      BaseModule[] values = getFlowGuard();
      PacketBlocker tmp = this;
      if (values == null) {
         if (this.entry == null) {
            return;
         }

         this.packetQueue.releaseEntry(this.entry);
         tmp = this;
      }

      tmp.entry = null;
   }

   public void reblock() {
      this.unblock();
      this.blockAll();
   }

   public boolean isBlocking() {
      long longValue = KEY ^ 96593952730933L;
      return this.entry != null;
   }

   public void reblockAfter(long delayMs) {
      this.blockAll();
      SCHEDULER.schedule(this::reblock, delayMs, TimeUnit.MILLISECONDS);
   }

   public void blockFor(long delayMs) {
      this.blockAll();
      SCHEDULER.schedule(this::unblock, delayMs, TimeUnit.MILLISECONDS);
   }

   public void reblockAfter(PacketTransformer transformer, PacketValidator validator, long delayMs) {
      this.block(transformer, validator);
      SCHEDULER.schedule(this::reblock, delayMs, TimeUnit.MILLISECONDS);
   }

   public void blockFor(PacketTransformer transformer, PacketValidator validator, long delayMs) {
      this.block(transformer, validator);
      SCHEDULER.schedule(this::unblock, delayMs, TimeUnit.MILLISECONDS);
   }

   public PacketQueue<?> getQueue() {
      return this.packetQueue;
   }

   public boolean isPriority() {
      return this.A;
   }

   public PacketBlockerEntry getEntry() {
      return this.entry;
   }

   static {
      setFlowGuard(null);
   }

   public static void setFlowGuard(BaseModule[] value) {
      flowGuard = value;
   }

   public static BaseModule[] getFlowGuard() {
      return flowGuard;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
