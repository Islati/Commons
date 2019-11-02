package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.Skip;
import com.caved_in.commons.yml.YamlConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;

/**
 * Settings to configure a multi page generator.
 */
public class PageSettings extends YamlConfig {

    @Path("rows")
    public int rows = 6;

    @Path("next-page.slot")
    public int slotPageNext = -1;

    @Path("next-page.item")
    public ItemStack itemPageNext = ItemBuilder.of(Material.PAPER).name("&a&l->").lore("&5Click for see the next page").item();

    @Path("previous-page.slot")
    public int slotPagePrevious = -1;

    @Path("previous-page.item")
    public ItemStack itemPagePrevious = ItemBuilder.of(Material.PAPER).name("&a&l<-").lore("&5Click to see the previous page").item();

    @Path("first-page.enabled")
    public boolean pageFirstEnabled = false;

    @Path("first-page.slot")
    public int slotFirstPage =-1;

    @Path("first-page.item")
    public ItemStack itemPageFirst = ItemBuilder.of(Material.PAPER).name("&a[&7&oFirst Page&r&a]").lore("&5Click to see the first page").item();

    @Skip
    private MultiPageMenuBuilder parentBuilder;

    public PageSettings(File file) {
        super(file);
    }

    public PageSettings(MultiPageMenuBuilder parent) {
        this.parentBuilder = parent;
    }

    public PageSettings() {}

    public PageSettings nextPageSlot(int index) {
        this.slotPageNext = index;
        return this;
    }

    public PageSettings nextPageItem(ItemStack item) {
        this.itemPageNext = item;
        return this;
    }

    public PageSettings previousPageSlot(int index) {
        this.slotPagePrevious = index;
        return this;
    }

    public PageSettings previousPageItem(ItemStack item) {
        this.itemPagePrevious = item;
        return this;
    }

    public PageSettings item(boolean enabled) {
        this.pageFirstEnabled = enabled;
        return this;
    }

    public PageSettings firstPageSlot(int index) {
        this.slotFirstPage = index;
        return this;
    }

    public PageSettings firstPageItem(ItemStack item) {
        this.itemPageFirst = item;
        return this;
    }

    public MultiPageMenuBuilder parentBuilder() {
        return parentBuilder;
    }

    public PageSettings rows(int count) {
        this.rows = count;

        if (this.rows > 6) {
            this.rows = 6;
        }

        if (this.rows < 1) {
            this.rows = 1;
        }

        return this;
    }

    public int getPageSlotCount() {
        return rows * 9;
    }
}
