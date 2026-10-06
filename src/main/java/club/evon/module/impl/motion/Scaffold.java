package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.InteractionSide;
import club.evon.data.PlacementResult;
import club.evon.data.PlacementTarget;
import club.evon.data.RotationHitResult;
import club.evon.data.ScaffoldSwitchMode;
import club.evon.data.ServerMode;
import club.evon.data.SlotSwitchMode;
import club.evon.event.BlockPlaceEvent;
import club.evon.event.ClientTickEvent;
import club.evon.event.InputEvent;
import club.evon.event.MotionEvent;
import club.evon.event.MovementInputEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.manager.KeybindManager;
import club.evon.manager.ModuleManager;
import club.evon.manager.RotationManager;
import club.evon.manager.SilentSlotManager;
import club.evon.mixins.ClientPlayerEntityAccessor;
import club.evon.mixins.LivingEntityAccessor;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.util.ItemUtils;
import club.evon.util.KeyBindingState;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.RotationUtil;
import club.evon.util.SimulatedPlayer;
import club.evon.util.math.MovementUtil;




public final class Scaffold extends BaseModule {
   private final EnumSetting<ServerMode> serverMode;
   private final EnumSetting<ScaffoldSwitchMode> switchMode;
   private final EnumSetting<InteractionSide> swingMode;
   private final DoubleSetting rotationSpeed;
   private final BooleanSetting keepY;
   private final BooleanSetting autoJump;
   private final BooleanSetting raycast;
   public PlacementResult placementResult;
   private int groundY;
   private RotationHitResult rotationHit;
   private Map<Integer, Integer> slotCounts;
   private int lastPlaceTick;
   private static BaseModule[] t;
   private static final long STRING_KEY = KeyedRandom.create(3751320022054999890L, 7520342242938223979L, MethodHandles.lookup().lookupClass()).next(281353567190965L);
   private static final String[] STRINGS;

   public Scaffold() {
      super((STRINGS)[0], (STRINGS)[6], ModuleCategory.MOTION);

      String[] strings = STRINGS;
      this.serverMode = new EnumSetting<>(strings[4], club.evon.data.ServerMode.HYPIXEL);
      this.switchMode = new EnumSetting<>(strings[8], club.evon.data.ScaffoldSwitchMode.CLIENT);
      this.swingMode = new EnumSetting<>(strings[2], InteractionSide.CLIENT);
      this.rotationSpeed = new DoubleSetting(strings[7], 5.0, 1.0, 10.0, 0.1);
      this.keepY = new BooleanSetting(strings[5], true);
      this.autoJump = new BooleanSetting(strings[1], true);
      this.raycast = new BooleanSetting(strings[3], true);
      this.rotationSpeed.setVisible(() -> {
         long var1x = STRING_KEY ^ 101511242052405L;
         BaseModule[] values = z();
         boolean equals = this.serverMode.getValue().equals(club.evon.data.ServerMode.HYPIXEL);
         if (values == null) {
            equals = !equals;
         }

         return equals;
      });
      this.addSettings(this.serverMode, this.switchMode, this.swingMode, this.rotationSpeed, this.keepY, this.autoJump, this.raycast);
      }

   @Override
   public void onDisable() {
      this.slotCounts = null;
      this.lastPlaceTick = 0;
      super.onDisable();
   }

   @Override
   public void onEnable() {
      long longValue = STRING_KEY ^ 97863648613711L;
      BaseModule[] values = z();
      super.onEnable();
      BaseModule[] values2 = values;
      this.placementResult = null;
      this.rotationHit = null;
      this.slotCounts = new HashMap<>();
      if (values2 == null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         this.groundY = MathHelper.floor(ClientContext.MINECRAFT_CLIENT.player.getY());
      }

      if (values2 != null) {
         BaseModule.setStateArray(new BaseModule[2]);
      }
   }

   @Subscribe
   public void onBlockPlace(BlockPlaceEvent event) {
      long longValue = STRING_KEY ^ 69623145901178L;
      BaseModule[] values = z();
      boolean creative = ClientContext.MINECRAFT_CLIENT.interactionManager.getCurrentGameMode().isCreative();
      int value;
      if (values == null) {
         if (creative) {
            return;
         }

         value = ClientContext.MINECRAFT_CLIENT.player.getInventory().getSelectedSlot();
      } else {
         value = creative ? 1 : 0;
      }

      this.slotCounts.put(value, this.slotCounts.getOrDefault(value, ClientContext.MINECRAFT_CLIENT.player.getMainHandStack().getCount() + 1) - 1);
   }

