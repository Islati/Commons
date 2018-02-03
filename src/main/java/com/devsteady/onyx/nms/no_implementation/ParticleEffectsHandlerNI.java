package com.devsteady.onyx.nms.no_implementation;

import com.devsteady.onyx.effect.ParticleEffect;
import com.devsteady.onyx.nms.ParticleEffectsHandler;

public class ParticleEffectsHandlerNI implements ParticleEffectsHandler {
    @Override
    public Object createParticleEffectPacket(ParticleEffect effect, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int count, int... extra) {
        throw new IllegalAccessError("Unimplemented");
    }
}
