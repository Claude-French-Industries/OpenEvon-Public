/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.consume.UseAction
 */
package club.evon.util;
import net.minecraft.item.consume.UseAction;




public class UseActionSwitchMap {
    public static final int[] SWITCH_MAP = new int[UseAction.values().length];

    static {
        try {
            UseActionSwitchMap.SWITCH_MAP[UseAction.BLOCK.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            UseActionSwitchMap.SWITCH_MAP[UseAction.NONE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            UseActionSwitchMap.SWITCH_MAP[UseAction.BOW.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
