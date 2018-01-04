package com.caved_in.commons.effect;

import com.caved_in.commons.nms.NMS;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum ParticleEffect {

    NONE("NONE","none",-1),
    BARRIER("BARRIER","barrier",0),
    BLOCK_CRACK("BLOCK_CRACK","blockcrack",1),
    BLOCK_DUST("BLOCK_DUST","blockdust",2),
    CLOUD("CLOUD","cloud",3),
    CRIT("CRIT","crit",4),
    CRIT_MAGIC("CRIT_MAGIC","magicCrit",5),
    DAMAGE_INDICATOR("DAMAGE_INDICATOR",null,6),
    DRAGON_BREATH("DRAGON_BREATH",null,7),
    DRIP_LAVA("DRIP_LAVA","dripLava",8),
    DRIP_WATER("DRIP_WATER","dripWater",9),
    ENCHANTMENT_TABLE("ENCHANTMENT_TABLE","enchantmenttable",10),
    END_ROD("END_ROD",null,11),
    EXPLOSION_HUGE("EXPLOSION_HUGE","hugeexplosion",12),
    EXPLOSION_LARGE("EXPLOSION_LARGE","largeexplode",13),
    EXPLOSION_NORMAL("EXPLOSION_NORMAL","explode",14),
    FIREWORKS_SPARK("FIREWORKS_SPARK","fireworksSpark",15),
    FLAME("FLAME","flame",16),
    FOOTSTEP("FOOTSTEP","footstep",17),
    HEART("HEART","heart",18),
    ITEM_CRACK("ITEM_CRACK","iconcrack",19),
    ITEM_TAKE("ITEM_TAKE","take",20),
    LAVA("LAVA","lava",21),
    MOB_APPEARANCE("MOB_APPEARANCE","mobappearance",22),
    NOTE("NOTE","note",23),
    PORTAL("PORTAL","portal",24),
    REDSTONE("REDSTONE","reddust",25),
    SLIME("SLIME","slime",26),
    SMOKE_LARGE("SMOKE_LARGE","largesmoke",27),
    SMOKE_NORMAL("SMOKE_NORMAL","smoke",28),
    SNOW_SHOVEL("SNOW_SHOVEL","snowshovel",29),
    SNOWBALL("SNOWBALL","snowballpoof",30),
    SPELL("SPELL","spell",31),
    SPELL_INSTANT("SPELL_INSTANT","instantSpell",32),
    SPELL_MOB("SPELL_MOB","mobSpell",33),
    SPELL_MOB_AMBIENT("SPELL_MOB_AMBIENT","mobSpellAmbient",34),
    SPELL_WITCH("SPELL_WITCH","witchMagic",35),
    SUSPENDED("SUSPENDED","suspended",36),
    SUSPENDED_DEPTH("SUSPENDED_DEPTH","depthsuspend",37),
    SWEEP_ATTACK("SWEEP_ATTACK",null,38),
    TOWN_AURA("TOWN_AURA","townaura",39),
    VILLAGER_ANGRY("VILLAGER_ANGRY","angryVillager",40),
    VILLAGER_HAPPY("VILLAGER_HAPPY","happyVillager",41),
    WATER_BUBBLE("WATER_BUBBLE","bubble",42),
    WATER_DROP("WATER_DROP","droplet",43),
    WATER_SPLASH("WATER_SPLASH","splash",44),WATER_WAKE("WATER_WAKE","wake",45);


    public static int PARTICLE_RADIUS = 10;
    private static final Random random = new Random();


    private static final Map<String, ParticleEffect> LEGACY_COMPAT_MAP = new HashMap<>();
    private static final Map<String, ParticleEffect> PARTICLE_EFFECTS_MAP = new HashMap<>();
    private static final Map<Integer, ParticleEffect> PARTICLE_EFFECTS_ID_MAP = new HashMap<>();

    static {
        for (ParticleEffect effect : EnumSet.allOf(ParticleEffect.class)) {
            PARTICLE_EFFECTS_MAP.put(effect.getName(), effect);
            PARTICLE_EFFECTS_MAP.put(effect.name(), effect);
            PARTICLE_EFFECTS_ID_MAP.put(effect.getId(), effect);
            LEGACY_COMPAT_MAP.put(effect.legacyName,effect);
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
    /* 1.7.10 compat */
    private String legacyName;

    ParticleEffect(String name, String legacyName, int id) {
        this.name = name;
        this.id = id;
        this.legacyName = legacyName;
    }

    /**
     * Used to retrieve the legacy name of the particle effect, for versions PRIOR to the massive refactor.
     * @return items legacy name, or null if it was not in a legacy version.
     */
    public String getLegacyName() {
        return legacyName;
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