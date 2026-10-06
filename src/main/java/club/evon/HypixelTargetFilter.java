package club.evon;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import net.hypixel.data.type.GameType;
import net.hypixel.data.type.ServerType;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.text.Text;
import org.json.JSONException;
import club.evon.api.HypixelLocationManager;
import club.evon.api.ServerProfile;
import club.evon.data.HypixelLocation;
import club.evon.event.ClientTickEvent;
import club.evon.event.JoinWorldEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.launch.Loader;
import club.evon.launch.hidden.Hidden0;
import club.evon.manager.ClientState;




public final class HypixelTargetFilter extends ServerProfile {
   private final Set<UUID> playerUuids = ConcurrentHashMap.newKeySet();
   private final Set<Integer> armorStandIds = ConcurrentHashMap.newKeySet();
   // NOTE: bookkeeping added so the fake player uuids can be dropped again once their entity is
   // destroyed and isValidTarget() no longer gets a chance to prune them.
   private final Map<UUID, Integer> playerEntityIds = new ConcurrentHashMap<>();
   public static final Pattern HYPIXEL_BRAND_PATTERN;
   public static final List<Pattern> U;
   private static int v;
   private static final long KEY;
   private static final String[] STRINGS;
   // NOTE: replaces the native (DLL supplied) pattern used by q(String) to recognise
   // Hypixel's NPC/bot scoreboard team names, e.g. "NPC", "npc12", "BOT_3".
   private static final Pattern NPC_TEAM_PATTERN = Pattern.compile("^(?:npc|bot)[a-z0-9_]*$", Pattern.CASE_INSENSITIVE);

   public HypixelTargetFilter() {
      super(STRINGS[9]);
   }

   @Override
   public boolean isValidTarget(LivingEntity livingEntity) {
      int value = M();
      HypixelLocation hypixelLocation = HypixelLocationManager.getInstance().getCurrentLocation();
      if (shouldSkipTargetFilter(hypixelLocation, value)) {
         return true;
      }

      String string = livingEntity.getName().getString();
      String text = string;
      if (value == 0) {
         if (string == null) {
            return true;
         }

         text = string;
      }

      boolean var15b = text.isEmpty();
      if (value == 0) {
         if (var15b) {
            return true;
         }

         var15b = livingEntity instanceof ArmorStandEntity;
      }

      int var15i = 0;
      if (value == 0) {
         if (var15b) {
            this.armorStandIds.add(livingEntity.getId());
            return false;
         }

         var15i = livingEntity.getId();
      }

      if (value == 0) {
         if (var15i == -1234) {
            return false;
         }

         var15b = livingEntity instanceof OtherClientPlayerEntity;
      }

      if (value == 0) {
         if (var15b) {
            OtherClientPlayerEntity player = (OtherClientPlayerEntity)livingEntity;
            PlayerListEntry playerListEntry = ClientContext.MINECRAFT_CLIENT.getNetworkHandler().getPlayerListEntry(player.getUuid());
            PlayerListEntry player2 = playerListEntry;
            if (value == 0) {
               if (playerListEntry == null) {
                  return false;
               }

               player2 = playerListEntry;
            }

            if (value == 0) {
               if (player2.getProfile() == null) {
                  return false;
               }

               player2 = playerListEntry;
            }

            label365: {
               int value3 = player2.getLatency();
               if (value == 0) {
                  if (value3 <= 1) {
                     break label365;
                  }

                  float floatValue;
                  value3 = (floatValue = player.getHealth() - 14.0F) == 0.0F ? 0 : (floatValue < 0.0F ? -1 : 1);
               }

               if (value == 0) {
                  if (value3 <= 0) {
                     break label365;
                  }

                  float floatValue2;
                  value3 = (floatValue2 = player.getHealth() - 20.0F) == 0.0F ? 0 : (floatValue2 < 0.0F ? -1 : 1);
               }

               if (value == 0) {
                  if (value3 >= 0) {
                     break label365;
                  }

                  value3 = player.isInvisible() ? 1 : 0;
               }

               if (value == 0) {
                  if (value3 == 0) {
                     break label365;
                  }

                  value3 = 0;
               }

               return value3 != 0;
            }

            final int value4 = hasKnownLobby(hypixelLocation) ? 1 : 0;

            byte byteValue = (byte)value4;
            UUID uuid = player.getUuid();
            int value5 = uuid.version();
            label307:
            if (value == 0) {
               if (value5 == 2) {
                  if (value != 0) {
                     return byteValue != 0;
                  }

                  if (byteValue != 0) {
                     return false;
                  }

                  float floatValue3;
                  value5 = (floatValue3 = player.getHealth() - 20.0F) == 0.0F ? 0 : (floatValue3 < 0.0F ? -1 : 1);
                  if (value != 0) {
                     break label307;
                  }

                  if (value5 == 0 && playerListEntry.getScoreboardTeam() == null) {
                     return false;
                  }
               }

               value5 = this.playerUuids.contains(uuid) ? 1 : 0;
            }

            label379: {
               if (value == 0) {
                  if (value5 == 0) {
                     break label379;
                  }

                  value5 = byteValue;
               }

               label292:
               if (value == 0) {
                  if (value5 == 0) {
                     float floatValue4;
                     int value6 = (floatValue4 = player.getHealth() - 20.0F) == 0.0F ? 0 : (floatValue4 < 0.0F ? -1 : 1);
                     if (value != 0) {
                        break label292;
                     }

                     if (value6 <= 0) {
                        int value7 = player.isInvisible() ? 1 : 0;
                        if (value != 0) {
                           return value7 != 0;
                        }

                        if (value7 != 0) {
                           return false;
                        }

                        boolean onGround = player.isOnGround();
                        if (value != 0) {
                           break label292;
                        }

                        if (!onGround) {
                           if (value != 0) {
                              return player.age != 0;
                           }

                           if (player.age <= 170) {
                              return false;
                           }
                        }
                     }
                  }

                  this.playerUuids.remove(uuid);
               }

               if (value != 0) {
                  return false;
               }
            }

            if (value == 0) {
               return true;
            }
         }

         var15i = livingEntity.getUuid().version();
      }

      if (value == 0) {
         if (var15i != 4) {
            boolean invisible = livingEntity.isInvisible();
            if (value == 0) {
               if (invisible) {
                  return false;
               }

               invisible = string.contains(" ");
            }

            label228:
            if (value == 0) {
               if (invisible) {
                  List siblings = livingEntity.getName().getSiblings();
                  invisible = siblings.isEmpty();
                  if (value != 0) {
                     break label228;
                  }

                  if (!invisible && ((Text)siblings.getFirst()).getStyle().getColor() != null) {
                     return false;
                  }
               }

               invisible = this.armorStandIds.contains(livingEntity.getId());
            }

            if (value == 0) {
               invisible = !invisible;
            }

            return invisible;
         }

         var15b = livingEntity instanceof VillagerEntity;
      }

      if (value == 0) {
         return !var15b;
      }

      return var15b;
   }

