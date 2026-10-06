package club.evon.command;
import net.minecraft.util.Formatting;
import club.evon.EvonClient;
import club.evon.util.ChatUtil;


/**
 * ".name &lt;name&gt;" - sets the local user name shown on the watermark.
 *
 * <p>The client no longer authenticates against a licence server, so the name that used to come
 * back from the auth response is now set locally instead.
 */
public final class NameCommand extends Command {
   public NameCommand() {
      super("name", "Sets the name shown on the watermark", ".name <name>");
   }

   @Override
   public boolean execute(String... arguments) {
      if (arguments.length == 0 || arguments[0].isBlank()) {
         ChatUtil.sendMessage(Formatting.GRAY + "Usage: " + Formatting.WHITE + ".name <name>");
         return true;
      }

      String name = String.join(" ", arguments).trim();
      EvonClient.setUserName(name);
      ChatUtil.sendMessage(Formatting.GRAY + "Name set to " + Formatting.WHITE + name);
      return true;
   }
}
