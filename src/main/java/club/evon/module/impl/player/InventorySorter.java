package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.hypixel.data.type.GameType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.EquipmentSlot.Type;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.HypixelTargetFilter;
import club.evon.api.HypixelLocationManager;
import club.evon.data.HypixelLocation;
import club.evon.event.ClientTickEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.manager.ModuleManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.impl.combat.KillAura;
import club.evon.module.impl.motion.InventoryMove;
import club.evon.module.impl.motion.Scaffold;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.ChatUtil;
import club.evon.util.ItemUtils;
import club.evon.util.KeyedRandom;
import club.evon.util.Stopwatch;




public final class InventorySorter extends BaseModule {
   private final BooleanGroupSetting itemsToSort;
   private final BooleanGroupSetting itemsToDrop;
   private final DoubleSetting G;
   private final DoubleSetting swordSlot;
   private final DoubleSetting pickaxeSlot;
   private final DoubleSetting axeSlot;
   private final DoubleSetting shovelSlot;
   private final DoubleSetting blockSlot;
   private final DoubleSetting foodSlot;
   private final DoubleSetting potionSlot;
   private final DoubleSetting pearlSlot;
   private final Stopwatch delayTimer;
   private boolean duplicateWarned;
   private static final long KEY = KeyedRandom.create(726038088800034297L, -2442567523658611411L, MethodHandles.lookup().lookupClass()).next(61829561514976L);
   private static final String[] STRINGS;

   public InventorySorter() {
      super((STRINGS)[9], (STRINGS)[28], ModuleCategory.PLAYER);

      String[] strings = STRINGS;
      this.itemsToSort = new BooleanGroupSetting(
         strings[10],
         new BooleanSetting(strings[24], true),
         new BooleanSetting(strings[5], true),
         new BooleanSetting(strings[0], true),
         new BooleanSetting(strings[21], true),
         new BooleanSetting(strings[3], true),
         new BooleanSetting(strings[4], true),
         new BooleanSetting(strings[37], true),
         new BooleanSetting(strings[12], true),
         new BooleanSetting(strings[15], true)
      );
      this.itemsToDrop = new BooleanGroupSetting(strings[7], new BooleanSetting(strings[29], false), new BooleanSetting(strings[18], false), new BooleanSetting(strings[8], true));
      this.G = new DoubleSetting(strings[14], 50.0, 0.0, 1000.0, 10.0);
      this.swordSlot = new DoubleSetting(strings[33], 1.0, 1.0, 9.0, 1.0);
      this.pickaxeSlot = new DoubleSetting(strings[31], 2.0, 1.0, 9.0, 1.0);
      this.axeSlot = new DoubleSetting(strings[36], 3.0, 1.0, 9.0, 1.0);
      this.shovelSlot = new DoubleSetting(strings[6], 4.0, 1.0, 9.0, 1.0);
      this.blockSlot = new DoubleSetting(strings[16], 5.0, 1.0, 9.0, 1.0);
      this.foodSlot = new DoubleSetting(strings[19], 6.0, 1.0, 9.0, 1.0);
      this.potionSlot = new DoubleSetting(strings[26], 7.0, 1.0, 9.0, 1.0);
      this.pearlSlot = new DoubleSetting(strings[11], 8.0, 1.0, 9.0, 1.0);
      this.delayTimer = new Stopwatch();
      this.swordSlot.setVisible(() -> this.itemsToSort.getByName(STRINGS[27]).I());
      this.pickaxeSlot.setVisible(() -> this.itemsToSort.getByName(STRINGS[13]).I());
      this.axeSlot.setVisible(() -> this.itemsToSort.getByName(STRINGS[34]).I());
      this.shovelSlot.setVisible(() -> this.itemsToSort.getByName(STRINGS[20]).I());
      this.blockSlot.setVisible(() -> this.itemsToSort.getByName(STRINGS[32]).I());
      this.foodSlot.setVisible(() -> this.itemsToSort.getByName(STRINGS[29]).I());
      this.potionSlot.setVisible(() -> this.itemsToSort.getByName(STRINGS[30]).I());
      this.pearlSlot.setVisible(() -> this.itemsToSort.getByName(STRINGS[23]).I());
      this.addSettings(this.G, this.itemsToDrop, this.itemsToSort, this.swordSlot, this.pickaxeSlot, this.axeSlot, this.shovelSlot, this.blockSlot, this.foodSlot, this.potionSlot, this.pearlSlot);
      }

   private boolean hasDuplicateSlots() {
      long longValue = KEY ^ 73866784009759L;
      boolean tmp = Breaker.a();
      boolean anyMatch = this.getSettings().stream().filter(setting -> setting instanceof DoubleSetting).map(setting2 -> (DoubleSetting)setting2).filter(setting3 -> {
         long var1x = KEY ^ 110279910236951L;
         boolean var3x = Breaker.a();
         boolean var10000x = setting3.getName().endsWith(STRINGS[1]);
         if (var3x) {
            if (!var10000x) {
               return false;
            }

            var10000x = setting3.isVisible();
         }

         return !var3x ? var10000x : var10000x;
      }).map(setting4 -> setting4.J().intValue()).collect(Collectors.groupingBy(integer -> (Integer)integer, Collectors.counting())).values().stream().anyMatch(longValue2 -> {
         long var1x = KEY ^ 31843331706588L;
         boolean var3x = Breaker.A();
         long var4x;
         int var10000x = (var4x = longValue2 - 1L) == 0L ? 0 : (var4x < 0L ? -1 : 1);
         if (!var3x) {
            var10000x = var10000x > 0 ? 1 : 0;
         }

         return var10000x != 0;
      });
      boolean flag = tmp;
      tmp = anyMatch;
      if (flag) {
         if (anyMatch) {
            tmp = this.duplicateWarned;
            if (flag) {
               if (!this.duplicateWarned) {
                  ChatUtil.sendError(STRINGS[2] + this.getName() + STRINGS[17]);
                  this.duplicateWarned = true;
               }

               tmp = true;
            }

            return tmp;
         }

         this.duplicateWarned = false;
         tmp = false;
      }

      return tmp;
   }

