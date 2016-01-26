package com.caved_in.commons.command;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.handlers.*;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.plugin.BukkitPlugin;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.joor.Reflect;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Method;
import java.util.*;


public class CommandHandler implements CommandExecutor {

	private JavaPlugin plugin;
	private Map<Class<?>, ArgumentHandler<?>> argumentHandlers = new HashMap<>();
	private Map<org.bukkit.command.Command, RootCommand> rootCommands = new HashMap<>();

	private HelpHandler helpHandler = new HelpHandler() {
		private String formatArgument(CommandArgument argument) {
			String def = argument.getDefault();
			if (def.equals(" ")) {
				def = "";
			} else if (def.startsWith("?")) {
				String varName = def.substring(1);
				def = argument.getHandler().getVariableUserFriendlyName(varName);
				if (def == null) {
					throw new IllegalArgumentException("The ArgumentVariable '" + varName + "' is not registered.");
				}
				def = ChatColor.GOLD + " | " + ChatColor.WHITE + def;
			} else {
				def = ChatColor.GOLD + " | " + ChatColor.WHITE + def;
			}

			return ChatColor.AQUA + "[" + argument.getName() + def + ChatColor.AQUA + "] " + ChatColor.DARK_AQUA + argument.getDescription();
		}

		@Override
		public String[] getHelpMessage(RegisteredCommand command) {
			//todo implement option to input a HelpScreen and move it to an array of strings.
			ArrayList<String> message = new ArrayList<String>();

			if (command.isSet()) {
				message.add(ChatColor.AQUA + command.getDescription());
			}

			message.add(getUsage(command));

			if (command.isSet()) {
				for (CommandArgument argument : command.getArguments()) {
					message.add(formatArgument(argument));
				}
				if (command.getWildcard() != null) {
					message.add(formatArgument(command.getWildcard()));
				}
				List<Flag> flags = command.getFlags();
				if (flags.size() > 0) {
					message.add(ChatColor.GOLD + "Flags:");
					for (Flag flag : flags) {
						StringBuilder args = new StringBuilder();
						for (FlagArgument argument : flag.getArguments()) {
							args.append(" [" + argument.getName() + "]");
						}
						message.add("-" + flag.getIdentifier() + ChatColor.AQUA + args.toString());
						for (FlagArgument argument : flag.getArguments()) {
							message.add(formatArgument(argument));
						}
					}
				}
			}


			List<RegisteredCommand> subCommands = command.getSuffixes();
			if (subCommands.size() > 0) {
				message.add(ChatColor.GOLD + "Subcommands:");
				for (RegisteredCommand scommand : subCommands) {
					message.add(scommand.getUsage());
				}
			}

			return message.toArray(new String[0]);
		}

		@Override
		//todo Change this to a help screen! Also, use JSON to make hoverable / clickable
		//info.
		public String getUsage(RegisteredCommand command) {
			StringBuilder usage = new StringBuilder();
			usage.append(command.getLabel());

			RegisteredCommand parent = command.getParent();
			while (parent != null) {
				usage.insert(0, parent.getLabel() + " ");
				parent = parent.getParent();
			}

			usage.insert(0, "/");

			if (!command.isSet()) {
				return usage.toString();
			}

			usage.append(ChatColor.AQUA);

			for (CommandArgument argument : command.getArguments()) {
				usage.append(" [" + argument.getName() + "]");
			}

			usage.append(ChatColor.WHITE);

			for (Flag flag : command.getFlags()) {
				usage.append(" (-" + flag.getIdentifier() + ChatColor.AQUA);
				for (FlagArgument arg : flag.getArguments()) {
					usage.append(" [" + arg.getName() + "]");
				}
				usage.append(ChatColor.WHITE + ")");
			}

			if (command.getWildcard() != null) {
				usage.append(ChatColor.AQUA + " [" + command.getWildcard().getName() + "]");
			}

			return usage.toString();
		}
	};

	private String helpSuffix = "help";

	public CommandHandler(JavaPlugin plugin) {
		this.plugin = plugin;

		registerArgumentHandler(String.class, new StringArgumentHandler());

		registerArgumentHandler(int.class, new IntegerArgumentHandler());

		registerArgumentHandler(double.class, new DoubleArgumentHandler());

		registerArgumentHandler(boolean.class, new BooleanArgumentHandler());

		registerArgumentHandler(Player.class, new PlayerArgumentHandler());

		registerArgumentHandler(World.class, new WorldArgumentHandler());

		registerArgumentHandler(EntityType.class, new EntityTypeArgumentHandler());

		registerArgumentHandler(Material.class, new MaterialArgumentHandler());

		//Allow the getting of Commons MinecraftPlayer object.
		registerArgumentHandler(MinecraftPlayer.class, new MinecraftPlayerArgumentHandler());

		//Allow the getting of MaterialData, via id:data and id values!
		registerArgumentHandler(MaterialData.class, new MaterialDataArgumentHandler());

		//Allow the getting of Enchantments via their name
		registerArgumentHandler(Enchantment.class, new EnchantmentArgumentHandler());

		//Allow creation of ItemStacks via arguments, and also has variables so you
		//can default to an itemstack using item names, ids, and id:data values!
		registerArgumentHandler(ItemStack.class, new ItemStackArgumentHandler());

	}

