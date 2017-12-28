package com.caved_in.commons.menu.inventory.multipage;

import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.Skip;
import com.caved_in.commons.yml.YamlConfig;
import org.bukkit.inventory.ItemStack;

import java.io.File;

/**
 * Settings to configure a multi page generator.
 */
public class PageSettings extends YamlConfig {
    @Path("next-page.slot")
    public int nextPageSlot = -1;

    @Path("next-page.item")
    public ItemStack nextPageItem;

    @Path("previous-page.slot")
    public int previousPageSlot = -1;

    @Path("previous-page.item")
    public ItemStack previousPageItem;

    @Path("first-page.enabled")
    public boolean firstPageItemEnabled = false;

    @Path("first-page.slot")
    public int firstPageSlot;

    @Path("first-page.item")
    public ItemStack firstPageItem;

    @Skip
    private MultiPageItemMenuBuilder parentBuilder;

    public PageSettings(File file) {
        super(file);
    }

    public PageSettings(MultiPageItemMenuBuilder parent) {
        this.parentBuilder = parent;
    }

    public PageSettings() {}

    public PageSettings nextPageSlot(int index) {
        this.nextPageSlot = index;
        return this;
    }

    public PageSettings nextPageItem(ItemStack item) {
        this.nextPageItem = item;
        return this;
    }

    public PageSettings previousPageSlot(int index) {
        this.previousPageSlot = index;
        return this;
    }

    public PageSettings previousPageItem(ItemStack item) {
        this.previousPageItem = item;
        return this;
    }

    public PageSettings firstPageItemEnabled(boolean enabled) {
        this.firstPageItemEnabled = enabled;
        return this;
    }

    public PageSettings firstPageItemSlot(int index) {
        this.firstPageSlot = index;
        return this;
    }

    public PageSettings firstPageItem(ItemStack item) {
        this.firstPageItem = item;
        return this;
    }

    public MultiPageItemMenuBuilder parent() {
        return parentBuilder;
    }
}
