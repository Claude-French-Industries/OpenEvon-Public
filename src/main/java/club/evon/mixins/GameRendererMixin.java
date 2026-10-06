/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicates
 *  com.llamalad7.mixinextras.sugar.Local
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gui.CubeMapRenderer
 *  net.minecraft.client.gui.RotatingCubeMapRenderer
 *  net.minecraft.client.render.BufferBuilderStorage
 *  net.minecraft.client.render.Camera
 *  net.minecraft.client.render.GameRenderer
 *  net.minecraft.client.render.RenderTickCounter
 *  net.minecraft.client.render.item.HeldItemRenderer
 *  net.minecraft.client.util.math.MatrixStack
 *  net.minecraft.entity.Entity
 *  net.minecraft.resource.ResourceManager
 *  net.minecraft.util.Identifier
 *  net.minecraft.util.hit.EntityHitResult
 *  net.minecraft.util.hit.HitResult
 *  net.minecraft.util.hit.HitResult$Type
 *  net.minecraft.util.math.Vec3d
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.json.JSONException
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Mutable
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package club.evon.mixins;
import com.google.common.base.Predicates;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.Predicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.event.EventBus;
import club.evon.event.RenderWorldEvent;
import club.evon.event.WindowResizeEvent;
import club.evon.module.impl.combat.KillAura;
import club.evon.util.RaycastUtil;
import club.evon.util.render.BlurRenderer;
import club.evon.util.render.ProjectionUtil;




@Mixin(value={GameRenderer.class})
public abstract class GameRendererMixin {
    @Mutable
    @Shadow
    @Final
    public HeldItemRenderer firstPersonRenderer;
    @Shadow
    @Final
    @Mutable
    private final BufferBuilderStorage buffers;
    @Shadow
    @Final
    @Mutable
    private final MinecraftClient client;
    @Shadow
    @Final
    private Camera camera;
    @Unique
    private boolean passThroughBlocks;
    @Mutable
    @Shadow
    @Final
    public CubeMapRenderer panoramaRenderer;
    @Mutable
    @Shadow
    @Final
    public RotatingCubeMapRenderer rotatingPanoramaRenderer;
    private static final String a;

    @Shadow
    public abstract void updateCrosshairTarget(float floatValue);

    @Inject(method={"<init>(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/render/item/HeldItemRenderer;Lnet/minecraft/client/render/BufferBuilderStorage;Lnet/minecraft/client/render/block/BlockRenderManager;)V"}, at={@At(value="RETURN")})
    private void replaceCubeMapRenderer(CallbackInfo callbackInfo) {
        this.panoramaRenderer = new CubeMapRenderer(Identifier.of((String)a));
        this.rotatingPanoramaRenderer = new RotatingCubeMapRenderer(this.panoramaRenderer);
    }

    public GameRendererMixin(MinecraftClient client, HeldItemRenderer heldItemRenderer, ResourceManager resourceManager, BufferBuilderStorage buffers) {
        this.client = client;
        this.firstPersonRenderer = heldItemRenderer;
        this.buffers = buffers;
    }

