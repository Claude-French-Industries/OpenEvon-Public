package club.evon.api;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.HypixelTargetFilter;
import club.evon.event.ClientTickEvent;
import club.evon.event.PacketReceiveEvent;
import club.evon.event.Subscribe;
import club.evon.manager.ClientState;
import club.evon.util.KeyedRandom;




public final class LiquidProxyDetector extends BrandDetector {
   private static boolean enabled;
   private static final long KEY = KeyedRandom.create(1459823261009376499L, -5857226005226035464L, MethodHandles.lookup().lookupClass()).next(143200481522442L);
   private static final String[] STRINGS;

   public LiquidProxyDetector() {
      super(STRINGS[1]);
   }

   @Subscribe
   private void onPacketReceive(PacketReceiveEvent event) {
      long longValue = KEY ^ 92574091517265L;
      boolean disabled = isDisabled();
      Packet packet = event.getPacket();
      boolean flag = disabled;
      Packet packet2 = packet;
      if (!flag) {
         if (!(packet instanceof PlayerListHeaderS2CPacket)) {
            return;
         }

         packet2 = packet;
      }

      PlayerListHeaderS2CPacket player = (PlayerListHeaderS2CPacket)packet2;
      if (player.header().getString().contains(STRINGS[0])) {
         ClientPlayNetworkHandler networkHandler = ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
         if (!flag) {
            if (networkHandler == null) {
               return;
            }

            networkHandler = ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
         }

         String brand = networkHandler.getBrand();
         String text = brand;
         if (!flag) {
            if (brand == null) {
               return;
            }

            text = brand;
         }

         if (text.contains(STRINGS[2])) {
            CubecraftServer cubecraftServer = new CubecraftServer();
            cubecraftServer.setBrandDetector(this);
            ClientState.getInstance().getServerDetector().setServerProfile(cubecraftServer);
         }
      }
   }

   @Subscribe
   private void onTick(ClientTickEvent event) {
      long longValue = KEY ^ 11964551710328L;
      boolean disabled = isDisabled();
      ClientPlayNetworkHandler networkHandler = ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
      if (!disabled) {
         if (networkHandler == null) {
            return;
         }

         networkHandler = ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
      }

      String brand = networkHandler.getBrand();
      if (brand != null && HypixelTargetFilter.HYPIXEL_BRAND_PATTERN.matcher(brand).matches()) {
         HypixelTargetFilter hypixelTargetFilter = new HypixelTargetFilter();
         hypixelTargetFilter.setBrandDetector(this);
         ClientState.getInstance().getServerDetector().setServerProfile(hypixelTargetFilter);
      }
   }

   public static void setEnabled(boolean flag) {
      enabled = flag;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
   }

   static {
      try {
      long var0 = KEY ^ 45163255153834L;
      setEnabled(true);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[3];
      int var7 = 0;
      String var6 = "'j\u00e3\u0002\u00e0\u00ba\u00bc\u0084\u00e5\u00f7\\\u00bbd\u00d7-\u00ae\u0010qv\u0086\u00f2\u00e4-\u0016\u0089\u001c\u009eg\u00aeJ\u00f5e\u00a1\u0010\u0002+V\u00d14i\u0085\u0096\u001a/\u0001i6\u00c1B\u0096";
      int var8 = "'j\u00e3\u0002\u00e0\u00ba\u00bc\u0084\u00e5\u00f7\\\u00bbd\u00d7-\u00ae\u0010qv\u0086\u00f2\u00e4-\u0016\u0089\u001c\u009eg\u00aeJ\u00f5e\u00a1\u0010\u0002+V\u00d14i\u0085\u0096\u001a/\u0001i6\u00c1B\u0096"
         .length();
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
