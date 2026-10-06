package club.evon.module.impl.combat;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientTickEndC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.CombatMode;
import club.evon.data.DamageTracker;
import club.evon.data.RotationHitResult;
import club.evon.data.TargetSortMode;
import club.evon.event.ClientTickEvent;
import club.evon.event.InputEvent;
import club.evon.event.ItemSlowdownEvent;
import club.evon.event.MotionEvent;
import club.evon.event.OutboundPacketEvent;
import club.evon.event.PostInputEvent;
import club.evon.event.Subscribe;
import club.evon.launch.Loader;
import club.evon.launch.hidden.Hidden0;
import club.evon.manager.ClickScheduler;
import club.evon.manager.KeybindManager;
import club.evon.manager.OutgoingPacketManager;
import club.evon.manager.RotationManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.SubMode;
import club.evon.module.impl.motion.Scaffold;
import club.evon.module.impl.player.Breaker;
import club.evon.runtime.IndyRuntime;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.setting.KillAuraTargetMode;
import club.evon.setting.LagMode;
import club.evon.setting.TargetsSetting;
import club.evon.util.PlayerUtil;
import club.evon.util.RotationUtil;
import club.evon.util.math.RandomUtil;
import club.evon.util.net.NetworkUtil;
import club.evon.util.net.PacketBlocker;




public final class KillAura extends BaseModule {
   private final EnumSetting<CombatMode> combatMode;
   private final EnumSetting<KillAuraTargetMode> targetMode;
   private final DoubleSetting minCps;
   private final DoubleSetting maxCps;
   private final DoubleSetting switchDelay;
   private final DoubleSetting rotationSpeed;
   private final EnumSetting<TargetSortMode> sortMode;
   private final EnumSetting<LagMode> lagMode;
   private final DoubleSetting E;
   private final BooleanSetting throughWalls;
   private final BooleanSetting raycast;
   private final TargetsSetting targets;
   private LivingEntity target;
   private LivingEntity lastTarget;
   private RotationHitResult rotationHit;
   private int hitCount;
   private int switchIndex;
   private int switchCooldown;
   private double attackDelay;
   private DamageTracker damageTracker;
   private boolean usingItem;
   private boolean lagging;
   private int lagTicks;
   private final PacketBlocker packetBlocker;
   private static String[] strings;
   private static long DIAG_LAST;
   private static final long KEY;
   private static final String[] STRINGS_TABLE;
   private static final long SWITCH_COOLDOWN;
   private static final long[] encryptedConstants;
   private static final Long[] decryptedConstants;
   private static final Map cipherCache;

   public KillAura() {
      super((STRINGS_TABLE)[11], (STRINGS_TABLE)[10], ModuleCategory.COMBAT);

      String[] strings2 = STRINGS_TABLE;
      this.combatMode = new EnumSetting<>(strings2[6], CombatMode.LEGACY);
      this.targetMode = new EnumSetting<>(strings2[2], KillAuraTargetMode.SINGLE);
      this.minCps = new DoubleSetting(strings2[9], 12.0, 1.0, 20.0, 1.0);
      this.maxCps = new DoubleSetting(strings2[3], 15.0, 1.0, 20.0, 1.0);
      this.switchDelay = new DoubleSetting(strings2[12], 1.0, 0.0, 20.0, 1.0);
      this.rotationSpeed = new DoubleSetting(strings2[0], 5.0, 1.0, 10.0, 0.1);
      this.sortMode = new EnumSetting<>(strings2[4], TargetSortMode.DISTANCE);
      this.lagMode = new EnumSetting<>(strings2[8], LagMode.NONE);
      this.E = new DoubleSetting(strings2[1], 6.0, 3.0, 8.0, 0.05);
      this.throughWalls = new BooleanSetting(strings2[5], false);
      this.raycast = new BooleanSetting(strings2[7], true);
      this.targets = new TargetsSetting();
      this.packetBlocker = new PacketBlocker(OutgoingPacketManager.getInstance());
      this.minCps.setVisible(() -> {
         long var1x = KEY ^ 40145720662326L;
         String[] strings3 = getStrings();
         boolean equals = this.combatMode.getValue().equals(CombatMode.MODERN);
         if (strings3 == null) {
            equals = !equals;
         }

         return equals;
      });
      this.maxCps.setVisible(() -> {
         long var1x = KEY ^ 135729451543646L;
         String[] strings4 = getStrings();
         boolean equals2 = this.combatMode.getValue().equals(CombatMode.MODERN);
         if (strings4 == null) {
            equals2 = !equals2;
         }

         return equals2;
      });
      this.switchDelay.setVisible(() -> this.targetMode.getValue().equals(KillAuraTargetMode.SWITCH));
      this.E.setVisible(() -> {
         long var1x = KEY ^ 47662333101048L;
         String[] strings5 = getStrings();
         boolean equals3 = this.lagMode.getValue().equals(LagMode.NONE);
         if (strings5 == null) {
            equals3 = !equals3;
         }

         return equals3;
      });
      this.addSettings(this.combatMode, this.minCps, this.maxCps, this.targetMode, this.rotationSpeed, this.switchDelay, this.lagMode, this.E, this.sortMode, this.throughWalls, this.raycast, this.targets);
      }

