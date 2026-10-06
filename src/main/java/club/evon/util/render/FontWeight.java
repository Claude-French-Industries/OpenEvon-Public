package club.evon.util.render;


public enum FontWeight {
   REGULAR("Regular"),
   MEDIUM("Medium"),
   SEMI_BOLD("SemiBold"),
   BOLD("Bold");

   private final String displayName;

   public String getDisplayName() {
      return this.displayName;
   }

   FontWeight(String text) {
      this.displayName = text;
   }
}
