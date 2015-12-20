package com.caved_in.commons.fireworks;

import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.utilities.ArrayUtils;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

/**
 * Manage (almost) all actions relating to Fireworks!
 */
public class Fireworks {
    private static final Random random = new Random();
    private static final Type[] FIREWORK_TYPES = Type.values();
    public static final Color[] FIREWORK_COLOURS = new Color[]{
            Color.WHITE,
            Color.AQUA,
            Color.BLACK,
            Color.BLUE,
            Color.FUCHSIA,
            Color.GRAY,
            Color.GREEN,
            Color.MAROON,
            Color.NAVY,
            Color.OLIVE,
            Color.RED,
            Color.LIME,
            Color.YELLOW,
            Color.ORANGE,
            Color.TEAL,
            Color.PURPLE,
            Color.SILVER
    };

    private static FireworkEffectPlayer fplayer = new FireworkEffectPlayer();

    /**
     * @return a random firework type
     */
    public static Type randomType() {
        return ArrayUtils.getRandom(FIREWORK_TYPES);
    }

    /**
     * @return a random firework color.
     */
    public static Color randomFireworkColor() {
        return ArrayUtils.getRandom(FIREWORK_COLOURS);
    }

    /**
     * @return a random color. Generated using random values in R, B, G.
     */
    public static Color randomColor() {
        return Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    /**
     * Retrieve an array of random colors.
     * @param amount amount of randomized colors to retrieve
     * @return an array of randomly generated colors.
     */
    public static Color[] randomColors(int amount) {
        Color[] colors = new Color[amount];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = randomColor();
        }
        return colors;
    }

    /**
     * Retrieve an array of random firework colors.
     * @param amount amount of randomized colors to retrieve.
     * @return an array of randomly selected firework colors.
     */
    public static Color[] randomFireworkColors(int amount) {
        Color[] colors = new Color[amount];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = randomFireworkColor();
        }
        return colors;
    }

    private static boolean randomBoolean() {
        return random.nextBoolean();
    }

    /**
     * Play a firework effect / launch a firework at the given location.
     * @param location location to prepare the firework at.
     * @param fwEffect effect to attach to the fireworks.
     */
    public static void playFirework(Location location, FireworkEffect fwEffect) {
        Firework firework = Entities.spawnFireworks(location);
        FireworkMeta meta = firework.getFireworkMeta();

        meta.addEffects(fwEffect);
        meta.setPower(NumberUtil.getRandomInRange(1, 4));

        firework.setFireworkMeta(meta);
//		try {
//			fplayer.playFirework(location,fwEffect);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

    }

    /**
     * Play a random firework effect at the given location.
     * @param location location to play the effect at.
     */
    public static void playRandomFirework(Location location) {
        playFirework(location, randomFireworkEffect());
    }

    public static void playRandomFireworks(Location location, int count) {
        for (int i = 0; i < count; i++) {
            playRandomFirework(location);
        }
    }

    /**
     * @return a randomly generated FireworkEffect with all aspects assigned.
     */
    public static FireworkEffect randomFireworkEffect() {
        return FireworkEffect.builder().with(randomType()).withColor(randomFireworkColors(NumberUtil.getRandomInRange(2, 6))).withFade(randomColor()).trail(randomBoolean()).flicker(randomBoolean()).build();
    }
}