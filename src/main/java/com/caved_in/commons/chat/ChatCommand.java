package com.caved_in.commons.chat;

import org.bukkit.entity.Player;

public abstract class ChatCommand {
    private String name;
    private String permission;
    private String usage;

    private String prefix = "!";

    private boolean global = false;

    public ChatCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    public ChatCommand(String name, String permission, String usage) {
        this(name, permission);
        this.usage = usage;
    }

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

    String prefix() {
        return prefix;
    }

    void setPrefix(String p) {
        this.prefix = p;
    }

    boolean global() {
        return global;
    }

    public ChatCommand name(String name) {
        this.name = name;
        return this;
    }

    public ChatCommand permission(String perm) {
        this.permission = perm;
        return this;
    }

    public ChatCommand usage(String usage) {
        this.usage = usage;
        return this;
    }

    public ChatCommand global(boolean global) {
        this.global = global;
        return this;
    }

    /**
     * The action to be performed whenever this command is called!
     *
     * @param p    player performing the chat command
     * @param args arguments that were passed to this chat command.
     */
    public abstract void perform(Player p, String[] args);

}
