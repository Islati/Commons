package com.caved_in.commons.menu.anvil;

import com.caved_in.commons.menu.Menu;
import com.caved_in.commons.menu.inventory.MenuAction;
import com.caved_in.commons.menu.inventory.MenuBehaviour;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
//todo finish.
public class AnvilMenu implements Menu {
    private Map<MenuAction, ArrayList<MenuBehaviour>> actions = new HashMap<>();
    private boolean exitOnClickOutside = false;
    private AnvilInputHandler inputHandler = null;

    public AnvilMenu() {
        actions.put(MenuAction.OPEN, Lists.newArrayList());
        actions.put(MenuAction.CLOSE, Lists.newArrayList());
    }

    public void setInputHandler(AnvilInputHandler handler) {
        this.inputHandler = handler;
    }

    @Override
    public void addBehaviour(MenuAction action, MenuBehaviour behaviour) {
        actions.get(action).add(behaviour);
    }

    @Override
    public List<MenuBehaviour> getBehaviours(MenuAction action) {
        return actions.get(action);
    }

    @Override
    public boolean exitOnClickOutside() {
        return exitOnClickOutside;
    }

    @Override
    public void openMenu(Player player) {
    }

    @Override
    public void closeMenu(Player player) {

    }

    @Override
    public AnvilMenu clone() {
        return null; //todo clone
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public String handleResponse(Player player, String input) {
        return inputHandler.onClick(player,input);
    }
}
