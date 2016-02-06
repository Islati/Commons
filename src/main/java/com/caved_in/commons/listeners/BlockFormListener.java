package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFormListener implements Listener {

    private Configuration config;

    public BlockFormListener() {
        config = Commons.getInstance().getConfiguration();
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        Material blockType = event.getNewState().getType();
        switch (blockType) {
            case SNOW:
                if (config.disableSnowAccumulation()) {
                    event.setCancelled(true);
                }
                break;
            case ICE:
                if (config.disableIceAccumulation()) {
                    event.setCancelled(true);
                }
                break;
            default:
                break;
        }
    }
}
