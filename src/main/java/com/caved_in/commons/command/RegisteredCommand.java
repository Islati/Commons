package com.caved_in.commons.command;

import com.caved_in.commons.permission.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class RegisteredCommand {
    private String label;
    private RegisteredCommand parent;
    private String description;
    private String[] permissions;
    private boolean onlyPlayers;
    private Method method;
    private Object methodInstance;
    private CommandHandler handler;

    private boolean set = false;

    private ArrayList<ExecutableArgument> methodArguments = new ArrayList<ExecutableArgument>();
    private ArrayList<CommandArgument> arguments = new ArrayList<CommandArgument>();
    private ArrayList<RegisteredCommand> suffixes = new ArrayList<RegisteredCommand>();
    private ArrayList<Flag> flags = new ArrayList<Flag>();
    private WildcardArgument wildcard;
    private Map<String, Flag> flagsByName = new LinkedHashMap<String, Flag>();
    private Map<String, RegisteredCommand> suffixesByName = new HashMap<String, RegisteredCommand>();

    RegisteredCommand(String label, CommandHandler handler, RegisteredCommand parent) {
        this.label = label;
        this.handler = handler;
        this.parent = parent;
    }

    void addSuffixCommand(String suffix, RegisteredCommand command) {
        suffixesByName.put(suffix.toLowerCase(), command);
        suffixes.add(command);
    }

    boolean doesSuffixCommandExist(String suffix) {
        return suffixesByName.get(suffix) != null;
    }

    void execute(CommandSender sender, String[] args) {
        if (!testPermission(sender)) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to do this!");
            return;
        }

        if (args.length > 0) {
            String suffixLabel = args[0].toLowerCase();
            if (suffixLabel.equals(handler.getHelpSuffix())) {
                sendHelpMessage(sender);
                return;
            }

            RegisteredCommand command = suffixesByName.get(suffixLabel);
            if (command == null) {
                executeMethod(sender, args);
            } else {
                String[] nargs = new String[args.length - 1];
                System.arraycopy(args, 1, nargs, 0, args.length - 1);
                command.execute(sender, nargs);
            }
        } else {
            executeMethod(sender, args);
        }

    }

    private void executeMethod(CommandSender sender, String[] args) {
        if (!set) {
            sendHelpMessage(sender);
            return;
        }

        ArrayList<Object> resultArgs = new ArrayList<>();
        resultArgs.add(sender);

        Arguments arguments;
        try {
            arguments = new Arguments(args, flagsByName);
        } catch (CommandError e) {
            sender.sendMessage(e.getColorizedMessage());
            return;
        }

        for (ExecutableArgument ea : this.methodArguments) {
            try {
                resultArgs.add(ea.execute(sender, arguments));
            } catch (CommandError e) {
                sender.sendMessage(e.getColorizedMessage());
                if (e.showUsage()) {
                    sender.sendMessage(getUsage());
                }
                return;
            }
        }

        try {
            try {
                method.invoke(methodInstance, resultArgs.toArray());
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof CommandError) {
                    CommandError ce = (CommandError) e.getCause();
                    sender.sendMessage(ce.getColorizedMessage());
                    if (ce.showUsage()) {
                        //todo: generate helpscreen from this.
                        sender.sendMessage(getUsage());
                    }
                } else {
                    throw e;
                }
            }
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command.");
            e.printStackTrace();
        }
    }

    private ArgumentHandler<?> getArgumenHandler(Class<?> argumentClass) {
        ArgumentHandler<?> argumentHandler = handler.getArgumentHandler(argumentClass);

        if (argumentHandler == null) {
            throw new RegisterCommandMethodException(method, "Could not find a ArgumentHandler for (" + argumentClass.getName() + ")");
        }

        return argumentHandler;
    }

    public List<CommandArgument> getArguments() {
        return arguments;
    }

    public String getDescription() {
        return description;
    }

    public List<Flag> getFlags() {
        return flags;
    }

    public String[] getHelpMessage() {
        return handler.getHelpHandler().getHelpMessage(this);
    }

    public String getLabel() {
        return label;
    }

    public RegisteredCommand getParent() {
        return parent;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public RegisteredCommand getSuffixCommand(String suffix) {
        return suffixesByName.get(suffix);
    }

    public List<RegisteredCommand> getSuffixes() {
        return suffixes;
    }

    public String getUsage() {
        return handler.getHelpHandler().getUsage(this);
    }

    public WildcardArgument getWildcard() {
        return wildcard;
    }

    public boolean isOnlyPlayers() {
        return onlyPlayers;
    }

    public boolean isSet() {
        return set;
    }

    public boolean onlyPlayers() {
        return onlyPlayers;
    }

    public void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(getHelpMessage());
    }

    void set(Object methodInstance, Method method) {
        this.methodInstance = methodInstance;
        this.method = method;
        method.setAccessible(true);
        Command command = method.getAnnotation(Command.class);
        Flags flagsAnnotation = method.getAnnotation(Flags.class);
        this.description = command.description();
        this.permissions = command.permissions();
        this.onlyPlayers = command.onlyPlayers();

        Class<?>[] methodParameters = method.getParameterTypes();

        if (methodParameters.length == 0 ? true : !CommandSender.class.isAssignableFrom(methodParameters[0])) {
            throw new RegisterCommandMethodException(method, "The first parameter in the command method must be assignable to the CommandSender interface.");
        }

        if (flagsAnnotation != null) {
            String[] flags = flagsAnnotation.identifier();
            String[] flagdescriptions = flagsAnnotation.description();

            for (int i = 0; i < flags.length; i++) {
                Flag flag = new Flag(flags[i], i < flagdescriptions.length ? flagdescriptions[i] : "");
                this.flagsByName.put(flags[i], flag);
                this.flags.add(flag);
            }
        }

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        for (int i = 1; i < methodParameters.length; i++) {

            //Find the CommandArgument annotation
            Arg commandArgAnnotation = null;
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation.annotationType() == Arg.class) {
                    commandArgAnnotation = (Arg) annotation;
                }
            }

            //Find the FlagArg annotation
            FlagArg flagArgAnnotation = null;
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation.annotationType() == FlagArg.class) {
                    flagArgAnnotation = (FlagArg) annotation;
                }
            }

            //If neither does not exist throw
            if (commandArgAnnotation == null && flagArgAnnotation == null) {
                throw new RegisterCommandMethodException(method, "The command annonation is present on a method, however one of the parameters is not annotated.");
            }

            Flag flag = null;

            if (flagArgAnnotation != null) {
                flag = this.flagsByName.get(flagArgAnnotation.value());
                if (flag == null) {
                    throw new RegisterCommandMethodException(method, "The flag annonation is present on a parameter, however the flag is not defined in the flags annonation.");
                }
            }

            Class<?> argumentClass = methodParameters[i];

            if (commandArgAnnotation == null) {
                if (argumentClass != boolean.class && argumentClass != Boolean.class) {
                    throw new RegisterCommandMethodException(method, "The flag annonation is present on a parameter without the arg annonation, however the parameter type is not an boolean.");
                }

                methodArguments.add(flag);

                continue;
            }


            if (flagArgAnnotation == null) {
                CommandArgument argument;
                if (i == methodParameters.length - 1) {
                    //Find the Wildcard annotation
                    Wildcard wildcard = null;
                    for (Annotation annotation : parameterAnnotations[i]) {
                        if (annotation.annotationType() == Wildcard.class) {
                            wildcard = (Wildcard) annotation;
                        }
                    }

                    if (wildcard != null) {
                        boolean join = wildcard.join();
                        if (!join) {
                            argumentClass = argumentClass.getComponentType();
                            if (argumentClass == null) {
                                throw new RegisterCommandMethodException(method, "The wildcard argument needs to be an array if join is false.");
                            }
                        }
                        this.wildcard = new WildcardArgument(commandArgAnnotation, argumentClass, getArgumenHandler(argumentClass), join);
                        argument = this.wildcard;

                    } else {
                        argument = new CommandArgument(commandArgAnnotation, argumentClass, getArgumenHandler(argumentClass));
                        arguments.add(argument);
                    }
                } else {
                    argument = new CommandArgument(commandArgAnnotation, argumentClass, getArgumenHandler(argumentClass));
                    arguments.add(argument);
                }

                methodArguments.add(argument);
            } else {
                FlagArgument argument = new FlagArgument(commandArgAnnotation, argumentClass, getArgumenHandler(argumentClass), flag);
                methodArguments.add(argument);
                flag.addArgument(argument);
            }
        }

        this.set = true;
    }

    public boolean testPermission(CommandSender sender) {
        return Permissions.hasPermissions(sender, this.permissions);
    }

}
