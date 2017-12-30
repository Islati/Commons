package com.caved_in.commons.nms;

import com.caved_in.commons.Commons;
import com.caved_in.commons.effect.ParticleEffect;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.player.MinecraftPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public interface ParticleEffectsHandler {
    Object createParticleEffectPacket(ParticleEffect effect, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int count, int... extra);

    default Object createParticleEffectPacket(ParticleEffect effect, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int count, int... extra) {
        return createParticleEffectPacket(effect, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), offsetX, offsetY, offsetZ, speed, count, extra);
    }

    default Object createParticleEffectPacket(ParticleEffect effect, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        return createParticleEffectPacket(effect, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), offsetX, offsetY, offsetZ, speed, count, 0);
    }

    /**
     * Send a particle effect to a player
     *
     * @param effect  The particle effect to send
     * @param player  The player to send the effect to
     * @param loc     The location to send the effect to
     * @param offsetX The x range of the particle effect
     * @param offsetY The y range of the particle effect
     * @param offsetZ The z range of the particle effect
     * @param speed   The speed (or color depending on the effect) of the particle
     *                effect
     * @param count   The count of effects
     */
    default void sendToPlayer(Player player, ParticleEffect effect, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        if (effect == ParticleEffect.NONE) {
            return;
        }

        try {
            Object packet = createParticleEffectPacket(effect, loc, offsetX, offsetY, offsetZ, speed, count);
            NmsPlayers.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void sendToPlayer(Player player, ParticleEffect effect, Location loc, int count) {
        Random rand = new Random();
        sendToPlayer(player, effect, loc, rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), count);
    }

    /**
     * Send a particle effect to all players
     *
     * @param loc     The location to send the effect to
     * @param effect  The particle effect to send
     * @param offsetX The x range of the particle effect
     * @param offsetY The y range of the particle effect
     * @param offsetZ The z range of the particle effect
     * @param speed   The speed (or color depending on the effect) of the particle
     *                effect
     * @param count   The count of effects
     */
    default void sendToLocation(Location loc, ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        sendToLocation(loc, effect, offsetX, offsetY, offsetZ, speed, count, ParticleEffect.PARTICLE_RADIUS);
    }

    /**
     * Send a particle effect to all players
     *
     * @param loc     The location to send the effect to
     * @param effect  The particle effect to send
     * @param offsetX The x range of the particle effect
     * @param offsetY The y range of the particle effect
     * @param offsetZ The z range of the particle effect
     * @param speed   The speed (or color depending on the effect) of the particle
     *                effect
     * @param count   The count of effects
     * @param radius  The radius at the location to search for, and send to, players.
     */
    default void sendToLocation(Location loc, ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int count, int radius) {
        if (effect == ParticleEffect.NONE) {
            return;
        }

        try {
            Object packet = createParticleEffectPacket(effect, loc, offsetX, offsetY, offsetZ, speed, count, 0);
            for (Player player : Locations.getPlayersInRadius(loc, radius)) {
                MinecraftPlayer mcPlayer = Commons.getInstance().getPlayerHandler().getData(player);
                if (mcPlayer.isHidingOtherPlayers()) {
                    continue;
                }

                NmsPlayers.sendPacket(player, packet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    default void sendToLocation(Location loc, ParticleEffect effect, int count, int radius) {
        Random rand = new Random();
        sendToLocation(loc, effect, rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), count, radius);
    }

    default void sendToLocation(Location loc, ParticleEffect effect, int count) {
        sendToLocation(loc, effect, count, ParticleEffect.PARTICLE_RADIUS);
    }
}
