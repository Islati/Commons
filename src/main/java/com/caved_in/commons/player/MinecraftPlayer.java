package com.caved_in.commons.player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.location.PreTeleportLocation;
import com.caved_in.commons.location.PreTeleportType;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.Skip;
import lombok.ToString;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MinecraftPlayer extends User {
    private static Commons commons = Commons.getInstance();

    @Path("last-online")
    private long lastOnline = 0L;

    @Path("is-premium")
    private boolean isPremium = false;
    //	private FriendList friendsList;

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
    private long reloadEnd = 0;

    /**
     * Location the player was before their last teleport
     */
    private PreTeleportLocation preTeleportLocation;

    /**
     * The custom arrow that the player's got equipped!
     */
    private ItemStack equippedArrow;

    private TeleportRequest teleportRequest;

    /**
     * PlayerWrapper  initialization with assigning their currency to {@param currencyAmount}
     *
     * @param playerName     name of the player to be instanced
     */
    @Deprecated
    public MinecraftPlayer(String playerName) {
        setName(playerName);
        initWrapper();
    }

    public MinecraftPlayer(UUID id) {
        setId(id);
        setName(Players.getPlayer(id).getName());
        initWrapper();
    }

    public void dispose() {
    }

    private void initWrapper() {
        lastOnline = System.currentTimeMillis();
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

    /**
     * Change the player walk speed; The higher the number, the faster they'll fly.
     * Used internally {@link com.caved_in.commons.command.commands.SpeedCommand}
     *
     * @param walkSpeed speed to give the player.
     */
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

    /**
     * Change the players fly speed.
     * The higher the number, the faster they'll fly.
     * Used internally in {@link com.caved_in.commons.command.commands.SpeedCommand}
     *
     * @param flySpeed speed to give the player.
     */
    public void setFlySpeed(double flySpeed) {
        this.flySpeed = flySpeed;
        getPlayer().setFlySpeed((float) flySpeed);
    }

    /**
     * @return the players location prior to their most recent teleport
     */
    public PreTeleportLocation getPreTeleportLocation() {
        return preTeleportLocation;
    }

    /**
     * Change the players pre-teleport location.
     * Used internally in {@link com.caved_in.commons.command.commands.BackCommand}
     *
     * @param loc          location the player was/is standing pre-teleport.
     * @param teleportType the type of teleport the player was involved in.
     */
    public void setPreTeleportLocation(Location loc, PreTeleportType teleportType) {
        this.preTeleportLocation = new PreTeleportLocation(loc, teleportType);
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

    /**
     * Set whether or not the player is in debug mode.
     * Used internally in {@link com.caved_in.commons.command.commands.DebugModeCommand} and {@link com.caved_in.commons.player.Players} to manage player(s) debugging.
     *
     * @param value boolean representing the desired debug status.
     */
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

    /**
     * Set whether or not the player is hiding others.
     * Used internally to provide methods for {@link com.caved_in.commons.player.Players} in the methods related to player visibility (hide/unhide)
     *
     * @param hidingOtherPlayers whether or not the user is hiding other players.
     */
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
     * {@link com.caved_in.commons.game.guns.Gun} and {@link com.caved_in.commons.game.guns.BaseGun}
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

    public void setTeleportRequest(TeleportRequest request) {
        this.teleportRequest = request;
        commons.debug("Teleport request for " + getName() + " has been set; Requester = " + request.requesterName);
    }

    public void requestTeleportTo(Player target) {
        MinecraftPlayer mcTarget = Commons.getInstance().getPlayerHandler().getData(target);
        Player player = getPlayer();

        mcTarget.setTeleportRequest(new TeleportRequest(TeleportRequest.TeleportRequestType.TELEPORT_TO, player, target));
        Chat.message(player, "&eYour teleport request was sent to &e" + target.getName());
        Chat.message(target, "&eYou've received a &6teleport request &efrom &a" + player.getName() + " &eto teleport to you.", "&aAccept &eor &cdeny&e the request with &a/tpaccept &eor &c/tpdeny");
    }

    public void requestTeleportHere(Player target) {
        MinecraftPlayer mcTarget = Commons.getInstance().getPlayerHandler().getData(target);
        Player player = getPlayer();

        mcTarget.setTeleportRequest(new TeleportRequest(TeleportRequest.TeleportRequestType.TELEPORT_HERE, player, target));
        Chat.message(player, "&eYour teleport request was sent to &e" + target.getName());
        Chat.message(target, "&eYou've received a &6teleport request &efrom &a" + player.getName() + " &efor you to teleport to them.", "&aAccept &eor &cdeny &ethe request with &a/tpaccept &eor &c/tpdeny");
    }

    public boolean hasTeleportRequest() {
        if (teleportRequest == null) {
            return false;
        }

        return !teleportRequest.hasExpired();
    }

    public TeleportRequest getTeleportRequest() {
        return teleportRequest;
    }

    public void acceptTeleport() {
        teleportRequest.accept(getPlayer());
        teleportRequest = null;
    }

    public void denyTeleport() {
        teleportRequest.deny(getPlayer());
        teleportRequest = null;
    }

    @ToString(of = {"filled", "requesterName", "requestedName", "requester", "receiver", "type"})
    public static class TeleportRequest {

        private static enum TeleportRequestType {
            TELEPORT_TO,
            TELEPORT_HERE
        }

        public static final boolean ONLY_REQUESTED_CAN_ACCEPT = true;
        private static final long TIME_UNTIL_EXPIRY = TimeHandler.getTimeInMilles(30, TimeType.SECOND);
        //TODO Add configurable expire time.

        private long expire;
        private boolean filled = false;

        private UUID requester;
        private UUID receiver;

        public final String requesterName;

        public final String requestedName;
        private TeleportRequestType type;

        public TeleportRequest(TeleportRequestType type, Player playerRequesting, Player playerReceiving) {
            this.type = type;
            expire = Long.sum(System.currentTimeMillis(), TIME_UNTIL_EXPIRY);
            this.requester = playerRequesting.getUniqueId();
            this.receiver = playerReceiving.getUniqueId();
            requestedName = playerReceiving.getName();
            requesterName = playerRequesting.getName();

            commons.debug(this.toString());
        }

        public void accept(Player accepting) {
            switch (type) {
                                /*
				Teleport the player who's accepting the teleport to the player
				they requested to teleport to!
				 */
                case TELEPORT_TO:
                    Player toRequested = Players.getPlayer(requester);
                    //					commons.debug("<TP> Player " + accepting.getName() + " teleported to " + toRequested.getName());
                    Players.teleport(toRequested, accepting);
                    Chat.message(toRequested, Messages.playerTeleportedToPlayer(toRequested.getName()));
                    Chat.message(accepting, Messages.playerTeleportedToYou(accepting.getName()));
                    filled = true;
                    break;
                case TELEPORT_HERE:
                    Player hereRequested = Players.getPlayer(requester);
                    //					commons.debug("<TP-HERE> Player " + hereRequested.getName() + " teleported to " + accepting.getName());
                    Players.teleport(accepting, hereRequested);
                    Chat.message(hereRequested, Messages.playerTeleportedToPlayer(accepting.getName()));
                    Chat.message(accepting, Messages.playerTeleportedToYou(hereRequested.getName()));
                    filled = true;
                    break;
            }
        }

        public void deny(Player denier) {
            Player sender = Players.getPlayer(requester);
            Chat.message(denier, String.format("&cYou denied the teleport request from &e%s", sender.getName()));
            Chat.message(sender, String.format("&e%s&c denied your teleport request", denier.getName()));
            filled = true;
        }

        public boolean hasExpired() {
            if (filled) {
                return true;
            }

            return System.currentTimeMillis() > expire;
        }

    }
}
