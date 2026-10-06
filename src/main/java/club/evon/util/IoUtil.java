package club.evon.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import club.evon.util.math.RandomUtil;




public final class IoUtil {
   private static final long KEY = KeyedRandom.create(2607090969562647511L, 3462499457209881754L, MethodHandles.lookup().lookupClass()).next(189614994980368L);

   public static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
      return BufferUtils.createByteBuffer(newCapacity).flip().put(buffer);
   }

   public static ByteBuffer readToBuffer(InputStream stream) {
      try {
         byte[] bytes = stream.readAllBytes();
         stream.close();
         ByteBuffer allocateDirect = ByteBuffer.allocateDirect(bytes.length);
         allocateDirect.put(bytes);
         allocateDirect.flip();
         return allocateDirect;
      } catch (IOException iOException) {
         throw new RuntimeException(iOException);
      }
   }

   public static String readString(InputStream inputStream) {
      long longValue = KEY ^ 85453689622746L;
      int[] values = RandomUtil.getStateArray();
      StringBuilder builder = new StringBuilder();
      int[] values2 = values;

      try {
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

         String text;
         while ((text = bufferedReader.readLine()) != null) {
            builder.append(text).append(System.lineSeparator());
            if (values2 != null || values2 != null) {
               break;
            }
         }
      } catch (Exception exception) {
         exception.fillInStackTrace();
      }

      return builder.toString();
   }

   private static Exception rethrow(Exception exception) {
      return exception;
   }
}
