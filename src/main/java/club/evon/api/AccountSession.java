package club.evon.api;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import net.minecraft.client.session.Session;
import club.evon.ClientContext;
import club.evon.mixins.MinecraftClientAccessor;
import club.evon.util.KeyedRandom;
import club.evon.util.math.RandomUtil;




public final class AccountSession {
   private String r;
   private String refreshToken;
   private String n;
   private String Q;
   private static final long KEY = KeyedRandom.create(-6338126182549272719L, 5955266365164259289L, MethodHandles.lookup().lookupClass()).next(224127752516094L);

   public boolean isValid() {
      long longValue = KEY ^ 70473926883011L;
      int[] values = RandomUtil.getStateArray();
      String tmp = this.n;
      if (values == null) {
         if (this.n == null) {
            return false;
         }

         tmp = this.Q;
      }

      if (values == null) {
         if (tmp == null) {
            return false;
         }

         tmp = this.n;
      }

      boolean empty = tmp.isEmpty();
      if (values == null) {
         if (empty) {
            return false;
         }

         empty = this.Q.isEmpty();
      }

      return values != null ? empty : !empty;
   }

   public void login() {
      long longValue = KEY ^ 117498909458344L;
      int[] values = RandomUtil.getStateArray();
      if (this.isValid()) {
         MinecraftClientAccessor minecraftClientAccessor = (MinecraftClientAccessor)ClientContext.MINECRAFT_CLIENT;
         String tmp = this.refreshToken;
         if (values == null) {
            if (this.refreshToken != null) {
               try {
                  MicrosoftAuthResult loginWithRefreshToken = AccountAuthService.MICROSOFT_AUTHENTICATOR.loginWithRefreshToken(this.refreshToken);
                  if (values == null) {
                     if (loginWithRefreshToken == null) {
                        return;
                     }

                     this.refreshToken = loginWithRefreshToken.getRefreshToken();
                  }

                  Session session = new Session(
                     loginWithRefreshToken.getProfile().getName(), AccountAuthService.parseUuid(loginWithRefreshToken.getProfile().getId()), loginWithRefreshToken.getAccessToken(), Optional.empty(), Optional.empty()
                  );
                  minecraftClientAccessor.setSession(session);
               } catch (Exception exception) {
               }

               return;
            }

            tmp = this.r;
         }

         if (values == null) {
            if (tmp == null) {
               Session session2 = new Session(this.Q, AccountAuthService.parseUuid(this.n), "0", Optional.empty(), Optional.empty());
               minecraftClientAccessor.setSession(session2);
               return;
            }

            tmp = this.r;
         }

         AccountSession fetchAccountSession = AccountAuthService.fetchAccountSession(tmp);
         AccountSession accountSession = fetchAccountSession;
         if (values == null) {
            if (fetchAccountSession == null) {
               return;
            }

            this.Q = fetchAccountSession.Q;
            this.n = fetchAccountSession.n;
            accountSession = this;
         }

         accountSession.r = fetchAccountSession.getToken();
         Session session3 = new Session(this.Q, AccountAuthService.parseUuid(this.n), this.r, Optional.empty(), Optional.empty());
         minecraftClientAccessor.setSession(session3);
      }
   }

   public String getToken() {
      return this.r;
   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public String getUuid() {
      return this.n;
   }

   public String getUsername() {
      return this.Q;
   }

   public void setToken(String token) {
      this.r = token;
   }

   public void setRefreshToken(String newRefreshToken) {
      this.refreshToken = newRefreshToken;
   }

   public void setUuid(String uuid) {
      this.n = uuid;
   }

   public void setUsername(String username) {
      this.Q = username;
   }

   public AccountSession(String token, String newRefreshToken, String uuid, String username) {
      this.r = token;
      this.refreshToken = newRefreshToken;
      this.n = uuid;
      this.Q = username;
   }

   private static Exception a(Exception exception) {
      return exception;
   }
}
