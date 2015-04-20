package com.caved_in.commons.debug.gadget;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.Effects;
import com.caved_in.commons.game.guns.BaseArrow;
import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ProtoExplosionArrow extends BaseArrow {
    private static ProtoExplosionArrow instance = null;

    public static ProtoExplosionArrow getInstance() {
        if (instance == null) {
            instance = new ProtoExplosionArrow();
        }
        return instance;
    }

    public ProtoExplosionArrow() {
        super(ItemBuilder.of(Material.ARROW).name("&eExploding Arrows &7(Prototype)").lore("&c&lKABOOM!").item());
    }

    @Override
    public boolean onDamage(LivingEntity entity, Player shooter) {
        if (entity.getType() == EntityType.CREEPER) {
            Creeper creeper = (Creeper) entity;
            creeper.setPowered(true);
            return true;
        }

        Effects.explode(entity.getLocation(), 0.5f, true, false);
        Chat.message(shooter, "&cBoom!");
        Chat.debug("Explodede!!!!!");
        return true;
    }

    @Override
    public int id() {
        return 13996;
    }
}
