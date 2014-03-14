package com.caved_in.commons.npc;

import com.caved_in.commons.event.Action;
import com.caved_in.commons.event.NPCClickEvent;
import com.caved_in.commons.packet.PacketUtil;
import com.caved_in.commons.packet.protocol.Packet;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NPCPacketHandler extends ChannelInboundHandlerAdapter {

	private Player player;

	public NPCPacketHandler(Player player) {
		this.player = player;
	}

	@Override
	public void channelRead(ChannelHandlerContext cxt, Object message) throws Exception {
		//If the packet is an entity being interacted/used
		if (message.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
			NpcHandler npcHandler = NpcHandler.getInstance();
			//Create the packet based on the message passed
			Packet packet = new Packet(message);
			int id = packet.read("a");
			//Get the action object from the packet
			Action action = PacketUtil.readAction(packet.read("action"));
			//If the ID of the entity interacted with is an NPC then fire a new PlayerInteractNPCEvent
			if (npcHandler.isNPC(id)) {
				NPCClickEvent event = new NPCClickEvent(npcHandler.getNPC(id), action, player);
				Bukkit.getPluginManager().callEvent(event);
			}
		}
		super.channelRead(cxt, message);
	}
}
