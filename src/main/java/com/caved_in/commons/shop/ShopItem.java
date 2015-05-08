package com.caved_in.commons.shop;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class ShopItem implements StoreItem {
    private int id;
    private String name;
    private String description;
    private double price;

    public ShopItem(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public int getItemId() {
        return id;
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public String getItemDescription() {
        return description;
    }

    @Override
    public double getItemPrice() {
        return price;
    }
}
