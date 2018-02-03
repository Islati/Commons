package com.devsteady.onyx.command;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;


public class CommandArgument implements ExecutableArgument {
    private final String name;
    private final String description;
    private final String def;
    private final Map<String, String[]> verifyArguments;
    private final ArgumentHandler<?> handler;
    private final Class<?> argumentClass;

    private Map<String, String> overrideMessages = new HashMap<String, String>();

    public CommandArgument(Arg commandArgAnnotation, Class<?> argumentClass, ArgumentHandler<?> argumentHandler) {
        this(commandArgAnnotation.name(), commandArgAnnotation.description(), commandArgAnnotation.def(), commandArgAnnotation.verifiers(), argumentClass, argumentHandler);
    }

    public CommandArgument(String name, String description, String def, String verifiers, Class<?> argumentClass, ArgumentHandler<?> handler) {
        this.name = name;
        this.description = description;
        this.def = def;
        this.verifyArguments = CommandUtil.parseVerifiers(verifiers);
        this.handler = handler;
        this.argumentClass = argumentClass;
    }

    @Override
    public Object execute(CommandSender sender, Arguments args) throws CommandError {
        String arg;
        if (!args.hasNext()) {
            if (def.equals(" ")) {
                throw new CommandError("The argument [" + name + "] is not defined (it has no default value)", true);
            }

            arg = def;
        } else {
            arg = CommandUtil.escapeArgumentVariable(args.nextArgument());
        }

        return handler.handle(sender, this, arg);
    }

    private String formatMessage(String msg, String[] vars) {
        msg = msg.replace("%p", name);

        for (int i = 1; i <= vars.length; i++) {
            msg = msg.replace("%" + i, vars[i - 1]);
        }

        return msg.replaceAll("%\\d+", "<variable not available>");
    }

    public Class<?> getArgumentClass() {
        return argumentClass;
    }

    public String getDefault() {
        return def;
    }

    public String getDescription() {
        return description;
    }

    public ArgumentHandler<?> getHandler() {
        return handler;
    }

    public String getMessage(String node) {
        return getMessage(node, new String[0]);
    }

    public String getMessage(String node, String... vars) {
        String msg = overrideMessages.get(node);

        if (msg != null) {
            return formatMessage(msg, vars);
        }

        msg = handler.getMessage(node);

        if (msg != null) {
            return formatMessage(msg, vars);
        }

        throw new IllegalArgumentException("The node \"" + node + "\" is not available.");
    }

    public String getName() {
        return name;
    }

    public Map<String, String[]> getVerifyArguments() {
        return verifyArguments;
    }
}
