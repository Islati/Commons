package com.devsteady.onyx.chat.menu;

public enum PageDisplay {
    DEFAULT("<name> (Page <page> of <maxpage>)"),
    SHORTHAND("<name> (P.<page>/<maxpage>)");

    private String formatting;

    PageDisplay(String formatting) {
        this.formatting = formatting;
    }

    @Override
    public String toString() {
        return this.formatting;
    }
}
