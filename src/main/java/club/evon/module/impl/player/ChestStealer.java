package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.RotationHitResult;
import club.evon.event.ClientTickEvent;
import club.evon.event.InputEvent;
import club.evon.event.JoinWorldEvent;
import club.evon.event.ScreenChangeEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClickScheduler;
import club.evon.manager.KeybindManager;
import club.evon.manager.RotationManager;
import club.evon.mixins.ChestBlockAccessor;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.impl.combat.KillAura;
import club.evon.module.impl.motion.Scaffold;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.ItemUtils;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.RaycastUtil;
import club.evon.util.RotationUtil;
import club.evon.util.Stopwatch;




public final class ChestStealer extends BaseModule {
   private final DoubleSetting delay;
   private final DoubleSetting cps;
   private final BooleanSetting smart;
   private final BooleanSetting titleCheck;
   private final BooleanSetting aura;
   private final BooleanGroupSetting addons;
   private final List<BlockPos> visitedChests;
   private final Stopwatch delayTimer;
   private BlockPos targetChest;
   private ScreenHandler v;
   private static final long KEY = KeyedRandom.create(2302825606386358576L, -533522060519409110L, MethodHandles.lookup().lookupClass()).next(197307248928637L);
   private static final String[] STRINGS;

   public ChestStealer() {
      super((STRINGS)[7], (STRINGS)[1], ModuleCategory.PLAYER);

      String[] strings = STRINGS;
      this.delay = new DoubleSetting(strings[8], 50.0, 0.0, 1000.0, 10.0);
      this.cps = new DoubleSetting(strings[5], 10.0, 1.0, 20.0, 1.0);
      this.smart = new BooleanSetting(strings[6], true);
      this.titleCheck = new BooleanSetting(strings[0], true);
      this.aura = new BooleanSetting(strings[3], false);
      this.addons = new BooleanGroupSetting(strings[2], this.smart, this.titleCheck, this.aura);
      this.visitedChests = new ArrayList<>();
      this.delayTimer = new Stopwatch();
      this.cps.setVisible(this.aura::I);
      this.addSettings(this.delay, this.addons, this.cps);
      }

   @Override
   public void onEnable() {
      this.visitedChests.clear();
      super.onEnable();
   }

   @Subscribe(h = 1)
   private void onTick(ClientTickEvent event) {
      long longValue = KEY ^ 127721387739326L;
      boolean tmp = Breaker.a();
      this.targetChest = null;
      boolean flag = tmp;
      MinecraftClient minecraftClient = ClientContext.MINECRAFT_CLIENT;
      if (flag) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         minecraftClient = ClientContext.MINECRAFT_CLIENT;
      }

      if (flag) {
         if (minecraftClient.world == null) {
            return;
         }

         minecraftClient = ClientContext.MINECRAFT_CLIENT;
      }

