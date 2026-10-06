package club.evon.data;
import java.lang.invoke.MethodHandles;
import net.hypixel.data.type.ServerType;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import club.evon.HypixelTargetFilter;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public record HypixelLocation(String serverName, @Nullable ServerType serverType, @Nullable String lobbyName, @Nullable String mode, @Nullable String map) {
   private static final long a = KeyedRandom.create(-6473258005651327509L, 4095316299414946426L, MethodHandles.lookup().lookupClass()).next(24991682417778L);

   public HypixelLocation(String serverName, @Nullable ServerType serverType, @Nullable String lobbyName, @Nullable String mode, @Nullable String map) {
      long longValue = a ^ 13302994118126L;
      int value = HypixelTargetFilter.e();
      this.serverName = serverName;
      this.serverType = serverType;
      int value2 = value;
      this.lobbyName = lobbyName;
      this.mode = mode;
      this.map = map;
      if (value2 == 0) {
         BaseModule.setStateArray(new BaseModule[4]);
      }
      }

   public boolean hasLobby() {
      long longValue = a ^ 120969852679003L;
      return this.lobbyName != null;
   }

   public String getServerName() {
      return this.serverName;
   }

   @Nullable
   public ServerType getServerType() {
      return this.serverType;
   }

   @Nullable
   public String getLobbyName() {
      return this.lobbyName;
   }

   @Nullable
   public String getMode() {
      return this.mode;
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
