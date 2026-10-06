package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.MathHelper;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.HypixelTargetFilter;
import club.evon.event.GameLoopEvent;
import club.evon.event.IncomingPacketEvent;
import club.evon.event.MovementInputEvent;
import club.evon.event.PlayerVelocityEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.manager.IncomingPacketManager;
import club.evon.mixins.LivingEntityAccessor;
import club.evon.module.BaseModule;
import club.evon.module.SubMode;
import club.evon.setting.BooleanGroupSetting;
import club.evon.setting.BooleanSetting;
import club.evon.setting.DoubleSetting;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;
import club.evon.util.Stopwatch;
import club.evon.util.math.MovementUtil;
import club.evon.util.net.PacketBlocker;




public final class VelocityHypixelMode extends SubMode<Velocity> {
   private final BooleanGroupSetting addons;
   private final DoubleSetting delay;
   private final Stopwatch timer;
   private final PacketBlocker packetBlocker;
   private boolean pendingJump;
   private int knockbackTicks;
   private static String stateString;
   private static final long KEY = KeyedRandom.create(-7492633765842120309L, -2543856811007456806L, MethodHandles.lookup().lookupClass()).next(238964673667752L);
   private static final String[] STRINGS_TABLE;
   private static final long KNOCKBACK_TICKS;

   public VelocityHypixelMode(String name, Velocity parent) {
      super(name, parent);
      String[] strings = STRINGS_TABLE;
      this.addons = new BooleanGroupSetting(strings[2], new BooleanSetting(strings[1], false));
      this.delay = new DoubleSetting(strings[1], 250.0, 50.0, 1000.0, 50.0);
      this.timer = new Stopwatch();
      this.packetBlocker = new PacketBlocker(IncomingPacketManager.getInstance());
      this.delay.setVisible(this.addons.getByName(strings[1])::I);
      this.addSettings(this.addons, this.delay);
   }

   @Subscribe
   public void onIncomingPacket(IncomingPacketEvent event) {
      long longValue = KEY ^ 80859152230288L;
      String stateString2 = getStateString();
      Packet packet = event.getPacket();
      boolean flag = packet instanceof EntityVelocityUpdateS2CPacket;
      if (stateString2 == null) {
         if (flag) {
            EntityVelocityUpdateS2CPacket packet2 = (EntityVelocityUpdateS2CPacket)packet;
            boolean shouldCancel = ((Velocity)this.getParent()).shouldCancel();
            if (shouldCancel) {
               return;
            }

            shouldCancel = packet2.getEntityId() != ClientContext.MINECRAFT_CLIENT.player.getId();
            if (shouldCancel) {
               return;
            }

            shouldCancel = this.addons.getByName(STRINGS_TABLE[3]).I();
            if (stateString2 == null) {
               if (!shouldCancel) {
                  return;
               }

               shouldCancel = this.packetBlocker.getQueue().getQueuedPackets().stream().anyMatch(packet3 -> {
                  long longValue2 = KEY ^ 88566470424123L;
                  String stateString3 = getStateString();
                  Packet var5x = packet3.getPacket();
                  if (stateString3 == null) {
                     if (!(var5x instanceof EntityVelocityUpdateS2CPacket)) {
                        return false;
                     }
                  }

                  EntityVelocityUpdateS2CPacket var4x = (EntityVelocityUpdateS2CPacket)var5x;
                  int var7x = var4x.getEntityId();
                  return stateString3 != null ? var7x != 0 : var7x == ClientContext.MINECRAFT_CLIENT.player.getId();
               });
            }

            if (shouldCancel) {
               event.cancel();
               ChatUtil.sendMessage(STRINGS_TABLE[0]);
            }

            this.packetBlocker.block(null, IncomingPacketManager.PACKET_VALIDATOR);
            this.timer.reset();
            if (stateString2 == null) {
               return;
            }

            BaseModule.setStateArray(new BaseModule[2]);
         }

         flag = event.getPacket() instanceof PlayerPositionLookS2CPacket;
      }

      if (flag) {
         this.packetBlocker.unblock();
      }
   }

