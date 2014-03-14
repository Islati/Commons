package com.caved_in.commons.menu.warpselection;

import com.caved_in.commons.warp.Warp;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 31/01/14
 * Time: 1:10 PM
 */
public class WarpMenuItem extends MenuItem {
	private static MaterialData warpIcon = new MaterialData(Material.PAPER);

	public WarpMenuItem(Warp warp) {
		super(warp.getName(), warpIcon);
	}

	@Override
	public void onClick(Player player) {

	}
}
