/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.hud.ChatHud
 *  net.minecraft.client.gui.hud.ChatHudLine$Visible
 *  net.minecraft.client.network.AbstractClientPlayerEntity
 *  net.minecraft.text.OrderedText
 *  net.minecraft.text.Style
 *  net.minecraft.util.Formatting
 *  org.json.JSONException
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package club.evon.mixins;
import java.util.ArrayList;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.json.JSONException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import club.evon.ClientContext;
import club.evon.data.UserProfile;
import club.evon.manager.ChatNameManager;




@Mixin(value={ChatHud.class})
public abstract class ChatHudMixin {
    @Unique
    private static Style GRAY_STYLE;
    private static final String a;

    @Redirect(method={"method_71991(ILnet/minecraft/client/gui/DrawContext;FIIILnet/minecraft/client/gui/hud/ChatHudLine$Visible;IF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/ChatHudLine$Visible;content()Lnet/minecraft/text/OrderedText;"))
    private OrderedText appendSocketUsernames(ChatHudLine.Visible visible) {
        if (GRAY_STYLE == null) {
            GRAY_STYLE = Style.EMPTY.withColor(Formatting.GRAY);
        }
        ArrayList<OrderedText> arrayList = new ArrayList<OrderedText>();
        StringBuilder stringBuilder = new StringBuilder();
        visible.content().accept((n, style, n2) -> {
            arrayList.add(OrderedText.styledForwardsVisitedString((String)String.valueOf((char)n2), (Style)style));
            stringBuilder.append((char)n2);
            return true;
        });
        String string = stringBuilder.toString();
        int n3 = 0;
        block0: for (AbstractClientPlayerEntity abstractClientPlayerEntity : ClientContext.MINECRAFT_CLIENT.world.getPlayers()) {
            int n4;
            String string2 = null;
            string2 = abstractClientPlayerEntity.getName().getString();
            int n5 = 0;
            while ((n4 = string.indexOf(string2, n5)) != -1) {
                char c;
                if (n4 > 0 && (c = string.charAt(n4 - 1)) != ' ' && c != '<') {
                    n5 = n4 + string2.length();
                    continue;
                }
                UserProfile w_c22 = ChatNameManager.getInstance().getProfileByMinecraftUsername(string2);
                if (w_c22 == null) continue block0;
                int n6 = n4 + string2.length();
                arrayList.add(n6 + n3, OrderedText.concat((OrderedText[])new OrderedText[]{OrderedText.styledForwardsVisitedString((String)a, (Style)GRAY_STYLE), OrderedText.styledForwardsVisitedString((String)w_c22.getUsername(), (Style)Style.EMPTY.withColor(w_c22.getRank().getTextColor())), OrderedText.styledForwardsVisitedString((String)")", (Style)GRAY_STYLE)}));
                n5 = n6;
                ++n3;
            }
        }
        return OrderedText.concat(arrayList);
    }

    private static JSONException a(JSONException jSONException) {
        return jSONException;
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {
        char[] cArray;
        block12: {
            int n;
            char[] cArray2;
            int n2;
            int n3;
            block11: {
                char[] cArray3 = "8\u007f".toCharArray();
                int n4 = cArray3.length;
                n3 = 0;
                n2 = 70;
                cArray2 = cArray3;
                n = n4;
                if (n4 <= 1) break block11;
                cArray = cArray2;
                n = n;
                if (n <= n3) break block12;
            }
            do {
                int n5 = n2;
                cArray2 = cArray2;
                char[] cArray4 = cArray2;
                int n6 = n2;
                int n7 = n3;
                while (true) {
                    cArray4[n7] = (char)(cArray4[n7] ^ (n6 ^ (switch (n3 % 7) {
                        case 0 -> 94;
                        case 1 -> 17;
                        case 2 -> 31;
                        case 3 -> 53;
                        case 4 -> 89;
                        case 5 -> 99;
                        default -> 17;
                    })));
                    ++n3;
                    n2 = n5;
                    if (n5 != 0) break;
                    n5 = n2;
                    cArray2 = cArray2;
                    n7 = n2;
                    cArray4 = cArray2;
                    n6 = n2;
                }
                cArray = cArray2;
                n = n;
            } while (n > n3);
        }
        a = new String(cArray).intern();
    }
}
