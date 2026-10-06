package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.InputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClickScheduler;
import club.evon.manager.KeybindManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.ClickMode;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.setting.TargetsSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.math.RandomUtil;




public final class TriggerBot extends BaseModule {
   private final EnumSetting<ClickMode> clickMode;
   private final DoubleSetting minCps;
   private final DoubleSetting maxCps;
   private final BooleanSetting requirePressed;
   private final TargetsSetting targets;
   private static final long KEY = KeyedRandom.create(-560347913366846428L, 1640770567743389547L, MethodHandles.lookup().lookupClass()).next(52683309768266L);
   private static final String[] STRINGS;

   public TriggerBot() {
      super((STRINGS)[3], (STRINGS)[2], ModuleCategory.COMBAT);

      String[] strings = STRINGS;
      this.clickMode = new EnumSetting<>(strings[0], ClickMode.LEGACY);
      this.minCps = new DoubleSetting(strings[4], 12.0, 1.0, 20.0, 1.0);
      this.maxCps = new DoubleSetting(strings[1], 15.0, 1.0, 20.0, 1.0);
      this.requirePressed = new BooleanSetting(strings[5], true);
      this.targets = new TargetsSetting();
      this.minCps.setVisible(() -> this.clickMode.getValue().equals(ClickMode.LEGACY));
      this.maxCps.setVisible(() -> this.clickMode.getValue().equals(ClickMode.LEGACY));
      this.addSettings(this.clickMode, this.minCps, this.maxCps, this.requirePressed, this.targets);
      }

   @Subscribe
   private void onInput(InputEvent event) {
      long longValue = KEY ^ 66416625156279L;
      String[] strings = KillAura.getStrings();
      HitResult hitResult = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      String[] strings2 = strings;
      HitResult hitResult2 = hitResult;
      if (strings2 == null) {
         if (!(hitResult instanceof EntityHitResult)) {
            return;
         }

         hitResult2 = hitResult;
      }

      EntityHitResult entity = (EntityHitResult)hitResult2;
      if (this.canAttack(entity.getEntity())) {
         KeybindManager.getAttackState().queuePress();
      }
   }

   private boolean canAttack(Entity entity) {
      long longValue = KEY ^ 38824642390278L;
      String[] strings = KillAura.getStrings();
      boolean breakingBlock = ClientContext.MINECRAFT_CLIENT.interactionManager.isBreakingBlock();
      if (strings == null) {
         if (breakingBlock) {
            return false;
         }

         breakingBlock = this.requirePressed.I();
      }

      label45:
      if (strings == null) {
         if (breakingBlock) {
            breakingBlock = ClientContext.MINECRAFT_CLIENT.options.attackKey.isPressed();
            if (strings != null) {
               break label45;
            }

            if (!breakingBlock) {
               return false;
            }
         }

         breakingBlock = this.targets.findTargets(ClientContext.MINECRAFT_CLIENT.player.getEntityInteractionRange()).stream().noneMatch(var1x -> {
            long var2x = KEY ^ 14249779728186L;
            return var1x == entity;
         });
      }

      if (strings == null) {
         if (breakingBlock) {
            return false;
         }

         breakingBlock = ClickScheduler.getInstance().tryClick(RandomUtil.randomDouble(this.minCps.J(), this.maxCps.J()), this.clickMode.getValue().equals(ClickMode.MODERN), true);
      }

      return breakingBlock;
   }

   static {
      try {
      long var0 = KEY ^ 79760624979688L;
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
      String var6 = "A\u00e3\u00f0\u00e2\u00fe\u00a5\u001c#\u00b6L\u00dc;\u00d3\f\u00b5P\b\u00a5\u00d5\u00de\u00dfc\u00e1\u0093\u009f\u0010`\u00d2R\u00c2Q\u00ac\u0084\u00b8\u0004\u00a7X%n\u00a5G\u00ef\u0010`\u00d2R\u00c2Q\u00ac\u0084\u00b8\u0012\u0016|q|\u001at\u00a9";
      int var8 = "A\u00e3\u00f0\u00e2\u00fe\u00a5\u001c#\u00b6L\u00dc;\u00d3\f\u00b5P\b\u00a5\u00d5\u00de\u00dfc\u00e1\u0093\u009f\u0010`\u00d2R\u00c2Q\u00ac\u0084\u00b8\u0004\u00a7X%n\u00a5G\u00ef\u0010`\u00d2R\u00c2Q\u00ac\u0084\u00b8\u0012\u0016|q|\u001at\u00a9"
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

                  var6 = "d\u0003\u00a7\u0000v\u00d0\u00cbX\u0010\u00cah:\u00af\u00ff\u0002V4\u00fc\u008dlg\u00a6/\u00ceG";
                  var8 = "d\u0003\u00a7\u0000v\u00d0\u00cbX\u0010\u00cah:\u00af\u00ff\u0002V4\u00fc\u008dlg\u00a6/\u00ceG".length();
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

   private static JSONException identity(JSONException exception) {
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
