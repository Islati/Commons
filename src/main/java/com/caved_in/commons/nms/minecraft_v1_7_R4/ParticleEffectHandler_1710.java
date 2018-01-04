package com.caved_in.commons.nms.minecraft_v1_7_R4;

import com.caved_in.commons.effect.ParticleEffect;
import com.caved_in.commons.nms.ParticleEffectsHandler;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.joor.Reflect;

public class ParticleEffectHandler_1710 implements ParticleEffectsHandler {
    private Class<?> particlePacketClass;

    public ParticleEffectHandler_1710() {
        this.particlePacketClass = ReflectionUtilities.getNMSClass("PacketPlayOutWorldParticles");
    }

    @Override
    public Object createParticleEffectPacket(ParticleEffect effect, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int count, int... extra) {
        return Reflect.on(particlePacketClass).create(
                effect.getLegacyName(),
                x,y,z,
                offsetX,offsetY,offsetZ,
                count).get();

    }
}
