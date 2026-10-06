package club.evon.module.impl.motion;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.OutboundPacketEvent;
import club.evon.event.Subscribe;
import club.evon.event.UpdateVelocityEvent;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.util.KeyedRandom;




public final class Stuck extends BaseModule {
   private static final long STRING_KEY = KeyedRandom.create(-8258928436553966593L, -1961409655545599805L, MethodHandles.lookup().lookupClass()).next(61438152218868L);
   private static final String[] STRINGS;

   public Stuck() {
      super((STRINGS)[0], (STRINGS)[1], ModuleCategory.MOTION);

      String[] strings = STRINGS;
      }

   @Subscribe
   private void onUpdateVelocity(UpdateVelocityEvent event) {
      ClientContext.MINECRAFT_CLIENT.player.setVelocity(Vec3d.ZERO);
   }

   @Subscribe
   private void onOutboundPacket(OutboundPacketEvent event) {
      long longValue = STRING_KEY ^ 103603031193993L;
      BaseModule[] values = Scaffold.z();
      OutboundPacketEvent tmp = event;
      if (values == null) {
         if (!(event.getPacket() instanceof PlayerMoveC2SPacket)) {
            return;
         }

         tmp = event;
      }

      tmp.cancel();
   }

   static {
      try {
      long var0 = STRING_KEY ^ 130690171100156L;
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
      String var6 = "c\u00b0\u0004\u000bW\u009b\u007f\f\u0010\u0097\u00d3\u001d\u0005\u008e\u00fa\u00d7\u0093\u0099-\u00b9\u00a5\u0092\u0095\u00a9\u0082";
      int var8 = "c\u00b0\u0004\u000bW\u009b\u007f\f\u0010\u0097\u00d3\u001d\u0005\u008e\u00fa\u00d7\u0093\u0099-\u00b9\u00a5\u0092\u0095\u00a9\u0082".length();
      char var5 = '\b';
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
