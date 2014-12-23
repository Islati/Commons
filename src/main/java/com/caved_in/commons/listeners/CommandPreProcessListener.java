package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.CommandConfiguration;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProcessListener implements Listener {


	private static Commons commons = Commons.getInstance();

	private static CommandConfiguration commandConfig = Commons.getInstance().getConfiguration().getCommandConfig();

	public CommandPreProcessListener() {

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();

		String command = e.getMessage();

		//Check if they're using a bukkit command, and bukkit commands are disabled.
		if (StringUtil.startsWithIgnoreCase(command, "/bukkit:") && commandConfig.disableBukkitCommands()) {
			e.setCancelled(true);
			Chat.message(player, Messages.COMMAND_DISABLED);
			return;
		}

		//Check if they're using the /pl or /plugins command, and it's disabled
		switch (command) {
			case "/pl":
			case "/plugins":
			case "/plugin":
				if (commandConfig.disablePluginsCommand()) {
					e.setCancelled(true);
					Chat.message(player, Messages.COMMAND_DISABLED);
					return;
				}
				break;
			default:
				break;
		}

		MinecraftPlayer minecraftPlayer = commons.getPlayerHandler().getData(player);

		if (minecraftPlayer.isInDebugMode()) {
			Debugger.debugCommandPreProcessEvent(player, e);
		}
	}
}
