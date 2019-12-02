package com.caved_in.commons.enchantments;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class GlowingEnchant extends Enchantment {

    private static GlowingEnchant instance = null;

    public static GlowingEnchant getInstance() {
        if (instance == null) {
            instance = new GlowingEnchant();
        }

        return instance;
    }

    @Getter
    private static boolean registered = false;

    public static boolean register() {
        if (registered) {
            return false;
        }

        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GlowingEnchant glow = GlowingEnchant.getInstance();
            Enchantment.registerEnchantment(glow);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    protected GlowingEnchant() {
        super(NamespacedKey.minecraft("glowing"));
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return false;
    }
}
