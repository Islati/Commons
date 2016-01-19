package com.caved_in.commons.nms;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.nms.minecraft_1_8_R3.ActionMessageHandler_18R3;
import com.caved_in.commons.nms.minecraft_1_8_R3.TitleHandler_18R3;
import com.caved_in.commons.nms.minecraft_1_8_R3.UnhandledStackTrace_18R3;
import com.caved_in.commons.nms.no_implementation.ActionMessageHandlerNI;
import com.caved_in.commons.nms.no_implementation.TitleHandlerNI;
import com.caved_in.commons.nms.no_implementation.UnhandledStackTraceNI;
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
                break;
            default:
                stackTraceHandler = new UnhandledStackTraceNI();
                actionMessageHandler = new ActionMessageHandlerNI();
                titleHandler = new TitleHandlerNI();
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
}
