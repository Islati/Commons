package com.caved_in.commons.event;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.config.DebugConfig;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StackTraceEvent extends Event {
	private static final HandlerList handler = new HandlerList();
	private Exception exception;

	public StackTraceEvent(Exception ex) {
		this.exception = ex;
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}

	public static HandlerList getHandlerList() {
		return handler;
	}

	public Exception getException() {
		return exception;
	}

	public static void handle(StackTraceEvent e) {
		DebugConfig debugConfig = Commons.getConfiguration().getDebugConfig();
		//If they've disabled stack-trace-events in the config, don't handle this
		if (!debugConfig.isStackTraceEvent()) {
			return;
		}

		Stream<Player> debuggingPlayers = Players.getAllDebugging().stream();
		Exception eventException = e.getException();
		//If the books for stack-tracing are enabled, then give one to all the debugging players
		if (debugConfig.isStackTraceBooks()) {
			ItemStack exceptionBook = Debugger.createExceptionBook(eventException);
			//YAY! MY FIRST JAVA LAMBDA STATEMENT
			debuggingPlayers.forEach(p -> Players.giveItem(p, exceptionBook));
		}

		//If the stack trace messages are to be sent in chat, send em!
		if (debugConfig.isStackTraceChat()) {
			List<String> stackMessages = Arrays.asList(Messages.exceptionInfo(eventException));
			//For every player that's debugging, send them the exception-info message
			debuggingPlayers.forEach(p -> Players.sendMessage(p, stackMessages));
		}
	}

	public static void handle(Exception e) {
		handle(new StackTraceEvent(e));
	}
}
