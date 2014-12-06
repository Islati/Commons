package com.caved_in.commons;

import com.caved_in.commons.item.ItemCycler;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemCyclerTest {
    private ItemCycler cycler;

    @Before
    public void setup() {

        ItemStack diamonds = mock(ItemStack.class);
        when(diamonds.getType()).thenReturn(Material.DIAMOND);

        ItemStack goldBar = mock(ItemStack.class);
        when(goldBar.getType()).thenReturn(Material.GOLD_INGOT);

        cycler = new ItemCycler(diamonds, goldBar);
    }

    @Test
    public void testCycler() {
        //Check that there's diamonds in the cycler active item, first.
        assertEquals(Material.DIAMOND, cycler.activeItem().getType());
        //Check that there's gold ingots in the next item for the cycler.
        assertEquals(Material.GOLD_INGOT, cycler.nextItem().getType());

    }

}
