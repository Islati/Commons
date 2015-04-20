package com.caved_in.commons.item;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LambdaEnchantment extends CustomEnchantment {
    public static LambdaEnchantment of(int id, String name) {
        return new LambdaEnchantment(id, name);
    }

    public LambdaEnchantment(int id, String name) {
        super(id, name);
    }

    @Override
    public void onAttack(Player player, LivingEntity entity) {

    }

    @Override
    public void onBlockBreak(Player player, Block block) {

    }

    @Override
    public void onTick(Player player) {

    }

    //todo implement interface actions to help with lambda enchants / being built with ease.


}
