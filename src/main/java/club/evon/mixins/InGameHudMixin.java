/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderPass
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.gl.RenderPipelines
 *  net.minecraft.client.gui.DrawContext
 *  net.minecraft.client.gui.hud.InGameHud
 *  net.minecraft.client.render.RenderTickCounter
 *  net.minecraft.client.util.Window
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.entity.player.PlayerInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ShieldItem
 *  net.minecraft.scoreboard.ScoreboardObjective
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package club.evon.mixins;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.SlotSwitchMode;
import club.evon.event.EventBus;
import club.evon.event.HudRenderEvent;
import club.evon.event.HudRenderPassEvent;
import club.evon.event.HudRenderStartEvent;
import club.evon.event.Render2DEvent;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.impl.render.Animations;
import club.evon.module.impl.render.ScoreboardModule;
import club.evon.util.render.BlurRenderer;
import club.evon.util.render.NvgFont;
import club.evon.util.render.RenderUtil;




@Mixin(value={InGameHud.class})
public abstract class InGameHudMixin {
    @Shadow
    private int titleRemainTicks;
    private static final String a;

    @Redirect(method={"tick()V"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerInventory;getSelectedStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack getMainHandStack(PlayerInventory playerInventory) {
        SilentSlotManager vD2 = SilentSlotManager.getInstance();
        if ((vD2.isActive() || vD2.getSwitchTick() > -1) && vD2.getMode() == SlotSwitchMode.FULL) {
            return vD2.getMainHandStack(ClientContext.MINECRAFT_CLIENT.player);
        }
        return playerInventory.getSelectedStack();
    }

    @Redirect(method={"renderHotbar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerInventory;getSelectedSlot()I"))
    private int onRenderHotbarSlot(PlayerInventory playerInventory) {
        return SilentSlotManager.getInstance().getVisualSelectedSlot(playerInventory);
    }

    @Inject(method={"render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"}, at={@At(value="HEAD")})
    private void render(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        float f = renderTickCounter.getTickProgress(false);
        EventBus.post(new HudRenderStartEvent(drawContext, f));
        this.applyPostProcessing(drawContext, f);
        Window window = ClientContext.MINECRAFT_CLIENT.getWindow();
        int n = window.getScaledWidth();
        int n2 = window.getScaledHeight();
        double d = ClientContext.MINECRAFT_CLIENT.mouse.getX() * ((double)n / (double)window.getWidth());
        double d2 = ClientContext.MINECRAFT_CLIENT.mouse.getY() * ((double)n2 / (double)window.getHeight());
        RenderUtil.beginFrame();
        RenderUtil.fillRectPaint(0.0f, 0.0f, n, n2, RenderUtil.hudBlurPaint);
        EventBus.post(new HudRenderEvent(drawContext, f, d, d2));
        RenderUtil.endFrame(true);
        EventBus.post(new Render2DEvent(drawContext, f));
        RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(BlurRenderer.getGuiBuffer().getColorAttachment(), 0, BlurRenderer.getGuiBuffer().getDepthAttachment(), 1.0);
    }

    @Inject(method={"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void injectRenderScoreboardSidebar(DrawContext drawContext, ScoreboardObjective objective, CallbackInfo ci) {
        ScoreboardModule g82 = EvonClient.getInstance().getModuleManager().getModuleByClass(ScoreboardModule.class);
        if (g82.isEnabled()) {
            g82.renderDefault(drawContext);
        }
        ci.cancel();
    }

    @Unique
    public void renderScoreboardRect(DrawContext drawContext) {
        block0: {
            ScoreboardModule g82 = EvonClient.getInstance().getModuleManager().getModuleByClass(ScoreboardModule.class);
            if (!g82.isEnabled()) break block0;
            g82.render(drawContext, false);
        }
    }

    @Inject(method={"renderStatusEffectOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void noRenderPotions(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        callbackInfo.cancel();
    }

    @Unique
    private void applyPostProcessing(DrawContext drawContext, float f) {
        NvgFont.suppressText = true;
        BlurRenderer.blurBackground();
        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> a, BlurRenderer.getGuiBuffer().getColorAttachmentView(), OptionalInt.empty(), BlurRenderer.getGuiBuffer().useDepthAttachment ? BlurRenderer.getGuiBuffer().getDepthAttachmentView() : null, OptionalDouble.empty());){
            renderPass.setPipeline(RenderPipelines.GUI);
            RenderUtil.beginFrame();
            EventBus.post(new HudRenderPassEvent(drawContext, f));
            RenderUtil.endFrame(false);
        }
        BlurRenderer.blurGui();
        NvgFont.suppressText = false;
    }

    @Inject(method={"renderCrosshair(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderCrosshair(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        block0: {
            if (ClientContext.MINECRAFT_CLIENT.currentScreen != EvonClient.getInstance().getClickGuiScreen()) break block0;
            callbackInfo.cancel();
        }
    }

    @Redirect(at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"), method={"renderHotbar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"})
    public ItemStack hideOffhandSlot(PlayerEntity player) {
        ItemStack itemStack = player.getOffHandStack();
        Animations gv_c22 = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
        if (((BaseModule)gv_c22).isEnabled() && itemStack.getItem() instanceof ShieldItem && gv_c22.isHideShield()) {
            return ItemStack.EMPTY;
        }
        return itemStack;
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
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
                char[] cArray3 = "\u001bR*p+\u0006d1_!z4".toCharArray();
                int n4 = cArray3.length;
                n3 = 0;
                n2 = 67;
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
                        case 0 -> 26;
                        case 1 -> 125;
                        case 2 -> 6;
                        case 3 -> 92;
                        case 4 -> 5;
                        case 5 -> 101;
                        default -> 116;
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
