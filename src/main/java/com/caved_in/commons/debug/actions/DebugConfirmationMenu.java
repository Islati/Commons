package com.caved_in.commons.debug.actions;

import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.menus.confirmation.ConfirmationMenu;
import com.caved_in.commons.time.TimeType;
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
