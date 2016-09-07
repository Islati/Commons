package com.caved_in.commons.nms.minecraft_1_8_R3;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.nms.AbstractTitle;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.utilities.StringUtil;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;


public class TitleHandler_18R3 implements AbstractTitle.TitleHandler {

	private Class<?> packetPlayOutTitleClass;
	private Class<?> chatComponentTextClass;

	private Class<?> iChatBaseComponentClass;

	private Constructor chatComponentConstructor;

	private Class<?> enumTitleActionClass;

	private Class<Enum> enumTitleAction;

	public TitleHandler_18R3() {
		packetPlayOutTitleClass = ReflectionUtilities.getNMSClass("PacketPlayOutTitle");


		chatComponentTextClass = ReflectionUtilities.getNMSClass("ChatComponentText");
		iChatBaseComponentClass = ReflectionUtilities.getNMSClass("IChatBaseComponent");

		chatComponentConstructor = ReflectionUtilities.getConstructor(chatComponentTextClass, String.class);

		enumTitleActionClass = ReflectionUtilities.getNMSClass("PacketPlayOutTitle.EnumTitleAction");

		enumTitleAction = (Class<Enum>) enumTitleActionClass;
	}


	@Override
	public void send(Player player, AbstractTitle t) {
		try {
//			CraftPlayer craftplayer = (CraftPlayer) player;
//			PlayerConnection connection = craftplayer.getHandle().playerConnection

			Enum<?> TITLE = Enum.valueOf(enumTitleAction, "TITLE");
			Enum<?> SUBTITLE = Enum.valueOf(enumTitleAction, "SUBTITLE");

			Constructor titlePacketContstructor = ReflectionUtilities.getConstructor(packetPlayOutTitleClass,
					enumTitleActionClass,
					iChatBaseComponentClass,
					Integer.class,
					Integer.class,
					Integer.class
			);

			Constructor subtitlePacketConstructor = ReflectionUtilities.getConstructor(packetPlayOutTitleClass,
					enumTitleActionClass,
					iChatBaseComponentClass
			);

			Object titleTextComponent = chatComponentConstructor.newInstance(StringUtil.colorize(t.getTitle()));
			Object subtitleTextComponent = chatComponentConstructor.newInstance(StringUtil.colorize(t.getSubtitle()));

			Object titlePacket = titlePacketContstructor.newInstance(TITLE, titleTextComponent, t.getFadeInTime(), t.getStayTime(), t.getFadeOutTime());
			Object subtitlePacket = subtitlePacketConstructor.newInstance(SUBTITLE, subtitleTextComponent);


			NmsPlayers.sendPacket(player, titlePacket);
			NmsPlayers.sendPacket(player, subtitlePacket);
		} catch (Exception e) {
			Chat.message(player, "Malformed Json Title:", t.getTitle(), "Malformed Json Subtitle:", t.getSubtitle());
		}
	}

	@Override
	public void resetTitle(Player player) {
		try {
			Constructor resetPacketConstructor = ReflectionUtilities.getConstructor(packetPlayOutTitleClass, enumTitleActionClass, iChatBaseComponentClass);


			Enum<?> RESET = Enum.valueOf(enumTitleAction, "RESET");
			Object resetPacket = resetPacketConstructor.newInstance(RESET, null);

			NmsPlayers.sendPacket(player, resetPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearTitle(Player player) {
		try {
			Constructor resetPacketConstructor = ReflectionUtilities.getConstructor(packetPlayOutTitleClass, enumTitleActionClass, iChatBaseComponentClass);


			Enum<?> CLEAR = Enum.valueOf(enumTitleAction, "CLEAR");
			Object clearPacket = resetPacketConstructor.newInstance(CLEAR, null);

			NmsPlayers.sendPacket(player, clearPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
