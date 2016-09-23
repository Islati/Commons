package com.caved_in.commons.effect;

import com.caved_in.commons.nms.NMS;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum ParticleEffect {

    NONE("none", -1),

    EXPLOSION_NORMAL("explode", 0),
    EXPLOSION_LARGE("largeexplode", 1),
    EXPLOSION_HUGE("hugeexplosion", 2),
    FIREWORKS_SPARK("fireworksSpark", 3),
    WATER_BUBBLE("bubble", 4),
    WATER_SPLASH("splash", 5),
    WATER_WAKE("wake", 6),
    SUSPENDED("suspended", 7),
    SUSPENDED_DEPTH("depthsuspend", 8),
    CRIT("crit", 9),
    CRIT_MAGIC("magicCrit", 10),
    SMOKE_NORMAL("smoke", 11),
    SMOKE_LARGE("largesmoke", 12),
    SPELL("spell", 13),
    SPELL_INSTANT("instantSpell", 14),
    SPELL_MOB("mobSpell", 15),
    SPELL_MOB_AMBIENT("mobSpellAmbient", 16),
    SPELL_WITCH("witchMagic", 17),
    DRIP_WATER("dripWater", 18),
    DRIP_LAVA("dripLava", 19),
    VILLAGER_ANGRY("angryVillager", 20),
    VILLAGER_HAPPY("happyVillager", 21),
    TOWN_AURA("townaura", 22),
    NOTE("note", 23),
    PORTAL("portal", 24),
    ENCHANTMENT_TABLE("enchantmenttable", 25),
    FLAME("flame", 26),
    LAVA("lava", 27),
    FOOTSTEP("footstep", 28),
    CLOUD("cloud", 29),
    REDSTONE("reddust", 30),
    SNOWBALL("snowballpoof", 31),
    SNOW_SHOVEL("snowshovel", 32),
    SLIME("slime", 33),
    HEART("heart", 34),
    BARRIER("barrier", 35),
    ITEM_CRACK("iconcrack", 36),
    BLOCK_CRACK("blockcrack", 37),
    BLOCK_DUST("blockdust", 38),
    WATER_DROP("droplet", 39),
    ITEM_TAKE("take", 40),
    MOB_APPEARANCE("mobappearance", 41),
    DRAGON_BREATH("dragonbreath", 42),
    END_ROD("endRod", 43),
    DAMAGE_INDICATOR("damageIndicator", 44),
    SWEEP_ATTACK("sweepAttack", 45),
    FALLING_DUST("fallingdust", 67);

    public static int PARTICLE_RADIUS = 10;
    private static final Random random = new Random();

    private static final Map<String, ParticleEffect> PARTICLE_EFFECTS_MAP = new HashMap<>();
    private static final Map<Integer, ParticleEffect> PARTICLE_EFFECTS_ID_MAP = new HashMap<>();

    static {
        for (ParticleEffect effect : EnumSet.allOf(ParticleEffect.class)) {
            PARTICLE_EFFECTS_MAP.put(effect.getName(), effect);
            PARTICLE_EFFECTS_MAP.put(effect.name(), effect);
            PARTICLE_EFFECTS_ID_MAP.put(effect.getId(), effect);
        }
    }

    public static ParticleEffect getEffect(String name) {
        return PARTICLE_EFFECTS_MAP.get(name);
    }

    public static ParticleEffect getEffect(int id) {
        return PARTICLE_EFFECTS_ID_MAP.get(id);
    }

    private String name;
    private int id;

    ParticleEffect(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Gets the name of the Particle Effect
     *
     * @return The particle effect name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the id of the Particle Effect
     *
     * @return The id of the Particle Effect
     */
    public int getId() {
        return id;
    }

    @Deprecated
    public static void sendToPlayer(ParticleEffect effect, Player player, Location location, float offsetX, float offsetY,
                                    float offsetZ, float speed, int count) {
        NMS.getParticleEffectsHandler().sendToPlayer(player, effect, location, offsetX, offsetY, offsetZ, speed, count);
    }

    @Deprecated
    public static void sendToLocation(ParticleEffect effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        NMS.getParticleEffectsHandler().sendToLocation(location, effect, offsetX, offsetY, offsetZ, speed, count);
    }

    @Deprecated
    public static void sendToLocation(ParticleEffect effects, Location loc, int count) {
        sendToLocation(effects, loc, random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat(), count);
    }

}