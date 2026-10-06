/*
 * Decompiled with CFR 0.152.
 */
package club.evon.setting;

import club.evon.config.ConfigManager;


public final class BindSetting
extends Setting<Integer> {
    private BindMode H;
    private static final String DEFAULT_NAME;

    public BindSetting(String name, Integer defaultValue, BindMode bindMode) {
        super(name, defaultValue);
        this.H = bindMode;
    }

    public BindSetting(Integer defaultValue, BindMode bindMode) {
        super(DEFAULT_NAME, defaultValue);
        this.H = bindMode;
    }

    public BindSetting(Integer defaultValue) {
        this(defaultValue, BindMode.TOGGLE);
    }

    public BindMode getBindMode() {
        return this.H;
    }

    public void setBindMode(BindMode bindMode) {
        // Bind mode is part of the saved bind; the key code itself goes through
        // Setting.setValue, which already marks the state dirty.
        if (this.H != bindMode) {
            this.H = bindMode;
            ConfigManager.markDirty();
        }
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
                char[] cArray3 = "\u007f)'\"".toCharArray();
                int n4 = cArray3.length;
                n3 = 0;
                n2 = 55;
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
                        case 0 -> 10;
                        case 1 -> 119;
                        case 2 -> 126;
                        case 3 -> 113;
                        case 4 -> 101;
                        case 5 -> 84;
                        default -> 4;
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
        DEFAULT_NAME = new String(cArray).intern();
    }
}