   private boolean shouldKeep(ItemStack stack) {
      boolean flag;
      Item item;
      boolean flag2;
      long longValue = KEY ^ 2459667991437L;
      flag2 = Breaker.a();
      item = stack.getItem();
      flag = flag2;
      flag2 = this.itemsToDrop.getByName(STRINGS[29]).I();
      label88:
      if (flag) {
         if (flag2) {
            flag2 = stack.contains(DataComponentTypes.FOOD);
            if (!flag) {
               break label88;
            }

            label84:
            if (flag2) {
               Item item2 = item;
               Item tmp = Items.GOLDEN_APPLE;
               if (flag) {
                  if (item == Items.GOLDEN_APPLE) {
                     break label84;
                  }

                  item2 = item;
                  tmp = Items.ENCHANTED_GOLDEN_APPLE;
               }

               if (item2 != tmp) {
                  return false;
               }
            }
         }

         String[] strings = STRINGS;
         flag2 = this.itemsToDrop.getByName(strings[22]).I();
      }

      label63:
      if (flag) {
         if (flag2) {
            flag2 = item instanceof BowItem;
            if (!flag) {
               return flag2;
            }

            if (flag2) {
               return false;
            }

            flag2 = item instanceof ArrowItem;
            if (!flag) {
               break label63;
            }

            if (flag2) {
               return false;
            }
         }

         flag2 = this.itemsToDrop.getByName(STRINGS[25]).I();
      }

      if (flag) {
         if (flag2) {
            return ItemUtils.isUsefulItem(stack);
         }

         flag2 = true;
      }

      return flag2;
   }

