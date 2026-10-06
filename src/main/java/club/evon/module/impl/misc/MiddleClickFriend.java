package club.evon.module.impl.misc;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.event.MouseButtonEvent;
import club.evon.event.Subscribe;
import club.evon.manager.FriendManager;
import club.evon.module.BaseModule;
import club.evon.module.ModuleCategory;
import club.evon.util.ChatUtil;
import club.evon.util.KeyedRandom;




public final class MiddleClickFriend extends BaseModule {
   private static final long KEY = KeyedRandom.create(4653593943535021586L, -8334872467472975987L, MethodHandles.lookup().lookupClass()).next(120489274359079L);
   private static final String[] STRINGS;

   public MiddleClickFriend() {
      super((STRINGS)[3], (STRINGS)[2], ModuleCategory.MISC);

      String[] strings = STRINGS;
      }

   @Subscribe
   private void onMouseButton(MouseButtonEvent event) {
      long longValue = KEY ^ 48737922396565L;
      int value = Streamer.getState();
      if (event.getInteractionCode() == 2) {
         MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
         if (value == 0) {
            if (ClientContext.MINECRAFT_CLIENT.player == null) {
               return;
            }

            tmp = ClientContext.MINECRAFT_CLIENT;
         }

         HitResult hitResult = tmp.crosshairTarget;
         HitResult hitResult2 = hitResult;
         if (value == 0) {
            if (!(hitResult instanceof EntityHitResult)) {
               return;
            }

            hitResult2 = hitResult;
         }

         EntityHitResult entity = (EntityHitResult)hitResult2;
         Entity entity2 = entity.getEntity();
         Entity entity3 = entity2;
         if (value == 0) {
            if (!(entity2 instanceof PlayerEntity)) {
               return;
            }

            entity3 = entity2;
         }

         PlayerEntity player = (PlayerEntity)entity3;
         FriendManager instance = FriendManager.getInstance();
         String string = player.getName().getString();
         FriendManager friendManager = instance;
         String tmp2 = string;
         if (value == 0) {
            if (instance.isFriend(string)) {
               instance.removeFriend(string);
               ChatUtil.sendMessage(STRINGS[4] + string + STRINGS[1]);
               if (value == 0) {
                  return;
               }
            }

            friendManager = instance;
            tmp2 = string;
         }

         friendManager.addFriend(tmp2);
         String[] strings = STRINGS;
         ChatUtil.sendMessage(strings[0] + string + strings[5]);
      }
   }

   static {
      try {
      long var0 = KEY ^ 5786400666931L;
      Cipher var2;
      Cipher var10000 = var2 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var0 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var3 = 1; var3 < 8; var3++) {
         var10003[var3] = (byte)(var0 << var3 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var9 = new String[6];
      int var7 = 0;
      String var6 = "\u008e\u009b9\u0091\u00b0j\u0017'\u0018\u00c4\u00bf\u009b\u0087\u0098\b\u00e5\r\u0002\u00c5\u00e7\u00ac\u008diA\u00f2\u00b9@\u007f\u000b\u0088\u009f\u001d*\u0018\u0084\u00bae\u00dak01\u00a7\u0005_\u0010:kg\u00fb\u00e0\ni\u00ba.\u008b\u00ad\u00f71\u0018\u0084\u00bae\u00dak01\u00a7\u0005_\u0010:kg\u00fb\u00e0\u00ba\u00c7)\u00bd\t\u00c5\nc";
      int var8 = "\u008e\u009b9\u0091\u00b0j\u0017'\u0018\u00c4\u00bf\u009b\u0087\u0098\b\u00e5\r\u0002\u00c5\u00e7\u00ac\u008diA\u00f2\u00b9@\u007f\u000b\u0088\u009f\u001d*\u0018\u0084\u00bae\u00dak01\u00a7\u0005_\u0010:kg\u00fb\u00e0\ni\u00ba.\u008b\u00ad\u00f71\u0018\u0084\u00bae\u00dak01\u00a7\u0005_\u0010:kg\u00fb\u00e0\u00ba\u00c7)\u00bd\t\u00c5\nc"
         .length();
      char var5 = '\b';
      int var12 = -1;

      label27:
      while (true) {
         String var13 = var6.substring(++var12, var12 + var5);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var2.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var9[var7++] = var19;
                  if ((var12 += var5) >= var8) {
                     STRINGS = var9;
                     break label27;
                  }

                  var5 = var6.charAt(var12);
                  break;
               default:
                  var9[var7++] = var19;
                  if ((var12 += var5) < var8) {
                     var5 = var6.charAt(var12);
                     continue label27;
                  }

                  var6 = "`\u00ab\u0007\u0086\u00e43\u00b7\u00c762P\u00ab\u00c5b\r\u00ac\u0018\u000f\u00be\u0013j\u00cc\u001c\u00912Tt)\u00f7\u00d1\u00bc\u0015r\tzp\r6|}\u009f";
                  var8 = "`\u00ab\u0007\u0086\u00e43\u00b7\u00c762P\u00ab\u00c5b\r\u00ac\u0018\u000f\u00be\u0013j\u00cc\u001c\u00912Tt)\u00f7\u00d1\u00bc\u0015r\tzp\r6|}\u009f"
                     .length();
                  var5 = 16;
                  var12 = -1;
            }

            var13 = var6.substring(++var12, var12 + var5);
            var10001 = 0;
         }
      }
   
      } catch (Exception var100) {
         throw new ExceptionInInitializerError(var100);
      }
   }

   private static JSONException rethrow(JSONException exception) {
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
