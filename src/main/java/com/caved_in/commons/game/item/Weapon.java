package com.caved_in.commons.game.item;

import com.caved_in.commons.game.gadget.Gadget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface Weapon extends Gadget {

    @Override
    default void perform(Player holder) {
        onSwing(holder);
    }

    /**
     * What actions to take when a player attacks the target entity;
     *
     * @param p player attacking
     * @param e entity attacked
     */
    void onAttack(Player p, LivingEntity e);

    /**
     * What actions to take when a player swings without hitting an entity!
     *
     * @param p player swinging the weapon
     */
    void onSwing(Player p);


    /**
     * What action to take when a player activates (right clicks with it equipped) the weapon
     *
     * @param p player activating the weapon
     */
    void onActivate(Player p);

    /**
     * Whether or not the player is able to damage the targeted entity
     *
     * @param p player attacking
     * @param e entity being attacked
     * @return true if the player is able to attack the entity, false otherwise
     */
    boolean canDamage(Player p, LivingEntity e);

    /**
     * Get the properties of the weapon.
     *
     * @return properties of the given weapon
     */
    WeaponProperties properties();
}
