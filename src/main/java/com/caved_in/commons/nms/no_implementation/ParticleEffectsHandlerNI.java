package com.caved_in.commons.nms.no_implementation;

import com.caved_in.commons.effect.ParticleEffect;
import com.caved_in.commons.nms.ParticleEffectsHandler;

public class ParticleEffectsHandlerNI implements ParticleEffectsHandler {
    @Override
    public Object createParticleEffectPacket(ParticleEffect effect, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int count, int... extra) {
        throw new IllegalAccessError("Unimplemented");
    }
}
