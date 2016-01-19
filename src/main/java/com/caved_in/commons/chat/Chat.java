package com.caved_in.commons.chat;

import com.caved_in.commons.Commons;
import com.caved_in.commons.menu.ChatMenu;
import com.caved_in.commons.nms.NMS;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.threading.RunnableManager;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.ArrayUtils;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Used to messages to players, console, specific permission groups, users with permissions, action messages, titles, and more.
 */
public class Chat {
    private static Commons commons = Commons.getInstance();

    private static ConsoleCommandSender commandSender = Bukkit.getConsoleSender();

    private static final Map<String, Cooldown> messageCooldowns = new HashMap<>();

    /**
     * Broadcast the messages passed, automagically formatting all color codes.
     *
     * @param messages message(s) to be broadcasted.
     */
    public static void broadcast(String... messages) {
        for (String message : messages) {
            Bukkit.broadcastMessage(StringUtil.formatColorCodes(message));
        }
    }

    /**
     * Broadcast the message passed (formatting all color codes) to all players
     * action bars!
     *
     * @param message message to send to players action bars.
     */
    public static void broadcastActionMessage(String message) {
        Players.stream().forEach(p -> actionMessage(p, message));
    }

    /**
     * Message the console,and have any color codes colorized.
     *
     * @param messages messages to send to the console.
     */
    public static void messageConsole(String... messages) {
        String[] msgs = messages;
        for (int i = 0; i < msgs.length; i++) {
            msgs[i] = StringUtil.colorize(msgs[i]);
        }

        commandSender.sendMessage(msgs);
    }

    /**
     * Message the console a collection of messages, translating all color codes.
     *
     * @param messages messages to send to the console.
     */
    public static void messageConsole(Collection<String> messages) {
        for (String message : messages) {
            commandSender.sendMessage(StringUtil.formatColorCodes(message));
        }
    }

    /**
     * Send the player a message on their action-bar, as opposed to the chat window.
     *
     * @param player  player receiving the message.
     * @param message message to send.
     */
    public static void actionMessage(Player player, String message) {
        NMS.getActionMessageHandler().actionMessage(player, message);
    }

    /**
     * Send message(s) to the receiver, formatting color codes.
     *
     * @param target   receiver of the message(s)
     * @param messages message(s) to send
     */
    public static void message(CommandSender target, String... messages) {
        sendMessage(target, messages);
    }


    /**
     * Sends a message to all online players.
     *
     * @param message message to send
     * @see #sendMessage(org.bukkit.command.CommandSender, String)
     * @since 1.0
     */
    public static void messageAll(String message) {
        for (Player player : Players.allPlayers()) {
            if (player == null) {
                continue;
            }
            sendMessage(player, message);
        }
    }

    /**
     * Send messages to a collection of players.
     *
     * @param receivers players receiving the message.
     * @param messages  messages to send/
     */
    public static void messageAll(Collection<Player> receivers, String... messages) {
        for (Player player : receivers) {
            if (player == null) {
                continue;
            }
            sendMessage(player, messages);
        }
    }

    /**
     * Sends messages to all players <i>with</i> a specific permission
     *
     * @param permission permission to check for on players
     * @param messages   messages to send to the players
     * @see #sendMessage(org.bukkit.command.CommandSender, String...)
     * @since 1.0
     */
    public static void messageAllWithPermission(String permission, String... messages) {
        for (Player player : Players.allPlayers()) {
            if (player.hasPermission(permission)) {
                sendMessage(player, messages);
            }
        }
    }

    /**
     * Sends a message to all players <i>without</i> a specific permission
     *
     * @param permission permission to check for on players
     * @param message    message to send
     * @see #sendMessage(org.bukkit.command.CommandSender, String)
     * @since 1.0
     */
    public static void messageAllWithoutPermission(String permission, String message) {
        for (Player player : Players.allPlayers()) {
            if (player.hasPermission(permission)) {
                sendMessage(player, message);
            }
        }
    }

    /**
     * Sends messages to all players <i>without</i> a specific permission
     *
     * @param permission permission to check for on players
     * @param messages   messages to send to the player
     * @since 1.0
     */
    public static void messageAllWithoutPermission(String permission, String... messages) {
        for (Player player : Players.allPlayers()) {
            if (!player.hasPermission(permission)) {
                for (String message : messages) {
                    sendMessage(player, message);
                }
            }
        }
    }

    /**
     * Sends messages to the commandsender; Automagically formats '&' to their {@link org.bukkit.ChatColor} correspondants
     *
     * @param messageReceiver commandsender to send the message to
     * @param messages        messages to send
     * @since 1.0
     */
    public static void sendMessage(CommandSender messageReceiver, String... messages) {
        for (String message : messages) {
            sendMessage(messageReceiver, message);
        }
    }

