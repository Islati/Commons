package com.caved_in.commons.command.commands;

import com.caved_in.commons.block.Letter;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class BlockTextCommand {
	@Command(identifier = "blocktext", permissions = {Perms.COMMAND_BLOCK_TEXT})
	public void onBlockText(Player player, @Arg(name = "material-data") MaterialData data, @Wildcard @Arg(name = "text") String text) {
		Letter.drawString(text, data.getItemType(), data.getData(), Players.getTargetLocation(player), Players.getDirection(player));
	}
}
