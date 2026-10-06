package club.evon.module.impl.render;
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
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.json.JSONException;
import org.lwjgl.opengl.GL11;
import club.evon.event.EntityModelRenderEvent;
import club.evon.event.EventPhase;
import club.evon.event.RenderLayerEvent;
import club.evon.event.RenderPhaseEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.runtime.IndyRuntime;
import club.evon.setting.BooleanSetting;
import club.evon.setting.ColorSetting;
import club.evon.setting.ColorType;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.render.ColorUtil;




public final class Chams extends BaseModule {
   private final BooleanSetting applyColor;
   private final EnumSetting<ColorType> colorType;
   private final ColorSetting color;
   private final DoubleSetting alpha;
   private static final Identifier OVERLAY_TEXTURE;
   private static Entity renderedEntity;
   private static final long KEY = KeyedRandom.create(-9039634628400386717L, 4381480740343462356L, MethodHandles.lookup().lookupClass()).next(60643695010173L);
   private static final String[] STRINGS;
   private static final long[] encryptedConstants;
   private static final Integer[] decryptedConstants;
   private static final Map threadCipherCache;

   public Chams() {
      super((STRINGS)[2], (STRINGS)[5], ModuleCategory.RENDER);

      String[] strings = STRINGS;
      this.applyColor = new BooleanSetting(strings[4], true);
      this.colorType = new EnumSetting<>(strings[7], ColorType.THEMED);
      this.color = new ColorSetting(strings[0], new Color(172, 13, 61));
      this.alpha = new DoubleSetting(strings[3], 75.0, 15.0, 100.0, 5.0);
      this.addSettings(this.applyColor, this.colorType, this.color, this.alpha);
      this.color.setVisible(() -> {
         long var1x = KEY ^ 83064561445069L;
         return this.colorType.getValue() == ColorType.CUSTOM;
      });
      }

   @Subscribe
   private void onRenderPhase(RenderPhaseEvent event) throws Throwable {
      long longValue = KEY ^ 108820275768468L;
      boolean flagUnset = Animations.isFlagUnset();
      if (flagUnset) {
         if (event.getPhase() == EventPhase.PRE) {
            long longValue2 = 1896518375425758041L ^ longValue;
            short shortValue = 17908;
            GL11.glEnable(
               ((Number)IndyRuntime.invoke(
                     "club.evon.module.impl.render.Chams",
                     "bootstrap",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "g",
                     "(IJ)I",
                     new Object[]{Integer.valueOf(shortValue), longValue2}
                  ))
                  .intValue()
            );
            GL11.glPolygonOffset(1.0F, -1100000.0F);
            if (flagUnset) {
               return;
            }
         }

         GL11.glPolygonOffset(1.0F, 1100000.0F);
      }

      long longValue3 = 6091518878587386138L ^ longValue;
      short shortValue2 = 28598;
      GL11.glDisable(
         ((Number)IndyRuntime.invoke(
               "club.evon.module.impl.render.Chams",
               "bootstrap",
               "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
               "g",
               "(IJ)I",
               new Object[]{Integer.valueOf(shortValue2), longValue3}
            ))
            .intValue()
      );
   }

   @Subscribe
   private void onRenderLayer(RenderLayerEvent event) {
      long longValue = KEY ^ 129395013202473L;
      boolean flagSet = Animations.isFlagSet();
      if (!flagSet) {
         if (!this.applyColor.I()) {
            return;
         }

         event.setRenderLayer(RenderLayer.getItemEntityTranslucentCull(OVERLAY_TEXTURE));
      }
   }

   @Subscribe
   private void onEntityModelRender(EntityModelRenderEvent event) {
      Color color2;
      label31: {
         long longValue = KEY ^ 13119655375533L;
         boolean flagUnset = Animations.isFlagUnset();
         Object tmp = this.applyColor.I();
         if (flagUnset) {
            if (!(Boolean)tmp) {
               color2 = Color.WHITE;
               break label31;
            }

            tmp = this.colorType.getValue();
         }

         if (flagUnset) {
            if (tmp == ColorType.THEMED) {
               color2 = Overlay.getPrimaryColor();
               break label31;
            }

            tmp = this.color.getValue();
         }

         color2 = (Color)tmp;
      }

      Color color3 = color2;
      event.getModel()
         .render(
            event.getMatrices(),
            event.getVertexConsumer(),
            event.getLight(),
            event.getOverlay(),
            ColorUtil.toArgb(new Color(color3.getRed(), color3.getGreen(), color3.getBlue(), (int)(255.0F * (this.alpha.J().floatValue() / this.alpha.getMaximum().floatValue()))))
         );
      event.setRenderModel(false);
   }

   public static Entity getRenderedEntity() {
      return renderedEntity;
   }

   public static void setRenderedEntity(Entity entity) {
      renderedEntity = entity;
   }

