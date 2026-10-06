package club.evon.util;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.CandleCakeBlock;
import net.minecraft.block.CartographyTableBlock;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.CaveVinesBodyBlock;
import net.minecraft.block.CaveVinesHeadBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.CobwebBlock;
import net.minecraft.block.CommandBlock;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.LightBlock;
import net.minecraft.block.LoomBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SlimeBlock;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BedItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CompassItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.HypixelTargetFilter;
import club.evon.data.StatusEffectScores;
import club.evon.manager.ClientState;




public final class ItemUtils {
   private static final List<Block> interactiveBlocks;
   public static final Map<StatusEffect, Integer> effectScores;
   private static final long KEY = KeyedRandom.create(-3993606808558606501L, 4520460332699325717L, MethodHandles.lookup().lookupClass()).next(204043652797140L);
   private static final String PUMPKIN_NAME;

   public static boolean isInteractiveBlock(Block block) {
      return interactiveBlocks.contains(block);
   }

   public static int getEffectScore(StatusEffect statusEffect) {
      return effectScores.getOrDefault(statusEffect, -1);
   }

   public static int getPrimaryEffectScore(ItemStack itemStack) {
      return getEffectScore(getPrimaryEffect(itemStack));
   }

   public static void dropSlot(ScreenHandler screenHandler, int slot) {
      ClientContext.MINECRAFT_CLIENT.interactionManager.clickSlot(screenHandler.syncId, slot, 1, SlotActionType.THROW, ClientContext.MINECRAFT_CLIENT.player);
   }

   public static void quickMoveSlot(ScreenHandler screenHandler, int slot, int button) {
      ClientContext.MINECRAFT_CLIENT.interactionManager.clickSlot(screenHandler.syncId, slot, button, SlotActionType.QUICK_MOVE, ClientContext.MINECRAFT_CLIENT.player);
   }

   public static void swapSlots(ScreenHandler screenHandler, int oldSlot, int newSlot) {
      ClientContext.MINECRAFT_CLIENT.interactionManager.clickSlot(screenHandler.syncId, oldSlot, newSlot, SlotActionType.SWAP, ClientContext.MINECRAFT_CLIENT.player);
   }

   public static boolean isInventoryFull() {
      return ClientContext.MINECRAFT_CLIENT.player.getInventory().getMainStacks().stream().noneMatch(ItemStack::isEmpty);
   }

   public static List<Slot> findSlots(ScreenHandler screenHandler, Predicate<Slot> filterCondition, boolean shuffle) {
      long longValue = KEY ^ 63276819098266L;
      List collect = screenHandler.slots.stream().filter(filterCondition).collect(Collectors.toList());
      if (shuffle) {
         Collections.shuffle(collect);
      }

      return collect;
   }

   public static float getArmorScore(ItemStack itemStack) {
      long longValue = KEY ^ 95303730047550L;
      String[] strings = PlayerUtil.getGuard();
      ItemStack tmp = itemStack;
      if (strings != null) {
         if (!isArmor(itemStack)) {
            return 0.0F;
         }

         tmp = itemStack;
      }

      float floatValue = (float)PlayerUtil.getArmor(tmp);
      Double attributeValue = getAttributeValue(itemStack, EntityAttributes.ARMOR_TOUGHNESS);
      if (attributeValue != null) {
         floatValue += attributeValue.floatValue() * 0.5F;
      }

      int value = getEnchantmentLevel(itemStack, Enchantments.PROTECTION);
      int value2 = getEnchantmentLevel(itemStack, Enchantments.THORNS);
      int value3 = getEnchantmentLevel(itemStack, Enchantments.UNBREAKING);
      floatValue += value;
      floatValue += value2 * 0.1F;
      floatValue += value3 * 0.3F;
      float floatValue2 = itemStack.getMaxDamage();
      float floatValue3 = itemStack.getDamage();
      float floatValue4 = floatValue2 - floatValue3;
      float floatValue5 = floatValue2;
      if (strings != null) {
         if (floatValue2 > 0.0F) {
            float floatValue6 = floatValue4 / floatValue2;
            floatValue *= 0.2F + 0.8F * floatValue6;
         }

         floatValue5 = floatValue;
      }

      return floatValue5;
   }

