package com.caved_in.commons.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerEvent;

public class PlayerDamagePlayerEvent extends PlayerEvent implements Cancellable {
    private static HandlerList handlers = new HandlerList();

    private Player target;

    private EntityDamageEvent.DamageCause damageCause;

    private boolean cancelled;

    private double damage;

    private double finalDamage;

    public PlayerDamagePlayerEvent(Player damager, Player target, double damage, double finalDamage, EntityDamageEvent.DamageCause cause) {
        super(damager);
        this.target = target;
        this.damageCause = cause;
        this.damage = damage;
        this.finalDamage = finalDamage;
    }

    public Player getTarget() {
        return target;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public double getDamage() {
        return damage;
    }

    public double getFinalDamage() {
        return finalDamage;
    }

    public void setDamage(double dmg) {
        this.damage = dmg;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
