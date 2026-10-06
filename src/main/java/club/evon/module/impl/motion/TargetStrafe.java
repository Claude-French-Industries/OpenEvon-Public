package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.OrbitMode;
import club.evon.event.Subscribe;
import club.evon.event.UpdateVelocityEvent;
import club.evon.manager.ModuleManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.impl.combat.KillAura;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.setting.StrafeKey;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.RotationUtil;
import club.evon.util.SimulatedPlayer;




public final class TargetStrafe extends BaseModule {
   private final DoubleSetting range;
   private final EnumSetting<OrbitMode> strafeMode;
   private final EnumSetting<StrafeKey> strafeKey;
   private final BooleanSetting autoThirdPerson;
   public Float strafeYaw;
   private boolean collided;
   private boolean clockwise;
   private boolean thirdPersonActive;
   private boolean strafeKeyHeld;
   private static final long STRING_KEY = KeyedRandom.create(2809122120495705999L, -248166363936353262L, MethodHandles.lookup().lookupClass()).next(150596068482651L);
   private static final String[] STRINGS;

   public TargetStrafe() {
      super((STRINGS)[4], (STRINGS)[2], ModuleCategory.MOTION);

      String[] strings = STRINGS;
      this.range = new DoubleSetting(strings[1], 3.0, 0.1, 6.0, 0.1);
      this.strafeMode = new EnumSetting<>(strings[0], OrbitMode.CIRCLE);
      this.strafeKey = new EnumSetting<>(strings[3], StrafeKey.SPACE);
      this.autoThirdPerson = new BooleanSetting(strings[5], false);
      this.addSettings(this.strafeMode, this.strafeKey, this.autoThirdPerson, this.range);
      }

   @Override
   public void onDisable() {
      this.strafeYaw = null;
      super.onDisable();
   }

   @Subscribe
   private void onUpdateVelocity(UpdateVelocityEvent event) {
      TargetStrafe targetStrafe;
      label209: {
         BaseModule[] values;
         LivingEntity entity;
         boolean flag;
         boolean flag2;
         boolean flag3;
         label206: {
            label219: {
               long longValue = STRING_KEY ^ 51406639652427L;
               BaseModule[] values2 = Scaffold.z();
               ModuleManager moduleManager = EvonClient.getInstance().getModuleManager();
               values = values2;
               Scaffold moduleByClass = moduleManager.getModuleByClass(Scaffold.class);
               Speed moduleByClass2 = moduleManager.getModuleByClass(Speed.class);
               Flight moduleByClass3 = moduleManager.getModuleByClass(Flight.class);
               KillAura moduleByClass4 = moduleManager.getModuleByClass(KillAura.class);
               entity = moduleByClass4.getTarget();
               flag = moduleByClass4.isEnabled();
               label190:
               if (values == null) {
                  label202:
                  if (flag) {
                     Object object = entity;
                     if (values == null) {
                        if (entity == null) {
                           break label202;
                        }

                        object = this.strafeKey.getValue();
                     }

                     flag = ((StrafeKey)object).equals(StrafeKey.SPACE);
                     if (values == null) {
                        if (flag) {
                           flag2 = PlayerUtil.isKeyPressed(32);
                           if (values != null) {
                              break label206;
                           }

                           if (flag2) {
                              break label219;
                           }
                           break label202;
                        }

                        flag = PlayerUtil.isKeyPressed(341);
                     }

                     if (values != null) {
                        break label190;
                     }

                     if (flag) {
                        flag = moduleByClass.isEnabled();
                        if (values != null) {
                           break label190;
                        }

                        if (!flag) {
                           flag2 = moduleByClass2.isEnabled();
                           if (values != null) {
                              break label206;
                           }

                           if (flag2) {
                              break label219;
                           }

                           flag2 = moduleByClass3.isEnabled();
                           if (values != null) {
                              break label206;
                           }

                           if (flag2) {
                              break label219;
                           }
                        }
                     }
                  }

                  targetStrafe = this;
                  if (values != null) {
                     break label209;
                  }

                  flag = this.thirdPersonActive;
               }

               if (flag) {
                  ClientContext.MINECRAFT_CLIENT.options.setPerspective(Perspective.FIRST_PERSON);
                  this.thirdPersonActive = false;
               }

               this.strafeKeyHeld = false;
               targetStrafe = this;
               break label209;
            }

            this.strafeKeyHeld = true;
            flag2 = this.autoThirdPerson.I();
         }

         label166:
         if (flag2) {
            GameOptions gameOptions = ClientContext.MINECRAFT_CLIENT.options;
            if (values == null) {
               if (ClientContext.MINECRAFT_CLIENT.options.getPerspective() != Perspective.FIRST_PERSON) {
                  break label166;
               }

               gameOptions = ClientContext.MINECRAFT_CLIENT.options;
            }

            gameOptions.setPerspective(Perspective.THIRD_PERSON_BACK);
            this.thirdPersonActive = true;
         }

         label159: {
            label158: {
               label214: {
                  SimulatedPlayer simulatedPlayer = new SimulatedPlayer(ClientContext.MINECRAFT_CLIENT.player);
                  OtherClientPlayerEntity fakePlayer = simulatedPlayer.getFakePlayer();
                  fakePlayer.tick();
                  Box offset = ClientContext.MINECRAFT_CLIENT.player.getBoundingBox().offset(ClientContext.MINECRAFT_CLIENT.player.getVelocity());
                  flag3 = ClientContext.MINECRAFT_CLIENT.player.horizontalCollision;
                  label156:
                  if (values == null) {
                     if (!ClientContext.MINECRAFT_CLIENT.player.horizontalCollision) {
                        flag3 = PlayerUtil.isBoxFreeAtHeight(entity.getY() - 3.0, offset);
                        if (values != null) {
                           break label156;
                        }

                        if (!flag3) {
                           flag3 = PlayerUtil.isPlayerBoxFree(offset);
                           if (values != null) {
                              break label156;
                           }

                           if (!flag3) {
                              flag3 = PlayerUtil.isTouchingHazard(fakePlayer);
                              if (values != null) {
                                 break label156;
                              }

                              if (!flag3) {
                                 break label158;
                              }
                           }
                        }
                     }

                     targetStrafe = this;
                     if (values != null) {
                        break label214;
                     }

                     flag3 = this.collided;
                  }

                  if (!flag3) {
                     boolean tmp = this.clockwise;
                     if (values == null) {
                        tmp = !this.clockwise;
                     }

                     this.clockwise = tmp;
                  }

                  targetStrafe = this;
               }

               targetStrafe.collided = true;
               if (values == null) {
                  break label159;
               }
            }

            this.collided = false;
         }
         double doubleValue = switch ((OrbitMode)this.strafeMode.getValue()) {
            case CIRCLE -> {
               int value = this.clockwise ? 1 : 0;
               if (values == null) {
                  value = this.clockwise ? 1 : -1;
               }

               yield RotationUtil.getRotationTo(entity.getEyePos()).x + value * 135;
            }
            case BEHIND -> entity.getYaw() + 180.0F;
         };
         double doubleValue2 = this.range.J() + Math.random() / 50.0;
         float floatValue = (float)Math.toRadians(doubleValue);
         Vec3d add = entity.getEntityPos().add(-MathHelper.sin(floatValue) * doubleValue2, 0.0, MathHelper.cos(floatValue) * doubleValue2);
         this.strafeYaw = RotationUtil.getRotationTo(add).x;
         return;
      }

      targetStrafe.strafeYaw = null;
   }

