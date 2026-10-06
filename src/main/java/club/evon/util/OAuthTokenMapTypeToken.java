package club.evon.util;
import com.google.common.reflect.TypeToken;
import java.util.Map;
import club.evon.api.AccountAuthService;




public class OAuthTokenMapTypeToken extends TypeToken<Map<String, Object>> {
   public final AccountAuthService authService;

   public OAuthTokenMapTypeToken(AccountAuthService accountAuthService) {
      this.authService = accountAuthService;
   }
}
