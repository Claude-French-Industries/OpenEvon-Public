package club.evon.command;
import net.minecraft.util.Formatting;
import club.evon.EvonClient;
import club.evon.launch.LaunchProperties;
import club.evon.util.ChatUtil;


/**
 * ".clientname &lt;name&gt;" - rebrands the client.
 *
 * <p>The name is used by the watermark title, the chat tag and the main menu heading.
 * ".clientname -reset" restores the default from {@link LaunchProperties#NAME}.
 */
public final class ClientNameCommand extends Command {
   public ClientNameCommand() {
      super("clientname", "Sets the name of the client", ".clientname <name> | .clientname -reset");
   }

   @Override
   public boolean execute(String... arguments) {
      if (arguments.length == 0 || arguments[0].isBlank()) {
         ChatUtil.sendMessage(
            Formatting.GRAY + "Usage: " + Formatting.WHITE + ".clientname <name>"
                  + Formatting.GRAY + " or " + Formatting.WHITE + ".clientname -reset"
         );
         return true;
      }

      if (arguments[0].equalsIgnoreCase("-reset")) {
         EvonClient.setClientName(LaunchProperties.NAME);
         ChatUtil.sendMessage(Formatting.GRAY + "Client name reset to " + Formatting.WHITE + LaunchProperties.NAME);
         return true;
      }

      String name = String.join(" ", arguments).trim();
      EvonClient.setClientName(name);
      ChatUtil.sendMessage(Formatting.GRAY + "Client name set to " + Formatting.WHITE + EvonClient.getClientName());
      return true;
   }
}
