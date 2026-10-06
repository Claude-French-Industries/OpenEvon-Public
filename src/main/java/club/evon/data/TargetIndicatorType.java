package club.evon.data;
import java.awt.Color;
import java.lang.invoke.MethodHandles;
import java.util.function.Predicate;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.ItemTags;
import org.json.JSONException;
import club.evon.module.impl.render.Animations;
import club.evon.util.KeyedRandom;
import club.evon.util.render.NvgImage;




public enum TargetIndicatorType {
   BLOCKING(new Color(0, 140, 255, 63), TargetIndicatorType::isBlocking, new NvgImage("blocking.png")),
   STRENGTH(new Color(255, 0, 0, 63), TargetIndicatorType::hasStrengthEffect, new NvgImage("strength.png"));

   public final Color color;
   public final Predicate<LivingEntity> predicate;
   public final NvgImage image;
   private static final TargetIndicatorType[] VALUES;
   private static final long KEY = KeyedRandom.create(5953673954247662186L, -951204702426748759L, MethodHandles.lookup().lookupClass()).next(50098992758461L);

   TargetIndicatorType(Color color2, Predicate<LivingEntity> entity, NvgImage nvgImage) {
      this.color = color2;
      this.predicate = entity;
      this.image = nvgImage;
   }

   private static boolean hasStrengthEffect(LivingEntity entity) {
      return entity.hasStatusEffect(StatusEffects.STRENGTH);
   }

   private static boolean isBlocking(LivingEntity entity) {
      long longValue = KEY ^ 20103508967127L;
      boolean flagUnset = Animations.isFlagUnset();
      LivingEntity tmp = entity;
      if (flagUnset) {
         if (entity == null) {
            return false;
         }

         tmp = entity;
      }

      boolean usingItem = tmp.isUsingItem();
      if (flagUnset) {
         if (!usingItem) {
            return false;
         }

         usingItem = entity.getMainHandStack().isIn(ItemTags.SWORDS);
      }

      return !flagUnset ? usingItem : usingItem;
   }

   private static TargetIndicatorType[] buildValues() {
      return new TargetIndicatorType[]{BLOCKING, STRENGTH};
   }

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      try {
      long var1 = KEY ^ 48760169011935L;
      Cipher var3;
      Cipher var10000 = var3 = Cipher.getInstance("DES/CBC/PKCS5Padding");
      SecretKeyFactory var10002 = SecretKeyFactory.getInstance("DES");
      byte[] var10003 = new byte[]{(byte)(var1 >>> 56), 0, 0, 0, 0, 0, 0, 0};

      for (int var4 = 1; var4 < 8; var4++) {
         var10003[var4] = (byte)(var1 << var4 * 8 >>> 56);
      }

      var10000.init(2, var10002.generateSecret(new DESKeySpec(var10003)), new IvParameterSpec(new byte[8]));
      String[] var0 = new String[4];
      int var8 = 0;
      String var7 = "\u00bf)\u0097+\u00ad\u0091\u00d3T\u00c1\u008bc|\u0005\u00ba\u00cd\u0083\u0010\u00b1A\u00e4\u00eb\u00dd\u0086\u00fa\u00bf!\u0006\u0088\u0085\u0016r\u008e)";
      int var9 = "\u00bf)\u0097+\u00ad\u0091\u00d3T\u00c1\u008bc|\u0005\u00ba\u00cd\u0083\u0010\u00b1A\u00e4\u00eb\u00dd\u0086\u00fa\u00bf!\u0006\u0088\u0085\u0016r\u008e)"
         .length();
      char var6 = 16;
      int var12 = -1;

      label28:
      while (true) {
         String var13 = var7.substring(++var12, var12 + var6);
         byte var10001 = -1;

         while (true) {
            byte[] var10 = var3.doFinal(var13.getBytes("ISO-8859-1"));
            String var19 = decodeUtf8(var10).intern();
            switch (var10001) {
               case 0:
                  var0[var8++] = var19;
                  if ((var12 += var6) >= var9) {
                     break label28;
                  }

                  var6 = var7.charAt(var12);
                  break;
               default:
                  var0[var8++] = var19;
                  if ((var12 += var6) < var9) {
                     var6 = var7.charAt(var12);
                     continue label28;
                  }

                  var7 = "#\u00d6\u00cb\u0014\u0080G\u00a9\u00006M\u008eH`;\u00d8\u00a7\u0010\u00aa&R\u00dd\u00da\u0004\u00d5\u00e4\u001e\r\u001b\u00b52\u00cb\u00a6\u009c";
                  var9 = "#\u00d6\u00cb\u0014\u0080G\u00a9\u00006M\u008eH`;\u00d8\u00a7\u0010\u00aa&R\u00dd\u00da\u0004\u00d5\u00e4\u001e\r\u001b\u00b52\u00cb\u00a6\u009c"
                     .length();
                  var6 = 16;
                  var12 = -1;
            }

            var13 = var7.substring(++var12, var12 + var6);
            var10001 = 0;
         }
      }
      VALUES = buildValues();
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
