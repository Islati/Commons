package com.caved_in.commons.sound;

import com.caved_in.commons.config.SerializableSound;
import org.bukkit.Sound;
import org.simpleframework.xml.Element;

public class SoundData {
    @Element(name = "sound", type = SerializableSound.class)
    private SerializableSound sound;

    @Element(name = "volume")
    private float volume = 1.0f;

    @Element(name = "pitch")
    private float pitch = 1.0f;

    private SoundPlayer parent;

    public static SoundData newInstance() {
        return new SoundData();
    }

    public SoundData(@Element(name = "sound", type = SerializableSound.class) SerializableSound sound, @Element(name = "volume") float volume, @Element(name = "pitch") float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public SoundData() {

    }

    public SoundData(SoundPlayer soundPlayer) {
        this.parent = soundPlayer;
    }

    public SoundData sound(Sound sound) {
        this.sound = SerializableSound.fromSound(sound);
        return this;
    }

    public SoundData volume(float volume) {
        this.volume = volume;
        return this;
    }

    public SoundData pitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public Sound getSound() {
        return sound.getSound();
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }
}
