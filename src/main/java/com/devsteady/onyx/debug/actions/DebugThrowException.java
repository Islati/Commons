package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.event.StackTraceEvent;
import com.devsteady.onyx.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DebugThrowException implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        Onyx.getInstance().registerListeners(new Listener() {
            @EventHandler
            public void onPlayerDropItem(PlayerDropItemEvent e) {
                if (Players.isDebugging(e.getPlayer())) {
                    throw new RuntimeException("DEBUGGNG AN EXCEPTION THROW FOR STACK-TRACE-EVENT-PURPOSES");
                }
            }

            @EventHandler
            public void onStackTraceEvent(StackTraceEvent e) {
                Chat.messageAll(Players.getAllDebugging(), "Exception Called: " + e.getException().toString());
            }
        });
        Chat.message(player, "&cRegistered Error-Ridden DropItem Listener");
    }

    @Override
    public String getActionName() {
        return "throw_exception";
    }
}
