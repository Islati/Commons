package com.caved_in.commons.nms.non_breaking_implementation;

import com.caved_in.commons.nms.AbstractTitle;
import org.bukkit.entity.Player;

public class TitleHandlerNonBreaking implements AbstractTitle.TitleHandler {
    @Override
    public void send(Player player, AbstractTitle title) {
        player.sendTitle(title.getTitle(),title.getSubtitle(),title.getFadeInTime(),title.getStayTime(),title.getFadeOutTime());
    }

    @Override
    public void resetTitle(Player player) {
        player.resetTitle();
    }

    @Override
    public void clearTitle(Player player) {
        player.resetTitle();
    }
}
