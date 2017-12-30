package com.caved_in.commons.sound;

import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.Skip;
import org.bukkit.Sound;

public class SoundData {
    @Path("type")
    private Sound sound;

    @Path("volume")
    private float volume = 1.0f;

    @Path("pitch")
    private float pitch = 1.0f;

    @Skip
    private SoundPlayer parent;

    public static SoundData newInstance() {
        return new SoundData();
    }

    public SoundData(Sound sound, float volume, float pitch) {
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
        this.sound = sound;
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
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }
}
