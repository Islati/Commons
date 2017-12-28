package com.caved_in.commons.inventory.menu.multipage;

import com.caved_in.commons.inventory.menu.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MultiPageMenuBuilder {

    private static final String MENU_TITLE_BASE = "{title} - Page {page}";

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

    public MultiPageMenuBuilder settings(PageSettings settings) {
        this.settings = settings;

        return this;
    }

    public MultiPageMenu build() {
        MultiPageMenu menu = new MultiPageMenu(title);
        menu.setSettings(settings);

        menu.populate(menuItems);

        return menu;
    }

}
