package club.evon.module.impl.player;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.EvonClient;
import club.evon.data.PlayerSnapshot;
import club.evon.event.MotionEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.manager.ModuleManager;
import club.evon.manager.OutgoingPacketManager;
import club.evon.mixins.ClientPlayerEntityAccessor;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.module.impl.motion.Flight;
import club.evon.util.KeyedRandom;
import club.evon.util.PlayerUtil;
import club.evon.util.net.PacketBlocker;




public class AntiVoid extends BaseModule {
   private final PacketBlocker packetBlocker;
   private PlayerSnapshot snapshot;
   private boolean wasFalling;
   private boolean suspended;
   private double lastY;
   private static final long STRING_KEY = KeyedRandom.create(-3851084760084118808L, -8190465629892444742L, MethodHandles.lookup().lookupClass()).next(161843252731059L);
   private static final String[] STRINGS;

   public AntiVoid() {
      super((STRINGS)[1], (STRINGS)[0], ModuleCategory.PLAYER);

      String[] strings = STRINGS;
      this.packetBlocker = new PacketBlocker(OutgoingPacketManager.getInstance());
      }

   @Subscribe
   public void onMotion(MotionEvent event) {
      long longValue = STRING_KEY ^ 92597004752707L;
      boolean flag = Breaker.A();
      ModuleManager moduleManager = EvonClient.getInstance().getModuleManager();
      boolean enabled = moduleManager.getModuleByClass(Flight.class).isEnabled();
      boolean flag2;
      if (flag) {
         flag2 = enabled;
      } else {
         flag2 = !enabled
            && !ClientContext.MINECRAFT_CLIENT.player.getAbilities().allowFlying
            && !ClientContext.MINECRAFT_CLIENT.player.getAbilities().flying;
      }

      if (!flag) {
         if (!flag2) {
            this.packetBlocker.unblock();
            this.snapshot = null;
            this.suspended = true;
            return;
         }

         if (PlayerUtil.isPlayerPositionFree()) {
            if (this.suspended) {
               this.wasFalling = true;
               return;
            }

            if (ClientContext.MINECRAFT_CLIENT.player.getY() - this.lastY <= -6.0 && this.snapshot != null) {
               this.snapshot.applyTo(ClientContext.MINECRAFT_CLIENT.player);
               this.packetBlocker.setTransformer(packet -> {
                  long longValue2 = STRING_KEY ^ 11963824095785L;
                  boolean flag3 = Breaker.a();
                  Packet var10000x = packet;
                  if (flag3) {
                     if (packet instanceof PlayerMoveC2SPacket) {
                        return null;
                     }

                     var10000x = packet;
                  }

                  return var10000x;
               });
               this.lastY = ClientContext.MINECRAFT_CLIENT.player.getY();
               this.packetBlocker.unblock();
            } else {
               this.packetBlocker.blockAll();
            }

            this.wasFalling = true;
            return;
         }
      } else {
         if (!flag2) {
            this.packetBlocker.blockAll();
         }

         this.wasFalling = true;
      }

      ClientPlayerEntityAccessor player = (ClientPlayerEntityAccessor)ClientContext.MINECRAFT_CLIENT.player;
      this.snapshot = new PlayerSnapshot(
         ClientContext.MINECRAFT_CLIENT.player.getEntityPos(),
         new Vec3d(ClientContext.MINECRAFT_CLIENT.player.lastX, ClientContext.MINECRAFT_CLIENT.player.lastY, ClientContext.MINECRAFT_CLIENT.player.lastZ),
         ClientContext.MINECRAFT_CLIENT.player.getVelocity(),
         player.getLastXClient(),
         player.getLastYClient(),
         player.getLastZClient(),
         player.getLastYawClient(),
         player.getLastPitchClient(),
         player.isLastOnGround(),
         player.getTicksSinceLastPositionPacketSent(),
         ClientState.getInstance().airTicks,
         ClientState.getInstance().groundTicks
      );
      this.lastY = ClientContext.MINECRAFT_CLIENT.player.getY();
      this.suspended = false;
      if (!flag && this.wasFalling) {
         this.packetBlocker.unblock();
      }

      this.wasFalling = false;
   }

   @Subscribe
   public void onPacketReceive(PacketReceiveEvent event) {
      long longValue = STRING_KEY ^ 127720321078327L;
      boolean flag = Breaker.a();
      if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
         AntiVoid tmp = this;
         if (flag) {
            if (this.snapshot == null) {
               return;
            }

            this.packetBlocker.unblock();
            this.snapshot = null;
            tmp = this;
         }

         tmp.suspended = true;
      }
   }

   @Override
   public void onDisable() {
      this.packetBlocker.unblock();
      super.onDisable();
   }

   static {
      try {
         long var0 = STRING_KEY ^ 116522451795972L;
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
         String var6 = "\u00ec\u00cd\u0001\u00a3\u00f0|QA$qP\u00e2\u00d6\u0011'\u00d9kw\u0098\u0010\u00bdN\u0095;\u00f22R\u00b6\u00e3e\u00db\u0086\"[\u00ea\u00d7\u00ed'\f% 9\u00d2\u001eD\u00f4\u00fb\u0000\u0010\u001f\u00e8\u0006\u0005\u00aeB<\u008bU\u00e6\u00ccA)G\u00c1\u00c5";
         int var8 = "\u00ec\u00cd\u0001\u00a3\u00f0|QA$qP\u00e2\u00d6\u0011'\u00d9kw\u0098\u0010\u00bdN\u0095;\u00f22R\u00b6\u00e3e\u00db\u0086\"[\u00ea\u00d7\u00ed'\f% 9\u00d2\u001eD\u00f4\u00fb\u0000\u0010\u001f\u00e8\u0006\u0005\u00aeB<\u008bU\u00e6\u00ccA)G\u00c1\u00c5"
            .length();
         char var5 = '0';
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
