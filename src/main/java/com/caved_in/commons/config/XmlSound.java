package com.caved_in.commons.config;

import org.bukkit.Sound;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Serializable wrapper for sounds.
 */
@Root(name = "sound")
public class XmlSound {
    @Attribute(name = "name")
    private String soundName = Sound.BLOCK_NOTE_SNARE.name();

    public static XmlSound fromSound(Sound sound) {
        return new XmlSound(sound);
    }

    public XmlSound(@Attribute(name = "name") String soundName) {
        this.soundName = soundName;
    }

    public XmlSound(Sound sound) {
        this.soundName = sound.name();
    }

    public Sound getSound() {
        return Sound.valueOf(soundName);
    }
}
