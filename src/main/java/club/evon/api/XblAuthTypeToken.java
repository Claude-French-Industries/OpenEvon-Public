package club.evon.api;
import com.google.common.reflect.TypeToken;
import java.util.Map;




public class XblAuthTypeToken extends TypeToken<Map<String, Object>> {
   public final AccountAuthService authService;

   public XblAuthTypeToken(AccountAuthService accountAuthService) {
      this.authService = accountAuthService;
   }
}
