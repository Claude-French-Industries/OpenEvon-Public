package club.evon.util.render;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class BoxRenderer {
   private static final RenderLayer RENDER_LAYER;
   private static final long KEY = KeyedRandom.create(1646501937973700018L, -6754565821547712736L, MethodHandles.lookup().lookupClass()).next(156114910794346L);

   public static void renderBox(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, MatrixStack stack, int color) {
      long longValue = KEY ^ 31817404369991L;
      stack.push();
      stack.translate(
         minX - ClientContext.MINECRAFT_CLIENT.gameRenderer.getCamera().getPos().x,
         minY - ClientContext.MINECRAFT_CLIENT.gameRenderer.getCamera().getPos().y,
         minZ - ClientContext.MINECRAFT_CLIENT.gameRenderer.getCamera().getPos().z
      );
      float floatValue = maxX - minX;
      float floatValue2 = maxY - minY;
      String[] strings = RenderUtil.getGuardState();
      float floatValue3 = maxZ - minZ;
      Immediate entityVertexConsumers = ClientContext.MINECRAFT_CLIENT.getBufferBuilders().getEntityVertexConsumers();
      VertexConsumer buffer = entityVertexConsumers.getBuffer(RENDER_LAYER);
      Matrix4f positionMatrix = stack.peek().getPositionMatrix();
      buffer.vertex(positionMatrix, 0.0F, 0.0F, floatValue3).color(color);
      buffer.vertex(positionMatrix, floatValue, 0.0F, floatValue3).color(color);
      buffer.vertex(positionMatrix, floatValue, 0.0F, 0.0F).color(color);
      buffer.vertex(positionMatrix, 0.0F, 0.0F, 0.0F).color(color);
      String[] strings2 = strings;
      buffer.vertex(positionMatrix, 0.0F, floatValue2, 0.0F).color(color);
      buffer.vertex(positionMatrix, floatValue, floatValue2, 0.0F).color(color);
      buffer.vertex(positionMatrix, floatValue, floatValue2, floatValue3).color(color);
      buffer.vertex(positionMatrix, 0.0F, floatValue2, floatValue3).color(color);
      buffer.vertex(positionMatrix, 0.0F, 0.0F, 0.0F).color(color);
      buffer.vertex(positionMatrix, 0.0F, 0.0F, floatValue3).color(color);
      buffer.vertex(positionMatrix, 0.0F, floatValue2, floatValue3).color(color);
      buffer.vertex(positionMatrix, 0.0F, floatValue2, 0.0F).color(color);
      buffer.vertex(positionMatrix, floatValue, 0.0F, floatValue3).color(color);
      buffer.vertex(positionMatrix, floatValue, 0.0F, 0.0F).color(color);
      buffer.vertex(positionMatrix, floatValue, floatValue2, 0.0F).color(color);
      buffer.vertex(positionMatrix, floatValue, floatValue2, floatValue3).color(color);
      buffer.vertex(positionMatrix, floatValue, 0.0F, 0.0F).color(color);
      buffer.vertex(positionMatrix, 0.0F, 0.0F, 0.0F).color(color);
      buffer.vertex(positionMatrix, 0.0F, floatValue2, 0.0F).color(color);
      buffer.vertex(positionMatrix, floatValue, floatValue2, 0.0F).color(color);
      buffer.vertex(positionMatrix, 0.0F, 0.0F, floatValue3).color(color);
      buffer.vertex(positionMatrix, floatValue, 0.0F, floatValue3).color(color);
      buffer.vertex(positionMatrix, floatValue, floatValue2, floatValue3).color(color);
      buffer.vertex(positionMatrix, 0.0F, floatValue2, floatValue3).color(color);
      entityVertexConsumers.drawCurrentLayer();
      stack.pop();
      if (strings2 != null) {
         BaseModule.setStateArray(new BaseModule[5]);
      }
   }

   static {
      try {
      long var1 = KEY ^ 45404100407792L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var0 = new String[2];
      int var8 = 0;
      String var7 = "\u009b(l\u00fc\u0004\u00ab\u00cc\u00ef&\u00ed~\u009d\u00a5\u007f\u00fa\u00b6\u008b\u00e2y\u00d4\u00fcs\u0014\u00a8\u00f7\u0094\u007f\u00eev\u00ddJ\u00b3 *\u00fe\u00cc\u00f1<\u008a\u00ae;p\u0016T\u00f4Q\u00a3\u00d9\u00ab\u0085\u001fu\\D\u00d8'\u00ea\u00f0+\u00eco\u001e\u00c1\u0090\u000b";
      int var9 = "\u009b(l\u00fc\u0004\u00ab\u00cc\u00ef&\u00ed~\u009d\u00a5\u007f\u00fa\u00b6\u008b\u00e2y\u00d4\u00fcs\u0014\u00a8\u00f7\u0094\u007f\u00eev\u00ddJ\u00b3 *\u00fe\u00cc\u00f1<\u008a\u00ae;p\u0016T\u00f4Q\u00a3\u00d9\u00ab\u0085\u001fu\\D\u00d8'\u00ea\u00f0+\u00eco\u001e\u00c1\u0090\u000b"
         .length();
      char var6 = ' ';
      int var5 = -1;

      while (true) {
         byte[] var10 = var3.doFinal(var7.substring(++var5, var5 + var6).getBytes("ISO-8859-1"));
         String var13 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var0[var8++] = var13;
         if ((var5 += var6) >= var9) {
            RENDER_LAYER = RenderLayer.of(
               var0[1],
               1024,
               false,
               true,
               RenderPipelines.register(
                  RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
                     .withLocation(Identifier.ofVanilla(var0[0]))
                     .withCull(false)
                     .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
                     .withBlend(BlendFunction.TRANSLUCENT)
                     .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                     .withDepthWrite(true)
                     .build()
               ),
               MultiPhaseParameters.builder().layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).build(false)
            );
            break;
         }

         var6 = var7.charAt(var5);
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
}
