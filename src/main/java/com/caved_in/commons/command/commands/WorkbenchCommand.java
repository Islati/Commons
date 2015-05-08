package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Command;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.permission.Perms;
import org.bukkit.entity.Player;

public class WorkbenchCommand {
    @Command(identifier = "workbench", permissions = Perms.COMMAND_WORKBENCH)
    public void onWorkbenchCommand(Player player) {
        Inventories.openWorkbench(player);
    }
}
