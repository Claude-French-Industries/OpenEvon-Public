package club.evon.module.impl.render;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.block.Blocks;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.AttackEntityEvent;
import club.evon.event.ClientTickEvent;
import club.evon.event.Subscribe;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DeathEffect;
import club.evon.setting.DoubleSetting;
import club.evon.setting.EnumSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.net.NetworkUtil;




public final class PlayerEffects extends BaseModule {
   private final EnumSetting<DeathEffect> deathEffect;
   private final BooleanGroupSetting attackParticles;
   private final DoubleSetting amount;
   private PlayerEntity lastAttackedEntity;
   private static final long KEY = KeyedRandom.create(-8634633151509117388L, 865803702405338855L, MethodHandles.lookup().lookupClass()).next(77305250056853L);
   private static final String[] STRINGS;

   public PlayerEffects() {
      super((STRINGS)[4], (STRINGS)[5], ModuleCategory.RENDER);

      String[] strings = STRINGS;
      this.deathEffect = new EnumSetting<>(strings[6], DeathEffect.NONE);
      this.attackParticles = new BooleanGroupSetting(strings[8], new BooleanSetting(strings[7], false), new BooleanSetting(strings[3], false));
      this.amount = new DoubleSetting(strings[2], 1.0, 1.0, 20.0, 1.0);
      this.amount.setVisible(() -> Arrays.stream(this.attackParticles.getValue()).anyMatch(BooleanSetting::I));
      this.addSettings(this.deathEffect, this.attackParticles, this.amount);
      }