      Screen screen = minecraftClient.currentScreen;
      tmp = screen instanceof GenericContainerScreen;
      if (flag) {
         if (tmp) {
            GenericContainerScreen genericContainerScreen = (GenericContainerScreen)screen;
            if (flag) {
               GenericContainerScreenHandler genericContainerScreenHandler = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
               Inventory inventory = genericContainerScreenHandler.getInventory();
               tmp = this.isValidTitle(genericContainerScreen.getTitle().getString());
               if (flag) {
                  if (!tmp) {
                     return;
                  }

                  tmp = inventory.isEmpty();
               }

               label212: {
                  if (flag) {
                     if (tmp) {
                        break label212;
                     }

                     tmp = ItemUtils.isInventoryFull();
                  }

                  if (!tmp) {
                     Map bestArmor = this.getBestArmor(inventory);
                     ItemStack bestSword = this.getBestSword(inventory);
                     ItemStack bestTool = this.getBestTool(inventory, ItemTags.PICKAXES);
                     ItemStack bestTool2 = this.getBestTool(inventory, ItemTags.AXES);
                     boolean flag2 = false;
                     int index = 0;

                     while (true) {
                        label202:
                        if (index < inventory.size()) {
                           ItemStack stack = inventory.getStack(index);
                           tmp = stack.isEmpty();
                           if (!flag) {
                              break;
                           }

                           label251: {
                              if (flag) {
                                 if (tmp && flag) {
                                    break label251;
                                 }

                                 tmp = this.shouldSteal(stack, bestArmor, bestSword, bestTool, bestTool2);
                              }

                              label192:
                              if (flag) {
                                 if (!tmp) {
                                    tmp = this.smart.I();
                                    if (!flag) {
                                       break label192;
                                    }

                                    if (tmp) {
                                       break label251;
                                    }
                                 }

                                 tmp = this.delayTimer.hasElapsed(this.delay.J().longValue(), true);
                              }

                              if (flag) {
                                 if (!tmp) {
                                    break label251;
                                 }

                                 ItemUtils.quickMoveSlot(genericContainerScreenHandler, index, 0);
                                 tmp = true;
                              }

                              flag2 = tmp;
                              if (flag) {
                                 break label202;
                              }
                           }

                           index++;
                           if (flag) {
                              continue;
                           }
                        }

                        tmp = this.smart.I();
                        break;
                     }

                     if (flag) {
                        if (!tmp) {
                           return;
                        }

                        tmp = flag2;
                     }

                     if (flag) {
                        if (tmp) {
                           return;
                        }

                        index = 0;
                        tmp = false;
                     }

                     int index2 = 0;

                     label161: {
                        while (index2 < inventory.size()) {
                           ItemStack stack2 = inventory.getStack(index2);
                           tmp = stack2.isEmpty();
                           if (!flag) {
                              break label161;
                           }

                           label254: {
                              if (flag) {
                                 if (tmp && flag) {
                                    break label254;
                                 }

                                 tmp = this.shouldSteal(stack2, bestArmor, bestSword, bestTool, bestTool2);
                              }

                              if (flag) {
                                 if (!tmp) {
                                    break label254;
                                 }

                                 tmp = true;
                              }

                              index = tmp ? 1 : 0;
                              if (flag) {
                                 break;
                              }
                           }

                           index2++;
                           if (!flag) {
                              break;
                           }
                        }

                        tmp = index != 0;
                     }

                     if (!tmp) {
                        genericContainerScreen.close();
                     }

                     return;
                  }
               }

               genericContainerScreen.close();
               return;
            }
         }

         tmp = this.aura.I();
      }

      ChestStealer chestStealer = null;
      label256: {
         if (flag) {
            if (!tmp) {
               return;
            }

            chestStealer = this;
            if (!flag) {
               break label256;
            }

            tmp = this.canOperate();
         }

         if (!tmp) {
            return;
         }

         this.findNearestChest();
         chestStealer = this;
      }

      BlockPos pos = chestStealer.targetChest;
      if (flag) {
         if (chestStealer.targetChest == null) {
            return;
         }

         pos = this.targetChest;
      }

