package com.caved_in.commons.packet;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.event.Action;
import com.caved_in.commons.packet.protocol.Protocol;
import com.caved_in.commons.packet.protocol.Sender;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.reflection.SafeField;

import java.lang.reflect.Method;
import java.util.Map;

public class PacketUtil {

	private static final SafeField<Map<Integer, Class<?>>> SERVER_PACKETS_MAP = new SafeField<Map<Integer, Class<?>>>(ReflectionUtilities.getNMSClass("EnumProtocol"), "i");
	private static final SafeField<Map<Integer, Class<?>>> CLIENT_PACKETS_MAP = new SafeField<Map<Integer, Class<?>>>(ReflectionUtilities.getNMSClass("EnumProtocol"), "h");

	private static final Method READ_ACTION = ReflectionUtilities.getMethod(ReflectionUtilities.getNMSClass("EnumEntityUseAction"), "a", ReflectionUtilities.getNMSClass("EnumEntityUseAction"));

	public static Object getPacket(Protocol protocol, Sender sender, int id) {
		try {
			if (sender == Sender.CLIENT) {
				return CLIENT_PACKETS_MAP.get(protocol.toVanilla()).get(id).newInstance();
			}

			if (sender == Sender.SERVER) {
				return SERVER_PACKETS_MAP.get(protocol.toVanilla()).get(id).newInstance();
			}

			return null;
		} catch (Exception e) {
			Commons.messageConsole(Messages.FAILED_TO_RETRIEVE_PACKET(protocol.toString(), sender.toString(), id));
			return null;
		}
	}

	public static Action readAction(Object enumAction) {
		return Action.getFromId((Integer) ReflectionUtilities.invokeMethod(READ_ACTION, null, enumAction));
	}
}