   public Float getStrafeYaw() {
      return this.strafeYaw;
   }

   public boolean isCollided() {
      return this.collided;
   }

   public boolean isClockwise() {
      return this.clockwise;
   }

   public boolean isThirdPersonActive() {
      return this.thirdPersonActive;
   }

   public boolean isStrafeKeyHeld() {
      return this.strafeKeyHeld;
   }

   static {
      try {
      long var0 = STRING_KEY ^ 9036460118174L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[6];
      int var7 = 0;
      String var6 = "\u00ca\u00dc\u0082\u001a\u00ee\u00c0\u00fbom\u00af\"\u0095a\u0001\u008eC\b\u0093\u0007'\u00c8\u00f7\u0087\u0084\u00e9\u0018\u009e\u00fc`M\u00c3$\u00c7y\u00be\u00b9\u00a8\u00cc\u00e6R\u0084\u00f3*d\u0006\u000f\u00fb\u00c47\u00c0\u0010\u00f0\n\u00a2\u008a\u00e4Zi\u0087\u0016\u00b6j\u00aawK\u00bcU";
      int var8 = "\u00ca\u00dc\u0082\u001a\u00ee\u00c0\u00fbom\u00af\"\u0095a\u0001\u008eC\b\u0093\u0007'\u00c8\u00f7\u0087\u0084\u00e9\u0018\u009e\u00fc`M\u00c3$\u00c7y\u00be\u00b9\u00a8\u00cc\u00e6R\u0084\u00f3*d\u0006\u000f\u00fb\u00c47\u00c0\u0010\u00f0\n\u00a2\u008a\u00e4Zi\u0087\u0016\u00b6j\u00aawK\u00bcU"
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

                  var6 = "`\u00e0d\u00a1\u0095_*\u00f9\u00bff=\u00e7\u0001|\u0083\u00ac\u0010c\u00d3\u000e\u00a2\u00a0\u00eb\u00d3a|U\u00ec\u001eu\u00e8\u0086*";
                  var8 = "`\u00e0d\u00a1\u0095_*\u00f9\u00bff=\u00e7\u0001|\u0083\u00ac\u0010c\u00d3\u000e\u00a2\u00a0\u00eb\u00d3a|U\u00ec\u001eu\u00e8\u0086*"
                     .length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var20) {
         throw new RuntimeException(var20);
      }
   }

   private static MatchException a(MatchException matchException) {
      return matchException;
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
