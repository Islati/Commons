package com.devsteady.onyx.game.listener;

import com.devsteady.onyx.game.players.UserManager;
import com.devsteady.onyx.player.User;
import com.devsteady.onyx.plugin.BukkitPlugin;
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