   static {
      try {
      long var11 = KEY ^ 69114584160547L;
      Cipher var13;
      Cipher var10000 = var13 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var14 = 1; var14 < 8; var14++) {
         var10003[var14] = (byte)(var11 << var14 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var20 = new String[8];
      int var18 = 0;
      String var17 = "\u00db\u008e\n!\u009d7\u00f3\u008c\bgZ$\r\u0090\u008bS#\b'\u0095\u00b1\u001b\u00d8D\u00d3\u00d5\b\u00d6`\u00fb\u0088h\u00c7p0\u0010S\u00be\u00f1\u0006R\u0015Hr\u00c3\u008f\u00b9\u0011>\u00de\u00a9\u00cf T\u00afd\u000e\u00ec\u0084b%{P\u008d\u0003-\u0016\u00fe\u00a0f+w\u0090\u008d\u00ae\u00a1X\u00fb\u00e5\f\u00b0\u007fG\u00d0\u00ca";
      int var19 = "\u00db\u008e\n!\u009d7\u00f3\u008c\bgZ$\r\u0090\u008bS#\b'\u0095\u00b1\u001b\u00d8D\u00d3\u00d5\b\u00d6`\u00fb\u0088h\u00c7p0\u0010S\u00be\u00f1\u0006R\u0015Hr\u00c3\u008f\u00b9\u0011>\u00de\u00a9\u00cf T\u00afd\u000e\u00ec\u0084b%{P\u008d\u0003-\u0016\u00fe\u00a0f+w\u0090\u008d\u00ae\u00a1X\u00fb\u00e5\f\u00b0\u007fG\u00d0\u00ca"
         .length();
      char var16 = '\b';
      int var24 = -1;

      label45:
      while (true) {
         String var25 = var17.substring(++var24, var24 + var16);
         int var10001 = -1;

         while (true) {
            byte[] var21 = var13.doFinal(var25.getBytes("ISO-8859-1"));
            String var34 = decodeUtf8(var21).intern();
            switch (var10001) {
               case 0:
                  var20[var18++] = var34;
                  if ((var24 += var16) >= var19) {
                     STRINGS = var20;
                     threadCipherCache = new HashMap(13);
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var11 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var11 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long[] var6 = new long[2];
                     int var3 = 0;
                     String var4 = "\u0003\u0002T\u0010\u00d2\u001d\u0012g<\u00ffB\u00b6`\ns\u0014";
                     int var5 = "\u0003\u0002T\u0010\u00d2\u001d\u0012g<\u00ffB\u00b6`\ns\u0014".length();
                     int var2 = 0;

                     do {
                        var10001 = var2;
                        var2 += 8;
                        byte[] var7 = var4.substring(var10001, var2).getBytes("ISO-8859-1");
                        var10001 = var3++;
                        long var8 = (var7[0] & 255L) << 56
                           | (var7[1] & 255L) << 48
                           | (var7[2] & 255L) << 40
                           | (var7[3] & 255L) << 32
                           | (var7[4] & 255L) << 24
                           | (var7[5] & 255L) << 16
                           | (var7[6] & 255L) << 8
                           | var7[7] & 255L;
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
                        long var10004 = (var10[0] & 255L) << 56
                           | (var10[1] & 255L) << 48
                           | (var10[2] & 255L) << 40
                           | (var10[3] & 255L) << 32
                           | (var10[4] & 255L) << 24
                           | (var10[5] & 255L) << 16
                           | (var10[6] & 255L) << 8
                           | var10[7] & 255L;
                        byte var39 = -1;
                        var6[var10001] = var10004;
                     } while (var2 < var5);

                     encryptedConstants = var6;
                     decryptedConstants = new Integer[2];
                     String[] var22 = STRINGS;
                     OVERLAY_TEXTURE = Identifier.of(var22[1], var22[6]);
                     break label45;
                  }

                  var16 = var17.charAt(var24);
                  break;
               default:
                  var20[var18++] = var34;
                  if ((var24 += var16) < var19) {
                     var16 = var17.charAt(var24);
                     continue label45;
                  }

                  var17 = "\u00dc\u00f4\u009eV\u009f\u008c\u00eb\u00dag}\u008b\u0019\u00d5t\u00b2\u00cc\u00cdbneG\u00e4\u00f6\u0007\u0010\u0081hh3\u007f/0\u00d3\u00ef\u00dcE\u00ac\u009c\u00ff\u0003,";
                  var19 = "\u00dc\u00f4\u009eV\u009f\u008c\u00eb\u00dag}\u008b\u0019\u00d5t\u00b2\u00cc\u00cdbneG\u00e4\u00f6\u0007\u0010\u0081hh3\u007f/0\u00d3\u00ef\u00dcE\u00ac\u009c\u00ff\u0003,"
                     .length();
                  var16 = 24;
                  var24 = -1;
            }

            var25 = var17.substring(++var24, var24 + var16);
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

   private static int decryptConstant(int index, long key) {
      int value = index ^ (int)(key & 32767L) ^ 7901;
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
            throw new RuntimeException("club/evon/ge", exception);
         }

         int value2 = (bytes3[4] & 255) << 24 | (bytes3[5] & 255) << 16 | (bytes3[6] & 255) << 8 | bytes3[7] & 255;
         decryptedConstants[value] = value2;
      }

      return decryptedConstants[value];
   }

   private static int bootstrapConstant(Lookup lookup, MutableCallSite callSite, String name, Object[] args) {
      int value = (Integer)args[0];
      long longValue = (Long)args[1];
      int value2 = decryptConstant(value, longValue);
      MethodHandle constant = MethodHandles.constant(int.class, value2);
      callSite.setTarget(MethodHandles.dropArguments(constant, 0, int.class, long.class));
      return value2;
   }

   private static CallSite bootstrap(Lookup lookup, String name, MethodType type) {
      MutableCallSite mutableCallSite = new MutableCallSite(type);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(
                  MethodHandles.lookup()
                     .findStatic(
                        Chams.class,
                        "bootstrapConstant",
                        MethodType.methodType(int.class, Lookup.class, MutableCallSite.class, String.class, Object[].class)
                     )
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
         throw new RuntimeException("club/evon/ge" + " : " + name + " : " + type.toString(), exception);
      }
   }
}
