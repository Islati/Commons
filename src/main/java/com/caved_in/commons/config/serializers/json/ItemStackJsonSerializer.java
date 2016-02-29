package com.caved_in.commons.config.serializers.json;

import com.caved_in.commons.item.Items;
import com.google.gson.*;
import com.mysql.jdbc.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jglue.fluentjson.JsonArrayBuilder;
import org.jglue.fluentjson.JsonBuilderFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemStackJsonSerializer implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject serializedItem = new JsonObject();

        serializedItem.addProperty("id", itemStack.getTypeId());
        serializedItem.addProperty("type", itemStack.getType().name());
        serializedItem.addProperty("data-value", Items.getDataValue(itemStack));
        serializedItem.addProperty("amount", itemStack.getAmount());
        /*
        If it has a custom name then we'll serialize that, too!
         */
        if (Items.hasName(itemStack)) {
            serializedItem.addProperty("name", Items.getName(itemStack));
        }

        /*
        Serialize the lore for the item!
         */
        if (Items.hasLore(itemStack)) {
            JsonArray jsonLoreArray = new JsonArray();

            JsonArrayBuilder<?, JsonArray> loreBuilder = JsonBuilderFactory.buildArray();

            for (String loreLine : Items.getLore(itemStack)) {
                loreBuilder.add(loreLine);
            }

            jsonLoreArray = loreBuilder.getJson();

            serializedItem.add("lore", jsonLoreArray);
        } else {
            serializedItem.add("lore", new JsonArray());
        }

        if (Items.hasEnchantments(itemStack)) {
            JsonArray enchantsJsonArray = new JsonArray();

            JsonArrayBuilder<?, JsonArray> enchantBuilder = JsonBuilderFactory.buildArray();

            for (Map.Entry<Enchantment, Integer> enchantEntry : itemStack.getEnchantments().entrySet()) {
                enchantBuilder.add(
                        //Create the information for each enchantment!
                        JsonBuilderFactory.buildObject()
                                .add("type", enchantEntry.getKey().getName())
                                .add("level", enchantEntry.getValue())
                );
            }

            enchantsJsonArray = enchantBuilder.getJson();
            serializedItem.add("enchantments", enchantsJsonArray);
        } else {
            serializedItem.add("enchantments", new JsonArray());
        }

        if (Items.isPlayerSkull(itemStack)) {
            serializedItem.addProperty("skull-owner", ((SkullMeta) itemStack.getItemMeta()).getOwner());
        }

        return serializedItem;
    }

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ItemStack item = null;

        JsonObject serializedItem = jsonElement.getAsJsonObject();

        Material material = Material.getMaterial(serializedItem.get("type").getAsString());
        int dataValue = serializedItem.get("data-value").getAsInt();

        /*
        See if the item is a skull, and if so create it with the
        desired skull owner.
         */
        if (material == Material.SKULL_ITEM && dataValue == 3) {
            String skullOwner = serializedItem.get("skull-owner").getAsString();
            if (!StringUtils.isNullOrEmpty(skullOwner)) {
                item = Items.getSkull(skullOwner);
            }
        } else if (dataValue > 0) {
            item = Items.makeItem(material, dataValue);
        } else {
            item = Items.makeItem(material);
        }

        if (serializedItem.has("name")) {
            Items.setName(item, serializedItem.get("name").getAsString());
        }

        JsonArray loreArray = serializedItem.getAsJsonArray("lore");
        if (loreArray.size() > 0) {
            List<String> loreList = new ArrayList<>();
            loreArray.forEach(la -> loreList.add(la.getAsString()));
            Items.setLore(item, loreList);
        }

        JsonArray enchantArray = serializedItem.getAsJsonArray("enchantments");

        /*
        Add all the serialized enchantments back onto the item :)
         */
        if (enchantArray.size() > 0) {
            for (JsonElement ele : enchantArray) {
                JsonObject enchantObj = ele.getAsJsonObject();
                Items.addEnchantment(item, Enchantment.getByName(enchantObj.get("type").getAsString()), enchantObj.get("level").getAsInt(), true);
            }
        }

        int amount = serializedItem.get("amount").getAsInt();
        if (amount > 1) {
            item.setAmount(amount);
        }

        return item;
    }
}
