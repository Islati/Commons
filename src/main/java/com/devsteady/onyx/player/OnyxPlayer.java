package com.devsteady.onyx.player;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.game.guns.BaseGun;
import com.devsteady.onyx.game.guns.Gun;
import com.devsteady.onyx.location.PreTeleportLocation;
import com.devsteady.onyx.location.PreTeleportType;
import com.devsteady.onyx.time.TimeHandler;
import com.devsteady.onyx.time.TimeType;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.Skip;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class OnyxPlayer extends User {
    /* If the players in debug mode, they'll receive messages when they do practically anything. */
    @Path("debug-mode")
    private boolean debugMode = false;

    @Path("hiding-other-players")
    private boolean hidingOtherPlayers = false;

    @Skip
    private boolean viewingRecipe = false;

    @Path("walk-speed")
    private double walkSpeed = 0.22;

    @Path("fly-speed")
    private double flySpeed = 0.1;

    @Skip
    public static final double DEFAULT_WALK_SPEED = 0.22;

    @Skip
    public static final double DEFAULT_FLY_SPEED = 0.1;

    @Path("god-mode")
    private boolean godMode = false;

    @Skip
    private boolean forceRespawn = false;

    /* Whether or not the player is currently reloading a gun */
    @Skip
    private long reloadEnd = 0;
    /**
     * PlayerWrapper  initialization with assigning their currency to {@param currencyAmount}
     *
     * @param playerName     name of the player to be instanced
     */
    @Deprecated
    public OnyxPlayer(String playerName) {
        setName(playerName);
        initWrapper();
    }

    public OnyxPlayer(UUID id) {
        setId(id);
        setName(Players.getPlayer(id).getName());
        initWrapper();
    }

    public OnyxPlayer(Player player) {
        super(player);
    }

    public void dispose() {
    }

    private void initWrapper() {
        setId(Players.getPlayer(getName()).getUniqueId());
    }

    /**
     * Check whether or not the player has a custom walk speed.
     *
     * @return whether or not the player has a custom walk speed.
     */
    public boolean hasCustomWalkSpeed() {
        return walkSpeed != DEFAULT_WALK_SPEED;
    }

    /**
     * Check whether or not the player has a modified fly speed.
     *
     * @return whether or not the player has a modified fly speed; If the players fly speed is greater than 0.1 then true is returned, false otherwise.
     */
    public boolean hasCustomFlySpeed() {
        return flySpeed != DEFAULT_FLY_SPEED;
    }

    /**
     * Get the players walk speed.
     *
     * @return the players active walk speed.
     */
    public double getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(double walkSpeed) {
        this.walkSpeed = walkSpeed;
        getPlayer().setWalkSpeed((float) walkSpeed);
    }

    /**
     * Get the players active fly speed
     *
     * @return the active fly speed of the given player.
     */
    public double getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(double flySpeed) {
        this.flySpeed = flySpeed;
        getPlayer().setFlySpeed((float) flySpeed);
    }

    /**
     * Check whether or not the player is viewing a recipe.
     * Used internally.
     *
     * @return whether or not the player is viewing a recipe.
     */
    public boolean isViewingRecipe() {
        return viewingRecipe;
    }

    public void setViewingRecipe(boolean viewingRecipe) {
        this.viewingRecipe = viewingRecipe;
    }

    /**
     * Check whether or not the player is in debug mode.
     * Used
     *
     * @return whether or not the player is in debug mode.
     */
    public boolean isInDebugMode() {
        return debugMode;
    }

    public void setInDebugMode(boolean value) {
        debugMode = value;
    }


    /**
     * Check whether or not the player is hiding other players.
     *
     * @return whether or not the player is hiding other players.
     */
    public boolean isHidingOtherPlayers() {
        return hidingOtherPlayers;
    }


    public void setHidingOtherPlayers(boolean hidingOtherPlayers) {
        this.hidingOtherPlayers = hidingOtherPlayers;
    }

    /**
     * Check whether or not the player is reloading their weapon.
     *
     * @return
     */
    public boolean isReloading() {
        return reloadEnd > System.currentTimeMillis();
    }

    /**
     * Set the duration in seconds for the players current reload time. Used internally in
     * {@link Gun} and {@link BaseGun}
     *
     * @param durationSeconds how long the player is "reloading" for.
     */
    public void setReloading(int durationSeconds) {
        this.reloadEnd = System.currentTimeMillis() + TimeHandler.getTimeInMilles(durationSeconds, TimeType.SECOND);
    }

    public boolean hasGodMode() {
        return godMode;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    public void setForceRespawn(boolean force) {
        this.forceRespawn = force;
    }

    public boolean hasForceRespawn() {
        return this.forceRespawn;
    }
}
