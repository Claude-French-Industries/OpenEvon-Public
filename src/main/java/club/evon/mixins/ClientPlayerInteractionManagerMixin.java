/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.network.ClientPlayerInteractionManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.util.math.BlockPos
 *  org.json.JSONException
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package club.evon.mixins;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import club.evon.EvonClient;
import club.evon.data.InteractionAction;
import club.evon.event.AttackEntityEvent;
import club.evon.event.CancelBlockBreakingEvent;
import club.evon.event.EventBus;
import club.evon.event.SyncSelectedSlotEvent;
import club.evon.manager.SilentSlotManager;
import club.evon.module.impl.player.Breaker;
import club.evon.util.BlockBreakingAccessor;




@Mixin(value={ClientPlayerInteractionManager.class})
public final class ClientPlayerInteractionManagerMixin
implements BlockBreakingAccessor {
    @Unique
    private static final String SYNC_TARGET;
    @Shadow
    private BlockPos currentBreakingPos;
    @Shadow
    private float currentBreakingProgress;

    @Inject(method={"cancelBlockBreaking()V"}, at={@At(value="HEAD")}, cancellable=true)
    private void hookCancelBlockBreaking(CallbackInfo callbackInfo) {
        block0: {
            CancelBlockBreakingEvent vY2 = new CancelBlockBreakingEvent();
            EventBus.post(vY2);
            if (!vY2.isCanceled()) break block0;
            callbackInfo.cancel();
        }
    }

    @Inject(method={"isCurrentlyBreaking(Lnet/minecraft/util/math/BlockPos;)Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void redirectCurrentlyBreaking(BlockPos blockPos, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        block0: {
            Breaker gr_c22 = EvonClient.getInstance().getModuleManager().getModuleByClass(Breaker.class);
            if (!gr_c22.isEnabled() || !gr_c22.isBreaking() || gr_c22.getTargetSlot() == -1) break block0;
            callbackInfoReturnable.setReturnValue(blockPos.equals((Object)this.currentBreakingPos));
        }
    }

    @Inject(method={"tick()V"}, at={@At(value="HEAD")})
    private void tick(CallbackInfo callbackInfo) {
        SilentSlotManager.getInstance().tick();
    }

    @Inject(method={"attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V", shift=At.Shift.AFTER)})
    private void hookAttackEvent(PlayerEntity playerEntity, Entity entity, CallbackInfo callbackInfo) {
        EventBus.post(new AttackEntityEvent(entity));
    }

    @Override
    public BlockPos evon$getCurrentBreakingPos() {
        return this.currentBreakingPos;
    }

    @Override
    public float evon$currentBreakingProgress() {
        return this.currentBreakingProgress;
    }

    @Unique
    private static void gate(ClientPlayerInteractionManager clientPlayerInteractionManager, InteractionAction gl_c22) {
        block0: {
            SyncSelectedSlotEvent vv_c22 = new SyncSelectedSlotEvent(gl_c22);
            EventBus.post(vv_c22);
            if (vv_c22.isCanceled()) break block0;
            ((ClientPlayerInteractionManagerAccessor)clientPlayerInteractionManager).callSyncSelectedSlot();
        }
    }

    @Redirect(method={"updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"))
    private void redirect$updateBlockBreakingProgress(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        ClientPlayerInteractionManagerMixin.gate(clientPlayerInteractionManager, InteractionAction.UPDATE_BLOCK_BREAKING_PROGRESS);
    }

    @Redirect(method={"tick()V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"))
    private void redirect$tick(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        ClientPlayerInteractionManagerMixin.gate(clientPlayerInteractionManager, InteractionAction.TICK);
    }

    @Redirect(method={"interactBlock(Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"))
    private void redirect$interactBlock(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        ClientPlayerInteractionManagerMixin.gate(clientPlayerInteractionManager, InteractionAction.INTERACT_BLOCK);
    }

    @Redirect(method={"interactItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"))
    private void redirect$interactItem(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        ClientPlayerInteractionManagerMixin.gate(clientPlayerInteractionManager, InteractionAction.INTERACT_ITEM);
    }

    @Redirect(method={"attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"))
    private void redirect$attackEntity(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        ClientPlayerInteractionManagerMixin.gate(clientPlayerInteractionManager, InteractionAction.ATTACK_ENTITY);
    }

    @Redirect(method={"interactEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"))
    private void redirect$interactEntity(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        ClientPlayerInteractionManagerMixin.gate(clientPlayerInteractionManager, InteractionAction.INTERACT_ENTITY);
    }

    @Redirect(method={"interactEntityAtLocation(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/hit/EntityHitResult;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"))
    private void redirect$interactEntityAtLocation(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        ClientPlayerInteractionManagerMixin.gate(clientPlayerInteractionManager, InteractionAction.INTERACT_ENTITY_AT_LOCATION);
    }

    @Redirect(method={"stopUsingItem(Lnet/minecraft/entity/player/PlayerEntity;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"))
    private void redirect$stopUsingItem(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        ClientPlayerInteractionManagerMixin.gate(clientPlayerInteractionManager, InteractionAction.STOP_USING_ITEM);
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
                char[] cArray3 = "\u001fmN(]\u000f~=fH.\u0013\u0004c|`G5\u0017\fc|mN(\u0005\re8,h0\u001b\u0007y'SG=\u000b\u0007e\u001am_9\u0000\u0003t'jD2?\u0003y2dN.I\u0011n=`x9\u001e\u0007t'fO\u000f\u001e\rc{*}".toCharArray();
                int n4 = cArray3.length;
                n3 = 0;
                n2 = 72;
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
                        case 0 -> 27;
                        case 1 -> 75;
                        case 2 -> 99;
                        case 3 -> 20;
                        case 4 -> 58;
                        case 5 -> 42;
                        default -> 95;
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
        SYNC_TARGET = new String(cArray).intern();
    }
}
