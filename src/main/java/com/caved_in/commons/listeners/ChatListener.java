package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private static Commons commons = Commons.getInstance();

    private Configuration config;

    public ChatListener() {
        config = commons.getConfiguration();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        //Check if the chat is silenced
        if (config.getWorldConfig().isChatSilenced()) {
            if (!Players.canChatWhileSilenced(player)) {
                //Send the player a message saying the chat's silenced
                Chat.message(player, Messages.CHAT_SILENCED);
                event.setCancelled(true);
                return;
            }
        }
        MinecraftPlayer minecraftPlayer = commons.getPlayerHandler().getData(player);

//		if (minecraftPlayer.isMuted()) {
//			event.setCancelled(true);
//
//			minecraftPlayer.actionMessage("&cYou've been muted. &7(You can appeal the mute on our forums)");
//			//todo implement message to send to players if they're muted in the config.
//			return;
//		}

        if (config.getWorldConfig().hasExternalChatHandler()) {
            return;
        }

        event.setFormat(StringUtil.formatColorCodes(String.format("&r%s - %s", (minecraftPlayer.getPrefix().isEmpty() ? player.getDisplayName() : minecraftPlayer.getPrefix() + " " + player.getDisplayName()), event.getMessage())));
        //todo Implement a node in the config to format the chat.
        //todo extend the configuration and variables that can be tweaked with chat.
    }
}
