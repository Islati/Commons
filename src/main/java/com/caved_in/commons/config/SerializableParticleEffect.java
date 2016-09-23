package com.caved_in.commons.config;

import com.caved_in.commons.effect.ParticleEffect;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "particles")
public class SerializableParticleEffect {
    @Attribute(name = "name")
    private String name;

    private ParticleEffect effect;

    public static SerializableParticleEffect of(ParticleEffect e) {
        return new SerializableParticleEffect(e);
    }

    public SerializableParticleEffect(ParticleEffect effect) {
        this.effect = effect;
        this.name = effect.name();
    }

    public SerializableParticleEffect(String name) {
        this.name = name;
    }

    public ParticleEffect getEffect() {
        if (effect == null) {
            effect = ParticleEffect.getEffect(name);
        }
        return effect;
    }
}
