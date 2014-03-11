package com.caved_in.commons.menu.serverselection;

import com.caved_in.commons.Commons;
import com.caved_in.commons.utilities.StringUtil;
import me.xhawk87.PopupMenuAPI.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerMenuItem extends MenuItem {

	private List<String> itemLore;
	private String commandToExecute = "lobby";

	/**
	 * @param text
	 * @param icon
	 * @param itemLore
	 * @param commandToExecute
	 */
	public ServerMenuItem(String text, MaterialData icon, List<String> itemLore, String commandToExecute) {
		super(StringUtil.formatColorCodes(text), icon);
		List<String> loreSet = new ArrayList<String>();
		for (String lore : itemLore) {
			loreSet.add(StringUtil.formatColorCodes(lore));
		}
		this.itemLore = loreSet;
		this.setDescriptions(loreSet);
		this.commandToExecute = commandToExecute;
	}

	public String getCommandToExecute() {
		return this.commandToExecute;
	}

	public List<String> getItemLore() {
		return itemLore;
	}

	@Override
	public void onClick(Player player) {
		teleportServer(player, this.commandToExecute);
	}

	private void teleportServer(Player p, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (IOException eee) {
			// Fehler
		}

		p.sendPluginMessage(Commons.getInstance(), "BungeeCord", b.toByteArray());
	}
}
