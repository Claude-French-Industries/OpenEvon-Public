package club.evon.ui;
import org.json.JSONException;
import club.evon.util.TextUtil;
import club.evon.util.render.NvgImage;




public enum MainMenuEntry {
   SINGLEPLAYER,
   MULTIPLAYER,
   OPTIONS,
   ACCOUNTS,
   EXIT("exit.png");

   public final Object display;
   private static final MainMenuEntry[] VALUES;
   private static boolean state;

   MainMenuEntry() {
      this.display = TextUtil.toDisplayName(this.toString());
   }

   MainMenuEntry(String iconPath) {
      this.display = new NvgImage(iconPath);
   }

   private static MainMenuEntry[] buildValues() {
      return new MainMenuEntry[]{SINGLEPLAYER, MULTIPLAYER, OPTIONS, ACCOUNTS, EXIT};
   }

   static {
      setState(false);
      VALUES = buildValues();
   }

   public static void setState(boolean value) {
      state = value;
   }

   public static boolean getState() {
      return state;
   }

   public static boolean isNotState() {
      boolean state2 = getState();
      return !state2;
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }
}
