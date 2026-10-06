package club.evon.util.net;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.Proxy.Type;
import java.util.List;
import club.evon.api.HttpProxyClient;




public class HttpProxySelector extends ProxySelector {
   public final String host;
   public final int port;

   public HttpProxySelector(HttpProxyClient client, String text, int value) {
      this.host = text;
      this.port = value;
   }

   @Override
   public List<Proxy> select(URI uri) {
      return List.of(new Proxy(Type.HTTP, new InetSocketAddress(this.host, this.port)));
   }

   @Override
   public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
      ioe.printStackTrace();
   }
}
