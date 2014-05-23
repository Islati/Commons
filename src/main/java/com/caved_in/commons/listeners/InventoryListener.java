package com.caved_in.commons.listeners;

import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.MenuBehaviour;
import com.caved_in.commons.menu.MenuBehaviourType;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		//Get the inventory that's being clicked
		Inventory inventory = event.getInventory();
		InventoryType inventoryType = inventory.getType();
		Player player = (Player) event.getWhoClicked();

		/*
		Handle all the clicks passed to item-menus.
		 */
		InventoryHolder holder = inventory.getHolder();
		if (holder instanceof ItemMenu) {
			ItemMenu menu = (ItemMenu) holder;
			//If the player is clicking outside the menu, and it closes when clicking outside, then close it!
			if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
				if (menu.exitOnClickOutside()) {
					menu.closeMenu(player);
				}
			} else {
				int index = event.getRawSlot();
				//if the players selecting within bounds of the inventory, then act accordingly
				if (index < inventory.getSize()) {
					menu.selectMenuItem(player, index);
				} else {
					if (menu.exitOnClickOutside()) {
						menu.closeMenu(player);
					}
				}
			}
			event.setCancelled(true);
		}

		if (event.isCancelled()) {
			return;
		}

		PlayerWrapper playerWrapper = Players.getData(player);
		switch (inventoryType) {
			case WORKBENCH:
				//If the player's viewing a recipe, don't let them click / manipulate
				if (playerWrapper.isViewingRecipe()) {
					event.setCancelled(true);
				}
				break;
			case PLAYER:
				break;
			case CHEST:
				break;
			default:
				break;
		}

		if (playerWrapper.isInDebugMode()) {
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
			List<MenuBehaviour> openBehaviours = menu.getBehaviours(MenuBehaviourType.OPEN);
			if (openBehaviours != null) {
				openBehaviours.stream().filter(behaviour -> behaviour != null).forEach(behaviour -> behaviour.doAction(player));
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
			List<MenuBehaviour> closeBehaviours = menu.getBehaviours(MenuBehaviourType.CLOSE);
			if (closeBehaviours != null) {
				closeBehaviours.stream().filter(behaviour -> behaviour != null).forEach(behaviour -> behaviour.doAction(player));
			}
		}
		//Get the wrapped player data
		PlayerWrapper playerWrapper = Players.getData(player);
		switch (inventoryType) {
			case WORKBENCH:
				//If the player's viewing a recipe, clear the inventory and update the inventory on close
				if (playerWrapper.isViewingRecipe()) {
					playerWrapper.setViewingRecipe(false);
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
