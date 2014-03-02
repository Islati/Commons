package com.caved_in.commons.npc.utils;

import java.lang.reflect.Method;
import java.util.Map;

import com.caved_in.commons.Commons;
import com.caved_in.commons.npc.reflection.ReflectionUtil;
import com.caved_in.commons.npc.reflection.SafeField;
import com.caved_in.commons.npc.utils.protocol.Protocol;
import com.caved_in.commons.npc.utils.protocol.Sender;

public class PacketUtil {

    private static final SafeField<Map<Integer, Class<?>>> SERVER_PACKETS_MAP = new SafeField<Map<Integer, Class<?>>>(ReflectionUtil.getNMSClass("EnumProtocol"), "i");
    private static final SafeField<Map<Integer, Class<?>>> CLIENT_PACKETS_MAP = new SafeField<Map<Integer, Class<?>>>(ReflectionUtil.getNMSClass("EnumProtocol"), "h");

    private static final Method READ_ACTION = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("EnumEntityUseAction"), "a", ReflectionUtil.getNMSClass("EnumEntityUseAction"));

    public static Object getPacket(Protocol protocol, Sender sender, int id) {
        try{
            if(sender == Sender.CLIENT) {
                return CLIENT_PACKETS_MAP.get(protocol.toVanilla()).get(id).newInstance();
            }

            if(sender == Sender.SERVER) {
                return SERVER_PACKETS_MAP.get(protocol.toVanilla()).get(id).newInstance();
            }

            return null;
        }catch(Exception e) {
            Commons.messageConsole("Failed to retrieve the packet object for: " + protocol.toString() + ", " + sender.toString() + ", " + id );
            return null;
        }
    }

    public static Action readAction(Object enumAction) {
        return Action.getFromId((Integer) ReflectionUtil.invokeMethod(READ_ACTION, null, enumAction));
    }
}
