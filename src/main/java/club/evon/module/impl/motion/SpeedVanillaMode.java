package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.SpeedMode;
import club.evon.event.JumpEvent;
import club.evon.event.Subscribe;
import club.evon.event.UpdateVelocityEvent;
import club.evon.manager.ClientState;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;




public class SpeedVanillaMode extends SubMode<Speed> {
   private final DoubleSetting jumpMotion;
   private final DoubleSetting airTick;
   private final DoubleSetting fastFall;
   private final DoubleSetting speed;
   private final BooleanGroupSetting modifications;
   private final BooleanGroupSetting fastStopAddons;
   private final BooleanSetting keepY;
   private final EnumSetting<SpeedMode> speedMode;
   private static String t;
   private static final long STRING_KEY = KeyedRandom.create(8936311814699350348L, -6702605063317353278L, MethodHandles.lookup().lookupClass()).next(247519395502672L);
   private static final String[] STRINGS;

   public SpeedVanillaMode(String name, Speed parent) {
      super(name, parent);
      String[] strings = STRINGS;
      this.jumpMotion = new DoubleSetting(strings[0], 0.42, 0.05, 1.0, 0.01);
      this.airTick = new DoubleSetting(strings[13], 5.0, 1.0, 10.0, 1.0);
      this.fastFall = new DoubleSetting(strings[12], 0.1, 0.01, 1.0, 0.01);
      this.speed = new DoubleSetting(strings[1], 1.0, 0.1, 2.0, 0.1);
      this.modifications = new BooleanGroupSetting(strings[6], new BooleanSetting(strings[2], false), new BooleanSetting(strings[10], false));
      this.fastStopAddons = new BooleanGroupSetting(strings[4], new BooleanSetting(strings[9], false), new BooleanSetting(strings[7], false));
      this.keepY = new BooleanSetting(strings[8], false);
      this.speedMode = new EnumSetting<>(strings[5], SpeedMode.JUMP);
      this.keepY.setVisible(() -> this.speedMode.getValue().equals(SpeedMode.GROUND));
      this.jumpMotion.setVisible(this.modifications.getByName(strings[2])::I);
      this.fastFall.setVisible(this.modifications.getByName(strings[10])::I);
      this.airTick.setVisible(this.modifications.getByName(strings[10])::I);
      this.addSettings(this.speedMode, this.fastStopAddons, this.speed, this.keepY, this.modifications, this.jumpMotion, this.fastFall, this.airTick);
   }

   @Override
   public void onDisable() {
      final String flowGuard = q();
      if (flowGuard == null && this.fastStopAddons.getByName(STRINGS[11]).I()) {
         ClientPlayerEntity player = ClientContext.MINECRAFT_CLIENT.player;
         if (player != null) {
            player.setVelocity(player.getVelocity().multiply(0.0, 1.0, 0.0));
         }
      }

      super.onDisable();
      if (BaseModule.getStateArray() == null) {
         p("iTRr9b");
      }
   }

   @Subscribe
   private void onJump(JumpEvent event) {
      long longValue = STRING_KEY ^ 77233522525522L;
      String text = q();
      boolean tmp = this.keepY.I();
      if (text == null) {
         if (tmp) {
            event.cancel();
         }

         tmp = this.modifications.getByName(STRINGS[2]).I();
      }

      if (tmp) {
         event.cancel();
      }
   }

   @Subscribe
   private void onUpdateVelocity(UpdateVelocityEvent event) {
      String text;
      boolean tmp;
      long longValue = STRING_KEY ^ 15555508979919L;
      text = q();
      tmp = ClientContext.MINECRAFT_CLIENT.player.isOnGround();
      label117:
      if (text == null) {
         label115:
         if (tmp) {
            label121: {
               tmp = this.speedMode.getValue().equals(SpeedMode.GROUND);
               label112:
               if (text == null) {
                  if (!tmp) {
                     tmp = MovementUtil.isMoving();
                     if (text != null) {
                        break label112;
                     }

                     if (tmp) {
                        break label121;
                     }
                  }

                  tmp = this.keepY.I();
               }

               if (text != null) {
                  break label117;
               }

               if (tmp) {
                  break label115;
               }

               tmp = ClientContext.MINECRAFT_CLIENT.options.jumpKey.isPressed();
               if (text != null) {
                  break label117;
               }

               if (!tmp) {
                  break label115;
               }
            }

            double doubleValue;
            Vec3d tmp2;
            Axis tmp3;
            ClientPlayerEntity player;
            label97: {
               player = ClientContext.MINECRAFT_CLIENT.player;
               tmp2 = ClientContext.MINECRAFT_CLIENT.player.getVelocity();
               tmp3 = Axis.Y;
               Object tmp4 = this.modifications.getByName(STRINGS[2]).I();
               if (text == null) {
                  if (!(Boolean)tmp4) {
                     doubleValue = 0.42;
                     break label97;
                  }

                  tmp4 = this.jumpMotion.J();
               }

               doubleValue = (Double)tmp4;
            }

            player.setVelocity(tmp2.withAxis(tmp3, doubleValue));
         }

         tmp = MovementUtil.isMoving();
      }

      if (text == null) {
         if (tmp) {
            double doubleValue2 = 0.0;
            label83: {
               Boolean flag = this.modifications.getByName(STRINGS[10]).I();
               if (text == null) {
                  if (flag.booleanValue()) {
                     doubleValue2 = ClientState.getInstance().airTicks;
                     if (text != null) {
                        break label83;
                     }

                     if (doubleValue2 == (Double)this.airTick.J()) {
                        ClientContext.MINECRAFT_CLIENT.player.setVelocity(ClientContext.MINECRAFT_CLIENT.player.getVelocity().subtract(0.0, (Double)this.fastFall.J(), 0.0));
                     }
                  }

                  doubleValue2 = this.speed.J();
               }
            }

            MovementUtil.setSpeed(doubleValue2);
            if (text == null) {
               return;
            }
         }

         tmp = this.fastStopAddons.getByName(STRINGS[3]).I();
      }

      if (tmp) {
         ClientContext.MINECRAFT_CLIENT.player.setVelocity(ClientContext.MINECRAFT_CLIENT.player.getVelocity().multiply(0.0, 1.0, 0.0));
      }
   }

