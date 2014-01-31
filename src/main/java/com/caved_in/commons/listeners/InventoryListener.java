package com.caved_in.commons.listeners;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 22/01/14
 * Time: 12:10 PM
 */
public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.isCancelled()) {
			return;
		}
		//Get the inventory that's being clicked
		Inventory inventory = event.getInventory();
		InventoryType inventoryType = inventory.getType();
		Player player = (Player)event.getWhoClicked();
		PlayerWrapper playerWrapper = PlayerHandler.getData(player);
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
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory inventory = event.getInventory();
		InventoryType inventoryType = inventory.getType();
		Player player = (Player)event.getPlayer();
		//Get the wrapped player data
		PlayerWrapper playerWrapper = PlayerHandler.getData(player);
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
