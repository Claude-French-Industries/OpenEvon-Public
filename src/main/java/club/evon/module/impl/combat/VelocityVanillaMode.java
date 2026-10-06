package club.evon.module.impl.combat;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.module.SubMode;
import club.evon.setting.DoubleSetting;
import club.evon.util.KeyedRandom;




public final class VelocityVanillaMode extends SubMode<Velocity> {
   private final DoubleSetting horizontal;
   private final DoubleSetting vertical;
   private static final long KEY = KeyedRandom.create(-7164705671545325393L, -5859146186701934495L, MethodHandles.lookup().lookupClass()).next(130445786260074L);
   private static final String[] STRINGS_TABLE;

   public VelocityVanillaMode(String name, Velocity parent) {
      super(name, parent);
      String[] strings = STRINGS_TABLE;
      this.horizontal = new DoubleSetting(strings[0], 0.0, 0.0, 100.0, 1.0);
      this.vertical = new DoubleSetting(strings[1], 0.0, 0.0, 100.0, 1.0);
      this.addSettings(this.horizontal, this.vertical);
   }

   @Subscribe
   private void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 49001706399194L;
      String stateString = VelocityHypixelMode.getStateString();
      Packet packet = event.getPacket();
      Packet packet2 = packet;
      if (stateString == null) {
         if (!(packet instanceof EntityVelocityUpdateS2CPacket)) {
            return;
         }

         packet2 = packet;
      }

      EntityVelocityUpdateS2CPacket packet3 = (EntityVelocityUpdateS2CPacket)packet2;
      boolean shouldCancel = this.getParent().shouldCancel();
      if (stateString == null) {
         if (shouldCancel) {
            return;
         }

         if (packet3.getEntityId() != ClientContext.MINECRAFT_CLIENT.player.getId()) {
            return;
         }
      } else if ((shouldCancel ? 1 : 0) != ClientContext.MINECRAFT_CLIENT.player.getId()) {
         return;
      }

      double doubleValue = (Double)this.horizontal.J() / 100.0;
      double doubleValue2 = this.vertical.J() / 100.0;
      ClientContext.MINECRAFT_CLIENT.player.setVelocity(packet3.getVelocity().multiply(doubleValue, doubleValue2, doubleValue));
      event.cancel();
   }

   static {
      try {
      long var0 = KEY ^ 80673762059193L;
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
      String var6 = "\u00f2\u00d57\u00e8q\u0016U\u0090\u00b3!n\u00de'\u0087\u00f08\u00105u\u00aa~\u00e2U\u00ac\u00fee3 \u00f3\u008a\u00e6\u00d7\u00a5";
      int var8 = "\u00f2\u00d57\u00e8q\u0016U\u0090\u00b3!n\u00de'\u0087\u00f08\u00105u\u00aa~\u00e2U\u00ac\u00fee3 \u00f3\u008a\u00e6\u00d7\u00a5".length();
      char var5 = 16;
      int var4 = -1;

      label28:
      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var13 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var13;
         if ((var4 += var5) >= var8) {
            STRINGS_TABLE = var9;
            break label28;
         }

         var5 = var6.charAt(var4);
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
