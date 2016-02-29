package com.caved_in.commons.config.serializers.json;

import com.caved_in.commons.inventory.Inventories;
import com.google.gson.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jglue.fluentjson.JsonBuilderFactory;

import java.lang.reflect.Type;

public class InventoryJsonSerializer implements JsonSerializer<Inventory>, JsonDeserializer<Inventory> {
    @Override
    public Inventory deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson itemGson = new GsonBuilder().registerTypeAdapter(ItemStack.class, new ItemStackJsonSerializer()).create();

        JsonObject serializedInv = jsonElement.getAsJsonObject();

        String title = serializedInv.get("title").getAsString();
        int maxStackSize = serializedInv.get("max-stack-size").getAsInt();
        int size = serializedInv.get("size").getAsInt();

        JsonArray contents = serializedInv.get("contents").getAsJsonArray();

        Inventory inv = Inventories.makeInventory(title, Inventories.getRows(size));
        inv.setMaxStackSize(maxStackSize);

        for (JsonElement element : contents) {
            JsonObject slottedItem = element.getAsJsonObject();
            int slot = slottedItem.get("slot").getAsInt();

            if (slottedItem.get("item").isJsonNull()) {
                continue;
            }

            ItemStack item = itemGson.fromJson(slottedItem.get("item").getAsJsonObject(), ItemStack.class);
            inv.setItem(slot, item);
        }

        return inv;
    }

    @Override
    public JsonElement serialize(Inventory inventory, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject serializedInv = new JsonObject();

        Gson itemGson = new GsonBuilder().registerTypeAdapter(ItemStack.class, new ItemStackJsonSerializer()).create();

        serializedInv.addProperty("title", inventory.getTitle());
        serializedInv.addProperty("max-stack-size", inventory.getMaxStackSize());
        serializedInv.addProperty("size", inventory.getSize());

        JsonArray serializedInvItems = new JsonArray();
        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            /*
            Assure that there's no item in the slot.
            We assign its number to null, to be sure.
             */
            if (item == null) {
                serializedInvItems.add(
                        JsonBuilderFactory.buildObject()
                                .add("slot", i)
                                .add("item", (String) null)
                                .getJson()
                );
                continue;
            }

            JsonObject slotObj = new JsonObject();
            slotObj.addProperty("slot", i);
            slotObj.add("item", itemGson.toJsonTree(item, ItemStack.class));

            serializedInvItems.add(slotObj);
        }

        serializedInv.add("contents", serializedInvItems);
        return serializedInv;
    }
}
