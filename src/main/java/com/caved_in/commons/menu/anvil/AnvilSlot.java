package com.caved_in.commons.menu.anvil;

import org.bukkit.inventory.ItemStack;

public class AnvilSlot {
    /**
     * The slot on the far left, where the first input is inserted. An {@link ItemStack} is always inserted
     * here to be renamed
     */
    public static final int INPUT_LEFT = 0;
    /**
     * Not used, but in a real anvil you are able to put the second item you want to combine here
     */
    public static final int INPUT_RIGHT = 1;
    /**
     * The output slot, where an item is put when two items are combined from {@link #INPUT_LEFT} and
     * {@link #INPUT_RIGHT} or {@link #INPUT_LEFT} is renamed
     */
    public static final int OUTPUT = 2;

}
