package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import club.evon.ClientContext;
import club.evon.event.InputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClickScheduler;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.setting.InfiniteAuraClickMode;
import club.evon.setting.InfiniteAuraSortMode;
import club.evon.setting.InfiniteAuraTargetMode;
import club.evon.setting.TargetsSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.PathFinder;
import club.evon.util.PlayerUtil;
import club.evon.util.math.RandomUtil;
import club.evon.util.net.NetworkUtil;




public final class InfiniteAura extends BaseModule {
   private final EnumSetting<InfiniteAuraClickMode> clickMode;
   private final DoubleSetting attackRange;
   private final DoubleSetting stepDistance;
   private final DoubleSetting minCps;
   private final DoubleSetting maxCps;
   private final EnumSetting<InfiniteAuraTargetMode> targetMode;
   private final EnumSetting<InfiniteAuraSortMode> sortMode;
   private final TargetsSetting targets;
   private final DoubleSetting maxTargets;
   private List<Vec3d> pathPoints;
   private static final long KEY = KeyedRandom.create(-7909538573051001964L, 606872955968449798L, MethodHandles.lookup().lookupClass()).next(60257572950831L);
   private static final String[] STRINGS;

   public InfiniteAura() {
      super((STRINGS)[3], (STRINGS)[6], ModuleCategory.COMBAT);

      String[] strings = STRINGS;
      this.clickMode = new EnumSetting<>(strings[8], club.evon.setting.InfiniteAuraClickMode.LEGACY);
      this.attackRange = new DoubleSetting(strings[0], 10.0, 1.0, 100.0, 1.0);
      this.stepDistance = new DoubleSetting(strings[2], 5.0, 0.5, 9.5, 0.5);
      this.minCps = new DoubleSetting(strings[5], 12.0, 1.0, 20.0, 1.0);
      this.maxCps = new DoubleSetting(strings[9], 15.0, 1.0, 20.0, 1.0);
      this.targetMode = new EnumSetting<>(strings[7], InfiniteAuraTargetMode.SINGLE);
      this.sortMode = new EnumSetting<>(strings[1], InfiniteAuraSortMode.DISTANCE);
      this.targets = new TargetsSetting();
      this.maxTargets = new DoubleSetting(strings[4], 1.0, 1.0, 100.0, 1.0);
      this.pathPoints = new ArrayList<>();
      this.minCps.setVisible(() -> {
         long var1x = KEY ^ 75329264502146L;
         String[] strings2 = KillAura.getStrings();
         boolean equals = this.clickMode.getValue().equals(club.evon.setting.InfiniteAuraClickMode.MODERN);
         if (strings2 == null) {
            equals = !equals;
         }

         return equals;
      });
      this.maxCps.setVisible(() -> {
         long var1x = KEY ^ 76431439453311L;
         String[] strings3 = KillAura.getStrings();
         boolean equals2 = this.clickMode.getValue().equals(club.evon.setting.InfiniteAuraClickMode.MODERN);
         if (strings3 == null) {
            equals2 = !equals2;
         }

         return equals2;
      });
      this.addSettings(this.clickMode, this.minCps, this.maxCps, this.attackRange, this.sortMode, this.targetMode, this.targets, this.maxTargets);
      }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Subscribe
   private void onInput(InputEvent event) {
      long longValue = KEY ^ 60313231401483L;
      String[] strings = KillAura.getStrings();
      Stream<LivingEntity> sorted = this.targets.findTargets(this.attackRange.J()).stream().sorted(Comparator.comparingDouble(this::getSortValue));
      boolean equals = this.targetMode.getValue().equals(InfiniteAuraTargetMode.SINGLE);
      long longValue2;
      if (strings == null && equals) {
         longValue2 = 1L;
      } else {
         longValue2 = this.maxTargets.J().intValue();
      }

      List<LivingEntity> toList = sorted.limit(longValue2).toList();
      boolean empty = toList.isEmpty();
      if (strings == null) {
         if (empty) {
            return;
         }

         empty = ClickScheduler.getInstance().tryClick(RandomUtil.randomDouble(this.minCps.J(), this.maxCps.J()), this.clickMode.getValue().equals(club.evon.setting.InfiniteAuraClickMode.MODERN), true);
      }

      if (empty) {
         for (LivingEntity entity : toList) {
            this.pathPoints = this.buildPath(ClientContext.MINECRAFT_CLIENT.player.getEntityPos(), entity.getEntityPos());
            Iterator iterator = this.pathPoints.iterator();

            while (true) {
               if (iterator.hasNext()) {
                  Vec3d pos = (Vec3d)iterator.next();
                  NetworkUtil.sendManagedPacket(new PositionAndOnGround(pos.getX(), pos.getY(), pos.getZ(), true, ClientContext.MINECRAFT_CLIENT.player.horizontalCollision));
                  if (strings != null) {
                     break;
                  }

                  if (strings == null) {
                     continue;
                  }
               }

               ClientContext.MINECRAFT_CLIENT.interactionManager.attackEntity(ClientContext.MINECRAFT_CLIENT.player, entity);
               ClientContext.MINECRAFT_CLIENT.player.swingHand(Hand.MAIN_HAND);
               break;
            }

            iterator = this.pathPoints.reversed().iterator();

            while (true) {
               if (iterator.hasNext()) {
                  Vec3d pos2 = (Vec3d)iterator.next();
                  NetworkUtil.sendManagedPacket(new PositionAndOnGround(pos2.getX(), pos2.getY(), pos2.getZ(), true, ClientContext.MINECRAFT_CLIENT.player.horizontalCollision));
                  if (strings != null) {
                     break;
                  }

                  if (strings == null) {
                     continue;
                  }
               }

               this.pathPoints.clear();
               break;
            }

            if (strings != null) {
               break;
            }
         }
      }
   }

