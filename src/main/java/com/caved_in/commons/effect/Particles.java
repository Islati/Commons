package com.caved_in.commons.effect;

import com.caved_in.commons.Commons;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.nms.NMS;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.player.MinecraftPlayer;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Random;

public class Particles {
    private static Random rand = new Random();

    public static void sendToPlayer(Player player, Particle effect, Location loc, int count) {
        sendToPlayer(player, effect, loc,  count);
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
    public static void sendToLocation(Location loc, Particle effect, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        sendToLocation(loc, effect, offsetX, offsetY, offsetZ, speed, count, 30);
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
    public static void sendToLocation(Location loc, Particle effect, float offsetX, float offsetY, float offsetZ, float speed, int count, int radius) {
        for (Player player : Locations.getPlayersInRadius(loc,30)) {
            sendToPlayer(player,effect,loc,count);
        }
    }


    public static void sendToLocation(Location loc, Particle effect, int count, int radius) {
        sendToLocation(loc, effect, rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), count, radius);
    }

    public static void sendToLocation(Location loc, Particle effect, int count) {
        sendToLocation(loc, effect, count, ParticleEffect.PARTICLE_RADIUS);
    }

}
