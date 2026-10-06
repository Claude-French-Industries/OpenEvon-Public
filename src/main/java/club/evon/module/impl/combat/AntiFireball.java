package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.compress.utils.Lists;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.data.RotationHitResult;
import club.evon.event.ClientTickEvent;
import club.evon.event.InputEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClickScheduler;
import club.evon.manager.KeybindManager;
import club.evon.manager.RotationManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.RotationUtil;




public final class AntiFireball extends BaseModule {
   private RotationHitResult rotationHitResult;
   private FireballEntity fireball;
   private final DoubleSetting cps;
   private final DoubleSetting rotationSpeed;
   private final BooleanSetting raycast;
   private static final long KEY = KeyedRandom.create(-2718053826364623692L, 2892317418509908558L, MethodHandles.lookup().lookupClass()).next(280016064297191L);
   private static final String[] STRINGS;

   public AntiFireball() {
      super((STRINGS)[3], (STRINGS)[1], ModuleCategory.COMBAT);

      String[] strings = STRINGS;
      this.cps = new DoubleSetting(strings[4], 10.0, 1.0, 20.0, 1.0);
      this.rotationSpeed = new DoubleSetting(strings[2], 5.0, 1.0, 10.0, 0.1);
      this.raycast = new BooleanSetting(strings[0], true);
      this.addSettings(this.cps, this.rotationSpeed, this.raycast);
      }

   @Override
   public void onDisable() {
      this.fireball = null;
      this.rotationHitResult = null;
      super.onDisable();
   }

   @Subscribe(h = -2)
   private void onInput(InputEvent event) {
      long longValue = KEY ^ 6427456635831L;
      String[] strings = KillAura.getStrings();
      AntiFireball tmp = this;
      if (strings == null) {
         if (this.fireball == null) {
            return;
         }

         tmp = this;
      }

      label49: {
         if (strings == null) {
            if (tmp.rotationHitResult == null) {
               break label49;
            }

            tmp = this;
         }

         if (!tmp.raycast.I()) {
            ClientContext.MINECRAFT_CLIENT.crosshairTarget = this.rotationHitResult.hitResult();
         }
      }

      HitResult hitResult = ClientContext.MINECRAFT_CLIENT.crosshairTarget;
      HitResult hitResult2 = hitResult;
      if (strings == null) {
         if (!(hitResult instanceof EntityHitResult)) {
            return;
         }

         hitResult2 = hitResult;
      }

      EntityHitResult entity = (EntityHitResult)hitResult2;
      if (entity.getEntity() == this.fireball && ClickScheduler.getInstance().tryClick(this.cps.J(), false, true)) {
         KeybindManager.getAttackState().queuePress();
      }
   }

   @Subscribe(h = -2)
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 32405335493638L;
      String[] strings = KillAura.getStrings();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (strings == null) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (tmp.world != null) {
         this.fireball = Lists.newArrayList(ClientContext.MINECRAFT_CLIENT.world.getEntities().iterator())
            .stream()
            .filter(entity -> entity instanceof FireballEntity)
            .map(entity2 -> (FireballEntity)entity2)
            .min(Comparator.comparingDouble(PlayerUtil::getDistanceToEntity))
            .orElse(null);
         AntiFireball antiFireball = this;
         if (strings == null) {
            if (this.fireball != null) {
               Vec3d eyePos = ClientContext.MINECRAFT_CLIENT.player.getEyePos();
               Vec3d closestPointOnEntity = PlayerUtil.closestPointOnEntity(eyePos, this.fireball);
               this.rotationHitResult = RotationUtil.findRotationToEntity(this.fireball, closestPointOnEntity, ClientContext.MINECRAFT_CLIENT.player.getEntityInteractionRange());
               if (this.rotationHitResult != null) {
                  RotationManager.getRotationController().rotateTo(this.rotationHitResult.rotation(), this.rotationSpeed.J().floatValue() * 18.0F);
               }

               return;
            }

            antiFireball = this;
         }

         antiFireball.rotationHitResult = null;
      }
   }

   static {
      try {
      long var0 = KEY ^ 77501087370070L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[5];
      int var7 = 0;
      String var6 = "\rp=\u00c7\u00d0\u00c4JP\u0018Y\u00e3\u0098\u0086n\u0005\u0012\u00bf~q3g\u00dcKb\u00bf\u0004njv\u00c32\u001b\u0018\u0010\u00ff\u00e7}\u00e0\u00df\u009dwi\u00c5{{,~wn\u00dc";
      int var8 = "\rp=\u00c7\u00d0\u00c4JP\u0018Y\u00e3\u0098\u0086n\u0005\u0012\u00bf~q3g\u00dcKb\u00bf\u0004njv\u00c32\u001b\u0018\u0010\u00ff\u00e7}\u00e0\u00df\u009dwi\u00c5{{,~wn\u00dc"
         .length();
      char var5 = '\b';
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

                  var6 = "k\u0015n6}@\u00fa\u00ad\fX+\u0004k\u00f8R\u00e2\b\u00de\u00acJ\u00c1r\u00f3\u00b6\u00cc";
                  var8 = "k\u0015n6}@\u00fa\u00ad\fX+\u0004k\u00f8R\u00e2\b\u00de\u00acJ\u00c1r\u00f3\u00b6\u00cc".length();
                  var5 = 16;
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