   public static Double getAttributeValue(ItemStack itemStack, RegistryEntry<EntityAttribute> attribute) {
      ComponentMap components = itemStack.getComponents();
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)components.getOrDefault(
         DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT
      );
      return attributeModifiersComponent.modifiers().stream().filter(entry -> entry.attribute().equals(attribute)).map(entry2 -> entry2.modifier().value()).findFirst().orElse(null);
   }

   public static float getSwordScore(ItemStack itemStack) {
      long longValue = KEY ^ 89653781512494L;
      String[] strings = PlayerUtil.getGuard();
      ItemStack tmp = itemStack;
      if (strings != null) {
         if (!itemStack.isIn(ItemTags.SWORDS)) {
            return 0.0F;
         }

         tmp = itemStack;
      }

      float floatValue;
      label46: {
         Double attributeValue = getAttributeValue(tmp, EntityAttributes.ATTACK_DAMAGE);
         Double doubleValue = attributeValue;
         if (strings != null) {
            if (attributeValue == null) {
               floatValue = 0.0F;
               break label46;
            }

            doubleValue = attributeValue;
         }

         floatValue = doubleValue.floatValue();
      }

      float floatValue2 = floatValue;
      float floatValue3 = getEnchantmentLevel(itemStack, Enchantments.SHARPNESS);
      float floatValue4 = getEnchantmentLevel(itemStack, Enchantments.UNBREAKING);
      float floatValue5 = getEnchantmentLevel(itemStack, Enchantments.FIRE_ASPECT);
      float floatValue6 = getEnchantmentLevel(itemStack, Enchantments.LOOTING);
      float floatValue7 = getEnchantmentLevel(itemStack, Enchantments.KNOCKBACK);
      float floatValue8;
      int value = (floatValue8 = floatValue3 - 0.0F) == 0.0F ? 0 : (floatValue8 < 0.0F ? -1 : 1);
      if (strings != null) {
         if (value > 0) {
            floatValue2 += floatValue3 * 0.5F + 0.5F;
         }

         floatValue2 += floatValue5 * 0.1F;
         floatValue2 += floatValue6 * 0.1F;
         floatValue2 += floatValue4 * 0.3F;
         floatValue2 += floatValue7 * 0.1F;
         value = itemStack.getMaxDamage();
      }

      float floatValue9 = value;
      float floatValue10 = itemStack.getDamage();
      float floatValue11 = floatValue9 - floatValue10;
      float floatValue12 = floatValue9;
      if (strings != null) {
         if (floatValue9 > 0.0F) {
            float floatValue13 = floatValue11 / floatValue9;
            floatValue2 *= 0.2F + 0.8F * floatValue13;
         }

         floatValue12 = floatValue2;
      }

      return floatValue12;
   }

   public static float getToolScore(ItemStack itemStack) {
      long longValue = KEY ^ 27808436390876L;
      String[] strings = PlayerUtil.getGuard();
      ToolComponent toolComponent = (ToolComponent)itemStack.get(DataComponentTypes.TOOL);
      String[] strings2 = strings;
      if (toolComponent == null) {
         return 0.0F;
      }

      float floatValue;
      int value;
      label70: {
         label74: {
            Item item = itemStack.getItem();
            floatValue = 0.0F;
            float floatValue2 = getEnchantmentLevel(itemStack, Enchantments.EFFICIENCY) * 1.2F;
            float floatValue3 = getEnchantmentLevel(itemStack, Enchantments.UNBREAKING) * 0.3F;
            floatValue += floatValue2;
            floatValue += floatValue3;
            value = itemStack.isIn(ItemTags.AXES) ? 1 : 0;
            if (strings2 != null) {
               if (value != 0) {
                  label64: {
                     floatValue += toolComponent.getSpeed(Blocks.OAK_LOG.getDefaultState()) * 1.1F;
                     float floatValue4 = getEnchantmentLevel(itemStack, Enchantments.SHARPNESS);
                     floatValue += (float)PlayerUtil.getAttackDamage(itemStack);
                     float floatValue5 = floatValue4;
                     float floatValue6 = 0.0F;
                     if (strings2 != null) {
                        if (!(floatValue4 > 0.0F)) {
                           break label64;
                        }

                        floatValue5 = floatValue;
                        floatValue6 = floatValue4 * 0.5F + 0.5F;
                     }

                     floatValue = floatValue5 + floatValue6;
                  }

                  if (strings2 != null) {
                     break label74;
                  }
               }

               value = itemStack.isIn(ItemTags.PICKAXES) ? 1 : 0;
            }

            if (strings2 != null) {
               if (value != 0) {
                  floatValue += toolComponent.getSpeed(Blocks.STONE.getDefaultState()) * 1.3F;
                  if (strings2 != null) {
                     break label74;
                  }
               }

               value = itemStack.isIn(ItemTags.SHOVELS) ? 1 : 0;
            }

            if (strings2 == null) {
               break label70;
            }

            if (value != 0) {
               floatValue += toolComponent.getSpeed(Blocks.DIRT.getDefaultState()) * 1.2F;
            }
         }

         value = itemStack.getMaxDamage();
      }

      float floatValue7 = value;
      float floatValue8 = itemStack.getDamage();
      float floatValue9 = floatValue7 - floatValue8;
      float floatValue10 = floatValue7;
      if (strings2 != null) {
         if (floatValue7 > 0.0F) {
            float floatValue11 = floatValue9 / floatValue7;
            floatValue *= 0.2F + 0.8F * floatValue11;
         }

         floatValue10 = floatValue;
      }

      return floatValue10;
   }

   public static float getStackBlockCount(ItemStack stack) {
      long longValue = KEY ^ 17554706539717L;
      String[] strings = PlayerUtil.getGuard();
      Item item = stack.getItem();
      String[] strings2 = strings;
      Item item2 = item;
      if (strings2 != null) {
         if (!(item instanceof BlockItem)) {
            return 0.0F;
         }

         item2 = item;
      }

      BlockItem blockItem = (BlockItem)item2;
      int value = isPlaceableBlock(blockItem.getBlock()) ? 1 : 0;
      if (strings2 != null) {
         if (value == 0) {
            return 0.0F;
         }

         value = stack.getCount();
      }

      return value;
   }

   public static float getFoodScore(ItemStack stack) {
      long longValue = KEY ^ 138094477952598L;
      String[] strings = PlayerUtil.getGuard();
      FoodComponent foodComponent = (FoodComponent)stack.get(DataComponentTypes.FOOD);
      String[] strings2 = strings;
      if (foodComponent == null) {
         return 0.0F;
      }

      float floatValue;
      ItemStack stack2;
      label58: {
         floatValue = 0.0F;
         boolean of = stack.isOf(Items.ENCHANTED_GOLDEN_APPLE);
         if (strings2 != null) {
            if (of) {
               return 100000.0F;
            }

            stack2 = stack;
            if (strings2 == null) {
               break label58;
            }

            of = stack.isOf(Items.GOLDEN_APPLE);
         }

         if (of) {
            return 50000.0F;
         }

         floatValue += foodComponent.nutrition() * 10.0F;
         floatValue += foodComponent.saturation() * 15.0F;
         stack2 = stack;
      }

      List potionEffects = getPotionEffects(stack2);
      if (strings2 != null) {
         label44:
         if (potionEffects != null) {
            int value = potionEffects.isEmpty() ? 1 : 0;
            if (strings2 != null) {
               if (value != 0) {
                  break label44;
               }

               value = potionEffects.stream().mapToInt(object -> effectScores.getOrDefault(((StatusEffectInstance)object).getEffectType().value(), -5) * (((StatusEffectInstance)object).getAmplifier() + 1)).sum();
            }

            int value2 = value;
            floatValue += value2 * 20.0F;
         }

         floatValue *= Math.min(stack.getCount(), 64);
      }

      return floatValue;
   }

   public static float getPotionScore(ItemStack itemStack) {
      long longValue = KEY ^ 69194342182464L;
      String[] strings = PlayerUtil.getGuard();
      ItemStack tmp = itemStack;
      if (strings != null) {
         if (!(itemStack.getItem() instanceof PotionItem)) {
            return 0.0F;
         }

         tmp = itemStack;
      }

      List potionEffects = getPotionEffects(tmp);
      List list = potionEffects;
      if (strings != null) {
         if (potionEffects == null) {
            return 0.0F;
         }

         list = potionEffects;
      }

      return list.stream().mapToInt(object -> effectScores.getOrDefault(((StatusEffectInstance)object).getEffectType().value(), -1)).sum();
   }

   public static boolean isUsefulItem(ItemStack itemStack) {
      long longValue = KEY ^ 128393775253146L;
      String[] strings = PlayerUtil.getGuard();
      Item item = itemStack.getItem();
      String[] strings2 = strings;
      Item item2 = item;
      if (strings2 != null) {
         if (item instanceof BlockItem blockItem) {
            return isPlaceableBlock(blockItem.getBlock());
         }

         item2 = item;
      }

      Item tmp = Items.PLAYER_HEAD;
      if (strings2 != null) {
         if (item2 == Items.PLAYER_HEAD) {
            return false;
         }

         item2 = item;
         tmp = Items.PUMPKIN;
      }

      label157: {
         if (strings2 != null) {
            if (item2 == tmp) {
               return false;
            }

            item2 = item;
            if (strings2 == null) {
               break label157;
            }

            tmp = Items.CARVED_PUMPKIN;
         }

         if (item2 == tmp) {
            return false;
         }

         item2 = item;
      }

      boolean flag = item2 instanceof EnderPearlItem;
      if (strings2 != null) {
         if (!flag) {
            boolean flag2 = item instanceof PotionItem;
            if (strings2 == null) {
               return flag2;
            }

            if (!flag2) {
               boolean flag3 = item instanceof ShieldItem;
               if (strings2 == null) {
                  return flag3;
               }

               if (!flag3) {
                  boolean flag4 = item instanceof FireChargeItem;
                  if (strings2 == null) {
                     return flag4;
                  }

                  if (!flag4) {
                     boolean contains = item.getComponents().contains(DataComponentTypes.FOOD);
                     if (strings2 == null) {
                        return contains;
                     }

                     if (!contains) {
                        boolean flag5 = item instanceof BowItem;
                        if (strings2 == null) {
                           return flag5;
                        }

                        if (!flag5) {
                           boolean flag6 = item instanceof ArrowItem;
                           if (strings2 == null) {
                              return flag6;
                           }

                           if (!flag6) {
                              boolean flag7 = item instanceof ShieldItem;
                              if (strings2 == null) {
                                 return flag7;
                              }

                              label119:
                              if (!flag7) {
                                 Item item3 = item;
                                 if (strings2 != null) {
                                    if (item == Items.BARRIER) {
                                       break label119;
                                    }

                                    item3 = item;
                                 }

                                 boolean flag8 = item3 instanceof CompassItem;
                                 if (strings2 == null) {
                                    return flag8;
                                 }

                                 if (!flag8) {
                                    boolean flag9 = item instanceof BedItem;
                                    if (strings2 == null) {
                                       return flag9;
                                    }

                                    if (!flag9) {
                                       return false;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         flag = true;
      }

      return flag;
   }

   public static EquipmentSlot getEquipmentSlot(Item item) {
      long longValue = KEY ^ 238450860629L;
      String[] strings = PlayerUtil.getGuard();
      EquippableComponent equippableComponent = (EquippableComponent)item.getComponents().get(DataComponentTypes.EQUIPPABLE);
      String[] strings2 = strings;
      EquippableComponent equippableComponent2 = equippableComponent;
      if (strings2 != null) {
         if (equippableComponent == null) {
            return null;
         }

         equippableComponent2 = equippableComponent;
      }

      return equippableComponent2.slot();
   }

   public static boolean isArmor(ItemStack itemStack) {
      String[] strings;
      Object tmp;
      label48: {
         long longValue = KEY ^ 101402317505558L;
         strings = PlayerUtil.getGuard();
         tmp = itemStack.getItem();
         Item tmp2 = Items.PLAYER_HEAD;
         if (strings != null) {
            if (tmp == Items.PLAYER_HEAD) {
               return false;
            }

            tmp = itemStack.getItem();
            if (strings == null) {
               break label48;
            }

            tmp2 = Items.PUMPKIN;
         }

         if (tmp == tmp2) {
            return false;
         }

         tmp = itemStack.getComponents().get(DataComponentTypes.EQUIPPABLE);
      }

      EquippableComponent equippableComponent = (EquippableComponent)tmp;
      List asList = Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
      if (equippableComponent != null) {
         boolean anyMatch = asList.stream().anyMatch(var1x -> {
            long longValue2 = KEY ^ 68591633311477L;
            return equippableComponent.slot() == var1x;
         });
         if (strings == null) {
            return anyMatch;
         }

         if (anyMatch) {
            return true;
         }
      }

      return false;
   }

   public static int findItemSlot(Item item, boolean hotbarOnly) {
      long longValue = KEY ^ 98266066155570L;
      String[] strings = PlayerUtil.getGuard();
      int index = 0;
      String[] strings2 = strings;

      while (true) {
         byte byteValue;
         if (hotbarOnly) {
            byteValue = 9;
            if (strings2 != null) {
            }
         } else {
            byteValue = 45;
         }

         if (index < byteValue) {
            ItemStack stack = ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(index);
            if (strings2 != null) {
               if (!stack.isEmpty()) {
                  int value = stack.getItem().equals(item) ? 1 : 0;
                  if (strings2 == null) {
                     return value;
                  }

                  if (value != 0) {
                     return index;
                  }
               }

               index++;
            }

            if (strings2 != null) {
               continue;
            }
         }

         return -1;
      }
   }

   public static boolean isPlaceableBlock(Block block) {
      String[] strings;
      boolean tmp;
      long longValue = KEY ^ 123818065747151L;
      strings = PlayerUtil.getGuard();
      tmp = block instanceof CarvedPumpkinBlock;
      Block block2;
      label78:
      if (strings != null) {
         label76: {
            if (tmp) {
               block2 = block;
               if (strings == null) {
                  break label76;
               }

               if (block.getName().getString() != null) {
                  tmp = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter;
                  if (strings == null) {
                     break label78;
                  }

                  if (tmp) {
                     String strip = Formatting.strip(block.getName().getString());
                     tmp = strip.equalsIgnoreCase(PUMPKIN_NAME);
                     if (strings == null) {
                        break label78;
                     }

                     if (tmp) {
                        return false;
                     }
                  }
               }
            }

            block2 = block;
         }

         tmp = block2 instanceof FallingBlock;
      }

      if (strings != null) {
         if (tmp) {
            return false;
         }

         tmp = isInteractiveBlock(block);
      }

      if (strings != null) {
         if (tmp) {
            return false;
         }

         tmp = block.getDefaultState().isFullCube(ClientContext.MINECRAFT_CLIENT.world, BlockPos.ORIGIN);
      }

      return strings == null ? tmp : tmp;
   }

   public static int findBestBlockSlot() {
      long longValue = KEY ^ 123253400030794L;
      String[] strings = PlayerUtil.getGuard();
      int index = 0;
      String[] strings2 = strings;
      int value = -1;
      int index2 = 0;
      int value2;
      int value3;

      while (true) {
         if (index2 < 9) {
            ItemStack stack = ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(index2);
            Item item = stack.getItem();
            label40:
            if (strings2 != null) {
               value2 = item instanceof BlockItem ? 1 : 0;
               if (strings2 == null) {
                  break;
               }

               if (value2 != 0) {
                  BlockItem blockItem = (BlockItem)item;
                  if (strings2 == null) {
                     break label40;
                  }

                  label51:
                  if (isPlaceableBlock(blockItem.getBlock())) {
                     value3 = value;
                     label49:
                     if (strings2 != null) {
                        if (value != -1) {
                           value3 = stack.getCount();
                           if (strings2 == null) {
                              break label49;
                           }

                           if (value3 <= index) {
                              break label51;
                           }
                        }

                        index = stack.getCount();
                        value3 = index2;
                     }

                     value = value3;
                  }
               }

               index2++;
            }

            if (strings2 != null) {
               continue;
            }
         }

         value2 = value;
         break;
      }

      return value2;
   }

   public static int countPlaceableBlocks() {
      long longValue = KEY ^ 111442000257591L;
      String[] strings = PlayerUtil.getGuard();
      int index = 0;
      int index2 = 0;
      String[] strings2 = strings;
      int value;

      while (true) {
         if (index2 < 9) {
            ItemStack stack = ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(index2);
            Item item = stack.getItem();
            label32:
            if (strings2 != null) {
               value = item instanceof BlockItem ? 1 : 0;
               if (strings2 == null) {
                  break;
               }

               if (value != 0) {
                  BlockItem blockItem = (BlockItem)item;
                  if (strings2 == null) {
                     break label32;
                  }

                  if (isPlaceableBlock(blockItem.getBlock())) {
                     index += stack.getCount();
                  }
               }

               index2++;
            }

            if (strings2 != null) {
               continue;
            }
         }

         value = index;
         break;
      }

      return value;
   }

   public static int findBestToolSlot(BlockPos blockPos) {
      long longValue = KEY ^ 66329290832537L;
      String[] strings = PlayerUtil.getGuard();
      BlockState blockState = ClientContext.MINECRAFT_CLIENT.world.getBlockState(blockPos);
      String[] strings2 = strings;
      float floatValue = blockState.getHardness(ClientContext.MINECRAFT_CLIENT.world, blockPos);
      float floatValue2 = floatValue;
      if (strings2 != null) {
         if (floatValue == 0.0F) {
            return -1;
         }

         floatValue2 = -1.0F;
      }

      float floatValue3 = floatValue2;
      int value = -1;
      int index = 0;
      int value2;

      while (true) {
         if (index < 9) {
            label76: {
               label84: {
                  ItemStack stack = ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(index);
                  ItemStack stack2 = stack;
                  label74:
                  if (strings2 != null) {
                     value2 = stack.isIn(ItemTags.PICKAXES) ? 1 : 0;
                     if (strings2 == null) {
                        break;
                     }

                     if (value2 == 0) {
                        stack2 = stack;
                        if (strings2 == null) {
                           break label74;
                        }

                        if (!stack.isIn(ItemTags.AXES)) {
                           stack2 = stack;
                           if (strings2 == null) {
                              break label74;
                           }

                           if (!stack.isIn(ItemTags.SHOVELS)) {
                              stack2 = stack;
                              if (strings2 == null) {
                                 break label74;
                              }

                              if (!(stack.getItem() instanceof ShearsItem)) {
                                 break label84;
                              }
                           }
                        }
                     }

                     stack2 = stack;
                  }

                  float floatValue4 = getMiningSpeed(stack2, blockState);
                  if (strings2 == null) {
                     break label76;
                  }

                  if (floatValue4 > floatValue3) {
                     floatValue3 = floatValue4;
                     value = index;
                  }
               }

               index++;
            }

            if (strings2 != null) {
               continue;
            }
         }

         value2 = value;
         break;
      }

      return value2;
   }

   public static int findBestSwordSlot() {
      long longValue = KEY ^ 139907179498760L;
      String[] strings = PlayerUtil.getGuard();
      int value = -1;
      double doubleValue = 1.0;
      String[] strings2 = strings;
      int index = 0;
      int value2;
      int value3;

      while (true) {
         if (index < 9) {
            ItemStack stack = ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(index);
            if (strings2 != null) {
               value2 = stack.isIn(ItemTags.SWORDS) ? 1 : 0;
               if (strings2 == null) {
                  break;
               }

               label44:
               if (value2 != 0) {
                  double doubleValue2 = getSwordScore(stack);
                  value3 = value;
                  label42:
                  if (strings2 != null) {
                     if (value != -1) {
                        double doubleValue3;
                        value3 = (doubleValue3 = doubleValue2 - doubleValue) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
                        if (strings2 == null) {
                           break label42;
                        }

                        if (value3 <= 0) {
                           break label44;
                        }
                     }

                     doubleValue = doubleValue2;
                     value3 = index;
                  }

                  value = value3;
               }

               index++;
            }

            if (strings2 != null) {
               continue;
            }
         }

         value2 = value;
         break;
      }

      return value2;
   }

   public static int findBestPotionSlot() {
      long longValue = KEY ^ 125568246223442L;
      String[] strings = PlayerUtil.getGuard();
      int value = -1;
      String[] strings2 = strings;
      double doubleValue = 1.0;
      int index = 0;
      int value2;
      int value3;

      while (true) {
         if (index < 9) {
            ItemStack stack = ClientContext.MINECRAFT_CLIENT.player.getInventory().getStack(index);
            if (strings2 != null) {
               value2 = stack.getItem().equals(Items.SPLASH_POTION) ? 1 : 0;
               if (strings2 == null) {
                  break;
               }

               label44:
               if (value2 != 0) {
                  double doubleValue2 = getPotionScore(stack);
                  value3 = value;
                  label42:
                  if (strings2 != null) {
                     if (value != -1) {
                        double doubleValue3;
                        value3 = (doubleValue3 = doubleValue2 - doubleValue) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);
                        if (strings2 == null) {
                           break label42;
                        }

                        if (value3 <= 0) {
                           break label44;
                        }
                     }

                     doubleValue = doubleValue2;
                     value3 = index;
                  }

                  value = value3;
               }

               index++;
            }

            if (strings2 != null) {
               continue;
            }
         }

         value2 = value;
         break;
      }

      return value2;
   }

   public static List<StatusEffectInstance> getPotionEffects(ItemStack itemStack) {
      long longValue = KEY ^ 116991847649942L;
      if (!(itemStack.getItem() instanceof PotionItem)) {
         return null;
      }

      ArrayList list = new ArrayList();
      ((PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS)).forEachEffect(list::add, 1.0F);
      return list;
   }

   public static StatusEffect getPrimaryEffect(ItemStack stack) {
      long longValue = KEY ^ 129624676789167L;
      String[] strings = PlayerUtil.getGuard();
      List potionEffects = getPotionEffects(stack);
      String[] strings2 = strings;
      Object object = potionEffects;
      if (strings2 != null) {
         if (potionEffects == null) {
            return null;
         }

         object = potionEffects;
      }

      if (strings2 != null) {
         if (((List)object).isEmpty()) {
            return null;
         }

         object = ((StatusEffectInstance)potionEffects.get(0)).getEffectType().value();
      }

      return (StatusEffect)object;
   }

   public static float getMiningSpeed(ItemStack stack, BlockState state) {
      long longValue = KEY ^ 68520896703937L;
      String[] strings = PlayerUtil.getGuard();
      float floatValue = stack.getMiningSpeedMultiplier(state);
      String[] strings2 = strings;
      float floatValue2 = floatValue;
      if (strings2 != null) {
         label24:
         if (floatValue > 1.0F) {
            int value = getEnchantmentLevel(stack, Enchantments.EFFICIENCY);
            int value2 = value;
            if (strings2 != null) {
               if (value <= 0) {
                  break label24;
               }

               value2 = stack.isEmpty() ? 1 : 0;
            }

            if (value2 == 0) {
               floatValue += value * value + 1;
            }
         }

         floatValue2 = floatValue;
      }

      return floatValue2;
   }

   public static void collectEnchantments(ItemStack stack, Object2IntMap<RegistryEntry<Enchantment>> enchantments) {
      long longValue = KEY ^ 43196226692713L;
      String[] strings = PlayerUtil.getGuard();
      enchantments.clear();
      String[] strings2 = strings;
      ItemStack stack2 = stack;
      if (strings2 != null) {
         if (stack.isEmpty()) {
            return;
         }

         stack2 = stack;
      }

      for (Entry entry : stack2.getEnchantments().getEnchantmentEntries()) {
         enchantments.put((RegistryEntry)entry.getKey(), entry.getIntValue());
         if (strings2 == null) {
            break;
         }
      }
   }

   public static int getEnchantmentLevel(ItemStack stack, RegistryKey<Enchantment> enchantment) {
      long longValue = KEY ^ 118218348129453L;
      String[] strings = PlayerUtil.getGuard();
      int value = stack.isEmpty() ? 1 : 0;
      if (strings != null) {
         if (value == 0) {
            Object2IntArrayMap map = new Object2IntArrayMap();
            collectEnchantments(stack, map);
            return lookupEnchantmentLevel(map, enchantment);
         }

         value = 0;
      }

      return value;
   }

   public static int lookupEnchantmentLevel(Object2IntMap<RegistryEntry<Enchantment>> itemEnchantments, RegistryKey<Enchantment> enchantment) {
      long longValue = KEY ^ 47398703005074L;
      String[] strings = PlayerUtil.getGuard();
      ObjectIterator iterator = Object2IntMaps.fastIterable(itemEnchantments).iterator();
      String[] strings2 = strings;
      int value;

      while (true) {
         if (iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            value = ((RegistryEntry)entry.getKey()).matchesKey(enchantment) ? 1 : 0;
            if (strings2 == null) {
               break;
            }

            if (strings2 == null) {
               return value;
            }

            if (value != 0) {
               return entry.getIntValue();
            }

            if (strings2 != null) {
               continue;
            }
         }

         value = 0;
         break;
      }

      return value;
   }

   public static boolean hasEnchantment(ItemStack itemStack, RegistryKey<Enchantment> enchantmentKey) {
      long longValue = KEY ^ 72609644816506L;
      String[] strings = PlayerUtil.getGuard();
      boolean empty = itemStack.isEmpty();
      if (strings != null) {
         if (!empty) {
            Object2IntArrayMap map = new Object2IntArrayMap();
            collectEnchantments(itemStack, map);
            return containsEnchantment(map, enchantmentKey);
         }

         empty = false;
      }

      return empty;
   }

   private static boolean containsEnchantment(Object2IntMap<RegistryEntry<Enchantment>> map, RegistryKey<Enchantment> registryKeyEnchantment) {
      long longValue = KEY ^ 97463493141421L;
      String[] strings = PlayerUtil.getGuard();
      ObjectIterator iterator = map.keySet().iterator();
      String[] strings2 = strings;
      boolean flag;

      while (true) {
         if (iterator.hasNext()) {
            RegistryEntry registryEntry = (RegistryEntry)iterator.next();
            flag = registryEntry.matchesKey(registryKeyEnchantment);
            if (strings2 == null) {
               break;
            }

            if (strings2 == null) {
               return flag;
            }

            if (flag) {
               return true;
            }

            if (strings2 != null) {
               continue;
            }
         }

         flag = false;
         break;
      }

      return flag;
   }

   static {
      try {
      long var0 = KEY ^ 11259353683403L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var4 = var2.doFinal("/\u00ba\u0091`\u0093\u0091-\u00e0\u0006\u00e2\u00d1\u00b5|;\u009b\u00d4N\u00c2\\W\u0014\u00f7\u00ffQ".getBytes("ISO-8859-1"));
      String var5 = decodeUtf8(var4).intern();
      byte var10001 = -1;
      PUMPKIN_NAME = var5;
      interactiveBlocks = Registries.BLOCK
         .stream()
         .filter(
            var0x -> {
               long var1 = KEY ^ 19379258180635L;
               String[] var3x = PlayerUtil.getGuard();
               boolean var10000x = var0x instanceof TrapdoorBlock;
               if (var3x != null) {
                  if (!var10000x) {
                     var10000x = var0x instanceof SweetBerryBushBlock;
                     if (var3x == null) {
                        return var10000x;
                     }

                     if (!var10000x) {
                        var10000x = var0x instanceof AbstractFurnaceBlock;
                        if (var3x == null) {
                           return var10000x;
                        }

                        if (!var10000x) {
                           var10000x = var0x instanceof AbstractSignBlock;
                           if (var3x == null) {
                              return var10000x;
                           }

                           if (!var10000x) {
                              var10000x = var0x instanceof AnvilBlock;
                              if (var3x == null) {
                                 return var10000x;
                              }

                              if (!var10000x) {
                                 var10000x = var0x instanceof BarrelBlock;
                                 if (var3x == null) {
                                    return var10000x;
                                 }

                                 if (!var10000x) {
                                    var10000x = var0x instanceof BeaconBlock;
                                    if (var3x == null) {
                                       return var10000x;
                                    }

                                    if (!var10000x) {
                                       var10000x = var0x instanceof BedBlock;
                                       if (var3x == null) {
                                          return var10000x;
                                       }

                                       if (!var10000x) {
                                          var10000x = var0x instanceof BellBlock;
                                          if (var3x == null) {
                                             return var10000x;
                                          }

                                          if (!var10000x) {
                                             var10000x = var0x instanceof BrewingStandBlock;
                                             if (var3x == null) {
                                                return var10000x;
                                             }

                                             if (!var10000x) {
                                                var10000x = var0x instanceof ButtonBlock;
                                                if (var3x == null) {
                                                   return var10000x;
                                                }

                                                if (!var10000x) {
                                                   var10000x = var0x instanceof CakeBlock;
                                                   if (var3x == null) {
                                                      return var10000x;
                                                   }

                                                   if (!var10000x) {
                                                      var10000x = var0x instanceof CandleCakeBlock;
                                                      if (var3x == null) {
                                                         return var10000x;
                                                      }

                                                      if (!var10000x) {
                                                         var10000x = var0x instanceof CartographyTableBlock;
                                                         if (var3x == null) {
                                                            return var10000x;
                                                         }

                                                         if (!var10000x) {
                                                            var10000x = var0x instanceof CaveVinesBodyBlock;
                                                            if (var3x == null) {
                                                               return var10000x;
                                                            }

                                                            if (!var10000x) {
                                                               var10000x = var0x instanceof CaveVinesHeadBlock;
                                                               if (var3x == null) {
                                                                  return var10000x;
                                                               }

                                                               if (!var10000x) {
                                                                  var10000x = var0x instanceof ChestBlock;
                                                                  if (var3x == null) {
                                                                     return var10000x;
                                                                  }

                                                                  if (!var10000x) {
                                                                     var10000x = var0x instanceof ChiseledBookshelfBlock;
                                                                     if (var3x == null) {
                                                                        return var10000x;
                                                                     }

                                                                     if (!var10000x) {
                                                                        var10000x = var0x instanceof CommandBlock;
                                                                        if (var3x == null) {
                                                                           return var10000x;
                                                                        }

                                                                        if (!var10000x) {
                                                                           var10000x = var0x instanceof ComparatorBlock;
                                                                           if (var3x == null) {
                                                                              return var10000x;
                                                                           }

                                                                           if (!var10000x) {
                                                                              var10000x = var0x instanceof ComposterBlock;
                                                                              if (var3x == null) {
                                                                                 return var10000x;
                                                                              }

                                                                              if (!var10000x) {
                                                                                 var10000x = var0x instanceof CraftingTableBlock;
                                                                                 if (var3x == null) {
                                                                                    return var10000x;
                                                                                 }

                                                                                 if (!var10000x) {
                                                                                    var10000x = var0x instanceof DaylightDetectorBlock;
                                                                                    if (var3x == null) {
                                                                                       return var10000x;
                                                                                    }

                                                                                    if (!var10000x) {
                                                                                       var10000x = var0x instanceof DecoratedPotBlock;
                                                                                       if (var3x == null) {
                                                                                          return var10000x;
                                                                                       }

                                                                                       if (!var10000x) {
                                                                                          var10000x = var0x instanceof DispenserBlock;
                                                                                          if (var3x == null) {
                                                                                             return var10000x;
                                                                                          }

                                                                                          if (!var10000x) {
                                                                                             var10000x = var0x instanceof DoorBlock;
                                                                                             if (var3x == null) {
                                                                                                return var10000x;
                                                                                             }

                                                                                             if (!var10000x) {
                                                                                                var10000x = var0x instanceof DragonEggBlock;
                                                                                                if (var3x == null) {
                                                                                                   return var10000x;
                                                                                                }

                                                                                                if (!var10000x) {
                                                                                                   var10000x = var0x instanceof EnchantingTableBlock;
                                                                                                   if (var3x == null) {
                                                                                                      return var10000x;
                                                                                                   }

                                                                                                   if (!var10000x) {
                                                                                                      var10000x = var0x instanceof EnderChestBlock;
                                                                                                      if (var3x == null) {
                                                                                                         return var10000x;
                                                                                                      }

                                                                                                      if (!var10000x) {
                                                                                                         var10000x = var0x instanceof FenceBlock;
                                                                                                         if (var3x == null) {
                                                                                                            return var10000x;
                                                                                                         }

                                                                                                         if (!var10000x) {
                                                                                                            var10000x = var0x instanceof FenceGateBlock;
                                                                                                            if (var3x == null) {
                                                                                                               return var10000x;
                                                                                                            }

                                                                                                            if (!var10000x) {
                                                                                                               var10000x = var0x instanceof FlowerPotBlock;
                                                                                                               if (var3x == null) {
                                                                                                                  return var10000x;
                                                                                                               }

                                                                                                               if (!var10000x) {
                                                                                                                  var10000x = var0x instanceof GrindstoneBlock;
                                                                                                                  if (var3x == null) {
                                                                                                                     return var10000x;
                                                                                                                  }

                                                                                                                  if (!var10000x) {
                                                                                                                     var10000x = var0x instanceof HopperBlock;
                                                                                                                     if (var3x == null) {
                                                                                                                        return var10000x;
                                                                                                                     }

                                                                                                                     if (!var10000x) {
                                                                                                                        var10000x = var0x instanceof JigsawBlock;
                                                                                                                        if (var3x == null) {
                                                                                                                           return var10000x;
                                                                                                                        }

                                                                                                                        if (!var10000x) {
                                                                                                                           var10000x = var0x instanceof JukeboxBlock;
                                                                                                                           if (var3x == null) {
                                                                                                                              return var10000x;
                                                                                                                           }

                                                                                                                           if (!var10000x) {
                                                                                                                              var10000x = var0x instanceof LecternBlock;
                                                                                                                              if (var3x == null) {
                                                                                                                                 return var10000x;
                                                                                                                              }

                                                                                                                              if (!var10000x) {
                                                                                                                                 var10000x = var0x instanceof LeverBlock;
                                                                                                                                 if (var3x == null) {
                                                                                                                                    return var10000x;
                                                                                                                                 }

                                                                                                                                 if (!var10000x) {
                                                                                                                                    var10000x = var0x instanceof LightBlock;
                                                                                                                                    if (var3x == null) {
                                                                                                                                       return var10000x;
                                                                                                                                    }

                                                                                                                                    if (!var10000x) {
                                                                                                                                       var10000x = var0x instanceof LoomBlock;
                                                                                                                                       if (var3x == null) {
                                                                                                                                          return var10000x;
                                                                                                                                       }

                                                                                                                                       if (!var10000x) {
                                                                                                                                          var10000x = var0x instanceof NoteBlock;
                                                                                                                                          if (var3x == null) {
                                                                                                                                             return var10000x;
                                                                                                                                          }

                                                                                                                                          if (!var10000x) {
                                                                                                                                             var10000x = var0x instanceof PistonExtensionBlock;
                                                                                                                                             if (var3x == null) {
                                                                                                                                                return var10000x;
                                                                                                                                             }

                                                                                                                                             if (!var10000x) {
                                                                                                                                                var10000x = var0x instanceof RedstoneWireBlock;
                                                                                                                                                if (var3x
                                                                                                                                                   == null) {
                                                                                                                                                   return var10000x;
                                                                                                                                                }

                                                                                                                                                if (!var10000x) {
                                                                                                                                                   var10000x = var0x instanceof RepeaterBlock;
                                                                                                                                                   if (var3x
                                                                                                                                                      == null) {
                                                                                                                                                      return var10000x;
                                                                                                                                                   }

                                                                                                                                                   if (!var10000x
                                                                                                                                                      )
                                                                                                                                                    {
                                                                                                                                                      var10000x = var0x instanceof RespawnAnchorBlock;
                                                                                                                                                      if (var3x
                                                                                                                                                         == null
                                                                                                                                                         )
                                                                                                                                                       {
                                                                                                                                                         return var10000x;
                                                                                                                                                      }

                                                                                                                                                      if (!var10000x
                                                                                                                                                         )
                                                                                                                                                       {
                                                                                                                                                         var10000x = var0x instanceof ShulkerBoxBlock;
                                                                                                                                                         if (var3x
                                                                                                                                                            == null
                                                                                                                                                            )
                                                                                                                                                          {
                                                                                                                                                            return var10000x;
                                                                                                                                                         }

                                                                                                                                                         if (!var10000x
                                                                                                                                                            )
                                                                                                                                                          {
                                                                                                                                                            var10000x = var0x instanceof SmithingTableBlock;
                                                                                                                                                            if (var3x
                                                                                                                                                               == null
                                                                                                                                                               )
                                                                                                                                                             {
                                                                                                                                                               return var10000x;
                                                                                                                                                            }

                                                                                                                                                            if (!var10000x
                                                                                                                                                               )
                                                                                                                                                             {
                                                                                                                                                               var10000x = var0x instanceof StonecutterBlock;
                                                                                                                                                               if (var3x
                                                                                                                                                                  == null
                                                                                                                                                                  )
                                                                                                                                                                {
                                                                                                                                                                  return var10000x;
                                                                                                                                                               }

                                                                                                                                                               if (!var10000x
                                                                                                                                                                  )
                                                                                                                                                                {
                                                                                                                                                                  var10000x = var0x instanceof FlowerBlock;
                                                                                                                                                                  if (var3x
                                                                                                                                                                     == null
                                                                                                                                                                     )
                                                                                                                                                                   {
                                                                                                                                                                     return var10000x;
                                                                                                                                                                  }

                                                                                                                                                                  if (!var10000x
                                                                                                                                                                     )
                                                                                                                                                                   {
                                                                                                                                                                     var10000x = var0x instanceof StructureBlock;
                                                                                                                                                                     if (var3x
                                                                                                                                                                        == null
                                                                                                                                                                        )
                                                                                                                                                                      {
                                                                                                                                                                        return var10000x;
                                                                                                                                                                     }

                                                                                                                                                                     if (!var10000x
                                                                                                                                                                        )
                                                                                                                                                                      {
                                                                                                                                                                        var10000x = var0x instanceof SlimeBlock;
                                                                                                                                                                        if (var3x
                                                                                                                                                                           == null
                                                                                                                                                                           )
                                                                                                                                                                         {
                                                                                                                                                                           return var10000x;
                                                                                                                                                                        }

                                                                                                                                                                        if (!var10000x
                                                                                                                                                                           )
                                                                                                                                                                         {
                                                                                                                                                                           var10000x = var0x instanceof CobwebBlock;
                                                                                                                                                                           if (var3x
                                                                                                                                                                              == null
                                                                                                                                                                              )
                                                                                                                                                                            {
                                                                                                                                                                              return var10000x;
                                                                                                                                                                           }

                                                                                                                                                                           if (!var10000x
                                                                                                                                                                              && var0x
                                                                                                                                                                                 != Blocks.FLETCHING_TABLE
                                                                                                                                                                              )
                                                                                                                                                                            {
                                                                                                                                                                              return false;
                                                                                                                                                                           }
                                                                                                                                                                        }
                                                                                                                                                                     }
                                                                                                                                                                  }
                                                                                                                                                               }
                                                                                                                                                            }
                                                                                                                                                         }
                                                                                                                                                      }
                                                                                                                                                   }
                                                                                                                                                }
                                                                                                                                             }
                                                                                                                                          }
                                                                                                                                       }
                                                                                                                                    }
                                                                                                                                 }
                                                                                                                              }
                                                                                                                           }
                                                                                                                        }
                                                                                                                     }
                                                                                                                  }
                                                                                                               }
                                                                                                            }
                                                                                                         }
                                                                                                      }
                                                                                                   }
                                                                                                }
                                                                                             }
                                                                                          }
                                                                                       }
                                                                                    }
                                                                                 }
                                                                              }
                                                                           }
                                                                        }
                                                                     }
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  var10000x = true;
               }

               return var10000x;
            }
         )
         .toList();
      effectScores = new StatusEffectScores();
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException rethrow(JSONException exception) {
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
}
