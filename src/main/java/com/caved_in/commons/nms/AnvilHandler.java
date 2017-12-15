package com.caved_in.commons.nms;

import com.caved_in.commons.reflection.ReflectionUtilities;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.joor.Reflect;

public interface AnvilHandler {

    Object newAnvilContainer(Player player);

    default Inventory makeInventory(Object container) {

        Class nmsContainer = ReflectionUtilities.getNMSClass("Container");

        return Reflect.on(nmsContainer.cast(container)).call("getBukkitView").call("getTopInventory").get();
    }

    default int getNextContainerId(Player player) {
        return Reflect.on(NmsPlayers.toEntityPlayer(player)).call("nextContainerCounter").get();
    }

    default void handleInventoryCloseEvent(Player player) {
        Class craftEventFactory = ReflectionUtilities.getCBClass("event.CraftEventFactory");

        Reflect.on(craftEventFactory).call("handleInventoryCloseEvent",NmsPlayers.toEntityPlayer(player));
    }

    default void setContainerId(Object container, int id) {
        Class nmsContainer = ReflectionUtilities.getNMSClass("Container");

        Reflect.on(nmsContainer.cast(container)).set("windowId",id);
    }

    default void addActiveContainerSlotListener(Object container, Player player) {
        Class nmsContainer = ReflectionUtilities.getNMSClass("Container");

        Reflect.on(nmsContainer.cast(container)).call("addSlotListener",NmsPlayers.toEntityPlayer(player));
    }

    default Object createPacket(AnvilPacketType packet, int containerId) {
        switch (packet) {
            case OPEN_WINDOW:
                Class packetPlayOutOpenWindowClass = ReflectionUtilities.getNMSClass("PacketPlayOutOpenWindow");
                Class chatMessageClass = ReflectionUtilities.getNMSClass("ChatMessage");

                Object chatMessageObject = Reflect.on(chatMessageClass).create(
                        //Get the blocks ANVIL class and call the method to get their name
                        Reflect.on(ReflectionUtilities.getNMSClass("Blocks")).field("ANVIL").call("a").get() + ".name"
                );

                return Reflect.on(packetPlayOutOpenWindowClass).create(containerId, "minecraft:anvil", Reflect.on(chatMessageObject).as(chatMessageClass));
//                return new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage(Blocks.ANVIL.a() + ".name"));
            case CLOSE_WINDOW:
                Class packetPlayOutCloseWindow = ReflectionUtilities.getNMSClass("PacketPlayOutCloseWindow");

                return Reflect.on(packetPlayOutCloseWindow).create(containerId).get();
//
//                return new PacketPlayOutCloseWindow(containerId);
            default:
                return null;
        }
    }
}
