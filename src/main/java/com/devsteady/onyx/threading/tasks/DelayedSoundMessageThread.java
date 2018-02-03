package com.devsteady.onyx.threading.tasks;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.sound.Sounds;
import com.devsteady.onyx.time.Cooldown;
import com.devsteady.onyx.utilities.StringUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DelayedSoundMessageThread implements Runnable {

    private UUID playerId;

    private String[] messages;

    private int messageIndex = 0;

    private int finalIndex = 0;

    private Sound sound;

    private Cooldown messageCooldown;

    private int taskId = 0;

    public DelayedSoundMessageThread(Player player, Sound sound, int secondsDelay, String... messages) {
        playerId = player.getUniqueId();
        this.sound = sound;
        this.messages = messages;

        //If there's only one message in the array, attempt to split the string on newline
        //As per default behaviour ;)
        if (messages.length == 1) {
            messages = StringUtil.splitOnNewline(messages[0]);
        }


        finalIndex = messages.length - 1;
        messageCooldown = new Cooldown(secondsDelay);
    }

    public void setTaskId(int id) {
        taskId = id;
    }

    @Override
    public void run() {
        if (messageIndex >= finalIndex) {
            Onyx.getInstance().getThreadManager().cancelTask(taskId);
            return;
        }

        Player player = Players.getPlayer(playerId);
        if (messageCooldown.isOnCooldown(player)) {
            return;
        }
        try {
            String message = messages[messageIndex];
            Chat.message(player, message);
            Sounds.playSound(player, sound);
            messageCooldown.setOnCooldown(player);
            messageIndex++;
        } catch (IndexOutOfBoundsException e) {
            Onyx.getInstance().debug("Uh oh! Message index[" + messageIndex + "] doesn't exist!\n" + StringUtil.joinString(messages, "\n"));
        }
    }
}