   @Subscribe
   private void onClientTick(ClientTickEvent event) {
      long longValue = KEY ^ 54171537881229L;
      boolean flagUnset = Animations.isFlagUnset();
      if (flagUnset) {
         if (ClientContext.MINECRAFT_CLIENT.player != null && ClientContext.MINECRAFT_CLIENT.world != null) {
            if (this.lastAttackedEntity == null) {
               return;
            }

            PlayerEntity tmp = this.lastAttackedEntity;
            int value;
            if (tmp.isDead() || !ClientContext.MINECRAFT_CLIENT.world.hasEntity(this.lastAttackedEntity) || this.lastAttackedEntity.isRemoved()) {
               value = this.deathEffect.getValue().ordinal();
            } else {
               return;
            }

            switch (value) {
               case 1:
                  ClientContext.MINECRAFT_CLIENT.world.addBlockBreakParticles(this.lastAttackedEntity.getBlockPos().up(), Blocks.REDSTONE_BLOCK.getDefaultState());
                  ClientContext.MINECRAFT_CLIENT.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_STONE_BREAK, 1.0F));
                  if (flagUnset) {
                     break;
                  }
               case 2:
                  LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, ClientContext.MINECRAFT_CLIENT.world);
                  lightningEntity.refreshPositionAfterTeleport(this.lastAttackedEntity.getEntityPos());
                  NetworkUtil.applyPacket(new EntitySpawnS2CPacket(lightningEntity, -1, this.lastAttackedEntity.getBlockPos()));
                  if (flagUnset) {
                     break;
                  }
               case 3:
                  ClientContext.MINECRAFT_CLIENT.particleManager.addEmitter(this.lastAttackedEntity, ParticleTypes.HEART);
                  ClientContext.MINECRAFT_CLIENT.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1.0F));
                  if (flagUnset) {
                     break;
                  }
               case 4:
                  ClientContext.MINECRAFT_CLIENT.particleManager.addEmitter(this.lastAttackedEntity, ParticleTypes.LAVA);
                  ClientContext.MINECRAFT_CLIENT.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ITEM_FIRECHARGE_USE, 1.0F));
            }

            this.lastAttackedEntity = null;
            return;
         }

         this.lastAttackedEntity = null;
      }
   }

   @Subscribe
   private void onAttackEntity(AttackEntityEvent event) {
      long longValue = KEY ^ 25562740434664L;
      boolean flagUnset = Animations.isFlagUnset();
      Entity entity;
      if (flagUnset) {
         if (ClientContext.MINECRAFT_CLIENT.player == null) {
            return;
         }

         entity = event.getEntity();
      } else {
         entity = ClientContext.MINECRAFT_CLIENT.player;
      }

      PlayerEntity player;
      if (flagUnset) {
         if (!(entity instanceof PlayerEntity)) {
            return;
         }
      }

      player = (PlayerEntity)entity;
      String[] strings = STRINGS;
      boolean flag = this.attackParticles.getByName(strings[1]).I();
      boolean flag2 = this.attackParticles.getByName(strings[0]).I();
      int index = 0;

      while (index < this.amount.J().intValue()) {
         if (!flagUnset) {
            return;
         }

         if (flag) {
            ClientContext.MINECRAFT_CLIENT.player.addCritParticles((Entity)entity);
         }

         if (flag2) {
            ClientContext.MINECRAFT_CLIENT.player.addEnchantedHitParticles((Entity)entity);
         }

         index++;
      }

      this.lastAttackedEntity = player;
   }

   static {
      try {
      long var0 = KEY ^ 112865169939392L;
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
      String var6 = "U\u00fd\u00f5L\u00fe\u0084\u00ff\u00b4`h\u0092\u00a6noQ\u00ee\u0010\u0086\u00ff\u00160\b\u00ed\u00c0j\u00e2\u00bc\u00e5@\u0097\u00b5\u00a6\u00f3\b\u008a\u00ce\u00a1\u0093jlm\u0088\u0010U\u00fd\u00f5L\u00fe\u0084\u00ff\u00b4`h\u0092\u00a6noQ\u00ee\u0010\u00bd\u0015E\u0082\u00c14\u00b1\u00df\u00af\r>\u0092\u00c1,\u008e\u00d7(9\u0005\u0007\u00acA\u00df\u0098\u009d\u00cd\u00aa\u00e3i9*\u00ab\u008e\u00e2R\u00c5\u00e5o\u0083W\u0096\u009a\u0010\u000esi\u00eayM8\u00f9\u00f9\u00dcIg\u009eb\u0010\u0010x\u00f0\u0005\u0082\u00de\u0012\u00eeTS\u00cbJ\u00b6R\u00fb\u00bc";
      int var8 = "U\u00fd\u00f5L\u00fe\u0084\u00ff\u00b4`h\u0092\u00a6noQ\u00ee\u0010\u0086\u00ff\u00160\b\u00ed\u00c0j\u00e2\u00bc\u00e5@\u0097\u00b5\u00a6\u00f3\b\u008a\u00ce\u00a1\u0093jlm\u0088\u0010U\u00fd\u00f5L\u00fe\u0084\u00ff\u00b4`h\u0092\u00a6noQ\u00ee\u0010\u00bd\u0015E\u0082\u00c14\u00b1\u00df\u00af\r>\u0092\u00c1,\u008e\u00d7(9\u0005\u0007\u00acA\u00df\u0098\u009d\u00cd\u00aa\u00e3i9*\u00ab\u008e\u00e2R\u00c5\u00e5o\u0083W\u0096\u009a\u0010\u000esi\u00eayM8\u00f9\u00f9\u00dcIg\u009eb\u0010\u0010x\u00f0\u0005\u0082\u00de\u0012\u00eeTS\u00cbJ\u00b6R\u00fb\u00bc"
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

                  var6 = "\u0086\u00ff\u00160\b\u00ed\u00c0j\u00e2\u00bc\u00e5@\u0097\u00b5\u00a6\u00f3\u0018T\u00f9Ft\u0019\u009c\u00e1\u00b3\u0006\u00d37\u00a8\u00a1\u00f7\u0090\u00b3\u00a8\u007f\u00cd\u00de\u0097\u001e\u00ee\u0093";
                  var8 = "\u0086\u00ff\u00160\b\u00ed\u00c0j\u00e2\u00bc\u00e5@\u0097\u00b5\u00a6\u00f3\u0018T\u00f9Ft\u0019\u009c\u00e1\u00b3\u0006\u00d37\u00a8\u00a1\u00f7\u0090\u00b3\u00a8\u007f\u00cd\u00de\u0097\u001e\u00ee\u0093"
                     .length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
   
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}

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


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
