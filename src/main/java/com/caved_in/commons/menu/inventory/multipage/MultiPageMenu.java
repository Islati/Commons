package com.caved_in.commons.menu.inventory.multipage;

import com.caved_in.commons.item.Items;
import com.caved_in.commons.menu.Menu;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.menu.inventory.*;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MultiPageMenu implements Menu {

    private List<ItemPage> pages;

    private String titleFormat = "{title} - Page {page}";
    private String title = "";

    private int pageActive = 1;

    private PageSettings settings = new PageSettings();

    private ItemStack itemNextPage = null;

    private ItemStack itemPreviousPage = null;

    private ItemStack itemFirstPage = null;

    private boolean btnFirstPageEnabled = false;

    private int slotFirstPage;

    private int slotPreviousPage;

    private int slotNextPage;

    /**
     * Create a new Multi Page Menu
     *
     * @param title Title of the menu!
     */
    public MultiPageMenu(String title) {
        this.title = title;
    }

    public void setTitleFormat(String titleFormat) {
        this.titleFormat = titleFormat;
    }

    public int getPageActive() {
        return pageActive;
    }

    public void setPageActive(int page) {
        this.pageActive = page;
    }

    public ItemPage getPage(int page) {
        return pages.get(page);
    }

    public int getPreviousPage(int index) {
        int previousPage = index -1;

        try {
            ItemPage page = pages.get(previousPage);
        } catch (IndexOutOfBoundsException ex) {
            //Ay!
        }
    }

    /**
     * Populate the internal display with the given items, and settings for rendering the menu.
     * Based on settings there will be special MenuItems
     *
     * @param items    items to populate the pages with!
     * @param settings Settings to configure the pages with.
     * @return Map indexed with the page number, and ItemPage used to render the pages view.
     */
    public List<ItemPage> generatePages(List<MenuItem> items, PageSettings settings) {
        List<ItemPage> generatedPages = new ArrayList<>();

        int utilitySlot = 0;

        if (settings.firstPageItemEnabled) {
            utilitySlot += 1;
        }

        if (settings.nextPageSlot > 0) {
            utilitySlot += 1;
        }

        if (settings.previousPageSlot > 0) {
            utilitySlot += 1;
        }
        int pageIndex = 0;

        List<List<MenuItem>> itemPages = Lists.partition(items, 54);
        for (List<MenuItem> pageItems : itemPages) {

            List<MenuItem> processedPageItems = new ArrayList<>(pageItems);

            for(int i = 0; i < 54; i++) {
                if (i == settings.firstPageSlot) {
                    InlineMenuItem firstPageItem = new InlineMenuItem(Items.getName(itemFirstPage));

                    firstPageItem.setClickHandler(new MenuItemClickHandler() {
                        @Override
                        public void onClick(MenuItem item, Player player, ClickType type) {
                            Menus.switchMenu(player,item.getMenu(),pages.get(0).getPageMenu());
                        }
                    });
                }

                if (i == settings.previousPageSlot) {
                    InlineMenuItem previousPageItem = new InlineMenuItem(Items.getName(itemPreviousPage));

                    previousPageItem.setClickHandler(new MenuItemClickHandler() {
                        @Override
                        public void onClick(MenuItem item, Player player, ClickType type) {
                            Menus.switchMenu(player, );
                        }
                    });
                }

                if (i == settings.nextPageSlot) {

                }
                //add
            }


        }
    }

    @Override
    public void addBehaviour(MenuAction action, MenuBehaviour behaviour) {

    }

    @Override
    public List<MenuBehaviour> getBehaviours(MenuAction action) {
        return null;
    }

    @Override
    public boolean exitOnClickOutside() {
        return false;
    }

    @Override
    public void openMenu(Player player) {

    }

    @Override
    public void closeMenu(Player player) {

    }

    @Override
    public Menu clone() {
        return null;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
