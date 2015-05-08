package com.caved_in.commons.sound;

import com.caved_in.commons.player.Players;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class SoundPlayer {

    private SoundPlayerType type;

    private Location center = null;
    private double radius = -1;

    private Location soundLoc;

    private Set<UUID> playerIds = Sets.newHashSet();

    private SoundData soundData = new SoundData();

    public SoundPlayer() {

    }

    public SoundPlayer forPlayers(Player... players) {
        playerIds.clear();

        for (Player p : players) {
            playerIds.add(p.getUniqueId());
        }

        type = SoundPlayerType.PLAYERS;
        return this;
    }

    public SoundPlayer forPlayers(Collection<Player> players) {
        playerIds.clear();
        players.stream().map(p -> p.getUniqueId()).forEach(playerIds::add);
        type = SoundPlayerType.PLAYERS;
        return this;
    }

    public SoundPlayer forPlayerIds(Collection<UUID> ids) {
        this.playerIds = Sets.newHashSet(playerIds);
        type = SoundPlayerType.PLAYERS;
        return this;
    }

    public SoundPlayer atLocation(Location soundLoc) {
        this.soundLoc = soundLoc;
        return this;
    }

    public SoundPlayer forArea(Location center, double radius) {
        this.center = center;
        this.radius = radius;
        type = SoundPlayerType.AREA;
        return this;
    }

    public SoundPlayer forAreaDistant(Location center, Location soundLoc, double radius) {
        this.center = center;
        this.radius = radius;
        this.soundLoc = soundLoc;
        type = SoundPlayerType.AREA_DISTANT;
        return this;
    }

    public void play() {
        switch (type) {
            case PLAYERS:
                if (soundLoc == null) {
                    playerIds.stream().map(Players::getPlayer).forEach(
                            p -> {
                                Sounds.playSound(p, soundData);
                            }
                    );
                } else {
                    playerIds.stream().map(Players::getPlayer).forEach(
                            p -> {
                                Sounds.playSoundDistant(p, soundLoc, soundData);
                            }
                    );
                }
                break;
            case AREA:
                Sounds.playSoundForPlayersAtLocation(center, soundData);
                break;
            case AREA_DISTANT:
                Sounds.playSoundDistantAtLocation(center, soundLoc, radius, soundData);
                break;
        }
    }

    /**
     * @return the data builder to specify what sound will be played, along with pitch and volume.
     */
    public SoundData data() {
        return soundData;
    }

    private enum SoundPlayerType {
        PLAYERS,
        AREA,
        AREA_DISTANT
    }
}