   public boolean isHypixel() {
      long longValue = STRING_KEY ^ 5345861253333L;
      return this.serverMode.getValue().equals(club.evon.data.ServerMode.HYPIXEL) && ClientContext.MINECRAFT_CLIENT.player != null;
   }

   @Subscribe
   public void onMotion(MotionEvent event) {
      BaseModule[] values;
      MinecraftClient minecraftClient;
      label41: {
         long longValue = STRING_KEY ^ 107134935025791L;
         values = z();
         boolean equals = this.serverMode.getValue().equals(club.evon.data.ServerMode.HYPIXEL);
         if (values == null) {
            if (!equals) {
               return;
            }

            minecraftClient = ClientContext.MINECRAFT_CLIENT;
            if (values != null) {
               break label41;
            }

            equals = ClientContext.MINECRAFT_CLIENT.options.sneakKey.isPressed();
         }

         if (equals) {
            return;
         }

         minecraftClient = ClientContext.MINECRAFT_CLIENT;
      }

      ClientPlayerEntity player = minecraftClient.player;
      if (values == null) {
         if (minecraftClient.player.getPose() == EntityPose.CROUCHING) {
            ClientContext.MINECRAFT_CLIENT.player.setPose(EntityPose.STANDING);
         }

         player = ClientContext.MINECRAFT_CLIENT.player;
      }

      ClientPlayerEntityAccessor player2 = (ClientPlayerEntityAccessor)player;
      player2.setInSneakingPose(false);
   }

   @Subscribe(h = 1)
   public void onMovementInput(MovementInputEvent event) {
      BaseModule[] values;
      boolean tmp;
      long longValue = STRING_KEY ^ 34816335005651L;
      values = z();
      tmp = this.keepY.I();
      label62:
      if (values == null) {
         if (tmp) {
            tmp = this.autoJump.I();
            if (values != null) {
               break label62;
            }

            if (tmp) {
               ((LivingEntityAccessor)ClientContext.MINECRAFT_CLIENT.player).setJumpingCooldown(0);
               event.setJump(true);
            }
         }

         tmp = this.serverMode.getValue().equals(club.evon.data.ServerMode.HYPIXEL);
      }

      if (values == null) {
         if (!tmp) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.player.isOnGround();
      }

      if (values == null) {
         if (!tmp) {
            return;
         }

         tmp = event.isJump();
      }

      if (values == null) {
         if (!tmp) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.player.isSneaking();
      }

      if (!tmp) {
         event.setSneak(true);
      }
   }

   @Subscribe(h = 1)
   public void onInput(InputEvent event) {
      BaseModule[] values;
      boolean tmp;
      long longValue = STRING_KEY ^ 116566857341849L;
      values = z();
      tmp = ClientContext.MINECRAFT_CLIENT.player.getMainHandStack().getItem() instanceof BlockItem;
      label106:
      if (values == null) {
         if (!tmp) {
            tmp = ClientContext.MINECRAFT_CLIENT.player.getOffHandStack().getItem() instanceof BlockItem;
            if (values != null) {
               break label106;
            }

            if (!tmp) {
               tmp = false;
               break label106;
            }
         }

         tmp = true;
      }

      label109: {
         boolean flag = (boolean)tmp;
         Scaffold scaffold = this;
         if (values == null) {
            if (this.placementResult == null) {
               break label109;
            }

            scaffold = this;
         }

         label91: {
            Object object = scaffold.rotationHit;
            if (values == null) {
               if (scaffold.rotationHit != null) {
                  tmp = this.raycast.I();
                  if (values != null) {
                     break label91;
                  }

                  if (!tmp) {
                     ClientContext.MINECRAFT_CLIENT.crosshairTarget = this.rotationHit.hitResult();
                  }
               }

               object = this.serverMode.getValue();
            }

            tmp = ((ServerMode)object).equals(club.evon.data.ServerMode.HYPIXEL);
         }

         int value = tmp ? 1 : 0;
         label83: {
            if (values == null) {
               if (value == 0) {
                  break label83;
               }

               value = ClientState.getInstance().airTicks;
            }

            if (value < 2) {
               return;
            }
         }

         label77: {
            Block targetBlock = PlayerUtil.getTargetBlock();
            boolean interactiveBlock = ItemUtils.isInteractiveBlock(targetBlock);
            if (values == null) {
               if (interactiveBlock) {
                  break label77;
               }

               interactiveBlock = flag;
            }

            if (interactiveBlock) {
               KeyBindingState useState = KeybindManager.getUseState();
               useState.queuePress();
               Scaffold scaffold2 = this;
               if (values == null) {
                  if (this.swingMode.getValue().equals(InteractionSide.SERVER)) {
                     useState.setShowSwings(false);
                  }

                  scaffold2 = this;
               }

               scaffold2.lastPlaceTick = ClientContext.MINECRAFT_CLIENT.player.age;
            }
         }

         if (values == null) {
            return;
         }
      }

      KeybindManager.getUseState().reset();
   }

