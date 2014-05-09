package com.caved_in.commons.config;

import org.simpleframework.xml.Element;

public class DebugConfig {
	@Element(name = "stack-trace-event")
	private boolean stackTraceEvent = true;

	@Element(name = "stack-trace-book")
	private boolean stackTraceBooks = true;

	@Element(name = "stack-trace-chat")
	private boolean stackTraceChat = true;

	public DebugConfig(@Element(name = "stack-trace-event") boolean stackTraceEvent, @Element(name = "stack-trace-book") boolean stackTraceBooks, @Element(name = "stack-trace-chat") boolean stackTraceChat) {
		this.stackTraceEvent = stackTraceEvent;
		this.stackTraceBooks = stackTraceBooks;
		this.stackTraceChat = stackTraceChat;
	}

	public DebugConfig() {
		//Default configuration creations
	}

	public boolean isStackTraceChat() {
		return stackTraceChat;
	}

	public boolean isStackTraceBooks() {
		return stackTraceBooks;
	}

	public boolean isStackTraceEvent() {
		return stackTraceEvent;
	}
}
