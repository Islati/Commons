package com.caved_in.commons.nms.minecraft_1_9x;

import com.caved_in.commons.nms.AnvilHandler;
import com.caved_in.commons.nms.AnvilPacketType;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.reflection.ReflectionUtilities;
import javassist.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.joor.Reflect;


public class AnvilHandler_19X implements AnvilHandler {
    private Class anvilContainerClass = null;

    public AnvilHandler_19X() {
        anvilContainerClass = createAnvilContainerClass();
    }

    @Override
    public Inventory makeInventory(Object container) {

        Class nmsContainer = ReflectionUtilities.getNMSClass("Container");

        return Reflect.on(nmsContainer.cast(container)).call("getBukkitView").call("getTopInventory").get();
    }

    @Override
    public Object newAnvilContainer(Player player) {
        Object container = Reflect.on(anvilContainerClass).create(NmsPlayers.toEntityPlayer(player));

        return container;
    }

    private Class createAnvilContainerClass() {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = null;



        try {
            ctClass = pool.getAndRename("net.minecraft.server.v1_9_R1.ContainerAnvil","net.minecraft.server.v1_9_R1..CustomAnvilContainer");
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        if (ctClass == null) {
            throw new NullPointerException("Unable to create compile-time class for net.minecraft.server.v1_9_R1.ContainerAnvil");
        }

        //Override the a method.
        try {
            ctClass.addMethod(
                    CtNewMethod.make("@Override public boolean a(net.minecraft.server.v1_9_R1.EntityHuman human) {return true;}",ctClass)
            );
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        // Add the constructor that takes a Human Entity.
        try {
            ctClass.addConstructor(CtNewConstructor.make("public AnvilContainer(net.minecraft.server.v1_9_R1.EntityHuman entity) { super(entity.inventory,entity.world,new net.minecraft.server.v1_9_R1.BlockPosition(0,0,0), entity); }",ctClass));
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        try {
            return ctClass.toClass();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object createPacket(AnvilPacketType packet, int containerId) {
        switch (packet) {
            case OPEN_WINDOW:
                Class packetPlayOutOpenWindowClass = ReflectionUtilities.getNMSClass("PacketPlayOutOpenWindow");
                Class chatMessageClass = ReflectionUtilities.getNMSClass("ChatMessage");

                Object chatMessageObject = Reflect.on(chatMessageClass).create(
                        //Get the blocks ANVIL class and call the method to get their name
                        Reflect.on(ReflectionUtilities.getNMSClass("Blocks")).field("ANVIL").call("a").get() + ".name"
                );

                return Reflect.on(packetPlayOutOpenWindowClass).create(containerId,"minecraft:anvil",Reflect.on(chatMessageObject).as(chatMessageClass));
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
