package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;

public class CommandRegister {

	public CommandRegister(Commons Plugin) {
		CommandController.registerCommands(Plugin, new FriendCommands());
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
		/* Punishment / Moderation Commands */
		registerCommand(new BanCommand());
		registerCommand(new UnbanCommand());
		registerCommand(new UnsilenceCommand());
		registerCommand(new AddCurrencyCommand());

		/* Premium related */
		registerCommand(new BuyPremiumCommand());
		registerCommand(new RemovePremiumCommand());

		/* Utilities / Misc */
		registerCommand(new HatCommand());
		registerCommand(new FireworksCommand());
		registerCommand(new FlyCommand());
		registerCommand(new MaintenanceCommand());
		registerCommand(new MessageCommand());
		registerCommand(new SilenceCommand());
		registerCommand(new QuickResponseCommand());
	}

	private void registerCommand(Object commandHandler) {
		CommandController.registerCommands(Commons.getCommons(), commandHandler);
	}

}
