/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.attribute.EntityAttributeModifier$Operation
 */
package club.evon.util;
import net.minecraft.entity.attribute.EntityAttributeModifier;




public class AttributeOperationSwitchMap {
    public static final int[] SWITCH_MAP = new int[EntityAttributeModifier.Operation.values().length];

    static {
        try {
            AttributeOperationSwitchMap.SWITCH_MAP[EntityAttributeModifier.Operation.ADD_VALUE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AttributeOperationSwitchMap.SWITCH_MAP[EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AttributeOperationSwitchMap.SWITCH_MAP[EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
