package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.tag.ItemTags;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.SlotSwitchMode;
import club.evon.event.InputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.KeybindManager;
import club.evon.manager.SilentSlotManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.impl.motion.NoSlow;
import club.evon.module.impl.motion.NoSlowSwords;
import club.evon.module.impl.motion.Scaffold;
import club.evon.setting.DoubleSetting;
import club.evon.setting.TargetsSetting;
import club.evon.util.ItemUtils;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.math.RandomUtil;




public final class AutoBlock extends BaseModule {
   private final DoubleSetting blockRange;
   private final TargetsSetting targets;
   private boolean blocking;
   private static final long KEY = KeyedRandom.create(-630489107297997899L, -1086111676256746891L, MethodHandles.lookup().lookupClass()).next(131162847336008L);
   private static final String[] STRINGS;

   public AutoBlock() {
      super((STRINGS)[1], (STRINGS)[2], ModuleCategory.COMBAT);

      String[] strings = STRINGS;
      this.blockRange = new DoubleSetting(strings[0], 3.0, 3.0, 8.0, 0.01);
      this.targets = new TargetsSetting();
      this.addSettings(this.blockRange, this.targets);
      }

   @Override
   public void onDisable() {
      if (KillAura.getStrings() == null) {
         this.blocking = false;
      }

      super.onDisable();
   }

   @Subscribe(h = 2)
   private void onInput(InputEvent event) {
      long longValue = KEY ^ 140197247600153L;
      String[] strings = KillAura.getStrings();
      AutoBlock tmp = this;
      if (strings == null) {
         this.blocking = false;
         if (EvonClient.getInstance().getModuleManager().getModuleByClass(Scaffold.class).isEnabled()) {
            return;
         }

         tmp = this;
      }

      List<LivingEntity> findTargets = tmp.targets.findTargets(this.blockRange.J());
      if (!findTargets.isEmpty()) {
         ItemStack stack;
         label70: {
            SilentSlotManager instance = SilentSlotManager.getInstance();
            SilentSlotManager silentSlotManager = instance;
            if (strings == null) {
               if (instance.getMode() != SlotSwitchMode.FULL) {
                  stack = ClientContext.MINECRAFT_CLIENT.player.getMainHandStack();
                  break label70;
               }

               silentSlotManager = instance;
            }

            stack = silentSlotManager.getMainHandStack(ClientContext.MINECRAFT_CLIENT.player);
         }

         label64: {
            ItemStack stack2 = stack;
            boolean in = stack2.isIn(ItemTags.SWORDS);
            if (strings == null) {
               if (in) {
                  break label64;
               }

               in = ClientContext.MINECRAFT_CLIENT.player.getOffHandStack().getItem() instanceof ShieldItem;
            }

            if (!in) {
               return;
            }
         }

         for (LivingEntity entity : findTargets) {
            if (PlayerUtil.getDistanceToLivingEntity(entity) <= this.blockRange.J()) {
               Block targetBlock = PlayerUtil.getTargetBlock();
               if (strings == null) {
                  if (ItemUtils.isInteractiveBlock(targetBlock)) {
                     return;
                  }

                  KeybindManager.getUseState().setPressState(true, RandomUtil.randomIntBelow(2));
               }

               this.blocking = true;
            }

            if (strings != null) {
               break;
            }
         }
      }
   }

   public boolean isNoSlowSwordsActive() {
      long longValue = KEY ^ 43853386225038L;
      String[] strings = KillAura.getStrings();
      NoSlow moduleByClass = EvonClient.getInstance().getModuleManager().getModuleByClass(NoSlow.class);
      String[] strings2 = strings;
      boolean enabled = moduleByClass.isEnabled();
      if (strings2 == null) {
         if (enabled) {
            Object value = moduleByClass.getMode().getValue();
            boolean flag = value instanceof NoSlowSwords;
            if (strings2 != null) {
               return flag;
            }

            if (flag) {
               NoSlowSwords noSlowSwords = (NoSlowSwords)value;
            }
         }

         enabled = true;
      }

      return enabled;
   }

   public boolean isBlocking() {
      return this.blocking;
   }

   static {
      try {
      long var0 = KEY ^ 128937143009780L;
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
      String var6 = "*^\u0098\u00da\u00b7RP\u00b6>\u00d0\u00a9\u00d5\u00d1\u0097\u00c5}\u0010T\u00cea\u001bZ\nN(\u0091\u009a\u00fa\u00d89\u0007A\u0002\u0018\b\u00f0#\u00e5\u00cf\u00e3\u00b1\u00f8\u0086Zt\u0016\u00d3n\u00d7\u00aa\u00ddL\u00ed{J\u001e\u008e\f";
      int var8 = "*^\u0098\u00da\u00b7RP\u00b6>\u00d0\u00a9\u00d5\u00d1\u0097\u00c5}\u0010T\u00cea\u001bZ\nN(\u0091\u009a\u00fa\u00d89\u0007A\u0002\u0018\b\u00f0#\u00e5\u00cf\u00e3\u00b1\u00f8\u0086Zt\u0016\u00d3n\u00d7\u00aa\u00ddL\u00ed{J\u001e\u008e\f"
         .length();
      char var5 = 16;
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
