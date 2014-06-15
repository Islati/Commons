package com.caved_in.commons.menu.menus.warpselection.behaviours;

import com.caved_in.commons.Commons;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.MenuCloseBehaviour;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class CleanPaperBehaviour implements MenuCloseBehaviour {

	private static CleanPaperBehaviour instance;

	private static final String[] itemNames = new String[]{"Next Page", "Previous Page"};
	private static final Material PAPER = Material.PAPER;

	protected CleanPaperBehaviour() {

	}

	public static CleanPaperBehaviour getInstance() {
		if (instance == null) {
			instance = new CleanPaperBehaviour();
		}
		return instance;
	}

	@Override
	public void doAction(ItemMenu menu, final Player player) {
		Commons.threadManager.runTaskOneTickLater(() -> {
			PlayerInventory inventory = player.getInventory();
			for (String name : itemNames) {
				int slot = Inventories.getSlotOf(inventory, PAPER, name);
				if (slot == -1) {
					continue;
				}
				Inventories.clearSlot(inventory, slot);
			}
		});
	}
}
