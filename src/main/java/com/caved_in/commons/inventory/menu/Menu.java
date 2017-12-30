package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.inventory.menu.MenuAction;
import com.caved_in.commons.inventory.menu.MenuBehaviour;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public interface Menu extends InventoryHolder, Cloneable {

    /**
     * Add to the behaviour of the menus on open and close.
     * @param action when this behaviour will be called.
     * @param behaviour behaviour to define the actions that will be performed.
     */
    void addBehaviour(MenuAction action, MenuBehaviour behaviour);

    /**
     * Retrieve a list of behaviours that are registered under the given action.
     * @param action action to retrieve the behaviours of.
     * @return list of behaviours that are attached to the menus. Should not return null.
     */
    List<MenuBehaviour> getBehaviours(MenuAction action);

    /**
     * Whether or not the menus will be closed when the player clicks outside of its gui.
     * @return true or false accordingly.
     */
    boolean exitOnClickOutside();

    /**
     * Open the menus for player.
     * @param player player who will see the menus.
     */
    default void openMenu(Player player) {
        Inventory inventory = getInventory();
        if (inventory.getViewers().contains(player)) {
            return;
        }
        player.openInventory(inventory);

        List<MenuBehaviour> behaviours = getBehaviours(MenuAction.OPEN);
        if (behaviours.size() > 0) {
            for (MenuBehaviour behaviour : behaviours) {
                behaviour.doAction(this, player);
            }
        }
    }

    /**
     * Close the menus for player.
     * @param player player who's menus will be closed.
     */
    default void closeMenu(Player player) {
        Inventory inventory = getInventory();
        if (!inventory.getViewers().contains(player)) {
            return;
        }

        inventory.getViewers().remove(player);
        player.closeInventory();
        List<MenuBehaviour> behaviours = getBehaviours(MenuAction.CLOSE);
        if (behaviours.size() > 0) {
            for (MenuBehaviour behaviour : behaviours) {
                behaviour.doAction(this, player);
            }
        }
    }

    /**
     * After editing values of a menus, the interface must be updated for the player; Otherwise it won't be displayed.
     * This is simply due to the way minecraft is designed.
     */
    default void updateMenu() {
        getInventory().getViewers().stream().filter(entity -> entity instanceof Player).forEach(entity -> ((Player) entity).updateInventory());
    }

    /**
     * Provide a clone of the menus instance.
     */
    public Menu clone();


    /**
     * Retrieve a list of the entities with this menus as their active screen.
     * @return List of entities viewing the menus; Empty list if none are.
     */
    default List<HumanEntity> getViewers() {
        return getInventory().getViewers();
    }
}