   @Subscribe
   public void onPlayerVelocity(PlayerVelocityEvent event) {
      long longValue = KEY ^ 75942728387428L;
      String stateString2 = getStateString();
      if (!((Velocity)this.getParent()).shouldCancel()) {
         double doubleValue = event.getVelocity().getY();
         boolean explosion = event.isExplosion();
         if (stateString2 == null) {
            if (explosion) {
               this.packetBlocker.unblock();
               return;
            }

            explosion = ClientState.getInstance().getServerDetector().getServerProfile() instanceof HypixelTargetFilter;
         }

         if (stateString2 == null) {
            if (!explosion) {
               this.knockbackTicks = (int)KNOCKBACK_TICKS;
               return;
            }

            if (ClientContext.MINECRAFT_CLIENT.player.isOnGround()
               && (ClientContext.MINECRAFT_CLIENT.player.input.playerInput.jump()
                  || ((LivingEntityAccessor)ClientContext.MINECRAFT_CLIENT.player).callGetJumpVelocity() - doubleValue < 0.0)) {
               this.pendingJump = true;
            }

            this.knockbackTicks = (int)KNOCKBACK_TICKS;
            return;
         }

         if (explosion) {
            ClientContext.MINECRAFT_CLIENT.player.input.playerInput.jump();
         }

         this.knockbackTicks = (int)KNOCKBACK_TICKS;
      }
   }

   @Subscribe
   public void onMovementInput(MovementInputEvent event) {
      long longValue = KEY ^ 90669484632056L;
      String stateString2 = getStateString();
      VelocityHypixelMode tmp = this;
      if (stateString2 == null) {
         if (!this.pendingJump) {
            return;
         }

         ((LivingEntityAccessor)ClientContext.MINECRAFT_CLIENT.player).setJumpingCooldown(0);
         event.setJump(true);
         tmp = this;
      }

      tmp.pendingJump = false;
   }

   @Subscribe
   private void onGameLoop(GameLoopEvent event) {
      long longValue = KEY ^ 102354681036969L;
      String stateString2 = getStateString();
      boolean ticking = event.isTicking();
      if (stateString2 == null) {
         if (!ticking) {
            return;
         }

         ticking = this.packetBlocker.isBlocking();
      }

      if (stateString2 == null) {
         if (ticking) {
            boolean flag;
            if (ClientContext.MINECRAFT_CLIENT.player == null) {
               flag = true;
            } else {
               flag = ClientContext.MINECRAFT_CLIENT.player.isClimbing();
               if (!flag) {
                  flag = ClientContext.MINECRAFT_CLIENT.player.isInFluid();
                  if (!flag) {
                     flag = this.timer.hasElapsed(this.delay.J().longValue());
                  }
               }
            }

            if (flag) {
               this.packetBlocker.unblock();
            }
         }

         if (this.knockbackTicks <= 0) {
            return;
         }

         this.knockbackTicks--;
         return;
      }

      if (ticking) {
         this.knockbackTicks--;
      }
   }

   public boolean isKnockbackActive() {
      long longValue = KEY ^ 75685705132479L;
      String stateString2 = getStateString();
      if (stateString2 != null) {
         return this.knockbackTicks != 0;
      }

      if (this.knockbackTicks <= 0) {
         return false;
      }

      float floatValue = MathHelper.angleBetween(MovementUtil.getMovementYaw(), MovementUtil.getInputYaw()) - 70.0F;
      return floatValue >= 0.0F;
   }

   public BooleanGroupSetting getAddons() {
      return this.addons;
   }

   public DoubleSetting getDelay() {
      return this.delay;
   }

   public Stopwatch getTimer() {
      return this.timer;
   }

   public PacketBlocker getPacketBlocker() {
      return this.packetBlocker;
   }

   public boolean isJumpPending() {
      return this.pendingJump;
   }