   @Subscribe
   public void onTick(ClientTickEvent event) {
      long longValue = KEY ^ 129680964800986L;
      boolean flag = Breaker.A();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (!flag) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (tmp.world != null) {
         ModuleManager module;
         label129: {
            label128: {
               module = EvonClient.getInstance().getModuleManager();
               boolean flag2 = ClientContext.MINECRAFT_CLIENT.currentScreen instanceof InventoryScreen;
               if (!flag) {
                  if (flag2) {
                     break label128;
                  }

                  InventoryMove moduleByClass = module.getModuleByClass(InventoryMove.class);
                  if (flag) {
                     break label129;
                  }

                  flag2 = moduleByClass.isEnabled();
               }

               if (!flag2) {
                  return;
               }
            }
         }

         KillAura killAura = (KillAura)module.getModuleByClass(KillAura.class);
         Scaffold moduleByClass2 = module.getModuleByClass(Scaffold.class);
         boolean enabled = killAura.isEnabled();
         if (!flag) {
            if (enabled && killAura.getTarget() != null) {
               return;
            }

            enabled = moduleByClass2.isEnabled();
         }

         if (!flag) {
            if (enabled) {
               return;
            }

            enabled = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter;
         }

         if (enabled) {
            HypixelLocation currentLocation = HypixelLocationManager.getInstance().getCurrentLocation();
            HypixelLocation hypixelLocation = currentLocation;
            if (!flag) {
               if (currentLocation == null) {
                  return;
               }

               hypixelLocation = currentLocation;
            }

            if (!flag) {
               if (hypixelLocation.hasLobby()) {
                  return;
               }

               hypixelLocation = currentLocation;
            }

            if (hypixelLocation.getServerType() == GameType.BEDWARS) {
               return;
            }
         }

         ScreenHandler screenHandler = ClientContext.MINECRAFT_CLIENT.player.currentScreenHandler;
         ScreenHandler screenHandler2 = screenHandler;
         if (!flag) {
            if (!(screenHandler instanceof PlayerScreenHandler)) {
               return;
            }

            screenHandler2 = screenHandler;
         }

         PlayerScreenHandler player = (PlayerScreenHandler)screenHandler2;
         if (!flag) {
            InventorySorter inventorySorter = this;
            if (!flag) {
               if (this.hasDuplicateSlots()) {
                  return;
               }

               inventorySorter = this;
            }

            Slot findBestSwordSlot = inventorySorter.findBestSwordSlot(player);
            Slot slot = screenHandler.getSlot(this.swordSlot.J().intValue() + 35);
            Slot findBestPickaxeSlot = this.findBestPickaxeSlot(player);
            Slot slot2 = screenHandler.getSlot(this.pickaxeSlot.J().intValue() + 35);
            Slot findBestAxeSlot = this.findBestAxeSlot(player);
            Slot slot3 = screenHandler.getSlot(this.axeSlot.J().intValue() + 35);
            Slot findBestShovelSlot = this.findBestShovelSlot(player);
            Slot slot4 = screenHandler.getSlot(this.shovelSlot.J().intValue() + 35);
            Slot findBestBlockSlot = this.findBestBlockSlot(player);
            Slot slot5 = screenHandler.getSlot(this.blockSlot.J().intValue() + 35);
            Slot findBestFoodSlot = this.findBestFoodSlot(player);
            Slot slot6 = screenHandler.getSlot(this.foodSlot.J().intValue() + 35);
            Slot findBestPotionSlot = this.findBestPotionSlot(player);
            Slot slot7 = screenHandler.getSlot(this.potionSlot.J().intValue() + 35);
            Slot findBestPearlSlot = this.findBestPearlSlot(player);
            Slot slot8 = screenHandler.getSlot(this.pearlSlot.J().intValue() + 35);
            List<Slot> armorSlots = this.getArmorSlots(player);
            if (!flag) {
               if (this.itemsToSort.getByName(STRINGS[35]).I()) {
                  ItemUtils.findSlots(player, slot9 -> {
                     long longValue2 = KEY ^ 16610960830023L;
                     boolean flag3 = Breaker.a();
                     boolean var10000x = slot9.getStack().isEmpty();
                     if (flag3) {
                        if (var10000x) {
                           return false;
                        }

                        var10000x = ItemUtils.isArmor(slot9.getStack());
                     }

                     return !flag3 ? var10000x : var10000x;
                  }, true).forEach(slot10 -> {
                     InventorySorter var9x;
                     label30: {
                        long var4x = KEY ^ 2864799137134L;
                        boolean var10000x = Breaker.a();
                        ItemStack var7x = slot10.getStack();
                        boolean var6x = var10000x;
                        boolean var8x = armorSlots.stream().noneMatch(var1x -> {
                           long var2xx = KEY ^ 55312343686447L;
                           return var1x.getStack() == var7x;
                        });
                        if (var6x) {
                           if (!var8x) {
                              return;
                           }

                           var9x = this;
                           if (!var6x) {
                              break label30;
                           }

                           var8x = this.hasDelayElapsed(this.G.J().longValue());
                        }

                        if (!var8x) {
                           return;
                        }

                        ItemUtils.dropSlot(player, slot10.id);
                        var9x = this;
                     }

                     var9x.delayTimer.reset();
                  });
                  armorSlots.forEach(var2x -> {
                     InventorySorter var8x;
                     label30: {
                        long longValue3 = KEY ^ 39038231895662L;
                        boolean var10000x = Breaker.a();
                        List<ItemStack> var6x = this.getEquippedArmor();
                        boolean var5x = var10000x;
                        Collections.shuffle(var6x);
                        boolean var7x = var6x.stream().noneMatch(var1x -> {
                           long var2xx = KEY ^ 65586671923056L;
                           return var2x.getStack() == var1x;
                        });
                        if (var5x) {
                           if (!var7x) {
                              return;
                           }

                           var8x = this;
                           if (!var5x) {
                              break label30;
                           }

                           var7x = this.hasDelayElapsed(this.G.J().longValue());
                        }

                        if (!var7x) {
                           return;
                        }

                        ItemUtils.quickMoveSlot(player, var2x.id, 0);
                        var8x = this;
                     }

                     var8x.delayTimer.reset();
                  });
               }

               ItemUtils.findSlots(player, slot11 -> {
                  long longValue4 = KEY ^ 113115583461506L;
                  boolean flag4 = Breaker.A();
                  boolean var10000x = slot11.getStack().isEmpty();
                  if (!flag4) {
                     var10000x = !var10000x;
                  }

                  return var10000x;
               }, true).forEach(var19x -> {
                  boolean var22x;
                  Object var32x;
                  label224: {
                     long var20x = KEY ^ 133212128863384L;
                     ItemStack var23x = var19x.getStack();
                     var32x = Breaker.A();
                     Item var24x = var23x.getItem();
                     var22x = (boolean)var32x;
                     var32x = this.hasDelayElapsed(this.G.J().longValue());
                     if (!var22x) {
                        if (!(boolean)var32x) {
                           return;
                        }

                        var32x = this;
                        if (var22x) {
                           break label224;
                        }

                        var32x = this.shouldKeep(var23x);
                     }

                      if ((boolean)var32x) {
                        return;
                     }

                     var32x = var24x.getComponents().get(DataComponentTypes.EQUIPPABLE);
                  }

                  if (var32x == null) {
                     var32x = this.itemsToSort.getByName(STRINGS[27]).I();
                     if (!var22x) {
                        if ((boolean)var32x) {
                           this.swapSword(screenHandler, slot, findBestSwordSlot);
                        }

                        String[] var25x = STRINGS;
                        var32x = this.itemsToSort.getByName(var25x[0]).I();
                     }

                     if (!var22x) {
                        if ((boolean)var32x) {
                           this.swapPickaxe(screenHandler, slot2, findBestPickaxeSlot);
                        }

                        String[] var26x = STRINGS;
                        var32x = this.itemsToSort.getByName(var26x[34]).I();
                     }

                     if (!var22x) {
                        if ((boolean)var32x) {
                           this.swapAxe(screenHandler, slot3, findBestAxeSlot);
                        }

                        String[] var27x = STRINGS;
                        var32x = this.itemsToSort.getByName(var27x[20]).I();
                     }

                     if (!var22x) {
                        if ((boolean)var32x) {
                           this.swapShovel(screenHandler, slot4, findBestShovelSlot);
                        }

                        String[] var28x = STRINGS;
                        var32x = this.itemsToSort.getByName(var28x[32]).I();
                     }

                     if (!var22x) {
                        if ((boolean)var32x) {
                           this.swapBlocks(screenHandler, slot5, findBestBlockSlot);
                        }

                        String[] var29x = STRINGS;
                        var32x = this.itemsToSort.getByName(var29x[29]).I();
                     }

                     if (!var22x) {
                        if ((boolean)var32x) {
                           this.swapFood(screenHandler, slot6, findBestFoodSlot);
                        }

                        String[] var30x = STRINGS;
                        var32x = this.itemsToSort.getByName(var30x[30]).I();
                     }

                     if (!var22x) {
                        if ((boolean)var32x) {
                           this.swapPotion(screenHandler, slot7, findBestPotionSlot);
                        }

                        var32x = this.itemsToSort.getByName(STRINGS[23]).I();
                     }

                     if (!var22x) {
                        if ((boolean)var32x) {
                           this.swapPearls(screenHandler, slot8, findBestPearlSlot);
                        }

                        var32x = var19x.getIndex();
                     }

                     int value = slot.getIndex();
                     if (!var22x) {
                        label194: {
                           if ((int)var32x == value) {
                              var32x = var19x.getStack().isIn(ItemTags.SWORDS);
                              if (var22x) {
                                 break label194;
                              }

                              if ((boolean)var32x) {
                                 return;
                              }
                           }

                           var32x = var19x.getIndex();
                        }

                        value = slot2.getIndex();
                     }

                     if (!var22x) {
                        label186: {
                           if ((int)var32x == value) {
                              var32x = var19x.getStack().isIn(ItemTags.PICKAXES);
                              if (var22x) {
                                 break label186;
                              }

                              if ((boolean)var32x) {
                                 return;
                              }
                           }

                           var32x = var19x.getIndex();
                        }

                        value = slot3.getIndex();
                     }

                     label226: {
                        if (!var22x) {
                           label177: {
                              if ((int)var32x == value) {
                                 var32x = var19x.getStack().getItem() instanceof AxeItem;
                                 if (var22x) {
                                    break label177;
                                 }

                                 if ((boolean)var32x) {
                                    return;
                                 }
                              }

                              var32x = var19x.getIndex();
                           }

                           if (var22x) {
                              break label226;
                           }

                           value = slot4.getIndex();
                        }

                        if ((int)var32x == value) {
                           var32x = var19x.getStack().getItem() instanceof ShovelItem;
                           if (var22x) {
                              break label226;
                           }

                           if ((boolean)var32x) {
                              return;
                           }
                        }

                        var32x = var19x.getStack().getItem() instanceof BucketItem;
                     }

                     if (!var22x) {
                        if ((boolean)var32x) {
                           return;
                        }

                        var32x = var19x.getStack().getName().getStyle().isEmpty();
                     }

                     if ((boolean)var32x) {
                        ItemUtils.dropSlot(player, var19x.id);
                        this.delayTimer.reset();
                     }
                  }
               });
            }
         }
      }
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 22980257194670L;
      boolean tmp = Breaker.A();
      Packet packet = event.getPacket();
      boolean flag = tmp;
      Packet packet2 = packet;
      if (!flag) {
         if (!(packet instanceof ScreenHandlerSlotUpdateS2CPacket)) {
            return;
         }

         packet2 = packet;
      }

      ScreenHandlerSlotUpdateS2CPacket packet3 = (ScreenHandlerSlotUpdateS2CPacket)packet2;
      if (packet3.getStack().getItem() != Items.AIR && ClientContext.MINECRAFT_CLIENT.player != null && packet3.getSyncId() == ClientContext.MINECRAFT_CLIENT.player.playerScreenHandler.syncId) {
         this.delayTimer.reset();
      }
   }