    /**
     * Send the player
     *
     * @param receiver
     * @param sound
     * @param delay
     * @param messages
     */
    public static void sendSoundedMessage(Player receiver, Sound sound, int delay, String... messages) {
        int index = 1;
        RunnableManager threadManager = Commons.getInstance().getThreadManager();
        for (String message : messages) {
            threadManager.runTaskLater(new DelayedMessage(receiver, message, sound), TimeHandler.getTimeInTicks(index * delay, TimeType.SECOND));
            index += 1;
        }
    }


    /**
     * Send message(s) to all the online operators.
     *
     * @param messages messages to send to the operators
     */
    public static void messageOps(String... messages) {
        messageAll(Players.onlineOperators(), messages);
    }

    /**
     * Sends messages to all online players
     *
     * @param messages messages to send
     * @see #sendMessage(org.bukkit.command.CommandSender, String)
     * @since 1.0
     */
    public static void messageAll(String... messages) {
        for (Player player : Players.allPlayers()) {
            sendMessage(player, messages);
        }
    }

    /**
     * Send a message to all the players excluding those passed as arguments.
     *
     * @param message    message to send to the players.
     * @param exceptions players to exclude from receiving the message
     */
    public static void messageAllExcept(String message, Player... exceptions) {
        UUID[] playerIds = ArrayUtils.getIdArray(exceptions);
        messageAll(Players.allPlayersExcept(playerIds), message);
    }

    /**
     * Send the player a message, and disallow them from receiving the same message
     * for a restricted amount of time.
     *
     * @param p        player receiving the message
     * @param cooldown amount of seconds between receiving the message.
     * @param message  message to send to the player.
     */
    public static void sendMessageOnCooldown(Player p, int cooldown, String message) {
        if (!messageCooldowns.containsKey(message)) {
            messageCooldowns.put(message, new Cooldown(cooldown));
        }

        Cooldown cool = messageCooldowns.get(message);

        if (cool.isOnCooldown(p)) {
            return;
        }

        cool.setOnCooldown(p);
        sendMessage(p, message);
    }

    /**
     * Send the player a set of messages, with the given delay (in seconds) between each message.
     *
     * @param receiver player receiving the message(s).
     * @param delay    delay between each message being received.
     * @param messages messages to send to the player.
     */
    public static void sendDelayedMessage(Player receiver, int delay, final String... messages) {
        int index = 1;
        RunnableManager threadManager = Commons.getInstance().getThreadManager();
        for (String message : messages) {
            threadManager.runTaskLater(new DelayedMessage(receiver, message), TimeHandler.getTimeInTicks(index * delay, TimeType.SECOND));
            index += 1;
        }
    }

    /**
     * Delayed message, used to send a player a message after a specific duration of time, and in order.
     */
    private static class DelayedMessage implements Runnable {

        private String message;
        private Sound sound = null;
        private UUID receiverId;

        public DelayedMessage(Player player, String message) {
            this.receiverId = player.getUniqueId();
            this.message = StringUtil.colorize(message);
        }

        public DelayedMessage(Player player, String message, Sound sound) {
            this(player, message);
            this.sound = sound;
        }

        @Override
        public void run() {
            Player player = Players.getPlayer(receiverId);

            message(player, message);
            if (sound != null) {
                Sounds.playSound(player, sound);
            }
        }
    }

    /**
     * Sends a message to the receiver a specific number of times;
     *
     * @param messageReceiver the receiver of this message
     * @param message         message to send to the receiver
     * @param messageAmount   how many times the message is to be sent
     */
    public static void sendRepeatedMessage(CommandSender messageReceiver, String message, int messageAmount) {
        for (int i = 0; i < messageAmount; i++) {
            sendMessage(messageReceiver, message);
        }
    }

    /**
     * Sends a message to the commandsender; Automagically formats '&' to their {@link org.bukkit.ChatColor} correspondants
     *
     * @param messageReceiver receiver of the message
     * @param message         message to send
     * @since 1.0
     */
    public static void sendMessage(CommandSender messageReceiver, String message) {
        if (messageReceiver == null || message == null) {
            return;
        }
        messageReceiver.sendMessage(StringUtil.formatColorCodes(message));
    }

    @Deprecated
    /**
     * Sends the given text to all players in debug mode, and the server console; along with logging it in log.txt
     */
    public static void debug(String... message) {
        commons.debug(message);
    }

    @Deprecated
    public static void formatDebug(String msg, Object... formatting) {
        commons.debug(String.format(msg, formatting));
    }

    public static String format(String text) {
        return StringUtil.formatColorCodes(text);
    }

    public static String format(String text, Object... args) {
        return String.format(StringUtil.formatColorCodes(text), args);
    }

    public static void format(Player p, String text, Object... args) {
        message(p, format(text, args));
    }

    /**
     * Initialize a builder of which you can create {@link ChatMenu} by.
     * Allows actions to be registered on click, and handled appropriately.
     *
     * @return a newly created {@link ChatMenu} for creating awesome text-menus.
     */
    public static ChatMenu createMenu() {
        return new ChatMenu();
    }

}
