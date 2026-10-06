package fr.litarvan.openauth.microsoft.model.response;

public class XboxLoginResponse {
   private final String IssueInstant;
   private final String NotAfter;
   private final String Token;
   private final XboxLoginResponse$XboxLiveLoginResponseClaims DisplayClaims;

   public XboxLoginResponse(String IssueInstant, String NotAfter, String Token, XboxLoginResponse$XboxLiveLoginResponseClaims DisplayClaims) {
      this.IssueInstant = IssueInstant;
      this.NotAfter = NotAfter;
      this.Token = Token;
      this.DisplayClaims = DisplayClaims;
   }

   public String getIssueInstant() {
      return this.IssueInstant;
   }

   public String getNotAfter() {
      return this.NotAfter;
   }

   public String getToken() {
      return this.Token;
   }

   public XboxLoginResponse$XboxLiveLoginResponseClaims getDisplayClaims() {
      return this.DisplayClaims;
   }
}
