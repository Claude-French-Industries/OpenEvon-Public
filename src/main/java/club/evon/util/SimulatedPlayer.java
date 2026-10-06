package club.evon.util;
import com.mojang.authlib.GameProfile;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.manager.RotationManager;
import club.evon.mixins.LivingEntityAccessor;
import club.evon.module.impl.motion.MoveFix;




public final class SimulatedPlayer {
   private OtherClientPlayerEntity fakePlayer;
   private final PlayerEntity u;
   private static final long KEY = KeyedRandom.create(-7202785198274869615L, 8505584687477441829L, MethodHandles.lookup().lookupClass()).next(259977089517111L);
   private static final String PROFILE_NAME;

   public SimulatedPlayer(PlayerEntity player) {
      super();

      long longValue = KEY ^ 14703731900743L;
      String[] strings = PlayerUtil.getGuard();
      String[] strings2 = strings;
      if (strings2 != null) {
         if (ClientContext.MINECRAFT_CLIENT.world != null) {
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), PROFILE_NAME);
            this.fakePlayer = new SimulatedPlayerEntity(this, ClientContext.MINECRAFT_CLIENT.world, gameProfile);
            this.u = player;
            this.copyPlayerState();
            return;
         }
      }

      this.u = null;
      }

   private void copyPlayerState() {
      String[] strings;
      float floatValue;
      label57: {
         long longValue = KEY ^ 3813639491210L;
         this.fakePlayer.noClip = this.u.noClip;
         this.fakePlayer.lastX = this.u.lastX;
         String[] strings2 = PlayerUtil.getGuard();
         this.fakePlayer.lastY = this.u.lastY;
         this.fakePlayer.lastZ = this.u.lastZ;
         this.fakePlayer.lastYaw = this.u.lastYaw;
         this.fakePlayer.lastPitch = this.u.lastPitch;
         this.fakePlayer.setPosition(this.u.getEntityPos());
         this.fakePlayer.setBoundingBox(this.u.getBoundingBox());
         this.fakePlayer.setVelocity(this.u.getVelocity());
         strings = strings2;
         PlayerEntity player = this.u;
         if (strings != null) {
            if (this.u == ClientContext.MINECRAFT_CLIENT.player) {
               floatValue = RotationManager.getMouseRotationManager().getYaw(this.u.getYaw());
               break label57;
            }

            player = this.u;
         }

         floatValue = player.getYaw();
      }

      PlayerEntity player2 = null;

      label51: {
         float floatValue2 = floatValue;
         player2 = this.fakePlayer;
         label50:
         if (strings != null) {
            this.fakePlayer.setYaw(floatValue2);
            if (!EvonClient.getInstance().getModuleManager().getModuleByClass(MoveFix.class).isEnabled()) {
               player2 = this.u;
               if (strings == null) {
                  break label50;
               }

               if (this.u == ClientContext.MINECRAFT_CLIENT.player) {
                  floatValue = RotationManager.getMouseRotationManager().getPitch(this.u.getPitch());
                  break label51;
               }
            }

            player2 = this.u;
         }

         floatValue = player2.getPitch();
      }

      float floatValue3 = floatValue;
      this.fakePlayer.setPitch(floatValue3);
      this.fakePlayer.setSneaking(this.u.isSneaking());
      this.fakePlayer.setOnGround(this.u.isOnGround());
      this.fakePlayer.setSprinting(this.u.isSprinting());
      Iterator iterator = this.u.getStatusEffects().iterator();

      while (true) {
         if (iterator.hasNext()) {
            StatusEffectInstance statusEffectInstance = (StatusEffectInstance)iterator.next();
            this.fakePlayer.addStatusEffect(statusEffectInstance);
            if (strings == null) {
               break;
            }

            if (strings != null) {
               continue;
            }
         }

         this.fakePlayer.setMovementSpeed(this.u.getMovementSpeed());
         this.fakePlayer.fallDistance = this.u.fallDistance;
         break;
      }
   }

   public void simulateTicks(int tickCount) {
      long longValue = KEY ^ 34380166441222L;
      String[] strings = PlayerUtil.getGuard();
      LivingEntityAccessor entity = (LivingEntityAccessor)this.fakePlayer;
      String[] strings2 = strings;
      int index = 0;

      while (index < tickCount) {
         entity.callTravelMidAir(new Vec3d(this.u.sidewaysSpeed, this.u.upwardSpeed, this.u.forwardSpeed));
         index++;
         if (strings2 == null) {
            break;
         }
      }
   }

   public void tick() {
      this.simulateTicks(1);
   }

   public OtherClientPlayerEntity getFakePlayer() {
      return this.fakePlayer;
   }

   static {
      try {
      long var0 = KEY ^ 10401801849147L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var4 = var2.doFinal("\u00ae\u0098\u00af\u0014\u00f7\u00af\u00e0\u00a8,s\u00c99<|#\u00a3Y\u00cb\u00bd\u00b8\u00f1\u00b6W$".getBytes("ISO-8859-1"));
      String var5 = decodeUtf8(var4).intern();
      byte var10001 = -1;
      PROFILE_NAME = var5;
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
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
