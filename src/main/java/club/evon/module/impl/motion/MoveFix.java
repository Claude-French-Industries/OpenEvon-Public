package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.math.MathHelper;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.MovementInputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.RotationManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.util.KeyedRandom;
import club.evon.util.math.MovementUtil;




public final class MoveFix extends BaseModule {
   private static long DIAG_LAST;
   private static final long KEY = KeyedRandom.create(4081397455535293099L, -1850893050962600968L, MethodHandles.lookup().lookupClass()).next(152921288803098L);
   private static final String[] STRINGS;

   public MoveFix() {
      super((STRINGS)[0], (STRINGS)[1], ModuleCategory.MOTION);

      String[] strings = STRINGS;
      }

   @Subscribe
   private void onMovementInput(MovementInputEvent event) {
      BaseModule[] values;
      float floatValue;
      float floatValue2;
      float floatValue3;
      long longValue = KEY ^ 67493349541178L;
      BaseModule[] values2 = Scaffold.z();
      float floatValue4 = event.getForward();
      values = values2;
      floatValue = event.getSideways();
      floatValue2 = floatValue4;
      floatValue3 = 0.0F;
      label93:
      if (values == null) {
         if (floatValue4 == 0.0F) {
            floatValue2 = floatValue;
            floatValue3 = 0.0F;
            if (values != null) {
               break label93;
            }

            if (floatValue == 0.0F) {
               return;
            }
         }

         floatValue2 = RotationManager.getMouseRotationManager().getYaw(ClientContext.MINECRAFT_CLIENT.player.getYaw());
         floatValue3 = floatValue4;
      }

      float floatValue5 = (float)Math.toDegrees(MovementUtil.getMovementYawRadians(floatValue2, floatValue3, floatValue));
      float floatValue6 = 0.0F;
      float floatValue7 = 0.0F;
      float floatValue8 = Float.MAX_VALUE;
      float floatValue9 = -1.0F;

      label85:
      while (true) {
         float floatValue10;
         int value = (floatValue10 = floatValue9 - 1.0F) == 0.0F ? 0 : (floatValue10 < 0.0F ? -1 : 1);

         label82:
         while (value <= 0) {
            if (values != null) {
               return;
            }

            float floatValue11 = -1.0F;

            while (floatValue11 <= 1.0F) {
               label76: {
                  label99: {
                     floatValue2 = floatValue11;
                     floatValue3 = 0.0F;
                     label74:
                     if (values == null) {
                        float floatValue12;
                        value = (floatValue12 = floatValue11 - 0.0F) == 0.0F ? 0 : (floatValue12 < 0.0F ? -1 : 1);
                        if (values != null) {
                           continue label82;
                        }

                        if (value == 0) {
                           floatValue2 = floatValue9;
                           floatValue3 = 0.0F;
                           if (values != null) {
                              break label74;
                           }

                           if (floatValue9 == 0.0F && values == null) {
                              break label99;
                           }
                        }

                        floatValue2 = ClientContext.MINECRAFT_CLIENT.player.getYaw();
                        floatValue3 = floatValue9;
                     }

                     float floatValue13 = (float)Math.toDegrees(MovementUtil.getMovementYawRadians(floatValue2, floatValue3, floatValue11));
                     double doubleValue = MathHelper.angleBetween(floatValue5, floatValue13);
                     if (values != null) {
                        break label76;
                     }

                     if (doubleValue < floatValue8) {
                        floatValue8 = (float)doubleValue;
                        floatValue6 = floatValue9;
                        floatValue7 = floatValue11;
                     }
                  }

                  floatValue11++;
               }

               if (values != null) {
                  break;
               }
            }

            floatValue9++;
            if (values == null) {
               continue label85;
            }
            break;
         }

         // TEMP-DIAG
         if (System.currentTimeMillis() - DIAG_LAST > 1500L) {
            DIAG_LAST = System.currentTimeMillis();
            System.out.println("[EVON-DIAG-MF] in=(" + floatValue4 + "," + floatValue + ")"
               + " playerYaw=" + ClientContext.MINECRAFT_CLIENT.player.getYaw()
               + " movementYaw=" + floatValue5
               + " out=(" + floatValue6 + "," + floatValue7 + ")"
               + " killAura=" + club.evon.EvonClient.getInstance().getModuleManager()
                    .getModuleByClass(club.evon.module.impl.combat.KillAura.class).isEnabled()
               + " scaffold=" + club.evon.EvonClient.getInstance().getModuleManager()
                    .getModuleByClass(club.evon.module.impl.motion.Scaffold.class).isEnabled());
         }

         event.setForward(floatValue6);
         event.setSideways(floatValue7);
         return;
      }
   }

   static {
      try {
      long var0 = KEY ^ 7591898526748L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[2];
      int var7 = 0;
      String var6 = "\u00b0\f\u00d8\u00b3[S\u0096\u0081\u0015_Y\u0016\u00b1\u0094U\u001d\u0018\u00ff\u0086\u00ac\u00057)\u00c1\u00f1\u00fb\u00dc\u000f\u00d8+\u0007\u00fd\u00bc&YD\u00ede\u00bc\u0001\u00bc";
      int var8 = "\u00b0\f\u00d8\u00b3[S\u0096\u0081\u0015_Y\u0016\u00b1\u0094U\u001d\u0018\u00ff\u0086\u00ac\u00057)\u00c1\u00f1\u00fb\u00dc\u000f\u00d8+\u0007\u00fd\u00bc&YD\u00ede\u00bc\u0001\u00bc"
         .length();
      char var5 = 16;
      int var4 = -1;

      label27:
      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var13 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var13;
         if ((var4 += var5) >= var8) {
            STRINGS = var9;
            break label27;
         }

         var5 = var6.charAt(var4);
      }
   
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}

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


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