   static boolean shouldSkipTargetFilter(HypixelLocation location, int flowGuard) {
      if (location == null) {
         return false;
      }

      ServerType serverType = location.getServerType();
      return serverType == GameType.REPLAY || (flowGuard == 0 && serverType == GameType.SMP);
   }

   static boolean hasKnownLobby(HypixelLocation location) {
      return location != null && location.hasLobby();
   }


   // NOTE: reconstructed from the DLL. The native handler only ever touched the two tracking
   // sets (Set.add / Set.remove) and the packet accessor (PacketReceiveEvent.getPacket), and
   // isValidTarget() consumes them as follows:
   //   armorStandIds -> entity ids of NPC name-tag armor stands (feeds the "armorStandIds.contains" check)
   //   playerUuids   -> uuids of entities already proven to be fake players (feeds the
   //                    "playerUuids.contains" check, which is also what removes stale entries)
   @Subscribe
   public void d(PacketReceiveEvent event) {
      if (event == null) {
         return;
      }

      Packet<?> packet = event.getPacket();
      if (packet == null) {
         return;
      }

      if (packet instanceof EntitySpawnS2CPacket spawn) {
         if (spawn.getEntityType() == EntityType.ARMOR_STAND) {
            this.armorStandIds.add(spawn.getEntityId());
            return;
         }

         if (spawn.getEntityType() == EntityType.PLAYER) {
            UUID uuid = spawn.getUuid();
            if (uuid != null && uuid.version() == 2) {
               // NOTE: version 2 uuids are what Hypixel hands out to its fake players; the real
               // ones use version 4 (isValidTarget() relies on exactly that distinction).
               this.playerUuids.add(uuid);
               this.playerEntityIds.put(uuid, spawn.getEntityId());
            }
         }

         return;
      }

      if (packet instanceof EntitiesDestroyS2CPacket destroy) {
         for (int entityId : destroy.getEntityIds()) {
            this.armorStandIds.remove(entityId);
         }

         this.pruneTrackedPlayers();
      }
   }

