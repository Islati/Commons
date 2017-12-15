package com.caved_in.commons.menu;

import com.caved_in.commons.menu.inventory.MenuAction;
import com.caved_in.commons.menu.inventory.MenuBehaviour;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public interface Menu extends InventoryHolder, Cloneable {

    /**
     * Add to the behaviour of the menu on open and close.
     * @param action when this behaviour will be called.
     * @param behaviour behaviour to define the actions that will be performed.
     */
    void addBehaviour(MenuAction action, MenuBehaviour behaviour);

    /**
     * Retrieve a list of behaviours that are registered under the given action.
     * @param action action to retrieve the behaviours of.
     * @return list of behaviours that are attached to the menu. Should not return null.
     */
    List<MenuBehaviour> getBehaviours(MenuAction action);

    /**
     * Whether or not the menu will be closed when the player clicks outside of its gui.
     * @return true or false accordingly.
     */
    boolean exitOnClickOutside();

    /**
     * Open the menu for player.
     * @param player player who will see the menu.
     */
    void openMenu(Player player);

    /**
     * Close the menu for player.
     * @param player player who's menu will be closed.
     */
    void closeMenu(Player player);

    /**
     * After editing values of a menu, the interface must be updated for the player; Otherwise it won't be displayed.
     * This is simply due to the way minecraft is designed.
     */
    default void updateMenu() {
        getInventory().getViewers().stream().filter(entity -> entity instanceof Player).forEach(entity -> ((Player) entity).updateInventory());
    }

    /**
     * Provide a clone of the menu instance.
     */
    public Menu clone();


    /**
     * Retrieve a list of the entities with this menu as their active screen.
     * @return List of entities viewing the menu; Empty list if none are.
     */
    default List<HumanEntity> getViewers() {
        return getInventory().getViewers();
    }
}
