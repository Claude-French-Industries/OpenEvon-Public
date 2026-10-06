/*
 * Decompiled with CFR 0.152.
 */
package club.evon.command;



public class BigTextCommand
extends Command {
    private static final String NAME;

    public BigTextCommand() {
        super(NAME, "", "");
    }

    @Override
    public boolean execute(String ... args) {
        return false;
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
                char[] cArray3 = "\f\u000f(voVi".toCharArray();
                int n4 = cArray3.length;
                n3 = 0;
                n2 = 69;
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
                        case 0 -> 43;
                        case 1 -> 35;
                        case 2 -> 10;
                        case 3 -> 71;
                        case 4 -> 79;
                        case 5 -> 107;
                        default -> 88;
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
        NAME = new String(cArray).intern();
    }
}
