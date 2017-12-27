package com.caved_in.commons.menu.inventory.multipage;

import com.caved_in.commons.menu.inventory.MenuItem;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiPageGenerator<T> implements PageGenerator<T> {
    public MultiPageGenerator() {

    }

    @Override
    public Map<Integer, ItemPage> process(PageGeneratorSettings settings, List<T> items) {
        Map<Integer, ItemPage> itemPages = new HashMap<>();

        List<List<T>> pagedCollection = Lists.partition(items,20);
        
    }

    @Override
    public MenuItem makeMenuItem(T item) {
        return null;
    }
}
