package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.ClientPlayerEntity;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.MotionEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.ModeSetting;
import club.evon.util.KeyedRandom;




public final class NoFall extends BaseModule {
   private final ModeSetting mode;
   private double lastFallDistance;
   private static final long KEY = KeyedRandom.create(-4336028479516388178L, -6732806901898002747L, MethodHandles.lookup().lookupClass()).next(82363207050736L);
   private static final String[] STRINGS;

   public NoFall() {
      super((STRINGS)[5], (STRINGS)[2], ModuleCategory.PLAYER);

      String[] strings = STRINGS;
      this.mode = new ModeSetting(strings[4], new NoFallVanillaMode(strings[0], this), new LegitNoFall(strings[1], this), new CubecraftNoFall(strings[3], this));
      this.addSettings(this.mode);
      }

   @Subscribe
   public void onMotion(MotionEvent event) {
      long longValue = KEY ^ 38708097416919L;
      if (ClientContext.MINECRAFT_CLIENT.player.fallDistance == 0.0) {
         this.updateFallDistance();
      }
   }

   public void updateFallDistance() {
      this.lastFallDistance = ClientContext.MINECRAFT_CLIENT.player.fallDistance;
   }

   public double getFallDistance() {
      long longValue = KEY ^ 38224672499168L;
      boolean flag = Breaker.a();
      ClientPlayerEntity tmp = ClientContext.MINECRAFT_CLIENT.player;
      if (flag) {
         if (ClientContext.MINECRAFT_CLIENT.player.getAbilities().allowFlying) {
            return 0.0;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.player;
      }

      return tmp.fallDistance - this.lastFallDistance;
   }

   @Override
   public void onEnable() {
      this.lastFallDistance = 0.0;
      super.onEnable();
   }

   @Override
   public String getSuffix() {
      return this.mode.getValue().getName();
   }

   static {
      try {
      long var0 = KEY ^ 58654812707456L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[6];
      int var7 = 0;
      String var6 = "\u00b3]\u00ec\u0094g\u00a7\u007f1\bSd\u000b5FY\u00c3\u00fc\u0018}u\u00cd\u00b6485\u00a9\u00f6\u008c\u00dc\u00e5\u0092\u00fc\u00f6\u00d9%q\u00f2\u0014\u00f7\u00a0\u00970\u0010\u00ef\u001e<A\u0018\u00e4\u00d1x\u0019\u00a7\u00e1\u001aV\u00140\u00a8";
      int var8 = "\u00b3]\u00ec\u0094g\u00a7\u007f1\bSd\u000b5FY\u00c3\u00fc\u0018}u\u00cd\u00b6485\u00a9\u00f6\u008c\u00dc\u00e5\u0092\u00fc\u00f6\u00d9%q\u00f2\u0014\u00f7\u00a0\u00970\u0010\u00ef\u001e<A\u0018\u00e4\u00d1x\u0019\u00a7\u00e1\u001aV\u00140\u00a8"
         .length();
      char var5 = '\b';
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeString(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var19;
                  if ((var12 += var5) >= var8) {
                     STRINGS = var9;
                     break label27;
                  }

                  var5 = var6.charAt(var12);
                  break;
               default:
                  var9[var7++] = var19;
                  if ((var12 += var5) < var8) {
                     var5 = var6.charAt(var12);
                     continue label27;
                  }

                  var6 = "\u00d7\u00a3\u000b\u0015C\u00e3\u001dc\b\u00dd]+\u0090\u00b2\u000bN\u00f3";
                  var8 = "\u00d7\u00a3\u000b\u0015C\u00e3\u001dc\b\u00dd]+\u0090\u00b2\u000bN\u00f3".length();
                  var5 = '\b';
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
   
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}

   private static JSONException identity(JSONException exception) {
      return exception;
   }

   private static String decodeString(byte[] bytes) {
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


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
