package com.caved_in.commons.listeners;

import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignEditListener implements Listener {

	@EventHandler
	public void onSignEdit(SignChangeEvent e) {
		String[] lines = e.getLines();

		for (int i = 0; i <= 3; i++) {
			String signLine = lines[i];
			if (signLine == null) {
				continue;
			}

			e.setLine(i, StringUtil.colorize(signLine));
		}
	}
}
