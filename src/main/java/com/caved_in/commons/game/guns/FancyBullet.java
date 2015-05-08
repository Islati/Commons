package com.caved_in.commons.game.guns;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.game.clause.BulletDamageEntityClause;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class FancyBullet extends Bullet {
    private ParticleEffects effect;

    private BulletDamageEntityClause damageConditions = null;

    public FancyBullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, double spread, ParticleEffects effect) {
        super(shooter, gun, item, force, damage, spread);
        this.effect = effect;
    }

    public FancyBullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, double spread, ParticleEffects effect, BulletDamageEntityClause damageConditions) {
        super(shooter, gun, item, force, damage, spread, damageConditions);
        this.effect = effect;
        this.damageConditions = damageConditions;
    }

    public FancyBullet(Player shooter, Gun gun, ItemStack item, double force, double damage, double spread, ParticleEffects effect) {
        super(shooter, gun, item, force, damage, spread);
        this.effect = effect;
    }

    @Override
    public void onTravel(Location l) {
        ParticleEffects.sendToLocation(effect, l, NumberUtil.getRandomInRange(3, 5));
    }
}
