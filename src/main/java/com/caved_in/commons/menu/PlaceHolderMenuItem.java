package com.caved_in.commons.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class PlaceHolderMenuItem extends MenuItem {

    public PlaceHolderMenuItem(String title, String description) {
        super(title, new MaterialData(Material.PAPER));
        setDescriptions(description);
    }

    @Override
    public void onClick(Player player) {

    }
}
