package com.caved_in.commons.npc.injector;

import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.npc.events.PlayerInteractNPCEvent;
import com.caved_in.commons.npc.utils.Action;
import com.caved_in.commons.npc.utils.PacketUtil;
import com.caved_in.commons.npc.utils.protocol.Packet;

public class NPCHandler extends ChannelInboundHandlerAdapter {

    private Player player;

    public NPCHandler(Player player) {
        this.player = player;
    }

    @Override
    public void channelRead(ChannelHandlerContext cxt, Object message) throws Exception {
        if(message.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            Packet packet = new Packet(message);
            int id = packet.read("a");
            Action action = PacketUtil.readAction(packet.read("action"));
            if(Commons.getInstance().isNPC(id)) {
                PlayerInteractNPCEvent event = new PlayerInteractNPCEvent(Commons.getInstance().getNPC(id), action, player);
                Bukkit.getPluginManager().callEvent(event);
            }
        }
        super.channelRead(cxt, message);
    }
}
