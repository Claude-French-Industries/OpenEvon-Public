package club.evon.setting;
import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.entity.LivingEntity;
import org.json.JSONException;
import club.evon.util.KeyedRandom;
import club.evon.util.TargetSelector;




public class TargetsSetting extends BooleanGroupSetting {
   private static long DIAG_LAST;
   private static final long KEY = KeyedRandom.create(2259159214908744531L, -6142039429461866456L, MethodHandles.lookup().lookupClass()).next(235886601809483L);
   private static final String[] STRINGS;

   public TargetsSetting() {
      super((STRINGS)[5], new BooleanSetting((STRINGS)[7], true), new BooleanSetting((STRINGS)[0], true), new BooleanSetting((STRINGS)[8], true), new BooleanSetting((STRINGS)[1], true), new BooleanSetting((STRINGS)[9], true));

      String[] strings = STRINGS;
      }

   public List<LivingEntity> findTargets(double searchRange) {
      long longValue = KEY ^ 67320703206960L;
      String state = BooleanSetting.getState();
      boolean flag = false;
      boolean flag2 = false;
      boolean flag3 = false;
      boolean flag4 = false;
      boolean flag5 = false;
      BooleanSetting[] values = this.getValue();
      int length = values.length;

      for (int i = 0; i < length; i++) {
         BooleanSetting setting = values[i];
         String name = setting.getName();
         int value = -1;
         int value2 = name.hashCode();
         if (state != null) {
            if (value2 == 1171085714) {
               if (name.equals(STRINGS[4])) {
                  value = 0;
               }
            } else if (value2 == 2403731) {
               if (name.equals(STRINGS[2])) {
                  value = 1;
               }
            } else if (value2 == 807717335) {
               if (name.equals(STRINGS[10])) {
                  value = 2;
               }
            } else if (value2 == -1127935354) {
               if (name.equals(STRINGS[6])) {
                  value = 3;
               }
            } else if (value2 == 1840622001) {
               if (name.equals(STRINGS[3])) {
                  value = 4;
               }
            }

            switch (value) {
               case 0:
                  flag = setting.I();
                  break;
               case 1:
                  flag2 = setting.I();
                  break;
               case 2:
                  flag3 = setting.I();
                  break;
               case 3:
                  flag4 = setting.I();
                  break;
               case 4:
                  flag5 = setting.I();
            }
         } else {
            flag = value2 != 0;
            flag2 = setting.I();
            flag3 = setting.I();
            flag4 = setting.I();
            flag5 = setting.I();
            break;
         }
      }

      // TEMP-DIAG
      if (System.currentTimeMillis() - DIAG_LAST > 3000L) {
         DIAG_LAST = System.currentTimeMillis();
         StringBuilder sb = new StringBuilder();
         for (int d = 0; d < values.length; d++) {
            sb.append(values[d].getName()).append("[I=").append(values[d].I())
              .append(",vis=").append(values[d].isVisible()).append("] ");
         }
         System.out.println("[EVON-DIAG-TS] state=" + state + " count=" + values.length
            + " settings=" + sb + " flags=" + flag + "," + flag2 + "," + flag3 + "," + flag4 + "," + flag5);
      }
      return TargetSelector.getInstance().findTargets(flag, flag2, flag3, flag5, flag4, searchRange);
   }

   static {
      STRINGS = new String[]{
         "Mobs",
         "Invisibles",
         "Mobs",
         "Teammates",
         "Players",
         "Targets",
         "Invisibles",
         "Players",
         "Animals",
         "Teammates",
         "Animals"
      };
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
