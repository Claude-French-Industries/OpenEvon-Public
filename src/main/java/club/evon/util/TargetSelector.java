package club.evon.util;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.commons.compress.utils.Lists;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.ClientService;
import club.evon.api.ServerProfile;
import club.evon.manager.ClientState;
import club.evon.manager.FriendManager;




public final class TargetSelector implements ClientService {
   public static TargetSelector instance;
   private static final long KEY = KeyedRandom.create(-1980610446425650012L, -5043721148608791568L, MethodHandles.lookup().lookupClass()).next(214759008473801L);

   public List<LivingEntity> findTargets(boolean players, boolean mobs, boolean animals, boolean teammates, boolean invisibles, double searchRange) {
      ServerProfile serverProfile = ClientState.getInstance().getServerDetector().getServerProfile();
      return Lists.newArrayList(ClientContext.MINECRAFT_CLIENT.world.getEntities().iterator()).stream().filter(var8x -> {
         long longValue = KEY ^ 130355479477333L;
         String flowGuard = ClientState.getFlowGuard();
         boolean tmp = var8x instanceof LivingEntity;
         if (flowGuard == null) {
            if (tmp) {
               LivingEntity entity = (LivingEntity)var8x;
               if (flowGuard == null) {
                  label194: {
                     label202: {
                        ServerProfile serverProfile2 = serverProfile;
                        if (flowGuard == null) {
                           if (serverProfile == null) {
                              break label202;
                           }

                           serverProfile2 = serverProfile;
                        }

                        tmp = serverProfile2.isValidTarget(entity);
                        if (flowGuard != null) {
                           break label194;
                        }

                        if (!tmp) {
                           return false;
                        }
                     }

                     tmp = var8x instanceof PlayerEntity;
                  }

                  label182:
                  if (flowGuard == null) {
                      if (tmp) {
                         if (flowGuard != null) {
                            return players;
                         }

                        if (!players) {
                           return false;
                        }

                        if (ClientContext.MINECRAFT_CLIENT.player == var8x) {
                           return false;
                        }

                        tmp = teammates;
                        if (flowGuard != null) {
                           break label182;
                        }

                        if (!teammates) {
                           tmp = PlayerUtil.isSameTeam(ClientContext.MINECRAFT_CLIENT.player, entity);
                           if (flowGuard != null) {
                              break label182;
                           }

                           if (tmp) {
                              return false;
                           }
                        }
                     }

                     tmp = var8x instanceof MobEntity;
                  }

                  if (flowGuard == null) {
                     if (tmp) {
                        if (flowGuard != null) {
                           return mobs;
                        }

                        if (!mobs) {
                           return false;
                        }
                     }

                     tmp = var8x instanceof AnimalEntity;
                  }

                  label151:
                  if (flowGuard == null) {
                     if (tmp) {
                        tmp = animals;
                        if (flowGuard != null) {
                           break label151;
                        }

                        if (!animals) {
                           return false;
                        }
                     }

                     tmp = FriendManager.getInstance().isFriend(var8x.getName().getString());
                  }

                  if (flowGuard == null) {
                     if (tmp) {
                        return false;
                     }

                     tmp = PlayerUtil.getDistanceToLivingEntity(entity) - searchRange > 0;
                  }

                  if (flowGuard == null) {
                     if (tmp) {
                        return false;
                     }

                     tmp = entity.isDead();
                  }

                  int index = 0;
                  if (flowGuard == null) {
                     if (tmp) {
                        return false;
                     }

                     index = var8x.age;
                  }

                  if (flowGuard == null) {
                     if (index < 5) {
                        return false;
                     }

                     tmp = invisibles;
                  }

                  if (flowGuard == null) {
                     if (!tmp) {
                        boolean invisibleTo = var8x.isInvisibleTo(ClientContext.MINECRAFT_CLIENT.player);
                        if (flowGuard != null) {
                           return invisibleTo;
                        }

                        if (invisibleTo) {
                           return false;
                        }
                     }

                     tmp = true;
                  }

                  return tmp;
               }
            }

            tmp = false;
         }

         return tmp;
      }).map(entity2 -> (LivingEntity)entity2).collect(Collectors.toList());
   }

   public static void init() {
      instance = new TargetSelector();
   }

   public static TargetSelector getInstance() {
      return instance;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
