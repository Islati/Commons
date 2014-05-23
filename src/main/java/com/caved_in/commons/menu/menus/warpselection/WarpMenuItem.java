package com.caved_in.commons.menu.menus.warpselection;

import com.caved_in.commons.warp.Warp;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

public class WarpMenuItem extends MenuItem {
	private static MaterialData warpIcon = new MaterialData(Material.PAPER);

	private Warp warp;

	public WarpMenuItem(Warp warp) {
		super(warp.getName(), warpIcon);
		this.warp = warp;
		setDescriptions(Arrays.asList(String.format("&aTeleport to the warp &e'%s'", warp.getName())));
	}

	@Override
	public void onClick(Player player) {
		warp.bring(player);
	}
}
