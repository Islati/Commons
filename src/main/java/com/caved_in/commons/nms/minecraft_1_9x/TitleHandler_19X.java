package com.caved_in.commons.nms.minecraft_1_9x;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.Title;
import com.caved_in.commons.chat.TitleBuilder;
import com.caved_in.commons.nms.AbstractTitle;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.StringUtil;
import com.mysql.jdbc.StringUtils;
import org.bukkit.entity.Player;
import org.joor.Reflect;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;


public class TitleHandler_19X implements AbstractTitle.TitleHandler {
    private Title blankTitle = TitleBuilder.create().fadeIn(0).stay(0).fadeOut(0).subtitle("").title("").build();

    private Class<?> packetPlayOutTitleClass;
    private Class<?> chatComponentTextClass;

    private Class<?> iChatBaseComponentClass;

    private Constructor chatComponentConstructor;

    private Class<?> enumTitleActionClass;

    private Class<Enum> enumTitleAction;

    public TitleHandler_19X() {
        packetPlayOutTitleClass = ReflectionUtilities.getNMSClass("PacketPlayOutTitle");


        chatComponentTextClass = ReflectionUtilities.getNMSClass("ChatComponentText");
        iChatBaseComponentClass = ReflectionUtilities.getNMSClass("IChatBaseComponent");

        chatComponentConstructor = ReflectionUtilities.getConstructor(chatComponentTextClass, String.class);

		/*
        The EnumTitleAction class is the first subclass of PacketPlayOutTitle so
		we snag it up via good-ol reflections!
		 */
        enumTitleActionClass = packetPlayOutTitleClass.getDeclaredClasses()[0];

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

            Object titleTextComponent = null;
            Object subtitleTextComponent = null;
            Object subtitlePacket = null;
            Object timePacket = null;
            Object titlePacket = null;
            boolean hasSubtitle = false;
            boolean hasTitle = false;

            if (t.getTitle() != null) {
                hasTitle = true;

                titleTextComponent = chatComponentConstructor.newInstance(StringUtil.colorize(t.getTitle()));
                titlePacket = titlePacketContstructor.newInstance(TITLE, titleTextComponent, t.getFadeInTime(), t.getStayTime(), t.getFadeOutTime());
            }

            if (t.getSubtitle() != null) {
                hasSubtitle = true;

                chatComponentConstructor.newInstance(StringUtil.colorize(t.getSubtitle()));
                subtitlePacket = subtitlePacketConstructor.newInstance(SUBTITLE, subtitleTextComponent, t.getFadeInTime(), t.getStayTime(), t.getFadeOutTime());
            }


            int fadeIn = t.getFadeInTime();
            int stay = t.getStayTime();
            int fadeOut = t.getFadeOutTime();

            if (!t.isTicks()) {
                fadeIn = Math.toIntExact(TimeHandler.getTimeInTicks(fadeIn, TimeType.SECOND));
                fadeOut = Math.toIntExact(TimeHandler.getTimeInTicks(fadeOut, TimeType.SECOND));
                stay = Math.toIntExact(TimeHandler.getTimeInTicks(stay, TimeType.SECOND));
            }

            if (hasTitle) {


                timePacket = timePacketConstructor.newInstance(fadeIn, stay, fadeOut);
                NmsPlayers.sendPacket(player, timePacket);
                NmsPlayers.sendPacket(player, titlePacket);
            }

            if (hasSubtitle) {
                timePacket = timePacketConstructor.newInstance(fadeIn, stay, fadeOut);

                NmsPlayers.sendPacket(player, timePacket);
                NmsPlayers.sendPacket(player, subtitlePacket);
            }

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
