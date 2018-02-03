package com.devsteady.onyx.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Flag implements ExecutableArgument {
    private final String identifier;
    private final String description;
    private List<FlagArgument> arguments = new ArrayList<FlagArgument>();

    public Flag(String identifier, String description) {
        this.identifier = identifier;
        this.description = description;
    }

    public void addArgument(FlagArgument argument) {
        arguments.add(argument);
    }

    @Override
    public Object execute(CommandSender sender, Arguments args) {
        return args.flagExists(this);
    }

    public List<FlagArgument> getArguments() {
        return arguments;
    }

    public String getDescription() {
        return description;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }
}
