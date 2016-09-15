package com.caved_in.commons.event;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.nms.NMS;
import com.caved_in.commons.nms.UnhandledStackTrace;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.joor.Reflect;

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
        Configuration config = Commons.getInstance().getConfiguration();
        Set<Player> debuggingPlayers = Players.getAllDebugging();
        Throwable eventException = e.getException();
        //If the books for stack-tracing are enabled, then give one to all the debugging players
        if (config.enableStackTraceBook()) {
            ItemStack exceptionBook = Debugger.createExceptionBook(eventException);
            debuggingPlayers.forEach(p -> Players.giveItem(p, exceptionBook));
        }

        //If the stack trace messages are to be sent in chat, send em!
        if (config.enableStackTraceChat()) {
            String[] exceptionMessages = Messages.exceptionInfo(eventException);
            //For every player that's debugging, send them the exception-info message
            debuggingPlayers.forEach(p -> Chat.message(p.getPlayer(), exceptionMessages));
        }
    }
}
