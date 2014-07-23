package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.commons.utilities.StringUtil;
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
			Commons.threadManager.cancelTask(taskId);
			return;
		}

		Player player = Players.getPlayer(playerId);
		if (messageCooldown.isOnCooldown(player)) {
			return;
		}
		try {
			String message = messages[messageIndex];
			Players.sendMessage(player, message);
			Sounds.playSound(player, sound);
			messageCooldown.setOnCooldown(player);
			messageIndex++;
		} catch (IndexOutOfBoundsException e) {
			Commons.debug("Uh oh! Message index[" + messageIndex + "] doesn't exist!\n" + StringUtil.joinString(messages, "\n"));
		}
	}
}
