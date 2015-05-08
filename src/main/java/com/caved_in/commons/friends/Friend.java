package com.caved_in.commons.friends;

import java.util.UUID;

public class Friend {
    private UUID playerId;
    private UUID friendId;
    private long lastSeenOnline = 0L;
    private boolean isAccepted = false;

    /**
     * @param player     name of the player who's friend this is
     * @param friend     name of the players friend
     * @param isAccepted if the friend is accepted or not
     */
    public Friend(UUID player, UUID friend, boolean isAccepted) {
        this.playerId = player;
        this.friendId = friend;
        this.isAccepted = isAccepted;
    }

    /**
     * Creates a new friend object where the accepted status is defaulted to false
     *
     * @param player name of the player who's friend this is
     * @param friend name of the players friend
     */
    public Friend(UUID player, UUID friend) {
        this(player, friend, false);
    }

    public UUID getFriendId() {
        return friendId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
