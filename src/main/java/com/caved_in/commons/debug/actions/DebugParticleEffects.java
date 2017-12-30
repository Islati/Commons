package com.caved_in.commons.debug.actions;

import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.effect.ParticleEffect;
import com.caved_in.commons.effect.Particles;
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
