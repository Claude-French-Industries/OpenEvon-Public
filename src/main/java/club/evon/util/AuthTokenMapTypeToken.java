package club.evon.util;
import com.google.common.reflect.TypeToken;
import java.util.Map;
import club.evon.api.AccountAuthService;




public class AuthTokenMapTypeToken extends TypeToken<Map<String, Object>> {
   public final AccountAuthService authService;

   public AuthTokenMapTypeToken(AccountAuthService accountAuthService) {
      this.authService = accountAuthService;
   }
}
