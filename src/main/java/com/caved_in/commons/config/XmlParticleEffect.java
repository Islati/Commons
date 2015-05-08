package com.caved_in.commons.config;

import com.caved_in.commons.effect.ParticleEffects;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "particles")
public class XmlParticleEffect {
    @Attribute(name = "name")
    private String name;

    private ParticleEffects effect;

    public static XmlParticleEffect of(ParticleEffects e) {
        return new XmlParticleEffect(e);
    }

    public XmlParticleEffect(ParticleEffects effect) {
        this.effect = effect;
        this.name = effect.name();
    }

    public XmlParticleEffect(String name) {
        this.name = name;
    }

    public ParticleEffects getEffect() {
        if (effect == null) {
            effect = ParticleEffects.getEffect(name);
        }
        return effect;
    }
}
