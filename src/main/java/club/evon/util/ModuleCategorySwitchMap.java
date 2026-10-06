/*
 * Decompiled with CFR 0.152.
 */
package club.evon.util;
import club.evon.module.ModuleCategory;




public class ModuleCategorySwitchMap {
    public static final int[] SWITCH_MAP = new int[ModuleCategory.values().length];

    static {
        try {
            ModuleCategorySwitchMap.SWITCH_MAP[ModuleCategory.COMBAT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ModuleCategorySwitchMap.SWITCH_MAP[ModuleCategory.MOTION.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ModuleCategorySwitchMap.SWITCH_MAP[ModuleCategory.PLAYER.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ModuleCategorySwitchMap.SWITCH_MAP[ModuleCategory.RENDER.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ModuleCategorySwitchMap.SWITCH_MAP[ModuleCategory.MISC.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
