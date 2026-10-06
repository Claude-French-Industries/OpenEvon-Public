package club.evon.setting;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.util.math.Vec2f;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class PositionSetting extends Setting<Vec2f> {
   private float offsetX;
   private float offsetY;
   private float G;
   private float q;
   private float H;
   private boolean v;
   private static final long KEY = KeyedRandom.create(2854026400486925218L, -2097415116328974556L, MethodHandles.lookup().lookupClass()).next(91122769388675L);
   private static final String DEFAULT_NAME;

   public PositionSetting(float x, float y) {
      super(DEFAULT_NAME, new Vec2f(x, y));
   }

   public float getOffsetX() {
      return this.offsetX * ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth();
   }

   public float getOffsetY() {
      return this.offsetY * ClientContext.MINECRAFT_CLIENT.getWindow().getScaledHeight();
   }

   public float getX() {
      return this.getValue().x * ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth();
   }

   public float getY() {
      return this.getValue().y * ClientContext.MINECRAFT_CLIENT.getWindow().getScaledHeight();
   }

   public void beginDrag(float mouseX, float mouseY, float realX, float realY) {
      long longValue = KEY ^ 106320141059663L;
      BooleanSetting.getState();
      this.offsetX = (mouseX - realX) / ClientContext.MINECRAFT_CLIENT.getWindow().getScaledWidth();
      this.offsetY = (mouseY - realY) / ClientContext.MINECRAFT_CLIENT.getWindow().getScaledHeight();
      if (BaseModule.getStateArray() == null) {
         BooleanSetting.setState("du6Kn");
      }
   }

   public float getWidth() {
      return this.G;
   }

   public float getHeight() {
      return this.q;
   }

   public float getRound() {
      return this.H;
   }

   public boolean isDragging() {
      return this.v;
   }

   public void setOffsetX(float dragX) {
      this.offsetX = dragX;
   }

   public void setOffsetY(float dragY) {
      this.offsetY = dragY;
   }

   public void setWidth(float width) {
      this.G = width;
   }

   public void setHeight(float height) {
      this.q = height;
   }

   public void setRound(float round) {
      this.H = round;
   }

   public void setDragging(boolean dragging) {
      this.v = dragging;
   }

   static {
      try {
      long var0 = KEY ^ 45906994331981L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      byte[] var4 = var2.doFinal("\r\u00f3\u008d3dvm4qJ\u0092C\u000f\r\u00b8u".getBytes("ISO-8859-1"));
      String var5 = decodeUtf8(var4).intern();
      byte var10001 = -1;
      DEFAULT_NAME = var5;
   
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
