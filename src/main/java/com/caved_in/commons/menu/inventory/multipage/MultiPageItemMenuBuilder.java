package com.caved_in.commons.menu.inventory.multipage;

import com.caved_in.commons.menu.inventory.ItemMenu;
import com.caved_in.commons.menu.inventory.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class MultiPageItemMenuBuilder {

    private static final String MENU_TITLE_BASE = "{title} - Page {page}";

    private List<MenuItem> menuItems = new ArrayList<>();

    private String title;

    private PageSettings settings = null;

    public MultiPageItemMenuBuilder() {

    }

    public MultiPageItemMenuBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MultiPageItemMenuBuilder addItems(MenuItem... items) {
        menuItems.addAll(Arrays.asList(items));
        return this;
    }

    public PageSettings settings() {
        if (settings == null) {
            settings = new PageSettings(this);
        }

        return settings;
    }

    public

}
