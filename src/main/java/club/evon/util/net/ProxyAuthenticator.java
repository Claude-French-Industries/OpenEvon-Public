package club.evon.util.net;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import club.evon.api.HttpProxyClient;




public class ProxyAuthenticator extends Authenticator {
   public final String username;
   public final String password;

   public ProxyAuthenticator(HttpProxyClient proxyClient, String text, String text2) {
      this.username = text;
      this.password = text2;
   }

   @Override
   public PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(this.username, this.password.toCharArray());
   }
}
