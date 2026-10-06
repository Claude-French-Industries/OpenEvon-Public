package club.evon.event;
import java.lang.invoke.MethodHandles;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class EntityModelRenderEvent {
   private final EntityModel w;
   private final MatrixStack N;
   private final VertexConsumer L;
   private final int p;
   private final int F;
   private boolean renderModel;
   private static final long KEY = KeyedRandom.create(-4290666306662234176L, 6635139558346916269L, MethodHandles.lookup().lookupClass()).next(98009725107128L);

   public EntityModelRenderEvent(EntityModel model, MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
      super();

      long longValue = KEY ^ 51975144001241L;
      this.renderModel = true;
      this.w = model;
      this.N = matrices;
      this.L = vertexConsumer;
      String dummyString = HudRenderEvent.getDummyString();
      this.p = light;
      String text = dummyString;
      this.F = overlay;
      if (text != null) {
         BaseModule.setStateArray(new BaseModule[5]);
      }
      }

   public EntityModel getModel() {
      return this.w;
   }

   public MatrixStack getMatrices() {
      return this.N;
   }

   public VertexConsumer getVertexConsumer() {
      return this.L;
   }

   public int getLight() {
      return this.p;
   }

   public int getOverlay() {
      return this.F;
   }

   public boolean shouldRenderModel() {
      return this.renderModel;
   }

   public void setRenderModel(boolean render) {
      this.renderModel = render;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
