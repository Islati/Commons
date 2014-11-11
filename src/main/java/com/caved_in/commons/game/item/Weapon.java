package com.caved_in.commons.game.item;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface Weapon {
	/**
	 * What actions to take when a player attacks the target entity;
	 *
	 * @param p player attacking
	 * @param e entity attacked
	 */
	public void onAttack(Player p, LivingEntity e);

	/**
	 * What actions to take when a player swings without hitting an entity!
	 *
	 * @param p player swinging the weapon
	 */
	public void onSwing(Player p);


	/**
	 * What action to take when a player activates (right clicks with it equipped) the weapon
	 *
	 * @param p player activating the weapon
	 */
	public void onActivate(Player p);

	/**
	 * What action to take when a player drops the weapon.
	 *
	 * @param p player dropping the item.
	 * @param p player dropping the item.
	 */
	public void onDrop(Player p);

	/**
	 * Whether or not the player is able to damage the targeted entity
	 *
	 * @param p player attacking
	 * @param e entity being attacked
	 * @return true if the player is able to attack the entity, false otherwise
	 */
	public boolean canDamage(Player p, LivingEntity e);

	/**
	 * Get the properties of the weapon.
	 *
	 * @return properties of the given weapon
	 */
	public WeaponProperties properties();
}
