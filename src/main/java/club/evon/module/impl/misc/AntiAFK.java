package club.evon.module.impl.misc;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.MovementInputEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.util.KeyedRandom;
import club.evon.util.Stopwatch;
import club.evon.util.math.MovementUtil;




public final class AntiAFK extends BaseModule {
   private final Stopwatch timer;
   private static final long KEY = KeyedRandom.create(5008186506838167349L, 6360892765056641484L, MethodHandles.lookup().lookupClass()).next(263996640505442L);
   private static final String[] STRINGS_TABLE;
   private static final long AFK_INTERVAL;

   public AntiAFK() {
      super((STRINGS_TABLE)[1], (STRINGS_TABLE)[0], ModuleCategory.MISC);

      String[] strings = STRINGS_TABLE;
      this.timer = new Stopwatch();
      }

   @Subscribe
   private void onMovementInput(MovementInputEvent event) {
      long longValue = KEY ^ 130824996575223L;
      int value = Streamer.getStateFlag();
      if (value != 0) {
         label43:
         if (!MovementUtil.isMoving()) {
            boolean jump = event.isJump();
            if (value != 0) {
               if (jump) {
                  break label43;
               }

               jump = ClientContext.MINECRAFT_CLIENT.player.isOnGround();
            }

            if (value != 0) {
               if (!jump) {
                  return;
               }

               jump = this.timer.hasElapsed(AFK_INTERVAL, true);
            }

            if (jump) {
               event.setJump(true);
            }

            return;
         }

         this.timer.reset();
      }
   }

   static {
      try {
      long var5 = KEY ^ 88749486526605L;
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[2];
      int var12 = 0;
      String var11 = "\u0018y\u00cd~w\u0094\u000f\u0010\u00b6\u00c3\u00f4\u0081\u00f2\u00ff\u00ca\u00ee\u00c7^Qw\u00b6\u001d0Yc\u0090\u001e\u0019\u00d5Q\b\u009d\u0010\u00ec\u0081A6\u00d9wj\b\\i\u00af\u0098\u00c4\u00d4?-";
      int var13 = "\u0018y\u00cd~w\u0094\u000f\u0010\u00b6\u00c3\u00f4\u0081\u00f2\u00ff\u00ca\u00ee\u00c7^Qw\u00b6\u001d0Yc\u0090\u001e\u0019\u00d5Q\b\u009d\u0010\u00ec\u0081A6\u00d9wj\b\\i\u00af\u0098\u00c4\u00d4?-"
         .length();
      char var10 = ' ';
      int var9 = -1;

      label28:
      while (true) {
         byte[] var15 = var7.doFinal(var11.substring(++var9, var9 + var10).getBytes("ISO-8859-1"));
         String var20 = decodeUtf8(var15).intern();
         byte var10001 = -1;
         var14[var12++] = var20;
         if ((var9 += var10) >= var13) {
            STRINGS_TABLE = var14;
            Cipher var0;
            var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
            var10002 = SecretKeyFactory.getInstance("DES");
            var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

            for (int var1 = 1; var1 < 8; var1++) {
               var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
            }

            var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
            long var2 = -3665408149401362096L;
            byte[] var4 = var0.doFinal(
               new byte[]{
                  (byte)(var2 >>> 56),
                  (byte)(var2 >>> 48),
                  (byte)(var2 >>> 40),
                  (byte)(var2 >>> 32),
                  (byte)(var2 >>> 24),
                  (byte)(var2 >>> 16),
                  (byte)(var2 >>> 8),
                  (byte)var2
               }
            );
            long var23 = (var4[0] & 255L) << 56
               | (var4[1] & 255L) << 48
               | (var4[2] & 255L) << 40
               | (var4[3] & 255L) << 32
               | (var4[4] & 255L) << 24
               | (var4[5] & 255L) << 16
               | (var4[6] & 255L) << 8
               | var4[7] & 255L;
            var10001 = -1;
            AFK_INTERVAL = var23;
            break label28;
         }

         var10 = var11.charAt(var9);
      }
      } catch (Exception var10004) {
         throw sneakyThrow(var10004);
      }
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }

   private static String decodeUtf8(byte[] bytes) {
      int index = 0;
      int value;
      char[] chars = new char[value = bytes.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & bytes[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = bytes[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
