package com.caved_in.commons.config.serializers.json;

import com.caved_in.commons.chat.Title;
import com.caved_in.commons.chat.TitleBuilder;
import com.google.gson.*;
import org.bukkit.ChatColor;
import org.jglue.fluentjson.JsonBuilderFactory;

import java.lang.reflect.Type;

public class TitleJsonSerializer implements JsonSerializer<Title>, JsonDeserializer<Title> {
    @Override
    public Title deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();

        TitleBuilder tb = TitleBuilder.create()
                .title(obj.get("title").getAsString())
                .subtitle(obj.get("subtitle").getAsString())
                .fadeIn(obj.get("fade-in-time").getAsInt())
                .fadeOut(obj.get("fade-out-time").getAsInt())
                .stay(obj.get("stay-time").getAsInt());

        if (obj.get("time-in-ticks").getAsBoolean()) {
            tb.ticks();
        }

        tb.titleColor(ChatColor.valueOf(obj.get("title-color").getAsString())).
                subtitleColor(ChatColor.valueOf(obj.get("subtitle-color").getAsString()));

        return tb.build();
    }

    @Override
    public JsonElement serialize(Title title, Type type, JsonSerializationContext jsonSerializationContext) {
        return JsonBuilderFactory.buildObject()
                .add("title", title.getTitle())
                .add("subtitle", title.getSubtitle())
                .add("fade-in-time", title.getFadeInTime())
                .add("fade-out-time", title.getFadeOutTime())
                .add("stay-time", title.getStayTime())
                .add("time-in-ticks", title.isTicks())
                .add("title-color", title.getTitleColor().name())
                .add("subtitle-color", title.getSubtitleColor().name())
                .getJson();
    }
}
