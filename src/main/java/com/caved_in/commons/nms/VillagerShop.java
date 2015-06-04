package com.caved_in.commons.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VillagerShop {
    private List<VillagerTrading.VillagerTradeOffer> offerList = new ArrayList<>();

    private boolean jsonTitle = false;

    private String text;

    public VillagerShop(String title) {
        this.text = title;
    }

    public VillagerShop jsonText() {
        this.jsonTitle = true;
        return this;
    }

    public VillagerShop plainText() {
        this.jsonTitle = false;
        return this;
    }

    public VillagerShop add(VillagerTrading.VillagerTradeOffer... offers) {
        for (VillagerTrading.VillagerTradeOffer offer : offers) {
            this.offerList.add(offer);
        }
        return this;
    }

    public VillagerShop add(ItemStack sell, ItemStack cost) {
        return add(new VillagerTrading.VillagerTradeOffer(sell, cost));
    }

    public VillagerShop add(ItemStack sell, ItemStack cost1, ItemStack cost2) {
        return add(new VillagerTrading.VillagerTradeOffer(sell, cost1, cost2));
    }

    public void open(Player p) {
        VillagerTrading.openTrade(p, this);
    }

    public String getTitle() {
        return text;
    }

    public boolean jsonTitle() {
        return jsonTitle;
    }

    public VillagerTrading.VillagerTradeOffer[] getOfferArray() {
        return getOfferList().toArray(new VillagerTrading.VillagerTradeOffer[offerList.size()]);
    }

    public List<VillagerTrading.VillagerTradeOffer> getOfferList() {
        return offerList;
    }
}
