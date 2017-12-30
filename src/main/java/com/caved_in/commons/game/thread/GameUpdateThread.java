package com.caved_in.commons.game.thread;

import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.game.IGameCore;
import com.caved_in.commons.game.feature.FeatureManager;

public class GameUpdateThread implements Runnable {

    private IGameCore core;

    public GameUpdateThread(CraftGame core) {
        this.core = core;
    }

    @Override
    public void run() {
        //Tick the main update thread!
        core.update();

        //Loop through all the registered features and tick them, aswell!
        FeatureManager features = core.getFeatureManager();

        //If the feature manager has features enabled
        if (features.hasFeatures()) {
            //tick those that are active & enabled.
            features.tickEnabled();
        }
    }
}
