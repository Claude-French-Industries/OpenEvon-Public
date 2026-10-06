package club.evon.util.render;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.lwjgl.nanovg.NanoVG;
import club.evon.util.KeyedRandom;




public final class NvgImage {
   private final int imageHandle;
   private static String stateString;
   private static final long KEY = KeyedRandom.create(-4843057052595714381L, 8829702774997097026L, MethodHandles.lookup().lookupClass()).next(266379547031351L);
   private static final String[] STRINGS_TABLE;

   public NvgImage(InputStream inputStream) {
      super();

      long longValue = KEY ^ 33159825989620L;
      if (inputStream == null) {
         String[] strings = STRINGS_TABLE;
         throw new RuntimeException(strings[2]);
      }

      try {
         byte[] bytes = inputStream.readAllBytes();
         ByteBuffer put = ByteBuffer.allocateDirect(bytes.length).put(bytes);
         put.flip();
         this.imageHandle = NanoVG.nvgCreateImageMem(RenderUtil.NVG_CONTEXT, 32, put);
         if (this.imageHandle == 0) {
            throw new RuntimeException(STRINGS_TABLE[0]);
         }

         inputStream.close();
      } catch (IOException iOException) {
         throw new RuntimeException(iOException);
      }
      }

   public NvgImage(String filePath) {
      this(NvgImage.class.getResourceAsStream(STRINGS_TABLE[1] + filePath));
   }

   public int getHandle() {
      return this.imageHandle;
   }

   public static void setStateString(String value) {
      stateString = value;
   }

   public static String getStateString() {
      return stateString;
   }

   static {
      try {
      long var0 = KEY ^ 54838136132040L;
      setStateString("q62GCb");
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
      String var6 = "g\u00f4d\u0002>\u00c63W\u00c3S?iN\u0083\u0083\u00a29b;\u00d4E\u008e\u00d6\u0010\u0018\u0087\u00a6\u00df_*\u00cc\u008e[y*\u00e2\u00bd\u00fdO\u009e/\u0084$\u00c8o\u009c\u00e2\u001a\u00e2\u0018\u00e7\u008e4\u0099\u00ff\u00bb\u00f8\u00bf\u00f3jT\u000b\u009a/\u00a0\u0080A\u00f6\u00d5 \u00b4X\u00be7";
      int var8 = "g\u00f4d\u0002>\u00c63W\u00c3S?iN\u0083\u0083\u00a29b;\u00d4E\u008e\u00d6\u0010\u0018\u0087\u00a6\u00df_*\u00cc\u008e[y*\u00e2\u00bd\u00fdO\u009e/\u0084$\u00c8o\u009c\u00e2\u001a\u00e2\u0018\u00e7\u008e4\u0099\u00ff\u00bb\u00f8\u00bf\u00f3jT\u000b\u009a/\u00a0\u0080A\u00f6\u00d5 \u00b4X\u00be7"
         .length();
      char var5 = 24;
      int var4 = -1;

      while (true) {
         byte[] var10 = var2.doFinal(var6.substring(++var4, var4 + var5).getBytes("ISO-8859-1"));
         String var13 = decodeUtf8(var10).intern();
         byte var10001 = -1;
         var9[var7++] = var13;
         if ((var4 += var5) >= var8) {
            STRINGS_TABLE = var9;
            break;
         }

         var5 = var6.charAt(var4);
      }
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static IOException rethrow(IOException exception) {
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
