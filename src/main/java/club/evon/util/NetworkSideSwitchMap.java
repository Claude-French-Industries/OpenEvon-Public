/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.NetworkSide
 */
package club.evon.util;
import net.minecraft.network.NetworkSide;




public class NetworkSideSwitchMap {
    public static final int[] SWITCH_MAP = new int[NetworkSide.values().length];

    static {
        try {
            NetworkSideSwitchMap.SWITCH_MAP[NetworkSide.CLIENTBOUND.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NetworkSideSwitchMap.SWITCH_MAP[NetworkSide.SERVERBOUND.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
