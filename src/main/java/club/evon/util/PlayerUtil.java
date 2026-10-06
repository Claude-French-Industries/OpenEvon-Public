package club.evon.util;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import net.hypixel.data.type.GameType;
import net.hypixel.data.type.ServerType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.lwjgl.glfw.GLFW;
import club.evon.ClientContext;
import club.evon.HypixelTargetFilter;
import club.evon.api.HypixelLocationManager;
import club.evon.api.ServerProfile;
import club.evon.data.HypixelLocation;
import club.evon.manager.ClientState;
import club.evon.util.math.RandomUtil;




public final class PlayerUtil {
   public static final List<KeyBinding> MOVEMENT_KEYS = List.of(
      ClientContext.MINECRAFT_CLIENT.options.forwardKey, ClientContext.MINECRAFT_CLIENT.options.backKey, ClientContext.MINECRAFT_CLIENT.options.leftKey, ClientContext.MINECRAFT_CLIENT.options.rightKey, ClientContext.MINECRAFT_CLIENT.options.jumpKey
   );
   private static final Block[] HAZARD_BLOCKS = new Block[]{Blocks.COBWEB, Blocks.LAVA, Blocks.FIRE};
   private static String[] guard;
   private static final long KEY = KeyedRandom.create(8540334931391742467L, 5401164305590167529L, MethodHandles.lookup().lookupClass()).next(95684620546381L);

   public static int findReplaceableSlot() {
      long longValue = KEY ^ 71665740840179L;
      String[] strings = getGuard();
      int value = RandomUtil.randomIntBelow(8);
      int value2;
      for (value2 = value; value2 < 9; value2++) {
         if (isReplaceableSlot(value2)) {
            return value2;
         }
      }

      for (value2 = value - 1; value2 >= 0; value2--) {
         if (isReplaceableSlot(value2)) {
            return value2;
         }
      }

      return value;
   }

   private static boolean isReplaceableSlot(int slot) {
      long longValue = KEY ^ 54685901620065L;
      String[] strings = getGuard();
      PlayerInventory inventory = ClientContext.MINECRAFT_CLIENT.player.getInventory();
      String[] strings2 = strings;
      int value = slot;
      if (strings2 != null) {
         if (slot != inventory.getSelectedSlot()) {
            ItemStack stack = (ItemStack)inventory.getMainStacks().get(slot);
            boolean empty = stack.isEmpty();
            if (strings2 != null) {
               if (!empty) {
                  boolean empty2 = stack.getName().getStyle().isEmpty();
                  if (strings2 == null) {
                     return empty2;
                  }

                  if (!empty2) {
                     return false;
                  }
               }

               empty = true;
            }

            return empty;
         }

         value = 0;
      }

      return value != 0;
   }

   public static boolean canMove() {
      long longValue = KEY ^ 94254966638111L;
      String[] strings = getGuard();
      boolean touchingWater = ClientContext.MINECRAFT_CLIENT.player.isTouchingWater();
      if (strings != null) {
         if (touchingWater) {
            return false;
         }

         touchingWater = ClientContext.MINECRAFT_CLIENT.player.isClimbing();
      }

      if (strings != null) {
         if (touchingWater) {
            return false;
         }

         touchingWater = ClientContext.MINECRAFT_CLIENT.player.hasStatusEffect(StatusEffects.BLINDNESS);
      }

      if (strings != null) {
         if (touchingWater) {
            return false;
         }

         touchingWater = ClientContext.MINECRAFT_CLIENT.player.hasVehicle();
      }

      return strings == null ? touchingWater : !touchingWater;
   }

   public static boolean isOnGround() {
      return isOnGroundWithOffset(0.0, 0.0);
   }

