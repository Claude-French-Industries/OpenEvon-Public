package club.evon.command;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public abstract class Command {
   private final String r;
   private final String H;
   private final String P;
   private final ArrayList<String> y = new ArrayList<>();
   private static boolean enabled;
   private static final long STRING_SEED = KeyedRandom.create(-1281884311225057803L, -3964255313655418605L, MethodHandles.lookup().lookupClass()).next(267477038983450L);

   public Command(String name, String description, String usage, String... aliases) {
      super();

      long longValue = STRING_SEED ^ 68380894816283L;
      boolean enabled2 = isEnabled();
      boolean flag = enabled2;
      this.r = name;
      this.H = description;
      this.P = usage;
      this.y.addAll(Arrays.asList(aliases));
      if (!flag) {
         BaseModule.setStateArray(new BaseModule[1]);
      }
      }

   public abstract boolean execute(String... args);

   public ArrayList<String> getSuggestions() {
      return null;
   }

   public String getName() {
      return this.r;
   }

   public String getDescription() {
      return this.H;
   }

   public ArrayList<String> getAliases() {
      return this.y;
   }

   public Command(String name, String description, String usage) {
      this.r = name;
      this.H = description;
      this.P = usage;
   }

   public static void setEnabled(boolean value) {
      enabled = value;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isDisabled() {
      boolean enabled2 = isEnabled();
      return !enabled2;
   }

   static {
      if (isDisabled()) {
         setEnabled(true);
      }
   }

   private static JSONException passThrough(JSONException exception) {
      return exception;
   }
}
