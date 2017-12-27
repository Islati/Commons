package com.caved_in.commons.menu.inventory.multipage;

import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.YamlConfig;
import org.bukkit.inventory.ItemStack;

import java.io.File;

/**
 * Settings to configure a multi page generator.
 */
public class PageGeneratorSettings extends YamlConfig {
    @Path("next-page.slot")
    public int nextPageSlot;

    @Path("next-page.item")
    public ItemStack nextPageItem;

    @Path("previous-page.slot")
    public int previousPageSlot;

    @Path("previous-page.item")
    public ItemStack previousPageItem;

    @Path("first-page.enabled")
    public boolean firstPageItemEnabled = false;

    @Path("first-page.slot")
    public int firstPageSlot;

    @Path("first-page.item")
    public ItemStack firstPageItem;

    public PageGeneratorSettings(File file) {
        super(file);
    }

    public PageGeneratorSettings() {

    }
}
