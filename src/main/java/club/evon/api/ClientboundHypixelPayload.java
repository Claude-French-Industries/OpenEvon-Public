package club.evon.api;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.error.ErrorReason;
import net.hypixel.modapi.packet.ClientboundHypixelPacket;
import net.hypixel.modapi.serializer.PacketSerializer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.CustomPayload.Id;
import club.evon.util.KeyedRandom;




public class ClientboundHypixelPayload implements CustomPayload {
   private final Id<ClientboundHypixelPayload> X;
   private ClientboundHypixelPacket packet;
   private ErrorReason errorReason;
   private static int[] o;
   private static final long KEY = KeyedRandom.create(-2813459727749272163L, 2805387817495384559L, MethodHandles.lookup().lookupClass()).next(28739829419961L);
   private static final String WRITE_ERROR_MESSAGE;

   private ClientboundHypixelPayload(Id<ClientboundHypixelPayload> idClientboundHypixelPayload, PacketByteBuf buffer) {
      super();

      long longValue = KEY ^ 82077042059372L;
      this.X = idClientboundHypixelPayload;
      PacketSerializer packetSerializer = new PacketSerializer(buffer);
      int[] values = t();
      boolean flag = packetSerializer.readBoolean();
      int[] values2 = values;
      if (values2 == null) {
         if (!flag) {
            this.errorReason = ErrorReason.getById(packetSerializer.readVarInt());
            return;
         }

         this.packet = HypixelModAPI.getInstance().getRegistry().createClientboundPacket(idClientboundHypixelPayload.id().toString(), packetSerializer);
      }
      }

   public Id<? extends CustomPayload> getId() {
      return this.X;
   }

   public boolean hasPacket() {
      long longValue = KEY ^ 76387641436069L;
      return this.packet != null;
   }

   public ClientboundHypixelPacket getPacket() {
      return this.packet;
   }

   public ErrorReason getErrorReason() {
      return this.errorReason;
   }

   public static PacketCodec<PacketByteBuf, ClientboundHypixelPayload> createCodec(Id<ClientboundHypixelPayload> id) {
      return CustomPayload.codecOf((clientboundHypixelPayload, packet2) -> {
         throw new UnsupportedOperationException(WRITE_ERROR_MESSAGE);
      }, packet3 -> new ClientboundHypixelPayload(id, packet3));
   }

   public static void y(int[] values) {
      o = values;
   }

   public static int[] t() {
      return o;
   }

   static {
      try {
      long var0 = KEY ^ 86847073029309L;
      y(null);
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var4 = var2.doFinal(
         "\u00fdH\u00dd0Y\u0018r#\u00e1\t\u00ca\u0003\u008f\u00d4\b\u00ad\u0014\u00ee\u0004\u0080\u00ee\u00fe\u00ceE\u008f4A\u000b\u0003.R8\u00d0y\u009e_\u00a1\u00fa\u0002\u00d0"
            .getBytes("ISO-8859-1")
      );
      String var5 = decodeUtf8(var4).intern();
      byte var10001 = -1;
      WRITE_ERROR_MESSAGE = var5;
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
      return unsupportedOperationException;
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
