package com.devsteady.onyx.debug;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.player.Players;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Debug handler defines what actions to perform when Chat.debug is called.
 * <p>
 * This allows debugger to go to log files, players, globally, give players a mode, or something along those lines.
 */
public class DebugHandler {

    @Getter
    @Setter
    private Logger logger = null;

    @Getter
    private Plugin parent;

    @Getter
    private boolean lateInitialization = false;

    public DebugHandler(Plugin parent) {
        this.parent = parent;

        if (!isLateInitialization()) {
            init();
        }
    }

    /**
     * Calling this void will initialize the logger for the instance of DebugHandler
     */
    public void init() {
        logger = new PluginLogger(getParent());
        logger.setUseParentHandlers(true);
    }

    public void process(String... messages) {
        Set<Player> playersInDebugMode = Players.getAllDebugging();
        Chat.messageAll(playersInDebugMode, messages);

        Stream.of(messages).forEach(msg -> logger.info(msg));
    }
}
