package com.caved_in.commons.nms;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.nms.minecraft_1_8_R3.*;
import com.caved_in.commons.nms.minecraft_1_9x.*;
import com.caved_in.commons.nms.no_implementation.ActionMessageHandlerNI;
import com.caved_in.commons.nms.no_implementation.ParticleEffectsHandlerNI;
import com.caved_in.commons.nms.no_implementation.TitleHandlerNI;
import com.caved_in.commons.nms.no_implementation.UnhandledStackTraceNI;
import com.caved_in.commons.nms.non_breaking_implementation.ForceRespawnHandlerNonBreaking;
import com.caved_in.commons.nms.non_breaking_implementation.NonBreakingInventoryHandler;
import com.caved_in.commons.plugin.Plugins;

public class NMS {

    /*
    Used to hook the threads for monitoring stack traces!
    Custom stack trace handler, and manages StackTraceEvent!
     */
    private static UnhandledStackTrace stackTraceHandler = null;

    private static ActionMessageHandler actionMessageHandler = null;

    private static AbstractTitle.TitleHandler titleHandler = null;

    private static InventoryHandler inventoryHandler = null;

    private static ForceRespawnHandler forceRespawnHandler = null;

    private static ParticleEffectsHandler particleEffectsHandler = null;

    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            throw new IllegalAccessError("Unable to re-initialize NMS Handler.");
        }

        Chat.debug("NMS Version is: '" + Plugins.getNmsVersion() + "'");

        switch (Plugins.getNmsVersion()) {
            case "v1_8_R3":
                stackTraceHandler = new UnhandledStackTrace_18R3();
                actionMessageHandler = new ActionMessageHandler_18R3();
                titleHandler = new TitleHandler_18R3();
                forceRespawnHandler = new ForceRespawnHandler_18R3();
                particleEffectsHandler = new ParticleEffectsHandler_18R3();
                break;
            case "v1_9_R1":
            case "v1_9_R2":
                stackTraceHandler = new UnhandledStackTrace_19X();
                titleHandler = new TitleHandler_19X();
                forceRespawnHandler = new ForceRespawnHandler_19X();
                actionMessageHandler = new ActionMessageHandler_19X();
                particleEffectsHandler = new ParticleEffectsHandler_19X();
                break;
            default:
                stackTraceHandler = new UnhandledStackTraceNI();
                actionMessageHandler = new ActionMessageHandlerNI();
                titleHandler = new TitleHandlerNI();
                forceRespawnHandler = new ForceRespawnHandlerNonBreaking();
                particleEffectsHandler = new ParticleEffectsHandlerNI();
                break;
        }
        stackTraceHandler.register();

        inventoryHandler = new NonBreakingInventoryHandler();

        initialized = true;
    }

    public static UnhandledStackTrace getStackTraceHandler() {
        return stackTraceHandler;
    }

    public static ActionMessageHandler getActionMessageHandler() {
        return actionMessageHandler;
    }

    public static AbstractTitle.TitleHandler getTitleHandler() {
        return titleHandler;
    }

    public static InventoryHandler getInventoryHandler() {
        return inventoryHandler;
    }

    public static ForceRespawnHandler getForceRespawnHandler() {
        return forceRespawnHandler;
    }

    public static ParticleEffectsHandler getParticleEffectsHandler() {
        return particleEffectsHandler;
    }
}