	@SuppressWarnings("unchecked")
	public <T> ArgumentHandler<? extends T> getArgumentHandler(Class<T> clazz) {
		return (ArgumentHandler<? extends T>) argumentHandlers.get(clazz);
	}

	public HelpHandler getHelpHandler() {
		return helpHandler;
	}

	public <T> void registerArgumentHandler(Class<? extends T> clazz, ArgumentHandler<T> argHandler) {
		if (argumentHandlers.get(clazz) != null) {
			throw new IllegalArgumentException("The is already a ArgumentHandler bound to the class " + clazz.getName() + ".");
		}

		argHandler.handler = this;
		argumentHandlers.put(clazz, argHandler);
	}

	public void registerCommands(Object... commands) {
		for (Object o : commands) {
			try {
				registerCommand(o);
			} catch (RegisterCommandMethodException e) {
				e.printStackTrace();
			}
		}
	}

	public void registerCommand(Object commands) {
		for (Method method : commands.getClass().getDeclaredMethods()) {
			Command commandAnno = method.getAnnotation(Command.class);
			if (commandAnno == null) {
				continue;
			}

			String[] identifiers = commandAnno.identifier().split(" ");
			if (identifiers.length == 0) {
				throw new RegisterCommandMethodException(method, "Invalid identifiers");
			}

			PluginCommand rootPcommand = plugin.getCommand(identifiers[0]);

			if (rootPcommand == null) {
				throw new RegisterCommandMethodException(method, "The rootcommand (the first identifier) is not registerd in the plugin.yml");
			}

			if (rootPcommand.getExecutor() != this) {
				rootPcommand.setExecutor(this);
			}

			RootCommand rootCommand = rootCommands.get(rootPcommand);

			if (rootCommand == null) {
				rootCommand = new RootCommand(rootPcommand, this);
				rootCommands.put(rootPcommand, rootCommand);
			}

			RegisteredCommand mainCommand = rootCommand;

			for (int i = 1; i < identifiers.length; i++) {
				String suffix = identifiers[i];
				if (mainCommand.doesSuffixCommandExist(suffix)) {
					mainCommand = mainCommand.getSuffixCommand(suffix);
				} else {
					RegisteredCommand newCommand = new RegisteredCommand(suffix, this, mainCommand);
					mainCommand.addSuffixCommand(suffix, newCommand);
					mainCommand = newCommand;
				}
			}

			mainCommand.set(commands, method);
		}
	}

	/**
	 * Iterate through all the classes inside a package, and determine if it's a class that has
	 * {@link com.caved_in.commons.command.Command} annotations available on any of its methods. If so, attempt to register them.
	 *
	 * @param pkg Package to scan classes which contain {@link com.caved_in.commons.command.Command} annotations.
	 */
	public void registerCommandsByPackage(String pkg) {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
				.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pkg))));

		Set<Class<?>> commandClasses = reflections.getSubTypesOf(Object.class);


		Set<Object> commandClassInstances = new HashSet<>();

		StringBuilder commandsRegistered = new StringBuilder();

		for (Class<?> clazz : commandClasses) {
			Set<Method> commandMethods = ReflectionUtils.getMethods(clazz, ReflectionUtils.withAnnotation(Command.class));
			if (commandMethods.isEmpty()) {
				continue;
			}

			commandClassInstances.add(Reflect.on(clazz).create().get());
			commandsRegistered.append(String.format("%s", StringUtils.substringAfterLast(clazz.getCanonicalName(), "."))).append(",");
		}

		if (commandClassInstances.isEmpty()) {
			return;
		}

		for (Object commandObj : commandClassInstances) {
			registerCommand(commandObj);
		}

		if (plugin instanceof BukkitPlugin) {
			((BukkitPlugin) plugin).getPluginLogger().info("Registered the following commands: " + commandsRegistered.toString());
		} else {
			plugin.getLogger().info("Registered the following commands: " + commandsRegistered.toString());
		}
	}

	public void setHelpHandler(HelpHandler helpHandler) {
		this.helpHandler = helpHandler;
	}

	public String getHelpSuffix() {
		return helpSuffix;
	}

	public void setHelpSuffix(String suffix) {
		this.helpSuffix = suffix;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		RootCommand rootCommand = rootCommands.get(command);
		if (rootCommand == null) {
			return false;
		}

		if (rootCommand.onlyPlayers() && !(sender instanceof Player)) {
			Chat.message(sender, "&cSorry, but only players can execute this command.");
			return true;
		}

		rootCommand.execute(sender, args);

		return true;
	}
}
