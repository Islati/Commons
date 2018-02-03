package com.devsteady.onyx.command;

import org.bukkit.command.CommandSender;


public class FlagArgument extends CommandArgument {
    private final Flag flag;

    public FlagArgument(Arg commandArgAnnotation, Class<?> argumentClass, ArgumentHandler<?> argumentHandler, Flag flag) {
        super(commandArgAnnotation, argumentClass, argumentHandler);
        this.flag = flag;
    }

    public FlagArgument(String name, String description, String def, String verifiers, Class<?> argumentClass, ArgumentHandler<?> handler, Flag flag) {
        super(name, description, def, verifiers, argumentClass, handler);
        this.flag = flag;
    }

    @Override
    public Object execute(CommandSender sender, Arguments args) throws CommandError {
        String arg;
        if (!args.flagExists(flag)) {
            arg = getDefault();
        } else if (!args.hasNext(flag)) {
            throw new CommandError("The argument s [" + getName() + "] to the flag -" + flag.getIdentifier() + " is not defined");
        } else {
            arg = CommandUtil.escapeArgumentVariable(args.nextFlagArgument(flag));
        }

        return getHandler().handle(sender, this, arg);
    }

    public Flag getFlag() {
        return flag;
    }
}
