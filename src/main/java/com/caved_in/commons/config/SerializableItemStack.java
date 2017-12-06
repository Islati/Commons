package com.caved_in.commons.config;

import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.YamlConfig;
import org.bukkit.inventory.ItemStack;

import java.io.File;

/**
 * Simple means to serialize a single item to a file.
 * If your intent is not to save a single ItemStack to a file (with no other fields)
 * then you're best off simply declaring a path variable on your ItemStack
 * and serializing that way.
 */
public class SerializableItemStack extends YamlConfig {
    @Path("item")
    private ItemStack item;

    public SerializableItemStack(File file) {
        super(file);
    }

    public SerializableItemStack(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