   public int getKnockbackTicks() {
      return this.knockbackTicks;
   }

   public static void setStateString(String value) {
      stateString = value;
   }

   public static String getStateString() {
      return stateString;
   }

   static {
      try {
      long var5 = KEY ^ 101923233994695L;
      setStateString(null);
      Cipher var7;
      Cipher var10000 = var7 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var8 = 1; var8 < 8; var8++) {
         var10003[var8] = (byte)(var5 << var8 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var14 = new String[4];
      int var12 = 0;
      String var11 = "!m`\u00d5\u00a3d\u00c4\u0019\u00c7vl>q\u00d1\u0019r\u009d\n\u00ceIE\u0014\u00ce5G\u001d6\u0089\u0000\u00c0\u008e!\b\u0012\u008c\u0004\u0001\u00d5O\u00ac.";
      int var13 = "!m`\u00d5\u00a3d\u00c4\u0019\u00c7vl>q\u00d1\u0019r\u009d\n\u00ceIE\u0014\u00ce5G\u001d6\u0089\u0000\u00c0\u008e!\b\u0012\u008c\u0004\u0001\u00d5O\u00ac."
         .length();
      char var10 = ' ';
      int var17 = -1;

      label37:
      while (true) {
         String var18 = var11.substring(++var17, var17 + var10);
         byte var10001 = -1;

         while (true) {
            byte[] var15 = var7.doFinal(var18.getBytes("ISO-8859-1"));
            String var26 = decodeUtf8(var15).intern();
            switch (var10001) {
               case 0:
                  var14[var12++] = var26;
                  if ((var17 += var10) >= var13) {
                     STRINGS_TABLE = var14;
                     Cipher var0;
                     var10000 = var0 = Cipher.getInstance("DES/CBC/NoPadding");
                     var10002 = SecretKeyFactory.getInstance("DES");
                     var10003 = new byte[]{(byte)(var5 >>> 56), 0, 0, 0, 0, 0, 0, 0};

                     for (int var1 = 1; var1 < 8; var1++) {
                        var10003[var1] = (byte)(var5 << var1 * 8 >>> 56);
                     }

                     var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
                     long var2 = -7774810542198589009L;
                     byte[] var4 = var0.doFinal(
                        new byte[]{
                           (byte)(var2 >>> 56),
                           (byte)(var2 >>> 48),
                           (byte)(var2 >>> 40),
                           (byte)(var2 >>> 32),
                           (byte)(var2 >>> 24),
                           (byte)(var2 >>> 16),
                           (byte)(var2 >>> 8),
                           (byte)var2
                        }
                     );
                     long var30 = (var4[0] & 255L) << 56
                        | (var4[1] & 255L) << 48
                        | (var4[2] & 255L) << 40
                        | (var4[3] & 255L) << 32
                        | (var4[4] & 255L) << 24
                        | (var4[5] & 255L) << 16
                        | (var4[6] & 255L) << 8
                        | var4[7] & 255L;
                     var10001 = -1;
                     KNOCKBACK_TICKS = var30;
                     break label37;
                  }

                  var10 = var11.charAt(var17);
                  break;
               default:
                  var14[var12++] = var26;
                  if ((var17 += var10) < var13) {
                     var10 = var11.charAt(var17);
                     continue label37;
                  }

                  var11 = "\u00abu-\u00b2\u00a0<\u00cfj\b\u0012\u008c\u0004\u0001\u00d5O\u00ac.";
                  var13 = "\u00abu-\u00b2\u00a0<\u00cfj\b\u0012\u008c\u0004\u0001\u00d5O\u00ac.".length();
                  var10 = '\b';
                  var17 = -1;
            }

            var18 = var11.substring(++var17, var17 + var10);
            var10001 = 0;
         }
      }
   
      } catch (Throwable var1000) {
         throw sneakyThrow(var1000);
      }}

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


   @SuppressWarnings("unchecked")
   private static <T extends Throwable> RuntimeException sneakyThrow(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
