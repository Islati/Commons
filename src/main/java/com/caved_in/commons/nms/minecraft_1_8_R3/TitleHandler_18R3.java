package com.caved_in.commons.nms.minecraft_1_8_R3;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.Title;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.nms.AbstractTitle;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;


public class TitleHandler_18R3 implements AbstractTitle.TitleHandler {
	private Title blankTitle = TitleBuilder.create().fadeIn(0).stay(0).fadeOut(0).subtitle("").title("").build();

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
			Enum<?> TIMES = Enum.valueOf(enumTitleAction, "TIMES");

			Constructor titlePacketContstructor = ReflectionUtilities.getConstructor(packetPlayOutTitleClass,
					enumTitleActionClass,
					iChatBaseComponentClass,
					int.class,
					int.class,
					int.class
			);

			Constructor timePacketConstructor = ReflectionUtilities.getConstructor(packetPlayOutTitleClass,
					int.class,
					int.class,
					int.class
			);

			Constructor subtitlePacketConstructor = ReflectionUtilities.getConstructor(packetPlayOutTitleClass,
					enumTitleActionClass,
					iChatBaseComponentClass
			);

			Object titleTextComponent = chatComponentConstructor.newInstance(StringUtil.colorize(t.getTitle()));
			Object subtitleTextComponent = chatComponentConstructor.newInstance(StringUtil.colorize(t.getSubtitle()));


			Object timePacket = timePacketConstructor.newInstance(TIMES, t.getFadeInTime(), t.getStayTime(), t.getFadeOutTime());
			Object titlePacket = titlePacketContstructor.newInstance(TITLE, titleTextComponent, t.getFadeInTime(), t.getStayTime(), t.getFadeOutTime());
			Object subtitlePacket = subtitlePacketConstructor.newInstance(SUBTITLE, subtitleTextComponent, t.getFadeInTime(), t.getStayTime(), t.getFadeOutTime());


			NmsPlayers.sendPacket(player, timePacket);
			NmsPlayers.sendPacket(player, titlePacket);
			NmsPlayers.sendPacket(player, timePacket);
			NmsPlayers.sendPacket(player, subtitlePacket);
		} catch (Exception e) {
			Chat.message(player, "Malformed Json Title:", t.getTitle(), "Malformed Json Subtitle:", t.getSubtitle());
			e.printStackTrace();
		}
	}

	@Override
	public void resetTitle(Player player) {
		blankTitle.send(player);
	}

	@Override
	public void clearTitle(Player player) {
		blankTitle.send(player);
	}
}
