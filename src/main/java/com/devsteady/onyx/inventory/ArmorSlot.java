package com.devsteady.onyx.inventory;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ArmorSlot {
    HELMET(4),
    CHEST(3),
    LEGGINGS(2),
    BOOTS(1),
    MAIN_HAND(0); //Main slot is the farthest left of the hot bar, not always the players active slot though.

    private static final Map<Integer, ArmorSlot> slots = new HashMap<>();

    static {
        for (ArmorSlot slot : EnumSet.allOf(ArmorSlot.class)) {
            slots.put(slot.getSlot(), slot);
        }
    }

    private int slot;

    ArmorSlot(int slot) {
        this.slot = slot;
    }


    public int getSlot() {
        return slot;
    }

    public static ArmorSlot getSlot(int id) {
        return slots.get(id);
    }
}
