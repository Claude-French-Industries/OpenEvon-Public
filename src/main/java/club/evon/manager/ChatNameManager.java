package club.evon.manager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.json.JSONException;
import club.evon.ClientContext;
import club.evon.ClientService;
import club.evon.data.UserProfile;
import club.evon.module.BaseModule;




public final class ChatNameManager implements ClientService {
   private final List<UserProfile> profiles = new ArrayList<>();
   private static ChatNameManager instance;
   private static final long KEY;
   private static final String[] STRINGS;

   public Text formatChatMessage(Text message, MessageSignatureData signatureData, MessageIndicator indicator) {
      long longValue = KEY ^ 92058374665283L;
      String flowGuard = ClientState.getFlowGuard();
      MinecraftClient tmp = ClientContext.MINECRAFT_CLIENT;
      if (flowGuard == null) {
         if (ClientContext.MINECRAFT_CLIENT.world == null) {
            return null;
         }

         tmp = ClientContext.MINECRAFT_CLIENT;
      }

      if (tmp.player != null) {
         UserProfile userProfile = null;

         for (UserProfile userProfile2 : this.profiles) {
            String string = message.getString();
            String[] strings = STRINGS;
            String replaceAll = string.replaceAll(strings[3], "");
            String minecraftUsername = userProfile2.getMinecraftUsername();
            boolean contains = replaceAll.contains(minecraftUsername);
            if (flowGuard == null) {
               if (!contains) {
                  continue;
               }

               contains = replaceAll.contains("(" + minecraftUsername);
            }

            if (!contains) {
               userProfile = userProfile2;
               break;
            }
         }

         UserProfile userProfile3 = userProfile;
         if (flowGuard == null) {
            if (userProfile == null) {
               return message;
            }

            userProfile3 = userProfile;
         }

         String minecraftUsername2 = userProfile3.getMinecraftUsername();
         String valueOf = String.valueOf(Formatting.GRAY);
         String valueOf2 = String.valueOf(userProfile.getRank().getTextColor());
         String username = userProfile.getUsername();
         String valueOf3 = String.valueOf(Formatting.GRAY);
         String text = username;
         String text2 = valueOf2;
         String text3 = valueOf;
         String text4 = minecraftUsername2 + text3 + STRINGS[2] + text2 + text + valueOf3 + ")";
         MutableText empty = Text.empty();
         message.visit((style, var4x) -> {
            String replace = var4x.replace(minecraftUsername2, text4);
            empty.append(Text.literal(replace).setStyle(style));
            return Optional.empty();
         }, Style.EMPTY);
         return empty;
      } else {
         return null;
      }
   }

   public String getMinecraftUsername(String evonUsername) {
      long longValue = KEY ^ 58132705865082L;
      String flowGuard = ClientState.getFlowGuard();
      Iterator iterator = this.profiles.iterator();
      String text = flowGuard;

      while (true) {
         if (iterator.hasNext()) {
            UserProfile userProfile = (UserProfile)iterator.next();
            flowGuard = userProfile.getUsername();
            if (text != null) {
               break;
            }

            if (flowGuard.equals(evonUsername)) {
               flowGuard = userProfile.getMinecraftUsername();
               break;
            }

            if (text == null) {
               continue;
            }
         }

         return null;
      }

      return flowGuard;
   }

   public UserProfile getProfileByMinecraftUsername(String minecraftUsername) {
      long longValue = KEY ^ 74926939887700L;
      String flowGuard = ClientState.getFlowGuard();
      List tmp = this.profiles;
      if (flowGuard == null) {
         if (this.profiles == null) {
            return null;
         }

         tmp = this.profiles;
      }

      Iterator iterator = tmp.iterator();
      UserProfile userProfile = null;

      while (true) {
         if (iterator.hasNext()) {
            UserProfile userProfile2 = (UserProfile)iterator.next();
            userProfile = userProfile2;
            if (flowGuard != null) {
               break;
            }

            if (userProfile2.getMinecraftUsername().equals(minecraftUsername)) {
               userProfile = userProfile2;
               break;
            }

            if (flowGuard == null) {
               continue;
            }
         }

         return null;
      }

      return userProfile;
   }

   public void loadUsers(String users) {
      long longValue = KEY ^ 97891515804862L;
      String flowGuard = ClientState.getFlowGuard();
      ArrayList list = new ArrayList();
      String text = flowGuard;
      String[] strings = users.split("\n");
      int length = strings.length;
      int index = 0;

      while (true) {
         if (index < length) {
            String text2 = strings[index];
            String[] strings2 = text2.split("&");
            int length2 = strings2.length;
            if (text != null) {
               break;
            }

            label31:
            if (text == null) {
               if (length2 < 3) {
                  if (text == null) {
                     break label31;
                  }

                  BaseModule.setStateArray(new BaseModule[3]);
               }

               list.add(new UserProfile(strings2[0], Integer.parseInt(strings2[1]), strings2[2]));
            }

            index++;
            if (text == null) {
               continue;
            }
         }

         this.profiles.clear();
         this.profiles.addAll(list);
         break;
      }
   }

   /**
    * Handles an incoming IRC payload and refreshes {@link #profiles} from it.
    *
    * <p>This used to be a {@code native} method implemented by the client's runtime
    * library, which talked to the author's IRC/authentication backend. That backend (and
    * the library) have been removed, so there is no longer a server to receive user
    * records from and this is intentionally inert. {@link #loadUsers(String)} still
    * parses the server's {@code name&level&minecraftName} format if a profile list is
    * supplied by other means.
    */
   public void i(String message) {
      // Remote IRC user-list feed removed with the authentication layer.
   }

   /**
    * Reports the locally signed-in Minecraft account to the IRC backend.
    *
    * <p>Formerly {@code native}; see {@link #i(String)}. Kept as a no-op so the
    * Microsoft/token login paths can still call it.
    */
   public void y(String username) {
      // Remote IRC presence reporting removed with the authentication layer.
   }

   public static void init() {
      instance = new ChatNameManager();
   }

   public List<UserProfile> getProfiles() {
      return this.profiles;
   }

   public static ChatNameManager getInstance() {
      return instance;
   }

   static {
      // These were populated by the removed native runtime (Hidden0.special_clinit_0_110).
      // [2] is inserted between the grey Minecraft name and the rank-coloured client name,
      // [3] strips legacy colour codes before matching a name inside a chat line.
      KEY = 0L;
      STRINGS = new String[]{"", "", " (", "\u00a7."};
   }

   private static JSONException identity(JSONException exception) {
      return exception;
   }

   private static String decodeUtf8(byte[] bytes) {
      int index = 0;
      int value;
      char[] chars = new char[value = bytes.length];

      for (int i = 0; i < value; i++) {
         int value2;
         if ((value2 = 255 & bytes[i]) < 192) {
            chars[index++] = (char)value2;
         } else if (value2 < 224) {
            char character = (char)((char)(value2 & 31) << 6);
            byte byteValue = bytes[++i];
            character = (char)(character | (char)(byteValue & 63));
            chars[index++] = character;
         } else if (i < value - 2) {
            char character2 = (char)((char)(value2 & 15) << '\f');
            byte byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63) << 6);
            byteValue2 = bytes[++i];
            character2 = (char)(character2 | (char)(byteValue2 & 63));
            chars[index++] = character2;
         }
      }

      return new String(chars, 0, index);
   }
}
