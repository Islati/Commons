package com.caved_in.commons.inventory;

import org.bukkit.inventory.EquipmentSlot;

/**
 * With the 1.9 Update comes Dual Wielding, and in the spirit of Commons, it's best to have
 * our own managing classes for the slots so we can serialize, cache, and manage with ease, these slots.
 * <p>
 * This Enum simply represents both hands of the player!
 */
public enum HandSlot {
    MAIN_HAND,
    OFF_HAND;

    /**
     * Helper method to retrieve the hand slot from Bukkit's EquipmentSlot enum.
     *
     * @param slot slot to get the hand slot of.
     * @return slot of hand if it's valid, otherwise null.
     */
    public static HandSlot getSlot(EquipmentSlot slot) {
        switch (slot) {
            case HAND:
                return MAIN_HAND;
            case OFF_HAND:
                return OFF_HAND;
            case FEET:
            case LEGS:
            case CHEST:
            case HEAD:
                return null;
        }

        return null;
    }
}
