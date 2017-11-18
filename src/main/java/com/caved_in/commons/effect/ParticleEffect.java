package com.caved_in.commons.effect;

import com.caved_in.commons.nms.NMS;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum ParticleEffect {

    NONE("NONE",0),
    BARRIER("BARRIER",1),
    BLOCK_CRACK("BLOCK_CRACK",2),
    BLOCK_DUST("BLOCK_DUST",3),
    CLOUD("CLOUD",4),
    CRIT("CRIT",5),
    CRIT_MAGIC("CRIT_MAGIC",6),
    DAMAGE_INDICATOR("DAMAGE_INDICATOR",7),
    DRAGON_BREATH("DRAGON_BREATH",8),
    DRIP_LAVA("DRIP_LAVA",9),
    DRIP_WATER("DRIP_WATER",10),
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE",11),
    END_ROD("END_ROD",12),
    EXPLOSION_HUGE("EXPLOSION_HUGE",13),
    EXPLOSION_LARGE("EXPLOSION_LARGE",14),
    EXPLOSION_NORMAL("EXPLOSION_NORMAL",15),
    FIREWORKS_SPARK("FIREWORKS_SPARK",16),
    FLAME("FLAME",17),
    FOOTSTEP("FOOTSTEP",18),
    HEART("HEART",19),
    ITEM_CRACK("ITEM_CRACK",20),
    ITEM_TAKE("ITEM_TAKE",21),
    LAVA("LAVA",22),
    MOB_APPEARANCE("MOB_APPEARANCE",23),
    NOTE("NOTE",24),
    PORTAL("PORTAL",25),
    REDSTONE("REDSTONE",26),
    SLIME("SLIME",27),
    SMOKE_LARGE("SMOKE_LARGE",28),
    SMOKE_NORMAL("SMOKE_NORMAL",29),
    SNOW_SHOVEL("SNOW_SHOVEL",30),
    SNOWBALL("SNOWBALL",31),
    SPELL("SPELL",32),
    SPELL_INSTANT("SPELL_INSTANT",33),
    SPELL_MOB("SPELL_MOB",34),
    SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT",35),
    SPELL_WITCH("SPELL_WITCH",36),
    SUSPENDED("SUSPENDED",37),
    SUSPENDED_DEPTH("SUSPENDED_DEPTH",38),
    SWEEP_ATTACK("SWEEP_ATTACK",39),
    TOWN_AURA("TOWN_AURA",40),
    VILLAGER_ANGRY("VILLAGER_ANGRY",41),
    VILLAGER_HAPPY("VILLAGER_HAPPY",42),
    WATER_BUBBLE("WATER_BUBBLE",43),
    WATER_DROP("WATER_DROP",44),
    WATER_SPLASH("WATER_SPLASH",45),WATER_WAKE("WATER_WAKE",46);


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