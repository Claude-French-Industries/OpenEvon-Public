package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.RotationHitResult;
import club.evon.data.RotationMode;
import club.evon.event.ClientTickEvent;
import club.evon.event.HudRenderEvent;
import club.evon.event.Subscribe;
import club.evon.manager.RotationManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.setting.TargetsSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.RotationUtil;




public final class AimAssist extends BaseModule {
   private final EnumSetting<RotationMode> rotationMode;
   private final DoubleSetting range;
   private final DoubleSetting fov;
   private final DoubleSetting rotationSpeed;
   private final BooleanSetting requirePressed;
   private final BooleanSetting vertical;
   private final BooleanSetting drawFov;
   private final TargetsSetting targets;
   private static final long KEY = KeyedRandom.create(-7732553074326409303L, 6204871669318015272L, MethodHandles.lookup().lookupClass()).next(37641908761926L);
   private static final String[] STRINGS;

   public AimAssist() {
      super((STRINGS)[2], (STRINGS)[4], ModuleCategory.COMBAT);

      long longValue = KEY ^ 42142966265505L;
      String[] strings = STRINGS;
      String[] strings2 = KillAura.getStrings();
      this.rotationMode = new EnumSetting<>(strings[5], RotationMode.NORMAL);
      this.range = new DoubleSetting(strings[7], 3.0, 3.0, 8.0, 0.05);
      this.fov = new DoubleSetting(strings[6], 180.0, 1.0, 180.0, 1.0);
      this.rotationSpeed = new DoubleSetting(strings[1], 5.0, 1.0, 10.0, 0.1);
      String[] strings3 = strings2;
      this.requirePressed = new BooleanSetting(strings[0], true);
      this.vertical = new BooleanSetting(strings[8], true);
      this.drawFov = new BooleanSetting(strings[3], true);
      this.targets = new TargetsSetting();
      this.addSettings(this.rotationMode, this.range, this.fov, this.rotationSpeed, this.vertical, this.requirePressed, this.targets);
      if (strings3 != null) {
         BaseModule.setStateArray(new BaseModule[4]);
      }
      }

   @Subscribe
   private void onHudRender(HudRenderEvent event) {
      long longValue = KEY ^ 85917870150675L;
      if (this.drawFov.I()) {
      }
   }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 128059544030066L;
      String[] strings = KillAura.getStrings();
      Object tmp = this;
      if (strings == null) {
         if (!this.shouldRun()) {
            return;
         }

         tmp = this.targets.findTargets(this.range.J()).stream().min(Comparator.comparingDouble(PlayerUtil::getDistanceToLivingEntity)).orElse(null);
      }

      LivingEntity entity = (LivingEntity)tmp;
      LivingEntity entity2 = entity;
      if (strings == null) {
         if (entity == null) {
            return;
         }

         entity2 = entity;
      }

      if (RotationUtil.isInFov(entity2, this.fov.J().floatValue())) {
         Vec3d eyePos = ClientContext.MINECRAFT_CLIENT.player.getEyePos();
         Vec3d closestPointOnLivingEntity = PlayerUtil.closestPointOnLivingEntity(eyePos, entity);
         RotationHitResult findRotationToLivingEntity = RotationUtil.findRotationToLivingEntity(entity, closestPointOnLivingEntity, ClientContext.MINECRAFT_CLIENT.player.getEntityInteractionRange());
         if (strings == null) {
            if (findRotationToLivingEntity == null) {
               return;
            }

            RotationManager.getRotationController().forceCursorLock();
         }

         RotationManager.getRotationController().rotateTo(findRotationToLivingEntity.rotation(), this.rotationSpeed.J().floatValue() * 18.0F);
      }
   }

   private boolean shouldRun() {
      long longValue = KEY ^ 68578104910237L;
      String[] strings = KillAura.getStrings();
      if (ClientContext.MINECRAFT_CLIENT.player == null) {
         return false;
      }

      boolean tmp = this.requirePressed.I();
      if (strings == null) {
         if (tmp) {
            tmp = ClientContext.MINECRAFT_CLIENT.options.attackKey.isPressed();
            if (strings != null) {
               return tmp;
            }

            if (!tmp) {
               return false;
            }
         }

         tmp = true;
      }

      return tmp;
   }

   static {
      try {
      long var0 = KEY ^ 19569244162752L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[9];
      int var7 = 0;
      String var6 = "\u0019,%\u00ec0M\u00e1\u00d5ik \u00df\u00a6\u0011B\u00c5\u0010\u0011S\u00b6}\u0006x\u0004\t \u00c5\u00e8pv\u00a8>\u001c\u0010\u001f+F\u0006\u008d\u000e'$~mv\u0090\u00f1\u00da\u00c0\u008e\u0010\u00a8\u00d6n\u00fb|\u00fe2\u00a9w\u00b5\u00f1\u00a1\u00e9\u0096\u0090\u00e0\u0018\u00f9K\u0087?c1ox\u00ff~\u0099\u00f3\"\u00f3\u0080\u00c0I\u00d2c\u00f9\"\u00eeu\"\u0010\u0011S\u00b6}\u0006x\u0004\t\u00a19\u00ab\t\u001e\u00caQ\u00cb\baM\u00ed\u00c0\u00d5\u00c2\u00f3l";
      int var8 = "\u0019,%\u00ec0M\u00e1\u00d5ik \u00df\u00a6\u0011B\u00c5\u0010\u0011S\u00b6}\u0006x\u0004\t \u00c5\u00e8pv\u00a8>\u001c\u0010\u001f+F\u0006\u008d\u000e'$~mv\u0090\u00f1\u00da\u00c0\u008e\u0010\u00a8\u00d6n\u00fb|\u00fe2\u00a9w\u00b5\u00f1\u00a1\u00e9\u0096\u0090\u00e0\u0018\u00f9K\u0087?c1ox\u00ff~\u0099\u00f3\"\u00f3\u0080\u00c0I\u00d2c\u00f9\"\u00eeu\"\u0010\u0011S\u00b6}\u0006x\u0004\t\u00a19\u00ab\t\u001e\u00caQ\u00cb\baM\u00ed\u00c0\u00d5\u00c2\u00f3l"
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

                  var6 = "\u008cX\u0085y\u00bf\u00a37\u00b1\u0010\u00b8\u00d9\u00bf\u0097~x[\u00c7\u0093\u008c\u00b7\u0016\u00a5\u00a5\u00a8|";
                  var8 = "\u008cX\u0085y\u00bf\u00a37\u00b1\u0010\u00b8\u00d9\u00bf\u0097~x[\u00c7\u0093\u008c\u00b7\u0016\u00a5\u00a5\u00a8|".length();
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