   private double getSortValue(LivingEntity entity) {
      long longValue = KEY ^ 78869043241591L;
      String[] strings = KillAura.getStrings();
      double doubleValue = PlayerUtil.getDistanceToLivingEntity(entity);
      String[] strings2 = strings;
      int value = this.sortMode.getValue().ordinal();
      if (strings2 == null) {
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

   private ArrayList<Vec3d> buildPath(Vec3d start, Vec3d end) {
      long longValue = KEY ^ 35512438574712L;
      String[] strings = KillAura.getStrings();
      if (!this.isPassable(BlockPos.ofFloored(start))) {
         start = start.add(0.0, 1.0, 0.0);
      }

      PathFinder pathFinder = new PathFinder(start, end);
      pathFinder.findPath();
      int index = 0;
      Vec3d pos = null;
      Vec3d pos2 = null;
      ArrayList<Vec3d> list = new ArrayList<>();
      ArrayList<Vec3d> path = pathFinder.getPath();

      for (Vec3d pos3 : path) {
         label153: {
            int value;
            label144: {
               label154: {
                  label155: {
                     value = index;
                     if (strings == null) {
                        if (index == 0) {
                           break label155;
                        }

                        value = index;
                     }

                     if (strings != null) {
                        break label144;
                     }

                     if (value != path.size() - 1) {
                        break label154;
                     }
                  }

                  Vec3d pos4 = pos;
                  if (strings == null) {
                     if (pos != null) {
                        list.add(pos.add(0.5, 0.0, 0.5));
                     }

                     list.add(pos3.add(0.5, 0.0, 0.5));
                     pos4 = pos3;
                  }

                  pos2 = pos4;
                  if (strings == null) {
                     break label153;
                  }
               }

               value = 1;
            }

            label128: {
               int value2;
               label156: {
                  value2 = value;
                  double doubleValue;
                  value = (doubleValue = pos3.squaredDistanceTo(pos2) - 25.0) == 0.0 ? 0 : (doubleValue < 0.0 ? -1 : 1);
                  if (strings == null) {
                     if (value > 0) {
                        value2 = 0;
                        if (strings == null) {
                           break label156;
                        }
                     }

                     value = (int)Math.min(pos2.getX(), pos3.getX());
                  }

                  int value3 = value;

                  label120:
                  do {
                     double doubleValue2;
                     value = (doubleValue2 = value3 - Math.max(pos2.getX(), pos3.getX())) == 0.0 ? 0 : (doubleValue2 < 0.0 ? -1 : 1);

                     label117:
                     while (true) {
                        if (value > 0) {
                           break label120;
                        }

                        value = (int)Math.min(pos2.getY(), pos3.getY());
                        if (strings != null) {
                           break label128;
                        }

                        int value4 = value;

                        while (true) {
                           double doubleValue3;
                           value = (doubleValue3 = value4 - Math.max(pos2.getY(), pos3.getY())) == 0.0 ? 0 : (doubleValue3 < 0.0 ? -1 : 1);

                           label112:
                           while (true) {
                              if (value > 0) {
                                 break label117;
                              }

                              value = (int)Math.min(pos2.getZ(), pos3.getZ());
                              if (strings != null) {
                                 continue label117;
                              }

                              int value5 = value;

                              while (true) {
                                 if (!(value5 <= Math.max(pos2.getZ(), pos3.getZ()))) {
                                    break label112;
                                 }

                                 value = PathFinder.isPassableAt(value3, value4, value5, false) ? 1 : 0;
                                 if (strings != null) {
                                    break;
                                 }

                                 label107: {
                                    if (strings == null) {
                                       if (value != 0) {
                                          break label107;
                                       }

                                       value = 0;
                                    }

                                    value2 = value;
                                    if (strings == null) {
                                       break label120;
                                    }
                                 }

                                 value5++;
                                 if (strings != null) {
                                    break label112;
                                 }
                              }
                           }

                           value4++;
                           if (strings != null) {
                              break label117;
                           }
                        }
                     }

                     value3++;
                  } while (strings == null);
               }

               value = value2;
            }

            if (strings == null) {
               if (value != 0) {
                  break label153;
               }

               list.add(pos.add(0.5, 0.0, 0.5));
            }

            pos2 = pos;
         }

         pos = pos3;
         index++;
         if (strings != null) {
            break;
         }
      }

      return list;
   }

   private boolean isPassable(BlockPos pos) {
      long longValue = KEY ^ 134928438830183L;
      String[] strings = KillAura.getStrings();
      BlockState blockState = ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos);
      String[] strings2 = strings;
      boolean air = blockState.isAir();
      if (strings2 == null) {
         label42:
         if (!air) {
            boolean solid = blockState.isSolid();
            if (strings2 == null) {
               if (!solid) {
                  boolean opaque = blockState.isOpaque();
                  if (strings2 != null) {
                     return opaque;
                  }

                  if (opaque) {
                     break label42;
                  }
               }

               solid = blockState.isLiquid();
            }

            if (strings2 != null) {
               return solid;
            }

            if (!solid) {
               return false;
            }
         }

         air = true;
      }

      return air;
   }

   static {
      try {
      long var0 = KEY ^ 107182384975702L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[10];
      int var7 = 0;
      String var6 = "\u0016\u0014F\u00ac\u00e9\u00bb\n\u009f\u00cc\u00ef%X,wq1\u0010\u0091R\u0096\u00cfX\u0019S\u00f8\u00bf\u00a3\u008c\u00e7~P8\u00fc\u0010\u00af\u0011\u009fk\u00e5\u00c0@\u00bbD\u00bd\u00b5A)\u00f0\u00d5\u00a7\u0010\u00f7\u0091^\u00a4*\u00e5\u0095L9\u00bf\f\u0084\u0012(\u008e\u0080\u0010\u00b5\u0085H\u009c\u0097\u00c3\u00dfM\u00ba\u009fe/u\u0003\u00f8\u008f\bC\u00a5\u00dd\u00a8\u00ef<\u00c8A(\u0084\u00d5\u00f0/\u00da\u0001\u0000G\u00c8g@\u001a\u001d;\u00fca\u00fb\u00c7\u00d7\u00b4\u00d3\u00a1b\u00ea\u00ec\u0083\u000f\u00b3,C\u0015\u008b\u00bcg4U\u00f2g\u00b4q\u00108\u00c1\u00a7x\t\u000b\u00e8=l%\u00e56S\u00e8\u00c9)";
      int var8 = "\u0016\u0014F\u00ac\u00e9\u00bb\n\u009f\u00cc\u00ef%X,wq1\u0010\u0091R\u0096\u00cfX\u0019S\u00f8\u00bf\u00a3\u008c\u00e7~P8\u00fc\u0010\u00af\u0011\u009fk\u00e5\u00c0@\u00bbD\u00bd\u00b5A)\u00f0\u00d5\u00a7\u0010\u00f7\u0091^\u00a4*\u00e5\u0095L9\u00bf\f\u0084\u0012(\u008e\u0080\u0010\u00b5\u0085H\u009c\u0097\u00c3\u00dfM\u00ba\u009fe/u\u0003\u00f8\u008f\bC\u00a5\u00dd\u00a8\u00ef<\u00c8A(\u0084\u00d5\u00f0/\u00da\u0001\u0000G\u00c8g@\u001a\u001d;\u00fca\u00fb\u00c7\u00d7\u00b4\u00d3\u00a1b\u00ea\u00ec\u0083\u000f\u00b3,C\u0015\u008b\u00bcg4U\u00f2g\u00b4q\u00108\u00c1\u00a7x\t\u000b\u00e8=l%\u00e56S\u00e8\u00c9)"
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

                  var6 = "\u00d9\u0019N\u008fpC:\u00c4~\u00baF\u00ed\u00c6\u00eb\f\u00f5\b\u00fa\u0088U\u001c\u00ce\u0086\u00c8-";
                  var8 = "\u00d9\u0019N\u008fpC:\u00c4~\u00baF\u00ed\u00c6\u00eb\f\u00f5\b\u00fa\u0088U\u001c\u00ce\u0086\u00c8-".length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception varE) {
         throw new ExceptionInInitializerError(varE);
      }
   }

   private static MatchException identity(MatchException exception) {
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