    @Redirect(method={"findCrosshairTarget(Lnet/minecraft/entity/Entity;DDF)Lnet/minecraft/util/hit/HitResult;"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/hit/HitResult;getType()Lnet/minecraft/util/hit/HitResult$Type;"))
    private HitResult.Type redirectBlockHitResultType(HitResult hitResult) {
        if (this.passThroughBlocks) {
            this.passThroughBlocks = false;
            return HitResult.Type.MISS;
        }
        return hitResult.getType();
    }

    @Redirect(method={"findCrosshairTarget(Lnet/minecraft/entity/Entity;DDF)Lnet/minecraft/util/hit/HitResult;"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/math/Vec3d;squaredDistanceTo(Lnet/minecraft/util/math/Vec3d;)D", ordinal=0))
    private double redirectPassedThroughBlockDistance(Vec3d vec3d, Vec3d vec3d2, @Local(ordinal=1, argsOnly=true) double d, @Local(argsOnly=true) float f) {
        EntityHitResult entityHitResult;
        KillAura gI2 = EvonClient.getInstance().getModuleManager().getModuleByClass(KillAura.class);
        if (gI2.isEnabled() && gI2.isThroughWalls() && (entityHitResult = RaycastUtil.raycastEntityFromPlayer(d, f, ClientContext.MINECRAFT_CLIENT.player.getYaw(), ClientContext.MINECRAFT_CLIENT.player.getPitch(), Predicates.alwaysTrue())) != null && entityHitResult.getType() == HitResult.Type.ENTITY) {
            this.passThroughBlocks = true;
            return Double.MAX_VALUE;
        }
        return vec3d.squaredDistanceTo(vec3d2);
    }

    @Inject(method={"onResized(II)V"}, at={@At(value="HEAD")})
    private void onResized(int n, int n2, CallbackInfo callbackInfo) {
        EventBus.post(new WindowResizeEvent(n, n2));
        BlurRenderer.resize(n, n2);
    }

    @Inject(method={"render(Lnet/minecraft/client/render/RenderTickCounter;Z)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/screen/Screen;renderWithTooltip(Lnet/minecraft/client/gui/DrawContext;IIF)V")}, cancellable=true)
    private void onRenderScreen(RenderTickCounter renderTickCounter, boolean bl, CallbackInfo callbackInfo) {
    }

    @Inject(method={"renderWorld(Lnet/minecraft/client/render/RenderTickCounter;)V"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/systems/RenderSystem;setProjectionMatrix(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lcom/mojang/blaze3d/systems/ProjectionType;)V", ordinal=1, shift=At.Shift.BEFORE)})
    private void injectRenderWorld(RenderTickCounter renderTickCounter, CallbackInfo callbackInfo, @Local(ordinal=0) Matrix4f matrix4f, @Local(ordinal=1) Matrix4f matrix4f2) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.multiplyPositionMatrix((Matrix4fc)matrix4f2);
        EventBus.post(new RenderWorldEvent(renderTickCounter.getTickProgress(false), matrixStack));
        ProjectionUtil.projectionMatrix.set((Matrix4fc)matrix4f);
        ProjectionUtil.modelViewMatrix.set((Matrix4fc)RenderSystem.getModelViewMatrix());
        ProjectionUtil.positionMatrix.set((Matrix4fc)matrixStack.peek().getPositionMatrix());
    }

    private static JSONException a(JSONException jSONException) {
        return jSONException;
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {
        char[] cArray;
        block12: {
            int n;
            char[] cArray2;
            int n2;
            int n3;
            block11: {
                char[] cArray3 = "g(4&+0#l1))|!mr?5'c!/c".toCharArray();
                int n4 = cArray3.length;
                n3 = 0;
                n2 = 15;
                cArray2 = cArray3;
                n = n4;
                if (n4 <= 1) break block11;
                cArray = cArray2;
                n = n;
                if (n <= n3) break block12;
            }
            do {
                int n5 = n2;
                cArray2 = cArray2;
                char[] cArray4 = cArray2;
                int n6 = n2;
                int n7 = n3;
                while (true) {
                    cArray4[n7] = (char)(cArray4[n7] ^ (n6 ^ (switch (n3 % 7) {
                        case 0 -> 13;
                        case 1 -> 81;
                        case 2 -> 84;
                        case 3 -> 71;
                        case 4 -> 30;
                        case 5 -> 79;
                        default -> 77;
                    })));
                    ++n3;
                    n2 = n5;
                    if (n5 != 0) break;
                    n5 = n2;
                    cArray2 = cArray2;
                    n7 = n2;
                    cArray4 = cArray2;
                    n6 = n2;
                }
                cArray = cArray2;
                n = n;
            } while (n > n3);
        }
        a = new String(cArray).intern();
    }
}
