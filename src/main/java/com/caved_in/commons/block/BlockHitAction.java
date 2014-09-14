package com.caved_in.commons.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface BlockHitAction {
	public void onHit(Player player, Block block);
}
