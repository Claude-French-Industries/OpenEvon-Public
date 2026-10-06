package club.evon.ui;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import club.evon.data.Notification;
import club.evon.data.NotificationType;
import club.evon.manager.NotificationManager;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class NotificationBuilder {
   private NotificationType A;
   private long u;
   private String i;
   private String S;
   public final NotificationManager manager;
   private static final long KEY = KeyedRandom.create(-7869442229636266171L, -1571784210483553932L, MethodHandles.lookup().lookupClass()).next(124736450698795L);
   private static final long DEFAULT_DURATION;

   public NotificationBuilder(final NotificationManager this$0) {
      super();
      long longValue = KEY ^ 64939434372750L;
      this.manager = this$0;
      this.u = DEFAULT_DURATION;
   }

   public NotificationBuilder createBuilder() {
      return new NotificationBuilder(this.manager);
   }

   public NotificationBuilder setType(NotificationType type) {
      this.A = type;
      return this;
   }

   public NotificationBuilder setDuration(long duration) {
      this.u = duration;
      return this;
   }

   public NotificationBuilder setTitle(String title) {
      this.i = title;
      return this;
   }

   public NotificationBuilder setSubtitle(String subtitle) {
      this.S = subtitle;
      return this;
   }

   public void show() {
      long longValue = KEY ^ 111061402964983L;
      BaseModule[] values = NotificationManager.getStateArray();
      NotificationBuilder tmp = this;
      if (values != null) {
         if (this.A == null) {
            throw new IllegalStateException();
         }

         tmp = this;
      }

      String text = tmp.i;
      if (values != null) {
         if (tmp.i == null) {
            throw new IllegalStateException();
         }

         text = this.S;
      }

      if (text != null) {
         Notification notification = new Notification(this.A, this.u, this.i, this.S);
         this.manager.notifications.add(notification);
         if (values == null) {
            BaseModule.setStateArray(new BaseModule[1]);
         }
      } else {
         throw new IllegalStateException();
      }
   }

   static {
      try {
      long var0 = KEY ^ 138816968376678L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/NoPadding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long var4 = -2923565896800146995L;
      byte[] var6 = var2.doFinal(
         new byte[]{
            (byte)(var4 >>> 56),
            (byte)(var4 >>> 48),
            (byte)(var4 >>> 40),
            (byte)(var4 >>> 32),
            (byte)(var4 >>> 24),
            (byte)(var4 >>> 16),
            (byte)(var4 >>> 8),
            (byte)var4
         }
      );
      long var7 = (var6[0] & 255L) << 56
         | (var6[1] & 255L) << 48
         | (var6[2] & 255L) << 40
         | (var6[3] & 255L) << 32
         | (var6[4] & 255L) << 24
         | (var6[5] & 255L) << 16
         | (var6[6] & 255L) << 8
         | var6[7] & 255L;
      byte var10001 = -1;
      DEFAULT_DURATION = var7;
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static IllegalStateException identity(IllegalStateException exception) {
      return exception;
   }
}
