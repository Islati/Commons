package com.devsteady.onyx.nms.minecraft_1_12_x;

import com.devsteady.onyx.effect.ParticleEffect;
import com.devsteady.onyx.nms.ParticleEffectsHandler;
import com.devsteady.onyx.utilities.ReflectionUtilities;
import org.joor.Reflect;

public class ParticleEffectsHandler_1_12X implements ParticleEffectsHandler {

    private Class<Enum> enumParticleClass;

    private Class<?> particlePacketClass;

    public ParticleEffectsHandler_1_12X() {
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
