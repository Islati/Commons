package com.caved_in.commons;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.caved_in.commons.commands.CommandRegister;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.listeners.BlockBreakListener;
import com.caved_in.commons.listeners.BlockFormListener;
import com.caved_in.commons.listeners.BlockSpreadListener;
import com.caved_in.commons.listeners.ChatListener;
import com.caved_in.commons.listeners.CommandPreProcessListener;
import com.caved_in.commons.listeners.CompassListener;
import com.caved_in.commons.listeners.FireSpreadListener;
import com.caved_in.commons.listeners.FoodChangeListener;
import com.caved_in.commons.listeners.InventoryListener;
import com.caved_in.commons.listeners.ItemDropListener;
import com.caved_in.commons.listeners.ItemPickupListener;
import com.caved_in.commons.listeners.LauncherListener;
import com.caved_in.commons.listeners.LightningStrikeListener;
import com.caved_in.commons.listeners.NPCListener;
import com.caved_in.commons.listeners.PlayerJoinListener;
import com.caved_in.commons.listeners.PlayerKickListener;
import com.caved_in.commons.listeners.PlayerLoginListener;
import com.caved_in.commons.listeners.PlayerQuitListener;
import com.caved_in.commons.listeners.PlayerTeleportListener;
import com.caved_in.commons.listeners.PrePlayerLoginListener;
import com.caved_in.commons.listeners.ServerPingListener;
import com.caved_in.commons.listeners.ThungerChangeListener;
import com.caved_in.commons.listeners.WeatherChangeListener;
import com.caved_in.commons.listeners.WorldLoadedListener;
import com.caved_in.commons.menus.serverselection.ServerMenuGenerator;
import com.caved_in.commons.menus.serverselection.ServerMenuWrapper;
import com.caved_in.commons.npc.EntityHuman;
import com.caved_in.commons.npc.NPC;
import com.caved_in.commons.npc.injector.PlayerInjector;
import com.caved_in.commons.npc.utils.PacketFactory;
import com.caved_in.commons.npc.utils.PlayerUtil;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.sql.BansSQL;
import com.caved_in.commons.sql.DisguiseSQL;
import com.caved_in.commons.sql.FriendSQL;
import com.caved_in.commons.sql.PlayerSQL;
import com.caved_in.commons.threading.RunnableManager;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.warps.WarpManager;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Commons extends JavaPlugin {

	/** NPC Stuff **/

	private static Commons INSTANCE;

	public BiMap<Integer, EntityHuman> LOOKUP = HashBiMap.create();
	
	public NPC spawnNPC(Location location, String name) {
        if(name.length() > 16) {
            messageConsole("NPC's can't have names longer than 16 characters!");
            String tmp = name.substring(0, 16);
            messageConsole("Name: " + name + " has been shortened to: " + tmp);
            name = tmp;
        }

        int id = getNextID();
        EntityHuman human = new EntityHuman(location, name, id);
        LOOKUP.put(id, human);
        updateNPC(human);
        return human;
    }

    public boolean isNPC(int id) {
        return LOOKUP.containsKey(id) ? true : false;
    }

    public NPC getNPC(int id) {
        if(isNPC(id)) {
            return LOOKUP.get(id);
        }
        messageConsole("Failed to return NPC with id: " + id);
        return null;
    }

    int nextID = Integer.MIN_VALUE;
    protected int getNextID() {
        int id =+ nextID++;
        for(World world : Bukkit.getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if (entity.getEntityId() != id) {
                    return id;
                } else {
                    return getNextID();
                }
            }
        }
        return id;
    }

    public static Commons getInstance() {
        if(INSTANCE == null) {
        	messageConsole("INSTANCE is NULL!");
            return null;
        }
        return INSTANCE;
    }

    public void startUp() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerInjector.injectPlayer(player);
        }
    }

    public void shutdown() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerInjector.uninjectPlayer(player);
        }
    }

    public void updatePlayer(Player player) {
        for(NPC npc : LOOKUP.values()) {
            if(npc.getLocation().getWorld().equals(player.getWorld())) {
                PlayerUtil.sendPacket(player, PacketFactory.craftSpawnPacket(npc));

                if(!PacketFactory.craftEquipmentPacket(npc).isEmpty()) {
                    for(Object packet : PacketFactory.craftEquipmentPacket(npc)) {
                        PlayerUtil.sendPacket(player, packet);
                    }
                }

                if(npc.isSleeping()) {
                    PlayerUtil.sendPacket(player, PacketFactory.craftSleepPacket(npc));
                }
            }
        }
    }

    public void updateNPC(NPC npc) {
        updateNPC(npc, PacketFactory.craftSpawnPacket(npc));

        for(Object packet : PacketFactory.craftEquipmentPacket(npc)) {
            updateNPC(npc, packet);
        }
    }

    public void updateNPC(NPC npc, Object packet) {
        if(packet == null)
            return;

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getWorld().equals(npc.getLocation().getWorld())) {
                PlayerUtil.sendPacket(player, packet);
            }
        }
    }

    public void despawn(NPC npc) {
        npc.despawn();
        LOOKUP.inverse().remove(npc);
    }

    public void despawn(int id) {
        LOOKUP.get(id).despawn();
    }

    public static Commons getCommons(Plugin plugin) {
        if(INSTANCE == null) {
            messageConsole("Common NPC-Lib is disabled! Cannot return a valid NPC-Lib!");
            return null;
        }
        return INSTANCE;
    }

	/** Normal Stuff **/

	public static BansSQL bansDatabase;
	public static DisguiseSQL disguiseDatabase;
	public static FriendSQL friendDatabase;
	public static PlayerSQL playerDatabase;
	public static RunnableManager threadManager;
	public static ServerMenuWrapper serverMenu;
	public static String WARP_DATA_FOLDER = "plugins/Tunnels-Common/Warps/";
	private static Commons plugin;
	private static Configuration globalConfig = new Configuration();
	private static String PLUGIN_DATA_FOLDER = "plugins/Tunnels-Common/";

	public static Commons getCommons() {
		if (plugin == null) {
			plugin = (Commons) Bukkit.getPluginManager().getPlugin(
					"Tunnels-Common");
		}
		return plugin;
	}

	public static void messageConsole(String message) {
		Bukkit.getConsoleSender().sendMessage(StringUtil.formatColorCodes(message));
	}

	public static boolean reloadConfiguration() {
		return getCommons().initConfiguration();
	}

	public static Configuration getConfiguration() {
		return globalConfig;
	}
	
	@Override
	public void onEnable() {
		/** NPC **/
		
		startUp();
		INSTANCE = this;

		for (NPC npc : LOOKUP.values()) {
			updateNPC(npc);
		}
		/** Normal **/

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		threadManager = new RunnableManager(this); // New Thread handler

		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdir();
		}

		File warpsFolder = new File(WARP_DATA_FOLDER);
		if (!warpsFolder.exists()) {
			warpsFolder.mkdirs();
		}

		initConfiguration(); // Init config

		SqlConfiguration sqlConfig = globalConfig.getSqlConfig();

		// Init connection to bans SQL
		bansDatabase = new BansSQL(sqlConfig);
		// Init Disguise sql
		disguiseDatabase = new DisguiseSQL(sqlConfig);
		// Init friends sql
		friendDatabase = new FriendSQL(sqlConfig);
		// Initialize the players sql
		playerDatabase = new PlayerSQL(sqlConfig);
		// Register commands
		CommandRegister.registerCommands();

		registerListeners(); // Register all our event listeners

		// Load all the warps
		WarpManager.loadWarps();

		threadManager.registerSynchRepeatTask("sqlRefresh", new Runnable() {
			@Override
			public void run() {
				bansDatabase.refreshConnection();
				disguiseDatabase.refreshConnection();
				friendDatabase.refreshConnection();
				playerDatabase.refreshConnection();
			}
		}, 36000, 36000); // SQL Keep alive

		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerHandler.addData(player);
			if (globalConfig.getWorldConfig().isCompassMenuEnabled()) {
				if (!player.getInventory().contains(Material.COMPASS)) {
					player.getInventory().addItem(
							ItemHandler.makeItemStack(Material.COMPASS,
									ChatColor.GREEN + "Server Selector"));
				}
			}
		}
	}

	private void registerListeners() {
		
		if (globalConfig.getNPCSEnabled()) {
			registerListener(new NPCListener());
			messageConsole("&aRegistered the NPC Listener");
		}
		
		WorldConfiguration worldConfig = globalConfig.getWorldConfig();

		if (!worldConfig.hasExternalChatHandler()) {
			registerListener(new ChatListener());
			messageConsole("&aUsing Tunnels-Common Chat Listener");
		}

		if (worldConfig.isCompassMenuEnabled()) {
			serverMenu = new ServerMenuWrapper("Server Selection",
					ServerMenuGenerator.generateMenuItems(Commons
							.getConfiguration().getItemMenuConfig()
							.getXmlItems()));
			registerListener(new CompassListener());
			messageConsole("&aRegistered the compass-menu listener");
		}

		if (worldConfig.hasLaunchpadPressurePlates()) {
			registerListener(new LauncherListener()); // Register launch pad
														// listener if its
														// enabled
			messageConsole("&aRegistered the launch pad listener");
		}

		if (worldConfig.isIceSpreadDisabled()
				|| worldConfig.isSnowSpreadDisabled()) {
			registerListener(new BlockFormListener());
			messageConsole("&aRegistered the block spread listener");
		}

		if (worldConfig.isMyceliumSpreadDisabled()) {
			registerListener(new BlockSpreadListener());
			messageConsole("&aRegistered the mycelium spread listener");
		}

		if (worldConfig.isThunderDisabled()) {
			registerListener(new ThungerChangeListener());
			messageConsole("&aRegistered the thunder listener");
		}

		if (worldConfig.isWeatherDisabled()) {
			registerListener(new WeatherChangeListener());
			messageConsole("&aRegistered the Weather-Change listener");
		}

		if (worldConfig.isLightningDisabled()) {
			registerListener(new LightningStrikeListener());
			messageConsole("&aRegistered the lightning listener");
		}

		if (worldConfig.isFireSpreadDisabled()) {
			registerListener(new FireSpreadListener());
			messageConsole("&aRegistered the fire-spread listener");
		}

		if (!worldConfig.isBlockBreakEnabled()) {
			registerListener(new BlockBreakListener());
			messageConsole("&aRegistered the block break listener");
		}

		if (!worldConfig.isItemDropEnabled()) {
			registerListener(new ItemDropListener());
			messageConsole("&aRegistered the item-drop listener");
		}

		if (!worldConfig.isItemPickupEnabled()) {
			registerListener(new ItemPickupListener());
			messageConsole("&aRegistered the item-pickup listener");
		}

		if (!worldConfig.isFoodChangeEnabled()) {
			registerListener(new FoodChangeListener());
			messageConsole("&aRegistered the food change listener");
		}

		// Events that'll be registered regardless of the configuration

		registerListener(new WorldLoadedListener());
		messageConsole("&aRegistered the World-Load listener");

		registerListener(new ServerPingListener());
		messageConsole("&aRegistered the Server Ping listener");

		registerListener(new PlayerLoginListener());
		messageConsole("&aRegistered the player Login listener");

		registerListener(new PlayerJoinListener());
		messageConsole("&aRegistered the player join listener");

		registerListener(new PlayerKickListener());
		messageConsole("&aRegistered the player kick listener");

		registerListener(new CommandPreProcessListener());
		messageConsole("&aRegistered the command pre-process listener");

		registerListener(new PrePlayerLoginListener());
		messageConsole("&aRegistered the player pre-login listener");

		registerListener(new PlayerQuitListener());
		messageConsole("&aRegistered the player Quit listener");

		registerListener(new InventoryListener());
		messageConsole("&aRegistered the inventory listener");

		registerListener(new PlayerTeleportListener());
		messageConsole("&aRegistered the player teleport listener");
	}

	private void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}

	private boolean initConfiguration() {
		Serializer configSerializer = new Persister();
		try {
			File configFile = new File(PLUGIN_DATA_FOLDER + "Config.xml");
			if (!configFile.exists()) {
				configSerializer.write(new Configuration(), configFile);
			}

			globalConfig = configSerializer.read(Configuration.class,
					configFile);
			return true;
		} catch (Exception Ex) {
			Ex.printStackTrace();
			return false;
		}
	}

	@Override
	public void onDisable() {
		
		shutdown();
		
		HandlerList.unregisterAll(this);
		Bukkit.getScheduler().cancelTasks(this);

		for (Player player : Bukkit.getOnlinePlayers()) {
			String playerName = player.getName();
			PlayerHandler.removeData(playerName);
			disguiseDatabase.deletePlayerDisguiseData(playerName);
		}

		WarpManager.saveWarps();
	}
}