   private List<Slot> getArmorSlots(PlayerScreenHandler handler) {
      return Arrays.stream(EquipmentSlot.values()).map(var1x -> ItemUtils.findSlots(handler, var1xx -> {
         long longValue = KEY ^ 110231702629984L;
         boolean flag = Breaker.a();
         boolean empty = var1xx.getStack().isEmpty();
         if (flag) {
            label45:
            if (!empty) {
               Object stack = var1xx.getStack();
               if (flag) {
                  if (!ItemUtils.isArmor((ItemStack)stack)) {
                     break label45;
                  }

                  stack = var1xx.getStack().getComponents().get(DataComponentTypes.EQUIPPABLE);
               }

               EquippableComponent equippableComponent = (EquippableComponent)stack;
               EquippableComponent equippableComponent2 = equippableComponent;
               if (flag) {
                  if (equippableComponent == null) {
                     return false;
                  }

                  equippableComponent2 = equippableComponent;
               }

               if (equippableComponent2.slot() == var1x) {
                  return true;
               }

               return false;
            }

            empty = false;
         }

         return empty;
      }, false).stream().max(Comparator.comparing(var0x -> ItemUtils.getArmorScore(var0x.getStack()))).orElse(null)).filter(Objects::nonNull).toList();
   }

   private List<ItemStack> getEquippedArmor() {
      long longValue = KEY ^ 116888412131219L;
      boolean tmp = Breaker.A();
      ArrayList list = new ArrayList();
      EquipmentSlot[] values = EquipmentSlot.values();
      int length = values.length;
      int index = 0;
      boolean flag = tmp;

      while (index < length) {
         label52: {
            EquipmentSlot equipmentSlot = values[index];
            if (equipmentSlot.getType() == Type.HUMANOID_ARMOR || flag) {
               ItemStack equippedStack = ClientContext.MINECRAFT_CLIENT.player.getEquippedStack(equipmentSlot);
               if (flag) {
                  break label52;
               }

               if (!equippedStack.isEmpty() && !flag && ItemUtils.isArmor(equippedStack)) {
                  list.add(equippedStack);
               }
            }

            index++;
         }

         if (flag) {
            break;
         }
      }

      return list;
   }

