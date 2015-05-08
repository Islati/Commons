package com.caved_in.commons.menu;

public enum ItemFormat {
    DOUBLE_DASH("<name> -- <desc>"),
    SINGLE_DASH("<name> - <desc>"),
    IS("<name> is <desc>"),
    FRIEND_REQUEST("<name> wants to add you as a friend!"),
    NO_DESCRIPTION("<name>");

    private String formatting;

    private ItemFormat(String formatting) {
        this.formatting = formatting;
    }

    @Override
    public String toString() {
        return this.formatting;
    }
}
