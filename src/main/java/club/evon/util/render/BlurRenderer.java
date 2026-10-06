package club.evon.util.render;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.WindowFramebuffer;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.util.Identifier;
import org.lwjgl.system.MemoryStack;
import club.evon.ClientContext;
import club.evon.mixins.GameRendererAccessor;
import club.evon.mixins.PostEffectPassAccessor;
import club.evon.util.KeyedRandom;




public final class BlurRenderer {
   private static Framebuffer backgroundBuffer;
   private static Framebuffer guiBuffer;
   private static final Identifier BLUR_ID;
   private static float lastRadius;
   private static final long KEY = KeyedRandom.create(-4172293852863484042L, -3596197409246402802L, MethodHandles.lookup().lookupClass()).next(148252462051867L);
   private static final String[] STRINGS_TABLE;

   public static void blurBackground() {
      long longValue = KEY ^ 74821203645917L;
      String[] strings = RenderUtil.getGuardState();
      Framebuffer tmp = backgroundBuffer;
      if (strings == null) {
         if (backgroundBuffer == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.getFramebuffer();
      }

      Framebuffer framebuffer = tmp;
      FramebufferUtil.copy(framebuffer, backgroundBuffer);
      applyBlur(backgroundBuffer, 5.0F);
   }

   public static void blurGui() {
      long longValue = KEY ^ 81566168340369L;
      String[] strings = RenderUtil.getGuardState();
      Framebuffer tmp = guiBuffer;
      if (strings == null) {
         if (guiBuffer == null) {
            return;
         }

         tmp = guiBuffer;
      }

      applyBlur(tmp, 5.0F);
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static void applyBlur(Framebuffer framebuffer, float radius) {
      long longValue = KEY ^ 13898497999279L;
      String[] strings = RenderUtil.getGuardState();
      PostEffectProcessor loadPostEffect = ClientContext.MINECRAFT_CLIENT.getShaderLoader().loadPostEffect(BLUR_ID, DefaultFramebufferSet.MAIN_ONLY);
      String[] strings2 = strings;
      if (loadPostEffect != null) {
         label100: {
            float floatValue;
            int value = (floatValue = lastRadius - radius) == 0.0F ? 0 : (floatValue < 0.0F ? -1 : 1);
            if (strings2 == null) {
               if (value == 0) {
                  break label100;
               }

               lastRadius = radius;
               value = 0;
            }

            int value2 = value;

            for (PostEffectPass postEffectPass : loadPostEffect.passes) {
               MemoryStack stackPush = MemoryStack.stackPush();

               try {
                  Std140Builder onStack = Std140Builder.onStack(stackPush, 16);
                  if (strings2 != null) {
                     return;
                  }

                  int value3 = value2 % 2;
                  if (strings2 == null) {
                     value3 = value3 == 0 ? 1 : 0;
                  }

                  int value4 = value3;
                  onStack.putVec2(value4 != 0 ? 1.0F : 0.0F, value4 != 0 ? 0.0F : 1.0F);
                  onStack.putFloat(radius);
                  PostEffectPassAccessor postEffectPassAccessor = (PostEffectPassAccessor)postEffectPass;
                  GpuBuffer createBuffer = RenderSystem.getDevice().createBuffer(() -> postEffectPassAccessor.getId() + STRINGS_TABLE[1], 128, onStack.get());
                  Map uniformBuffers = postEffectPassAccessor.getUniformBuffers();
                  String[] strings3 = STRINGS_TABLE;
                  ((GpuBuffer)uniformBuffers.get(strings3[2])).close();
                  postEffectPassAccessor.getUniformBuffers().put(strings3[0], createBuffer);
               } catch (Throwable throwable) {
                  MemoryStack memoryStack = stackPush;
                  if (strings2 == null) {
                     if (stackPush == null) {
                        throw throwable;
                     }

                     try {
                        memoryStack = stackPush;
                     } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                        throw throwable;
                     }
                  }

                  try {
                     memoryStack.close();
                  } catch (Throwable throwable3) {
                     throwable.addSuppressed(throwable3);
                  }

                  throw throwable;
               }

               if (strings2 == null) {
                  if (stackPush != null) {
                     stackPush.close();
                  }

                  value2++;
               }

               if (strings2 != null) {
                  break;
               }
            }
         }

         loadPostEffect.render(framebuffer, ((GameRendererAccessor)ClientContext.MINECRAFT_CLIENT.gameRenderer).getPool());
      }
   }

   public static void resize(int width, int height) {
      long longValue = KEY ^ 119323550644663L;
      String[] strings = RenderUtil.getGuardState();
      Framebuffer tmp = backgroundBuffer;
      if (strings == null) {
         if (backgroundBuffer != null) {
            backgroundBuffer.delete();
         }

         tmp = guiBuffer;
      }

      label25: {
         if (strings == null) {
            if (tmp == null) {
               break label25;
            }

            tmp = guiBuffer;
         }

         tmp.delete();
      }

      backgroundBuffer = new WindowFramebuffer(width, height);
      RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(backgroundBuffer.getColorAttachment(), 0, backgroundBuffer.getDepthAttachment(), 1.0);
      guiBuffer = new WindowFramebuffer(width, height);
      RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(guiBuffer.getColorAttachment(), 0, guiBuffer.getDepthAttachment(), 1.0);
      RenderUtil.initImagePaint(width, height, Integer.parseInt(backgroundBuffer.getColorAttachment().getLabel()), RenderUtil.gameBlurPaint);
      RenderUtil.initImagePaint(width, height, Integer.parseInt(guiBuffer.getColorAttachment().getLabel()), RenderUtil.hudBlurPaint);
   }

   public static Framebuffer getGuiBuffer() {
      return guiBuffer;
   }

   static {
      try {
      long var0 = KEY ^ 29660433601794L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[4];
      int var7 = 0;
      String var6 = "\u00fa\u0081\u008c=\u0089\u0003\u0001\u0080\u00e4\u00aa\u00bc\u0093\u00f6O\u0097j\u0010=\u00df\u00e4\u008b94\u00a7]\u00a5\u00a0M\u000exq^\"";
      int var8 = "\u00fa\u0081\u008c=\u0089\u0003\u0001\u0080\u00e4\u00aa\u00bc\u0093\u00f6O\u0097j\u0010=\u00df\u00e4\u008b94\u00a7]\u00a5\u00a0M\u000exq^\""
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
                     STRINGS_TABLE = var9;
                     BLUR_ID = Identifier.ofVanilla(STRINGS_TABLE[3]);
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

                  var6 = "\u00fa\u0081\u008c=\u0089\u0003\u0001\u0080\u00e4\u00aa\u00bc\u0093\u00f6O\u0097j\b7\u001b\u0080\u00c27\u00e2\u00f3\u00a9";
                  var8 = "\u00fa\u0081\u008c=\u0089\u0003\u0001\u0080\u00e4\u00aa\u00bc\u0093\u00f6O\u0097j\b7\u001b\u0080\u00c27\u00e2\u00f3\u00a9".length();
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

   private static Throwable identity(Throwable throwable) {
      return throwable;
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
