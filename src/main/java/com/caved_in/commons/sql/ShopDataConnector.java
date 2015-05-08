package com.caved_in.commons.sql;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */

import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.shop.ShopItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.caved_in.commons.sql.DatabaseField.*;

public class ShopDataConnector extends DatabaseConnector {
    private static String RETRIEVE_SHOP_ITEMS = "SELECT * FROM " + SHOP_ITEMS_TABLE;
    private static String RETRIEVE_SHOP_ITEM_BY_NAME = "SELECT * FROM " + SHOP_ITEMS_TABLE + " WHERE " + SHOP_ITEM_NAME + "=?";

    public ShopDataConnector(SqlConfiguration sqlConfiguration) {
        super(sqlConfiguration);
    }

    public Map<Integer, ShopItem> getShopInventory() {
        Map<Integer, ShopItem> inventory = new HashMap<>();
        PreparedStatement statement = prepareStatement(RETRIEVE_SHOP_ITEMS);
        try {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //Create a new shopItem to return based on the values for each row
                int itemId = resultSet.getInt(SHOP_ITEM_ID.toString());
                String itemName = resultSet.getString(SHOP_ITEM_NAME.toString());
                String itemDescription = resultSet.getString(SHOP_ITEM_DESCRIPTION.toString());
                double itemPrice = resultSet.getDouble(SHOP_ITEM_PRICE.toString());
                inventory.put(itemId, new ShopItem(itemId, itemName, itemDescription, itemPrice));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(statement);
        }
        return inventory;
    }

}
