package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.entity.Entities;
import com.devsteady.onyx.menus.confirmation.ConfirmationMenu;
import com.devsteady.onyx.time.TimeType;
import org.bukkit.entity.Player;

public class DebugConfirmationMenu implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        ConfirmationMenu.of("Smite yourself?")
                .onConfirm((m, p) -> {
                    p.getWorld().strikeLightning(p.getLocation());
                    m.closeMenu(p);
                }).onDeny((m, p) -> {
            Entities.burn(p, 1, TimeType.MINUTE);
            m.closeMenu(p);
        }).exitOnClickOutside(false).denyOnClose()
                .openMenu(player);
    }

    @Override
    public String getActionName() {
        return "confirmation_menu";
    }
}