   public static boolean isOnGroundWithOffset(double offsetX, double offsetZ) {
      Box offset = ClientContext.MINECRAFT_CLIENT.player.getBoundingBox().offset(offsetX, -(ClientContext.MINECRAFT_CLIENT.player.getBoundingBox().getLengthY() + 1.0), offsetZ);
      return BlockPos.stream(offset).noneMatch(pos -> {
         long longValue = KEY ^ 55810279469874L;
         BlockState var4x = ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos);
         String[] strings = getGuard();
         VoxelShape offset2 = var4x.getCollisionShape(ClientContext.MINECRAFT_CLIENT.world, pos).offset(pos.getX(), pos.getY(), pos.getZ());
         String[] strings2 = strings;
         boolean flag = pos.getY() - Math.floor(ClientContext.MINECRAFT_CLIENT.player.getY() - 1.0) != 0.0;
         if (strings2 != null) {
            if (flag) {
               return false;
            }

            flag = offset2.isEmpty();
         }

         if (strings2 == null) {
            return flag;
         }

         if (flag) {
            return true;
         }

         boolean flag2 = offset2.getMax(Axis.Y) - (ClientContext.MINECRAFT_CLIENT.player.getY() - ClientContext.MINECRAFT_CLIENT.player.getY() % 0.0625) != 0.0;
         return strings2 == null ? flag2 : flag2;
      });
   }

   public static boolean isPlayerOnGround(PlayerEntity player) {
      Box offset = player.getBoundingBox().offset(0.0, -(player.getBoundingBox().getLengthY() + 1.0), 0.0);
      return BlockPos.stream(offset).noneMatch(var1x -> {
         long longValue = KEY ^ 30680716803170L;
         String[] strings = getGuard();
         BlockState blockState = ClientContext.MINECRAFT_CLIENT.world.getBlockState(var1x);
         String[] strings2 = strings;
         VoxelShape offset2 = blockState.getCollisionShape(ClientContext.MINECRAFT_CLIENT.world, var1x).offset(var1x.getX(), var1x.getY(), var1x.getZ());
         boolean flag = var1x.getY() - Math.floor(player.getY() - 1.0) != 0.0;
         if (strings2 != null) {
            if (flag) {
               return false;
            }

            flag = offset2.isEmpty();
         }

         if (strings2 == null) {
            return flag;
         }

         if (flag) {
            return true;
         }

         boolean flag2 = offset2.getMax(Axis.Y) - (player.getY() - player.getY() % 0.0625) != 0.0;
         return strings2 == null ? flag2 : flag2;
      });
   }

   public static String getDisplayName(LivingEntity entity) {
      long longValue = KEY ^ 40416940727930L;
      String[] strings = getGuard();
      Text displayName = entity.getDisplayName();
      if (strings != null) {
         if (displayName != null) {
            LegacyTextCollector legacyTextCollector = new LegacyTextCollector();
            entity.getDisplayName().asOrderedText().accept(legacyTextCollector);
            return legacyTextCollector.getText();
         }

         displayName = entity.getName();
      }

      return displayName.getString();
   }

   public static boolean hasServerBrand(String brand) {
      long longValue = KEY ^ 51795944726078L;
      String[] strings = getGuard();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (strings != null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return false;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (strings != null) {
         if (tmp.isInSingleplayer()) {
            return false;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      ServerInfo currentServerEntry = tmp.getCurrentServerEntry();
      if (currentServerEntry == null) {
         return false;
      }

      String brand2 = ClientContext.MINECRAFT_CLIENT.player.networkHandler.getBrand();
      String text = brand2;
      if (strings != null) {
         if (brand2 == null) {
            return false;
         }

         text = brand2.toLowerCase();
      }

      boolean contains = text.contains(brand.toLowerCase());
      return strings == null ? contains : contains;
   }

   public static int getBlockBreakTime() {
      long longValue = KEY ^ 57685739354963L;
      String[] strings = getGuard();
      int value = StatusEffectUtil.hasHaste(ClientContext.MINECRAFT_CLIENT.player) ? 1 : 0;
      if (strings != null) {
         if (value != 0) {
            return 6 - (1 + StatusEffectUtil.getHasteAmplifier(ClientContext.MINECRAFT_CLIENT.player));
         }

         value = ClientContext.MINECRAFT_CLIENT.player.hasStatusEffect(StatusEffects.MINING_FATIGUE) ? 1 : 0;
      }

      if (strings != null) {
         if (value != 0) {
            return 6 + (1 + ClientContext.MINECRAFT_CLIENT.player.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) * 2;
         }

         value = 6;
      }

      return value;
   }

   public static Vec3d closestPointOnBox(Vec3d from, Box box) {
      double doubleValue = Math.max(box.minX, Math.min(from.getX(), box.maxX));
      double doubleValue2 = Math.max(box.minY, Math.min(from.getY(), box.maxY));
      double doubleValue3 = Math.max(box.minZ, Math.min(from.getZ(), box.maxZ));
      return new Vec3d(doubleValue, doubleValue2, doubleValue3);
   }

   public static Vec3d closestPointOnLivingEntity(Vec3d from, LivingEntity entity) {
      return closestPointOnBox(from, entity.getBoundingBox().expand(entity.getTargetingMargin()));
   }

   public static Vec3d closestPointOnEntity(Vec3d from, Entity entity) {
      return closestPointOnBox(from, entity.getBoundingBox().expand(entity.getTargetingMargin()));
   }

   public static double getDistanceToLivingEntity(LivingEntity entity) {
      Vec3d eyePos = ClientContext.MINECRAFT_CLIENT.player.getEyePos();
      return eyePos.distanceTo(closestPointOnBox(eyePos, entity.getBoundingBox().expand(entity.getTargetingMargin())));
   }

   public static double getDistanceToEntity(Entity entity) {
      Vec3d eyePos = ClientContext.MINECRAFT_CLIENT.player.getEyePos();
      return eyePos.distanceTo(closestPointOnBox(eyePos, entity.getBoundingBox().expand(entity.getTargetingMargin())));
   }

   public static Box getBlockBox(BlockPos blockPos) {
      long longValue = KEY ^ 102499821516763L;
      String[] strings = getGuard();
      VoxelShape outlineShape = ClientContext.MINECRAFT_CLIENT.world.getBlockState(blockPos).getOutlineShape(ClientContext.MINECRAFT_CLIENT.world, blockPos, ShapeContext.of(ClientContext.MINECRAFT_CLIENT.player));
      String[] strings2 = strings;
      VoxelShape voxelShape = outlineShape;
      if (strings2 != null) {
         if (outlineShape.isEmpty()) {
            return new Box(blockPos);
         }

         voxelShape = outlineShape;
      }

      Box boundingBox = voxelShape.getBoundingBox();
      return new Box(
         blockPos.getX() + boundingBox.minX,
         blockPos.getY() + boundingBox.minY,
         blockPos.getZ() + boundingBox.minZ,
         blockPos.getX() + boundingBox.maxX,
         blockPos.getY() + boundingBox.maxY,
         blockPos.getZ() + boundingBox.maxZ
      );
   }

   public static double getDistanceToBlock(BlockPos blockPos) {
      Vec3d eyePos = ClientContext.MINECRAFT_CLIENT.player.getEyePos();
      Box blockBox = getBlockBox(blockPos);
      Vec3d closestPointOnBox = closestPointOnBox(eyePos, blockBox);
      return eyePos.distanceTo(closestPointOnBox);
   }

   public static boolean isBoxFreeAtHeight(double posY, Box playerBox) {
      Box box = new Box(playerBox.minX, posY, playerBox.minZ, playerBox.maxX, playerBox.maxY, playerBox.maxZ);
      return isFree(box);
   }

   public static boolean isFree(Box box) {
      return BlockPos.stream(box)
         .noneMatch(
            pos -> {
               long longValue = KEY ^ 87721565602424L;
               String[] strings = getGuard();
               BlockState blockState = ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos);
               String[] strings2 = strings;
               VoxelShape collisionShape = blockState.getCollisionShape(ClientContext.MINECRAFT_CLIENT.world, pos);
               boolean empty = collisionShape.isEmpty();
               if (strings2 != null) {
                  if (empty) {
                     return false;
                  }

                  empty = VoxelShapes.matchesAnywhere(
                     blockState.getCollisionShape(ClientContext.MINECRAFT_CLIENT.world, pos).offset(pos.getX(), pos.getY(), pos.getZ()), VoxelShapes.cuboid(box), BooleanBiFunction.AND
                  );
               }

               return strings2 == null ? empty : empty;
            }
         );
   }

   public static boolean isPlayerBoxFree(Box playerBox) {
      return isBoxFreeAtHeight(0.0, playerBox.expand(-0.005, 0.0, -0.005));
   }

   public static boolean isPlayerPositionFree() {
      return isPlayerBoxFree(ClientContext.MINECRAFT_CLIENT.player.getBoundingBox());
   }

   public static Block getTargetBlock() {
      long longValue = KEY ^ 61891170967334L;
      String[] strings = getGuard();
      HitResult tmp = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      if (strings != null) {
         if (ClientContext.MINECRAFT_CLIENT.crosshairTarget == null) {
            return null;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      }

      if (strings != null) {
         if (tmp.getType() != Type.BLOCK) {
            return null;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      }

      BlockHitResult blockHitResult = (BlockHitResult)tmp;
      BlockPos blockPos = blockHitResult.getBlockPos();
      return ClientContext.MINECRAFT_CLIENT.world.getBlockState(blockPos).getBlock();
   }

   public static BlockPos getTargetBlockPos() {
      long longValue = KEY ^ 87658053120229L;
      String[] strings = getGuard();
      HitResult tmp = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      if (strings != null) {
         if (ClientContext.MINECRAFT_CLIENT.crosshairTarget == null) {
            return null;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      }

      if (strings != null) {
         if (tmp.getType() != Type.BLOCK) {
            return null;
         }

         tmp = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      }

      BlockHitResult blockHitResult = (BlockHitResult)tmp;
      return blockHitResult.getBlockPos();
   }

   public static float getJumpVelocity() {
      long longValue = KEY ^ 20432972206143L;
      float floatValue = 3.0F;
      if (ClientContext.MINECRAFT_CLIENT.player.hasStatusEffect(StatusEffects.JUMP_BOOST)) {
         floatValue += ClientContext.MINECRAFT_CLIENT.player.getStatusEffect(StatusEffects.JUMP_BOOST).getAmplifier() + 1;
      }

      return floatValue;
   }

   public static boolean isKeyPressed(int keyCode) {
      return InputUtil.isKeyPressed(ClientContext.MINECRAFT_CLIENT.getWindow(), keyCode);
   }

   public static boolean isBindingPressed(KeyBinding keyBinding) {
      return isKeyPressed(keyBinding.getDefaultKey().getCode());
   }

   public static boolean isMouseButtonPressed(int button) {
      long longValue = KEY ^ 81627926118621L;
      String[] strings = getGuard();
      int value = GLFW.glfwGetMouseButton(ClientContext.MINECRAFT_CLIENT.getWindow().getHandle(), button);
      if (strings != null) {
         value = value == 1 ? 1 : 0;
      }

      return value != 0;
   }

   public static void updateKeyStates() {
      MOVEMENT_KEYS.forEach(keyBinding -> KeyBinding.setKeyPressed(keyBinding.getDefaultKey(), isBindingPressed(keyBinding)));
   }

   public static void releaseKeys() {
      MOVEMENT_KEYS.forEach(keyBinding -> KeyBinding.setKeyPressed(keyBinding.getDefaultKey(), false));
   }

   public static boolean isColliding(double offsetX, double offsetY, double offsetZ) {
      long longValue = KEY ^ 50413612940831L;
      String[] strings = getGuard();
      boolean free = isFree(ClientContext.MINECRAFT_CLIENT.player.getBoundingBox().offset(offsetX, offsetY, offsetZ));
      if (strings != null) {
         free = !free;
      }

      return free;
   }

   public static boolean isPlayerColliding() {
      long longValue = KEY ^ 10273837857566L;
      String[] strings = getGuard();
      boolean free = isFree(ClientContext.MINECRAFT_CLIENT.player.getBoundingBox());
      if (strings != null) {
         free = !free;
      }

      return free;
   }

   public static boolean isSameTeam(LivingEntity entity, LivingEntity other) {
      String[] strings;
      label90: {
         long longValue = KEY ^ 107278119301446L;
         String[] strings2 = getGuard();
         ServerProfile serverProfile = ClientState.getInstance().getServerDetector().getServerProfile();
         strings = strings2;
         ServerProfile serverProfile2 = serverProfile;
         if (strings != null) {
            if (!(serverProfile instanceof HypixelTargetFilter)) {
               break label90;
            }

            serverProfile2 = serverProfile;
         }

         HypixelTargetFilter hypixelTargetFilter = (HypixelTargetFilter)serverProfile2;
         HypixelLocation currentLocation = HypixelLocationManager.getInstance().getCurrentLocation();
         HypixelLocation hypixelLocation = currentLocation;
         if (strings != null) {
            if (currentLocation == null) {
               return false;
            }

            hypixelLocation = currentLocation;
         }

         ServerType serverType = hypixelLocation.getServerType();
         GameType tmp = GameType.PIT;
         if (strings != null) {
            if (serverType == GameType.PIT) {
               return false;
            }

            serverType = currentLocation.getServerType();
            tmp = GameType.REPLAY;
         }

         if (strings != null) {
            if (serverType == tmp) {
               return false;
            }

            serverType = currentLocation.getServerType();
            tmp = GameType.SMP;
         }

         if (serverType == tmp) {
            return false;
         }
      }

      LivingEntity entity2;
      label92: {
         Text displayName = entity.getDisplayName();
         if (strings != null) {
            if (displayName == null) {
               return false;
            }

            entity2 = other;
            if (strings == null) {
               break label92;
            }

            displayName = other.getDisplayName();
         }

         if (displayName == null) {
            return false;
         }

         entity2 = entity;
      }

      int value = entity2.getTeamColorValue();
      int value2 = other.getTeamColorValue();
      int value3 = value;
      if (strings != null) {
         // Entity#getTeamColorValue() falls back to white (0xFFFFFF) when the entity has no
         // scoreboard team, so on any server where players are teamless every player compared
         // equal and isSameTeam() reported EVERYONE as a teammate. TargetSelector rejects
         // teammates, so with the default "Teammates" target toggle off no player was ever a
         // valid target anywhere, at any range - KillAura simply never acquired one.
         // Two entities can only share a team if they actually have one.
         if (entity2.getScoreboardTeam() == null || other.getScoreboardTeam() == null) {
            return false;
         }

         value3 = value == value2 ? 1 : 0;
      }

      return value3 != 0;
   }

   public static double getAttackSpeed(ItemStack stack) {
      long longValue = KEY ^ 5395008871862L;
      String[] strings = getGuard();
      double doubleValue = 4.0;
      String[] strings2 = strings;
      double doubleValue2 = 4.0;
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)stack.getOrDefault(
         DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT
      );

      for (Entry entry : attributeModifiersComponent.modifiers()) {
         Entry entry2 = entry;
         if (strings2 != null) {
            if (entry.attribute() != EntityAttributes.ATTACK_SPEED) {
               continue;
            }

            entry2 = entry;
         }

         if (strings2 != null) {
            if (entry2.slot() != AttributeModifierSlot.MAINHAND && strings2 != null) {
               continue;
            }

            entry2 = entry;
         }

         EntityAttributeModifier modifier = entry2.modifier();

         doubleValue2 += switch (AttributeOperationSwitchMap.SWITCH_MAP[modifier.operation().ordinal()]) {
            case 1 -> modifier.value();
            case 2 -> modifier.value() * 4.0;
            case 3 -> modifier.value() * doubleValue2;
            default -> throw new MatchException(null, null);
         };
         if (strings2 == null) {
            break;
         }
      }

      return doubleValue2;
   }

   public static double getAttackDamage(ItemStack stack) {
      long longValue = KEY ^ 14840320966934L;
      String[] strings = getGuard();
      double doubleValue = 0.0;
      String[] strings2 = strings;
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)stack.getOrDefault(
         DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT
      );

      for (Entry entry : attributeModifiersComponent.modifiers()) {
         Entry entry2 = entry;
         if (strings2 != null) {
            if (entry.attribute() != EntityAttributes.ATTACK_DAMAGE) {
               continue;
            }

            entry2 = entry;
         }

         if (strings2 != null) {
            if (entry2.slot() != AttributeModifierSlot.MAINHAND && strings2 != null) {
               continue;
            }

            entry2 = entry;
         }

         EntityAttributeModifier modifier = entry2.modifier();
         doubleValue += modifier.value();
         if (strings2 == null) {
            break;
         }
      }

      return doubleValue;
   }

   public static double getArmor(ItemStack stack) {
      long longValue = KEY ^ 50772637657165L;
      double doubleValue = 0.0;
      String[] strings = getGuard();
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)stack.getOrDefault(
         DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT
      );
      String[] strings2 = strings;

      for (Entry entry : attributeModifiersComponent.modifiers()) {
         Entry entry2 = entry;
         if (strings2 != null) {
            if (entry.attribute() != EntityAttributes.ARMOR && strings2 != null) {
               continue;
            }

            entry2 = entry;
         }

         EntityAttributeModifier modifier = entry2.modifier();
         doubleValue += modifier.value();
         if (strings2 == null) {
            break;
         }
      }

      return doubleValue;
   }

   public static boolean isStandingInCobweb(PlayerEntity player) {
      long longValue = KEY ^ 95845626383665L;
      String[] strings = getGuard();
      BlockPos blockPos = player.getBlockPos();
      String[] strings2 = strings;
      boolean of = ClientContext.MINECRAFT_CLIENT.world.getBlockState(blockPos).isOf(Blocks.COBWEB);
      if (strings2 != null) {
         if (!of) {
            BlockPos down = blockPos.down();
            BlockPos pos = blockPos.up();
            boolean of2 = ClientContext.MINECRAFT_CLIENT.world.getBlockState(down).isOf(Blocks.COBWEB);
            if (strings2 != null) {
               if (!of2) {
                  boolean of3 = ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).isOf(Blocks.COBWEB);
                  if (strings2 == null) {
                     return of3;
                  }

                  if (!of3) {
                     return false;
                  }
               }

               of2 = true;
            }

            return of2;
         }

         of = true;
      }

      return of;
   }

   public static boolean isTouchingHazard(PlayerEntity player) {
      return BlockPos.stream(player.getBoundingBox()).anyMatch(pos -> {
         BlockState blockState = ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos);
         return Arrays.asList(HAZARD_BLOCKS).contains(blockState.getBlock());
      });
   }

   public static boolean isTouchingCobweb(PlayerEntity player) {
      return BlockPos.stream(player.getBoundingBox()).anyMatch(pos -> ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).isOf(Blocks.COBWEB));
   }

   public static boolean isPlayerTouchingCobweb() {
      return isTouchingCobweb(ClientContext.MINECRAFT_CLIENT.player);
   }

   public static boolean isPlayerStandingInCobweb() {
      return isStandingInCobweb(ClientContext.MINECRAFT_CLIENT.player);
   }

   static {
      String[] var10000 = new String[5];
      setGuard(var10000);
   }

   public static void setGuard(String[] value) {
      guard = value;
   }

   public static String[] getGuard() {
      return guard;
   }

   private static MatchException ignoreException(MatchException exception) {
      return exception;
   }
}
