package com.caved_in.commons.nms.minecraft_1_9x;

import com.caved_in.commons.effect.ParticleEffect;
import com.caved_in.commons.nms.ParticleEffectsHandler;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.joor.Reflect;

public class ParticleEffectsHandler_19X implements ParticleEffectsHandler {

    private Class<Enum> enumParticleClass;

    private Class<?> particlePacketClass;

    public ParticleEffectsHandler_19X() {
        this.enumParticleClass = (Class<Enum>) ReflectionUtilities.getNMSClass("EnumParticle");
        this.particlePacketClass = ReflectionUtilities.getNMSClass("PacketPlayOutWorldParticles");
    }

    @Override
    public Object createParticleEffectPacket(ParticleEffect effect, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int count, int... extra) {

        return Reflect.on(particlePacketClass).create()
                .set("a", Reflect.on(this.enumParticleClass).call("valueOf",effect.getName()).get())
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
