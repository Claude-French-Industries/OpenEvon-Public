package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.ClientTickEvent;
import club.evon.event.MovementInputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.KeyedRandom;




public final class BridgeAssist extends BaseModule {
   private final DoubleSetting delay;
   private final BooleanSetting requireBlocks;
   private final BooleanSetting requireBackwards;
   private final BooleanSetting requireSneak;
   private final BooleanSetting requireGround;
   private final BooleanSetting pitchCheck;
   private boolean assistActive;
   private int activeTicks;
   private static final long STRING_KEY = KeyedRandom.create(-7419810335924511826L, -123218502087126991L, MethodHandles.lookup().lookupClass()).next(271450839725129L);
   private static final String[] STRINGS;

   public BridgeAssist() {
      super((STRINGS)[0], (STRINGS)[4], ModuleCategory.PLAYER);

      long longValue = STRING_KEY ^ 23109573999205L;
      String[] strings = STRINGS;
      this.delay = new DoubleSetting(strings[3], 0.0, 1.0, 10.0, 1.0);
      this.requireBlocks = new BooleanSetting(strings[5], true);
      this.requireBackwards = new BooleanSetting(strings[2], true);
      this.requireSneak = new BooleanSetting(strings[7], true);
      boolean tmp = Breaker.A();
      this.requireGround = new BooleanSetting(strings[6], true);
      this.pitchCheck = new BooleanSetting(strings[1], true);
      this.addSettings(this.delay, this.requireBlocks, this.requireBackwards, this.requireSneak, this.requireGround, this.pitchCheck);
      boolean flag = tmp;
      if (flag) {
         BaseModule.setStateArray(new BaseModule[3]);
      }
      }

   @Subscribe
   private void onTick(ClientTickEvent event) {
      long longValue = STRING_KEY ^ 21629105886620L;
      boolean flag = Breaker.a();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (flag) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (tmp.world != null) {
         label197: {
            BridgeAssist bridgeAssist = this;
            if (flag) {
               if (this.assistActive) {
                  this.activeTicks++;
                  if (flag) {
                     break label197;
                  }
               }

               bridgeAssist = this;
            }

            bridgeAssist.activeTicks = 0;
         }

         boolean flag2;
         label190: {
            label189: {
               label207: {
                  flag2 = this.requireBlocks.I();
                  if (flag) {
                     if (!flag2) {
                        break label207;
                     }

                     flag2 = SilentSlotManager.getInstance().getMainHandStack(ClientContext.MINECRAFT_CLIENT.player).getItem() instanceof BlockItem;
                  }

                  if (!flag) {
                     break label190;
                  }

                  if (flag2) {
                     break label189;
                  }

                  flag2 = ClientContext.MINECRAFT_CLIENT.player.getOffHandStack().getItem() instanceof BlockItem;
                  if (!flag) {
                     break label190;
                  }

                  if (flag2) {
                     break label189;
                  }
               }

               flag2 = false;
               break label190;
            }

            flag2 = true;
         }

         boolean flag3;
         flag3 = flag2;
         boolean flag4;
         flag4 = this.requireBackwards.I();
         label173:
         if (flag) {
            if (flag4) {
               flag4 = ClientContext.MINECRAFT_CLIENT.options.backKey.isPressed();
               if (!flag) {
                  break label173;
               }

               if (!flag4) {
                  flag4 = false;
                  break label173;
               }
            }

            flag4 = true;
         }

         boolean flag5 = flag4;
         if (flag) {
            label162:
            if (flag3) {
               boolean flag6 = flag5;
               if (flag) {
                  if (!flag5) {
                     break label162;
                  }

                  flag6 = this.pitchCheck.I();
               }

               label151:
               if (flag) {
                  if (flag6) {
                     float floatValue;
                     flag6 = (floatValue = ClientContext.MINECRAFT_CLIENT.player.getPitch() - 50.0F) > 0.0F;
                  } else {
                     flag6 = true;
                  }
               }

               boolean flag7 = flag6;
               boolean flag8 = flag7;
               if (flag) {
                  if (!flag7) {
                     this.assistActive = true;
                     return;
                  }

                  flag8 = ClientContext.MINECRAFT_CLIENT.player.isOnGround();
               }

               label210: {
                  label211: {
                     label212: {
                        if (flag) {
                           if (!flag8) {
                              flag8 = this.requireGround.I();
                              if (!flag) {
                                 break label211;
                              }

                              if (flag8) {
                                 break label212;
                              }
                           }

                           flag8 = ClientContext.MINECRAFT_CLIENT.world.getBlockState(ClientContext.MINECRAFT_CLIENT.player.getBlockPos().down()).isAir();
                        }

                        if (!flag) {
                           break label211;
                        }

                        if (flag8) {
                           this.assistActive = true;
                           if (flag) {
                              return;
                           }
                        }
                     }

                     if (!flag) {
                        break label210;
                     }
                  }

                  int var12i = this.activeTicks;
                  if (var12i < this.delay.J().intValue()) {
                     return;
                  }
               }

               this.assistActive = false;
               return;
            }

            this.assistActive = false;
         }
      }
   }