   private void swapSword(ScreenHandler handler, Slot targetSlot, Slot sourceSlot) {
      long longValue = KEY ^ 131600778847164L;
      boolean flag = Breaker.A();
      Slot tmp = sourceSlot;
      if (!flag) {
         if (sourceSlot == null) {
            return;
         }

         tmp = sourceSlot;
      }

      if (!flag) {
         if (tmp.getIndex() == targetSlot.getIndex()) {
            return;
         }

         tmp = sourceSlot;
      }

      double doubleValue = ItemUtils.getSwordScore(tmp.getStack());
      double doubleValue2 = ItemUtils.getSwordScore(targetSlot.getStack());
      if (!flag) {
         if (!(doubleValue > doubleValue2)) {
            return;
         }

         ItemUtils.swapSlots(handler, sourceSlot.id, targetSlot.id - 36);
      }

      this.delayTimer.reset();
   }

   private Slot findBestSwordSlot(ScreenHandler handler) {
      return ItemUtils.findSlots(handler, slot -> slot.getStack().isIn(ItemTags.SWORDS), false).stream().max(Comparator.comparing(slot2 -> ItemUtils.getSwordScore(slot2.getStack()))).orElse(null);
   }

   private void swapPickaxe(ScreenHandler handler, Slot targetSlot, Slot sourceSlot) {
      long longValue = KEY ^ 50088799551681L;
      boolean flag = Breaker.a();
      Slot tmp = sourceSlot;
      if (flag) {
         if (sourceSlot == null) {
            return;
         }

         tmp = sourceSlot;
      }

      if (flag) {
         if (tmp.getIndex() == targetSlot.getIndex()) {
            return;
         }

         tmp = sourceSlot;
      }

      double doubleValue = ItemUtils.getToolScore(tmp.getStack());
      double doubleValue2 = ItemUtils.getToolScore(targetSlot.getStack());
      if (flag) {
         if (!(doubleValue > doubleValue2)) {
            return;
         }

         ItemUtils.swapSlots(handler, sourceSlot.id, targetSlot.id - 36);
      }

      this.delayTimer.reset();
   }

   private void swapShovel(ScreenHandler handler, Slot targetSlot, Slot sourceSlot) {
      long longValue = KEY ^ 43421963412195L;
      boolean flag = Breaker.A();
      Slot tmp = sourceSlot;
      if (!flag) {
         if (sourceSlot == null) {
            return;
         }

         tmp = sourceSlot;
      }

      if (!flag) {
         if (tmp.getIndex() == targetSlot.getIndex()) {
            return;
         }

         tmp = sourceSlot;
      }

      double doubleValue = ItemUtils.getToolScore(tmp.getStack());
      double doubleValue2 = ItemUtils.getToolScore(targetSlot.getStack());
      if (!flag) {
         if (!(doubleValue > doubleValue2)) {
            return;
         }

         ItemUtils.swapSlots(handler, sourceSlot.id, targetSlot.id - 36);
      }

      this.delayTimer.reset();
   }

   private Slot findBestShovelSlot(ScreenHandler handler) {
      return ItemUtils.findSlots(handler, slot -> slot.getStack().isIn(ItemTags.SHOVELS), false)
         .stream()
         .max(Comparator.comparing(slot2 -> ItemUtils.getToolScore(slot2.getStack())))
         .orElse(null);
   }

   private Slot findBestPickaxeSlot(ScreenHandler handler) {
      return ItemUtils.findSlots(handler, slot -> slot.getStack().isIn(ItemTags.PICKAXES), false)
         .stream()
         .max(Comparator.comparing(slot2 -> ItemUtils.getToolScore(slot2.getStack())))
         .orElse(null);
   }

   private void swapAxe(ScreenHandler handler, Slot targetSlot, Slot sourceSlot) {
      long longValue = KEY ^ 121934659107269L;
      boolean flag = Breaker.A();
      Slot tmp = sourceSlot;
      if (!flag) {
         if (sourceSlot == null) {
            return;
         }

         tmp = sourceSlot;
      }

      if (!flag) {
         if (tmp.getIndex() == targetSlot.getIndex()) {
            return;
         }

         tmp = sourceSlot;
      }

      double doubleValue = ItemUtils.getToolScore(tmp.getStack());
      double doubleValue2 = ItemUtils.getToolScore(targetSlot.getStack());
      if (!flag) {
         if (!(doubleValue > doubleValue2)) {
            return;
         }

         ItemUtils.swapSlots(handler, sourceSlot.id, targetSlot.id - 36);
      }

      this.delayTimer.reset();
   }

