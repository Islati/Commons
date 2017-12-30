package com.caved_in.commons.nms.minecraft_1_8_R3;

import com.caved_in.commons.nms.ForceRespawnHandler;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.bukkit.entity.Player;

import java.util.ConcurrentModificationException;

public class ForceRespawnHandler_18R3 implements ForceRespawnHandler {
	@Override
	public void forceRespawn(Player player) throws ConcurrentModificationException {

		try {
			//Retrieve the packetplayinclientcommand class which we'll be using to force a respawn
			Class<?> packetPlayInClientCommandClass = ReflectionUtilities.getNMSClass("PacketPlayInClientCommand");
			//Retrieve the enumClientCommand class which is used to send the "PERFORM_RESPAWN" param (enum) as part of the packet.
			Class<?> enumClientCommandClass = ReflectionUtilities.getNMSClass("PacketPlayInClientCommand.EnumClientCommand");
			Class<Enum> enumClientCommand = (Class<Enum>) enumClientCommandClass;

			//Now we translate the above class into an enum value, as per requried by the packet we're sending
			Enum<?> PERFORM_RESPAWN = Enum.valueOf(enumClientCommand, "PERFORM_RESPAWN");

			//Create an instance of the packet!
			Object packet = ReflectionUtilities.getConstructor(packetPlayInClientCommandClass, enumClientCommandClass).newInstance(PERFORM_RESPAWN);

			//Away we go! Send it off to our player and hope it works :)
			NmsPlayers.sendPacket(player, packet);


		} catch (Exception ex) {
			ex.printStackTrace();
		}
//		((CraftPlayer) player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
	}
}
