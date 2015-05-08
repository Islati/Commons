package com.caved_in.commons.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

//todo document
public interface BlockHitAction {
    void onHit(Player player, Block block);
}
