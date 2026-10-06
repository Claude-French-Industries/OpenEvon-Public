package club.evon.mixins;
import java.util.List;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import club.evon.data.UserProfile;
import club.evon.manager.ChatNameManager;




@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
   @Unique
   private static Text GRAY_OPENING_PARENTHESIS;
   @Unique
   private static Text GRAY_CLOSING_PARENTHESIS;
   @Unique
   private static Text EMPTY_TEXT;

   @Redirect(
      method = "render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/hud/PlayerListHud;getPlayerName(Lnet/minecraft/client/network/PlayerListEntry;)Lnet/minecraft/text/Text;"
      )
   )
   private Text redirectPlayerName(PlayerListHud player, PlayerListEntry player2) {
      ChatNameManager instance = ChatNameManager.getInstance();
      UserProfile profileByMinecraftUsername = instance.getProfileByMinecraftUsername(player2.getProfile().name());
      Text playerName = player.getPlayerName(player2);
      if (profileByMinecraftUsername == null) {
         return playerName;
      }

      if (GRAY_OPENING_PARENTHESIS == null) {
         GRAY_OPENING_PARENTHESIS = Text.literal(" " + Formatting.GRAY + "(");
         GRAY_CLOSING_PARENTHESIS = Text.literal(Formatting.GRAY + ")");
         EMPTY_TEXT = Text.empty();
      }

      return Texts.join(
         List.of(playerName, GRAY_OPENING_PARENTHESIS, MutableText.of(PlainTextContent.of(profileByMinecraftUsername.getUsername())).withColor(profileByMinecraftUsername.getRank().getTextColor().getRgb()), GRAY_CLOSING_PARENTHESIS),
         EMPTY_TEXT
      );
   }

   private static JSONException a(JSONException jSONException) {
      return jSONException;
   }
}
