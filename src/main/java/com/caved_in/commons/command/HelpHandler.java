package com.caved_in.commons.command;

public interface HelpHandler {
    public String[] getHelpMessage(RegisteredCommand command);

    public String getUsage(RegisteredCommand command);
}
