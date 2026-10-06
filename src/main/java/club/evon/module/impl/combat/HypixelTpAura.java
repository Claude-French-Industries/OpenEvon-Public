package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import java.util.Optional;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.InputEvent;
import club.evon.event.MotionEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.manager.KeybindManager;
import club.evon.manager.OutgoingPacketManager;
import club.evon.mixins.ClientPlayerEntityAccessor;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.setting.DoubleSetting;
import club.evon.setting.TargetsSetting;
import club.evon.util.KeyedRandom;
import club.evon.util.RotationUtil;
import club.evon.util.net.PacketBlocker;




public final class HypixelTpAura extends BaseModule {
   private final DoubleSetting range;
   private final TargetsSetting targets;
   private PacketBlocker packetBlocker;
   private LivingEntity target;
   private LivingEntity attackTarget;
   private boolean initialized;
   private int ticksSinceTeleport;
   private static final long KEY = KeyedRandom.create(1310558596539821275L, -4140309393299128678L, MethodHandles.lookup().lookupClass()).next(212194433869803L);
   private static final String[] STRINGS;

   public HypixelTpAura() {
      super((STRINGS)[0], "", ModuleCategory.COMBAT);

      String[] strings = STRINGS;
      this.range = new DoubleSetting(strings[1], 20.0, 6.0, 20.0, 1.0);
      this.targets = new TargetsSetting();
      this.addSettings(this.range, this.targets);
      }

   private void startBlocking() {
      long longValue = KEY ^ 108561216531050L;
      String[] strings = KillAura.getStrings();
      PacketBlocker tmp = this.packetBlocker;
      if (strings == null) {
         if (this.packetBlocker != null) {
            return;
         }

         this.packetBlocker = new PacketBlocker(OutgoingPacketManager.getInstance());
         tmp = this.packetBlocker;
      }

      tmp.blockAll();
   }

   private void stopBlocking() {
      long longValue = KEY ^ 40687088924680L;
      String[] strings = KillAura.getStrings();
      PacketBlocker tmp = this.packetBlocker;
      if (strings == null) {
         if (this.packetBlocker == null) {
            return;
         }

         tmp = this.packetBlocker;
      }

      tmp.unblock();
      this.packetBlocker = null;
      this.initialized = false;
   }

   @Subscribe
   private void onMotion(MotionEvent event) {
      long longValue = KEY ^ 107931730489416L;
      String[] strings = KillAura.getStrings();
      this.updateTarget();
      String[] strings2 = strings;
      HypixelTpAura hypixelTpAura = this;
      if (strings2 == null) {
         if (!this.initialized) {
            this.initialized = true;
            return;
         }

         hypixelTpAura = this;
      }

      if (strings2 == null) {
         if (hypixelTpAura.target == null) {
            return;
         }

         hypixelTpAura = this;
      }

      label40: {
         label39: {
            if (strings2 == null) {
               if (hypixelTpAura.packetBlocker == null) {
                  break label39;
               }

               hypixelTpAura = this;
            }

            LivingEntity entity = hypixelTpAura.target.getEntity();
            event.setX(entity.getX());
            event.setY(entity.getY());
            event.setZ(entity.getZ());
            this.attackTarget = entity;
            if (strings2 == null) {
               break label40;
            }
         }

         event.setX(0.0);
         event.setY(0.0);
         event.setZ(0.0);
      }

      ClientPlayerEntityAccessor player = (ClientPlayerEntityAccessor)ClientContext.MINECRAFT_CLIENT.player;
      player.setTicksSinceLastPositionPacketSent(20);
   }

   private void updateTarget() {
      long longValue = KEY ^ 96093536674110L;
      String[] strings = KillAura.getStrings();
      Optional min = this.targets.findTargets(this.range.J()).stream().min(Comparator.comparingDouble(entity -> {
         float var1x = RotationUtil.getRotationTo(entity.getEyePos()).x;
         return (int)MathHelper.angleBetween(ClientContext.MINECRAFT_CLIENT.player.getYaw(), var1x);
      }));
      String[] strings2 = strings;
      if (strings2 == null) {
         if (min.isEmpty()) {
            this.target = null;
            return;
         }

         this.target = (LivingEntity)min.get();
      }
   }

   @Subscribe
   private void onInput(InputEvent event) {
      long longValue = KEY ^ 132768903683127L;
      String[] strings = KillAura.getStrings();
      HypixelTpAura tmp = this;
      if (strings == null) {
         if (this.attackTarget != null) {
            ClientContext.MINECRAFT_CLIENT.crosshairTarget = new EntityHitResult(this.attackTarget);
            KeybindManager.getAttackState().queuePress();
            this.attackTarget = null;
         }

         tmp = this;
      }

      if (strings == null) {
         if (++tmp.ticksSinceTeleport <= 1) {
            return;
         }

         tmp = this;
      }

      tmp.stopBlocking();
   }

   @Subscribe
   private void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 72030739945930L;
      String[] strings = KillAura.getStrings();
      if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
         HypixelTpAura tmp = this;
         if (strings == null) {
            if (this.packetBlocker != null) {
               return;
            }

            this.startBlocking();
            tmp = this;
         }

         tmp.ticksSinceTeleport = 0;
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.stopBlocking();
      this.target = null;
      this.attackTarget = null;
      this.ticksSinceTeleport = 0;
   }

   static {
      try {
      long var0 = KEY ^ 94426892215783L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[2];
      int var7 = 0;
      String var6 = "\u000e*\u00ff/W>\u00b0\u000b\u00a7 \u00f3\u007f!\u0006\u00ac\u00c1\b\u00f9\u00f6F\u00e8\u00a0\u00c1\u0087\u00ab";
      int var8 = "\u000e*\u00ff/W>\u00b0\u000b\u00a7 \u00f3\u007f!\u0006\u00ac\u00c1\b\u00f9\u00f6F\u00e8\u00a0\u00c1\u0087\u00ab".length();
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
