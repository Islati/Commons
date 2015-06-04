package com.caved_in.commons.nms;

import com.google.common.base.Preconditions;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VillagerTrading {

    public static VillagerShop createShop(String title) {
        return new VillagerShop(title);
    }

    public static void openTrade(Player p, VillagerShop shop) {
        openTrade(p, shop.getTitle(), shop.jsonTitle(), shop.getOfferArray());
    }

    public static void openTrade(Player p, String text, boolean json, VillagerTradeOffer... offers) {
        Merchant merchant = new Merchant(text, json, offers);

        EntityHuman humanPlayer = ((CraftPlayer) p).getHandle();
        /*
        Assign the player viewing the merchant as the player passed.
         */
        merchant.a_(humanPlayer);

        /*
        Open the trade for the player!
         */

        humanPlayer.openTrade(merchant);
    }

    public static class Merchant implements IMerchant {
        private IChatBaseComponent sendTitle;
        /*
        The entity who's currently viewing the trade.
         */
        private EntityHuman tradingWith = null;

        /*
        The recipe list used to handle all the recipes associated with the merchant.
         */
        private MerchantRecipeList recipeList = new MerchantRecipeList();

        private String title;
        private boolean json;

        public Merchant(String title, boolean json) {
            setTitle(title, json);
        }

        public Merchant(String title, boolean json, VillagerTradeOffer... offers) {
            for (VillagerTradeOffer offer : offers) {
                recipeList.add(offer.getMerchantRecipe());
            }

            setTitle(title, json);
        }

        public void setTitle(String title, boolean json) {
            Preconditions.checkNotNull(title, "title");

            IChatBaseComponent oldTitle = sendTitle;
            IChatBaseComponent newTitle;

            if (json) {
                try {
                    newTitle = IChatBaseComponent.ChatSerializer.a(title);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid json format (" + title + ")", e);
                }
            } else {
                newTitle = CraftChatMessage.fromString(title)[0];
            }

            this.sendTitle = newTitle;
            this.json = json;
            this.title = title;
        }

        public Merchant title(String text, boolean json) {
            setTitle(text, json);
            return this;
        }

        public void add(VillagerTradeOffer... offers) {
            for (VillagerTradeOffer offer : offers) {
                recipeList.add(offer.getMerchantRecipe());
            }
        }

        @Override
        public void a_(EntityHuman entityHuman) {
            this.tradingWith = entityHuman;
        }

        @Override
        public EntityHuman v_() {
            return tradingWith;
        }

        @Override
        public MerchantRecipeList getOffers(EntityHuman entityHuman) {
            //todo implement a per-player recipe exchange, option, perhaps?s
            return recipeList;
        }

        @Override
        public void a(MerchantRecipe merchantRecipe) {
            recipeList.add(merchantRecipe);
        }

        @Override
        public void a_(net.minecraft.server.v1_8_R3.ItemStack itemStack) {

        }

        @Override
        public IChatBaseComponent getScoreboardDisplayName() {
            return sendTitle;
        }
    }


    public static class VillagerTradeOffer {

        public static enum Slot {
            FIRST,
            SECOND
        }

        public static VillagerTradeOffer of(ItemStack cost, ItemStack offer) {
            return new VillagerTradeOffer(cost, offer);
        }

        public static VillagerTradeOffer of(ItemStack cost, ItemStack cost2, ItemStack offer) {
            return new VillagerTradeOffer(cost, cost2, offer);
        }

        private ItemStack offer, cost1, cost2;

        public VillagerTradeOffer() {
        }

        public VillagerTradeOffer(ItemStack cost, ItemStack offer) {
            this.cost1 = cost;
            this.offer = offer;
        }

        public VillagerTradeOffer(ItemStack cost, ItemStack cost2, ItemStack offer) {
            this.offer = offer;
            this.cost1 = cost;
            this.cost2 = cost2;
        }

        public VillagerTradeOffer offer(ItemStack i) {
            this.offer = i;
            return this;
        }

        public VillagerTradeOffer cost(Slot slot, ItemStack i) {
            if (slot == Slot.FIRST) {
                this.cost1 = i;
            } else {
                this.cost2 = i;
            }

            return this;
        }

        public MerchantRecipe getMerchantRecipe() {
            if (cost1 == null || offer == null) {
                throw new IllegalArgumentException();
            }

            return cost2 == null ? new MerchantRecipe(CraftItemStack.asNMSCopy(cost1), CraftItemStack.asNMSCopy(offer)) : new MerchantRecipe(CraftItemStack.asNMSCopy(cost1), CraftItemStack.asNMSCopy(cost2), CraftItemStack.asNMSCopy(offer));
        }
    }


}
