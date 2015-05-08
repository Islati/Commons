package com.caved_in.commons.command;

import org.bukkit.ChatColor;

public class CommandError extends Exception {
    private static final long serialVersionUID = 1L;

    private boolean showUsage;

    public CommandError(String msg) {
        this(msg, false);
    }

    public CommandError(String msg, boolean showUsage) {
        super(msg);
        this.showUsage = showUsage;
    }

    public String getColorizedMessage() {
        String msg = getMessage();
        msg = msg.replaceAll("\\[", ChatColor.AQUA + "[");
        msg = msg.replaceAll("\\]", "]" + ChatColor.RED);
        return ChatColor.RED + msg;
    }

    public boolean showUsage() {
        return showUsage;
    }
}
