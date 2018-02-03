package com.devsteady.onyx.threading.tasks;

import com.devsteady.onyx.entity.Entities;
import org.bukkit.Location;
import org.bukkit.entity.Item;

import java.util.Set;

public class ClearDroppedItemsThread implements Runnable {

    private Location center;
    private int radius;

    public ClearDroppedItemsThread(Location center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void run() {
        Set<Item> droppedItems = Entities.getDroppedItemsNearLocation(center, radius);
        droppedItems.stream().forEach(Item::remove);
    }
}
