package com.devsteady.onyx.nms.no_implementation;

import com.devsteady.onyx.nms.ActionMessageHandler;
import org.bukkit.entity.Player;

public class ActionMessageHandlerNI implements ActionMessageHandler {
    @Override
    public void actionMessage(Player player, String message) {
        throw new IllegalAccessError("Unable to perform action messages on a Bukkit/Spigot versin < 1.8");
    }
}
