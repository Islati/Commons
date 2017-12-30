package com.caved_in.commons.effect;

import com.caved_in.commons.nms.NMS;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum ParticleEffect {

    NONE("NONE",-1),
    BARRIER("BARRIER",0),
    BLOCK_CRACK("BLOCK_CRACK",1),
    BLOCK_DUST("BLOCK_DUST",2),
    CLOUD("CLOUD",3),
    CRIT("CRIT",4),
    CRIT_MAGIC("CRIT_MAGIC",5),
    DAMAGE_INDICATOR("DAMAGE_INDICATOR",6),
    DRAGON_BREATH("DRAGON_BREATH",7),
    DRIP_LAVA("DRIP_LAVA",8),
    DRIP_WATER("DRIP_WATER",9),
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE",10),
    END_ROD("END_ROD",11),
    EXPLOSION_HUGE("EXPLOSION_HUGE",12),
    EXPLOSION_LARGE("EXPLOSION_LARGE",13),
    EXPLOSION_NORMAL("EXPLOSION_NORMAL",14),
    FIREWORKS_SPARK("FIREWORKS_SPARK",15),
    FLAME("FLAME",16),
    FOOTSTEP("FOOTSTEP",17),
    HEART("HEART",18),
    ITEM_CRACK("ITEM_CRACK",19),
    ITEM_TAKE("ITEM_TAKE",20),
    LAVA("LAVA",21),
    MOB_APPEARANCE("MOB_APPEARANCE",22),
    NOTE("NOTE",23),
    PORTAL("PORTAL",24),
    REDSTONE("REDSTONE",25),
    SLIME("SLIME",26),
    SMOKE_LARGE("SMOKE_LARGE",27),
    SMOKE_NORMAL("SMOKE_NORMAL",28),
    SNOW_SHOVEL("SNOW_SHOVEL",29),
    SNOWBALL("SNOWBALL",30),
    SPELL("SPELL",31),
    SPELL_INSTANT("SPELL_INSTANT",32),
    SPELL_MOB("SPELL_MOB",33),
    SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT",34),
    SPELL_WITCH("SPELL_WITCH",35),
    SUSPENDED("SUSPENDED",36),
    SUSPENDED_DEPTH("SUSPENDED_DEPTH",37),
    SWEEP_ATTACK("SWEEP_ATTACK",38),
    TOWN_AURA("TOWN_AURA",39),
    VILLAGER_ANGRY("VILLAGER_ANGRY",40),
    VILLAGER_HAPPY("VILLAGER_HAPPY",41),
    WATER_BUBBLE("WATER_BUBBLE",42),
    WATER_DROP("WATER_DROP",43),
    WATER_SPLASH("WATER_SPLASH",44),WATER_WAKE("WATER_WAKE",45);


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