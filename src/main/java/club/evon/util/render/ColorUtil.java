package club.evon.util.render;
import java.awt.Color;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.json.JSONException;
import club.evon.runtime.IndyRuntime;
import club.evon.util.KeyedRandom;




public final class ColorUtil {
   private static int flowState;
   private static final long INDY_KEY = KeyedRandom.create(-1226132674947104492L, 595309295658165290L, MethodHandles.lookup().lookupClass()).next(160484078706940L);
   private static final long[] ENCRYPTED_CONSTANTS;
   private static final Integer[] DECRYPTED_CACHE;
   private static final Map CIPHER_CACHE = new HashMap(13);

   public static Color withAlpha(Color color, double percentage) {
      percentage = MathHelper.clamp(percentage, 0.0, 1.0);
      return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(percentage * 255.0));
   }

   public static Color scaleBrightness(Color color, float factor) {
      return new Color(
         Math.max((int)(color.getRed() * factor), 0),
         Math.max((int)(color.getGreen() * factor), 0),
         Math.max((int)(color.getBlue() * factor), 0),
         color.getAlpha()
      );
   }

   public static Color clamp(Color color) {
      return new Color(
         MathHelper.clamp(color.getRed(), 0, 255),
         MathHelper.clamp(color.getGreen(), 0, 255),
         MathHelper.clamp(color.getBlue(), 0, 255),
         MathHelper.clamp(color.getAlpha(), 0, 255)
      );
   }

   public static Color interpolate(Color color1, Color color2, double percentage) {
      percentage = MathHelper.clamp(percentage, 0.0, 1.0);
      return new Color(
         (int)MathHelper.lerp(percentage, color1.getRed(), color2.getRed()),
         (int)MathHelper.lerp(percentage, color1.getGreen(), color2.getGreen()),
         (int)MathHelper.lerp(percentage, color1.getBlue(), color2.getBlue()),
         (int)MathHelper.lerp(percentage, color1.getAlpha(), color2.getAlpha())
      );
   }

   public static Formatting getClosestFormatting(Color color) {
      long longValue = INDY_KEY ^ 28209726069528L;
      String[] strings = RenderUtil.getGuardState();
      Formatting formatting;
      double doubleValue;
      Formatting formatting2 = null;
      double doubleValue2 = Double.MAX_VALUE;
      Formatting[] values = Formatting.values();
      String[] strings2 = strings;
      int length = values.length;
      int index = 0;

      while (true) {
         if (index < length) {
            Formatting formatting3 = values[index];
            if (strings2 == null) {
               formatting = formatting3;
               if (strings2 != null) {
                  break;
               }

               label79:
               if (formatting3.isColor()) {
                  Formatting formatting4 = formatting3;
                  Formatting tmp = Formatting.BLACK;
                  if (strings2 == null) {
                     if (formatting3 == Formatting.BLACK) {
                        break label79;
                     }

                     formatting4 = formatting3;
                     tmp = Formatting.GRAY;
                  }

                  if (strings2 == null) {
                     if (formatting4 == tmp) {
                        break label79;
                     }

                     formatting4 = formatting3;
                     tmp = Formatting.DARK_GRAY;
                  }

                  label67:
                  if (formatting4 != tmp || strings2 != null) {
                     label63: {
                        double doubleValue3 = getColorDistance(color, fromRgb(formatting3.getColorValue()));
                        Formatting formatting5 = formatting2;
                        if (strings2 == null) {
                           if (formatting2 != null) {
                              doubleValue = doubleValue3;
                              if (strings2 != null) {
                                 break label63;
                              }

                              if (!(doubleValue3 < doubleValue2)) {
                                 break label67;
                              }
                           }

                           formatting5 = formatting3;
                        }

                        formatting2 = formatting5;
                        doubleValue = doubleValue3;
                     }

                     doubleValue2 = doubleValue;
                  }
               }

               index++;
            }

            if (strings2 == null) {
               continue;
            }
         }

         formatting = formatting2;
         break;
      }

      return formatting;
   }

   public static Color fromRgb(int decimal) {
      try {
      long longValue = INDY_KEY ^ 43870821065604L;
      long longValue2 = 6840652215877792837L ^ longValue;
      short shortValue = 25227;
      int value = (
            decimal
               & ((Number)IndyRuntime.invoke(
                     "club.evon.util.render.ColorUtil",
                     "createConstantCallSite",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "h",
                     "(IJ)I",
                     new Object[]{Integer.valueOf(shortValue), longValue2}
                  ))
                  .intValue()
         )
         >> 16;
      long longValue3 = 5913954883786407988L ^ longValue;
      short shortValue2 = 5880;
      int value2 = (
            decimal
               & ((Number)IndyRuntime.invoke(
                     "club.evon.util.render.ColorUtil",
                     "createConstantCallSite",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "h",
                     "(IJ)I",
                     new Object[]{Integer.valueOf(shortValue2), longValue3}
                  ))
                  .intValue()
         )
         >> 8;
      int value3 = decimal & 0xFF;
      return new Color(value, value2, value3);
      } catch (Throwable throwable) {
         throw sneakyThrow(throwable);
      }
   }

   public static double getColorDistance(Color color1, Color color2) {
      int value = color1.getRed() + color2.getRed() >> 1;
      int value2 = color1.getRed() - color2.getRed();
      int value3 = color1.getGreen() - color2.getGreen();
      int value4 = color1.getBlue() - color2.getBlue();
      return Math.sqrt(((512 + value) * value2 * value2 >> 8) + 4 * value3 * value3 + ((767 - value) * value4 * value4 >> 8));
   }

   public static Color sinusoidalBlend(Color color1, Color color2, double milliseconds, double offset) {
      return interpolate(color1, color2, Math.sin(System.currentTimeMillis() / milliseconds - offset) * 0.5 + 0.5);
   }

   public static Color sinusoidalBlend(Color color1, Color color2, double milliseconds) {
      return sinusoidalBlend(color1, color2, milliseconds, 0.0);
   }

   public static Formatting getFormattingByColor(int color) {
      long longValue = INDY_KEY ^ 67314918842366L;
      String[] strings = RenderUtil.getGuardState();
      Formatting formatting;
      Formatting[] values = Formatting.values();
      int length = values.length;
      String[] strings2 = strings;
      int index = 0;

      while (true) {
         if (index < length) {
            Formatting formatting2 = values[index];
            if (strings2 == null) {
               if (formatting2.getColorValue() != null) {
                  formatting = formatting2;
                  if (strings2 != null) {
                     break;
                  }

                  if (formatting2.getColorValue().equals(color)) {
                     formatting = formatting2;
                     break;
                  }
               }

               index++;
            }

            if (strings2 == null) {
               continue;
            }
         }

         return null;
      }

      return formatting;
   }

   public static int getHealthColor(float health) {
      try {
      long longValue = INDY_KEY ^ 82695364801668L;
      int value = Color.HSBtoRGB(health / 3.0F, 0.65F, 1.0F);
      long longValue2 = 5694375182170266102L ^ longValue;
      short shortValue = 22588;
      return value
         | ((Number)IndyRuntime.invoke(
               "club.evon.util.render.ColorUtil",
               "createConstantCallSite",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "h",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue), longValue2}
            ))
            .intValue();
      } catch (Throwable throwable) {
         throw sneakyThrow(throwable);
      }
   }

   public static int[] extractRgba(int hex) {
      return new int[]{hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, hex >> 24 & 0xFF};
   }

   public static Color toColor(int decimal) {
      try {
      long longValue = INDY_KEY ^ 128951538205227L;
      long longValue2 = 6471253276291515636L ^ longValue;
      short shortValue = 13206;
      int value = (
            decimal
               & ((Number)IndyRuntime.invoke(
                     "club.evon.util.render.ColorUtil",
                     "createConstantCallSite",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "h",
                     "(IJ)I",
                     new Object[]{Integer.valueOf(shortValue), longValue2}
                  ))
                  .intValue()
         )
         >> 16;
      long longValue3 = 141528656642975005L ^ longValue;
      short shortValue2 = 21117;
      return new Color(
         value,
         (
               decimal
                  & ((Number)IndyRuntime.invoke(
                        "club.evon.util.render.ColorUtil",
                        "createConstantCallSite",
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        "h",
                        "(IJ)I",
                        new Object[]{Integer.valueOf(shortValue2), longValue3}
                     ))
                     .intValue()
            )
            >> 8,
         decimal & 0xFF
      );
      } catch (Throwable throwable) {
         throw sneakyThrow(throwable);
      }
   }

   public static int applyOpacity(int color, float opacity) {
      opacity = Math.min(1.0F, Math.max(0.0F, opacity));
      Color color2 = new Color(color);
      return new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), (int)(color2.getAlpha() * opacity)).getRGB();
   }

   public static int toArgb(Color color) {
      int value = color.getRed();
      int value2 = color.getGreen();
      int value3 = color.getBlue();
      int value4 = color.getAlpha();
      return (value << 16) + (value2 << 8) + value3 + (value4 << 24);
   }

   public static void setFlowState(int state) {
      flowState = state;
   }

   public static int getFlowState() {
      return flowState;
   }

   public static int getBranchFlag() {
      int value = getFlowState();
      return value == 0 ? 45 : 0;
   }

   static {
      try {
      long var0 = INDY_KEY ^ 14035030487844L;
      setFlowState(0);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/NoPadding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      long[] var8 = new long[5];
      int var5 = 0;
      String var6 = "m\u00d5\u0002\u00a77\u009a\u00fe9\u0001\u0018\u00fa}\u00cd\u0092`D\u00ef@N\u00b8\u0098\u00ddg\u00b3";
      int var7 = "m\u00d5\u0002\u00a77\u009a\u00fe9\u0001\u0018\u00fa}\u00cd\u0092`D\u00ef@N\u00b8\u0098\u00ddg\u00b3".length();
      int var4 = 0;

      label23:
      while (true) {
         int var10001 = var4;
         var4 += 8;
         byte[] var9 = var6.substring(var10001, var4).getBytes("ISO-8859-1");
         long[] var14 = var8;
         var10001 = var5++;
         long var17 = (var9[0] & 255L) << 56
            | (var9[1] & 255L) << 48
            | (var9[2] & 255L) << 40
            | (var9[3] & 255L) << 32
            | (var9[4] & 255L) << 24
            | (var9[5] & 255L) << 16
            | (var9[6] & 255L) << 8
            | var9[7] & 255L;
         byte var19 = -1;

         while (true) {
            long var10 = var17;
            byte[] var12 = var2.doFinal(
               new byte[]{
                  (byte)(var10 >>> 56),
                  (byte)(var10 >>> 48),
                  (byte)(var10 >>> 40),
                  (byte)(var10 >>> 32),
                  (byte)(var10 >>> 24),
                  (byte)(var10 >>> 16),
                  (byte)(var10 >>> 8),
                  (byte)var10
               }
            );
            long var21 = (var12[0] & 255L) << 56
               | (var12[1] & 255L) << 48
               | (var12[2] & 255L) << 40
               | (var12[3] & 255L) << 32
               | (var12[4] & 255L) << 24
               | (var12[5] & 255L) << 16
               | (var12[6] & 255L) << 8
               | var12[7] & 255L;
            switch (var19) {
               case 0:
                  var14[var10001] = var21;
                  if (var4 >= var7) {
                     ENCRYPTED_CONSTANTS = var8;
                     DECRYPTED_CACHE = new Integer[5];
                     break label23;
                  }
                  break;
               default:
                  var14[var10001] = var21;
                  if (var4 < var7) {
                     continue label23;
                  }

                  var6 = "\u00d2\u00a8\u00f1\u00cd|]\u001c\u00fb\u00cd\r\u009b\u00b9\u00d1-25";
                  var7 = "\u00d2\u00a8\u00f1\u00cd|]\u001c\u00fb\u00cd\r\u009b\u00b9\u00d1-25".length();
                  var4 = 0;
            }

            int var16 = var4;
            var4 += 8;
            var9 = var6.substring(var16, var4).getBytes("ISO-8859-1");
            var14 = var8;
            var10001 = var5++;
            var17 = (var9[0] & 255L) << 56
               | (var9[1] & 255L) << 48
               | (var9[2] & 255L) << 40
               | (var9[3] & 255L) << 32
               | (var9[4] & 255L) << 24
               | (var9[5] & 255L) << 16
               | (var9[6] & 255L) << 8
               | var9[7] & 255L;
            var19 = 0;
         }
      }
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }
   }

   private static JSONException passThroughException(JSONException exception) {
      return exception;
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }

   private static int decryptConstant(int seed, long key) {
      int value = seed ^ (int)(key & 32767L) ^ 2714;
      if (DECRYPTED_CACHE[value] == null) {
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
         long longValue = ENCRYPTED_CONSTANTS[value];
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
         Object[] values = (Object[])CIPHER_CACHE.get(threadId);

         byte[] bytes3;
         try {
            if (values == null) {
               values = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
               CIPHER_CACHE.put(threadId, values);
            }

            DESKeySpec dESKeySpec = new DESKeySpec(bytes);
            SecretKey generateSecret = ((SecretKeyFactory)values[1]).generateSecret(dESKeySpec);
            Cipher cipher = (Cipher)values[0];
            cipher.init(2, generateSecret, (IvParameterSpec)values[2]);
            bytes3 = cipher.doFinal(bytes2);
         } catch (Exception exception) {
            throw new RuntimeException("club/evon/util/render/ColorUtil", exception);
         }

         int value2 = (bytes3[4] & 255) << 24 | (bytes3[5] & 255) << 16 | (bytes3[6] & 255) << 8 | bytes3[7] & 255;
         DECRYPTED_CACHE[value] = value2;
      }

      return DECRYPTED_CACHE[value];
   }

   private static int bootstrapConstant(Lookup lookup, MutableCallSite callSite, String name, Object[] args) {
      int value = (Integer)args[0];
      long longValue = (Long)args[1];
      int value2 = decryptConstant(value, longValue);
      MethodHandle constant = MethodHandles.constant(int.class, value2);
      callSite.setTarget(MethodHandles.dropArguments(constant, 0, int.class, long.class));
      return value2;
   }

   private static CallSite createConstantCallSite(Lookup lookup, String name, MethodType type) {
      MutableCallSite mutableCallSite = new MutableCallSite(type);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(
                  MethodHandles.lookup()
                     .findStatic(ColorUtil.class, "bootstrapConstant", MethodType.methodType(int.class, Lookup.class, MutableCallSite.class, String.class, Object[].class))
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
         throw new RuntimeException("club/evon/util/render/ColorUtil : " + name + " : " + type.toString(), exception);
      }
   }
}
