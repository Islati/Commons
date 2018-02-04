package com.devsteady.onyx.listeners;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.debug.Debugger;
import com.devsteady.onyx.inventory.menu.ItemMenu;
import com.devsteady.onyx.inventory.menu.MenuAction;
import com.devsteady.onyx.inventory.menu.MenuBehaviour;
import com.devsteady.onyx.player.OnyxPlayer;
import com.devsteady.onyx.player.OnyxPlayerManager;
import com.devsteady.onyx.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public class MenuInventoryListener implements Listener {

    private static OnyxPlayerManager playerManager = null;

    public MenuInventoryListener() {
        playerManager = Onyx.getInstance().getPlayerHandler();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMenuClick(InventoryClickEvent event) {
        //Get the inventory that's being clicked
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof ItemMenu)) {
            return;
        }
        event.setCancelled(true);
        ItemMenu menu = (ItemMenu) holder;
        //If the player is clicking outside the menus, and it closes when clicking outside, then close it!

        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            if (menu.exitOnClickOutside()) {
                menu.closeMenu(player);
            }
        }
        int index = event.getRawSlot();
        //if the players selecting within bounds of the inventory, then act accordingly
        if (index < inventory.getSize()) {
            menu.selectMenuItem(player, index, event.getClick());
        } else {
            //If they're gonna mess with their inventory, they don't need a menus open.
            if (menu.exitOnClickOutside()) {
                menu.closeMenu(player);
            }
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        //Get the inventory that's being clicked
        Inventory inventory = event.getInventory();
        InventoryType inventoryType = inventory.getType();
        Player player = (Player) event.getWhoClicked();
        if (event.isCancelled()) {
            return;
        }

        OnyxPlayer minecraftPlayer = playerManager.getUser(player);
        switch (inventoryType) {
            case WORKBENCH:
                //If the player's viewing a recipe, don't let them click / manipulate
                if (minecraftPlayer.isViewingRecipe()) {
                    event.setCancelled(true);
                }
                break;
            case CHEST:
                break;
            default:
                break;
        }

        if (minecraftPlayer.isInDebugMode()) {
            //todo implement option to filter debug messages
            Debugger.debugInventoryClickEvent(player, event);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        Inventory inventory = e.getInventory();
        InventoryHolder holder = inventory.getHolder();
        Player player = (Player) e.getPlayer();
        if (holder instanceof ItemMenu) {
            ItemMenu menu = (ItemMenu) holder;
            List<MenuBehaviour> openBehaviours = menu.getBehaviours(MenuAction.OPEN);
            if (openBehaviours != null) {
                openBehaviours.stream().filter(behaviour -> behaviour != null).forEach(behaviour -> behaviour.doAction(menu, player));
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        InventoryType inventoryType = inventory.getType();
        Player player = (Player) event.getPlayer();
        InventoryHolder holder = inventory.getHolder();
        if (holder instanceof ItemMenu) {
            ItemMenu menu = (ItemMenu) holder;
            List<MenuBehaviour> closeBehaviours = menu.getBehaviours(MenuAction.CLOSE);
            if (closeBehaviours != null) {
                closeBehaviours.stream().filter(behaviour -> behaviour != null).forEach(behaviour -> behaviour.doAction(menu, player));
            }
        }
        //Get the wrapped player data
        OnyxPlayer minecraftPlayer = playerManager.getUser(player);
        switch (inventoryType) {
            case WORKBENCH:
                //If the player's viewing a recipe, clear the inventory and update the inventory on close
                if (minecraftPlayer.isViewingRecipe()) {
                    minecraftPlayer.setViewingRecipe(false);
                    inventory.clear();
                    player.updateInventory();
                }
                break;
            case PLAYER:
                break;
            case CHEST:
                break;
            default:
                break;
        }
    }
}
