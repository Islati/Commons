package com.caved_in.commons.nms.minecraft_1_8_R3;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.event.StackTraceEvent;
import com.caved_in.commons.nms.UnhandledStackTrace;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.joor.Reflect;

public class UnhandledStackTrace_18R3 implements UnhandledStackTrace {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            StackTraceEvent.call(e);
        } catch (Throwable th) {
            e.printStackTrace();
        }
    }

    @Override
    public void register() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();

        Thread serverThread = Reflect.on(server).get("serverThread");
        serverThread.setUncaughtExceptionHandler(this);

        Thread primaryThread = Reflect.on(server).get("primaryThread");
        primaryThread.setUncaughtExceptionHandler(this);
        Chat.messageConsole("Registered uncaught exception handler for serverThread and PrimaryThread");
    }
}
