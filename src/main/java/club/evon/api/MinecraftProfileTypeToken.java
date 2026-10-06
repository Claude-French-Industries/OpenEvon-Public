package club.evon.api;
import com.google.common.reflect.TypeToken;
import java.util.Map;




public class MinecraftProfileTypeToken extends TypeToken<Map<String, Object>> {
   public final AccountAuthService owner;

   public MinecraftProfileTypeToken(AccountAuthService accountAuthService) {
      this.owner = accountAuthService;
   }
}
