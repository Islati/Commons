package com.caved_in.commons.chat;

import com.caved_in.commons.Messages;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatCommandHandler implements Listener {
    private Map<String, ChatCommand> commands = new HashMap<>();

    private Set<String> prefixes = new HashSet<>();

    private BukkitPlugin parent;

    private boolean overrideCommands = false;

    public ChatCommandHandler(BukkitPlugin plugin) {
        this.parent = plugin;
    }

    public BukkitPlugin getParent() {
        return parent;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String msg = e.getMessage();

        /*
        Check if they're using a registered prefix on the message / command.
        If not, then it's not for the ChatCommandHandler to use!
         */
        if (!hasPrefix(msg)) {
            return;
        }

        String[] argsUnparsed = msg.split(" ");

        String[] args = null;

        if (argsUnparsed.length > 0) {
            Chat.debug("[ChatCommand] Arguments were passed for command [" + msg + "]");
            args = new String[argsUnparsed.length - 1];
        }

        if (args != null) {
            try {
                Chat.debug("[ChatCommand] Beginning to parse arguments for chat command");
                System.arraycopy(argsUnparsed, 1, args, 0, argsUnparsed.length - 1);
                Chat.debug("[ChatCommand] Parsed arguments for chat command");
            } catch (IndexOutOfBoundsException | NullPointerException | ArrayStoreException ex1) {
                ex1.printStackTrace();
            }
        }


        ChatCommand command = null;

        if (args == null) {
            if (!isCommand(msg)) {
                Chat.debug(String.format("[ChatCommand] Invalid Command: %s", msg));
                return;

            }

            command = getCommand(msg);

            if (command == null) {
                Chat.message(player, "&cInvalid Chat Command: &e" + msg);
                return;
            }

            if (!canPerform(player, command)) {
                Chat.message(player, Messages.permissionRequired(command.permission()));
                return;
            }

            command.perform(player, new String[]{});
            Chat.debug("[ChatCommand] Player " + player.getName() + " executed command '" + command.name() + "'");
            return;
        }

        try {
            String commandMessage = argsUnparsed[0];

            command = getCommand(commandMessage);

            if (command == null) {
                Chat.message(player, "&cInvalid Chat Command: &e" + msg);
                return;
            }

            if (!canPerform(player, command)) {
                return;
            }

            command.perform(player, args);
            Chat.debug("[ChatCommand] Player " + player.getName() + " executed command '" + command.name() + "'");
        } catch (Exception ex) {
            Chat.message(player, "&cInvalid Chat Command: &e" + msg);
            return;
        }
    }

    public boolean registerCommand(ChatCommand cmd) {
        /*
        Verify that no command with the given name has already been registered.
        If it has, and the chat command handler doesn't have override
         */
        if (!overrideCommands) {
            if (isCommand(cmd.name())) {
                return false;
            }
        }

        /*
        Put the commands prefix and name in the map of commands,
        so when the player performs it there's distinction and less parsing on the fly!
         */
        String commandExecuteString = String.format("%s%s", cmd.prefix(), cmd.name());

        prefixes.add(cmd.prefix());

        commands.put(commandExecuteString, cmd);

        return true;
    }

    public boolean isCommand(String text) {
        for (ChatCommand command : commands.values()) {
            if (command.name().equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

    private ChatCommand getCommand(String text) {
        for (Map.Entry<String, ChatCommand> entry : commands.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(text)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private boolean hasPrefix(String text) {
        for (String prefix : prefixes) {
            if (StringUtil.startsWithIgnoreCase(text, prefix)) {
                return true;
            }
        }

        return false;
    }

    private boolean canPerform(Player player, ChatCommand cmd) {
        /*
        Global commands allow any player to execute them, and don't require
        permissions to function!

        Otherwise, if it's not global, make sure the player executing has
        the permission required to execute the command.
         */
        return cmd.global() || player.hasPermission(cmd.permission());

    }

    public void setOverrideCommands(boolean override) {
        this.overrideCommands = override;
    }
}
