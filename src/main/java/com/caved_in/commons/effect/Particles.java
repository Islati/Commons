package com.caved_in.commons.effect;

import com.caved_in.commons.Commons;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.nms.NMS;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.player.MinecraftPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class Particles {
    private static Random rand = new Random();


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
    public static void sendToPlayer(Player player, ParticleEffect effect, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        NMS.getParticleEffectsHandler().sendToPlayer(player, effect, loc, offsetX, offsetY, offsetZ, speed, count);
    }

    public static void sendToPlayer(Player player, ParticleEffect effect, Location loc, int count) {
        NMS.getParticleEffectsHandler().sendToPlayer(player, effect, loc, count);
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
    public static void sendToLocation(Location loc, ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int count) {
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
    public static void sendToLocation(Location loc, ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int count, int radius) {
        NMS.getParticleEffectsHandler().sendToLocation(loc, effect, offsetX, offsetY, offsetZ, speed, count, radius);
    }


    public static void sendToLocation(Location loc, ParticleEffect effect, int count, int radius) {
        sendToLocation(loc, effect, rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), count, radius);
    }

    public static void sendToLocation(Location loc, ParticleEffect effect, int count) {
        sendToLocation(loc, effect, count, ParticleEffect.PARTICLE_RADIUS);
    }

}
