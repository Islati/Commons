package com.caved_in.commons.game;

import com.caved_in.commons.game.feature.FeatureManager;
import com.caved_in.commons.game.feature.GameFeature;
import com.caved_in.commons.game.players.UserManager;

/**
 * Core used to handle operations related to Game-Engines in commons.
 * Current implementations: {@link CraftGame}, {@link MiniGame}
 * @param <T> usermanager used to handle all the user-data associated with the game.
 */
public interface GameCore<T extends UserManager> {

    /**
     * Operations to perform each game-tick.
     */
    void update();

    /**
     * Delay between each game tick, in minecraft ticks. 20 ticks = 1 second.
     * @return delay between each game tick.
     */
    long tickDelay();

    /**
     * UserManager used to handle all the user data associated with the game.
     * @return UserManager used to handle user data associated and utilized by the core.
     */
    T getUserManager();

    FeatureManager getFeatureManager();

    void registerFeatures(GameFeature... features);
}
