package com.caved_in.commons.config;

import org.simpleframework.xml.ElementMap;

import java.util.HashMap;
import java.util.Map;

public class CommandConfiguration {

	@ElementMap(name = "commands", entry = "command", key = "name", attribute = true, inline = true)
	private Map<String, Boolean> commandsMap = new HashMap<>();

	public CommandConfiguration(@ElementMap(name = "commands", entry = "command", key = "name", attribute = true, inline = true) Map<String, Boolean> commandsMap) {
		this.commandsMap = commandsMap;
	}

	public boolean isCommandEnabled(String command) {
		if (!commandsMap.containsKey(command)) {
			return false;
		}
		return commandsMap.get(command);
	}
}