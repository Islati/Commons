package com.caved_in.commons.location;

import com.caved_in.commons.permission.Permissible;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PreTeleportLocation extends BaseLocation implements Permissible {

	private PreTeleportType type;

	public PreTeleportLocation(Location loc, PreTeleportType type) {
		super(loc);
		this.type = type;
	}

	public PreTeleportType getType() {
		return type;
	}

	@Override
	public String getPermission() {
		return getType().getPermission();
	}

	@Override
	public boolean hasPermission(Player p) {
		return p.hasPermission(getPermission());
	}
}
