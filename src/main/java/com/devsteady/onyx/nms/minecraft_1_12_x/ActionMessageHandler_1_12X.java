package com.devsteady.onyx.nms.minecraft_1_12_x;

import com.devsteady.onyx.nms.ActionMessageHandler;
import com.devsteady.onyx.nms.NmsPlayers;
import com.devsteady.onyx.utilities.ReflectionUtilities;
import com.devsteady.onyx.utilities.StringUtil;
import org.bukkit.entity.Player;
import org.joor.Reflect;

public class ActionMessageHandler_1_12X implements ActionMessageHandler {
	@Override
	public void actionMessage(Player player, String message) {

		/*
		Create our packet by instancing reflection on playout chat, with the chat component text
		inheritely inside its instancing.

		Using the Reflect module allows us to write functional, builder-like code
		for handling our work!
		 */
		Object packet = Reflect.on(ReflectionUtilities.getNMSClass("PacketPlayOutChat"))
				.create(
						//First argument: Chat Component Text, used to hold our action bar message
						Reflect.on(ReflectionUtilities.getNMSClass("ChatComponentText"))
								.create(StringUtil.colorize(message))
								.get(),
						//Second Argument: byte 2; Signifies where the chat is destined.
						Reflect.on(ReflectionUtilities.getNMSClass("ChatMessageType")).call("valueOf","GAME_INFO").get()
				)
			.get();

		NmsPlayers.sendPacket(player,packet);
	}
}
