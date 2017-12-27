package com.caved_in.commons.menu.inventory.multipage;

import com.caved_in.commons.menu.inventory.ItemMenu;
import com.caved_in.commons.menu.inventory.ItemMenuBuilder;
import com.caved_in.commons.menu.inventory.MenuItem;

import java.util.Map;

public class ItemPage {
    private Map<Integer, MenuItem> pageItems;
    private int page;
    private String itemPageTitleFormat = "{title} - Page {page}";
    private String title;

    public ItemPage(int page, Map<Integer, MenuItem> items) {
        this.pageItems = items;
        this.page = page;
    }

    public void setTitle(String title){
        this.title = title;
    }

    /**
     * The collection of items, indexed by their slot in the inventory, to build this menu page.
     * @return Map of keyvalue for the item slot, and actual menu item
     */
    Map<Integer, MenuItem> items() {
        return pageItems;
    }

    /**
     * This pages index (1 being the first) in the overall menu.
     * @return page number.
     */
    Integer page() {
        return page;
    }

    public ItemMenu getPageMenu() {
        ItemMenuBuilder builder = new ItemMenuBuilder();

        for(Map.Entry<Integer, MenuItem> item : pageItems.entrySet()) {
            builder.item(item.getKey(),item.getValue());
        }

        builder.title(itemPageTitleFormat.replace("{title}",title).replace("{page}","" + page));

        return builder.getMenu();
    }
}
