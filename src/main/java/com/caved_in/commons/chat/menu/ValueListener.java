package com.caved_in.commons.chat.menu;

import org.bukkit.entity.Player;

public interface ValueListener<T> {

    void onChange(Player player, T oldValue, T newValue);
}
