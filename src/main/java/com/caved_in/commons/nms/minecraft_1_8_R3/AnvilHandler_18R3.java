package com.caved_in.commons.nms.minecraft_1_8_R3;

import com.caved_in.commons.nms.AnvilHandler;
import com.caved_in.commons.nms.AnvilPacketType;
import com.caved_in.commons.nms.NmsPlayers;
import com.caved_in.commons.reflection.ReflectionUtilities;
import javassist.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.joor.Reflect;


public class AnvilHandler_18R3 implements AnvilHandler {
    private Class anvilContainerClass = null;

    public AnvilHandler_18R3() {
        anvilContainerClass = createAnvilContainerClass();
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
            ctClass = pool.getAndRename("net.minecraft.server.v1_8_R1.ContainerAnvil","net.minecraft.server.v1_8_R1.CustomAnvilContainer");
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        if (ctClass == null) {
            throw new NullPointerException("Unable to create compile-time class for net.minecraft.server.v1_8_R1.ContainerAnvil");
        }

        //Override the a method.
        try {
            ctClass.addMethod(
                    CtNewMethod.make("@Override public boolean a(net.minecraft.server.v1_8_R1.EntityHuman human) {return true;}",ctClass)
            );
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        // Add the constructor that takes a Human Entity.
        try {
            ctClass.addConstructor(CtNewConstructor.make("public AnvilContainer(net.minecraft.server.v1_8_R1.EntityHuman entity) { super(entity.inventory,entity.world,new net.minecraft.server.v1_8_R1.BlockPosition(0,0,0), entity); }",ctClass));
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
