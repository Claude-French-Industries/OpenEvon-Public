package club.evon.mixins;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import club.evon.EvonClient;
import club.evon.data.SlotSwitchMode;
import club.evon.event.BlockPlaceEvent;
import club.evon.event.ClientTickEvent;
import club.evon.event.DisconnectEvent;
import club.evon.event.EventBus;
import club.evon.event.GameLoopEvent;
import club.evon.event.InputEvent;
import club.evon.event.ItemUseCooldownEvent;
import club.evon.event.JoinWorldEvent;
import club.evon.event.PostInputEvent;
import club.evon.event.PostTickEvent;
import club.evon.manager.KeybindManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.impl.motion.InventoryMove;
import club.evon.module.impl.render.Animations;
import club.evon.util.KeyBindingState;
import club.evon.util.PlayerUtil;
import club.evon.util.SwingHandAccessor;




@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
   @Shadow
   private int itemUseCooldown;
   @Shadow
   public int attackCooldown;
   @Shadow
   @Nullable
   public HitResult crosshairTarget;
   @Shadow
   @Nullable
   public ClientPlayerEntity player;

   @Shadow
   public abstract boolean doAttack();

   private MinecraftClientMixin() {
   }

   @Inject(method = "<init>(Lnet/minecraft/client/RunArgs;)V", at = @At("TAIL"))
   private void init(RunArgs runArgs, CallbackInfo callbackInfo) {
      EvonClient.getInstance();
   }

   @Inject(
      method = "handleInputEvents()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0)
   )
   private void handleInputEventsMouse(CallbackInfo callbackInfo) {
      EventBus.post(new InputEvent());
   }

   @Inject(method = "handleInputEvents()V", at = @At("TAIL"))
   private void handleInputEventsTail(CallbackInfo callbackInfo) {
      KeybindManager.getInstance().tick();
      EventBus.post(new PostInputEvent());
   }

   @Redirect(
      method = "doAttack()Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V")
   )
   private void redirectAttackSwings(ClientPlayerEntity player2, Hand hand) {
      KeyBindingState attackState = KeybindManager.getAttackState();
      if (attackState.shouldSwing()) {
         player2.swingHand(hand);
      } else {
         ((SwingHandAccessor)player2).evon$swingHandServerside(hand);
      }
   }

   @Redirect(
      method = "doItemUse()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V")
   )
   private void redirectUseSwings(ClientPlayerEntity player2, Hand hand) {
      KeyBindingState useState = KeybindManager.getUseState();
      if (useState.shouldSwing()) {
         player2.swingHand(hand);
      } else {
         ((SwingHandAccessor)player2).evon$swingHandServerside(hand);
      }
   }

   @Inject(
      method = "setScreen(Lnet/minecraft/client/gui/screen/Screen;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;unpressAll()V", shift = Shift.AFTER)
   )
   private void hookSetScreen(Screen screen, CallbackInfo callbackInfo) {
      if (this.player != null) {
         InventoryMove moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(InventoryMove.class);
         if (moduleByClass.isEnabled() && !moduleByClass.isChatOrGuiScreen()) {
            PlayerUtil.updateKeyStates();
         }
      }
   }

   @Redirect(method = "handleInputEvents()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V"))
   private void redirectSelectedSlot(PlayerInventory player2, int value) {
      SilentSlotManager instance = SilentSlotManager.getInstance();
      if (instance.isActive()) {
         if (instance.getMode() != SlotSwitchMode.NONE) {
            instance.setRealSlot(value);
         }
      } else {
         player2.setSelectedSlot(value);
      }
   }

   @Inject(
      method = "handleInputEvents()V",
      at = @At(
         value = "FIELD",
         target = "Lnet/minecraft/client/option/GameOptions;socialInteractionsKey:Lnet/minecraft/client/option/KeyBinding;",
         shift = Shift.BEFORE
      )
   )
   private void postSlotHandleInput(CallbackInfo callbackInfo) {
      SilentSlotManager.getInstance().update(false, false);
   }

   @Redirect(method = "handleInputEvents()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"))
   private boolean redirectIsPressed(KeyBinding keyBinding) {
      KeyBindingState state = KeybindManager.getState(keyBinding);
      return state != null ? state.isPressed() : keyBinding.isPressed();
   }

   @Redirect(
      method = "handleInputEvents()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z"),
      slice = @Slice(
         from = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;useKey:Lnet/minecraft/client/option/KeyBinding;", ordinal = 1),
         to = @At("TAIL")
      )
   )
   private boolean redirectWasPressed(KeyBinding keyBinding) {
      KeyBindingState state = KeybindManager.getState(keyBinding);
      return state != null ? state.wasPressed() : keyBinding.wasPressed();
   }

   @Redirect(method = "handleInputEvents()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z", ordinal = 11))
   private boolean redirectUsingAttack(KeyBinding keyBinding, @Local LocalBooleanRef localBooleanRef) {
      if (this.isSwingWhileUsing() && KeybindManager.getAttackState().wasPressed()) {
         boolean get = localBooleanRef.get();
         boolean flag = get | this.doAttack();
         localBooleanRef.set(flag);
         return true;
      } else {
         return false;
      }
   }

   @Inject(method = "doItemUse()V", at = @At("HEAD"))
   private void hookItemUse(CallbackInfo callbackInfo) {
   }

   @Inject(
      method = "handleInputEvents()V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;useKey:Lnet/minecraft/client/option/KeyBinding;", ordinal = 1)
   )
   private void onItemUseMouseHandle(CallbackInfo callbackInfo) {
      Animations moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Animations.class);
      KeyBindingState attackState = KeybindManager.getAttackState();
      if (this.player != null
         && ((BaseModule)moduleByClass).isEnabled()
         && moduleByClass.isAllowSwingOnUse()
         && attackState.isPressed()
         && attackState.shouldSwing()
         && (this.crosshairTarget != null && this.crosshairTarget.getType() == Type.BLOCK || attackState.wasPressed())) {
         ((SwingHandAccessor)this.player).evon$swingHandClientside(Hand.MAIN_HAND);
      }

      while (attackState.wasPressed()) {
      }
   }

   @Unique
   private boolean isSwingWhileUsing() {
      return false;
   }

   @Inject(method = "render(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;runTasks()V", shift = Shift.BEFORE))
   private void onGameLoop(boolean flag, CallbackInfo callbackInfo, @Local(ordinal = 0) int value) {
      EventBus.post(new GameLoopEvent(value > 0));
   }

   @Inject(method = "tick()V", at = @At("HEAD"))
   private void tickHead(CallbackInfo callbackInfo) {
      EventBus.post(new ClientTickEvent());
   }

   @Inject(method = "joinWorld(Lnet/minecraft/client/world/ClientWorld;)V", at = @At("HEAD"))
   private void hookJoinWorld(ClientWorld world, CallbackInfo callbackInfo) {
      EventBus.post(new JoinWorldEvent());
   }

   @Inject(method = "tick()V", at = @At("TAIL"))
   private void tickTail(CallbackInfo callbackInfo) {
      EventBus.post(new PostTickEvent());
   }

   @Inject(method = "onDisconnected()V", at = @At("HEAD"))
   private void disconnected(CallbackInfo callbackInfo) {
      EventBus.post(new DisconnectEvent());
   }

   @Inject(method = "isTelemetryEnabledByApi()Z", at = @At("HEAD"), cancellable = true)
   private void disableTelemetry(CallbackInfoReturnable<Boolean> callbackInfoReturnableBoolean) {
      callbackInfoReturnableBoolean.setReturnValue(false);
   }

   @Inject(method = "onResolutionChanged()V", at = @At("HEAD"))
   private void resolutionChange(CallbackInfo callbackInfo) {
   }

   @Inject(
      method = "doItemUse()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ActionResult$Success;swingSource()Lnet/minecraft/util/ActionResult$SwingSource;", ordinal = 1)
   )
   private void hookBlockPlaceEvent(CallbackInfo callbackInfo, @Local BlockHitResult blockHitResult) {
      EventBus.post(new BlockPlaceEvent(blockHitResult));
   }

   @Redirect(
      method = "doItemUse()V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;resetEquipProgress(Lnet/minecraft/util/Hand;)V", ordinal = 0)
   )
   private void redirectResetEquipProgress(HeldItemRenderer heldItemRenderer, Hand hand) {
      SilentSlotManager instance = SilentSlotManager.getInstance();
      if (hand != Hand.MAIN_HAND || !instance.isActive() || instance.getMainHandStack(this.player).getItem() instanceof BlockItem) {
         heldItemRenderer.resetEquipProgress(hand);
      }
   }

   @Inject(
      method = "doItemUse()V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;itemUseCooldown:I", shift = Shift.AFTER),
      cancellable = true
   )
   private void injectItemUseCooldown(CallbackInfo callbackInfo) {
      ItemUseCooldownEvent itemUseCooldownEvent = new ItemUseCooldownEvent(4);
      EventBus.post(itemUseCooldownEvent);
      if (itemUseCooldownEvent.isCanceled()) {
         callbackInfo.cancel();
      } else {
         this.itemUseCooldown = itemUseCooldownEvent.getCooldown();
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
