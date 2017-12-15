package com.caved_in.commons.nms.minecraft_1_12_x;

import com.caved_in.commons.nms.AnvilHandler;
import com.caved_in.commons.nms.AnvilPacketType;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.reflection.ReflectionUtilities;
import javassist.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.joor.Reflect;


public class AnvilHandler_1_12X implements AnvilHandler {
    private Class anvilContainerClass = null;

    public AnvilHandler_1_12X() {
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
            ctClass = pool.getAndRename("net.minecraft.server.v1_12_R1.ContainerAnvil","net.minecraft.server.v1_12_R1.CustomAnvilContainer");
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        if (ctClass == null) {
            throw new NullPointerException("Unable to create compile-time class for net.minecraft.server.v1_12_R1.ContainerAnvil");
        }

        //Previous versions have the override of method A; This isn't the case for us.

        // Add the constructor that takes a Human Entity.
        try {
            ctClass.addConstructor(CtNewConstructor.make("public AnvilContainer(net.minecraft.server.v1_12_R1.EntityHuman entity) { super(entity.inventory,entity.world,new net.minecraft.server.v1_12_R1.BlockPosition(0,0,0), entity); this.checkReachable = false;}",ctClass));
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
}
