package com.caved_in.commons.config;

import com.caved_in.commons.effect.ParticleEffects;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "particles")
public class ParticleEffect {
    @Attribute(name = "name")
    private String name;

    private ParticleEffects effect;

    public static ParticleEffect of(ParticleEffects e) {
        return new ParticleEffect(e);
    }

    public ParticleEffect(ParticleEffects effect) {
        this.effect = effect;
        this.name = effect.name();
    }

    public ParticleEffect(String name) {
        this.name = name;
    }

    public ParticleEffects getEffect() {
        if (effect == null) {
            effect = ParticleEffects.getEffect(name);
        }
        return effect;
    }
}
