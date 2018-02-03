package com.devsteady.onyx.command.handlers;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.command.ArgumentHandler;
import com.devsteady.onyx.command.CommandArgument;
import com.devsteady.onyx.command.CommandError;
import com.devsteady.onyx.command.TransformError;
import com.devsteady.onyx.player.MinecraftPlayer;
import com.devsteady.onyx.player.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinecraftPlayerArgumentHandler extends ArgumentHandler<MinecraftPlayer> {

    private static Onyx commons = Onyx.getInstance();

    public MinecraftPlayerArgumentHandler() {
        setMessage("player_not_online", "%1 isn't online!");

        addVariable("sender", "The command executor", (sender, argument, varName) -> {
            if (!(sender instanceof Player)) {
                throw new CommandError(Messages.CANT_AS_CONSOLE);
            }

            Player player = (Player) sender;
            MinecraftPlayer mcPlayer = commons.getPlayerHandler().getData(player);
            if (mcPlayer == null) {
                throw new CommandError("No MinecraftPlayer data exists for " + player.getName());
            }
            return mcPlayer;
        });
    }

    @Override
    public MinecraftPlayer transform(CommandSender sender, CommandArgument argument, String value) throws TransformError {
        Player player = Players.getPlayer(value);

        if (player == null) {
            throw new TransformError(Messages.playerOffline(value));
        }

        return commons.getPlayerHandler().getData(player);
    }
}
