package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.inventory.menu.Menu;
import com.caved_in.commons.inventory.menu.Menus;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemMenu implements Menu {

    private int rows;

    private boolean rowsHasChanged = false;

    private String title;
    private Inventory inventory;
    private Map<Integer, MenuItem> items = new HashMap<>();
    private boolean exitOnClickOutside = true;

    private Map<MenuAction, ArrayList<MenuBehaviour>> menuActions = new HashMap<>();

    public ItemMenu(String title, int rows) {
        this.title = StringUtil.formatColorCodes(title);
        this.rows = rows;
        menuActions.put(MenuAction.OPEN, Lists.newArrayList());
        menuActions.put(MenuAction.CLOSE, Lists.newArrayList());
    }

    @Override
    public Inventory getInventory() {
        if (rowsHasChanged || inventory == null) {
            inventory = Bukkit.createInventory(this, rows * 9, title);

			/*
            If the rows have updated, then we're going to re-assign the items
			to the menus, and assure that they're being seen.
			 */
            if (rowsHasChanged) {
                setMenuItems(inventory, items);
            }
        }
        return inventory;
    }

    public void addBehaviour(MenuAction type, MenuBehaviour behaviour) {
        menuActions.get(type).add(behaviour);
    }

    public void addBehaviours(MenuAction type, List<MenuBehaviour> behaviours) {
        if (behaviours == null || behaviours.size() == 0) {
            return;
        }
        menuActions.get(type).addAll(behaviours);
    }

    public void setBehaviours(Map<MenuAction, ArrayList<MenuBehaviour>> behaviours) {
        this.menuActions = behaviours;
    }

    public List<MenuBehaviour> getBehaviours(MenuAction type) {
        return menuActions.get(type);
    }

    public void setExitOnClickOutside(boolean exit) {
        this.exitOnClickOutside = exit;
    }

    private void setMenuItems(Inventory inv, Map<Integer, MenuItem> items) {
        this.items = items;
        for (Map.Entry<Integer, MenuItem> menuItems : items.entrySet()) {
            int index = menuItems.getKey();
            ItemStack slot = inv.getItem(index);

            if (slot != null && slot.getType() != Material.AIR) {
                continue;
            }

            inv.setItem(index, slot);
            menuItems.getValue().addToMenu(this);
        }
    }

    public void setMenuItems(Map<Integer, MenuItem> items) {
        setMenuItems(getInventory(), items);
    }

    public void setItem(int index, MenuItem item) {
        Inventory inventory = getInventory();
        inventory.setItem(index, item.getItemStack());
        items.put(index, item);
        item.addToMenu(this);
    }

    public boolean addMenuItem(MenuItem item, int x, int y) {
        return addMenuItem(item, y * 9 + x);
    }

    public boolean addMenuItem(MenuItem item, int index) {
        Inventory inventory = getInventory();
        ItemStack slot = inventory.getItem(index);
        //If the item's not air, we're not adding it.
        if (!rowsHasChanged && (slot != null && slot.getType() != Material.AIR)) {
            return false;
        }

        inventory.setItem(index, item.getItemStack());
        items.put(index, item);
        item.addToMenu(this);
        return true;
    }

    public boolean removeMenuItem(int index) {
        Inventory inventory = getInventory();
        ItemStack slot = inventory.getItem(index);
        if (slot == null || slot.getType() == Material.AIR) {
            return false;
        }

        inventory.clear(index);
        items.remove(index).removeFromMenu(this);
        return true;
    }

    public void selectMenuItem(Player player, int index, ClickType type) {
        if (!items.containsKey(index)) {
            return;
        }

        MenuItem item = items.get(index);
        item.onClick(player, type);
    }

    public void openMenu(Player player) {
        Inventory inventory = getInventory();
        if (inventory.getViewers().contains(player)) {
            return;
        }
        player.openInventory(inventory);

        List<MenuBehaviour> behaviours = getBehaviours(MenuAction.OPEN);
        if (behaviours.size() > 0) {
            for (MenuBehaviour behaviour : behaviours) {
                behaviour.doAction(this, player);
            }
        }
    }

    public void closeMenu(Player player) {
        Inventory inventory = getInventory();
        if (!inventory.getViewers().contains(player)) {
            return;
        }

        inventory.getViewers().remove(player);
        player.closeInventory();
        List<MenuBehaviour> behaviours = getBehaviours(MenuAction.CLOSE);
        if (behaviours.size() > 0) {
            for (MenuBehaviour behaviour : behaviours) {
                behaviour.doAction(this, player);
            }
        }
    }

    public void closeMenu() {
        Inventory inv = getInventory();

        List<MenuBehaviour> closeActions = getBehaviours(MenuAction.CLOSE);
        if (closeActions.size() > 0) {
            for (HumanEntity viewer : getViewers()) {
                if (!(viewer instanceof Player)) {
                    continue;
                }

                Player p = (Player) viewer;

                p.closeInventory();

                for (MenuBehaviour onClose : closeActions) {
                    onClose.doAction(this, p);
                }
            }
            return;
        }

        for (HumanEntity viewer : getViewers()) {
            if (!(viewer instanceof Player)) {
                continue;
            }

            viewer.closeInventory();
        }
    }

    public void switchMenu(Player player, Menu toMenu) {
        Menus.switchMenu(player, this, toMenu);
    }

    public MenuItem getItem(int index) {
        return items.get(index);
    }

    @Override
    public ItemMenu clone() {
        ItemMenu clone = new ItemMenu(title, rows);
        clone.setExitOnClickOutside(exitOnClickOutside);
        clone.setBehaviours(menuActions);
        for (Map.Entry<Integer, MenuItem> itemEntry : items.entrySet()) {
            clone.addMenuItem(itemEntry.getValue(), itemEntry.getKey());
        }

        return clone;
    }

    @Override
    public void updateMenu() {
        getViewers().stream().filter(e -> e instanceof Player).forEach(p -> switchMenu((Player) p, this.clone()));
    }

    public void updateMenu(Collection<HumanEntity> viewers) {
        if (rowsHasChanged) {
            /*
            If the rows has changed; then we'll switch to the next menus, as opposed to
			updating the existing one!
			 */
            viewers.stream().filter(e -> e instanceof Player).forEach(
                    p -> {
                        switchMenu((Player) p, this.clone());
                    }
            );
            return;
        }
        viewers.stream().filter(e -> e instanceof Player).forEach(p -> Players.updateInventory((Player) p));
    }

    public Map<Integer, MenuItem> getIndexedMenuItems() {
        return items;
    }

    public Collection<MenuItem> getMenuItems() {
        return items.values();
    }

    public boolean exitOnClickOutside() {
        return exitOnClickOutside;
    }

    public void clearMenuItems() {
        items.clear();

        if (inventory != null) {
            /* Also clear the items from inventory */
            inventory.clear();
        }
    }

    public void setRows(int count) {
        this.rows = count;
        rowsHasChanged = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (inventory == null) {
            throw new IllegalAccessError("Unable to rename the inventory as it's not been initialized");
        }
        this.title = title;
        Inventories.rename(getInventory(), StringUtil.formatColorCodes(title));
    }

    public void fillEmpty(MenuItem item) {
        Inventory inv = getInventory();

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack invItem = inv.getItem(i);
            if (invItem == null || Items.isAir(invItem)) {
                items.put(i, item);
            }
        }
    }


}
