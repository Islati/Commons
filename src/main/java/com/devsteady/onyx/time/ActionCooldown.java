package com.devsteady.onyx.time;

import com.devsteady.onyx.chat.Chat;

import java.util.HashMap;

public class ActionCooldown {
    private HashMap<String, Long> cooldowns = new HashMap<>();
    private int cooldownTime = 0;

    /**
     * Create a new cooldown object with a duration in seconds
     *
     * @param seconds
     */
    public ActionCooldown(int seconds) {
        this.cooldownTime = seconds;
    }

    public void removeCooldown(String action) {
        if (!cooldowns.containsKey(action)) {
            return;
        }

        cooldowns.remove(action);
    }

    public void setOnCooldown(String action) {
        cooldowns.put(action, System.currentTimeMillis() / 1000L);
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public double getRemainingSeconds(String action) {
        if (!cooldowns.containsKey(action)) {
            return 0;
        }

        double actionTime = cooldowns.get(action);
        long timeCheck = System.currentTimeMillis() / 1000L;
        double difference = cooldownTime - (timeCheck - actionTime);

        Chat.debug("Difference in time from then to now is " + difference + " seconds");
        if (difference <= 0) {
            return 0;
        }

        return difference;
    }

    public double getRemainingMinutes(String action) {

        return getRemainingSeconds(action) / 60;
    }

    public boolean isOnCooldown(String string) {
        if (!cooldowns.containsKey(string)) {
            return false;
        }

        double actionTime = cooldowns.get(string);
        long currentTime = System.currentTimeMillis() / 1000L;
        return (currentTime - actionTime) < cooldownTime;
    }
}