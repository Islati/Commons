package com.caved_in.commons.game.item;

import com.caved_in.commons.item.Items;
import org.bukkit.Material;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Root(name = "container-data")
public class ContainerData {
    @Element(name = "enabled")
    private boolean enabled = true;

    @Element(name = "container-title")
    private String title = "Chest";

    @Element(name = "container-id")
    private int blockId = 54;

    private Material material = null;

    @ElementList(name = "rewards", type = RewardData.class, entry = "reward", inline = true)
    private List<RewardData> rewards = new ArrayList<>();

    @Element(name = "min-items-amount")
    private int minChestRewards = 0;

    @Element(name = "max-items-amount")
    private int maxChestRewards = 15;

    public ContainerData(@Element(name = "enabled") boolean enabled, @Element(name = "container-title") String title, @Element(name = "container-id") int blockId, @ElementList(name = "rewards", type = RewardData.class, entry = "reward", inline = true) List<RewardData> rewards, @Element(name = "min-items-amount") int minChestRewards, @Element(name = "max-items-amount") int maxChestRewards) {
        this.enabled = enabled;
        this.title = title;
        this.blockId = blockId;
        this.rewards = rewards;
        this.minChestRewards = minChestRewards;
        this.maxChestRewards = maxChestRewards;
        material = Items.getMaterialById(blockId);
    }

    public ContainerData() {
        rewards = Arrays.asList(new RewardData());
    }


    public boolean isEnabled() {
        return enabled;
    }

    public Material getContainerMaterial() {
        return material;
    }

    public int getMaxChestRewards() {
        return maxChestRewards;
    }

    public int getMinChestRewards() {
        return minChestRewards;
    }

    public List<RewardData> getRewards() {
        return rewards;
    }

    public void addRewardData(RewardData data) {
        rewards.add(data);
    }

    public String getTitle() {
        return title;
    }

    public int getBlockId() {
        return blockId;
    }
}
