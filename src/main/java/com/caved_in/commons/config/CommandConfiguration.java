package com.caved_in.commons.config;

import org.simpleframework.xml.Element;

/**
 * Used internally to handle commands. Unimplemented currently.
 */

//todo implement into main config.
public class CommandConfiguration {

    @Element(name = "disable-bukkit-commands")
    private boolean disableBukkitCommands = true;

    @Element(name = "disable-plugins-command")
    private boolean disablePluginsCommand = true;

    public CommandConfiguration(@Element(name = "disable-bukkit-commands") boolean disableBukkitCommands, @Element(name = "disable-plugins-command") boolean disablePluginsCommand) {
        this.disableBukkitCommands = disableBukkitCommands;
        this.disablePluginsCommand = disablePluginsCommand;
    }

    public CommandConfiguration() {
    }

    public boolean disableBukkitCommands() {
        return disableBukkitCommands;
    }

    public boolean disablePluginsCommand() {
        return disablePluginsCommand;
    }

    public void setDisableBukkitCommands(boolean disableBukkitCommands) {
        this.disableBukkitCommands = disableBukkitCommands;
    }

    public void setDisablePluginsCommand(boolean disablePluginsCommand) {
        this.disablePluginsCommand = disablePluginsCommand;
    }
}