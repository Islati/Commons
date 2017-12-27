package com.caved_in.commons.menu.inventory.multipage;

import com.caved_in.commons.menu.inventory.MenuItem;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The purpose of a page generator is to supply condition logic for each Multi Page Item Menu.
 * Design from this comes from the following things:
 * - We don't always know how many items the menu will have
 * - We want to be passed a collection of items, and process them into an indexed map.
 * @see MultiPageItemMenu
 * @param T the type the page generator will be accepting in a collection, in order to generate the pages of items.
 */
public interface PageGenerator<T> {
    /**
     * Process a collection of items into a map of pages, and their associated menu displays.
     * @param items collection of items that need to be processed.
     * @return Map of indexed pages and their associated page data.
     */
     Map<Integer, ItemPage> process(PageGeneratorSettings settings, List<T> items);

    /**
     *
     * @param item
     * @return
     */
     MenuItem makeMenuItem(T item);
}
