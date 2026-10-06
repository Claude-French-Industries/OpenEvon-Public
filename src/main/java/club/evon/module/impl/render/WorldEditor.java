package club.evon.module.impl.render;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.BrightnessMode;
import club.evon.data.TimeMode;
import club.evon.event.ClientTickEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.runtime.IndyRuntime;
import club.evon.setting.EnumSetting;
import club.evon.util.KeyedRandom;




public final class WorldEditor extends BaseModule {
   private final EnumSetting<TimeMode> timeMode;
   private final EnumSetting<BrightnessMode> brightnessMode;
   private long worldTime;
   private long realTimeOfDay;
   private static final long KEY = KeyedRandom.create(808974600185147880L, 4496837085969300501L, MethodHandles.lookup().lookupClass()).next(272180593492914L);
   private static String[] STRINGS;
   private static long nightVisionDuration;
   private static long[] encryptedConstants;
   private static Long[] decryptedConstants;
   private static Map threadCipherCache;

   public WorldEditor() {
      super((STRINGS)[2], (STRINGS)[0], ModuleCategory.RENDER);

      String[] strings = STRINGS;
      this.timeMode = new EnumSetting<>(strings[1], TimeMode.IRL);
      this.brightnessMode = new EnumSetting<>(strings[3], BrightnessMode.NONE);
      this.addSettings(this.timeMode, this.brightnessMode);
      }

   @Subscribe
   private void onWorldTimeUpdate(PacketReceiveEvent event) {
      long longValue = KEY ^ 6057247867144L;
      boolean flagSet = Animations.isFlagSet();
      Packet packet = event.getPacket();
      boolean flag = flagSet;
      Packet packet2 = packet;
      if (!flag) {
         if (!(packet instanceof WorldTimeUpdateS2CPacket)) {
            return;
         }

         packet2 = packet;
      }

      WorldTimeUpdateS2CPacket packet3 = (WorldTimeUpdateS2CPacket)packet2;
      this.worldTime = packet3.timeOfDay();
      event.cancel();
   }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      try {
      long longValue = KEY ^ 17404271690683L;
      boolean flagSet = Animations.isFlagSet();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (!flagSet) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      label69: {
         Object object = tmp.world;
         if (!flagSet) {
            if (tmp.world == null) {
               return;
            }

            if (flagSet) {
               break label69;
            }

            object = this.brightnessMode.getValue();
         }

         switch ((BrightnessMode)object) {
            case NIGHT_VISION:
               StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.NIGHT_VISION, (int)nightVisionDuration, 67);
               ClientContext.MINECRAFT_CLIENT.player.addStatusEffect(statusEffectInstance);
               if (!flagSet) {
                  break;
               }
            case GAMMA:
               this.removeNightVision();
         }

      }