   private Slot findBestAxeSlot(ScreenHandler handler) {
      return ItemUtils.findSlots(handler, slot -> slot.getStack().getItem() instanceof AxeItem, false)
         .stream()
         .max(Comparator.comparing(slot2 -> ItemUtils.getToolScore(slot2.getStack())))
         .orElse(null);
   }

   private Slot findBestBlockSlot(ScreenHandler handler) {
      return ItemUtils.findSlots(handler, slot -> {
         long var1x = KEY ^ 134697933485896L;
         boolean flag = Breaker.a();
         Item item = slot.getStack().getItem();
         Item item2 = item;
         if (flag) {
            if (!(item instanceof BlockItem)) {
               return false;
            }

            item2 = item;
         }

         BlockItem blockItem = (BlockItem)item2;
         int value = slot.getStack().getCount();
         if (flag) {
            if (value <= 0) {
               return false;
            }

            return ItemUtils.isPlaceableBlock(blockItem.getBlock());
         }

         return value != 0;
      }, false).stream().max(Comparator.comparing(slot2 -> slot2.getStack().getCount())).orElse(null);
   }

   private Slot findBestPearlSlot(ScreenHandler handler) {
      return ItemUtils.findSlots(handler, slot -> {
         long var1x = KEY ^ 80723940239489L;
         boolean flag = Breaker.A();
         boolean flag2 = slot.getStack().getItem() instanceof EnderPearlItem;
         if (!flag) {
            if (!flag2) {
               return false;
            }

            return slot.getStack().getCount() > 0;
         }

         return flag2;
      }, false).stream().max(Comparator.comparing(slot2 -> slot2.getStack().getCount())).orElse(null);
   }

   private void swapBlocks(ScreenHandler handler, Slot targetSlot, Slot sourceSlot) {
      long longValue = KEY ^ 42338801863293L;
      boolean flag = Breaker.a();
      Slot tmp = sourceSlot;
      if (flag) {
         if (sourceSlot == null) {
            return;
         }

         tmp = sourceSlot;
      }

      int value = tmp.getIndex();
      if (flag) {
         if (value == targetSlot.getIndex()) {
            return;
         }

         value = sourceSlot.getStack().getCount();
      }

      double doubleValue = value;
      double doubleValue2 = targetSlot.getStack().getCount();
      if (flag) {
         if (!(doubleValue > doubleValue2)) {
            return;
         }

         ItemUtils.swapSlots(handler, sourceSlot.id, targetSlot.id - 36);
      }

      this.delayTimer.reset();
   }

   private void swapPearls(ScreenHandler handler, Slot targetSlot, Slot sourceSlot) {
      long longValue = KEY ^ 26890986222383L;
      boolean flag = Breaker.A();
      Slot tmp = sourceSlot;
      if (!flag) {
         if (sourceSlot == null) {
            return;
         }

         tmp = sourceSlot;
      }

      int value = tmp.getIndex();
      if (!flag) {
         if (value == targetSlot.getIndex()) {
            return;
         }

         value = sourceSlot.getStack().getCount();
      }

      double doubleValue = value;
      double doubleValue2 = targetSlot.getStack().getCount();
      if (!flag) {
         if (!(doubleValue > doubleValue2)) {
            return;
         }

         ItemUtils.swapSlots(handler, sourceSlot.id, targetSlot.id - 36);
      }

      this.delayTimer.reset();
   }

   private Slot findBestFoodSlot(ScreenHandler handler) {
      return ItemUtils.findSlots(handler, slot -> slot.getStack().contains(DataComponentTypes.FOOD), false)
         .stream()
         .max(Comparator.comparing(slot2 -> ItemUtils.getFoodScore(slot2.getStack())))
         .orElse(null);
   }

   private Slot findBestPotionSlot(ScreenHandler handler) {
      return ItemUtils.findSlots(handler, slot -> slot.getStack().getItem() instanceof PotionItem, false)
         .stream()
         .max(Comparator.comparing(slot2 -> ItemUtils.getPotionScore(slot2.getStack())))
         .orElse(null);
   }

   private void swapFood(ScreenHandler handler, Slot targetSlot, Slot sourceSlot) {
      long longValue = KEY ^ 118873366397832L;
      boolean flag = Breaker.a();
      Slot tmp = sourceSlot;
      if (flag) {
         if (sourceSlot == null) {
            return;
         }

         tmp = sourceSlot;
      }

      if (flag) {
         if (tmp.getIndex() == targetSlot.getIndex()) {
            return;
         }

         tmp = sourceSlot;
      }

      double doubleValue = ItemUtils.getFoodScore(tmp.getStack());
      double doubleValue2 = ItemUtils.getFoodScore(targetSlot.getStack());
      if (flag) {
         if (!(doubleValue > doubleValue2)) {
            return;
         }

         ItemUtils.swapSlots(handler, sourceSlot.id, targetSlot.id - 36);
      }

      this.delayTimer.reset();
   }

