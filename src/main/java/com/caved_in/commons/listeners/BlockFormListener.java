package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.CommonsXmlConfiguration;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFormListener implements Listener {

    private CommonsXmlConfiguration config;

    public BlockFormListener() {
        config = Commons.getInstance().getConfiguration();
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        Material blockType = event.getNewState().getType();
        switch (blockType) {
            case SNOW:
                if (config.getWorldConfig().isSnowSpreadDisabled()) {
                    event.setCancelled(true);
                }
                break;
            case ICE:
                if (config.getWorldConfig().isIceSpreadDisabled()) {
                    event.setCancelled(true);
                }
                break;
            default:
                break;
        }
    }
}
