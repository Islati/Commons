package com.caved_in.commons.menu.chat;

import org.bukkit.entity.Player;

public interface ValueListener<T> {

    void onChange(Player player, T oldValue, T newValue);
}