   private void swapPotion(ScreenHandler handler, Slot targetSlot, Slot sourceSlot) {
      long longValue = KEY ^ 84450263448977L;
      boolean flag = Breaker.A();
      Slot tmp = sourceSlot;
      if (!flag) {
         if (sourceSlot == null) {
            return;
         }

         tmp = sourceSlot;
      }

      if (!flag) {
         if (tmp.getIndex() == targetSlot.getIndex()) {
            return;
         }

         tmp = sourceSlot;
      }

      double doubleValue = ItemUtils.getPotionScore(tmp.getStack());
      double doubleValue2 = ItemUtils.getPotionScore(targetSlot.getStack());
      if (!flag) {
         if (!(doubleValue > doubleValue2)) {
            return;
         }

         ItemUtils.swapSlots(handler, sourceSlot.id, targetSlot.id - 36);
      }

      this.delayTimer.reset();
   }

   public boolean hasDelayElapsed(long delay) {
      long longValue = KEY ^ 52644616923184L;
      boolean flag = Breaker.A();
      long longValue2;
      boolean tmp = (longValue2 = delay - 0L) != 0L;
      if (!flag) {
         if (tmp) {
            boolean elapsed = this.delayTimer.hasElapsed(delay);
            if (flag) {
               return elapsed;
            }

            if (!elapsed) {
               return false;
            }
         }

         tmp = true;
      }

      return tmp;
   }

   public BooleanGroupSetting getItemsToSort() {
      return this.itemsToSort;
   }

   public BooleanGroupSetting getItemsToDrop() {
      return this.itemsToDrop;
   }

   public DoubleSetting getDelay() {
      return this.G;
   }

   public DoubleSetting getSwordSlot() {
      return this.swordSlot;
   }

   public DoubleSetting getPickaxeSlot() {
      return this.pickaxeSlot;
   }

   public DoubleSetting getAxeSlot() {
      return this.axeSlot;
   }

   public DoubleSetting getShovelSlot() {
      return this.shovelSlot;
   }

   public DoubleSetting getBlockSlot() {
      return this.blockSlot;
   }

   public DoubleSetting getFoodSlot() {
      return this.foodSlot;
   }

   public DoubleSetting getPotionSlot() {
      return this.potionSlot;
   }

   public DoubleSetting getPearlSlot() {
      return this.pearlSlot;
   }

   public Stopwatch getDelayTimer() {
      return this.delayTimer;
   }

   public boolean isDuplicateWarned() {
      return this.duplicateWarned;
   }

