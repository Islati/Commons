package com.caved_in.commons.nms.minecraft_1_8_R3;

import com.caved_in.commons.nms.AbstractTitle;
import com.caved_in.commons.utilities.StringUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class TitleHandler_18R3 implements AbstractTitle.TitleHandler {

    public TitleHandler_18R3() {
    }


    @Override
    public void send(Player player, AbstractTitle t) {
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + StringUtil.colorize(t.getTitle()) + "'}");
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + StringUtil.colorize(t.getSubtitle()) + "'}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, t.getFadeInTime(), t.getStayTime(), t.getFadeOutTime());
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }

    @Override
    public void resetTitle(Player player) {
        try {
            PacketPlayOutTitle resetPacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(resetPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearTitle(Player player) {
        try {
            PacketPlayOutTitle resetPacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.CLEAR, null);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(resetPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
