/*
 * Decompiled with CFR 0.152.
 */
package club.evon.command;
import java.util.ArrayList;




public class IrcChatCommandSuggestions
extends ArrayList<String> {
    public final IrcChatCommand command;
    private static final String USAGE;

    public IrcChatCommandSuggestions(IrcChatCommand _Q2) {
        this.command = _Q2;
        this.add(USAGE);
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
                char[] cArray3 = "(@Vs\u001d\u0004,r\t\u0018\u007f\u0010C\"`O\u001a".toCharArray();
                int n4 = cArray3.length;
                n3 = 0;
                n2 = 84;
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
                        case 0 -> 82;
                        case 1 -> 125;
                        case 2 -> 112;
                        case 3 -> 68;
                        case 4 -> 42;
                        case 5 -> 56;
                        default -> 25;
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
        USAGE = new String(cArray).intern();
    }
}