   public static void p(String text) {
      t = text;
   }

   public static String q() {
      return t;
   }

   static {
      try {
      long var0 = STRING_KEY ^ 117525232477353L;
      p(null);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[14];
      int var7 = 0;
      String var6 = "\u00dd\t\u0015\u00e3\u00a4\u00f9\u00c8Qt\u00b3\u0084\u00d3\u001e{\u00cb'\bB7@\b\u0094\u0017S\u00ea\u0010\u00dd\t\u0015\u00e3\u00a4\u00f9\u00c8Qt\u00b3\u0084\u00d3\u001e{\u00cb'\u0010w\u00dd\r1\u00fa\u00e7?\u00ea/\u00bc%\u00e7>kz\u00ae\u0018\u00bc\\\u00ebe\u00b128\u00a5n\u009e\u00e1\u009c\u00a7\u00d3\u009a\u00af.I\u008a{S\u00fa\u00e7\u00e6\u00109\u008c\r/_\u00b3\u00a7IuH\u0010\u00fd~~T\u0019\u0010\u009f\u0004\u00e6\u00ee/\u00a1\u00d7*\\\u0019\u0004t4\u0081\u0090b\u0010w\u00dd\r1\u00fa\u00e7?\u00ea/\u00bc%\u00e7>kz\u00ae\bi\u001fS\u001f\u00a7 \u0012\u00a7\u0010\u00b5k\u0006\u00d0\u008e\u00b1\u00dcu\u0095\u00c7UL\u0080*e\u008e\u0010\u0097\u0099\u009eK\u0017\u00d4\u00f5\u0093\u000e1\u00dejx^\u00a3\u00ae\u0010\u00b5k\u0006\u00d0\u008e\u00b1\u00dcu\u0095\u00c7UL\u0080*e\u008e";
      int var8 = "\u00dd\t\u0015\u00e3\u00a4\u00f9\u00c8Qt\u00b3\u0084\u00d3\u001e{\u00cb'\bB7@\b\u0094\u0017S\u00ea\u0010\u00dd\t\u0015\u00e3\u00a4\u00f9\u00c8Qt\u00b3\u0084\u00d3\u001e{\u00cb'\u0010w\u00dd\r1\u00fa\u00e7?\u00ea/\u00bc%\u00e7>kz\u00ae\u0018\u00bc\\\u00ebe\u00b128\u00a5n\u009e\u00e1\u009c\u00a7\u00d3\u009a\u00af.I\u008a{S\u00fa\u00e7\u00e6\u00109\u008c\r/_\u00b3\u00a7IuH\u0010\u00fd~~T\u0019\u0010\u009f\u0004\u00e6\u00ee/\u00a1\u00d7*\\\u0019\u0004t4\u0081\u0090b\u0010w\u00dd\r1\u00fa\u00e7?\u00ea/\u00bc%\u00e7>kz\u00ae\bi\u001fS\u001f\u00a7 \u0012\u00a7\u0010\u00b5k\u0006\u00d0\u008e\u00b1\u00dcu\u0095\u00c7UL\u0080*e\u008e\u0010\u0097\u0099\u009eK\u0017\u00d4\u00f5\u0093\u000e1\u00dejx^\u00a3\u00ae\u0010\u00b5k\u0006\u00d0\u008e\u00b1\u00dcu\u0095\u00c7UL\u0080*e\u008e"
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

                  var6 = "\u0097\u0099\u009eK\u0017\u00d4\u00f5\u0093\u000e1\u00dejx^\u00a3\u00ae\u0010\u0017\u00f3#*\u00d5\u00e0B9p\u009f\u00daO5\u00e5\u00de7";
                  var8 = "\u0097\u0099\u009eK\u0017\u00d4\u00f5\u0093\u000e1\u00dejx^\u00a3\u00ae\u0010\u0017\u00f3#*\u00d5\u00e0B9p\u009f\u00daO5\u00e5\u00de7"
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

   private static JSONException a(JSONException jSONException) {
      return jSONException;
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
