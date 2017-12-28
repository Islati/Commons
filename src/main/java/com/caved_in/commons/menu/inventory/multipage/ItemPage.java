package com.caved_in.commons.menu.inventory.multipage;

import com.caved_in.commons.menu.inventory.ItemMenu;
import com.caved_in.commons.menu.inventory.ItemMenuBuilder;
import com.caved_in.commons.menu.inventory.MenuItem;

import java.util.List;
import java.util.Map;

public class ItemPage {
    private List<MenuItem> pageItems;
    private int pageIndex;
    private String itemPageTitleFormat = "{title} - Page {pageIndex}";
    private String title;

    public ItemPage(int pageIndex, List<MenuItem> items) {
        this.pageItems = items;
        this.pageIndex = pageIndex;
    }

    public void setTitle(String title){
        this.title = title;
    }

    /**
     * The collection of items, indexed by their slot in the inventory, to build this menu pageIndex.
     * @return Map of keyvalue for the item slot, and actual menu item
     */
    public List<MenuItem> items() {
        return pageItems;
    }

    /**
     * This pages index (1 being the first) in the overall menu.
     * @return pageIndex number.
     */
    public int pageIndex() {
        return pageIndex;
    }

    public ItemMenu getPageMenu() {
        ItemMenuBuilder builder = new ItemMenuBuilder();

        for(MenuItem item : pageItems) {
            builder.item(item);
        }

        builder.title(itemPageTitleFormat.replace("{title}",title).replace("{pageIndex}","" + pageIndex + 1));

        return builder.getMenu();
    }
}
