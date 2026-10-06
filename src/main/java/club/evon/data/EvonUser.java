package club.evon.data;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class EvonUser {
   private final String H;
   private final String G;
   private final UserRank u;
   private static String[] strings;
   private static final long a = KeyedRandom.create(8974397902692560669L, 8390850361822759061L, MethodHandles.lookup().lookupClass()).next(173042132475113L);

   public EvonUser(String username, String uid, UserRank role) {
      super();

      long longValue = a ^ 39112842803808L;
      String[] strings2 = getStrings();
      this.H = username;
      String[] strings3 = strings2;
      this.G = uid;
      this.u = role;
      if (strings3 == null) {
         BaseModule.setStateArray(new BaseModule[1]);
      }
      }

   public String getUsername() {
      return this.H;
   }

   public String getUid() {
      return this.G;
   }

   public UserRank getRole() {
      return this.u;
   }

   public static void setStrings(String[] values) {
      strings = values;
   }

   public static String[] getStrings() {
      return strings;
   }

   static {
      if (getStrings() == null) {
         setStrings(new String[2]);
      }
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
