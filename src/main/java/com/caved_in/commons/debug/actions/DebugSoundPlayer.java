package com.caved_in.commons.debug.actions;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.sound.Sounds;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DebugSoundPlayer implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {

        if (args.length == 0) {
            List<String> soundNames = new ArrayList<>();
            EnumSet.allOf(Sound.class).forEach(sound -> soundNames.add(sound.name()));

            StringBuilder message = new StringBuilder();
            soundNames.forEach(nm -> message.append(nm).append(" , "));
            Chat.message(player,message.toString());
            return;
        }

        String soundName = args[0];

        Sounds.playSound(player,Sound.valueOf(soundName));
    }

    @Override
    public String getActionName() {
        return "sounds";
    }
}
