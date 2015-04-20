package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomEnchantmentTickThread extends BukkitRunnable {

    /**
     * How often custom enchantments onTick() method will be called *
     */
    public static final long ENCHANT_TICK_RATE = 40L;

    private static boolean init = false;

    private static CustomEnchantmentTickThread instance = null;

    /**
     * Get the instance of the custom enchantment tick thread.
     *
     * @return the instance of the enchantment tick thread.
     */
    public static CustomEnchantmentTickThread getInstance() {
        if (instance == null) {
            instance = new CustomEnchantmentTickThread();
        }

        return instance;
    }

    protected CustomEnchantmentTickThread() {
    }

    @Override
    public void run() {
        for (Player p : Players.allPlayers()) {
            //todo check if player's armor has custom enchant
            //todo check if player's weapon has custom enchant
            //todo execute logic of enchantment
        }
    }
}
