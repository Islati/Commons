package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.effect.ParticleEffect;
import com.devsteady.onyx.effect.Particles;
import org.bukkit.entity.Player;

public class DebugParticleEffects implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        Particles.sendToPlayer(player, ParticleEffect.HEART, player.getLocation(), 15);
    }

    @Override
    public String getActionName() {
        return "particle_effects";
    }
}
