package com.devsteady.onyx.inventory.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiPageMenuBuilder {

    private static final String MENU_TITLE_BASE = "{displayName} - Page {page}";

    private List<MenuItem> menuItems = new ArrayList<>();

    private String title;

    private PageSettings settings = null;

    public MultiPageMenuBuilder() {

    }

    public MultiPageMenuBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MultiPageMenuBuilder addItems(MenuItem... items) {
        menuItems.addAll(Arrays.asList(items));
        return this;
    }

    public MultiPageMenuBuilder addItems(List<? extends MenuItem> items) {
        menuItems.addAll(items);
        return this;
    }

    public MultiPageMenuBuilder settings(PageSettings settings) {
        this.settings = settings;

        return this;
    }

    public MultiPageMenu build() {
        MultiPageMenu menu = new MultiPageMenu(title);

        if (settings == null) {
            settings = new PageSettings();
        }

        menu.setSettings(settings);

        menu.populate(menuItems);

        return menu;
    }

}
