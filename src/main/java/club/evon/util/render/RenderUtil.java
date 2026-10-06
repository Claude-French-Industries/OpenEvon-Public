package club.evon.util.render;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.awt.Color;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class RenderUtil {
   public static final long NVG_CONTEXT;
   private static final Map<Integer, Integer> imageHandleCache;
   public static final NVGPaint gameBlurPaint;
   public static final NVGPaint hudBlurPaint;
   public static boolean frameActive;
   private static final Map<LivingEntity, Integer> entityTextureCache;
   private static final Map<String, Integer> skinImageCache;
   private static String[] guardState;
   private static final long XOR_KEY = KeyedRandom.create(-3561515604486728122L, 4199685306706303859L, MethodHandles.lookup().lookupClass()).next(22514488004252L);
   private static final String[] STRINGS_TABLE;
   private static final long SHADOW_COLOR;

   public static void render(Runnable content) {
      beginFrame();
      content.run();
      endFrame(true);
   }

   public static boolean beginFrame() {
      String[] strings;
      Window window;
      label30: {
         long longValue = XOR_KEY ^ 44437098771215L;
         String[] strings2 = getGuardState();
         window = ClientContext.MINECRAFT_CLIENT.getWindow();
         strings = strings2;
         MinecraftClient minecraftClient = ClientContext.MINECRAFT_CLIENT;
         if (strings == null) {
            if (ClientContext.MINECRAFT_CLIENT.player != null) {
               break label30;
            }

            minecraftClient = ClientContext.MINECRAFT_CLIENT;
         }

         if (minecraftClient.currentScreen != null) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
         }
      }

      float floatValue = window.getScaleFactor();
      boolean flag = frameActive;
      if (strings == null) {
         if (!frameActive) {
            GlStateBackup.backup();
            NanoVG.nvgBeginFrame(NVG_CONTEXT, window.getFramebufferWidth() / floatValue, window.getFramebufferHeight() / floatValue, floatValue);
            frameActive = true;
            return true;
         }

         flag = false;
      }

      return flag;
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static void endFrame(boolean createRenderPass) {
      long longValue = XOR_KEY ^ 52949244150236L;
      String[] strings = getGuardState();
      boolean tmp = frameActive;
      if (strings == null) {
         if (!frameActive) {
            return;
         }

         tmp = createRenderPass;
      }

      label68: {
         if (tmp) {
            GpuTextureView gpuTextureView;
            Supplier tmp2;
            GpuTextureView tmp3;
            OptionalInt tmp4;
            Framebuffer framebuffer = ClientContext.MINECRAFT_CLIENT.getFramebuffer();
            CommandEncoder createCommandEncoder = RenderSystem.getDevice().createCommandEncoder();
            label65: {
               tmp2 = () -> STRINGS_TABLE[10];
               tmp3 = framebuffer.getColorAttachmentView();
               tmp4 = OptionalInt.empty();
               Framebuffer tmp5 = framebuffer;
               if (strings == null) {
                  if (!framebuffer.useDepthAttachment) {
                     gpuTextureView = null;
                     break label65;
                  }

                  tmp5 = framebuffer;
               }

               gpuTextureView = tmp5.getDepthAttachmentView();
            }

            RenderPass createRenderPass2 = createCommandEncoder.createRenderPass(tmp2, tmp3, tmp4, gpuTextureView, OptionalDouble.empty());

            try {
               createRenderPass2.setPipeline(RenderPipelines.GUI);
               NanoVG.nvgEndFrame(NVG_CONTEXT);
            } catch (Throwable throwable) {
               RenderPass renderPass = createRenderPass2;
               if (strings == null) {
                  if (createRenderPass2 == null) {
                     throw throwable;
                  }

                  try {
                     renderPass = createRenderPass2;
                  } catch (Throwable throwable2) {
                     throwable.addSuppressed(throwable2);
                     throw throwable;
                  }
               }

               try {
                  renderPass.close();
               } catch (Throwable throwable3) {
                  throwable.addSuppressed(throwable3);
               }

               throw throwable;
            }

            label58: {
               RenderPass renderPass2 = createRenderPass2;
               if (strings == null) {
                  if (createRenderPass2 == null) {
                     break label58;
                  }

                  renderPass2 = createRenderPass2;
               }

               renderPass2.close();
            }

            if (strings == null) {
               break label68;
            }
         }

         NanoVG.nvgEndFrame(NVG_CONTEXT);
      }

      GlStateBackup.restore();
      Window window = ClientContext.MINECRAFT_CLIENT.getWindow();
      frameActive = false;
   }

   public static void initImagePaint(int width, int height, int textureId, NVGPaint nvgPaint) {
      int value = NanoVGGL3.nvglCreateImageFromHandle(NVG_CONTEXT, textureId, width, height, 9);
      NanoVG.nvgImagePattern(NVG_CONTEXT, 0.0F, 0.0F, ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth(), ClientContext.MINECRAFT_CLIENT.getWindow().getScaledHeight(), 0.0F, value, 1.0F, nvgPaint);
   }

   public static void pushClip(float x, float y, float width, float height) {
      saveState();
      NanoVG.nvgIntersectScissor(NVG_CONTEXT, x, y, width, height);
   }

   public static void popClip() {
      restoreState();
   }

   public static void saveState() {
      NanoVG.nvgSave(NVG_CONTEXT);
   }

   public static void restoreState() {
      NanoVG.nvgRestore(NVG_CONTEXT);
   }

   public static void translate(float x, float y) {
      NanoVG.nvgTranslate(NVG_CONTEXT, x, y);
   }

   public static void rotate(float angle) {
      NanoVG.nvgRotate(NVG_CONTEXT, angle);
   }

   public static void scale(float x, float y) {
      NanoVG.nvgScale(NVG_CONTEXT, x, y);
   }

   public static void fillRect(float x, float y, float width, float height, Color color) {
      fillRectArgb(x, y, width, height, color.getRGB());
   }

   public static void fillRectArgb(float x, float y, float width, float height, int color) {
      NVGColor toNvgColor = toNvgColor(color);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillColor(NVG_CONTEXT, toNvgColor);
      NanoVG.nvgRect(NVG_CONTEXT, x, y, width, height);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      toNvgColor.free();
   }

   public static void fillRectPaint(float x, float y, float width, float height, NVGPaint nvgPaint) {
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillPaint(NVG_CONTEXT, nvgPaint);
      NanoVG.nvgRect(NVG_CONTEXT, x, y, width, height);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
   }

   public static void fillRectWithBlur(float x, float y, float width, float height, Color color) {
      fillRectArgb(x, y, width, height, color.getRGB());
      fillRectPaint(x, y, width, height, hudBlurPaint);
   }

   public static void fillRoundedRectWithBlur(float x, float y, float width, float height, float radius, Color color) {
      fillRoundedRectArgb(x, y, width, height, radius, color.getRGB());
      fillRoundedRectPaint(x, y, width, height, radius, hudBlurPaint);
   }

   public static void fillRoundedRectPaint(float x, float y, float width, float height, float radius, NVGPaint nvgPaint) {
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillPaint(NVG_CONTEXT, nvgPaint);
      NanoVG.nvgRoundedRect(NVG_CONTEXT, x, y, width, height, radius);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
   }

   public static void fillColorPicker(float x, float y, float width, float height, float radius, float hue) {
      fillRoundedRect(x, y, width, height, radius, Color.getHSBColor(hue, 1.0F, 1.0F));
      fillAngledGradientRect(x, y, width, height, radius, Color.getHSBColor(hue, 0.0F, 1.0F).getRGB(), ColorUtil.applyOpacity(Color.getHSBColor(hue, 0.0F, 1.0F).getRGB(), 0.0F), 0.0F);
      fillAngledGradientRect(x, y, width, height, radius, ColorUtil.applyOpacity(Color.getHSBColor(hue, 1.0F, 0.0F).getRGB(), 0.0F), Color.getHSBColor(hue, 1.0F, 0.0F).getRGB(), 90.0F);
   }

   private static void fillAngledGradientRect(float floatValue, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int startColor, int endColor, float floatValue6) {
      NVGColor toNvgColor = toNvgColor(startColor);
      NVGColor toNvgColor2 = toNvgColor(endColor);
      float floatValue7 = (float)Math.toRadians(floatValue6);
      float floatValue8 = (float)Math.cos(floatValue7);
      float floatValue9 = (float)Math.sin(floatValue7);
      NVGPaint calloc = NVGPaint.calloc();
      NanoVG.nvgLinearGradient(
         NVG_CONTEXT,
         floatValue + floatValue3 * 0.5F - floatValue8 * floatValue3 * 0.5F,
         floatValue2 + floatValue4 * 0.5F - floatValue9 * floatValue4 * 0.5F,
         floatValue + floatValue3 * 0.5F + floatValue8 * floatValue3 * 0.5F,
         floatValue2 + floatValue4 * 0.5F + floatValue9 * floatValue4 * 0.5F,
         toNvgColor,
         toNvgColor2,
         calloc
      );
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillPaint(NVG_CONTEXT, calloc);
      NanoVG.nvgRoundedRect(NVG_CONTEXT, floatValue, floatValue2, floatValue3, floatValue4, floatValue5);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      calloc.free();
      toNvgColor.free();
      toNvgColor2.free();
   }

   public static void drawSkinHead(int skinTextureGlId, float x, float y, float radius) {
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NVGPaint createImagePaint = createImagePaint(skinTextureGlId, x, y, 64, 64, 3, 0, 1);
      NanoVG.nvgFillPaint(NVG_CONTEXT, createImagePaint);
      NanoVG.nvgRoundedRect(NVG_CONTEXT, x + 3.0F, y + 3.0F, 24.0F, 24.0F, radius);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      createImagePaint.free();
   }

   public static void fillRoundedRect(float x, float y, float width, float height, float radius, Color color) {
      fillRoundedRectArgb(x, y, width, height, radius, color.getRGB());
   }

   public static void strokeRectSides(float x, float y, float width, float height, float outlineWidth, int color, boolean top, boolean bottom, boolean left, boolean right) {
      long longValue = XOR_KEY ^ 134460894908759L;
      String[] strings = getGuardState();
      NVGColor toNvgColor = toNvgColor(color);
      String[] strings2 = strings;
      NanoVG.nvgStrokeColor(NVG_CONTEXT, toNvgColor);
      NanoVG.nvgStrokeWidth(NVG_CONTEXT, outlineWidth);
      boolean flag = top;
      if (strings2 == null) {
         if (top) {
            NanoVG.nvgBeginPath(NVG_CONTEXT);
            NanoVG.nvgMoveTo(NVG_CONTEXT, x, y);
            NanoVG.nvgLineTo(NVG_CONTEXT, x + width, y);
            NanoVG.nvgStroke(NVG_CONTEXT);
            NanoVG.nvgClosePath(NVG_CONTEXT);
         }

         flag = bottom;
      }

      if (strings2 == null) {
         if (flag) {
            NanoVG.nvgBeginPath(NVG_CONTEXT);
            NanoVG.nvgMoveTo(NVG_CONTEXT, x, y + height);
            NanoVG.nvgLineTo(NVG_CONTEXT, x + width, y + height);
            NanoVG.nvgStroke(NVG_CONTEXT);
            NanoVG.nvgClosePath(NVG_CONTEXT);
         }

         flag = left;
      }

      if (strings2 == null) {
         if (flag) {
            NanoVG.nvgBeginPath(NVG_CONTEXT);
            NanoVG.nvgMoveTo(NVG_CONTEXT, x, y);
            NanoVG.nvgLineTo(NVG_CONTEXT, x, y + height);
            NanoVG.nvgStroke(NVG_CONTEXT);
            NanoVG.nvgClosePath(NVG_CONTEXT);
         }

         flag = right;
      }

      if (flag) {
         NanoVG.nvgBeginPath(NVG_CONTEXT);
         NanoVG.nvgMoveTo(NVG_CONTEXT, x + width, y);
         NanoVG.nvgLineTo(NVG_CONTEXT, x + width, y + height);
         NanoVG.nvgStroke(NVG_CONTEXT);
         NanoVG.nvgClosePath(NVG_CONTEXT);
      }

      toNvgColor.free();
   }

   public static void fillRoundedRectArgb(float x, float y, float width, float height, float radius, int color) {
      NVGColor toNvgColor = toNvgColor(color);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillColor(NVG_CONTEXT, toNvgColor);
      NanoVG.nvgRoundedRect(NVG_CONTEXT, x, y, width, height, radius);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      toNvgColor.free();
   }

   public static void strokeRoundedRectSides(
      float x, float y, float width, float height, float radius, float outlineWidth, int color, boolean top, boolean bottom, boolean left, boolean right
   ) {
      NVGColor nVGColor;
      label193: {
         long longValue = XOR_KEY ^ 63119363042783L;
         String[] strings = getGuardState();
         nVGColor = toNvgColor(color);
         NanoVG.nvgStrokeColor(NVG_CONTEXT, nVGColor);
         NanoVG.nvgStrokeWidth(NVG_CONTEXT, outlineWidth);
         String[] strings2 = strings;
         float floatValue = Math.min(Math.min(radius, width / 2.0F), height / 2.0F);
         float floatValue2;
         int value = (floatValue2 = floatValue - 0.0F) == 0.0F ? 0 : (floatValue2 < 0.0F ? -1 : 1);
         if (strings2 == null) {
            if (value <= 0) {
               boolean flag = top;
               if (strings2 == null) {
                  if (top) {
                     drawLine(x, y, x + width, y);
                  }

                  flag = bottom;
               }

               if (strings2 == null) {
                  if (flag) {
                     drawLine(x, y + height, x + width, y + height);
                  }

                  flag = left;
               }

               if (strings2 == null) {
                  if (flag) {
                     drawLine(x, y, x, y + height);
                  }

                  flag = right;
               }

               if (!flag) {
                  break label193;
               }

               drawLine(x + width, y, x + width, y + height);
               if (strings2 == null) {
                  break label193;
               }
            }

            NanoVG.nvgBeginPath(NVG_CONTEXT);
            value = top ? 1 : 0;
         }

         if (strings2 == null) {
            if (value != 0) {
               NanoVG.nvgMoveTo(NVG_CONTEXT, x + floatValue, y);
               NanoVG.nvgLineTo(NVG_CONTEXT, x + width - floatValue, y);
            }

            value = right ? 1 : 0;
         }

         if (strings2 == null) {
            if (value != 0) {
               NanoVG.nvgMoveTo(NVG_CONTEXT, x + width, y + floatValue);
               NanoVG.nvgLineTo(NVG_CONTEXT, x + width, y + height - floatValue);
            }

            value = bottom ? 1 : 0;
         }

         if (strings2 == null) {
            if (value != 0) {
               NanoVG.nvgMoveTo(NVG_CONTEXT, x + width - floatValue, y + height);
               NanoVG.nvgLineTo(NVG_CONTEXT, x + floatValue, y + height);
            }

            value = left ? 1 : 0;
         }

         if (strings2 == null) {
            if (value != 0) {
               NanoVG.nvgMoveTo(NVG_CONTEXT, x, y + height - floatValue);
               NanoVG.nvgLineTo(NVG_CONTEXT, x, y + floatValue);
            }

            value = top ? 1 : 0;
         }

         label163:
         if (strings2 == null) {
            if (value != 0) {
               value = left ? 1 : 0;
               if (strings2 != null) {
                  break label163;
               }

               if (left) {
                  strokeArc(NVG_CONTEXT, x + floatValue, y + floatValue, floatValue, (float) Math.PI, (float) (Math.PI * 3.0 / 2.0));
               }
            }

            value = top ? 1 : 0;
         }

         label155:
         if (strings2 == null) {
            if (value != 0) {
               value = right ? 1 : 0;
               if (strings2 != null) {
                  break label155;
               }

               if (right) {
                  strokeArc(NVG_CONTEXT, x + width - floatValue, y + floatValue, floatValue, (float) (Math.PI * 3.0 / 2.0), 0.0F);
               }
            }

            value = bottom ? 1 : 0;
         }

         label147:
         if (strings2 == null) {
            if (value != 0) {
               value = right ? 1 : 0;
               if (strings2 != null) {
                  break label147;
               }

               if (right) {
                  strokeArc(NVG_CONTEXT, x + width - floatValue, y + height - floatValue, floatValue, 0.0F, (float) (Math.PI / 2));
               }
            }

            value = bottom ? 1 : 0;
         }

         label140: {
            if (strings2 == null) {
               if (value == 0) {
                  break label140;
               }

               value = left ? 1 : 0;
            }

            if (value != 0) {
               strokeArc(NVG_CONTEXT, x + floatValue, y + height - floatValue, floatValue, (float) (Math.PI / 2), (float) Math.PI);
            }
         }

         NanoVG.nvgStroke(NVG_CONTEXT);
         NanoVG.nvgClosePath(NVG_CONTEXT);
      }

      nVGColor.free();
   }

   private static void drawLine(float x1, float y1, float x2, float y2) {
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgMoveTo(NVG_CONTEXT, x1, y1);
      NanoVG.nvgLineTo(NVG_CONTEXT, x2, y2);
      NanoVG.nvgStroke(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
   }

   private static void strokeArc(long context, float floatValue, float floatValue2, float floatValue3, float startAngle, float endAngle) {
      NanoVG.nvgBeginPath(context);
      NanoVG.nvgArc(context, floatValue, floatValue2, floatValue3, startAngle, endAngle, 2);
      NanoVG.nvgStroke(context);
      NanoVG.nvgClosePath(context);
   }

   public static void fillRoundedRectVarying(float x, float y, float width, float height, float radTopLeft, float radTopRight, float radBottomRight, float radBottomLeft, int color) {
      NVGColor toNvgColor = toNvgColor(color);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillColor(NVG_CONTEXT, toNvgColor);
      NanoVG.nvgRoundedRectVarying(NVG_CONTEXT, x, y, width, height, radTopLeft, radTopRight, radBottomRight, radBottomLeft);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      toNvgColor.free();
   }

   public static void fillRoundedRectVaryingPaint(
      float x, float y, float width, float height, float radTopLeft, float radTopRight, float radBottomRight, float radBottomLeft, NVGPaint nvgPaint
   ) {
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillPaint(NVG_CONTEXT, nvgPaint);
      NanoVG.nvgRoundedRectVarying(NVG_CONTEXT, x, y, width, height, radTopLeft, radTopRight, radBottomRight, radBottomLeft);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
   }

   public static void drawImage(float x, float y, float width, float height, Color color, NvgImage texture) {
      long longValue = XOR_KEY ^ 54800165292674L;
      NVGPaint calloc = NVGPaint.calloc();
      String[] strings = getGuardState();
      int value = texture.getHandle();
      String[] strings2 = strings;
      NanoVG.nvgImagePattern(NVG_CONTEXT, x, y, width, height, 0.0F, value, 1.0F, calloc);
      if (strings2 == null) {
         if (color != null) {
            NVGColor toNvgColor = toNvgColor(color.getRGB());
            calloc.innerColor(toNvgColor);
            toNvgColor.free();
         }

         NanoVG.nvgBeginPath(NVG_CONTEXT);
         NanoVG.nvgRect(NVG_CONTEXT, x, y, width, height);
         NanoVG.nvgFillPaint(NVG_CONTEXT, calloc);
         NanoVG.nvgFill(NVG_CONTEXT);
         calloc.free();
      }
   }

   public static void drawRoundedImage(float x, float y, float width, float height, float radius, Color color, NvgImage texture) {
      long longValue = XOR_KEY ^ 32759097060571L;
      saveState();
      translate(x, y);
      String[] strings = getGuardState();
      NVGPaint calloc = NVGPaint.calloc();
      String[] strings2 = strings;
      int value = texture.getHandle();
      NanoVG.nvgImagePattern(NVG_CONTEXT, 0.0F, 0.0F, width, height, 0.0F, value, 1.0F, calloc);
      if (strings2 == null) {
         if (color != null) {
            NVGColor toNvgColor = toNvgColor(color.getRGB());
            calloc.innerColor(toNvgColor);
            toNvgColor.free();
         }

         NanoVG.nvgBeginPath(NVG_CONTEXT);
         NanoVG.nvgRoundedRect(NVG_CONTEXT, 0.0F, 0.0F, width, height, radius);
         NanoVG.nvgFillPaint(NVG_CONTEXT, calloc);
         NanoVG.nvgFill(NVG_CONTEXT);
         calloc.free();
         restoreState();
      }
   }

   public static void drawRoundedImageRegion(
      float x, float y, float width, float height, float radius, float screenX, float screenY, float screenWidth, float screenHeight, Color color, NvgImage texture
   ) {
      long longValue = XOR_KEY ^ 81397318601390L;
      String[] strings = getGuardState();
      NVGPaint calloc = NVGPaint.calloc();
      String[] strings2 = strings;
      int value = texture.getHandle();
      NanoVG.nvgImagePattern(NVG_CONTEXT, screenX, screenY, screenWidth, screenHeight, 0.0F, value, 1.0F, calloc);
      if (strings2 == null) {
         if (color != null) {
            NVGColor toNvgColor = toNvgColor(color.getRGB());
            calloc.innerColor(toNvgColor);
            toNvgColor.free();
         }

         NanoVG.nvgBeginPath(NVG_CONTEXT);
         NanoVG.nvgRoundedRect(NVG_CONTEXT, x, y, width, height, radius);
         NanoVG.nvgFillPaint(NVG_CONTEXT, calloc);
         NanoVG.nvgFill(NVG_CONTEXT);
         calloc.free();
      }
   }

   public static void fillGradientRect(float x, float y, float width, float height, int color1, int color2, boolean horizontal) {
      long longValue = XOR_KEY ^ 49637142061131L;
      NVGPaint calloc = NVGPaint.calloc();
      NVGColor toNvgColor = toNvgColor(color1);
      NVGColor toNvgColor2 = toNvgColor(color2);
      NanoVG.nvgLinearGradient(NVG_CONTEXT, x, y, x + (horizontal ? width : 0.0F), y + height, toNvgColor, toNvgColor2, calloc);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillPaint(NVG_CONTEXT, calloc);
      NanoVG.nvgRect(NVG_CONTEXT, x, y, width, height);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      toNvgColor.free();
      toNvgColor2.free();
      calloc.free();
   }

   public static void fillRoundedGradientRect(float x, float y, float width, float height, float radius, Color color1, Color color2, boolean horizontal) {
      long longValue = XOR_KEY ^ 38757461443416L;
      NVGPaint calloc = NVGPaint.calloc();
      NVGColor toNvgColor = toNvgColor(color1.getRGB());
      NVGColor toNvgColor2 = toNvgColor(color2.getRGB());
      NanoVG.nvgLinearGradient(NVG_CONTEXT, x, y, x + (horizontal ? width : 0.0F), y + height, toNvgColor, toNvgColor2, calloc);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillPaint(NVG_CONTEXT, calloc);
      NanoVG.nvgRoundedRect(NVG_CONTEXT, x, y, width, height, radius);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      toNvgColor.free();
      toNvgColor2.free();
      calloc.free();
   }

   public static void fillShadowedGradientRect(float x, float y, float width, float height, float radius, Color color1, Color color2) {
      long longValue = XOR_KEY ^ 12835316002907L;
      fillRoundedGradientRect(x, y, width, height, radius, color1, color2, true);
      fillRoundedGradientRect(x, y, width, height, radius, new Color(0, 0, 0, 0), ColorUtil.withAlpha(new Color((int)SHADOW_COLOR), 0.6F), false);
   }

   public static void strokeRoundedRect(float x, float y, float width, float height, float radius, float outlineWidth, Color color) {
      strokeRoundedRectArgb(x, y, width, height, radius, outlineWidth, color.getRGB());
   }

   public static void strokeRoundedRectArgb(float x, float y, float width, float height, float radius, float outlineWidth, int color) {
      NVGColor toNvgColor = toNvgColor(color);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgStrokeColor(NVG_CONTEXT, toNvgColor);
      NanoVG.nvgStrokeWidth(NVG_CONTEXT, outlineWidth);
      NanoVG.nvgRoundedRect(NVG_CONTEXT, x, y, width, height, radius);
      NanoVG.nvgStroke(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      toNvgColor.free();
   }

   public static void strokeHoverOutline(float x, float y, float width, float height, float radius, float outlineWidth, Color baseColor, float mouseX) {
      long longValue = XOR_KEY ^ 53526902169294L;
      float floatValue = 0.2F;
      float floatValue2 = width * floatValue;
      float floatValue3 = Math.max(x, Math.min(mouseX, x + width));
      NVGColor toNvgColor = toNvgColor(baseColor.getRGB());
      NVGColor nVGColor = NVGColor.calloc().r(0.0F).g(0.0F).b(0.0F).a(0.0F);
      NVGPaint calloc = NVGPaint.calloc();
      NanoVG.nvgLinearGradient(NVG_CONTEXT, floatValue3 - floatValue2, y, floatValue3, y, nVGColor, toNvgColor, calloc);
      NVGPaint calloc2 = NVGPaint.calloc();
      NanoVG.nvgLinearGradient(NVG_CONTEXT, floatValue3, y, floatValue3 + floatValue2, y, toNvgColor, nVGColor, calloc2);
      NanoVG.nvgScissor(NVG_CONTEXT, x, y, floatValue3 - x, height);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgRoundedRect(NVG_CONTEXT, x + outlineWidth / 2.0F, y + outlineWidth / 2.0F, width - outlineWidth, height - outlineWidth, radius);
      NanoVG.nvgStrokePaint(NVG_CONTEXT, calloc);
      NanoVG.nvgStrokeWidth(NVG_CONTEXT, outlineWidth);
      NanoVG.nvgStroke(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      NanoVG.nvgScissor(NVG_CONTEXT, floatValue3, y, x + width - floatValue3, height);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgRoundedRect(NVG_CONTEXT, x + outlineWidth / 2.0F, y + outlineWidth / 2.0F, width - outlineWidth, height - outlineWidth, radius);
      NanoVG.nvgStrokePaint(NVG_CONTEXT, calloc2);
      NanoVG.nvgStrokeWidth(NVG_CONTEXT, outlineWidth);
      getGuardState();
      NanoVG.nvgStroke(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      NanoVG.nvgResetScissor(NVG_CONTEXT);
      toNvgColor.free();
      nVGColor.free();
      calloc.free();
      calloc2.free();
      if (BaseModule.getStateArray() == null) {
         setGuardState(new String[4]);
      }
   }

   public static void fillCircle(float x, float y, float radius, int color) {
      NVGColor toNvgColor = toNvgColor(color);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgFillColor(NVG_CONTEXT, toNvgColor);
      NanoVG.nvgCircle(NVG_CONTEXT, x, y, radius);
      NanoVG.nvgFill(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      toNvgColor.free();
   }

   public static void strokeCircle(float x, float y, float radius, float strokeWidth, int color) {
      NVGColor toNvgColor = toNvgColor(color);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgStrokeColor(NVG_CONTEXT, toNvgColor);
      NanoVG.nvgStrokeWidth(NVG_CONTEXT, strokeWidth);
      NanoVG.nvgCircle(NVG_CONTEXT, x, y, radius);
      NanoVG.nvgStroke(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      toNvgColor.free();
   }

   public static void strokeCircleOutline(float x, float y, float radius, float outlineWidth, int color) {
      NVGColor toNvgColor = toNvgColor(color);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgStrokeColor(NVG_CONTEXT, toNvgColor);
      NanoVG.nvgStrokeWidth(NVG_CONTEXT, outlineWidth);
      NanoVG.nvgCircle(NVG_CONTEXT, x, y, radius);
      NanoVG.nvgStroke(NVG_CONTEXT);
      NanoVG.nvgClosePath(NVG_CONTEXT);
      toNvgColor.free();
   }

   public static void drawEntityHead(float x, float y, float size, float radius, Color color, LivingEntity entity) {
      long longValue = XOR_KEY ^ 113543140937444L;
      String[] strings = getGuardState();
      int value = getEntityTextureId(entity);
      String[] strings2 = strings;
      int value2 = value;
      if (strings2 == null) {
         if (value == -1) {
            return;
         }

         value2 = value;
      }

      NVGPaint createImagePaint = createImagePaint(value2, x - size, y - size, (int)(size * 8.0F), (int)(size * 8.0F), 1, 0, 1);
      NanoVG.nvgBeginPath(NVG_CONTEXT);
      NanoVG.nvgRoundedRect(NVG_CONTEXT, x, y, size, size, radius);
      NanoVG.nvgFillPaint(NVG_CONTEXT, createImagePaint);
      NanoVG.nvgFill(NVG_CONTEXT);
      createImagePaint.free();
      NanoVG.nvgBeginPath(NVG_CONTEXT);
   }

   public static void drawPlayerHead(float x, float y, float size, float radius, Color color, String uuid) {
      long longValue = XOR_KEY ^ 93125290720357L;
      String[] strings = getGuardState();
      int value = fetchSkinImageId(uuid);
      String[] strings2 = strings;
      int value2 = value;
      if (strings2 == null) {
         if (value == -1) {
            return;
         }

         value2 = value;
      }

      NVGPaint createImagePaint = createImagePaint(value2, x - size, y - size, (int)(size * 8.0F), (int)(size * 8.0F), 1, 0, 1);
      NanoVG.nvgImagePattern(NVG_CONTEXT, x - size, y - size, size * 8.0F, size * 8.0F, 0.0F, value, 1.0F, createImagePaint);
      if (strings2 == null) {
         if (color != null) {
            NVGColor toNvgColor = toNvgColor(color.getRGB());
            createImagePaint.innerColor(toNvgColor);
            toNvgColor.free();
         }

         NanoVG.nvgBeginPath(NVG_CONTEXT);
         NanoVG.nvgRoundedRect(NVG_CONTEXT, x, y, size, size, radius);
         NanoVG.nvgFillPaint(NVG_CONTEXT, createImagePaint);
         NanoVG.nvgFill(NVG_CONTEXT);
         createImagePaint.free();
         NanoVG.nvgClosePath(NVG_CONTEXT);
      }
   }

   private static int getEntityTextureId(LivingEntity entity) {
      return entityTextureCache.computeIfAbsent(entity, entity2 -> {
         Identifier identifier;
         label24: {
            long longValue = XOR_KEY ^ 70894259978829L;
            String[] strings = getGuardState();
            Object object = entity;
            String[] strings2 = strings;
            if (strings2 == null) {
               switch (object) {
                  case AbstractClientPlayerEntity player:
                     identifier = player.getSkin().body().texturePath();
                     break label24;
                  case SkeletonEntity entity3:
                     String[] strings3 = STRINGS_TABLE;
                     identifier = Identifier.ofVanilla(strings3[0]);
                     break label24;
                  case ZombieEntity entity4:
                     String[] strings4 = STRINGS_TABLE;
                     identifier = Identifier.ofVanilla(strings4[4]);
                     break label24;
                  case CreeperEntity entity5:
                     String[] strings5 = STRINGS_TABLE;
                     identifier = Identifier.ofVanilla(strings5[5]);
                     break label24;
                  case PiglinEntity entity6:
                     identifier = Identifier.ofVanilla(STRINGS_TABLE[9]);
                     break label24;
                  default:
                     identifier = null;
                     break label24;
               }
            }

            AbstractClientPlayerEntity player2 = (AbstractClientPlayerEntity)object;
            identifier = player2.getSkin().body().texturePath();
         }

         Identifier identifier2 = identifier;
         return identifier2 == null ? -1 : Integer.parseInt(ClientContext.MINECRAFT_CLIENT.getTextureManager().getTexture(identifier2).getGlTexture().getLabel());
      });
   }

   private static int fetchSkinImageId(String text) {
      return skinImageCache.computeIfAbsent(text, string -> {
         try {
            String[] strings = STRINGS_TABLE;
            URL uRL = new URL(strings[8] + text);
            JsonObject asJsonObject = JsonParser.parseReader(new InputStreamReader(uRL.openStream())).getAsJsonObject();
            JsonArray asJsonArray = asJsonObject.getAsJsonArray(strings[1]);
            JsonObject asJsonObject2 = asJsonArray.get(0).getAsJsonObject();
            String asString = asJsonObject2.get(strings[3]).getAsString();
            String string2 = new String(Base64.getDecoder().decode(asString));
            JsonObject asJsonObject3 = JsonParser.parseString(string2).getAsJsonObject();
            String asString2 = asJsonObject3.getAsJsonObject(strings[7]).getAsJsonObject(strings[2]).get(strings[6]).getAsString();
            NvgImage nvgImage = new NvgImage(new URL(asString2).openStream());
            return nvgImage.getHandle();
         } catch (Exception exception) {
            return -1;
         }
      });
   }

   public static NVGPaint createImagePaint(int textureId, float x, float y, int textureWidth, int textureHeight, int scale, int angle, int opacity) {
      NVGPaint calloc = NVGPaint.calloc();
      int value = getOrCreateImageId(textureId, textureWidth, textureHeight, 0);
      float floatValue = x - (float)textureWidth / scale;
      float floatValue2 = y - (float)textureHeight / scale;
      NanoVG.nvgImagePattern(NVG_CONTEXT, floatValue, floatValue2, textureWidth * scale, textureHeight * scale, angle, value, opacity, calloc);
      return calloc;
   }

   private static int getOrCreateImageId(int textureId, int value, int value2, int flags) {
      return imageHandleCache.computeIfAbsent(textureId, var3x -> NanoVGGL3.nvglCreateImageFromHandle(NVG_CONTEXT, var3x, value, value2, flags));
   }

   public static NVGColor toNvgColor(int color) {
      int[] values = ColorUtil.extractRgba(color);
      return NVGColor.calloc().r(values[0] / 255.0F).g(values[1] / 255.0F).b(values[2] / 255.0F).a(values[3] / 255.0F);
   }

   static {
      try {
      long var5 = XOR_KEY ^ 3706077032282L;
      setGuardState(null);
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[11];
      int var12 = 0;
      String var11 = "\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u00b9\u00eaY>;\u0092r\u00eavy\u00c4\t\u00fb\u00da&\u00e8N\u00db\u001a\u00b4\u0084.\u0013\u00ef\u000b\u0088Z\u001c\u00fe\u00fb\u001c?\u0010\u00dd:}XE\u009aiJ\u009a\u00b8B\u001c\u0096\u00de\u00d7\u009f\b\u0095\u008f*\u00f7l&\u00f7\u009c\b\u008bN\u00d6D\u00cb\n0\u0093(\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u00b9\u00eaY>;\u0092r\u00ea2V\u00e3\u00ce%\u009f\u00b9\u00ee\u00f0\u00ed\u0081\u0088\u0015;\u009ciF\u009dWT\u00e3\u00c1\u00dd\u009b(\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u00b9\u00eaY>;\u0092r\u00ea'\u00c6\u008f*\u008f\u00f8\u001e\u001e\u00a2\u00f7U\u00df\u0093\u00bc+`\u00141\u0097\u00b6\u00e3\u00f3$\u00c4\b\u00ae\u00a8TR\u00b3\u008a\u00c3\u0002\u0010\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u0085\u00e5\u0017\u00a7Z\u00d8>m@\u00b1\u00e7\u00a8\u00d1IG}\u0096\u00be(\u00fb\u0095\u00c3\u00da\u00d3\u00eb\u0005C\u001b\u00bb\u00cc\u00ab\u0016\u00b2j\u00b6\u00e02\u00df\u00ad\u00ee\u008fD\u008b\u0094\u00e7y\u00f5L\u00ae\u00b1\u009eT:JG\u00bf\u00f4\u0019\u00a8\u00d9{\u009c\u0013)\u0003\u00be$9\u00c9l\u001f.\u0013";
      int var13 = "\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u00b9\u00eaY>;\u0092r\u00eavy\u00c4\t\u00fb\u00da&\u00e8N\u00db\u001a\u00b4\u0084.\u0013\u00ef\u000b\u0088Z\u001c\u00fe\u00fb\u001c?\u0010\u00dd:}XE\u009aiJ\u009a\u00b8B\u001c\u0096\u00de\u00d7\u009f\b\u0095\u008f*\u00f7l&\u00f7\u009c\b\u008bN\u00d6D\u00cb\n0\u0093(\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u00b9\u00eaY>;\u0092r\u00ea2V\u00e3\u00ce%\u009f\u00b9\u00ee\u00f0\u00ed\u0081\u0088\u0015;\u009ciF\u009dWT\u00e3\u00c1\u00dd\u009b(\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u00b9\u00eaY>;\u0092r\u00ea'\u00c6\u008f*\u008f\u00f8\u001e\u001e\u00a2\u00f7U\u00df\u0093\u00bc+`\u00141\u0097\u00b6\u00e3\u00f3$\u00c4\b\u00ae\u00a8TR\u00b3\u008a\u00c3\u0002\u0010\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u0085\u00e5\u0017\u00a7Z\u00d8>m@\u00b1\u00e7\u00a8\u00d1IG}\u0096\u00be(\u00fb\u0095\u00c3\u00da\u00d3\u00eb\u0005C\u001b\u00bb\u00cc\u00ab\u0016\u00b2j\u00b6\u00e02\u00df\u00ad\u00ee\u008fD\u008b\u0094\u00e7y\u00f5L\u00ae\u00b1\u009eT:JG\u00bf\u00f4\u0019\u00a8\u00d9{\u009c\u0013)\u0003\u00be$9\u00c9l\u001f.\u0013"
         .length();
      char var10 = '(';
      int var17 = -1;

      label37:
      while (true) {
         String var18 = var11.substring(++var17, var17 + var10);
         byte var10001 = -1;

         while (true) {
            byte[] var15 = var7.doFinal(var18.getBytes("ISO-8859-1"));
            String var26 = decodeString(var15).intern();
            switch (var10001) {
               case 0:
                  var14[var12++] = var26;
                  if ((var17 += var10) >= var13) {
                     STRINGS_TABLE = var14;
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long var2 = 3367236419532047901L;
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
                     long var30 = (var4[0] & 255L) << 56
                        | (var4[1] & 255L) << 48
                        | (var4[2] & 255L) << 40
                        | (var4[3] & 255L) << 32
                        | (var4[4] & 255L) << 24
                        | (var4[5] & 255L) << 16
                        | (var4[6] & 255L) << 8
                        | var4[7] & 255L;
                     var10001 = -1;
                     SHADOW_COLOR = var30;
                     NVG_CONTEXT = NanoVGGL3.nvgCreate(3);
                     imageHandleCache = new HashMap<>();
                     gameBlurPaint = NVGPaint.create();
                     hudBlurPaint = NVGPaint.create();
                     frameActive = false;
                     entityTextureCache = new HashMap<>();
                     skinImageCache = new HashMap<>();
                     break label37;
                  }

                  var10 = var11.charAt(var17);
                  break;
               default:
                  var14[var12++] = var26;
                  if ((var17 += var10) < var13) {
                     var10 = var11.charAt(var17);
                     continue label37;
                  }

                  var11 = "\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u00b9\u00eaY>;\u0092r\u00eaeF\u00fe\u00a1i\u00cac\\\u00ba\u008c\u00f7Cx\u00a4\u00b33\u00bbf\u00aa\u0083_\u00d92q\u0010\u00a0E\u00f0\u0085\u00d0\u00ff\u00f2\u0093\u009bD\u00e4\u0098\u001aX\u00f0\u00e7";
                  var13 = "\u00a2\u0015~\u00bf\u00b4\u00b5\u00dap\u00b9\u00eaY>;\u0092r\u00eaeF\u00fe\u00a1i\u00cac\\\u00ba\u008c\u00f7Cx\u00a4\u00b33\u00bbf\u00aa\u0083_\u00d92q\u0010\u00a0E\u00f0\u0085\u00d0\u00ff\u00f2\u0093\u009bD\u00e4\u0098\u001aX\u00f0\u00e7"
                     .length();
                  var10 = '(';
                  var17 = -1;
            }

            var18 = var11.substring(++var17, var17 + var10);
            var10001 = 0;
         }
      }
      } catch (Exception varE) {
         throw new ExceptionInInitializerError(varE);
      }
   }

   public static void setGuardState(String[] state) {
      guardState = state;
   }

   public static String[] getGuardState() {
      return guardState;
   }

   private static Throwable a(Throwable throwable) {
      return throwable;
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
}
