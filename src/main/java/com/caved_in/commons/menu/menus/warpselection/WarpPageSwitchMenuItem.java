package com.caved_in.commons.menu.menus.warpselection;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.menu.inventory.MenuItem;
import com.caved_in.commons.warp.Warps;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class WarpPageSwitchMenuItem extends MenuItem {
	public static enum Direction {
		PREVIOUS,
		NEXT
	}

	private Direction direction;
	private int page = 1;

	public WarpPageSwitchMenuItem(Direction direction, int page) {
		super(direction == Direction.NEXT ? "&eNext Page" : "&aPrevious Page");
		this.direction = direction;
		this.page = page;
	}

	@Override
	public void onClick(Player player, ClickType type) {
		int warpPageCount = Warps.getWarpPagesCount();

		switch (direction) {
			case NEXT:
				if (page >= warpPageCount) {
					Chat.message(player, "&7This is the final page, please use the &oprevious&r&7 button");
				} else {
					getMenu().switchMenu(player, WarpSelectionMenu.getMenu(page + 1));
				}
				break;
			case PREVIOUS:
				if (page <= warpPageCount) {
					Chat.message(player, "&7This is the first page, please use the &onext&r&7 button");
				} else {
					getMenu().switchMenu(player, WarpSelectionMenu.getMenu(page - 1));
				}
				break;
		}
	}
}