   @Subscribe
   private void onMovementInput(MovementInputEvent event) {
      boolean tmp;
      label58: {
         label60: {
            long longValue = STRING_KEY ^ 36545244860545L;
            boolean flag = Breaker.a();
            tmp = this.assistActive;
            if (flag) {
               if (this.assistActive) {
                  tmp = ClientContext.MINECRAFT_CLIENT.options.sneakKey.isPressed();
                  if (!flag) {
                     break label58;
                  }

                  if (tmp) {
                     break label60;
                  }

                  tmp = this.requireSneak.I();
                  if (!flag) {
                     break label58;
                  }

                  if (!tmp) {
                     break label60;
                  }
               }

               tmp = ClientContext.MINECRAFT_CLIENT.options.sneakKey.isPressed();
            }

            label62: {
               if (flag) {
                  if (!tmp) {
                     break label62;
                  }

                  tmp = this.requireSneak.I();
               }

               if (!flag) {
                  break label58;
               }

               if (!tmp) {
                  break label60;
               }
            }

            tmp = false;
            break label58;
         }

         tmp = true;
      }

      event.setSneak(tmp);
   }

   static {
      try {
      long var0 = STRING_KEY ^ 66464601605127L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[8];
      int var7 = 0;
      String var6 = "\u00ad\u0005\u008dh]b7+7\u0091\u00caXt\u00a5\u00f4u\u0010H\u0098\n\u0092\u00e7Z\u000e\u009b5m\u00a7\u00d2\u00d9\u00b4\u00d0o\u0018\u001eL\u0001\u00f6\u00ae?\u00a8\b\u00f6\u00f0\u00902\u00c3Q$\u00ae\u00a1\u008b(\u00be\u00de\u00ef\u00db\u0003\b\u00cd\u00f9\u00f8h1\u0080p\u00c4\u0018A\u008b\u0089E\u00ca\bp\u0095\u00dc`\bd\u00f32\u00f5\u009a\u001a\u00c6#\u00da\u00d6\u00bf\u00b50\u0010\u001eL\u0001\u00f6\u00ae?\u00a8\b+(\u00d3K\u00e7\u0004\u0085\u00e6";
      int var8 = "\u00ad\u0005\u008dh]b7+7\u0091\u00caXt\u00a5\u00f4u\u0010H\u0098\n\u0092\u00e7Z\u000e\u009b5m\u00a7\u00d2\u00d9\u00b4\u00d0o\u0018\u001eL\u0001\u00f6\u00ae?\u00a8\b\u00f6\u00f0\u00902\u00c3Q$\u00ae\u00a1\u008b(\u00be\u00de\u00ef\u00db\u0003\b\u00cd\u00f9\u00f8h1\u0080p\u00c4\u0018A\u008b\u0089E\u00ca\bp\u0095\u00dc`\bd\u00f32\u00f5\u009a\u001a\u00c6#\u00da\u00d6\u00bf\u00b50\u0010\u001eL\u0001\u00f6\u00ae?\u00a8\b+(\u00d3K\u00e7\u0004\u0085\u00e6"
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

                  var6 = "\u001eL\u0001\u00f6\u00ae?\u00a8\b\u001a\u00f8L\u00b9\u00ea\u00c7\u00a0=\u0010\u001eL\u0001\u00f6\u00ae?\u00a8\b\u00d9\u0096\n\u0005\u00a1\u00a8\u00fbQ";
                  var8 = "\u001eL\u0001\u00f6\u00ae?\u00a8\b\u001a\u00f8L\u00b9\u00ea\u00c7\u00a0=\u0010\u001eL\u0001\u00f6\u00ae?\u00a8\b\u00d9\u0096\n\u0005\u00a1\u00a8\u00fbQ"
                     .length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
      } catch (Exception var10004) {
         throw new RuntimeException("club/evon/module/impl/player/BridgeAssist", var10004);
      }
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
