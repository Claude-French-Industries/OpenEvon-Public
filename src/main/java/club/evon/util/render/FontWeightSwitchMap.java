/*
 * Decompiled with CFR 0.152.
 */
package club.evon.util.render;



public class FontWeightSwitchMap {
    public static final int[] SWITCH_MAP = new int[FontWeight.values().length];

    static {
        try {
            FontWeightSwitchMap.SWITCH_MAP[FontWeight.BOLD.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FontWeightSwitchMap.SWITCH_MAP[FontWeight.SEMI_BOLD.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FontWeightSwitchMap.SWITCH_MAP[FontWeight.MEDIUM.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FontWeightSwitchMap.SWITCH_MAP[FontWeight.REGULAR.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
