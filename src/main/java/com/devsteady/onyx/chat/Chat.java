package com.devsteady.onyx.chat;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.nms.NMS;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.sound.Sounds;
import com.devsteady.onyx.threading.RunnableManager;
import com.devsteady.onyx.time.Cooldown;
import com.devsteady.onyx.time.TimeHandler;
import com.devsteady.onyx.time.TimeType;
import com.devsteady.onyx.utilities.StringUtil;
import lombok.NonNull;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Used to messages to players, console, specific permission groups, users with permissions, action messages, titles, and more.
 */
public class Chat {

    /**
     * The pixel dimensions of Minecraft's Chat Box.
     */
    private static final int CENTER_PX = 154;

    private static Onyx onyx = Onyx.getInstance();

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
     * Broadcast the base components that will be used to display your message.
     * @param components component(s) to be broadcasted.
     */
    public static void broadcast(BaseComponent... components) {
        for(BaseComponent component : components) {
            Players.allPlayers().forEach(player -> player.spigot().sendMessage(component));
        }
    }

    /**
     * Broadcast the message passed (formatting all color codes) to all players
     * action bars!
     *
     * @param message message to send to players action bars.
     */
    public static void broadcastActionMessage(String message) {
        //todo implement version check
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
        //todo implement version check.
        try {
            NMS.getActionMessageHandler().actionMessage(player, message);
        } catch (Exception ex) {
            message(player,message);
        }
    }

    /**
     * Send message(s) to the receiver, formatting color codes.
     *
     * @param target   receiver of the message(s)
     * @param messages message(s) to send
     */
    public static void message(@NonNull CommandSender target, String... messages) {
        for(String msg : messages) {
            target.sendMessage(format(msg));
        }
    }

    /**
     * Send messages in the form of base components to the player.
     * @param target player receiving the message(s).
     * @param components message (components) to send.
     */
    public static void message(Player target, BaseComponent... components) {
        Stream.of(components).forEach((message) -> target.spigot().sendMessage(message));
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
            message(player, messages);
        }
    }

    public static void messageAll(Collection<Player> receivers, BaseComponent... components) {
        receivers.forEach(p -> message(p,components));
    }

    public static void messageAllWithPermission(String permission, String... messages) {
        for (Player player : Players.allPlayers()) {
            if (player.hasPermission(permission)) {
                message(player, messages);
            }
        }
    }

    public static void messageAllWithPermission(String permission, BaseComponent... components) {
        for(Player player : Players.allPlayers()) {
            if (!player.hasPermission(permission)) {
                continue;
            }

            message(player,components);
        }
    }

    public static void messageAllWithoutPermission(String permission, String message) {
        for (Player player : Players.allPlayers()) {
            if (player.hasPermission(permission)) {
                message(player, message);
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
                    message(player, message);
                }
            }
        }
    }

    public static void messageAllWithoutPermission(String permission, BaseComponent... components) {
        for(Player player : Players.allPlayers()) {
            if (player.hasPermission(permission)) {
                continue;
            }

            message(player,components);
        }
    }

    public static void messageSounded(Player receiver, Sound sound, int delay, String... messages) {
        int index = 1;
        RunnableManager threadManager = Onyx.getInstance().getThreadManager();
        for (String message : messages) {
            threadManager.runTaskLater(new DelayedMessage(receiver, message, sound), TimeHandler.getTimeInTicks(index * delay, TimeType.SECOND));
            index += 1;
        }
    }

    public static void messageAll(String... messages) {
        for (Player player : Players.allPlayers()) {
            message(player, messages);
        }
    }

    public static void messageAll(BaseComponent... components) {
        for (Player player : Players.allPlayers()) {
            message(player,components);
        }
    }

    /**
     * Send the player a set of messages, with the given delay (in seconds) between each message.
     *
     * @param receiver player receiving the message(s).
     * @param delay    delay between each message being received.
     * @param messages messages to send to the player.
     */
    public static void messageDelayed(Player receiver, int delay, final String... messages) {
        int index = 1;
        RunnableManager threadManager = Onyx.getInstance().getThreadManager();
        for (String message : messages) {
            threadManager.runTaskLater(new DelayedMessage(receiver, message), TimeHandler.getTimeInTicks(index * delay, TimeType.SECOND));
            index += 1;
        }
    }

    public static void messageDelayed(Player receiver, int delay, BaseComponent... components) {
        int index = 1;
        RunnableManager threads = Onyx.getInstance().getThreadManager();
        for(BaseComponent component : components) {
            threads.runTaskLater(new DelayedMessage(receiver,component),TimeHandler.getTimeInTicks(index * delay,TimeType.SECOND));
        }
    }

    /**
     * Delayed message, used to send a player a message after a specific duration of time, and in order.
     */
    private static class DelayedMessage implements Runnable {

        private String message;
        private Sound sound = null;
        private UUID receiverId;

        private BaseComponent component = null;

        public DelayedMessage(Player player, String message) {
            this.receiverId = player.getUniqueId();
            this.message = StringUtil.colorize(message);
        }

        public DelayedMessage(Player player, String message, Sound sound) {
            this(player, message);
            this.sound = sound;
        }

        public DelayedMessage(Player player, BaseComponent component) {
            this.receiverId = player.getUniqueId();
            this.component = component;
        }

        public DelayedMessage(Player player, BaseComponent component, Sound sound) {
            this.receiverId = player.getUniqueId();
            this.component = component;
            this.sound = sound;
        }

        @Override
        public void run() {
            Player player = Players.getPlayer(receiverId);

            if (player == null) {
                return;
            }

            if (component != null) {
                message(player,component);
            } else {
                message(player, message);
            }

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
    public static void messageRepeated(CommandSender messageReceiver, String message, int messageAmount) {
        for (int i = 0; i < messageAmount; i++) {
            message(messageReceiver, message);
        }
    }

    public static void messageRepeat(Player player, BaseComponent component, int count) {
        for (int i = 0; i < count; i++) {
            message(player,component);
        }
    }

    /**
     * Sends the given text to all players in debug mode, and the server console; along with logging it in log.txt
     */
    public static void debug(String... message) {
        onyx.debug(message);
    }

    @Deprecated
    public static void formatDebug(String msg, Object... formatting) {
        onyx.debug(String.format(msg, formatting));
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
     * Based on the default font information for Minecraft, center the users text.
     *
     * @param text text that you wish to center.
     * @return String with padding / maths performed to center it.
     */
    public static String center(String text) {
        text = format(text);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : text.toCharArray()) {
            if (c == '§') {
                previousCode = true;
                continue;
            } else if (previousCode) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                } else {
                    isBold = false;
                }
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        return sb.toString() + text;
    }

    /**
     * Send the target (a) centered message(s) .
     * @param target target receiving the message
     * @param messages message(s) to send the target.
     */
    public static void messageCentered(CommandSender target, String... messages) {
        for(String s : messages) {
            message(target,center(s));
        }
    }

}
