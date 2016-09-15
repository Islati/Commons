package com.caved_in.commons.inventory;

/**
 * With the 1.9 Update comes Dual Wielding, and in the spirit of Commons, it's best to have
 * our own managing classes for the slots so we can serialize, cache, and manage with ease, these slots.
 *
 * This Enum simply represents both hands of the player!
 */
public enum HandSlot {
    MAIN_HAND,
    OFF_HAND;
}
