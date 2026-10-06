package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.hypixel.data.type.GameType;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.HypixelTargetFilter;
import club.evon.api.HypixelLocationManager;
import club.evon.data.BlockTarget;
import club.evon.data.ClientServerSide;
import club.evon.data.HypixelLocation;
import club.evon.data.SlotSwitchMode;
import club.evon.data.TeamColor;
import club.evon.event.CancelBlockBreakingEvent;
import club.evon.event.ClientTickEvent;
import club.evon.event.PostTickEvent;
import club.evon.event.Subscribe;
import club.evon.event.SwingHandEvent;
import club.evon.manager.ClientState;
import club.evon.manager.KeybindManager;
import club.evon.manager.RotationManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.BreakerMode;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.util.BlockBreakingAccessor;
import club.evon.util.ItemUtils;
import club.evon.util.KeyedRandom;
import club.evon.util.RaycastUtil;
import club.evon.util.RotationUtil;
import club.evon.util.math.ScoredBlockPos;




public final class Breaker extends BaseModule {
   private static final Direction[] DIRECTIONS;
   private final EnumSetting<BreakerMode> mode;
   private final DoubleSetting rotationSpeed;
   private final EnumSetting<ClientServerSide> swingMode;
   private final BooleanSetting surroundings;
   private final BooleanSetting onlyRotateIfNeeded;
   private BlockTarget target;
   private Vec2f targetRotation;
   private boolean breaking;
   private boolean rotating;
   private boolean cancelSwing;
   private int breakTicks;
   private int targetSlot;
   private long lastBedBreak;
   private static boolean k;
   private static final long STRING_KEY = KeyedRandom.create(4849781885061679935L, -7761065447016200813L, MethodHandles.lookup().lookupClass()).next(191033834550191L);
   private static final String[] STRINGS;
   private static final long BED_BREAK_COOLDOWN;

   public Breaker() {
      super((STRINGS)[1], (STRINGS)[3], ModuleCategory.PLAYER);

      String[] strings = STRINGS;
      this.mode = new EnumSetting<>(strings[2], BreakerMode.BED);
      this.rotationSpeed = new DoubleSetting(strings[6], 360.0, 10.0, 360.0, 10.0);
      this.swingMode = new EnumSetting<>(strings[0], ClientServerSide.CLIENT);
      this.surroundings = new BooleanSetting(strings[5], true);
      this.onlyRotateIfNeeded = new BooleanSetting(strings[4], false);
      this.addSettings(this.mode, this.swingMode, this.rotationSpeed, this.surroundings, this.onlyRotateIfNeeded);
      }

   @Override
   public void onDisable() {
      this.breaking = this.rotating = false;
      this.target = null;
      this.targetSlot = -1;
      super.onDisable();
   }

