package com.caved_in.commons.config.serializers.json;

import com.caved_in.commons.item.EnchantWrapper;
import com.google.gson.*;
import org.bukkit.enchantments.Enchantment;
import org.jglue.fluentjson.JsonBuilderFactory;

import java.lang.reflect.Type;

public class EnchantWrapperJsonSerializer implements JsonSerializer<EnchantWrapper>, JsonDeserializer<EnchantWrapper> {

    @Override
    public EnchantWrapper deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject ecwObj = jsonElement.getAsJsonObject();
        String enchantName = ecwObj.get("enchantment").getAsString();
        int level = ecwObj.get("level").getAsInt();
        boolean glow = ecwObj.get("glow").getAsBoolean();

        return new EnchantWrapper(Enchantment.getByName(enchantName), level, glow);
    }

    @Override
    public JsonElement serialize(EnchantWrapper enchantWrapper, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = JsonBuilderFactory.buildObject()
                .add("enchantment", enchantWrapper.getEnchantment().getName())
                .add("level", enchantWrapper.getLevel())
                .add("glow", enchantWrapper.isItemGlow())
                .getJson();

        return obj;
    }
}
