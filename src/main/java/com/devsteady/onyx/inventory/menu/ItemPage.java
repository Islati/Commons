package com.devsteady.onyx.inventory.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container class to hold the data used for a specific page in a
 * multi page menus.
 */
public class ItemPage {

    /**
     * Item Menu with ability to get the active page.
     */
    public class Menu extends ItemMenu {
        private int page;

        public Menu(String title, int rows) {
            super(title, rows);
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    private List<MenuItem> pageItems;
    private int pageIndex;
    private String itemPageTitleFormat = "{displayName} - Page {pageIndex}";
    private String title;

    private Map<MenuAction, List<MenuBehaviour>> behaviours = new HashMap<>();

    public ItemPage(int pageIndex, List<MenuItem> items) {
        this.pageItems = items;
        this.pageIndex = pageIndex;
        behaviours.put(MenuAction.OPEN,new ArrayList<>());
        behaviours.put(MenuAction.CLOSE, new ArrayList<>());
    }

    public void addBehaviour(MenuAction action, MenuBehaviour behaviour) {
        behaviours.get(action).add(behaviour);
    }

    public void setPageIndex(int index) {
        this.pageIndex = index;
    }

    public void setTitle(String title){
        this.title = title;
    }

    /**
     * The collection of items, indexed by their slot in the inventory, to build this menus pageIndex.
     * @return Map of keyvalue for the firstPageEnabled slot, and actual menus firstPageEnabled
     */
    public List<MenuItem> items() {
        return pageItems;
    }

    /**
     * This pages index in the overall list of pages avaialable for generating the menus.
     * @return pageIndex number.
     */
    public int pageIndex() {
        return pageIndex;
    }

    public ItemMenu getPageMenu() {
        ItemPage.Menu pageMenu = new ItemPage.Menu(itemPageTitleFormat.replace("{displayName}",title).replace("{pageIndex}","" + pageIndex + 1), Menus.getRows(pageItems.size()));

        pageMenu.setPage(pageIndex);

        for (MenuBehaviour behaviourOpen : behaviours.get(MenuAction.OPEN)) {
            pageMenu.addBehaviour(MenuAction.OPEN,behaviourOpen);
        }

        for (MenuBehaviour behaviourClose : behaviours.get(MenuAction.CLOSE)) {
            pageMenu.addBehaviour(MenuAction.CLOSE,behaviourClose);
        }

        return pageMenu;
    }
}
