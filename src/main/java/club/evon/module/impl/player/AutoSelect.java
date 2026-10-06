package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.math.BlockPos;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.SlotSwitchMode;
import club.evon.event.InputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.KeybindManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.util.ItemUtils;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;




public final class AutoSelect extends BaseModule {
   private final BooleanSetting silent;
   private static final long STRING_KEY = KeyedRandom.create(4573692154986861408L, -2428180130820311696L, MethodHandles.lookup().lookupClass()).next(248528987965867L);
   private static final String[] STRINGS;

   public AutoSelect() {
      super((STRINGS)[2], (STRINGS)[1], ModuleCategory.PLAYER);

      String[] strings = STRINGS;
      this.silent = new BooleanSetting(strings[0], false);
      this.addSettings(this.silent);
      }

   @Subscribe
   private void onInput(InputEvent event) {
      long longValue = STRING_KEY ^ 34235574876364L;
      boolean flag = Breaker.a();
      boolean pressed = KeybindManager.getAttackState().isPressed();
      if (flag) {
         if (!pressed) {
            return;
         }

         pressed = KeybindManager.getUseState().isPressed();
      }

      if (flag) {
         if (pressed) {
            return;
         }

         pressed = ClientContext.MINECRAFT_CLIENT.player.isUsingItem();
      }

      if (flag) {
         if (pressed) {
            return;
         }

         pressed = ClientContext.MINECRAFT_CLIENT.player.isCreative();
      }

      if (!pressed) {
         BlockPos targetBlockPos = PlayerUtil.getTargetBlockPos();
         BlockPos pos = targetBlockPos;
         if (flag) {
            if (targetBlockPos == null) {
               return;
            }

            pos = targetBlockPos;
         }

         int value = ItemUtils.findBestToolSlot(pos);
         if (value != -1) {
            SilentSlotManager.getInstance().switchTo(value).setMode(this.silent.I() ? SlotSwitchMode.FULL : SlotSwitchMode.NONE);
         }
      }
   }

   static {
      try {
         long var0 = STRING_KEY ^ 97872560906053L;
         Cipher var2;
         Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
         SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
         byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

         for (int var3 = 1; var3 < 8; var3++) {
            var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
         }

         var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
         String[] var9 = new String[3];
         int var7 = 0;
         String var6 = "\u00a5\u00fe[\u00c59\u008a\u00f1\u00d6(S\u00f1n\u00dd*\u00e8\u00bd\u0014\u00dc\u00b9\t\u00d9\u00e4+f<X\u00fa\u00d2!{\u00a1A\u0090\u00ee\u00e9\u0001\u00c0\u00ff\u0097D\u009e\u00abH\u00a7\u00e4\u00f5]#\u00b7\u0010w\u00dc|\u00ad\u0085\u0012\u00d8\u00b5^\u00d3T$\u009by\u0016\u00b5";
         int var8 = "\u00a5\u00fe[\u00c59\u008a\u00f1\u00d6(S\u00f1n\u00dd*\u00e8\u00bd\u0014\u00dc\u00b9\t\u00d9\u00e4+f<X\u00fa\u00d2!{\u00a1A\u0090\u00ee\u00e9\u0001\u00c0\u00ff\u0097D\u009e\u00abH\u00a7\u00e4\u00f5]#\u00b7\u0010w\u00dc|\u00ad\u0085\u0012\u00d8\u00b5^\u00d3T$\u009by\u0016\u00b5"
            .length();
         char var5 = '\b';
         int var4 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
            String var13 = decodeUtf8(var10).intern();
            byte var10001 = -1;
            var9[var7++] = var13;
            if ((var4 += var5) >= var8) {
               STRINGS = var9;
               break;
            }

            var5 = var6.charAt(var4);
         }
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
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
