package com.devsteady.onyx.permission;

import org.bukkit.entity.Player;

/**
 * An object that has permission data attached.
 */
public interface Permissible {
    String getPermission();

    boolean hasPermission(Player p);
}