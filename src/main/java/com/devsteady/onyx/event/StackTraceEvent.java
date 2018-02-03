package com.devsteady.onyx.event;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.config.Configuration;
import com.devsteady.onyx.nms.NMS;
import com.devsteady.onyx.nms.UnhandledStackTrace;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.plugin.Plugins;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

public class StackTraceEvent extends Event {
    private static UnhandledStackTrace errorHandler = NMS.getStackTraceHandler();

    private static final HandlerList handler = new HandlerList();
    private Throwable throwable;

    public StackTraceEvent(Throwable ex) {
        this.throwable = ex;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }

    public Throwable getException() {
        return throwable;
    }

    public static void call(Throwable throwable) {
        Chat.messageConsole("Calling Stack Trace Event!");
        StackTraceEvent event = new StackTraceEvent(throwable);
        Plugins.callEvent(event);
        handle(event);
    }

    public static void handle(StackTraceEvent e) {
        Configuration config = Onyx.getInstance().getConfiguration();
        Set<Player> debuggingPlayers = Players.getAllDebugging();
        Throwable eventException = e.getException();

        //If the stack trace messages are to be sent in chat, send em!
        if (config.enableStackTraceChat()) {
            String[] exceptionMessages = Messages.exceptionInfo(eventException);
            //For every player that's debugging, send them the exception-info message
            debuggingPlayers.forEach(p -> Chat.message(p.getPlayer(), exceptionMessages));
        }

        /*
        By default we also want to message our console!
         */
        Chat.messageConsole(Messages.exceptionInfo(eventException));
    }
}
