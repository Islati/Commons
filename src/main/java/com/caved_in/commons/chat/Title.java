package com.caved_in.commons.chat;

import com.caved_in.commons.nms.AbstractTitle;
import com.caved_in.commons.nms.NMS;
import org.bukkit.entity.Player;

public class Title extends AbstractTitle {

    public Title(String title) {
        super(title);
    }

    public Title(String title, String subtitle) {
        super(title, subtitle);
    }

    public Title(Title title) {
        super(title);
    }

    public Title(String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
        super(title, subtitle, fadeInTime, stayTime, fadeOutTime);
    }

    @Override
    public void send(Player player) {
        NMS.getTitleHandler().send(player, this);
    }

    @Override
    public void clearTitle(Player player) {
        NMS.getTitleHandler().clearTitle(player);
    }

    @Override
    public void resetTitle(Player player) {
        NMS.getTitleHandler().resetTitle(player);
    }
}