   static {
      try {
      long var0 = KEY ^ 99103374863597L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[38];
      int var7 = 0;
      String var6 = "H\u00c1\u00c0\u001e\u0099\u00a5\u0013\u001fkrO9\u00aa\u00dd^\u0014\b\u00fd\u001fm\u001e\u008b\u00da$;\u0018v7\u00d8\u00a7\u0000\u00a8?Ir\u0097\u00d86{z>U\u0003 \u00fd@\u0002\u00a8\u00b9n\bbn\u00bf\u001e\u00dc\u0011LB\b\u00c2\u0085\u0015\u0084f$\u000br\b\u00b0\u00a8\u00cb\u00eeq\u0098\u00e1\u00ea\u0010\u00d1y\u0014\u00c9\u00c9%\u00e0\u00c6,f\u00c5\u001d~\u008d\u00c8\u00dd\u0010\u00d3\u00bf\u00d1\u00a9M\u00fe\u00c5\u00c6\u008et\u00a5e\u00a9\u0015\u0095\u00b8\b2\u0088U\u00bcW3\u00f3\u00a5\u0018\u00c6\u00e6\u00b1ek\u00c4[)<Z2@+)\u0091\u00afKu\u000bN?\u0006\u008b4\u0010\u00d3\u00bf\u00d1\u00a9M\u00fe\u00c5\u00c6\u00f1\u0084\u0098a\u00d2\u0017\u00eex\u0010\u000f\u00ab\u00bc\u00ca@\"\u008d\u0096Q\u0012\u00ab\u009e\u0003\u00ae\u00eef\b\f\u00eb\u008a\u0085;Qz\u001e\u0010H\u00c1\u00c0\u001e\u0099\u00a5\u0013\u001fkrO9\u00aa\u00dd^\u0014\bW\u009c\u00ec_q\u00efb\u00de\be\u0087,\u00af\u0005_\u00995\u0010\u00b3\u00ce\u008b(\f\u00a3\u00f18%\u00cb\u009c\u000fy\u0012\u00c0M08\u0097~k\u00c4sz\u009b\u00ce\u00ae\u00e1k\u00a7\u00c0\u00cbw\u0018u\u00d1\u0007\u0004\u0095\u00f1>\u000f/\u00b5\u0013D@I\u00fcxYF\u00beC\t\u00a3\u00f9z\u00c9\u007f\u00c4\u0001\u00f4\u0087O\b\u00a3xdH'\u001b\u001d\u0014\u0010\u008c\u0002i\u00e06Sn\u00b7\u0012\u00cc\u00f2\u0084\u009a\u00d5\u0083\r\bbn\u00bf\u001e\u00dc\u0011LB\b\u00e0\\\u00821\u00f3\u0091\b\u00a4\b\u00a3xdH'\u001b\u001d\u0014\be\u0087,\u00af\u0005_\u00995\ba\u00e1\u00d4cl\u00b7\u008an\b2\u0088U\u00bcW3\u00f3\u00a5\u0010\u0002\u00d0\u008a\u00a5\u0092\u00d3\"\u00f9\u0091\u00f7]-45\u0087\u00aa\b\u00b0\u00a8\u00cb\u00eeq\u0098\u00e1\u00ea \u00d3,\u00cc\u00b9\u00fa\u00a1\u008f\u008d\u00d56\b5\u001e\u0015&\u00d2~Ed5\u0019\u0012d\u00fd\u0098\u00a6q\b\u0082\u0097\u0015B\b\u0016w\u000e\u001d\u00a6\u00bc\u0081L\b\f\u00eb\u008a\u0085;Qz\u001e\u0010\u00df_\u0014!\u0005\u0001\u008f\u00d2\u00b2\u0001K\u00e8\u00ba\u00be^\b\b\u00c2\u0085\u0015\u0084f$\u000br\u0010\u0092\u0005\u00ee\u00cbI\u00e9(\u0010\u0005Z\u00f1\u0019]\u00fc\u00a7\u00cf\b\u00e0\\\u00821\u00f3\u0091\b\u00a4\ba\u00e1\u00d4cl\u00b7\u008an";
      int var8 = "H\u00c1\u00c0\u001e\u0099\u00a5\u0013\u001fkrO9\u00aa\u00dd^\u0014\b\u00fd\u001fm\u001e\u008b\u00da$;\u0018v7\u00d8\u00a7\u0000\u00a8?Ir\u0097\u00d86{z>U\u0003 \u00fd@\u0002\u00a8\u00b9n\bbn\u00bf\u001e\u00dc\u0011LB\b\u00c2\u0085\u0015\u0084f$\u000br\b\u00b0\u00a8\u00cb\u00eeq\u0098\u00e1\u00ea\u0010\u00d1y\u0014\u00c9\u00c9%\u00e0\u00c6,f\u00c5\u001d~\u008d\u00c8\u00dd\u0010\u00d3\u00bf\u00d1\u00a9M\u00fe\u00c5\u00c6\u008et\u00a5e\u00a9\u0015\u0095\u00b8\b2\u0088U\u00bcW3\u00f3\u00a5\u0018\u00c6\u00e6\u00b1ek\u00c4[)<Z2@+)\u0091\u00afKu\u000bN?\u0006\u008b4\u0010\u00d3\u00bf\u00d1\u00a9M\u00fe\u00c5\u00c6\u00f1\u0084\u0098a\u00d2\u0017\u00eex\u0010\u000f\u00ab\u00bc\u00ca@\"\u008d\u0096Q\u0012\u00ab\u009e\u0003\u00ae\u00eef\b\f\u00eb\u008a\u0085;Qz\u001e\u0010H\u00c1\u00c0\u001e\u0099\u00a5\u0013\u001fkrO9\u00aa\u00dd^\u0014\bW\u009c\u00ec_q\u00efb\u00de\be\u0087,\u00af\u0005_\u00995\u0010\u00b3\u00ce\u008b(\f\u00a3\u00f18%\u00cb\u009c\u000fy\u0012\u00c0M08\u0097~k\u00c4sz\u009b\u00ce\u00ae\u00e1k\u00a7\u00c0\u00cbw\u0018u\u00d1\u0007\u0004\u0095\u00f1>\u000f/\u00b5\u0013D@I\u00fcxYF\u00beC\t\u00a3\u00f9z\u00c9\u007f\u00c4\u0001\u00f4\u0087O\b\u00a3xdH'\u001b\u001d\u0014\u0010\u008c\u0002i\u00e06Sn\u00b7\u0012\u00cc\u00f2\u0084\u009a\u00d5\u0083\r\bbn\u00bf\u001e\u00dc\u0011LB\b\u00e0\\\u00821\u00f3\u0091\b\u00a4\b\u00a3xdH'\u001b\u001d\u0014\be\u0087,\u00af\u0005_\u00995\ba\u00e1\u00d4cl\u00b7\u008an\b2\u0088U\u00bcW3\u00f3\u00a5\u0010\u0002\u00d0\u008a\u00a5\u0092\u00d3\"\u00f9\u0091\u00f7]-45\u0087\u00aa\b\u00b0\u00a8\u00cb\u00eeq\u0098\u00e1\u00ea \u00d3,\u00cc\u00b9\u00fa\u00a1\u008f\u008d\u00d56\b5\u001e\u0015&\u00d2~Ed5\u0019\u0012d\u00fd\u0098\u00a6q\b\u0082\u0097\u0015B\b\u0016w\u000e\u001d\u00a6\u00bc\u0081L\b\f\u00eb\u008a\u0085;Qz\u001e\u0010\u00df_\u0014!\u0005\u0001\u008f\u00d2\u00b2\u0001K\u00e8\u00ba\u00be^\b\b\u00c2\u0085\u0015\u0084f$\u000br\u0010\u0092\u0005\u00ee\u00cbI\u00e9(\u0010\u0005Z\u00f1\u0019]\u00fc\u00a7\u00cf\b\u00e0\\\u00821\u00f3\u0091\b\u00a4\ba\u00e1\u00d4cl\u00b7\u008an"
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

                  var6 = "\u00baVs\u00db\tO g\u0098)\u00a1\u0092W\u00e1\u0080\u00af\b\u0016w\u000e\u001d\u00a6\u00bc\u0081L";
                  var8 = "\u00baVs\u00db\tO g\u0098)\u00a1\u0092W\u00e1\u0080\u00af\b\u0016w\u000e\u001d\u00a6\u00bc\u0081L".length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var20) {
         throw new ExceptionInInitializerError(var20);
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
