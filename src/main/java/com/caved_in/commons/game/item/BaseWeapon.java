package com.caved_in.commons.game.item;

import com.caved_in.commons.game.clause.PlayerDamageEntityClause;
import com.caved_in.commons.game.gadget.ItemGadget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseWeapon extends ItemGadget implements Weapon {
	private Set<PlayerDamageEntityClause> damageClauses = new HashSet<>();

	private WeaponProperties properties = new WeaponProperties();

	public BaseWeapon(ItemStack item) {
		super(item);
	}

	@Override
	public void perform(Player holder) {
		onSwing(holder);
	}

	public void addDamageClause(PlayerDamageEntityClause... clauses) {
		Collections.addAll(damageClauses, clauses);
	}

	@Override
	public boolean canDamage(Player p, LivingEntity e) {
		for (PlayerDamageEntityClause clause : damageClauses) {
			if (!clause.canDamage(p, e)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public WeaponProperties properties() {
		return properties;
	}

	@Override
	public abstract void onSwing(Player p);

	@Override
	public abstract void onActivate(Player p);

	@Override
	public abstract void onAttack(Player p, LivingEntity e);

	@Override
	public abstract void onDrop(Player p);

	public abstract int id();
}
