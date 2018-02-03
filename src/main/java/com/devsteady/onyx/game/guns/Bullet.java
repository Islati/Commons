package com.devsteady.onyx.game.guns;

import com.devsteady.onyx.effect.ParticleEffect;
import com.devsteady.onyx.effect.Particles;
import com.devsteady.onyx.game.clause.BulletDamageEntityClause;
import com.devsteady.onyx.time.BasicTicker;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Bullet extends BaseBullet {

    /* Path-tracing ticker, every 7 blocks traveled, particles are made
    to trace / show the path of the bullet
     */
    private BasicTicker ticker = new BasicTicker(7);

    public Bullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, double spread, BulletDamageEntityClause damageConditions) {
        super(shooter, gun, item, force, damage, spread, damageConditions);
    }

    public Bullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, double spread) {
        super(shooter, gun, item, force, damage, spread);
    }

    public Bullet(Player shooter, Gun gun, ItemStack item, double force, double damage, double spread) {
        super(shooter, gun, item, force, damage, spread);
    }

    @Override
    public void onTravel(Location l) {
        if (ticker.allow()) {
            Particles.sendToLocation(l, ParticleEffect.SPELL_INSTANT, 2);
            ticker.reset();
        } else {
            ticker.tick();
        }
    }
}
