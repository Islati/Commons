package com.caved_in.commons.nms.no_implementation;

import com.caved_in.commons.nms.AnvilHandler;
import org.bukkit.entity.Player;

public class AnvilHandlerNI implements AnvilHandler {
    @Override
    public Object newAnvilContainer(Player player) {
        throw new IllegalAccessError("Unable to create an anvil container; Implementation for your version of Minecraft doesn't exist");
    }
}
