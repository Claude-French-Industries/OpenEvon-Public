package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Direction.Axis;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.MovementInputEvent;
import club.evon.event.Subscribe;
import club.evon.event.UpdateVelocityEvent;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;




public class FlightVanillaMode extends SubMode<Flight> {
   private final DoubleSetting horizontalSpeed;
   private final DoubleSetting verticalSpeed;
   private final BooleanGroupSetting fastStopAddons;
   private static int[] state;
   private static final long KEY = KeyedRandom.create(-7429886322995266278L, 1787916858046986109L, MethodHandles.lookup().lookupClass()).next(129042387448601L);
   private static final String[] STRINGS;

   public FlightVanillaMode(String name, Flight parent) {
      super(name, parent);
      String[] strings = STRINGS;
      this.horizontalSpeed = new DoubleSetting(strings[6], 1.0, 0.0, 9.5, 0.01);
      this.verticalSpeed = new DoubleSetting(strings[0], 1.0, 0.0, 9.5, 0.01);
      this.fastStopAddons = new BooleanGroupSetting(strings[4], new BooleanSetting(strings[1], false), new BooleanSetting(strings[5], false));
      this.addSettings(this.fastStopAddons, this.horizontalSpeed, this.verticalSpeed);
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Override
   public void onDisable() {
      final int[] flowGuard = getState();
      if (flowGuard == null && this.fastStopAddons.getByName(STRINGS[2]).I()) {
         ClientPlayerEntity player = ClientContext.MINECRAFT_CLIENT.player;
         if (player != null) {
            player.setVelocity(player.getVelocity().multiply(0.0, 1.0, 0.0));
         }
      }

      super.onDisable();
      if (flowGuard != null) {
         BaseModule.setStateArray(new BaseModule[3]);
      }
   }

   @Subscribe
   private void onUpdateVelocity(UpdateVelocityEvent event) {
      long longValue = KEY ^ 62662242493932L;
      int[] values = getState();
      double doubleValue = 0.0;
      int[] values2 = values;
      boolean pressed = ClientContext.MINECRAFT_CLIENT.options.jumpKey.isPressed();
      if (values2 == null) {
         if (pressed) {
            doubleValue += this.verticalSpeed.J();
         }

         pressed = ClientContext.MINECRAFT_CLIENT.options.sneakKey.isPressed();
      }

      if (values2 == null) {
         if (pressed) {
            doubleValue -= this.verticalSpeed.J();
         }

         ClientContext.MINECRAFT_CLIENT.player.setVelocity(ClientContext.MINECRAFT_CLIENT.player.getVelocity().withAxis(Axis.Y, doubleValue));
         pressed = MovementUtil.isMoving();
      }

      if (values2 == null) {
         if (pressed) {
            MovementUtil.setSpeed(this.horizontalSpeed.J());
            if (values2 == null) {
               return;
            }
         }

         pressed = this.fastStopAddons.getByName(STRINGS[3]).I();
      }

      if (pressed) {
         ClientContext.MINECRAFT_CLIENT.player.setVelocity(ClientContext.MINECRAFT_CLIENT.player.getVelocity().multiply(0.0, 1.0, 0.0));
      }
   }

   @Subscribe
   public void onMovementInput(MovementInputEvent event) {
      event.setSneak(false);
   }

   public static void setState(int[] value) {
      state = value;
   }

   public static int[] getState() {
      return state;
   }

   static {
      try {
      long var0 = KEY ^ 120600468818104L;
      setState(null);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[7];
      int var7 = 0;
      String var6 = "p\u00bc\u009c\u00c3\u00fd\u00b5{\u00f0\u00ffv\u00a1\u00b9]o\u0003\u0093\u0010\u00b5A\u0010\u008e\u00d4GT\u0014\u00e2+hP\u00ee\u00d4\u0005M\u0010\u00b5A\u0010\u008e\u00d4GT\u0014\u00e2+hP\u00ee\u00d4\u0005M\u0010\r\u00c0G\u0087\u009ey\u0092\u00ad\u00e2\u001e\u0081\u00b5\u00bb\u00b6lj\u0018\u00b3\u00b2\u0018\\\u0007\u008b{\u00c5\u00ed\\\u0081F\u00d5\u00ed\u00cf\u00dd:\u009e\u001c\u00edyU\u00f1\u00a0";
      int var8 = "p\u00bc\u009c\u00c3\u00fd\u00b5{\u00f0\u00ffv\u00a1\u00b9]o\u0003\u0093\u0010\u00b5A\u0010\u008e\u00d4GT\u0014\u00e2+hP\u00ee\u00d4\u0005M\u0010\u00b5A\u0010\u008e\u00d4GT\u0014\u00e2+hP\u00ee\u00d4\u0005M\u0010\r\u00c0G\u0087\u009ey\u0092\u00ad\u00e2\u001e\u0081\u00b5\u00bb\u00b6lj\u0018\u00b3\u00b2\u0018\\\u0007\u008b{\u00c5\u00ed\\\u0081F\u00d5\u00ed\u00cf\u00dd:\u009e\u001c\u00edyU\u00f1\u00a0"
         .length();
      char var5 = 16;
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeUtf8(var10).intern();
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

                  var6 = "\r\u00c0G\u0087\u009ey\u0092\u00ad\u00e2\u001e\u0081\u00b5\u00bb\u00b6lj\u0018\u00b0\u00f7\u00dc#\u0019\u00a7\u0083\u00fe\u0082\u00f9|\u0084;!i\u009aM\u001a\u0098?\u0010\u00b5\u00e8\u00ab";
                  var8 = "\r\u00c0G\u0087\u009ey\u0092\u00ad\u00e2\u001e\u0081\u00b5\u00bb\u00b6lj\u0018\u00b0\u00f7\u00dc#\u0019\u00a7\u0083\u00fe\u0082\u00f9|\u0084;!i\u009aM\u001a\u0098?\u0010\u00b5\u00e8\u00ab"
                     .length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException rethrow(JSONException exception) {
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
