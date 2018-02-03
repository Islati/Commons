package com.devsteady.onyx.command;

public interface HelpHandler {
    String[] getHelpMessage(RegisteredCommand command);

    String getUsage(RegisteredCommand command);
}
