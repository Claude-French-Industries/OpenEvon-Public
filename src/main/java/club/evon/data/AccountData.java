package club.evon.data;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.util.KeyedRandom;
import club.evon.util.math.RandomUtil;




public final class AccountData {
   private String W;
   private String refreshToken;
   private String D;
   private String A;
   private static final long a = KeyedRandom.create(-1976397807390963010L, -6418018557032102150L, MethodHandles.lookup().lookupClass()).next(71426390917945L);

   public AccountData() {
   }

   public boolean hasSession() {
      long longValue = a ^ 124302435471668L;
      int[] values = RandomUtil.getStateArray();
      String tmp = this.D;
      if (values == null) {
         if (this.D == null) {
            return false;
         }

         tmp = this.A;
      }

      if (values == null) {
         if (tmp == null) {
            return false;
         }

         tmp = this.D;
      }

      boolean empty = tmp.isEmpty();
      if (values == null) {
         if (empty) {
            return false;
         }

         empty = this.A.isEmpty();
      }

      return values != null ? empty : !empty;
   }

   public String getToken() {
      return this.W;
   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public String getUuid() {
      return this.D;
   }

   public String getUsername() {
      return this.A;
   }

   public void setToken(String token) {
      this.W = token;
   }

   public void setRefreshToken(String newRefreshToken) {
      this.refreshToken = newRefreshToken;
   }

   public void setUuid(String uuid) {
      this.D = uuid;
   }

   public void setUsername(String username) {
      this.A = username;
   }

   public AccountData(String token, String newRefreshToken, String uuid, String username) {
      this.W = token;
      this.refreshToken = newRefreshToken;
      this.D = uuid;
      this.A = username;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
