package com.caved_in.commons.command;

import com.caved_in.commons.Commons;
import com.caved_in.commons.command.commands.*;

public class CommandRegister {

	public static void registerCommands() {
		//Register all the commands that require an SQL Database, if there's a database being used
		if (Commons.hasSqlBackend()) {
			registerCommand(new FriendCommands());
			registerCommand(new TunnelsXPCommand());
//			registerCommand(new NicklistCommand());
			/* Punishment / Moderation Commands */
			registerCommand(new BanCommand());
			registerCommand(new UnbanCommand());
			registerCommand(new UnsilenceCommand());
			registerCommand(new AddCurrencyCommand());
			/* Premium related */
			registerCommand(new BuyPremiumCommand());
			registerCommand(new RemovePremiumCommand());
		}

		registerCommand(new DebugModeCommand());

		/*== Utility Commands ==*/
		registerCommand(new TeleportHereCommand());
		registerCommand(new TeleportCommand());
		registerCommand(new TeleportAllCommand());
		registerCommand(new WorkbenchCommand());
		registerCommand(new TimeCommand());
		registerCommand(new SpeedCommand());
		registerCommand(new SpawnMobCommand());
		registerCommand(new SkullCommand());
		registerCommand(new SpawnCommand());
		registerCommand(new SetSpawnCommand());
		registerCommand(new RepairCommand());
		registerCommand(new RecipeCommand());
		registerCommand(new NightCommand());
		registerCommand(new DayCommand());
		registerCommand(new MoreCommand());
		registerCommand(new ItemCommand());
		registerCommand(new HealCommand());
		registerCommand(new GamemodeCommand());
		registerCommand(new FeedCommand());
		registerCommand(new EnchantCommand());
		registerCommand(new ClearInventoryCommand());
		registerCommand(new HatCommand());
		registerCommand(new FireworksCommand());
		registerCommand(new FlyCommand());
		registerCommand(new MaintenanceCommand());
		registerCommand(new MessageCommand());
		registerCommand(new SilenceCommand());
		registerCommand(new QuickResponseCommand());
		registerCommand(new TeleportPositionCommand());
		registerCommand(new SetWarpCommand());
		registerCommand(new WarpCommand());
		registerCommand(new BackCommand());
		registerCommand(new WarpsCommand());
		registerCommand(new PotionCommand());
		registerCommand(new SlayCommand());
		registerCommand(new ArmorCommand());
	}

	private static void registerCommand(Object commandHandler) {
		CommandController.registerCommands(Commons.getInstance(), commandHandler);
	}

}
