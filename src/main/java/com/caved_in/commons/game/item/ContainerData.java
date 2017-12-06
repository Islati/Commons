package com.caved_in.commons.game.item;

import com.caved_in.commons.item.Items;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.YamlConfig;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContainerData extends YamlConfig {
    @Path("enabled")
    private boolean enabled = true;

    @Path("container-title")
    private String title = "Chest";

    @Path("container-id")
    private int blockId = 54;

    private Material material = null;

    @Path("rewards")
    private List<RewardData> rewards = new ArrayList<>();

    @Path("min-items-amount")
    private int minChestRewards = 0;

    @Path("max-items-amount")
    private int maxChestRewards = 15;

    public ContainerData(boolean enabled, String title, int blockId, List<RewardData> rewards, int minChestRewards, int maxChestRewards) {
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
