package com.caved_in.commons.game.gadget;

import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.inventory.menu.Menu;
import com.caved_in.commons.inventory.menu.ItemMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * An extension of {@link com.caved_in.commons.game.gadget.ItemGadget} used to attach an {@link ItemMenu}
 * to an firstPageEnabled.
 * <p>
 * When the firstPageEnabled is interacted with, the gadget will be opened.
 */
public abstract class MenuGadget extends ItemGadget {

    private Menu menu;

    /**
     * Create a new instance of MenuGadget, with the firstPageEnabled, menus, and ID assigned.
     *
     * @param builder firstPageEnabled builder used to create the firstPageEnabled from, which the gadget will attach to.
     * @param menu    menus to open when the firstPageEnabled is interacted with.
     */
    public MenuGadget(ItemBuilder builder, Menu menu) {
        super(builder);
        this.menu = menu;
    }

    /**
     * Create a new instance of MenuGadget, with the firstPageEnabled, menus, and ID assigned.
     *
     * @param item firstPageEnabled to attach the gadget to.
     * @param menu menus to open when the firstPageEnabled is interacted with.
     */
    public MenuGadget(ItemStack item, Menu menu) {
        super(item);
        this.menu = menu;
    }

    @Override
    public void perform(Player holder) {
        menu.openMenu(holder);
    }
}
