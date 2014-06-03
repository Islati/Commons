package com.caved_in.commons.menu.menus.warpselection;

import com.caved_in.commons.Commons;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.MenuBehaviourType;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.menu.menus.warpselection.behaviours.CleanPaperBehaviour;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.warp.Warps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpSelectionMenu extends ItemMenu {
	public static final int START_INDEX = 0;
	public static final int END_INDEX = 54;
	private static final Map<Integer, WarpSelectionMenu> warpMenus = new HashMap<>();

	protected WarpSelectionMenu(int page) {
		super(String.format("Warps Selection (Page %s)", page), Menus.getRows(END_INDEX));
		addMenuItem(new WarpPageSwitchMenuItem(WarpPageSwitchMenuItem.Direction.PREVIOUS, page), 45);
		addMenuItem(new WarpPageSwitchMenuItem(WarpPageSwitchMenuItem.Direction.NEXT, page), 53);
		setExitOnClickOutside(false);
		List<Warp> warpPages = Warps.getWarpsPage(page);
		for (int i = 0; i < warpPages.size(); i++) {
			addMenuItem(new WarpMenuItem(warpPages.get(i)), i >= 45 ? i + 1 : i);
		}
		addBehaviour(MenuBehaviourType.CLOSE, CleanPaperBehaviour.getInstance());
	}

	public static WarpSelectionMenu getMenu(int page) {
		if (!warpMenus.containsKey(page) || Warps.isUpdated()) {
			warpMenus.put(page, new WarpSelectionMenu(page));
			Commons.debug(String.format("Added menu #%s to menus map", page));
			Warps.setUpdated(false);
		}

		return warpMenus.get(page);
	}


}
