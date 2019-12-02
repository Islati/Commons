package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Wool;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToggleBooleanMenuItem extends MenuItem {

    @Getter
    @Setter
    private boolean value = false;

    @Getter
    @Setter
    private ToggleAction callback = null;

    @Getter
    @Setter
    private String enabledIconTitle = "&aEnabled";

    @Getter
    @Setter
    private List<String> enabledIconLore = Arrays.asList(
            "- Click to Disable."
    );

    @Getter
    @Setter
    private String disabledIconTitle = "&cDisabled";

    @Getter
    @Setter
    private List<String> disabledIconLore = Arrays.asList(
            "- Click to Enable."
    );

    public ToggleBooleanMenuItem(boolean initialValue) {
        this.value = initialValue;
    }

    public ToggleBooleanMenuItem onToggle(ToggleAction action) {
        this.callback = action;
        return this;
    }

    public ToggleBooleanMenuItem enabledName(String name) {
        this.enabledIconTitle = name;
        return this;
    }

    public ToggleBooleanMenuItem enabledDescription(String... description) {
        this.enabledIconLore = Arrays.asList(description);
        return this;
    }

    public ToggleBooleanMenuItem disabledName(String name) {
        this.disabledIconTitle = name;
        return this;
    }

    public ToggleBooleanMenuItem disabledDescription(String... description) {
        this.disabledIconLore = Arrays.asList(description);
        return this;
    }

    @Override
    public ItemStack getItemStack() {
        return ItemBuilder.of(value ? Wool.GREEN_WOOL : Wool.RED_WOOL)
                .name(value ? enabledIconTitle : disabledIconTitle)
                .lore(value ? enabledIconLore : disabledIconLore)
                .item();
    }

    @Override
    public void onClick(Player player, ClickType type) {
        setValue(!value);
        callback.onToggle(this, player, value);

        ItemMenu menu = getMenu();
        getMenu().switchMenu(player,menu.clone());
    }

    public interface ToggleAction {
        void onToggle(MenuItem item, Player player, boolean newValue);
    }
}
