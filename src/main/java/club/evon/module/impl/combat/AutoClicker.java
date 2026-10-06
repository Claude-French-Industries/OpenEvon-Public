package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.event.InputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClickScheduler;
import club.evon.manager.KeybindManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.math.RandomUtil;




public final class AutoClicker extends BaseModule {
   private final DoubleSetting minCps;
   private final DoubleSetting maxCps;
   private final BooleanGroupSetting mouseButtons;
   private final BooleanSetting requirePressed;
   private static final long KEY = KeyedRandom.create(-7007600247352357743L, -1150600981121714221L, MethodHandles.lookup().lookupClass()).next(104546859528018L);
   private static final String[] STRINGS;

   public AutoClicker() {
      super((STRINGS)[6], (STRINGS)[0], ModuleCategory.COMBAT);

      long longValue = KEY ^ 76101457593147L;
      String[] strings = STRINGS;
      KillAura.getStrings();
      this.minCps = new DoubleSetting(strings[3], 12.0, 1.0, 20.0, 1.0);
      this.maxCps = new DoubleSetting(strings[5], 15.0, 1.0, 20.0, 1.0);
      this.mouseButtons = new BooleanGroupSetting(strings[9], new BooleanSetting(strings[2], true), new BooleanSetting(strings[8], false));
      this.requirePressed = new BooleanSetting(strings[4], true);
      this.addSettings(this.minCps, this.maxCps, this.mouseButtons, this.requirePressed);
      if (BaseModule.getStateArray() == null) {
         KillAura.setStrings(new String[1]);
      }
      }

   @Subscribe
   private void onInput(InputEvent event) {
      String[] strings;
      boolean flag;
      label137: {
         label140: {
            long longValue = KEY ^ 55178401405687L;
            String[] strings2 = KillAura.getStrings();
            AutoBlock moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(AutoBlock.class);
            strings = strings2;
            flag = moduleByClass.isEnabled();
            if (strings == null) {
               if (!flag) {
                  break label140;
               }

               flag = moduleByClass.isNoSlowSwordsActive();
            }

            if (strings != null) {
               break label137;
            }

            if (flag) {
               flag = true;
               break label137;
            }
         }

         flag = false;
      }

      boolean flag2 = flag;
      flag = ClientContext.MINECRAFT_CLIENT.player.isUsingItem();
      label126:
      if (strings == null) {
         if (flag) {
            flag = flag2;
            if (strings != null) {
               break label126;
            }

            if (!flag2) {
               return;
            }
         }

         flag = ClickScheduler.getInstance().tryClick(RandomUtil.randomDouble(this.minCps.J(), this.maxCps.J()), false, true);
      }

      if (strings == null) {
         if (!flag) {
            return;
         }

         flag = this.mouseButtons.getByName(STRINGS[1]).I();
      }

      label95:
      if (strings == null) {
         label111:
         if (flag) {
            HitResult hitResult = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
            if (strings == null) {
               if (ClientContext.MINECRAFT_CLIENT.crosshairTarget == null) {
                  break label111;
               }

               hitResult = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
            }

            label105:
            if (hitResult.getType() != Type.BLOCK) {
               label143: {
                  flag = this.requirePressed.I();
                  if (strings == null) {
                     if (!flag) {
                        break label143;
                     }

                     flag = ClientContext.MINECRAFT_CLIENT.options.attackKey.isPressed();
                  }

                  if (strings != null) {
                     break label95;
                  }

                  if (!flag) {
                     break label105;
                  }
               }

               KeybindManager.getAttackState().queuePress();
            }
         }

         flag = this.mouseButtons.getByName(STRINGS[7]).I();
      }

      if (strings == null) {
         if (!flag) {
            return;
         }

         flag = this.requirePressed.I();
      }

      label86: {
         if (strings == null) {
            if (!flag) {
               break label86;
            }

            flag = ClientContext.MINECRAFT_CLIENT.options.useKey.isPressed();
         }

         if (!flag) {
            return;
         }
      }

      KeybindManager.getUseState().queuePress();
   }

   static {
      try {
      long var0 = KEY ^ 76357202895763L;
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
      String var6 = "\u00a5(\u00f8\u000b\u0015A\u00e8I\n\u00e4`\u00ba\u001c\fc\u00fev\u00a9\u0015\u0097\u0012\u00d6\u0001\u00fb\u00a4I\u0081N\u000e\u00d3\u00ddi\b\u00f3\u009e\u00ad\u00d6\u00d2\u00d0\u00d7\u00d8\b\u00f3\u009e\u00ad\u00d6\u00d2\u00d0\u00d7\u00d8\b\u00a5U\u0004Zf\u000f\u0098x\u0010\u009c\b9FC\u007f\u00f4b\u00e9\u00ebT\n\u0012:\u000e\u00aa\b\u001bC\u0012\u009e@\u00b4\u00b8\u00a6\u0010\u00a9\u009c\u00ecL\u0001\u00f0}\u00d0\u00ea\u00f3/sD\u00e4\u00b4\u001e\b<Q\u00e9\u00d9\u00cc\u00c8?z";
      int var8 = "\u00a5(\u00f8\u000b\u0015A\u00e8I\n\u00e4`\u00ba\u001c\fc\u00fev\u00a9\u0015\u0097\u0012\u00d6\u0001\u00fb\u00a4I\u0081N\u000e\u00d3\u00ddi\b\u00f3\u009e\u00ad\u00d6\u00d2\u00d0\u00d7\u00d8\b\u00f3\u009e\u00ad\u00d6\u00d2\u00d0\u00d7\u00d8\b\u00a5U\u0004Zf\u000f\u0098x\u0010\u009c\b9FC\u007f\u00f4b\u00e9\u00ebT\n\u0012:\u000e\u00aa\b\u001bC\u0012\u009e@\u00b4\u00b8\u00a6\u0010\u00a9\u009c\u00ecL\u0001\u00f0}\u00d0\u00ea\u00f3/sD\u00e4\u00b4\u001e\b<Q\u00e9\u00d9\u00cc\u00c8?z"
         .length();
      char var5 = ' ';
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

                  var6 = "<Q\u00e9\u00d9\u00cc\u00c8?z\u0010\u00df\u008f\u00b2w\u009b\u0085\u0015H\u00bc\u008dZh\u008c\u00ae\u0098\u0011";
                  var8 = "<Q\u00e9\u00d9\u00cc\u00c8?z\u0010\u00df\u008f\u00b2w\u009b\u0085\u0015H\u00bc\u008dZh\u008c\u00ae\u0098\u0011".length();
                  var5 = '\b';
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException passThrough(JSONException exception) {
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
