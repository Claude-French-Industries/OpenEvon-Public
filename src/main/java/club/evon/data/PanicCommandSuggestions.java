/*
 * Decompiled with CFR 0.152.
 */
package club.evon.data;
import java.util.ArrayList;
import club.evon.command.PanicCommand;




public class PanicCommandSuggestions
extends ArrayList<String> {
    public final PanicCommand command;
    private static final String PANIC_PREFIX;

    public PanicCommandSuggestions(PanicCommand _x_c22) {
        this.command = _x_c22;
        this.add(PANIC_PREFIX);
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
                char[] cArray3 = "&!X\u0005xL".toCharArray();
                int n4 = cArray3.length;
                n3 = 0;
                n2 = 90;
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
                        case 1 -> 11;
                        case 2 -> 99;
                        case 3 -> 49;
                        case 4 -> 75;
                        case 5 -> 117;
                        default -> 43;
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
        PANIC_PREFIX = new String(cArray).intern();
    }
}
