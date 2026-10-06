package club.evon.api;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.hypixel.modapi.packet.HypixelPacket;
import net.hypixel.modapi.serializer.PacketSerializer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.CustomPayload.Id;
import net.minecraft.util.Identifier;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public class ServerboundHypixelPayload implements CustomPayload {
   private final Id<ServerboundHypixelPayload> m;
   private final HypixelPacket T;
   private static int state;
   private static final long STRING_SEED = KeyedRandom.create(20790114908014660L, 907453162469052850L, MethodHandles.lookup().lookupClass()).next(258806447308846L);
   private static final String ERROR_MESSAGE;

   public ServerboundHypixelPayload(HypixelPacket packet) {
      super();

      long longValue = STRING_SEED ^ 18096136918215L;
      int value = getDefaultState();
      int value2 = value;
      this.m = new Id(Identifier.of(packet.getIdentifier()));
      this.T = packet;
      if (value2 != 0) {
         BaseModule.setStateArray(new BaseModule[2]);
      }
      }

   private void writePacket(PacketByteBuf buf) {
      PacketSerializer packetSerializer = new PacketSerializer(buf);
      this.T.write(packetSerializer);
   }

   public Id<? extends CustomPayload> getId() {
      return this.m;
   }

   public static PacketCodec<PacketByteBuf, ServerboundHypixelPayload> createCodec(Id<ServerboundHypixelPayload> id) {
      return CustomPayload.codecOf(ServerboundHypixelPayload::writePacket, packet -> {
         throw new UnsupportedOperationException(ERROR_MESSAGE);
      });
   }

   public static void setState(int value) {
      state = value;
   }

   public static int getState() {
      return state;
   }

   public static int getDefaultState() {
      int value = getState();
      return value == 0 ? 78 : 0;
   }

   static {
      try {
      long var0 = STRING_SEED ^ 59094946872866L;
      setState(124);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var4 = var2.doFinal(
         "\u00fd\u008b\u00be\u0000\u00f8\u0095\"sOV\u00a4?s\u0005\u00cb\u009dyG\u00c1\u0090\u00c41T\u009c\u009f\u00d2\u008a#_\u00be\u00844\u00d1\u00cfw\u0084:\u0004?`"
            .getBytes("ISO-8859-1")
      );
      String var5 = decryptString(var4).intern();
      byte var10001 = -1;
      ERROR_MESSAGE = var5;
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static UnsupportedOperationException passThrough(UnsupportedOperationException exception) {
      return exception;
   }

   private static String decryptString(byte[] data) {
      int index = 0;
      int value;
      char[] chars = new char[value = data.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & data[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = data[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = data[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
