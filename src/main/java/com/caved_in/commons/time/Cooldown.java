package com.caved_in.commons.time;

import com.caved_in.commons.chat.Chat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//todo set individual cooldowns for players
//todo implement yaml serialize
//todo implement json serializef
public class Cooldown {
    private HashMap<UUID, Long> cooldowns = new HashMap<>();
    private long cooldownTime = 0;

    /**
     * Create a new cooldown object with a duration in seconds
     *
     * @param seconds
     */
    public Cooldown(int seconds) {
        this.cooldownTime = seconds * 1000;
    }

    public void removeCooldown(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId())) {
            return;
        }

        cooldowns.remove(player.getUniqueId());
    }

    /**
     * Set a player on cooldown.
     *
     * @param player
     */
    public void setOnCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), Long.sum(System.currentTimeMillis(), cooldownTime));
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime * 1000;
    }

    public double getRemainingSeconds(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId())) {
            return 0;
        }

        long playerOffCooldown = cooldowns.get(player.getUniqueId());
        long currentTime = System.currentTimeMillis();
        long difference = playerOffCooldown - currentTime;

        Chat.debug("Difference in time from then to now on " + player.getName() + " is " + difference + " seconds");
        if (difference <= 0) {
            return 0;
        }

        long seconds = TimeUnit.MILLISECONDS.toSeconds(difference);
        return (double) seconds;
    }

    public double getRemainingMinutes(Player player) {
        return getRemainingSeconds(player) / 60;
    }

    /**
     * Checks whether or not the player is on cooldown by comparing when the player was last timestamped to the current time.
     *
     * @param player player to check for cooldown
     * @return true if there's been more than the alloted duration passed, false otherwise.
     */
    public boolean isOnCooldown(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId())) {
            return false;
        }

        double seconds = getRemainingSeconds(player);

        return seconds > 0;
    }
}