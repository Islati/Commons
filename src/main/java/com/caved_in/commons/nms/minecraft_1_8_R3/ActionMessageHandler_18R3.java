package com.caved_in.commons.nms.minecraft_1_8_R3;

import com.caved_in.commons.nms.ActionMessageHandler;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.utilities.StringUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.entity.Player;

public class ActionMessageHandler_18R3 implements ActionMessageHandler {
    private static final String ACTION_JSON = "{\"text\": \"%s\"}";

    @Override
    public void actionMessage(Player player, String message) {
        IChatBaseComponent actionComponent = IChatBaseComponent.ChatSerializer.a(String.format(ACTION_JSON, StringUtil.colorize(message)));
        PacketPlayOutChat actionChatPacket = new PacketPlayOutChat(actionComponent, (byte) 2);
        NmsPlayers.sendPacket(player, actionChatPacket);
    }
}
