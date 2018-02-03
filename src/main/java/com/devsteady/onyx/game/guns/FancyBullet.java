package com.devsteady.onyx.game.guns;

import com.devsteady.onyx.effect.ParticleEffect;
import com.devsteady.onyx.game.clause.BulletDamageEntityClause;
import com.devsteady.onyx.utilities.NumberUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class FancyBullet extends Bullet {
    private ParticleEffect effect;

    private BulletDamageEntityClause damageConditions = null;

    public FancyBullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, double spread, ParticleEffect effect) {
        super(shooter, gun, item, force, damage, spread);
        this.effect = effect;
    }

    public FancyBullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, double spread, ParticleEffect effect, BulletDamageEntityClause damageConditions) {
        super(shooter, gun, item, force, damage, spread, damageConditions);
        this.effect = effect;
        this.damageConditions = damageConditions;
    }

    public FancyBullet(Player shooter, Gun gun, ItemStack item, double force, double damage, double spread, ParticleEffect effect) {
        super(shooter, gun, item, force, damage, spread);
        this.effect = effect;
    }

    @Override
    public void onTravel(Location l) {
        ParticleEffect.sendToLocation(effect, l, NumberUtil.getRandomInRange(3, 5));
    }
}