   @Subscribe
   private void onTick(ClientTickEvent event) {
      long longValue = STRING_KEY ^ 21980522701566L;
      boolean flag = a();
      int value;
      MinecraftClient minecraftClient;
      Breaker tmp = this;
      if (flag) {
         if (!this.canBreak()) {
            this.breaking = false;
            this.rotating = false;
            this.targetRotation = null;
            return;
         }

         this.updateTarget();
         tmp = this;
      }

      if (flag) {
         if (tmp.target != null && !(ClientContext.MINECRAFT_CLIENT.world.getBlockState(this.target.target().getPos()).getBlock() instanceof AirBlock)) {
            BlockPos pos;
            label126: {
               label125: {
                  label136: {
                     pos = this.target.target().getPos();
                     BlockBreakingAccessor blockBreakingAccessor = (BlockBreakingAccessor)ClientContext.MINECRAFT_CLIENT.interactionManager;
                     float floatValue = ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).calcBlockBreakingDelta(ClientContext.MINECRAFT_CLIENT.player, ClientContext.MINECRAFT_CLIENT.world, pos);
                     float floatValue2 = blockBreakingAccessor.evon$currentBreakingProgress() + floatValue;
                     this.targetRotation = RotationUtil.getRotationTo(pos.toCenterPos());
                  double doubleValue = floatValue2 + floatValue;
                  value = this.onlyRotateIfNeeded.I() ? 1 : 0;
                     label123:
                     if (flag) {
                        if (value != 0) {
                           double doubleValue2;
                           value = (doubleValue2 = doubleValue - 1.0) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);
                           label118:
                           if (flag) {
                              if (value < 0) {
                                 double doubleValue3;
                                 value = (doubleValue3 = floatValue2 - floatValue - 0.6) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
                                 if (!flag) {
                                    break label118;
                                 }

                                 if (value < 0) {
                                    break label136;
                                 }
                              }

                              double doubleValue4;
                              value = (doubleValue4 = doubleValue - (Double.MAX_VALUE)) == 0.0 ? 0 : (doubleValue4 < 0.0 ? -1 : 1);
                           }

                           if (!flag) {
                              break label123;
                           }

                           if (value >= 0) {
                              break label136;
                           }
                        }

                        RotationManager.getRotationController().rotateTo(this.targetRotation, this.rotationSpeed.J().floatValue());
                        this.rotating = true;
                        tmp = this;
                        if (!flag) {
                           break label126;
                        }

                        value = this.targetSlot;
                     }

                     if (value == -1) {
                        break label125;
                     }

                     SilentSlotManager.getInstance().switchTo(this.targetSlot).setMode(SlotSwitchMode.FULL);
                     if (flag) {
                        break label125;
                     }
                  }

                  this.rotating = false;
               }

               tmp = this;
            }

            BlockHitResult raycastTarget = tmp.raycastTarget();
            BlockHitResult blockHitResult = raycastTarget;
            if (flag) {
               if (raycastTarget == null) {
                  return;
               }

               blockHitResult = raycastTarget;
            }

            label140: {
               Direction side = blockHitResult.getSide();
               boolean flag2 = this.breaking;
               if (flag) {
                  if (!this.breaking) {
                     if (!ClientContext.MINECRAFT_CLIENT.interactionManager.attackBlock(pos, side)) {
                        return;
                     }

                     this.breakTicks = (int)(ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getHardness(ClientContext.MINECRAFT_CLIENT.world, pos) * 20.0F);
                     this.breaking = true;
                  }

                  minecraftClient = ClientContext.MINECRAFT_CLIENT;
                  if (!flag) {
                     break label140;
                  }

                  flag2 = ClientContext.MINECRAFT_CLIENT.interactionManager.updateBlockBreakingProgress(pos, side);
               }

               if (!flag2) {
                  return;
               }

               KeybindManager.getUseState().reset();
               KeybindManager.getAttackState().reset();
               this.breakTicks--;
               this.cancelSwing = this.swingMode.getValue().equals(ClientServerSide.SERVER);
               minecraftClient = ClientContext.MINECRAFT_CLIENT;
            }

            minecraftClient.player.swingHand(Hand.MAIN_HAND);
            return;
         }

