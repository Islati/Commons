package com.devsteady.onyx.debug.gadget;

import com.devsteady.onyx.effect.Effects;
import com.devsteady.onyx.effect.ParticleEffect;
import com.devsteady.onyx.effect.Particles;
import com.devsteady.onyx.game.gadget.Gadgets;
import com.devsteady.onyx.game.guns.BaseGun;
import com.devsteady.onyx.game.guns.BulletActions;
import com.devsteady.onyx.item.ItemBuilder;
import com.devsteady.onyx.item.Items;
import com.devsteady.onyx.location.Locations;
import com.devsteady.onyx.time.TimeHandler;
import com.devsteady.onyx.time.TimeType;
import com.devsteady.onyx.utilities.NumberUtil;
import com.devsteady.onyx.world.Worlds;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FishCannon extends BaseGun {
    private static FishCannon instance = null;

    public static FishCannon getInstance() {
        if (instance == null) {
            instance = new FishCannon();
            Gadgets.registerGadget(instance);
        }
        return instance;
    }

    public FishCannon() {
        super(ItemBuilder.of(Material.DIAMOND_BARDING).name("&eFish Cannon"));
        setBulletActions(FishCannonAction.getInstance());
        initProperties();
    }

    private void initProperties() {
        properties().ammunition(ItemBuilder.of(Material.RAW_FISH).name("&3Live Ammo")).roundsPerShot(2).shotDelay(5).clipSize(100).reloadSpeed(2);
        bulletProperties().damage(10).delayBetweenRounds(1).spread(1.2).speed(5).effect(ParticleEffect.WATER_BUBBLE);
    }

    @Override
    public void onFire(Player shooter) {
    }

    private static class FishCannonAction implements BulletActions {
        private static FishCannonAction instance;

        public static FishCannonAction getInstance() {
            if (instance == null) {
                instance = new FishCannonAction();
            }
            return instance;
        }

        @Override
        public void onHit(Player player, LivingEntity damaged) {
            Location hitLoc = damaged.getLocation();

            Effects.explode(hitLoc, 1.0f, false, false);

            ItemStack item = Items.makeItem(Material.COOKED_FISH);

            List<Location> circle = Locations.getCircle(hitLoc, 3);
            circle.forEach(l -> {
                Item dropItem = Worlds.dropItem(l, item,false);
                dropItem.setFireTicks((int) TimeHandler.getTimeInTicks(4, TimeType.SECOND));
            });

            Worlds.clearDroppedItems(hitLoc, 3, 3, TimeType.SECOND);
        }

        @Override
        public void onHit(Player player, Block block) {
            Particles.sendToLocation(block.getLocation(), ParticleEffect.CLOUD, NumberUtil.getRandomInRange(1, 3));
        }
    }
}
