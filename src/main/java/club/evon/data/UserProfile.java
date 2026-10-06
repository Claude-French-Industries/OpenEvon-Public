package club.evon.data;
import java.lang.invoke.MethodHandles;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public class UserProfile {
   private final String D;
   private final UserRank rank;
   private final String j;
   private static final long KEY = KeyedRandom.create(-1092301870276301063L, -8789041546116183542L, MethodHandles.lookup().lookupClass()).next(232930658297196L);

   public UserProfile(String username, int level, String minecraftUsername) {
      super();

      long longValue = KEY ^ 94739123229640L;
      String state = UserRank.getState();
      String text = state;
      this.D = username;
      this.rank = UserRank.fromLevel(level);
      this.j = minecraftUsername;
      if (text == null) {
         BaseModule.setStateArray(new BaseModule[3]);
      }
      }

   public String getUsername() {
      return this.D;
   }

   public UserRank getRank() {
      return this.rank;
   }

   public String getMinecraftUsername() {
      return this.j;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