   @Override
   public void onEnable() {
      long longValue = KEY ^ 61498799168940L;
      String[] strings2 = getStrings();
      if (strings2 == null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         super.onEnable();
      }
   }

   @Override
   public void onDisable() {
      this.target = null;
      this.hitCount = this.switchCooldown = 0;
      this.damageTracker = null;
      this.packetBlocker.unblock();
      this.lagging = false;
      this.releaseItem();
      super.onDisable();
   }

   private void sendBlock() {
      long longValue = KEY ^ 126642014543960L;
      String[] strings2 = getStrings();
      HitResult hitResult = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      if (strings2 == null) {
         if (hitResult instanceof EntityHitResult entity) {
            ClientContext.MINECRAFT_CLIENT.player.interact(entity.getEntity(), Hand.MAIN_HAND);
            NetworkUtil.sendPacket(PlayerInteractEntityC2SPacket.interact(entity.getEntity(), ClientContext.MINECRAFT_CLIENT.player.isSneaking(), Hand.MAIN_HAND));
         }

         NetworkUtil.sendSequencedPacket(value -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, value, ClientContext.MINECRAFT_CLIENT.player.getYaw(), ClientContext.MINECRAFT_CLIENT.player.getPitch()));
         this.usingItem = true;
      }
   }

   private void releaseItem() {
      long longValue = KEY ^ 118573012149314L;
      String[] strings2 = getStrings();
      boolean tmp = this.usingItem;
      if (strings2 == null) {
         if (!this.usingItem) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.player.getMainHandStack().isIn(ItemTags.SWORDS);
      }

      if (tmp) {
         NetworkUtil.sendPacket(new UpdateSelectedSlotC2SPacket(PlayerUtil.findReplaceableSlot()));
         NetworkUtil.sendPacket(new UpdateSelectedSlotC2SPacket(ClientContext.MINECRAFT_CLIENT.player.getInventory().getSelectedSlot()));
         NetworkUtil.sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
      }

      this.usingItem = false;
   }

   @Subscribe
   private void S_r2(PostInputEvent event) {
   }

   @Subscribe
   private void onInput(InputEvent event) {
      long longValue = KEY ^ 40068764354593L;
      String[] strings2 = getStrings();
      BaseModule tmp = this;
      if (strings2 == null) {
         if (this.target == null) {
            return;
         }

         tmp = this;
      }

      if (strings2 == null) {
         if (!this.canRun()) {
            return;
         }

         tmp = EvonClient.getInstance().getModuleManager().getModuleByClass(Breaker.class);
      }

      label104: {
         Breaker breaker = (Breaker)tmp;
         boolean enabled = breaker.isEnabled();
         if (strings2 == null) {
            if (!enabled) {
               break label104;
            }

            enabled = breaker.isBreaking();
         }

         if (enabled) {
            return;
         }
      }

      ClickScheduler clickScheduler;
      label98: {
         clickScheduler = ClickScheduler.getInstance();
         KillAura killAura = this;
         if (strings2 == null) {
            if (this.rotationHit == null) {
               break label98;
            }

            killAura = this;
         }

         if (!killAura.raycast.I()) {
            ClientContext.MINECRAFT_CLIENT.crosshairTarget = this.rotationHit.hitResult();
         }
      }

      HitResult hitResult = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      HitResult hitResult2 = hitResult;
      if (strings2 == null) {
         if (!(hitResult instanceof EntityHitResult)) {
            return;
         }

         hitResult2 = hitResult;
      }

      EntityHitResult entity = (EntityHitResult)hitResult2;
      KillAura killAura2 = this;
      if (strings2 == null) {
         if (this.canAttack()) {
            if (entity.getEntity() != this.target) {
               return;
            }

            KeybindManager.getAttackState().queuePress();
            int value = this.switchCooldown;
            if (strings2 == null) {
               value = this.switchCooldown == 0 ? 1 : 0;
            }

            this.updateDamage(value != 0);
            clickScheduler.reset();
            killAura2 = this;
            if (strings2 == null) {
               if (this.switchCooldown > 0) {
                  this.switchCooldown--;
                  if (strings2 == null) {
                     return;
                  }
               }

               killAura2 = this;
            }

            killAura2.switchCooldown = (int)SWITCH_COOLDOWN;
            if (strings2 == null) {
               return;
            }
         }

         killAura2 = this;
      }

      killAura2.switchCooldown = 0;
   }

   @Subscribe(h = 2)
   private void S_r4(MotionEvent event) {
      MinecraftClient client = ClientContext.MINECRAFT_CLIENT;
      if (client == null || client.player == null || client.world == null || !this.isEnabled()) {
         return;
      }

      if (!this.canRun()) {
         this.rotationHit = null;
         return;
      }

      // Target selection and rotation, reconstructed from the native descriptor table: the original
      // body resolved the target with the player's interaction range, aimed at the closest point of
      // the target box (PlayerUtil.closestPointOnLivingEntity) and cached the raycast result in the
      // rotationHit field that onInput() consumes.
      double range = this.E.J();   // module Range setting (default 6.0) - not the vanilla 3.0 entity reach
      LivingEntity selected = this.getTarget(range);
      this.target = selected;
      // TEMP-DIAG
      if (System.currentTimeMillis() - DIAG_LAST > 2000L) {
         DIAG_LAST = System.currentTimeMillis();
         net.minecraft.entity.LivingEntity nearestEntity = null;
         double nearestDist = Double.MAX_VALUE;
         for (net.minecraft.entity.player.PlayerEntity other : client.world.getPlayers()) {
            if (other == client.player) continue;
            double dist = PlayerUtil.getDistanceToLivingEntity(other);
            if (dist < nearestDist) { nearestDist = dist; nearestEntity = other; }
         }
         club.evon.api.ServerProfile profile =
            club.evon.manager.ClientState.getInstance().getServerDetector().getServerProfile();
         String profileName = profile == null ? "none" : profile.getName();
         String nearestValid = "-";
         if (nearestEntity != null) {
            nearestValid = String.valueOf(profile == null || profile.isValidTarget(nearestEntity));
         }
         System.out.println("[EVON-DIAG] range=" + range + " players=" + client.world.getPlayers().size()
            + " nearest=" + (nearestEntity == null ? "-" : String.format("%.2f", nearestDist) + "/" + nearestEntity.getName().getString())
            + " profile=" + profileName + " nearestValid=" + nearestValid
            + " f@3=" + this.targets.findTargets(3.0).size()
            + " f@6=" + this.targets.findTargets(6.0).size()
            + " f@12=" + this.targets.findTargets(12.0).size()
            + " selected=" + (selected == null ? "null" : selected.getName().getString()));
      }
      if (selected == null) {
         this.rotationHit = null;
         return;
      }

      Vec3d closestPoint = PlayerUtil.closestPointOnLivingEntity(client.player.getEyePos(), selected);
      RotationHitResult hit = RotationUtil.findRotationToLivingEntity(selected, closestPoint, range);
      if (hit == null) {
         if (!this.isThroughWalls()) {
            this.rotationHit = null;
            return;
         }

         // NOTE: throughWalls - no unobstructed sample was found, so aim straight at the closest point
         // and hand the attack handler a synthetic entity hit.
         hit = new RotationHitResult(RotationUtil.wrapYawNearPlayer(RotationUtil.getRotationTo(closestPoint)), new EntityHitResult(selected));
      }

      this.rotationHit = hit;
      // NOTE: the native body never wrote yaw/pitch back into the event - its descriptor table contains
      // no (F)V entry and method_36454/36455 are Entity#getYaw/getPitch - the rotation is applied the
      // same way every other rotation module does it, through the rotation controller.
      RotationManager.getRotationController().rotateTo(hit.rotation(), this.rotationSpeed.J().floatValue() * 18.0F);
   }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 45047220616220L;
      String[] strings2 = getStrings();
      Breaker moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Breaker.class);
      String[] strings3 = strings2;
      Scaffold moduleByClass2 = EvonClient.getInstance().getModuleManager().getModuleByClass(Scaffold.class);
      boolean flag;
      KillAura killAura;
      KillAura killAura2;
      if (strings3 == null) {
         label130:
         if (!moduleByClass2.isEnabled()) {
            flag = moduleByClass.isEnabled();
            label136:
            if (strings3 == null) {
               if (flag) {
                  flag = moduleByClass.isRotating();
                  if (strings3 != null) {
                     break label136;
                  }

                  if (flag) {
                     break label130;
                  }
               }

               flag = this.canRun();
            }

            label147: {
               if (strings3 == null) {
                  if (!flag) {
                     return;
                  }

                  killAura = this;
                  if (strings3 != null) {
                     break label147;
                  }

                  flag = this.lagMode.getValue().equals(LagMode.HYPIXEL);
               }

               if (!flag) {
                  return;
               }

               killAura = this;
            }

            LivingEntity target2 = killAura.getTarget(this.E.J());
            if (strings3 == null) {
               label84:
               if (target2 != null) {
                  int value = ClientContext.MINECRAFT_CLIENT.player.getMainHandStack().isIn(ItemTags.SWORDS) ? 1 : 0;
                  if (strings3 == null) {
                     if (value == 0) {
                        break label84;
                     }

                     double doubleValue;
                     value = (doubleValue = PlayerUtil.getDistanceToLivingEntity(target2) - this.E.J()) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
                  }

                  if (strings3 == null) {
                     if (value > 0) {
                        break label84;
                     }

                     value = this.lagMode.getValue().ordinal();
                  }

                  label104: {
                     label103: {
                        label150: {
                           if (strings3 == null) {
                              switch (value) {
                                 case 1:
                                    break label103;
                                 case 2:
                                    this.lagTicks++;
                                    killAura2 = this;
                                    if (strings3 != null) {
                                       break label150;
                                    }

                                    value = this.lagTicks;
                                    break;
                                 default:
                                    break label104;
                              }
                           }

                           switch (value) {
                              case 1:
                                 this.sendBlock();
                                 this.packetBlocker.unblock();
                                 this.packetBlocker.blockAll();
                                 this.releaseItem();
                                 if (strings3 == null) {
                                    break;
                                 }
                              case 2:
                                 this.lagTicks = 0;
                           }

                           killAura2 = this;
                        }

                        killAura2.lagging = true;
                        if (strings3 == null) {
                           break label104;
                        }
                     }

                     this.lagging = true;
                  }

                  if (strings3 == null) {
                     return;
                  }
               }

               this.packetBlocker.unblock();
               this.releaseItem();
               this.lagTicks = 0;
            }

            this.lagging = false;
            return;
         }

         this.packetBlocker.unblock();
         this.releaseItem();
         this.lagTicks = 0;
      }
   }

   @Subscribe
   public void onOutboundPacket(OutboundPacketEvent event) {
      long longValue = KEY ^ 82136661149050L;
      String[] strings2 = getStrings();
      Packet packet = event.getPacket();
      String[] strings3 = strings2;
      Packet packet2 = packet;
      if (strings3 == null) {
         if (!(packet instanceof ClientTickEndC2SPacket)) {
            return;
         }

         packet2 = packet;
      }

      ClientTickEndC2SPacket packet3 = (ClientTickEndC2SPacket)packet2;
      int value = ClientContext.MINECRAFT_CLIENT.player.hurtTime;
      if (strings3 == null) {
         if (ClientContext.MINECRAFT_CLIENT.player.hurtTime != 0) {
            return;
         }

         value = this.packetBlocker.isBlocking() ? 1 : 0;
      }

      if (value == 0) {
         event.cancel();
         NetworkUtil.sendManagedPacket(packet3);
      }
   }

   @Subscribe
   public void onItemSlowdown(ItemSlowdownEvent event) {
      long longValue = KEY ^ 86890884905406L;
      String[] strings2 = getStrings();
      KillAura tmp = this;
      if (strings2 == null) {
         if (!this.lagMode.getValue().equals(LagMode.HYPIXEL)) {
            return;
         }

         tmp = this;
      }

      if (tmp.target != null) {
         event.cancel();
      }
   }

   @Subscribe(h = 2)
   private void V(ClientTickEvent event) {
      MinecraftClient client = ClientContext.MINECRAFT_CLIENT;
      if (client == null || client.player == null || client.world == null) {
         // World is gone: drop the per-tick combat state without touching the player.
         this.packetBlocker.unblock();
         this.usingItem = false;
         this.target = null;
         this.rotationHit = null;
         this.hitCount = 0;
         this.switchCooldown = 0;
         this.lagTicks = 0;
         this.lagging = false;
         return;
      }

      if (!this.isEnabled() || !this.canRun()) {
         // Module off or Scaffold owns the rotation: mirror onDisable()'s cleanup.
         this.packetBlocker.unblock();
         this.releaseItem();
         this.target = null;
         this.rotationHit = null;
         this.hitCount = 0;
         this.switchCooldown = 0;
         this.lagTicks = 0;
         this.lagging = false;
         return;
      }

      // Target upkeep. S_r4() re-selects the target on every motion tick; here the held target is only
      // validated against the very same target filter (dead, despawned, friendly or out of range
      // targets drop out) so the counters below cannot drift away from it.
      double range = this.E.J();   // module Range setting (default 6.0) - not the vanilla 3.0 entity reach
      LivingEntity current = this.target;
      if (current == null || !this.targets.findTargets(range).contains(current)) {
         if (current != null) {
            this.lastTarget = current;
            if (this.targetMode.getValue().equals(KillAuraTargetMode.SWITCH)) {
               // SWITCH mode continues with the next entry of the sorted list; getTarget() wraps it.
               this.switchIndex++;
            }
         }

         this.target = null;
         this.rotationHit = null;
         this.hitCount = 0;
         this.switchCooldown = 0;
         this.damageTracker = null;
         this.packetBlocker.unblock();
         this.releaseItem();
         this.lagTicks = 0;
         this.lagging = false;
         return;
      }

      // Per-tick upkeep of the attack counters. lastTarget always holds the target of the previous
      // tick, so a switch performed by S_r4() restarts them.
      if (current != this.lastTarget) {
         this.hitCount = 0;
         this.switchCooldown = 0;
      } else {
         // NOTE: hitCount is not read anywhere; it is maintained as "ticks the current target has been
         // held", because the native meaning of the counter is not recoverable.
         this.hitCount++;
         if (this.switchCooldown > 0) {
            this.switchCooldown--;
         }
      }

      this.lastTarget = current;

      if (this.damageTracker == null) {
         this.damageTracker = new DamageTracker(this.getAttackDamage());
      }

      if (this.lagMode.getValue().equals(LagMode.FAKE)) {
         // NOTE: approximated. The native tick handler additionally froze the client thread on
         // EvonClient.getSemaphore() (Semaphore.acquire + Thread.sleep in the descriptor table) with a
         // delay read from a JSON blob that no longer exists; blocking the client thread here would risk
         // a stall, so FAKE mode only maintains the client-side flag. The HYPIXEL packet cycle
         // (sendBlock/packetBlocker/lagTicks) is the one already implemented by onClientTick().
         this.lagging = true;
      }
   }

   public boolean isThroughWalls() {
      return this.throughWalls.I();
   }

   public double getAttackDamage() {
      long longValue = KEY ^ 50377013967362L;
      String[] strings2 = getStrings();
      double doubleValue = PlayerUtil.getAttackDamage(ClientContext.MINECRAFT_CLIENT.player.getMainHandStack());
      String[] strings3 = strings2;
      double doubleValue2;
      int value = (doubleValue2 = doubleValue - 0.5) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);
      if (strings3 == null) {
         if (value < 0) {
            doubleValue = 0.5;
         }

         value = PlayerUtil.canMove() ? 1 : 0;
      }

      if (strings3 == null) {
         if (value == 0) {
            return doubleValue;
         }

         if (strings3 != null) {
            return ClientContext.MINECRAFT_CLIENT.player.fallDistance;
         }

         double doubleValue3;
         value = (doubleValue3 = ClientContext.MINECRAFT_CLIENT.player.fallDistance - 0.0) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
      }

      if (value > 0) {
         doubleValue *= 1.5;
      }

      return doubleValue;
   }

   public void updateDamage(boolean reset) {
      label25: {
         long longValue = KEY ^ 93148606156827L;
         String[] strings2 = getStrings();
         double doubleValue = this.getAttackDamage();
         String[] strings3 = strings2;
         DamageTracker damageTracker2 = this.damageTracker;
         if (strings3 == null) {
            if (this.damageTracker == null) {
               this.damageTracker = new DamageTracker(doubleValue);
               if (strings3 == null) {
                  break label25;
               }
            }

            damageTracker2 = this.damageTracker;
         }

         damageTracker2.recordDamage(reset, doubleValue);
      }

      if (reset) {
         this.attackDelay = RandomUtil.randomDouble(this.minCps.J(), this.maxCps.J());
      }
   }

   public boolean isDamageReady() {
      long longValue = KEY ^ 133760741555020L;
      String[] strings2 = getStrings();
      KillAura tmp = this;
      if (strings2 == null) {
         if (this.damageTracker == null) {
            return true;
         }

         tmp = this;
      }

      double doubleValue = tmp.getAttackDamage();
      double doubleValue2;
      int value = (doubleValue2 = this.target.getHealth() + this.target.getAbsorptionAmount() - doubleValue) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);
      if (strings2 == null) {
         if (value <= 0) {
            return true;
         }

         long longValue2 = this.damageTracker.getElapsedTime();
         long longValue3 = 5829746896490591799L ^ longValue;
         short shortValue = 7417;
         long longValue4;
         try {
            longValue4 = longValue2
               - ((Number)IndyRuntime.invoke(
                     "club.evon.module.impl.combat.KillAura",
                     "decryptLongSiteFactory",
                     "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                     "a",
                     "(IJ)J",
                     new Object[]{Integer.valueOf(shortValue), longValue3}
                  ))
                  .longValue();
         } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
         }

         value = longValue4 == 0L ? 0 : (longValue4 < 0L ? -1 : 1);
      }

      if (strings2 == null) {
         if (value < 0) {
            double doubleValue3;
            int value2 = (doubleValue3 = doubleValue - this.damageTracker.getDamage()) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
            if (strings2 != null) {
               return value2 != 0;
            }

            if (value2 <= 0) {
               return false;
            }
         }

         value = 1;
      }

      return value != 0;
   }

   private boolean canAttack() {
      long longValue = KEY ^ 83344225747563L;
      String[] strings2 = getStrings();
      boolean equals = this.combatMode.getValue().equals(CombatMode.LEGACY);
      if (strings2 == null) {
         if (equals) {
            Velocity moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Velocity.class);
            equals = moduleByClass.isEnabled();
            label58:
            if (strings2 == null) {
               if (equals) {
                  SubMode mode = moduleByClass.getMode();
                  equals = mode instanceof VelocityHypixelMode;
                  if (strings2 != null) {
                     break label58;
                  }

                  if (equals) {
                     VelocityHypixelMode velocityHypixelMode = (VelocityHypixelMode)mode;
                     equals = velocityHypixelMode.isKnockbackActive();
                     if (strings2 != null) {
                        break label58;
                     }

                     if (equals) {
                        return true;
                     }
                  }
               }

               equals = this.isDamageReady();
            }

            if (strings2 != null) {
               return equals;
            }

            if (equals) {
               return true;
            }

         if (strings2 != null) {
            return this.switchCooldown != 0;
         }

            if (this.switchCooldown > 0) {
               return true;
            }
         }

         return ClickScheduler.getInstance().canClick(this.attackDelay, this.combatMode.getValue().equals(CombatMode.MODERN));
      } else {
         return equals;
      }
   }

   private boolean canRun() {
      long longValue = KEY ^ 83412021478770L;
      String[] strings2 = getStrings();
      if (ClientContext.MINECRAFT_CLIENT.player == null) {
         return false;
      }

      Scaffold moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(Scaffold.class);
      boolean enabled = moduleByClass.isEnabled();
      if (strings2 == null) {
         enabled = !enabled;
      }

      return enabled;
   }

   public LivingEntity getTarget(double range) {
      long longValue = KEY ^ 96939594300354L;
      String[] strings2 = getStrings();
      List toList = this.targets.findTargets(range).stream().sorted(Comparator.comparingDouble(this::getSortValue)).toList();
      String[] strings3 = strings2;
      Object object = toList;
      if (strings3 == null) {
         if (toList.isEmpty()) {
            return null;
         }

         object = toList.getFirst();
      }

      LivingEntity entity = (LivingEntity)object;
      int value = this.targetMode.getValue().equals(KillAuraTargetMode.SWITCH) ? 1 : 0;
      if (strings3 == null) {
         if (value == 0) {
            return entity;
         }

         value = this.switchIndex;
      }

      if (strings3 == null) {
         if (value >= toList.size()) {
            this.switchIndex = 0;
         }

         Stream stream = toList.stream();
         if (strings3 != null) {
            return (LivingEntity)stream;
         }

         value = stream.anyMatch(object2 -> {
            long var3x = KEY ^ 121032501933070L;
            String[] var5x = getStrings();
            double var6x;
            int var10000x = (var6x = PlayerUtil.getDistanceToLivingEntity((LivingEntity)object2) - range) == 0.0 ? 0 : (var6x < 0.0 ? -1 : 1);
            if (var5x == null) {
               var10000x = var10000x <= 0 ? 1 : 0;
            }

            return var10000x != 0;
         }) ? 1 : 0;
      }

      return value == 0 ? entity : (LivingEntity)toList.get(this.switchIndex);
   }

   private double getSortValue(LivingEntity entity) {
      long longValue = KEY ^ 93462081681655L;
      String[] strings2 = getStrings();
      double doubleValue = PlayerUtil.getDistanceToLivingEntity(entity);
      String[] strings3 = strings2;
      int value = this.targetMode.getValue().equals(KillAuraTargetMode.SWITCH) ? 1 : 0;
      if (strings3 == null) {
         if (value != 0) {
            return doubleValue;
         }

         value = this.sortMode.getValue().ordinal();
      }

      if (strings3 == null) {
         switch (value) {
            case 0:
               return doubleValue;
            case 1:
               return entity.getMaxHealth() + entity.getHealth();
            case 2:
               value = entity.hurtTime;
               break;
            default:
               throw new MatchException(null, null);
         }
      }

      return value + doubleValue;
   }

   @Override
   public String getSuffix() {
      return this.sortMode.getDisplayValue();
   }

   public EnumSetting<CombatMode> getCombatMode() {
      return this.combatMode;
   }

   public EnumSetting<KillAuraTargetMode> getTargetMode() {
      return this.targetMode;
   }

   public DoubleSetting getMinCps() {
      return this.minCps;
   }

   public DoubleSetting getMaxCps() {
      return this.maxCps;
   }

   public DoubleSetting getSwitchDelay() {
      return this.switchDelay;
   }

   public DoubleSetting getRotationSpeed() {
      return this.rotationSpeed;
   }

   public EnumSetting<TargetSortMode> getSortMode() {
      return this.sortMode;
   }

   public EnumSetting<LagMode> getLagMode() {
      return this.lagMode;
   }

   public DoubleSetting getRange() {
      return this.E;
   }

   public BooleanSetting getThroughWalls() {
      return this.throughWalls;
   }

   public BooleanSetting getRaycast() {
      return this.raycast;
   }

   public TargetsSetting getTargets() {
      return this.targets;
   }

   public RotationHitResult getRotationHit() {
      return this.rotationHit;
   }

   public int getHitCount() {
      return this.hitCount;
   }

   public int getSwitchIndex() {
      return this.switchIndex;
   }

   public int getSwitchCooldown() {
      return this.switchCooldown;
   }

   public double getAttackDelay() {
      return this.attackDelay;
   }

   public DamageTracker getDamageTracker() {
      return this.damageTracker;
   }

   public int getLagTicks() {
      return this.lagTicks;
   }

   public PacketBlocker getPacketBlocker() {
      return this.packetBlocker;
   }

   public LivingEntity getTarget() {
      return this.target;
   }

   public LivingEntity getLastTarget() {
      return this.lastTarget;
   }

   public boolean isUsingItem() {
      return this.usingItem;
   }

   public boolean isLagging() {
      return this.lagging;
   }

   public static void setStrings(String[] strings2) {
      strings = strings2;
   }

   public static String[] getStrings() {
      return strings;
   }

   static {
      // NOTE: these values used to be injected by the native library (Loader.attachNatives). They were
      // recovered verbatim by running the original class against the still-shipped x64-windows.dll, so
      // the table below is the real one, not an approximation:
      //   STRINGS_TABLE = [Rotation Speed, Auto Block Range, Target Mode, Max CPS, Sort Mode, Through Walls,
      //                    Click Mode, Raycast, Auto Block Mode, Min CPS, <description>, <display name>, Switch Delay]
      //   (int)SWITCH_COOLDOWN == 2 ticks, and decryptLong's only live call site (index 7417 in
      //   isDamageReady) resolves to encryptedConstants[2], which decrypts to 470 (ms).
      KEY = 41987737433221L;
      STRINGS_TABLE = new String[]{
         "Rotation Speed",
         "Auto Block Range",
         "Target Mode",
         "Max CPS",
         "Sort Mode",
         "Through Walls",
         "Click Mode",
         "Raycast",
         "Auto Block Mode",
         "Min CPS",
         "Attacks your enemies automatically.",
         "Kill Aura",
         "Switch Delay"
      };
      SWITCH_COOLDOWN = 3536320460566298626L;
      encryptedConstants = new long[]{2974467899568203146L, 5646804534538139990L, 7629177672002971423L, 908469505462228316L};
      decryptedConstants = new Long[4];
      cipherCache = new ConcurrentHashMap<>();
      Loader.attachNatives(2, KillAura.class);
      Hidden0.special_clinit_2_530(KillAura.class);
   }

   private static Exception identity(Exception exception) {
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

   private static long decryptLong(int index, long key) {
      int value = index ^ (int)(key & 32767L) ^ 25861;
      // NOTE: the encrypted tables are no longer supplied by the native library, so an index that falls
      // outside the recovered table (or a slot that was never filled in) degrades to 0L instead of
      // throwing. The only live call site (isDamageReady) uses this as a millisecond threshold, so a
      // small non-negative default simply means "no delay gate" rather than a crash.
      if (encryptedConstants == null || decryptedConstants == null || cipherCache == null) {
         return 0L;
      }

      if (value < 0 || value >= encryptedConstants.length || value >= decryptedConstants.length) {
         return 0L;
      }

      Long cached = decryptedConstants[value];
      if (cached != null) {
         return cached;
      }

      if (encryptedConstants[value] == 0L) {
         return 0L;
      }

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
      Object[] values = (Object[])cipherCache.get(threadId);

      byte[] bytes3;
      try {
         if (values == null) {
            values = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
            cipherCache.put(threadId, values);
         }

         DESKeySpec dESKeySpec = new DESKeySpec(bytes);
         SecretKey generateSecret = ((SecretKeyFactory)values[1]).generateSecret(dESKeySpec);
         Cipher cipher = (Cipher)values[0];
         cipher.init(2, generateSecret, (IvParameterSpec)values[2]);
         bytes3 = cipher.doFinal(bytes2);
      } catch (Exception exception) {
         // NOTE: a missing/unsupported cipher degrades to the same safe default instead of propagating,
         // because this helper is only used for a timing threshold.
         return 0L;
      }

      long longValue2 = (bytes3[0] & 255L) << 56
         | (bytes3[1] & 255L) << 48
         | (bytes3[2] & 255L) << 40
         | (bytes3[3] & 255L) << 32
         | (bytes3[4] & 255L) << 24
         | (bytes3[5] & 255L) << 16
         | (bytes3[6] & 255L) << 8
         | bytes3[7] & 255L;
      decryptedConstants[value] = longValue2;
      return longValue2;
   }

   private static long decryptLongBootstrap(Lookup lookup, MutableCallSite callSite, String name, Object[] args) {
      int value = (Integer)args[0];
      long longValue = (Long)args[1];
      long longValue2 = decryptLong(value, longValue);
      MethodHandle constant = MethodHandles.constant(long.class, longValue2);
      callSite.setTarget(MethodHandles.dropArguments(constant, 0, int.class, long.class));
      return longValue2;
   }

   private static CallSite decryptLongSiteFactory(Lookup lookup, String name, MethodType type) {
      MutableCallSite mutableCallSite = new MutableCallSite(type);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(
                  lookup.findStatic(
                        KillAura.class,
                        "decryptLongBootstrap",
                        MethodType.methodType(long.class, Lookup.class, MutableCallSite.class, String.class, Object[].class)
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
         throw new RuntimeException("club/evon/module/impl/combat/KillAura : " + name + " : " + type.toString(), exception);
      }
   }
}
