package com.caved_in.commons.nms.no_implementation;

import com.caved_in.commons.nms.AbstractTitle;
import org.bukkit.entity.Player;

public class TitleHandlerNI implements AbstractTitle.TitleHandler {
    @Override
    public void send(Player player, AbstractTitle title) {
        throw new IllegalAccessError("Unimplemented");
    }

    @Override
    public void resetTitle(Player player) {
        throw new IllegalAccessError("Unimplemented");
    }

    @Override
    public void clearTitle(Player player) {
        throw new IllegalAccessError("Unimplemented");
    }
}
