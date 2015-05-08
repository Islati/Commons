package com.caved_in.commons.effect;

import com.caved_in.commons.location.Locations;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Effects {
    public static final int BLOCK_EFFECT_RADIUS = 6;

    public static final int BLEED_EFFECT_RADIUS = 10;

    /**
     * Plays the given effect  at a specific location to all the players within the
     * given radius of said location
     *
     * @param location  location to play the effect
     * @param radius    radius to search for players
     * @param blockType the material of the block
     */
    public static void playBlockEffectAt(Location location, int radius, Effect effect, Material blockType) {
        for (Player player : Locations.getPlayersInRadius(location, radius)) {
            player.playEffect(location, effect, blockType.getId());
        }
    }

    /**
     * Plays the effect of a block breaking at a specific location to all the players within the
     * given radius of said location
     *
     * @param location  location to play the block break effect
     * @param radius    radius to search for players
     * @param blockType the material of the block
     */
    public static void playBlockBreakEffect(Location location, int radius, Material blockType) {
        for (Player player : Locations.getPlayersInRadius(location, radius)) {
            player.playEffect(location, Effect.STEP_SOUND, blockType.getId());
        }
    }

    /**
     * Plays the effect for a single player, at a specific location.
     *
     * @param player    player who'll see the effect.
     * @param location  location to play the block break effect
     * @param effect    effect to play
     * @param blockType material to assign to the effect.
     */
    public static void playBlockEffect(Player player, Location location, Effect effect, Material blockType) {
        player.playEffect(location, effect, blockType.getId());
    }

    /**
     * Strike lightning at a specific location.
     *
     * @param loc    location to strike with lightning
     * @param damage whether or not to apply damage.
     */
    public static void strikeLightning(Location loc, boolean damage) {
        World world = loc.getWorld();
        if (damage) {
            world.strikeLightning(loc);
        } else {
            world.strikeLightningEffect(loc);
        }
    }

    /**
     * Make it look like the player's bleeding.
     *
     * @param entity    entity to make bleed!
     * @param intensity the amount of times to play the effect (the greater the value, the more intense it'll be)
     */
    public static void playBleedEffect(Entity entity, int intensity) {
        for (int i = 0; i < intensity; i++) {
            playBleedEffect(entity);
        }
    }

    /**
     * Play the bleed effect on the entity.
     *
     * @param entity entity to make bleed.
     */
    public static void playBleedEffect(Entity entity) {
        playBlockBreakEffect(entity.getLocation(), BLEED_EFFECT_RADIUS, Material.REDSTONE_WIRE);
    }

    /**
     * Play a(n) effect at a specific location viewed by everyone within a defined radius.
     *
     * @param location location to play the effect
     * @param effect   effect to play
     * @param data     data to attach to the effect
     * @param radius   radius to search for players
     */
    public static void playEffect(Location location, Effect effect, int data, int radius) {
        location.getWorld().playEffect(location, effect, data, radius);
    }

    /**
     * Play a(n) effect at a specific location viewed by everyone within a 10 block radius.
     *
     * @param location location to play the effect
     * @param effect   effect to play
     */
    public static void playEffect(Location location, Effect effect) {
        playEffect(location, effect, 1, BLEED_EFFECT_RADIUS);
    }

    /**
     * Create an explosion at a specific location.
     *
     * @param location    location to make the explosion at.
     * @param power       power behind the explosion.
     * @param setFire     whether or not to burn things afterwards.
     * @param breakBlocks whether or not blocks will break due to the explosion.
     */
    public static void explode(Location location, float power, boolean setFire, boolean breakBlocks) {
        World world = location.getWorld();
        int[] xyz = Locations.getXYZ(location);
        world.createExplosion(xyz[0], xyz[1], xyz[2], power, setFire, breakBlocks);
    }

}
