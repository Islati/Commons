package com.caved_in.commons.command;

import com.caved_in.commons.Commons;

public class CommandRegister {

	public static void registerCommands() {
		registerCommand(new FriendCommands());
		//Register the individual commands
		
		/*== Utility Commands ==*/
		registerCommand(new TeleportHereCommand());
		registerCommand(new TeleportCommand());
		registerCommand(new TeleportAllCommand());
		registerCommand(new WorkbenchCommand());
		registerCommand(new TunnelsXPCommand());
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
		registerCommand(new NicklistCommand());
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

		/* Punishment / Moderation Commands */
		registerCommand(new BanCommand());
		registerCommand(new UnbanCommand());
		registerCommand(new UnsilenceCommand());
		registerCommand(new AddCurrencyCommand());

		/* Premium related */
		registerCommand(new BuyPremiumCommand());
		registerCommand(new RemovePremiumCommand());


	}

	private static void registerCommand(Object commandHandler) {
		CommandController.registerCommands(Commons.getInstance(), commandHandler);
	}

}
