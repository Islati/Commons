package com.caved_in.commons.debug.gadget;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.effect.Effects;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.game.gadget.BaseGun;
import com.caved_in.commons.game.guns.BulletActions;
import com.caved_in.commons.game.guns.GunProperties;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FishCannon extends BaseGun {
	private GunProperties properties = new GunProperties();

	private int id;

	public FishCannon(int id) {
		super(ItemBuilder.of(Material.DIAMOND_BARDING).name("&eFish Cannon").item());
		this.id = id;
		setBulletActions(FishCannonAction.getInstance());
		initProperties();
	}

	private void initProperties() {
		properties.ammunition(ItemBuilder.of(Material.RAW_FISH).name("&3Live Ammo").item());
		attributes(properties);
	}

	@Override
	public void onFire(Player shooter) {
		ParticleEffects.sendToLocation(ParticleEffects.DRIP_WATER, shooter.getEyeLocation(), NumberUtil.getRandomInRange(3, 10));
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
		public void onHit(LivingEntity damaged) {
			Location hitLoc = damaged.getLocation();

			Effects.explode(hitLoc, 1.0f, false, false);

			ItemStack item = Items.makeItem(Material.COOKED_FISH);

			List<Location> circle = Locations.getCircle(hitLoc, 3);
			circle.forEach(l -> {
				Item dropItem = Worlds.dropItem(l, item);
				dropItem.setFireTicks((int) TimeHandler.getTimeInTicks(4, TimeType.SECOND));
			});

			Worlds.clearDroppedItems(hitLoc, 3, 3, TimeType.SECOND);
		}

		@Override
		public void onHit(Block block) {
			ParticleEffects.sendToLocation(ParticleEffects.CLOUD, block.getLocation(), NumberUtil.getRandomInRange(1, 3));
			Blocks.breakBlock(block, false, true);
			Blocks.scheduleBlockRegen(block, true);
		}
	}

	@Override
	public int id() {
		return id;
	}
}
