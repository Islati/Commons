package com.caved_in.commons.menu;

import com.caved_in.commons.Commons;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class ChatMenuCommandListener implements CommandExecutor {

    private Commons plugin;

    private Map<String, List<ChatMenuListener>> listenerMap = new HashMap<>();

    public ChatMenuCommandListener(Commons plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if ("chatmenu".equals(command.getName())) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length > 0) {
                    String key = args[0];
                    if (listenerMap.containsKey(key)) {
                        List<ChatMenuListener> listeners = listenerMap.get(key);

                        for (ChatMenuListener listener : listeners) {
                            listener.onClick(player);
                        }
                    } else {
                        plugin.getLogger().warning(sender.getName() + " tried to run click-command for an unknown listener");
                    }
                }
            }
        }
        return false;
    }

    public void registerListener(ChatMenuListener listener, String key) {
        List<ChatMenuListener> list = this.listenerMap.get(key);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(listener);
        listenerMap.put(key, list);
    }

    public void registerListener(ChatMenuListener listener, UUID key) {
        registerListener(listener, key.toString().replace("-", ""));
    }

    public String registerListener(ChatMenuListener listener) {
        for (Map.Entry<String, List<ChatMenuListener>> entry : listenerMap.entrySet()) {
            for (ChatMenuListener listener1 : entry.getValue()) {
                if (listener1.equals(listener)) {
                    return entry.getKey();
                }
            }
        }

        UUID uuid = UUID.randomUUID();
        String key = uuid.toString().replace("-", "");
        registerListener(listener, key);
        return key;
    }

    public void unregisterListener(String key) {
        this.listenerMap.remove(key);
    }
}