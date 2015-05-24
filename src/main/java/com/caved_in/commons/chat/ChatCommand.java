package com.caved_in.commons.chat;

import org.bukkit.entity.Player;

public class ChatCommand {
	private String name;
	private String permission;
	private String usage;

	private boolean global = false;

	/**
	 * The actual identifier of the command, used to getcha bake on boi
	 *
	 * @return the name of the strain you brewed your command with.
	 */
	String name() {
		return name;
	}

	/**
	 * The permission required to execute this command.
	 *
	 * @return
	 */
	String permission() {
		return permission;
	}

	String usage() {
		return usage;
	}

	boolean global() {
		return global;
	}

	/**
	 * The action to be performed whenever this command is called!
	 *
	 * @param p
	 * @param args
	 */
	void perform(Player p, String[] args) {
	}

}
