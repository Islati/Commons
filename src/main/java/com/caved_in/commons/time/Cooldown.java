package com.caved_in.commons.time;

import com.caved_in.commons.chat.Chat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {
	private HashMap<UUID, Long> cooldowns = new HashMap<>();
	private int cooldownTime = 0;

	/**
	 * Create a new cooldown object with a duration in seconds
	 *
	 * @param seconds
	 */
	public Cooldown(int seconds) {
		this.cooldownTime = seconds;
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
		cooldowns.put(player.getUniqueId(), System.currentTimeMillis() / 1000L);
	}

	public void setCooldownTime(int cooldownTime) {
		this.cooldownTime = cooldownTime;
	}

	public double getRemainingSeconds(Player player) {
		if (!cooldowns.containsKey(player.getUniqueId())) {
			return 0;
		}

		double playerTime = cooldowns.get(player.getUniqueId());
		long timeCheck = System.currentTimeMillis() / 1000L;
		double difference = cooldownTime - (timeCheck - playerTime);

		Chat.debug("Difference in time from then to now is " + difference + " seconds");
		if (difference <= 0) {
			return 0;
		}

		return difference;
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

		double playerTime = cooldowns.get(player.getUniqueId());
		long currentTime = System.currentTimeMillis() / 1000L;
		return (currentTime - playerTime) < cooldownTime;
	}
}