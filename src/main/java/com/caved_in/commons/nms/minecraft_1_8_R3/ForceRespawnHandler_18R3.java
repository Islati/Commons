package com.caved_in.commons.nms.minecraft_1_8_R3;

import com.caved_in.commons.nms.ForceRespawnHandler;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ConcurrentModificationException;

public class ForceRespawnHandler_18R3 implements ForceRespawnHandler {
	@Override
	public void forceRespawn(Player player) throws ConcurrentModificationException {
		((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
	}
}
