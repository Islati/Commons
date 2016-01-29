package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.MenuBehaviour;
import com.caved_in.commons.menu.MenuBehaviourType;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public class InventoryListener implements Listener {

	private static Players playerManager = null;

	public InventoryListener() {
		playerManager = Commons.getInstance().getPlayerHandler();
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
		//If the player is clicking outside the menu, and it closes when clicking outside, then close it!

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
			//If they're gonna mess with their inventory, they don't need a menu open.
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

		MinecraftPlayer minecraftPlayer = playerManager.getData(player);
		switch (inventoryType) {
			case WORKBENCH:
				//If the player's viewing a recipe, don't let them click / manipulate
				if (minecraftPlayer.isViewingRecipe()) {
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
			List<MenuBehaviour> openBehaviours = menu.getBehaviours(MenuBehaviourType.OPEN);
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
			List<MenuBehaviour> closeBehaviours = menu.getBehaviours(MenuBehaviourType.CLOSE);
			if (closeBehaviours != null) {
				closeBehaviours.stream().filter(behaviour -> behaviour != null).forEach(behaviour -> behaviour.doAction(menu, player));
			}
		}
		//Get the wrapped player data
		MinecraftPlayer minecraftPlayer = playerManager.getData(player);
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