   @Subscribe(h = 1)
   public void onTick(ClientTickEvent event) {
      long longValue = STRING_KEY ^ 104544772593026L;
      BaseModule[] values = z();
      int index = 0;
      int index2 = 0;
      int index3 = 0;
      Object object = null;
      if (values == null) {
         label324:
         if (ClientContext.MINECRAFT_CLIENT.player != null) {
            Object tmp = ClientContext.MINECRAFT_CLIENT.world;
            if (values == null) {
               if (ClientContext.MINECRAFT_CLIENT.world == null) {
                  break label324;
               }

               tmp = this.serverMode.getValue();
            }

            index = ((ServerMode)tmp).equals(club.evon.data.ServerMode.HYPIXEL) ? 1 : 0;
            label314:
            if (values == null) {
               if (index != 0) {
                  index = ClientContext.MINECRAFT_CLIENT.options.sneakKey.isPressed() ? 1 : 0;
                  if (values != null) {
                     break label314;
                  }

                  if (index == 0) {
                     ClientPlayerEntity player = ClientContext.MINECRAFT_CLIENT.player;
                     if (values == null) {
                        if (ClientContext.MINECRAFT_CLIENT.player.getPose() == EntityPose.CROUCHING) {
                           ClientContext.MINECRAFT_CLIENT.player.setPose(EntityPose.STANDING);
                        }

                        player = ClientContext.MINECRAFT_CLIENT.player;
                     }

                     ClientPlayerEntityAccessor player2 = (ClientPlayerEntityAccessor)player;
                     player2.setInSneakingPose(false);
                  }
               }

               index = this.findBlockSlot();
            }

            int value;
            value = index;
            index2 = value;
            label305:
            if (values == null) {
               if (value == -1) {
                  Item item = ClientContext.MINECRAFT_CLIENT.player.getOffHandStack().getItem();
                  Item item2 = item;
                  if (values == null) {
                     if (!(item instanceof BlockItem)) {
                        return;
                     }

                     item2 = item;
                  }

                  BlockItem blockItem = (BlockItem)item2;
                  index2 = ItemUtils.isPlaceableBlock(blockItem.getBlock()) ? 1 : 0;
                  if (values != null) {
                     break label305;
                  }

                  if (index2 == 0) {
                     return;
                  }
               }

               index2 = this.switchMode.getValue().equals(club.evon.data.ScaffoldSwitchMode.SERVER) ? 1 : 0;
            }

            SlotSwitchMode slotSwitchMode = index2 != 0 ? SlotSwitchMode.FULL : SlotSwitchMode.DEFAULT;
            SilentSlotManager.getInstance().switchTo(value).setMode(slotSwitchMode);
            ModuleManager moduleManager = EvonClient.getInstance().getModuleManager();
            index3 = this.keepY.I() ? 1 : 0;
            label290:
            if (values == null) {
               if (index3 != 0) {
                  index3 = ClientContext.MINECRAFT_CLIENT.options.useKey.isPressed() ? 1 : 0;
                  if (values != null) {
                     break label290;
                  }

                  label286:
                  if (index3 == 0) {
                     index3 = this.autoJump.I() ? 1 : 0;
                     if (values == null) {
                        if (index3 != 0) {
                           index3 = PlayerUtil.isBindingPressed(ClientContext.MINECRAFT_CLIENT.options.jumpKey) ? 1 : 0;
                           if (values != null) {
                              break label290;
                           }

                           if (index3 != 0) {
                              break label286;
                           }
                        }

                        index3 = ClientContext.MINECRAFT_CLIENT.player.isOnGround() ? 1 : 0;
                     }

                     if (values != null) {
                        break label290;
                     }

                     if (index3 == 0) {
                        double doubleValue;
                        index3 = (byte)((doubleValue = Math.abs(Math.floor(ClientContext.MINECRAFT_CLIENT.player.getY() - this.groundY)) - 2.0) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1));
                        if (values != null) {
                           break label290;
                        }

                        if (index3 <= 0) {
                           index3 = moduleManager.getModuleByClass(Flight.class).isEnabled() ? 1 : 0;
                           if (values != null) {
                              break label290;
                           }

                           if (index3 == 0) {
                              index3 = 0;
                              break label290;
                           }
                        }
                     }
                  }
               }

               index3 = 1;
            }

            byte byteValue = (byte)index3;
            int value2 = byteValue;
            if (values == null) {
               if (byteValue != 0) {
                  this.groundY = MathHelper.floor(ClientContext.MINECRAFT_CLIENT.player.getY());
               }

               value2 = this.serverMode.getValue().equals(club.evon.data.ServerMode.HYPIXEL) ? 1 : 0;
            }

            label264: {
               label263: {
                  label335: {
                     byte byteValue2 = (byte)value2;
                     value2 = byteValue2;
                     label261:
                     if (values == null) {
                        if (byteValue2 != 0) {
                           value2 = ClientContext.MINECRAFT_CLIENT.player.input.playerInput.jump() ? 1 : 0;
                           if (values != null) {
                              break label261;
                           }

                           if (value2 != 0) {
                              value2 = ClientContext.MINECRAFT_CLIENT.player.isOnGround() ? 1 : 0;
                              if (values == null) {
                                 if (value2 != 0) {
                                    break label335;
                                 }

                                 double doubleValue2;
                                 value2 = (byte)((doubleValue2 = ClientContext.MINECRAFT_CLIENT.player.getVelocity().getY() - 0.0) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1));
                              }

                              if (values != null) {
                                 break label261;
                              }

                              if (value2 < 0) {
                                 value2 = PlayerUtil.isFree(ClientContext.MINECRAFT_CLIENT.player.getBoundingBox().offset(0.0, ClientContext.MINECRAFT_CLIENT.player.getVelocity().getY(), 0.0)) ? 1 : 0;
                                 if (values != null) {
                                    break label261;
                                 }

                                 if (value2 == 0) {
                                    break label335;
                                 }
                              }
                           }
                        }

                        this.updatePlacement();
                        object = ClientContext.MINECRAFT_CLIENT.player.input.playerInput;
                        if (values != null) {
                           break label264;
                        }

                        value2 = ClientContext.MINECRAFT_CLIENT.player.input.playerInput.sneak() ? 1 : 0;
                     }

                     if (value2 == 0) {
                        break label263;
                     }

                     object = ClientContext.MINECRAFT_CLIENT.player;
                     if (values != null) {
                        break label264;
                     }

                     if ((int)(ClientContext.MINECRAFT_CLIENT.player.getY() + ClientContext.MINECRAFT_CLIENT.player.getVelocity().getY()) != (int)ClientContext.MINECRAFT_CLIENT.player.getY()) {
                        break label263;
                     }

                     label241: {
                        double doubleValue3;
                        value2 = (doubleValue3 = ClientContext.MINECRAFT_CLIENT.player.getVelocity().getY() - 0.2) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
                        if (values == null) {
                           if (value2 >= 0) {
                              break label241;
                           }

                           value2 = byteValue;
                        }

                        if (value2 != 0) {
                           break label263;
                        }
                     }

                     this.placementResult = null;
                     if (values == null) {
                        break label263;
                     }
                  }

                  this.placementResult = null;
                  this.rotationHit = null;
               }

               object = this.rotationSpeed.J();
            }

            float floatValue = ((Double)object).floatValue() * 18.0F;
            Scaffold scaffold = this;
            if (values == null) {
               if (this.serverMode.getValue().equals(club.evon.data.ServerMode.HYPIXEL)) {
                  byte byteValue3;
                  label220: {
                     label219: {
                        label339: {
                        floatValue = 40.0F;
                        value2 = ClientContext.MINECRAFT_CLIENT.options.jumpKey.isPressed() ? 1 : 0;
                           if (values == null) {
                              if (value2 != 0) {
                                 value2 = ClientState.getInstance().airTicks;
                                 byteValue3 = 7;
                                 if (values != null) {
                                    break label220;
                                 }

                                 if (value2 != 7) {
                                    break label219;
                                 }
                                 break label339;
                              }

                              value2 = ClientState.getInstance().airTicks;
                           }

                           byteValue3 = 10;
                           if (values != null) {
                              break label220;
                           }

                           if (value2 != 10) {
                              break label219;
                           }
                        }

                        floatValue = 180.0F;
                     }

                     value2 = ClientState.getInstance().airTicks;
                     byteValue3 = 1;
                  }

                  if (value2 == byteValue3) {
                     floatValue = 125.0F;
                  }
               }

               scaffold = this;
            }

            if (scaffold.rotationHit != null) {
               RotationManager.getRotationController().rotateTo(this.rotationHit.rotation(), floatValue);
            }

            return;
         }

