package com.caved_in.commons.nms.minecraft_1_8_R3;

import com.caved_in.commons.effect.ParticleEffect;
import com.caved_in.commons.nms.ParticleEffectsHandler;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.joor.Reflect;

public class ParticleEffectsHandler_18R3 implements ParticleEffectsHandler {

    private Class<Enum> enumParticleClass;

    private Class<?> particlePacketClass;

    public ParticleEffectsHandler_18R3() {
        this.enumParticleClass = (Class<Enum>) ReflectionUtilities.getNMSClass("EnumParticle");
        this.particlePacketClass = ReflectionUtilities.getNMSClass("PacketPlayOutWorldParticles");
    }

    @Override
    public Object createParticleEffectPacket(ParticleEffect effect, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int count, int... extra) {

        return Reflect.on(particlePacketClass).create()
                .set("a", enumParticleClass.getEnumConstants()[effect.getId()])
                .set("j", true)
                .set("b", x)
                .set("c", y)
                .set("d", z)
                .set("e", offsetX)
                .set("f", offsetY)
                .set("g", offsetZ)
                .set("h", speed)
                .set("i", count)
                .get();

    }
}