      long longValue2 = this.worldTime;
      if (!flagSet) {
         switch ((TimeMode)this.timeMode.getValue()) {
            case IRL:
               if (ClientContext.MINECRAFT_CLIENT.player.age % 20 == 0) {
                  LocalDateTime now = LocalDateTime.now();
                  long longValue3 = now.toLocalTime().toSecondOfDay();
                  long longValue4 = 7105487515254742422L ^ longValue;
                  short shortValue = 13238;
                  long longValue5 = longValue3
                     * ((Number)IndyRuntime.invoke(
                           "club.evon.module.impl.render.WorldEditor",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "m",
                           "(IJ)J",
                           new Object[]{Integer.valueOf(shortValue), longValue4}
                        ))
                        .longValue();
                  long longValue6 = 5609187957683508515L ^ longValue;
                  short shortValue2 = 15105;
                  longValue5 /= ((Number)IndyRuntime.invoke(
                        "club.evon.module.impl.render.WorldEditor",
                        "bootstrap",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        "m",
                        "(IJ)J",
                        new Object[]{Integer.valueOf(shortValue2), longValue6}
                     ))
                     .longValue();
                  long longValue7 = 2393253215548035446L ^ longValue;
                  short shortValue3 = 16211;
                  longValue5 += ((Number)IndyRuntime.invoke(
                        "club.evon.module.impl.render.WorldEditor",
                        "bootstrap",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        "m",
                        "(IJ)J",
                        new Object[]{Integer.valueOf(shortValue3), longValue7}
                     ))
                     .longValue();
                  long longValue8 = 3896376637641240853L ^ longValue;
                  short shortValue4 = 24370;
                  this.realTimeOfDay = longValue5
                     % ((Number)IndyRuntime.invoke(
                           "club.evon.module.impl.render.WorldEditor",
                           "bootstrap",
                           "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                           "m",
                           "(IJ)J",
                           new Object[]{Integer.valueOf(shortValue4), longValue8}
                        ))
                        .longValue();
               }

               longValue2 = this.realTimeOfDay;
               if (!flagSet) {
                  break;
               }
            case MORNING:
               long longValue9 = 8323715074503216766L ^ longValue;
               short shortValue5 = 24664;
               longValue2 = ((Number)IndyRuntime.invoke(
                     "club.evon.module.impl.render.WorldEditor",
                     "bootstrap",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "m",
                     "(IJ)J",
                     new Object[]{Integer.valueOf(shortValue5), longValue9}
                  ))
                  .longValue();
               if (!flagSet) {
                  break;
               }
            case DAY:
               long longValue10 = 1731670715481113401L ^ longValue;
               short shortValue6 = 11549;
               longValue2 = ((Number)IndyRuntime.invoke(
                     "club.evon.module.impl.render.WorldEditor",
                     "bootstrap",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "m",
                     "(IJ)J",
                     new Object[]{Integer.valueOf(shortValue6), longValue10}
                  ))
                  .longValue();
               if (!flagSet) {
                  break;
               }
            case EVENING:
               long longValue11 = 6280473188394231997L ^ longValue;
               short shortValue7 = 1694;
               longValue2 = ((Number)IndyRuntime.invoke(
                     "club.evon.module.impl.render.WorldEditor",
                     "bootstrap",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "m",
                     "(IJ)J",
                     new Object[]{Integer.valueOf(shortValue7), longValue11}
                  ))
                  .longValue();
               if (!flagSet) {
                  break;
               }
            case MIDNIGHT:
               long longValue12 = 7292638260884241411L ^ longValue;
               short shortValue8 = 4642;
               longValue2 = ((Number)IndyRuntime.invoke(
                     "club.evon.module.impl.render.WorldEditor",
                     "bootstrap",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "m",
                     "(IJ)J",
                     new Object[]{Integer.valueOf(shortValue8), longValue12}
                  ))
                  .longValue();
         }

         ClientContext.MINECRAFT_CLIENT.world.getLevelProperties().setTimeOfDay(longValue2);
      }
      } catch (Throwable throwable) {
         throw sneakyThrow(throwable);
      }
   }

   @Override
   public void onDisable() {
      long longValue = KEY ^ 72514221236030L;
      boolean flagUnset = Animations.isFlagUnset();
      if (flagUnset) {
         label24:
         if (ClientContext.MINECRAFT_CLIENT.player != null) {
            ClientWorld tmp = ClientContext.MINECRAFT_CLIENT.world;
            if (flagUnset) {
               if (ClientContext.MINECRAFT_CLIENT.world == null) {
                  break label24;
               }

               tmp = ClientContext.MINECRAFT_CLIENT.world;
            }

            tmp.getLevelProperties().setTimeOfDay(this.worldTime);
            this.removeNightVision();
         }

         super.onDisable();
      }
   }

   public boolean isGamma() {
      return this.brightnessMode.getValue().equals(BrightnessMode.GAMMA);
   }

   private void removeNightVision() {
      long longValue = KEY ^ 99999103343090L;
      boolean flagUnset = Animations.isFlagUnset();
      StatusEffectInstance statusEffect = ClientContext.MINECRAFT_CLIENT.player.getStatusEffect(StatusEffects.NIGHT_VISION);
      boolean flag = flagUnset;
      StatusEffectInstance statusEffectInstance = statusEffect;
      if (flag) {
         if (statusEffect == null) {
            return;
         }

         statusEffectInstance = statusEffect;
      }

      if (flag && statusEffectInstance.getAmplifier() == 67) {
         ClientContext.MINECRAFT_CLIENT.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
      }
   }

   static {
      try {
      long var16 = KEY ^ 110324125365924L;
      Cipher var18;
      Cipher var10000 = var18 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var16 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var19 = 1; var19 < 8; var19++) {
         var10003[var19] = (byte)(var16 << var19 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var25 = new String[4];
      int var23 = 0;
      String var22 = "\u00e3Q5!\u00ebn\u008e\u009b\u0016\u00d6\u0093f\u00c9\u0095\u00d8O\u00faq\u00d27\u00a9\u00aaEq/f\u0093\u00df\u00dd\u00b2\u00d7\u00b2\u0010\u0091\u00be\u00afi\u00fe\u00b0\u000fh\u00df\u00bf\u00ac\n\u00da\u00a0@\u00fd";
      int var24 = "\u00e3Q5!\u00ebn\u008e\u009b\u0016\u00d6\u0093f\u00c9\u0095\u00d8O\u00faq\u00d27\u00a9\u00aaEq/f\u0093\u00df\u00dd\u00b2\u00d7\u00b2\u0010\u0091\u00be\u00afi\u00fe\u00b0\u000fh\u00df\u00bf\u00ac\n\u00da\u00a0@\u00fd"
         .length();
      char var21 = ' ';
      int var29 = -1;

      label66:
      while (true) {
         String var30 = var22.substring(++var29, var29 + var21);
         int var10001 = -1;

         while (true) {
            byte[] var26 = var18.doFinal(var30.getBytes("ISO-8859-1"));
            String var43 = decodeUtf8(var26).intern();
            switch (var10001) {
               case 0:
                  var25[var23++] = var43;
                  if ((var29 += var21) >= var24) {
                     STRINGS = var25;
                     Cipher var11;
                     var10000 = var11 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var16 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var12 = 1; var12 < 8; var12++) {
                        var10003[var12] = (byte)(var16 << var12 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long var13 = 4792879600793211400L;
                     byte[] var15 = var11.doFinal(
                        new byte[]{
                           (byte)(var13 >>> 56),
                           (byte)(var13 >>> 48),
                           (byte)(var13 >>> 40),
                           (byte)(var13 >>> 32),
                           (byte)(var13 >>> 24),
                           (byte)(var13 >>> 16),
                           (byte)(var13 >>> 8),
                           (byte)var13
                        }
                     );
                     long var47 = (var15[0] & 255L) << 56
                        | (var15[1] & 255L) << 48
                        | (var15[2] & 255L) << 40
                        | (var15[3] & 255L) << 32
                        | (var15[4] & 255L) << 24
                        | (var15[5] & 255L) << 16
                        | (var15[6] & 255L) << 8
                        | var15[7] & 255L;
                     var10001 = (byte)-1;
                     nightVisionDuration = var47;
                     threadCipherCache = new HashMap(13);
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var16 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var16 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long[] var6 = new long[8];
                     int var3 = 0;
                     String var4 = "\u00c5\u00cb{p\u00aa\u008f\u00d6 /!\u00b7\u00dc\u00a5\u00fcRT\u000b~\u00d6\u001f\u0087^\u00ceWfj\u000b\u00ef\u00cfN\u00ef\f\u0081\u00f5W\u00a5\u0014~\u00ba\u0091\u00f3\u00ba\u001a\u00a5\u0005\u00f1\u00d9\u0019";
                     int var5 = "\u00c5\u00cb{p\u00aa\u008f\u00d6 /!\u00b7\u00dc\u00a5\u00fcRT\u000b~\u00d6\u001f\u0087^\u00ceWfj\u000b\u00ef\u00cfN\u00ef\f\u0081\u00f5W\u00a5\u0014~\u00ba\u0091\u00f3\u00ba\u001a\u00a5\u0005\u00f1\u00d9\u0019"
                        .length();
                     int var2 = 0;

                     label42:
                     while (true) {
                        var10001 = var2;
                        var2 += 8;
                        byte[] var7 = var4.substring(var10001, var2).getBytes("ISO-8859-1");
                        long[] var34 = var6;
                        var10001 = var3++;
                        long var49 = (var7[0] & 255L) << 56
                           | (var7[1] & 255L) << 48
                           | (var7[2] & 255L) << 40
                           | (var7[3] & 255L) << 32
                           | (var7[4] & 255L) << 24
                           | (var7[5] & 255L) << 16
                           | (var7[6] & 255L) << 8
                           | var7[7] & 255L;
                        byte var53 = -1;

                        while (true) {
                           long var8 = var49;
                           byte[] var10 = var0.doFinal(
                              new byte[]{
                                 (byte)(var8 >>> 56),
                                 (byte)(var8 >>> 48),
                                 (byte)(var8 >>> 40),
                                 (byte)(var8 >>> 32),
                                 (byte)(var8 >>> 24),
                                 (byte)(var8 >>> 16),
                                 (byte)(var8 >>> 8),
                                 (byte)var8
                              }
                           );
                           long var55 = (var10[0] & 255L) << 56
                              | (var10[1] & 255L) << 48
                              | (var10[2] & 255L) << 40
                              | (var10[3] & 255L) << 32
                              | (var10[4] & 255L) << 24
                              | (var10[5] & 255L) << 16
                              | (var10[6] & 255L) << 8
                              | var10[7] & 255L;
                           switch (var53) {
                              case 0:
                                 var34[var10001] = var55;
                                 if (var2 >= var5) {
                                     encryptedConstants = var6;
                                     decryptedConstants = new Long[8];
                                     break label66;
                                 }
                                 break;
                              default:
                                 var34[var10001] = var55;
                                 if (var2 < var5) {
                                    continue label42;
                                 }

                                 var4 = "\u00a6\u00b9F\u0093\u00f6\u00adN\u00c6\u00eb#\u00e3L\u00c8\u00d8GV";
                                 var5 = "\u00a6\u00b9F\u0093\u00f6\u00adN\u00c6\u00eb#\u00e3L\u00c8\u00d8GV".length();
                                 var2 = 0;
                           }

                           int var41 = var2;
                           var2 += 8;
                           var7 = var4.substring(var41, var2).getBytes("ISO-8859-1");
                           var34 = var6;
                           var10001 = var3++;
                           var49 = (var7[0] & 255L) << 56
                              | (var7[1] & 255L) << 48
                              | (var7[2] & 255L) << 40
                              | (var7[3] & 255L) << 32
                              | (var7[4] & 255L) << 24
                              | (var7[5] & 255L) << 16
                              | (var7[6] & 255L) << 8
                              | var7[7] & 255L;
                           var53 = 0;
                        }
                     }
                  }

                  var21 = var22.charAt(var29);
                  break;
               default:
                  var25[var23++] = var43;
                  if ((var29 += var21) < var24) {
                     var21 = var22.charAt(var29);
                     continue label66;
                  }

                  var22 = "\u00eb\u001d7q\u00ea|Z\u00ab\u00b2c\u00fb]r\u00c0\\x\u0010\u00f2u\u0099\u0084\u00b2~M\u00139\u0012Ave\u008a\u00f5p";
                  var24 = "\u00eb\u001d7q\u00ea|Z\u00ab\u00b2c\u00fb]r\u00c0\\x\u0010\u00f2u\u0099\u0084\u00b2~M\u00139\u0012Ave\u008a\u00f5p".length();
                  var21 = 16;
                  var29 = -1;
            }

            var30 = var22.substring(++var29, var29 + var21);
            var10001 = 0;
         }
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
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

   private static long decryptConstant(int index, long key) {
      int value = index ^ (int)(key & 32767L) ^ 19947;
      if (decryptedConstants[value] == null) {
         byte[] bytes = new byte[]{
            (byte)(key >>> 56),
            (byte)(key >>> 48),
            (byte)(key >>> 40),
            (byte)(key >>> 32),
            (byte)(key >>> 24),
            (byte)(key >>> 16),
            (byte)(key >>> 8),
            (byte)key
         };
         long longValue = encryptedConstants[value];
         byte[] bytes2 = new byte[]{
            (byte)(longValue >>> 56),
            (byte)(longValue >>> 48),
            (byte)(longValue >>> 40),
            (byte)(longValue >>> 32),
            (byte)(longValue >>> 24),
            (byte)(longValue >>> 16),
            (byte)(longValue >>> 8),
            (byte)longValue
         };
         Long threadId = Thread.currentThread().threadId();
         Object[] values = (Object[])threadCipherCache.get(threadId);

         byte[] bytes3;
         try {
            if (values == null) {
               values = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
               threadCipherCache.put(threadId, values);
            }

            DESKeySpec dESKeySpec = new DESKeySpec(bytes);
            SecretKey generateSecret = ((SecretKeyFactory)values[1]).generateSecret(dESKeySpec);
            Cipher cipher = (Cipher)values[0];
            cipher.init(2, generateSecret, (IvParameterSpec)values[2]);
            bytes3 = cipher.doFinal(bytes2);
         } catch (Exception exception) {
            throw new RuntimeException("club/evon/module/impl/render/WorldEditor", exception);
         }

         long longValue2 = (bytes3[0] & 255L) << 56
            | (bytes3[1] & 255L) << 48
            | (bytes3[2] & 255L) << 40
            | (bytes3[3] & 255L) << 32
            | (bytes3[4] & 255L) << 24
            | (bytes3[5] & 255L) << 16
            | (bytes3[6] & 255L) << 8
            | bytes3[7] & 255L;
         decryptedConstants[value] = longValue2;
      }

      return decryptedConstants[value];
   }

   private static long bootstrapConstant(Lookup lookup, MutableCallSite callSite, String name, Object[] args) {
      int value = (Integer)args[0];
      long longValue = (Long)args[1];
      long longValue2 = decryptConstant(value, longValue);
      MethodHandle constant = MethodHandles.constant(long.class, longValue2);
      callSite.setTarget(MethodHandles.dropArguments(constant, 0, int.class, long.class));
      return longValue2;
   }

   private static CallSite bootstrap(Lookup lookup, String name, MethodType type) {
      MutableCallSite mutableCallSite = new MutableCallSite(type);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(
                  MethodHandles.lookup()
                     .findStatic(WorldEditor.class, "bootstrapConstant", MethodType.methodType(long.class, Lookup.class, MutableCallSite.class, String.class, Object[].class))
                     .asCollector(Object[].class, type.parameterCount()),
                  0,
                  lookup,
                  mutableCallSite,
                  name
               ),
               type
            )
         );
         return mutableCallSite;
      } catch (Exception exception) {
         throw new RuntimeException("club/evon/module/impl/render/WorldEditor : " + name + " : " + type.toString(), exception);
      }
   }
}