   private void pruneTrackedPlayers() {
      if (this.playerEntityIds.isEmpty()) {
         return;
      }

      if (ClientContext.MINECRAFT_CLIENT == null || ClientContext.MINECRAFT_CLIENT.world == null) {
         return;
      }

      for (Map.Entry<UUID, Integer> tracked : this.playerEntityIds.entrySet()) {
         if (ClientContext.MINECRAFT_CLIENT.world.getEntityById(tracked.getValue()) != null) {
            continue;
         }

         this.playerEntityIds.remove(tracked.getKey());
         this.playerUuids.remove(tracked.getKey());
      }
   }

   @Subscribe
   public void onJoinWorld(JoinWorldEvent event) {
      this.armorStandIds.clear();
      this.playerUuids.clear();
      this.playerEntityIds.clear();
   }

   @Subscribe
   public void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 101707993199020L;
      int value = e();
      ClientPlayNetworkHandler networkHandler = ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
      if (value != 0) {
         if (networkHandler == null) {
            return;
         }

         networkHandler = ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
      }

      String brand = networkHandler.getBrand();
      if (brand != null && !HYPIXEL_BRAND_PATTERN.matcher(brand).matches()) {
         ClientState.getInstance().getServerDetector().clearServerProfile();
      }
   }

   // NOTE: reconstructed from the DLL. Its attribute table shows this predicate only ever ran
   // String.contains/startsWith/equals/matches on the argument (it never resolved the team), so
   // the semantics are a pure "does this scoreboard team name belong to a Hypixel NPC" check.
   // Usage is npc-only and polarity is unobservable from the Java side, so the conservative
   // default is used: return false (= "not a filtered NPC team") unless the name is clearly NPC-like.
   public boolean q(String teamName) {
      if (teamName == null) {
         return false;
      }

      // Hypixel prefixes the team name with a colour code, e.g. "\u00a7cNPC" or "\u00a78\u00a7lBOT".
      String stripped = teamName.replaceAll("\u00a7[0-9a-fk-or]", "").trim();
      if (stripped.isEmpty()) {
         return false;
      }

      return NPC_TEAM_PATTERN.matcher(stripped).matches();
   }

   static {
      HYPIXEL_BRAND_PATTERN = Pattern.compile("(?i)^(?:\u00a7.|&.)*hypixel.*$");
      // NOTE: the DLL supplied these; only index 9 (the ServerProfile display name) is read in
      // this class. The other slots get placeholders so STRINGS[9] cannot shift.
      STRINGS = new String[]{"", "", "", "", "", "", "", "", "", "Hypixel"};
      // NOTE: the DLL supplied this list; HypixelAddons.onChatMessage matches it against incoming
      // chat lines to decide whether to auto-send the GG message, i.e. it held Hypixel win/game-end
      // messages. Only the clearest win patterns are reconstructed here (approximation).
      U = List.of(Pattern.compile("(?i)^\\s*(?:\u00a7.|&.)*you won!?.*$"), Pattern.compile("(?i)^\\s*(?:\u00a7.|&.)*winner!?.*$"));
      KEY = 0L;
      // The DLL used to seed this flow-guard, and it MUST be non-zero.
      //
      // M() is `e() == 0 ? 17 : 0`, so a zero seed makes M() return 17 and turns every
      // `if (value == 0)` guard in the target filters false. The guards are where the real body
      // lives: with them skipped HypixelTargetFilter.isValidTarget() collapses to
      // `return var15b` where var15b is just `name.isEmpty()`, so every named player is rejected.
      // MinemenAntibot and CubecraftServer read the same value and fail the same way, and
      // TeamColor.fromTeamColor() breaks out of its loop on the first iteration and always
      // returns null. Net effect: no module could find a target on any server.
      O(1);
      Loader.attachNatives(1, HypixelTargetFilter.class);
      Hidden0.special_clinit_1_60(HypixelTargetFilter.class);
   }

   public static void O(int value) {
      v = value;
   }

   public static int e() {
      return v;
   }

   public static int M() {
      int value = e();
      return value == 0 ? 17 : 0;
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