         this.placementResult = null;
      }
   }

   private boolean isDiagonal() {
      long longValue = STRING_KEY ^ 85747463032264L;
      float floatValue = Math.abs(MovementUtil.getInputYaw() % 90.0F);
      BaseModule[] values = z();
      byte byteValue = 30;
      BaseModule[] values2 = values;
      float floatValue2;
      int value = (floatValue2 = floatValue - 15.0F) == 0.0F ? 0 : (floatValue2 < 0.0F ? -1 : 1);
      if (values2 == null) {
         if (value <= 0) {
            return false;
         }

         float floatValue3;
         value = (floatValue3 = floatValue - 75.0F) == 0.0F ? 0 : (floatValue3 < 0.0F ? -1 : 1);
      }

      return (boolean)(values2 != null ? value : value < 0);
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent event) {
      long longValue = STRING_KEY ^ 8918285749783L;
      BaseModule[] values = z();
      Packet packet = event.getPacket();
      BaseModule[] values2 = values;
      Packet packet2 = packet;
      if (values2 == null) {
         if (!(packet instanceof ItemPickupAnimationS2CPacket)) {
            return;
         }

         packet2 = packet;
      }

      ItemPickupAnimationS2CPacket packet3 = (ItemPickupAnimationS2CPacket)packet2;
      if (ClientContext.MINECRAFT_CLIENT.player != null) {
         int value = packet3.getCollectorEntityId();
         if (values2 == null) {
            if (value != ClientContext.MINECRAFT_CLIENT.player.getId()) {
               return;
            }

            value = ClientContext.MINECRAFT_CLIENT.player.getInventory().getSelectedSlot();
         }

         int value2 = value;
         this.slotCounts.put(value2, this.slotCounts.getOrDefault(value2, ClientContext.MINECRAFT_CLIENT.player.getMainHandStack().getCount() - packet3.getStackAmount()) + packet3.getStackAmount());
      }
   }

   private int findBlockSlot() {
      long longValue = STRING_KEY ^ 111160648227611L;
      BaseModule[] values = z();
      int index = 0;
      int index2 = 0;
      BaseModule[] values2 = values;

      while (true) {
         if (index < 9) {
            ItemStack stack = (ItemStack)ClientContext.MINECRAFT_CLIENT.player.getInventory().getMainStacks().get(index);
            Item item = stack.getItem();
            label44:
            if (values2 == null) {
               index2 = item instanceof BlockItem ? 1 : 0;
               if (values2 != null) {
                  break;
               }

               if (index2 != 0) {
                  BlockItem blockItem = (BlockItem)item;
                  if (values2 != null) {
                     break label44;
                  }

                  if (this.slotCounts.getOrDefault(index, stack.getCount()) > 0) {
                     int value = ItemUtils.isPlaceableBlock(blockItem.getBlock()) ? 1 : 0;
                     if (values2 != null) {
                        return value;
                     }

                     if (value != 0) {
                        return index;
                     }
                  }
               }

               index++;
            }

            if (values2 == null) {
               continue;
            }
         }

         index2 = -1;
         break;
      }

      return index2;
   }

   private void updatePlacement() {
      long longValue = STRING_KEY ^ 116234435536740L;
      BaseModule[] values = z();
      this.placementResult = this.findPlacementBelow();
      BaseModule[] values2 = values;
      Scaffold scaffold = this;
      if (values2 == null) {
         if (this.placementResult == null) {
            SimulatedPlayer simulatedPlayer = new SimulatedPlayer(ClientContext.MINECRAFT_CLIENT.player);
            OtherClientPlayerEntity fakePlayer = simulatedPlayer.getFakePlayer();
            int index = 0;

            while (index < 10) {
               simulatedPlayer.tick();
                  PlacementResult findPlacementAt = this.findPlacementAt(fakePlayer.getBlockPos().down(), fakePlayer);
                  if (values2 == null) {
                     if (findPlacementAt != null) {
                        this.rotationHit = findPlacementAt.getHitResult();
                     if (values2 == null) {
                        break;
                     }
                  }

                  index++;
               }

               if (values2 != null) {
                  break;
               }
            }

            return;
         }

         scaffold = this;
      }

      scaffold.rotationHit = this.placementResult.getHitResult();
   }

   private RotationHitResult computeRotationHit(PlacementTarget target, Vec3d eyePos) {
      Vec2f pos;
      label17: {
         long longValue = STRING_KEY ^ 69031304406775L;
         BaseModule[] values = z();
         RotationHitResult tmp = this.rotationHit;
         if (values == null) {
            if (this.rotationHit == null) {
               pos = RotationUtil.getPlayerRotation();
               break label17;
            }

            tmp = this.rotationHit;
         }

         pos = tmp.rotation();
      }

      Vec2f pos2 = pos;
      return RotationUtil.findRotationToBlock(target.getPos(), target.getDirection(), pos2, eyePos);
   }

   private PlacementResult findPlacementBelow() {
      return this.findPlacementAt(ClientContext.MINECRAFT_CLIENT.player.getBlockPos().withY(this.groundY).down(), ClientContext.MINECRAFT_CLIENT.player);
   }

   private PlacementResult findPlacementAt(BlockPos origin, PlayerEntity player) {
      long longValue = STRING_KEY ^ 13155191969379L;
      BaseModule[] values = z();
      if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(origin).isReplaceable()) {
         Mutable mutable = new Mutable();
         ArrayList<PlacementTarget> list = new ArrayList<>();
         byte byteValue = 5;
         int index = 0;
         boolean flag = false;

         ArrayList<PlacementTarget> list2;
         label132: {
            label125:
            while (true) {
               int value = index;

               label122:
               while (value > -byteValue) {
                  flag = false;
                  if (values != null) {
                     break label125;
                  }

                  int index2 = 0;

                  label119:
                  while (true) {
                     int value2 = index2;

                     label117:
                     while (true) {
                        if (value2 >= byteValue) {
                           break label119;
                        }

                        value = 0;
                        if (values != null) {
                           continue label122;
                        }

                        int index3 = 0;

                        label114:
                        while (true) {
                           int value3 = index3;

                           label112:
                           while (true) {
                              if (value3 >= byteValue) {
                                 break label114;
                              }

                              int[] values2 = new int[]{1, -1};
                              int length = values2.length;
                              value2 = 0;
                              if (values != null) {
                                 continue label117;
                              }

                              int index4 = 0;

                              label109:
                              while (true) {
                                 int value4 = index4;

                                 label107:
                                 while (true) {
                                    if (value4 >= length) {
                                       break label109;
                                    }

                                    int value5 = values2[index4];
                                    mutable.set(origin.getX() + index2 * value5, origin.getY() + index * value5, origin.getZ() + index3 * value5);
                                    value3 = ClientContext.MINECRAFT_CLIENT.world.getBlockState(mutable).isReplaceable() ? 1 : 0;
                                    if (values != null) {
                                       continue label112;
                                    }

                                    if (value3 != 0) {
                                       Direction[] values3 = Direction.values();
                                       int length2 = values3.length;
                                       int index5 = 0;

                                       while (index5 < length2) {
                                          Direction direction = values3[index5];
                                          BlockPos offset = mutable.offset(direction);
                                          if (values == null) {
                                             value4 = ClientContext.MINECRAFT_CLIENT.world.getBlockState(offset).isReplaceable() ? 1 : 0;
                                             if (values != null) {
                                                continue label107;
                                             }

                                             if (value4 == 0) {
                                                list.add(new PlacementTarget(offset, direction.getOpposite()));
                                             }

                                             index5++;
                                          }

                                          if (values != null) {
                                             break;
                                          }
                                       }
                                    }

                                    index4++;
                                    if (values != null) {
                                       break label109;
                                    }
                                    break;
                                 }
                              }

                              index3++;
                              if (values != null) {
                                 break label114;
                              }
                              break;
                           }
                        }

                        index2++;
                        if (values != null) {
                           break label119;
                        }
                        break;
                     }
                  }

                  index--;
                  if (values == null) {
                     continue label125;
                  }
                  break;
               }

               list2 = list;
               if (values != null) {
                  break label132;
               }

               flag = list.isEmpty();
               break;
            }

            if (flag) {
               return null;
            }

            list2 = list;
         }

         list2.sort(Comparator.comparingDouble(var1x -> var1x.getPos().offset(var1x.getDirection()).getSquaredDistance(origin)));
         Vec3d add = player.getEntityPos().add(0.0, player.getStandingEyeHeight(), 0.0);

         for (PlacementTarget placementTarget : list) {
            RotationHitResult computeRotationHit = this.computeRotationHit(placementTarget, add);
            if (computeRotationHit != null) {
               return new PlacementResult(placementTarget, computeRotationHit);
            }

            if (values != null) {
               break;
            }
         }
      }

      return null;
   }

   public static void X_r2(BaseModule[] values) {
      t = values;
   }

   public static BaseModule[] z() {
      return t;
   }

   static {
      String[] var31 = null;
      try {
      long var0 = STRING_KEY ^ 131830794415927L;
      X_r2(null);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[9];
      int var7 = 0;
      String var6 = "\u0084\u0006c\u00ed\u00e6\u0011\u00a5_5\u0007\u00c7\u00d9\u00b6\u0089\u00d5A\u0010\u00fek\u0099\u00cbs\u00f3et\\w\u00deg\u00a5l\u00eaq\u0010\u00dc\u00cf\u00ce\u009e5\u00d1\u00cb\u00f9\u0082\u0006\u0089\u008f\u0011\u001a\u00d8-\b\fnp>\u00d4\u00d5\u00df\u00d6\b\u00ed\u00e3\u00b9U\u000f\u00e5\u00e1)\b\u00da\u00b7S3\u001e\u00db\u0095 (\u0003X\u001b\u00c2Q\u00ec\u00ac\u00c3\u00f0@r\u00df\u00b3a\u0090<y\u0006\u00d8\u00b1\u00aeH\b@\u00ff\u00eb\u00a5\u00b0\u00dd\u00ecN[\u00a8\u00a9\u00b3\u0089\u000e\u0001\u00e1*";
      int var8 = "\u0084\u0006c\u00ed\u00e6\u0011\u00a5_5\u0007\u00c7\u00d9\u00b6\u0089\u00d5A\u0010\u00fek\u0099\u00cbs\u00f3et\\w\u00deg\u00a5l\u00eaq\u0010\u00dc\u00cf\u00ce\u009e5\u00d1\u00cb\u00f9\u0082\u0006\u0089\u008f\u0011\u001a\u00d8-\b\fnp>\u00d4\u00d5\u00df\u00d6\b\u00ed\u00e3\u00b9U\u000f\u00e5\u00e1)\b\u00da\u00b7S3\u001e\u00db\u0095 (\u0003X\u001b\u00c2Q\u00ec\u00ac\u00c3\u00f0@r\u00df\u00b3a\u0090<y\u0006\u00d8\u00b1\u00aeH\b@\u00ff\u00eb\u00a5\u00b0\u00dd\u00ecN[\u00a8\u00a9\u00b3\u0089\u000e\u0001\u00e1*"
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
                     var31 = var9;
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

                  var6 = "\u00b7g\u00a2\u008a+\u001f\u0097\u0097\u009e\n\u00d0\u00ebD[:J\u0010\u000e\u00a2\u00b6\u0006\u00cf\u0012]\u0090\u0014\u00e9fw\u0092;0'";
                  var8 = "\u00b7g\u00a2\u008a+\u001f\u0097\u0097\u009e\n\u00d0\u00ebD[:J\u0010\u000e\u00a2\u00b6\u0006\u00cf\u0012]\u0090\u0014\u00e9fw\u0092;0'"
                     .length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var30) {
         Scaffold.<RuntimeException>sneakyThrow(var30);
      }
      STRINGS = var31;
   }

   private static <T extends Throwable> void sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
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
