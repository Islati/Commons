package com.caved_in.commons.game.listener;

import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.player.User;
import com.caved_in.commons.plugin.BukkitPlugin;
import org.bukkit.entity.Player;

public class UserManagerListener extends AbstractUserManagerListener {

    public UserManagerListener(BukkitPlugin plugin, UserManager userManager) {
        super(plugin,userManager);
    }

    @Override
    public void handleJoin(Player player) {
        getUserManager().addUser(player);
    }

    @Override
    public void handleLeave(Player player) {
        User user = getUserManager().getUser(player);
        if (user == null) {
            getPlugin().getPluginLogger().severe("Unable to retrieve User data (" + getUserManager().getUserClass().getCanonicalName() + ") for player " + player.getName());
            return;
        }

        user.destroy();

        getUserManager().removeUser(player);
    }
}
