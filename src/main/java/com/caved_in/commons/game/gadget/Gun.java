package com.caved_in.commons.game.gadget;

import com.caved_in.commons.game.guns.BulletActions;
import com.caved_in.commons.game.guns.GunProperties;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface Gun {

	public void damage(LivingEntity damaged, Player shooter);

	public void onFire(Player shooter);

	public BulletActions getBulletActions();

	public void setBulletActions(BulletActions actions);

	public GunProperties attributes();

	public void attributes(GunProperties attributes);

	public boolean reload(Player player);

	public boolean needsReload(Player player);
}
