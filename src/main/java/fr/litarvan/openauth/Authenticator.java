package fr.litarvan.openauth;

import com.google.gson.Gson;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.AuthError;
import fr.litarvan.openauth.model.request.AuthRequest;
import fr.litarvan.openauth.model.request.InvalidateRequest;
import fr.litarvan.openauth.model.request.RefreshRequest;
import fr.litarvan.openauth.model.request.SignoutRequest;
import fr.litarvan.openauth.model.request.ValidateRequest;
import fr.litarvan.openauth.model.response.AuthResponse;
import fr.litarvan.openauth.model.response.RefreshResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Authenticator {
   @Deprecated
   public static final String MOJANG_AUTH_URL = "https://authserver.mojang.com/";
   private final String authURL;
   private final AuthPoints authPoints;

   public Authenticator(String authURL, AuthPoints authPoints) {
      this.authURL = authURL;
      this.authPoints = authPoints;
   }

   public AuthResponse authenticate(AuthAgent agent, String username, String password, String clientToken) throws AuthenticationException {
      return this.authenticate_r2(agent, username, password, clientToken, Proxy.NO_PROXY);
   }

   public AuthResponse authenticate_r2(AuthAgent agent, String username, String password, String clientToken, Proxy proxy) throws AuthenticationException {
      AuthRequest var6 = new AuthRequest(agent, username, password, clientToken);
      return (AuthResponse)this.sendRequest_r2(var6, AuthResponse.class, this.authPoints.getAuthenticatePoint(), proxy);
   }

   public RefreshResponse refresh(String accessToken, String clientToken) throws AuthenticationException {
      return this.refresh_r2(accessToken, clientToken, Proxy.NO_PROXY);
   }

   public RefreshResponse refresh_r2(String accessToken, String clientToken, Proxy proxy) throws AuthenticationException {
      RefreshRequest var4 = new RefreshRequest(accessToken, clientToken);
      return (RefreshResponse)this.sendRequest_r2(var4, RefreshResponse.class, this.authPoints.getRefreshPoint(), proxy);
   }

   public void validate(String accessToken) throws AuthenticationException {
      this.validate_r2(accessToken, Proxy.NO_PROXY);
   }

   public void validate_r2(String accessToken, Proxy proxy) throws AuthenticationException {
      ValidateRequest var3 = new ValidateRequest(accessToken);
      this.sendRequest_r2(var3, null, this.authPoints.getValidatePoint(), proxy);
   }

   public void signout(String username, String password) throws AuthenticationException {
      this.signout_r2(username, password, Proxy.NO_PROXY);
   }

   public void signout_r2(String username, String password, Proxy proxy) throws AuthenticationException {
      SignoutRequest var4 = new SignoutRequest(username, password);
      this.sendRequest_r2(var4, null, this.authPoints.getSignoutPoint(), proxy);
   }

   public void invalidate(String accessToken, String clientToken) throws AuthenticationException {
      this.invalidate_r2(accessToken, clientToken, Proxy.NO_PROXY);
   }

   public void invalidate_r2(String accessToken, String clientToken, Proxy proxy) throws AuthenticationException {
      InvalidateRequest var4 = new InvalidateRequest(accessToken, clientToken);
      this.sendRequest_r2(var4, null, this.authPoints.getInvalidatePoint(), proxy);
   }

   private Object sendRequest(Object var1, Class<?> var2, String var3) throws AuthenticationException {
      return this.sendRequest_r2(var1, var2, var3, Proxy.NO_PROXY);
   }

   private Object sendRequest_r2(Object var1, Class<?> var2, String var3, Proxy var4) throws AuthenticationException {
      Gson var5 = new Gson();

      String var6;
      try {
         var6 = this.sendPostRequest_r2(this.authURL + var3, var5.toJson(var1), var4);
      } catch (IOException var8) {
         throw new AuthenticationException(new AuthError("Can't send the request : " + var8.getClass().getName(), var8.getMessage(), "Unknown"));
      }

      return var2 != null ? var5.fromJson(var6, var2) : null;
   }

   private String sendPostRequest(String var1, String var2) throws AuthenticationException, IOException {
      return this.sendPostRequest_r2(var1, var2, Proxy.NO_PROXY);
   }

   private String sendPostRequest_r2(String var1, String var2, Proxy var3) throws AuthenticationException, IOException {
      byte[] var4 = var2.getBytes(StandardCharsets.UTF_8);
      URL var5 = new URL(var1);
      HttpURLConnection var6 = (HttpURLConnection)var5.openConnection(var3 != null ? var3 : Proxy.NO_PROXY);
      var6.setRequestMethod("POST");
      var6.setDoOutput(true);
      var6.setRequestProperty("Accept-Charset", "UTF-8");
      var6.setRequestProperty("Content-Type", "application/json;charset=utf-8");
      var6.setRequestProperty("Content-Length", String.valueOf(var4.length));
      DataOutputStream var7 = new DataOutputStream(var6.getOutputStream());
      var7.write(var4, 0, var4.length);
      var7.flush();
      var7.close();
      var6.connect();
      int var8 = var6.getResponseCode();
      if (var8 == 204) {
         var6.disconnect();
         return null;
      }

      InputStream var9;
      if (var8 == 200) {
         var9 = var6.getInputStream();
      } else {
         var9 = var6.getErrorStream();
      }

      BufferedReader var11 = new BufferedReader(new InputStreamReader(var9));
      String var10 = var11.readLine();

      try {
         var11.close();
      } catch (IOException var13) {
         var13.printStackTrace();
      }

      var6.disconnect();

      while (var10 != null && var10.startsWith("\ufeff")) {
         var10 = var10.substring(1);
      }

      if (var8 != 200) {
         Gson var12 = new Gson();
         if (var10 != null && !var10.startsWith("{")) {
            throw new AuthenticationException(new AuthError("Internal server error", var10, "Remote"));
         } else {
            throw new AuthenticationException((AuthError)var12.fromJson(var10, AuthError.class));
         }
      } else {
         return var10;
      }
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