      RotationHitResult findRotationToBlock = RotationUtil.findRotationToBlock(pos, Direction.UP, RotationUtil.getPlayerRotation(), ClientContext.MINECRAFT_CLIENT.player.getEyePos());
      if (findRotationToBlock != null) {
         RotationManager.getRotationController().rotateTo(findRotationToBlock.rotation(), 360.0F);
      }
   }

   public boolean shouldSteal(ItemStack stack, Map<EquipmentSlot, ItemStack> bestChestArmor, ItemStack bestChestSword, ItemStack bestChestPickaxe, ItemStack bestChestAxe) {
      long longValue = KEY ^ 137906913922119L;
      boolean flag = Breaker.a();
      boolean usefulItem = ItemUtils.isUsefulItem(stack);
      if (flag) {
         if (usefulItem) {
            return true;
         }

         usefulItem = stack.isIn(ItemTags.SWORDS);
      }

      if (flag) {
         if (usefulItem) {
            double doubleValue = ItemUtils.getSwordScore(stack);
            double doubleValue2 = ItemUtils.getSwordScore(this.getBestHotbarSword());
            if (stack == bestChestSword) {
               double doubleValue3;
               int var30i = (doubleValue3 = doubleValue - doubleValue2) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
               if (!flag) {
                  return var30i != 0;
               }

               if (var30i > 0) {
                  return true;
               }
            }

            return false;
         }

         usefulItem = stack.isIn(ItemTags.PICKAXES);
      }

      if (flag) {
         if (usefulItem) {
            double doubleValue4 = ItemUtils.getToolScore(stack);
            double doubleValue5 = ItemUtils.getToolScore(this.getBestHotbarTool(ItemTags.PICKAXES));
            if (stack == bestChestPickaxe) {
               double doubleValue6;
               int var31i = (doubleValue6 = doubleValue4 - doubleValue5) == 0.0 ? 0 : (doubleValue6 < 0.0 ? -1 : 1);
               if (!flag) {
                  return var31i != 0;
               }

               if (var31i > 0) {
                  return true;
               }
            }

            return false;
         }

         usefulItem = stack.isIn(ItemTags.AXES);
      }

      Object object = null;
      label142: {
         if (flag) {
            if (usefulItem) {
               double doubleValue7 = ItemUtils.getToolScore(stack);
               double doubleValue8 = ItemUtils.getToolScore(this.getBestHotbarAxe());
               if (stack == bestChestAxe) {
                  double doubleValue9;
                  int var32i = (doubleValue9 = doubleValue7 - doubleValue8) == 0.0 ? 0 : (doubleValue9 < 0.0 ? -1 : 1);
                  if (!flag) {
                     return var32i != 0;
                  }

                  if (var32i > 0) {
                     return true;
                  }
               }

               return false;
            }

            object = stack;
            if (!flag) {
               break label142;
            }

            usefulItem = ItemUtils.isArmor(stack);
         }

         if (!usefulItem) {
            return false;
         }

         object = stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
      }

      EquippableComponent equippableComponent = (EquippableComponent)object;
      EquippableComponent equippableComponent2 = equippableComponent;
      if (flag) {
         if (equippableComponent == null) {
            return false;
         }

         equippableComponent2 = equippableComponent;
      }

      EquipmentSlot slot = equippableComponent2.slot();
      ItemStack equippedStack = ClientContext.MINECRAFT_CLIENT.player.getEquippedStack(slot);
      ItemStack orDefault = bestChestArmor.getOrDefault(slot, ItemStack.EMPTY);
      ItemStack stack2 = stack;
      if (flag) {
         if (stack != orDefault) {
            return false;
         }

         stack2 = stack;
      }

      double doubleValue10 = ItemUtils.getArmorScore(stack2);
      double doubleValue11 = ItemUtils.getArmorScore(equippedStack);
      double doubleValue12;
      int var33i = (doubleValue12 = doubleValue10 - doubleValue11) == 0.0 ? 0 : (doubleValue12 < 0.0 ? -1 : 1);
      if (flag) {
         var33i = var33i > 0 ? 1 : 0;
      }

      return var33i != 0;
   }

   public Map<EquipmentSlot, ItemStack> getBestArmor(Inventory chest) {
      return IntStream.range(0, chest.size()).<ItemStack>mapToObj(chest::getStack).filter(ItemUtils::isArmor).map(stack -> {
         long longValue = KEY ^ 61559626974239L;
         boolean tmp = Breaker.a();
         EquippableComponent equippableComponent = (EquippableComponent)stack.getComponents().get(DataComponentTypes.EQUIPPABLE);
         boolean flag = tmp;
         EquippableComponent equippableComponent2 = equippableComponent;
         if (flag) {
            if (equippableComponent == null) {
               return null;
            }

            equippableComponent2 = equippableComponent;
         }

         return Map.entry(equippableComponent2.slot(), stack);
      }).filter(Objects::nonNull).collect(HashMap::new, (stack2, stack3) -> stack2.merge(stack3.getKey(), stack3.getValue(), (var0x, var1x) -> {
         long longValue2 = KEY ^ 122378684256973L;
         boolean flag2 = Breaker.A();
         ItemStack tmp2 = var1x;
         if (!flag2) {
            tmp2 = ItemUtils.getArmorScore(var1x) > ItemUtils.getArmorScore(var0x) ? var1x : var0x;
         }

         return tmp2;
      }), HashMap::putAll);
   }

   public ItemStack getBestSword(Inventory chest) {
      return IntStream.range(0, chest.size())
         .<ItemStack>mapToObj(chest::getStack)
         .filter(stack -> stack.isIn(ItemTags.SWORDS))
         .max(Comparator.comparingDouble(ItemUtils::getSwordScore))
         .orElse(ItemStack.EMPTY);
   }

   public ItemStack getBestTool(Inventory chest, TagKey<Item> tag) {
      return IntStream.range(0, chest.size())
         .<ItemStack>mapToObj(chest::getStack)
         .filter(stack -> stack.isIn(tag))
         .max(Comparator.comparingDouble(ItemUtils::getToolScore))
         .orElse(ItemStack.EMPTY);
   }

   private ItemStack getBestHotbarSword() {
      return IntStream.range(0, 9)
         .mapToObj(value -> ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(value))
         .filter(stack -> stack.isIn(ItemTags.SWORDS))
         .max(Comparator.comparingDouble(ItemUtils::getSwordScore))
         .orElse(ItemStack.EMPTY);
   }

   private ItemStack getBestHotbarTool(TagKey<Item> tagKeyItem) {
      return IntStream.range(0, 9)
         .mapToObj(value -> ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(value))
         .filter(var1x -> var1x.isIn(tagKeyItem))
         .max(Comparator.comparingDouble(ItemUtils::getToolScore))
         .orElse(ItemStack.EMPTY);
   }

   private ItemStack getBestHotbarAxe() {
      return IntStream.range(0, 9)
         .mapToObj(value -> ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(value))
         .filter(stack -> stack.getItem() instanceof AxeItem)
         .max(Comparator.comparingDouble(ItemUtils::getToolScore))
         .orElse(ItemStack.EMPTY);
   }

   @Subscribe(h = 1)
   private void onScreenChange(ScreenChangeEvent event) {
   }

   @Subscribe(h = 1)
   private void onInput(InputEvent event) {
      long longValue = KEY ^ 83426727663491L;
      boolean flag = Breaker.A();
      ChestStealer tmp = this;
      if (!flag) {
         if (!this.aura.I()) {
            return;
         }

         tmp = this;
      }

      if (tmp.targetChest != null) {
         MinecraftClient minecraftClient = ClientContext.MINECRAFT_CLIENT;
         if (!flag) {
            if (ClientContext.MINECRAFT_CLIENT.currentScreen instanceof GenericContainerScreen) {
               return;
            }

            minecraftClient = ClientContext.MINECRAFT_CLIENT;
         }

         HitResult raycastFromPlayer = RaycastUtil.raycastFromPlayer(
            minecraftClient.player.getBlockInteractionRange(),
            ClientContext.MINECRAFT_CLIENT.getRenderTickCounter().getTickProgress(false),
            false,
            ClientContext.MINECRAFT_CLIENT.player.getYaw(),
            ClientContext.MINECRAFT_CLIENT.player.getPitch()
         );
         HitResult hitResult = raycastFromPlayer;
         if (!flag) {
            if (!(raycastFromPlayer instanceof BlockHitResult)) {
               return;
            }

            hitResult = raycastFromPlayer;
         }

         BlockHitResult blockHitResult = (BlockHitResult)hitResult;
         boolean equals = blockHitResult.getBlockPos().equals(this.targetChest);
         if (!flag) {
            if (!equals) {
               return;
            }

            equals = this.canOperate();
         }

         if (!flag) {
            if (!equals) {
               return;
            }

            equals = this.visitedChests.contains(this.targetChest);
         }

         if (!flag) {
            if (equals) {
               return;
            }

            equals = ClickScheduler.getInstance().tryClick(this.cps.J(), false, true);
         }

         if (!flag && equals) {
            KeybindManager.getUseState().queuePress();
            this.visitedChests.add(this.targetChest);
         }
      }
   }

   @Subscribe(h = 1)
   private void onJoinWorld(JoinWorldEvent event) {
      this.visitedChests.clear();
   }

   private void findNearestChest() {
      long longValue = KEY ^ 100474293800914L;
      boolean flag = Breaker.A();
      int value = (int)Math.ceil(ClientContext.MINECRAFT_CLIENT.player.getBlockInteractionRange());
      double doubleValue = value;
      int value2 = -value;

      label118:
      while (true) {
         int value3 = value2;

         label116:
         while (value3 <= value) {
            int value4 = -value;

            label113:
            while (true) {
               value3 = value4;

               label111:
               while (true) {
                  if (value3 > value) {
                     break label113;
                  }

                  value3 = -value;
                  if (flag) {
                     continue label116;
                  }

                  int value5 = value3;

                  while (value5 <= value) {
                     BlockPos add = ClientContext.MINECRAFT_CLIENT.player.getBlockPos().add(value2, value4, value5);
                     BlockState blockState = ClientContext.MINECRAFT_CLIENT.world.getBlockState(add);
                     Block block = blockState.getBlock();
                     label105:
                     if (!flag) {
                        boolean flag2 = block instanceof ChestBlock;
                        if (flag) {
                           continue label111;
                        }

                        if (flag2) {
                           ChestBlock chestBlock = (ChestBlock)block;
                           if (flag) {
                              break label105;
                           }

                           if (!this.visitedChests.contains(add)) {
                              double doubleValue2 = PlayerUtil.getDistanceToBlock(add);
                              if (flag) {
                                 break label105;
                              }

                              label98:
                              if (doubleValue2 <= value) {
                                 label126: {
                                    double doubleValue3;
                                    int var21i = (doubleValue3 = doubleValue2 - doubleValue) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
                                    boolean flag3 = var21i != 0;
                                    if (!flag) {
                                       if (var21i > 0) {
                                          break label98;
                                       }

                                       if (flag) {
                                          break label126;
                                       }

                                       flag3 = this.titleCheck.I();
                                    }

                                    if (flag3) {
                                       NamedScreenHandlerFactory callCreateScreenHandlerFactory = ((ChestBlockAccessor)chestBlock)
                                          .callCreateScreenHandlerFactory(blockState, ClientContext.MINECRAFT_CLIENT.world, add);
                                       if (flag) {
                                          break label105;
                                       }

                                       if (callCreateScreenHandlerFactory == null) {
                                          break label98;
                                       }

                                       if (flag) {
                                          break label126;
                                       }

                                       if (!this.isValidTitle(callCreateScreenHandlerFactory.getDisplayName().getString()) && !flag) {
                                          break label98;
                                       }
                                    }
                                 }

                                 this.targetChest = add;
                                 doubleValue = doubleValue2;
                              }
                           }
                        }

                        value5++;
                     }

                     if (flag) {
                        break;
                     }
                  }

                  value4++;
                  if (flag) {
                     break label113;
                  }
                  break;
               }
            }

            value2++;
            if (flag) {
               return;
            }
            continue label118;
         }

         return;
      }
   }

   private boolean canOperate() {
      long longValue = KEY ^ 61922321892583L;
      boolean tmp = Breaker.A();
      KillAura moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(KillAura.class);
      Scaffold moduleByClass2 = EvonClient.getInstance().getModuleManager().getModuleByClass(Scaffold.class);
      boolean flag = tmp;
      tmp = moduleByClass.isEnabled();
      if (!flag) {
         if (tmp && moduleByClass.getTarget() != null) {
            return false;
         }

         tmp = moduleByClass2.isEnabled();
      }

      return flag ? tmp : !tmp;
   }

   private boolean isValidTitle(String title) {
      long longValue = KEY ^ 136647026384816L;
      boolean flag = Breaker.a();
      boolean tmp = this.titleCheck.I();
      if (flag) {
         if (tmp) {
            tmp = title.toLowerCase().contains(STRINGS[4]);
            if (!flag) {
               return tmp;
            }

            if (!tmp) {
               return false;
            }
         }

         tmp = true;
      }

      return tmp;
   }

   static {
      try {
      long var0 = KEY ^ 28967776540421L;
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
      String var6 = "c\u00c5\u00a2\u00db\u0088\u00a0n\u00e8\u00fbzi\u00d5$\u00f2\u00f6= \u00a2\u00beG\u00d6\u0017\u00ee\b\u00c0\u00a4#z\u0017\u00a8\n}\u00af\u00c0\u009b\u001e:\u0006\u009a\u009c\u000e}/\u00cc~\u00ce\u00b0\u0002o\b\u00dd\u00ff\u0002\u00bc\u00d9\u00db\u0094\u0007\b\u00efy\u00ab\u008dCy\u00b4\u0087\bt$\u00db\u00a4\u00a9p\u000b\b\b\u00c0vS<\u001c\u00aa\u009d\u00a2\b\u00da\u001a\r\u00a8vj$\u00de";
      int var8 = "c\u00c5\u00a2\u00db\u0088\u00a0n\u00e8\u00fbzi\u00d5$\u00f2\u00f6= \u00a2\u00beG\u00d6\u0017\u00ee\b\u00c0\u00a4#z\u0017\u00a8\n}\u00af\u00c0\u009b\u001e:\u0006\u009a\u009c\u000e}/\u00cc~\u00ce\u00b0\u0002o\b\u00dd\u00ff\u0002\u00bc\u00d9\u00db\u0094\u0007\b\u00efy\u00ab\u008dCy\u00b4\u0087\bt$\u00db\u00a4\u00a9p\u000b\b\b\u00c0vS<\u001c\u00aa\u009d\u00a2\b\u00da\u001a\r\u00a8vj$\u00de"
         .length();
      char var5 = 16;
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeString(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var19;
                  if ((var12 += var5) >= var8) {
                     STRINGS = var9;
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

                  var6 = "!r\u00f4\u00d3\u00de\u00b1\u00ba\u0005\u008e\u00ce]]<R\u00e4\u00bb\b\u007f\u0012p\u009d\u00ff\u00e3o ";
                  var8 = "!r\u00f4\u00d3\u00de\u00b1\u00ba\u0005\u008e\u00ce]]<R\u00e4\u00bb\b\u007f\u0012p\u009d\u00ff\u00e3o ".length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var10004) {
         throw new RuntimeException("club/evon/module/impl/player/ChestStealer", var10004);
      }
   }

   private static JSONException identity(JSONException exception) {
      return exception;
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