         this.breaking = false;
         this.rotating = false;
         tmp = this;
      }

      tmp.targetRotation = null;
   }

   @Subscribe
   private void onPostTick(PostTickEvent event) {
      long longValue = STRING_KEY ^ 96023868306899L;
      boolean flag = A();
      if (this.breaking) {
         ClientPlayerEntity tmp = ClientContext.MINECRAFT_CLIENT.player;
         if (!flag) {
            if (ClientContext.MINECRAFT_CLIENT.player == null) {
               return;
            }

            this.cancelSwing = this.swingMode.getValue().equals(ClientServerSide.SERVER);
            tmp = ClientContext.MINECRAFT_CLIENT.player;
         }

         tmp.swingHand(Hand.MAIN_HAND);
         Breaker breaker = this;
         if (!flag) {
            if (this.breakTicks >= 0) {
               return;
            }

            this.breaking = false;
            this.rotating = false;
            breaker = this;
         }

         if (breaker.target != null && ClientContext.MINECRAFT_CLIENT.world.getBlockState(this.target.target().getPos()).getBlock() instanceof BedBlock) {
            this.lastBedBreak = System.currentTimeMillis();
         }
      }
   }

   @Subscribe
   private void onCancelBlockBreaking(CancelBlockBreakingEvent event) {
      long longValue = STRING_KEY ^ 5168303231584L;
      if (this.breaking) {
         event.cancel();
      }
   }

   @Subscribe
   private void onSwingHand(SwingHandEvent event) {
      long longValue = STRING_KEY ^ 128517651352907L;
      boolean flag = A();
      Breaker tmp = this;
      if (!flag) {
         if (!this.cancelSwing) {
            return;
         }

         tmp = this;
      }

      tmp.cancelSwing = false;
      event.cancel();
   }

   private void updateTarget() {
      boolean flag;
      float floatValue;
      int value;
      int value2;
      int value3;
      int value4;
      int value5;
      int value6;
      ArrayList<ScoredBlockPos> pos;
      TeamColor teamColor;
      int value7;
      label240: {
         long longValue = STRING_KEY ^ 83223752427638L;
         boolean tmp = a();
         this.targetSlot = -1;
         Vec3d eyePos = ClientContext.MINECRAFT_CLIENT.player.getEyePos();
         floatValue = (float)ClientContext.MINECRAFT_CLIENT.player.getBlockInteractionRange();
         value = (int)Math.floor(eyePos.x - floatValue - 1.0);
         flag = tmp;
         value2 = (int)Math.floor(eyePos.y - floatValue - 1.0);
         value3 = (int)Math.floor(eyePos.z - floatValue - 1.0);
         value4 = (int)Math.ceil(eyePos.x + floatValue + 1.0);
         value5 = (int)Math.ceil(eyePos.y + floatValue + 1.0);
         value6 = (int)Math.ceil(eyePos.z + floatValue + 1.0);
         pos = new ArrayList();
         int value8 = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter ? 1 : 0;
         if (flag) {
            if (value8 == 0) {
               teamColor = null;
               break label240;
            }

            value8 = ClientContext.MINECRAFT_CLIENT.player.getTeamColorValue();
         }

         teamColor = TeamColor.fromTeamColor(value8);
      }

      TeamColor teamColor2 = teamColor;
      int value9 = value;

      label233:
      while (true) {
         int value10 = value9;

         label231:
         while (true) {
            if (value10 > value4) {
               break label233;
            }

            int value11 = value2;

            label228:
            while (true) {
               int value12 = value11;

               label226:
               while (true) {
                  if (value12 > value5) {
                     break label228;
                  }

                  value10 = value3;
                  if (!flag) {
                     continue label231;
                  }

                  int value13 = value3;

                  while (value13 <= value6) {
                     BlockPos blockPos = new BlockPos(value9, value11, value13);
                     BlockState blockState = ClientContext.MINECRAFT_CLIENT.world.getBlockState(blockPos);
                     Block block = blockState.getBlock();
                      if (flag) {
                         value12 = block instanceof BedBlock ? 1 : 0;
                        if (!flag) {
                           continue label226;
                        }

                        label210:
                        if (value12 != 0) {
                           label215: {
                              BedBlock bedBlock = (BedBlock)block;
                              teamColor = teamColor2;
                              if (flag) {
                                 if (teamColor2 == null) {
                                    break label215;
                                 }

                                 teamColor = teamColor2;
                              }

                              if (teamColor.mapColorId == bedBlock.getColor().getMapColor().id && flag) {
                                 break label210;
                              }
                           }

                           ScoredBlockPos scoredBlockPos = new ScoredBlockPos(blockPos);
                           pos.add(scoredBlockPos);
                           ScoredBlockPos offset = scoredBlockPos.offset(BedBlock.getOppositePartDirection(blockState));
                           pos.add(offset);
                        }

                        value13++;
                     }

                     if (!flag) {
                        break;
                     }
                  }

                  value11++;
                  if (!flag) {
                     break label228;
                  }
                  break;
               }
            }

            value9++;
            if (!flag) {
               break label233;
            }
            break;
         }
      }

      ScoredBlockPos orElse = pos.stream().filter(var1x -> {
         long longValue2 = STRING_KEY ^ 3244202414565L;
         boolean var4x = A();
         double var5x;
         int var10000x = (var5x = ((ScoredBlockPos)var1x).distance - floatValue) == 0.0 ? 0 : (var5x < 0.0 ? -1 : 1);
         if (!var4x) {
            var10000x = var10000x <= 0 ? 1 : 0;
         }

         return var10000x != 0;
      }).min(Comparator.comparingDouble(pos2 -> ((ScoredBlockPos)pos2).distance)).orElse(null);
      if (flag) {
         if (orElse != null) {
            Object object = this.surroundings.I();
            if (flag) {
               if (!((Boolean)object)) {
                  this.setTarget(new BlockTarget(orElse, 0.01));
                  return;
               }

               object = Arrays.stream(DIRECTIONS).map(orElse::offset).collect(Collectors.toList());
            }

            List list = (List)object;
            BlockState blockState2 = ClientContext.MINECRAFT_CLIENT.world.getBlockState(orElse.getPos());
            if (flag) {
               if (blockState2.getBlock() instanceof BedBlock) {
                  ScoredBlockPos offset2 = orElse.offset(BedBlock.getOppositePartDirection(blockState2));
                  Arrays.stream(DIRECTIONS).map(offset2::offset).forEach(list::add);
               }

               list = list.stream().filter(var1x -> {
                  long longValue3 = STRING_KEY ^ 101829877698448L;
                  boolean var4x = a();
                  double var5x;
                  int var10000x = (var5x = ((ScoredBlockPos)var1x).distance - floatValue) == 0.0 ? 0 : (var5x < 0.0 ? -1 : 1);
                  if (var4x) {
                     var10000x = var10000x <= 0 ? 1 : 0;
                  }

                  return var10000x != 0;
               }).sorted(Comparator.comparingDouble(object2 -> ((ScoredBlockPos)object2).distance)).toList();
            }

            Iterator iterator = list.iterator();

            while (true) {
               if (iterator.hasNext()) {
                  ScoredBlockPos pos3 = (ScoredBlockPos)iterator.next();
                  BlockState blockState3 = ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos3.getPos());
                  if (!flag) {
                     break;
                  }

                  if (blockState3.isAir() || !blockState3.getFluidState().isEmpty()) {
                     this.setTarget(new BlockTarget(orElse, 0.01));
                     break;
                  }

                  if (flag) {
                     continue;
                  }
               }

               ScoredBlockPos pos4 = null;
               double doubleValue = Float.MAX_VALUE;
               int value14 = -1;
               Iterator iterator2 = list.iterator();

               while (true) {
                  if (iterator2.hasNext()) {
                     ScoredBlockPos pos5 = (ScoredBlockPos)iterator2.next();
                     BlockState blockState4 = ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos5.getPos());
                     value7 = blockState4.getBlock() instanceof BedBlock ? 1 : 0;
                     if (!flag) {
                        break;
                     }

                     if (flag) {
                        if (value7 != 0 && flag) {
                           continue;
                        }

                        value7 = ItemUtils.findBestToolSlot(pos5.getPos());
                     }

                     int value15 = value7;
                     int value16 = value15;
                     if (flag) {
                        value16 = value15 == -1 ? ClientContext.MINECRAFT_CLIENT.player.getInventory().getSelectedSlot() : ItemUtils.findBestToolSlot(pos5.getPos());
                     }

                     int value17;
                     double doubleValue2;
                     value17 = value16;
                     double doubleValue3 = SilentSlotManager.getInstance().getMainHandStack(ClientContext.MINECRAFT_CLIENT.player).getMiningSpeedMultiplier(blockState4);
                     doubleValue2 = Math.max(0.01, blockState4.getHardness(ClientContext.MINECRAFT_CLIENT.world, pos5.getPos())) / doubleValue3;
                     label161:
                     if (!this.breaking) {
                        BlockBreakingAccessor blockBreakingAccessor = (BlockBreakingAccessor)ClientContext.MINECRAFT_CLIENT.interactionManager;
                        BlockPos evon$getCurrentBreakingPos = blockBreakingAccessor.evon$getCurrentBreakingPos();
                        BlockPos pos6 = evon$getCurrentBreakingPos;
                        if (flag) {
                           if (evon$getCurrentBreakingPos == null) {
                              break label161;
                           }

                           pos6 = evon$getCurrentBreakingPos;
                        }

                        if (pos6.equals(pos5.getPos())) {
                           doubleValue2 *= 1.0F - blockBreakingAccessor.evon$currentBreakingProgress();
                        }
                     }

                     label154: {
                        label153: {
                           ScoredBlockPos pos7 = pos4;
                           if (flag) {
                              if (pos4 != null) {
                                 double doubleValue4;
                                 value16 = (doubleValue4 = doubleValue2 - doubleValue) == 0.0 ? 0 : (doubleValue4 < 0.0 ? -1 : 1);
                                 if (!flag) {
                                    break label153;
                                 }

                                 if (value16 >= 0) {
                                    break label154;
                                 }
                              }

                              pos7 = pos5;
                           }

                           pos4 = pos7;
                           doubleValue = doubleValue2;
                           value16 = value17;
                        }

                        value14 = value16;
                     }

                     if (flag) {
                        continue;
                     }
                  }

                  if (pos4 == null) {
                     return;
                  }

                  long longValue4;
                  value7 = (longValue4 = System.currentTimeMillis() - this.lastBedBreak - BED_BREAK_COOLDOWN) == 0L ? 0 : (longValue4 < 0L ? -1 : 1);
                  break;
               }

               if (value7 < 0) {
                  this.target = null;
                  return;
               }

               this.targetSlot = value14;
               this.setTarget(new BlockTarget(pos4, doubleValue));
               return;
            }

            return;
         }

         this.target = null;
      }
   }

   private void setTarget(BlockTarget blockTarget) {
      long longValue = STRING_KEY ^ 108564928534885L;
      boolean flag = a();
      Breaker tmp = this;
      BlockTarget tmp2 = blockTarget;
      if (flag) {
         if (!this.isTargetValid(blockTarget)) {
            return;
         }

         tmp = this;
         tmp2 = blockTarget;
      }

      tmp.target = tmp2;
   }

   private boolean isTargetValid(BlockTarget blockTarget) {
      long longValue = STRING_KEY ^ 131870476195787L;
      boolean flag = a();
      if (this.target == null) {
         return true;
      }

      BlockState blockState = ClientContext.MINECRAFT_CLIENT.world.getBlockState(this.target.target().getPos());
      int value = blockState.isAir() ? 1 : 0;
      if (flag) {
         label81:
         if (value == 0) {
            value = blockState.getFluidState().isEmpty() ? 1 : 0;
            if (flag) {
               if (value == 0) {
                  break label81;
               }

               value = this.surroundings.I() ? 1 : 0;
            }

            label70:
            if (flag) {
               if (value != 0) {
                  value = blockState.getBlock() instanceof BedBlock ? 1 : 0;
                  if (!flag) {
                     break label70;
                  }

                  if (value != 0) {
                     value = ClientContext.MINECRAFT_CLIENT.world.getBlockState(blockTarget.target().getPos()).getBlock() instanceof BedBlock ? 1 : 0;
                     if (!flag) {
                        break label70;
                     }

                     if (value == 0) {
                        return true;
                     }
                  }
               }

               this.target.target().updateDistance();
               double doubleValue;
               value = (doubleValue = this.target.target().distance - ClientContext.MINECRAFT_CLIENT.player.getBlockInteractionRange()) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
            }

            if (flag) {
               if (value <= 0) {
                  float floatValue = ((BlockBreakingAccessor)ClientContext.MINECRAFT_CLIENT.interactionManager).evon$currentBreakingProgress();
                  double doubleValue2 = this.target.score() * (1.0F - floatValue);
                  double doubleValue3;
                  value = (doubleValue3 = doubleValue2 - blockTarget.score()) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
                  if (flag) {
                     value = value >= 0 ? 1 : 0;
                  }

                  return value != 0;
               }

               value = 1;
            }

            return value != 0;
         }

         value = 1;
      }

      return value != 0;
   }

   private BlockHitResult raycastTarget() {
      long longValue = STRING_KEY ^ 56497644510485L;
      boolean flag = A();
      if (this.targetRotation == null) {
         return null;
      }

      HitResult raycastFromPlayer = RaycastUtil.raycastFromPlayer(ClientContext.MINECRAFT_CLIENT.player.getBlockInteractionRange(), 1.0F, false, this.targetRotation.x, this.targetRotation.y);
      HitResult tmp = raycastFromPlayer;
      if (!flag) {
         if (!(raycastFromPlayer instanceof BlockHitResult)) {
            return null;
         }

         tmp = raycastFromPlayer;
      }

      BlockHitResult blockHitResult = (BlockHitResult)tmp;
      return !flag ? blockHitResult : null;
   }

   private boolean canBreak() {
      long longValue = STRING_KEY ^ 92660534165058L;
      boolean flag = a();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (flag) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return false;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (tmp.world != null) {
         boolean flag2 = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter;
         if (flag) {
            if (flag2) {
               HypixelLocation currentLocation = HypixelLocationManager.getInstance().getCurrentLocation();
               HypixelLocation hypixelLocation = currentLocation;
               if (flag) {
                  if (currentLocation == null) {
                     return true;
                  }

                  hypixelLocation = currentLocation;
               }

               if (flag) {
                  if (hypixelLocation.hasLobby()) {
                     return false;
                  }

                  hypixelLocation = currentLocation;
               }

               if (hypixelLocation.getServerType() != GameType.REPLAY) {
                  boolean equals = STRINGS[7].equals(currentLocation.getMode());
                  if (!flag) {
                     return equals;
                  }

                  if (!equals) {
                     return true;
                  }
               }

               return false;
            }

            flag2 = true;
         }

         return flag2;
      } else {
         return false;
      }
   }

   public BlockTarget getTarget() {
      return this.target;
   }

   public boolean isBreaking() {
      return this.breaking;
   }

   public boolean isRotating() {
      return this.rotating;
   }

   public int getTargetSlot() {
      return this.targetSlot;
   }

   static {
      try {
      long var5 = STRING_KEY ^ 44965495895829L;
      e(true);
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[8];
      int var12 = 0;
      String var11 = "v;K\u00b7\u0013n\u0012\u00a6\u00d7L\u009b\u007f\u00ac\u00e6\u0015\f\bU\u00c9oOL!C\u00ea\b\u00b6\u0096\u0016\u0004\u001bu\u0082\u001c(\u00b7%b\u00f4\u00e9\u001c\u0004R\u0094n\u00be\u0086=S\u0005q\u0016\u0093UD\u00a2@$\\\u0005\u00e0\u00c9\u0002qhW\u00b6o\u00f7\u0086\u00bb9\u00f3\u0000\u0017\u0018\u00abr\u00f1\f\u00af\u0092\u008ey\u00ed\u00ad\u0094-+EN\u00a6@\u0082\u0083Ey.k\u00e3\u0010\u00ae\u00f7\u00ba\u00a6=?Bw\u00c5P^vk}S\u0015";
      int var13 = "v;K\u00b7\u0013n\u0012\u00a6\u00d7L\u009b\u007f\u00ac\u00e6\u0015\f\bU\u00c9oOL!C\u00ea\b\u00b6\u0096\u0016\u0004\u001bu\u0082\u001c(\u00b7%b\u00f4\u00e9\u001c\u0004R\u0094n\u00be\u0086=S\u0005q\u0016\u0093UD\u00a2@$\\\u0005\u00e0\u00c9\u0002qhW\u00b6o\u00f7\u0086\u00bb9\u00f3\u0000\u0017\u0018\u00abr\u00f1\f\u00af\u0092\u008ey\u00ed\u00ad\u0094-+EN\u00a6@\u0082\u0083Ey.k\u00e3\u0010\u00ae\u00f7\u00ba\u00a6=?Bw\u00c5P^vk}S\u0015"
         .length();
      char var10 = 16;
      int var17 = -1;

      label37:
      while (true) {
         String var18 = var11.substring(++var17, var17 + var10);
         byte var10001 = -1;

         while (true) {
            byte[] var15 = var7.doFinal(var18.getBytes("ISO-8859-1"));
            String var26 = decodeUtf8(var15).intern();
            switch (var10001) {
               case 0:
                  var14[var12++] = var26;
                  if ((var17 += var10) >= var13) {
                     STRINGS = var14;
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long var2 = 1838077216964437728L;
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
                     BED_BREAK_COOLDOWN = var30;
                     DIRECTIONS = new Direction[]{Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
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

                  var11 = "\u00b8z\u00a3\ni.\u008bd\u00d2\u00d4\u00e5\u00b2\u00a5\u00aa\u0087o\u0018\u001c3?\u0097}\u00e9\u00b8\u0081\u00f3\u0007D\u00c3&x/\u009ej\u00bcJ\u00999\t`\u00b2";
                  var13 = "\u00b8z\u00a3\ni.\u008bd\u00d2\u00d4\u00e5\u00b2\u00a5\u00aa\u0087o\u0018\u001c3?\u0097}\u00e9\u00b8\u0081\u00f3\u0007D\u00c3&x/\u009ej\u00bcJ\u00999\t`\u00b2"
                     .length();
                  var10 = 16;
                  var17 = -1;
            }

            var18 = var11.substring(++var17, var17 + var10);
            var10001 = 0;
         }
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   public static void e(boolean flag) {
      k = flag;
   }

   public static boolean a() {
      return k;
   }

   public static boolean A() {
      boolean flag = a();
      return !flag;
   }

   private static JSONException a_r2(JSONException jSONException) {
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
