package com.devsteady.onyx.game.event;

import com.devsteady.onyx.game.guns.BaseGun;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LauncherFireEvent extends Event implements Cancellable {
    private static HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private BaseGun launcher;
    private Player shooter;

    public LauncherFireEvent(Player shooter, BaseGun launcher) {
        this.shooter = shooter;
        this.launcher = launcher;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public static void handle(LauncherFireEvent e) {
        if (e.isCancelled()) {
            return;
        }

        e.getLauncher().perform(e.getShooter());
    }

    public Player getShooter() {
        return shooter;
    }

    public BaseGun getLauncher() {
        return launcher;
    }
}
