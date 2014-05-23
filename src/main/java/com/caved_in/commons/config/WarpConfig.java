package com.caved_in.commons.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "warp-config")
public class WarpConfig {
	@Element(name = "enable-warps-menu")
	private boolean warpsMenu;

	public WarpConfig(@Element(name = "enable-warps-menu") boolean warpsMenu) {
		this.warpsMenu = warpsMenu;
	}

	public boolean isWarpsMenuEnabled() {
		return warpsMenu;
	}

	/**
	 * Change whether or not the warps menu is enabled.
	 *
	 * @param status if true, the warps menu will be enabled, if false, it won't.
	 */
	public void setWarpsMenuEnabled(boolean status) {
		this.warpsMenu = status;
	}
}
