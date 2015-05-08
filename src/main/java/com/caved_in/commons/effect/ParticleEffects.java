package com.caved_in.commons.effect;

import com.caved_in.commons.Commons;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum ParticleEffects {

    NONE("none", -1),

    // == Support for 1.7 Implementation ==
    HUGE_EXPLODE("hugeexplosion", "EXPLOSION_HUGE", 1),
    LARGE_EXPLODE("largeexplode", "EXPLOSION_LARGE", 2),
    BUBBLE("bubble", "WATER_BUBBLE", 3),
    SUSPEND("suspend", "SUSPENDED", 4),
    DEPTH_SUSPEND("depthSuspend", "SUSPENDED_DEPTH", 5),
    MAGIC_CRIT("magicCrit", "CRIT_MAGIC", 6),
    MOB_SPELL("mobSpell", "SPELL_MOB", 7),
    MOB_SPELL_AMBIENT("mobSpellAmbient", "SPELL_MOB_AMBIENT", 8),
    INSTANT_SPELL("instantSpell", "SPELL_INSTANT", 9),
    WITCH_MAGIC("witchMagic", "SPELL_WITCH", 10),
    EXPLODE("explode", "EXPLOSION_NORMAL", 11),
    SPLASH("splash", "WATER_SPLASH", 12),
    LARGE_SMOKE("largesmoke", "SMOKE_LARGE", 13),
    RED_DUST("reddust", "REDSTONE", 14),
    SNOWBALL_POOF("snowballpoof", "SNOWBALL", 15),
    ANGRY_VILLAGER("angryVillager", "VILLAGER_ANGRY", 16),
    HAPPY_VILLAGER("happerVillager", "VILLAGER_HAPPY", 17),
    // == 1.8 Particles and Supported 1.7 Particles ==
    /**
     * 1.8 only!
     *
     * @see {@link #EXPLODE}
     */
    EXPLOSION_NORMAL(EXPLODE.getName(), 18),
    /**
     * 1.8 only!
     *
     * @see {@link #LARGE_EXPLODE}
     */
    EXPLOSION_LARGE(LARGE_EXPLODE.getName(), 19),
    /**
     * 1.8 only!
     *
     * @see {@link #HUGE_EXPLODE}
     */
    EXPLOSION_HUGE(HUGE_EXPLODE.getName(), 20),
    FIREWORKS_SPARK("fireworksSpark", 21),
    /**
     * 1.8 only!
     *
     * @see {@link #BUBBLE}
     */
    WATER_BUBBLE(BUBBLE.getName(), 22),
    /**
     * 1.8 only!
     *
     * @see {@link #SPLASH}
     */
    WATER_SPLASH(SPLASH.getName(), 23),
    /**
     * 1.8 only!
     */
    WATER_WAKE(24),
    /**
     * 1.8 only!
     *
     * @see {@link #SUSPEND}
     */
    SUSPENDED(SUSPEND.getName(), 25),
    /**
     * 1.8 only!
     *
     * @see {@link #DEPTH_SUSPEND}
     */
    SUSPENDED_DEPTH(DEPTH_SUSPEND.getName(), 26),
    CRIT("crit", 27),
    /**
     * 1.8 only!
     *
     * @see {@link #MAGIC_CRIT}
     */
    CRIT_MAGIC(MAGIC_CRIT.getName(), 28),
    /**
     * 1.8 only!
     */
    SMOKE_NORMAL(29),
    /**
     * 1.8 only!
     *
     * @see {@link #LARGE_SMOKE}
     */
    SMOKE_LARGE(LARGE_SMOKE.getName(), 30),
    SPELL("spell", 31),
    /**
     * 1.8 only!
     *
     * @see {@link #INSTANT_SPELL}
     */
    SPELL_INSTANT(INSTANT_SPELL.getName(), 32),
    /**
     * 1.8 only!
     *
     * @see {@link #MOB_SPELL}
     */
    SPELL_MOB(MOB_SPELL.getName(), 33),
    /**
     * 1.8 only!
     *
     * @see {@link #MOB_SPELL_AMBIENT}
     */
    SPELL_MOB_AMBIENT(MOB_SPELL_AMBIENT.getName(), 34),
    /**
     * 1.8 only!
     *
     * @see {@link #WITCH_MAGIC}
     */
    SPELL_WITCH(WITCH_MAGIC.getName(), 35),
    DRIP_WATER("dripWater", 36),
    DRIP_LAVA("dripLava", 37),
    /**
     * 1.8 only!
     *
     * @see {@link #ANGRY_VILLAGER}
     */
    VILLAGER_ANGRY(ANGRY_VILLAGER.getName(), 38),
    /**
     * 1.8 only!
     *
     * @see {@link #HAPPY_VILLAGER}
     */
    VILLAGER_HAPPY(HAPPY_VILLAGER.getName(), 39),
    TOWN_AURA("townaura", 40),
    NOTE("note", 41),
    PORTAL("portal", 42),
    ENCHANTMENT_TABLE("enchantmenttable", 43),
    FLAME("flame", 44),
    LAVA("lave", 45),
    FOOTSTEP("footstep", 46),
    CLOUD("cloud", 47),
    REDSTONE("reddust", 48),
    SNOWBALL("snowballpoof", 49),
    SNOW_SHOVEL("snowshovel", 50),
    SLIME("slime", 51),
    HEART("heart", 52),
    /**
     * 1.8 only!
     */
    BARRIER(53),
    /**
     * 1.8 only!
     */
    ITEM_CRACK(54),
    /**
     * 1.8 only!
     */
    BLOCK_CRACK(55),
    /**
     * 1.8 only!
     */
    BLOCK_DUST(56),
    /**
     * 1.8 only!
     */
    WATER_DROP(57),
    /**
     * 1.8 only!
     */
    ITEM_TAKE(58),
    /**
     * 1.8 only!
     */
    MOB_APPEARANCE(59),
    ENDER(DEPTH_SUSPEND.getName(), 60),
    AIR_BUBBLE(BUBBLE.getName(), 61),
    MAGIC_CRITICAL_HIT(MAGIC_CRIT.name(), 62);

    public static int PARTICLE_RADIUS = 10;
    private static final Random random = new Random();

    private static Class<?> nmsPacketPlayOutParticle = ReflectionUtilities.getNMSClass("PacketPlayOutWorldParticles");
    private static Class<?> nmsEnumParticle;

    private static final Map<String, ParticleEffects> PARTICLE_EFFECTS_MAP = new HashMap<>();

    private static Commons commons = Commons.getInstance();

    static {
        for (ParticleEffects effect : EnumSet.allOf(ParticleEffects.class)) {
            PARTICLE_EFFECTS_MAP.put(effect.getName(), effect);
            PARTICLE_EFFECTS_MAP.put(effect.name(), effect);
        }
    }

    public static ParticleEffects getEffect(String name) {
        return PARTICLE_EFFECTS_MAP.get(name);
    }


    private String name;
    private String enumValue;
    private int id;

    ParticleEffects(String name, String enumValue, int id) {
        this.name = name;
        this.enumValue = enumValue;
        this.id = id;
    }

    ParticleEffects(String name, int id) {
        this(name, null, id);
    }

    ParticleEffects(int id) {
        this(null, null, id);
    }

    /**
     * Gets the name of the Particle Effect
     *
     * @return The particle effect name
     */
    String getName() {
        return name;
    }

    String getValue() {
        return enumValue;
    }

    /**
     * Gets the id of the Particle Effect
     *
     * @return The id of the Particle Effect
     */
    int getId() {
        return id;
    }

    /**
     * Send a particle effect to a player
     *
     * @param effect   The particle effect to send
     * @param player   The player to send the effect to
     * @param location The location to send the effect to
     * @param offsetX  The x range of the particle effect
     * @param offsetY  The y range of the particle effect
     * @param offsetZ  The z range of the particle effect
     * @param speed    The speed (or color depending on the effect) of the particle
     *                 effect
     * @param count    The count of effects
     */
    public static void sendToPlayer(ParticleEffects effect, Player player, Location location, float offsetX, float offsetY,
                                    float offsetZ, float speed, int count) {
        if (effect == ParticleEffects.NONE) {
            return;
        }

        try {
            Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
            NmsPlayers.sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Send a particle effect to all players
     *
     * @param effect   The particle effect to send
     * @param location The location to send the effect to
     * @param offsetX  The x range of the particle effect
     * @param offsetY  The y range of the particle effect
     * @param offsetZ  The z range of the particle effect
     * @param speed    The speed (or color depending on the effect) of the particle
     *                 effect
     * @param count    The count of effects
     */
    public static void sendToLocation(ParticleEffects effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        if (effect == ParticleEffects.NONE) {
            return;
        }

        try {
            Object packet = createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count);
            for (Player player : Locations.getPlayersInRadius(location, PARTICLE_RADIUS)) {
                //If the player has other players hidden, we don't want to see their particles, either!
                MinecraftPlayer wrapper = commons.getPlayerHandler().getData(player);
                if (wrapper.isHidingOtherPlayers()) {
                    continue;
                }
                NmsPlayers.sendPacket(player, packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendToLocation(ParticleEffects effects, Location loc, int count) {
        sendToLocation(effects, loc, random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat(), count);
    }

    private static Object createPacket(ParticleEffects effect, Location location, float offsetX, float offsetY,
                                       float offsetZ, float speed, int count) throws Exception {

        if (effect == ParticleEffects.NONE) {
            return null;
        }

        Object packet = null;

        if (Plugins.getBukkitVersion().contains("v1_8")) {
            try {
                if (nmsEnumParticle == null) {
                    nmsEnumParticle = ReflectionUtilities.getNMSClass("EnumParticle");
                }
                packet = nmsPacketPlayOutParticle.getConstructor(new Class[]{nmsEnumParticle, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class})
                        .newInstance(ReflectionUtilities.getEnum(nmsEnumParticle.getName() + "." + (effect.getValue() != null ? effect.getValue() : effect.name().toUpperCase())), true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, speed, count, new int[]{});

            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to create Particle " + effect.name() + ". (Version 1.8): " + e.getMessage());
            }
        } else {
            try {
                if (effect.getName() == null) {
                    throw new Exception();
                }
                packet = nmsPacketPlayOutParticle.getConstructor(new Class[]{String.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class}).newInstance(effect.getName(), (float) location.getX(), (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, speed, count);
            } catch (Exception e) {
                throw new IllegalArgumentException("Unable to create Particle " + effect.name() + ". (Invalid Server Version: 1.7) " + e.getMessage());
            }
        }
//
//		if (count <= 0) {
//			count = 1;
//		}
//		Class<?> packetClass = getCraftClass("PacketPlayOutWorldParticles");
//		Object packet = nmsPacketPlayOutParticle.getConstructor(String.class, float.class, float.class, float.class, float.class,
//				float.class, float.class, float.class, int.class).newInstance(effect.name, (float) location.getX(),
//				(float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, speed, count);


        return packet;
    }

}