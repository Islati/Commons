package com.caved_in.commons.game.item;

import com.caved_in.commons.item.Items;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "reward-data")
/**
 * An internal class used to manage chanced, and randomized reward-data, generally in a container, but is also used for loot selection in minigames.
 */
public class RewardData {
    @Attribute(name = "min")
    private int min = 1;

    @Attribute(name = "max")
    private int max = 1;

    @Attribute(name = "rarity")
    private int rarity = 100;

    @Element(name = "item", type = SerializableItemStack.class)
    private SerializableItemStack itemStack = new SerializableItemStack(Items.makeItem(Material.GOLD_BOOTS));

    public RewardData(@Attribute(name = "min") int min, @Attribute(name = "max") int max, @Attribute(name = "rarity") int rarity, @Element(name = "item", type = SerializableItemStack.class) SerializableItemStack itemStack) {
        this.min = min;
        this.max = max;
        this.rarity = rarity;
        this.itemStack = itemStack;
    }

    public RewardData() {
        rarity = NumberUtil.getRandomInRange(1, 100);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public ItemStack generateRewardItem() {
        if (!NumberUtil.percentCheck(getRarity())) {
            return null;
        }

        ItemStack item = itemStack.getItemStack();
        //Set the item amount to be a random between the min and max value
        if (getMin() >= getMax()) {
            item.setAmount(getMax());
        } else {
            item.setAmount(NumberUtil.getRandomInRange(getMin(), getMax()));
        }
        return item;
    }

    public ItemStack getItemStack() {
        return itemStack.getItemStack();
    }

    public void setItemStack(ItemStack item) {
        itemStack = new SerializableItemStack(item);
    }
}