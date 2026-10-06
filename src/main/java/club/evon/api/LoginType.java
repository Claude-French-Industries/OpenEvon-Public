package club.evon.api;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.config.AccountConfig;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public enum LoginType {
   COOKIE(0),
   MICROSOFT(1),
   TOKEN(2);

   private final int z;
   private static final long KEY = KeyedRandom.create(2232692036552261504L, -1231381566105632913L, MethodHandles.lookup().lookupClass()).next(134329616971700L);

   private LoginType(int n2) {
      this.z = n2;
   }

   public static LoginType fromLevel(int level) {
      LoginType yg_c22;
      block4: {
         long l = KEY ^ 0x64B62EDABFE1L;
         LoginType[] yg_c2Array = LoginType.values();
         int n = yg_c2Array.length;
         String[] stringArray = AccountConfig.getStateArray();
         int n2 = 0;
         while (n2 < n) {
            LoginType yg_c23 = yg_c2Array[n2];
            if (stringArray != null) {
               yg_c22 = yg_c23;
               if (stringArray == null) break block4;
               if (yg_c22.z == level) return yg_c23;
               ++n2;
            }
            if (stringArray != null) continue;
            BaseModule.setStateArray(new BaseModule[3]);
            break;
         }
         yg_c22 = MICROSOFT;
      }
      return yg_c22;
   }

   public int getLevel() {
      return this.z;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
