package com.caved_in.commons.game;

import com.caved_in.commons.game.feature.FeatureManager;
import com.caved_in.commons.game.feature.GameFeature;
import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.game.thread.GameUpdateThread;
import com.caved_in.commons.plugin.BukkitPlugin;

/**
 * Barebones implementation of the GameCore. Extends {@link com.caved_in.commons.plugin.BukkitPlugin}
 *
 * @param <T> UserManager used to handle all the user data associated with the Game.
 */
public abstract class CraftGame<T extends UserManager> extends BukkitPlugin implements GameCore {

    private FeatureManager featureManager;

    @Override
    public void onEnable() {
        super.onEnable();

        /*
        Initialize the feature manager!
         */
        featureManager = new FeatureManager(this);

        //Create the core update thread and begin running it immediately, with the desired delay.
        GameUpdateThread updateThread = new GameUpdateThread(this);
        getThreadManager().registerSyncRepeatTask("Game Update", updateThread, 20, tickDelay());
    }

    public abstract void startup();

    public abstract void shutdown();

    public abstract String getAuthor();

    public abstract void initConfig();

    @Override
    public abstract void update();

    public abstract long tickDelay();

    public abstract T getUserManager();

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    public void registerFeatures(GameFeature... features) {
        getFeatureManager().addFeatures(features);
        /*
        Register the listeners attached to each feature!
         */
        registerListeners(features);
    }
}
