package com.caved_in.commons.game.feature;

import com.caved_in.commons.game.CraftGame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FeatureManager {
    private Map<String, GameFeature> gameFeatures = new HashMap<>();

    private CraftGame game;

    public FeatureManager(CraftGame game) {
        this.game = game;
    }

    public FeatureManager() {

    }

    public void addFeatures(GameFeature... features) {
        for (GameFeature feature : features) {
            gameFeatures.put(feature.identifier(), feature);
        }
    }

    public void removeFeature(String id) {
        gameFeatures.remove(id);
    }

    public void setParent(CraftGame game) {
        this.game = game;
    }

    public Collection<GameFeature> getFeatures() {
        return gameFeatures.values();
    }

    public Collection<GameFeature> getEnabledFeatures() {
        return getFeatures().stream().filter(GameFeature::enabled).collect(Collectors.toList());
    }

    public Collection<GameFeature> getDisabledFeatures() {
        return getFeatures().stream().filter(g -> !g.enabled()).collect(Collectors.toList());
    }

    public void tickEnabled() {
        getFeatures().stream().filter(GameFeature::enabled).forEach(gf -> {
            if (gf.allowExecute()) {
                gf.tick();
            }
        });
    }
}
