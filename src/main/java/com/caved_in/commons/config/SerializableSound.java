package com.caved_in.commons.config;

import org.bukkit.Sound;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Serializable wrapper for sounds.
 */
@Root(name = "sound")
public class SerializableSound {
    @Attribute(name = "name")
    private String soundName = Sound.BLOCK_NOTE_SNARE.name();

    public static SerializableSound fromSound(Sound sound) {
        return new SerializableSound(sound);
    }

    public SerializableSound(@Attribute(name = "name") String soundName) {
        this.soundName = soundName;
    }

    public SerializableSound(Sound sound) {
        this.soundName = sound.name();
    }

    public Sound getSound() {
        return Sound.valueOf(soundName);
    }
}
