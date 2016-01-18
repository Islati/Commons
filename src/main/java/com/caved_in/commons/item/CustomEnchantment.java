package com.caved_in.commons.item;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
//todo DOCUMENT EVERYTHING
public abstract class CustomEnchantment extends Enchantment {
    private int id;
    private String name;
    private int minLVl;
    private int maxLvl;

    private EnchantmentTarget target;

    private Set<Enchantment> conflictions = new HashSet<>();

    public CustomEnchantment(int id, String name) {
        super(id);
        this.id = id;
        this.name = name;
    }

    public void setRange(int minLVl, int maxLvl) {
        this.minLVl = minLVl;
        this.maxLvl = maxLvl;
    }

    public void setTarget(EnchantmentTarget target) {
        this.target = target;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLvl;
    }

    @Override
    public int getStartLevel() {
        return minLVl;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return target;
    }

    public void setConflictions(Enchantment... enchants) {
        Collections.addAll(conflictions, enchants);
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return conflictions.contains(enchantment);
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return target.includes(itemStack);
    }

    public abstract void onAttack(Player player, LivingEntity entity);

    public abstract void onBlockBreak(Player player, Block block);

    public abstract void onTick(Player player);

}